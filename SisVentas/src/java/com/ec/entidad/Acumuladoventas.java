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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "acumuladoventas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acumuladoventas.findAll", query = "SELECT a FROM Acumuladoventas a"),
    @NamedQuery(name = "Acumuladoventas.findById", query = "SELECT a FROM Acumuladoventas a WHERE a.id = :id"),
    @NamedQuery(name = "Acumuladoventas.findByNumCompro", query = "SELECT a FROM Acumuladoventas a WHERE a.numCompro = :numCompro"),
    @NamedQuery(name = "Acumuladoventas.findByCliCedula", query = "SELECT a FROM Acumuladoventas a WHERE a.cliCedula = :cliCedula"),
    @NamedQuery(name = "Acumuladoventas.findByTidCodigo", query = "SELECT a FROM Acumuladoventas a WHERE a.tidCodigo = :tidCodigo"),
    @NamedQuery(name = "Acumuladoventas.findByFacTotalBaseGravaba", query = "SELECT a FROM Acumuladoventas a WHERE a.facTotalBaseGravaba = :facTotalBaseGravaba"),
    @NamedQuery(name = "Acumuladoventas.findByFacIva", query = "SELECT a FROM Acumuladoventas a WHERE a.facIva = :facIva"),
    @NamedQuery(name = "Acumuladoventas.findByForCodigo", query = "SELECT a FROM Acumuladoventas a WHERE a.forCodigo = :forCodigo"),
    @NamedQuery(name = "Acumuladoventas.findByFacFecha", query = "SELECT a FROM Acumuladoventas a WHERE a.facFecha = :facFecha")})
public class Acumuladoventas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "num_compro")
    private Integer numCompro;
    @Size(max = 2147483647)
    @Column(name = "cli_cedula")
    private String cliCedula;
    @Size(max = 2147483647)
    @Column(name = "tid_codigo")
    private String tidCodigo;
    @Column(name = "fac_total_base_gravaba")
    private BigDecimal facTotalBaseGravaba;
    @Column(name = "fac_iva")
    private BigDecimal facIva;
    @Size(max = 2147483647)
    @Column(name = "for_codigo")
    private String forCodigo;
    @Column(name = "fac_fecha")
    @Temporal(TemporalType.DATE)
    private Date facFecha;

    public Acumuladoventas() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumCompro() {
        return numCompro;
    }

    public void setNumCompro(Integer numCompro) {
        this.numCompro = numCompro;
    }

    public String getCliCedula() {
        return cliCedula;
    }

    public void setCliCedula(String cliCedula) {
        this.cliCedula = cliCedula;
    }

    public String getTidCodigo() {
        return tidCodigo;
    }

    public void setTidCodigo(String tidCodigo) {
        this.tidCodigo = tidCodigo;
    }

    public BigDecimal getFacTotalBaseGravaba() {
        return facTotalBaseGravaba;
    }

    public void setFacTotalBaseGravaba(BigDecimal facTotalBaseGravaba) {
        this.facTotalBaseGravaba = facTotalBaseGravaba;
    }

    public BigDecimal getFacIva() {
        return facIva;
    }

    public void setFacIva(BigDecimal facIva) {
        this.facIva = facIva;
    }

    public String getForCodigo() {
        return forCodigo;
    }

    public void setForCodigo(String forCodigo) {
        this.forCodigo = forCodigo;
    }

    public Date getFacFecha() {
        return facFecha;
    }

    public void setFacFecha(Date facFecha) {
        this.facFecha = facFecha;
    }
    
}
