/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Usuario;
import com.ec.servicio.ServicioUsuario;
import org.zkoss.bind.annotation.AfterCompose;
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
public class AgregarUsuario {

    @Wire
    Window windowIdUsuario;
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    private Usuario usuarioSistema = new Usuario();
    private String tipoUSuario = "1";
    private String accion = "create";

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("usuario") Usuario usuarioSistema, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (usuarioSistema != null) {
            this.usuarioSistema = usuarioSistema;
            tipoUSuario = this.usuarioSistema.getUsuNivel().toString();
            accion = "update";
        } else {
            this.usuarioSistema = new Usuario();
            accion = "create";
        }
    }

    public Usuario getUsuarioSistema() {
        return usuarioSistema;
    }

    public void setUsuarioSistema(Usuario usuarioSistema) {
        this.usuarioSistema = usuarioSistema;
    }

    public String getTipoUSuario() {
        return tipoUSuario;
    }

    public void setTipoUSuario(String tipoUSuario) {
        this.tipoUSuario = tipoUSuario;
    }

    @Command
    @NotifyChange("usuarioSistema")
    public void guardar() {
        if (usuarioSistema != null && !usuarioSistema.getUsuNombre().equals("")
                    && !usuarioSistema.getUsuLogin().equals("")
                    && usuarioSistema.getUsuCaduca() != null
                    && usuarioSistema.getUsuNumEmpresas() != null
                    && !tipoUSuario.equals("")) {
            usuarioSistema.setUsuNivel(Integer.valueOf(tipoUSuario));

            if (accion.equals("accion")) {
                if (Integer.valueOf(tipoUSuario) == 1) {
                    usuarioSistema.setUsuTipoUsuario("ADMINISTRADOR");
                } else if (Integer.valueOf(tipoUSuario) == 2) {
                    usuarioSistema.setUsuTipoUsuario("CONTADOR");
                }
                servicioUsuario.crear(usuarioSistema);

            } else {
                servicioUsuario.modificar(usuarioSistema);

            }
            windowIdUsuario.detach();
        } else {
            Clients.showNotification("Verifique la informacion requerida",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
        }

    }
}
