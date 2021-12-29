/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Proveedores;
import com.ec.entidad.TipoIdentificacionCompra;
import com.ec.servicio.ServicioProveedor;
import com.ec.servicio.ServicioTipoIdentificacionCompra;
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
public class AdmProveedor {

    ServicioProveedor servicioProducto = new ServicioProveedor();

    private List<Proveedores> listaProveedores = new ArrayList<Proveedores>();

    private String buscarNombre = "";
    private String buscarCedula = "";

    public AdmProveedor() {
        findLikeNombre();

    }

    private void findLikeCedula() {
        listaProveedores = servicioProducto.findProveedorCedula(buscarCedula);
    }

    private void findLikeNombre() {
        listaProveedores = servicioProducto.findLikeProvNombre(buscarNombre);
    }

    public List<Proveedores> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedores> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    public String getBuscarCedula() {
        return buscarCedula;
    }

    public void setBuscarCedula(String buscarCedula) {
        this.buscarCedula = buscarCedula;
    }

    @Command
    @NotifyChange({"listaProveedores", "buscarNombre"})
    public void buscarLikeNombre() {

        findLikeNombre();
    }

    @Command
    @NotifyChange({"listaProveedores", "buscarCedula"})
    public void buscarLikeCedula() {

        findLikeCedula();
    }

    @Command
    @NotifyChange({"listaProveedores", "buscarNombre"})
    public void nuevoProvedor() {
        buscarNombre = "";
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/proveedor.zul", null, null);
        window.doModal();
        findLikeNombre();
    }

    @Command
    @NotifyChange({"listaProveedores", "buscarNombre"})
    public void actualizarProveedor(@BindingParam("valor") Proveedores valor) {
        buscarNombre = "";
        final HashMap<String, Proveedores> map = new HashMap<String, Proveedores>();
        map.put("valor", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/proveedor.zul", null, map);
        window.doModal();
        findLikeNombre();
    }

}
