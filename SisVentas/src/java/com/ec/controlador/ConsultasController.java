/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.vista.servicios.ServicioSriCatastro;
import com.ec.vistas.SriCatastro;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;

public class ConsultasController extends SelectorComposer<Component> {

    ServicioSriCatastro servicioSriCatastro = new ServicioSriCatastro();

    private List<SriCatastro> listaSriCatastro = new ArrayList<SriCatastro>();
    private String buscar = "";

    public ConsultasController() {

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
