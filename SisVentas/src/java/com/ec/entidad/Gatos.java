/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "gatos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gatos.findAll", query = "SELECT g FROM Gatos g"),
    @NamedQuery(name = "Gatos.findByIdGasto", query = "SELECT g FROM Gatos g WHERE g.idGasto = :idGasto"),
    @NamedQuery(name = "Gatos.findByGasFecha", query = "SELECT g FROM Gatos g WHERE g.gasFecha = :gasFecha"),
    @NamedQuery(name = "Gatos.findByGasRazonSocial", query = "SELECT g FROM Gatos g WHERE g.gasRazonSocial = :gasRazonSocial"),
    @NamedQuery(name = "Gatos.findByGasSubtotal", query = "SELECT g FROM Gatos g WHERE g.gasSubtotal = :gasSubtotal"),
    @NamedQuery(name = "Gatos.findByGasIva", query = "SELECT g FROM Gatos g WHERE g.gasIva = :gasIva"),
    @NamedQuery(name = "Gatos.findByGasValorTotal", query = "SELECT g FROM Gatos g WHERE g.gasValorTotal = :gasValorTotal"),
    @NamedQuery(name = "Gatos.findByGasNumeroComprobante", query = "SELECT g FROM Gatos g WHERE g.gasNumeroComprobante = :gasNumeroComprobante")})
public class Gatos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_gasto")
    private Integer idGasto;
    @Column(name = "gas_fecha")
    @Temporal(TemporalType.DATE)
    private Date gasFecha;
    @Size(max = 100)
    @Column(name = "gas_razon_social")
    private String gasRazonSocial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "gas_subtotal")
    private BigDecimal gasSubtotal;
    @Column(name = "gas_iva")
    private BigDecimal gasIva;
    @Column(name = "gas_valor_total")
    private BigDecimal gasValorTotal;
    @Size(max = 50)
    @Column(name = "gas_numero_comprobante")
    private String gasNumeroComprobante;
    @OneToMany(mappedBy = "idGasto")
    private Collection<DetalleGasto> detalleGastoCollection;

    public Gatos() {
    }

    public Gatos(Integer idGasto) {
        this.idGasto = idGasto;
    }

    public Integer getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(Integer idGasto) {
        this.idGasto = idGasto;
    }

    public Date getGasFecha() {
        return gasFecha;
    }

    public void setGasFecha(Date gasFecha) {
        this.gasFecha = gasFecha;
    }

    public String getGasRazonSocial() {
        return gasRazonSocial;
    }

    public void setGasRazonSocial(String gasRazonSocial) {
        this.gasRazonSocial = gasRazonSocial;
    }

    public BigDecimal getGasSubtotal() {
        return gasSubtotal;
    }

    public void setGasSubtotal(BigDecimal gasSubtotal) {
        this.gasSubtotal = gasSubtotal;
    }

    public BigDecimal getGasIva() {
        return gasIva;
    }

    public void setGasIva(BigDecimal gasIva) {
        this.gasIva = gasIva;
    }

    public BigDecimal getGasValorTotal() {
        return gasValorTotal;
    }

    public void setGasValorTotal(BigDecimal gasValorTotal) {
        this.gasValorTotal = gasValorTotal;
    }

    public String getGasNumeroComprobante() {
        return gasNumeroComprobante;
    }

    public void setGasNumeroComprobante(String gasNumeroComprobante) {
        this.gasNumeroComprobante = gasNumeroComprobante;
    }

    @XmlTransient
    public Collection<DetalleGasto> getDetalleGastoCollection() {
        return detalleGastoCollection;
    }

    public void setDetalleGastoCollection(Collection<DetalleGasto> detalleGastoCollection) {
        this.detalleGastoCollection = detalleGastoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGasto != null ? idGasto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gatos)) {
            return false;
        }
        Gatos other = (Gatos) object;
        if ((this.idGasto == null && other.idGasto != null) || (this.idGasto != null && !this.idGasto.equals(other.idGasto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Gatos[ idGasto=" + idGasto + " ]";
    }
    
}
