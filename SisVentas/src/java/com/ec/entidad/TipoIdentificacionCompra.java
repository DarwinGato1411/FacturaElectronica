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
@Table(name = "tipo_identificacion_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoIdentificacionCompra.findAll", query = "SELECT t FROM TipoIdentificacionCompra t")
    ,
    @NamedQuery(name = "TipoIdentificacionCompra.findByIdTipoIdentificacionCompra", query = "SELECT t FROM TipoIdentificacionCompra t WHERE t.idTipoIdentificacionCompra = :idTipoIdentificacionCompra")
    ,
    @NamedQuery(name = "TipoIdentificacionCompra.findByTicNombre", query = "SELECT t FROM TipoIdentificacionCompra t WHERE t.ticNombre = :ticNombre")
    ,
    @NamedQuery(name = "TipoIdentificacionCompra.findByTicCodigo", query = "SELECT t FROM TipoIdentificacionCompra t WHERE t.ticCodigo = :ticCodigo")})
public class TipoIdentificacionCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_identificacion_compra")
    private Integer idTipoIdentificacionCompra;
    @Size(max = 100)
    @Column(name = "tic_nombre")
    private String ticNombre;
    @Size(max = 10)
    @Column(name = "tic_codigo")
    private String ticCodigo;
    @OneToMany(mappedBy = "idTipoIdentificacionCompra")
    private Collection<Proveedores> proveedoresCollection;

    public TipoIdentificacionCompra() {
    }

    public TipoIdentificacionCompra(Integer idTipoIdentificacionCompra) {
        this.idTipoIdentificacionCompra = idTipoIdentificacionCompra;
    }

    public Integer getIdTipoIdentificacionCompra() {
        return idTipoIdentificacionCompra;
    }

    public void setIdTipoIdentificacionCompra(Integer idTipoIdentificacionCompra) {
        this.idTipoIdentificacionCompra = idTipoIdentificacionCompra;
    }

    public String getTicNombre() {
        return ticNombre;
    }

    public void setTicNombre(String ticNombre) {
        this.ticNombre = ticNombre;
    }

    public String getTicCodigo() {
        return ticCodigo;
    }

    public void setTicCodigo(String ticCodigo) {
        this.ticCodigo = ticCodigo;
    }

    @XmlTransient
    public Collection<Proveedores> getProveedoresCollection() {
        return proveedoresCollection;
    }

    public void setProveedoresCollection(Collection<Proveedores> proveedoresCollection) {
        this.proveedoresCollection = proveedoresCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoIdentificacionCompra != null ? idTipoIdentificacionCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoIdentificacionCompra)) {
            return false;
        }
        TipoIdentificacionCompra other = (TipoIdentificacionCompra) object;
        if ((this.idTipoIdentificacionCompra == null && other.idTipoIdentificacionCompra != null) || (this.idTipoIdentificacionCompra != null && !this.idTipoIdentificacionCompra.equals(other.idTipoIdentificacionCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.TipoIdentificacionCompra[ idTipoIdentificacionCompra=" + idTipoIdentificacionCompra + " ]";
    }

}
