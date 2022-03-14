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
@XmlRootElement(name="autorizacion")
@XmlType(propOrder = {"estado", "numeroAutorizacion", "fechaAutorizacion", "ambiente", "comprobante"})
public class RetencionXML {

    private String estado;

    private String numeroAutorizacion;
    private String fechaAutorizacion;
    private String ambiente;

    private Comprobante comprobante;

    public String getEstado() {
        return estado;
    }

    @XmlElement
    public void setEstado(String value) {
        this.estado = value;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    @XmlElement
    public void setNumeroAutorizacion(String value) {
        this.numeroAutorizacion = value;
    }

    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(String value) {
        this.fechaAutorizacion = value;
    }

    @XmlElement
    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String value) {
        this.ambiente = value;
    }

    @XmlElement
    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante value) {
        this.comprobante = value;
    }
}
