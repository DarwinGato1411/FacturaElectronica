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
//@XmlType(propOrder = { "codigo", "codigoRetencion", "baseImponible","porcentajeRetener","valorRetenido","codDocSustento","numDocSustento","fechaEmisionDocSustento"})
public class Impuesto {

    private String codigo;
    private String codigoRetencion;
    private String baseImponible;
    private String porcentajeRetener;
    private String valorRetenido;
    private String codDocSustento;
    private String numDocSustento;
    private String fechaEmisionDocSustento;

    public String getCodigo() {
        return codigo;
    }

    @XmlElement
    public void setCodigo(String value) {
        this.codigo = value;
    }

    public String getCodigoRetencion() {
        return codigoRetencion;
    }

    @XmlElement
    public void setCodigoRetencion(String value) {
        this.codigoRetencion = value;
    }

    public String getBaseImponible() {
        return baseImponible;
    }

    @XmlElement
    public void setBaseImponible(String value) {
        this.baseImponible = value;
    }

    public String getPorcentajeRetener() {
        return porcentajeRetener;
    }

    @XmlElement
    public void setPorcentajeRetener(String value) {
        this.porcentajeRetener = value;
    }

    public String getValorRetenido() {
        return valorRetenido;
    }

    @XmlElement
    public void setValorRetenido(String value) {
        this.valorRetenido = value;
    }

    public String getCodDocSustento() {
        return codDocSustento;
    }

    @XmlElement
    public void setCodDocSustento(String value) {
        this.codDocSustento = value;
    }

    public String getNumDocSustento() {
        return numDocSustento;
    }

    @XmlElement
    public void setNumDocSustento(String value) {
        this.numDocSustento = value;
    }

    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    @XmlElement
    public void setFechaEmisionDocSustento(String value) {
        this.fechaEmisionDocSustento = value;
    }
}
