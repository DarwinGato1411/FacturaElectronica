/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.DetallePago;
import com.ec.entidad.DetallePago;
import com.ec.entidad.Factura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetallePago {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetallePago detallePago) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(detallePago);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detallePago " + e);
        } finally {
            em.close();
        }

    }

    public void eliminar(DetallePago detallePago) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(detallePago));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  detallePago" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(DetallePago detallePago) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(detallePago);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detallePago " + e);
        } finally {
            em.close();
        }

    }

    public List<DetallePago> finForIdFactura(Factura factura) {

        List<DetallePago> listaDatos = new ArrayList<DetallePago>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM DetallePago a WHERE a.idFactura=:idFactura");
            query.setParameter("idFactura", factura);
            listaDatos = (List<DetallePago>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detallePago " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }
}
