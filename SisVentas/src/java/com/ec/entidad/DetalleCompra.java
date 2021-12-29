/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

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
 * @author gato
 */
@Entity
@Table(name = "detalle_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCompra.findAll", query = "SELECT i FROM DetalleCompra i")
    ,
    @NamedQuery(name = "DetalleCompra.findByIdIngresoProd", query = "SELECT i FROM DetalleCompra i WHERE i.idIngresoProd = :idIngresoProd")
    ,
    @NamedQuery(name = "DetalleCompra.findByIprodCantidad", query = "SELECT i FROM DetalleCompra i WHERE i.iprodCantidad = :iprodCantidad")})
public class DetalleCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ingreso_prod")
    private Integer idIngresoProd;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "iprod_cantidad")
    private BigDecimal iprodCantidad;
    @Size(max = 100)
    @Column(name = "iprod_descripcion")
    private String detDescripcion;
    @Column(name = "iprod_subtotal")
    private BigDecimal iprodSubtotal;
    @Column(name = "iprod_total")
    private BigDecimal iprodTotal;
    @Column(name = "iprod_codigo_provee")
    private BigDecimal iprodCodigoProvee;
    @Column(name = "det_valor_inicial")
    private BigDecimal detValorInicial;
    @Column(name = "det_factor")
    private BigDecimal detFactor;
    @JoinColumn(name = "id_cabecera", referencedColumnName = "id_cabecera")
    @ManyToOne
    private CabeceraCompra idCabecera;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;

    public DetalleCompra() {
    }

    public DetalleCompra(BigDecimal iprodCantidad,
            String detDescripcion, BigDecimal iprodSubtotal,
            BigDecimal iprodTotal, CabeceraCompra idCabecera, Producto idProducto) {
        this.iprodCantidad = iprodCantidad;
        this.detDescripcion = detDescripcion;
        this.iprodSubtotal = iprodSubtotal;
        this.iprodTotal = iprodTotal;
        this.idCabecera = idCabecera;
        this.idProducto = idProducto;
    }

    public DetalleCompra(Integer idIngresoProd) {
        this.idIngresoProd = idIngresoProd;
    }

    public Integer getIdIngresoProd() {
        return idIngresoProd;
    }

    public void setIdIngresoProd(Integer idIngresoProd) {
        this.idIngresoProd = idIngresoProd;
    }

    public BigDecimal getIprodCantidad() {
        return iprodCantidad;
    }

    public void setIprodCantidad(BigDecimal iprodCantidad) {
        this.iprodCantidad = iprodCantidad;
    }

    public CabeceraCompra getIdCabecera() {
        return idCabecera;
    }

    public void setIdCabecera(CabeceraCompra idCabecera) {
        this.idCabecera = idCabecera;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public String getDetDescripcion() {
        return detDescripcion;
    }

    public void setDetDescripcion(String detDescripcion) {
        this.detDescripcion = detDescripcion;
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

    public BigDecimal getIprodCodigoProvee() {
        return iprodCodigoProvee;
    }

    public void setIprodCodigoProvee(BigDecimal iprodCodigoProvee) {
        this.iprodCodigoProvee = iprodCodigoProvee;
    }

    public BigDecimal getDetValorInicial() {
        return detValorInicial;
    }

    public void setDetValorInicial(BigDecimal detValorInicial) {
        this.detValorInicial = detValorInicial;
    }

    public BigDecimal getDetFactor() {
        return detFactor;
    }

    public void setDetFactor(BigDecimal detFactor) {
        this.detFactor = detFactor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIngresoProd != null ? idIngresoProd.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.DetalleCompra[ idIngresoProd=" + idIngresoProd + " ]";
    }
}
