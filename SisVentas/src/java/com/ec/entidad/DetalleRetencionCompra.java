/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "detalle_retencion_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleRetencionCompra.findAll", query = "SELECT d FROM DetalleRetencionCompra d")
    ,
    @NamedQuery(name = "DetalleRetencionCompra.findByDrcCodigo", query = "SELECT d FROM DetalleRetencionCompra d WHERE d.drcCodigo = :drcCodigo")
    ,
    @NamedQuery(name = "DetalleRetencionCompra.findByDrcBaseImponible", query = "SELECT d FROM DetalleRetencionCompra d WHERE d.drcBaseImponible = :drcBaseImponible")
    ,
    @NamedQuery(name = "DetalleRetencionCompra.findByDrcPorcentaje", query = "SELECT d FROM DetalleRetencionCompra d WHERE d.drcPorcentaje = :drcPorcentaje")
    ,
    @NamedQuery(name = "DetalleRetencionCompra.findByDrcValorRetenido", query = "SELECT d FROM DetalleRetencionCompra d WHERE d.drcValorRetenido = :drcValorRetenido")})
public class DetalleRetencionCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "drc_codigo")
    private Integer drcCodigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "drc_base_imponible")
    private BigDecimal drcBaseImponible;
    @Basic(optional = false)
    @NotNull
    @Column(name = "drc_porcentaje")
    private BigDecimal drcPorcentaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "drc_valor_retenido")
    private BigDecimal drcValorRetenido;

    @Column(name = "drc_tipo_registro")
    private String drcTipoRegistro;
    @Column(name = "drc_cod_impuesto_asignado")
    private String drcCodImpuestoAsignado;
    @Column(name = "drc_descripcion")
    private String drcDescripcion;

    @JoinColumn(name = "tire_codigo", referencedColumnName = "tire_codigo")
    @ManyToOne
    private TipoRetencion tireCodigo;
    @JoinColumn(name = "rco_codigo", referencedColumnName = "rco_codigo")
    @ManyToOne
    private RetencionCompra rcoCodigo;
    @JoinColumn(name = "id_tipoivaretencion", referencedColumnName = "id_tipoivaretencion")
    @ManyToOne
    private Tipoivaretencion idTipoivaretencion;

    public DetalleRetencionCompra() {
    }

    public DetalleRetencionCompra(Integer drcCodigo) {
        this.drcCodigo = drcCodigo;
    }

    public DetalleRetencionCompra(Integer drcCodigo, BigDecimal drcBaseImponible, BigDecimal drcPorcentaje, BigDecimal drcValorRetenido) {
        this.drcCodigo = drcCodigo;
        this.drcBaseImponible = drcBaseImponible;
        this.drcPorcentaje = drcPorcentaje;
        this.drcValorRetenido = drcValorRetenido;
    }

    public DetalleRetencionCompra(BigDecimal drcBaseImponible, BigDecimal drcPorcentaje, BigDecimal drcValorRetenido, TipoRetencion tireCodigo, RetencionCompra rcoCodigo) {
        this.drcBaseImponible = drcBaseImponible;
        this.drcPorcentaje = drcPorcentaje;
        this.drcValorRetenido = drcValorRetenido;
        this.tireCodigo = tireCodigo;
        this.rcoCodigo = rcoCodigo;
    }

    public Integer getDrcCodigo() {
        return drcCodigo;
    }

    public void setDrcCodigo(Integer drcCodigo) {
        this.drcCodigo = drcCodigo;
    }

    public BigDecimal getDrcBaseImponible() {
        return drcBaseImponible;
    }

    public void setDrcBaseImponible(BigDecimal drcBaseImponible) {
        this.drcBaseImponible = drcBaseImponible;
    }

    public BigDecimal getDrcPorcentaje() {
        return drcPorcentaje;
    }

    public void setDrcPorcentaje(BigDecimal drcPorcentaje) {
        this.drcPorcentaje = drcPorcentaje;
    }

    public BigDecimal getDrcValorRetenido() {
        return drcValorRetenido;
    }

    public void setDrcValorRetenido(BigDecimal drcValorRetenido) {
        this.drcValorRetenido = drcValorRetenido;
    }

    public TipoRetencion getTireCodigo() {
        return tireCodigo;
    }

    public void setTireCodigo(TipoRetencion tireCodigo) {
        this.tireCodigo = tireCodigo;
    }

    public RetencionCompra getRcoCodigo() {
        return rcoCodigo;
    }

    public void setRcoCodigo(RetencionCompra rcoCodigo) {
        this.rcoCodigo = rcoCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (drcCodigo != null ? drcCodigo.hashCode() : 0);
        return hash;
    }

    public String getDrcTipoRegistro() {
        return drcTipoRegistro;
    }

    public void setDrcTipoRegistro(String drcTipoRegistro) {
        this.drcTipoRegistro = drcTipoRegistro;
    }

    public String getDrcCodImpuestoAsignado() {
        return drcCodImpuestoAsignado;
    }

    public void setDrcCodImpuestoAsignado(String drcCodImpuestoAsignado) {
        this.drcCodImpuestoAsignado = drcCodImpuestoAsignado;
    }

    public String getDrcDescripcion() {
        return drcDescripcion;
    }

    public void setDrcDescripcion(String drcDescripcion) {
        this.drcDescripcion = drcDescripcion;
    }

    public Tipoivaretencion getIdTipoivaretencion() {
        return idTipoivaretencion;
    }

    public void setIdTipoivaretencion(Tipoivaretencion idTipoivaretencion) {
        this.idTipoivaretencion = idTipoivaretencion;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleRetencionCompra)) {
            return false;
        }
        DetalleRetencionCompra other = (DetalleRetencionCompra) object;
        if ((this.drcCodigo == null && other.drcCodigo != null) || (this.drcCodigo != null && !this.drcCodigo.equals(other.drcCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.DetalleRetencionCompra[ drcCodigo=" + drcCodigo + " ]";
    }

}
