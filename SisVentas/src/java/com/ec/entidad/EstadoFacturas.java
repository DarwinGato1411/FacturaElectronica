/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import com.ec.entidad.sri.CabeceraCompraSri;
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
 * @author gato
 */
@Entity
@Table(name = "estado_facturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoFacturas.findAll", query = "SELECT e FROM EstadoFacturas e")
    ,
    @NamedQuery(name = "EstadoFacturas.findByIdEstado", query = "SELECT e FROM EstadoFacturas e WHERE e.idEstado = :idEstado")
    ,
    @NamedQuery(name = "EstadoFacturas.findByEstNombre", query = "SELECT e FROM EstadoFacturas e WHERE e.estNombre = :estNombre")
    ,
    @NamedQuery(name = "EstadoFacturas.findByEstCodigo", query = "SELECT e FROM EstadoFacturas e WHERE e.estCodigo = :estCodigo")})
public class EstadoFacturas implements Serializable {

    @Column(name = "isprincipal")
    private Boolean isprincipal;
    @OneToMany(mappedBy = "estIdEstado")
    private Collection<CabeceraCompraSri> cabeceraCompraSriCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado")
    private Integer idEstado;
    @Size(max = 30)
    @Column(name = "est_nombre")
    private String estNombre;
    @Size(max = 5)
    @Column(name = "est_codigo")
    private String estCodigo;
    @OneToMany(mappedBy = "idEstado")
    private Collection<Factura> facturaCollection;
    @OneToMany(mappedBy = "idEstado")
    private Collection<CabeceraCompra> cabeceraCompraCollection;
    @OneToMany(mappedBy = "idEstado")
    private Collection<OrdenTrabajo> ordenTrabajoCollection;

    public EstadoFacturas() {
    }

    public EstadoFacturas(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstNombre() {
        return estNombre;
    }

    public void setEstNombre(String estNombre) {
        this.estNombre = estNombre;
    }

    public String getEstCodigo() {
        return estCodigo;
    }

    public void setEstCodigo(String estCodigo) {
        this.estCodigo = estCodigo;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @XmlTransient
    public Collection<CabeceraCompra> getCabeceraCompraCollection() {
        return cabeceraCompraCollection;
    }

    public void setCabeceraCompraCollection(Collection<CabeceraCompra> cabeceraCompraCollection) {
        this.cabeceraCompraCollection = cabeceraCompraCollection;
    }

    public Collection<OrdenTrabajo> getOrdenTrabajoCollection() {
        return ordenTrabajoCollection;
    }

    public void setOrdenTrabajoCollection(Collection<OrdenTrabajo> ordenTrabajoCollection) {
        this.ordenTrabajoCollection = ordenTrabajoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoFacturas)) {
            return false;
        }
        EstadoFacturas other = (EstadoFacturas) object;
        if ((this.idEstado == null && other.idEstado != null) || (this.idEstado != null && !this.idEstado.equals(other.idEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.EstadoFacturas[ idEstado=" + idEstado + " ]";
    }

    public Boolean getIsprincipal() {
        return isprincipal;
    }

    public void setIsprincipal(Boolean isprincipal) {
        this.isprincipal = isprincipal;
    }

    @XmlTransient
    public Collection<CabeceraCompraSri> getCabeceraCompraSriCollection() {
        return cabeceraCompraSriCollection;
    }

    public void setCabeceraCompraSriCollection(Collection<CabeceraCompraSri> cabeceraCompraSriCollection) {
        this.cabeceraCompraSriCollection = cabeceraCompraSriCollection;
    }

}
