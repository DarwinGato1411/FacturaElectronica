/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import com.ec.entidad.InversionEmpresa;
import com.ec.entidad.InversionEmpresaTotal;
import com.ec.servicio.HelperPersistencia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioInversionEmpresaTotal {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<InversionEmpresaTotal> findInversion() {

        List<InversionEmpresaTotal> listaDatos = new ArrayList<InversionEmpresaTotal>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM InversionEmpresaTotal a ORDER BY a.prodNombre ASC" );
         
            listaDatos = (List<InversionEmpresaTotal>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta RotacionProducto " + e.getMessage());
        } finally {
            em.close();
        }

        return listaDatos;
    }

}
