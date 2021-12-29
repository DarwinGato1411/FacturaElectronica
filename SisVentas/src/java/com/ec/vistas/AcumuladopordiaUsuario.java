/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Darwin
 */
@Entity
@Table(name = "acumuladopordia_usuario")
public class AcumuladopordiaUsuario implements Serializable {

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
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name = "usu_login")
    private String usuLogin;

    public AcumuladopordiaUsuario() {
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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

}
