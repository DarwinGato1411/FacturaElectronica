/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ec.entidad;

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
@Table(name = "ingresoskardex")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingresoskardex.findAll", query = "SELECT i FROM Ingresoskardex i"),
    @NamedQuery(name = "Ingresoskardex.findByIngresos", query = "SELECT i FROM Ingresoskardex i WHERE i.ingresos = :ingresos"),
    @NamedQuery(name = "Ingresoskardex.findByIdKardex", query = "SELECT i FROM Ingresoskardex i WHERE i.idKardex = :idKardex")})
public class Ingresoskardex implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id    
    @Column(name = "id_kardex")
    private Integer idKardex;
    @Column(name = "ingresos")
    private BigDecimal ingresos;


    public Ingresoskardex() {
    }

    public BigDecimal getIngresos() {
        return ingresos;
    }

    public void setIngresos(BigDecimal ingresos) {
        this.ingresos = ingresos;
    }

    public Integer getIdKardex() {
        return idKardex;
    }

    public void setIdKardex(Integer idKardex) {
        this.idKardex = idKardex;
    }
    
}
