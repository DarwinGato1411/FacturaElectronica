/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.DetalleCompra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalleCompra {

    
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleCompra detalleCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(detalleCompra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleCompra "+e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(DetalleCompra detalleCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(detalleCompra));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  detalleCompra" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(DetalleCompra detalleCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(detalleCompra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detalleCompra");
        } finally {
            em.close();
        }

    }

    public List<DetalleCompra> FindALlDetalleCompra() {

        List<DetalleCompra> listaDetalleCompras = new ArrayList<DetalleCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleCompra.findAll", DetalleCompra.class);
//           query.setParameter("codigoUsuario", detalleCompra);
            listaDetalleCompras = (List<DetalleCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleCompra");
        } finally {
            em.close();
        }

        return listaDetalleCompras;
    }

    
    public List<DetalleCompra> findCabProveedor(String valor) {

        List<DetalleCompra> listaDetalleCompras = new ArrayList<DetalleCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleCompra.findCabProveedor", DetalleCompra.class);
           query.setParameter("cabProveedor", "%"+valor+"%");
            listaDetalleCompras = (List<DetalleCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleCompra");
        } finally {
            em.close();
        }

        return listaDetalleCompras;
    }
    public List<DetalleCompra> findByBetweenFecha(Date incio, Date fin) {

        List<DetalleCompra> listaDetalleCompras = new ArrayList<DetalleCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleCompra.findByBetweenFecha", DetalleCompra.class);
           query.setParameter("inicio", incio);
           query.setParameter("fin", fin);
            listaDetalleCompras = (List<DetalleCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleCompra");
        } finally {
            em.close();
        }

        return listaDetalleCompras;
    }
    public List<DetalleCompra> findDetalleCompra(CabeceraCompra cabeceraCompra) {

        List<DetalleCompra> listaDetalleCompras = new ArrayList<DetalleCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM DetalleCompra a WHERE a.idCabecera=:idCabecera");
           query.setParameter("idCabecera", cabeceraCompra);
            listaDetalleCompras = (List<DetalleCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detalleCompra");
        } finally {
            em.close();
        }

        return listaDetalleCompras;
    }
    
    
    

}
