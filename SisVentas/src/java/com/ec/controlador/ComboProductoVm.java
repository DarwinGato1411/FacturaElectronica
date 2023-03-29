/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.ComboProducto;
import com.ec.entidad.Producto;
import com.ec.servicio.ServicioComboProducto;
import com.ec.servicio.ServicioProducto;
import com.ec.untilitario.ComboProductoDao;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class ComboProductoVm {

    @Wire
    Window windowCombo;
    /*combos*/
    private List<ComboProductoDao> listaComboProducto = new ArrayList<ComboProductoDao>();
    ServicioComboProducto servicioComboProducto = new ServicioComboProducto();
    private ListModelList<ComboProductoDao> listaComboProductoModel;
    private Set<ComboProductoDao> registrosSeleccionados = new HashSet<ComboProductoDao>();
    private Producto productoSelected = null;

    /*producto*/
    private List<Producto> listaProducto = new ArrayList<Producto>();
    ServicioProducto servicioProducto = new ServicioProducto();
    private String buscarNombre = "";
    private String buscarCodigo = "";

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Producto producto, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        productoSelected = producto;
        getComboProdcutoModel();
        findLikeNombre();
    }

    @Command
    @NotifyChange({"listaProducto", "buscarNombre"})
    public void buscarLikeNombre() {
        findLikeNombre();
    }

    @Command
    @NotifyChange({"listaProducto", "buscarCodigo"})
    public void buscarLikeCodigo() {
        findLikeProdCodigo();
    }

    private void findLikeNombre() {
        listaProducto = servicioProducto.findLikeProdNombre(buscarNombre);
    }

    private void findLikeProdCodigo() {
        listaProducto = servicioProducto.findLikeProdCodigo(buscarCodigo);
    }

    private void getComboProdcutoModel() {
        listaComboProducto.clear();
        /*obtenemos los datos*/
        List<ComboProducto> listaRecup = servicioComboProducto.findForProducto(productoSelected);
        for (ComboProducto item : listaRecup) {
            ComboProductoDao comboProductoDao = new ComboProductoDao(item, "", item.getComCantidad());

            listaComboProducto.add(comboProductoDao);
        }
        /*MOSTRAMOS EN EL MODELLIST*/
        setListaComboProductoModel(new ListModelList<ComboProductoDao>(getListaComboProducto()));
        ((ListModelList<ComboProductoDao>) listaComboProductoModel).setMultiple(true);
    }

    @Command
    public void seleccionarRegistros() {
        registrosSeleccionados = ((ListModelList<ComboProductoDao>) getListaComboProductoModel()).getSelection();
    }

    @Command
    @NotifyChange({"listaComboProductoModel",})
    public void agregarItemLista(@BindingParam("valor") Producto producto) {
        ComboProducto comboProducto = new ComboProducto();
        comboProducto.setComCantidad(BigDecimal.ONE);
        
        comboProducto.setIdProducto(producto);
        comboProducto.setIdProductoPadre(productoSelected);
        comboProducto.setComFecha(new Date());
        comboProducto.setComDetalle("Generar");

        ComboProductoDao dao = new ComboProductoDao(comboProducto, "");
        dao.setCantidad(BigDecimal.ONE);
        ((ListModelList<ComboProductoDao>) listaComboProductoModel).add(dao);
    }

    @Command
    @NotifyChange({"listaComboProductoModel"})
    public void eliminarRegistros() {
        if (registrosSeleccionados.size() > 0) {
            ((ListModelList<ComboProductoDao>) listaComboProductoModel).removeAll(registrosSeleccionados);
        } else {

        }
    }

    @Command
    public void guardar() {
        try {
            List<ComboProductoDao> listaCombo = listaComboProductoModel.getInnerList();
            servicioComboProducto.eliminarPorProd(productoSelected);
            servicioComboProducto.crearCombo(listaCombo);
            Clients.showNotification("Guardo con exito",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 2000, true);
            windowCombo.detach();
        } catch (Exception e) {
            Clients.showNotification("Error al guardar " + e.getMessage(),
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 2000, true);
        }

    }

    /*COMBOS*/
    public List<ComboProductoDao> getListaComboProducto() {
        return listaComboProducto;
    }

    public void setListaComboProducto(List<ComboProductoDao> listaComboProducto) {
        this.listaComboProducto = listaComboProducto;
    }

    public ListModelList<ComboProductoDao> getListaComboProductoModel() {
        return listaComboProductoModel;
    }

    public void setListaComboProductoModel(ListModelList<ComboProductoDao> listaComboProductoModel) {
        this.listaComboProductoModel = listaComboProductoModel;
    }

    public Set<ComboProductoDao> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<ComboProductoDao> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    public Producto getProductoSelected() {
        return productoSelected;
    }

    public void setProductoSelected(Producto productoSelected) {
        this.productoSelected = productoSelected;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    public String getBuscarCodigo() {
        return buscarCodigo;
    }

    public void setBuscarCodigo(String buscarCodigo) {
        this.buscarCodigo = buscarCodigo;
    }

}
