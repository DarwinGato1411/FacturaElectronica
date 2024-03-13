/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.TipoRetencion;
import com.ec.servicio.ServicioTipoRetencion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author gato
 */
public class CodigoRetenciones {

    private List<TipoRetencion> listaCodigos = new ArrayList<TipoRetencion>();
    ServicioTipoRetencion servicioTipoRetencion = new ServicioTipoRetencion();
    private String tireCodigo = "";

    public CodigoRetenciones() {
        consultarCodigos();

    }

    private void consultarCodigos() {
        listaCodigos = servicioTipoRetencion.findTireCodigo(tireCodigo);
    }

    @Command
    @NotifyChange({"listaCodigos", "tireCodigo"})
    public void buscarPorCodigo() {

        consultarCodigos();

    }
    
     @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void nuevoTipoRetencion() {
      
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/tiporetencion.zul", null, null);
        window.doModal();
         consultarCodigos();
    }

    @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void actualizarTipoRetencion(@BindingParam("valor") TipoRetencion valor) {
      
        final HashMap<String, TipoRetencion> map = new HashMap<String, TipoRetencion>();
        map.put("valor", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/tiporetencion.zul", null, map);
        window.doModal();
        consultarCodigos();
    }


    public List<TipoRetencion> getListaCodigos() {
        return listaCodigos;
    }

    public void setListaCodigos(List<TipoRetencion> listaCodigos) {
        this.listaCodigos = listaCodigos;
    }

    public String getTireCodigo() {
        return tireCodigo;
    }

    public void setTireCodigo(String tireCodigo) {
        this.tireCodigo = tireCodigo;
    }

}
