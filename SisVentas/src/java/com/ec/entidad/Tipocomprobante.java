/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "tipocomprobante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipocomprobante.findAll", query = "SELECT t FROM Tipocomprobante t")
    , @NamedQuery(name = "Tipocomprobante.findByIdTipoComprobante", query = "SELECT t FROM Tipocomprobante t WHERE t.idTipoComprobante = :idTipoComprobante")
    , @NamedQuery(name = "Tipocomprobante.findByTipNombre", query = "SELECT t FROM Tipocomprobante t WHERE t.tipNombre = :tipNombre")
    , @NamedQuery(name = "Tipocomprobante.findByTipCodigo", query = "SELECT t FROM Tipocomprobante t WHERE t.tipCodigo = :tipCodigo")})
public class Tipocomprobante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_comprobante")
    private Integer idTipoComprobante;
    @Column(name = "tip_nombre")
    private String tipNombre;
    @Column(name = "tip_codigo")
    private String tipCodigo;

    public Tipocomprobante() {
    }

    public Tipocomprobante(Integer idTipoComprobante) {
        this.idTipoComprobante = idTipoComprobante;
    }

    public Integer getIdTipoComprobante() {
        return idTipoComprobante;
    }

    public void setIdTipoComprobante(Integer idTipoComprobante) {
        this.idTipoComprobante = idTipoComprobante;
    }

    public String getTipNombre() {
        return tipNombre;
    }

    public void setTipNombre(String tipNombre) {
        this.tipNombre = tipNombre;
    }

    public String getTipCodigo() {
        return tipCodigo;
    }

    public void setTipCodigo(String tipCodigo) {
        this.tipCodigo = tipCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoComprobante != null ? idTipoComprobante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipocomprobante)) {
            return false;
        }
        Tipocomprobante other = (Tipocomprobante) object;
        if ((this.idTipoComprobante == null && other.idTipoComprobante != null) || (this.idTipoComprobante != null && !this.idTipoComprobante.equals(other.idTipoComprobante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Tipocomprobante[ idTipoComprobante=" + idTipoComprobante + " ]";
    }
    
}
