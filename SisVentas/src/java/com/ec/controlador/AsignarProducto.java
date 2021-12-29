/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.ProductoProveedor;
import com.ec.entidad.Proveedores;
import com.ec.servicio.ServicioProductoProveedor;
import com.ec.servicio.ServicioProveedor;
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
public class AsignarProducto {

    ServicioProductoProveedor servicioProductoProveedor = new ServicioProductoProveedor();
    private List<ProductoProveedor>  listaProductoProveedor= new ArrayList<ProductoProveedor>();
    private String buscarNombre = "";

    public AsignarProducto() {
        findLikeNombre();
    }

    private void findLikeNombre() {
        listaProductoProveedor = servicioProductoProveedor.findLikeProdNombre(buscarNombre);
    }

    public List<ProductoProveedor> getListaProductoProveedor() {
        return listaProductoProveedor;
    }

    public void setListaProductoProveedor(List<ProductoProveedor> listaProductoProveedor) {
        this.listaProductoProveedor = listaProductoProveedor;
    }

 

   
  
    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    @Command
    @NotifyChange({"listaProveedores", "buscarNombre"})
    public void buscarLikeNombre(@BindingParam("valor") String valor) {
        buscarNombre = valor;
        findLikeNombre();
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
