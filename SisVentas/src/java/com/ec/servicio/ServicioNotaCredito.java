/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.Cliente;
import com.ec.entidad.DetalleNotaDebitoCredito;
import com.ec.entidad.NotaCreditoDebito;
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
public class ServicioNotaCredito {

    ServicioDetalleNotaCredito servicioDetalleNotaCreditoDebito = new ServicioDetalleNotaCredito();
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(NotaCreditoDebito notaCreditoDebito) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(notaCreditoDebito);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar notaCreditoDebito simple " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void guardarNotaCreditoDebito(List<DetalleFacturaDAO> detalleNotaCreditoDebitoDAOs, NotaCreditoDebito notaCreditoDebito) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(notaCreditoDebito);
            em.flush();
            DetalleNotaDebitoCredito detalleNotaCreditoDebito = null;
            for (DetalleFacturaDAO item : detalleNotaCreditoDebitoDAOs) {
                detalleNotaCreditoDebito = new DetalleNotaDebitoCredito(item.getCantidad(),
                        item.getDescripcion(),
                        item.getSubTotal(),
                        item.getTotal(),
                        item.getProducto(),
                        notaCreditoDebito, item.getTipoVenta());
                detalleNotaCreditoDebito.setDetIva(item.getDetIva());
                detalleNotaCreditoDebito.setDetTotalconiva(item.getDetTotalconiva());

                detalleNotaCreditoDebito.setDetSubtotaldescuento(item.getSubTotalDescuento());
                detalleNotaCreditoDebito.setDetTotaldescuento(item.getDetTotaldescuento());
                detalleNotaCreditoDebito.setDetPordescuento(item.getDetPordescuento());
                detalleNotaCreditoDebito.setDetValdescuento(item.getDetValdescuento());
                detalleNotaCreditoDebito.setDetTotaldescuentoiva(item.getDetTotalconivadescuento());
                detalleNotaCreditoDebito.setDetCantpordescuento(item.getDetCantpordescuento());
                detalleNotaCreditoDebito.setDetSubtotaldescuentoporcantidad(item.getDetSubtotaldescuentoporcantidad());
                detalleNotaCreditoDebito.setDetCodTipoVenta(item.getCodTipoVenta());
                // servicioDetalleNotaCreditoDebito.crear(detalleNotaCreditoDebito);
                em.persist(detalleNotaCreditoDebito);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar notaCreditoDebito GUARDAR CON DETALLE " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(NotaCreditoDebito notaCreditoDebito) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(notaCreditoDebito));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  notaCreditoDebito" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(NotaCreditoDebito notaCreditoDebito) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(notaCreditoDebito);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar notaCreditoDebito");
        } finally {
            em.close();
        }

    }

    public List<NotaCreditoDebito> FindALlNotaCreditoDebito() {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findAll", NotaCreditoDebito.class);
//           query.setParameter("codigoUsuario", notaCreditoDebito);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito");
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }

    public NotaCreditoDebito FindUltimaNotaCreditoDebito() {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        NotaCreditoDebito notaCreditoDebitos = new NotaCreditoDebito();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findUltimaNC", NotaCreditoDebito.class);
            query.setMaxResults(2);
//           query.setParameter("codigoUsuario", notaCreditoDebito);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            if (listaNotaCreditoDebitos.size() > 0) {
                notaCreditoDebitos = listaNotaCreditoDebitos.get(0);
            } else {
                notaCreditoDebitos = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito " + e.getMessage());
        } finally {
            em.close();
        }

        return notaCreditoDebitos;
    }

    public NotaCreditoDebito FindUltimaProforma() {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        NotaCreditoDebito notaCreditoDebitos = new NotaCreditoDebito();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findUltimaProforma", NotaCreditoDebito.class);
            query.setMaxResults(2);
//           query.setParameter("codigoUsuario", notaCreditoDebito);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            if (listaNotaCreditoDebitos.size() > 0) {
                notaCreditoDebitos = listaNotaCreditoDebitos.get(0);
            } else {
                notaCreditoDebitos = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito");
        } finally {
            em.close();
        }

        return notaCreditoDebitos;
    }

    public NotaCreditoDebito findFirIdFact(Integer idNotaCreditoDebito) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        NotaCreditoDebito notaCreditoDebitos = new NotaCreditoDebito();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findByIdNotaCreditoDebito", NotaCreditoDebito.class);
            query.setMaxResults(2);
            query.setParameter("idNotaCreditoDebito", idNotaCreditoDebito);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            if (listaNotaCreditoDebitos.size() > 0) {
                notaCreditoDebitos = listaNotaCreditoDebitos.get(0);
            } else {
                notaCreditoDebitos = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito");
        } finally {
            em.close();
        }

        return notaCreditoDebitos;
    }

    public NotaCreditoDebito findByIdCotizacion(Integer idNotaCreditoDebito) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        NotaCreditoDebito notaCreditoDebitos = new NotaCreditoDebito();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findByIdCotizacion", NotaCreditoDebito.class);
            query.setMaxResults(2);
            query.setParameter("idNotaCreditoDebito", idNotaCreditoDebito);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            if (listaNotaCreditoDebitos.size() > 0) {
                notaCreditoDebitos = listaNotaCreditoDebitos.get(0);
            } else {
                notaCreditoDebitos = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito");
        } finally {
            em.close();
        }

        return notaCreditoDebitos;
    }

    public NotaCreditoDebito findFirIdFactDiaria(Integer idNotaCreditoDebito) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        NotaCreditoDebito notaCreditoDebitos = new NotaCreditoDebito();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findByIdNotaCreditoDebitoDiaria", NotaCreditoDebito.class);
            query.setMaxResults(2);
            query.setParameter("idNotaCreditoDebito", idNotaCreditoDebito);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            if (listaNotaCreditoDebitos.size() > 0) {
                notaCreditoDebitos = listaNotaCreditoDebitos.get(0);
            } else {
                notaCreditoDebitos = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito");
        } finally {
            em.close();
        }

        return notaCreditoDebitos;
    }

    public List<NotaCreditoDebito> findLikeCedula(String cliente) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM NotaCreditoDebito a WHERE a.idFactura.idCliente.cliCedula LIKE :cliCedula ORDER BY a.facFecha DESC");
//            query.setMaxResults(2);
            query.setParameter("cliCedula", "%" + cliente + "%");
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito findLikeCedula"+e.getMessage());
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }
    
     public List<NotaCreditoDebito> findLikeCliente(String cliente) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM NotaCreditoDebito a WHERE a.idFactura.idCliente.cliNombre LIKE :cliNombre ORDER BY a.facFecha DESC");
//            query.setMaxResults(2);º
            query.setParameter("cliNombre", "%" + cliente + "%");
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito findLikeCedula"+e.getMessage());
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }

    public List<NotaCreditoDebito> findLikeProformaCliente(String cliente) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findLikeProformaCliente", NotaCreditoDebito.class);
//            query.setMaxResults(2);
            query.setParameter("cliente", "%" + cliente + "%");
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito");
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }

    //consulta las 20 primeras notas de venta
    public List<NotaCreditoDebito> FindALlNotaCreditoDebitoMaxVeinte() {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findAllMaxUltimoVeinte", NotaCreditoDebito.class);
            query.setMaxResults(400);
//           query.setParameter("codigoUsuario", notaCreditoDebito);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito");
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }

    public List<NotaCreditoDebito> findAllProformas() {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findAllProformas", NotaCreditoDebito.class);
            query.setMaxResults(400);
//           query.setParameter("codigoUsuario", notaCreditoDebito);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito");
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }

    //buscar por estado
    public List<NotaCreditoDebito> FindEstado(String estado) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findAllEstado", NotaCreditoDebito.class);
//            query.setMaxResults(2);
            query.setParameter("facEstado", estado);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito");
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }

    //ultima venta diaria
    public NotaCreditoDebito ultimaVentaDiaria(Date fecha) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        NotaCreditoDebito notaCreditoDebitos = new NotaCreditoDebito();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findUltimaVentaDiaria", NotaCreditoDebito.class);
            query.setMaxResults(2);
            query.setParameter("facFecha", fecha);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            System.out.println("lista de venta diaria " + listaNotaCreditoDebitos.size());
            if (listaNotaCreditoDebitos.size() > 0) {
                notaCreditoDebitos = listaNotaCreditoDebitos.get(0);
            } else {
                notaCreditoDebitos = null;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito");
        } finally {
            em.close();
        }

        return notaCreditoDebitos;
    }

    /*CONSULTAS DEL PORTAL*/
    //consulta las 20 primeras notas de venta
    public List<NotaCreditoDebito> findAllMaxUltimoVeinteForCliente(Cliente idCliente) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findAllMaxUltimoVeinteForCliente", NotaCreditoDebito.class);
            query.setParameter("idCliente", idCliente);
            query.setMaxResults(400);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito " + e.getMessage());
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }

    public List<NotaCreditoDebito> findBetweenFacFecha(Date inicio, Date fin, Cliente idCliente) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("NotaCreditoDebito.findBetweenFacFecha", NotaCreditoDebito.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setParameter("idCliente", idCliente);
            query.setMaxResults(400);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito " + e.getMessage());
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }

    public List<NotaCreditoDebito> findBetweenFecha(Date inicio, Date fin) {

        List<NotaCreditoDebito> listaNotaCreditoDebitos = new ArrayList<NotaCreditoDebito>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM NotaCreditoDebito a WHERE a.facFecha BETWEEN :inicio AND :fin ORDER BY a.facFecha DESC");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setMaxResults(400);
            listaNotaCreditoDebitos = (List<NotaCreditoDebito>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito " + e.getMessage());
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }

    public List<Totales> totalVenta(Date inicio, Date fin) {

        List<Totales> listaNotaCreditoDebitos = new ArrayList<Totales>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.untilitario.Totales(SUM(f.facTotalBaseGravaba)) FROM NotaCreditoDebito f WHERE f.facFecha BETWEEN :inicio AND :fin AND f.facNumero > 0 AND f.facTipo='FACT'");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setMaxResults(400);
            listaNotaCreditoDebitos = (List<Totales>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito " + e.getMessage());
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }

    public List<Totales> acumuladosVentas(Date inicio, Date fin) {

        List<Totales> listaNotaCreditoDebitos = new ArrayList<Totales>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT new com.ec.untilitario.Totales(SUM(f.facTotalBaseGravaba)) FROM NotaCreditoDebito f WHERE f.facFecha BETWEEN :inicio AND :fin AND f.facNumero > 0 AND f.facTipo='FACT'");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setMaxResults(400);
            listaNotaCreditoDebitos = (List<Totales>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta notaCreditoDebito " + e.getMessage());
        } finally {
            em.close();
        }

        return listaNotaCreditoDebitos;
    }
}
