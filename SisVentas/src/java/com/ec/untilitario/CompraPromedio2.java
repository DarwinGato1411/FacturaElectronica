/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author HC
 */
public class CompraPromedio2 {
    private String descripcion;
    private Date fecha;
    private Double subtotal;


       public CompraPromedio2() {
    }

    public CompraPromedio2(String descripcion, Date fecha, Double subtotal) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.subtotal = subtotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }


    
    
    
}
