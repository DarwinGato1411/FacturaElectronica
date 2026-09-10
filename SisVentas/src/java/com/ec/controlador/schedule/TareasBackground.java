/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador.schedule;

import com.ec.controlador.ListaFacturas;
import com.ec.entidad.Factura;
import com.ec.entidad.Tipoambiente;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.AutorizarDocumentos;
import com.ec.untilitario.XAdESBESSignature;
import ec.gob.sri.comprobantes.exception.RespuestaAutorizacionException;
import ec.gob.sri.comprobantes.util.reportes.ReporteUtil;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Darwin
 */
@WebListener
public class TareasBackground implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(TareasBackground.class.getName());
    private static final int MAX_FACTURAS_POR_CICLO = 8;
    private static final long DELAY_ENTRE_CICLOS_SEG = 60;

    private ScheduledExecutorService scheduler;
    private final AtomicBoolean cicloEnCurso = new AtomicBoolean(false);

    private final ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private final ServicioFactura servicioFactura = new ServicioFactura();
    private Tipoambiente amb;
    private String pathBase = "";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Aplicación iniciada. Iniciando scheduler de envío SRI...");

        ThreadFactory factory = r -> {
            Thread t = new Thread(r, "tareas-background-sri");
            t.setDaemon(true);
            t.setUncaughtExceptionHandler((th, ex) ->
                    LOG.log(Level.SEVERE, "Excepción no capturada en " + th.getName() + "; el scheduler continua", ex));
            return t;
        };
        scheduler = Executors.newSingleThreadScheduledExecutor(factory);

        Runnable tarea = () -> {
            if (!cicloEnCurso.compareAndSet(false, true)) {
                LOG.fine("Ciclo SRI anterior aún en curso; se omite esta corrida.");
                return;
            }
            try {
                ejecutarCicloSri();
            } catch (Throwable ex) {
                LOG.log(Level.SEVERE, "Error en TareasBackground: el scheduler se mantiene activo", ex);
            } finally {
                cicloEnCurso.set(false);
                Thread.interrupted();
            }
        };

        scheduler.scheduleWithFixedDelay(tarea, 30, DELAY_ENTRE_CICLOS_SEG, TimeUnit.SECONDS);
    }

    private void ejecutarCicloSri() {
        amb = servicioTipoAmbiente.FindALlTipoambiente();
        if (amb == null) {
            LOG.warning("ENVIO AUTOMATICO: no hay Tipoambiente configurado");
            return;
        }
        if (!Boolean.TRUE.equals(amb.getAmEnvioSriAutomatico())) {
            LOG.fine("ENVIO DESACTIVADO " + amb.getAmRazonSocial());
            return;
        }

        pathBase = amb.getAmDirBaseArchivos() != null ? amb.getAmDirBaseArchivos() : "";

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        Date fechaInicio = ArchivoUtils.recuperarFecha(cal.getTime(), "inicio");
        Date fechaFin = ArchivoUtils.recuperarFecha(new Date(), "fin");

        List<Factura> pendientes = limitar(
                servicioFactura.findBetweenPendientesEnviarSRI(fechaInicio, fechaFin));
        List<Factura> reenviar = limitar(
                servicioFactura.findBetweenDevueltaPorReenviarSRI(fechaInicio, fechaFin));

        if (pendientes.isEmpty() && reenviar.isEmpty()) {
            return;
        }

        LOG.info("SRI automatico: " + pendientes.size() + " pendientes, "
                + reenviar.size() + " a reenviar. Rango " + fechaInicio + " - " + fechaFin);

        for (Factura items : pendientes) {
            try {
                autorizarFacturasSRI(items);
            } catch (Throwable ex) {
                LOG.log(Level.SEVERE, "Error enviando factura "
                        + (items != null ? items.getFacNumeroText() : "") + " (el scheduler continua)", ex);
            }
        }

        for (Factura items : reenviar) {
            try {
                reenviarSRI(items);
            } catch (Throwable ex) {
                LOG.log(Level.SEVERE, "Error reenviando factura "
                        + (items != null ? items.getFacNumeroText() : "") + " (el scheduler continua)", ex);
            }
        }
    }

    private List<Factura> limitar(List<Factura> lista) {
        if (lista == null || lista.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        if (lista.size() <= MAX_FACTURAS_POR_CICLO) {
            return lista;
        }
        return lista.subList(0, MAX_FACTURAS_POR_CICLO);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Aplicación detenida. Deteniendo scheduler...");
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(15, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                    LOG.warning("Scheduler forzado a detenerse.");
                } else {
                    LOG.info("Scheduler detenido correctamente.");
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private String mensajeSolicitud(RespuestaSolicitud resSolicitud) {
        try {
            return resSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getMensaje();
        } catch (Exception e) {
            return resSolicitud.getEstado() != null ? resSolicitud.getEstado() : "SIN MENSAJE DEL SRI";
        }
    }

    private void autorizarFacturasSRI(Factura valor) throws JRException, IOException, NamingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String folderGenerados = pathBase + File.separator + amb.getAmGenerados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();
        String folderEnviarCliente = pathBase + File.separator + amb.getAmEnviocliente()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();
        String folderFirmado = pathBase + File.separator + amb.getAmFirmados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();

        String foldervoAutorizado = pathBase + File.separator + amb.getAmAutorizados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();

        String folderNoAutorizados = pathBase + File.separator + amb.getAmNoAutorizados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();

        /*EN EL CASO DE NO EXISTIR LOS DIRECTORIOS LOS CREA*/
        File folderGen = new File(folderGenerados);
        if (!folderGen.exists()) {
            folderGen.mkdirs();
        }
        File folderFirm = new File(folderFirmado);
        if (!folderFirm.exists()) {
            folderFirm.mkdirs();
        }

        File folderAu = new File(foldervoAutorizado);
        if (!folderAu.exists()) {
            folderAu.mkdirs();
        }

        File folderCliente = new File(folderEnviarCliente);
        if (!folderCliente.exists()) {
            folderCliente.mkdirs();
        }
        File folderNoAut = new File(folderNoAutorizados);
        if (!folderNoAut.exists()) {
            folderNoAut.mkdirs();
        }
        /*Ubicacion del archivo firmado para obtener la informacion*/

 /*PARA CREAR EL ARCHIVO XML FIRMADO*/
        String nombreArchivoXML = File.separator + "FACT-"
                + valor.getCodestablecimiento()
                + valor.getPuntoemision()
                + valor.getFacNumeroText() + ".xml";


        /*RUTAS FINALES DE,LOS ARCHIVOS XML FIRMADOS Y AUTORIZADOS*/
        String pathArchivoFirmado = folderFirmado + nombreArchivoXML;
        String pathArchivoAutorizado = foldervoAutorizado + nombreArchivoXML;
        String pathArchivoNoAutorizado = folderNoAutorizados + nombreArchivoXML;
        String archivoEnvioCliente = "";
        AutorizarDocumentos aut = new AutorizarDocumentos();
        String archivo = aut.generaXMLFactura(valor, amb, folderGenerados, nombreArchivoXML, Boolean.FALSE, new Date());
        XAdESBESSignature.firmar(archivo, nombreArchivoXML,
                amb.getAmClaveAccesoSri(), amb, folderFirmado);

        File f = new File(pathArchivoFirmado);
        byte[] datos = ArchivoUtils.ConvertirBytes(pathArchivoFirmado);
        String claveAccesoComprobante = ArchivoUtils.obtenerValorXML(f, "/*/infoTributaria/claveAcceso");
        valor.setFacClaveAcceso(claveAccesoComprobante);
        RespuestaSolicitud resSolicitud = aut.validar(datos);
        if (resSolicitud != null && resSolicitud.getComprobantes() != null) {
            // Autorizacion autorizacion = null;

            if (resSolicitud.getEstado().equals("RECIBIDA")) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Tipoambiente.class.getName()).log(Level.SEVERE, null, ex);
//                }
                try {

                    RespuestaComprobante resComprobante = aut.autorizarComprobante(claveAccesoComprobante);
                    if (resComprobante == null || resComprobante.getAutorizaciones() == null
                            || resComprobante.getAutorizaciones().getAutorizacion() == null
                            || resComprobante.getAutorizaciones().getAutorizacion().isEmpty()) {
                        valor.setMensajesri("ERROR EN EL METODO DE AUTORIZAR NO DEVUELVE NADA ENVIO");
                        servicioFactura.modificar(valor);
                        return;
                    }

                    for (Autorizacion autorizacion : resComprobante.getAutorizaciones().getAutorizacion()) {
                        try (FileOutputStream nuevo = new FileOutputStream(pathArchivoNoAutorizado)) {
                        if (autorizacion.getComprobante() != null) {
                            nuevo.write(autorizacion.getComprobante().getBytes());
                        }

                        if (!autorizacion.getEstado().equals("AUTORIZADO")) {
                            String texto = "Sin Identificar el error";
                            String smsInfo = "Sin identificar el error";

                            if (autorizacion.getEstado().equals("EN PROCESO")) {
//                                Clients.showNotification("Autoriza con reenvio ", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000, true);
                                reenviarSRI(valor);
                            } else {

                                if (autorizacion.getMensajes() != null
                                        && autorizacion.getMensajes().getMensaje() != null
                                        && !autorizacion.getMensajes().getMensaje().isEmpty()) {
                                    texto = autorizacion.getMensajes().getMensaje().size() > 0 ? autorizacion.getMensajes().getMensaje().get(0).getMensaje() : "ERROR SIN DEFINIR " + autorizacion.getEstado();
                                    smsInfo = autorizacion.getMensajes().getMensaje().size() > 0 ? autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional() : " ERROR SIN DEFINIR " + autorizacion.getEstado();
                                    nuevo.write(smsInfo.getBytes());
                                    nuevo.write(smsInfo.getBytes());
                                }

                                valor.setMensajesri(texto);
                                valor.setEstadosri(autorizacion.getEstado());
                                valor.setFacMsmInfoSri(smsInfo);
                                nuevo.flush();
                                servicioFactura.modificar(valor);
                            }
                        } else {

                            valor.setFacClaveAutorizacion(claveAccesoComprobante);
                            valor.setEstadosri(autorizacion.getEstado());
//                            String fechaForm = autorizacion.getFechaAutorizacion().toGregorianCalendar().toZonedDateTime().toString();
                            Instant instant = autorizacion.getFechaAutorizacion().toGregorianCalendar().toZonedDateTime().toInstant();
                            Date date = Date.from(instant);
                            valor.setFacFechaAutorizacion(date);
//                            System.out.println("autorizacion.getFechaAutorizacion().toGregorianCalendar().getTime() " + autorizacion.getFechaAutorizacion().toGregorianCalendar().getTime());
                            //se agrega la la autorizacion, fecha de autorizacion y se firma nuevamente/
                            archivoEnvioCliente = aut.generaXMLFactura(valor, amb, foldervoAutorizado, nombreArchivoXML, Boolean.TRUE, autorizacion.getFechaAutorizacion().toGregorianCalendar().getTime());
//                            XAdESBESSignature.firmar(archivoEnvioCliente,
//                                    nombreArchivoXML,
//                                    amb.getAmClaveAccesoSri(),
//                                    amb, foldervoAutorizado);
                            valor.setFacpath(archivoEnvioCliente.replace(".xml", ".pdf"));
                            servicioFactura.modificar(valor);
                        }

                    }
                    }
                } catch (RespuestaAutorizacionException ex) {
                    Logger.getLogger(ListaFacturas.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                String smsInfo = mensajeSolicitud(resSolicitud);
                ArchivoUtils.FileCopy(pathArchivoFirmado, pathArchivoNoAutorizado);
                valor.setEstadosri(resSolicitud.getEstado());
                valor.setMensajesri(smsInfo);
                valor.setFacMsmInfoSri(smsInfo);
                servicioFactura.modificar(valor);
            }
        } else {

            valor.setMensajesri(resSolicitud != null ? resSolicitud.getEstado() != null ? resSolicitud.getEstado() : "SIN MENSAJE DE ERROR AL VALIDAR" : "VALIDACION NULL");
            servicioFactura.modificar(valor);
        }
    }

    private void reenviarSRI(Factura valor)
            throws JRException, IOException, NamingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        String folderGenerados = pathBase + File.separator + amb.getAmGenerados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();
        String folderEnviarCliente = pathBase + File.separator + amb.getAmEnviocliente()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();
        String folderFirmado = pathBase + File.separator + amb.getAmFirmados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();

        String foldervoAutorizado = pathBase + File.separator + amb.getAmAutorizados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();

        String folderNoAutorizados = pathBase + File.separator + amb.getAmNoAutorizados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();

        /*EN EL CASO DE NO EXISTIR LOS DIRECTORIOS LOS CREA*/
        File folderGen = new File(folderGenerados);
        if (!folderGen.exists()) {
            folderGen.mkdirs();
        }
        File folderFirm = new File(folderFirmado);
        if (!folderFirm.exists()) {
            folderFirm.mkdirs();
        }

        File folderAu = new File(foldervoAutorizado);
        if (!folderAu.exists()) {
            folderAu.mkdirs();
        }

        File folderCliente = new File(folderEnviarCliente);
        if (!folderCliente.exists()) {
            folderCliente.mkdirs();
        }
        File folderNoAut = new File(folderNoAutorizados);
        if (!folderNoAut.exists()) {
            folderNoAut.mkdirs();
        }
        /*Ubicacion del archivo firmado para obtener la informacion*/

 /*PARA CREAR EL ARCHIVO XML FIRMADO*/
        String nombreArchivoXML = File.separator + "FACT-"
                + valor.getCodestablecimiento()
                + valor.getPuntoemision()
                + valor.getFacNumeroText() + ".xml";


        /*RUTAS FINALES DE,LOS ARCHIVOS XML FIRMADOS Y AUTORIZADOS*/
        String pathArchivoFirmado = folderFirmado + nombreArchivoXML;
        String pathArchivoAutorizado = foldervoAutorizado + nombreArchivoXML;
        String pathArchivoNoAutorizado = folderNoAutorizados + nombreArchivoXML;
        String archivoEnvioCliente = "";

        File f = null;
        File fEnvio = null;
        byte[] datos = null;
        //tipoambiente tiene los parameteos para los directorios y la firma digital
        AutorizarDocumentos aut = new AutorizarDocumentos();
        /*Generamos el archivo XML de la factura*/
        String archivo = aut.generaXMLFactura(valor, amb, folderGenerados, nombreArchivoXML, Boolean.FALSE, new Date());

        /*amb.getAmClaveAccesoSri() es el la clave proporcionada por el SRI
        archivo es la ruta del archivo xml generado
        nomre del archivo a firmar*/
        XAdESBESSignature.firmar(archivo, nombreArchivoXML,
                amb.getAmClaveAccesoSri(), amb, folderFirmado);

        f = new File(pathArchivoFirmado);

        datos = ArchivoUtils.ConvertirBytes(pathArchivoFirmado);
        //obtener la clave de acceso desde el archivo xml
        String claveAccesoComprobante = ArchivoUtils.obtenerValorXML(f, "/*/infoTributaria/claveAcceso");
        /*GUARDAMOS LA CLAVE DE ACCESO ANTES DE ENVIAR A AUTORIZAR*/
        valor.setFacClaveAcceso(claveAccesoComprobante);
        try {
            Thread.sleep(400);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        try {
            RespuestaComprobante resComprobante = aut.autorizarComprobante(claveAccesoComprobante);
            if (resComprobante == null || resComprobante.getAutorizaciones() == null
                    || resComprobante.getAutorizaciones().getAutorizacion() == null
                    || resComprobante.getAutorizaciones().getAutorizacion().isEmpty()) {
                valor.setMensajesri("ERROR EN EL METODO DE AUTORIZAR NO DEVUELVE NADA REENVIO");
                servicioFactura.modificar(valor);
                return;
            }
            for (Autorizacion autorizacion : resComprobante.getAutorizaciones().getAutorizacion()) {
                FileOutputStream nuevo = null;

                /*CREA EL ARCHIVO XML AUTORIZADO*/
                if (!autorizacion.getEstado().equals("AUTORIZADO")) {
//                    System.out.println("pathArchivoNoAutorizado " + pathArchivoNoAutorizado);
//                    nuevo = new FileOutputStream(pathArchivoNoAutorizado);
//                    nuevo.write(autorizacion.getComprobante().getBytes());

                    String texto = "";
                    if (autorizacion.getMensajes() != null
                            && autorizacion.getMensajes().getMensaje() != null
                            && !autorizacion.getMensajes().getMensaje().isEmpty()) {
                        texto = autorizacion.getMensajes().getMensaje().get(0).getMensaje();
                    }
//                    nuevo.write(autorizacion.getMensajes().getMensaje().get(0).getMensaje().getBytes());
//                    if (autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional() != null) {
//                        nuevo.write(autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional().getBytes());
//                    }

                    valor.setMensajesri(texto);
//                    nuevo.flush();
                    System.out.println("ERROR AL ENVIAR AL SRI " + texto);
                } else {

                    valor.setFacClaveAutorizacion(claveAccesoComprobante);
                    valor.setEstadosri(autorizacion.getEstado());
                    valor.setFacFechaAutorizacion(autorizacion.getFechaAutorizacion().toGregorianCalendar().getTime());

                    /*se agrega la la autorizacion, fecha de autorizacion y se firma nuevamente*/
                    archivoEnvioCliente = aut.generaXMLFactura(valor, amb, foldervoAutorizado, nombreArchivoXML, Boolean.TRUE, autorizacion.getFechaAutorizacion().toGregorianCalendar().getTime());
                    XAdESBESSignature.firmar(archivoEnvioCliente,
                            nombreArchivoXML,
                            amb.getAmClaveAccesoSri(),
                            amb, foldervoAutorizado);

                    fEnvio = new File(archivoEnvioCliente);
                }

//                System.out.println("PATH DEL ARCHIVO PARA ENVIAR AL CLIENTE " + archivoEnvioCliente);
//                ArchivoUtils.reporteGeneralPdfMail(archivoEnvioCliente.replace(".xml", ".pdf"), valor.getFacNumero(), "FACT");
//                ArchivoUtils.zipFile(fEnvio, archivoEnvioCliente);
                /*GUARDA EL PATH PDF CREADO*/
                valor.setFacpath(archivoEnvioCliente.replace(".xml", ".pdf"));
                servicioFactura.modificar(valor);
                /*envia el mail*/

//                String[] attachFiles = new String[2];
//                attachFiles[0] = archivoEnvioCliente.replace(".xml", ".pdf");
//                attachFiles[1] = archivoEnvioCliente.replace(".xml", ".xml");
//                MailerClass mail = new MailerClass();
//                if (valor.getIdCliente().getCliCorreo() != null) {
//                    mail.sendMailSimple(valor.getIdCliente().getCliCorreo(),
//                            attachFiles,
//                            "FACTURA ELECTRONICA",
//                            valor.getFacClaveAcceso(),
//                            valor.getFacNumeroText(),
//                            valor.getFacTotal(),
//                            valor.getIdCliente().getCliNombre());
//                }
            }
        } catch (RespuestaAutorizacionException ex) {
            Logger.getLogger(ListaFacturas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void reporteGeneralPdfMail(String pathPDF, Integer numeroFactura, String tipo) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();
        Connection con = null;
        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);
            String reportPath = ReporteUtil.class.getResource("/reportes/factura.jasper").getPath();
//            String reportPath = "";
//            emf.getTransaction().begin();
//            con = emf.unwrap(Connection.class);
//            if (tipo.contains("FACT")) {
//                reportPath = reportFile + File.separator + "factura.jasper";
//            } else if (tipo.contains("NCRE")) {
//                reportPath = reportFile + File.separator + "notacr.jasper";
//            } else if (tipo.contains("RET")) {
//                reportPath = reportFile + File.separator + "retencion.jasper";
//            } else if (tipo.contains("GUIA")) {
//                reportPath = reportFile + File.separator + "guia.jasper";
//            }

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numfactura", numeroFactura);

            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);

//                byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            JasperPrint print = JasperFillManager.fillReport(reportPath, parametros, con);
            JasperExportManager.exportReportToPdfFile(print, pathPDF);
        } catch (FileNotFoundException e) {
            System.out.println("Error en generar el reporte file " + e.getMessage());
        } catch (JRException e) {
            System.out.println("Error en generar el reporte JRE  " + e.getMessage());
        } finally {
            if (con != null) {
                con.close();
            }
            if (emf != null) {
                emf.close();
                System.out.println("cerro entity");
            }
        }

    }
}
