/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "venta_ruta")
public class VentaRuta implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_venta_ruta")
    private BigDecimal idVentaRuta;
    @Column(name = "venta")
    private String venta;
    @Column(name = "cedula")
    private String cedula;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cantidad")
    private String cantidad;
    @Column(name = "direccion")
    private String direccion;

    @Column(name = "correo")
    private String correo;

    @Column(name = "celular")
    private String celular;

    @Column(name = "semana")
    private String semana;

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "codigo")
    private String codigo;
    @Column(name = "facturado")
    private String facturado;
    @Column(name = "transporte")
    private String transporte;

    public VentaRuta() {
    }

    public VentaRuta(String cedula, String nombre, String cantidad, String direccion, String correo, String celular, String semana, Date fecha, String codigo, String facturado) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.direccion = direccion;
        this.correo = correo;
        this.celular = celular;
        this.semana = semana;
        this.fecha = fecha;
        this.codigo = codigo;
        this.facturado = facturado;
    }

    public VentaRuta(BigDecimal idVentaRuta) {
        this.idVentaRuta = idVentaRuta;
    }

    public BigDecimal getIdVentaRuta() {
        return idVentaRuta;
    }

    public void setIdVentaRuta(BigDecimal idVentaRuta) {
        this.idVentaRuta = idVentaRuta;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFacturado() {
        return facturado;
    }

    public void setFacturado(String facturado) {
        this.facturado = facturado;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVentaRuta != null ? idVentaRuta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentaRuta)) {
            return false;
        }
        VentaRuta other = (VentaRuta) object;
        if ((this.idVentaRuta == null && other.idVentaRuta != null) || (this.idVentaRuta != null && !this.idVentaRuta.equals(other.idVentaRuta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.VentaRuta[ idVentaRuta=" + idVentaRuta + " ]";
    }

}
