/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Cliente;
import com.ec.entidad.MailMasivo;
import com.ec.entidad.Usuario;
import com.ec.servicio.ServicioCliente;
import com.ec.servicio.ServicioMailMasivo;
import com.ec.servicio.ServicioUsuario;
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
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author gato
 */
public class AdministrarVenta {

    ServicioUsuario servicioUsuario = new ServicioUsuario();
    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();

    ServicioCliente servicioCliente = new ServicioCliente();
    ServicioMailMasivo servicioMailMasivo = new ServicioMailMasivo();
    private List<Cliente> listaClientesAll = new ArrayList<Cliente>();
    private String buscarNombre = "";
    private String buscarRazonSocial = "";
    private String buscarCedula = "";
    //mailing
    private List<MailMasivo> listaContactoMail = new ArrayList<MailMasivo>();
    private String buscarEmail = "";

    public AdministrarVenta() {
        FindClienteLikeNombre();
        consultarMail();
        cosultarUsuarios("");
    }

    private void FindClienteLikeNombre() {
        listaClientesAll = servicioCliente.FindClienteLikeNombre(buscarNombre);
    }

    private void FindClienteLikeRazon() {
        listaClientesAll = servicioCliente.FindClienteLikeRazonSocial(buscarRazonSocial);
    }

    private void FindClienteLikeCedula() {
        listaClientesAll = servicioCliente.FindClienteLikeCedula(buscarCedula);
    }

    //get y set de las variables de la vista
    public List<Cliente> getListaClientesAll() {
        return listaClientesAll;
    }

    public void setListaClientesAll(List<Cliente> listaClientesAll) {
        this.listaClientesAll = listaClientesAll;
    }

    public String getBuscarCedula() {
        return buscarCedula;
    }

    public void setBuscarCedula(String buscarCedula) {
        this.buscarCedula = buscarCedula;
    }

    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    public String getBuscarRazonSocial() {
        return buscarRazonSocial;
    }

    public void setBuscarRazonSocial(String buscarRazonSocial) {
        this.buscarRazonSocial = buscarRazonSocial;
    }

    public List<MailMasivo> getListaContactoMail() {
        return listaContactoMail;
    }

    public void setListaContactoMail(List<MailMasivo> listaContactoMail) {
        this.listaContactoMail = listaContactoMail;
    }

    public String getBuscarEmail() {
        return buscarEmail;
    }

    public void setBuscarEmail(String buscarEmail) {
        this.buscarEmail = buscarEmail;
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarRazonSocial"})
    public void buscarClienteRazon() {

        FindClienteLikeRazon();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarNombre"})
    public void buscarClienteNombre() {

        FindClienteLikeNombre();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarCedula"})
    public void buscarClienteCedula() {

        FindClienteLikeCedula();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarCedula"})
    public void nuevoCliente() {
        buscarCedula = "";
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/cliente.zul", null, null);
        window.doModal();
        FindClienteLikeCedula();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarCedula"})
    public void actualizarCliente(@BindingParam("valor") Cliente valor) {
        buscarCedula = "";
        final HashMap<String, Cliente> map = new HashMap<String, Cliente>();
        map.put("valor", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/cliente.zul", null, map);
        window.doModal();
        FindClienteLikeCedula();
    }

    @Command
    @NotifyChange({"listaContactoMail", "buscarEmail"})
    public void buscarMailing(@BindingParam("valor") String valor) {
        buscarEmail = valor;
        consultarMail();
    }

    private void consultarMail() {
        listaContactoMail = servicioMailMasivo.FindAllLike(buscarEmail);
    }

    @Command
    @NotifyChange("listaContactoMail")
    public void agregarMailing() {
//
//        final HashMap<String, Cliente> map = new HashMap<String, Cliente>();
//        map.put("cliente", null);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/nuevoMailing.zul", null, null);
        window.doModal();
        consultarMail();
    }

    @Command
    @NotifyChange("listaContactoMail")
    public void modificarMailing(@BindingParam("valor") MailMasivo valor) {
//
        final HashMap<String, MailMasivo> map = new HashMap<String, MailMasivo>();
        map.put("valor", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/nuevoMailing.zul", null, map);
        window.doModal();
        consultarMail();
    }

    @Command
    @NotifyChange("listaContactoMail")
    public void eliminarMailCont(@BindingParam("valor") MailMasivo valor) throws java.text.ParseException {
        if (Messagebox.show("¿Seguro que desea eliminar el contacto?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            servicioMailMasivo.eliminar(valor);
            Messagebox.show("Eliminado con exito");
            consultarMail();
        }
    }

    /*ADMINISTRAR USUARIO*/
    private void cosultarUsuarios(String buscar) {
        listaUsuarios = servicioUsuario.FindALlUsuarioPorLikeNombre(buscar);
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    //usuarios
    @Command
    @NotifyChange("listaUsuarios")
    public void agregarUsario() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/usuario.zul", null, null);
        window.doModal();
        cosultarUsuarios("");
    }

    @Command
    @NotifyChange("listaUsuarios")
    public void modificarUsario(@BindingParam("valor") Usuario usuario) {
        final HashMap<String, Usuario> map = new HashMap<String, Usuario>();
        map.put("usuario", usuario);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/usuario.zul", null, map);
        window.doModal();
        cosultarUsuarios("");
    }

    /*EXPORTAR EXCEL clientes*/
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

    private String exportarExcel() throws FileNotFoundException, IOException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "clientes.xls";
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

            HSSFCell chfe = r.createCell(0);
            chfe.setCellValue(new HSSFRichTextString("CI/RUC"));
            chfe.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("Nombre"));
            ch1.setCellStyle(estiloCelda);

            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("Direccion"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch21 = r.createCell(j++);
            ch21.setCellValue(new HSSFRichTextString("Telefono"));
            ch21.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(j++);
            ch3.setCellValue(new HSSFRichTextString("Movil"));
            ch3.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(j++);
            ch4.setCellValue(new HSSFRichTextString("Correo"));
            ch4.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (Cliente item : listaClientesAll) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getCliCedula().toString()));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getCliNombre()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getCliDireccion() != null ? item.getCliDireccion().toString() : ""));

                HSSFCell c11 = r.createCell(i++);
                c11.setCellValue(new HSSFRichTextString(item.getCliTelefono() != null ? item.getCliTelefono().toString() : ""));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getCliMovil() != null ? item.getCliMovil().toString() : ""));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(item.getCliCorreo() != null ? item.getCliCorreo().toString() : ""));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaClientesAll.size(); k++) {
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
