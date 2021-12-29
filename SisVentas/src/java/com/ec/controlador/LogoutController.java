/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.seguridad.AutentificadorLogeo;
import com.ec.seguridad.AutentificadorService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

public class LogoutController extends SelectorComposer<Component> {

    //services
    AutentificadorService authService = new AutentificadorLogeo();

    @Listen("onClick=#logout")
    public void doLogout() {
        authService.logout();
        Executions.sendRedirect("/");
    }
}