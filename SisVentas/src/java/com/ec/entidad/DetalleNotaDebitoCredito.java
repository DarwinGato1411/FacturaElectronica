/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
 * @author Darwin
 */
@Entity
@Table(name = "detalle_nota_debito_credito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleNotaDebitoCredito.findAll", query = "SELECT d FROM DetalleNotaDebitoCredito d")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByIdNota", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.idNota.idNota = :idNota")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByIdDetalleNota", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.idDetalleNota = :idDetalleNota")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetCantidad", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detCantidad = :detCantidad")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetDescripcion", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detDescripcion = :detDescripcion")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetSubtotal", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detSubtotal = :detSubtotal")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetTotal", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detTotal = :detTotal")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetTipoVenta", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detTipoVenta = :detTipoVenta")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetIva", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detIva = :detIva")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetTotalconiva", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detTotalconiva = :detTotalconiva")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetPordescuento", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detPordescuento = :detPordescuento")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetValdescuento", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detValdescuento = :detValdescuento")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetSubtotaldescuento", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detSubtotaldescuento = :detSubtotaldescuento")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetTotaldescuento", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detTotaldescuento = :detTotaldescuento")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetTotaldescuentoiva", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detTotaldescuentoiva = :detTotaldescuentoiva")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetCantpordescuento", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detCantpordescuento = :detCantpordescuento")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetSubtotaldescuentoporcantidad", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detSubtotaldescuentoporcantidad = :detSubtotaldescuentoporcantidad")
    ,
    @NamedQuery(name = "DetalleNotaDebitoCredito.findByDetCodTipoVenta", query = "SELECT d FROM DetalleNotaDebitoCredito d WHERE d.detCodTipoVenta = :detCodTipoVenta")})
public class DetalleNotaDebitoCredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_nota")
    private Integer idDetalleNota;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "det_cantidad")
    private BigDecimal detCantidad;
    @Size(max = 100)
    @Column(name = "det_descripcion")
    private String detDescripcion;
    @Column(name = "det_subtotal")
    private BigDecimal detSubtotal;
    @Column(name = "det_total")
    private BigDecimal detTotal;
    @Size(max = 45)
    @Column(name = "det_tipo_venta")
    private String detTipoVenta;
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
    @Column(name = "det_subtotaldescuentoporcantidad")
    private BigDecimal detSubtotaldescuentoporcantidad;
    @Size(max = 2)
    @Column(name = "det_cod_tipo_venta")
    private String detCodTipoVenta;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;
    @JoinColumn(name = "id_nota", referencedColumnName = "id_nota")
    @ManyToOne
    private NotaCreditoDebito idNota;

    public DetalleNotaDebitoCredito() {
    }

    public DetalleNotaDebitoCredito(BigDecimal detCantidad,
            String detDescripcion,
            BigDecimal detSubtotal,
            BigDecimal detTotal,
            Producto idProducto,
            NotaCreditoDebito idNota) {
        this.detCantidad = detCantidad;
        this.detDescripcion = detDescripcion;
        this.detSubtotal = detSubtotal;
        this.detTotal = detTotal;
        this.idProducto = idProducto;
        this.idNota = idNota;
    }

    public DetalleNotaDebitoCredito(BigDecimal detCantidad,
            String detDescripcion,
            BigDecimal detSubtotal,
            BigDecimal detTotal,
            Producto idProducto,
            NotaCreditoDebito idNota, String detTipoVenta) {
        this.detCantidad = detCantidad;
        this.detDescripcion = detDescripcion;
        this.detSubtotal = detSubtotal;
        this.detTotal = detTotal;
        this.idProducto = idProducto;
        this.idNota = idNota;
        this.detTipoVenta = detTipoVenta;
    }

    public DetalleNotaDebitoCredito(Integer idDetalleNota) {
        this.idDetalleNota = idDetalleNota;
    }

    public Integer getIdDetalleNota() {
        return idDetalleNota;
    }

    public void setIdDetalleNota(Integer idDetalleNota) {
        this.idDetalleNota = idDetalleNota;
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

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public NotaCreditoDebito getIdNota() {
        return idNota;
    }

    public void setIdNota(NotaCreditoDebito idNota) {
        this.idNota = idNota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleNota != null ? idDetalleNota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleNotaDebitoCredito)) {
            return false;
        }
        DetalleNotaDebitoCredito other = (DetalleNotaDebitoCredito) object;
        if ((this.idDetalleNota == null && other.idDetalleNota != null) || (this.idDetalleNota != null && !this.idDetalleNota.equals(other.idDetalleNota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.DetalleNotaDebitoCredito[ idDetalleNota=" + idDetalleNota + " ]";
    }

}
