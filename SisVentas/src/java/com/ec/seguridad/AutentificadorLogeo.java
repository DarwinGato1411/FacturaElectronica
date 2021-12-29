/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.seguridad;

import com.ec.entidad.Usuario;
import com.ec.servicio.ServicioUsuario;
import java.io.Serializable;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

public class AutentificadorLogeo implements AutentificadorService, Serializable {

    private static final long serialVersionUID = 1L;
    ServicioUsuario servicioUsuario = new ServicioUsuario();

    public UserCredential getUserCredential() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        if (cre == null) {
            cre = new UserCredential();
            sess.setAttribute(EnumSesion.userCredential.getNombre(), cre);
        }
        return cre;
    }


    /*
     * Cambiar el método en el ModeloUsuario para traer datos de los usuarios de hibernate
     */
    public boolean login(String nombreUsuario, String claveUsuario) {
        Usuario dato = (Usuario) servicioUsuario.FindUsuarioPorNombre(nombreUsuario);

//        System.out.println("usuario " + nombreUsuario);
//        System.out.println("Valor recuperado");
//        System.out.println("-------------->"+dato.getUsuLogin());
        if (dato == null) {
            return false;
        }
        if (!dato.getUsuLogin().equals(nombreUsuario) || !dato.getUsuPassword().equals(claveUsuario)) {
            return false;
        }

        Session sess = Sessions.getCurrent();
        UserCredential cre = new UserCredential(dato, dato.getUsuLogin(), dato.getUsuPassword(), dato.getUsuNivel(), dato.getUsuNombre());
        // System.out.println("VALOR DE LA CREDENCIAL ASIGNADA A LA SESSION"+EnumSesion.userCredential.getNombre());

        sess.setAttribute(EnumSesion.userCredential.getNombre(), cre);

        return true;
    }

    public void logout() {
        Session sess = Sessions.getCurrent();
        sess.removeAttribute(EnumSesion.userCredential.getNombre());
    }
}
