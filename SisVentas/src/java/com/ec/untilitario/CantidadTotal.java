/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import java.math.BigDecimal;

/**
 *
 * @author Darwin
 */
public class CantidadTotal {

    private Long cantidad;
    private BigDecimal sumaTotal;
    private BigDecimal baseGravada;
    private BigDecimal baseCero;
 

    public CantidadTotal() {
    }

    public CantidadTotal(Long cantidad, BigDecimal sumaTotal) {
        this.cantidad = cantidad;
        this.sumaTotal = sumaTotal;
    }
    public CantidadTotal(Long cantidad, BigDecimal sumaTotal, BigDecimal baseGravada, BigDecimal baseCero) {
        this.cantidad = cantidad;
        this.sumaTotal = sumaTotal;
        this.baseGravada = baseGravada;
        this.baseCero = baseCero;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

   

    public BigDecimal getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(BigDecimal sumaTotal) {
        this.sumaTotal = sumaTotal;
    }

    public BigDecimal getBaseGravada() {
        return baseGravada;
    }

    public void setBaseGravada(BigDecimal baseGravada) {
        this.baseGravada = baseGravada;
    }

    public BigDecimal getBaseCero() {
        return baseCero;
    }

    public void setBaseCero(BigDecimal baseCero) {
        this.baseCero = baseCero;
    }

  

}
