/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.AmortizacionCompra;
import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.Factura;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioDetallePagoCompra {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(AmortizacionCompra amortizacionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(amortizacionCompra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar amortizacionCompra " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(AmortizacionCompra amortizacionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(amortizacionCompra));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  amortizacionCompra " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(AmortizacionCompra amortizacionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(amortizacionCompra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar amortizacionCompra " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<AmortizacionCompra> finForIdFacturaCompra(CabeceraCompra factura) {

        List<AmortizacionCompra> listaDatos = new ArrayList<AmortizacionCompra>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM AmortizacionCompra a WHERE a.idCompra=:idCompra");
            query.setParameter("idCompra", factura);
            listaDatos = (List<AmortizacionCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta amortizacionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }
}
