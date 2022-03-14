/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;


import com.ec.entidad.sri.DetalleRetencionCompraSri;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalleRetencionSri {

    ServicioDetalleCompra servicioIngresoProducto = new ServicioDetalleCompra();
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleRetencionCompraSri detalleRetencionCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(detalleRetencionCompraSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleRetencionCompraSri");
        } finally {
            em.close();
        }

    }

    public void eliminar(DetalleRetencionCompraSri detalleRetencionCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(detalleRetencionCompraSri));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  detalleRetencionCompraSri" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(DetalleRetencionCompraSri detalleRetencionCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(detalleRetencionCompraSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleRetencionCompraSri");
        } finally {
            em.close();
        }

    }

    public List<DetalleRetencionCompraSri> FindALlDetalleRetencionCompraSri() {

        List<DetalleRetencionCompraSri> listaDetalleRetencionCompraSris = new ArrayList<DetalleRetencionCompraSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleRetencionCompraSri.findAll", DetalleRetencionCompraSri.class);
//           query.setParameter("codigoUsuario", detalleRetencionCompraSri);
            listaDetalleRetencionCompraSris = (List<DetalleRetencionCompraSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleRetencionCompraSri");
        } finally {
            em.close();
        }

        return listaDetalleRetencionCompraSris;
    }

     public List<DetalleRetencionCompraSri> findBetweenDetalle(Date inicio, Date fin) {

        List<DetalleRetencionCompraSri> listaDetalleRetencionCompraSris = new ArrayList<DetalleRetencionCompraSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM DetalleRetencionCompraSri a WHERE a.rcoCodigo.cabFechaEmision BETWEEN :inicio AND :fin ORDER BY a.rcoCodigo.cabFechaEmision DESC ");
           query.setParameter("inicio", inicio);
           query.setParameter("fin", fin);
            listaDetalleRetencionCompraSris = (List<DetalleRetencionCompraSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta findBetweenDetalle "+e.getMessage());
        } finally {
            em.close();
        }

        return listaDetalleRetencionCompraSris;
    }
   
}
