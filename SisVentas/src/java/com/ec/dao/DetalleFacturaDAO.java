/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.dao;

import com.ec.entidad.Factura;
import com.ec.entidad.Producto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gato
 */
public class DetalleFacturaDAO {

    private String codigo = "";
    private Factura factura = null;
    private Producto producto = null;
    private BigDecimal cantidad = BigDecimal.ZERO;
    private String descripcion = "";
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal subTotalDescuento = BigDecimal.ZERO;
    private BigDecimal subTotalPorCantidad = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal totalInicial = BigDecimal.ZERO;
    private String elementoNuevo = "SI";
    private String tipoVenta = "";
    private String codTipoVenta = "";

    private BigDecimal detIva = BigDecimal.ZERO;
    private BigDecimal detIvaDesc = BigDecimal.ZERO;
    private BigDecimal detTotalconiva = BigDecimal.ZERO;
    private BigDecimal detTotalconivadescuento = BigDecimal.ZERO;
    private BigDecimal detPordescuento = BigDecimal.ZERO;
    private BigDecimal detValdescuento = BigDecimal.ZERO;
    private BigDecimal detTotaldescuento = BigDecimal.ZERO;
    private BigDecimal detCantpordescuento = BigDecimal.ZERO;
    private BigDecimal detSubtotaldescuentoporcantidad = BigDecimal.ZERO;
    private List<Producto> listaProductoCmb = new ArrayList<Producto>();
    private Boolean esProducto = Boolean.TRUE;

    private String tipoIdentificacionPropietario = "C";
    private String detCamvcpn = "";
    private String detSerialvin = "";
    private String detAtributo = "";

    private String tipodir = "RESIDENCIA";
    private String calle = "";
    private String numero = "";
    private String interseccion;
    private String tipotel = "MOVIL";
    private String provincia = "217";
    private String numerotel = "";
    private String codigoCantonMatriculacion = "21702";

    public DetalleFacturaDAO() {
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getElementoNuevo() {
        return elementoNuevo;
    }

    public void setElementoNuevo(String elementoNuevo) {
        this.elementoNuevo = elementoNuevo;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public BigDecimal getDetIva() {
        return detIva;
    }

    public void setDetIva(BigDecimal detIva) {
        this.detIva = detIva;
    }

    public BigDecimal getSubTotalPorCantidad() {
        return subTotalPorCantidad;
    }

    public void setSubTotalPorCantidad(BigDecimal subTotalPorCantidad) {
        this.subTotalPorCantidad = subTotalPorCantidad;
    }

    public BigDecimal getDetTotalconiva() {
        return detTotalconiva;
    }

    public void setDetTotalconiva(BigDecimal detTotalconiva) {
        this.detTotalconiva = detTotalconiva;
    }

    public BigDecimal getDetValdescuento() {
        return detValdescuento;
    }

    public void setDetValdescuento(BigDecimal detValdescuento) {
        this.detValdescuento = detValdescuento;
    }

    public BigDecimal getDetPordescuento() {
        return detPordescuento;
    }

    public void setDetPordescuento(BigDecimal detPordescuento) {
        this.detPordescuento = detPordescuento;
    }

    public BigDecimal getDetTotaldescuento() {
        return detTotaldescuento;
    }

    public void setDetTotaldescuento(BigDecimal detTotaldescuento) {
        this.detTotaldescuento = detTotaldescuento;
    }

    public BigDecimal getSubTotalDescuento() {
        return subTotalDescuento;
    }

    public void setSubTotalDescuento(BigDecimal subTotalDescuento) {
        this.subTotalDescuento = subTotalDescuento;
    }

    public BigDecimal getDetIvaDesc() {
        return detIvaDesc;
    }

    public void setDetIvaDesc(BigDecimal detIvaDesc) {
        this.detIvaDesc = detIvaDesc;
    }

    public BigDecimal getDetTotalconivadescuento() {
        return detTotalconivadescuento;
    }

    public void setDetTotalconivadescuento(BigDecimal detTotalconivadescuento) {
        this.detTotalconivadescuento = detTotalconivadescuento;
    }

    public BigDecimal getDetCantpordescuento() {
        return detCantpordescuento;
    }

    public void setDetCantpordescuento(BigDecimal detCantpordescuento) {
        this.detCantpordescuento = detCantpordescuento;
    }

    public BigDecimal getDetSubtotaldescuentoporcantidad() {
        return detSubtotaldescuentoporcantidad;
    }

    public void setDetSubtotaldescuentoporcantidad(BigDecimal detSubtotaldescuentoporcantidad) {
        this.detSubtotaldescuentoporcantidad = detSubtotaldescuentoporcantidad;
    }

    public String getCodTipoVenta() {
        return codTipoVenta;
    }

    public void setCodTipoVenta(String codTipoVenta) {
        this.codTipoVenta = codTipoVenta;
    }

    public BigDecimal getTotalInicial() {
        return totalInicial;
    }

    public void setTotalInicial(BigDecimal totalInicial) {
        this.totalInicial = totalInicial;
    }

    public List<Producto> getListaProductoCmb() {
        return listaProductoCmb;
    }

    public void setListaProductoCmb(List<Producto> listaProductoCmb) {
        this.listaProductoCmb = listaProductoCmb;
    }

    public Boolean getEsProducto() {
        return esProducto;
    }

    public void setEsProducto(Boolean esProducto) {
        this.esProducto = esProducto;
    }

    public String getDetCamvcpn() {
        return detCamvcpn;
    }

    public void setDetCamvcpn(String detCamvcpn) {
        this.detCamvcpn = detCamvcpn;
    }

    public String getDetSerialvin() {
        return detSerialvin;
    }

    public void setDetSerialvin(String detSerialvin) {
        this.detSerialvin = detSerialvin;
    }

    public String getDetAtributo() {
        return detAtributo;
    }

    public void setDetAtributo(String detAtributo) {
        this.detAtributo = detAtributo;
    }

    public String getTipodir() {
        return tipodir;
    }

    public void setTipodir(String tipodir) {
        this.tipodir = tipodir;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipotel() {
        return tipotel;
    }

    public void setTipotel(String tipotel) {
        this.tipotel = tipotel;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getNumerotel() {
        return numerotel;
    }

    public void setNumerotel(String numerotel) {
        this.numerotel = numerotel;
    }

    public String getInterseccion() {
        return interseccion;
    }

    public void setInterseccion(String interseccion) {
        this.interseccion = interseccion;
    }

    public String getTipoIdentificacionPropietario() {
        return tipoIdentificacionPropietario;
    }

    public void setTipoIdentificacionPropietario(String tipoIdentificacionPropietario) {
        this.tipoIdentificacionPropietario = tipoIdentificacionPropietario;
    }

    public String getCodigoCantonMatriculacion() {
        return codigoCantonMatriculacion;
    }

    public void setCodigoCantonMatriculacion(String codigoCantonMatriculacion) {
        this.codigoCantonMatriculacion = codigoCantonMatriculacion;
    }
    

}
