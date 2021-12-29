/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

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
public class ServicioCantVentProducto {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<CantVentProductos> findByMes(Date inicio, Date fin) {

        List<CantVentProductos> listaCantVentProductoss = new ArrayList<CantVentProductos>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.vistas.CantVentProductos( MAX(a.idDetalle), CAST(SUM(a.cantidad) as NUMERIC),MAX(a.prodNombre), CAST(MAX(a.facFecha) as Date), CAST(SUM(a.compra) as NUMERIC), CAST(SUM(a.diferencia) as NUMERIC) )FROM CantVentProductos a where a.facFecha BETWEEN :inicio and :fin  GROUP BY a.prodNombre" );
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaCantVentProductoss = (List<CantVentProductos>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta CantVentProductos " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCantVentProductoss;
    }

}
