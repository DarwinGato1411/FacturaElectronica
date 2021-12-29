/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import com.ec.entidad.Producto;
import java.math.BigDecimal;

/**
 *
 * @author Darwin
 */
public class DetalleGuiaDao {

    private BigDecimal detCantidad;
    private String detDescripcion;
    private Producto idProducto;

    public DetalleGuiaDao() {
    }

    public DetalleGuiaDao(BigDecimal detCantidad, String detDescripcion) {
        this.detCantidad = detCantidad;
        this.detDescripcion = detDescripcion;
    }

    public BigDecimal getDetCantidad() {
        return detCantidad;
    }

    public void setDetCantidad(BigDecimal detCantidad) {
        this.detCantidad = detCantidad;
    }

    public String getDetDescripcion() {
        return detDescripcion;
    }

    public void setDetDescripcion(String detDescripcion) {
        this.detDescripcion = detDescripcion;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

}
