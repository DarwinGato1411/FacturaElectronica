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
@XmlType(propOrder = {"ambiente", "tipoEmision", "razonSocial", "nombreComercial", "ruc", "claveAcceso", "codDoc", "estab", "ptoEmi", "secuencial", "dirMatriz"})
public class InfoTributaria {

    private String ambiente;
    private String tipoEmision;
    private String razonSocial;
    private String nombreComercial;
    private String ruc;
    private String claveAcceso;
    private String codDoc;
    private String estab;
    private String ptoEmi;
    private String secuencial;
    private String dirMatriz;

    public String getAmbiente() {
        return ambiente;
    }

    @XmlElement
    public void setAmbiente(String value) {
        this.ambiente = value;
    }

    public String getTipoEmision() {
        return tipoEmision;
    }

    @XmlElement
    public void setTipoEmision(String value) {
        this.tipoEmision = value;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    @XmlElement
    public void setRazonSocial(String value) {
        this.razonSocial = value;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    @XmlElement
    public void setNombreComercial(String value) {
        this.nombreComercial = value;
    }

    public String getRuc() {
        return ruc;
    }

    @XmlElement
    public void setRuc(String value) {
        this.ruc = value;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    @XmlElement
    public void setClaveAcceso(String value) {
        this.claveAcceso = value;
    }

    public String getCodDoc() {
        return codDoc;
    }

    @XmlElement
    public void setCodDoc(String value) {
        this.codDoc = value;
    }

    public String getEstab() {
        return estab;
    }

    public void setEstab(String value) {
        this.estab = value;
    }

    public String getPtoEmi() {
        return ptoEmi;
    }

    public void setPtoEmi(String value) {
        this.ptoEmi = value;
    }

    public String getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(String value) {
        this.secuencial = value;
    }

    public String getDirMatriz() {
        return dirMatriz;
    }

    public void setDirMatriz(String value) {
        this.dirMatriz = value;
    }
}
