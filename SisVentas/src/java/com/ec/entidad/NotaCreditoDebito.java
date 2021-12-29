/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Darwin
 */
@Entity
@Table(name = "nota_credito_debito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotaCreditoDebito.findAll", query = "SELECT n FROM NotaCreditoDebito n"),
    @NamedQuery(name = "NotaCreditoDebito.findUltimaNC", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facNumero IS NOT NULL ORDER BY  n.facNumero DESC"),
    @NamedQuery(name = "NotaCreditoDebito.findByIdNota", query = "SELECT n FROM NotaCreditoDebito n WHERE n.idNota = :idNota"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacFecha", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facFecha = :facFecha"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacSubtotal", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facSubtotal = :facSubtotal"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacIva", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facIva = :facIva"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacTotal", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facTotal = :facTotal"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacEstado", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facEstado = :facEstado"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacTipo", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facTipo = :facTipo"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacAbono", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facAbono = :facAbono"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacSaldo", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facSaldo = :facSaldo"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacDescripcion", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facDescripcion = :facDescripcion"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacNumProforma", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facNumProforma = :facNumProforma"),
    @NamedQuery(name = "NotaCreditoDebito.findByTipodocumento", query = "SELECT n FROM NotaCreditoDebito n WHERE n.tipodocumento = :tipodocumento"),
    @NamedQuery(name = "NotaCreditoDebito.findByPuntoemision", query = "SELECT n FROM NotaCreditoDebito n WHERE n.puntoemision = :puntoemision"),
    @NamedQuery(name = "NotaCreditoDebito.findByCodestablecimiento", query = "SELECT n FROM NotaCreditoDebito n WHERE n.codestablecimiento = :codestablecimiento"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacNumeroText", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facNumeroText = :facNumeroText"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacTipoIdentificadorComprobador", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facTipoIdentificadorComprobador = :facTipoIdentificadorComprobador"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacDescuento", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facDescuento = :facDescuento"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacCodIce", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facCodIce = :facCodIce"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacCodIva", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facCodIva = :facCodIva"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacTotalBaseCero", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facTotalBaseCero = :facTotalBaseCero"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacTotalBaseGravaba", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facTotalBaseGravaba = :facTotalBaseGravaba"),
    @NamedQuery(name = "NotaCreditoDebito.findByCodigoPorcentaje", query = "SELECT n FROM NotaCreditoDebito n WHERE n.codigoPorcentaje = :codigoPorcentaje"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacPorcentajeIva", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facPorcentajeIva = :facPorcentajeIva"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacMoneda", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facMoneda = :facMoneda"),
    @NamedQuery(name = "NotaCreditoDebito.findByIdFormaPago", query = "SELECT n FROM NotaCreditoDebito n WHERE n.idFormaPago = :idFormaPago"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacPlazo", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facPlazo = :facPlazo"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacUnidadTiempo", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facUnidadTiempo = :facUnidadTiempo"),
    @NamedQuery(name = "NotaCreditoDebito.findByEstadosri", query = "SELECT n FROM NotaCreditoDebito n WHERE n.estadosri = :estadosri"),
    @NamedQuery(name = "NotaCreditoDebito.findByMensajesri", query = "SELECT n FROM NotaCreditoDebito n WHERE n.mensajesri = :mensajesri"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacFechaAutorizacion", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facFechaAutorizacion = :facFechaAutorizacion"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacClaveAcceso", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facClaveAcceso = :facClaveAcceso"),
    @NamedQuery(name = "NotaCreditoDebito.findByCodTipoambiente", query = "SELECT n FROM NotaCreditoDebito n WHERE n.codTipoambiente = :codTipoambiente"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacClaveAutorizacion", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facClaveAutorizacion = :facClaveAutorizacion"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacPath", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facPath = :facPath"),
    @NamedQuery(name = "NotaCreditoDebito.findByTipodocumentomod", query = "SELECT n FROM NotaCreditoDebito n WHERE n.tipodocumentomod = :tipodocumentomod"),
    @NamedQuery(name = "NotaCreditoDebito.findByFacFechaSustento", query = "SELECT n FROM NotaCreditoDebito n WHERE n.facFechaSustento = :facFechaSustento")})
public class NotaCreditoDebito implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_nota")
    private Integer idNota;
    @Column(name = "fac_numero")
    private Integer facNumero;
    @Column(name = "fac_fecha")
    @Temporal(TemporalType.DATE)
    private Date facFecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "fac_subtotal")
    private BigDecimal facSubtotal;
    @Column(name = "fac_iva")
    private BigDecimal facIva;
    @Column(name = "fac_total")
    private BigDecimal facTotal;
    @Size(max = 20)
    @Column(name = "fac_estado")
    private String facEstado;
    @Size(max = 20)
    @Column(name = "fac_tipo")
    private String facTipo;
    @Column(name = "fac_abono")
    private BigDecimal facAbono;
    @Column(name = "fac_saldo")
    private BigDecimal facSaldo;
    @Size(max = 150)
    @Column(name = "fac_descripcion")
    private String facDescripcion;
    @Column(name = "fac_num_proforma")
    private Integer facNumProforma;
    @Size(max = 2147483647)
    @Column(name = "tipodocumento")
    private String tipodocumento;
    @Size(max = 2147483647)
    @Column(name = "puntoemision")
    private String puntoemision;
    @Size(max = 2147483647)
    @Column(name = "codestablecimiento")
    private String codestablecimiento;
    @Size(max = 2147483647)
    @Column(name = "fac_numero_text")
    private String facNumeroText;
    @Size(max = 2147483647)
    @Column(name = "fac_tipo_identificador_comprobador")
    private String facTipoIdentificadorComprobador;
    @Column(name = "fac_descuento")
    private BigDecimal facDescuento;
    @Size(max = 2147483647)
    @Column(name = "fac_cod_ice")
    private String facCodIce;
    @Size(max = 2147483647)
    @Column(name = "fac_cod_iva")
    private String facCodIva;
    @Column(name = "fac_total_base_cero")
    private BigDecimal facTotalBaseCero;
    @Column(name = "fac_total_base_gravaba")
    private BigDecimal facTotalBaseGravaba;
    @Size(max = 2147483647)
    @Column(name = "codigo_porcentaje")
    private String codigoPorcentaje;
    @Size(max = 2147483647)
    @Column(name = "fac_porcentaje_iva")
    private String facPorcentajeIva;
    @Size(max = 2147483647)
    @Column(name = "fac_moneda")
    private String facMoneda;
    @Column(name = "id_forma_pago")
    private Integer idFormaPago;
    @Column(name = "fac_plazo")
    private BigDecimal facPlazo;
    @Size(max = 2147483647)
    @Column(name = "fac_unidad_tiempo")
    private String facUnidadTiempo;
    @Size(max = 2147483647)
    @Column(name = "estadosri")
    private String estadosri;
    @Size(max = 2147483647)
    @Column(name = "mensajesri")
    private String mensajesri;
    @Column(name = "fac_fecha_autorizacion")
    @Temporal(TemporalType.DATE)
    private Date facFechaAutorizacion;
    @Size(max = 2147483647)
    @Column(name = "fac_clave_acceso")
    private String facClaveAcceso;
    @Column(name = "cod_tipoambiente")
    private Integer codTipoambiente;
    @Size(max = 2147483647)
    @Column(name = "fac_clave_autorizacion")
    private String facClaveAutorizacion;
    @Size(max = 2147483647)
    @Column(name = "fac_path")
    private String facPath;
    @Size(max = 2147483647)
    @Column(name = "tipodocumentomod")
    private String tipodocumentomod;
    @Column(name = "fac_fecha_sustento")
    @Temporal(TemporalType.DATE)
    private Date facFechaSustento;
    @Column(name = "mensajeinf")
    private String mensajeInf;
    @OneToMany(mappedBy = "idNota")
    private Collection<DetalleNotaDebitoCredito> detalleNotaDebitoCreditoCollection;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;
    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura")
    @ManyToOne
    private Factura idFactura;

    public NotaCreditoDebito() {
    }

    public NotaCreditoDebito(Integer idNota) {
        this.idNota = idNota;
    }

    public Integer getIdNota() {
        return idNota;
    }

    public void setIdNota(Integer idNota) {
        this.idNota = idNota;
    }

    public Date getFacFecha() {
        return facFecha;
    }

    public void setFacFecha(Date facFecha) {
        this.facFecha = facFecha;
    }

    public BigDecimal getFacSubtotal() {
        return facSubtotal;
    }

    public void setFacSubtotal(BigDecimal facSubtotal) {
        this.facSubtotal = facSubtotal;
    }

    public BigDecimal getFacIva() {
        return facIva;
    }

    public void setFacIva(BigDecimal facIva) {
        this.facIva = facIva;
    }

    public BigDecimal getFacTotal() {
        return facTotal;
    }

    public void setFacTotal(BigDecimal facTotal) {
        this.facTotal = facTotal;
    }

    public String getFacEstado() {
        return facEstado;
    }

    public void setFacEstado(String facEstado) {
        this.facEstado = facEstado;
    }

    public String getFacTipo() {
        return facTipo;
    }

    public void setFacTipo(String facTipo) {
        this.facTipo = facTipo;
    }

    public BigDecimal getFacAbono() {
        return facAbono;
    }

    public void setFacAbono(BigDecimal facAbono) {
        this.facAbono = facAbono;
    }

    public BigDecimal getFacSaldo() {
        return facSaldo;
    }

    public void setFacSaldo(BigDecimal facSaldo) {
        this.facSaldo = facSaldo;
    }

    public String getFacDescripcion() {
        return facDescripcion;
    }

    public void setFacDescripcion(String facDescripcion) {
        this.facDescripcion = facDescripcion;
    }

    public Integer getFacNumProforma() {
        return facNumProforma;
    }

    public void setFacNumProforma(Integer facNumProforma) {
        this.facNumProforma = facNumProforma;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getPuntoemision() {
        return puntoemision;
    }

    public void setPuntoemision(String puntoemision) {
        this.puntoemision = puntoemision;
    }

    public String getCodestablecimiento() {
        return codestablecimiento;
    }

    public void setCodestablecimiento(String codestablecimiento) {
        this.codestablecimiento = codestablecimiento;
    }

    public String getFacNumeroText() {
        return facNumeroText;
    }

    public void setFacNumeroText(String facNumeroText) {
        this.facNumeroText = facNumeroText;
    }

    public String getFacTipoIdentificadorComprobador() {
        return facTipoIdentificadorComprobador;
    }

    public void setFacTipoIdentificadorComprobador(String facTipoIdentificadorComprobador) {
        this.facTipoIdentificadorComprobador = facTipoIdentificadorComprobador;
    }

    public BigDecimal getFacDescuento() {
        return facDescuento;
    }

    public void setFacDescuento(BigDecimal facDescuento) {
        this.facDescuento = facDescuento;
    }

    public String getFacCodIce() {
        return facCodIce;
    }

    public void setFacCodIce(String facCodIce) {
        this.facCodIce = facCodIce;
    }

    public String getFacCodIva() {
        return facCodIva;
    }

    public void setFacCodIva(String facCodIva) {
        this.facCodIva = facCodIva;
    }

    public BigDecimal getFacTotalBaseCero() {
        return facTotalBaseCero;
    }

    public void setFacTotalBaseCero(BigDecimal facTotalBaseCero) {
        this.facTotalBaseCero = facTotalBaseCero;
    }

    public BigDecimal getFacTotalBaseGravaba() {
        return facTotalBaseGravaba;
    }

    public void setFacTotalBaseGravaba(BigDecimal facTotalBaseGravaba) {
        this.facTotalBaseGravaba = facTotalBaseGravaba;
    }

    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public String getFacPorcentajeIva() {
        return facPorcentajeIva;
    }

    public void setFacPorcentajeIva(String facPorcentajeIva) {
        this.facPorcentajeIva = facPorcentajeIva;
    }

    public String getFacMoneda() {
        return facMoneda;
    }

    public void setFacMoneda(String facMoneda) {
        this.facMoneda = facMoneda;
    }

    public Integer getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(Integer idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public BigDecimal getFacPlazo() {
        return facPlazo;
    }

    public void setFacPlazo(BigDecimal facPlazo) {
        this.facPlazo = facPlazo;
    }

    public String getFacUnidadTiempo() {
        return facUnidadTiempo;
    }

    public void setFacUnidadTiempo(String facUnidadTiempo) {
        this.facUnidadTiempo = facUnidadTiempo;
    }

    public String getEstadosri() {
        return estadosri;
    }

    public void setEstadosri(String estadosri) {
        this.estadosri = estadosri;
    }

    public String getMensajesri() {
        return mensajesri;
    }

    public void setMensajesri(String mensajesri) {
        this.mensajesri = mensajesri;
    }

    public Date getFacFechaAutorizacion() {
        return facFechaAutorizacion;
    }

    public void setFacFechaAutorizacion(Date facFechaAutorizacion) {
        this.facFechaAutorizacion = facFechaAutorizacion;
    }

    public String getFacClaveAcceso() {
        return facClaveAcceso;
    }

    public void setFacClaveAcceso(String facClaveAcceso) {
        this.facClaveAcceso = facClaveAcceso;
    }

    public Integer getCodTipoambiente() {
        return codTipoambiente;
    }

    public void setCodTipoambiente(Integer codTipoambiente) {
        this.codTipoambiente = codTipoambiente;
    }

    public String getFacClaveAutorizacion() {
        return facClaveAutorizacion;
    }

    public void setFacClaveAutorizacion(String facClaveAutorizacion) {
        this.facClaveAutorizacion = facClaveAutorizacion;
    }

    public String getFacPath() {
        return facPath;
    }

    public void setFacPath(String facPath) {
        this.facPath = facPath;
    }

    public String getTipodocumentomod() {
        return tipodocumentomod;
    }

    public void setTipodocumentomod(String tipodocumentomod) {
        this.tipodocumentomod = tipodocumentomod;
    }

    public Date getFacFechaSustento() {
        return facFechaSustento;
    }

    public void setFacFechaSustento(Date facFechaSustento) {
        this.facFechaSustento = facFechaSustento;
    }

    @XmlTransient
    public Collection<DetalleNotaDebitoCredito> getDetalleNotaDebitoCreditoCollection() {
        return detalleNotaDebitoCreditoCollection;
    }

    public void setDetalleNotaDebitoCreditoCollection(Collection<DetalleNotaDebitoCredito> detalleNotaDebitoCreditoCollection) {
        this.detalleNotaDebitoCreditoCollection = detalleNotaDebitoCreditoCollection;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Factura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Factura idFactura) {
        this.idFactura = idFactura;
    }

    public Integer getFacNumero() {
        return facNumero;
    }

    public void setFacNumero(Integer facNumero) {
        this.facNumero = facNumero;
    }

    public String getMensajeInf() {
        return mensajeInf;
    }

    public void setMensajeInf(String mensajeInf) {
        this.mensajeInf = mensajeInf;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNota != null ? idNota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaCreditoDebito)) {
            return false;
        }
        NotaCreditoDebito other = (NotaCreditoDebito) object;
        if ((this.idNota == null && other.idNota != null) || (this.idNota != null && !this.idNota.equals(other.idNota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.NotaCreditoDebito[ idNota=" + idNota + " ]";
    }
    
}
