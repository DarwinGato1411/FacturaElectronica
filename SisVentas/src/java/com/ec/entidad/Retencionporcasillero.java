/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "retencionporcasillero")
public class Retencionporcasillero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private BigInteger id;
    @Size(max = 6)
    @Column(name = "tire_codigo")
    private String tireCodigo;
    @Size(max = 2147483647)
    @Column(name = "tipo_retencion")
    private String tipoRetencion;
    @Column(name = "numero_comprobante")
    private BigInteger numeroComprobante;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor_retenido")
    private Double valorRetenido;
    @Column(name = "cab_fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date cabFechaEmision;

    public Retencionporcasillero() {
    }

    public Retencionporcasillero(BigInteger id, String tireCodigo, String tipoRetencion, BigInteger numeroComprobante, Double valorRetenido, Date cabFechaEmision) {
        this.id = id;
        this.tireCodigo = tireCodigo;
        this.tipoRetencion = tipoRetencion;
        this.numeroComprobante = numeroComprobante;
        this.valorRetenido = valorRetenido;
        this.cabFechaEmision = cabFechaEmision;
    }
    

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTireCodigo() {
        return tireCodigo;
    }

    public void setTireCodigo(String tireCodigo) {
        this.tireCodigo = tireCodigo;
    }

    public String getTipoRetencion() {
        return tipoRetencion;
    }

    public void setTipoRetencion(String tipoRetencion) {
        this.tipoRetencion = tipoRetencion;
    }

    public BigInteger getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(BigInteger numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public Double getValorRetenido() {
        return valorRetenido;
    }

    public void setValorRetenido(Double valorRetenido) {
        this.valorRetenido = valorRetenido;
    }

    public Date getCabFechaEmision() {
        return cabFechaEmision;
    }

    public void setCabFechaEmision(Date cabFechaEmision) {
        this.cabFechaEmision = cabFechaEmision;
    }
    
}
