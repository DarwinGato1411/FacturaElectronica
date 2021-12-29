/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Transportista;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTransportista {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Transportista transportista) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(transportista);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar transportista "+e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Transportista transportista) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(transportista));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  transportista " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(Transportista transportista) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(transportista);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar transportista "+e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<Transportista> findTransportista(String valor) {

        List<Transportista> listaTransportistas = new ArrayList<Transportista>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Transportista a WHERE a.trpRazonSocial LIKE :trpRazonSocial");
            query.setParameter("trpRazonSocial", "%"+valor+"%");
            listaTransportistas = (List<Transportista>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta transportista " + e.getMessage());
        } finally {
            em.close();
        }

        return listaTransportistas;
    }

}
