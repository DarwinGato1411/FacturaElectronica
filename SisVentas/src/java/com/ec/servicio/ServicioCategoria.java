/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCategoria {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Categoria categoria) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(categoria);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar categoria");
        } finally {
            em.close();
        }

    }

    public void eliminar(Categoria categoria) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(categoria));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  categoria" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Categoria categoria) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(categoria);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar categoria");
        } finally {
            em.close();
        }

    }

    public List<Categoria> FindALlCategoria() {

        List<Categoria> listaCategorias = new ArrayList<Categoria>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Categoria.findAll", Categoria.class);
//           query.setParameter("codigoUsuario", categoria);
            listaCategorias = (List<Categoria>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta categoria");
        } finally {
            em.close();
        }

        return listaCategorias;
    }

    public List<Categoria> findLikeProdNombre(String buscar) {

        List<Categoria> listaCategorias = new ArrayList<Categoria>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Categoria.findLikeProdNombre", Categoria.class);
            query.setParameter("prodNombre", "%" + buscar + "%");
            listaCategorias = (List<Categoria>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta categoria");
        } finally {
            em.close();
        }

        return listaCategorias;
    }

    public Categoria findByProdCodigo(String buscar) {

        Categoria categoria = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Categoria.findByProdCodigo", Categoria.class);
            query.setParameter("prodCodigo", buscar);
            if (query.getResultList().size() > 0) {
                categoria = (Categoria) query.getSingleResult();
            }

          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta categoria");
        } finally {
            em.close();
        }

        return categoria;
    }

    public List<Categoria> findLikeProdCodigo(String buscar) {

        List<Categoria> listaCategoria = new ArrayList<Categoria>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Categoria.findLikeProdCodigo", Categoria.class);
            query.setParameter("prodCodigo", buscar);

            listaCategoria = (List<Categoria>) query.getResultList();


          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta categoria");
        } finally {
            em.close();
        }

        return listaCategoria;
    }
}
