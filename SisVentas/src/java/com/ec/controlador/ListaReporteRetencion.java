/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.ReporteRetencion;
import com.ec.entidad.Tipoambiente;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioCompra;
import com.ec.servicio.ServicioReporteRetencion;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.untilitario.ArchivoUtils;
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
import javax.persistence.EntityTransaction;
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
public class ListaReporteRetencion {

    /*RUTAS PARA LOS ARCHIVPOS XML SRI*/
    private static String PATH_BASE = "";
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    ServicioReporteRetencion servicioReporteRetencion = new ServicioReporteRetencion();
    ServicioCompra servicioCompra = new ServicioCompra();
    private List<ReporteRetencion> listaReporteRetencions = new ArrayList<ReporteRetencion>();
    private String buscar = "";
    private String buscarSecuencial = "";
    private String buscarNumFac = "";
    private Date inicio = new Date();
    private Date fin = new Date();
    //tabla para los parametros del SRI
    private Tipoambiente amb = new Tipoambiente();

    public ListaReporteRetencion() {
        buscarPorFechas();
        amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();
    }

    private void buscarPorFechas() {
        listaReporteRetencions = servicioReporteRetencion.findByFecha(inicio, fin);

    }

    private void buscarFacturaCompra() {
        listaReporteRetencions = servicioReporteRetencion.findByNumeroFactura(buscarNumFac);

    }

    private void buscarPorSecuencialRetencion() {
        listaReporteRetencions = servicioReporteRetencion.findBySecuencialRet(buscarSecuencial);
    }

    @Command
    @NotifyChange({"listaReporteRetencions", "inicio", "fin"})
    public void buscarForFechas() {
        buscarPorFechas();
    }

    @Command
    @NotifyChange({"listaReporteRetencions", "buscarNumFac"})
    public void buscarForNumeroFactura() {
        buscarFacturaCompra();
    }

    @Command
    @NotifyChange({"listaReporteRetencions", "buscarSecuencial"})
    public void buscarForRetencion() {
        buscarPorSecuencialRetencion();
    }

    @Command
    public void cambiarEstadoRet(@BindingParam("valor") ReporteRetencion valor) throws JRException, IOException, NamingException, SQLException {
        try {
            final HashMap<String, ReporteRetencion> map = new HashMap<String, ReporteRetencion>();

            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/modificar/estadofactRet.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
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

    public List<ReporteRetencion> getListaReporteRetencions() {
        return listaReporteRetencions;
    }

    public void setListaReporteRetencions(List<ReporteRetencion> listaReporteRetencions) {
        this.listaReporteRetencions = listaReporteRetencions;
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

        String pathSalida = directorioReportes + File.separator + "retenciones_rep.xls";
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

            HSSFCell ch6 = r.createCell(j++);
            ch6.setCellValue(new HSSFRichTextString("Total iva"));
            ch6.setCellStyle(estiloCelda);

            HSSFCell ch7 = r.createCell(j++);
            ch7.setCellValue(new HSSFRichTextString("Total renta"));
            ch7.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (ReporteRetencion item : listaReporteRetencions) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getCabNumFactura().toString()));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(sm.format(item.getCabFechaEmision())));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getRcoSecuencialText()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getDrcEstadosri()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(item.getRcoFechaAutorizacion() != null ? sm.format(item.getRcoFechaAutorizacion()) : ""));

                HSSFCell c4 = r.createCell(i++);
                c4.setCellValue(new HSSFRichTextString(item.getRcoAutorizacion()));
                HSSFCell c5 = r.createCell(i++);
                c5.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getValorIva(), 2).toString()));
                HSSFCell c6 = r.createCell(i++);
                c6.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getValorRenta(), 2).toString()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaReporteRetencions.size(); k++) {
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
    public void reporteGeneral(@BindingParam("valor") ReporteRetencion retencionCompra) throws JRException, IOException, NamingException, SQLException {

        EntityManager emf = HelperPersistencia.getEMF();
        EntityTransaction transaction = null;

        try {
            transaction = emf.getTransaction();
            transaction.begin();
            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = reportFile + File.separator + "retencion.jasper";

            Map<String, Object> parametros = new HashMap<>();

            parametros.put("numfactura", retencionCompra.getRcoCodigo());

            if (con != null) {
                System.out.println("Conexión Realizada Correctamente");
            }

            FileInputStream is = new FileInputStream(reportPath);
            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Reporte", "pdf", "application/pdf", mediais);
            fileContent = amedia;

            final HashMap<String, AMedia> map = new HashMap<>();
            map.put("pdf", fileContent);

            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/venta/contenedorReporte.zul", null, map);
            window.doModal();

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("ERROR AL PRESENTAR EL REPORTE: " + e.getMessage());
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
            if (con != null) {
                con.close();
            }
        }
    }

}
