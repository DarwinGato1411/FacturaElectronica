/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.VistaVentaDiaria;
import com.ec.entidad.VistaVentaDiaria;
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
public class ServicioVistaVentaDiaria {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(VistaVentaDiaria ventaDiaria) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(ventaDiaria);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ventaDiaria");
        } finally {
            em.close();
        }

    }

    public void eliminar(VistaVentaDiaria ventaDiaria) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(ventaDiaria));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  ventaDiaria" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(VistaVentaDiaria ventaDiaria) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(ventaDiaria);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar ventaDiaria");
        } finally {
            em.close();
        }

    }

    public List<VistaVentaDiaria> FindALlVistaVentaDiaria() {

        List<VistaVentaDiaria> listaVistaVentaDiarias = new ArrayList<VistaVentaDiaria>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("VistaVentaDiaria.findAll", VistaVentaDiaria.class);
            listaVistaVentaDiarias = (List<VistaVentaDiaria>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ventaDiaria");
        } finally {
            em.close();
        }

        return listaVistaVentaDiarias;
    }

    public VistaVentaDiaria FindALlVistaVentaDiariaForFecha(Date fecha) {
        VistaVentaDiaria ventaDiaria = null;
        List<VistaVentaDiaria> listaVistaVentaDiarias = new ArrayList<VistaVentaDiaria>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("VistaVentaDiaria.findByFecha", VistaVentaDiaria.class);
            query.setParameter("fecha", fecha);
            listaVistaVentaDiarias = (List<VistaVentaDiaria>) query.getResultList();
            for (VistaVentaDiaria item : listaVistaVentaDiarias) {
                System.out.println("valor vista venta diaria " + item.getFecha());
            }
            if (!listaVistaVentaDiarias.isEmpty()) {
                ventaDiaria = listaVistaVentaDiarias.get(0);
            } else {
                ventaDiaria = null;
            }
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ventaDiaria " + e.getMessage());
        } finally {
            em.close();
        }

        return ventaDiaria;
    }

    public List<VistaVentaDiaria> FindALlListaVistaVentaDiariaForFecha(Date fecha) {
        VistaVentaDiaria ventaDiaria = null;
        List<VistaVentaDiaria> listaVistaVentaDiarias = new ArrayList<VistaVentaDiaria>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("VistaVentaDiaria.findByFecha", VistaVentaDiaria.class);
            query.setParameter("fecha", fecha);
            listaVistaVentaDiarias = (List<VistaVentaDiaria>) query.getResultList();

          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ventaDiaria " + e);
        } finally {
            em.close();
        }

        return listaVistaVentaDiarias;
    }
    
    //entre fechas para la grafica
        public List<VistaVentaDiaria> findBetweenFecha(Date inicio, Date fin) {

        List<VistaVentaDiaria> listaVistaVentaDiarias = new ArrayList<VistaVentaDiaria>();
        try {
            System.out.println("inicio "+inicio);
            System.out.println("fin nnnnnnnn "+fin);
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("VistaVentaDiaria.findBetweenFecha", VistaVentaDiaria.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            System.out.println("SQLLL "+query);
            listaVistaVentaDiarias = (List<VistaVentaDiaria>) query.getResultList();
            System.out.println("LEMENTOS "+listaVistaVentaDiarias.size());
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta ventaDiaria");
        } finally {
            em.close();
        }

        return listaVistaVentaDiarias;
    }

}
