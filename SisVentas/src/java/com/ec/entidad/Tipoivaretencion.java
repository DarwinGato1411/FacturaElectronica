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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "tipoivaretencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoivaretencion.findAll", query = "SELECT t FROM Tipoivaretencion t"),
    @NamedQuery(name = "Tipoivaretencion.findByIdTipoivaretencion", query = "SELECT t FROM Tipoivaretencion t WHERE t.idTipoivaretencion = :idTipoivaretencion"),
    @NamedQuery(name = "Tipoivaretencion.findByTipivaretDescripcion", query = "SELECT t FROM Tipoivaretencion t WHERE t.tipivaretDescripcion = :tipivaretDescripcion"),
    @NamedQuery(name = "Tipoivaretencion.findByTipivaretValor", query = "SELECT t FROM Tipoivaretencion t WHERE t.tipivaretValor = :tipivaretValor")})
public class Tipoivaretencion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipoivaretencion")
    private Integer idTipoivaretencion;
    @Size(max = 255)
    @Column(name = "tipivaret_descripcion")
    private String tipivaretDescripcion;
    @Size(max = 255)
    @Column(name = "tipivaret_valor")
    private String tipivaretValor;

    public Tipoivaretencion() {
    }

    public Tipoivaretencion(Integer idTipoivaretencion) {
        this.idTipoivaretencion = idTipoivaretencion;
    }

    public Integer getIdTipoivaretencion() {
        return idTipoivaretencion;
    }

    public void setIdTipoivaretencion(Integer idTipoivaretencion) {
        this.idTipoivaretencion = idTipoivaretencion;
    }

    public String getTipivaretDescripcion() {
        return tipivaretDescripcion;
    }

    public void setTipivaretDescripcion(String tipivaretDescripcion) {
        this.tipivaretDescripcion = tipivaretDescripcion;
    }

    public String getTipivaretValor() {
        return tipivaretValor;
    }

    public void setTipivaretValor(String tipivaretValor) {
        this.tipivaretValor = tipivaretValor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoivaretencion != null ? idTipoivaretencion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoivaretencion)) {
            return false;
        }
        Tipoivaretencion other = (Tipoivaretencion) object;
        if ((this.idTipoivaretencion == null && other.idTipoivaretencion != null) || (this.idTipoivaretencion != null && !this.idTipoivaretencion.equals(other.idTipoivaretencion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Tipoivaretencion[ idTipoivaretencion=" + idTipoivaretencion + " ]";
    }
    
}
