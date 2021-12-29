/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.DetalleRetencionCompra;
import com.ec.entidad.RetencionCompra;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalleRetencionCompra {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleRetencionCompra detalleRetencionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(detalleRetencionCompra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleRetencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(DetalleRetencionCompra detalleRetencionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(detalleRetencionCompra));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  detalleRetencionCompra" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(DetalleRetencionCompra detalleRetencionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(detalleRetencionCompra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleRetencionCompra");
        } finally {
            em.close();
        }

    }

    public List<DetalleRetencionCompra> findAll() {

        List<DetalleRetencionCompra> listaDetalleRetencionCompras = new ArrayList<DetalleRetencionCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM DetalleRetencionCompra a");
            listaDetalleRetencionCompras = (List<DetalleRetencionCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleRetencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDetalleRetencionCompras;
    }
    public List<DetalleRetencionCompra> findByCanRetencion(RetencionCompra retencioncab) {

        List<DetalleRetencionCompra> listaDetalleRetencionCompras = new ArrayList<DetalleRetencionCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM DetalleRetencionCompra a WHERE a.rcoCodigo.idCabecera=:idCabecera");
            query.setParameter("idCabecera", retencioncab.getIdCabecera());
            listaDetalleRetencionCompras = (List<DetalleRetencionCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleRetencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDetalleRetencionCompras;
    }
    
   

}
