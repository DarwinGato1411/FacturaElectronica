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
//@XmlType(propOrder = { "fechaEmision","dirEstablecimiento","obligadoContabilidad","tipoIdentificacionSujetoRetenido","razonSocialSujetoRetenido","identificacionSujetoRetenido","periodoFiscal"})
public class InfoCompRetencion {

    private String fechaEmision;
    private String dirEstablecimiento;
    private String obligadoContabilidad;
    private String tipoIdentificacionSujetoRetenido;
    private String razonSocialSujetoRetenido;
    private String identificacionSujetoRetenido;
    private String periodoFiscal;

    public String getFechaEmision() {
        return fechaEmision;
    }

    @XmlElement
    public void setFechaEmision(String value) {
        this.fechaEmision = value;
    }

    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    @XmlElement
    public void setDirEstablecimiento(String value) {
        this.dirEstablecimiento = value;
    }

    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    @XmlElement
    public void setObligadoContabilidad(String value) {
        this.obligadoContabilidad = value;
    }

    public String getTipoIdentificacionSujetoRetenido() {
        return tipoIdentificacionSujetoRetenido;
    }

    @XmlElement
    public void setTipoIdentificacionSujetoRetenido(String value) {
        this.tipoIdentificacionSujetoRetenido = value;
    }

    public String getRazonSocialSujetoRetenido() {
        return razonSocialSujetoRetenido;
    }

    @XmlElement
    public void setRazonSocialSujetoRetenido(String value) {
        this.razonSocialSujetoRetenido = value;
    }

    public String getIdentificacionSujetoRetenido() {
        return identificacionSujetoRetenido;
    }

    @XmlElement
    public void setIdentificacionSujetoRetenido(String value) {
        this.identificacionSujetoRetenido = value;
    }

    public String getPeriodoFiscal() {
        return periodoFiscal;
    }

    @XmlElement
    public void setPeriodoFiscal(String value) {
        this.periodoFiscal = value;
    }
}
