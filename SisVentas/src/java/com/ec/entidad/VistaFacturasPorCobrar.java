/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Darwin Morocho
 */
@Entity
@Table(name = "facturas_por_cobrar")
public class VistaFacturasPorCobrar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 12)
    @Column(name = "fac_numero_text")
    private String facNumeroText;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cli_cedula")
    private String cliCedula;
    @Column(name = "cli_nombres")
    private String cliNombres;
    @Column(name = "fac_total")
    private BigDecimal fac_total;
    @Column(name = "fac_saldo_amortizado")
    private BigDecimal facSaldoAmortizado;
    @Column(name = "dias")
    private Integer dias;
    @Column(name = "fac_fecha")
    @Temporal(TemporalType.DATE)
    private Date facFecha;
    @Column(name = "id_usuario")
    private Integer idUsuario;

    public VistaFacturasPorCobrar(Long id, String facNumeroText, String cliCedula, String cliNombres, BigDecimal fac_total, BigDecimal facSaldoAmortizado, Integer dias, Date facFecha) {
        this.id = id;
        this.facNumeroText = facNumeroText;
        this.cliCedula = cliCedula;
        this.cliNombres = cliNombres;
        this.fac_total = fac_total;
        this.facSaldoAmortizado = facSaldoAmortizado;
        this.dias = dias;
        this.facFecha = facFecha;
    }

    public VistaFacturasPorCobrar() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFacNumeroText() {
        return facNumeroText;
    }

    public void setFacNumeroText(String facNumeroText) {
        this.facNumeroText = facNumeroText;
    }

    public String getCliCedula() {
        return cliCedula;
    }

    public void setCliCedula(String cliCedula) {
        this.cliCedula = cliCedula;
    }

    public String getCliNombres() {
        return cliNombres;
    }

    public void setCliNombres(String cliNombres) {
        this.cliNombres = cliNombres;
    }

    public BigDecimal getFac_total() {
        return fac_total;
    }

    public void setFac_total(BigDecimal fac_total) {
        this.fac_total = fac_total;
    }

    public BigDecimal getFacSaldoAmortizado() {
        return facSaldoAmortizado;
    }

    public void setFacSaldoAmortizado(BigDecimal facSaldoAmortizado) {
        this.facSaldoAmortizado = facSaldoAmortizado;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public Date getFacFecha() {
        return facFecha;
    }

    public void setFacFecha(Date facFecha) {
        this.facFecha = facFecha;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

}
