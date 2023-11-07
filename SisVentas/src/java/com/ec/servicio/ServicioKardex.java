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
            e.printStackTrace();
            System.out.println("Error al insertar Kardex: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void eliminar(Kardex kardex) {
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(kardex));
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al eliminar Kardex: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void modificar(Kardex kardex) {
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(kardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al modificar Kardex: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public List<Kardex> FindALlKardex() {
        List<Kardex> listaKardexs = new ArrayList<Kardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Kardex.findAll", Kardex.class);
            listaKardexs = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la consulta Kardex FindALlKardex: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
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
            listaKardexs = query.getResultList();
            if (listaKardexs.size() > 0) {
                kardex = listaKardexs.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la consulta Kardex FindALlKardexs: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
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
            // Ingresos
            listaIngKardexs = query.getResultList();
            // Egresos
            listaEgreKardexs = query1.getResultList();
            if (!listaIngKardexs.isEmpty()) {
                ingresosKardex = listaIngKardexs.get(0).getIngresos();
            }
            if (!listaEgreKardexs.isEmpty()) {
                egresosKardex = listaEgreKardexs.get(0).getEgresos();
            }
            totalKardex = ingresosKardex.subtract(egresosKardex);
            totales = new TotalKardex(ingresosKardex, egresosKardex, totalKardex);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la consulta Kardex totalesForKardex: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return totales;
    }

    public List<Kardex> findByCodOrName(String prodCodigo, String prodNombre) {
        List<Kardex> listaKardexs = new ArrayList<Kardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            String queryStr = "SELECT a FROM Kardex a WHERE a.idProducto.prodCodigo LIKE :prodCodigo AND a.idProducto.prodNombre LIKE :prodNombre ORDER BY a.idProducto.prodNombre ASC";
            Query query = em.createQuery(queryStr);
            query.setParameter("prodCodigo", "%" + prodCodigo + "%");
            query.setParameter("prodNombre", "%" + prodNombre + "%");
            query.setMaxResults(200);
            listaKardexs = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la consulta Kardex findByCodOrName: " + e.getMessage());
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return listaKardexs;
    }

    public boolean deleteKardexFromCompra(CabeceraCompra cabeceraCompra) {
        boolean actualiza = true;
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM DetalleKardex c WHERE c.idCompra = :idCompra");
            query.setParameter("idCompra", cabeceraCompra);
            int deletedCount = query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la consulta Kardex deleteKardexFromCompra: " + e.getMessage());
            actualiza = false;
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return actualiza;
    }

    public List<Kardex> FindALlKardexMaxMininimo(String estado) {
        List<Kardex> listaKardexs = new ArrayList<Kardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            String SQL = "SELECT a FROM Kardex a ";
            String WHERE = "";
            String ORDERBY = " ORDER BY a.idProducto.prodNombre ASC";

            if (estado.equals("MEM")) {
                WHERE = " WHERE a.idProducto.prodCantMinima >= a.karTotal";
            } else if (estado.equals("MAM")) {
                WHERE = " WHERE a.idProducto.prodCantMinima < a.karTotal";
            }
            SQL = SQL + WHERE + ORDERBY;
            Query query = em.createQuery(SQL);
            listaKardexs = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la consulta Kardex FindALlKardexMaxMininimo: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return listaKardexs;
    }

    public List<Kardex> findByCodOrNameCat(String prodCodigo, String prodNombre, Integer categoria) {
        List<Kardex> listaKardexs = new ArrayList<Kardex>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();

            String queryStr = "SELECT a FROM Kardex a WHERE a.idProducto.prodCodigo LIKE :prodCodigo AND a.idProducto.prodNombre LIKE :prodNombre  ORDER BY a.idProducto.prodNombre ASC";
            Query query;

            if (categoria != 8) {
                queryStr = "SELECT a FROM Kardex a WHERE a.idProducto.prodCodigo LIKE :prodCodigo AND a.idProducto.prodNombre LIKE :prodNombre AND a.idProducto.idSubCategoria.idSubCategoria = :idSubCategoria ORDER BY a.idProducto.prodNombre ASC";
                query = em.createQuery(queryStr);
                query.setParameter("idSubCategoria", categoria);
                query.setParameter("prodCodigo", "%" + prodCodigo + "%");
                query.setParameter("prodNombre", "%" + prodNombre + "%");

            } else {
                query = em.createQuery(queryStr);
                query.setParameter("prodCodigo", "%" + prodCodigo + "%");
                query.setParameter("prodNombre", "%" + prodNombre + "%");
            }

            query.setParameter("prodCodigo", "%" + prodCodigo + "%");
            query.setParameter("prodNombre", "%" + prodNombre + "%");
            query.setMaxResults(200);
            listaKardexs = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en la consulta Kardex findByCodOrNameCat: " + e.getMessage());
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return listaKardexs;
    }
}
