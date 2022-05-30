/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "tipoambiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoambiente.findAll", query = "SELECT t FROM Tipoambiente t")
    ,@NamedQuery(name = "Tipoambiente.findAllActivo", query = "SELECT t FROM Tipoambiente t WHERE t.amEstado=TRUE")
    , @NamedQuery(name = "Tipoambiente.findByCodTipoambiente", query = "SELECT t FROM Tipoambiente t WHERE t.codTipoambiente = :codTipoambiente")
    , @NamedQuery(name = "Tipoambiente.findByAmCodigo", query = "SELECT t FROM Tipoambiente t WHERE t.amCodigo = :amCodigo")
    , @NamedQuery(name = "Tipoambiente.findByAmDescripcion", query = "SELECT t FROM Tipoambiente t WHERE t.amDescripcion = :amDescripcion")
    , @NamedQuery(name = "Tipoambiente.findByAmEstado", query = "SELECT t FROM Tipoambiente t WHERE t.amEstado = :amEstado")
    , @NamedQuery(name = "Tipoambiente.findByAmIdEmpresa", query = "SELECT t FROM Tipoambiente t WHERE t.amIdEmpresa = :amIdEmpresa")
    , @NamedQuery(name = "Tipoambiente.findByAmUsuariosri", query = "SELECT t FROM Tipoambiente t WHERE t.amUsuariosri = :amUsuariosri")
    , @NamedQuery(name = "Tipoambiente.findByAmUrlsri", query = "SELECT t FROM Tipoambiente t WHERE t.amUrlsri = :amUrlsri")
    , @NamedQuery(name = "Tipoambiente.findByAmDirReportes", query = "SELECT t FROM Tipoambiente t WHERE t.amDirReportes = :amDirReportes")
    , @NamedQuery(name = "Tipoambiente.findByAmDirFirma", query = "SELECT t FROM Tipoambiente t WHERE t.amDirFirma = :amDirFirma")
    , @NamedQuery(name = "Tipoambiente.findByAmDirBaseArchivos", query = "SELECT t FROM Tipoambiente t WHERE t.amDirBaseArchivos = :amDirBaseArchivos")
    , @NamedQuery(name = "Tipoambiente.findByAmDirXml", query = "SELECT t FROM Tipoambiente t WHERE t.amDirXml = :amDirXml")
    , @NamedQuery(name = "Tipoambiente.findByAmFirmados", query = "SELECT t FROM Tipoambiente t WHERE t.amFirmados = :amFirmados")
    , @NamedQuery(name = "Tipoambiente.findByAmTrasmitidos", query = "SELECT t FROM Tipoambiente t WHERE t.amTrasmitidos = :amTrasmitidos")
    , @NamedQuery(name = "Tipoambiente.findByAmDevueltos", query = "SELECT t FROM Tipoambiente t WHERE t.amDevueltos = :amDevueltos")
    , @NamedQuery(name = "Tipoambiente.findByAmAutorizados", query = "SELECT t FROM Tipoambiente t WHERE t.amAutorizados = :amAutorizados")
    , @NamedQuery(name = "Tipoambiente.findByAmNoAutorizados", query = "SELECT t FROM Tipoambiente t WHERE t.amNoAutorizados = :amNoAutorizados")
    , @NamedQuery(name = "Tipoambiente.findByAmClaveAccesoSri", query = "SELECT t FROM Tipoambiente t WHERE t.amClaveAccesoSri = :amClaveAccesoSri")})
public class Tipoambiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tipoambiente")
    private Integer codTipoambiente;
    @Size(max = 255)
    @Column(name = "am_codigo")
    private String amCodigo;
    @Size(max = 255)
    @Column(name = "am_descripcion")
    private String amDescripcion;
    @Column(name = "am_estado")
    private Boolean amEstado;

    @Column(name = "am_id_empresa")
    private Integer amIdEmpresa;
    @Size(max = 2147483647)
    @Column(name = "am_usuariosri")
    private String amUsuariosri;
    @Size(max = 2147483647)
    @Column(name = "am_urlsri")
    private String amUrlsri;
    @Size(max = 2147483647)
    @Column(name = "am_dir_reportes")
    private String amDirReportes;
    @Size(max = 2147483647)
    @Column(name = "am_dir_firma")
    private String amDirFirma;
    @Size(max = 2147483647)
    @Column(name = "am_dir_base_archivos")
    private String amDirBaseArchivos;
    @Size(max = 2147483647)
    @Column(name = "am_dir_xml")
    private String amDirXml;
    @Size(max = 2147483647)
    @Column(name = "am_firmados")
    private String amFirmados;
    @Size(max = 2147483647)
    @Column(name = "am_trasmitidos")
    private String amTrasmitidos;
    @Size(max = 2147483647)
    @Column(name = "am_devueltos")
    private String amDevueltos;
    @Size(max = 2147483647)
    @Column(name = "am_autorizados")
    private String amAutorizados;
    @Column(name = "am_enviocliente")
    private String amEnviocliente;
    @Size(max = 2147483647)
    @Column(name = "am_no_autorizados")
    private String amNoAutorizados;
    @Size(max = 2147483647)
    @Column(name = "am_clave_acceso_sri")
    private String amClaveAccesoSri;
    @Size(max = 2147483647)
    @Column(name = "am_generados")
    private String amGenerados;
    @Column(name = "am_razon_social")
    private String amRazonSocial;
    @Column(name = "am_nombre_comercial")
    private String amNombreComercial;
    @Column(name = "am_ruc")
    private String amRuc;
    @Column(name = "am_tipo_emision")
    private String amTipoEmision;
    @Column(name = "am_direccion_matriz")
    private String amDireccionMatriz;
    @Column(name = "llevar_contabilidad")
    private String llevarContabilidad;
    @Column(name = "am_estab")
    private String amEstab;
    @Column(name = "am_ptoemi")
    private String amPtoemi;
    @Column(name = "am_nro_contribuyente")
    private String amNroContribuyente;
    @Column(name = "am_unidad_disco")
    private String amUnidadDisco;
    @Column(name = "am_folder_firma")
    private String amFolderFirma;
    @Column(name = "am_dir_ats")
    private String amDirAts;
    @Column(name = "am_ciudad")
    private String amCiudad;
    @Column(name = "am_telefono")
    private String amTelefono;
    @Column(name = "am_dir_img_punto_venta")
    private String am_DirImgPuntoVenta;
    @Column(name = "am_port")
    private String amPort;
    @Column(name = "am_host")
    private String amHost;
    @Column(name = "am_protocol")
    private String amProtocol;
    @Column(name = "am_usuario_smpt")
    private String amUsuarioSmpt;
    @Column(name = "am_password")
    private String amPassword;
    @Column(name = "am_direccion_sucursal")
    private String amDireccionSucursal;
    @OneToMany(mappedBy = "cod_tipoambiente")
    private Collection<Factura> facturaCollection;

    @Column(name = "am_micro_emp")
    private Boolean amMicroEmp;

    @Column(name = "am_age_ret")
    private Boolean amAgeRet;

    @Column(name = "am_contr_esp")
    private Boolean amContrEsp;

    @Column(name = "am_exp")
    private Boolean amExp;
    @Column(name = "am_ripme")
    private Boolean amRimpe;

    public Tipoambiente() {
    }

    public Tipoambiente(Integer codTipoambiente) {
        this.codTipoambiente = codTipoambiente;
    }

    public Integer getCodTipoambiente() {
        return codTipoambiente;
    }

    public void setCodTipoambiente(Integer codTipoambiente) {
        this.codTipoambiente = codTipoambiente;
    }

    public String getAmCodigo() {
        return amCodigo;
    }

    public void setAmCodigo(String amCodigo) {
        this.amCodigo = amCodigo;
    }

    public String getAmDescripcion() {
        return amDescripcion;
    }

    public void setAmDescripcion(String amDescripcion) {
        this.amDescripcion = amDescripcion;
    }

    public Boolean getAmEstado() {
        return amEstado;
    }

    public void setAmEstado(Boolean amEstado) {
        this.amEstado = amEstado;
    }

    public Integer getAmIdEmpresa() {
        return amIdEmpresa;
    }

    public void setAmIdEmpresa(Integer amIdEmpresa) {
        this.amIdEmpresa = amIdEmpresa;
    }

    public String getAmUsuariosri() {
        return amUsuariosri;
    }

    public void setAmUsuariosri(String amUsuariosri) {
        this.amUsuariosri = amUsuariosri;
    }

    public String getAmUrlsri() {
        return amUrlsri;
    }

    public void setAmUrlsri(String amUrlsri) {
        this.amUrlsri = amUrlsri;
    }

    public String getAmDirReportes() {
        return amDirReportes;
    }

    public void setAmDirReportes(String amDirReportes) {
        this.amDirReportes = amDirReportes;
    }

    public String getAmDirFirma() {
        return amDirFirma;
    }

    public void setAmDirFirma(String amDirFirma) {
        this.amDirFirma = amDirFirma;
    }

    public String getAmDirBaseArchivos() {
        return amDirBaseArchivos;
    }

    public void setAmDirBaseArchivos(String amDirBaseArchivos) {
        this.amDirBaseArchivos = amDirBaseArchivos;
    }

    public String getAmDirXml() {
        return amDirXml;
    }

    public void setAmDirXml(String amDirXml) {
        this.amDirXml = amDirXml;
    }

    public String getAmFirmados() {
        return amFirmados;
    }

    public void setAmFirmados(String amFirmados) {
        this.amFirmados = amFirmados;
    }

    public String getAmTrasmitidos() {
        return amTrasmitidos;
    }

    public void setAmTrasmitidos(String amTrasmitidos) {
        this.amTrasmitidos = amTrasmitidos;
    }

    public String getAmDevueltos() {
        return amDevueltos;
    }

    public void setAmDevueltos(String amDevueltos) {
        this.amDevueltos = amDevueltos;
    }

    public String getAmAutorizados() {
        return amAutorizados;
    }

    public void setAmAutorizados(String amAutorizados) {
        this.amAutorizados = amAutorizados;
    }

    public String getAmNoAutorizados() {
        return amNoAutorizados;
    }

    public void setAmNoAutorizados(String amNoAutorizados) {
        this.amNoAutorizados = amNoAutorizados;
    }

    public String getAmClaveAccesoSri() {
        return amClaveAccesoSri;
    }

    public void setAmClaveAccesoSri(String amClaveAccesoSri) {
        this.amClaveAccesoSri = amClaveAccesoSri;
    }

    public String getAmGenerados() {
        return amGenerados;
    }

    public void setAmGenerados(String amGenerados) {
        this.amGenerados = amGenerados;
    }

    public String getAmRazonSocial() {
        return amRazonSocial;
    }

    public void setAmRazonSocial(String amRazonSocial) {
        this.amRazonSocial = amRazonSocial;
    }

    public String getAmNombreComercial() {
        return amNombreComercial;
    }

    public void setAmNombreComercial(String amNombreComercial) {
        this.amNombreComercial = amNombreComercial;
    }

    public String getAmRuc() {
        return amRuc;
    }

    public void setAmRuc(String amRuc) {
        this.amRuc = amRuc;
    }

    public String getAmTipoEmision() {
        return amTipoEmision;
    }

    public void setAmTipoEmision(String amTipoEmision) {
        this.amTipoEmision = amTipoEmision;
    }

    public String getAmDireccionMatriz() {
        return amDireccionMatriz;
    }

    public void setAmDireccionMatriz(String amDireccionMatriz) {
        this.amDireccionMatriz = amDireccionMatriz;
    }

    public String getLlevarContabilidad() {
        return llevarContabilidad;
    }

    public void setLlevarContabilidad(String llevarContabilidad) {
        this.llevarContabilidad = llevarContabilidad;
    }

    public String getAmEstab() {
        return amEstab;
    }

    public void setAmEstab(String amEstab) {
        this.amEstab = amEstab;
    }

    public String getAmPtoemi() {
        return amPtoemi;
    }

    public void setAmPtoemi(String amPtoemi) {
        this.amPtoemi = amPtoemi;
    }

    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    public String getAmNroContribuyente() {
        return amNroContribuyente;
    }

    public void setAmNroContribuyente(String amNroContribuyente) {
        this.amNroContribuyente = amNroContribuyente;
    }

    public String getAmEnviocliente() {
        return amEnviocliente;
    }

    public void setAmEnviocliente(String amEnviocliente) {
        this.amEnviocliente = amEnviocliente;
    }

    public String getAmUnidadDisco() {
        return amUnidadDisco;
    }

    public void setAmUnidadDisco(String amUnidadDisco) {
        this.amUnidadDisco = amUnidadDisco;
    }

    public String getAmFolderFirma() {
        return amFolderFirma;
    }

    public void setAmFolderFirma(String amFolderFirma) {
        this.amFolderFirma = amFolderFirma;
    }

    public String getAmDirAts() {
        return amDirAts;
    }

    public void setAmDirAts(String amDirAts) {
        this.amDirAts = amDirAts;
    }

    public String getAmCiudad() {
        return amCiudad;
    }

    public void setAmCiudad(String amCiudad) {
        this.amCiudad = amCiudad;
    }

    public String getAmTelefono() {
        return amTelefono;
    }

    public void setAmTelefono(String amTelefono) {
        this.amTelefono = amTelefono;
    }

    public String getAm_DirImgPuntoVenta() {
        return am_DirImgPuntoVenta;
    }

    public void setAm_DirImgPuntoVenta(String am_DirImgPuntoVenta) {
        this.am_DirImgPuntoVenta = am_DirImgPuntoVenta;
    }

    public String getAmHost() {
        return amHost;
    }

    public void setAmHost(String amHost) {
        this.amHost = amHost;
    }

    public String getAmPort() {
        return amPort;
    }

    public void setAmPort(String amPort) {
        this.amPort = amPort;
    }

    public String getAmProtocol() {
        return amProtocol;
    }

    public void setAmProtocol(String amProtocol) {
        this.amProtocol = amProtocol;
    }

    public String getAmUsuarioSmpt() {
        return amUsuarioSmpt;
    }

    public void setAmUsuarioSmpt(String amUsuarioSmpt) {
        this.amUsuarioSmpt = amUsuarioSmpt;
    }

    public String getAmPassword() {
        return amPassword;
    }

    public void setAmPassword(String amPassword) {
        this.amPassword = amPassword;
    }

    public String getAmDireccionSucursal() {
        return amDireccionSucursal;
    }

    public void setAmDireccionSucursal(String amDireccionSucursal) {
        this.amDireccionSucursal = amDireccionSucursal;
    }

    public Boolean getAmMicroEmp() {

        return amMicroEmp == null ? false : amMicroEmp;
    }

    public void setAmMicroEmp(Boolean amMicroEmp) {
        this.amMicroEmp = amMicroEmp;
    }

    public Boolean getAmAgeRet() {
        return amAgeRet;
    }

    public void setAmAgeRet(Boolean amAgeRet) {
        this.amAgeRet = amAgeRet;
    }

    public Boolean getAmContrEsp() {
        return amContrEsp;
    }

    public void setAmContrEsp(Boolean amContrEsp) {
        this.amContrEsp = amContrEsp;
    }

    public Boolean getAmExp() {
        return amExp;
    }

    public void setAmExp(Boolean amExp) {
        this.amExp = amExp;
    }

    public Boolean getAmRimpe() {
        return amRimpe == null ? Boolean.FALSE : amRimpe;
    }

    public void setAmRimpe(Boolean amRimpe) {
        this.amRimpe = amRimpe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTipoambiente != null ? codTipoambiente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoambiente)) {
            return false;
        }
        Tipoambiente other = (Tipoambiente) object;
        if ((this.codTipoambiente == null && other.codTipoambiente != null) || (this.codTipoambiente != null && !this.codTipoambiente.equals(other.codTipoambiente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Tipoambiente[ codTipoambiente=" + codTipoambiente + " ]";
    }

}
