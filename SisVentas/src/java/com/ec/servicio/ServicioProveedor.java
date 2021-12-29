/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Proveedores;
import com.ec.entidad.Proveedores;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioProveedor {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Proveedores proveedores) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(proveedores);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar proveedores");
        } finally {
            em.close();
        }

    }

    public void eliminar(Proveedores proveedores) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(proveedores));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  proveedores" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Proveedores proveedores) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(proveedores);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar proveedores");
        } finally {
            em.close();
        }

    }

    public List<Proveedores> FindALlProveedores() {

        List<Proveedores> listaProveedoress = new ArrayList<Proveedores>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Proveedores.findAll", Proveedores.class);
//           query.setParameter("codigoUsuario", proveedores);
            listaProveedoress = (List<Proveedores>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta proveedores");
        } finally {
            em.close();
        }

        return listaProveedoress;
    }

    public List<Proveedores> findLikeProvNombre(String buscar) {

        List<Proveedores> listaProveedoress = new ArrayList<Proveedores>();
        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Proveedores.findLikeProvNombre", Proveedores.class);
            query.setParameter("provNombre", "%" + buscar + "%");
            query.setMaxResults(100);
            listaProveedoress = (List<Proveedores>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta proveedores");
        } finally {
            em.close();
        }

        return listaProveedoress;
    }

    public Proveedores findProvCedula(String buscar) {
        Proveedores proveedores = new Proveedores();
        List<Proveedores> listaProveedoress = new ArrayList<Proveedores>();
        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Proveedores.findByProvCedula", Proveedores.class);
            query.setParameter("provCedula", buscar);
            query.setMaxResults(100);
            listaProveedoress = (List<Proveedores>) query.getResultList();
            if (listaProveedoress.size() > 0) {
                proveedores = (Proveedores) listaProveedoress.get(0);
            } else {
                proveedores = null;
            }
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta proveedores " + e);
        } finally {
            em.close();
        }

        return proveedores;
    }

    public List<Proveedores> findProveedorCedula(String buscar) {

        List<Proveedores> listaProveedoress = new ArrayList<Proveedores>();
        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("Proveedores.findByLikeProvCedula", Proveedores.class);
            query.setParameter("provCedula", "%"+buscar+"%");
            query.setMaxResults(100);
            listaProveedoress = (List<Proveedores>) query.getResultList();

          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta proveedores " + e);
        } finally {
            em.close();
        }

        return listaProveedoress;
    }
}
