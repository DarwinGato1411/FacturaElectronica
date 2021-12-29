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
 * @author gato
 */
@Entity
@Table(name = "cierre_caja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CierreCaja.findAll", query = "SELECT c FROM CierreCaja c")
    ,
    @NamedQuery(name = "CierreCaja.findByIdCierre", query = "SELECT c FROM CierreCaja c WHERE c.idCierre = :idCierre")
    ,
    @NamedQuery(name = "CierreCaja.findByCieDescripcion", query = "SELECT c FROM CierreCaja c WHERE c.cieDescripcion = :cieDescripcion")
    ,
    @NamedQuery(name = "CierreCaja.findByCieFecha", query = "SELECT c FROM CierreCaja c WHERE c.cieFecha = :cieFecha")
    ,
    @NamedQuery(name = "CierreCaja.findByCieValor", query = "SELECT c FROM CierreCaja c WHERE c.cieValor = :cieValor")
    ,
    @NamedQuery(name = "CierreCaja.findByCieEstado", query = "SELECT c FROM CierreCaja c WHERE c.cieEstado = :cieEstado")
    ,
    @NamedQuery(name = "CierreCaja.findByCieCuadre", query = "SELECT c FROM CierreCaja c WHERE c.cieCuadre = :cieCuadre")})
public class CierreCaja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cierre")
    private Integer idCierre;
    @Size(max = 150)
    @Column(name = "cie_descripcion")
    private String cieDescripcion;
    @Column(name = "cie_fecha")
    @Temporal(TemporalType.DATE)
    private Date cieFecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cie_valor")
    private BigDecimal cieValor;
    @Size(max = 20)
    @Column(name = "cie_estado")
    private String cieEstado;
    @Column(name = "cie_cuadre")
    private BigDecimal cieCuadre;
    @Column(name = "cie_diferencia")
    private BigDecimal cieDiferencia;
    @Column(name = "cie_valor_inicio")
    private BigDecimal cieValorInicio;
    @Column(name = "cie_total")
    private BigDecimal cieTotal;
    @Column(name = "cie_credito")
    private BigDecimal cieCredito;
    @Column(name = "cir_recaudado")
    private BigDecimal cirRecaudado;
    @Column(name = "cie_nota_venta")
    private BigDecimal cieNotaVenta;

    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario idUsuario;

    @Column(name = "cie_cerrada")
    private Boolean cieCerrada;

    public CierreCaja() {
    }

    public CierreCaja(Integer idCierre) {
        this.idCierre = idCierre;
    }

    public Integer getIdCierre() {
        return idCierre;
    }

    public void setIdCierre(Integer idCierre) {
        this.idCierre = idCierre;
    }

    public String getCieDescripcion() {
        return cieDescripcion;
    }

    public void setCieDescripcion(String cieDescripcion) {
        this.cieDescripcion = cieDescripcion;
    }

    public Date getCieFecha() {
        return cieFecha;
    }

    public void setCieFecha(Date cieFecha) {
        this.cieFecha = cieFecha;
    }

    public BigDecimal getCieValor() {
        return cieValor;
    }

    public void setCieValor(BigDecimal cieValor) {
        this.cieValor = cieValor;
    }

    public String getCieEstado() {
        return cieEstado;
    }

    public void setCieEstado(String cieEstado) {
        this.cieEstado = cieEstado;
    }

    public BigDecimal getCieCuadre() {
        return cieCuadre;
    }

    public void setCieCuadre(BigDecimal cieCuadre) {
        this.cieCuadre = cieCuadre;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getCieDiferencia() {
        return cieDiferencia;
    }

    public BigDecimal getCieValorInicio() {
        return cieValorInicio;
    }

    public void setCieValorInicio(BigDecimal cieValorInicio) {
        this.cieValorInicio = cieValorInicio;
    }

    public void setCieDiferencia(BigDecimal cieDiferencia) {
        this.cieDiferencia = cieDiferencia;
    }

    public BigDecimal getCieTotal() {
        return cieTotal;
    }

    public void setCieTotal(BigDecimal cieTotal) {
        this.cieTotal = cieTotal;
    }

    public BigDecimal getCieCredito() {
        return cieCredito;
    }

    public void setCieCredito(BigDecimal cieCredito) {
        this.cieCredito = cieCredito;
    }

    public BigDecimal getCirRecaudado() {
        return cirRecaudado;
    }

    public void setCirRecaudado(BigDecimal cirRecaudado) {
        this.cirRecaudado = cirRecaudado;
    }

    public BigDecimal getCieNotaVenta() {
        return cieNotaVenta;
    }

    public void setCieNotaVenta(BigDecimal cieNotaVenta) {
        this.cieNotaVenta = cieNotaVenta;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCierre != null ? idCierre.hashCode() : 0);
        return hash;
    }

    public Boolean getCieCerrada() {
        return cieCerrada;
    }

    public void setCieCerrada(Boolean cieCerrada) {
        this.cieCerrada = cieCerrada;
    }

   

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CierreCaja)) {
            return false;
        }
        CierreCaja other = (CierreCaja) object;
        if ((this.idCierre == null && other.idCierre != null) || (this.idCierre != null && !this.idCierre.equals(other.idCierre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.CierreCaja[ idCierre=" + idCierre + " ]";
    }
}
