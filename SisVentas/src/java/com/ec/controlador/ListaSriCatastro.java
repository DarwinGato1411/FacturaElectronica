/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.CabeceraCompra;
import com.ec.servicio.ServicioFactura;
import com.ec.vista.servicios.ServicioSriCatastro;
import com.ec.vistas.SriCatastro;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

/**
 *
 * @author gato
 */
public class ListaSriCatastro {

    ServicioSriCatastro servicioSriCatastro = new ServicioSriCatastro();

    private List<SriCatastro> listaSriCatastro = new ArrayList<SriCatastro>();
    private String buscar = "";

    public ListaSriCatastro() {

    }

    private void consultaCatastro() {
        listaSriCatastro = servicioSriCatastro.findCatastro(buscar);
    }

    @Command
    @NotifyChange({"listaSriCatastro", "buscar"})
    public void buscarForCedula() {
        consultaCatastro();
    }

    public List<SriCatastro> getListaSriCatastro() {
        return listaSriCatastro;
    }

    public void setListaSriCatastro(List<SriCatastro> listaSriCatastro) {
        this.listaSriCatastro = listaSriCatastro;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }
    
    

}
