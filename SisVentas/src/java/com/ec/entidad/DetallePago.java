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
@Table(name = "detalle_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetallePago.findAll", query = "SELECT d FROM DetallePago d")
    , @NamedQuery(name = "DetallePago.findByIdDetallePago", query = "SELECT d FROM DetallePago d WHERE d.idDetallePago = :idDetallePago")
    , @NamedQuery(name = "DetallePago.findByDetpFechaCobro", query = "SELECT d FROM DetallePago d WHERE d.detpFechaCobro = :detpFechaCobro")
    , @NamedQuery(name = "DetallePago.findByDetpFechaPago", query = "SELECT d FROM DetallePago d WHERE d.detpFechaPago = :detpFechaPago")
    , @NamedQuery(name = "DetallePago.findByDetpNumPago", query = "SELECT d FROM DetallePago d WHERE d.detpNumPago = :detpNumPago")
    , @NamedQuery(name = "DetallePago.findByDetpSubtotal", query = "SELECT d FROM DetallePago d WHERE d.detpSubtotal = :detpSubtotal")
    , @NamedQuery(name = "DetallePago.findByDetpMulta", query = "SELECT d FROM DetallePago d WHERE d.detpMulta = :detpMulta")
    , @NamedQuery(name = "DetallePago.findByDetpTotal", query = "SELECT d FROM DetallePago d WHERE d.detpTotal = :detpTotal")
    , @NamedQuery(name = "DetallePago.findByDetpAbono", query = "SELECT d FROM DetallePago d WHERE d.detpAbono = :detpAbono")
    , @NamedQuery(name = "DetallePago.findByDetpSaldo", query = "SELECT d FROM DetallePago d WHERE d.detpSaldo = :detpSaldo")})
public class DetallePago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_pago")
    private Integer idDetallePago;
    @Column(name = "detp_fecha_cobro")
    @Temporal(TemporalType.DATE)
    private Date detpFechaCobro;
    @Column(name = "detp_fecha_pago")
    @Temporal(TemporalType.DATE)
    private Date detpFechaPago;
    @Column(name = "detp_num_pago")
    private Integer detpNumPago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "detp_subtotal")
    private BigDecimal detpSubtotal;
    @Column(name = "detp_multa")
    private BigDecimal detpMulta;
    @Column(name = "detp_total")
    private BigDecimal detpTotal;
    @Column(name = "detp_abono")
    private BigDecimal detpAbono;
    @Column(name = "detp_saldo")
    private BigDecimal detpSaldo;
    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura")
    @ManyToOne
    private Factura idFactura;

    public DetallePago() {
    }

    public DetallePago(Integer idDetallePago) {
        this.idDetallePago = idDetallePago;
    }

    public Integer getIdDetallePago() {
        return idDetallePago;
    }

    public void setIdDetallePago(Integer idDetallePago) {
        this.idDetallePago = idDetallePago;
    }

    public Date getDetpFechaCobro() {
        return detpFechaCobro;
    }

    public void setDetpFechaCobro(Date detpFechaCobro) {
        this.detpFechaCobro = detpFechaCobro;
    }

    public Date getDetpFechaPago() {
        return detpFechaPago;
    }

    public void setDetpFechaPago(Date detpFechaPago) {
        this.detpFechaPago = detpFechaPago;
    }

    public Integer getDetpNumPago() {
        return detpNumPago;
    }

    public void setDetpNumPago(Integer detpNumPago) {
        this.detpNumPago = detpNumPago;
    }

    public BigDecimal getDetpSubtotal() {
        return detpSubtotal;
    }

    public void setDetpSubtotal(BigDecimal detpSubtotal) {
        this.detpSubtotal = detpSubtotal;
    }

    public BigDecimal getDetpMulta() {
        return detpMulta;
    }

    public void setDetpMulta(BigDecimal detpMulta) {
        this.detpMulta = detpMulta;
    }

    public BigDecimal getDetpTotal() {
        return detpTotal;
    }

    public void setDetpTotal(BigDecimal detpTotal) {
        this.detpTotal = detpTotal;
    }

    public BigDecimal getDetpAbono() {
        return detpAbono;
    }

    public void setDetpAbono(BigDecimal detpAbono) {
        this.detpAbono = detpAbono;
    }

    public BigDecimal getDetpSaldo() {
        return detpSaldo;
    }

    public void setDetpSaldo(BigDecimal detpSaldo) {
        this.detpSaldo = detpSaldo;
    }

    public Factura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Factura idFactura) {
        this.idFactura = idFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetallePago != null ? idDetallePago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallePago)) {
            return false;
        }
        DetallePago other = (DetallePago) object;
        if ((this.idDetallePago == null && other.idDetallePago != null) || (this.idDetallePago != null && !this.idDetallePago.equals(other.idDetallePago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.DetallePago[ idDetallePago=" + idDetallePago + " ]";
    }
    
}
