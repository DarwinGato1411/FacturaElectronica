/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import com.ec.entidad.Usuario;
import com.ec.servicio.HelperPersistencia;
import com.ec.untilitario.ModeloAcumuladoDiaUsuario;
import com.ec.vistas.AcumuladopordiaUsuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioAcumuladoDiarioUsuario {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<ModeloAcumuladoDiaUsuario> findCierrePorUsuario(Date fecha, Usuario idUsuario) {

        List<ModeloAcumuladoDiaUsuario> listaAcumuladopordiaUsuarios = new ArrayList<ModeloAcumuladoDiaUsuario>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            String SQL = "SELECT new com.ec.untilitario.ModeloAcumuladoDiaUsuario(a.usuLogin,a.facTotal,a.totalntv,a.totalacumulado) FROM AcumuladopordiaUsuario a WHERE a.facFecha =:facFecha ";
            String WHERE = " AND a.idUsuario=:idUsuario";
            Query query = null;
            if (idUsuario.getIdUsuario() == 1) {
                query = em.createQuery(SQL);
                query.setParameter("facFecha", fecha);
            } else {
                query = em.createQuery(SQL + WHERE);
                query.setParameter("facFecha", fecha);
                query.setParameter("idUsuario", idUsuario.getIdUsuario());
            }

            listaAcumuladopordiaUsuarios = (List<ModeloAcumuladoDiaUsuario>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta findCierrePorUsuario " + e.getMessage());
        } finally {
            em.close();
        }

        return listaAcumuladopordiaUsuarios;
    }

}
