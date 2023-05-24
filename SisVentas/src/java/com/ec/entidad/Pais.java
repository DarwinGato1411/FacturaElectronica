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

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "pais")
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p")})
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pais")
    private Integer idPais;
    @Column(name = "pa_codigo")
    private String paCodigo;
    @Column(name = "pa_nombre")
    private String paNombre;
    @Column(name = "pa_origen")
    private Boolean paOrigen;
    @Column(name = "pa_destino")
    private Boolean paDestino;
//    @OneToMany(mappedBy = "idPaisDestino")
//    private Collection<Factura> facturaCollection;
//    @OneToMany(mappedBy = "idPaisOrigen")
//    private Collection<Factura> facturaCollection1;
//    @OneToMany(mappedBy = "idPaisAdquisicion")
//    private Collection<Factura> facturaCollection2;

    public Pais() {
    }

    public Pais(Integer idPais) {
        this.idPais = idPais;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    public String getPaCodigo() {
        return paCodigo;
    }

    public void setPaCodigo(String paCodigo) {
        this.paCodigo = paCodigo;
    }

    public String getPaNombre() {
        return paNombre;
    }

    public void setPaNombre(String paNombre) {
        this.paNombre = paNombre;
    }

    public Boolean getPaOrigen() {
        return paOrigen;
    }

    public void setPaOrigen(Boolean paOrigen) {
        this.paOrigen = paOrigen;
    }

    public Boolean getPaDestino() {
        return paDestino;
    }

    public void setPaDestino(Boolean paDestino) {
        this.paDestino = paDestino;
    }

//    public Collection<Factura> getFacturaCollection() {
//        return facturaCollection;
//    }
//
//    public void setFacturaCollection(Collection<Factura> facturaCollection) {
//        this.facturaCollection = facturaCollection;
//    }
//
//    public Collection<Factura> getFacturaCollection1() {
//        return facturaCollection1;
//    }
//
//    public void setFacturaCollection1(Collection<Factura> facturaCollection1) {
//        this.facturaCollection1 = facturaCollection1;
//    }
//
//    public Collection<Factura> getFacturaCollection2() {
//        return facturaCollection2;
//    }
//
//    public void setFacturaCollection2(Collection<Factura> facturaCollection2) {
//        this.facturaCollection2 = facturaCollection2;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPais != null ? idPais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.idPais == null && other.idPais != null) || (this.idPais != null && !this.idPais.equals(other.idPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Pais[ idPais=" + idPais + " ]";
    }
    
}
