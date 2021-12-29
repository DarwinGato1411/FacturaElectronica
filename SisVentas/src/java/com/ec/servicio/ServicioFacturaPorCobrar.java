/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.VistaFacturasPorCobrar;
import com.ec.entidad.VistaVentaDiaria;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioFacturaPorCobrar {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<VistaFacturasPorCobrar> findPorCobrar(String nombre, Boolean group) {

        List<VistaFacturasPorCobrar> lista = new ArrayList<VistaFacturasPorCobrar>();
        try {
            String SQL = "SELECT a FROM VistaFacturasPorCobrar a  ";
            String WHERE = " WHERE a.cliNombres LIKE :nomnre";
            String GROUPBY = " ";
            if (group) {
                SQL = "SELECT new com.ec.entidad.VistaFacturasPorCobrar( max(a.id),max(a.facNumeroText),max(a.cliCedula),max(a.cliNombres),max(a.fac_total),sum(a.facSaldoAmortizado),max(a.dias),max(a.facFecha)) FROM VistaFacturasPorCobrar a  ";
                GROUPBY = GROUPBY + "  GROUP BY a.cliCedula ";
            }
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery(SQL + WHERE + GROUPBY);
            query.setParameter("nomnre", "%" + nombre + "%");
            lista = (List<VistaFacturasPorCobrar>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ventaDiaria " + e.getMessage());
        } finally {
            em.close();
        }

        return lista;
    }

    public List<VistaFacturasPorCobrar> findPorCobrarDia(Integer idusuario) {

        List<VistaFacturasPorCobrar> lista = new ArrayList<VistaFacturasPorCobrar>();
        try {
            String SQL = "";
            String WHERE = " WHERE a.facFecha=:facFecha AND a.idUsuario=:idUsuario";
            String GROUPBY = " ";

            SQL = "SELECT new com.ec.entidad.VistaFacturasPorCobrar( max(a.id),max(a.facNumeroText),max(a.cliCedula),max(a.cliNombres),sum(a.fac_total),sum(a.facSaldoAmortizado),max(a.dias),max(a.facFecha)) FROM VistaFacturasPorCobrar a  ";
            GROUPBY = GROUPBY + "  GROUP BY a.facFecha ";

            em = HelperPersistencia.getEMF();

            em.getTransaction().begin();
            Query query = em.createQuery(SQL + WHERE + GROUPBY);
            query.setParameter("facFecha", new Date());
            query.setParameter("idUsuario", idusuario);
            lista = (List<VistaFacturasPorCobrar>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ventaDiaria " + e.getMessage());
        } finally {
            em.close();
        }

        return lista;
    }

}
