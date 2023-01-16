/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import com.ec.servicio.HelperPersistencia;
import com.ec.untilitario.CompraPromedio2;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author gato
 */
public class ServicioCostoPromedioCompraView2 {
    Date inicio2 = new Date("01/09/2020");
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<CompraPromedio2> findBetweenGroupByProducto(Date inicio, Date fin) {
    inicio = inicio2;
        List<CompraPromedio2> listaCostoPromedioCompras = new ArrayList<CompraPromedio2>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();

         Query query = em.createQuery("SELECT NEW com.ec.untilitario.CompraPromedio2(a.descripcion,  min(a.fecha), AVG( a.subtotal ) ) FROM Costopromediocompraview2 a WHERE a.fecha BETWEEN :inicio  and :fin GROUP BY a.descripcion ORDER BY a.descripcion ASC");
     
         
         query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaCostoPromedioCompras = (List<CompraPromedio2>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta CostoPromedioCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCostoPromedioCompras;
    }

}
