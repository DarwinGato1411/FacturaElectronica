/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.DetalleKardex;
import com.ec.entidad.Kardex;
import com.ec.entidad.Producto;
import com.ec.entidad.Tipokardex;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioDetalleKardex;
import com.ec.servicio.ServicioKardex;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioTipoKardex;
import com.ec.untilitario.TotalKardex;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author gato
 */
public class AdmKardex {

    /*DETALLE DEL KARDEX Y DETALLE KARDEX*/
    private Kardex kardex = new Kardex();
    private DetalleKardex detalleKardex = new DetalleKardex();
    private List<DetalleKardex> listaDetalleKardex = new ArrayList<DetalleKardex>();
    private List<Producto> listaProductos = new ArrayList<Producto>();
    UserCredential credential = new UserCredential();
    ServicioKardex servicioKardex = new ServicioKardex();
    ServicioDetalleKardex servicioDetalleKardex = new ServicioDetalleKardex();
    ServicioTipoKardex servicioTipoKardex = new ServicioTipoKardex();
    ServicioProducto servicioProducto = new ServicioProducto();
    private String buscarProducto = "";
    private String buscarProductoCodigo = "";
    private Date fechaIngreso = new Date();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") String valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        getProductos();
    }

    private void getProductos() {
        listaProductos = servicioProducto.findLikeProdNombre(buscarProducto);
    }

    public AdmKardex() {

        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;

    }

    @Command
    @NotifyChange({"kardex", "listaDetalleKardex"})
    public void seleccionarProductoLista(@BindingParam("valor") Producto valor) {

        kardex = servicioKardex.FindALlKardexs(valor);
        listaDetalleKardex.clear();
        if (kardex != null) {
            listaDetalleKardex = servicioDetalleKardex.findByIdKardex(kardex);
        }

    }

    @Command
    @NotifyChange({"listaProductos", "buscarProducto"})
    public void buscarLikeNombreProd() {

        findProductoLikeNombre();
    }

    @Command
    @NotifyChange({"listaProductos", "buscarProductoCodigo"})
    public void buscarProductoLikeCodigo() {

        findProductoLikeCodigo();
    }

    @Command
    @NotifyChange({"detalleKardex", "fechaIngreso", "listaDetalleKardex"})
    public void detalleKardexManual() {

        Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla("ING");
        if (kardex.getIdKardex() != null) {
            if (detalleKardex.getDetkCantidad() == null) {
                Clients.showNotification("Verifique la cantidad ingresada",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
                return;
            }

            BigDecimal detCantidad = detalleKardex.getDetkCantidad();
            kardex = servicioKardex.FindALlKardexs(kardex.getIdProducto());
            detalleKardex.setIdKardex(kardex);
            detalleKardex.setDetkFechakardex(fechaIngreso);
            detalleKardex.setDetkFechacreacion(new Date());
            detalleKardex.setIdTipokardex(tipokardex);
            detalleKardex.setDetkKardexmanual(Boolean.TRUE);
            detalleKardex.setDetkDetalles("Aumenta al kardex desde facturacion con kardex MANUAL");
            servicioDetalleKardex.crear(detalleKardex);
            /*ACTUALIZA EL TOTAL DEL KARDEX*/
            TotalKardex totales = servicioKardex.totalesForKardex(kardex);
            BigDecimal total = totales.getTotalKardex();
            kardex.setKarTotal(total);
            servicioKardex.modificar(kardex);
            listaDetalleKardex = servicioDetalleKardex.findByIdKardex(kardex);
            detalleKardex = new DetalleKardex();
            Clients.showNotification("Registrado correctamente",
                    Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 3000, true);
        } else {
            Clients.showNotification("Debe seleccionar un producto",
                    Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
        }
    }

    private void findProductoLikeNombre() {
        listaProductos = servicioProducto.findLikeProdNombre(buscarProducto);
    }

    private void findProductoLikeCodigo() {
        listaProductos = servicioProducto.findLikeProdCodigo(buscarProductoCodigo);
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }

    public List<DetalleKardex> getListaDetalleKardex() {
        return listaDetalleKardex;
    }

    public void setListaDetalleKardex(List<DetalleKardex> listaDetalleKardex) {
        this.listaDetalleKardex = listaDetalleKardex;
    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public String getBuscarProducto() {
        return buscarProducto;
    }

    public void setBuscarProducto(String buscarProducto) {
        this.buscarProducto = buscarProducto;
    }

    public DetalleKardex getDetalleKardex() {
        return detalleKardex;
    }

    public void setDetalleKardex(DetalleKardex detalleKardex) {
        this.detalleKardex = detalleKardex;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getBuscarProductoCodigo() {
        return buscarProductoCodigo;
    }

    public void setBuscarProductoCodigo(String buscarProductoCodigo) {
        this.buscarProductoCodigo = buscarProductoCodigo;
    }

}
