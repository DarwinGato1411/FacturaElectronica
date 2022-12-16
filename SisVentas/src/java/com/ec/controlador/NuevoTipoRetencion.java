/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.TipoRetencion;
import com.ec.servicio.ServicioTipoRetencion;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NuevoTipoRetencion {

    ServicioTipoRetencion servicioTipoRetencion = new ServicioTipoRetencion();
    private TipoRetencion entidad = new TipoRetencion();

    private String accion = "create";

    @Wire
    Window windowTipOCodigo;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") TipoRetencion valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        if (valor != null) {
            this.entidad = valor;
            accion = "update";
        } else {
            this.entidad = new TipoRetencion();
            this.entidad.setTireTipo("RET");
            this.entidad.setTirePorcentajeRetencion(0);
            accion = "create";
        }

    }

    @Command
    public void guardar() {
        /*getCliNombre es el nombre comercial*/

        if (entidad.getTireCodigo() != null
                && entidad.getTireNombre() != null
                && entidad.getTirePorcentajeRetencion() != 0) {

            if (servicioTipoRetencion.findOneTireCodigo(entidad.getTireCodigo()) != null && accion.equals("create")) {
                Clients.showNotification("El codigo ya se encuentra registrado",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 2000, true);
                return;
            }

            if (accion.equals("create")) {
                servicioTipoRetencion.crear(entidad);
                Clients.showNotification("Informacion registrada",
                        Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 2000, true);
            } else {
                servicioTipoRetencion.modificar(entidad);
                Clients.showNotification("Informacion modificada",
                        Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 2000, true);
            }
            windowTipOCodigo.detach();
        } else {

            Clients.showNotification("Verifique la informacion requerida",
                    Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
        }
    }

    public TipoRetencion getEntidad() {
        return entidad;
    }

    public void setEntidad(TipoRetencion entidad) {
        this.entidad = entidad;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
