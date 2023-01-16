/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Proveedores;
import com.ec.entidad.TipoIdentificacionCompra;
import com.ec.servicio.ServicioProveedor;
import com.ec.servicio.ServicioTipoIdentificacionCompra;
import com.ec.untilitario.AduanaJson;
import com.ec.untilitario.ArchivoUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPathExpressionException;
import org.json.JSONException;
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
public class NuevoProveedor {

    ServicioTipoIdentificacionCompra servicioTipoIdentificacionCompra = new ServicioTipoIdentificacionCompra();
    private List<TipoIdentificacionCompra> listaIdentificacionCompras = new ArrayList<TipoIdentificacionCompra>();
    private TipoIdentificacionCompra identificacionCompra = null;

    private Proveedores proveedor = new Proveedores();
    ServicioProveedor servicioProveedor = new ServicioProveedor();
    private String accion = "create";
    @Wire
    Window windowCliente;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Proveedores proveedor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (proveedor != null) {
            this.proveedor = proveedor;
            accion = "update";
            identificacionCompra = proveedor.getIdTipoIdentificacionCompra();
        } else {
            this.proveedor = new Proveedores();
            accion = "create";
            identificacionCompra = null;
        }
        cargarTipoIdentificacion();

    }

    private void cargarTipoIdentificacion() {
        listaIdentificacionCompras = servicioTipoIdentificacionCompra.findALlTipoIdentificacionCompra();
    }

    @Command
    public void guardar() {
        if (proveedor.getProvCedula() != null
                    && proveedor.getProvNombre() != null
                    && proveedor.getProvTelefono() != null
                    && proveedor.getProvDireccion() != null
                    && identificacionCompra != null) {
            proveedor.setIdTipoIdentificacionCompra(identificacionCompra);
            if (accion.equals("create")) {
                servicioProveedor.crear(proveedor);
                // Messagebox.show("Guardado con exito");

                windowCliente.detach();
            } else {
                servicioProveedor.modificar(proveedor);
                // Messagebox.show("Guardado con exito");

                windowCliente.detach();
            }

        } else {
            Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange({"proveedor"})
    public void buscarAduana() throws URISyntaxException, IOException, XPathExpressionException, JSONException {
        if (proveedor.getProvCedula() != null) {
            if (!proveedor.getProvCedula().equals("")) {
                String cedulaBuscar = "";
                if (proveedor.getProvCedula().length() > 10) {
                    cedulaBuscar = proveedor.getProvCedula().substring(0, 10);
                } else {
                    cedulaBuscar = proveedor.getProvCedula();
                }
                AduanaJson aduana = ArchivoUtils.obtenerdatoAduana(cedulaBuscar);
                String nombreApellido = aduana.getNombre();

                proveedor.setProvNombre(nombreApellido);

            }
        }
    }

    public Proveedores getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedores proveedor) {
        this.proveedor = proveedor;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public List<TipoIdentificacionCompra> getListaIdentificacionCompras() {
        return listaIdentificacionCompras;
    }

    public void setListaIdentificacionCompras(List<TipoIdentificacionCompra> listaIdentificacionCompras) {
        this.listaIdentificacionCompras = listaIdentificacionCompras;
    }

    public TipoIdentificacionCompra getIdentificacionCompra() {
        return identificacionCompra;
    }

    public void setIdentificacionCompra(TipoIdentificacionCompra identificacionCompra) {
        this.identificacionCompra = identificacionCompra;
    }

}
