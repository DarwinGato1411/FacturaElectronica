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
@Table(name = "referencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Referencia.findAll", query = "SELECT r FROM Referencia r")
    , @NamedQuery(name = "Referencia.findByIdReferencia", query = "SELECT r FROM Referencia r WHERE r.idReferencia = :idReferencia")
    , @NamedQuery(name = "Referencia.findByRefNombre", query = "SELECT r FROM Referencia r WHERE r.refNombre = :refNombre")
    , @NamedQuery(name = "Referencia.findByRefCodigo", query = "SELECT r FROM Referencia r WHERE r.refCodigo = :refCodigo")
    , @NamedQuery(name = "Referencia.findByIsprincipal", query = "SELECT r FROM Referencia r WHERE r.isprincipal = :isprincipal")})
public class Referencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_referencia")
    private Integer idReferencia;
    @Column(name = "ref_nombre")
    private String refNombre;
    @Column(name = "ref_codigo")
    private String refCodigo;
    @Column(name = "isprincipal")
    private Boolean isprincipal;
    @OneToMany(mappedBy = "idReferencia")
    private Collection<Factura> facturaCollection;

    public Referencia() {
    }

    public Referencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getRefNombre() {
        return refNombre;
    }

    public void setRefNombre(String refNombre) {
        this.refNombre = refNombre;
    }

    public String getRefCodigo() {
        return refCodigo;
    }

    public void setRefCodigo(String refCodigo) {
        this.refCodigo = refCodigo;
    }

    public Boolean getIsprincipal() {
        return isprincipal;
    }

    public void setIsprincipal(Boolean isprincipal) {
        this.isprincipal = isprincipal;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReferencia != null ? idReferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Referencia)) {
            return false;
        }
        Referencia other = (Referencia) object;
        if ((this.idReferencia == null && other.idReferencia != null) || (this.idReferencia != null && !this.idReferencia.equals(other.idReferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Referencia[ idReferencia=" + idReferencia + " ]";
    }
    
}
