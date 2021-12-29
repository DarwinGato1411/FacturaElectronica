/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Tipoivaretencion;
import com.ec.entidad.Tipoivaretencion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioTipoIvaRetencion {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void crear(Tipoivaretencion tipoivaretencion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.persist(tipoivaretencion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoivaretencion " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void eliminar(Tipoivaretencion tipoivaretencion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.remove(em.merge(tipoivaretencion));
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Error en eliminar  tipoivaretencion " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void modificar(Tipoivaretencion tipoivaretencion) {

        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            em.merge(tipoivaretencion);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en insertar tipoivaretencion " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public List<Tipoivaretencion> findALlTipoivaretencion() {

        List<Tipoivaretencion> listaDatos = new ArrayList<Tipoivaretencion>();
        Tipoivaretencion tipoivaretencion = null;
        try {
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM Tipoivaretencion a ORDER BY a.tipivaretDescripcion ASC");
//           query.setParameter("codigoUsuario", tipoivaretencion);
            listaDatos = (List<Tipoivaretencion>) query.getResultList();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta tipoivaretencion " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }
}
