/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vistas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "costo_promedio_compra")
public class CostoPromedioCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id_producto")
    @Id
    private Integer idProducto;
    @Column(name = "cantidadprod")
    private BigInteger cantidadprod;
    @Column(name = "iprod_subtotal")
    private BigInteger iprodSubtotal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "promedio")
    private BigDecimal promedio;

    public CostoPromedioCompra() {
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public BigInteger getCantidadprod() {
        return cantidadprod;
    }

    public void setCantidadprod(BigInteger cantidadprod) {
        this.cantidadprod = cantidadprod;
    }

    public BigInteger getIprodSubtotal() {
        return iprodSubtotal;
    }

    public void setIprodSubtotal(BigInteger iprodSubtotal) {
        this.iprodSubtotal = iprodSubtotal;
    }

    public BigDecimal getPromedio() {
        return promedio;
    }

    public void setPromedio(BigDecimal promedio) {
        this.promedio = promedio;
    }
    
}
