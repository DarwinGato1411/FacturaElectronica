/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import com.ec.servicio.HelperPersistencia;
import com.ec.vistas.SriCatastro;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioSriCatastro {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<SriCatastro> findCatastro(String valor) {

        List<SriCatastro> listaSriCatastros = new ArrayList<SriCatastro>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM SriCatastro a WHERE a.micRuc =:ruc");
            query.setParameter("ruc", valor);            
            listaSriCatastros = (List<SriCatastro>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en las consulta SriCatastro " + e.getMessage());
        } finally {
            em.close();
        }

        return listaSriCatastros;
    }

}
