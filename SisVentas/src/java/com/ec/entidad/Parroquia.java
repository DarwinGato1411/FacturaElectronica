/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "parroquia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parroquia.findAll", query = "SELECT p FROM Parroquia p")
    , @NamedQuery(name = "Parroquia.findByIdParroquia", query = "SELECT p FROM Parroquia p WHERE p.idParroquia = :idParroquia")
    , @NamedQuery(name = "Parroquia.findByParrNumero", query = "SELECT p FROM Parroquia p WHERE p.parrNumero = :parrNumero")
    , @NamedQuery(name = "Parroquia.findByParrNombre", query = "SELECT p FROM Parroquia p WHERE p.parrNombre = :parrNombre")
    , @NamedQuery(name = "Parroquia.findByParrEstado", query = "SELECT p FROM Parroquia p WHERE p.parrEstado = :parrEstado")
    , @NamedQuery(name = "Parroquia.findByParrZona", query = "SELECT p FROM Parroquia p WHERE p.parrZona = :parrZona")
    , @NamedQuery(name = "Parroquia.findByParrPrecio", query = "SELECT p FROM Parroquia p WHERE p.parrPrecio = :parrPrecio")
    , @NamedQuery(name = "Parroquia.findByParrKiloAdicional", query = "SELECT p FROM Parroquia p WHERE p.parrKiloAdicional = :parrKiloAdicional")})
public class Parroquia implements Serializable {

    @OneToMany(mappedBy = "idParroquia")
    private Collection<Usuario> usuarioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_parroquia")
    private Integer idParroquia;
    @Column(name = "parr_numero")
    private Integer parrNumero;
    @Size(max = 100)
    @Column(name = "parr_nombre")
    private String parrNombre;
    @Column(name = "parr_estado")
    private Boolean parrEstado;
    @Size(max = 100)
    @Column(name = "parr_zona")
    private String parrZona;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "parr_precio")
    private BigDecimal parrPrecio;
    @Column(name = "parr_kilo_adicional")
    private BigDecimal parrKiloAdicional;
    @JoinColumn(name = "id_canton", referencedColumnName = "id_canton")
    @ManyToOne
    private Canton idCanton;
  

    public Parroquia() {
    }

    public Parroquia(Integer idParroquia) {
        this.idParroquia = idParroquia;
    }

    public Integer getIdParroquia() {
        return idParroquia;
    }

    public void setIdParroquia(Integer idParroquia) {
        this.idParroquia = idParroquia;
    }

    public Integer getParrNumero() {
        return parrNumero;
    }

    public void setParrNumero(Integer parrNumero) {
        this.parrNumero = parrNumero;
    }

    public String getParrNombre() {
        return parrNombre;
    }

    public void setParrNombre(String parrNombre) {
        this.parrNombre = parrNombre;
    }

    public Boolean getParrEstado() {
        return parrEstado;
    }

    public void setParrEstado(Boolean parrEstado) {
        this.parrEstado = parrEstado;
    }

    public String getParrZona() {
        return parrZona;
    }

    public void setParrZona(String parrZona) {
        this.parrZona = parrZona;
    }

    public BigDecimal getParrPrecio() {
        return parrPrecio;
    }

    public void setParrPrecio(BigDecimal parrPrecio) {
        this.parrPrecio = parrPrecio;
    }

    public BigDecimal getParrKiloAdicional() {
        return parrKiloAdicional;
    }

    public void setParrKiloAdicional(BigDecimal parrKiloAdicional) {
        this.parrKiloAdicional = parrKiloAdicional;
    }

    public Canton getIdCanton() {
        return idCanton;
    }

    public void setIdCanton(Canton idCanton) {
        this.idCanton = idCanton;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParroquia != null ? idParroquia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parroquia)) {
            return false;
        }
        Parroquia other = (Parroquia) object;
        if ((this.idParroquia == null && other.idParroquia != null) || (this.idParroquia != null && !this.idParroquia.equals(other.idParroquia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Parroquia[ idParroquia=" + idParroquia + " ]";
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }


}
