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
@Table(name = "forma_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormaPago.findAll", query = "SELECT f FROM FormaPago f ORDER BY f.forNombre ASC")
    ,@NamedQuery(name = "FormaPago.finPrincipal", query = "SELECT f FROM FormaPago f WHERE f.isprincipal=TRUE  ORDER BY f.forNombre ASC")
    , @NamedQuery(name = "FormaPago.findByIdFormaPago", query = "SELECT f FROM FormaPago f WHERE f.idFormaPago = :idFormaPago")
    , @NamedQuery(name = "FormaPago.findByForNombre", query = "SELECT f FROM FormaPago f WHERE f.forNombre = :forNombre")
    , @NamedQuery(name = "FormaPago.findByForCodigo", query = "SELECT f FROM FormaPago f WHERE f.forCodigo = :forCodigo")})
public class FormaPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_forma_pago")
    private Integer idFormaPago;
    @Column(name = "for_nombre")
    private String forNombre;
    @Column(name = "for_codigo")
    private String forCodigo;
    @Column(name = "isprincipal")
    private Boolean isprincipal;
    @Column(name = "unidad_tiempo")
    private String unidadTiempo;
    @Column(name = "plazo")
    private String plazo;
    @OneToMany(mappedBy = "idFormaPago")
    private Collection<Factura> facturaCollection;

    public FormaPago() {
    }

    public FormaPago(Integer idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public Integer getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(Integer idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public String getForNombre() {
        return forNombre;
    }

    public void setForNombre(String forNombre) {
        this.forNombre = forNombre;
    }

    public String getForCodigo() {
        return forCodigo;
    }

    public void setForCodigo(String forCodigo) {
        this.forCodigo = forCodigo;
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

    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormaPago != null ? idFormaPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormaPago)) {
            return false;
        }
        FormaPago other = (FormaPago) object;
        if ((this.idFormaPago == null && other.idFormaPago != null) || (this.idFormaPago != null && !this.idFormaPago.equals(other.idFormaPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.FormaPago[ idFormaPago=" + idFormaPago + " ]";
    }

}
