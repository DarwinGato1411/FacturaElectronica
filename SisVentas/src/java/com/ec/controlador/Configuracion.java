/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Tipoambiente;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.vista.servicios.ServicioSriCatastro;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.Image;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Darwin
 */
public class Configuracion extends SelectorComposer<Component> {

    @Wire
    Checkbox chkRM;
    @Wire
    Checkbox chkAR;
    @Wire
    Checkbox chkCE;
    @Wire
    Checkbox chkEX;

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private Tipoambiente tipoambiente = new Tipoambiente();
    private String llevaContabilidad = "NO";
    private String codigoAmbiente = "1";
    private String amCodifo = "1";
    private String carpetaRaizSRI = "DOCUMENTOSRI";
    private String carpetaFirma = "FIRMA";
    private List<String> listaDicos = new ArrayList<String>();

    ServicioSriCatastro servicioSriCatastro = new ServicioSriCatastro();
    private List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
    UserCredential credential = new UserCredential();
    private String buscar = "";

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

    }

    public Configuracion() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        buscarForNombre();
    }

    private void buscarForNombre() {

        listaTipoambientes = servicioTipoAmbiente.findAllNombre(credential.getUsuarioSistema(), buscar);
    }

    @Command
    @NotifyChange({"listaTipoambientes", "buscar"})
    public void buscarEmpresas() throws InterruptedException, IOException {
        buscarForNombre();

    }

    @Command
    @NotifyChange({"listaTipoambientes", "buscar"})
    public void nuevo() {
        if (credential.getUsuarioSistema().getUsuNumEmpresas() > listaTipoambientes.size()) {

            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/nuevo/tipoambiente.zul", null, null);
            window.doModal();
            buscarForNombre();

        } else {
            Clients.showNotification("No puede crear mas empresas contactese con el administrador",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 5000, true);
            return;
        }
    }

    @Command
    @NotifyChange({"listaTipoambientes", "buscar"})
    public void actualizar(@BindingParam("valor") Tipoambiente valor) {

        final HashMap<String, Tipoambiente> map = new HashMap<String, Tipoambiente>();
        map.put("valor", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/nuevo/tipoambiente.zul", null, map);
        window.doModal();
        buscarForNombre();
    }

    //subir pdf
    private String filePath;
    byte[] buffer = new byte[1024 * 1024];

    @Command
    @NotifyChange({"fileContent", "tipoambiente"})
    public void subirFirma() throws InterruptedException, IOException {

        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.util.media.AMedia) {
            String nombre = media.getName();
            if (media != null && nombre.contains("p12")) {

                if (media.getByteData().length > 10 * 1024 * 1024) {
                    Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 10Mb.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

                    return;
                }
                filePath = tipoambiente.getAmDirBaseArchivos() + File.separator + tipoambiente.getAmFolderFirma() + File.separator;

                File baseDir = new File(filePath);
                if (!baseDir.exists()) {
                    baseDir.mkdirs();
                }
                Files.copy(new File(filePath + media.getName()),
                            media.getStreamData());
                tipoambiente.setAmDirFirma(nombre);

            }

        }
    }

    public void LeerExcel() {

    }
    //Imagen ruta 
    private String filePathImg;

    @Command
    @NotifyChange({"fileContent", "tipoambiente"})
    public void subirPathImagen() throws InterruptedException, IOException {

        org.zkoss.util.media.Media media = Fileupload.get();
        if (media instanceof org.zkoss.image.Image) {
            String nombre = media.getName();
            if (media instanceof Image) {

                if (media.getByteData().length > 10 * 1024 * 1024) {
                    Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 10Mb.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

                    return;
                }
                filePathImg = tipoambiente.getAmDirBaseArchivos() + File.separator + tipoambiente.getAmFolderFirma() + File.separator;

                File baseDir = new File(filePathImg);
                if (!baseDir.exists()) {
                    baseDir.mkdirs();
                }
                Files.copy(new File(filePathImg + media.getName()),
                            media.getStreamData());
                tipoambiente.setAm_DirImgPuntoVenta(filePathImg + File.separator + nombre);
            }

        }
    }

    @Command
    @NotifyChange({"tipoambiente", "llevaContabilidad"})
    public void guardar() {
        tipoambiente.setLlevarContabilidad(llevaContabilidad);
        tipoambiente.setAmDirBaseArchivos(tipoambiente.getAmUnidadDisco() + File.separator + carpetaRaizSRI);
        tipoambiente.setAmDirReportes("REPORTES");
        tipoambiente.setAmGenerados("GENERADOS");
        tipoambiente.setAmDirXml("XML");
        tipoambiente.setAmFirmados("FIRMADOS");
        tipoambiente.setAmTrasmitidos("TRASMITIDOS");
        tipoambiente.setAmDevueltos("DEVUELTOS");
        tipoambiente.setAmAutorizados("AUTORIZADOS");
        tipoambiente.setAmNoAutorizados("NOAUTORIZADOS");
        tipoambiente.setAmTipoEmision("1");
        tipoambiente.setAmEnviocliente("ENVIARCLIENTE");
        servicioTipoAmbiente.modificar(tipoambiente);
        Clients.showNotification("Información registrada exitosamente",
                    Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 3000, true);

    }

    public Tipoambiente getTipoambiente() {
        return tipoambiente;
    }

    public void setTipoambiente(Tipoambiente tipoambiente) {
        this.tipoambiente = tipoambiente;
    }

    public String getLlevaContabilidad() {
        return llevaContabilidad;
    }

    public void setLlevaContabilidad(String llevaContabilidad) {
        this.llevaContabilidad = llevaContabilidad;
    }

    public String getCodigoAmbiente() {
        return codigoAmbiente;
    }

    public void setCodigoAmbiente(String codigoAmbiente) {
        this.codigoAmbiente = codigoAmbiente;
    }

    public String getCarpetaRaizSRI() {
        return carpetaRaizSRI;
    }

    public void setCarpetaRaizSRI(String carpetaRaizSRI) {
        this.carpetaRaizSRI = carpetaRaizSRI;
    }

    public List<String> getListaDicos() {
        return listaDicos;
    }

    public void setListaDicos(List<String> listaDicos) {
        this.listaDicos = listaDicos;
    }

    public String getAmCodifo() {
        return amCodifo;
    }

    public void setAmCodifo(String amCodifo) {
        this.amCodifo = amCodifo;
    }

    private void listaDiscos() {
        File[] files = File.listRoots();
        if (files != null) {
            for (File f : files) {
                listaDicos.add(f.getPath());
                System.out.println("getAbsolutePath " + f.getAbsolutePath());
                System.out.println("getPath " + f.getPath());
            }
        }

    }

    public List<Tipoambiente> getListaTipoambientes() {
        return listaTipoambientes;
    }

    public void setListaTipoambientes(List<Tipoambiente> listaTipoambientes) {
        this.listaTipoambientes = listaTipoambientes;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

}
