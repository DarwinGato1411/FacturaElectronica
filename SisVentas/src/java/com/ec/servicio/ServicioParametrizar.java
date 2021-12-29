/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Parametrizar;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioParametrizar {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Parametrizar parametrizar) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(parametrizar);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar parametrizar");
        } finally {
            em.close();
        }

    }

    public void eliminar(Parametrizar parametrizar) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(parametrizar));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  parametrizar" + e);
        } finally {
            em.close();
        }

    }

    public void modificar(Parametrizar parametrizar) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(parametrizar);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar parametrizar " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public Parametrizar FindALlParametrizar() {

        List<Parametrizar> listaParametrizars = new ArrayList<Parametrizar>();
        Parametrizar parametrizar = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT p FROM Parametrizar p WHERE p.isprincipal=TRUE");
//           query.setParameter("codigoUsuario", parametrizar);
            listaParametrizars = (List<Parametrizar>) query.getResultList();
            if (listaParametrizars.size() > 0) {
                parametrizar = listaParametrizars.get(0);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta FindALlParametrizar parametrizar " + e.getMessage());
        } finally {
            em.close();
        }

        return parametrizar;
    }
}
