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
public class TotalKardex {

    private BigDecimal ingresosKardex = BigDecimal.ZERO;
    private BigDecimal egresosKardex = BigDecimal.ZERO;
    private BigDecimal totalKardex = BigDecimal.ZERO;

    public TotalKardex() {
    }

    public TotalKardex(BigDecimal totalKardex) {
        this.totalKardex = totalKardex;
    }

    public TotalKardex(BigDecimal ingresosKardex ,BigDecimal egresosKardex,BigDecimal totalKardex) {
        this.ingresosKardex = ingresosKardex;
        this.egresosKardex = egresosKardex;
        this.totalKardex = totalKardex;
    }

    public BigDecimal getTotalKardex() {
        return totalKardex;
    }

    public void setTotalKardex(BigDecimal totalKardex) {
        this.totalKardex = totalKardex;
    }

    public BigDecimal getIngresosKardex() {
        return ingresosKardex;
    }

    public void setIngresosKardex(BigDecimal ingresosKardex) {
        this.ingresosKardex = ingresosKardex;
    }

    public BigDecimal getEgresosKardex() {
        return egresosKardex;
    }

    public void setEgresosKardex(BigDecimal egresosKardex) {
        this.egresosKardex = egresosKardex;
    }

}
