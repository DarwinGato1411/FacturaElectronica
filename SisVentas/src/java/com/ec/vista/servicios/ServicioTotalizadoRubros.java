
package com.ec.vista.servicios;

import com.ec.entidad.Tipoambiente;
import com.ec.entidad.TotalizadoRubros;
import com.ec.servicio.HelperPersistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTotalizadoRubros {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<TotalizadoRubros> findTotalizadoRubros(Date inicio, Date fin, Tipoambiente codTipoambiente) {
        System.out.println(inicio+" "+fin+" "+codTipoambiente.getCodTipoambiente());
        List<TotalizadoRubros> listaTotalizadoRubross = new ArrayList<TotalizadoRubros>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.entidad.TotalizadoRubros(a.clasificacion,sum(a.subtotal),sum(a.total) )FROM TotalizadoRubros a where a.cabFecha BETWEEN :inicio and :fin  and a.codTipoambiente=:codTipoambiente  GROUP BY a.clasificacion");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setParameter("codTipoambiente", codTipoambiente.getCodTipoambiente());
            listaTotalizadoRubross = (List<TotalizadoRubros>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta TotalizadoRubros " + e.getMessage());
        } finally {
            em.close();
        }

        return listaTotalizadoRubross;
    }

}
