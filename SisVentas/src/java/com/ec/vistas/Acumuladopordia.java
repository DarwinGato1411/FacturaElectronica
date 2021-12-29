/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vistas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigDecimal;
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
@Table(name = "acumuladopordia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acumuladopordia.findAll", query = "SELECT a FROM Acumuladopordia a")
    , @NamedQuery(name = "Acumuladopordia.findById", query = "SELECT a FROM Acumuladopordia a WHERE a.id = :id")
    , @NamedQuery(name = "Acumuladopordia.findByFacTotal", query = "SELECT a FROM Acumuladopordia a WHERE a.facTotal = :facTotal")
    , @NamedQuery(name = "Acumuladopordia.findByTotalntv", query = "SELECT a FROM Acumuladopordia a WHERE a.totalntv = :totalntv")
    , @NamedQuery(name = "Acumuladopordia.findByTotalacumulado", query = "SELECT a FROM Acumuladopordia a WHERE a.totalacumulado = :totalacumulado")
    , @NamedQuery(name = "Acumuladopordia.findByFacFecha", query = "SELECT a FROM Acumuladopordia a WHERE a.facFecha = :facFecha")})
public class Acumuladopordia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private BigDecimal id;
    @Column(name = "fac_total")
    private BigDecimal facTotal;
    @Column(name = "totalntv")
    private BigDecimal totalntv;
    @Column(name = "totalacumulado")
    private BigDecimal totalacumulado;
    @Column(name = "fac_fecha")
    @Temporal(TemporalType.DATE)
    private Date facFecha;

    public Acumuladopordia() {
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getFacTotal() {
        return facTotal;
    }

    public void setFacTotal(BigDecimal facTotal) {
        this.facTotal = facTotal;
    }

    public BigDecimal getTotalntv() {
        return totalntv;
    }

    public void setTotalntv(BigDecimal totalntv) {
        this.totalntv = totalntv;
    }

    public BigDecimal getTotalacumulado() {
        return totalacumulado;
    }

    public void setTotalacumulado(BigDecimal totalacumulado) {
        this.totalacumulado = totalacumulado;
    }

    public Date getFacFecha() {
        return facFecha;
    }

    public void setFacFecha(Date facFecha) {
        this.facFecha = facFecha;
    }

}
