/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Tipokardex;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTipoKardex {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Tipokardex tipokardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(tipokardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipokardex " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Tipokardex tipokardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(tipokardex));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar tipokardex " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(Tipokardex tipokardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(tipokardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en modificar tipokardex " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<Tipokardex> findAll() {

        List<Tipokardex> listaTipokardexs = new ArrayList<Tipokardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Tipokardex.findAll", Tipokardex.class);
            listaTipokardexs = (List<Tipokardex>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipokardex " + e.getMessage());
        } finally {
            em.close();
        }

        return listaTipokardexs;
    }

    public Tipokardex findByTipkSigla(String tipkSigla) {

        List<Tipokardex> listaTipokardexs = new ArrayList<Tipokardex>();
        Tipokardex tipokardex = null;
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Tipokardex.findByTipkSigla", Tipokardex.class);
            query.setParameter("tipkSigla", tipkSigla);
            listaTipokardexs = (List<Tipokardex>) query.getResultList();
            if (listaTipokardexs.size() > 0) {
                tipokardex = listaTipokardexs.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipokardex " + e.getMessage());
        } finally {
            em.close();
        }

        return tipokardex;
    }

}
