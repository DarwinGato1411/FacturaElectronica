/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.FacturasActorizadaSri;
import com.ec.entidad.FacturasActorizadaSri;
import com.ec.untilitario.CantidadTotal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioFacturasAutorizadas {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<FacturasActorizadaSri> findFacturasAutorizadas(Date inicio, Date fin) {

        List<FacturasActorizadaSri> listaFacturasActorizadaSris = new ArrayList<FacturasActorizadaSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT  a FROM FacturasActorizadaSri a WHERE a.facFecha BETWEEN :inicio AND :fin ORDER BY a.facFecha  DESC");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaFacturasActorizadaSris = (List<FacturasActorizadaSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta FacturasActorizadaSri " + e.getMessage());
        } finally {
            em.close();
        }

        return listaFacturasActorizadaSris;
    }

    public CantidadTotal totalFacturasAutorizadas(Date inicio, Date fin) {

        CantidadTotal cantidadTotal = new CantidadTotal();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT  NEW com.ec.untilitario.CantidadTotal(COUNT(a.facNumero),SUM(a.facSubtotal),SUM(a.facTotalBaseGravaba),SUM(a.facTotalBaseCero)) FROM FacturasActorizadaSri a WHERE a.facFecha BETWEEN :inicio AND :fin ");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            cantidadTotal = (CantidadTotal) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta FacturasActorizadaSri " + e.getMessage());
        } finally {
            em.close();
        }

        return (cantidadTotal == null) ? new CantidadTotal() : cantidadTotal;
    }

}
