/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.ReporteRetencion;
import com.ec.entidad.ReporteRetencion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioReporteRetencion {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    

    public List<ReporteRetencion> findAll() {

        List<ReporteRetencion> listaReporteRetencions = new ArrayList<ReporteRetencion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ReporteRetencion a");
            listaReporteRetencions = (List<ReporteRetencion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaReporteRetencions;
    }

    
    public ReporteRetencion findByCabeceraCompra(CabeceraCompra cabcompra) {
        ReporteRetencion retencionCompra1 = null;
        List<ReporteRetencion> listaReporteRetencions = new ArrayList<ReporteRetencion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ReporteRetencion a where a.idCabecera=:idCabecera");
            query.setParameter("idCabecera", cabcompra);
            listaReporteRetencions = (List<ReporteRetencion>) query.getResultList();
            if (listaReporteRetencions.size() > 0) {
                retencionCompra1 = listaReporteRetencions.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return retencionCompra1;
    }

    

    public List<ReporteRetencion> findByFecha(Date inicio, Date fin) {

        List<ReporteRetencion> listaReporteRetencions = new ArrayList<ReporteRetencion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ReporteRetencion a WHERE a.rcoFecha BETWEEN :inicio AND :fin ORDER BY a.rcoFecha DESC ");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaReporteRetencions = (List<ReporteRetencion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaReporteRetencions;
    }

    public List<ReporteRetencion> findByNumeroFactura(String valor) {

        List<ReporteRetencion> listaReporteRetencions = new ArrayList<ReporteRetencion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ReporteRetencion a WHERE a.cabNumFactura LIKE :cabNumFactura ORDER BY a.rcoFecha DESC ");
            query.setParameter("cabNumFactura", "%" + valor + "%");
            listaReporteRetencions = (List<ReporteRetencion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaReporteRetencions;
    }

    public List<ReporteRetencion> findBySecuencialRet(String valor) {

        List<ReporteRetencion> listaReporteRetencions = new ArrayList<ReporteRetencion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ReporteRetencion a WHERE a.rcoSecuencialText LIKE :rcoSecuencialText ORDER BY a.rcoFecha DESC ");
            query.setParameter("rcoSecuencialText", "%" + valor + "%");
            listaReporteRetencions = (List<ReporteRetencion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaReporteRetencions;
    }

}
