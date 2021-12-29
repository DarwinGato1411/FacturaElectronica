/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Retencionporcasillero;
import com.ec.entidad.VistaIngresoEgreso;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioRetencionPorCasillero {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<Retencionporcasillero> finPorFechas(Date inicio, Date fin) {

        List<Retencionporcasillero> listaResultado = new ArrayList<Retencionporcasillero>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT NEW com.ec.entidad.Retencionporcasillero( max(a.id) ,a.tireCodigo , max(a.tipoRetencion),SUM(a.numeroComprobante),sum(a.valorRetenido),max(a.cabFechaEmision)) FROM Retencionporcasillero a where a.cabFechaEmision BETWEEN :inicio AND :fin GROUP BY a.tireCodigo");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaResultado = (List<Retencionporcasillero>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta Retencionporcasillero " + e.getMessage());
        } finally {
            em.close();
        }

        return listaResultado;
    }

}
