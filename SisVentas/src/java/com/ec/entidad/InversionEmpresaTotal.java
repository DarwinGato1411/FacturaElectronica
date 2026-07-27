/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Darwin Morocho
 */
@Entity
@Table(name = "inversion_empresa_total")
public class InversionEmpresaTotal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_kardex")
    private Long idKardex;
    @Column(name = "prod_nombre")
    private String prodNombre;
    @Column(name = "kar_total")
    private BigDecimal karTotal;
    @Column(name = "precio_compra")
    private BigDecimal precioCompra;
    @Column(name = "total_compra")
    private BigDecimal totalCompra;
    @Column(name = "precio_venta")
    private BigDecimal precioVenta;
    @Column(name = "total_venta")
    private BigDecimal totalVenta;

    public InversionEmpresaTotal() {
    }

    public Long getIdKardex() {
        return idKardex;
    }

    public void setIdKardex(Long idKardex) {
        this.idKardex = idKardex;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public BigDecimal getKarTotal() {
        return karTotal;
    }

    public void setKarTotal(BigDecimal karTotal) {
        this.karTotal = karTotal;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }
    
    
}
