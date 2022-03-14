/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad.sri;

import com.ec.entidad.docsri.RetencionCompraSri;
import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "detalle_retencion_compra_sri")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleRetencionCompraSri.findAll", query = "SELECT d FROM DetalleRetencionCompraSri d")
    , @NamedQuery(name = "DetalleRetencionCompraSri.findByDrcCodigo", query = "SELECT d FROM DetalleRetencionCompraSri d WHERE d.drcCodigo = :drcCodigo")
    , @NamedQuery(name = "DetalleRetencionCompraSri.findByDrcBaseImponible", query = "SELECT d FROM DetalleRetencionCompraSri d WHERE d.drcBaseImponible = :drcBaseImponible")
    , @NamedQuery(name = "DetalleRetencionCompraSri.findByDrcPorcentaje", query = "SELECT d FROM DetalleRetencionCompraSri d WHERE d.drcPorcentaje = :drcPorcentaje")
    , @NamedQuery(name = "DetalleRetencionCompraSri.findByDrcValorRetenido", query = "SELECT d FROM DetalleRetencionCompraSri d WHERE d.drcValorRetenido = :drcValorRetenido")
    , @NamedQuery(name = "DetalleRetencionCompraSri.findByTireCodigo", query = "SELECT d FROM DetalleRetencionCompraSri d WHERE d.tireCodigo = :tireCodigo")
    , @NamedQuery(name = "DetalleRetencionCompraSri.findByDrcCodImpuestoAsignado", query = "SELECT d FROM DetalleRetencionCompraSri d WHERE d.drcCodImpuestoAsignado = :drcCodImpuestoAsignado")
    , @NamedQuery(name = "DetalleRetencionCompraSri.findByIdTipoivaretencion", query = "SELECT d FROM DetalleRetencionCompraSri d WHERE d.idTipoivaretencion = :idTipoivaretencion")
    , @NamedQuery(name = "DetalleRetencionCompraSri.findByDrcDescripcion", query = "SELECT d FROM DetalleRetencionCompraSri d WHERE d.drcDescripcion = :drcDescripcion")
    , @NamedQuery(name = "DetalleRetencionCompraSri.findByDrcTipoRegistro", query = "SELECT d FROM DetalleRetencionCompraSri d WHERE d.drcTipoRegistro = :drcTipoRegistro")})
public class DetalleRetencionCompraSri implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "drc_codigo")
    private Integer drcCodigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "drc_base_imponible")
    private double drcBaseImponible;
    @Basic(optional = false)
    @NotNull
    @Column(name = "drc_porcentaje")
    private double drcPorcentaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "drc_valor_retenido")
    private double drcValorRetenido;
    @Size(max = 6)
    @Column(name = "tire_codigo")
    private String tireCodigo;
    @Size(max = 2)
    @Column(name = "drc_cod_impuesto_asignado")
    private String drcCodImpuestoAsignado;
    @Column(name = "id_tipoivaretencion")
    private Integer idTipoivaretencion;
    @Size(max = 200)
    @Column(name = "drc_descripcion")
    private String drcDescripcion;
    @Size(max = 10)
    @Column(name = "drc_tipo_registro")
    private String drcTipoRegistro;
    @JoinColumn(name = "rco_codigo", referencedColumnName = "rco_codigo")
    @ManyToOne
    private RetencionCompraSri rcoCodigo;

    public DetalleRetencionCompraSri() {
    }

    public DetalleRetencionCompraSri(Integer drcCodigo) {
        this.drcCodigo = drcCodigo;
    }

    public DetalleRetencionCompraSri(Integer drcCodigo, double drcBaseImponible, double drcPorcentaje, double drcValorRetenido) {
        this.drcCodigo = drcCodigo;
        this.drcBaseImponible = drcBaseImponible;
        this.drcPorcentaje = drcPorcentaje;
        this.drcValorRetenido = drcValorRetenido;
    }

    public Integer getDrcCodigo() {
        return drcCodigo;
    }

    public void setDrcCodigo(Integer drcCodigo) {
        this.drcCodigo = drcCodigo;
    }

    public double getDrcBaseImponible() {
        return drcBaseImponible;
    }

    public void setDrcBaseImponible(double drcBaseImponible) {
        this.drcBaseImponible = drcBaseImponible;
    }

    public double getDrcPorcentaje() {
        return drcPorcentaje;
    }

    public void setDrcPorcentaje(double drcPorcentaje) {
        this.drcPorcentaje = drcPorcentaje;
    }

    public double getDrcValorRetenido() {
        return drcValorRetenido;
    }

    public void setDrcValorRetenido(double drcValorRetenido) {
        this.drcValorRetenido = drcValorRetenido;
    }

    public String getTireCodigo() {
        return tireCodigo;
    }

    public void setTireCodigo(String tireCodigo) {
        this.tireCodigo = tireCodigo;
    }

    public String getDrcCodImpuestoAsignado() {
        return drcCodImpuestoAsignado;
    }

    public void setDrcCodImpuestoAsignado(String drcCodImpuestoAsignado) {
        this.drcCodImpuestoAsignado = drcCodImpuestoAsignado;
    }

    public Integer getIdTipoivaretencion() {
        return idTipoivaretencion;
    }

    public void setIdTipoivaretencion(Integer idTipoivaretencion) {
        this.idTipoivaretencion = idTipoivaretencion;
    }

    public String getDrcDescripcion() {
        return drcDescripcion;
    }

    public void setDrcDescripcion(String drcDescripcion) {
        this.drcDescripcion = drcDescripcion;
    }

    public String getDrcTipoRegistro() {
        return drcTipoRegistro;
    }

    public void setDrcTipoRegistro(String drcTipoRegistro) {
        this.drcTipoRegistro = drcTipoRegistro;
    }

    public RetencionCompraSri getRcoCodigo() {
        return rcoCodigo;
    }

    public void setRcoCodigo(RetencionCompraSri rcoCodigo) {
        this.rcoCodigo = rcoCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (drcCodigo != null ? drcCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleRetencionCompraSri)) {
            return false;
        }
        DetalleRetencionCompraSri other = (DetalleRetencionCompraSri) object;
        if ((this.drcCodigo == null && other.drcCodigo != null) || (this.drcCodigo != null && !this.drcCodigo.equals(other.drcCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.sri.DetalleRetencionCompraSri[ drcCodigo=" + drcCodigo + " ]";
    }
    
}
