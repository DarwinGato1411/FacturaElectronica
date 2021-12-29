/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.TipoRetencion;

import com.ec.untilitario.DetalleCompraUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTipoRetencion {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(TipoRetencion tipoRetencion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(tipoRetencion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoRetencion");
        } finally {
            em.close();
        }

    }

    public void eliminar(TipoRetencion tipoRetencion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(tipoRetencion));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  tipoRetencion" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(TipoRetencion tipoRetencion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(tipoRetencion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoRetencion");
        } finally {
            em.close();
        }

    }

    public List<TipoRetencion> FindALlTipoRetencion() {

        List<TipoRetencion> listaTipoRetencions = new ArrayList<TipoRetencion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("TipoRetencion.findAll", TipoRetencion.class);
//           query.setParameter("codigoUsuario", tipoRetencion);
            listaTipoRetencions = (List<TipoRetencion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoRetencion");
        } finally {
            em.close();
        }

        return listaTipoRetencions;
    }

    public void guardar(List<DetalleCompraUtil> detalleCompra, TipoRetencion tipoRetencion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(tipoRetencion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar factura tipoRetencion " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<TipoRetencion> findAllTipo(String tipo) {

        List<TipoRetencion> listaTipoRetencions = new ArrayList<TipoRetencion>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM TipoRetencion a WHERE a.tireTipo=:tireTipo");
            query.setParameter("tireTipo", tipo);
            listaTipoRetencions = (List<TipoRetencion>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoRetencion " + e.getMessage());
        } finally {
            em.close();
        }

        return listaTipoRetencions;
    }

}
