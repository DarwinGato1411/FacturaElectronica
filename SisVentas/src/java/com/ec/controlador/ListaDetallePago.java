/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.DetallePago;
import com.ec.entidad.Factura;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioDetallePago;
import com.ec.servicio.ServicioFactura;
import com.ec.untilitario.ArchivoUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class ListaDetallePago {

    @Wire
    Window windowDetallePago;
    private Boolean generar = Boolean.TRUE;

    /*RUTAS PARA LOS ARCHIVPOS XML SRI*/
    ServicioDetallePago servicioDetallePago = new ServicioDetallePago();
    ServicioFactura servicioFactura = new ServicioFactura();
    private List<DetallePago> lstPagos = new ArrayList<DetallePago>();
    private Factura factura = new Factura();
    private Integer numeroMeses = 0;
    private BigDecimal saldo = BigDecimal.ZERO;
    private BigDecimal totalFactura = BigDecimal.ZERO;

    Connection con = null;
    AMedia fileContent = null;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Factura valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor != null) {
            factura = valor;
            totalFactura = factura.getFacTotal();
            totalFactura = ArchivoUtils.redondearDecimales(totalFactura, 2);
            consultarDetallepago();
        }
    }

    public ListaDetallePago() {

    }

    private void consultarDetallepago() {
        BigDecimal abonos = BigDecimal.ZERO;
        lstPagos = servicioDetallePago.finForIdFactura(factura);
        if (lstPagos.size() > 0) {
            generar = Boolean.FALSE;
            for (DetallePago lstPago : lstPagos) {
                abonos = abonos.add(lstPago.getDetpAbono());

            }
            saldo = totalFactura.subtract(abonos);
            saldo = ArchivoUtils.redondearDecimales(saldo, 2);

        } else {
            generar = Boolean.TRUE;
        }
    }

    @Command
    @NotifyChange({"lstPagos", "numeroMeses", "saldo"})
    public void calculoCuota() {
        try {
            if (numeroMeses <= 0) {
                Clients.showNotification("El numero de meses debe ser mayora a cero",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
                return;
            }
            saldo = BigDecimal.ZERO;
            lstPagos.clear();
            DetallePago detallePago = null;
            //muestra meses dias atras
            Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy 
            Date fechaCobro = null;
            //BigDecimal cuota = factura.getFacTotal().divide(BigDecimal.valueOf(numeroMeses), 2, RoundingMode.UP);
//            cuota.setScale(3, RoundingMode.FLOOR);
            for (int i = 1; i <= numeroMeses; i++) {
                calendar.add(Calendar.MONTH, i); //el -3 indica que se le restaran 3 dias 
                fechaCobro = calendar.getTime();
                detallePago = new DetallePago();
                detallePago.setDetpNumPago(i);
                detallePago.setDetpFechaCobro(new Date());
                detallePago.setDetpTotal(BigDecimal.ZERO);
                detallePago.setDetpAbono(BigDecimal.ZERO);
                detallePago.setDetpSaldo(BigDecimal.ZERO);
                detallePago.setIdFactura(factura);
                lstPagos.add(detallePago);
            }
//            for (DetallePago lstPago : lstPagos) {
//                saldo = saldo.add(lstPago.getDetpSaldo());
//            }
//            saldo.setScale(2, RoundingMode.UP);
//            factura.setFacSaldoAmortizado(saldo);
//            servicioFactura.modificar(factura);
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange({"lstPagos", "generar", "saldo"})
    public void confirmarTabla() {
        saldo = BigDecimal.ZERO;
        for (DetallePago lstPago : lstPagos) {
            servicioDetallePago.crear(lstPago);
        }
        for (DetallePago lstPago : lstPagos) {
            saldo = saldo.add(lstPago.getDetpSaldo());
        }
        saldo.setScale(2, RoundingMode.UP);
        generar = Boolean.FALSE;
        Clients.showNotification("Su tabla fue generada éxitosamente",
                Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 3000, true);
    }

    @Command
    public void reporteComprobante(@BindingParam("valor") DetallePago detallePago) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(factura.getFacNumero(), detallePago, "COMP");
    }

    @Command
    @NotifyChange({"lstPagos", "saldo"})
    public void modificar(@BindingParam("valor") DetallePago valor) {
        try {
            BigDecimal abonos = BigDecimal.ZERO;
            BigDecimal saldoInicial = saldo;
            saldo = BigDecimal.ZERO;

            valor.setDetpFechaPago(new Date());
            if (valor.getDetpAbono() != null) {
                valor.setDetpSaldo(BigDecimal.ZERO);
            }
            for (DetallePago lstPago : lstPagos) {
                abonos = abonos.add(lstPago.getDetpAbono());

            }

            if (abonos.doubleValue() > totalFactura.doubleValue()) {
                valor.setDetpAbono(BigDecimal.ZERO);
                saldo = saldoInicial;
                Clients.showNotification("No se puede realizar un cobro superior al saldo pendiente",
                        Clients.NOTIFICATION_TYPE_INFO, null, "end_center", 2000, true);
                return;

            }
            saldo = totalFactura.subtract(abonos);
            saldo = ArchivoUtils.redondearDecimales(saldo, 2);
//            saldo.setScale(2, RoundingMode.UP);
//            BigDecimal saldoAmortizacionCliente=factura.getIdCliente().getCliMontoAsignado().subtract(saldo);
            if (saldo.intValue() <= 0) {
                factura.setFacEstado("PA");
                factura.setFacSaldoAmortizado(BigDecimal.ZERO);
            } else {
                factura.setFacEstado("PE");
            }
            servicioDetallePago.modificar(valor);
            factura.setFacSaldoAmortizado(saldo);
            servicioFactura.modificar(factura);
//            windowDetallePago.detach();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public void reporteGeneral(Integer numeroFactura, DetallePago detallePago, String tipo) throws JRException, IOException, NamingException, SQLException {

        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";

            reportPath = reportFile + File.separator + "cotizacionCuota.jasper";
            Map<String, Object> parametros = new HashMap<String, Object>();

            parametros.put("numfactura", numeroFactura);
            parametros.put("detallePago", detallePago.getIdDetallePago());

            if (con != null) {
                System.out.println("Conexión Realizada Correctamente");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);

            byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Reporte", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/venta/contenedorReporte.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            System.out.println("ERROR EL PRESENTAR EL REPORTE " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }

        }

    }

    public List<DetallePago> getLstPagos() {
        return lstPagos;
    }

    public void setLstPagos(List<DetallePago> lstPagos) {
        this.lstPagos = lstPagos;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
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

}
