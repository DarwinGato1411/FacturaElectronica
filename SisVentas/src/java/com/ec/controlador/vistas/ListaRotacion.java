/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador.vistas;

import com.ec.untilitario.ArchivoUtils;
import com.ec.vista.servicios.ServicioRotacionProducto;
import com.ec.vistas.RotacionProducto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.internet.ParseException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;

/**
 *
 * @author gato
 */
public class ListaRotacion {

    ServicioRotacionProducto servicioRotacionProducto = new ServicioRotacionProducto();

    private Date fechainicio = new Date();
    private Date fechafin = new Date();
    private BigDecimal totalVenta = BigDecimal.ZERO;
    private List<RotacionProducto> listaRotacionProductos = new ArrayList<RotacionProducto>();

    /*DIARIA*/
    public ListaRotacion() {

        Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy 
        calendar.add(Calendar.DATE, -6); //el -3 indica que se le restaran 3 dias 
        fechainicio = calendar.getTime();

        //fechainicioDiaria.setDate(-7);
        consultaVentasDiarias();
    }

    @Command
    @NotifyChange({"listaRotacionProductos", "fechainicioDiaria", "fechafinDiaria", "totalVenta"})
    public void buscarDiaria() {

        consultaVentasDiarias();

    }

    private void consultaVentasDiarias() {
        totalVenta = BigDecimal.ZERO;
        listaRotacionProductos = servicioRotacionProducto.findBetweenGroupByProducto(fechainicio, fechafin);

        /*Calculo el total*/
        for (RotacionProducto item : listaRotacionProductos) {
            totalVenta = totalVenta.add(item.getValorVentaProducto());
        }
        /*coloco el porcentaje*/
        for (RotacionProducto item : listaRotacionProductos) {
            BigDecimal itemporcient = BigDecimal.valueOf(100.0).multiply(item.getValorVentaProducto());
            BigDecimal porcentaje = itemporcient.divide(totalVenta, 4, RoundingMode.FLOOR);

            item.setPorcentaje(ArchivoUtils.redondearDecimales(porcentaje, 2));
        }
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

    public List<RotacionProducto> getListaRotacionProductos() {
        return listaRotacionProductos;
    }

    public void setListaRotacionProductos(List<RotacionProducto> listaRotacionProductos) {
        this.listaRotacionProductos = listaRotacionProductos;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    //exportar informacion por dia
    @Command
    public void exportExcelDiario() throws Exception {
        try {
            File dosfile = new File(exportarExcelDiario());
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO RotacionProd" + e.getMessage());
        }
    }

    private String exportarExcelDiario() throws FileNotFoundException, IOException, ParseException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "RotacionProd.xls";
        System.out.println("Direccion del reporte RotacionProd diario " + pathSalida);
        try {
            int j = 0;
            File archivoXLS = new File(pathSalida);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet("Rotacion");

            HSSFFont fuente = wb.createFont();
            fuente.setBoldweight((short) 700);
            HSSFCellStyle estiloCelda = wb.createCellStyle();
            //   estiloCelda.setWrapText(true);
            //  estiloCelda.setAlignment((short) 2);
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
            chfe.setCellValue(new HSSFRichTextString("Producto"));
            chfe.setCellStyle(estiloCelda);

            HSSFCell chfe1 = r.createCell(j++);
            chfe1.setCellValue(new HSSFRichTextString("Cantidad vendida"));
            chfe1.setCellStyle(estiloCelda);

            HSSFCell chfe11 = r.createCell(j++);
            chfe11.setCellValue(new HSSFRichTextString("Total Venta"));
            chfe11.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("Porcentaje (%)"));
            ch1.setCellStyle(estiloCelda);
            int rownum = 1;
            int i = 0;
            BigDecimal totalConFactura = BigDecimal.ZERO;
            BigDecimal totalNotaVenta = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal Fecha = BigDecimal.ZERO;

            for (RotacionProducto item : listaRotacionProductos) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getProdNombre()));

                HSSFCell cf1 = r.createCell(i++);
                cf1.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getCantidadVenta(), 2).toString()));

                HSSFCell cf11 = r.createCell(i++);
                cf11.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getValorVentaProducto(), 2).toString()));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getPorcentaje(),2).toString()));

                /*autemta la siguiente fila*/
                rownum += 1;

            }

//            j = 0;
//            r = s.createRow(rownum);
//            HSSFCell chfeF1 = r.createCell(j++);
//            chfeF1.setCellValue(new HSSFRichTextString(totalConFactura.toString()));
//            chfeF1.setCellStyle(estiloCelda);
//
//            HSSFCell chfeF2 = r.createCell(j++);
//            chfeF2.setCellValue(new HSSFRichTextString(totalNotaVenta.toString()));
//            chfeF2.setCellStyle(estiloCelda);
//
//            HSSFCell chfeF3 = r.createCell(j++);
//            chfeF3.setCellValue(new HSSFRichTextString(total.toString()));
//            chfeF3.setCellStyle(estiloCelda);
//
//            HSSFCell chF4 = r.createCell(j++);
//            chF4.setCellValue(new HSSFRichTextString(""));
//            chF4.setCellStyle(estiloCelda);
            for (int k = 0; k <= listaRotacionProductos.size(); k++) {
                s.autoSizeColumn(k);
            }
            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }

}
