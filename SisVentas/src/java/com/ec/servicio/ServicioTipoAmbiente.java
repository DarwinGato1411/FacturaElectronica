/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Tipoambiente;
import com.ec.entidad.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTipoAmbiente {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Tipoambiente tipoambiente) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(tipoambiente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en insertar tipoambiente " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Tipoambiente tipoambiente) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(tipoambiente));
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en eliminar  tipoambiente " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(Tipoambiente tipoambiente) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(tipoambiente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            System.out.println("Error en insertar tipoambiente " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public Tipoambiente FindALlTipoambiente() {

        List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
        Tipoambiente tipoambiente = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Tipoambiente.findAllActivo", Tipoambiente.class);
//           query.setParameter("codigoUsuario", tipoambiente);
            listaTipoambientes = (List<Tipoambiente>) query.getResultList();
            if (listaTipoambientes.size() > 0) {
                tipoambiente = listaTipoambientes.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoambiente");
        } finally {
            em.close();
        }

        return tipoambiente;
    }

//    public Tipoambiente FindALlTipoambiente() {
//
//        List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
//        Tipoambiente tipoambiente = null;
//        try {
//            //Connection connection = em.unwrap(Connection.class);
//            em = HelperPersistencia.getEMF();
//            em.getTransaction().begin();
//            Query query = em.createNamedQuery("Tipoambiente.findAllActivo", Tipoambiente.class);
////           query.setParameter("codigoUsuario", tipoambiente);
//            listaTipoambientes = (List<Tipoambiente>) query.getResultList();
//            if (listaTipoambientes.size() > 0) {
//                tipoambiente = listaTipoambientes.get(0);
//            }
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            System.out.println("Error en lsa consulta tipoambiente");
//        } finally {
//            em.close();
//        }
//
//        return tipoambiente;
//    }
    public List<Tipoambiente> findAll(Usuario idUsuario) {

        List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();

        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Tipoambiente a  WHERE a.idUsuario=:idUsuario ORDER BY a.amNombreComercial ASC");
            query.setParameter("idUsuario", idUsuario);
            listaTipoambientes = (List<Tipoambiente>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoambiente");
        } finally {
            em.close();
        }

        return listaTipoambientes;
    }

    public List<Tipoambiente> findAllNombre(Usuario idUsuario, String buscar) {

        List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();

        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Tipoambiente a  WHERE a.idUsuario=:idUsuario AND UPPER(a.amNombreComercial) LIKE :amNombreComercial ORDER BY a.amNombreComercial ASC");
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("amNombreComercial", "%" + buscar + "%");
            listaTipoambientes = (List<Tipoambiente>) query.getResultList();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoambiente");
        } finally {
            em.close();
        }

        return listaTipoambientes;
    }

    public Tipoambiente finSelectFirst(Usuario usuario) {

        List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
        Tipoambiente selected = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Tipoambiente a  WHERE a.idUsuario=:idUsuario ORDER BY a.amNombreComercial asc");
            query.setParameter("idUsuario", usuario);
            listaTipoambientes = (List<Tipoambiente>) query.getResultList();
            if (listaTipoambientes.size() > 0) {
                selected = listaTipoambientes.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoambiente");
        } finally {
            em.close();
        }

        return selected;
    }

    public Tipoambiente findByUsuario(Usuario usuario) {

        List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
        Tipoambiente tipoambiente = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Tipoambiente a WHERE a.amEstado=TRUE");
//            query.setParameter("idUsuario", usuario);
            listaTipoambientes = (List<Tipoambiente>) query.getResultList();
            if (!listaTipoambientes.isEmpty()) {
                tipoambiente = listaTipoambientes.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoambiente " + e.getMessage());
        } finally {
            em.close();
        }

        return tipoambiente;
    }

    public Tipoambiente findALlTipoambientePorUsuario(Usuario usuario) {

        List<Tipoambiente> listaTipoambientes = new ArrayList<Tipoambiente>();
        Tipoambiente tipoambiente = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Tipoambiente a WHERE a.idUsuario=:idUsuario AND a.amEstado=:amEstado");
            query.setParameter("idUsuario", usuario);
            query.setParameter("amEstado", Boolean.TRUE);
            listaTipoambientes = (List<Tipoambiente>) query.getResultList();
            if (listaTipoambientes.size() > 0) {
                tipoambiente = listaTipoambientes.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoambiente");
        } finally {
            em.close();
        }

        return tipoambiente;
    }

}
