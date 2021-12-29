/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Factura;
import com.ec.entidad.Tipoambiente;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioCliente;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.ParamFactura;
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
public class ListaNotaVenta {

    /*RUTAS PARA LOS ARCHIVPOS XML SRI*/
    private static String PATH_BASE = "";
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    ServicioFactura servicioFactura = new ServicioFactura();
    ServicioCliente servicioCliente = new ServicioCliente();
    private List<Factura> lstFacturas = new ArrayList<Factura>();
    //reporte
    AMedia fileContent = null;
    Connection con = null;
    private String buscarCliente = "";
    private String buscarCedula = "";
    private String estadoBusqueda = "TODO";
    private BigDecimal porCobrar = BigDecimal.ZERO;
    //tabla para los parametros del SRI
    private Tipoambiente amb = new Tipoambiente();
    private Date fechainicio = new Date();
    private Date fechafin = new Date();

    public ListaNotaVenta() {
        consultarFactura();
        amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();
    }

    private void consultarFactura() {
        lstFacturas = servicioFactura.FindALlNVMaxVeinte();
    }

    public List<Factura> getLstFacturas() {
        return lstFacturas;
    }

    public void setLstFacturas(List<Factura> lstFacturas) {
        this.lstFacturas = lstFacturas;
    }

    public String getBuscarCliente() {
        return buscarCliente;
    }

    public void setBuscarCliente(String buscarCliente) {
        this.buscarCliente = buscarCliente;
    }

    public String getEstadoBusqueda() {
        return estadoBusqueda;
    }

    public void setEstadoBusqueda(String estadoBusqueda) {
        this.estadoBusqueda = estadoBusqueda;
    }

    public BigDecimal getPorCobrar() {
        return porCobrar;
    }

    public void setPorCobrar(BigDecimal porCobrar) {
        this.porCobrar = porCobrar;
    }

    @Command
    public void reporteNotaVenta(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getFacNumero(), "FACT");
    }

    @Command
    public void reporteComprobante(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getFacNumNotaVenta(), "NTV");
    }

    @Command
    public void reporteFacturaPerzonalizada(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getFacNumero(), "FACT5");
    }

    @Command
    public void reporteNotaVentaTicket(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getFacNumNotaVenta(), "NTV");
    }

    @Command
    @NotifyChange({"lstFacturas", "buscarCliente"})
    public void modificarCotizacion(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        try {
            ParamFactura param = new ParamFactura();
            param.setIdFactura(valor.getIdFactura().toString());
            param.setBusqueda("modificar");
            param.setTipoDoc("NTV");
            final HashMap<String, ParamFactura> map = new HashMap<String, ParamFactura>();

            map.put("valor", param);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/modificar/factura.zul", null, map);
            window.doModal();
//            window.detach();
            buscarFechas();

        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public void reporteGeneral(Integer numeroFactura, String tipo) throws JRException, IOException, NamingException, SQLException {

        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            if (tipo.equals("COMP")) {
                reportPath = reportFile + File.separator + "puntoventa.jasper";
            } else if (tipo.equals("FACT")) {
                reportPath = reportFile + File.separator + "factura.jasper";
            } else if (tipo.equals("FACT5")) {
                reportPath = reportFile + File.separator + "facturaa5.jasper";
            } else if (tipo.equals("NTV")) {
                reportPath = reportFile + File.separator + "notaventaticket.jasper";
            }

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numfactura", numeroFactura);

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
            System.out.println("ERROR EL PRESENTAR EL REPORTE " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }

        }

    }

    //buscart notas de venta
    @Command
    @NotifyChange({"lstFacturas", "buscarCliente"})
    public void buscarLikeCliente() {

        consultarFacturas();

    }

    private void consultarFacturas() {
        lstFacturas = servicioFactura.findNVLikeCliente(buscarCliente);
        //saldoPorCobrar();
    }

    //buscart notas de venta
    @Command
    @NotifyChange({"lstFacturas", "buscarCliente"})
    public void buscarLikeCedula() {

        consultarFacturasForCedula();

    }

    private void consultarFacturasForCedula() {
        lstFacturas = servicioFactura.findNVLikeCedula(buscarCedula);
        //   saldoPorCobrar();
    }

    @Command
    @NotifyChange({"lstFacturas", "buscarCedula", "porCobrar"})
    public void buscarEstado() {
        consultarFacturasEstado();

    }

    private void consultarFacturasEstado() {
        lstFacturas = servicioFactura.findEstadoNotaVenta(estadoBusqueda);
        //saldoPorCobrar();
    }

    @Command
    @NotifyChange({"lstFacturas", "fechafin", "fechainicio"})
    public void buscarFechas() {
        consultarFacturaFecha();
        //saldoPorCobrar();
    }

    private void consultarFacturaFecha() {
        lstFacturas = servicioFactura.findNVFacFecha(fechainicio, fechafin, estadoBusqueda);
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public String getBuscarCedula() {
        return buscarCedula;
    }

    public void setBuscarCedula(String buscarCedula) {
        this.buscarCedula = buscarCedula;
    }

    @Command
    public void cambiarEstadoFact(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        try {
            final HashMap<String, Factura> map = new HashMap<String, Factura>();

            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/modificar/estadofact.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }
    
    /*EXPORTAR A EXCEL
    lstFacturas
     */
    @Command
    public void exportListboxToExcel() throws Exception {
        try {
            File dosfile = new File(exportarExcel());
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO NOTA VENTA" + e.getMessage());
        }
    }

    private String exportarExcel() throws FileNotFoundException, IOException, ParseException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "notasventa.xls";
        System.out.println("Direccion del reporte  " + pathSalida);
        try {
            int j = 0;
            File archivoXLS = new File(pathSalida);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet("NotasVenta");

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

            HSSFCell chfe = r.createCell(j++);
            chfe.setCellValue(new HSSFRichTextString("NotaVenta"));
            chfe.setCellStyle(estiloCelda);

            HSSFCell chfe1 = r.createCell(j++);
            chfe1.setCellValue(new HSSFRichTextString("CI/RUC"));
            chfe1.setCellStyle(estiloCelda);

            HSSFCell chfe11 = r.createCell(j++);
            chfe11.setCellValue(new HSSFRichTextString("Cliente"));
            chfe11.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("F Emision"));
            ch1.setCellStyle(estiloCelda);

            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("Subtotal"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch22 = r.createCell(j++);
            ch22.setCellValue(new HSSFRichTextString("Subtotal 12%"));
            ch22.setCellStyle(estiloCelda);

            HSSFCell ch23 = r.createCell(j++);
            ch23.setCellValue(new HSSFRichTextString("Subtotal 0%"));
            ch23.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(j++);
            ch3.setCellValue(new HSSFRichTextString("Iva"));
            ch3.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(j++);
            ch4.setCellValue(new HSSFRichTextString("Total"));
            ch4.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (Factura item : lstFacturas) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getFacNumero().toString()));

                HSSFCell cf1 = r.createCell(i++);
                cf1.setCellValue(new HSSFRichTextString(item.getIdCliente().getCliCedula().toString()));

                HSSFCell cf11 = r.createCell(i++);
                cf11.setCellValue(new HSSFRichTextString(item.getIdCliente().getCliNombre().toString()));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(sm.format(item.getFacFecha())));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString((ArchivoUtils.redondearDecimales(item.getFacSubtotal(), 2)).toString()));

                HSSFCell c11 = r.createCell(i++);
                c11.setCellValue(new HSSFRichTextString((ArchivoUtils.redondearDecimales(item.getFacTotalBaseGravaba(), 2)).toString()));

                HSSFCell c12 = r.createCell(i++);
                c12.setCellValue(new HSSFRichTextString((ArchivoUtils.redondearDecimales(item.getFacTotalBaseCero(), 2)).toString()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString((ArchivoUtils.redondearDecimales(item.getFacIva(), 2)).toString()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString((ArchivoUtils.redondearDecimales(item.getFacTotal(), 2)).toString()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= lstFacturas.size(); k++) {
                s.autoSizeColumn(k);
            }
            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }
    @Command
    public void verDetallePago(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        try {
            final HashMap<String, Factura> map = new HashMap<String, Factura>();

            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/venta/detallepago.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }

    }
}


 