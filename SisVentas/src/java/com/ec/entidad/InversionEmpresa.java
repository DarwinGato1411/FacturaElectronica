/*
 * To change this template, choose Tools | Templates
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

/**
 *
 * @author Darwin Morocho
 */
@Entity
@Table(name = "inversion_empresa")
public class InversionEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_detalle_kardex")
    private Long idDetalleKardex;
    @Column(name = "prod_nombre")
    private String prodNombre;
    @Column(name = "detk_fechakardex")
    @Temporal(TemporalType.DATE)
    private Date detkFechakardex;
    @Column(name = "detk_cantidad")
    private BigDecimal detkCantidad;
    @Column(name = "pord_costo_venta_ref")
    private BigDecimal pordCostoVentaRef;
    @Column(name = "compra")
    private BigDecimal compra;

    public InversionEmpresa() {
    }

    public InversionEmpresa(Long idDetalleKardex, String prodNombre, Date detkFechakardex, BigDecimal detkCantidad, BigDecimal pordCostoVentaRef, BigDecimal compra) {
        this.idDetalleKardex = idDetalleKardex;
        this.prodNombre = prodNombre;
        this.detkFechakardex = detkFechakardex;
        this.detkCantidad = detkCantidad;
        this.pordCostoVentaRef = pordCostoVentaRef;
        this.compra = compra;
    }

    public Long getIdDetalleKardex() {
        return idDetalleKardex;
    }

    public void setIdDetalleKardex(Long idDetalleKardex) {
        this.idDetalleKardex = idDetalleKardex;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public Date getDetkFechakardex() {
        return detkFechakardex;
    }

    public void setDetkFechakardex(Date detkFechakardex) {
        this.detkFechakardex = detkFechakardex;
    }

    public BigDecimal getDetkCantidad() {
        return detkCantidad;
    }

    public void setDetkCantidad(BigDecimal detkCantidad) {
        this.detkCantidad = detkCantidad;
    }

    public BigDecimal getPordCostoVentaRef() {
        return pordCostoVentaRef;
    }

    public void setPordCostoVentaRef(BigDecimal pordCostoVentaRef) {
        this.pordCostoVentaRef = pordCostoVentaRef;
    }

    public BigDecimal getCompra() {
        return compra;
    }

    public void setCompra(BigDecimal compra) {
        this.compra = compra;
    }

}
