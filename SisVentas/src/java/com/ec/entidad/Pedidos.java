/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin Morocho
 */
@Entity
@Table(name = "pedidos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedidos.findAll", query = "SELECT p FROM Pedidos p"),
    @NamedQuery(name = "Pedidos.findAllPendientes", query = "SELECT p FROM Pedidos p WHERE p.pedEstado='SOLICITADO'"),
    @NamedQuery(name = "Pedidos.findAllComprado", query = "SELECT p FROM Pedidos p WHERE p.pedEstado='CONFIRMADO'"),
    @NamedQuery(name = "Pedidos.findByCodPedido", query = "SELECT p FROM Pedidos p WHERE p.codPedido = :codPedido"),
    @NamedQuery(name = "Pedidos.findByPedCantidad", query = "SELECT p FROM Pedidos p WHERE p.pedCantidad = :pedCantidad"),
    @NamedQuery(name = "Pedidos.findByPedDescripcion", query = "SELECT p FROM Pedidos p WHERE p.pedDescripcion = :pedDescripcion"),
    @NamedQuery(name = "Pedidos.findByPedFecha", query = "SELECT p FROM Pedidos p WHERE p.pedFecha = :pedFecha"),
    @NamedQuery(name = "Pedidos.findBetweenPedFecha", query = "SELECT p FROM Pedidos p WHERE p.pedFecha BETWEEN :inicio AND :fin"),
    @NamedQuery(name = "Pedidos.findByPedEstado", query = "SELECT p FROM Pedidos p WHERE p.pedEstado = :pedEstado"),
    @NamedQuery(name = "Pedidos.findByPedProveedor", query = "SELECT p FROM Pedidos p WHERE p.pedProveedor = :pedProveedor")})
public class Pedidos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_pedido")
    private Integer codPedido;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ped_cantidad")
    private BigDecimal pedCantidad;
    @Size(max = 200)
    @Column(name = "ped_descripcion")
    private String pedDescripcion;
    @Column(name = "ped_fecha")
    @Temporal(TemporalType.DATE)
    private Date pedFecha;
    @Size(max = 45)
    @Column(name = "ped_estado")
    private String pedEstado;
    @Size(max = 150)
    @Column(name = "ped_proveedor")
    private String pedProveedor;

    public Pedidos() {
    }

    public Pedidos(BigDecimal pedCantidad, String pedDescripcion, Date pedFecha, String pedEstado, String pedProveedor) {
        this.pedCantidad = pedCantidad;
        this.pedDescripcion = pedDescripcion;
        this.pedFecha = pedFecha;
        this.pedEstado = pedEstado;
        this.pedProveedor = pedProveedor;
    }

    public Pedidos(Integer codPedido) {
        this.codPedido = codPedido;
    }

    public Integer getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(Integer codPedido) {
        this.codPedido = codPedido;
    }

    public BigDecimal getPedCantidad() {
        return pedCantidad;
    }

    public void setPedCantidad(BigDecimal pedCantidad) {
        this.pedCantidad = pedCantidad;
    }

    public String getPedDescripcion() {
        return pedDescripcion;
    }

    public void setPedDescripcion(String pedDescripcion) {
        this.pedDescripcion = pedDescripcion;
    }

    public Date getPedFecha() {
        return pedFecha;
    }

    public void setPedFecha(Date pedFecha) {
        this.pedFecha = pedFecha;
    }

    public String getPedEstado() {
        return pedEstado;
    }

    public void setPedEstado(String pedEstado) {
        this.pedEstado = pedEstado;
    }

    public String getPedProveedor() {
        return pedProveedor;
    }

    public void setPedProveedor(String pedProveedor) {
        this.pedProveedor = pedProveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codPedido != null ? codPedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedidos)) {
            return false;
        }
        Pedidos other = (Pedidos) object;
        if ((this.codPedido == null && other.codPedido != null) || (this.codPedido != null && !this.codPedido.equals(other.codPedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Pedidos[ codPedido=" + codPedido + " ]";
    }
    
}
