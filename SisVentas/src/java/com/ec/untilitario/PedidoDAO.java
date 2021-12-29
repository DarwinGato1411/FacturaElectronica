/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import com.ec.entidad.Pedidos;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Darwin Morocho
 */
public class PedidoDAO {

    private Pedidos pedido;
    private BigDecimal pedCantidad;
    private String pedDescripcion;
    private Date pedFecha;
    private String pedEstado;
    private String pedProveedor;

    public PedidoDAO(BigDecimal pedCantidad, String pedDescripcion, Date pedFecha, String pedEstado, String pedProveedor, Pedidos pedido) {
        this.pedCantidad = pedCantidad;
        this.pedDescripcion = pedDescripcion;
        this.pedFecha = pedFecha;
        this.pedEstado = pedEstado;
        this.pedProveedor = pedProveedor;
        this.pedido = pedido;
    }

    public PedidoDAO() {
    }

    public BigDecimal getPedCantidad() {
        return pedCantidad;
    }

    public void setPedCantidad(BigDecimal pedCantidad) {
        this.pedCantidad = pedCantidad;
    }

    public String getPedDescripcion() {
        return pedDescripcion;
    }

    public void setPedDescripcion(String pedDescripcion) {
        this.pedDescripcion = pedDescripcion;
    }

    public Date getPedFecha() {
        return pedFecha;
    }

    public void setPedFecha(Date pedFecha) {
        this.pedFecha = pedFecha;
    }

    public String getPedEstado() {
        return pedEstado;
    }

    public void setPedEstado(String pedEstado) {
        this.pedEstado = pedEstado;
    }

    public String getPedProveedor() {
        return pedProveedor;
    }

    public void setPedProveedor(String pedProveedor) {
        this.pedProveedor = pedProveedor;
    }

    public Pedidos getPedido() {
        return pedido;
    }

    public void setPedido(Pedidos pedido) {
        this.pedido = pedido;
    }
}
