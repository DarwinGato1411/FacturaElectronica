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
@Table(name = "guiaremision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Guiaremision.findAll", query = "SELECT g FROM Guiaremision g")
    ,
    @NamedQuery(name = "Guiaremision.findByIdGuiaremision", query = "SELECT g FROM Guiaremision g WHERE g.idGuiaremision = :idGuiaremision")
    ,
    @NamedQuery(name = "Guiaremision.findByFacFecha", query = "SELECT g FROM Guiaremision g WHERE g.facFecha = :facFecha")
    ,
    @NamedQuery(name = "Guiaremision.findByFacSubtotal", query = "SELECT g FROM Guiaremision g WHERE g.facSubtotal = :facSubtotal")
    ,
    @NamedQuery(name = "Guiaremision.findByFacIva", query = "SELECT g FROM Guiaremision g WHERE g.facIva = :facIva")
    ,
    @NamedQuery(name = "Guiaremision.findByFacTotal", query = "SELECT g FROM Guiaremision g WHERE g.facTotal = :facTotal")
    ,
    @NamedQuery(name = "Guiaremision.findByFacEstado", query = "SELECT g FROM Guiaremision g WHERE g.facEstado = :facEstado")
    ,
    @NamedQuery(name = "Guiaremision.findByFacTipo", query = "SELECT g FROM Guiaremision g WHERE g.facTipo = :facTipo")
    ,
    @NamedQuery(name = "Guiaremision.findByFacAbono", query = "SELECT g FROM Guiaremision g WHERE g.facAbono = :facAbono")
    ,
    @NamedQuery(name = "Guiaremision.findByFacSaldo", query = "SELECT g FROM Guiaremision g WHERE g.facSaldo = :facSaldo")
    ,
    @NamedQuery(name = "Guiaremision.findByFacDescripcion", query = "SELECT g FROM Guiaremision g WHERE g.facDescripcion = :facDescripcion")
    ,
    @NamedQuery(name = "Guiaremision.findByFacNumProforma", query = "SELECT g FROM Guiaremision g WHERE g.facNumProforma = :facNumProforma")
    ,
    @NamedQuery(name = "Guiaremision.findByTipodocumento", query = "SELECT g FROM Guiaremision g WHERE g.tipodocumento = :tipodocumento")
    ,
    @NamedQuery(name = "Guiaremision.findByPuntoemision", query = "SELECT g FROM Guiaremision g WHERE g.puntoemision = :puntoemision")
    ,
    @NamedQuery(name = "Guiaremision.findByCodestablecimiento", query = "SELECT g FROM Guiaremision g WHERE g.codestablecimiento = :codestablecimiento")
    ,
    @NamedQuery(name = "Guiaremision.findByFacNumeroText", query = "SELECT g FROM Guiaremision g WHERE g.facNumeroText = :facNumeroText")
    ,
    @NamedQuery(name = "Guiaremision.findByFacTipoIdentificadorComprobador", query = "SELECT g FROM Guiaremision g WHERE g.facTipoIdentificadorComprobador = :facTipoIdentificadorComprobador")
    ,
    @NamedQuery(name = "Guiaremision.findByFacDescuento", query = "SELECT g FROM Guiaremision g WHERE g.facDescuento = :facDescuento")
    ,
    @NamedQuery(name = "Guiaremision.findByFacCodIce", query = "SELECT g FROM Guiaremision g WHERE g.facCodIce = :facCodIce")
    ,
    @NamedQuery(name = "Guiaremision.findByFacCodIva", query = "SELECT g FROM Guiaremision g WHERE g.facCodIva = :facCodIva")
    ,
    @NamedQuery(name = "Guiaremision.findByFacTotalBaseCero", query = "SELECT g FROM Guiaremision g WHERE g.facTotalBaseCero = :facTotalBaseCero")
    ,
    @NamedQuery(name = "Guiaremision.findByFacTotalBaseGravaba", query = "SELECT g FROM Guiaremision g WHERE g.facTotalBaseGravaba = :facTotalBaseGravaba")
    ,
    @NamedQuery(name = "Guiaremision.findByCodigoPorcentaje", query = "SELECT g FROM Guiaremision g WHERE g.codigoPorcentaje = :codigoPorcentaje")
    ,
    @NamedQuery(name = "Guiaremision.findByFacPorcentajeIva", query = "SELECT g FROM Guiaremision g WHERE g.facPorcentajeIva = :facPorcentajeIva")
    ,
    @NamedQuery(name = "Guiaremision.findByFacMoneda", query = "SELECT g FROM Guiaremision g WHERE g.facMoneda = :facMoneda")
    ,
    @NamedQuery(name = "Guiaremision.findByIdFormaPago", query = "SELECT g FROM Guiaremision g WHERE g.idFormaPago = :idFormaPago")
    ,
    @NamedQuery(name = "Guiaremision.findByFacPlazo", query = "SELECT g FROM Guiaremision g WHERE g.facPlazo = :facPlazo")
    ,
    @NamedQuery(name = "Guiaremision.findByFacUnidadTiempo", query = "SELECT g FROM Guiaremision g WHERE g.facUnidadTiempo = :facUnidadTiempo")
    ,
    @NamedQuery(name = "Guiaremision.findByEstadosri", query = "SELECT g FROM Guiaremision g WHERE g.estadosri = :estadosri")
    ,
    @NamedQuery(name = "Guiaremision.findByMensajesri", query = "SELECT g FROM Guiaremision g WHERE g.mensajesri = :mensajesri")
    ,
    @NamedQuery(name = "Guiaremision.findByFacFechaAutorizacion", query = "SELECT g FROM Guiaremision g WHERE g.facFechaAutorizacion = :facFechaAutorizacion")
    ,
    @NamedQuery(name = "Guiaremision.findByFacClaveAcceso", query = "SELECT g FROM Guiaremision g WHERE g.facClaveAcceso = :facClaveAcceso")
    ,
    @NamedQuery(name = "Guiaremision.findByCodTipoambiente", query = "SELECT g FROM Guiaremision g WHERE g.codTipoambiente = :codTipoambiente")
    ,
    @NamedQuery(name = "Guiaremision.findByFacClaveAutorizacion", query = "SELECT g FROM Guiaremision g WHERE g.facClaveAutorizacion = :facClaveAutorizacion")
    ,
    @NamedQuery(name = "Guiaremision.findByFacPath", query = "SELECT g FROM Guiaremision g WHERE g.facPath = :facPath")
    ,
    @NamedQuery(name = "Guiaremision.findByTipodocumentomod", query = "SELECT g FROM Guiaremision g WHERE g.tipodocumentomod = :tipodocumentomod")
    ,
    @NamedQuery(name = "Guiaremision.findByFacFechaSustento", query = "SELECT g FROM Guiaremision g WHERE g.facFechaSustento = :facFechaSustento")
    ,
    @NamedQuery(name = "Guiaremision.findByFacNumero", query = "SELECT g FROM Guiaremision g WHERE g.facNumero = :facNumero")})
public class Guiaremision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_guiaremision")
    private Integer idGuiaremision;
    @Column(name = "fac_fecha")
    @Temporal(TemporalType.DATE)
    private Date facFecha;
    @Column(name = "fechainitranspguia")
    @Temporal(TemporalType.DATE)
    private Date fechainitranspguia;
    @Column(name = "fechafintranspguia")
    @Temporal(TemporalType.DATE)
    private Date fechafintranspguia;
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
    @Column(name = "fac_numero_text_recibida")
    private String facNumeroTextRecibida;
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
    @Column(name = "documentoaduanerounico")
    private String Documentoaduanerounico;
    @Size(max = 2147483647)
    @Column(name = "tipodocumentomod")
    private String tipodocumentomod;
    @Column(name = "numplacaguia")
    private String numplacaguia;
    @Column(name = "motivoguia")
    private String motivoGuia;
    @Column(name = "partida")
    private String partida;
    @Column(name = "llegada")
    private String llegada;
    @Column(name = "fac_fecha_sustento")
    @Temporal(TemporalType.DATE)
    private Date facFechaSustento;
    @Column(name = "fac_numero")
    private Integer facNumero;
    /*ESTE CAMPO ES PARA VERIFICAR LAS GUIAS EMITIDAS Y RECIBIDAS */
    @Column(name = "tipo_guia")
    private String tipoGuia;
    @Column(name = "numero_guia_recibida")
    private String numeroGuiaRecibida;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;
    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura")
    @ManyToOne
    private Factura idFactura;
    @OneToMany(mappedBy = "idGuiaremision")
    private Collection<DetalleGuiaremision> detalleGuiaremisionCollection;
    @JoinColumn(name = "id_transportista", referencedColumnName = "id_transportista")
    @ManyToOne
    private Transportista idTransportista;

    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne
    private Cliente idCliente;

    public Guiaremision() {
    }

    public Guiaremision(Integer idGuiaremision) {
        this.idGuiaremision = idGuiaremision;
    }

    public Integer getIdGuiaremision() {
        return idGuiaremision;
    }

    public void setIdGuiaremision(Integer idGuiaremision) {
        this.idGuiaremision = idGuiaremision;
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

    public Integer getFacNumero() {
        return facNumero;
    }

    public void setFacNumero(Integer facNumero) {
        this.facNumero = facNumero;
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

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public String getDocumentoaduanerounico() {
        return Documentoaduanerounico;
    }

    public void setDocumentoaduanerounico(String Documentoaduanerounico) {
        this.Documentoaduanerounico = Documentoaduanerounico;
    }

    public Transportista getIdTransportista() {
        return idTransportista;
    }

    public String getNumplacaguia() {
        return numplacaguia;
    }

    public void setNumplacaguia(String numplacaguia) {
        this.numplacaguia = numplacaguia;
    }

    public Date getFechainitranspguia() {
        return fechainitranspguia;
    }

    public void setFechainitranspguia(Date fechainitranspguia) {
        this.fechainitranspguia = fechainitranspguia;
    }

    public Date getFechafintranspguia() {
        return fechafintranspguia;
    }

    public void setFechafintranspguia(Date fechafintranspguia) {
        this.fechafintranspguia = fechafintranspguia;
    }

    public void setIdTransportista(Transportista idTransportista) {
        this.idTransportista = idTransportista;
    }

    public String getMotivoGuia() {
        return motivoGuia;
    }

    public void setMotivoGuia(String motivoGuia) {
        this.motivoGuia = motivoGuia;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getLlegada() {
        return llegada;
    }

    public void setLlegada(String llegada) {
        this.llegada = llegada;
    }

    public String getTipoGuia() {
        return tipoGuia;
    }

    public void setTipoGuia(String tipoGuia) {
        this.tipoGuia = tipoGuia;
    }

    public String getNumeroGuiaRecibida() {
        return numeroGuiaRecibida;
    }

    public void setNumeroGuiaRecibida(String numeroGuiaRecibida) {
        this.numeroGuiaRecibida = numeroGuiaRecibida;
    }

    @XmlTransient
    public Collection<DetalleGuiaremision> getDetalleGuiaremisionCollection() {
        return detalleGuiaremisionCollection;
    }

    public String getFacNumeroTextRecibida() {
        return facNumeroTextRecibida;
    }

    public void setFacNumeroTextRecibida(String facNumeroTextRecibida) {
        this.facNumeroTextRecibida = facNumeroTextRecibida;
    }

    public void setDetalleGuiaremisionCollection(Collection<DetalleGuiaremision> detalleGuiaremisionCollection) {
        this.detalleGuiaremisionCollection = detalleGuiaremisionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGuiaremision != null ? idGuiaremision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Guiaremision)) {
            return false;
        }
        Guiaremision other = (Guiaremision) object;
        if ((this.idGuiaremision == null && other.idGuiaremision != null) || (this.idGuiaremision != null && !this.idGuiaremision.equals(other.idGuiaremision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Guiaremision[ idGuiaremision=" + idGuiaremision + " ]";
    }

}
