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
public class ResultadoCompraVenta {

    private BigDecimal sumaSubtotalCompra;
    private BigDecimal sumaTotalCompra;
    private BigDecimal sumaSubtotalVenta;
    private BigDecimal sumaTotalVenta;

    private BigDecimal utilidadSubtotal;
    private BigDecimal utilidadTotal;

    public ResultadoCompraVenta() {
    }

    
    public ResultadoCompraVenta(BigDecimal sumaSubtotalCompra, BigDecimal sumaTotalCompra, BigDecimal sumaSubtotalVenta, BigDecimal sumaTotalVenta, BigDecimal utilidadSubtotal, BigDecimal utilidadTotal) {
        this.sumaSubtotalCompra = sumaSubtotalCompra;
        this.sumaTotalCompra = sumaTotalCompra;
        this.sumaSubtotalVenta = sumaSubtotalVenta;
        this.sumaTotalVenta = sumaTotalVenta;
        this.utilidadSubtotal = utilidadSubtotal;
        this.utilidadTotal = utilidadTotal;
    }

    public BigDecimal getSumaSubtotalCompra() {
        return sumaSubtotalCompra;
    }

    public void setSumaSubtotalCompra(BigDecimal sumaSubtotalCompra) {
        this.sumaSubtotalCompra = sumaSubtotalCompra;
    }

    public BigDecimal getSumaTotalCompra() {
        return sumaTotalCompra;
    }

    public void setSumaTotalCompra(BigDecimal sumaTotalCompra) {
        this.sumaTotalCompra = sumaTotalCompra;
    }

    public BigDecimal getSumaSubtotalVenta() {
        return sumaSubtotalVenta;
    }

    public void setSumaSubtotalVenta(BigDecimal sumaSubtotalVenta) {
        this.sumaSubtotalVenta = sumaSubtotalVenta;
    }

    public BigDecimal getSumaTotalVenta() {
        return sumaTotalVenta;
    }

    public void setSumaTotalVenta(BigDecimal sumaTotalVenta) {
        this.sumaTotalVenta = sumaTotalVenta;
    }

    public BigDecimal getUtilidadSubtotal() {
        return utilidadSubtotal;
    }

    public void setUtilidadSubtotal(BigDecimal utilidadSubtotal) {
        this.utilidadSubtotal = utilidadSubtotal;
    }

    public BigDecimal getUtilidadTotal() {
        return utilidadTotal;
    }

    public void setUtilidadTotal(BigDecimal utilidadTotal) {
        this.utilidadTotal = utilidadTotal;
    }

}
