/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "detalle_kardex")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleKardex.findAll", query = "SELECT d FROM DetalleKardex d")
    ,
    @NamedQuery(name = "DetalleKardex.findByIdDetalleKardex", query = "SELECT d FROM DetalleKardex d WHERE d.idDetalleKardex = :idDetalleKardex")
    ,
    @NamedQuery(name = "DetalleKardex.findByDetkFechakardex", query = "SELECT d FROM DetalleKardex d WHERE d.detkFechakardex = :detkFechakardex")
    ,
    @NamedQuery(name = "DetalleKardex.findByDetkTipokardex", query = "SELECT d FROM DetalleKardex d WHERE d.detkTipokardex = :detkTipokardex")
    ,
    @NamedQuery(name = "DetalleKardex.findByDetkCantidad", query = "SELECT d FROM DetalleKardex d WHERE d.detkCantidad = :detkCantidad")
    ,
    @NamedQuery(name = "DetalleKardex.findByDetkFechacreacion", query = "SELECT d FROM DetalleKardex d WHERE d.detkFechacreacion = :detkFechacreacion")
    ,
    @NamedQuery(name = "DetalleKardex.findByIdKardex", query = "SELECT d FROM DetalleKardex d WHERE d.idKardex = :idKardex")
    ,
    @NamedQuery(name = "DetalleKardex.findByDetkFechacreacion", query = "SELECT d FROM DetalleKardex d WHERE d.detkFechacreacion = :detkFechacreacion")
    ,
    @NamedQuery(name = "DetalleKardex.findByDetkKardexmanual", query = "SELECT d FROM DetalleKardex d WHERE d.detkKardexmanual = :detkKardexmanual")
    ,
    @NamedQuery(name = "DetalleKardex.findByDetkDetalles", query = "SELECT d FROM DetalleKardex d WHERE d.detkDetalles = :detkDetalles")})
public class DetalleKardex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_kardex")
    private Integer idDetalleKardex;
    @Column(name = "detk_fechakardex")
    @Temporal(TemporalType.DATE)
    private Date detkFechakardex;
    @Column(name = "detk_tipokardex")
    private Integer detkTipokardex;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "detk_cantidad")
    private BigDecimal detkCantidad;

    @Column(name = "detk_fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date detkFechacreacion;
    @Column(name = "detk_kardexmanual")
    private Boolean detkKardexmanual;
    @Size(max = 300)
    @Column(name = "detk_detalles")
    private String detkDetalles;

    @Column(name = "id_ingreso")
    private Integer idIngreso;
    @Column(name = "idventa")
    private Integer idVenta;
    @JoinColumn(name = "id_kardex", referencedColumnName = "id_kardex")
    @ManyToOne
    private Kardex idKardex;
    @JoinColumn(name = "id_tipokardex", referencedColumnName = "id_tipokardex")
    @ManyToOne
    private Tipokardex idTipokardex;
    @JoinColumn(name = "id_compra", referencedColumnName = "id_cabecera")
    @ManyToOne
    private CabeceraCompra idCompra;
    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura")
    @ManyToOne
    private Factura idFactura;

    @Column(name = "detk_ingreso_cantidad_sin_transformar")
    private BigDecimal detkIngresoCantidadSinTransformar;
    @Column(name = "detk_unidad_origen")
    private String detkUnidadOrigen;
    @Column(name = "detk_unidad_fin")
    private String detkUnidadFin;

    public DetalleKardex() {
    }

    public DetalleKardex(Integer idDetalleKardex) {
        this.idDetalleKardex = idDetalleKardex;
    }

    public Integer getIdDetalleKardex() {
        return idDetalleKardex;
    }

    public void setIdDetalleKardex(Integer idDetalleKardex) {
        this.idDetalleKardex = idDetalleKardex;
    }

    public Date getDetkFechakardex() {
        return detkFechakardex;
    }

    public void setDetkFechakardex(Date detkFechakardex) {
        this.detkFechakardex = detkFechakardex;
    }

    public Integer getDetkTipokardex() {
        return detkTipokardex;
    }

    public void setDetkTipokardex(Integer detkTipokardex) {
        this.detkTipokardex = detkTipokardex;
    }

    public BigDecimal getDetkCantidad() {
        return detkCantidad;
    }

    public void setDetkCantidad(BigDecimal detkCantidad) {
        this.detkCantidad = detkCantidad;
    }

    public Date getDetkFechacreacion() {
        return detkFechacreacion;
    }

    public void setDetkFechacreacion(Date detkFechacreacion) {
        this.detkFechacreacion = detkFechacreacion;
    }

    public Boolean getDetkKardexmanual() {
        return detkKardexmanual;
    }

    public void setDetkKardexmanual(Boolean detkKardexmanual) {
        this.detkKardexmanual = detkKardexmanual;
    }

    public String getDetkDetalles() {
        return detkDetalles;
    }

    public void setDetkDetalles(String detkDetalles) {
        this.detkDetalles = detkDetalles;
    }

    public Integer getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(Integer idIngreso) {
        this.idIngreso = idIngreso;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Kardex getIdKardex() {
        return idKardex;
    }

    public void setIdKardex(Kardex idKardex) {
        this.idKardex = idKardex;
    }

    public Tipokardex getIdTipokardex() {
        return idTipokardex;
    }

    public void setIdTipokardex(Tipokardex idTipokardex) {
        this.idTipokardex = idTipokardex;
    }

    public CabeceraCompra getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(CabeceraCompra idCompra) {
        this.idCompra = idCompra;
    }

    public Factura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Factura idFactura) {
        this.idFactura = idFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleKardex != null ? idDetalleKardex.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleKardex)) {
            return false;
        }
        DetalleKardex other = (DetalleKardex) object;
        if ((this.idDetalleKardex == null && other.idDetalleKardex != null) || (this.idDetalleKardex != null && !this.idDetalleKardex.equals(other.idDetalleKardex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.DetalleKardex[ idDetalleKardex=" + idDetalleKardex + " ]";
    }

    public BigDecimal getDetkIngresoCantidadSinTransformar() {
        return detkIngresoCantidadSinTransformar;
    }

    public void setDetkIngresoCantidadSinTransformar(BigDecimal detkIngresoCantidadSinTransformar) {
        this.detkIngresoCantidadSinTransformar = detkIngresoCantidadSinTransformar;
    }

    public String getDetkUnidadOrigen() {
        return detkUnidadOrigen;
    }

    public void setDetkUnidadOrigen(String detkUnidadOrigen) {
        this.detkUnidadOrigen = detkUnidadOrigen;
    }

    public String getDetkUnidadFin() {
        if (detkUnidadFin==null) {
            detkUnidadFin="S/U";
        }
        return detkUnidadFin;
    }

    public void setDetkUnidadFin(String detkUnidadFin) {
        this.detkUnidadFin = detkUnidadFin;
    }

    
}
