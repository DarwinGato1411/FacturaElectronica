/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.EstadoFacturas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioEstadoFactura {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(EstadoFacturas estadoFacturas) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(estadoFacturas);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar estadoFacturas");
        } finally {
            em.close();
        }

    }

    public void eliminar(EstadoFacturas estadoFacturas) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(estadoFacturas));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  estadoFacturas" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(EstadoFacturas estadoFacturas) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(estadoFacturas);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar estadoFacturas");
        } finally {
            em.close();
        }

    }

    public List<EstadoFacturas> FindALlEstadoFacturas() {

        List<EstadoFacturas> listaEstadoFacturass = new ArrayList<EstadoFacturas>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("EstadoFacturas.findAll", EstadoFacturas.class);
//           query.setParameter("codigoUsuario", estadoFacturas);
            listaEstadoFacturass = (List<EstadoFacturas>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta estadoFacturas");
        } finally {
            em.close();
        }

        return listaEstadoFacturass;
    }

    public EstadoFacturas findByEstCodigo(String buscar) {

        EstadoFacturas listaEstadoFacturass = new EstadoFacturas();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("EstadoFacturas.findByEstCodigo", EstadoFacturas.class);
            query.setParameter("estCodigo", buscar);
            listaEstadoFacturass = (EstadoFacturas) query.getSingleResult();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta estadoFacturas");
        } finally {
            em.close();
        }

        return listaEstadoFacturass;
    }
}
