/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioUsuario {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Usuario usuario) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(usuario);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar usuario");
        } finally {
            em.close();
        }

    }

    public void eliminar(Usuario usuario) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(usuario));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  usuario" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Usuario usuario) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(usuario);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar usuario");
        } finally {
            em.close();
        }

    }

    public Usuario FindUsuarioPorNombre(String nombre) {

        List<Usuario> listaClientes = new ArrayList<Usuario>();
        Usuario usuarioObtenido = new Usuario();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.usuLogin = :usuLogin");
            query.setParameter("usuLogin", nombre);
            listaClientes = (List<Usuario>) query.getResultList();
            if (listaClientes.size() > 0) {
                for (Usuario usuario : listaClientes) {
                    usuarioObtenido = usuario;
                }
            } else {
                usuarioObtenido = null;
            }
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuario  FindUsuarioPorNombre  " + e);
        } finally {
            em.close();
        }

        return usuarioObtenido;
    }

    public List<Usuario> FindALlUsuarioPorLikeNombre(String nombre) {

        Usuario usuarioLogeado = new Usuario();
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        try {
            System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Usuario.findLikeNombre", Usuario.class);
            query.setParameter("usuNombre", "%" + nombre + "%");
            listaUsuarios = (List<Usuario>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta usuarios");
        } finally {
            em.close();
        }

        return listaUsuarios;
    }
}
