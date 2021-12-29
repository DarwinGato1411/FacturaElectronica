/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Tipoadentificacion;
import com.ec.entidad.Tipoadentificacion;
import com.ec.entidad.Tipoadentificacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTipoIdentificacion {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Tipoadentificacion tipoadentificacion) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(tipoadentificacion);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoadentificacion");
        } finally {
            em.close();
        }

    }

    public void eliminar(Tipoadentificacion tipoadentificacion) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(tipoadentificacion));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  tipoadentificacion" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Tipoadentificacion tipoadentificacion) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(tipoadentificacion);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoadentificacion");
        } finally {
            em.close();
        }

    }

    public List<Tipoadentificacion>  FindALlTipoadentificacion() {

        List<Tipoadentificacion> listaTipoadentificacions = new ArrayList<Tipoadentificacion>();
        Tipoadentificacion tipoadentificacion = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Tipoadentificacion.findAll", Tipoadentificacion.class);
//           query.setParameter("codigoUsuario", tipoadentificacion);
            listaTipoadentificacions = (List<Tipoadentificacion>) query.getResultList();
//            if (listaTipoadentificacions.size() > 0) {
//                tipoadentificacion = listaTipoadentificacions.get(0);
//            }
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoadentificacion");
        } finally {
            em.close();
        }

        return listaTipoadentificacions;
    }
    
    public Tipoadentificacion  findByTipoIdentificacion(String tidNombre) {

        List<Tipoadentificacion> listaTipoadentificacions = new ArrayList<Tipoadentificacion>();
        Tipoadentificacion t=null;
        Tipoadentificacion tipoadentificacion = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createQuery("SELECT A FROM Tipoadentificacion a WHERE a.tidNombre=:tidNombre");
           query.setParameter("tidNombre", tidNombre);
            listaTipoadentificacions = (List<Tipoadentificacion>) query.getResultList();
            if (listaTipoadentificacions.size() > 0) {
                tipoadentificacion = listaTipoadentificacions.get(0);
            }
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoadentificacion");
        } finally {
            em.close();
        }

        return tipoadentificacion;
    }
    
    
}
