/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "producto_proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoProveedor.findAll", query = "SELECT p FROM ProductoProveedor p"),
    @NamedQuery(name = "ProductoProveedor.findByIdProveedor", query = "SELECT p FROM ProductoProveedor p WHERE p.productoProveedorPK.idProveedor = :idProveedor"),
    @NamedQuery(name = "ProductoProveedor.findByIdProducto", query = "SELECT p FROM ProductoProveedor p WHERE p.productoProveedorPK.idProducto = :idProducto"),
    @NamedQuery(name = "ProductoProveedor.findByIdProductoIdProveedor", query = "SELECT p FROM ProductoProveedor p WHERE p.productoProveedorPK.idProducto = :idProducto AND p.productoProveedorPK.idProveedor = :idProveedor"),
    @NamedQuery(name = "ProductoProveedor.findByIdProducto", query = "SELECT p FROM ProductoProveedor p WHERE p.productoProveedorPK.idProducto = :idProducto"),
    @NamedQuery(name = "ProductoProveedor.findlikeProducto", query = "SELECT p FROM ProductoProveedor p WHERE p.producto.prodNombre LIKE :prodNombre"),
    @NamedQuery(name = "ProductoProveedor.findByProdProvCosto", query = "SELECT p FROM ProductoProveedor p WHERE p.prodProvCosto = :prodProvCosto"),
    @NamedQuery(name = "ProductoProveedor.findByProdProvFechaReg", query = "SELECT p FROM ProductoProveedor p WHERE p.prodProvFechaReg = :prodProvFechaReg")})
public class ProductoProveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoProveedorPK productoProveedorPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prod_prov_costo")
    private BigDecimal prodProvCosto;
    @Column(name = "prod_prov_fecha_reg")
    @Temporal(TemporalType.DATE)
    private Date prodProvFechaReg;
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proveedores proveedores;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
//    @OneToMany(mappedBy = "productoProveedor")
//    private Collection<IngresoProducto> ingresoProductoCollection;

    public ProductoProveedor() {
    }

    public ProductoProveedor(ProductoProveedorPK productoProveedorPK) {
        this.productoProveedorPK = productoProveedorPK;
    }

    public ProductoProveedor(int idProveedor, int idProducto) {
        this.productoProveedorPK = new ProductoProveedorPK(idProveedor, idProducto);
    }

    public ProductoProveedorPK getProductoProveedorPK() {
        return productoProveedorPK;
    }

    public void setProductoProveedorPK(ProductoProveedorPK productoProveedorPK) {
        this.productoProveedorPK = productoProveedorPK;
    }

    public BigDecimal getProdProvCosto() {
        return prodProvCosto;
    }

    public void setProdProvCosto(BigDecimal prodProvCosto) {
        this.prodProvCosto = prodProvCosto;
    }

    public Date getProdProvFechaReg() {
        return prodProvFechaReg;
    }

    public void setProdProvFechaReg(Date prodProvFechaReg) {
        this.prodProvFechaReg = prodProvFechaReg;
    }

    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

//    @XmlTransient
//    public Collection<IngresoProducto> getIngresoProductoCollection() {
//        return ingresoProductoCollection;
//    }
//
//    public void setIngresoProductoCollection(Collection<IngresoProducto> ingresoProductoCollection) {
//        this.ingresoProductoCollection = ingresoProductoCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoProveedorPK != null ? productoProveedorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoProveedor)) {
            return false;
        }
        ProductoProveedor other = (ProductoProveedor) object;
        if ((this.productoProveedorPK == null && other.productoProveedorPK != null) || (this.productoProveedorPK != null && !this.productoProveedorPK.equals(other.productoProveedorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.ProductoProveedor[ productoProveedorPK=" + productoProveedorPK + " ]";
    }
    
}
