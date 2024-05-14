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
@Table(name = "reporte_retencion")
public class ReporteRetencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rco_codigo")
    private Integer rcoCodigo;
    @Column(name = "rco_autorizacion")
    private String rcoAutorizacion;
    @Basic(optional = false)
    @Size(min = 1, max = 100)
    @Column(name = "rco_detalle")
    private String rcoDetalle;
    @Basic(optional = false)
    @Column(name = "rco_fecha")
    @Temporal(TemporalType.DATE)
    private Date rcoFecha;
    @Basic(optional = false)
    @Column(name = "rco_iva")
    private boolean rcoIva;
    @Basic(optional = false)
    @Column(name = "rco_porcentaje_iva")
    private int rcoPorcentajeIva;
    @Basic(optional = false)
    @Size(min = 1, max = 3)
    @Column(name = "rco_punto_emision")
    private String rcoPuntoEmision;
    @Basic(optional = false)
    @Column(name = "rco_secuencial")
    private int rcoSecuencial;
    @Basic(optional = false)

    @Size(min = 1, max = 3)
    @Column(name = "rco_serie")
    private String rcoSerie;

    @Basic(optional = false)
    @Column(name = "rco_base_grava_iva")
    private BigDecimal rcoBaseGravaIva;
    @Basic(optional = false)
    @Column(name = "rco_valor_retencion_iva")
    private BigDecimal rcoValorRetencionIva;
    @Column(name = "cab_fecha_emision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cabFechaEmision;
    @Column(name = "rco_secuencial_text")
    private String rcoSecuencialText;
    @Column(name = "drc_mensajesri")
    private String drcMensajesri;
    @Column(name = "rco_msm_Info_sri")
    private String rcoMsmInfoSri;
    @Column(name = "drc_estadosri")
    private String drcEstadosri;
    @Temporal(TemporalType.DATE)
    @Column(name = "rco_fecha_autorizacion")
    private Date rcoFechaAutorizacion;
    @Size(max = 50)
    @Column(name = "cab_num_factura")
    private String cabNumFactura;

    @Column(name = "valor_renta")
    private BigDecimal valorRenta;

    @Column(name = "valor_iva")
    private BigDecimal valorIva;

    public ReporteRetencion() {
    }

    public ReporteRetencion(Integer rcoCodigo) {
        this.rcoCodigo = rcoCodigo;
    }

    public ReporteRetencion(Integer rcoCodigo, String rcoAutorizacion, String rcoDetalle, Date rcoFecha, boolean rcoIva, int rcoPorcentajeIva, String rcoPuntoEmision, int rcoSecuencial, String rcoSerie, BigDecimal rcoValorRetencionIva) {
        this.rcoCodigo = rcoCodigo;
        this.rcoAutorizacion = rcoAutorizacion;
        this.rcoDetalle = rcoDetalle;
        this.rcoFecha = rcoFecha;
        this.rcoIva = rcoIva;
        this.rcoPorcentajeIva = rcoPorcentajeIva;
        this.rcoPuntoEmision = rcoPuntoEmision;
        this.rcoSecuencial = rcoSecuencial;
        this.rcoSerie = rcoSerie;
        this.rcoValorRetencionIva = rcoValorRetencionIva;
    }

    public Integer getRcoCodigo() {
        return rcoCodigo;
    }

    public void setRcoCodigo(Integer rcoCodigo) {
        this.rcoCodigo = rcoCodigo;
    }

    public String getRcoAutorizacion() {
        return rcoAutorizacion;
    }

    public void setRcoAutorizacion(String rcoAutorizacion) {
        this.rcoAutorizacion = rcoAutorizacion;
    }

    public String getRcoDetalle() {
        return rcoDetalle;
    }

    public void setRcoDetalle(String rcoDetalle) {
        this.rcoDetalle = rcoDetalle;
    }

    public Date getRcoFecha() {
        return rcoFecha;
    }

    public void setRcoFecha(Date rcoFecha) {
        this.rcoFecha = rcoFecha;
    }

    public boolean getRcoIva() {
        return rcoIva;
    }

    public void setRcoIva(boolean rcoIva) {
        this.rcoIva = rcoIva;
    }

    public int getRcoPorcentajeIva() {
        return rcoPorcentajeIva;
    }

    public void setRcoPorcentajeIva(int rcoPorcentajeIva) {
        this.rcoPorcentajeIva = rcoPorcentajeIva;
    }

    public String getRcoPuntoEmision() {
        return rcoPuntoEmision;
    }

    public void setRcoPuntoEmision(String rcoPuntoEmision) {
        this.rcoPuntoEmision = rcoPuntoEmision;
    }

    public int getRcoSecuencial() {
        return rcoSecuencial;
    }

    public void setRcoSecuencial(int rcoSecuencial) {
        this.rcoSecuencial = rcoSecuencial;
    }

    public String getRcoSerie() {
        return rcoSerie;
    }

    public void setRcoSerie(String rcoSerie) {
        this.rcoSerie = rcoSerie;
    }

    public BigDecimal getRcoValorRetencionIva() {
        return rcoValorRetencionIva;
    }

    public void setRcoValorRetencionIva(BigDecimal rcoValorRetencionIva) {
        this.rcoValorRetencionIva = rcoValorRetencionIva;
    }

    public Date getCabFechaEmision() {
        return cabFechaEmision;
    }

    public void setCabFechaEmision(Date cabFechaEmision) {
        this.cabFechaEmision = cabFechaEmision;
    }

    public String getRcoSecuencialText() {
        return rcoSecuencialText;
    }

    public void setRcoSecuencialText(String rcoSecuencialText) {
        this.rcoSecuencialText = rcoSecuencialText;
    }

    public String getDrcMensajesri() {
        return drcMensajesri;
    }

    public void setDrcMensajesri(String drcMensajesri) {
        this.drcMensajesri = drcMensajesri;
    }

    public String getDrcEstadosri() {
        return drcEstadosri;
    }

    public void setDrcEstadosri(String drcEstadosri) {
        this.drcEstadosri = drcEstadosri;
    }

    public Date getRcoFechaAutorizacion() {
        return rcoFechaAutorizacion;
    }

    public void setRcoFechaAutorizacion(Date rcoFechaAutorizacion) {
        this.rcoFechaAutorizacion = rcoFechaAutorizacion;
    }

    public String getRcoMsmInfoSri() {
        return rcoMsmInfoSri;
    }

    public void setRcoMsmInfoSri(String rcoMsmInfoSri) {
        this.rcoMsmInfoSri = rcoMsmInfoSri;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rcoCodigo != null ? rcoCodigo.hashCode() : 0);
        return hash;
    }

    public BigDecimal getRcoBaseGravaIva() {
        return rcoBaseGravaIva;
    }

    public void setRcoBaseGravaIva(BigDecimal rcoBaseGravaIva) {
        this.rcoBaseGravaIva = rcoBaseGravaIva;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReporteRetencion)) {
            return false;
        }
        ReporteRetencion other = (ReporteRetencion) object;
        if ((this.rcoCodigo == null && other.rcoCodigo != null) || (this.rcoCodigo != null && !this.rcoCodigo.equals(other.rcoCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.RetencionCompra[ rcoCodigo=" + rcoCodigo + " ]";
    }

    public String getCabNumFactura() {
        return cabNumFactura;
    }

    public void setCabNumFactura(String cabNumFactura) {
        this.cabNumFactura = cabNumFactura;
    }

    public BigDecimal getValorRenta() {
        return valorRenta;
    }

    public void setValorRenta(BigDecimal valorRenta) {
        this.valorRenta = valorRenta;
    }

    public BigDecimal getValorIva() {
        return valorIva;
    }

    public void setValorIva(BigDecimal valorIva) {
        this.valorIva = valorIva;
    }

}
