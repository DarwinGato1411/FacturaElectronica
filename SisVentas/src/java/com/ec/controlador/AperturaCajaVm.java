/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.CierreCaja;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCierreCaja;
import java.math.BigDecimal;
import java.util.Date;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class AperturaCajaVm {
    
    @Wire
    Window windowAperCaja;
    private CierreCaja cierreCaja = new CierreCaja();
    ServicioCierreCaja servicioCierreCaja = new ServicioCierreCaja();
    UserCredential credential = new UserCredential();
    
    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        
        Session sess = Sessions.getCurrent();
        //sess.setMaxInactiveInterval(10000);
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        cierreCaja.setCieValorInicio(BigDecimal.ZERO);
        cierreCaja.setCieCuadre(BigDecimal.ZERO);
        cierreCaja.setCieValor(BigDecimal.ZERO);
        cierreCaja.setCieDiferencia(BigDecimal.ZERO);
        cierreCaja.setCieFecha(new Date());
        cierreCaja.setIdUsuario(credential.getUsuarioSistema());
        cierreCaja.setCieCerrada(Boolean.FALSE);
    }
    
    @Command
    public void guardar() {
        if (cierreCaja.getCieValorInicio() != null) {
            servicioCierreCaja.crear(cierreCaja);
            Clients.showNotification("Apertura  de caja correcta",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 2000, true);
            windowAperCaja.detach();
        } else {
            Clients.showNotification("Ingrese un valor de apertura",
                    Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
        }
        
    }
    
    public CierreCaja getCierreCaja() {
        return cierreCaja;
    }
    
    public void setCierreCaja(CierreCaja cierreCaja) {
        this.cierreCaja = cierreCaja;
    }
    
}
