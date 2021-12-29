/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "compras_sri")
public class ComprasSri implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comp_sri")
    private BigDecimal idCompSri;

    @Column(name = "csri_comprobante")
    private String csriComprobante;

    @Column(name = "csri_serie_comprobante")
    private String csriSerieComprobante;

    @Column(name = "csri_ruc_emisor")
    private String csriRucEmisor;

    @Column(name = "csri_razon_social")
    private String csriRazonSocial;
    @Column(name = "csri_fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date csriFechaEmision;
    @Column(name = "csri_fecha_autorizacion")
    @Temporal(TemporalType.DATE)
    private Date csriFechaAutorizacion;

    @Column(name = "csri_tipo_emision")
    private String csriTipoEmision;

    @Column(name = "csri_identificacion_receptor")
    private String csriIdentificacionReceptor;

    @Column(name = "csri_clave_acceso")
    private String csriClaveAcceso;

    @Column(name = "csri_autorizacion")
    private String csriAutorizacion;

    @Column(name = "csri_total")
    private String csriTotal;

    @Column(name = "csri_verificado")
    private String csriVerificado;

    public ComprasSri() {
    }

    public ComprasSri(String csriComprobante, String csriSerieComprobante, String csriRucEmisor, String csriRazonSocial, Date csriFechaEmision, Date csriFechaAutorizacion, String csriTipoEmision, String csriIdentificacionReceptor, String csriClaveAcceso, String csriAutorizacion, String csriTotal, String csriVerificado) {
        this.csriComprobante = csriComprobante;
        this.csriSerieComprobante = csriSerieComprobante;
        this.csriRucEmisor = csriRucEmisor;
        this.csriRazonSocial = csriRazonSocial;
        this.csriFechaEmision = csriFechaEmision;
        this.csriFechaAutorizacion = csriFechaAutorizacion;
        this.csriTipoEmision = csriTipoEmision;
        this.csriIdentificacionReceptor = csriIdentificacionReceptor;
        this.csriClaveAcceso = csriClaveAcceso;
        this.csriAutorizacion = csriAutorizacion;
        this.csriTotal = csriTotal;
        this.csriVerificado = csriVerificado;
    }

    public ComprasSri(BigDecimal idCompSri) {
        this.idCompSri = idCompSri;
    }

    public BigDecimal getIdCompSri() {
        return idCompSri;
    }

    public void setIdCompSri(BigDecimal idCompSri) {
        this.idCompSri = idCompSri;
    }

    public String getCsriComprobante() {
        return csriComprobante;
    }

    public void setCsriComprobante(String csriComprobante) {
        this.csriComprobante = csriComprobante;
    }

    public String getCsriSerieComprobante() {
        return csriSerieComprobante;
    }

    public void setCsriSerieComprobante(String csriSerieComprobante) {
        this.csriSerieComprobante = csriSerieComprobante;
    }

    public String getCsriRucEmisor() {
        return csriRucEmisor;
    }

    public void setCsriRucEmisor(String csriRucEmisor) {
        this.csriRucEmisor = csriRucEmisor;
    }

    public String getCsriRazonSocial() {
        return csriRazonSocial;
    }

    public void setCsriRazonSocial(String csriRazonSocial) {
        this.csriRazonSocial = csriRazonSocial;
    }

    public Date getCsriFechaEmision() {
        return csriFechaEmision;
    }

    public void setCsriFechaEmision(Date csriFechaEmision) {
        this.csriFechaEmision = csriFechaEmision;
    }

    public Date getCsriFechaAutorizacion() {
        return csriFechaAutorizacion;
    }

    public void setCsriFechaAutorizacion(Date csriFechaAutorizacion) {
        this.csriFechaAutorizacion = csriFechaAutorizacion;
    }

    public String getCsriTipoEmision() {
        return csriTipoEmision;
    }

    public void setCsriTipoEmision(String csriTipoEmision) {
        this.csriTipoEmision = csriTipoEmision;
    }

    public String getCsriIdentificacionReceptor() {
        return csriIdentificacionReceptor;
    }

    public void setCsriIdentificacionReceptor(String csriIdentificacionReceptor) {
        this.csriIdentificacionReceptor = csriIdentificacionReceptor;
    }

    public String getCsriClaveAcceso() {
        return csriClaveAcceso;
    }

    public void setCsriClaveAcceso(String csriClaveAcceso) {
        this.csriClaveAcceso = csriClaveAcceso;
    }

    public String getCsriAutorizacion() {
        return csriAutorizacion;
    }

    public void setCsriAutorizacion(String csriAutorizacion) {
        this.csriAutorizacion = csriAutorizacion;
    }

    public String getCsriTotal() {
        return csriTotal;
    }

    public void setCsriTotal(String csriTotal) {
        this.csriTotal = csriTotal;
    }

    public String getCsriVerificado() {
        return csriVerificado;
    }

    public void setCsriVerificado(String csriVerificado) {
        this.csriVerificado = csriVerificado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompSri != null ? idCompSri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComprasSri)) {
            return false;
        }
        ComprasSri other = (ComprasSri) object;
        if ((this.idCompSri == null && other.idCompSri != null) || (this.idCompSri != null && !this.idCompSri.equals(other.idCompSri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ComprasSri{" + "idCompSri=" + idCompSri + ", csriComprobante=" + csriComprobante + ", csriSerieComprobante=" + csriSerieComprobante + ", csriRucEmisor=" + csriRucEmisor + ", csriRazonSocial=" + csriRazonSocial + ", csriFechaEmision=" + csriFechaEmision + ", csriFechaAutorizacion=" + csriFechaAutorizacion + ", csriTipoEmision=" + csriTipoEmision + ", csriIdentificacionReceptor=" + csriIdentificacionReceptor + ", csriClaveAcceso=" + csriClaveAcceso + ", csriAutorizacion=" + csriAutorizacion + ", csriTotal=" + csriTotal + ", csriVerificado=" + csriVerificado + '}';
    }

}
