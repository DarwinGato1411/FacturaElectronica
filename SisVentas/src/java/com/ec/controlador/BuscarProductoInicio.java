/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Producto;
import com.ec.servicio.ServicioProducto;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

/**
 *
 * @author Darwin
 */
public class BuscarProductoInicio {

    ServicioProducto servicioProducto = new ServicioProducto();
    private List<Producto> listaProducto = new ArrayList<Producto>();
    private String buscarNombreProd = "";

    @Command
    @NotifyChange({"listaProducto", "buscarNombreProd"})
    public void buscarLikeNombreProd() {
       // buscarNombreProd = valor;
        findProductoLikeNombre();
    }

    private void findProductoLikeNombre() {
        listaProducto = servicioProducto.findLikeProdNombre(buscarNombreProd);
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public String getBuscarNombreProd() {
        return buscarNombreProd;
    }

    public void setBuscarNombreProd(String buscarNombreProd) {
        this.buscarNombreProd = buscarNombreProd;
    }
    
    
}
