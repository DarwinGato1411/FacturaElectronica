/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.Factura;
import com.ec.entidad.RetencionCompra;
import com.ec.servicio.ServicioCompra;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioRetencionCompra;
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
public class CambioEstadoFacturaRet {

    @Wire
    Window windowEstFact;
    private RetencionCompra retencionCompra;
    private ServicioRetencionCompra servicioRetencionCompra = new ServicioRetencionCompra();
    private ServicioCompra servicioCompra= new ServicioCompra();
    private String estado;
    private String descripcionAnula;
    ServicioFactura servicioFactura = new ServicioFactura();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") RetencionCompra valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        this.retencionCompra = valor;
        estado = valor.getDrcEstadosri() != null ? valor.getDrcEstadosri() : "";
        System.out.println(estado);
    }

    @Command
    public void guardar() {
        if (retencionCompra.getDrcEstadosri().equals("ANULADA")) {
            CabeceraCompra cabecerCompra=retencionCompra.getIdCabecera();
            cabecerCompra.setCabRetencionAutori("N");
            servicioCompra.modificar(cabecerCompra);
        }
        servicioRetencionCompra.modificar(retencionCompra);
        Clients.showNotification("Guardado correctamente, refresque la lista",
                Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 1000, true);
        windowEstFact.detach();

    }

    public RetencionCompra getRetencionCompra() {
        return retencionCompra;
    }

    public void setRetencionCompra(RetencionCompra retencionCompra) {
        this.retencionCompra = retencionCompra;
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
