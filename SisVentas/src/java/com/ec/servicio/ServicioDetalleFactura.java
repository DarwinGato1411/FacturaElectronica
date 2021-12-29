/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.DetalleFactura;
import com.ec.entidad.Factura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetalleFactura {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleFactura detalleFactura) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(detalleFactura);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar detallefactura "+e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(DetalleFactura detallefactura) {

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

    public void modificar(DetalleFactura detallefactura) {

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

    public List<DetalleFactura> findDetalleForIdFactuta(Factura fac) {

        List<DetalleFactura> listadetallefacturas = new ArrayList<DetalleFactura>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleFactura.finForIdFact", DetalleFactura.class);
            query.setParameter("idFactura", fac.getIdFactura());
            listadetallefacturas = (List<DetalleFactura>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detallefactura");
        } finally {
            em.close();
        }

        return listadetallefacturas;
    }
    public List<DetalleFactura> findDetalleForIdFac(Integer id) {

        List<DetalleFactura> listadetallefacturas = new ArrayList<DetalleFactura>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createQuery("SELECT d FROM DetalleFactura d where d.idFactura.idFactura =:idFactura");
            query.setParameter("idFactura", id);
            listadetallefacturas = (List<DetalleFactura>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detallefactura "+e.getMessage());
        } finally {
            em.close();
        }

        return listadetallefacturas;
    }

    //busca por id de factura
    public List<DetalleFactura> FindALldetallefactura() {

        List<DetalleFactura> listadetallefacturas = new ArrayList<DetalleFactura>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("detallefactura.findAll", DetalleFactura.class);
//           query.setParameter("codigoUsuario", detallefactura);
            listadetallefacturas = (List<DetalleFactura>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta detallefactura");
        } finally {
            em.close();
        }

        return listadetallefacturas;
    }
}
