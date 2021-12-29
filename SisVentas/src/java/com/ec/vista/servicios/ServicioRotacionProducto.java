/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import com.ec.servicio.HelperPersistencia;
import com.ec.vistas.RotacionProducto;
import com.ec.vistas.RotacionProducto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioRotacionProducto {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<RotacionProducto> findBetweenGroupByProducto(Date inicio, Date fin) {

        List<RotacionProducto> listaRotacionProductos = new ArrayList<RotacionProducto>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.vistas.RotacionProducto(max(a.prodNombre),SUM(a.cantidadVenta),SUM(a.valorVentaProducto)) FROM RotacionProducto a WHERE a.facFecha BETWEEN :inicio and :fin  GROUP BY a.idProducto" );
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaRotacionProductos = (List<RotacionProducto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta RotacionProducto " + e.getMessage());
        } finally {
            em.close();
        }

        return listaRotacionProductos;
    }

}
