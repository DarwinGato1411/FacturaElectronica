/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.Factura;

import com.ec.servicio.ServicioCompra;

import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioGeneral;
import com.ec.untilitario.ResultadoCompraVenta;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

/**
 *
 * @author gato
 */
public class AdminstrarCompraVenta {

    ServicioCompra servicioCompra = new ServicioCompra();
    ServicioFactura servicioFactura = new ServicioFactura();
    ServicioGeneral servicioGeneral = new ServicioGeneral();
    private List<Factura> listaVentas = new ArrayList<Factura>();
    private List<CabeceraCompra> listaCompras = new ArrayList<CabeceraCompra>();
    private String buscar = "";
    private Date inicio = new Date();
    private Date fin = new Date();
    ResultadoCompraVenta compraVenta = new ResultadoCompraVenta();

    public AdminstrarCompraVenta() {
    }

    private void findByBetweenFecha() {

        Date fechaInicio = inicio;
        Date fechaFin = fin;

        fechaInicio = ajustarHoraInicio(fechaInicio);
        fechaFin = ajustarHoraFin(fechaFin);

        listaCompras = servicioCompra.findByBetweenFecha(fechaInicio, fechaFin);
        listaVentas = servicioFactura.findBetweenFecha(fechaInicio, fechaFin);
//        for (Factura venta : listaVentas) {
//            compraVenta
//        }
//        for (CabeceraCompra taCompra : listaCompras) {
//            
//        }
        compraVenta = servicioGeneral.totalesCompraVenta(fechaInicio, fechaFin);
    }

    private static Date ajustarHoraInicio(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    // Método para ajustar la hora de fin a "23:59:59"
    private static Date ajustarHoraFin(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    @Command
    @NotifyChange({"listaCompras", "listaVentas", "inicio", "fin", "compraVenta"})
    public void buscarForFechas() {
        findByBetweenFecha();
    }

    @Command
    public void descargaATS() throws FileNotFoundException {
        Boolean valida = Boolean.TRUE;
//
//        List<Totales> totalesesVenta = servicioFactura.totalVenta(inicio, fin);
//        if (totalesesVenta.size() > 0) {
//            valida = Boolean.TRUE;
//        } else {
//            valida = Boolean.FALSE;
//        }
//        if (valida) {
//            GenerarATS generarATS = new GenerarATS();
//            File f = new File(generarATS.generaXMLFactura(servicioAcumuladoVentas.findAcumuladoventas(inicio, fin),
//                    totalesesVenta.get(0).getTotal(),
//                    new ArrayList<CabeceraCompra>(),
//                    inicio, fin));
//            Filedownload.save(f, null);
//            Clients.showNotification("ATS generado correctamente..", "info", null, "end_before", 2000, true);
//        } else {
//            Clients.showNotification("Verifique la informacion para generar el ATS", "error", null, "start_before", 2000, true);
//        }
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public List<Factura> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Factura> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public List<CabeceraCompra> getListaCompras() {
        return listaCompras;
    }

    public void setListaCompras(List<CabeceraCompra> listaCompras) {
        this.listaCompras = listaCompras;
    }

    public ResultadoCompraVenta getCompraVenta() {
        return compraVenta;
    }

    public void setCompraVenta(ResultadoCompraVenta compraVenta) {
        this.compraVenta = compraVenta;
    }

}
