/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad.partida;

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
 * @author Darwin
 */
@Entity
@Table(name = "cu_sub_cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuSubCuenta.findAll", query = "SELECT c FROM CuSubCuenta c")
    , @NamedQuery(name = "CuSubCuenta.findByIdSubCuenta", query = "SELECT c FROM CuSubCuenta c WHERE c.idSubCuenta = :idSubCuenta")
    , @NamedQuery(name = "CuSubCuenta.findBySubcNumero", query = "SELECT c FROM CuSubCuenta c WHERE c.subcNumero = :subcNumero")
    , @NamedQuery(name = "CuSubCuenta.findBySubcNombre", query = "SELECT c FROM CuSubCuenta c WHERE c.subcNombre = :subcNombre")
    , @NamedQuery(name = "CuSubCuenta.findBySubcTotal", query = "SELECT c FROM CuSubCuenta c WHERE c.subcTotal = :subcTotal")
    , @NamedQuery(name = "CuSubCuenta.findBySubcSaldo", query = "SELECT c FROM CuSubCuenta c WHERE c.subcSaldo = :subcSaldo")
    , @NamedQuery(name = "CuSubCuenta.findBySubcOtro", query = "SELECT c FROM CuSubCuenta c WHERE c.subcOtro = :subcOtro")})
public class CuSubCuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sub_cuenta")
    private Integer idSubCuenta;
    @Size(max = 100)
    @Column(name = "subc_numero")
    private String subcNumero;
    @Size(max = 100)
    @Column(name = "subc_nombre")
    private String subcNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "subc_total")
    private BigDecimal subcTotal;
    @Column(name = "subc_saldo")
    private BigDecimal subcSaldo;
    @Column(name = "subc_otro")
    private BigDecimal subcOtro;
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id_cuenta")
    @ManyToOne
    private CuCuenta idCuenta;

    public CuSubCuenta() {
    }

    public CuSubCuenta(Integer idSubCuenta) {
        this.idSubCuenta = idSubCuenta;
    }

    public Integer getIdSubCuenta() {
        return idSubCuenta;
    }

    public void setIdSubCuenta(Integer idSubCuenta) {
        this.idSubCuenta = idSubCuenta;
    }

    public String getSubcNumero() {
        return subcNumero;
    }

    public void setSubcNumero(String subcNumero) {
        this.subcNumero = subcNumero;
    }

    public String getSubcNombre() {
        return subcNombre;
    }

    public void setSubcNombre(String subcNombre) {
        this.subcNombre = subcNombre;
    }

    public BigDecimal getSubcTotal() {
        return subcTotal;
    }

    public void setSubcTotal(BigDecimal subcTotal) {
        this.subcTotal = subcTotal;
    }

    public BigDecimal getSubcSaldo() {
        return subcSaldo;
    }

    public void setSubcSaldo(BigDecimal subcSaldo) {
        this.subcSaldo = subcSaldo;
    }

    public BigDecimal getSubcOtro() {
        return subcOtro;
    }

    public void setSubcOtro(BigDecimal subcOtro) {
        this.subcOtro = subcOtro;
    }

    public CuCuenta getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(CuCuenta idCuenta) {
        this.idCuenta = idCuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubCuenta != null ? idSubCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuSubCuenta)) {
            return false;
        }
        CuSubCuenta other = (CuSubCuenta) object;
        if ((this.idSubCuenta == null && other.idSubCuenta != null) || (this.idSubCuenta != null && !this.idSubCuenta.equals(other.idSubCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.partida.CuSubCuenta[ idSubCuenta=" + idSubCuenta + " ]";
    }
    
}
