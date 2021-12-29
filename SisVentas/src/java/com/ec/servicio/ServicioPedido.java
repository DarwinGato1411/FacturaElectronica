/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Pedidos;
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
public class ServicioPedido {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Pedidos pedidos) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(pedidos);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar     pedidos");
        } finally {
            em.close();
        }

    }
 

    public void eliminar(Pedidos pedidos) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(pedidos));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar      pedidos" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Pedidos pedidos) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(pedidos);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar     pedidos");
        } finally {
            em.close();
        }

    }

    public List<Pedidos> FindALlPedidos() {

        List<Pedidos> listaPedidoss = new ArrayList<Pedidos>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Pedidos.findAll", Pedidos.class);
//           query.setParameter("codigoUsuario",     pedidos);
            listaPedidoss = (List<Pedidos>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta     pedidos");
        } finally {
            em.close();
        }

        return listaPedidoss;
    }

    public List<Pedidos> findAllPendientes() {

        List<Pedidos> listaPedidoss = new ArrayList<Pedidos>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Pedidos.findAllPendientes", Pedidos.class);
//           query.setParameter("codigoUsuario",     pedidos);
            listaPedidoss = (List<Pedidos>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta     pedidos");
        } finally {
            em.close();
        }

        return listaPedidoss;
    }

    public List<Pedidos> findAllComprado() {

        List<Pedidos> listaPedidoss = new ArrayList<Pedidos>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Pedidos.findAllComprado", Pedidos.class);
//           query.setParameter("codigoUsuario",     pedidos);
            listaPedidoss = (List<Pedidos>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta     pedidos");
        } finally {
            em.close();
        }

        return listaPedidoss;
    }

    public List<Pedidos> findBetweenPedFecha(Date inicio, Date fin) {

        List<Pedidos> listaPedidoss = new ArrayList<Pedidos>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Pedidos.findBetweenPedFecha", Pedidos.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaPedidoss = (List<Pedidos>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta     pedidos");
        } finally {
            em.close();
        }

        return listaPedidoss;
    }
}
