/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.MailMasivo;
import com.ec.servicio.ServicioMailMasivo;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NuevoMailing {

    ServicioMailMasivo servicioMailMasivo = new ServicioMailMasivo();
    @Wire("#windowMailing")
    Window windowMailing;
    String accion = "new";
    private MailMasivo mailMasivo = new MailMasivo();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") MailMasivo param, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (param == null) {
            accion = "new";
            mailMasivo = new MailMasivo();
            System.out.println("nuevo");
        } else {
            accion = "update";
            mailMasivo = param;
            System.out.println("actualizar");
        }
    }

    @Command
    @NotifyChange({"mailMasivo"})
    public void guardar() {
        if (!mailMasivo.getEmail().equals("")) {
            if (accion.equals("new")) {
                servicioMailMasivo.crear(mailMasivo);
                Messagebox.show("Guardado con exito");
            } else {
                servicioMailMasivo.modificar(mailMasivo);
                Messagebox.show("Guardado con exito");
            }
            windowMailing.detach();
        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }


    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public MailMasivo getMailMasivo() {
        return mailMasivo;
    }

    public void setMailMasivo(MailMasivo mailMasivo) {
        this.mailMasivo = mailMasivo;
    }
    
}
