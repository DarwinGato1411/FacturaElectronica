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
@Table(name = "detalle_guiaremision")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleGuiaremision.findAll", query = "SELECT d FROM DetalleGuiaremision d"),
    @NamedQuery(name = "DetalleGuiaremision.findByIdDetalleGuiaremision", query = "SELECT d FROM DetalleGuiaremision d WHERE d.idDetalleGuiaremision = :idDetalleGuiaremision"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetCantidad", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detCantidad = :detCantidad"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetDescripcion", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detDescripcion = :detDescripcion"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetSubtotal", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detSubtotal = :detSubtotal"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetTotal", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detTotal = :detTotal"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetTipoVenta", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detTipoVenta = :detTipoVenta"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetIva", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detIva = :detIva"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetTotalconiva", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detTotalconiva = :detTotalconiva"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetPordescuento", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detPordescuento = :detPordescuento"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetValdescuento", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detValdescuento = :detValdescuento"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetSubtotaldescuento", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detSubtotaldescuento = :detSubtotaldescuento"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetTotaldescuento", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detTotaldescuento = :detTotaldescuento"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetTotaldescuentoiva", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detTotaldescuentoiva = :detTotaldescuentoiva"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetCantpordescuento", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detCantpordescuento = :detCantpordescuento"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetSubtotaldescuentoporcantidad", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detSubtotaldescuentoporcantidad = :detSubtotaldescuentoporcantidad"),
    @NamedQuery(name = "DetalleGuiaremision.findByDetCodTipoVenta", query = "SELECT d FROM DetalleGuiaremision d WHERE d.detCodTipoVenta = :detCodTipoVenta")})
public class DetalleGuiaremision implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_guiaremision")
    private Integer idDetalleGuiaremision;
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
    @JoinColumn(name = "id_guiaremision", referencedColumnName = "id_guiaremision")
    @ManyToOne
    private Guiaremision idGuiaremision;

    public DetalleGuiaremision() {
    }

    public DetalleGuiaremision(BigDecimal detCantidad, String detDescripcion, Producto idProducto, Guiaremision idGuiaremision) {
        this.detCantidad = detCantidad;
        this.detDescripcion = detDescripcion;
        this.idProducto = idProducto;
        this.idGuiaremision = idGuiaremision;
    }
    
    

    public DetalleGuiaremision(Integer idDetalleGuiaremision) {
        this.idDetalleGuiaremision = idDetalleGuiaremision;
    }

    public Integer getIdDetalleGuiaremision() {
        return idDetalleGuiaremision;
    }

    public void setIdDetalleGuiaremision(Integer idDetalleGuiaremision) {
        this.idDetalleGuiaremision = idDetalleGuiaremision;
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

    public Guiaremision getIdGuiaremision() {
        return idGuiaremision;
    }

    public void setIdGuiaremision(Guiaremision idGuiaremision) {
        this.idGuiaremision = idGuiaremision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleGuiaremision != null ? idDetalleGuiaremision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleGuiaremision)) {
            return false;
        }
        DetalleGuiaremision other = (DetalleGuiaremision) object;
        if ((this.idDetalleGuiaremision == null && other.idDetalleGuiaremision != null) || (this.idDetalleGuiaremision != null && !this.idDetalleGuiaremision.equals(other.idDetalleGuiaremision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.DetalleGuiaremision[ idDetalleGuiaremision=" + idDetalleGuiaremision + " ]";
    }
    
}
