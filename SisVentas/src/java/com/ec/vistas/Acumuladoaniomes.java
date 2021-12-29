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
@Table(name = "acumuladoaniomes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acumuladoaniomes.findAll", query = "SELECT a FROM Acumuladoaniomes a")
    , @NamedQuery(name = "Acumuladoaniomes.findByIdVista", query = "SELECT a FROM Acumuladoaniomes a WHERE a.idVista = :idVista")
    , @NamedQuery(name = "Acumuladoaniomes.findByFacTotal", query = "SELECT a FROM Acumuladoaniomes a WHERE a.facTotal = :facTotal")
    , @NamedQuery(name = "Acumuladoaniomes.findByTotalntv", query = "SELECT a FROM Acumuladoaniomes a WHERE a.totalntv = :totalntv")
    , @NamedQuery(name = "Acumuladoaniomes.findByTotalacumulado", query = "SELECT a FROM Acumuladoaniomes a WHERE a.totalacumulado = :totalacumulado")
    , @NamedQuery(name = "Acumuladoaniomes.findByAnio", query = "SELECT a FROM Acumuladoaniomes a WHERE a.anio = :anio")
    , @NamedQuery(name = "Acumuladoaniomes.findByMes", query = "SELECT a FROM Acumuladoaniomes a WHERE a.mes = :mes")})
public class Acumuladoaniomes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_vista")
    private BigInteger idVista;
    @Column(name = "fac_total")
    private BigDecimal facTotal;
    @Column(name = "totalntv")
    private BigDecimal totalntv;
    @Column(name = "totalacumulado")
    private BigDecimal totalacumulado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "anio")
    private Double anio;
    @Column(name = "mes")
    private Double mes;

    public Acumuladoaniomes() {
    }

    public BigInteger getIdVista() {
        return idVista;
    }

    public void setIdVista(BigInteger idVista) {
        this.idVista = idVista;
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

    public Double getAnio() {
        return anio;
    }

    public void setAnio(Double anio) {
        this.anio = anio;
    }

    public Double getMes() {
        return mes;
    }

    public void setMes(Double mes) {
        this.mes = mes;
    }
    
}
