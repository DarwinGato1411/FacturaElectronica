/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Usuario;
import com.ec.servicio.ServicioUsuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author gato
 */
public class AdministrarUsuario {

    ServicioUsuario servicioUsuario = new ServicioUsuario();
    private List<Usuario> listaUsuarios = new ArrayList<Usuario>();

    public AdministrarUsuario() {
        cosultarUsuarios("");
    }

    private void cosultarUsuarios(String buscar) {
        listaUsuarios = servicioUsuario.FindALlUsuarioPorLikeNombre(buscar);
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    //usuarios
    @Command
    @NotifyChange("listaUsuarios")
    public void agregarUsario() {
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/usuario.zul", null, null);
        window.doModal();
        cosultarUsuarios("");
    }

    @Command
    @NotifyChange("listaUsuarios")
    public void modificarUsario(@BindingParam("usuario") Usuario usuario) {
        final HashMap<String, Usuario> map = new HashMap<String, Usuario>();
        map.put("usuario", usuario);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/usuario.zul", null, map);
        window.doModal();
        cosultarUsuarios("");
    }
}
