/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import com.ec.entidad.NumeroDocumentosEmitidos;
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
public class ServicioNumeroDocumentosEmitidos {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public NumeroDocumentosEmitidos findByMes(Integer mes) {

        NumeroDocumentosEmitidos dato = null;
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.entidad.NumeroDocumentosEmitidos(max(a.id),max(a.mes),sum(a.numero)) FROM NumeroDocumentosEmitidos a WHERE a.mes=:mes ");
            query.setParameter("mes", mes);
            List<NumeroDocumentosEmitidos> listado = (List<NumeroDocumentosEmitidos>) query.getResultList();
           System.out.println("listado "+listado.size());
            if (!listado.isEmpty()) {
                dato = listado.get(0);
            } else {
                dato = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta CantVentProductos " + e.getMessage());
        } finally {
            em.close();
        }

        return dato;
    }

}
