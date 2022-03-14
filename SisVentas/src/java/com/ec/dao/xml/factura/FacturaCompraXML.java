/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.dao.xml.factura;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin
 */
@XmlRootElement(name="autorizacion")
public class FacturaCompraXML {
     private String estado;
 private String numeroAutorizacion;
 private String fechaAutorizacion;
 private String ambiente;
 Comprobante comprobante;
 
 
 // Getter Methods 

 public String getEstado() {
  return estado;
 }

 public String getNumeroAutorizacion() {
  return numeroAutorizacion;
 }

 public String getFechaAutorizacion() {
  return fechaAutorizacion;
 }

 public String getAmbiente() {
  return ambiente;
 }


 // Setter Methods 

 public void setEstado(String estado) {
  this.estado = estado;
 }

 public void setNumeroAutorizacion(String numeroAutorizacion) {
  this.numeroAutorizacion = numeroAutorizacion;
 }

 public void setFechaAutorizacion(String fechaAutorizacion) {
  this.fechaAutorizacion = fechaAutorizacion;
 }

 public void setAmbiente(String ambiente) {
  this.ambiente = ambiente;
 }

    public Comprobante getComprobante() {
        return comprobante;
    }

    public void setComprobante(Comprobante comprobante) {
        this.comprobante = comprobante;
    }


}
