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
import com.ec.untilitario.MailerClass;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.zkoss.bind.annotation.BindingParam;

/**
 *
 * @author Darwin
 */
@WebListener
public class TareasBackground implements ServletContextListener {

    private ScheduledExecutorService scheduler;

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();

    ServicioFactura servicioFactura = new ServicioFactura();
    Tipoambiente amb = servicioTipoAmbiente.FindALlTipoambiente();

    private static String PATH_BASE = "";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Aplicación iniciada. Iniciando scheduler...");

        scheduler = Executors.newSingleThreadScheduledExecutor();

        Runnable tarea = () -> {
            String time = java.time.LocalDateTime.now().toString();

            Date fechaInicio = ArchivoUtils.recuperarFecha(new Date(), "inicio");
            Date fechaFin = ArchivoUtils.recuperarFecha(new Date(), "fin");

            // Aquí tu lógica: actualizar BD, enviar correo, etc.
            if (amb.getAmEnvioSriAutomatico()) {
                List<Factura> listaFactura = servicioFactura.findBetweenPendientesEnviarSRI(fechaInicio, fechaFin);
                List<Factura> listaFacturaDev = servicioFactura.findBetweenDevueltaPorReenviarSRI(fechaInicio, fechaFin);
                System.out.println("Envio activado: " + time);
                for (Factura items : listaFactura) {
                    try {
                        autorizarFacturasSRI(items);
                    } catch (JRException | IOException | NamingException | SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        Logger.getLogger(TareasBackground.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

//                 List<Factura> listaFacturaDev = servicioFactura.findBetweenDevueltaPorReenviarSRI(fechaInicio, fechaFin);
                System.out.println("Envio activado: " + time);
                for (Factura items : listaFacturaDev) {
                    try {
                        reenviarSRI(items);
                    } catch (JRException | IOException | NamingException | SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                        Logger.getLogger(TareasBackground.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else {
                System.out.println("Envio desactivado" + time);
            }
        };

        // Ejecutar cada 10 segundos, empezando inmediatamente
        scheduler.scheduleAtFixedRate(tarea, 0, 1, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Aplicación detenida. Deteniendo scheduler...");
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                if (scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    System.out.println("Scheduler detenido correctamente.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void autorizarFacturasSRI(Factura valor) throws JRException, IOException, NamingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String folderGenerados = PATH_BASE + File.separator + amb.getAmGenerados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();
        String folderEnviarCliente = PATH_BASE + File.separator + amb.getAmEnviocliente()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();
        String folderFirmado = PATH_BASE + File.separator + amb.getAmFirmados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();

        String foldervoAutorizado = PATH_BASE + File.separator + amb.getAmAutorizados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();

        String folderNoAutorizados = PATH_BASE + File.separator + amb.getAmNoAutorizados()
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
        AutorizarDocumentos autorizarDocumentos = new AutorizarDocumentos();
        RespuestaSolicitud resSolicitud = autorizarDocumentos.validar(datos);
        if (resSolicitud != null && resSolicitud.getComprobantes() != null) {
            // Autorizacion autorizacion = null;

            if (resSolicitud.getEstado().equals("RECIBIDA")) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(Tipoambiente.class.getName()).log(Level.SEVERE, null, ex);
//                }
                try {

                    RespuestaComprobante resComprobante = autorizarDocumentos.autorizarComprobante(claveAccesoComprobante);
                    if (resComprobante.getAutorizaciones().getAutorizacion().isEmpty()) {
                        valor.setMensajesri("ERROR EN EL METODO DE AUTORIZAR NO DEVUELVE NADA ENVIO");
                        servicioFactura.modificar(valor);
//                        return;
                    }

                    for (Autorizacion autorizacion : resComprobante.getAutorizaciones().getAutorizacion()) {
                        FileOutputStream nuevo = null;

                        /*CREA EL ARCHIVO XML AUTORIZADO*/
//                        System.out.println("pathArchivoNoAutorizado " + pathArchivoNoAutorizado);
                        nuevo = new FileOutputStream(pathArchivoNoAutorizado);
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

                                if (!autorizacion.getMensajes().getMensaje().isEmpty()) {
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
                            fEnvio = new File(archivoEnvioCliente);

//                            System.out.println("PATH DEL ARCHIVO PARA ENVIAR AL CLIENTE " + archivoEnvioCliente);
//                            reporteGeneralPdfMail(archivoEnvioCliente.replace(".xml", ".pdf"), valor.getFacNumero(), "FACT");
//
//                            String[] attachFiles = new String[2];
//                            attachFiles[0] = archivoEnvioCliente.replace(".xml", ".pdf");
//                            attachFiles[1] = archivoEnvioCliente.replace(".xml", ".xml");
//                            MailerClass mail = new MailerClass();
//                            if (valor.getIdCliente().getCliCorreo() != null) {
//                                mail.sendMailSimple(valor.getIdCliente().getCliCorreo(),
//                                        attachFiles,
//                                        "FACTURA ELECTRONICA",
//                                        valor.getFacClaveAcceso(),
//                                        valor.getFacNumeroText(),
//                                        valor.getFacTotal(),
//                                        valor.getIdCliente().getCliNombre());
//                            }
                        }

                    }
                } catch (RespuestaAutorizacionException ex) {
                    Logger.getLogger(ListaFacturas.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                String smsInfo = resSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getMensaje();
                ArchivoUtils.FileCopy(pathArchivoFirmado, pathArchivoNoAutorizado);
                valor.setEstadosri(resSolicitud.getEstado());
                valor.setMensajesri(resSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getMensaje());
                valor.setFacMsmInfoSri(smsInfo);
                if (smsInfo != null) {
//                    if (smsInfo.equals("ERROR SECUENCIAL REGISTRADO")) {
//
//                        if (Messagebox.show("¿El numero de factura ya se encuentra en el SRI desea crear un nuevo secuencial?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
//                            numeroFactura();
//                            valor.setFacNumero(numeroFactura);
//                            valor.setFacNumeroText(numeroFacturaText);
//                            Clients.showNotification("EL NUEVO SECUENCIAL ASIGNADO ES:  " + numeroFacturaText + " ESTE DOCUMENTO DEBE SER ENVIADO NUEVAMENTE",
//                                    Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 5000, true);
//
//                        }
//
                    servicioFactura.modificar(valor);
//                    }
                }

            }
        } else {

            valor.setMensajesri(resSolicitud != null ? resSolicitud.getEstado() != null ? resSolicitud.getEstado() : "SIN MENSAJE DE ERROR AL VALIDAR" : "VALIDACION NULL");
            servicioFactura.modificar(valor);
        }
    }

    private void reenviarSRI(@BindingParam("valor") Factura valor)
            throws JRException, IOException, NamingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        String folderGenerados = PATH_BASE + File.separator + amb.getAmGenerados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();
        String folderEnviarCliente = PATH_BASE + File.separator + amb.getAmEnviocliente()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();
        String folderFirmado = PATH_BASE + File.separator + amb.getAmFirmados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();

        String foldervoAutorizado = PATH_BASE + File.separator + amb.getAmAutorizados()
                + File.separator + new Date().getYear()
                + File.separator + new Date().getMonth();

        String folderNoAutorizados = PATH_BASE + File.separator + amb.getAmNoAutorizados()
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
        AutorizarDocumentos autorizarDocumentos = new AutorizarDocumentos();
//        RespuestaSolicitud resSolicitud = autorizarDocumentos.validar(datos);
//        if (resSolicitud != null && resSolicitud.getComprobantes() != null) {
//            // Autorizacion autorizacion = null;
//
//            if (resSolicitud.getEstado().equals("RECIBIDA")) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Tipoambiente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

            RespuestaComprobante resComprobante = autorizarDocumentos.autorizarComprobante(claveAccesoComprobante);
            System.out.println("RespuestaComprobante " + resComprobante);
//            if (resComprobante.getAutorizaciones().getAutorizacion() == null) {
//                Clients.showNotification("No se encontro el documento, presione el boton enviar.",
//                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 5000, true);
//                return;
//            }

            if (resComprobante.getAutorizaciones().getAutorizacion().isEmpty()) {
                valor.setMensajesri("ERROR EN EL METODO DE AUTORIZAR NO DEVUELVE NADA REENVIO");
                servicioFactura.modificar(valor);
            }
            for (Autorizacion autorizacion : resComprobante.getAutorizaciones().getAutorizacion()) {
                FileOutputStream nuevo = null;

                /*CREA EL ARCHIVO XML AUTORIZADO*/
                if (!autorizacion.getEstado().equals("AUTORIZADO")) {
//                    System.out.println("pathArchivoNoAutorizado " + pathArchivoNoAutorizado);
//                    nuevo = new FileOutputStream(pathArchivoNoAutorizado);
//                    nuevo.write(autorizacion.getComprobante().getBytes());

                    String texto = autorizacion.getMensajes() != null ? autorizacion.getMensajes().getMensaje().get(0).getMensaje() : "";
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
