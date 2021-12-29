/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vistas;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "sri_catastro")
public class SriCatastro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    private BigInteger id;
    @Size(max = 13)
    @Column(name = "mic_ruc")
    private String micRuc;
    @Size(max = 2147483647)
    @Column(name = "razon_social")
    private String razonSocial;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sigla")
    private String sigla;

    public SriCatastro() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getMicRuc() {
        return micRuc;
    }

    public void setMicRuc(String micRuc) {
        this.micRuc = micRuc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

}
