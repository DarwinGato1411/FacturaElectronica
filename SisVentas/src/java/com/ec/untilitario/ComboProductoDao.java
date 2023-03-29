/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import com.ec.entidad.ComboProducto;
import java.math.BigDecimal;

/**
 *
 * @author Darwin
 */
public class ComboProductoDao {

    private ComboProducto comboProducto;
    private String descripcion;
    private BigDecimal cantidad;

    public ComboProductoDao(ComboProducto comboProducto, String descripcion) {
        this.comboProducto = comboProducto;
        this.descripcion = descripcion;
    }

    public ComboProductoDao(ComboProducto comboProducto, String descripcion, BigDecimal cantidad) {
        this.comboProducto = comboProducto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public ComboProducto getComboProducto() {
        return comboProducto;
    }

    public void setComboProducto(ComboProducto comboProducto) {
        this.comboProducto = comboProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

}
