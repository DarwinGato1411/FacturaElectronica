/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import com.ec.entidad.Costopromediocompraview;
import com.ec.servicio.HelperPersistencia;
import com.ec.untilitario.CompraPromedio;
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
public class ServicioCostoPromedioCompraView {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<CompraPromedio> findBetweenGroupByProducto(Date inicio, Date fin) {

        List<CompraPromedio> listaCostoPromedioCompras = new ArrayList<CompraPromedio>();
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT NEW com.ec.untilitario.CompraPromedio(max(a.prodNombre),sum(a.cantidad), CASE WHEN sum(a.cantidad)<>0 THEN (sum(a.comprapromedio)/COUNT(a.cantidad)) ELSE 0 END,max(a.fecha),max(a.prodCantidadInicial)) FROM Costopromediocompraview a WHERE a.fecha BETWEEN :inicio and :fin GROUP BY a.prodNombre ORDER BY a.prodNombre ASC");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            listaCostoPromedioCompras = (List<CompraPromedio>) query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en la consulta CostoPromedioCompra " + e.getMessage());
        } finally {
            em.close();
        }

        return listaCostoPromedioCompras;
    }

}
