/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Acumuladoventas;
import com.ec.entidad.Acumuladoventas;
import com.ec.vistas.Acumuladoaniomes;
import com.ec.vistas.Acumuladopordia;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioAcumuladoVentas {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Acumuladoventas acumuladoventas) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(acumuladoventas);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar acumuladoventas");
        } finally {
            em.close();
        }

    }

    public void eliminar(Acumuladoventas acumuladoventas) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(acumuladoventas));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  acumuladoventas" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Acumuladoventas acumuladoventas) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(acumuladoventas);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar acumuladoventas");
        } finally {
            em.close();
        }

    }

    public List<Acumuladoventas> findAcumuladoventas(Date inicio, Date fin) {

        List<Acumuladoventas> listaAcumuladoventass = new ArrayList<Acumuladoventas>();        
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Acumuladoventas a WHERE a.facFecha BETWEEN :inicio AND :fin");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaAcumuladoventass = (List<Acumuladoventas>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta acumuladoventas " + e.getMessage());
        } finally {
            em.close();
        }

        return listaAcumuladoventass;
    }
    
      public List<Acumuladoaniomes> findAcumuladoventasAnioMes(Date inicio, Date fin) {

        List<Acumuladoaniomes> listaAcumuladoventass = new ArrayList<Acumuladoaniomes>();
        try {
            //Connection connection = em.unwrap(Connection.class);

            SimpleDateFormat sm = new SimpleDateFormat("yyyy");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String anio = sm.format(inicio);

            SimpleDateFormat smInicio = new SimpleDateFormat("MM");
            //SimpleDateFormat smFin = new SimpleDateFormat("MM");
            String mesIni = smInicio.format(inicio);
            String mesfin = smInicio.format(fin);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Acumuladoaniomes a WHERE a.anio >=:anio and a.mes BETWEEN :inicio and :fin");
            query.setParameter("anio", Double.valueOf(anio));
            query.setParameter("inicio", Double.valueOf(mesIni));
            query.setParameter("fin", Double.valueOf(mesfin));
            listaAcumuladoventass = (List<Acumuladoaniomes>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta findAcumuladoventasAnioMes " + e.getMessage());
        } finally {
            em.close();
        }

        return listaAcumuladoventass;
    }
      
       public List<Acumuladopordia> findAcumuladoventasdiaria(Date inicio, Date fin) {

        List<Acumuladopordia> listaAcumuladoventass = new ArrayList<Acumuladopordia>();
        try {
            //Connection connection = em.unwrap(Connection.class);

          
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Acumuladopordia a WHERE a.facFecha BETWEEN :inicio and :fin");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaAcumuladoventass = (List<Acumuladopordia>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta findAcumuladoventasdiaria " + e.getMessage());
        } finally {
            em.close();
        }

        return listaAcumuladoventass;
    }
}
