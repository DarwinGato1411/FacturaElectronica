/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vistas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Darwin Morocho
 */
@Entity
@Table(name = "cant_vent_productos")
public class CantVentProductos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_detalle")
    private Long idDetalle;
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @Column(name = "prod_nombre")
    private String prodNombre;
//    @Column(name = "mes")
//    private String mes;
//    @Column(name = "anio")
//    private String anio;

    @Column(name = "fac_fecha")
    @Temporal(TemporalType.DATE)
    private Date facFecha;
    @Column(name = "compra")
    private BigDecimal compra;
    @Column(name = "id_producto")
    private Integer idProducto;
    @Column(name = "diferencia")
    private BigDecimal diferencia;

    public CantVentProductos() {
    }

    public CantVentProductos(Long idDetalle, BigDecimal cantidad, String prodNombre, Date facFecha) {
        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.prodNombre = prodNombre;
        this.facFecha = facFecha;
    }
    public CantVentProductos(Long idDetalle, BigDecimal cantidad, String prodNombre, Date facFecha, BigDecimal compra, BigDecimal diferencia) {
        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.prodNombre = prodNombre;
        this.facFecha = facFecha;
        this.compra = compra;
        this.diferencia = diferencia;
    }

    public Long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }
//
//    public String getMes() {
//        return mes;
//    }
//
//    public void setMes(String mes) {
//        this.mes = mes;
//    }
//
//    public String getAnio() {
//        return anio;
//    }
//
//    public void setAnio(String anio) {
//        this.anio = anio;
//    }

    public Date getFacFecha() {
        return facFecha;
    }

    public void setFacFecha(Date facFecha) {
        this.facFecha = facFecha;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getCompra() {
        return compra;
    }

    public void setCompra(BigDecimal compra) {
        this.compra = compra;
    }

    public BigDecimal getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(BigDecimal diferencia) {
        this.diferencia = diferencia;
    }

}
