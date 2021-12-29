/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.MailMasivo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioMailMasivo {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(MailMasivo mailMasivo) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(mailMasivo);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar mailMasivo " + e);
        } finally {
            em.close();
        }

    }

    public void eliminar(MailMasivo mailMasivo) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(mailMasivo));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  mailMasivo " + e);
        } finally {
            em.close();
        }

    }

    public void modificar(MailMasivo mailMasivo) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(mailMasivo);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar mailMasivo " + e);
        } finally {
            em.close();
        }

    }

    public List<MailMasivo> FindAll(String cliente) {

        List<MailMasivo> listaPersonas = new ArrayList<MailMasivo>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("MailMasivo.findAll", MailMasivo.class);
//            query.setParameter("clienet", cliente);
            listaPersonas = (List<MailMasivo>) query.getResultList();
            System.out.println("numero de elementos " + listaPersonas.size());
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta mailMasivo " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }

    public List<MailMasivo> FindAllLike(String email) {

        List<MailMasivo> listaPersonas = new ArrayList<MailMasivo>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("MailMasivo.findAllLike", MailMasivo.class);
            query.setParameter("email", "%" + email + "%");
            listaPersonas = (List<MailMasivo>) query.getResultList();
            System.out.println("numero de elementos " + listaPersonas.size());
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta mailMasivo " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }
    public List<MailMasivo> FindAllCategoria(String categoria) {

        List<MailMasivo> listaPersonas = new ArrayList<MailMasivo>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("MailMasivo.findByEmailCategoria", MailMasivo.class);
            query.setParameter("categoria", categoria);
            listaPersonas = (List<MailMasivo>) query.getResultList();
            System.out.println("numero de elementos " + listaPersonas.size());
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta mailMasivo " + e);
        } finally {
            em.close();
        }

        return listaPersonas;
    }
}
