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
public class ModeloAcumuladoDiaUsuario {

    private String usuario;
    private BigDecimal valorFacturas;
    private BigDecimal valorNotaVenta;
    private BigDecimal valorTotal;

    public ModeloAcumuladoDiaUsuario(String usuario, BigDecimal valorFacturas, BigDecimal valorNotaVenta, BigDecimal valorTotal) {
        this.usuario = usuario;
        this.valorFacturas = valorFacturas;
        this.valorNotaVenta = valorNotaVenta;
        this.valorTotal = valorTotal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getValorFacturas() {
        return valorFacturas;
    }

    public void setValorFacturas(BigDecimal valorFacturas) {
        this.valorFacturas = valorFacturas;
    }

    public BigDecimal getValorNotaVenta() {
        return valorNotaVenta;
    }

    public void setValorNotaVenta(BigDecimal valorNotaVenta) {
        this.valorNotaVenta = valorNotaVenta;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

}
