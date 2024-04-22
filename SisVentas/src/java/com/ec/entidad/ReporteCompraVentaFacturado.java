/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "reporte_compra_venta_facturado")
public class ReporteCompraVentaFacturado implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
   @Id
    @Column(name = "id_detalle")
    private Integer idDetalle;
    @Column(name = "det_cantidad")
    private BigDecimal detCantidad;
    @Size(max = 200)
    @Column(name = "prod_nombre")
    private String prodNombre;
    @Column(name = "precio_compra")
    private BigDecimal precioCompra;
    @Column(name = "precio_venta")
    private BigDecimal precioVenta;
    @Column(name = "fac_fecha")
    @Temporal(TemporalType.DATE)
    private Date facFecha;
    @Column(name = "total_compra")
    private BigDecimal totalCompra;
    @Column(name = "total_venta")
    private BigDecimal totalVenta;

    public ReporteCompraVentaFacturado(BigDecimal detCantidad, String prodNombre, BigDecimal precioCompra, BigDecimal precioVenta, Date facFecha, BigDecimal totalCompra, BigDecimal totalVenta) {
        this.detCantidad = detCantidad;
        this.prodNombre = prodNombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.facFecha = facFecha;
        this.totalCompra = totalCompra;
        this.totalVenta = totalVenta;
    }

    
    
    public ReporteCompraVentaFacturado() {
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }
    
    

    public BigDecimal getDetCantidad() {
        return detCantidad==null?BigDecimal.ZERO:detCantidad;
    }

    public void setDetCantidad(BigDecimal detCantidad) {
        this.detCantidad = detCantidad;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra==null?BigDecimal.ZERO:precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta==null?BigDecimal.ZERO:precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Date getFacFecha() {
        return facFecha;
    }

    public void setFacFecha(Date facFecha) {
        this.facFecha = facFecha;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra==null?BigDecimal.ZERO:totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta==null?BigDecimal.ZERO:totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

}
