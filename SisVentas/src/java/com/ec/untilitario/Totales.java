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
public class Totales {

    private BigDecimal total;

    public Totales() {
    }

    public Totales(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
