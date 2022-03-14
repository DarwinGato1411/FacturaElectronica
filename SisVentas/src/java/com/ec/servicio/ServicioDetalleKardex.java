/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.DetalleKardex;
import com.ec.entidad.Kardex;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalleKardex {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleKardex detalleKardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(detalleKardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleKardex simple " + e.getMessage());
            e.getStackTrace();
            StackTraceElement[] elems = e.getStackTrace();
            for (int i = 0; i < elems.length; i++) {
                System.out.println("ERROR CREAR deTALLE kARDEX "+elems[i].toString());
            }
        } finally {
            em.close();
        }

    }

    public void eliminar(DetalleKardex detalleKardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(detalleKardex));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  detalleKardex" + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminarKardexVenta(Integer idVenta) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM DetalleKardex dt WHERE dt.idVenta=:idVenta");
            query.setParameter("idVenta", idVenta);
            query.executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  detalleKardex" + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminarKardexCompra(Integer idCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM DetalleKardex dt WHERE dt.idCompra=:idCompra");
            query.setParameter("idCompra", idCompra);
            query.executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  detalleKardex" + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(DetalleKardex detalleKardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(detalleKardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleKardex" + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<DetalleKardex> findAll() {

        List<DetalleKardex> listadetalleKardexs = new ArrayList<DetalleKardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleKardex.findAll", DetalleKardex.class);
            listadetalleKardexs = (List<DetalleKardex>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleKardex " + e.getMessage());
        } finally {
            em.close();
        }

        return listadetalleKardexs;
    }

    public List<DetalleKardex> findByIdKardex(Kardex kardex) {

        List<DetalleKardex> listadetalleKardexs = new ArrayList<DetalleKardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT d FROM DetalleKardex d WHERE d.idKardex = :idKardex ORDER BY d.detkFechakardex ASC");
            query.setParameter("idKardex", kardex);
            listadetalleKardexs = (List<DetalleKardex>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleKardex " + e.getMessage());
        } finally {
            em.close();
        }

        return listadetalleKardexs;
    }

}
