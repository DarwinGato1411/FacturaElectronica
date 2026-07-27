/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.DetalleKardex;
import com.ec.entidad.Kardex;
import com.ec.entidad.Producto;
import com.ec.entidad.RetencionCompra;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.Tipokardex;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioDetalleKardex;
import com.ec.servicio.ServicioKardex;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.servicio.ServicioTipoKardex;
import com.ec.untilitario.ArchivoUtils;
import java.io.BufferedWriter;
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
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;

/**
 *
 * @author gato
 */
public class KardexGeneral {

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    ServicioKardex servicioKardex = new ServicioKardex();
    private String estdoKardex = "TOD";
    private List<Kardex> listKardex = new ArrayList<Kardex>();
    ServicioProducto servicioProducto = new ServicioProducto();
    private static String PATH_BASE = "";

    ServicioDetalleKardex servicioDetalleKardex = new ServicioDetalleKardex();

    private String tipoAjuste = "ING";
    private String prodNombre = "";
    private String motivoAjuste = "";

    ServicioTipoKardex servicioTipoKardex = new ServicioTipoKardex();

    public KardexGeneral() {
        Tipoambiente amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();
        buscarKardex();

    }

    private void buscarKardex() {
        listKardex = servicioKardex.FindALlKardexMaxMininimo(estdoKardex, prodNombre);

    }

    @Command
    @NotifyChange({"listKardex", "estdoKardex"})
    public void consultaKardexMinimoMax() {
        buscarKardex();
    }

    @Command
    @NotifyChange({"listKardex"})
    public void buscarKardexAll() {
        buscarKardex();
    }

    @Command
    public void exportToExcel() {
        try {
            File dosfile = new File(exportarExcel());
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO" + e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(KardexGeneral.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(KardexGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String exportarExcel() throws FileNotFoundException, IOException, ParseException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "kardexproducto.xls";
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
            chfe.setCellValue(new HSSFRichTextString("Codigo"));
            chfe.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("Nombre"));
            ch1.setCellStyle(estiloCelda);

            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("Total"));
            ch2.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (Kardex item : listKardex) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getIdProducto().getProdCodigo()));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getIdProducto().getProdNombre()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getKarTotal(), 2).toString()));

                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listKardex.size(); k++) {
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

    public List<Kardex> getListKardex() {
        return listKardex;
    }

    public void setListKardex(List<Kardex> listKardex) {
        this.listKardex = listKardex;
    }

    public String getEstdoKardex() {
        return estdoKardex;
    }

    public void setEstdoKardex(String estdoKardex) {
        this.estdoKardex = estdoKardex;
    }

    @Command
    @NotifyChange({"listKardex", "estdoKardex"})
    public void cargarProducto() {

        try {

            org.zkoss.util.media.Media media = Fileupload.get();
            if (media instanceof org.zkoss.util.media.AMedia) {
                Boolean existenRepetido = Boolean.FALSE;
                BufferedWriter bfwriter = null;
                File descargar = null;

                String productosRepetidos = "";
                String nombre = media.getName();

                if (!nombre.contains("xls")) {
                    Clients.showNotification("Su documento debe ser un archivo excel",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);

                    return;
                }

                System.out.println("media " + nombre);
                Files.copy(new File(PATH_BASE + File.separator + "CARGAR" + File.separator + nombre),
                        new ByteArrayInputStream(media.getByteData()));

                String rutaArchivo = PATH_BASE + File.separator + "CARGAR" + File.separator + nombre;

                InputStream myFile = new FileInputStream(new File(rutaArchivo));
                HSSFWorkbook wb = new HSSFWorkbook(myFile);
                HSSFSheet sheet = wb.getSheetAt(0);

                HSSFCell cell;
                HSSFRow row;

                System.out.println("Apunto de entrar a loops");
                DetalleKardex detalleKardex = null;

                System.out.println("" + sheet.getLastRowNum());
                Producto prod = new Producto();
                for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
                    row = sheet.getRow(i);
//                    for (int j = 0; j < row.getLastCellNum(); j++) {
//                    for (int j = 0; j < 6; j++) {
                    List<Kardex> prodcutos = servicioKardex.findByCodigo(String.valueOf(row.getCell(0)));

                    if (!prodcutos.isEmpty()) {
//                            cell = row.getCell(j);
                        Kardex selected = prodcutos.get(0);
                        if (selected.getKarTotal().doubleValue() == Double.valueOf(String.valueOf(row.getCell(2)))) {

                        } else if (selected.getKarTotal().doubleValue() > Double.valueOf(String.valueOf(row.getCell(2)))) {

                            BigDecimal saldo = BigDecimal.valueOf(selected.getKarTotal().doubleValue() - Double.valueOf(String.valueOf(row.getCell(2))));
                            Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla("SAL");
                            detalleKardex = new DetalleKardex();
                            Kardex kardex = servicioKardex.FindALlKardexs(selected.getIdProducto());
                            detalleKardex.setIdKardex(kardex);
                            detalleKardex.setDetkFechakardex(new Date());
                            detalleKardex.setDetkFechacreacion(new Date());
                            detalleKardex.setIdTipokardex(tipokardex);
                            detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                            detalleKardex.setDetkDetalles("AJUSTE CARGA GENERAL SALIDA");
//                    detalleKardex.setIdFactura(factura);
                            detalleKardex.setDetkCantidad(saldo);
                            servicioDetalleKardex.crear(detalleKardex);

                            kardex.setKarTotal(BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(2)))));
                            servicioKardex.modificar(kardex);
                        } else if (selected.getKarTotal().doubleValue() < Double.valueOf(String.valueOf(row.getCell(2)))) {

                            BigDecimal saldo = BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(2))) - selected.getKarTotal().doubleValue());
                            Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla("ING");
                            detalleKardex = new DetalleKardex();
                            Kardex kardex = servicioKardex.FindALlKardexs(selected.getIdProducto());
                            detalleKardex.setIdKardex(kardex);
                            detalleKardex.setDetkFechakardex(new Date());
                            detalleKardex.setDetkFechacreacion(new Date());
                            detalleKardex.setIdTipokardex(tipokardex);
                            detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                            detalleKardex.setDetkDetalles("AJUSTE CARGA GENERAL INGRESO");
//                    detalleKardex.setIdFactura(factura);
                            detalleKardex.setDetkCantidad(saldo);
                            servicioDetalleKardex.crear(detalleKardex);

                            kardex.setKarTotal(BigDecimal.valueOf(Double.valueOf(String.valueOf(row.getCell(2)))));
                            servicioKardex.modificar(kardex);
                        }

//                           
                    }

//                    }
                }

                buscarKardex();
                System.out.println("Finalizado");

                Clients.showNotification("Productos ajustados correctamente",
                        Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 3000, true);
            }
        } catch (IOException e) {
            Clients.showNotification("Verifique le archivo para cargar",
                    Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
            e.printStackTrace();
//            Messagebox.show("Upload failed");
        }

    }

    @Command
    @NotifyChange({"listKardex", "estdoKardex"})
    public void cuadrarValor(@BindingParam("valor") Kardex valor) throws JRException, IOException, NamingException, SQLException {

        if (valor.getTotalCuadre().doubleValue() > 0) {

            DetalleKardex detalleKardex = null;
//                            cell = row.getCell(j);
            Kardex selected = valor;
            if (selected.getKarTotal().doubleValue() == selected.getTotalCuadre().doubleValue()) {

            } else if (selected.getKarTotal().doubleValue() > selected.getTotalCuadre().doubleValue()) {

                BigDecimal saldo = BigDecimal.valueOf(selected.getKarTotal().doubleValue() - selected.getTotalCuadre().doubleValue());
                Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla("SAL");
                detalleKardex = new DetalleKardex();
                Kardex kardex = servicioKardex.FindALlKardexs(selected.getIdProducto());
                detalleKardex.setIdKardex(kardex);
                detalleKardex.setDetkFechakardex(new Date());
                detalleKardex.setDetkFechacreacion(new Date());
                detalleKardex.setIdTipokardex(tipokardex);
                detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                detalleKardex.setDetkDetalles("AJUSTE GENERAL INDIVIDUAL SALIDA");
//                    detalleKardex.setIdFactura(factura);
                detalleKardex.setDetkCantidad(saldo);
                servicioDetalleKardex.crear(detalleKardex);

                kardex.setKarTotal(selected.getTotalCuadre());
                servicioKardex.modificar(kardex);

                Clients.showNotification("Realizo un ajuste de salida por: " + saldo,
                        Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 3000, true);
            } else if (selected.getKarTotal().doubleValue() < selected.getTotalCuadre().doubleValue()) {

                BigDecimal saldo = BigDecimal.valueOf(selected.getTotalCuadre().doubleValue() - selected.getKarTotal().doubleValue());
                Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla("ING");
                detalleKardex = new DetalleKardex();
                Kardex kardex = servicioKardex.FindALlKardexs(selected.getIdProducto());
                detalleKardex.setIdKardex(kardex);
                detalleKardex.setDetkFechakardex(new Date());
                detalleKardex.setDetkFechacreacion(new Date());
                detalleKardex.setIdTipokardex(tipokardex);
                detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                detalleKardex.setDetkDetalles("AJUSTE GENERAL INDIVIDUAL INGRESO");
//                    detalleKardex.setIdFactura(factura);
                detalleKardex.setDetkCantidad(saldo);
                servicioDetalleKardex.crear(detalleKardex);

                kardex.setKarTotal(selected.getTotalCuadre());
                servicioKardex.modificar(kardex);
                Clients.showNotification("Realizo un ajuste de ingreso por " + saldo,
                        Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 3000, true);
            }

        } else {
            Clients.showNotification("No puede realizar un cuadre con valor igual o menor a cero",
                    Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
        }//                           
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    
    
}
