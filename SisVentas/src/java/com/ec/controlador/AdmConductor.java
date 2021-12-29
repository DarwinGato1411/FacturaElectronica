/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Proveedores;
import com.ec.entidad.Transportista;
import com.ec.servicio.ServicioProveedor;
import com.ec.servicio.ServicioTransportista;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author gato
 */
public class AdmConductor {

    ServicioTransportista servicioTransportista = new ServicioTransportista();
 
    private List<Transportista> listaProveedores = new ArrayList<Transportista>();
   
    private String buscarNombre = "";

    public AdmConductor() {
        findLikeNombre();
 
    }

  

    private void findLikeNombre() {
        listaProveedores = servicioTransportista.findTransportista(buscarNombre);
    }

    public List<Transportista> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Transportista> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

  

    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    @Command
    @NotifyChange({"listaProveedores", "buscarNombre"})
    public void buscarLikeNombre() {
        
        findLikeNombre();
    }

    @Command
    @NotifyChange({"listaProveedores", "buscarNombre"})
    public void nuevo() {
        buscarNombre = "";
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/conductor.zul", null, null);
        window.doModal();
        findLikeNombre();
    }

    @Command
    @NotifyChange({"listaProveedores", "buscarNombre"})
    public void actualizar(@BindingParam("valor") Transportista valor) {
        buscarNombre = "";
        final HashMap<String, Transportista> map = new HashMap<String, Transportista>();
        map.put("valor", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/conductor.zul", null, map);
        window.doModal();
        findLikeNombre();
    }


}
