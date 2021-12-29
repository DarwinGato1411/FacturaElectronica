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

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = format.format(new Date());
        //            inicio = format.parse("2020-02-01");
//            caduca = format.parse("2030-02-21");
        Parametrizar param = servicioParametrizar.FindALlParametrizar();
        NumeroDocumentosEmitidos emitidos = servicioNumeroDocumentosEmitidos.findByMes(caduca.getMonth() + 1);
//        NumeroDocumentosEmitidos emitidos = listaDocum != null ? listaDocum : null;
        caduca = param.getParCaduca();
        if (param.getParPlanBasico()) {
            System.out.println("emitidos " + emitidos);
            numeroDocumentos = emitidos.getNumero() == null ? 0 : emitidos.getNumero().intValue();
            System.out.println("numeroDocumentos " + numeroDocumentos);
            if (numeroDocumentos > param.getParNumeroFactura()) {
                System.out.println("caduco  " + actual + " vigente hasta " + caduca);
                Messagebox.show("Usted cuenta con un plan basico y sobre paso el limite de " + param.getParNumeroFactura() + " documentos ¡contactese con el administrador!", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
                return;
            }
        }
        System.out.println("actual " + actual);
        if (actual.after(caduca) && param.getParIlimitadoArriendo()) {
            System.out.println("caduco  " + actual + " vigente hasta " + caduca);
            Messagebox.show("Usted cuenta con un plan ilimitado mensual, pero su sistema caduco ¡contactese con el administrador!", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
        } else {
            System.out.println("vigente  " + actual + " vegente hasta " + caduca);
            AutentificadorLogeo servicioAuth = new AutentificadorLogeo();
            if (servicioAuth.login(account.getValue(), password.getValue())) {
                Session sess = Sessions.getCurrent();
                UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
                if (cre.getNivelUsuario().intValue() == GrupoUsuarioEnum.USUARIO.getCodigo()) {
                    Executions.sendRedirect("/venta/facturar.zul");
                } else if (cre.getNivelUsuario().intValue() == GrupoUsuarioEnum.ADMINISTRADOR.getCodigo()) {
                    Executions.sendRedirect("/venta/facturar.zul");
                }
            } else {
                Messagebox.show("Usuario o Contraseña incorrecto. \n Contactese con el administrador.", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);

            }
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
