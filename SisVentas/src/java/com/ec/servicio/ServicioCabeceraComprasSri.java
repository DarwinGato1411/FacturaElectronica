/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.sri.CabeceraCompraSri;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCabeceraComprasSri {

    ServicioDetalleCompra servicioIngresoProducto = new ServicioDetalleCompra();
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(CabeceraCompraSri cabeceraCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(cabeceraCompraSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cabeceraCompraSri " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(CabeceraCompraSri cabeceraCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(cabeceraCompraSri));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  cabeceraCompraSri" + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminarbyClaveAcceso(String clave) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM CabeceraCompraSri c WHERE c.cabClaveAcceso=:clave");
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

    public void modificar(CabeceraCompraSri cabeceraCompraSri) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(cabeceraCompraSri);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar cabeceraCompraSri " + e.getMessage()
            );
        } finally {
            em.close();
        }

    }

    public List<CabeceraCompraSri> findByBetweenFechaSRI(Date incio, Date fin) {

        List<CabeceraCompraSri> listaCabeceraCompras = new ArrayList<CabeceraCompraSri>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM CabeceraCompraSri c WHERE c.cabFechaEmision BETWEEN :inicio AND :fin AND c.cabHomologado='N' ORDER BY c.cabFechaEmision DESC");
            query.setParameter("inicio", incio);
            query.setParameter("fin", fin);
            listaCabeceraCompras = (List<CabeceraCompraSri>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta compra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCabeceraCompras;
    }

    public CabeceraCompraSri findByClaveAccesoSRI(String clave) {

        CabeceraCompraSri respuesta = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT c FROM CabeceraCompraSri c WHERE c.cabClaveAcceso=:clave");
            query.setParameter("clave", clave);

            respuesta = (CabeceraCompraSri) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta findByClaveAccesoSRI " + e.getMessage());
        } finally {
            em.close();
        }

        return respuesta;
    }

}
