/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin Morocho
 */
@Entity
@Table(name = "vista_ingreso_egreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaIngresoEgreso.findAll", query = "SELECT v FROM VistaIngresoEgreso v"),
    @NamedQuery(name = "VistaIngresoEgreso.findByProdNombre", query = "SELECT v FROM VistaIngresoEgreso v WHERE v.prodNombre = :prodNombre"),
    @NamedQuery(name = "VistaIngresoEgreso.findByLikeProdNombre", query = "SELECT v FROM VistaIngresoEgreso v WHERE v.prodNombre like :prodNombre and v.stock <= :stock ORDER BY v.stock ASC"),
    @NamedQuery(name = "VistaIngresoEgreso.findByIngreso", query = "SELECT v FROM VistaIngresoEgreso v WHERE v.ingreso = :ingreso"),
    @NamedQuery(name = "VistaIngresoEgreso.findBySalida", query = "SELECT v FROM VistaIngresoEgreso v WHERE v.salida = :salida")})
public class VistaIngresoEgreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Size(max = 100)
    @Column(name = "prod_nombre")
    private String prodNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ingreso")
    private BigDecimal ingreso;
    @Column(name = "salida")
    private BigDecimal salida;
    @Column(name = "stock")
    private BigDecimal stock;

    public VistaIngresoEgreso() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public BigDecimal getIngreso() {
        return ingreso;
    }

    public void setIngreso(BigDecimal ingreso) {
        this.ingreso = ingreso;
    }

    public BigDecimal getSalida() {
        return salida;
    }

    public void setSalida(BigDecimal salida) {
        this.salida = salida;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }
    
    
}
