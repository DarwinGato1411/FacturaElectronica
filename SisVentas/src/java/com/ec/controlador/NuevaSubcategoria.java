/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Subcategoria;
import com.ec.servicio.ServicioSubCategoria;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NuevaSubcategoria {

 

    private Subcategoria subcategoria = new Subcategoria();
    ServicioSubCategoria servicioSubCategoria = new ServicioSubCategoria();
    private String accion = "create";
    @Wire
    Window windowSubCategoria;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Subcategoria valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor != null) {
            this.subcategoria = valor;
            accion = "update";
            
        } else {
            this.subcategoria = new Subcategoria();
            accion = "create";
           
        }
    

    }

   

    @Command
    public void guardar() {
        if (subcategoria.getSubCatDescripcion()!= null){
            if (accion.equals("create")) {
                servicioSubCategoria.crear(subcategoria);
              //  Messagebox.show("Guardado con exito");

                windowSubCategoria.detach();
            } else {
                servicioSubCategoria.modificar(subcategoria);
               // Messagebox.show("Guardado con exito");

                windowSubCategoria.detach();
            }

        } else {
            Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Subcategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

   

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

   
}
