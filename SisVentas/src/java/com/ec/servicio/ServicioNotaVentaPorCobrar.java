/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.VistaNotaVentaPorCobrar;
import com.ec.entidad.VistaNotaVentaPorCobrar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioNotaVentaPorCobrar {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<VistaNotaVentaPorCobrar> findPorCobrar(String nombre, Boolean group) {

        List<VistaNotaVentaPorCobrar> lista = new ArrayList<VistaNotaVentaPorCobrar>();
        try {
            String SQL = "SELECT a FROM VistaNotaVentaPorCobrar a  ";
            String WHERE = " WHERE a.cliNombres LIKE :nomnre";
            String GROUPBY = " ";
            if (group) {
                SQL = "SELECT new com.ec.entidad.VistaNotaVentaPorCobrar( max(a.id),max(a.facNumeroText),max(a.cliCedula),max(a.cliNombres),max(a.fac_total),sum(a.facSaldoAmortizado),max(a.dias),max(a.facFecha)) FROM VistaNotaVentaPorCobrar a  ";
                GROUPBY = GROUPBY + "  GROUP BY a.cliCedula ";
            }
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery(SQL + WHERE + GROUPBY);
            query.setParameter("nomnre", "%" + nombre + "%");
            lista = (List<VistaNotaVentaPorCobrar>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ventaDiaria " + e.getMessage());
        } finally {
            em.close();
        }

        return lista;
    }

    public List<VistaNotaVentaPorCobrar> findPorCobrarDia(Integer idusuario, Date fecha) {

        List<VistaNotaVentaPorCobrar> lista = new ArrayList<VistaNotaVentaPorCobrar>();
        try {
            String SQL = "";
            String WHERE = " WHERE a.facFecha=:facFecha AND a.idUsuario=:idUsuario";
            String GROUPBY = " ";

            SQL = "SELECT new com.ec.entidad.VistaNotaVentaPorCobrar( max(a.id),max(a.facNumeroText),max(a.cliCedula),max(a.cliNombres),sum(a.fac_total),sum(a.facSaldoAmortizado),max(a.dias),max(a.facFecha)) FROM VistaNotaVentaPorCobrar a  ";
            GROUPBY = GROUPBY + "  GROUP BY a.facFecha ";

            em = HelperPersistencia.getEMF();

            System.out.println("FECHA CREDITO " + new Date());
            em.getTransaction().begin();
            Query query = em.createQuery(SQL + WHERE + GROUPBY);
            query.setParameter("facFecha", fecha);
            query.setParameter("idUsuario", idusuario);
            lista = (List<VistaNotaVentaPorCobrar>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ventaDiaria " + e.getMessage());
        } finally {
            em.close();
        }

        return lista;
    }

}
