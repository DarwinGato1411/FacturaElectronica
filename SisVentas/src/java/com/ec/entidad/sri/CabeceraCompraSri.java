/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad.sri;

import com.ec.entidad.EstadoFacturas;
import com.ec.entidad.Proveedores;
import com.ec.entidad.Usuario;
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
 * @author Darwin
 */
@Entity
@Table(name = "cabecera_compra_sri")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CabeceraCompraSri.findAll", query = "SELECT c FROM CabeceraCompraSri c")
    , @NamedQuery(name = "CabeceraCompraSri.findByIdCabeceraSri", query = "SELECT c FROM CabeceraCompraSri c WHERE c.idCabeceraSri = :idCabeceraSri")
    , @NamedQuery(name = "CabeceraCompraSri.findByIdUsuario", query = "SELECT c FROM CabeceraCompraSri c WHERE c.idUsuario = :idUsuario")
    , @NamedQuery(name = "CabeceraCompraSri.findByIdEstado", query = "SELECT c FROM CabeceraCompraSri c WHERE c.idEstado = :idEstado")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabNumFactura", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabNumFactura = :cabNumFactura")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabFecha", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabFecha = :cabFecha")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabSubTotal", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabSubTotal = :cabSubTotal")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabIva", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabIva = :cabIva")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabTotal", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabTotal = :cabTotal")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabDescripcion", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabDescripcion = :cabDescripcion")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabEstado", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabEstado = :cabEstado")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabProveedor", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabProveedor = :cabProveedor")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabClaveAcceso", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabClaveAcceso = :cabClaveAcceso")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabAutorizacion", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabAutorizacion = :cabAutorizacion")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabFechaEmision", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabFechaEmision = :cabFechaEmision")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabRucProveedor", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabRucProveedor = :cabRucProveedor")
    , @NamedQuery(name = "CabeceraCompraSri.findByIdTipoIdentificacionCompra", query = "SELECT c FROM CabeceraCompraSri c WHERE c.idTipoIdentificacionCompra = :idTipoIdentificacionCompra")
    , @NamedQuery(name = "CabeceraCompraSri.findByDrcCodigoSustento", query = "SELECT c FROM CabeceraCompraSri c WHERE c.drcCodigoSustento = :drcCodigoSustento")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabRetencionAutori", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabRetencionAutori = :cabRetencionAutori")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabTraeSri", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabTraeSri = :cabTraeSri")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabHomologado", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabHomologado = :cabHomologado")
    , @NamedQuery(name = "CabeceraCompraSri.findByCabSubTotalCero", query = "SELECT c FROM CabeceraCompraSri c WHERE c.cabSubTotalCero = :cabSubTotalCero")})
public class CabeceraCompraSri implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cabecera_sri")
    private Integer idCabeceraSri;

    @Size(max = 50)
    @Column(name = "cab_num_factura")
    private String cabNumFactura;
    @Column(name = "cab_fecha")
    @Temporal(TemporalType.DATE)
    private Date cabFecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cab_sub_total")
    private BigDecimal cabSubTotal;
    @Column(name = "cab_iva")
    private BigDecimal cabIva;
    @Column(name = "cab_total")
    private BigDecimal cabTotal;
    @Size(max = 100)
    @Column(name = "cab_descripcion")
    private String cabDescripcion;
    @Size(max = 45)
    @Column(name = "cab_estado")
    private String cabEstado;
    @Size(max = 100)
    @Column(name = "cab_proveedor")
    private String cabProveedor;
    @Size(max = 50)
    @Column(name = "cab_clave_acceso")
    private String cabClaveAcceso;
    @Size(max = 50)
    @Column(name = "cab_autorizacion")
    private String cabAutorizacion;
    @Column(name = "cab_fecha_emision")
    @Temporal(TemporalType.DATE)
    private Date cabFechaEmision;
    @Size(max = 13)
    @Column(name = "cab_ruc_proveedor")
    private String cabRucProveedor;
    @Column(name = "id_tipo_identificacion_compra")
    private Integer idTipoIdentificacionCompra;
    @Size(max = 100)
    @Column(name = "drc_codigo_sustento")
    private String drcCodigoSustento;
    @Size(max = 1)
    @Column(name = "cab_retencion_autori")
    private String cabRetencionAutori;
    @Column(name = "cab_trae_sri")
    private Boolean cabTraeSri;
    @Size(max = 2)
    @Column(name = "cab_homologado")
    private String cabHomologado;
    @Column(name = "cab_sub_total_cero")
    private BigDecimal cabSubTotalCero;
    @Column(name = "categoria_factura")
    private String categoriaFactura;
    @Column(name = "cab_xml_sri")
    private String cabXmlSri;
    @Column(name = "cab_casillero")
    private String cabCasillero;
    @JoinColumn(name = "est_id_estado", referencedColumnName = "id_estado")
    @ManyToOne
    private EstadoFacturas estIdEstado;
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor")
    @ManyToOne
    private Proveedores idProveedor;
    @JoinColumn(name = "usu_id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario usuIdUsuario;
    @OneToMany(mappedBy = "idCabeceraSri")
    private Collection<DetalleCompraSri> detalleCompraSriCollection;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne
    private EstadoFacturas idEstado;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;
    @JoinColumn(name = "id_categoria_compras", referencedColumnName = "id_categoria_compras")
    @ManyToOne
    private CategoriaCompras idCategoriaCompras;

    public CabeceraCompraSri() {
    }

    public CabeceraCompraSri(Integer idCabeceraSri) {
        this.idCabeceraSri = idCabeceraSri;
    }

    public Integer getIdCabeceraSri() {
        return idCabeceraSri;
    }

    public void setIdCabeceraSri(Integer idCabeceraSri) {
        this.idCabeceraSri = idCabeceraSri;
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

    public String getCabRucProveedor() {
        return cabRucProveedor;
    }

    public void setCabRucProveedor(String cabRucProveedor) {
        this.cabRucProveedor = cabRucProveedor;
    }

    public Integer getIdTipoIdentificacionCompra() {
        return idTipoIdentificacionCompra;
    }

    public void setIdTipoIdentificacionCompra(Integer idTipoIdentificacionCompra) {
        this.idTipoIdentificacionCompra = idTipoIdentificacionCompra;
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

    public EstadoFacturas getEstIdEstado() {
        return estIdEstado;
    }

    public void setEstIdEstado(EstadoFacturas estIdEstado) {
        this.estIdEstado = estIdEstado;
    }

    public Proveedores getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Proveedores idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Usuario getUsuIdUsuario() {
        return usuIdUsuario;
    }

    public void setUsuIdUsuario(Usuario usuIdUsuario) {
        this.usuIdUsuario = usuIdUsuario;
    }

    @XmlTransient
    public Collection<DetalleCompraSri> getDetalleCompraSriCollection() {
        return detalleCompraSriCollection;
    }

    public void setDetalleCompraSriCollection(Collection<DetalleCompraSri> detalleCompraSriCollection) {
        this.detalleCompraSriCollection = detalleCompraSriCollection;
    }

    public CategoriaCompras getIdCategoriaCompras() {
        return idCategoriaCompras;
    }

    public void setIdCategoriaCompras(CategoriaCompras idCategoriaCompras) {
        this.idCategoriaCompras = idCategoriaCompras;
    }

    public String getCategoriaFactura() {
        return categoriaFactura;
    }

    public void setCategoriaFactura(String categoriaFactura) {
        this.categoriaFactura = categoriaFactura;
    }

    public String getCabXmlSri() {
        return cabXmlSri;
    }

    public void setCabXmlSri(String cabXmlSri) {
        this.cabXmlSri = cabXmlSri;
    }

    public String getCabCasillero() {
        return cabCasillero;
    }

    public void setCabCasillero(String cabCasillero) {
        this.cabCasillero = cabCasillero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCabeceraSri != null ? idCabeceraSri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CabeceraCompraSri)) {
            return false;
        }
        CabeceraCompraSri other = (CabeceraCompraSri) object;
        if ((this.idCabeceraSri == null && other.idCabeceraSri != null) || (this.idCabeceraSri != null && !this.idCabeceraSri.equals(other.idCabeceraSri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.sri.CabeceraCompraSri[ idCabeceraSri=" + idCabeceraSri + " ]";
    }

}
