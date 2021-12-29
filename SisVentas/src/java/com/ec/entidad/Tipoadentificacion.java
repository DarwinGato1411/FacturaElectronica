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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "tipoadentificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoadentificacion.findAll", query = "SELECT t FROM Tipoadentificacion t ORDER BY t.tidNombre ASC")
    , @NamedQuery(name = "Tipoadentificacion.findByIdTipoIdentificacion", query = "SELECT t FROM Tipoadentificacion t WHERE t.idTipoIdentificacion = :idTipoIdentificacion")
    , @NamedQuery(name = "Tipoadentificacion.findByTidNombre", query = "SELECT t FROM Tipoadentificacion t WHERE t.tidNombre = :tidNombre")
    , @NamedQuery(name = "Tipoadentificacion.findByTidCodigo", query = "SELECT t FROM Tipoadentificacion t WHERE t.tidCodigo = :tidCodigo")})
public class Tipoadentificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_identificacion")
    private Integer idTipoIdentificacion;
    @Column(name = "tid_nombre")
    private String tidNombre;
    @Column(name = "tid_codigo")
    private String tidCodigo;
    @OneToMany(mappedBy = "idTipoIdentificacion")
    private Collection<Cliente> clienteCollection;

    public Tipoadentificacion() {
    }

    public Tipoadentificacion(Integer idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public Integer getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setIdTipoIdentificacion(Integer idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public String getTidNombre() {
        return tidNombre;
    }

    public void setTidNombre(String tidNombre) {
        this.tidNombre = tidNombre;
    }

    public String getTidCodigo() {
        return tidCodigo;
    }

    public void setTidCodigo(String tidCodigo) {
        this.tidCodigo = tidCodigo;
    }

    @XmlTransient
    public Collection<Cliente> getClienteCollection() {
        return clienteCollection;
    }

    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        this.clienteCollection = clienteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoIdentificacion != null ? idTipoIdentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoadentificacion)) {
            return false;
        }
        Tipoadentificacion other = (Tipoadentificacion) object;
        if ((this.idTipoIdentificacion == null && other.idTipoIdentificacion != null) || (this.idTipoIdentificacion != null && !this.idTipoIdentificacion.equals(other.idTipoIdentificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Tipoadentificacion[ idTipoIdentificacion=" + idTipoIdentificacion + " ]";
    }
    
}
