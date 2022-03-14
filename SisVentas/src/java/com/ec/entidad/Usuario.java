/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import com.ec.entidad.sri.CabeceraCompraSri;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    ,
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario")
    ,
    @NamedQuery(name = "Usuario.findByUsuNombre", query = "SELECT u FROM Usuario u WHERE u.usuNombre = :usuNombre")
    ,
    @NamedQuery(name = "Usuario.findLikeNombre", query = "SELECT u FROM Usuario u WHERE u.usuNombre like :usuNombre")
    ,
    @NamedQuery(name = "Usuario.findByUsuLogin", query = "SELECT u FROM Usuario u WHERE u.usuLogin = :usuLogin")
    ,
    @NamedQuery(name = "Usuario.findByUsuPassword", query = "SELECT u FROM Usuario u WHERE u.usuPassword = :usuPassword")
    ,
    @NamedQuery(name = "Usuario.findByUsuCorreo", query = "SELECT u FROM Usuario u WHERE u.usuCorreo = :usuCorreo")
    ,
    @NamedQuery(name = "Usuario.findByUsuNivel", query = "SELECT u FROM Usuario u WHERE u.usuNivel = :usuNivel")})
public class Usuario implements Serializable {

    @Column(name = "usu_foto")
    private Character usuFoto;
    @Size(max = 20)
    @Column(name = "usu_whatsapp")
    private String usuWhatsapp;
    @Size(max = 100)
    @Column(name = "usu_pagina")
    private String usuPagina;
    @Size(max = 100)
    @Column(name = "usu_facebook")
    private String usuFacebook;
    @Column(name = "usu_numero_fotos")
    private Integer usuNumeroFotos;
    @Size(max = 150)
    @Column(name = "usu_fotografia")
    private String usuFotografia;
    @Size(max = 150)
    @Column(name = "usu_actividad")
    private String usuActividad;
    @Size(max = 150)
    @Column(name = "usu_servlet")
    private String usuServlet;
    @Column(name = "usu_activa_movil")
    private Boolean usuActivaMovil;
    @Column(name = "usu_fecha_reg_mov")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFechaRegMov;
    @Column(name = "usu_fecha_caduca")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuFechaCaduca;
    @Size(max = 300)
    @Column(name = "usu_descripcion_negocio")
    private String usuDescripcionNegocio;
    @Size(max = 100)
    @Column(name = "usu_long_negocio")
    private String usuLongNegocio;
    @Size(max = 100)
    @Column(name = "usu_lat_negocio")
    private String usuLatNegocio;
    @Column(name = "usu_es_drive")
    private Boolean usuEsDrive;
    @Column(name = "usu_drive_activo")
    private Boolean usuDriveActivo;
    @Size(max = 10)
    @Column(name = "usu_drive_placa")
    private String usuDrivePlaca;
    @Size(max = 20)
    @Column(name = "usu_drive_color")
    private String usuDriveColor;
    @Column(name = "usu_drive_disponible")
    private Boolean usuDriveDisponible;
    @JoinColumn(name = "id_parroquia", referencedColumnName = "id_parroquia")
    @ManyToOne
    private Parroquia idParroquia;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Size(max = 100)
    @Column(name = "usu_nombre")
    private String usuNombre;
    @Size(max = 100)
    @Column(name = "usu_login")
    private String usuLogin;
    @Size(max = 100)
    @Column(name = "usu_password")
    private String usuPassword;
    @Size(max = 100)
    @Column(name = "usu_correo")
    private String usuCorreo;
    @Column(name = "usu_nivel")
    private Integer usuNivel;
    @Size(max = 100)
    @Column(name = "usu_tipo_usuario")
    private String usuTipoUsuario;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<Factura> facturaCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<CabeceraCompra> cabeceraCompraCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<CierreCaja> cierreCajaCollection;
    @OneToMany(mappedBy = "usuIdUsuario")
    private Collection<CabeceraCompraSri> cabeceraCompraSriCollection;
    @OneToMany(mappedBy = "idUsuario")
    private Collection<OrdenTrabajo> ordenTrabajoCollection;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getUsuPassword() {
        return usuPassword;
    }

    public void setUsuPassword(String usuPassword) {
        this.usuPassword = usuPassword;
    }

    public String getUsuCorreo() {
        return usuCorreo;
    }

    public void setUsuCorreo(String usuCorreo) {
        this.usuCorreo = usuCorreo;
    }

    public Integer getUsuNivel() {
        return usuNivel;
    }

    public void setUsuNivel(Integer usuNivel) {
        this.usuNivel = usuNivel;
    }

//    public byte[] getUsuFoto() {
//        return usuFoto;
//    }
//
//    public void setUsuFoto(byte[] usuFoto) {
//        this.usuFoto = usuFoto;
//    }

    public String getUsuTipoUsuario() {
        return usuTipoUsuario;
    }

    public void setUsuTipoUsuario(String usuTipoUsuario) {
        this.usuTipoUsuario = usuTipoUsuario;
    }

    public Collection<CabeceraCompraSri> getCabeceraCompraSriCollection() {
        return cabeceraCompraSriCollection;
    }

    public void setCabeceraCompraSriCollection(Collection<CabeceraCompraSri> cabeceraCompraSriCollection) {
        this.cabeceraCompraSriCollection = cabeceraCompraSriCollection;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @XmlTransient
    public Collection<CabeceraCompra> getCabeceraCompraCollection() {
        return cabeceraCompraCollection;
    }

    public void setCabeceraCompraCollection(Collection<CabeceraCompra> cabeceraCompraCollection) {
        this.cabeceraCompraCollection = cabeceraCompraCollection;
    }

    @XmlTransient
    public Collection<CierreCaja> getCierreCajaCollection() {
        return cierreCajaCollection;
    }

    public void setCierreCajaCollection(Collection<CierreCaja> cierreCajaCollection) {
        this.cierreCajaCollection = cierreCajaCollection;
    }

    public Collection<OrdenTrabajo> getOrdenTrabajoCollection() {
        return ordenTrabajoCollection;
    }

    public void setOrdenTrabajoCollection(Collection<OrdenTrabajo> ordenTrabajoCollection) {
        this.ordenTrabajoCollection = ordenTrabajoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Usuario[ idUsuario=" + idUsuario + " ]";
    }

    public Character getUsuFoto() {
        return usuFoto;
    }

    public void setUsuFoto(Character usuFoto) {
        this.usuFoto = usuFoto;
    }

    public String getUsuWhatsapp() {
        return usuWhatsapp;
    }

    public void setUsuWhatsapp(String usuWhatsapp) {
        this.usuWhatsapp = usuWhatsapp;
    }

    public String getUsuPagina() {
        return usuPagina;
    }

    public void setUsuPagina(String usuPagina) {
        this.usuPagina = usuPagina;
    }

    public String getUsuFacebook() {
        return usuFacebook;
    }

    public void setUsuFacebook(String usuFacebook) {
        this.usuFacebook = usuFacebook;
    }

    public Integer getUsuNumeroFotos() {
        return usuNumeroFotos;
    }

    public void setUsuNumeroFotos(Integer usuNumeroFotos) {
        this.usuNumeroFotos = usuNumeroFotos;
    }

    public String getUsuFotografia() {
        return usuFotografia;
    }

    public void setUsuFotografia(String usuFotografia) {
        this.usuFotografia = usuFotografia;
    }

    public String getUsuActividad() {
        return usuActividad;
    }

    public void setUsuActividad(String usuActividad) {
        this.usuActividad = usuActividad;
    }

    public String getUsuServlet() {
        return usuServlet;
    }

    public void setUsuServlet(String usuServlet) {
        this.usuServlet = usuServlet;
    }

    public Boolean getUsuActivaMovil() {
        return usuActivaMovil;
    }

    public void setUsuActivaMovil(Boolean usuActivaMovil) {
        this.usuActivaMovil = usuActivaMovil;
    }

    public Date getUsuFechaRegMov() {
        return usuFechaRegMov;
    }

    public void setUsuFechaRegMov(Date usuFechaRegMov) {
        this.usuFechaRegMov = usuFechaRegMov;
    }

    public Date getUsuFechaCaduca() {
        return usuFechaCaduca;
    }

    public void setUsuFechaCaduca(Date usuFechaCaduca) {
        this.usuFechaCaduca = usuFechaCaduca;
    }

    public String getUsuDescripcionNegocio() {
        return usuDescripcionNegocio;
    }

    public void setUsuDescripcionNegocio(String usuDescripcionNegocio) {
        this.usuDescripcionNegocio = usuDescripcionNegocio;
    }

    public String getUsuLongNegocio() {
        return usuLongNegocio;
    }

    public void setUsuLongNegocio(String usuLongNegocio) {
        this.usuLongNegocio = usuLongNegocio;
    }

    public String getUsuLatNegocio() {
        return usuLatNegocio;
    }

    public void setUsuLatNegocio(String usuLatNegocio) {
        this.usuLatNegocio = usuLatNegocio;
    }

    public Boolean getUsuEsDrive() {
        return usuEsDrive;
    }

    public void setUsuEsDrive(Boolean usuEsDrive) {
        this.usuEsDrive = usuEsDrive;
    }

    public Boolean getUsuDriveActivo() {
        return usuDriveActivo;
    }

    public void setUsuDriveActivo(Boolean usuDriveActivo) {
        this.usuDriveActivo = usuDriveActivo;
    }

    public String getUsuDrivePlaca() {
        return usuDrivePlaca;
    }

    public void setUsuDrivePlaca(String usuDrivePlaca) {
        this.usuDrivePlaca = usuDrivePlaca;
    }

    public String getUsuDriveColor() {
        return usuDriveColor;
    }

    public void setUsuDriveColor(String usuDriveColor) {
        this.usuDriveColor = usuDriveColor;
    }

    public Boolean getUsuDriveDisponible() {
        return usuDriveDisponible;
    }

    public void setUsuDriveDisponible(Boolean usuDriveDisponible) {
        this.usuDriveDisponible = usuDriveDisponible;
    }

    public Parroquia getIdParroquia() {
        return idParroquia;
    }

    public void setIdParroquia(Parroquia idParroquia) {
        this.idParroquia = idParroquia;
    }
}
