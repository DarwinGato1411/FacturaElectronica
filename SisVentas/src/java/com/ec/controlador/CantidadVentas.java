/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.vista.servicios.ServicioCantVentProducto;
import com.ec.vistas.CantVentProductos;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

/**
 *
 * @author gato
 */
public class CantidadVentas {

    ServicioCantVentProducto servicioCantVentProducto = new ServicioCantVentProducto();
    private List<CantVentProductos> listaCantVentProductos = new ArrayList<CantVentProductos>();
    private String mes = "";
    private String anio = "";
    private Date fechainicio=new Date();
    private Date fechafin=new Date();


    public CantidadVentas() {
    }

    private void findByAnioMes() {
      listaCantVentProductos=servicioCantVentProducto.findByMes(fechainicio,fechafin);
    }

    @Command
    @NotifyChange({"listaCantVentProductos","anio", "mes"})
    public void buscarVentas() {
        findByAnioMes();
    }

    

    public List<CantVentProductos> getListaCantVentProductos() {
        return listaCantVentProductos;
    }

    public void setListaCantVentProductos(List<CantVentProductos> listaCantVentProductos) {
        this.listaCantVentProductos = listaCantVentProductos;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

}
