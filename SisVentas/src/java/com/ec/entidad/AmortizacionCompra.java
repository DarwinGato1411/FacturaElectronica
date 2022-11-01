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

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "amortizacion_compra")
@NamedQueries({
    @NamedQuery(name = "AmortizacionCompra.findAll", query = "SELECT a FROM AmortizacionCompra a")})
public class AmortizacionCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_amortizacion_compra")
    private Integer idAmortizacionCompra;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "det_valor")
    private BigDecimal detValor;
    @Column(name = "det_fecha")
    @Temporal(TemporalType.DATE)
    private Date detFecha;
    @Column(name = "det_dias")
    private Integer detDias;
    @JoinColumn(name = "id_compra", referencedColumnName = "id_cabecera")
    @ManyToOne
    private CabeceraCompra idCompra;

    public AmortizacionCompra() {
    }

    public AmortizacionCompra(Integer idAmortizacionCompra) {
        this.idAmortizacionCompra = idAmortizacionCompra;
    }

    public Integer getIdAmortizacionCompra() {
        return idAmortizacionCompra;
    }

    public void setIdAmortizacionCompra(Integer idAmortizacionCompra) {
        this.idAmortizacionCompra = idAmortizacionCompra;
    }

    public BigDecimal getDetValor() {
        return detValor;
    }

    public void setDetValor(BigDecimal detValor) {
        this.detValor = detValor;
    }

    public Date getDetFecha() {
        return detFecha;
    }

    public void setDetFecha(Date detFecha) {
        this.detFecha = detFecha;
    }

    public Integer getDetDias() {
        return detDias;
    }

    public void setDetDias(Integer detDias) {
        this.detDias = detDias;
    }

    public CabeceraCompra getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(CabeceraCompra idCompra) {
        this.idCompra = idCompra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAmortizacionCompra != null ? idAmortizacionCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AmortizacionCompra)) {
            return false;
        }
        AmortizacionCompra other = (AmortizacionCompra) object;
        if ((this.idAmortizacionCompra == null && other.idAmortizacionCompra != null) || (this.idAmortizacionCompra != null && !this.idAmortizacionCompra.equals(other.idAmortizacionCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.AmortizacionCompra[ idAmortizacionCompra=" + idAmortizacionCompra + " ]";
    }
    
}
