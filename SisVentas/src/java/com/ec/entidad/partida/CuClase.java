/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad.partida;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "cu_clase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CuClase.findAll", query = "SELECT c FROM CuClase c")
    , @NamedQuery(name = "CuClase.findByIdClase", query = "SELECT c FROM CuClase c WHERE c.idClase = :idClase")
    , @NamedQuery(name = "CuClase.findByClasNumero", query = "SELECT c FROM CuClase c WHERE c.clasNumero = :clasNumero")
    , @NamedQuery(name = "CuClase.findByClasNombre", query = "SELECT c FROM CuClase c WHERE c.clasNombre = :clasNombre")
    , @NamedQuery(name = "CuClase.findByClasTotal", query = "SELECT c FROM CuClase c WHERE c.clasTotal = :clasTotal")
    , @NamedQuery(name = "CuClase.findByClasSaldo", query = "SELECT c FROM CuClase c WHERE c.clasSaldo = :clasSaldo")
    , @NamedQuery(name = "CuClase.findByClasOtro", query = "SELECT c FROM CuClase c WHERE c.clasOtro = :clasOtro")})
public class CuClase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_clase")
    private Integer idClase;
    @Size(max = 100)
    @Column(name = "clas_numero")
    private String clasNumero;
    @Size(max = 100)
    @Column(name = "clas_nombre")
    private String clasNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "clas_total")
    private BigDecimal clasTotal;
    @Column(name = "clas_saldo")
    private BigDecimal clasSaldo;
    @Column(name = "clas_otro")
    private BigDecimal clasOtro;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cuClase")
    private CuGrupo cuGrupo;

    public CuClase() {
    }

    public CuClase(Integer idClase) {
        this.idClase = idClase;
    }

    public Integer getIdClase() {
        return idClase;
    }

    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }

    public String getClasNumero() {
        return clasNumero;
    }

    public void setClasNumero(String clasNumero) {
        this.clasNumero = clasNumero;
    }

    public String getClasNombre() {
        return clasNombre;
    }

    public void setClasNombre(String clasNombre) {
        this.clasNombre = clasNombre;
    }

    public BigDecimal getClasTotal() {
        return clasTotal;
    }

    public void setClasTotal(BigDecimal clasTotal) {
        this.clasTotal = clasTotal;
    }

    public BigDecimal getClasSaldo() {
        return clasSaldo;
    }

    public void setClasSaldo(BigDecimal clasSaldo) {
        this.clasSaldo = clasSaldo;
    }

    public BigDecimal getClasOtro() {
        return clasOtro;
    }

    public void setClasOtro(BigDecimal clasOtro) {
        this.clasOtro = clasOtro;
    }

    public CuGrupo getCuGrupo() {
        return cuGrupo;
    }

    public void setCuGrupo(CuGrupo cuGrupo) {
        this.cuGrupo = cuGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClase != null ? idClase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CuClase)) {
            return false;
        }
        CuClase other = (CuClase) object;
        if ((this.idClase == null && other.idClase != null) || (this.idClase != null && !this.idClase.equals(other.idClase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.partida.CuClase[ idClase=" + idClase + " ]";
    }
    
}
