/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Pais;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioPais {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Pais pais) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(pais);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar     pais " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Pais pais) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(pais));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar      pais " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(Pais pais) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(pais);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar     pais " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<Pais> findAll() {

        List<Pais> listaPaiss = new ArrayList<Pais>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Pais a ORDER BY a.paNombre ASC");
//           query.setParameter("codigoUsuario",     pais);
            listaPaiss = (List<Pais>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta     pais " + e.getMessage());
        } finally {
            em.close();
        }

        return listaPaiss;
    }

    public Pais findDefectoOrigen() {

        Pais respuesta = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Pais a WHERE a.paOrigen=:paOrigen ORDER BY a.paNombre ASC");
            query.setParameter("paOrigen", Boolean.TRUE);
            List<Pais> listaPaiss = (List<Pais>) query.getResultList();
            if (!listaPaiss.isEmpty()) {
                return listaPaiss.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta     pais " + e.getMessage());
        } finally {
            em.close();
        }

        return respuesta;
    }

    public Pais findDefectoDestino() {

        Pais respuesta = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Pais a WHERE a.paDestino=:paDestino ORDER BY a.paNombre ASC");
            query.setParameter("paDestino", Boolean.TRUE);
            List<Pais> listaPaiss = (List<Pais>) query.getResultList();
            if (listaPaiss.isEmpty()) {
                return listaPaiss.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta     pais " + e.getMessage());
        } finally {
            em.close();
        }

        return respuesta;
    }

     public Pais findByNombre(String nombre) {

        Pais respuesta = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Pais a WHERE a.paNombre=:paNombre ORDER BY a.paNombre ASC");
            query.setParameter("paNombre", nombre);
            List<Pais> listaPaiss = (List<Pais>) query.getResultList();
            if (!listaPaiss.isEmpty()) {
                return listaPaiss.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta     pais " + e.getMessage());
        } finally {
            em.close();
        }

        return respuesta;
    }
}
