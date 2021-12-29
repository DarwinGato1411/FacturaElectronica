/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.CierreCaja;
import com.ec.entidad.Producto;
import com.ec.entidad.VistaFacturasPorCobrar;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCierreCaja;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioFacturaPorCobrar;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.DispararReporte;
import com.ec.untilitario.ModeloAcumuladoDiaUsuario;
import com.ec.vista.servicios.ServicioAcumuladoDiarioUsuario;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class CierreCajaVm {
    
    @Wire
    Window windowCierre;
    private CierreCaja cierreCaja = new CierreCaja();
    ServicioCierreCaja servicioCierreCaja = new ServicioCierreCaja();
    UserCredential credential = new UserCredential();
    private Date fecha = new Date();
    private BigDecimal totFactura = BigDecimal.ZERO;
    private BigDecimal totNotaVenta = BigDecimal.ZERO;
    private VistaFacturasPorCobrar totalesFactura = new VistaFacturasPorCobrar();
    private BigDecimal totalEmitido = BigDecimal.ZERO;
    private BigDecimal totalDeuda = BigDecimal.ZERO;
    
    ServicioFacturaPorCobrar servicioFacturaPorCobrar = new ServicioFacturaPorCobrar();
    ServicioFactura servicioFactura = new ServicioFactura();
    ServicioAcumuladoDiarioUsuario servicioAcumuladoDiarioUsuario = new ServicioAcumuladoDiarioUsuario();
    private Boolean cajaCerrada = Boolean.FALSE;
    
    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Producto producto, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        cierreCaja = servicioCierreCaja.findALlCierreCajaForFechaIdUsuario(new Date(), credential.getUsuarioSistema()).get(0);
        
        System.out.println("cierreCaja " + cierreCaja);
        System.out.println("cierreCaja sss " + cierreCaja != null ? cierreCaja.getCieValorInicio() : "NULO");
        if (servicioAcumuladoDiarioUsuario.findCierrePorUsuario(fecha, credential.getUsuarioSistema()).size() > 0) {
            
            ModeloAcumuladoDiaUsuario acumuladoDiaUsuario = servicioAcumuladoDiarioUsuario.findCierrePorUsuario(fecha, credential.getUsuarioSistema()).get(0);
            totNotaVenta = ArchivoUtils.redondearDecimales(acumuladoDiaUsuario.getValorNotaVenta(), 2);
            /*total del credito en facturas*/
            totalesFactura = servicioFacturaPorCobrar.findPorCobrarDia(credential.getUsuarioSistema().getIdUsuario()).size() > 0 ? servicioFacturaPorCobrar.findPorCobrarDia(credential.getUsuarioSistema().getIdUsuario()).get(0) : null;
            totalEmitido = acumuladoDiaUsuario.getValorFacturas();
            totalEmitido = ArchivoUtils.redondearDecimales(totalEmitido, 2);
            totalDeuda = totalesFactura != null ? totalesFactura.getFacSaldoAmortizado() : BigDecimal.ZERO;
            totalDeuda = ArchivoUtils.redondearDecimales(totalDeuda, 2);
            totFactura = totalEmitido.subtract(totalDeuda);
            totFactura = ArchivoUtils.redondearDecimales(totFactura, 2);
            cierreCaja.setCieValor(ArchivoUtils.redondearDecimales(totNotaVenta, 2).add(ArchivoUtils.redondearDecimales(totFactura, 2)).add(ArchivoUtils.redondearDecimales(cierreCaja.getCieValorInicio(), 2)));
            cajaCerrada = cierreCaja.getCieCerrada();
            cierreCaja.setCieCuadre(ArchivoUtils.redondearDecimales(cierreCaja.getCieCuadre(), 2));
        } else {
            cierreCaja.setCieValor(ArchivoUtils.redondearDecimales(cierreCaja.getCieValor(), 2).add(ArchivoUtils.redondearDecimales(cierreCaja.getCieValorInicio(), 2)));
            cierreCaja.setCieCuadre(ArchivoUtils.redondearDecimales(cierreCaja.getCieCuadre(), 2));
            cierreCaja.setCieDiferencia(ArchivoUtils.redondearDecimales(cierreCaja.getCieDiferencia(), 2));
            cierreCaja.setCieValorInicio(ArchivoUtils.redondearDecimales(cierreCaja.getCieValorInicio(), 2));
            totFactura = BigDecimal.ZERO;
            totNotaVenta = BigDecimal.ZERO;
            cajaCerrada = cierreCaja.getCieCerrada();
        }
        
    }
    
    @Command
    @NotifyChange({"cierreCaja"})
    public void calcularDiferencia() {
        if (cierreCaja.getCieCuadre() != null) {
            if (cierreCaja.getCieCuadre().doubleValue() > 0) {
                cierreCaja.setCieDiferencia(cierreCaja.getCieValor().subtract(cierreCaja.getCieCuadre()));
            }
        }
        
    }
    
    @Command
    @NotifyChange({"cierreCaja", "cajaCerrada"})
    public void guardar() {
        
        try {
            /*RECALCULAR EL VALOR DEL CUADRE PARA GARANTIZAR EL CIERRE CORRECTO*/
            cierreCaja.setCieDiferencia(cierreCaja.getCieValor().subtract(cierreCaja.getCieCuadre()));
            cierreCaja.setCieCredito(totalDeuda);
            cierreCaja.setCirRecaudado(totFactura);
            cierreCaja.setCieNotaVenta(totNotaVenta);
            cierreCaja.setCieTotal(totalEmitido);
            cierreCaja.setCieCerrada(Boolean.TRUE);
            servicioCierreCaja.modificar(cierreCaja);
            
            System.out.println("cierreCaja " + cierreCaja.getIdCierre());
            DispararReporte.reporteCierrecaja(cierreCaja.getIdCierre());
        } catch (JRException ex) {
            Logger.getLogger(CierreCajaVm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CierreCajaVm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(CierreCajaVm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CierreCajaVm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        windowCierre.detach();
    }
    
    public CierreCaja getCierreCaja() {
        return cierreCaja;
    }
    
    public void setCierreCaja(CierreCaja cierreCaja) {
        this.cierreCaja = cierreCaja;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public BigDecimal getTotFactura() {
        return totFactura;
    }
    
    public void setTotFactura(BigDecimal totFactura) {
        this.totFactura = totFactura;
    }
    
    public BigDecimal getTotNotaVenta() {
        return totNotaVenta;
    }
    
    public void setTotNotaVenta(BigDecimal totNotaVenta) {
        this.totNotaVenta = totNotaVenta;
    }
    
    public VistaFacturasPorCobrar getTotalesFactura() {
        return totalesFactura;
    }
    
    public void setTotalesFactura(VistaFacturasPorCobrar totalesFactura) {
        this.totalesFactura = totalesFactura;
    }
    
    public BigDecimal getTotalEmitido() {
        return totalEmitido;
    }
    
    public void setTotalEmitido(BigDecimal totalEmitido) {
        this.totalEmitido = totalEmitido;
    }
    
    public BigDecimal getTotalDeuda() {
        return totalDeuda;
    }
    
    public void setTotalDeuda(BigDecimal totalDeuda) {
        this.totalDeuda = totalDeuda;
    }
    
    public Boolean getCajaCerrada() {
        return cajaCerrada;
    }
    
    public void setCajaCerrada(Boolean cajaCerrada) {
        this.cajaCerrada = cajaCerrada;
    }
    
}
