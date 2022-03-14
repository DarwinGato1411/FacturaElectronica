/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.DetalleCompra;
import com.ec.entidad.docsri.RetencionCompraSri;
import com.ec.untilitario.CompraPromedio;

import com.ec.untilitario.DetalleCompraUtil;
import com.ec.untilitario.Totales;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioRetencionSri {

    ServicioDetalleCompra servicioIngresoProducto = new ServicioDetalleCompra();
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(RetencionCompraSri retencionCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(retencionCompraSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar retencionCompraSri");
        } finally {
            em.close();
        }

    }

    public void eliminar(RetencionCompraSri retencionCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(retencionCompraSri));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  retencionCompraSri" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(RetencionCompraSri retencionCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(retencionCompraSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar retencionCompraSri");
        } finally {
            em.close();
        }

    }

    public List<RetencionCompraSri> findRetencionesBetween(Date inicio, Date fin) {

        List<RetencionCompraSri> listaRetencionCompraSris = new ArrayList<RetencionCompraSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM RetencionCompraSri a WHERE a.cabFechaEmision BETWEEN :inicio AND :fin ORDER BY a.cabFechaEmision DESC");
           query.setParameter("inicio", inicio);
           query.setParameter("fin", fin);
            listaRetencionCompraSris = (List<RetencionCompraSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompraSri");
        } finally {
            em.close();
        }

        return listaRetencionCompraSris;
    }
     public void eliminarbyClaveAcceso(String clave) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM RetencionCompraSri c WHERE c.rcoAutorizacion=:clave");
            query.setParameter("clave", clave);
            int deletedCount = query.executeUpdate();

            System.out.println("deletedCount " + deletedCount);
            //  em.remove(em.merge(cabeceraCompraSri));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  cabeceraCompraSri" + e.getMessage());
        } finally {
            em.close();
        }

    }

   
}
