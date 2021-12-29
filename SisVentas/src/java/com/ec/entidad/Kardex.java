/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "kardex")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kardex.findAll", query = "SELECT k FROM Kardex k")
    , @NamedQuery(name = "Kardex.findByIdKardex", query = "SELECT k FROM Kardex k WHERE k.idKardex = :idKardex")
    , @NamedQuery(name = "Kardex.findByKarTotal", query = "SELECT k FROM Kardex k WHERE k.karTotal = :karTotal")
    , @NamedQuery(name = "Kardex.findByKarFecha", query = "SELECT k FROM Kardex k WHERE k.karFecha = :karFecha")
    , @NamedQuery(name = "Kardex.findByIdProducto", query = "SELECT k FROM Kardex k WHERE k.idProducto = :idProducto")
    , @NamedQuery(name = "Kardex.findByKarDetalle", query = "SELECT k FROM Kardex k WHERE k.karDetalle = :karDetalle")
    , @NamedQuery(name = "Kardex.findByKarEstado", query = "SELECT k FROM Kardex k WHERE k.karEstado = :karEstado")
    , @NamedQuery(name = "Kardex.findByKarFechaIngreso", query = "SELECT k FROM Kardex k WHERE k.karFechaIngreso = :karFechaIngreso")
    , @NamedQuery(name = "Kardex.findByKarFechaKardex", query = "SELECT k FROM Kardex k WHERE k.karFechaKardex = :karFechaKardex")
    , @NamedQuery(name = "Kardex.findByKarIsActivo", query = "SELECT k FROM Kardex k WHERE k.karIsActivo = :karIsActivo")})
public class Kardex implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_kardex")
    private Integer idKardex;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "kar_total")
    private BigDecimal karTotal;
    @Column(name = "kar_fecha")
    @Temporal(TemporalType.DATE)
    private Date karFecha;
    @Column(name = "kar_detalle")
    private String karDetalle;
    @Column(name = "kar_estado")
    private String karEstado;
    @Column(name = "kar_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date karFechaIngreso;
    @Column(name = "kar_fecha_kardex")
    @Temporal(TemporalType.DATE)
    private Date karFechaKardex;
    @Column(name = "kar_is_activo")
    private Boolean karIsActivo;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;
    @OneToMany(mappedBy = "idKardex")
    private Collection<DetalleKardex> detalleKardexCollection;

    @Transient
    private Boolean verificaStock;

    public Kardex() {
    }

    public Kardex(Integer idKardex) {
        this.idKardex = idKardex;
    }

    public Integer getIdKardex() {
        return idKardex;
    }

    public void setIdKardex(Integer idKardex) {
        this.idKardex = idKardex;
    }

    public BigDecimal getKarTotal() {
        if (karTotal == null) {
            karTotal = BigDecimal.ZERO;
        } else {
            karTotal.setScale(2, RoundingMode.FLOOR);
        }
        return karTotal;
    }

    public void setKarTotal(BigDecimal karTotal) {
        this.karTotal = karTotal;
    }

    public Date getKarFecha() {
        return karFecha;
    }

    public void setKarFecha(Date karFecha) {
        this.karFecha = karFecha;
    }

    public String getKarDetalle() {
        return karDetalle;
    }

    public void setKarDetalle(String karDetalle) {
        this.karDetalle = karDetalle;
    }

    public String getKarEstado() {
        return karEstado;
    }

    public void setKarEstado(String karEstado) {
        this.karEstado = karEstado;
    }

    public Date getKarFechaIngreso() {
        return karFechaIngreso;
    }

    public void setKarFechaIngreso(Date karFechaIngreso) {
        this.karFechaIngreso = karFechaIngreso;
    }

    public Date getKarFechaKardex() {
        return karFechaKardex;
    }

    public void setKarFechaKardex(Date karFechaKardex) {
        this.karFechaKardex = karFechaKardex;
    }

    public Boolean getKarIsActivo() {
        return karIsActivo;
    }

    public void setKarIsActivo(Boolean karIsActivo) {
        this.karIsActivo = karIsActivo;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @XmlTransient
    public Collection<DetalleKardex> getDetalleKardexCollection() {
        return detalleKardexCollection;
    }

    public void setDetalleKardexCollection(Collection<DetalleKardex> detalleKardexCollection) {
        this.detalleKardexCollection = detalleKardexCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKardex != null ? idKardex.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kardex)) {
            return false;
        }
        Kardex other = (Kardex) object;
        if ((this.idKardex == null && other.idKardex != null) || (this.idKardex != null && !this.idKardex.equals(other.idKardex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Kardex[ idKardex=" + idKardex + " ]";
    }

    public Boolean getVerificaStock() {
        BigDecimal minimo = BigDecimal.ZERO;
        BigDecimal stock = BigDecimal.ZERO;
        if (idProducto.getProdCantMinima() != null) {
            minimo = idProducto.getProdCantMinima();
        }

        if (karTotal != null) {
            stock = karTotal;
        }

        if (minimo.doubleValue() == 0 && stock.doubleValue() == 0) {
            verificaStock = Boolean.FALSE;
        } else if (stock.doubleValue() < minimo.doubleValue()) {
            verificaStock = Boolean.FALSE;
        } else {
            verificaStock = Boolean.TRUE;
        }

        return verificaStock;
    }

}
