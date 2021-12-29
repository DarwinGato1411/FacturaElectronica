/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import com.ec.entidad.RetencionCompra;
import com.ec.entidad.TipoRetencion;
import com.ec.entidad.Tipoivaretencion;
import java.math.BigDecimal;

/**
 *
 * @author Darwin
 */
public class DetalleRetencionCompraDao {

    private Integer drcCodigo;
    private BigDecimal drcBaseImponible;

    private BigDecimal drcPorcentaje;

    private BigDecimal drcValorRetenido;

    private String drcDescripcion;
    private String tipoResgistro;
    private String codImpuestoAsignado;

    private TipoRetencion tireCodigo;

    private RetencionCompra rcoCodigo;

    Tipoivaretencion tipoivaretencion;

    public BigDecimal getDrcBaseImponible() {
        return drcBaseImponible;
    }

    public void setDrcBaseImponible(BigDecimal drcBaseImponible) {
        this.drcBaseImponible = drcBaseImponible;
    }

    public BigDecimal getDrcPorcentaje() {
        return drcPorcentaje;
    }

    public void setDrcPorcentaje(BigDecimal drcPorcentaje) {
        this.drcPorcentaje = drcPorcentaje;
    }

    public BigDecimal getDrcValorRetenido() {
        return drcValorRetenido;
    }

    public void setDrcValorRetenido(BigDecimal drcValorRetenido) {
        this.drcValorRetenido = drcValorRetenido;
    }

    public TipoRetencion getTireCodigo() {
        return tireCodigo;
    }

    public void setTireCodigo(TipoRetencion tireCodigo) {
        this.tireCodigo = tireCodigo;
    }

    public RetencionCompra getRcoCodigo() {
        return rcoCodigo;
    }

    public void setRcoCodigo(RetencionCompra rcoCodigo) {
        this.rcoCodigo = rcoCodigo;
    }

    public Integer getDrcCodigo() {
        return drcCodigo;
    }

    public void setDrcCodigo(Integer drcCodigo) {
        this.drcCodigo = drcCodigo;
    }

    public String getDrcDescripcion() {
        return drcDescripcion;
    }

    public void setDrcDescripcion(String drcDescripcion) {
        this.drcDescripcion = drcDescripcion;
    }

    public String getTipoResgistro() {
        return tipoResgistro;
    }

    public void setTipoResgistro(String tipoResgistro) {
        this.tipoResgistro = tipoResgistro;
    }

    public String getCodImpuestoAsignado() {
        return codImpuestoAsignado;
    }

    public void setCodImpuestoAsignado(String codImpuestoAsignado) {
        this.codImpuestoAsignado = codImpuestoAsignado;
    }

    public Tipoivaretencion getTipoivaretencion() {
        return tipoivaretencion;
    }

    public void setTipoivaretencion(Tipoivaretencion tipoivaretencion) {
        this.tipoivaretencion = tipoivaretencion;
    }

}
