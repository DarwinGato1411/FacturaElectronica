/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Proveedores;
import com.ec.entidad.TipoIdentificacionCompra;
import com.ec.entidad.Tipoadentificacion;
import com.ec.entidad.Transportista;
import com.ec.servicio.ServicioProveedor;
import com.ec.servicio.ServicioTipoIdentificacion;
import com.ec.servicio.ServicioTipoIdentificacionCompra;
import com.ec.servicio.ServicioTransportista;
import java.util.ArrayList;
import java.util.List;
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
public class NuevoConductor {

    ServicioTipoIdentificacion servicioTipoIdentificacionCompra = new ServicioTipoIdentificacion();
    private List<Tipoadentificacion> listaIdentificacionCompras = new ArrayList<Tipoadentificacion>();
    private Tipoadentificacion identificacionCompra = null;

    private Transportista transportista = new Transportista();
    ServicioTransportista servicioTransportista = new ServicioTransportista();
    private String accion = "create";
    @Wire
    Window windowCliente;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Transportista valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor != null) {
            this.transportista = valor;
            accion = "update";
            identificacionCompra = transportista.getIdTipoIdentificacion();
        } else {
            this.transportista = new Transportista();
            accion = "create";
             identificacionCompra = null;
        }
        cargarTipoIdentificacion();

    }

    private void cargarTipoIdentificacion() {
        listaIdentificacionCompras = servicioTipoIdentificacionCompra.FindALlTipoadentificacion();
    }

    @Command
    public void guardar() {
        if (transportista.getTrpCedula()!= null
                && transportista.getTrpRazonSocial()!= null
                && transportista.getTrpDireccion()!= null
                && transportista.getTrpTelefono()!= null
                && identificacionCompra != null) {
            transportista.setIdTipoIdentificacion(identificacionCompra);
            if (accion.equals("create")) {
                servicioTransportista.crear(transportista);
              //  Messagebox.show("Guardado con exito");

                windowCliente.detach();
            } else {
                servicioTransportista.modificar(transportista);
               // Messagebox.show("Guardado con exito");

                windowCliente.detach();
            }

        } else {
            Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public List<Tipoadentificacion> getListaIdentificacionCompras() {
        return listaIdentificacionCompras;
    }

    public void setListaIdentificacionCompras(List<Tipoadentificacion> listaIdentificacionCompras) {
        this.listaIdentificacionCompras = listaIdentificacionCompras;
    }

    public Tipoadentificacion getIdentificacionCompra() {
        return identificacionCompra;
    }

    public void setIdentificacionCompra(Tipoadentificacion identificacionCompra) {
        this.identificacionCompra = identificacionCompra;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public void setTransportista(Transportista transportista) {
        this.transportista = transportista;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

   
}
