/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.TipoIdentificacionCompra;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTipoIdentificacionCompra {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(TipoIdentificacionCompra tipoIdentificacionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(tipoIdentificacionCompra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoIdentificacionCompra");
        } finally {
            em.close();
        }

    }

    public void eliminar(TipoIdentificacionCompra tipoIdentificacionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(tipoIdentificacionCompra));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  tipoIdentificacionCompra" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(TipoIdentificacionCompra tipoIdentificacionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(tipoIdentificacionCompra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoIdentificacionCompra");
        } finally {
            em.close();
        }

    }

    public List<TipoIdentificacionCompra> findALlTipoIdentificacionCompra() {

        List<TipoIdentificacionCompra> listaTipoIdentificacionCompras = new ArrayList<TipoIdentificacionCompra>();
        TipoIdentificacionCompra tipoIdentificacionCompra = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("TipoIdentificacionCompra.findAll", TipoIdentificacionCompra.class);
//           query.setParameter("codigoUsuario", tipoIdentificacionCompra);
            listaTipoIdentificacionCompras = (List<TipoIdentificacionCompra>) query.getResultList();
//            if (listaTipoIdentificacionCompras.size() > 0) {
//                tipoIdentificacionCompra = listaTipoIdentificacionCompras.get(0);
//            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoIdentificacionCompra");
        } finally {
            em.close();
        }

        return listaTipoIdentificacionCompras;
    }

    public TipoIdentificacionCompra findByCedulaRuc(String ticCodigo) {

        List<TipoIdentificacionCompra> listaTipoIdentificacionCompras = new ArrayList<TipoIdentificacionCompra>();
        TipoIdentificacionCompra tipoIdentificacionCompra = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT  a FROM TipoIdentificacionCompra a where a.ticCodigo=:ticCodigo");
            query.setParameter("ticCodigo", ticCodigo);
            listaTipoIdentificacionCompras = (List<TipoIdentificacionCompra>) query.getResultList();
            if (listaTipoIdentificacionCompras.size() > 0) {
                tipoIdentificacionCompra = listaTipoIdentificacionCompras.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoIdentificacionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return tipoIdentificacionCompra;
    }

}
