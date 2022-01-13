/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Factura;
import com.ec.entidad.RetencionVenta;
import com.ec.servicio.ServicioRetencionVenta;
import java.math.BigDecimal;
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
public class RetencionVentaVm {

    @Wire
    Window windowRetencionVenta;

    ServicioRetencionVenta servicioRetencionVenta = new ServicioRetencionVenta();
    private String accion = "new";

    private RetencionVenta retencionVenta = new RetencionVenta();
    private Factura facturaSelected = new Factura();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Factura valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        facturaSelected = valor;

            RetencionVenta recup = servicioRetencionVenta.findByFactura(valor);
        if (recup != null) {
            accion = "update";
            retencionVenta = recup;
        } else {
            accion = "new";
            retencionVenta = new RetencionVenta();
            retencionVenta.setRveValorRetencionIva30(BigDecimal.ZERO);
            retencionVenta.setRveValorRetencionIva70(BigDecimal.ZERO);
            retencionVenta.setRveValorRetencionRenta(BigDecimal.ZERO);
            retencionVenta.setRveCodigo(1);
            retencionVenta.setRveSerie("001");
        }

    }

    @Command
    public void guardar() {
        try {
            retencionVenta.setIdFactura(facturaSelected);
            if (facturaSelected != null) {

                if (accion.equals("new")) {
                    servicioRetencionVenta.crear(retencionVenta);
                    windowRetencionVenta.detach();
                } else {
                    servicioRetencionVenta.modificar(retencionVenta);
                    windowRetencionVenta.detach();
                }
            } else {
                Clients.showNotification("Verifique la informacion", Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 3000, true);
            }
        } catch (Exception e) {
            Clients.showNotification("Error al guardar rentencion de ventas " + e.getMessage(), Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
        }

    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public RetencionVenta getRetencionVenta() {
        return retencionVenta;
    }

    public void setRetencionVenta(RetencionVenta retencionVenta) {
        this.retencionVenta = retencionVenta;
    }

}
