/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import com.ec.entidad.ReporteCompraVentaFacturado;
import com.ec.servicio.HelperPersistencia;
import com.ec.vistas.CantVentProductos;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCompraVentaFacturado {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<ReporteCompraVentaFacturado> findByFecha(Date inicio, Date fin) {

        List<ReporteCompraVentaFacturado> listaDatos = new ArrayList<ReporteCompraVentaFacturado>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.entidad.ReporteCompraVentaFacturado(SUM(a.detCantidad),a.prodNombre,MAX(a.precioCompra),MAX(a.precioVenta),max(a.facFecha),SUM(a.totalCompra),SUM(a.totalVenta)) FROM ReporteCompraVentaFacturado a WHERE a.facFecha BETWEEN :inicio and :fin GROUP BY a.prodNombre" );
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaDatos = (List<ReporteCompraVentaFacturado>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta CantVentProductos " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }

        return listaDatos;
    }
    
    public List<ReporteCompraVentaFacturado> findByFechaDetallado(Date inicio, Date fin) {

        List<ReporteCompraVentaFacturado> listaDatos = new ArrayList<ReporteCompraVentaFacturado>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ReporteCompraVentaFacturado a WHERE a.facFecha BETWEEN :inicio and :fin" );
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaDatos = (List<ReporteCompraVentaFacturado>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta CantVentProductos " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }

        return listaDatos;
    }

}
