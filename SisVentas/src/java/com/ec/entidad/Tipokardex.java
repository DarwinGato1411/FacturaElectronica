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
@Table(name = "tipokardex")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipokardex.findAll", query = "SELECT t FROM Tipokardex t")
    , @NamedQuery(name = "Tipokardex.findByIdTipokardex", query = "SELECT t FROM Tipokardex t WHERE t.idTipokardex = :idTipokardex")
    , @NamedQuery(name = "Tipokardex.findByTipkSigla", query = "SELECT t FROM Tipokardex t WHERE t.tipkSigla = :tipkSigla")
    , @NamedQuery(name = "Tipokardex.findByTipkDescripcion", query = "SELECT t FROM Tipokardex t WHERE t.tipkDescripcion = :tipkDescripcion")
    , @NamedQuery(name = "Tipokardex.findByTidEstado", query = "SELECT t FROM Tipokardex t WHERE t.tidEstado = :tidEstado")})
public class Tipokardex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipokardex")
    private Integer idTipokardex;
    @Column(name = "tipk_descripcion")
    private String tipkDescripcion;
    @Column(name = "tipk_sigla")
    private String tipkSigla;
    @Column(name = "tid_estado")
    private Boolean tidEstado;
    @OneToMany(mappedBy = "idTipokardex")
    private Collection<DetalleKardex> detalleKardexCollection;

    public Tipokardex() {
    }

    public Tipokardex(Integer idTipokardex) {
        this.idTipokardex = idTipokardex;
    }

    public Integer getIdTipokardex() {
        return idTipokardex;
    }

    public void setIdTipokardex(Integer idTipokardex) {
        this.idTipokardex = idTipokardex;
    }

    public String getTipkDescripcion() {
        return tipkDescripcion;
    }

    public void setTipkDescripcion(String tipkDescripcion) {
        this.tipkDescripcion = tipkDescripcion;
    }

    public Boolean getTidEstado() {
        return tidEstado;
    }

    public void setTidEstado(Boolean tidEstado) {
        this.tidEstado = tidEstado;
    }

    public String getTipkSigla() {
        return tipkSigla;
    }

    public void setTipkSigla(String tipkSigla) {
        this.tipkSigla = tipkSigla;
    }

    @XmlTransient
    public Collection<DetalleKardex> getDetalleKardexCollection() {
        return detalleKardexCollection;
    }

    public void setDetalleKardexCollection(Collection<DetalleKardex> detalleKardexCollection) {
        this.detalleKardexCollection = detalleKardexCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipokardex != null ? idTipokardex.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipokardex)) {
            return false;
        }
        Tipokardex other = (Tipokardex) object;
        if ((this.idTipokardex == null && other.idTipokardex != null) || (this.idTipokardex != null && !this.idTipokardex.equals(other.idTipokardex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Tipokardex[ idTipokardex=" + idTipokardex + " ]";
    }

}
