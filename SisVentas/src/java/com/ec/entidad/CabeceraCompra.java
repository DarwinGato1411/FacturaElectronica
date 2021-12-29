/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
@Table(name = "cabecera_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CabeceraCompra.findAll", query = "SELECT c FROM CabeceraCompra c")
    ,
    @NamedQuery(name = "CabeceraCompra.findCabProveedor", query = "SELECT c FROM CabeceraCompra c WHERE c.cabProveedor LIKE :cabProveedor ORDER BY c.cabFechaEmision DESC")
    ,
    @NamedQuery(name = "CabeceraCompra.findByIdCabecera", query = "SELECT c FROM CabeceraCompra c WHERE c.idCabecera = :idCabecera")
    ,
    @NamedQuery(name = "CabeceraCompra.findByCabNumFactura", query = "SELECT c FROM CabeceraCompra c WHERE c.cabNumFactura = :cabNumFactura")
    ,
    @NamedQuery(name = "CabeceraCompra.findByCabFecha", query = "SELECT c FROM CabeceraCompra c WHERE c.cabFecha = :cabFecha")
    ,
    @NamedQuery(name = "CabeceraCompra.findByBetweenFecha", query = "SELECT c FROM CabeceraCompra c WHERE c.cabFecha BETWEEN :inicio AND :fin ")
    ,@NamedQuery(name = "CabeceraCompra.findByBetweenCabFechaEmision", query = "SELECT c FROM CabeceraCompra c WHERE c.cabFechaEmision BETWEEN :inicio AND :fin ")
    ,
    @NamedQuery(name = "CabeceraCompra.findByCabSubTotal", query = "SELECT c FROM CabeceraCompra c WHERE c.cabSubTotal = :cabSubTotal")
    ,
    @NamedQuery(name = "CabeceraCompra.findByCabIva", query = "SELECT c FROM CabeceraCompra c WHERE c.cabIva = :cabIva")
    ,
    @NamedQuery(name = "CabeceraCompra.findByCabTotal", query = "SELECT c FROM CabeceraCompra c WHERE c.cabTotal = :cabTotal")
    ,
    @NamedQuery(name = "CabeceraCompra.findByCabDescripcion", query = "SELECT c FROM CabeceraCompra c WHERE c.cabDescripcion = :cabDescripcion")})
public class CabeceraCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cabecera")
    private Integer idCabecera;
    @Size(max = 50)
    @Column(name = "cab_num_factura")
    private String cabNumFactura;
    @Column(name = "cab_fecha")
    @Temporal(TemporalType.DATE)
    private Date cabFecha;
    @Column(name = "cab_fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date cabFechaEmision;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cab_sub_total")
    private BigDecimal cabSubTotal;
    @Column(name = "cab_sub_total_cero")
    private BigDecimal cabSubTotalCero;
    @Column(name = "cab_iva")
    private BigDecimal cabIva;
    @Column(name = "cab_total")
    private BigDecimal cabTotal;
    @Size(max = 100)
    @Column(name = "cab_descripcion")
    private String cabDescripcion;
    @Size(max = 40)
    @Column(name = "cab_estado")
    private String cabEstado;
    @Size(max = 100)
    @Column(name = "cab_proveedor")
    private String cabProveedor;
    @Column(name = "cab_clave_acceso")
    private String cabClaveAcceso;
    @Column(name = "cab_autorizacion")
    private String cabAutorizacion;
    @Size(max = 13)
    @Column(name = "cab_ruc_proveedor")
    private String cab_ruc_proveedor;
    @Column(name = "drc_codigo_sustento")
    private String drcCodigoSustento;
    @Column(name = "cab_retencion_autori")
    private String cabRetencionAutori;
    @Column(name = "cab_trae_sri")
    private Boolean cabTraeSri;
    @Column(name = "cab_homologado")
    private String cabHomologado;
    @Column(name = "cab_establecimiento")
    private String cabEstablecimiento;
    @Column(name = "cab_punto_emi")
    private String cabPuntoEmi;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne
    private EstadoFacturas idEstado;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;
    @OneToMany(mappedBy = "idCompra")
    private Collection<DetalleKardex> detalleKardexCollection;

    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    @ManyToOne
    private Proveedores idProveedor;

    public CabeceraCompra() {
    }

    public CabeceraCompra(Integer idCabecera) {
        this.idCabecera = idCabecera;
    }

    public Integer getIdCabecera() {
        return idCabecera;
    }

    public void setIdCabecera(Integer idCabecera) {
        this.idCabecera = idCabecera;
    }

    public String getCabNumFactura() {
        return cabNumFactura;
    }

    public void setCabNumFactura(String cabNumFactura) {
        this.cabNumFactura = cabNumFactura;
    }

    public Date getCabFecha() {
        return cabFecha;
    }

    public void setCabFecha(Date cabFecha) {
        this.cabFecha = cabFecha;
    }

    public BigDecimal getCabSubTotal() {
        return cabSubTotal;
    }

    public void setCabSubTotal(BigDecimal cabSubTotal) {
        this.cabSubTotal = cabSubTotal;
    }

    public BigDecimal getCabIva() {
        return cabIva;
    }

    public void setCabIva(BigDecimal cabIva) {
        this.cabIva = cabIva;
    }

    public BigDecimal getCabTotal() {
        return cabTotal;
    }

    public void setCabTotal(BigDecimal cabTotal) {
        this.cabTotal = cabTotal;
    }

    public String getCabDescripcion() {
        return cabDescripcion;
    }

    public void setCabDescripcion(String cabDescripcion) {
        this.cabDescripcion = cabDescripcion;
    }

    public EstadoFacturas getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadoFacturas idEstado) {
        this.idEstado = idEstado;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCabEstado() {
        return cabEstado;
    }

    public void setCabEstado(String cabEstado) {
        this.cabEstado = cabEstado;
    }

    public String getCabProveedor() {
        return cabProveedor;
    }

    public void setCabProveedor(String cabProveedor) {
        this.cabProveedor = cabProveedor;
    }

    public String getCabClaveAcceso() {
        return cabClaveAcceso;
    }

    public void setCabClaveAcceso(String cabClaveAcceso) {
        this.cabClaveAcceso = cabClaveAcceso;
    }

    public String getCabAutorizacion() {
        return cabAutorizacion;
    }

    public void setCabAutorizacion(String cabAutorizacion) {
        this.cabAutorizacion = cabAutorizacion;
    }

    public Date getCabFechaEmision() {
        return cabFechaEmision;
    }

    public void setCabFechaEmision(Date cabFechaEmision) {
        this.cabFechaEmision = cabFechaEmision;
    }

    public String getCab_ruc_proveedor() {
        return cab_ruc_proveedor;
    }

    public void setCab_ruc_proveedor(String cab_ruc_proveedor) {
        this.cab_ruc_proveedor = cab_ruc_proveedor;
    }

    @XmlTransient
    public Collection<DetalleKardex> getDetalleKardexCollection() {
        return detalleKardexCollection;
    }

    public void setDetalleKardexCollection(Collection<DetalleKardex> detalleKardexCollection) {
        this.detalleKardexCollection = detalleKardexCollection;
    }

    public Proveedores getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedores idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getDrcCodigoSustento() {
        return drcCodigoSustento;
    }

    public void setDrcCodigoSustento(String drcCodigoSustento) {
        this.drcCodigoSustento = drcCodigoSustento;
    }

    public String getCabRetencionAutori() {
        return cabRetencionAutori;
    }

    public void setCabRetencionAutori(String cabRetencionAutori) {
        this.cabRetencionAutori = cabRetencionAutori;
    }

    public Boolean getCabTraeSri() {
        return cabTraeSri;
    }

    public void setCabTraeSri(Boolean cabTraeSri) {
        this.cabTraeSri = cabTraeSri;
    }

    public String getCabHomologado() {
        return cabHomologado;
    }

    public void setCabHomologado(String cabHomologado) {
        this.cabHomologado = cabHomologado;
    }

    public BigDecimal getCabSubTotalCero() {
        return cabSubTotalCero;
    }

    public void setCabSubTotalCero(BigDecimal cabSubTotalCero) {
        this.cabSubTotalCero = cabSubTotalCero;
    }

    public String getCabEstablecimiento() {
        return cabEstablecimiento;
    }

    public void setCabEstablecimiento(String cabEstablecimiento) {
        this.cabEstablecimiento = cabEstablecimiento;
    }

    public String getCabPuntoEmi() {
        return cabPuntoEmi;
    }

    public void setCabPuntoEmi(String cabPuntoEmi) {
        this.cabPuntoEmi = cabPuntoEmi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCabecera != null ? idCabecera.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CabeceraCompra)) {
            return false;
        }
        CabeceraCompra other = (CabeceraCompra) object;
        if ((this.idCabecera == null && other.idCabecera != null) || (this.idCabecera != null && !this.idCabecera.equals(other.idCabecera))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.CabeceraCompra[ idCabecera=" + idCabecera + " ]";
    }
}
