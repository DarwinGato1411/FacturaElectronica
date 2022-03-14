/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.dao.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Darwin
 */
public class ComprobanteRetencion {

    private InfoTributaria infoTributaria;
    private InfoCompRetencion infoCompRetencion;
    private Impuestos impuestos;
//    private String id;
//    private String version;

    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    @XmlElement
    public void setInfoTributaria(InfoTributaria value) {
        this.infoTributaria = value;
    }

    public InfoCompRetencion getInfoCompRetencion() {
        return infoCompRetencion;
    }

    @XmlElement
    public void setInfoCompRetencion(InfoCompRetencion value) {
        this.infoCompRetencion = value;
    }

    public Impuestos getImpuestos() {
        return impuestos;
    }

    @XmlElement
    public void setImpuestos(Impuestos value) {
        this.impuestos = value;
    }

//    public String getID() { return id; }
//    public void setID(String value) { this.id = value; }
//
//    public String getVersion() { return version; }
//    public void setVersion(String value) { this.version = value; }
}
