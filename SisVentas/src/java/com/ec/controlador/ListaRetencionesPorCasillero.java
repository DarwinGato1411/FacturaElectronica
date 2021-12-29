/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Retencionporcasillero;
import com.ec.servicio.ServicioRetencionPorCasillero;
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
public class ListaRetencionesPorCasillero {

    /*RUTAS PARA LOS ARCHIVPOS XML SRI*/
    ServicioRetencionPorCasillero servicioRetencionPorCasillero = new ServicioRetencionPorCasillero();
    private List<Retencionporcasillero> listaRetencionCasilleros = new ArrayList<Retencionporcasillero>();
    private Date inicio = new Date();
    private Date fin = new Date();
    //tabla para los parametros del SRI

    public ListaRetencionesPorCasillero() {

    }

    private void buscarPorFechas() {
        listaRetencionCasilleros = servicioRetencionPorCasillero.finPorFechas(inicio, fin);

    }

    @Command
    @NotifyChange({"listaRetencionCasilleros", "inicio", "fin"})
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

    public List<Retencionporcasillero> getListaRetencionCasilleros() {
        return listaRetencionCasilleros;
    }

    public void setListaRetencionCasilleros(List<Retencionporcasillero> listaRetencionCasilleros) {
        this.listaRetencionCasilleros = listaRetencionCasilleros;
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

        String pathSalida = directorioReportes + File.separator + "retencioncasillero.xls";
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
            HSSFSheet s = wb.createSheet("Retencion Casillero");

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
            chfe.setCellValue(new HSSFRichTextString("Casillero"));
            chfe.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("Descripcion"));
            ch1.setCellStyle(estiloCelda);

            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("# Documentos"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(j++);
            ch3.setCellValue(new HSSFRichTextString("Total"));
            ch3.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (Retencionporcasillero item : listaRetencionCasilleros) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getTireCodigo().toString()));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getTipoRetencion()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getNumeroComprobante().toString()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getValorRetenido().toString()));

                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaRetencionCasilleros.size(); k++) {
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
