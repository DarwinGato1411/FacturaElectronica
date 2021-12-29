/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Referencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioReferencia {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Referencia referencia) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(referencia);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar referencia "+e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Referencia referencia) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(referencia));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  referencia "+e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(Referencia referencia) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(referencia);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en modificar referencia "+e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<Referencia> findAll() {

        List<Referencia> listaReferencias = new ArrayList<Referencia>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Referencia a ORDER BY a.refNombre ASC");
//           query.setParameter("codigoUsuario", referencia);
            listaReferencias = (List<Referencia>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta referencia "+e.getMessage());
        } finally {
            em.close();
        }

        return listaReferencias;
    }


}
