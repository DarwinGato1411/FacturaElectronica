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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@Entity
@Table(name = "combo_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComboProducto.findAll", query = "SELECT c FROM ComboProducto c")})
public class ComboProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_combo")
    private Integer idCombo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "com_cantidad")
    private BigDecimal comCantidad;
    @Column(name = "com_fecha")
    @Temporal(TemporalType.DATE)
    private Date comFecha;
    @Column(name = "com_detalle")
    private String comDetalle;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProducto;

    @JoinColumn(name = "id_producto_padre", referencedColumnName = "id_producto")
    @ManyToOne
    private Producto idProductoPadre;

    public ComboProducto() {
    }

    public ComboProducto(BigDecimal comCantidad, String comDetalle, Producto idProducto, Producto idProductoPadre) {
        this.comCantidad = comCantidad;
        this.comDetalle = comDetalle;
        this.idProducto = idProducto;
        this.idProductoPadre = idProductoPadre;
    }

    public ComboProducto(Integer idCombo) {
        this.idCombo = idCombo;
    }

    public Integer getIdCombo() {
        return idCombo;
    }

    public void setIdCombo(Integer idCombo) {
        this.idCombo = idCombo;
    }

    public BigDecimal getComCantidad() {
        return comCantidad;
    }

    public void setComCantidad(BigDecimal comCantidad) {
        this.comCantidad = comCantidad;
    }

    public Date getComFecha() {
        return comFecha;
    }

    public void setComFecha(Date comFecha) {
        this.comFecha = comFecha;
    }

    public String getComDetalle() {
        return comDetalle;
    }

    public void setComDetalle(String comDetalle) {
        this.comDetalle = comDetalle;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Producto getIdProductoPadre() {
        return idProductoPadre;
    }

    public void setIdProductoPadre(Producto idProductoPadre) {
        this.idProductoPadre = idProductoPadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCombo != null ? idCombo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComboProducto)) {
            return false;
        }
        ComboProducto other = (ComboProducto) object;
        if ((this.idCombo == null && other.idCombo != null) || (this.idCombo != null && !this.idCombo.equals(other.idCombo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.ComboProducto[ idCombo=" + idCombo + " ]";
    }

}
