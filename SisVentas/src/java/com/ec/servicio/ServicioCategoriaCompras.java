/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.sri.CategoriaCompras;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author gato
 */
public class ServicioCategoriaCompras {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(CategoriaCompras categoriaCompras) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(categoriaCompras);
            em.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            System.out.println("Error en insertar categoriaCompras " + e.getMessage());
            for (ConstraintViolation actual : e.getConstraintViolations()) {
            System.out.println(actual.toString());
        }
        } finally {
            em.close();
        }

    }

    public void eliminar(CategoriaCompras categoriaCompras) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(categoriaCompras));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  categoriaCompras " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(CategoriaCompras categoriaCompras) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(categoriaCompras);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar categoriaCompras " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<CategoriaCompras> findAll() {

        List<CategoriaCompras> listaCategoriaComprass = new ArrayList<CategoriaCompras>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM CategoriaCompras a");
            listaCategoriaComprass = (List<CategoriaCompras>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta categoriaCompras " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCategoriaComprass;
    }

    public CategoriaCompras findByAutorizacion(String csriAutorizacion) {

        CategoriaCompras retorno = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM CategoriaCompras a WHERE a.csriAutorizacion=:csriAutorizacion");
           query.setParameter("csriAutorizacion", csriAutorizacion);
          List<CategoriaCompras>  datos = (List<CategoriaCompras>) query.getResultList();
            if (datos.size()>0) {
                retorno=datos.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta categoriaCompras " + e.getMessage());
            return null;
        } finally {
            em.close();
        }

        return retorno;
    }

    public List<CategoriaCompras> findNoVerificados() {

        List<CategoriaCompras> listaCategoriaComprass = new ArrayList<CategoriaCompras>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM CategoriaCompras a WHERE a.csriVerificado='N'");
            listaCategoriaComprass = (List<CategoriaCompras>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta categoriaCompras " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCategoriaComprass;
    }
    
    /*documentos no procesados por rango de fechas*/
    public List<CategoriaCompras> findNoVerificadosBetweenFecha(Date inicio,Date fin) {

        List<CategoriaCompras> listaCategoriaComprass = new ArrayList<CategoriaCompras>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM CategoriaCompras a WHERE a.csriVerificado='N' AND a.csriFechaEmision BETWEEN :inicio and :fin");
            query.setParameter("inicio",inicio);
            query.setParameter("fin", fin);
            listaCategoriaComprass = (List<CategoriaCompras>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta categoriaCompras " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCategoriaComprass;
    }
    
    
    
}
