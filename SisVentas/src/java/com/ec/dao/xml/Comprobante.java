/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.dao.xml;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Darwin
 */
public class Comprobante {

    private ComprobanteRetencion comprobanteRetencion;

    public ComprobanteRetencion getComprobanteRetencion() {
        return comprobanteRetencion;
    }

    @XmlElement
    public void setComprobanteRetencion(ComprobanteRetencion value) {
        this.comprobanteRetencion = value;
    }
}
