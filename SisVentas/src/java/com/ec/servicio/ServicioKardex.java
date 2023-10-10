/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.Egresoskardex;
import com.ec.entidad.Ingresoskardex;
import com.ec.entidad.Kardex;
import com.ec.entidad.Producto;
import com.ec.untilitario.TotalKardex;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioKardex {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Kardex kardex) {

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

    public void eliminar(Kardex kardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(kardex));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  kardex" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Kardex kardex) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(kardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar kardex");
        } finally {
            em.close();
        }
    }

    public List<Kardex> FindALlKardex() {

        List<Kardex> listaKardexs = new ArrayList<Kardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Kardex.findAll", Kardex.class);
            listaKardexs = (List<Kardex>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta kardex");
        } finally {
            em.close();
        }

        return listaKardexs;
    }

    public Kardex FindALlKardexs(Producto producto) {

        List<Kardex> listaKardexs = new ArrayList<Kardex>();
        Kardex kardex = null;
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Kardex.findByIdProducto", Kardex.class);
            query.setParameter("idProducto", producto);
            listaKardexs = (List<Kardex>) query.getResultList();
            if (listaKardexs.size() > 0) {
                kardex = listaKardexs.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta kardex");
        } finally {
            em.close();
        }

        return kardex;
    }

    public TotalKardex totalesForKardex(Kardex k) {
        BigDecimal ingresosKardex = BigDecimal.ZERO;
        BigDecimal egresosKardex = BigDecimal.ZERO;
        BigDecimal totalKardex = BigDecimal.ZERO;
        List<Ingresoskardex> listaIngKardexs = new ArrayList<Ingresoskardex>();
        List<Egresoskardex> listaEgreKardexs = new ArrayList<Egresoskardex>();
        TotalKardex totales = null;
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT k FROM Ingresoskardex k where k.idKardex=:idKardex");
            query.setParameter("idKardex", k.getIdKardex());
            Query query1 = em.createQuery("SELECT k FROM Egresoskardex k where k.idKardex=:idKardex");
            query1.setParameter("idKardex", k.getIdKardex());
            //ingresos
            listaIngKardexs = (List<Ingresoskardex>) query.getResultList();
            //egresos
            listaEgreKardexs = (List<Egresoskardex>) query1.getResultList();
            if (listaIngKardexs.size() > 0) {
                ingresosKardex = listaIngKardexs.get(0).getIngresos();
            }
            if (listaEgreKardexs.size() > 0) {
                egresosKardex = listaEgreKardexs.get(0).getEgresos();
            }
            totalKardex = ingresosKardex.subtract(egresosKardex);
            totales = new TotalKardex(ingresosKardex, egresosKardex, totalKardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta kardex prodCodigo " + e.getMessage());
        } finally {
            em.close();
        }

        return totales;
    }

    public List<Kardex> findByCodOrName(String prodCodigo, String prodNombre) {

        List<Kardex> listaKardexs = new ArrayList<Kardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a from Kardex a where a.idProducto.prodCodigo like :prodCodigo AND a.idProducto.prodNombre LIKE :prodNombre ORDER BY a.idProducto.prodNombre ASC");
            query.setParameter("prodCodigo", "%" + prodCodigo + "%");
            query.setParameter("prodNombre", "%" + prodNombre + "%");
            query.setMaxResults(200);
            listaKardexs = (List<Kardex>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta kardex findByCodOrName" + e.getMessage());
        } finally {
            em.close();
        }

        return listaKardexs;
    }

    public boolean deleteKardexFromCompra(CabeceraCompra cabeceraCompra) {

        List<Kardex> listaKardexs = new ArrayList<Kardex>();
        boolean actualiza = Boolean.TRUE;
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM DetalleKardex c WHERE c.idCompra :idCompra");
            int deletedCount = query.setParameter("idCompra", cabeceraCompra).executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            actualiza = Boolean.TRUE;
            System.out.println("Error en la consulta kardex deleteKardexFromCompra" + e.getMessage());
        } finally {
            em.close();
        }

        return actualiza;
    }

    public List<Kardex> FindALlKardexMaxMininimo(String estado) {

        List<Kardex> listaKardexs = new ArrayList<Kardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            String SQL = "SELECT a from Kardex a ";
            String WHERE = "";
            String ORDERBY = " ORDER BY a.idProducto.prodNombre ASC";

            if (estado.equals("MEM")) {
                WHERE = "WHERE a.idProducto.prodCantMinima >= a.karTotal";
            } else if (estado.equals("MAM")) {
                WHERE = "WHERE a.idProducto.prodCantMinima < a.karTotal";
            }
            SQL = SQL + WHERE + ORDERBY;
            Query query = em.createQuery(SQL);
            listaKardexs = (List<Kardex>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta kardex");
        } finally {
            em.close();
        }

        return listaKardexs;
    }
}
