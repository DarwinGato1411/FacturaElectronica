/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Cliente;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCliente;
import org.zkoss.bind.annotation.Command;
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

public class LoginControllerPortal extends SelectorComposer<Component> {

    /**
     *
     */
    ServicioCliente servicioCliente = new ServicioCliente();
    private static final long serialVersionUID = 1L;
    @Wire
    Textbox accountTest;
    @Wire
    Textbox passwordTest;
    @Wire
    Label messageTest;

    public void LoginController() {
    }

    @Listen("onClick=#buttonEntrarTest; onOK=#loginWinTest")
    public void doLogin() {
        Cliente cli = servicioCliente.findByCliCedulaPassword(accountTest.getValue(), passwordTest.getValue());
        if (cli != null) {
            Session sess = Sessions.getCurrent();

            UserCredential cre = new UserCredential(cli);
            // System.out.println("VALOR DE LA CREDENCIAL ASIGNADA A LA SESSION"+EnumSesion.userCredential.getNombre());

            sess.setAttribute(EnumSesion.userCredential.getNombre(), cre);
            Executions.sendRedirect("/portal/portalfacturas.zul");
        } else {
            Messagebox.show("Usuario o Contraseña incorrecto. \n Contactese con el administrador.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);

        }

    }

    @Command
    @Listen("onClick= #Idprincipal")
    public void ingresarPsico() {
        Executions.sendRedirect("/portal/inicio.zul");
    }
}
