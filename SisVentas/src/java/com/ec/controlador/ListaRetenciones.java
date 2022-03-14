/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.RetencionCompra;
import com.ec.entidad.Tipoambiente;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioCompra;
import com.ec.servicio.ServicioRetencionCompra;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.AutorizarDocumentos;
import com.ec.untilitario.MailerClass;
import com.ec.untilitario.XAdESBESSignature;
import ec.gob.sri.comprobantes.exception.RespuestaAutorizacionException;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.internet.ParseException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class ListaRetenciones {

    /*RUTAS PARA LOS ARCHIVPOS XML SRI*/
    private static String PATH_BASE = "";
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    ServicioRetencionCompra servicioRetencionCompra = new ServicioRetencionCompra();
    ServicioCompra servicioCompra = new ServicioCompra();
    private List<RetencionCompra> listaRetencionCompras = new ArrayList<RetencionCompra>();
    private String buscar = "";
    private String buscarSecuencial = "";
    private String buscarNumFac = "";
    private Date inicio = new Date();
    private Date fin = new Date();
    //tabla para los parametros del SRI
    private Tipoambiente amb = new Tipoambiente();

    public ListaRetenciones() {
        buscarPorFechas();
        amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();
    }

    private void buscarPorFechas() {
        listaRetencionCompras = servicioRetencionCompra.findByFecha(inicio, fin);

    }

    private void buscarFacturaCompra() {
        listaRetencionCompras = servicioRetencionCompra.findByNumeroFactura(buscarNumFac);

    }

    private void buscarPorSecuencialRetencion() {
        listaRetencionCompras = servicioRetencionCompra.findBySecuencialRet(buscarSecuencial);
    }

    @Command
    @NotifyChange({"listaRetencionCompras", "inicio", "fin"})
    public void buscarForFechas() {
        buscarPorFechas();
    }

    @Command
    @NotifyChange({"listaRetencionCompras", "buscarNumFac"})
    public void buscarForNumeroFactura() {
        buscarFacturaCompra();
    }

    @Command
    @NotifyChange({"listaRetencionCompras", "buscarSecuencial"})
    public void buscarForRetencion() {
        buscarPorSecuencialRetencion();
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public List<RetencionCompra> getListaRetencionCompras() {
        return listaRetencionCompras;
    }

    public void setListaRetencionCompras(List<RetencionCompra> listaRetencionCompras) {
        this.listaRetencionCompras = listaRetencionCompras;
    }

    /*envia el doc al sri y notifica por correo electronico*/
    @Command
    @NotifyChange({"listaRetencionCompras"})
    public void autorizarSRI(@BindingParam("valor") RetencionCompra valor)
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
        String nombreArchivoXML = File.separator + "RET-"
                + amb.getAmEstab()
                + valor.getRcoPuntoEmision()
                + valor.getRcoSecuencialText() + ".xml";


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
        String archivo = aut.generaXMLComprobanteRetencion(valor, amb, folderGenerados, nombreArchivoXML);

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
        valor.setRcoAutorizacion(claveAccesoComprobante);
        AutorizarDocumentos autorizarDocumentos = new AutorizarDocumentos();
        RespuestaSolicitud resSolicitud = autorizarDocumentos.validar(datos);
        if (resSolicitud != null && resSolicitud.getComprobantes() != null) {
            // Autorizacion autorizacion = null;

            if (resSolicitud.getEstado().equals("RECIBIDA")) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Tipoambiente.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {

                    RespuestaComprobante resComprobante = autorizarDocumentos.autorizarComprobante(claveAccesoComprobante);
                    for (Autorizacion autorizacion : resComprobante.getAutorizaciones().getAutorizacion()) {
                        FileOutputStream nuevo = null;

                        /*CREA EL ARCHIVO XML AUTORIZADO*/
                        System.out.println("pathArchivoNoAutorizado " + pathArchivoNoAutorizado);
                        nuevo = new FileOutputStream(pathArchivoNoAutorizado);
                        nuevo.write(autorizacion.getComprobante().getBytes());
                        if (!autorizacion.getEstado().equals("AUTORIZADO")) {

                            String texto = autorizacion.getMensajes().getMensaje().get(0).getMensaje();
                            String smsInfo = autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional();
                            nuevo.write(autorizacion.getMensajes().getMensaje().get(0).getMensaje().getBytes());
                            if (autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional() != null) {
                                nuevo.write(autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional().getBytes());
                            }

                            valor.setDrcMensajesri(texto);
                            valor.setDrcEstadosri(autorizacion.getEstado());
                            valor.setRcoMsmInfoSri(smsInfo);
                            servicioRetencionCompra.modificar(valor);

                            nuevo.flush();
                        } else {
                            /*COLOCAL LA VALIDACION DE RETENCION AUTORIZADA EN LA COMPRA*/
                            CabeceraCompra compra = valor.getIdCabecera();
                            compra.setCabRetencionAutori("S");
                            servicioCompra.modificar(compra);
                            valor.setRcoAutorizacion(claveAccesoComprobante);
                            valor.setDrcEstadosri(autorizacion.getEstado());
                            valor.setRcoFechaAutorizacion(autorizacion.getFechaAutorizacion().toGregorianCalendar().getTime());

                            /*se agrega la la autorizacion, fecha de autorizacion y se firma nuevamente*/
                            archivoEnvioCliente = aut.generaXMLComprobanteRetencion(valor, amb, foldervoAutorizado, nombreArchivoXML);
                            XAdESBESSignature.firmar(archivoEnvioCliente,
                                    nombreArchivoXML,
                                    amb.getAmClaveAccesoSri(),
                                    amb, foldervoAutorizado);

                            fEnvio = new File(archivoEnvioCliente);

                            servicioRetencionCompra.modificar(valor);
                            System.out.println("PATH DEL ARCHIVO PARA ENVIAR AL CLIENTE " + archivoEnvioCliente);
                            ArchivoUtils.reporteGeneralPdfMail(archivoEnvioCliente.replace(".xml", ".pdf"), valor.getRcoCodigo(), "RET");
//                            ArchivoUtils.zipFile(fEnvio, archivoEnvioCliente);
                            /*GUARDA EL PATH PDF CREADO*/
                            valor.setRcoPathRet(archivoEnvioCliente.replace(".xml", ".pdf"));
                            servicioRetencionCompra.modificar(valor);
                            /*envia el mail*/

                            String[] attachFiles = new String[2];
                            attachFiles[0] = archivoEnvioCliente.replace(".xml", ".pdf");
                            attachFiles[1] = archivoEnvioCliente.replace(".xml", ".xml");
                            MailerClass mail = new MailerClass();
//                        if (valor.getIdCabecera(). getIdCliente().getCliClave() == null) {
//                            Cliente mod = valor.getIdCliente();
//                            mod.setCliClave(ArchivoUtils.generaraClaveTemporal());
//                            servicioRetencionCompra.modificar(mod);
//                        }
                            if (valor.getIdCabecera().getIdProveedor().getProvCorreo() != null) {

                                mail.sendMailSimple(valor.getIdCabecera().getIdProveedor().getProvCorreo(),
                                        attachFiles,
                                        "RETENCION ELECTRONICA",
                                        valor.getRcoAutorizacion(),
                                        valor.getRcoSecuencialText(),
                                        BigDecimal.ZERO,
                                        valor.getIdCabecera().getIdProveedor().getProvNombre());

                            }
                        }

                    }
                } catch (RespuestaAutorizacionException ex) {
                    Logger.getLogger(ListaFacturas.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    String smsInfo = resSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getInformacionAdicional();
                    ArchivoUtils.FileCopy(pathArchivoFirmado, pathArchivoNoAutorizado);
                    valor.setDrcEstadosri(resSolicitud.getEstado());
                    valor.setDrcMensajesri(resSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje().get(0).getMensaje());
                    valor.setRcoMsmInfoSri(smsInfo);
                    servicioRetencionCompra.modificar(valor);
                } catch (Exception e) {
                    valor.setDrcMensajesri(e.getMessage());
                    servicioRetencionCompra.modificar(valor);
                }
            }
        } else {
            valor.setDrcMensajesri(resSolicitud.getEstado());
            // valor.setDrcMensajesri("SRI NO ENVIA LA RESPUESTA DE LA SOLICITUD DEVUELVE NULO");
            servicioRetencionCompra.modificar(valor);
        }

    }

    /*envia el doc al sri y notifica por correo electronico*/
    @Command
    @NotifyChange({"listaRetencionCompras"})
    public void reenviarSri(@BindingParam("valor") RetencionCompra valor)
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
        String nombreArchivoXML = File.separator + "RET-"
                + amb.getAmEstab()
                + valor.getRcoPuntoEmision()
                + valor.getRcoSecuencialText() + ".xml";


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
        String archivo = aut.generaXMLComprobanteRetencion(valor, amb, folderGenerados, nombreArchivoXML);

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
        valor.setRcoAutorizacion(claveAccesoComprobante);
        AutorizarDocumentos autorizarDocumentos = new AutorizarDocumentos();

        try {

            RespuestaComprobante resComprobante = autorizarDocumentos.autorizarComprobante(claveAccesoComprobante);
            for (Autorizacion autorizacion : resComprobante.getAutorizaciones().getAutorizacion()) {
                FileOutputStream nuevo = null;

                /*CREA EL ARCHIVO XML AUTORIZADO*/
                System.out.println("pathArchivoNoAutorizado " + pathArchivoNoAutorizado);
                nuevo = new FileOutputStream(pathArchivoNoAutorizado);
                nuevo.write(autorizacion.getComprobante().getBytes());
                if (!autorizacion.getEstado().equals("AUTORIZADO")) {

                    String texto = autorizacion.getMensajes().getMensaje().get(0).getMensaje();
                    String smsInfo = autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional();
                    nuevo.write(autorizacion.getMensajes().getMensaje().get(0).getMensaje().getBytes());
                    if (autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional() != null) {
                        nuevo.write(autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional().getBytes());
                    }

                    valor.setDrcMensajesri(texto);
                    valor.setDrcEstadosri(autorizacion.getEstado());
                    valor.setRcoMsmInfoSri(smsInfo);
                    servicioRetencionCompra.modificar(valor);

                    nuevo.flush();
                } else {
                    /*COLOCAL LA VALIDACION DE RETENCION AUTORIZADA EN LA COMPRA*/
                    CabeceraCompra compra = valor.getIdCabecera();
                    compra.setCabRetencionAutori("S");
                    servicioCompra.modificar(compra);
                    valor.setRcoAutorizacion(claveAccesoComprobante);
                    valor.setDrcEstadosri(autorizacion.getEstado());
                    valor.setRcoFechaAutorizacion(autorizacion.getFechaAutorizacion().toGregorianCalendar().getTime());

                    /*se agrega la la autorizacion, fecha de autorizacion y se firma nuevamente*/
                    archivoEnvioCliente = aut.generaXMLComprobanteRetencion(valor, amb, foldervoAutorizado, nombreArchivoXML);
                    XAdESBESSignature.firmar(archivoEnvioCliente,
                            nombreArchivoXML,
                            amb.getAmClaveAccesoSri(),
                            amb, foldervoAutorizado);

                    fEnvio = new File(archivoEnvioCliente);

                    servicioRetencionCompra.modificar(valor);
                    System.out.println("PATH DEL ARCHIVO PARA ENVIAR AL CLIENTE " + archivoEnvioCliente);
                    ArchivoUtils.reporteGeneralPdfMail(archivoEnvioCliente.replace(".xml", ".pdf"), valor.getRcoCodigo(), "RET");
//                            ArchivoUtils.zipFile(fEnvio, archivoEnvioCliente);
                    /*GUARDA EL PATH PDF CREADO*/
                    valor.setRcoPathRet(archivoEnvioCliente.replace(".xml", ".pdf"));
                    servicioRetencionCompra.modificar(valor);
                    /*envia el mail*/

                    String[] attachFiles = new String[2];
                    attachFiles[0] = archivoEnvioCliente.replace(".xml", ".pdf");
                    attachFiles[1] = archivoEnvioCliente.replace(".xml", ".xml");
                    MailerClass mail = new MailerClass();
//                        if (valor.getIdCabecera(). getIdCliente().getCliClave() == null) {
//                            Cliente mod = valor.getIdCliente();
//                            mod.setCliClave(ArchivoUtils.generaraClaveTemporal());
//                            servicioRetencionCompra.modificar(mod);
//                        }
                    if (valor.getIdCabecera().getIdProveedor().getProvCorreo() != null) {
                        mail.sendMailSimple(valor.getIdCabecera().getIdProveedor().getProvCorreo(),
                                attachFiles,
                                "RETENCION ELECTRONICA",
                                valor.getRcoAutorizacion(),
                                valor.getRcoSecuencialText(),
                                BigDecimal.ZERO,
                                valor.getIdCabecera().getIdProveedor().getProvNombre());
                    }
                }

            }
        } catch (RespuestaAutorizacionException ex) {
            Logger.getLogger(ListaFacturas.class.getName()).log(Level.SEVERE, null, ex);
            valor.setRcoMsmInfoSri(ex.getMessage());
            servicioRetencionCompra.modificar(valor);
        }

    }

    public String getBuscarSecuencial() {
        return buscarSecuencial;
    }

    public void setBuscarSecuencial(String buscarSecuencial) {
        this.buscarSecuencial = buscarSecuencial;
    }

    public String getBuscarNumFac() {
        return buscarNumFac;
    }

    public void setBuscarNumFac(String buscarNumFac) {
        this.buscarNumFac = buscarNumFac;
    }

    @Command
    public void exportListboxToExcel() throws Exception {
        try {
            File dosfile = new File(exportarExcel());
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO" + e.getMessage());
        }
    }

    private String exportarExcel() throws FileNotFoundException, IOException, ParseException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "retenciones.xls";
        System.out.println("Direccion del reporte  " + pathSalida);
        try {
            int j = 1;
            File archivoXLS = new File(pathSalida);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet("Retenciones");

            HSSFFont fuente = wb.createFont();
            fuente.setBoldweight((short) 700);
            HSSFCellStyle estiloCelda = wb.createCellStyle();
            estiloCelda.setWrapText(true);
            estiloCelda.setAlignment((short) 2);
            estiloCelda.setFont(fuente);

            HSSFCellStyle estiloCeldaInterna = wb.createCellStyle();
            estiloCeldaInterna.setWrapText(true);
            estiloCeldaInterna.setAlignment((short) 5);
            estiloCeldaInterna.setFont(fuente);

            HSSFCellStyle estiloCelda1 = wb.createCellStyle();
            estiloCelda1.setWrapText(true);
            estiloCelda1.setFont(fuente);

            HSSFRow r = null;

            HSSFCell c = null;
            r = s.createRow(0);

            HSSFCell chfe = r.createCell(0);
            chfe.setCellValue(new HSSFRichTextString("Factura Compra"));
            chfe.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("F Emision"));
            ch1.setCellStyle(estiloCelda);

            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("Secuencial Ret"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(j++);
            ch3.setCellValue(new HSSFRichTextString("Estado SRI"));
            ch3.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(j++);
            ch4.setCellValue(new HSSFRichTextString("Fecha Aut. SRI"));
            ch4.setCellStyle(estiloCelda);

            HSSFCell ch5 = r.createCell(j++);
            ch5.setCellValue(new HSSFRichTextString("Clace_Acceso"));
            ch5.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (RetencionCompra item : listaRetencionCompras) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getIdCabecera().getCabNumFactura().toString()));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(sm.format(item.getIdCabecera().getCabFechaEmision())));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getRcoSecuencialText()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getDrcEstadosri()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(sm.format(item.getRcoFechaAutorizacion())));

                HSSFCell c4 = r.createCell(i++);
                c4.setCellValue(new HSSFRichTextString(item.getRcoAutorizacion()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaRetencionCompras.size(); k++) {
                s.autoSizeColumn(k);
            }
            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }
    //reporte
    AMedia fileContent = null;
    Connection con = null;

    @Command
    public void reporteGeneral(@BindingParam("valor") RetencionCompra retencionCompra) throws JRException, IOException, NamingException, SQLException {

        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";

            reportPath = reportFile + File.separator + "retencion.jasper";

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numfactura", retencionCompra.getRcoCodigo());

            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);

            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Reporte", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/venta/contenedorReporte.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            if (emf != null) {
                emf.getTransaction().rollback();
            }
            System.out.println("ERROR EL PRESENTAR EL REPORTE " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }
            if (con != null) {
                con.close();
            }

        }

    }

    @Command
    public void crearRetencionCompra(@BindingParam("valor") RetencionCompra retencionCompra) {
        try {
            final HashMap<String, CabeceraCompra> map = new HashMap<String, CabeceraCompra>();

            map.put("valor", retencionCompra.getIdCabecera());
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/compra/retencion.zul", null, map);
            window.doModal();
//            window.detach();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }
}
