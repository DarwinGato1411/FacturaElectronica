/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.Producto;
import com.ec.entidad.ProductoProveedor;
import com.ec.entidad.ProductoProveedorPK;
import com.ec.entidad.Proveedores;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioProductoProveedor;
import com.ec.servicio.ServicioProveedor;
import com.ec.untilitario.ProductoProveedorCosto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class AdmProductoProveedor {
//servicios

    ServicioProveedor servicioProveedor = new ServicioProveedor();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioProductoProveedor servicioProductoProveedor = new ServicioProductoProveedor();
    //variables para proveedor
    List<Proveedores> listaProveedores = new ArrayList<Proveedores>();
    private Proveedores proveedorSelected = null;
    //variables par producto proveedor
    private List<ProductoProveedor> listaProductoProveedor = new ArrayList<ProductoProveedor>();
    //modelo para multi seleccion
    private List<ProductoProveedorCosto> lstProductoProveedorCostos = new ArrayList<ProductoProveedorCosto>();
    private ListModelList<ProductoProveedorCosto> listaProductoProveedorModel;
    private List<ProductoProveedorCosto> listaProductoProveedorDatos = new ArrayList<ProductoProveedorCosto>();
    private Set<ProductoProveedorCosto> registrosSeleccionados = new HashSet<ProductoProveedorCosto>();
    //busquedas en las cajas de texto
    private String buscarProveedor = "";
    private String buscarProductoProveedorCostos = "";

    public AdmProductoProveedor() {
        findProveedorByNombre();
//        findProductoProveedorCostos();
        getDetallefactura();
    }

    private void getDetallefactura() {

        setListaProductoProveedorModel(new ListModelList<ProductoProveedorCosto>(getLstProductoProveedorCostos()));
        ((ListModelList<ProductoProveedorCosto>) listaProductoProveedorModel).setMultiple(true);
    }

    @Command
    public void seleccionarRegistros() {
        registrosSeleccionados = ((ListModelList<ProductoProveedorCosto>) getListaProductoProveedorModel()).getSelection();
    }

    private void findProductoProveedorCostos() {
        lstProductoProveedorCostos = servicioProducto.findProductoProveedorCosto(buscarProductoProveedorCostos);
    }

    private void findProveedorByNombre() {
        listaProveedores = servicioProveedor.findLikeProvNombre(buscarProveedor);
    }

    public List<ProductoProveedor> getListaProductoProveedor() {
        return listaProductoProveedor;
    }

    public void setListaProductoProveedor(List<ProductoProveedor> listaProductoProveedor) {
        this.listaProductoProveedor = listaProductoProveedor;
    }

    public String getBuscarProveedor() {
        return buscarProveedor;
    }

    public void setBuscarProveedor(String buscarProveedor) {
        this.buscarProveedor = buscarProveedor;
    }

    public List<Proveedores> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Proveedores> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public Proveedores getProveedorSelected() {
        return proveedorSelected;
    }

    public void setProveedorSelected(Proveedores proveedorSelected) {
        this.proveedorSelected = proveedorSelected;
    }

    public List<ProductoProveedorCosto> getLstProductoProveedorCostos() {
        return lstProductoProveedorCostos;
    }

    public void setLstProductoProveedorCostos(List<ProductoProveedorCosto> lstProductoProveedorCostos) {
        this.lstProductoProveedorCostos = lstProductoProveedorCostos;
    }

    public String getBuscarProductoProveedorCostos() {
        return buscarProductoProveedorCostos;
    }

    public ListModelList<ProductoProveedorCosto> getListaProductoProveedorModel() {
        return listaProductoProveedorModel;
    }

    public void setListaProductoProveedorModel(ListModelList<ProductoProveedorCosto> listaProductoProveedorModel) {
        this.listaProductoProveedorModel = listaProductoProveedorModel;
    }

    public List<ProductoProveedorCosto> getListaProductoProveedorDatos() {
        return listaProductoProveedorDatos;
    }

    public void setListaProductoProveedorDatos(List<ProductoProveedorCosto> listaProductoProveedorDatos) {
        this.listaProductoProveedorDatos = listaProductoProveedorDatos;
    }

    public Set<ProductoProveedorCosto> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<ProductoProveedorCosto> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    @Command
    @NotifyChange({"listaProveedores", "buscarProveedor"})
    public void buscarLikeNombrePro(@BindingParam("valor") String valor) {
        buscarProveedor = valor;
        findProveedorByNombre();
    }

//    @Command
//    @NotifyChange({"listaProductoProveedorModel", "buscarProductoProveedorCostos"})
//    public void buscarProductoByNombre(@BindingParam("valor") String valor) {
//        buscarProductoProveedorCostos = valor;
//        findProductoProveedorCostos();
//        getDetallefactura();
//    }
    @Command
    @NotifyChange({"proveedorSelected", "listaProductoProveedorModel"})
    public void seleccionarProveedor() {
        consultarListaProductoProvee();
    }

    private void consultarListaProductoProvee() {
        lstProductoProveedorCostos.clear();
        //refresca el listdo de productos que aun no se encuentran registrados para el proveedor
        List<Producto> lstProducto = servicioProducto.FindALlProducto();
        for (Producto producto : lstProducto) {
            if (servicioProductoProveedor.findByIdProductoIdProveedor(producto.getIdProducto(), proveedorSelected.getIdProveedor()) == 0) {
                lstProductoProveedorCostos.add(new ProductoProveedorCosto(producto, BigDecimal.ZERO));
            }
        }

        getDetallefactura();
    }

    @Command
    @NotifyChange({"listaProductoProveedorModel"})
    public void registrarProducto() {
        if (Messagebox.show("¿Desea registrar " + registrosSeleccionados.size() + "?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            for (ProductoProveedorCosto item : registrosSeleccionados) {
                ProductoProveedorPK idP = new ProductoProveedorPK(proveedorSelected.getIdProveedor(), item.getProducto().getIdProducto());
                ProductoProveedor pp = new ProductoProveedor();
                pp.setProductoProveedorPK(idP);
                pp.setProdProvCosto(item.getCosto());
                pp.setProdProvFechaReg(new Date());
                servicioProductoProveedor.crear(pp);

            }

        }
        consultarListaProductoProvee();
    }
}
