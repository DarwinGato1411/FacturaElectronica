/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Factura;
import com.ec.entidad.ProductoVendido;
import com.ec.untilitario.ArchivoUtils;
import com.ec.vista.servicios.ServicioProdcutosVendidos;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;

import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Filedownload;

/**
 *
 * @author gato
 */
public class VistaVentaProducto {

    ServicioProdcutosVendidos servicioProdcutosVendidos = new ServicioProdcutosVendidos();
    private List<ProductoVendido> productosVendidos = new ArrayList<ProductoVendido>();
    Date fechaInicio = new Date();
    Date fechaFin = new Date();

    public VistaVentaProducto() {
        getVentas();
    }

    public void getVentas() {

        fechaInicio = parseFechaInicial(new Date(), "inicio");
        fechaFin = parseFechaInicial(new Date(), "fin");
        productosVendidos = servicioProdcutosVendidos.obtenerProductosVendidos(fechaInicio, fechaFin);

    }

    public Date parseFechaInicial(Date Fecha, String tipo) {
        try {
            Date fechaActual = Fecha;

            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
            String fechaFormateada = formatoFecha.format(fechaActual);

            String hora = " 00:00:00";
            int mes = -1;
            if (tipo.equals("fin")) {
                hora = " 23:59:59";
                mes = 0;
            }
            String fechaConHoraCero = fechaFormateada + hora;
            SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date fechaConHora = formatoFechaHora.parse(fechaConHoraCero);

            // Crear una instancia de Calendar y establecer la fecha con hora
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaConHora);

            // Restar un mes
            calendar.add(Calendar.MONTH, mes);

            // Obtener la nueva fecha
            Date nuevaFecha = calendar.getTime();

            return nuevaFecha;
        } catch (Exception e) {
            System.out.println("Error al obtener fecha");
            return null;
        }
    }

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") String valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

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

    private String exportarExcel() throws FileNotFoundException, IOException, javax.mail.internet.ParseException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        String pathSalida = directorioReportes + File.separator + "ProductosVendidos.xls";
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
            HSSFSheet s = wb.createSheet("Emitidas");

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

            HSSFCell chf0 = r.createCell(j++);
            chf0.setCellValue(new HSSFRichTextString("Codigo"));
            chf0.setCellStyle(estiloCelda);

            HSSFCell chf1 = r.createCell(j++);
            chf1.setCellValue(new HSSFRichTextString("Nombre"));
            chf1.setCellStyle(estiloCelda);

            HSSFCell chf2 = r.createCell(j++);
            chf2.setCellValue(new HSSFRichTextString("Cantidad vendido"));
            chf2.setCellStyle(estiloCelda);

            HSSFCell chf3 = r.createCell(j++);
            chf3.setCellValue(new HSSFRichTextString("Total vendido"));
            chf3.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (ProductoVendido item : productosVendidos) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getProdCodigo()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getProdNombre()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(String.valueOf(item.getCantidadVendido())));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(String.valueOf(item.getTotalVenta())));

                rownum += 1;

            }

            j = 0;
            r = s.createRow(rownum);

            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }

    @Command
    @NotifyChange({"productosVendidos", "buscarCliente"})
    public void buscarProductos() {
        System.out.println(fechaInicio);
        System.out.println(fechaFin);
        productosVendidos = servicioProdcutosVendidos.obtenerProductosVendidos(fechaInicio, fechaFin);
    }

    public List<ProductoVendido> getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(List<ProductoVendido> productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}
