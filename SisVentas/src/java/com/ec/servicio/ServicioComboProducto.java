/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.ComboProducto;
import com.ec.entidad.Producto;
import com.ec.untilitario.ComboProductoDao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioComboProducto {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(ComboProducto kardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(kardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar kardex simple " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void crearCombo(List<ComboProductoDao> productos) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();            
            for (ComboProductoDao item : productos) {
                ComboProducto com=item.getComboProducto();
                com.setComCantidad(item.getCantidad());
                em.persist(item.getComboProducto());
                em.flush();;
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar kardex simple " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(ComboProducto kardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(kardex));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  comboProeducto" + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminarPorProd(Producto producto) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM ComboProducto a where a.idProductoPadre=:idProducto");
            query.setParameter("idProducto", producto);
            int deletedCount = query.executeUpdate();
            if (deletedCount > 0) {
                System.out.println("BORRO ComboProducto");
            }
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  comboProeducto" + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(ComboProducto kardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(kardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en modificar " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<ComboProducto> FindALlComboProducto() {

        List<ComboProducto> listaComboProductos = new ArrayList<ComboProducto>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("ComboProducto.findAll", ComboProducto.class);
            listaComboProductos = (List<ComboProducto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta kardex");
        } finally {
            em.close();
        }

        return listaComboProductos;
    }

    public List<ComboProducto> findForProducto(Producto producto) {

        List<ComboProducto> listaComboProductos = new ArrayList<ComboProducto>();

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM ComboProducto a WHERE a.idProductoPadre=:idProducto");
            query.setParameter("idProducto", producto);
            listaComboProductos = (List<ComboProducto>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta findForProducto " + e.getMessage());
        } finally {
            em.close();
        }

        return listaComboProductos;
    }

}
