package com.ec.servicio;

import com.ec.entidad.DetalleKardex;
import com.ec.entidad.Kardex;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ServicioDetalleKardex {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(DetalleKardex detalleKardex) {
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(detalleKardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al insertar detalleKardex: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void eliminar(DetalleKardex detalleKardex) {
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(detalleKardex));
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar detalleKardex: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void eliminarKardexVenta(Integer idVenta) {
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM DetalleKardex dt WHERE dt.idVenta = :idVenta");
            query.setParameter("idVenta", idVenta);
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar detalleKardex de venta: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void eliminarKardexCompra(Integer idCompra) {
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM DetalleKardex dt WHERE dt.idCompra = :idCompra");
            query.setParameter("idCompra", idCompra);
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar detalleKardex de compra: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void modificar(DetalleKardex detalleKardex) {
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(detalleKardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al modificar detalleKardex: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<DetalleKardex> findAll() {
        List<DetalleKardex> listadetalleKardexs = new ArrayList<DetalleKardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("DetalleKardex.findAll", DetalleKardex.class);
            listadetalleKardexs = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la consulta detalleKardex findAll: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return listadetalleKardexs;
    }

    public List<DetalleKardex> findByIdKardex(Kardex kardex) {
        List<DetalleKardex> listadetalleKardexs = new ArrayList<DetalleKardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT d FROM DetalleKardex d WHERE d.idKardex = :idKardex ORDER BY d.detkFechakardex ASC");
            query.setParameter("idKardex", kardex);
            listadetalleKardexs = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la consulta detalleKardex findByIdKardex: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return listadetalleKardexs;
    }
}