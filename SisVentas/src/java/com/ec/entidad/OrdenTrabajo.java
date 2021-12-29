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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "orden_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenTrabajo.findAll", query = "SELECT o FROM OrdenTrabajo o")
    , @NamedQuery(name = "OrdenTrabajo.findByIdOrdenTrabajo", query = "SELECT o FROM OrdenTrabajo o WHERE o.idOrdenTrabajo = :idOrdenTrabajo")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdNumero", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordNumero = :ordNumero")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdFecha", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordFecha = :ordFecha")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdSubtotal", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordSubtotal = :ordSubtotal")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdIva", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordIva = :ordIva")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdTotal", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordTotal = :ordTotal")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdEstado", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordEstado = :ordEstado")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdTipo", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordTipo = :ordTipo")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdAbono", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordAbono = :ordAbono")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdSaldo", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordSaldo = :ordSaldo")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdTitulo", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordTitulo = :ordTitulo")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdDetalle", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordDetalle = :ordDetalle")
    , @NamedQuery(name = "OrdenTrabajo.findByOrdNumProforma", query = "SELECT o FROM OrdenTrabajo o WHERE o.ordNumProforma = :ordNumProforma")})
public class OrdenTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden_trabajo")
    private Integer idOrdenTrabajo;
    @Column(name = "ord_numero")
    private Integer ordNumero;
    @Column(name = "ord_fecha")
    @Temporal(TemporalType.DATE)
    private Date ordFecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ord_subtotal")
    private BigDecimal ordSubtotal;
    @Column(name = "ord_iva")
    private BigDecimal ordIva;
    @Column(name = "ord_total")
    private BigDecimal ordTotal;
    @Column(name = "ord_estado")
    private String ordEstado;
    @Column(name = "ord_tipo")
    private String ordTipo;
    @Column(name = "ord_abono")
    private BigDecimal ordAbono;
    @Column(name = "ord_saldo")
    private BigDecimal ordSaldo;
    @Column(name = "ord_titulo")
    private String ordTitulo;
    @Column(name = "ord_detalle")
    private String ordDetalle;
    @Column(name = "ord_num_text")
    private String ordNumText;
    @Column(name = "ord_observacion")
    private String ordObservacion;
    @Column(name = "ord_num_proforma")
    private Integer ordNumProforma;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne
    private EstadoFacturas idEstado;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public Integer getIdOrdenTrabajo() {
        return idOrdenTrabajo;
    }

    public void setIdOrdenTrabajo(Integer idOrdenTrabajo) {
        this.idOrdenTrabajo = idOrdenTrabajo;
    }

    public Integer getOrdNumero() {
        return ordNumero;
    }

    public void setOrdNumero(Integer ordNumero) {
        this.ordNumero = ordNumero;
    }

    public Date getOrdFecha() {
        return ordFecha;
    }

    public void setOrdFecha(Date ordFecha) {
        this.ordFecha = ordFecha;
    }

    public BigDecimal getOrdSubtotal() {
        return ordSubtotal;
    }

    public void setOrdSubtotal(BigDecimal ordSubtotal) {
        this.ordSubtotal = ordSubtotal;
    }

    public BigDecimal getOrdIva() {
        return ordIva;
    }

    public void setOrdIva(BigDecimal ordIva) {
        this.ordIva = ordIva;
    }

    public BigDecimal getOrdTotal() {
        return ordTotal;
    }

    public void setOrdTotal(BigDecimal ordTotal) {
        this.ordTotal = ordTotal;
    }

    public String getOrdEstado() {
        return ordEstado;
    }

    public void setOrdEstado(String ordEstado) {
        this.ordEstado = ordEstado;
    }

    public String getOrdTipo() {
        return ordTipo;
    }

    public void setOrdTipo(String ordTipo) {
        this.ordTipo = ordTipo;
    }

    public BigDecimal getOrdAbono() {
        return ordAbono;
    }

    public void setOrdAbono(BigDecimal ordAbono) {
        this.ordAbono = ordAbono;
    }

    public BigDecimal getOrdSaldo() {
        return ordSaldo;
    }

    public void setOrdSaldo(BigDecimal ordSaldo) {
        this.ordSaldo = ordSaldo;
    }

    public String getOrdTitulo() {
        return ordTitulo;
    }

    public void setOrdTitulo(String ordTitulo) {
        this.ordTitulo = ordTitulo;
    }

    public String getOrdDetalle() {
        return ordDetalle;
    }

    public void setOrdDetalle(String ordDetalle) {
        this.ordDetalle = ordDetalle;
    }

    public Integer getOrdNumProforma() {
        return ordNumProforma;
    }

    public void setOrdNumProforma(Integer ordNumProforma) {
        this.ordNumProforma = ordNumProforma;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public EstadoFacturas getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadoFacturas idEstado) {
        this.idEstado = idEstado;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getOrdNumText() {
        return ordNumText;
    }

    public void setOrdNumText(String ordNumText) {
        this.ordNumText = ordNumText;
    }

    public String getOrdObservacion() {
        return ordObservacion;
    }

    public void setOrdObservacion(String ordObservacion) {
        this.ordObservacion = ordObservacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrdenTrabajo != null ? idOrdenTrabajo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenTrabajo)) {
            return false;
        }
        OrdenTrabajo other = (OrdenTrabajo) object;
        if ((this.idOrdenTrabajo == null && other.idOrdenTrabajo != null) || (this.idOrdenTrabajo != null && !this.idOrdenTrabajo.equals(other.idOrdenTrabajo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.OrdenTrabajo[ idOrdenTrabajo=" + idOrdenTrabajo + " ]";
    }

}
