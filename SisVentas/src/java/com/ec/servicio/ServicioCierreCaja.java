/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.CierreCaja;
import com.ec.entidad.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCierreCaja {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(CierreCaja cierreCaja) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(cierreCaja);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cierreCaja " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(CierreCaja cierreCaja) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(cierreCaja));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  cierreCaja" + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(CierreCaja cierreCaja) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(cierreCaja);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cierreCaja " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<CierreCaja> FindALlCierreCaja() {

        List<CierreCaja> listaCierreCajas = new ArrayList<CierreCaja>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("SELECT c FROM CierreCaja c");
            listaCierreCajas = (List<CierreCaja>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cierreCaja " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCierreCajas;
    }

    public List<CierreCaja> findALlCierreCajaForFechaIdUsuario(Date fecha, Usuario idUsuario) {
//        CierreCaja cierreCaja = null;
        List<CierreCaja> listaCierreCajas = new ArrayList<CierreCaja>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            String SQL = "SELECT a FROM CierreCaja a WHERE a.idUsuario=:idUsuario ";
            String WHERE = "  AND a.cieFecha=:cieFecha ORDER BY  a.cieFecha DESC";
            Query query = null;
            if (idUsuario.getUsuNivel() == 1) {
                query = em.createQuery(SQL + WHERE);
                query.setParameter("idUsuario", idUsuario);
                 query.setParameter("cieFecha", fecha);
            } else {
                query = em.createQuery(SQL + WHERE);
                query.setParameter("idUsuario", idUsuario);
                query.setParameter("cieFecha", fecha);
            }

            listaCierreCajas = (List<CierreCaja>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cierreCaja " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCierreCajas;
    }

    public List<CierreCaja> findAllCierres(Date fecha, Usuario idUsuario) {
//        CierreCaja cierreCaja = null;
        List<CierreCaja> listaCierreCajas = new ArrayList<CierreCaja>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            String SQL = "SELECT a FROM CierreCaja a ";
            String WHERE = " WHERE a.cieFecha=:cieFecha ORDER BY  a.cieFecha DESC";
            Query query = null;

            query = em.createQuery(SQL + WHERE);
            query.setParameter("cieFecha", fecha);

            listaCierreCajas = (List<CierreCaja>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta cierreCaja " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCierreCajas;
    }
}
