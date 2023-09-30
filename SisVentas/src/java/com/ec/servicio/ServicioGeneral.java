/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.servicio;

import com.ec.entidad.Usuario;
import com.ec.untilitario.ResultadoCompraVenta;
import com.ec.untilitario.SumaTotales;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

/**
 *
 * @author gato
 */
public class ServicioGeneral {

    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public ResultadoCompraVenta totalesCompraVenta(Date inicio, Date fin) {
        ResultadoCompraVenta compraVenta = new ResultadoCompraVenta(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ZERO, BigDecimal.ZERO);

        try {
            System.out.println("Entra a consultar usuarios");
            //Connection connection = em.unwrap(Connection.class);
            em = HelperPersistencia.getEMF();
            em.getTransaction().begin();

            Query query = em.createQuery("SELECT new com.ec.untilitario.SumaTotales(SUM(f.facSubtotal),SUM(f.facTotal))FROM Factura f WHERE f.facFecha BETWEEN :inicio AND :fin AND f.facNumero > 0 AND f.facTipo='FACT' ");
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            List<SumaTotales> lstVentas = query.getResultList();
            Query queryCompras = em.createQuery("SELECT new com.ec.untilitario.SumaTotales(SUM(c.cabSubTotal),SUM(c.cabTotal))FROM CabeceraCompra c WHERE c.cabFechaEmision BETWEEN :inicio AND :fin");
            queryCompras.setParameter("inicio", inicio);
            queryCompras.setParameter("fin", fin);
            List<SumaTotales> lstCompras = queryCompras.getResultList();

            for (SumaTotales lstVenta : lstVentas) {
                compraVenta.setSumaSubtotalVenta(lstVenta.getSumaSubtotal());
                compraVenta.setSumaTotalVenta(lstVenta.getSumaTotal());
            }

            for (SumaTotales lstCompra : lstCompras) {
                compraVenta.setSumaSubtotalCompra(lstCompra.getSumaSubtotal());
                compraVenta.setSumaTotalCompra(lstCompra.getSumaTotal());
            }

            compraVenta.setUtilidadSubtotal(compraVenta.getSumaSubtotalVenta().subtract(compraVenta.getSumaSubtotalCompra()));
            compraVenta.setUtilidadTotal(compraVenta.getSumaTotalVenta().subtract(compraVenta.getSumaTotalCompra()));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error en lsa consulta totalesCompraVenta " + e.getMessage());
        } finally {
            em.close();
        }

        return compraVenta;
    }

    public void corregitfacturasDiaActual() {
        try {
            em = HelperPersistencia.getEMF();

            em.getTransaction().begin();
//           Query elimina= em.createNativeQuery("delete from model_ruta;");
//            int i=elimina.executeUpdate();
//            System.out.println("VALOR BORRA "+i);
            StoredProcedureQuery queryStore = em.createStoredProcedureQuery("corregirproformasrepetidasdiaactual");
//            queryStore.registerStoredProcedureParameter("mes", Integer.class, ParameterMode.IN);
//            queryStore.registerStoredProcedureParameter("fecha", Date.class, ParameterMode.IN);
//            queryStore.setParameter("mes", mes);
//            queryStore.setParameter("fecha", fecha);
            queryStore.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("error corregitfacturasDiaActual " + e.getMessage());
        } finally {
            em.close();
        }

    }

    public void corregirProductos() {
        System.out.println("Entra a consultar usuarios");
        //Connection connection = em.unwrap(Connection.class);
        em = HelperPersistencia.getEMF();
        em.getTransaction().begin();
        StoredProcedureQuery queryStore = em.createStoredProcedureQuery("corregir_campos_producto");
        queryStore.executeUpdate();
        em.getTransaction().commit();
    }

    public void corregirProformas() {
        System.out.println("Entra a consultar usuarios");
        //Connection connection = em.unwrap(Connection.class);
        em = HelperPersistencia.getEMF();
        em.getTransaction().begin();
        StoredProcedureQuery queryStore = em.createStoredProcedureQuery("corregirproformasrepetidas");
        queryStore.executeUpdate();
        em.getTransaction().commit();
    }
    
     public void cierreCajaDetallePago(Date fecha) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate = df.format(fecha);
        System.out.println("Entra a cierre_caja_detalle_pago");
        //Connection connection = em.unwrap(Connection.class);
        em = HelperPersistencia.getEMF();
        em.getTransaction().begin();
        StoredProcedureQuery queryStore = em.createStoredProcedureQuery("cerrar_caja_detalle_pago");
        queryStore.registerStoredProcedureParameter("fecha_par", Date.class, ParameterMode.IN);
        try {
            queryStore.setParameter("fecha_par", df.parse(stringDate));
        } catch (ParseException ex) {
            Logger.getLogger(ServicioGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
        queryStore.executeUpdate();
        em.getTransaction().commit();
    }
}
