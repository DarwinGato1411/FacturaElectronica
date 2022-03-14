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
public class Factura {

    InfoTributaria InfoTributaria;
    InfoFactura InfoFactura;
    Detalles Detalles;
//    InfoAdicional infoAdicional;

    public InfoTributaria getInfoTributaria() {
        return InfoTributaria;
    }

    public void setInfoTributaria(InfoTributaria InfoTributaria) {
        this.InfoTributaria = InfoTributaria;
    }

    public InfoFactura getInfoFactura() {
        return InfoFactura;
    }

    public void setInfoFactura(InfoFactura InfoFactura) {
        this.InfoFactura = InfoFactura;
    }

    public Detalles getDetalles() {
        return Detalles;
    }

    public void setDetalles(Detalles Detalles) {
        this.Detalles = Detalles;
    }

//    public InfoAdicional getInfoAdicional() {
//        return infoAdicional;
//    }
//
//    public void setInfoAdicional(InfoAdicional infoAdicional) {
//        this.infoAdicional = infoAdicional;
//    }

  
   
}
