/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "provincia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provincia.findAll", query = "SELECT p FROM Provincia p")
    , @NamedQuery(name = "Provincia.findByIdProvincia", query = "SELECT p FROM Provincia p WHERE p.idProvincia = :idProvincia")
    , @NamedQuery(name = "Provincia.findByProvNumero", query = "SELECT p FROM Provincia p WHERE p.provNumero = :provNumero")
    , @NamedQuery(name = "Provincia.findByProvNombre", query = "SELECT p FROM Provincia p WHERE p.provNombre = :provNombre")
    , @NamedQuery(name = "Provincia.findByProvEstado", query = "SELECT p FROM Provincia p WHERE p.provEstado = :provEstado")})
public class Provincia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_provincia")
    private Integer idProvincia;
    @Column(name = "prov_numero")
    private Integer provNumero;
    @Size(max = 100)
    @Column(name = "prov_nombre")
    private String provNombre;
    @Column(name = "prov_estado")
    private Boolean provEstado;
    @OneToMany(mappedBy = "idProvincia")
    private Collection<Canton> cantonCollection;

    public Provincia() {
    }

    public Provincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public Integer getProvNumero() {
        return provNumero;
    }

    public void setProvNumero(Integer provNumero) {
        this.provNumero = provNumero;
    }

    public String getProvNombre() {
        return provNombre;
    }

    public void setProvNombre(String provNombre) {
        this.provNombre = provNombre;
    }

    public Boolean getProvEstado() {
        return provEstado;
    }

    public void setProvEstado(Boolean provEstado) {
        this.provEstado = provEstado;
    }

    @XmlTransient
    public Collection<Canton> getCantonCollection() {
        return cantonCollection;
    }

    public void setCantonCollection(Collection<Canton> cantonCollection) {
        this.cantonCollection = cantonCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProvincia != null ? idProvincia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provincia)) {
            return false;
        }
        Provincia other = (Provincia) object;
        if ((this.idProvincia == null && other.idProvincia != null) || (this.idProvincia != null && !this.idProvincia.equals(other.idProvincia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Provincia[ idProvincia=" + idProvincia + " ]";
    }
    
}
