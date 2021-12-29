/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.DetalleGuiaremision;
import com.ec.entidad.Guiaremision;
import com.ec.entidad.NotaCreditoDebito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalleGuia {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleGuiaremision detalleNotaDebitoCredito) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(detalleNotaDebitoCredito);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar DetalleGuiaremision "+e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(DetalleGuiaremision DetalleGuiaremision) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(DetalleGuiaremision));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  DetalleGuiaremision" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(DetalleGuiaremision DetalleGuiaremision) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(DetalleGuiaremision);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar DetalleGuiaremision");
        } finally {
            em.close();
        }

    }

    public List<DetalleGuiaremision> findDetalleForIdGuia(Guiaremision fac) {

        List<DetalleGuiaremision> listaDetalleGuiaremisions = new ArrayList<DetalleGuiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createQuery("SELECT b FROM DetalleGuiaremision b WHERE b.idGuiaremision=:idGuiaremision");
            query.setParameter("idGuiaremision", fac);
            listaDetalleGuiaremisions = (List<DetalleGuiaremision>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta DetalleGuiaremision");
        } finally {
            em.close();
        }

        return listaDetalleGuiaremisions;
    }
    public List<DetalleGuiaremision> findDetalleForIdFac(Integer id) {

        List<DetalleGuiaremision> listaDetalleGuiaremisions = new ArrayList<DetalleGuiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleGuiaremision.finForIdFact", DetalleGuiaremision.class);
            query.setParameter("idFactura", id);
            listaDetalleGuiaremisions = (List<DetalleGuiaremision>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta DetalleGuiaremision");
        } finally {
            em.close();
        }

        return listaDetalleGuiaremisions;
    }

    //busca por id de factura
    public List<DetalleGuiaremision> FindALlDetalleGuiaremision() {

        List<DetalleGuiaremision> listaDetalleGuiaremisions = new ArrayList<DetalleGuiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleGuiaremision.findAll", DetalleGuiaremision.class);
//           query.setParameter("codigoUsuario", DetalleGuiaremision);
            listaDetalleGuiaremisions = (List<DetalleGuiaremision>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta DetalleGuiaremision");
        } finally {
            em.close();
        }

        return listaDetalleGuiaremisions;
    }
}
