/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Parametrizar;
import com.ec.servicio.ServicioParametrizar;
import java.math.BigDecimal;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author Darwin
 */
public class AdmParametrizar {

    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    private Parametrizar parametrizar = new Parametrizar();
    private String tieneKardex = "SI";
    private String creditoClientes = "SI";

    private String imprimeAutomatico = "SI";

    private String imprimeComprobante = "SI";
    private String parPistolaNuevo = "SI";

    public AdmParametrizar() {

        parametrizar = servicioParametrizar.FindALlParametrizar();

        if (parametrizar.getParActivaKardex()) {
            tieneKardex = "SI";
        } else {
            tieneKardex = "NO";
        }

        if (parametrizar.getParCreditoClientes()) {
            creditoClientes = "SI";
        } else {
            creditoClientes = "NO";
        }

        if (parametrizar.getParImpAutomatico()) {
            imprimeAutomatico = "SI";
        } else {
            imprimeAutomatico = "NO";
        }

        if (parametrizar.getParImpFactura()) {
            imprimeComprobante = "SI";
        } else {
            imprimeComprobante = "NO";
        }
        if (parametrizar.getParImpFactura()) {
            imprimeComprobante = "SI";
        } else {
            imprimeComprobante = "NO";
        }
        if (parametrizar.getParPistolaNuevo()) {
            parPistolaNuevo = "SI";
        } else {
            parPistolaNuevo = "NO";
        }

    }

    @Command
    @NotifyChange({"tipoambiente", "llevaContabilidad"})
    public void guardar() {
        if (parametrizar.getParCodigoIva().equals("2")) {
            parametrizar.setParIva(BigDecimal.valueOf(12));
        } else {
            parametrizar.setParIva(BigDecimal.valueOf(0));
        }

        if (tieneKardex.equals("SI")) {
            parametrizar.setParActivaKardex(Boolean.TRUE);
        } else {
            parametrizar.setParActivaKardex(Boolean.FALSE);
        }
        if (creditoClientes.equals("SI")) {
            parametrizar.setParCreditoClientes(Boolean.TRUE);
        } else {
            parametrizar.setParCreditoClientes(Boolean.FALSE);
        }

        if (imprimeAutomatico.equals("SI")) {
            parametrizar.setParImpAutomatico(Boolean.TRUE);
        } else {
            parametrizar.setParImpAutomatico(Boolean.FALSE);
        }

        if (imprimeComprobante.equals("SI")) {
            parametrizar.setParImpFactura(Boolean.TRUE);
        } else {
            parametrizar.setParImpFactura(Boolean.FALSE);
        }
        if (parPistolaNuevo.equals("SI")) {
            parametrizar.setParPistolaNuevo(Boolean.TRUE);
        } else {
            parametrizar.setParPistolaNuevo(Boolean.FALSE);
        }
        parametrizar.setIsprincipal(Boolean.TRUE);
        parametrizar.setParEstado(Boolean.FALSE);
        servicioParametrizar.modificar(parametrizar);
        Clients.showNotification("Información registrada exitosamente",
                Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 3000, true);

    }

    public Parametrizar getParametrizar() {
        return parametrizar;
    }

    public void setParametrizar(Parametrizar parametrizar) {
        this.parametrizar = parametrizar;
    }

    public String getTieneKardex() {
        return tieneKardex;
    }

    public void setTieneKardex(String tieneKardex) {
        this.tieneKardex = tieneKardex;
    }

    public String getCreditoClientes() {
        return creditoClientes;
    }

    public void setCreditoClientes(String creditoClientes) {
        this.creditoClientes = creditoClientes;
    }

    public String getImprimeAutomatico() {
        return imprimeAutomatico;
    }

    public void setImprimeAutomatico(String imprimeAutomatico) {
        this.imprimeAutomatico = imprimeAutomatico;
    }

    public String getImprimeComprobante() {
        return imprimeComprobante;
    }

    public void setImprimeComprobante(String imprimeComprobante) {
        this.imprimeComprobante = imprimeComprobante;
    }

    public String getParPistolaNuevo() {
        return parPistolaNuevo;
    }

    public void setParPistolaNuevo(String parPistolaNuevo) {
        this.parPistolaNuevo = parPistolaNuevo;
    }

}
