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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "cu_cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuCuenta.findAll", query = "SELECT c FROM CuCuenta c")
    , @NamedQuery(name = "CuCuenta.findByIdCuenta", query = "SELECT c FROM CuCuenta c WHERE c.idCuenta = :idCuenta")
    , @NamedQuery(name = "CuCuenta.findByGrupNumero", query = "SELECT c FROM CuCuenta c WHERE c.grupNumero = :grupNumero")
    , @NamedQuery(name = "CuCuenta.findByGrupNombre", query = "SELECT c FROM CuCuenta c WHERE c.grupNombre = :grupNombre")
    , @NamedQuery(name = "CuCuenta.findByGrupTotal", query = "SELECT c FROM CuCuenta c WHERE c.grupTotal = :grupTotal")
    , @NamedQuery(name = "CuCuenta.findByGrupSaldo", query = "SELECT c FROM CuCuenta c WHERE c.grupSaldo = :grupSaldo")
    , @NamedQuery(name = "CuCuenta.findByGrupOtro", query = "SELECT c FROM CuCuenta c WHERE c.grupOtro = :grupOtro")})
public class CuCuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuenta")
    private Integer idCuenta;
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
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")
    @ManyToOne
    private CuGrupo idGrupo;
    @OneToMany(mappedBy = "idCuenta")
    private Collection<CuSubCuenta> cuSubCuentaCollection;

    public CuCuenta() {
    }

    public CuCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
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

    public CuGrupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(CuGrupo idGrupo) {
        this.idGrupo = idGrupo;
    }

    @XmlTransient
    public Collection<CuSubCuenta> getCuSubCuentaCollection() {
        return cuSubCuentaCollection;
    }

    public void setCuSubCuentaCollection(Collection<CuSubCuenta> cuSubCuentaCollection) {
        this.cuSubCuentaCollection = cuSubCuentaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCuenta != null ? idCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuCuenta)) {
            return false;
        }
        CuCuenta other = (CuCuenta) object;
        if ((this.idCuenta == null && other.idCuenta != null) || (this.idCuenta != null && !this.idCuenta.equals(other.idCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.partida.CuCuenta[ idCuenta=" + idCuenta + " ]";
    }
    
}
