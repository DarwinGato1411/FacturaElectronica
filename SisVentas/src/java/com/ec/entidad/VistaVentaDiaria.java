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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin Morocho
 */
@Entity
@Table(name = "vista_venta_diaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaVentaDiaria.findAll", query = "SELECT v FROM VistaVentaDiaria v"),
    @NamedQuery(name = "VistaVentaDiaria.findById", query = "SELECT v FROM VistaVentaDiaria v WHERE v.id = :id"),
    @NamedQuery(name = "VistaVentaDiaria.findByVenta", query = "SELECT v FROM VistaVentaDiaria v WHERE v.venta = :venta"),
    @NamedQuery(name = "VistaVentaDiaria.findByTotal", query = "SELECT v FROM VistaVentaDiaria v WHERE v.total = :total"),
    @NamedQuery(name = "VistaVentaDiaria.findBetweenFecha", query = "SELECT v FROM VistaVentaDiaria v WHERE v.fecha  BETWEEN :inicio AND :fin"),
    @NamedQuery(name = "VistaVentaDiaria.findByFecha", query = "SELECT v FROM VistaVentaDiaria v WHERE v.fecha = :fecha")})
public class VistaVentaDiaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 12)
    @Column(name = "venta")
    private String venta;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public VistaVentaDiaria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
