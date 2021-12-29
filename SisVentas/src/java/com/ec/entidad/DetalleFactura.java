/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "detalle_factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleFactura.findAll", query = "SELECT d FROM DetalleFactura d")
    ,
    @NamedQuery(name = "DetalleFactura.finForIdFact", query = "SELECT d FROM DetalleFactura d where d.idFactura.idFactura =:idFactura")
    ,
    @NamedQuery(name = "DetalleFactura.findByIdDetalle", query = "SELECT d FROM DetalleFactura d WHERE d.idDetalle = :idDetalle")
    ,
    @NamedQuery(name = "DetalleFactura.findByDetCantidad", query = "SELECT d FROM DetalleFactura d WHERE d.detCantidad = :detCantidad")
    ,
    @NamedQuery(name = "DetalleFactura.findByDetDescripcion", query = "SELECT d FROM DetalleFactura d WHERE d.detDescripcion = :detDescripcion")
    ,
    @NamedQuery(name = "DetalleFactura.findByDetSubtotal", query = "SELECT d FROM DetalleFactura d WHERE d.detSubtotal = :detSubtotal")
    ,
    @NamedQuery(name = "DetalleFactura.findByDetTotal", query = "SELECT d FROM DetalleFactura d WHERE d.detTotal = :detTotal")})
public class DetalleFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "det_cantidad")
    private BigDecimal detCantidad;
    @Column(name = "det_descripcion")
    private String detDescripcion;
    @Column(name = "det_subtotal")
    private BigDecimal detSubtotal;
    @Column(name = "det_total")
    private BigDecimal detTotal;
    @Column(name = "det_iva")
    private BigDecimal detIva;
    @Column(name = "det_totalconiva")
    private BigDecimal detTotalconiva;
    @Column(name = "det_pordescuento")
    private BigDecimal detPordescuento;
    @Column(name = "det_valdescuento")
    private BigDecimal detValdescuento;
    @Column(name = "det_subtotaldescuento")
    private BigDecimal detSubtotaldescuento;
    @Column(name = "det_totaldescuento")
    private BigDecimal detTotaldescuento;
    @Column(name = "det_totaldescuentoiva")
    private BigDecimal detTotaldescuentoiva;
    @Column(name = "det_cantpordescuento")
    private BigDecimal detCantpordescuento;
    @Column(name = "det_cod_iva")
    private String detCodIva;
    @Column(name = "det_cod_porcentaje")
    private String detCodPorcentaje;
    @Column(name = "det_subtotaldescuentoporcantidad")
    private BigDecimal detSubtotaldescuentoporcantidad;
    @Size(max = 100)
    @Column(name = "det_tipo_venta")
    private String detTipoVenta;
    @Size(max = 2)
    @Column(name = "det_cod_tipo_venta")
    private String detCodTipoVenta;

    @Column(name = "tipo_identificacion_propietario")
    private String tipoIdentificacionPropietario;
    @Column(name = "det_camvcpn")
    private String detCamvcpn;
    @Column(name = "det_serialvin")
    private String detSerialvin;
    @Column(name = "det_atributo")
    private String detAtributo;
    @Column(name = "tipodir")
    private String tipodir;
    @Column(name = "calle")
    private String calle;
    @Column(name = "numero")
    private String numero;
    @Column(name = "interseccion")
    private String interseccion;
    @Column(name = "tipotel")
    private String tipotel;
    @Column(name = "provincia")
    private String provincia;
    @Column(name = "numerotel")
    private String numerotel;
    @Column(name = "codigo_canton_matriculacion")
    private String codigoCantonMatriculacion;

    @Column(name = "det_tarifa")
    private BigDecimal detTarifa;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;
    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura")
    @ManyToOne
    private Factura idFactura;

    public DetalleFactura() {
    }

    public DetalleFactura(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public DetalleFactura(BigDecimal detCantidad,
            String detDescripcion,
            BigDecimal detSubtotal,
            BigDecimal detTotal,
            Producto idProducto,
            Factura idFactura) {
        this.detCantidad = detCantidad;
        this.detDescripcion = detDescripcion;
        this.detSubtotal = detSubtotal;
        this.detTotal = detTotal;
        this.idProducto = idProducto;
        this.idFactura = idFactura;
    }

    public DetalleFactura(BigDecimal detCantidad,
            String detDescripcion,
            BigDecimal detSubtotal,
            BigDecimal detTotal,
            Producto idProducto,
            Factura idFactura, String detTipoVenta) {
        this.detCantidad = detCantidad;
        this.detDescripcion = detDescripcion;
        this.detSubtotal = detSubtotal;
        this.detTotal = detTotal;
        this.idProducto = idProducto;
        this.idFactura = idFactura;
        this.detTipoVenta = detTipoVenta;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public BigDecimal getDetCantidad() {
        return detCantidad;
    }

    public void setDetCantidad(BigDecimal detCantidad) {
        this.detCantidad = detCantidad;
    }

    public String getDetDescripcion() {
        return detDescripcion;
    }

    public void setDetDescripcion(String detDescripcion) {
        this.detDescripcion = detDescripcion;
    }

    public BigDecimal getDetSubtotal() {
        return detSubtotal;
    }

    public void setDetSubtotal(BigDecimal detSubtotal) {
        this.detSubtotal = detSubtotal;
    }

    public BigDecimal getDetTotal() {
        return detTotal;
    }

    public void setDetTotal(BigDecimal detTotal) {
        this.detTotal = detTotal;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Factura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Factura idFactura) {
        this.idFactura = idFactura;
    }

    public String getDetTipoVenta() {
        return detTipoVenta;
    }

    public void setDetTipoVenta(String detTipoVenta) {
        this.detTipoVenta = detTipoVenta;
    }

    public BigDecimal getDetIva() {
        return detIva;
    }

    public void setDetIva(BigDecimal detIva) {
        this.detIva = detIva;
    }

    public BigDecimal getDetTotalconiva() {
        return detTotalconiva;
    }

    public void setDetTotalconiva(BigDecimal detTotalconiva) {
        this.detTotalconiva = detTotalconiva;
    }

    public BigDecimal getDetPordescuento() {
        return detPordescuento;
    }

    public void setDetPordescuento(BigDecimal detPordescuento) {
        this.detPordescuento = detPordescuento;
    }

    public BigDecimal getDetValdescuento() {
        return detValdescuento;
    }

    public void setDetValdescuento(BigDecimal detValdescuento) {
        this.detValdescuento = detValdescuento;
    }

    public BigDecimal getDetSubtotaldescuento() {
        return detSubtotaldescuento;
    }

    public void setDetSubtotaldescuento(BigDecimal detSubtotaldescuento) {
        this.detSubtotaldescuento = detSubtotaldescuento;
    }

    public BigDecimal getDetTotaldescuento() {
        return detTotaldescuento;
    }

    public void setDetTotaldescuento(BigDecimal detTotaldescuento) {
        this.detTotaldescuento = detTotaldescuento;
    }

    public BigDecimal getDetTotaldescuentoiva() {
        return detTotaldescuentoiva;
    }

    public void setDetTotaldescuentoiva(BigDecimal detTotaldescuentoiva) {
        this.detTotaldescuentoiva = detTotaldescuentoiva;
    }

    public BigDecimal getDetCantpordescuento() {
        return detCantpordescuento;
    }

    public void setDetCantpordescuento(BigDecimal detCantpordescuento) {
        this.detCantpordescuento = detCantpordescuento;
    }

    public BigDecimal getDetSubtotaldescuentoporcantidad() {
        return detSubtotaldescuentoporcantidad;
    }

    public void setDetSubtotaldescuentoporcantidad(BigDecimal detSubtotaldescuentoporcantidad) {
        this.detSubtotaldescuentoporcantidad = detSubtotaldescuentoporcantidad;
    }

    public String getDetCodTipoVenta() {
        return detCodTipoVenta;
    }

    public void setDetCodTipoVenta(String detCodTipoVenta) {
        this.detCodTipoVenta = detCodTipoVenta;
    }

    public String getDetCodIva() {
        return detCodIva;
    }

    public void setDetCodIva(String detCodIva) {
        this.detCodIva = detCodIva;
    }

    public String getDetCodPorcentaje() {
        return detCodPorcentaje;
    }

    public void setDetCodPorcentaje(String detCodPorcentaje) {
        this.detCodPorcentaje = detCodPorcentaje;
    }

    public BigDecimal getDetTarifa() {
        return detTarifa;
    }

    public void setDetTarifa(BigDecimal detTarifa) {
        this.detTarifa = detTarifa;
    }

    public String getDetCamvcpn() {
        return detCamvcpn;
    }

    public void setDetCamvcpn(String detCamvcpn) {
        this.detCamvcpn = detCamvcpn;
    }

    public String getDetSerialvin() {
        return detSerialvin;
    }

    public void setDetSerialvin(String detSerialvin) {
        this.detSerialvin = detSerialvin;
    }

    public String getDetAtributo() {
        return detAtributo;
    }

    public void setDetAtributo(String detAtributo) {
        this.detAtributo = detAtributo;
    }

    public String getTipodir() {
        return tipodir;
    }

    public void setTipodir(String tipodir) {
        this.tipodir = tipodir;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getInterseccion() {
        return interseccion;
    }

    public void setInterseccion(String interseccion) {
        this.interseccion = interseccion;
    }

    public String getTipotel() {
        return tipotel;
    }

    public void setTipotel(String tipotel) {
        this.tipotel = tipotel;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getNumerotel() {
        return numerotel;
    }

    public void setNumerotel(String numerotel) {
        this.numerotel = numerotel;
    }

    public String getTipoIdentificacionPropietario() {
        return tipoIdentificacionPropietario;
    }

    public void setTipoIdentificacionPropietario(String tipoIdentificacionPropietario) {
        this.tipoIdentificacionPropietario = tipoIdentificacionPropietario;
    }

    public String getCodigoCantonMatriculacion() {
        return codigoCantonMatriculacion;
    }

    public void setCodigoCantonMatriculacion(String codigoCantonMatriculacion) {
        this.codigoCantonMatriculacion = codigoCantonMatriculacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFactura)) {
            return false;
        }
        DetalleFactura other = (DetalleFactura) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.DetalleFactura[ idDetalle=" + idDetalle + " ]";
    }
}
