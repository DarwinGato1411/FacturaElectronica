/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author gato
 */
@Embeddable
public class ProductoProveedorPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_proveedor")
    private int idProveedor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_producto")
    private int idProducto;

    public ProductoProveedorPK() {
    }

    public ProductoProveedorPK(int idProveedor, int idProducto) {
        this.idProveedor = idProveedor;
        this.idProducto = idProducto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProveedor;
        hash += (int) idProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoProveedorPK)) {
            return false;
        }
        ProductoProveedorPK other = (ProductoProveedorPK) object;
        if (this.idProveedor != other.idProveedor) {
            return false;
        }
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.ProductoProveedorPK[ idProveedor=" + idProveedor + ", idProducto=" + idProducto + " ]";
    }
    
}
