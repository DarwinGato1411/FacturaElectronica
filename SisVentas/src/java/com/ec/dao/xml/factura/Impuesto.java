/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.dao.xml.factura;

/**
 *
 * @author Darwin
 */
public class Impuesto {

    private String codigo;
    private String codigoPorcentaje;
    private String tarifa;
    private String baseImponible;
    private String valor;

    // Getter Methods 
    public String getCodigo() {
        return codigo;
    }

    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public String getTarifa() {
        return tarifa;
    }

    public String getBaseImponible() {
        return baseImponible;
    }

    public String getValor() {
        return valor;
    }

    // Setter Methods 
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public void setBaseImponible(String baseImponible) {
        this.baseImponible = baseImponible;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
