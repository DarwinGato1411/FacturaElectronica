/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import com.ec.entidad.Producto;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author gato
 */
public class ProductoProveedorCosto implements Serializable{

    private Producto producto = new Producto();
    private BigDecimal costo = BigDecimal.ZERO;

    public ProductoProveedorCosto() {
    }

    public ProductoProveedorCosto(Producto producto, BigDecimal costo) {
        this.producto = producto;
        this.costo = costo;
    }
    public ProductoProveedorCosto(Producto producto) {
        this.producto = producto;
     
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
}
