/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador.vistas;

import com.ec.entidad.CierreCaja;
import com.ec.entidad.Factura;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCierreCaja;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.DispararReporte;
import com.ec.untilitario.ModeloAcumuladoDiaUsuario;
import com.ec.vista.servicios.ServicioAcumuladoDiarioUsuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.internet.ParseException;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
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
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Filedownload;

/**
 *
 * @author gato
 */
public class ListaCierreCaja {

    ServicioAcumuladoDiarioUsuario servicioAcumuladoDiarioUsuario = new ServicioAcumuladoDiarioUsuario();
    UserCredential credential = new UserCredential();
    private Date fecha = new Date();

//    private List<ModeloAcumuladoDiaUsuario> listaCierreCaja = new ArrayList<ModeloAcumuladoDiaUsuario>();
    private List<CierreCaja> listaCierreCajaUsuario = new ArrayList<CierreCaja>();
    ServicioCierreCaja servicioCierreCaja = new ServicioCierreCaja();

    public ListaCierreCaja() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        consultaCierreCajaUsurio();
    }

    @Command
    @NotifyChange({"listaCierreCajaUsuario", "fecha"})
    public void buscarCierre() {

        consultaCierreCajaUsurio();

    }
  @Command   
    public void reporteCierre(@BindingParam("valor") CierreCaja valor)
            throws JRException, IOException, NamingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        DispararReporte.reporteCierrecaja(valor.getIdCierre());

    }

//    private void consultaCierreCaja() {
//        listaCierreCaja = servicioAcumuladoDiarioUsuario.findCierrePorUsuario(fecha, credential.getUsuarioSistema());
//    }
    private void consultaCierreCajaUsurio() {
        listaCierreCajaUsuario = servicioCierreCaja.findAllCierres(fecha, credential.getUsuarioSistema());
    }

    public List<CierreCaja> getListaCierreCajaUsuario() {
        return listaCierreCajaUsuario;
    }

    public void setListaCierreCajaUsuario(List<CierreCaja> listaCierreCajaUsuario) {
        this.listaCierreCajaUsuario = listaCierreCajaUsuario;
    }



    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    //exportar informacion por dia
    @Command
    public void exportCierreDiario() throws Exception {
        try {
            File dosfile = new File(exportarExcelDiario());
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO" + e.getMessage());
        }
    }

    private String exportarExcelDiario() throws FileNotFoundException, IOException, ParseException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(fecha);

        String pathSalida = directorioReportes + File.separator + "cierrecaja.xls";
        System.out.println("Direccion del reporte acumulado diario " + pathSalida);
        try {
            int j = 0;
            File archivoXLS = new File(pathSalida);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet("Diario");

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

            HSSFCell chfeee = r.createCell(j++);
            chfeee.setCellValue(new HSSFRichTextString("Fecha"));
            chfeee.setCellStyle(estiloCelda);

            HSSFCell chfee = r.createCell(j++);
            chfee.setCellValue(new HSSFRichTextString("Usuario"));
            chfee.setCellStyle(estiloCelda);

            HSSFCell chfe = r.createCell(j++);
            chfe.setCellValue(new HSSFRichTextString("Apertura caja"));
            chfe.setCellStyle(estiloCelda);

            HSSFCell chfe1 = r.createCell(j++);
            chfe1.setCellValue(new HSSFRichTextString("Total ventas"));
            chfe1.setCellStyle(estiloCelda);

            HSSFCell chfe11 = r.createCell(j++);
            chfe11.setCellValue(new HSSFRichTextString("Total recudado"));
            chfe11.setCellStyle(estiloCelda);

            HSSFCell chfe111 = r.createCell(j++);
            chfe111.setCellValue(new HSSFRichTextString("Diferencia"));
            chfe111.setCellStyle(estiloCelda);

            HSSFCell chfe1111 = r.createCell(j++);
            chfe1111.setCellValue(new HSSFRichTextString("Observacion"));
            chfe1111.setCellStyle(estiloCelda);
//            HSSFCell ch1 = r.createCell(j++);
//            ch1.setCellValue(new HSSFRichTextString("Fecha"));
//            ch1.setCellStyle(estiloCelda);
            int rownum = 1;
            int i = 0;
            BigDecimal totalConFactura = BigDecimal.ZERO;
            BigDecimal totalCuadre = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal Fecha = BigDecimal.ZERO;

            for (CierreCaja item : listaCierreCajaUsuario) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cff = r.createCell(i++);
                cff.setCellValue(new HSSFRichTextString(sm.format(item.getCieFecha())));

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getIdUsuario().getUsuNombre()));

                HSSFCell cf11 = r.createCell(i++);
                cf11.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getCieValorInicio(), 2).toString()));

                HSSFCell cf1 = r.createCell(i++);
                cf1.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getCieValor(), 2).toString()));
                total = total.add(ArchivoUtils.redondearDecimales(item.getCieCuadre(), 2));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getCieCuadre(), 2).toString()));
                totalCuadre = totalCuadre.add(ArchivoUtils.redondearDecimales(item.getCieCuadre(), 2));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(ArchivoUtils.redondearDecimales(item.getCieDiferencia(), 2).toString()));
                totalCuadre = totalCuadre.add(ArchivoUtils.redondearDecimales(item.getCieCuadre(), 2));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getCieDescripcion()));

                /*autemta la siguiente fila*/
                rownum += 1;

            }

            j = 0;
            r = s.createRow(rownum);
            HSSFCell chfeF1 = r.createCell(j++);
            chfeF1.setCellValue(new HSSFRichTextString(""));
            chfeF1.setCellStyle(estiloCelda);

            HSSFCell chfeF11 = r.createCell(j++);
            chfeF11.setCellValue(new HSSFRichTextString(""));
            chfeF11.setCellStyle(estiloCelda);

            HSSFCell chfeF2 = r.createCell(j++);
            chfeF2.setCellValue(new HSSFRichTextString(""));
            chfeF2.setCellStyle(estiloCelda);

            HSSFCell chfeF22 = r.createCell(j++);
            chfeF22.setCellValue(new HSSFRichTextString(total.toString()));
            chfeF22.setCellStyle(estiloCelda);

            HSSFCell chfeF3 = r.createCell(j++);
            chfeF3.setCellValue(new HSSFRichTextString(totalCuadre.toString()));
            chfeF3.setCellStyle(estiloCelda);

            HSSFCell chF4 = r.createCell(j++);
            chF4.setCellValue(new HSSFRichTextString(""));
            chF4.setCellStyle(estiloCelda);
            
             HSSFCell chF41 = r.createCell(j++);
            chF41.setCellValue(new HSSFRichTextString(""));
            chF41.setCellStyle(estiloCelda);

            for (int k = 0; k <= listaCierreCajaUsuario.size(); k++) {
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
