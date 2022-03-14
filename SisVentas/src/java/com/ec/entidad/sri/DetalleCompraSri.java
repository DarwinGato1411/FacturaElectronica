/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad.sri;

import com.ec.entidad.Producto;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "detalle_compra_sri")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCompraSri.findAll", query = "SELECT d FROM DetalleCompraSri d")
    , @NamedQuery(name = "DetalleCompraSri.findByIdIngresoProdSri", query = "SELECT d FROM DetalleCompraSri d WHERE d.idIngresoProdSri = :idIngresoProdSri")
    , @NamedQuery(name = "DetalleCompraSri.findByIprodCantidad", query = "SELECT d FROM DetalleCompraSri d WHERE d.iprodCantidad = :iprodCantidad")
    , @NamedQuery(name = "DetalleCompraSri.findByIprodDescripcion", query = "SELECT d FROM DetalleCompraSri d WHERE d.iprodDescripcion = :iprodDescripcion")
    , @NamedQuery(name = "DetalleCompraSri.findByIprodSubtotal", query = "SELECT d FROM DetalleCompraSri d WHERE d.iprodSubtotal = :iprodSubtotal")
    , @NamedQuery(name = "DetalleCompraSri.findByIprodTotal", query = "SELECT d FROM DetalleCompraSri d WHERE d.iprodTotal = :iprodTotal")
    , @NamedQuery(name = "DetalleCompraSri.findByIprodCodigoProvee", query = "SELECT d FROM DetalleCompraSri d WHERE d.iprodCodigoProvee = :iprodCodigoProvee")})
public class DetalleCompraSri implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ingreso_prod_sri")
    private Integer idIngresoProdSri;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "iprod_cantidad")
    private BigDecimal iprodCantidad;

    @Column(name = "iprod_descripcion")
    private String iprodDescripcion;
    @Column(name = "iprod_subtotal")
    private BigDecimal iprodSubtotal;
    @Column(name = "iprod_total")
    private BigDecimal iprodTotal;
    @Size(max = 50)
    @Column(name = "iprod_codigo_provee")
    private String iprodCodigoProvee;
    @JoinColumn(name = "id_cabecera_sri", referencedColumnName = "id_cabecera_sri")
    @ManyToOne
    private CabeceraCompraSri idCabeceraSri;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;

    public DetalleCompraSri() {
    }

    public DetalleCompraSri(Integer idIngresoProdSri) {
        this.idIngresoProdSri = idIngresoProdSri;
    }

    public Integer getIdIngresoProdSri() {
        return idIngresoProdSri;
    }

    public void setIdIngresoProdSri(Integer idIngresoProdSri) {
        this.idIngresoProdSri = idIngresoProdSri;
    }

    public BigDecimal getIprodCantidad() {
        return iprodCantidad;
    }

    public void setIprodCantidad(BigDecimal iprodCantidad) {
        this.iprodCantidad = iprodCantidad;
    }

    public String getIprodDescripcion() {
        return iprodDescripcion;
    }

    public void setIprodDescripcion(String iprodDescripcion) {
        this.iprodDescripcion = iprodDescripcion;
    }

    public BigDecimal getIprodSubtotal() {
        return iprodSubtotal;
    }

    public void setIprodSubtotal(BigDecimal iprodSubtotal) {
        this.iprodSubtotal = iprodSubtotal;
    }

    public BigDecimal getIprodTotal() {
        return iprodTotal;
    }

    public void setIprodTotal(BigDecimal iprodTotal) {
        this.iprodTotal = iprodTotal;
    }

    public String getIprodCodigoProvee() {
        return iprodCodigoProvee;
    }

    public void setIprodCodigoProvee(String iprodCodigoProvee) {
        this.iprodCodigoProvee = iprodCodigoProvee;
    }

    public CabeceraCompraSri getIdCabeceraSri() {
        return idCabeceraSri;
    }

    public void setIdCabeceraSri(CabeceraCompraSri idCabeceraSri) {
        this.idCabeceraSri = idCabeceraSri;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIngresoProdSri != null ? idIngresoProdSri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCompraSri)) {
            return false;
        }
        DetalleCompraSri other = (DetalleCompraSri) object;
        if ((this.idIngresoProdSri == null && other.idIngresoProdSri != null) || (this.idIngresoProdSri != null && !this.idIngresoProdSri.equals(other.idIngresoProdSri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.sri.DetalleCompraSri[ idIngresoProdSri=" + idIngresoProdSri + " ]";
    }
    
}
