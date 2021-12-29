/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import com.ec.entidad.sri.CabeceraCompraSri;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "proveedores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p")
    ,
    @NamedQuery(name = "Proveedores.findByIdProveedor", query = "SELECT p FROM Proveedores p WHERE p.idProveedor = :idProveedor")
    ,
    @NamedQuery(name = "Proveedores.findByProvCedula", query = "SELECT p FROM Proveedores p WHERE p.provCedula = :provCedula")
    ,
    @NamedQuery(name = "Proveedores.findByLikeProvCedula", query = "SELECT p FROM Proveedores p WHERE p.provCedula LIKE :provCedula")
    ,
    @NamedQuery(name = "Proveedores.findByProvNombre", query = "SELECT p FROM Proveedores p WHERE p.provNombre = :provNombre")
    ,
    @NamedQuery(name = "Proveedores.findLikeProvNombre", query = "SELECT p FROM Proveedores p WHERE p.provNombre like :provNombre")
    ,
    @NamedQuery(name = "Proveedores.findByProvDireccion", query = "SELECT p FROM Proveedores p WHERE p.provDireccion = :provDireccion")
    ,
    @NamedQuery(name = "Proveedores.findByProvTelefono", query = "SELECT p FROM Proveedores p WHERE p.provTelefono = :provTelefono")
    ,
    @NamedQuery(name = "Proveedores.findByProvMovil", query = "SELECT p FROM Proveedores p WHERE p.provMovil = :provMovil")
    ,
    @NamedQuery(name = "Proveedores.findByProvCorreo", query = "SELECT p FROM Proveedores p WHERE p.provCorreo = :provCorreo")
    ,
    @NamedQuery(name = "Proveedores.findByProvPagina", query = "SELECT p FROM Proveedores p WHERE p.provPagina = :provPagina")
    ,
    @NamedQuery(name = "Proveedores.findByProvBanco", query = "SELECT p FROM Proveedores p WHERE p.provBanco = :provBanco")
    ,
    @NamedQuery(name = "Proveedores.findByProvTipoCuenta", query = "SELECT p FROM Proveedores p WHERE p.provTipoCuenta = :provTipoCuenta")
    ,
    @NamedQuery(name = "Proveedores.findByProvNumeroCuenta", query = "SELECT p FROM Proveedores p WHERE p.provNumeroCuenta = :provNumeroCuenta")})
public class Proveedores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_proveedor")
    private Integer idProveedor;
    @Size(max = 100)
    @Column(name = "prov_cedula")
    private String provCedula;
    @Size(max = 100)
    @Column(name = "prov_nombre")
    private String provNombre;
    @Size(max = 100)
    @Column(name = "prov_nom_comercial")
    private String provNomComercial;
    @Size(max = 150)
    @Column(name = "prov_direccion")
    private String provDireccion;
    @Size(max = 20)
    @Column(name = "prov_telefono")
    private String provTelefono;
    @Size(max = 20)
    @Column(name = "prov_movil")
    private String provMovil;
    @Size(max = 100)
    @Column(name = "prov_correo")
    private String provCorreo;
    @Size(max = 100)
    @Column(name = "prov_pagina")
    private String provPagina;
    @Size(max = 100)
    @Column(name = "prov_banco")
    private String provBanco;
    @Size(max = 100)
    @Column(name = "prov_tipo_cuenta")
    private String provTipoCuenta;
    @Size(max = 50)
    @Column(name = "prov_numero_cuenta")
    private String provNumeroCuenta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedores")
    private Collection<ProductoProveedor> productoProveedorCollection;
    @JoinColumn(name = "id_tipo_identificacion_compra", referencedColumnName = "id_tipo_identificacion_compra")
    @ManyToOne
    private TipoIdentificacionCompra idTipoIdentificacionCompra;

    @OneToMany(mappedBy = "idProveedor")
    private Collection<CabeceraCompra> cabeceraCompraCollection;

    @OneToMany(mappedBy = "idProveedor")
    private Collection<CabeceraCompraSri> cabeceraCompraSriCollection;

    public Proveedores() {
    }

    public Proveedores(String provCedula) {
        this.provCedula = provCedula;
    }

    public Proveedores(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getProvCedula() {
        return provCedula;
    }

    public void setProvCedula(String provCedula) {
        this.provCedula = provCedula;
    }

    public String getProvNombre() {
        return provNombre;
    }

    public void setProvNombre(String provNombre) {
        this.provNombre = provNombre;
    }

    public String getProvDireccion() {
        return provDireccion;
    }

    public void setProvDireccion(String provDireccion) {
        this.provDireccion = provDireccion;
    }

    public String getProvTelefono() {
        return provTelefono;
    }

    public void setProvTelefono(String provTelefono) {
        this.provTelefono = provTelefono;
    }

    public String getProvMovil() {
        return provMovil;
    }

    public void setProvMovil(String provMovil) {
        this.provMovil = provMovil;
    }

    public String getProvCorreo() {
        return provCorreo;
    }

    public void setProvCorreo(String provCorreo) {
        this.provCorreo = provCorreo;
    }

    public String getProvPagina() {
        return provPagina;
    }

    public void setProvPagina(String provPagina) {
        this.provPagina = provPagina;
    }

    public String getProvBanco() {
        return provBanco;
    }

    public void setProvBanco(String provBanco) {
        this.provBanco = provBanco;
    }

    public String getProvTipoCuenta() {
        return provTipoCuenta;
    }

    public void setProvTipoCuenta(String provTipoCuenta) {
        this.provTipoCuenta = provTipoCuenta;
    }

    public String getProvNumeroCuenta() {
        return provNumeroCuenta;
    }

    public void setProvNumeroCuenta(String provNumeroCuenta) {
        this.provNumeroCuenta = provNumeroCuenta;
    }

    @XmlTransient
    public Collection<ProductoProveedor> getProductoProveedorCollection() {
        return productoProveedorCollection;
    }

    public void setProductoProveedorCollection(Collection<ProductoProveedor> productoProveedorCollection) {
        this.productoProveedorCollection = productoProveedorCollection;
    }

    public TipoIdentificacionCompra getIdTipoIdentificacionCompra() {
        return idTipoIdentificacionCompra;
    }

    public void setIdTipoIdentificacionCompra(TipoIdentificacionCompra idTipoIdentificacionCompra) {
        this.idTipoIdentificacionCompra = idTipoIdentificacionCompra;
    }

    public String getProvNomComercial() {
        return provNomComercial;
    }

    public void setProvNomComercial(String provNomComercial) {
        this.provNomComercial = provNomComercial;
    }

    @XmlTransient
    public Collection<CabeceraCompra> getCabeceraCompraCollection() {
        return cabeceraCompraCollection;
    }

    public void setCabeceraCompraCollection(Collection<CabeceraCompra> cabeceraCompraCollection) {
        this.cabeceraCompraCollection = cabeceraCompraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.idProveedor == null && other.idProveedor != null) || (this.idProveedor != null && !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Proveedores[ idProveedor=" + idProveedor + " ]";
    }

    @XmlTransient
    public Collection<CabeceraCompraSri> getCabeceraCompraSriCollection() {
        return cabeceraCompraSriCollection;
    }

    public void setCabeceraCompraSriCollection(Collection<CabeceraCompraSri> cabeceraCompraSriCollection) {
        this.cabeceraCompraSriCollection = cabeceraCompraSriCollection;
    }
}
