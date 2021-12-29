/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.FacturasActorizadaSri;
import com.ec.servicio.ServicioFacturasAutorizadas;
import com.ec.untilitario.CantidadTotal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class FacturasAutorizadasCtr {

    ServicioFacturasAutorizadas servicioFacturasAutorizadas = new ServicioFacturasAutorizadas();

    private List<FacturasActorizadaSri> listaFacturasActorizadaSri = new ArrayList<FacturasActorizadaSri>();
    private CantidadTotal cantidadTotal = new CantidadTotal();
    private Date inicio = new Date();
    private Date fin = new Date();

    public FacturasAutorizadasCtr() {
        buscarPorFechas();

    }

    private void buscarPorFechas() {
        listaFacturasActorizadaSri = servicioFacturasAutorizadas.findFacturasAutorizadas(inicio, fin);
        cantidadTotal = servicioFacturasAutorizadas.totalFacturasAutorizadas(inicio, fin);
    }

    @Command
    @NotifyChange({"listaFacturasActorizadaSri", "cantidadTotal", "inicio", "fin"})
    public void buscarForFechas() {
        buscarPorFechas();
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

    public List<FacturasActorizadaSri> getListaFacturasActorizadaSri() {
        return listaFacturasActorizadaSri;
    }

    public void setListaFacturasActorizadaSri(List<FacturasActorizadaSri> listaFacturasActorizadaSri) {
        this.listaFacturasActorizadaSri = listaFacturasActorizadaSri;
    }

    public CantidadTotal getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(CantidadTotal cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
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

        String pathSalida = directorioReportes + File.separator + "autorizadas.xls";
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
            HSSFSheet s = wb.createSheet("Autorizadas");

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
            chfe.setCellValue(new HSSFRichTextString("Factura"));
            chfe.setCellStyle(estiloCelda);
            
            HSSFCell chfe1= r.createCell(j++);
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

            for (FacturasActorizadaSri item : listaFacturasActorizadaSri) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getFacNumero().toString()));

                HSSFCell cf1 = r.createCell(i++);
                cf1.setCellValue(new HSSFRichTextString(item.getCliCedula().toString()));

                HSSFCell cf11 = r.createCell(i++);
                cf11.setCellValue(new HSSFRichTextString(item.getCliNombre().toString()));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(sm.format(item.getFacFecha())));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getFacSubtotal().toString()));

                HSSFCell c11 = r.createCell(i++);
                c11.setCellValue(new HSSFRichTextString(item.getFacTotalBaseGravaba().toString()));

                HSSFCell c12 = r.createCell(i++);
                c12.setCellValue(new HSSFRichTextString(item.getFacTotalBaseCero().toString()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getFacIva().toString()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(item.getFacTotal().toString()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaFacturasActorizadaSri.size(); k++) {
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
