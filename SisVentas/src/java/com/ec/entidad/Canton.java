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
@Table(name = "canton")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Canton.findAll", query = "SELECT c FROM Canton c")
    , @NamedQuery(name = "Canton.findByIdCanton", query = "SELECT c FROM Canton c WHERE c.idCanton = :idCanton")
    , @NamedQuery(name = "Canton.findByCantNumero", query = "SELECT c FROM Canton c WHERE c.cantNumero = :cantNumero")
    , @NamedQuery(name = "Canton.findByCantNombre", query = "SELECT c FROM Canton c WHERE c.cantNombre = :cantNombre")
    , @NamedQuery(name = "Canton.findByCantEstado", query = "SELECT c FROM Canton c WHERE c.cantEstado = :cantEstado")})
public class Canton implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_canton")
    private Integer idCanton;
    @Column(name = "cant_numero")
    private Integer cantNumero;
    @Size(max = 100)
    @Column(name = "cant_nombre")
    private String cantNombre;
    @Column(name = "cant_estado")
    private Boolean cantEstado;
    @OneToMany(mappedBy = "idCanton")
    private Collection<Parroquia> parroquiaCollection;
    @JoinColumn(name = "id_provincia", referencedColumnName = "id_provincia")
    @ManyToOne
    private Provincia idProvincia;

    public Canton() {
    }

    public Canton(Integer idCanton) {
        this.idCanton = idCanton;
    }

    public Integer getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(Integer idCanton) {
        this.idCanton = idCanton;
    }

    public Integer getCantNumero() {
        return cantNumero;
    }

    public void setCantNumero(Integer cantNumero) {
        this.cantNumero = cantNumero;
    }

    public String getCantNombre() {
        return cantNombre;
    }

    public void setCantNombre(String cantNombre) {
        this.cantNombre = cantNombre;
    }

    public Boolean getCantEstado() {
        return cantEstado;
    }

    public void setCantEstado(Boolean cantEstado) {
        this.cantEstado = cantEstado;
    }

    @XmlTransient
    public Collection<Parroquia> getParroquiaCollection() {
        return parroquiaCollection;
    }

    public void setParroquiaCollection(Collection<Parroquia> parroquiaCollection) {
        this.parroquiaCollection = parroquiaCollection;
    }

    public Provincia getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Provincia idProvincia) {
        this.idProvincia = idProvincia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCanton != null ? idCanton.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canton)) {
            return false;
        }
        Canton other = (Canton) object;
        if ((this.idCanton == null && other.idCanton != null) || (this.idCanton != null && !this.idCanton.equals(other.idCanton))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Canton[ idCanton=" + idCanton + " ]";
    }
    
}
