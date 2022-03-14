/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.dao.xml.factura;

/**
 *
 * @author Darwin
 */
public class Pago {

    private String formaPago;
    private String total;

    // Getter Methods 
    public String getFormaPago() {
        return formaPago;
    }

    public String getTotal() {
        return total;
    }

    // Setter Methods 
    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
