/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Factura;
import com.ec.servicio.ServicioFactura;
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
public class CambioEstadoFactura {

    @Wire
    Window windowEstFact;
    private Factura facturar;
    private String estado;
    private String descripcionAnula;
    ServicioFactura servicioFactura = new ServicioFactura();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Factura valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        this.facturar = valor;
        estado = valor.getEstadosri() != null ? valor.getEstadosri() : "";
    }

    @Command
    public void guardar() {
       
//            facturar.setEstadosri(estado);
//            facturar.setMensajesri(descripcionAnula);
            servicioFactura.modificar(facturar);

            Clients.showNotification("Guardado correctamente",
                    Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 1000, true);
            windowEstFact.detach();
//        } else {
//            Clients.showNotification("Verifique el estado de la factura",
//                    Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
//            windowEstFact.detach();
//        }

    }

    public Factura getFacturar() {
        return facturar;
    }

    public void setFacturar(Factura facturar) {
        this.facturar = facturar;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcionAnula() {
        return descripcionAnula;
    }

    public void setDescripcionAnula(String descripcionAnula) {
        this.descripcionAnula = descripcionAnula;
    }

}
