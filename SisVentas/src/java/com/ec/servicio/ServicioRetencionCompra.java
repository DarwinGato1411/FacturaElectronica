/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.DetalleRetencionCompra;
import com.ec.entidad.RetencionCompra;
import com.ec.untilitario.DetalleRetencionCompraDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioRetencionCompra {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(RetencionCompra retencionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(retencionCompra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void crearCabDetalle(RetencionCompra retencionCompra, List<DetalleRetencionCompraDao> compraDao) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();

            em.persist(retencionCompra);
            em.flush();
            DetalleRetencionCompra compra = null;
            for (DetalleRetencionCompraDao item : compraDao) {
                compra = new DetalleRetencionCompra(
                        item.getDrcBaseImponible(),
                        item.getDrcPorcentaje(),
                        item.getDrcValorRetenido(),
                        item.getTireCodigo(),
                        retencionCompra);
                compra.setIdTipoivaretencion(item.getTipoivaretencion());
                compra.setDrcDescripcion(item.getDrcDescripcion());
                compra.setDrcCodImpuestoAsignado(item.getCodImpuestoAsignado());
                compra.setDrcTipoRegistro(item.getTipoResgistro());
                em.persist(compra);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificarCabDetalle(RetencionCompra retencionCompra, List<DetalleRetencionCompraDao> compraDao) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(retencionCompra);
            em.flush();
            DetalleRetencionCompra compra = null;
            for (DetalleRetencionCompraDao item : compraDao) {
                compra = new DetalleRetencionCompra(
                        item.getDrcBaseImponible(),
                        item.getDrcPorcentaje(),
                        item.getDrcValorRetenido(),
                        item.getTireCodigo(),
                        retencionCompra);

                if (item.getRcoCodigo() != null) {
                    compra.setDrcCodigo(item.getDrcCodigo());
                }
                em.merge(compra);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(RetencionCompra retencionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(retencionCompra));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  retencionCompra" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(RetencionCompra retencionCompra) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(retencionCompra);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<RetencionCompra> findAll() {

        List<RetencionCompra> listaRetencionCompras = new ArrayList<RetencionCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM RetencionCompra a");
            listaRetencionCompras = (List<RetencionCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaRetencionCompras;
    }

    public RetencionCompra findByCompra(RetencionCompra retencionCompra) {
        RetencionCompra retencionCompra1 = null;
        List<RetencionCompra> listaRetencionCompras = new ArrayList<RetencionCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM RetencionCompra a where a.idCabecera=:idCabecera");
            query.setParameter("idCabecera", retencionCompra.getIdCabecera());
            listaRetencionCompras = (List<RetencionCompra>) query.getResultList();
            if (listaRetencionCompras.size() > 0) {
                retencionCompra1 = listaRetencionCompras.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return retencionCompra1;
    }

    public RetencionCompra findByCabeceraCompra(CabeceraCompra cabcompra) {
        RetencionCompra retencionCompra1 = null;
        List<RetencionCompra> listaRetencionCompras = new ArrayList<RetencionCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM RetencionCompra a where a.idCabecera=:idCabecera");
            query.setParameter("idCabecera", cabcompra);
            listaRetencionCompras = (List<RetencionCompra>) query.getResultList();
            if (listaRetencionCompras.size() > 0) {
                retencionCompra1 = listaRetencionCompras.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return retencionCompra1;
    }

    public RetencionCompra findUtlimaRetencion() {
        RetencionCompra retencionCompra = null;
        List<RetencionCompra> listaRetencionCompras = new ArrayList<RetencionCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM RetencionCompra a ORDER BY a.rcoSecuencial DESC ");
            listaRetencionCompras = (List<RetencionCompra>) query.getResultList();
            if (listaRetencionCompras.size() > 0) {
                retencionCompra = listaRetencionCompras.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return retencionCompra;
    }

    public List<RetencionCompra> findByFecha(Date inicio, Date fin) {

        List<RetencionCompra> listaRetencionCompras = new ArrayList<RetencionCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM RetencionCompra a WHERE a.rcoFecha BETWEEN :inicio AND :fin ORDER BY a.rcoFecha DESC ");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaRetencionCompras = (List<RetencionCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaRetencionCompras;
    }

    public List<RetencionCompra> findByNumeroFactura(String valor) {

        List<RetencionCompra> listaRetencionCompras = new ArrayList<RetencionCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM RetencionCompra a WHERE a.idCabecera.cabNumFactura LIKE :cabNumFactura ORDER BY a.rcoFecha DESC ");
            query.setParameter("cabNumFactura", "%" + valor + "%");
            listaRetencionCompras = (List<RetencionCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaRetencionCompras;
    }

    public List<RetencionCompra> findBySecuencialRet(String valor) {

        List<RetencionCompra> listaRetencionCompras = new ArrayList<RetencionCompra>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM RetencionCompra a WHERE a.rcoSecuencialText LIKE :rcoSecuencialText ORDER BY a.rcoFecha DESC ");
            query.setParameter("rcoSecuencialText", "%" + valor + "%");
            listaRetencionCompras = (List<RetencionCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta retencionCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaRetencionCompras;
    }

}
