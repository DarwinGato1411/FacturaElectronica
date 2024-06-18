/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import com.ec.entidad.InversionEmpresa;
import com.ec.servicio.HelperPersistencia;
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
public class ServicioInversionEmpresa {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<InversionEmpresa> findBetweenFechaKardexGroupByProducto(Date inicio, Date fin) {

        List<InversionEmpresa> listaDatos = new ArrayList<InversionEmpresa>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.entidad.InversionEmpresa(max(a.idDetalleKardex),a.prodNombre,max(a.detkFechakardex),SUM(a.detkCantidad),max(a.pordCostoVentaRef),sum(a.compra)) FROM InversionEmpresa a WHERE a.detkFechakardex BETWEEN :inicio and :fin  GROUP BY a.prodNombre" );
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaDatos = (List<InversionEmpresa>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta RotacionProducto " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }

}
