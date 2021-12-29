/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.CierreCaja;
import com.ec.entidad.VistaVentaDiaria;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCierreCaja;
import com.ec.servicio.ServicioVistaVentaDiaria;
import java.math.BigDecimal;
import java.util.Date;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Darwin Morocho
 */
public class CierreCajaView {

    ServicioCierreCaja servicioCierreCaja = new ServicioCierreCaja();
    ServicioVistaVentaDiaria servicioVistaVentaDiaria = new ServicioVistaVentaDiaria();
    private CierreCaja cierreCaja = new CierreCaja();
    private VistaVentaDiaria vistaVentaDiaria = new VistaVentaDiaria();
    private Date fechaBusqueda = new Date();
    UserCredential credential = new UserCredential();

    public CierreCajaView() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        cargarCierre();
    }

    @Command
    @NotifyChange({"cierreCaja"})
    public void calcularDiferencia() {
        if (cierreCaja.getCieCuadre().doubleValue() > 0) {
            cierreCaja.setCieDiferencia(cierreCaja.getCieValor().add(cierreCaja.getCieCuadre().negate()));
        }

    }

    private void cargarCierre() {
        vistaVentaDiaria = servicioVistaVentaDiaria.FindALlVistaVentaDiariaForFecha(fechaBusqueda);
        if (vistaVentaDiaria != null) {
            cierreCaja = new CierreCaja();
            cierreCaja.setCieFecha(fechaBusqueda);
            cierreCaja.setCieValor(vistaVentaDiaria.getTotal());
            cierreCaja.setCieCuadre(BigDecimal.ZERO);
            cierreCaja.setCieDiferencia(BigDecimal.ZERO);
            cierreCaja.setCieDescripcion("");
        }
    }

    public CierreCaja getCierreCaja() {
        return cierreCaja;
    }

    public void setCierreCaja(CierreCaja cierreCaja) {
        this.cierreCaja = cierreCaja;
    }

    public VistaVentaDiaria getVistaVentaDiaria() {
        return vistaVentaDiaria;
    }

    public void setVistaVentaDiaria(VistaVentaDiaria vistaVentaDiaria) {
        this.vistaVentaDiaria = vistaVentaDiaria;
    }

    public Date getFechaBusqueda() {
        return fechaBusqueda;
    }

    public void setFechaBusqueda(Date fechaBusqueda) {
        this.fechaBusqueda = fechaBusqueda;
    }

    @Command
    @NotifyChange({"cierreCaja"})
    public void guardar() {
        if (servicioCierreCaja.findALlCierreCajaForFechaIdUsuario(fechaBusqueda, credential.getUsuarioSistema()).size() > 0) {
            servicioCierreCaja.crear(cierreCaja);
            Executions.sendRedirect("/venta/cierrecaja.zul");
        } else {
            Messagebox.show("Ya se registro un cierre de caja de este dia", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }
}
