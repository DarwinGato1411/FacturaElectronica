/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.ComprasSri;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.sri.CabeceraCompraSri;
import com.ec.entidad.sri.DetalleCompraSri;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalleComprasSri {

    ServicioDetalleCompra servicioIngresoProducto = new ServicioDetalleCompra();
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleCompraSri detalleCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(detalleCompraSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleCompraSri " + e.getMessage());
            StackTraceElement[] elems = e.getStackTrace();
            for (int i = 0; i < elems.length; i++) {
                System.out.println("ERROR CREAR ServicioDetalleComprasSri " + elems[i].toString());
            }
        } finally {
            em.close();
        }

    }

    public void eliminar(DetalleCompraSri detalleCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(detalleCompraSri));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  ServicioDetalleComprasSri" + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(DetalleCompraSri detalleCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(detalleCompraSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleCompraSri " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<DetalleCompraSri> findByBetweenFechaSRI(Date incio, Date fin) {

        List<DetalleCompraSri> listaCabeceraCompras = new ArrayList<DetalleCompraSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM DetalleCompraSri c WHERE c.idCabeceraSri.cabFechaEmision BETWEEN :inicio AND :fin ORDER BY c.idCabeceraSri.cabFechaEmision DESC");
            query.setParameter("inicio", incio);
            query.setParameter("fin", fin);
            listaCabeceraCompras = (List<DetalleCompraSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta DetalleCompraSri " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCabeceraCompras;
    }

    public List<DetalleCompraSri> detallebyCompraSri(CabeceraCompraSri valor) {

        List<DetalleCompraSri> listaCabeceraCompras = new ArrayList<DetalleCompraSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM DetalleCompraSri c WHERE c.idCabeceraSri=:idCabeceraSri ORDER BY c.idCabeceraSri.cabFechaEmision DESC");
            query.setParameter("idCabeceraSri", valor);
//            query.setParameter("fin", fin);
            listaCabeceraCompras = (List<DetalleCompraSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta DetalleCompraSri " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCabeceraCompras;
    }

    public List<DetalleCompraSri> detalleCompraSriForTipoambiente(Tipoambiente codTipoambiente, String iprodClasificacion) {

        List<DetalleCompraSri> listaCabeceraCompras = new ArrayList<DetalleCompraSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            String SQL = "SELECT c FROM DetalleCompraSri c WHERE c.idCabeceraSri.codTipoambiente=:codTipoambiente ";
            String WHERE = " AND c.iprodClasificacion=:iprodClasificacion ";
            String ORDERBY = " ORDER BY c.idCabeceraSri.cabFecha ASC";

            if (iprodClasificacion.equals("TODO")) {
                SQL = SQL + ORDERBY;
            } else {
                SQL = SQL + WHERE + ORDERBY;
            }
            System.out.println("SQL "+SQL);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery(SQL);
            query.setParameter("codTipoambiente", codTipoambiente);
            if (!iprodClasificacion.equals("TODO")) {
                query.setParameter("iprodClasificacion", iprodClasificacion);
            }
            listaCabeceraCompras = (List<DetalleCompraSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta DetalleCompraSri " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCabeceraCompras;
    }
}
