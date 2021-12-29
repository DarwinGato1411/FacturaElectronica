/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vistas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "rotacion_producto")
public class RotacionProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id_rotacion")
    @Id
    private BigInteger idRotacion;
    @Column(name = "id_producto")
    private Integer idProducto;
    @Column(name = "prod_nombre")
    private String prodNombre;
    @Column(name = "cantidad_venta")
    private BigDecimal cantidadVenta;
    @Column(name = "valor_venta_producto")
    private BigDecimal valorVentaProducto;
    @Column(name = "fac_fecha")
    @Temporal(TemporalType.DATE)
    private Date facFecha;

    @Transient
    private BigDecimal totalVenta = BigDecimal.ZERO;
    @Transient
    private BigDecimal porcentaje = BigDecimal.ZERO;

    public RotacionProducto() {
    }

    public RotacionProducto(String prodNombre, BigDecimal cantidadVenta, BigDecimal valorVentaProducto) {
        this.prodNombre = prodNombre;
        this.cantidadVenta = cantidadVenta;
        this.valorVentaProducto = valorVentaProducto;
    }

    public BigInteger getIdRotacion() {
        return idRotacion;
    }

    public void setIdRotacion(BigInteger idRotacion) {
        this.idRotacion = idRotacion;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public BigDecimal getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(BigDecimal cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public BigDecimal getValorVentaProducto() {
        return valorVentaProducto;
    }

    public void setValorVentaProducto(BigDecimal valorVentaProducto) {
        this.valorVentaProducto = valorVentaProducto;
    }

    public Date getFacFecha() {
        return facFecha;
    }

    public void setFacFecha(Date facFecha) {
        this.facFecha = facFecha;
    }

    public BigDecimal getTotalVenta() {
        totalVenta = totalVenta.add(valorVentaProducto);
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

}
