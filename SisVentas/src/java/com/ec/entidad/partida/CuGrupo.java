/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad.partida;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "cu_grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuGrupo.findAll", query = "SELECT c FROM CuGrupo c")
    , @NamedQuery(name = "CuGrupo.findByIdGrupo", query = "SELECT c FROM CuGrupo c WHERE c.idGrupo = :idGrupo")
    , @NamedQuery(name = "CuGrupo.findByIdClase", query = "SELECT c FROM CuGrupo c WHERE c.idClase = :idClase")
    , @NamedQuery(name = "CuGrupo.findByGrupNumero", query = "SELECT c FROM CuGrupo c WHERE c.grupNumero = :grupNumero")
    , @NamedQuery(name = "CuGrupo.findByGrupNombre", query = "SELECT c FROM CuGrupo c WHERE c.grupNombre = :grupNombre")
    , @NamedQuery(name = "CuGrupo.findByGrupTotal", query = "SELECT c FROM CuGrupo c WHERE c.grupTotal = :grupTotal")
    , @NamedQuery(name = "CuGrupo.findByGrupSaldo", query = "SELECT c FROM CuGrupo c WHERE c.grupSaldo = :grupSaldo")
    , @NamedQuery(name = "CuGrupo.findByGrupOtro", query = "SELECT c FROM CuGrupo c WHERE c.grupOtro = :grupOtro")})
public class CuGrupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_grupo")
    private Integer idGrupo;
    @Column(name = "id_clase")
    private Integer idClase;
    @Size(max = 100)
    @Column(name = "grup_numero")
    private String grupNumero;
    @Size(max = 100)
    @Column(name = "grup_nombre")
    private String grupNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "grup_total")
    private BigDecimal grupTotal;
    @Column(name = "grup_saldo")
    private BigDecimal grupSaldo;
    @Column(name = "grup_otro")
    private BigDecimal grupOtro;
    @OneToMany(mappedBy = "idGrupo")
    private Collection<CuCuenta> cuCuentaCollection;
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_clase", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private CuClase cuClase;

    public CuGrupo() {
    }

    public CuGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdClase() {
        return idClase;
    }

    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }

    public String getGrupNumero() {
        return grupNumero;
    }

    public void setGrupNumero(String grupNumero) {
        this.grupNumero = grupNumero;
    }

    public String getGrupNombre() {
        return grupNombre;
    }

    public void setGrupNombre(String grupNombre) {
        this.grupNombre = grupNombre;
    }

    public BigDecimal getGrupTotal() {
        return grupTotal;
    }

    public void setGrupTotal(BigDecimal grupTotal) {
        this.grupTotal = grupTotal;
    }

    public BigDecimal getGrupSaldo() {
        return grupSaldo;
    }

    public void setGrupSaldo(BigDecimal grupSaldo) {
        this.grupSaldo = grupSaldo;
    }

    public BigDecimal getGrupOtro() {
        return grupOtro;
    }

    public void setGrupOtro(BigDecimal grupOtro) {
        this.grupOtro = grupOtro;
    }

    @XmlTransient
    public Collection<CuCuenta> getCuCuentaCollection() {
        return cuCuentaCollection;
    }

    public void setCuCuentaCollection(Collection<CuCuenta> cuCuentaCollection) {
        this.cuCuentaCollection = cuCuentaCollection;
    }

    public CuClase getCuClase() {
        return cuClase;
    }

    public void setCuClase(CuClase cuClase) {
        this.cuClase = cuClase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuGrupo)) {
            return false;
        }
        CuGrupo other = (CuGrupo) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.partida.CuGrupo[ idGrupo=" + idGrupo + " ]";
    }
    
}
