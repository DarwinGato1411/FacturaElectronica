/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Tipoambiente;
import com.ec.servicio.ServicioTipoAmbiente;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.Image;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Darwin
 */
public class ConfiguracionSri {

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private Tipoambiente tipoambiente = new Tipoambiente();
    private String llevaContabilidad = "NO";
    private String codigoAmbiente = "1";
    private String amCodifo = "1";
    private String carpetaRaizSRI = "DOCUMENTOSRI";
    private String carpetaFirma = "FIRMA";
    private List<String> listaDicos = new ArrayList<String>();

    public ConfiguracionSri() {

        tipoambiente = servicioTipoAmbiente.FindALlTipoambiente();
        if (tipoambiente != null) {
            amCodifo = tipoambiente.getAmCodigo();
            if (tipoambiente.getLlevarContabilidad().equals("NO")) {
                llevaContabilidad = "NO";
            } else {
                llevaContabilidad = "SI";
            }

        }

        /*LISTA LAS UNIDADES DEL DISCO PRESENTES EN EL SISTEMA OPERATIVO*/
        listaDiscos();
    }

    @Command
    @NotifyChange({"tipoambiente"})
    public void ambienteCodigo() {
        /*COLOCA EL ANTERIOR EN FALSO*/
        tipoambiente.setAmEstado(Boolean.FALSE);
        servicioTipoAmbiente.modificar(tipoambiente);
        tipoambiente = servicioTipoAmbiente.findByAmCodigo(amCodifo);
        /*COLOCA EL NUEVO AMBIENTE EN ACTIVO*/
        tipoambiente.setAmEstado(Boolean.TRUE);
        servicioTipoAmbiente.modificar(tipoambiente);

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
                tipoambiente.setAm_DirImgPuntoVenta(filePathImg+ File.separator +nombre);
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

    
}
