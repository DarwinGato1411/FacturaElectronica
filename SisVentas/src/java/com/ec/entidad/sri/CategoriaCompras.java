/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad.sri;


import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "categoria_compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaCompras.findAll", query = "SELECT c FROM CategoriaCompras c")
    , @NamedQuery(name = "CategoriaCompras.findByIdCategoriaCompras", query = "SELECT c FROM CategoriaCompras c WHERE c.idCategoriaCompras = :idCategoriaCompras")
    , @NamedQuery(name = "CategoriaCompras.findByCatcNombre", query = "SELECT c FROM CategoriaCompras c WHERE c.catcNombre = :catcNombre")
    , @NamedQuery(name = "CategoriaCompras.findByCatcCodigo", query = "SELECT c FROM CategoriaCompras c WHERE c.catcCodigo = :catcCodigo")
    , @NamedQuery(name = "CategoriaCompras.findByCatcIsprincipal", query = "SELECT c FROM CategoriaCompras c WHERE c.catcIsprincipal = :catcIsprincipal")})
public class CategoriaCompras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_categoria_compras")
    private Integer idCategoriaCompras;
    @Column(name = "catc_nombre")
    private String catcNombre;
    @Column(name = "catc_codigo")
    private String catcCodigo;
    @Column(name = "catc_isprincipal")
    private Boolean catcIsprincipal;
    @OneToMany(mappedBy = "idCategoriaCompras")
    private Collection<CabeceraCompraSri> cabeceraCompraSriCollection;

    public CategoriaCompras() {
    }

    public CategoriaCompras(Integer idCategoriaCompras) {
        this.idCategoriaCompras = idCategoriaCompras;
    }

    public Integer getIdCategoriaCompras() {
        return idCategoriaCompras;
    }

    public void setIdCategoriaCompras(Integer idCategoriaCompras) {
        this.idCategoriaCompras = idCategoriaCompras;
    }

    public String getCatcNombre() {
        return catcNombre;
    }

    public void setCatcNombre(String catcNombre) {
        this.catcNombre = catcNombre;
    }

    public String getCatcCodigo() {
        return catcCodigo;
    }

    public void setCatcCodigo(String catcCodigo) {
        this.catcCodigo = catcCodigo;
    }

    public Boolean getCatcIsprincipal() {
        return catcIsprincipal;
    }

    public void setCatcIsprincipal(Boolean catcIsprincipal) {
        this.catcIsprincipal = catcIsprincipal;
    }

    @XmlTransient
    public Collection<CabeceraCompraSri> getCabeceraCompraSriCollection() {
        return cabeceraCompraSriCollection;
    }

    public void setCabeceraCompraSriCollection(Collection<CabeceraCompraSri> cabeceraCompraSriCollection) {
        this.cabeceraCompraSriCollection = cabeceraCompraSriCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoriaCompras != null ? idCategoriaCompras.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaCompras)) {
            return false;
        }
        CategoriaCompras other = (CategoriaCompras) object;
        if ((this.idCategoriaCompras == null && other.idCategoriaCompras != null) || (this.idCategoriaCompras != null && !this.idCategoriaCompras.equals(other.idCategoriaCompras))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entdades.CategoriaCompras[ idCategoriaCompras=" + idCategoriaCompras + " ]";
    }
    
}
