/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.vista.servicios;

import java.util.Date;
import com.ec.entidad.ProductoVendido;
import com.ec.servicio.HelperPersistencia;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

/**
 *
 * @author NanoDan
 */
public class ServicioProdcutosVendidos {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public List<ProductoVendido> obtenerProductosVendidos(Date fechaInicio, Date fechaFin) {
        try {
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();
            StoredProcedureQuery storedProcedure = em.createNamedStoredProcedureQuery("obtener_productos_vendidos");
            storedProcedure.setParameter("fecha_inicio", fechaInicio);
            storedProcedure.setParameter("fecha_fin", fechaFin);
            storedProcedure.execute();

            List<ProductoVendido> productosVendidos = storedProcedure.getResultList();
            return productosVendidos;
        } catch (Exception e) {
            // Manejo de la excepción según tus necesidades
            e.printStackTrace();
            throw new RuntimeException("Error al obtener los productos vendidos");
        }
    }
}
