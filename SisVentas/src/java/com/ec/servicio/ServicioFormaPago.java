/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.FormaPago;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioFormaPago {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(FormaPago formaPago) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(formaPago);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar formaPago");
        } finally {
            em.close();
        }

    }

    public void eliminar(FormaPago formaPago) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(formaPago));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  formaPago" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(FormaPago formaPago) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(formaPago);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar formaPago");
        } finally {
            em.close();
        }

    }

    public List<FormaPago> FindALlFormaPago() {

        List<FormaPago> listaFormaPagos = new ArrayList<FormaPago>();
        FormaPago formaPago = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("FormaPago.findAll", FormaPago.class);
//           query.setParameter("codigoUsuario", formaPago);
            listaFormaPagos = (List<FormaPago>) query.getResultList();
            if (listaFormaPagos.size() > 0) {
                formaPago = listaFormaPagos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta formaPago");
        } finally {
            em.close();
        }

        return listaFormaPagos;
    }

    public FormaPago finPrincipal() {

        List<FormaPago> listaFormaPagos = new ArrayList<FormaPago>();
        FormaPago formaPago = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("FormaPago.finPrincipal", FormaPago.class);
//           query.setParameter("codigoUsuario", formaPago);
            listaFormaPagos = (List<FormaPago>) query.getResultList();
            if (listaFormaPagos.size() > 0) {
                formaPago = listaFormaPagos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta formaPago");
        } finally {
            em.close();
        }

        return formaPago;
    }
}
