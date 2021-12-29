/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.VentaRuta;
import com.ec.entidad.VentaRuta;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioVentaRuta {

//    ServicioDetalleGuia servicioDetalleGuia = new ServicioDetalleGuia();
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(VentaRuta ventaRuta) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(ventaRuta);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            System.out.println("Error en insertar ventaRuta simple " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(VentaRuta ventaRuta) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(ventaRuta));
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en eliminar  ventaRuta" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(VentaRuta ventaRuta) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(ventaRuta);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en insertar ventaRuta");
        } finally {
            em.close();
        }

    }

    public List<VentaRuta> findFacFecha(Date inicio, Date fin, String estado, String nombre, String cedula) {

        List<VentaRuta> listaVentaRutas = new ArrayList<VentaRuta>();
        try {

            // Setting the pattern
            // Setting the pattern
            SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            String strDate = sm.format(inicio);
            //Converting the String back to java.util.Date
            Date inicioBus = sm.parse(strDate);
            fin.setHours(23);
            fin.setMinutes(59);
            fin.setSeconds(00);

            Query query;

//            String SQL = "SELECT f FROM VentaRuta f WHERE f.facFecha BETWEEN :inicio and :fin ORDER BY f.facFecha DESC";
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();

            query = em.createQuery("SELECT f FROM VentaRuta f WHERE f.fecha BETWEEN :inicio and :fin AND UPPER(f.facturado)=:facturado AND f.cedula LIKE :cedula AND  UPPER(f.nombre) LIKE :nombre ORDER BY f.idVentaRuta DESC");
            query.setParameter("inicio", inicioBus);
            query.setParameter("fin", fin);
            query.setParameter("facturado", estado);
            query.setParameter("nombre", "%" + nombre + "%");
            query.setParameter("cedula", "%" + cedula + "%");
            listaVentaRutas = (List<VentaRuta>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en lsa consulta ventaRuta " + e.getMessage());
        } finally {
            em.close();
        }

        return listaVentaRutas;
    }

    public List<VentaRuta> findBetweenFecha(Date inicio, Date fin) {

        List<VentaRuta> listaVentaRutas = new ArrayList<VentaRuta>();
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a from VentaRuta a where a.fecha BETWEEN :inicio AND :fin");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            query.setMaxResults(400);
            listaVentaRutas = (List<VentaRuta>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en lsa consulta ventaRuta " + e.getMessage());
        } finally {
            em.close();
        }

        return listaVentaRutas;
    }

    public VentaRuta findByCodigo(String codigo) {

        List<VentaRuta> listaVentaRutas = new ArrayList<VentaRuta>();
        VentaRuta ventaRuta = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a from VentaRuta a where a.codigo =:codigo");
            query.setParameter("codigo", codigo);
            query.setMaxResults(2);
            listaVentaRutas = (List<VentaRuta>) query.getResultList();
            if (listaVentaRutas.size() > 0) {
                ventaRuta = listaVentaRutas.get(0);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en lsa consulta ventaRuta " + e.getMessage());
        } finally {
            em.close();
        }

        return ventaRuta;
    }

}
