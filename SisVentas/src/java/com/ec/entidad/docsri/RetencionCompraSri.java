/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad.docsri;

import com.ec.entidad.sri.DetalleRetencionCompraSri;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "retencion_compra_sri")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RetencionCompraSri.findAll", query = "SELECT r FROM RetencionCompraSri r")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoCodigo", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoCodigo = :rcoCodigo")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoAutorizacion", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoAutorizacion = :rcoAutorizacion")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoDetalle", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoDetalle = :rcoDetalle")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoFecha", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoFecha = :rcoFecha")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoIva", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoIva = :rcoIva")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoPorcentajeIva", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoPorcentajeIva = :rcoPorcentajeIva")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoPuntoEmision", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoPuntoEmision = :rcoPuntoEmision")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoSecuencial", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoSecuencial = :rcoSecuencial")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoSerie", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoSerie = :rcoSerie")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoValorRetencionIva", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoValorRetencionIva = :rcoValorRetencionIva")
    , @NamedQuery(name = "RetencionCompraSri.findByCabFechaEmision", query = "SELECT r FROM RetencionCompraSri r WHERE r.cabFechaEmision = :cabFechaEmision")
    , @NamedQuery(name = "RetencionCompraSri.findByDrcEstadosri", query = "SELECT r FROM RetencionCompraSri r WHERE r.drcEstadosri = :drcEstadosri")
    , @NamedQuery(name = "RetencionCompraSri.findByDrcMensajesri", query = "SELECT r FROM RetencionCompraSri r WHERE r.drcMensajesri = :drcMensajesri")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoPathret", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoPathret = :rcoPathret")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoFechaAutorizacion", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoFechaAutorizacion = :rcoFechaAutorizacion")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoSecuencialText", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoSecuencialText = :rcoSecuencialText")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoBaseGravaIva", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoBaseGravaIva = :rcoBaseGravaIva")
    , @NamedQuery(name = "RetencionCompraSri.findByRcoMsmInfoSri", query = "SELECT r FROM RetencionCompraSri r WHERE r.rcoMsmInfoSri = :rcoMsmInfoSri")})
public class RetencionCompraSri implements Serializable {

    @OneToMany(mappedBy = "rcoCodigo")
    private Collection<DetalleRetencionCompraSri> detalleRetencionCompraSriCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rco_codigo")
    private Integer rcoCodigo;
    @Size(max = 50)
    @Column(name = "rco_autorizacion")
    private String rcoAutorizacion;
    @Size(max = 100)
    @Column(name = "rco_detalle")
    private String rcoDetalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rco_fecha")
    @Temporal(TemporalType.DATE)
    private Date rcoFecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rco_iva")
    private boolean rcoIva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rco_porcentaje_iva")
    private int rcoPorcentajeIva;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "rco_punto_emision")
    private String rcoPuntoEmision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rco_secuencial")
    private int rcoSecuencial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "rco_serie")
    private String rcoSerie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rco_valor_retencion_iva")
    private double rcoValorRetencionIva;
    @Column(name = "cab_fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date cabFechaEmision;
    @Size(max = 100)
    @Column(name = "drc_estadosri")
    private String drcEstadosri;
    @Size(max = 2147483647)
    @Column(name = "drc_mensajesri")
    private String drcMensajesri;
    @Size(max = 150)
    @Column(name = "rco_pathret")
    private String rcoPathret;
    @Column(name = "rco_fecha_autorizacion")
    @Temporal(TemporalType.DATE)
    private Date rcoFechaAutorizacion;
    @Size(max = 100)
    @Column(name = "rco_secuencial_text")
    private String rcoSecuencialText;
    @Column(name = "rco_ruc")
    private String rcoRuc;
    @Column(name = "rco_num_factura")
    private String rcoNumFactura;
    @Column(name = "rco_nombre")
    private String rcoNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rco_base_grava_iva")
    private BigDecimal rcoBaseGravaIva;
    @Size(max = 400)
    @Column(name = "rco_msm_info_sri")
    private String rcoMsmInfoSri;
//    @JoinColumn(name = "id_cabecera", referencedColumnName = "id_cabecera")
//    @ManyToOne(optional = false)
//    private CabeceraCompra idCabecera;
    

    public RetencionCompraSri() {
    }

    public RetencionCompraSri(Integer rcoCodigo) {
        this.rcoCodigo = rcoCodigo;
    }

    public RetencionCompraSri(Integer rcoCodigo, Date rcoFecha, boolean rcoIva, int rcoPorcentajeIva, String rcoPuntoEmision, int rcoSecuencial, String rcoSerie, double rcoValorRetencionIva) {
        this.rcoCodigo = rcoCodigo;
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

    public double getRcoValorRetencionIva() {
        return rcoValorRetencionIva;
    }

    public void setRcoValorRetencionIva(double rcoValorRetencionIva) {
        this.rcoValorRetencionIva = rcoValorRetencionIva;
    }

    public Date getCabFechaEmision() {
        return cabFechaEmision;
    }

    public void setCabFechaEmision(Date cabFechaEmision) {
        this.cabFechaEmision = cabFechaEmision;
    }

    public String getDrcEstadosri() {
        return drcEstadosri;
    }

    public void setDrcEstadosri(String drcEstadosri) {
        this.drcEstadosri = drcEstadosri;
    }

    public String getDrcMensajesri() {
        return drcMensajesri;
    }

    public void setDrcMensajesri(String drcMensajesri) {
        this.drcMensajesri = drcMensajesri;
    }

    public String getRcoPathret() {
        return rcoPathret;
    }

    public void setRcoPathret(String rcoPathret) {
        this.rcoPathret = rcoPathret;
    }

    public Date getRcoFechaAutorizacion() {
        return rcoFechaAutorizacion;
    }

    public void setRcoFechaAutorizacion(Date rcoFechaAutorizacion) {
        this.rcoFechaAutorizacion = rcoFechaAutorizacion;
    }

    public String getRcoSecuencialText() {
        return rcoSecuencialText;
    }

    public void setRcoSecuencialText(String rcoSecuencialText) {
        this.rcoSecuencialText = rcoSecuencialText;
    }

    public BigDecimal getRcoBaseGravaIva() {
        return rcoBaseGravaIva;
    }

    public void setRcoBaseGravaIva(BigDecimal rcoBaseGravaIva) {
        this.rcoBaseGravaIva = rcoBaseGravaIva;
    }

    public String getRcoMsmInfoSri() {
        return rcoMsmInfoSri;
    }

    public void setRcoMsmInfoSri(String rcoMsmInfoSri) {
        this.rcoMsmInfoSri = rcoMsmInfoSri;
    }

//    public CabeceraCompra getIdCabecera() {
//        return idCabecera;
//    }
//
//    public void setIdCabecera(CabeceraCompra idCabecera) {
//        this.idCabecera = idCabecera;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rcoCodigo != null ? rcoCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RetencionCompraSri)) {
            return false;
        }
        RetencionCompraSri other = (RetencionCompraSri) object;
        if ((this.rcoCodigo == null && other.rcoCodigo != null) || (this.rcoCodigo != null && !this.rcoCodigo.equals(other.rcoCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.docsri.RetencionCompraSri[ rcoCodigo=" + rcoCodigo + " ]";
    }

    @XmlTransient
    public Collection<DetalleRetencionCompraSri> getDetalleRetencionCompraSriCollection() {
        return detalleRetencionCompraSriCollection;
    }

    public void setDetalleRetencionCompraSriCollection(Collection<DetalleRetencionCompraSri> detalleRetencionCompraSriCollection) {
        this.detalleRetencionCompraSriCollection = detalleRetencionCompraSriCollection;
    }

    public String getRcoRuc() {
        return rcoRuc;
    }

    public void setRcoRuc(String rcoRuc) {
        this.rcoRuc = rcoRuc;
    }

    public String getRcoNombre() {
        return rcoNombre;
    }

    public void setRcoNombre(String rcoNombre) {
        this.rcoNombre = rcoNombre;
    }

    public String getRcoNumFactura() {
        return rcoNumFactura;
    }

    public void setRcoNumFactura(String rcoNumFactura) {
        this.rcoNumFactura = rcoNumFactura;
    }
    
}
