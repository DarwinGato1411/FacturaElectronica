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
public class SumaTotales {

    private BigDecimal sumaSubtotal;
    private BigDecimal sumaTotal;

    public SumaTotales() {
    }

    public SumaTotales(BigDecimal sumaSubtotal, BigDecimal sumaTotal) {
        this.sumaSubtotal = sumaSubtotal;
        this.sumaTotal = sumaTotal;
    }

    public BigDecimal getSumaSubtotal() {
        return sumaSubtotal;
    }

    public void setSumaSubtotal(BigDecimal sumaSubtotal) {
        this.sumaSubtotal = sumaSubtotal;
    }

    public BigDecimal getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(BigDecimal sumaTotal) {
        this.sumaTotal = sumaTotal;
    }

  

}
