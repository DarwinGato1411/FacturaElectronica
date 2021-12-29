/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.ProductoProveedor;
import com.ec.entidad.ProductoProveedor;
import com.ec.entidad.Proveedores;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioProductoProveedor {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(ProductoProveedor productoProveedor) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.persist(productoProveedor);
            em.flush();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar productoProveedor " + e);
        } finally {
            em.close();
        }

    }

    public void eliminar(ProductoProveedor productoProveedor) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.remove(em.merge(productoProveedor));
          em.getTransaction().commit();



        } catch (Exception e) {
            System.out.println("Error en eliminar  productoProveedor" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(ProductoProveedor productoProveedor) {

        try {
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            em.merge(productoProveedor);
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar productoProveedor");
        } finally {
            em.close();
        }

    }

    public List<ProductoProveedor> FindALlProductoProveedor() {

        List<ProductoProveedor> listaProductoProveedors = new ArrayList<ProductoProveedor>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("ProductoProveedor.findAll", ProductoProveedor.class);
//           query.setParameter("codigoUsuario", productoProveedor);
            listaProductoProveedors = (List<ProductoProveedor>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta productoProveedor");
        } finally {
            em.close();
        }

        return listaProductoProveedors;
    }

    public List<ProductoProveedor> findLikeProdNombre(String buscar) {

        List<ProductoProveedor> listaProductoProveedors = new ArrayList<ProductoProveedor>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("ProductoProveedor.findlikeProducto", ProductoProveedor.class);
            query.setParameter("prodNombre", "%" + buscar + "%");
            listaProductoProveedors = (List<ProductoProveedor>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta productoProveedor");
        } finally {
            em.close();
        }

        return listaProductoProveedors;
    }

    public Integer findByIdProductoIdProveedor(Integer producto, Integer proveedor) {
        Integer existe = 0;
        List<ProductoProveedor> listaProductoProveedors = new ArrayList<ProductoProveedor>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("ProductoProveedor.findByIdProductoIdProveedor", ProductoProveedor.class);
            query.setParameter("idProducto", producto);
            query.setParameter("idProveedor", proveedor);
            listaProductoProveedors = (List<ProductoProveedor>) query.getResultList();
            em.flush();
            System.out.println("numero de elementos " + listaProductoProveedors.size());
            if (listaProductoProveedors.size() > 0) {
                //si existe algun registro
//                System.out.println("existe " + listaProductoProveedors.get(0).getProducto().getProdNombre());
                existe = 1;
            } else {
                //si no existe ningun registro
                existe = 0;
            }
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta productoProveedor por id prod and idprovee " + e);
        } finally {
            em.close();
        }

        return existe;
    }

    //consulta por proveedor
    public List<ProductoProveedor> findByIdProveedor(Proveedores p) {

        List<ProductoProveedor> listaProductoProveedors = new ArrayList<ProductoProveedor>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
          em.getTransaction().begin();
            Query query = em.createNamedQuery("ProductoProveedor.findByIdProveedor", ProductoProveedor.class);
            query.setParameter("idProveedor", p.getIdProveedor());
            listaProductoProveedors = (List<ProductoProveedor>) query.getResultList();
          em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta productoProveedor");
        } finally {
            em.close();
        }

        return listaProductoProveedors;
    }
}
