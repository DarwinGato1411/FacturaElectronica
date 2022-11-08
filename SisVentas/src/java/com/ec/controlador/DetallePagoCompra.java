/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.AmortizacionCompra;
import com.ec.entidad.CabeceraCompra;
import com.ec.servicio.ServicioCompra;
import com.ec.servicio.ServicioDetallePagoCompra;
import com.ec.untilitario.ArchivoUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class DetallePagoCompra {

    @Wire
    Window windowDetallePagoCom;
    private Boolean generar = Boolean.TRUE;

    /*RUTAS PARA LOS ARCHIVPOS XML SRI*/
    ServicioDetallePagoCompra servicioDetallePago = new ServicioDetallePagoCompra();
    ServicioCompra servicioCompra = new ServicioCompra();
    private List<AmortizacionCompra> lstPagos = new ArrayList<AmortizacionCompra>();
    private CabeceraCompra factura = new CabeceraCompra();
    private Integer numeroMeses = 0;
    private BigDecimal saldo = BigDecimal.ZERO;
    private BigDecimal totalFactura = BigDecimal.ZERO;

    /*Agregar pago*/
    private Date fecha = new Date();
    private BigDecimal valorPago = BigDecimal.ZERO;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") CabeceraCompra valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor != null) {
            factura = valor;
            totalFactura = factura.getCabTotal();
            totalFactura = ArchivoUtils.redondearDecimales(totalFactura, 2);
            consultarDetallepago();
        }
    }

    public DetallePagoCompra() {

    }

    private void consultarDetallepago() {
        BigDecimal abonos = BigDecimal.ZERO;
        lstPagos = servicioDetallePago.finForIdFacturaCompra(factura);
        if (lstPagos.size() > 0) {
            generar = Boolean.FALSE;
            for (AmortizacionCompra lstPago : lstPagos) {
                abonos = abonos.add(lstPago.getDetValor());

            }
            saldo = totalFactura.subtract(abonos);
            saldo = ArchivoUtils.redondearDecimales(saldo, 2);

        } else {

            saldo = totalFactura;
        }
    }

    @Command
    @NotifyChange({"lstPagos", "valorPago", "saldo"})
    public void agregarPago() {

        if (Messagebox.show("¿Desea registrar un pago de " + valorPago + " Dolares ?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            
            if (valorPago.doubleValue() <= saldo.doubleValue()) {
                AmortizacionCompra nuevopago = new AmortizacionCompra();
                nuevopago.setDetDias(numeroMeses);
                nuevopago.setDetFecha(fecha);
                nuevopago.setDetValor(valorPago);
                nuevopago.setIdCompra(factura);
                servicioDetallePago.crear(nuevopago);

                for (AmortizacionCompra lstPago : lstPagos) {
                    saldo = saldo.add(lstPago.getDetValor());
                }
                saldo.setScale(2, RoundingMode.UP);
                generar = Boolean.FALSE;
                valorPago=BigDecimal.ZERO;
                consultarDetallepago();
                if (saldo.doubleValue()==0) {
                    factura.setCabEstado("PA");
                    servicioCompra.modificar(factura);
                }
                Clients.showNotification("Pago registrado correctamente",
                            Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 2000, true);
            } else {
                Clients.showNotification("No puede ingresar un valor superior al saldo",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 2000, true);
            }

        }
          consultarDetallepago();
    }

    @Command
    @NotifyChange({"lstPagos", "saldo"})
    public void modificar(@BindingParam("valor") AmortizacionCompra valor) {
        try {
            BigDecimal abonos = BigDecimal.ZERO;
            BigDecimal saldoInicial = saldo;
            saldo = BigDecimal.ZERO;

//            valor.setF(new Date());
//            if (valor.get() != null) {
//                valor.setDetpSaldo(BigDecimal.ZERO);
//            }
            for (AmortizacionCompra lstPago : lstPagos) {
                abonos = abonos.add(lstPago.getDetValor());

            }

            if (abonos.doubleValue() > totalFactura.doubleValue()) {
//                valor.setDetpAbono(BigDecimal.ZERO);
                saldo = saldoInicial;
                Clients.showNotification("No se puede realizar un cobro superior al saldo pendiente",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 2000, true);
                return;

            }
            saldo = totalFactura.subtract(abonos);
            saldo = ArchivoUtils.redondearDecimales(saldo, 2);
//            saldo.setScale(2, RoundingMode.UP);
//            BigDecimal saldoAmortizacionCliente=factura.getIdCliente().getCliMontoAsignado().subtract(saldo);
            if (saldo.intValue() <= 0) {
                factura.setCabEstado("PA");
                factura.setCabSaldoFactura(BigDecimal.ZERO);
            } else {
                factura.setCabEstado("PE");
            }
            servicioDetallePago.modificar(valor);
            factura.setCabSaldoFactura(saldo);
            servicioCompra.modificar(factura);
            Clients.showNotification("Registro correcto",
                            Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 2000, true);
//            windowDetallePago.detach();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public List<AmortizacionCompra> getLstPagos() {
        return lstPagos;
    }

    public void setLstPagos(List<AmortizacionCompra> lstPagos) {
        this.lstPagos = lstPagos;
    }

    public CabeceraCompra getFactura() {
        return factura;
    }

    public void setFactura(CabeceraCompra factura) {
        this.factura = factura;
    }

    public Integer getNumeroMeses() {
        return numeroMeses;
    }

    public void setNumeroMeses(Integer numeroMeses) {
        this.numeroMeses = numeroMeses;
    }

    public Boolean getGenerar() {
        return generar;
    }

    public void setGenerar(Boolean generar) {
        this.generar = generar;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

}
