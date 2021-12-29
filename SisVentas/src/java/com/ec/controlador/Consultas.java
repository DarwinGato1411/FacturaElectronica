/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.ProductoProveedor;
import com.ec.entidad.Proveedores;
import com.ec.servicio.ServicioProductoProveedor;
import com.ec.servicio.ServicioProveedor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class Consultas {

    ServicioProductoProveedor servicioProductoProveedor = new ServicioProductoProveedor();
    ServicioProveedor servicioProveedor = new ServicioProveedor();
    private List<Proveedores> lstProveedores = new ArrayList<Proveedores>();
    private List<ProductoProveedor> lstProductoProveedor = new ArrayList<ProductoProveedor>();
    private Proveedores proveedorSelected = new Proveedores();

    public Consultas() {
        consultarProveedores();
    }

    @Command
    @NotifyChange({"proveedorSelected", "lstProductoProveedor"})
    public void seleccionarProveedor() {
        consultarProductosPorProveedor();
    }

    @Command
    @NotifyChange({"proveedorSelected", "lstProductoProveedor"})
    public void actualizarProducto(@BindingParam("valor") ProductoProveedor valor) {
        if (Messagebox.show("¿Desea registrar los cambios?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            valor.setProdProvFechaReg(new Date());
            servicioProductoProveedor.modificar(valor);
            consultarProductosPorProveedor();
        }
    }

    private void consultarProveedores() {
        lstProveedores = servicioProveedor.FindALlProveedores();
    }

    private void consultarProductosPorProveedor() {
        lstProductoProveedor = servicioProductoProveedor.findByIdProveedor(proveedorSelected);
    }

    public List<Proveedores> getLstProveedores() {
        return lstProveedores;
    }

    public void setLstProveedores(List<Proveedores> lstProveedores) {
        this.lstProveedores = lstProveedores;
    }

    public List<ProductoProveedor> getLstProductoProveedor() {
        return lstProductoProveedor;
    }

    public void setLstProductoProveedor(List<ProductoProveedor> lstProductoProveedor) {
        this.lstProductoProveedor = lstProductoProveedor;
    }

    public Proveedores getProveedorSelected() {
        return proveedorSelected;
    }

    public void setProveedorSelected(Proveedores proveedorSelected) {
        this.proveedorSelected = proveedorSelected;
    }
}
