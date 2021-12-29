/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "costopromediocompraview")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Costopromediocompraview.findAll", query = "SELECT c FROM Costopromediocompraview c")})
public class Costopromediocompraview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    private BigInteger id;
    @Column(name = "prod_nombre")
    private String prodNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @Column(name = "comprapromedio")
    private BigDecimal comprapromedio;
    @Column(name = "prod_cantidad_inicial")
    private BigDecimal prodCantidadInicial;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Costopromediocompraview() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
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

    public BigDecimal getComprapromedio() {
        return comprapromedio;
    }

    public void setComprapromedio(BigDecimal comprapromedio) {
        this.comprapromedio = comprapromedio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getProdCantidadInicial() {
        return prodCantidadInicial;
    }

    public void setProdCantidadInicial(BigDecimal prodCantidadInicial) {
        this.prodCantidadInicial = prodCantidadInicial;
    }

}
