/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Tipoambiente;
import com.ec.entidad.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTipoAmbiente {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Tipoambiente tipoambiente) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(tipoambiente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en insertar tipoambiente " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Tipoambiente tipoambiente) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(tipoambiente));
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en eliminar  tipoambiente " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(Tipoambiente tipoambiente) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(tipoambiente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en insertar tipoambiente " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public Tipoambiente FindALlTipoambiente() {

        List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
        Tipoambiente tipoambiente = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Tipoambiente.findAllActivo", Tipoambiente.class);
//           query.setParameter("codigoUsuario", tipoambiente);
            listaTipoambientes = (List<Tipoambiente>) query.getResultList();
            if (listaTipoambientes.size() > 0) {
                tipoambiente = listaTipoambientes.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoambiente");
        } finally {
            em.close();
        }

        return tipoambiente;
    }

    public Tipoambiente findByAmCodigo(String amCodigo) {

        List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
        Tipoambiente tipoambiente = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Tipoambiente.findByAmCodigo", Tipoambiente.class);
            query.setParameter("amCodigo", amCodigo);
            listaTipoambientes = (List<Tipoambiente>) query.getResultList();
            if (listaTipoambientes.size() > 0) {
                tipoambiente = listaTipoambientes.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoambiente");
        } finally {
            em.close();
        }

        return tipoambiente;
    }

    public Tipoambiente findByUsuario(Usuario usuario) {

        List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
        Tipoambiente tipoambiente = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM tipo a where a.idUsuario=:idUsuario AND a.amEstado=TRUE");
            query.setParameter("idUsuario", usuario);
            listaTipoambientes = (List<Tipoambiente>) query.getResultList();
            if (!listaTipoambientes.isEmpty()) {
                tipoambiente = listaTipoambientes.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoambiente " + e.getMessage());
        } finally {
            em.close();
        }

        return tipoambiente;
    }
}
