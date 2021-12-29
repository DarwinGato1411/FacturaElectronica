/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.VistaIngresoEgreso;
import com.ec.entidad.VistaIngresoEgreso;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioVistaIngresoEgreso {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(VistaIngresoEgreso vistaIngresoEgreso) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(vistaIngresoEgreso);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar vistaIngresoEgreso");
        } finally {
            em.close();
        }

    }

    public void eliminar(VistaIngresoEgreso vistaIngresoEgreso) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(vistaIngresoEgreso));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  vistaIngresoEgreso" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(VistaIngresoEgreso vistaIngresoEgreso) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(vistaIngresoEgreso);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar vistaIngresoEgreso");
        } finally {
            em.close();
        }

    }

    public List<VistaIngresoEgreso> FindALlVistaIngresoEgreso() {

        List<VistaIngresoEgreso> listaVistaIngresoEgresos = new ArrayList<VistaIngresoEgreso>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("VistaIngresoEgreso.findAll", VistaIngresoEgreso.class);
            listaVistaIngresoEgresos = (List<VistaIngresoEgreso>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta vistaIngresoEgreso");
        } finally {
            em.close();
        }

        return listaVistaIngresoEgresos;
    }

    public List<VistaIngresoEgreso> FindALlLikeVistaIngresoEgreso(String nombre, BigDecimal stock) {

        List<VistaIngresoEgreso> listaVistaIngresoEgresos = new ArrayList<VistaIngresoEgreso>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("VistaIngresoEgreso.findByLikeProdNombre", VistaIngresoEgreso.class);
            query.setParameter("prodNombre", "%" + nombre + "%");
            query.setParameter("stock", stock);
            listaVistaIngresoEgresos = (List<VistaIngresoEgreso>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta vistaIngresoEgreso");
        } finally {
            em.close();
        }

        return listaVistaIngresoEgresos;
    }
}
