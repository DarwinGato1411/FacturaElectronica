/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Darwin
 */
public class CompraPromedio {

    private String prodNombre;
    private BigDecimal cantidad;
    private BigDecimal cantidadInicial;
    private BigDecimal precio;
    private BigDecimal precioActual;
    private Date fecha;

    public CompraPromedio() {
    }

    public CompraPromedio(String prodNombre, BigDecimal cantidad, BigDecimal precio, Date fecha) {
        this.prodNombre = prodNombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.fecha = fecha;
    }

    public CompraPromedio(String prodNombre, BigDecimal cantidad, BigDecimal precio, Date fecha, BigDecimal cantidadInicial) {
        this.prodNombre = prodNombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.fecha = fecha;
        this.cantidadInicial = cantidadInicial;
    }

    public CompraPromedio(String prodNombre, BigDecimal cantidad, BigDecimal precio) {
        this.prodNombre = prodNombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public CompraPromedio(String prodNombre, BigDecimal cantidad, BigDecimal precio, BigDecimal precioActual) {
        this.prodNombre = prodNombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.precioActual = precioActual;
    }

    public CompraPromedio(String prodNombre, BigDecimal cantidad, BigDecimal precio, BigDecimal precioActual, Date fecha) {
        this.prodNombre = prodNombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.precioActual = precioActual;
        this.fecha = fecha;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {

        return ArchivoUtils.redondearDecimales(precio, 3);
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(BigDecimal precioActual) {
        this.precioActual = precioActual;
    }

    public BigDecimal getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(BigDecimal cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

}
