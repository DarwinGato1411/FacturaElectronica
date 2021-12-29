/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Tipocomprobante;
import com.ec.entidad.Tipocomprobante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTipoComprobante {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Tipocomprobante tipocomprobante) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(tipocomprobante);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipocomprobante");
        } finally {
            em.close();
        }

    }

    public void eliminar(Tipocomprobante tipocomprobante) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(tipocomprobante));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  tipocomprobante" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Tipocomprobante tipocomprobante) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(tipocomprobante);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipocomprobante");
        } finally {
            em.close();
        }

    }

    public Tipocomprobante FindALlTipocomprobante() {

        List<Tipocomprobante> listaTipocomprobantes = new ArrayList<Tipocomprobante>();
        Tipocomprobante tipocomprobante = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Tipocomprobante.findAll", Tipocomprobante.class);
//           query.setParameter("codigoUsuario", tipocomprobante);
            listaTipocomprobantes = (List<Tipocomprobante>) query.getResultList();
            if (listaTipocomprobantes.size() > 0) {
                tipocomprobante = listaTipocomprobantes.get(0);
            }
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipocomprobante");
        } finally {
            em.close();
        }

        return tipocomprobante;
    }
}
