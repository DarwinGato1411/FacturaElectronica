/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Cliente;
import com.ec.entidad.DetalleGuiaremision;
import com.ec.entidad.Guiaremision;
import com.ec.untilitario.Totales;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioGuia {

//    ServicioDetalleGuia servicioDetalleGuia = new ServicioDetalleGuia();
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Guiaremision guia) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(guia);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar guia simple " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void guardarGuiaremision(List<DetalleGuiaremision> detalleGuiaremisionDAOs, Guiaremision guia) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(guia);
            em.flush();
//            DetalleGuiaremision detalleGuiaremision = null;
            for (DetalleGuiaremision item : detalleGuiaremisionDAOs) {
                em.persist(item);
                em.flush();
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar guia GUARDAR CON DETALLE " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Guiaremision guia) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(guia));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  guia" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Guiaremision guia) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(guia);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar guia " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<Guiaremision> FindALlGuiaremision() {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findAll", Guiaremision.class);
//           query.setParameter("codigoUsuario", guia);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    public Guiaremision findUltimaGuiaremision() {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        Guiaremision guias = new Guiaremision();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Guiaremision a where (a.facNumero<>'0' or a.facNumero IS NOT NULL) ORDER BY  a.facNumero DESC");
            query.setMaxResults(2);
//           query.setParameter("codigoUsuario", guia);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            if (listaGuiaremisions.size() > 0) {
                guias = listaGuiaremisions.get(0);
            } else {
                guias = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia " + e.getMessage());
        } finally {
            em.close();
        }

        return guias;
    }

    public Guiaremision FindUltimaProforma() {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        Guiaremision guias = new Guiaremision();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findUltimaProforma", Guiaremision.class);
            query.setMaxResults(2);
//           query.setParameter("codigoUsuario", guia);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            if (listaGuiaremisions.size() > 0) {
                guias = listaGuiaremisions.get(0);
            } else {
                guias = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return guias;
    }

    public Guiaremision findFirIdFact(Integer idGuiaremision) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        Guiaremision guias = new Guiaremision();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findByIdGuiaremision", Guiaremision.class);
            query.setMaxResults(2);
            query.setParameter("idGuiaremision", idGuiaremision);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            if (listaGuiaremisions.size() > 0) {
                guias = listaGuiaremisions.get(0);
            } else {
                guias = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return guias;
    }

    public Guiaremision findByIdCotizacion(Integer idGuiaremision) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        Guiaremision guias = new Guiaremision();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findByIdCotizacion", Guiaremision.class);
            query.setMaxResults(2);
            query.setParameter("idGuiaremision", idGuiaremision);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            if (listaGuiaremisions.size() > 0) {
                guias = listaGuiaremisions.get(0);
            } else {
                guias = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return guias;
    }

    public Guiaremision findFirIdFactDiaria(Integer idGuiaremision) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        Guiaremision guias = new Guiaremision();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findByIdGuiaremisionDiaria", Guiaremision.class);
            query.setMaxResults(2);
            query.setParameter("idGuiaremision", idGuiaremision);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            if (listaGuiaremisions.size() > 0) {
                guias = listaGuiaremisions.get(0);
            } else {
                guias = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return guias;
    }

    public List<Guiaremision> FindLikeCliente(String cliente) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a from Guiaremision a WHERE a.idCliente.cliRazonSocial like :cliente and a.tipoGuia='EMITIDA'");
//            query.setMaxResults(2);
            query.setParameter("cliente", "%" + cliente + "%");
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia LIKE CLIENTE");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }
    public List<Guiaremision> FindLikeClienteRecibida(String cliente) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a from Guiaremision a WHERE a.idCliente.cliRazonSocial like :cliente and a.tipoGuia='RECIBIDA' ");
//            query.setMaxResults(2);
            query.setParameter("cliente", "%" + cliente + "%");
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia LIKE CLIENTE");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    public List<Guiaremision> findLikeProformaCliente(String cliente) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findLikeProformaCliente", Guiaremision.class);
//            query.setMaxResults(2);
            query.setParameter("cliente", "%" + cliente + "%");
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    //consulta las 20 primeras notas de venta
    public List<Guiaremision> FindALlGuiaremisionMaxVeinte() {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findAllMaxUltimoVeinte", Guiaremision.class);
            query.setMaxResults(400);
//           query.setParameter("codigoUsuario", guia);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    public List<Guiaremision> findAllProformas() {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findAllProformas", Guiaremision.class);
            query.setMaxResults(400);
//           query.setParameter("codigoUsuario", guia);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    //buscar por estado
    public List<Guiaremision> FindEstado(String estado) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findAllEstado", Guiaremision.class);
//            query.setMaxResults(2);
            query.setParameter("facEstado", estado);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    //buscar por estado
    public List<Guiaremision> findEstadoGuiaremision(String estado) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query;
            if (!estado.equals("TODO")) {
                query = em.createQuery("SELECT f FROM Guiaremision f WHERE  f.facEstado=:facEstado");
                query.setParameter("facEstado", estado);
            } else {
                query = em.createQuery("SELECT f FROM Guiaremision f");
                query.setMaxResults(2000);
            }

            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    public List<Guiaremision> findEstadoCliente(String estado, Cliente cliente) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT f FROM Guiaremision f WHERE  f.facEstado=:facEstado AND f.idCliente=:idCliente AND f.facNumero > 0 AND f.facTipo='FACT' ORDER BY f.facNumero DESC");
//            query.setMaxResults(2);
            query.setParameter("facEstado", estado);
            query.setParameter("idCliente", cliente);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    //ultima venta diaria
    public Guiaremision ultimaVentaDiaria(Date fecha) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        Guiaremision guias = new Guiaremision();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findUltimaVentaDiaria", Guiaremision.class
            );
            query.setMaxResults(2);
            query.setParameter("facFecha", fecha);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            System.out.println("lista de venta diaria " + listaGuiaremisions.size());
            if (listaGuiaremisions.size() > 0) {
                guias = listaGuiaremisions.get(0);
            } else {
                guias = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return guias;
    }

    /*CONSULTAS DEL PORTAL*/
    //consulta las 20 primeras notas de venta
    public List<Guiaremision> findAllMaxUltimoVeinteForCliente(Cliente idCliente) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findAllMaxUltimoVeinteForCliente", Guiaremision.class
            );
            query.setParameter("idCliente", idCliente);
            query.setMaxResults(400);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia " + e.getMessage());
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    public List<Guiaremision> findBetweenFacFecha(Date inicio, Date fin, Cliente idCliente) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Guiaremision.findBetweenFacFecha", Guiaremision.class
            );
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setParameter("idCliente", idCliente);
            query.setMaxResults(400);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia " + e.getMessage());
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    public List<Guiaremision> findFacFecha(Date inicio, Date fin, String estado) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            Query query;

//            String SQL = "SELECT f FROM Guiaremision f WHERE f.facFecha BETWEEN :inicio and :fin ORDER BY f.facFecha DESC";
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            if (!estado.equals("TODO")) {
                query = em.createQuery("SELECT f FROM Guiaremision f WHERE f.facFecha BETWEEN :inicio and :fin and f.tipoGuia='EMITIDA'  ORDER BY f.facNumero DESC");
                query.setParameter("inicio", inicio);
                query.setParameter("fin", fin);
//                query.setParameter("facEstado", estado);
            } else {
                query = em.createQuery("SELECT f FROM Guiaremision f WHERE f.facFecha BETWEEN :inicio and :fin and f.tipoGuia='EMITIDA' ORDER BY f.facNumero  DESC");
                query.setParameter("inicio", inicio);
                query.setParameter("fin", fin);
            }

//            query.setMaxResults(400);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia " + e.getMessage());
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }
    public List<Guiaremision> findFacFechaRecibida(Date inicio, Date fin, String estado) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            Query query;

//            String SQL = "SELECT f FROM Guiaremision f WHERE f.facFecha BETWEEN :inicio and :fin ORDER BY f.facFecha DESC";
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            if (!estado.equals("TODO")) {
                query = em.createQuery("SELECT f FROM Guiaremision f WHERE f.facFecha BETWEEN :inicio and :fin and f.tipoGuia='RECIBIDA'  ORDER BY f.facNumero DESC");
                query.setParameter("inicio", inicio);
                query.setParameter("fin", fin);
//                query.setParameter("facEstado", estado);
            } else {
                query = em.createQuery("SELECT f FROM Guiaremision f WHERE f.facFecha BETWEEN :inicio and :fin and f.tipoGuia='RECIBIDA' ORDER BY f.facNumero  DESC");
                query.setParameter("inicio", inicio);
                query.setParameter("fin", fin);
            }

//            query.setMaxResults(400);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia " + e.getMessage());
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    public List<Guiaremision> findBetweenFecha(Date inicio, Date fin) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a from Guiaremision a where a.facFecha BETWEEN :inicio AND :fin and a.tipoGuia='EMITIDA'");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setMaxResults(400);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia " + e.getMessage());
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }
    public List<Guiaremision> findBetweenFechaRecibida(Date inicio, Date fin) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a from Guiaremision a where a.facFecha BETWEEN :inicio AND :fin and a.tipoGuia='RECIBIDA'");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setMaxResults(400);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia " + e.getMessage());
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    public List<Totales> totalVenta(Date inicio, Date fin) {

        List<Totales> listaGuiaremisions = new ArrayList<Totales>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.untilitario.Totales(SUM(f.facTotalBaseGravaba)) FROM Guiaremision f WHERE f.facFecha BETWEEN :inicio AND :fin AND f.facNumero > 0 AND f.facTipo='FACT'");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setMaxResults(400);
            listaGuiaremisions = (List<Totales>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia " + e.getMessage());
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    public List<Totales> acumuladosVentas(Date inicio, Date fin) {

        List<Totales> listaGuiaremisions = new ArrayList<Totales>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.untilitario.Totales(SUM(f.facTotalBaseGravaba)) FROM Guiaremision f WHERE f.facFecha BETWEEN :inicio AND :fin AND f.facNumero > 0 AND f.facTipo='FACT'");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setMaxResults(400);
            listaGuiaremisions = (List<Totales>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia " + e.getMessage());
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    public List<Guiaremision> findLikeCedula(String valor) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT f FROM Guiaremision f WHERE f.idCliente.cliCedula LIKE :cliCedula AND f.facNumero > 0 and f.tipoGuia='EMITIDA'  ORDER BY f.idGuiaremision DESC");
//            query.setMaxResults(2);
            query.setParameter("cliCedula", "%" + valor + "%");
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }
      public List<Guiaremision> findLikeCedulaRecibida(String valor) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT f FROM Guiaremision f WHERE f.idCliente.cliCedula LIKE :cliCedula AND f.facNumero > 0 and f.tipoGuia='RECIBIDA'  ORDER BY f.idGuiaremision DESC");
//            query.setMaxResults(2);
            query.setParameter("cliCedula", "%" + valor + "%");
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    /*NOTA DE ENTREGA*/
    public Guiaremision findUltimaNotaEnt() {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        Guiaremision guias = new Guiaremision();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT f FROM Guiaremision f WHERE f.facTipo='NTE' AND f.facNumNotaEntrega IS NOT NULL ORDER BY f.facNumNotaEntrega DESC");
            query.setMaxResults(2);
//           query.setParameter("codigoUsuario", guia);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            if (listaGuiaremisions.size() > 0) {
                guias = listaGuiaremisions.get(0);
            } else {
                guias = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return guias;
    }

    /*NOTA DE ENTREGA POR CLIENTE*/
    public List<Guiaremision> findNotaEntPorCliente(String cedula) {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        Guiaremision guias = new Guiaremision();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT f FROM Guiaremision f WHERE f.facTipo='NTE' AND f.facNumNotaEntrega IS NOT NULL AND f.idCliente.cliCedula=:cliCedula AND (f.facNotaEntregaProcess IS NOT NULL OR f.facNotaEntregaProcess ='Nx   ') ORDER BY f.facNumNotaEntrega DESC");
            query.setParameter("cliCedula", cedula);
//           query.setParameter("codigoUsuario", guia);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }

    /*TODAS LASNOTA DE ENTREGA */
    public List<Guiaremision> findAllNotaEnt() {

        List<Guiaremision> listaGuiaremisions = new ArrayList<Guiaremision>();
        Guiaremision guias = new Guiaremision();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT f FROM Guiaremision f WHERE f.facTipo='NTE' AND f.facNumNotaEntrega IS NOT NULL AND f.facNotaEntregaProcess IS NOT NULL AND f.facNotaEntregaProcess ='N' ORDER BY f.facNumNotaEntrega DESC");
//            query.setParameter("cliCedula", cedula);
//           query.setParameter("codigoUsuario", guia);
            listaGuiaremisions = (List<Guiaremision>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta guia");
        } finally {
            em.close();
        }

        return listaGuiaremisions;
    }
}
