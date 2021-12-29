/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.DetalleNotaDebitoCredito;
import com.ec.entidad.NotaCreditoDebito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalleNotaCredito {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleNotaDebitoCredito detalleNotaDebitoCredito) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(detalleNotaDebitoCredito);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detallefactura "+e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(DetalleNotaDebitoCredito detallefactura) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(detallefactura));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  detallefactura" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(DetalleNotaDebitoCredito detallefactura) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(detallefactura);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detallefactura");
        } finally {
            em.close();
        }

    }

    public List<DetalleNotaDebitoCredito> findDetalleForIdFactuta(NotaCreditoDebito fac) {

        List<DetalleNotaDebitoCredito> listadetallefacturas = new ArrayList<DetalleNotaDebitoCredito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleNotaDebitoCredito.findByIdNota", DetalleNotaDebitoCredito.class);
            query.setParameter("idNota", fac.getIdNota());
            listadetallefacturas = (List<DetalleNotaDebitoCredito>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detallefactura");
        } finally {
            em.close();
        }

        return listadetallefacturas;
    }
    public List<DetalleNotaDebitoCredito> findDetalleForIdFac(Integer id) {

        List<DetalleNotaDebitoCredito> listadetallefacturas = new ArrayList<DetalleNotaDebitoCredito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleNotaDebitoCredito.finForIdFact", DetalleNotaDebitoCredito.class);
            query.setParameter("idFactura", id);
            listadetallefacturas = (List<DetalleNotaDebitoCredito>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detallefactura");
        } finally {
            em.close();
        }

        return listadetallefacturas;
    }

    //busca por id de factura
    public List<DetalleNotaDebitoCredito> FindALldetallefactura() {

        List<DetalleNotaDebitoCredito> listadetallefacturas = new ArrayList<DetalleNotaDebitoCredito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("detallefactura.findAll", DetalleNotaDebitoCredito.class);
//           query.setParameter("codigoUsuario", detallefactura);
            listadetallefacturas = (List<DetalleNotaDebitoCredito>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detallefactura");
        } finally {
            em.close();
        }

        return listadetallefacturas;
    }
}
