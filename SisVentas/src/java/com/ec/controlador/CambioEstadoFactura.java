/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.ComboProducto;
import com.ec.entidad.DetalleFactura;
import com.ec.entidad.DetalleKardex;
import com.ec.entidad.Factura;
import com.ec.entidad.Kardex;
import com.ec.entidad.Tipokardex;
import com.ec.servicio.ServicioComboProducto;
import com.ec.servicio.ServicioDetalleFactura;
import com.ec.servicio.ServicioDetalleKardex;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioKardex;
import com.ec.servicio.ServicioTipoKardex;
import com.ec.untilitario.TotalKardex;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class CambioEstadoFactura {

    @Wire
    Window windowEstFact;
    private Factura facturar;
    private String estado;
    private String descripcionAnula;
    ServicioFactura servicioFactura = new ServicioFactura();
    ServicioDetalleFactura servicioDetalleFactura = new ServicioDetalleFactura();

    /* DETALLE DEL KARDEX Y DETALLE KARDEX */
    ServicioKardex servicioKardex = new ServicioKardex();
    ServicioDetalleKardex servicioDetalleKardex = new ServicioDetalleKardex();
    ServicioTipoKardex servicioTipoKardex = new ServicioTipoKardex();

    /* PARA GESTION DE COMBO DE PRODCUTO */
    ServicioComboProducto servicioComboProducto = new ServicioComboProducto();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Factura valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        this.facturar = valor;
        estado = valor.getEstadosri() != null ? valor.getEstadosri() : "";
    }

    @Command
    public void guardar() {

        List<DetalleKardex> listadetalleKardexs = servicioDetalleKardex.findByFactura(facturar);
        if (!listadetalleKardexs.isEmpty()) {
            if (Messagebox.show("¿La factura ya se encuentra en el kardex desea registrarla nuevamente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {

            } else {
                windowEstFact.detach();
                return;
            }
        }

        if (facturar.getEstadosri().toUpperCase().contains("ANULADA")) {
            List<DetalleFactura> listaDet = servicioDetalleFactura.findDetalleForIdFactuta(facturar);
            /* INGRESAMOS LO MOVIMIENTOS AL KARDEX */
            Kardex kardex = null;
            DetalleKardex detalleKardex = null;
            Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla("SAL");
            for (DetalleFactura item : listaDet) {
                if (item.getIdProducto() != null) {
                    if (!item.getIdProducto().getProdEsreceta()) {

//                           
                        detalleKardex = new DetalleKardex();
                        kardex = servicioKardex.FindALlKardexs(item.getIdProducto());
                        detalleKardex.setIdKardex(kardex);
                        detalleKardex.setDetkFechakardex(new Date());
                        detalleKardex.setDetkFechacreacion(new Date());
                        detalleKardex.setIdTipokardex(tipokardex);
                        detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                        detalleKardex.setDetkDetalles("Aumenta al kardex por ANULACION  factura: "
                                + "-" + facturar.getFacNumeroText());
                        detalleKardex.setIdFactura(facturar);
                        detalleKardex.setDetkCantidad(item.getDetCantidad());
                        servicioDetalleKardex.crear(detalleKardex);
                        BigDecimal total = kardex.getKarTotal();
                        total = total.add(item.getDetCantidad());
                        kardex.setKarTotal(total);
                        servicioKardex.modificar(kardex);

                    } else {
                        List<ComboProducto> lislaRecup = servicioComboProducto.findForProducto(item.getIdProducto());
                        for (ComboProducto comboProducto : lislaRecup) {

                            detalleKardex = new DetalleKardex();
                            kardex = servicioKardex.FindALlKardexs(comboProducto.getIdProducto());
                            detalleKardex.setIdKardex(kardex);
                            detalleKardex.setDetkFechakardex(new Date());
                            detalleKardex.setDetkFechacreacion(new Date());
                            detalleKardex.setIdTipokardex(tipokardex);
                            detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                            detalleKardex.setDetkDetalles("Aumenta el kardex por ANULACION  factura: "
                                    + facturar.getFacNumeroText());
                            detalleKardex.setIdFactura(facturar);

                            /* calcular la cantidad a descontar del Kardex */
                            BigDecimal cantidadDescuento = comboProducto.getComCantidad()
                                    .multiply(item.getDetCantidad());
                            detalleKardex.setDetkCantidad(cantidadDescuento);
                            servicioDetalleKardex.crear(detalleKardex);
                            /* ACTUALIZA EL TOTAL DEL KARDEX */
                            TotalKardex totales = servicioKardex.totalesForKardex(kardex);
                            BigDecimal total = totales.getTotalKardex();
                            kardex.setKarTotal(total);
                            servicioKardex.modificar(kardex);
                        }
                    }
                }
            }
        }
        servicioFactura.modificar(facturar);

        Clients.showNotification("Guardado correctamente",
                Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 1000, true);
        windowEstFact.detach();
//        } else {
//            Clients.showNotification("Verifique el estado de la factura",
//                    Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
//            windowEstFact.detach();
//        }

    }

    public Factura getFacturar() {
        return facturar;
    }

    public void setFacturar(Factura facturar) {
        this.facturar = facturar;
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
