/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;


import com.ec.entidad.sri.DetalleCompraSri;
import javax.persistence.EntityManager;

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
            System.out.println("Error en eliminar  detalleCompraSri" + e.getMessage());
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

}
