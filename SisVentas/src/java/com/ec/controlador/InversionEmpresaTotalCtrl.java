/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.InversionEmpresa;
import com.ec.entidad.InversionEmpresaTotal;
import com.ec.entidad.Tipoambiente;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.untilitario.ArchivoUtils;
import com.ec.vista.servicios.ServicioInversionEmpresa;
import com.ec.vista.servicios.ServicioInversionEmpresaTotal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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
public class InversionEmpresaTotalCtrl {

    private static String PATH_BASE = "";
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private Tipoambiente amb = new Tipoambiente();

    ServicioInversionEmpresaTotal servicioInversionEmpresa = new ServicioInversionEmpresaTotal();

    private List<InversionEmpresaTotal> listaDatos = new ArrayList<InversionEmpresaTotal>();
//    private List<ReporteCompraVentaFacturado> listaDatosDet = new ArrayList<ReporteCompraVentaFacturado>();
    private String buscar = "";
    private String buscarNumFac = "";
    private Date inicio = new Date();
    private Date fin = new Date();

    public InversionEmpresaTotalCtrl() {
        findByBetweenFecha();
        amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();
    }

    private void findByBetweenFecha() {
        listaDatos = servicioInversionEmpresa.findInversion();
    }

    public List<InversionEmpresaTotal> getListaDatos() {
        return listaDatos;
    }

    public void setListaDatos(List<InversionEmpresaTotal> listaDatos) {
        this.listaDatos = listaDatos;
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

        String pathSalida = directorioReportes + File.separator + "Inversion_actual.xls";
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
            HSSFSheet s = wb.createSheet("Compras_Ventas-" + strDate);

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

            HSSFCell ch0 = r.createCell(j++);
            ch0.setCellValue(new HSSFRichTextString("Producto"));
            ch0.setCellStyle(estiloCelda);

//            HSSFCell ch1 = r.createCell(j++);
//            ch1.setCellValue(new HSSFRichTextString("Fecha"));
//            ch1.setCellStyle(estiloCelda);
            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("Cantidad"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(j++);
            ch3.setCellValue(new HSSFRichTextString("Precio compra"));
            ch3.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(j++);
            ch4.setCellValue(new HSSFRichTextString("Total compra"));
            ch4.setCellStyle(estiloCelda);
            
              HSSFCell ch5 = r.createCell(j++);
            ch5.setCellValue(new HSSFRichTextString("Precio venta"));
            ch5.setCellStyle(estiloCelda);

            HSSFCell ch6 = r.createCell(j++);
            ch6.setCellValue(new HSSFRichTextString("Total venta"));
            ch6.setCellStyle(estiloCelda);

            BigDecimal totalCompra = BigDecimal.ZERO;
            BigDecimal totalVenta = BigDecimal.ZERO;

            int rownum = 1;
            int i = 0;

            for (InversionEmpresaTotal item : listaDatos) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getProdNombre()));

//                HSSFCell c11 = r.createCell(i++);
//                c11.setCellValue(new HSSFRichTextString(sm.format(item.getFacFecha())));
                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getKarTotal(), 2).toString()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getPrecioCompra(), 2).toString()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getTotalCompra(), 2).toPlainString()));
                totalCompra = totalCompra.add(item.getTotalCompra());
                
                 HSSFCell c4 = r.createCell(i++);
                c4.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getPrecioVenta(), 2).toString()));

                HSSFCell c5 = r.createCell(i++);
                c5.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getTotalVenta(), 2).toPlainString()));
                totalVenta = totalVenta.add(item.getTotalCompra());

                /*autemta la siguiente fila*/
                rownum += 1;

            }
            
             j = 0;
            r = s.createRow(rownum);
            HSSFCell chfeF1 = r.createCell(j++);
            chfeF1.setCellValue(new HSSFRichTextString(""));
            chfeF1.setCellStyle(estiloCelda);

            HSSFCell chfeF2 = r.createCell(j++);
            chfeF2.setCellValue(new HSSFRichTextString(""));
            chfeF2.setCellStyle(estiloCelda);

            HSSFCell chfeF3 = r.createCell(j++);
            chfeF3.setCellValue(new HSSFRichTextString(""));
            chfeF3.setCellStyle(estiloCelda);

            HSSFCell chF5 = r.createCell(j++);
            chF5.setCellValue(new HSSFRichTextString((ArchivoUtils.redondearDecimales(totalCompra, 2)).toString()));
            chF5.setCellStyle(estiloCelda);
            
            HSSFCell chfeF6 = r.createCell(j++);
            chfeF6.setCellValue(new HSSFRichTextString(""));
            chfeF6.setCellStyle(estiloCelda);
            
            HSSFCell chfeF7 = r.createCell(j++);
            chfeF7.setCellValue(new HSSFRichTextString((ArchivoUtils.redondearDecimales(totalVenta, 2)).toString()));
            chfeF7.setCellStyle(estiloCelda);
            
            for (int k = 0; k <= listaDatos.size(); k++) {
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
