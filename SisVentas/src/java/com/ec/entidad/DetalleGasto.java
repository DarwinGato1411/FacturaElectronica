/*
 * To change this template, choose Tools | Templates
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "detalle_gasto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleGasto.findAll", query = "SELECT d FROM DetalleGasto d"),
    @NamedQuery(name = "DetalleGasto.findByIdDetalleGasto", query = "SELECT d FROM DetalleGasto d WHERE d.idDetalleGasto = :idDetalleGasto"),
    @NamedQuery(name = "DetalleGasto.findByDgasCatidad", query = "SELECT d FROM DetalleGasto d WHERE d.dgasCatidad = :dgasCatidad"),
    @NamedQuery(name = "DetalleGasto.findByDgasDescripcion", query = "SELECT d FROM DetalleGasto d WHERE d.dgasDescripcion = :dgasDescripcion"),
    @NamedQuery(name = "DetalleGasto.findByDgasTotal", query = "SELECT d FROM DetalleGasto d WHERE d.dgasTotal = :dgasTotal")})
public class DetalleGasto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_gasto")
    private Integer idDetalleGasto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dgas_catidad")
    private BigDecimal dgasCatidad;
    @Size(max = 100)
    @Column(name = "dgas_descripcion")
    private String dgasDescripcion;
    @Column(name = "dgas_total")
    private BigDecimal dgasTotal;
    @Column(name = "dgas_iva")
    private BigDecimal dgasIva;
    @Column(name = "dgas_subtotal")
    private BigDecimal dgasSubtotal;
    @JoinColumn(name = "id_gasto", referencedColumnName = "id_gasto")
    @ManyToOne
    private Gatos idGasto;

    public DetalleGasto() {
    }

    public DetalleGasto(Integer idDetalleGasto) {
        this.idDetalleGasto = idDetalleGasto;
    }

    public Integer getIdDetalleGasto() {
        return idDetalleGasto;
    }

    public void setIdDetalleGasto(Integer idDetalleGasto) {
        this.idDetalleGasto = idDetalleGasto;
    }

    public BigDecimal getDgasCatidad() {
        return dgasCatidad;
    }

    public void setDgasCatidad(BigDecimal dgasCatidad) {
        this.dgasCatidad = dgasCatidad;
    }

    public String getDgasDescripcion() {
        return dgasDescripcion;
    }

    public void setDgasDescripcion(String dgasDescripcion) {
        this.dgasDescripcion = dgasDescripcion;
    }

    public BigDecimal getDgasTotal() {
        return dgasTotal;
    }

    public void setDgasTotal(BigDecimal dgasTotal) {
        this.dgasTotal = dgasTotal;
    }

    public Gatos getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(Gatos idGasto) {
        this.idGasto = idGasto;
    }

    public BigDecimal getDgasIva() {
        return dgasIva;
    }

    public void setDgasIva(BigDecimal dgasIva) {
        this.dgasIva = dgasIva;
    }

    public BigDecimal getDgasSubtotal() {
        return dgasSubtotal;
    }

    public void setDgasSubtotal(BigDecimal dgasSubtotal) {
        this.dgasSubtotal = dgasSubtotal;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleGasto != null ? idDetalleGasto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleGasto)) {
            return false;
        }
        DetalleGasto other = (DetalleGasto) object;
        if ((this.idDetalleGasto == null && other.idDetalleGasto != null) || (this.idDetalleGasto != null && !this.idDetalleGasto.equals(other.idDetalleGasto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.DetalleGasto[ idDetalleGasto=" + idDetalleGasto + " ]";
    }
    
}
