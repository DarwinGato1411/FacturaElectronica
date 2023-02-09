/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.NumeroDocumentosEmitidos;
import com.ec.entidad.Parametrizar;
import com.ec.seguridad.AutentificadorLogeo;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.GrupoUsuarioEnum;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioParametrizar;
import com.ec.vista.servicios.ServicioNumeroDocumentosEmitidos;
import java.util.Date;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LoginController extends SelectorComposer<Component> {

    /**
     *
     */
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    ServicioNumeroDocumentosEmitidos servicioNumeroDocumentosEmitidos = new ServicioNumeroDocumentosEmitidos();
    private static final long serialVersionUID = 1L;
    @Wire
    Textbox account;
    @Wire
    Textbox password;
    @Wire
    Label message;

    Integer numeroDocumentos = 0;

    public void LoginController() {
    }

    @Listen("onClick=#buttonEntrar; onOK=#loginWin")
    public void doLogin() {
        Date actual = new Date();
        Date caduca = new Date();


   
        AutentificadorLogeo servicioAuth = new AutentificadorLogeo();
        if (servicioAuth.login(account.getValue(), password.getValue())) {
            Session sess = Sessions.getCurrent();
            UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
            if (cre.getNivelUsuario().intValue() == GrupoUsuarioEnum.USUARIO.getCodigo()) {

                if (actual.after(cre.getUsuarioSistema().getUsuCaduca())) {
                    System.out.println("caduco  " + actual + " vigente hasta " + cre.getUsuarioSistema().getUsuCaduca());
                    Messagebox.show("Usted cuenta con un plan ilimitado anual, pero su sistema caduco ¡contactese con el administrador!", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
                    return;
                }
                Executions.sendRedirect("/compra/listacomprassri.zul");
            } else if (cre.getNivelUsuario().intValue() == GrupoUsuarioEnum.ADMINISTRADOR.getCodigo()) {
                  Executions.sendRedirect("/administrar/gestionusuarios.zul");
            }
        } else {
            Messagebox.show("Usuario o Contraseña incorrecto. \n Contactese con el administrador.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);

        }

    }

    @Listen("onClick = #linkRegistrarme")
    public void doRegistrarme() {
        Window window = (Window) Executions.createComponents(
                    "/celec/candidato/registrame.zul", null, null);
        window.doModal();
    }

    @Listen("onClick= #linkOlvideContrasena")
    public void linkOlvideContrasena() {
        Window window = (Window) Executions.createComponents(
                    "/celec/candidato/olvideMiClave.zul", null, null);
        window.doModal();
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    @Listen("onClick = #buttonConsultar")
    public void buttonConsultar() {
        Executions.sendRedirect("/consultas.zul");
    }
}
