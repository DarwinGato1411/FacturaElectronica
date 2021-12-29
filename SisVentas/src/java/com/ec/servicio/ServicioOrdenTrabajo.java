/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.OrdenTrabajo;
import com.ec.untilitario.PedidoDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioOrdenTrabajo {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(OrdenTrabajo ordenTrabajo) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(ordenTrabajo);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ordenTrabajo " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(OrdenTrabajo ordenTrabajo) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(ordenTrabajo));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar ordenTrabajo " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(OrdenTrabajo ordenTrabajo) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(ordenTrabajo);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ordenTrabajo " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<OrdenTrabajo> findOrdenTrabajoNumNombreApellido(String buscar) {

        List<OrdenTrabajo> listaOrdenTrabajos = new ArrayList<OrdenTrabajo>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM OrdenTrabajo a WHERE a.ordNumText like :buscar OR a.idCliente.cliNombres like :buscar OR a.idCliente.cliApellidos like :buscar ORDER BY a.ordFecha DESC");
            query.setParameter("buscar", "%" + buscar + "%");
            query.setMaxResults(300);
            listaOrdenTrabajos = (List<OrdenTrabajo>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta findOrdenTrabajoNumNombreApellido " + e.getMessage());
        } finally {
            em.close();
        }

        return listaOrdenTrabajos;
    }

    public OrdenTrabajo findByIdOrden(Integer idOrden) {

        List<OrdenTrabajo> listaOrdenTrabajos = new ArrayList<OrdenTrabajo>();
        OrdenTrabajo ordenTrabajo = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM OrdenTrabajo a WHERE a.idOrdenTrabajo =:idOrden");
            query.setParameter("idOrden", idOrden);
            query.setMaxResults(300);
            listaOrdenTrabajos = (List<OrdenTrabajo>) query.getResultList();
            if (listaOrdenTrabajos.size() > 0) {
                ordenTrabajo = listaOrdenTrabajos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta findOrdenTrabajoNumNombreApellido " + e.getMessage());
        } finally {
            em.close();
        }

        return ordenTrabajo;
    }
    
    public OrdenTrabajo findUltimaOrden() {

        List<OrdenTrabajo> listaOrdenTrabajos = new ArrayList<OrdenTrabajo>();
        OrdenTrabajo ordenTrabajo = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM OrdenTrabajo a ORDER BY a.idOrdenTrabajo DESC");
//            query.setParameter("idOrden", idOrden);
            query.setMaxResults(300);
            listaOrdenTrabajos = (List<OrdenTrabajo>) query.getResultList();
            if (listaOrdenTrabajos.size() > 0) {
                ordenTrabajo = listaOrdenTrabajos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta findOrdenTrabajoNumNombreApellido " + e.getMessage());
        } finally {
            em.close();
        }

        return ordenTrabajo;
    }

}
