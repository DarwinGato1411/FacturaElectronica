/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.Tipoambiente;
import com.ec.servicio.ServicioAcumuladoVentas;
import com.ec.servicio.ServicioCompra;
import com.ec.servicio.ServicioDetalleCompra;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioTipoAmbiente;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class ListaCompras {

    private static String PATH_BASE = "";
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private Tipoambiente amb = new Tipoambiente();
    ServicioDetalleCompra servicioDetalleCompra = new ServicioDetalleCompra();
    ServicioCompra servicioCompra = new ServicioCompra();
    ServicioFactura servicioFactura = new ServicioFactura();

    ServicioAcumuladoVentas servicioAcumuladoVentas = new ServicioAcumuladoVentas();

    private List<CabeceraCompra> listaCabeceraCompras = new ArrayList<CabeceraCompra>();
    private String buscar = "";
    private String buscarNumFac = "";
    private Date inicio = new Date();
    private Date fin = new Date();

    public ListaCompras() {
        findByBetweenFecha();
        amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();
    }

    private void buscarLikeNombre() {
        listaCabeceraCompras = servicioCompra.findCabProveedor(buscar);
    }

    private void findByBetweenFecha() {
        listaCabeceraCompras = servicioCompra.findByBetweenFecha(inicio, fin);
    }

    private void findByNumFac() {
        listaCabeceraCompras = servicioCompra.findByNumeroFactura(buscarNumFac);
    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "buscar"})
    public void buscarForProveedor() {
        buscarLikeNombre();
    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "buscarNumFac"})
    public void buscarForNumFactura() {
        findByNumFac();
    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "inicio", "fin"})
    public void buscarForFechas() {
        findByBetweenFecha();
    }

    @Command
    public void crearRetencionCompra(@BindingParam("valor") CabeceraCompra valor) {
        try {
            final HashMap<String, CabeceraCompra> map = new HashMap<String, CabeceraCompra>();

            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/compra/retencion.zul", null, map);
            window.doModal();
//            window.detach();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    public void modificarFactura(@BindingParam("valor") CabeceraCompra valor) {
        try {
            if (Messagebox.show("¿Desea modificar el registro, recuerde que debe crear las reteniones nuevamente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
                final HashMap<String, CabeceraCompra> map = new HashMap<String, CabeceraCompra>();

                map.put("valor", valor);
                org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/compra/modificarcompra.zul", null, map);
                window.doModal();
            }
//            window.detach();
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

    public List<CabeceraCompra> getListaCabeceraCompras() {
        return listaCabeceraCompras;
    }

    public void setListaCabeceraCompras(List<CabeceraCompra> listaCabeceraCompras) {
        this.listaCabeceraCompras = listaCabeceraCompras;
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

        String pathSalida = directorioReportes + File.separator + "compras.xls";
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
            HSSFSheet s = wb.createSheet("Compras");

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
            ch0.setCellValue(new HSSFRichTextString("RUC"));
            ch0.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("Proveedor"));
            ch1.setCellStyle(estiloCelda);

            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("Factura"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(j++);
            ch3.setCellValue(new HSSFRichTextString("Fecha emision"));
            ch3.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(j++);
            ch4.setCellValue(new HSSFRichTextString("Subtotal"));
            ch4.setCellStyle(estiloCelda);

            HSSFCell ch5 = r.createCell(j++);
            ch5.setCellValue(new HSSFRichTextString("Iva"));
            ch5.setCellStyle(estiloCelda);

            HSSFCell ch6 = r.createCell(j++);
            ch6.setCellValue(new HSSFRichTextString("Total"));
            ch6.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (CabeceraCompra item : listaCabeceraCompras) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getIdProveedor().getProvCedula()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getIdProveedor().getProvNombre()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getCabNumFactura()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(sm.format(item.getCabFechaEmision())));

                HSSFCell c4 = r.createCell(i++);
                c4.setCellValue(new HSSFRichTextString(item.getCabSubTotal().toString()));

                HSSFCell c5 = r.createCell(i++);
                c5.setCellValue(new HSSFRichTextString(item.getCabIva().toString()));

                HSSFCell c6 = r.createCell(i++);
                c6.setCellValue(new HSSFRichTextString(item.getCabTotal().toString()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaCabeceraCompras.size(); k++) {
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
