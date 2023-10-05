/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Subcategoria;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioSubCategoria {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Subcategoria subcategoria) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(subcategoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar subcategoria " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Subcategoria subcategoria) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(subcategoria));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  subcategoria" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Subcategoria subcategoria) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(subcategoria);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar subcategoria");
        } finally {
            em.close();
        }

    }

    public List<Subcategoria> findLikeDescipcion(String valor) {

        List<Subcategoria> listaSubcategorias = new ArrayList<Subcategoria>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Subcategoria a WHERE a.subCatDescripcion like :subCategoria ORDER BY a.subPrincipal, a.subCatDescripcion ASC");
            query.setParameter("subCategoria", "%" + valor + "%");
            listaSubcategorias = (List<Subcategoria>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta subcategoria");
        } finally {
            em.close();
        }

        return listaSubcategorias;
    }

    public List<Subcategoria> findLikeProdNombre(String buscar) {

        List<Subcategoria> listaSubcategorias = new ArrayList<Subcategoria>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Subcategoria.findLikeProdNombre", Subcategoria.class);
            query.setParameter("prodNombre", "%" + buscar + "%");
            listaSubcategorias = (List<Subcategoria>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta subcategoria");
        } finally {
            em.close();
        }

        return listaSubcategorias;
    }

    public Subcategoria findByProdCodigo(String buscar) {

        Subcategoria subcategoria = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Subcategoria.findByProdCodigo", Subcategoria.class);
            query.setParameter("prodCodigo", buscar);
            if (query.getResultList().size() > 0) {
                subcategoria = (Subcategoria) query.getSingleResult();
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta subcategoria");
        } finally {
            em.close();
        }

        return subcategoria;
    }

    public Subcategoria findById(Integer id) {

        Subcategoria subcategoria = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Subcategoria a where a.idSubCategoria=:idSubCategoria");
            query.setParameter("idSubCategoria", id);
            if (query.getResultList().size() > 0) {
                subcategoria = (Subcategoria) query.getSingleResult();
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta subcategoria");
        } finally {
            em.close();
        }

        return subcategoria;
    }

    public List<Subcategoria> findLikeProdCodigo(String buscar) {

        List<Subcategoria> listaSubcategoria = new ArrayList<Subcategoria>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Subcategoria.findLikeProdCodigo", Subcategoria.class);
            query.setParameter("prodCodigo", buscar);

            listaSubcategoria = (List<Subcategoria>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta subcategoria");
        } finally {
            em.close();
        }

        return listaSubcategoria;
    }

    /*categoria principal*/
    public Subcategoria findPrincipal() {

        Subcategoria subcategoria = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Subcategoria a where a.subPrincipal=:subPrincipal");
            query.setParameter("subPrincipal", Boolean.TRUE);
            if (query.getResultList().size() > 0) {
                subcategoria = (Subcategoria) query.getSingleResult();
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta subcategoria");
        } finally {
            em.close();
        }

        return subcategoria;
    }

}
