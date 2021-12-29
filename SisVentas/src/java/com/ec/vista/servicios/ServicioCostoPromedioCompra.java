/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import com.ec.servicio.HelperPersistencia;
import com.ec.vistas.CostoPromedioCompra;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCostoPromedioCompra {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<CostoPromedioCompra> findBetweenGroupByProducto(Integer idProducto) {

        List<CostoPromedioCompra> listaCostoPromedioCompras = new ArrayList<CostoPromedioCompra>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT a FROM CostoPromedioCompra a WHERE a.idProducto=:idProducto");
            query.setParameter("idProducto", idProducto);
            listaCostoPromedioCompras = (List<CostoPromedioCompra>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta CostoPromedioCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCostoPromedioCompras;
    }

}
