/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.DetalleKardex;
import com.ec.entidad.Kardex;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Producto;
import com.ec.servicio.ServicioDetalleKardex;
import com.ec.servicio.ServicioKardex;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioTipoKardex;
import com.ec.untilitario.ArchivoUtils;
import com.sun.imageio.plugins.common.BogusColorSpace;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NuevoProducto {

    ServicioKardex servicioKardex = new ServicioKardex();
    ServicioDetalleKardex servicioDetalleKardex = new ServicioDetalleKardex();
    ServicioTipoKardex servicioTipoKardex = new ServicioTipoKardex();
    private Producto producto = new Producto();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    private String accion = "create";
    private Parametrizar parametrizar = new Parametrizar();
    private Kardex kardex = new Kardex();
    @Wire
    Window windowCliente;
    @Wire
    Textbox txtIvaRec;

    private String conIva = "S";
    private String esProducto = "P";

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Producto producto, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        parametrizar = servicioParametrizar.FindALlParametrizar();
        if (producto != null) {
            this.producto = producto;
            if (producto.getProdGrabaIva()) {
                conIva = "S";
            } else {
                conIva = "N";
            }

            if (producto.getProdEsproducto()) {
                esProducto = "P";
            } else {
                esProducto = "S";
            }
            producto.setProdUnidadMedida(producto.getProdUnidadMedida() == null ? "UNIDAD" : producto.getProdUnidadMedida());
            producto.setProdUnidadConversion(producto.getProdUnidadConversion() == null ? "UNIDAD" : producto.getProdUnidadConversion());
            producto.setProdFactorConversion(producto.getProdFactorConversion() == null ? BigDecimal.ONE : producto.getProdFactorConversion());
            accion = "update";
        } else {
            this.producto = new Producto(0, Boolean.FALSE);
            this.producto.setProdIva(parametrizar.getParIva());
            this.producto.setProdManoObra(BigDecimal.ZERO);
            this.producto.setProdCantidadInicial(BigDecimal.ZERO);
            this.producto.setProdTrasnporte(BigDecimal.ZERO);
            this.producto.setProdUtilidadNormal(parametrizar.getParUtilidad());
            this.producto.setProdUtilidadPreferencial(parametrizar.getParUtilidadPreferencial());
            this.producto.setProdUtilidadDos(parametrizar.getParUtilidadPreferencialDos());
            this.producto.setProdCostoPreferencialDos(BigDecimal.ZERO);
            this.producto.setProdCostoPreferencialTres(BigDecimal.ZERO);
            this.producto.setPordCostoVentaFinal(BigDecimal.ZERO);
            this.producto.setProdCantMinima(BigDecimal.valueOf(5));
            this.producto.setProdTieneSubsidio("N");
            this.producto.setProdFechaRegistro(new Date());
            this.producto.setProdPrecioSinSubsidio(BigDecimal.ZERO);
            this.producto.setProdSubsidio(BigDecimal.ZERO);
            this.producto.setProdUnidadMedida("UNIDAD");
            this.producto.setProdUnidadConversion("UNIDAD");
            this.producto.setProdFactorConversion(BigDecimal.ONE);

            accion = "create";
        }

    }

    @Command
    @NotifyChange({"producto"})
    public void colocarIva() {
        if (conIva.equals("S")) {
            txtIvaRec.setText("12");
            producto.setProdIva(parametrizar.getParIva());
        } else {
            txtIvaRec.setText("0");
            producto.setProdIva(BigDecimal.ZERO);
        }
    }

    @Command
    @NotifyChange({"producto"})
    public void calculopreciofinal() {
        BigDecimal porcenIva = (producto.getProdIva().divide(BigDecimal.valueOf(100), 4, RoundingMode.FLOOR)).add(BigDecimal.ONE);
        if (producto.getPordCostoCompra() != null) {
            //VALOR DE LA COMPRA MAS EL IVA
            BigDecimal compraMasIva = producto.getPordCostoCompra().multiply(porcenIva).setScale(4, RoundingMode.UP);
            producto.setPordCostoVentaRef(compraMasIva);
            /*PRECIO FINAL*/
 /*COSTO CON LA UTILIDAD MAS ALTA*/
            BigDecimal UtiManTrans = ((producto.getProdUtilidadNormal().add(producto.getProdManoObra()).add(producto.getProdTrasnporte())).divide(BigDecimal.valueOf(100))).add(BigDecimal.ONE);
            BigDecimal costoPorUtiManTrans = compraMasIva.multiply(UtiManTrans).setScale(4, RoundingMode.UP);
            producto.setPordCostoVentaFinal(costoPorUtiManTrans);

        }
    }

    @Command
    @NotifyChange({"producto"})
    public void calculopreciofinalUno() {
        BigDecimal porcenIva = (producto.getProdIva().divide(BigDecimal.valueOf(100), 4, RoundingMode.FLOOR)).add(BigDecimal.ONE);
        if (producto.getPordCostoCompra() != null) {
            //VALOR DE LA COMPRA MAS EL IVA
            BigDecimal compraMasIva = producto.getPordCostoCompra().multiply(porcenIva).setScale(4, RoundingMode.UP);
            producto.setPordCostoVentaRef(compraMasIva);

            //precio preferencial precio al por mayor
            BigDecimal UtiManTransPref = ((producto.getProdUtilidadPreferencial().add(producto.getProdManoObra()).add(producto.getProdTrasnporte())).divide(BigDecimal.valueOf(100), 4, RoundingMode.FLOOR)).add(BigDecimal.ONE);
            BigDecimal costoPorUtiManTransPref = compraMasIva.multiply(UtiManTransPref).setScale(4, RoundingMode.UP);
            producto.setProdCostoPreferencial(costoPorUtiManTransPref);

        }
    }

    @Command
    @NotifyChange({"producto"})
    public void calculopreciofinalDos() {
        BigDecimal porcenIva = (producto.getProdIva().divide(BigDecimal.valueOf(100), 4, RoundingMode.FLOOR)).add(BigDecimal.ONE);
        if (producto.getPordCostoCompra() != null) {
            //VALOR DE LA COMPRA MAS EL IVA
            BigDecimal compraMasIva = producto.getPordCostoCompra().multiply(porcenIva).setScale(4, RoundingMode.UP);
            producto.setPordCostoVentaRef(compraMasIva);
            /*PRECIO FINAL*/
            //precio preferencial precio por caja
            BigDecimal UtiManTransPref1 = ((producto.getProdUtilidadDos().add(producto.getProdManoObra()).add(producto.getProdTrasnporte())).divide(BigDecimal.valueOf(100), 4, RoundingMode.FLOOR)).add(BigDecimal.ONE);
            BigDecimal costoPorUtiManTransPref1 = compraMasIva.multiply(UtiManTransPref1).setScale(4, RoundingMode.UP);
            producto.setProdCostoPreferencialDos(costoPorUtiManTransPref1);
        }
    }

    @Command
    @NotifyChange({"producto"})
    public void calculoutilidad() {
        BigDecimal precioventasobrereferen = producto.getPordCostoVentaFinal().divide(producto.getPordCostoVentaRef(), 3, RoundingMode.FLOOR);
        BigDecimal precioventasobrereferenporcien = precioventasobrereferen.multiply(BigDecimal.valueOf(100));
        BigDecimal utilidad = precioventasobrereferenporcien.subtract(BigDecimal.valueOf(100));
        producto.setProdUtilidadNormal(utilidad);
    }

    @Command
    @NotifyChange({"producto"})
    public void calculoutilidadUno() {
        BigDecimal precioventasobrereferen = producto.getProdCostoPreferencial().divide(producto.getPordCostoVentaRef(), 3, RoundingMode.FLOOR);
        BigDecimal precioventasobrereferenporcien = precioventasobrereferen.multiply(BigDecimal.valueOf(100));
        BigDecimal utilidad = precioventasobrereferenporcien.subtract(BigDecimal.valueOf(100));
        producto.setProdUtilidadPreferencial(utilidad);
    }

    @Command
    @NotifyChange({"producto"})
    public void calculoutilidadDos() {
        BigDecimal precioventasobrereferen = producto.getProdCostoPreferencialDos().divide(producto.getPordCostoVentaRef(), 3, RoundingMode.FLOOR);
        BigDecimal precioventasobrereferenporcien = precioventasobrereferen.multiply(BigDecimal.valueOf(100));
        BigDecimal utilidad = precioventasobrereferenporcien.subtract(BigDecimal.valueOf(100));
        producto.setProdUtilidadDos(utilidad);
    }

    @Command
    @NotifyChange({"producto", "conIva"})
    public void calcularValores() {

        BigDecimal porcenIva = (producto.getProdIva().divide(BigDecimal.valueOf(100), 4, RoundingMode.FLOOR)).add(BigDecimal.ONE);
        // BigDecimal porcenUtilidad = ((producto.getProdIva().add(producto.getProdUtilidadNormal()).add(producto.getProdManoObra()).add(producto.getProdTrasnporte())).divide(BigDecimal.valueOf(100))).add(BigDecimal.ONE);
        //BigDecimal porcenUtilidadPref = ((producto.getProdIva().add(producto.getProdUtilidadPreferencial()).add(producto.getProdManoObra()).add(producto.getProdTrasnporte())).divide(BigDecimal.valueOf(100))).add(BigDecimal.ONE);
//para el precio normal
        if (producto.getProdIva().intValue() == 0) {
            conIva = "N";
        } else {
            conIva = "S";
            producto.setProdIva(parametrizar.getParIva());
        }
        if (producto.getPordCostoCompra() != null) {
            //VALOR DE LA COMPRA MAS EL IVA
            BigDecimal compraMasIva = ArchivoUtils.redondearDecimales(producto.getPordCostoCompra().multiply(porcenIva), 3);
            producto.setPordCostoVentaRef(compraMasIva);
            /*PRECIO FINAL*/

        }


        /*COSTO CON LA UTILIDAD MAS MENOR*/
//        producto.setProdCostoPreferencial(costoPorUtiManTransPref);
    }

    @Command
    public void verificarValor() {
        System.out.println("varificar");
        System.out.println("Valor " + producto.getPordCostoCompra());
    }

    @Command
    public void guardar() {
        if (producto.getProdNombre() != null
                && producto.getProdCodigo() != null
                && producto.getPordCostoVentaRef() != null
                && producto.getPordCostoVentaFinal() != null
                && producto.getProdCantidadInicial() != null) {

            if (conIva.equals("S")) {
                producto.setProdGrabaIva(Boolean.TRUE);
            } else {
                producto.setProdGrabaIva(Boolean.FALSE);
                producto.setProdIva(BigDecimal.ZERO);
            }
            if (esProducto.equals("P")) {
                producto.setProdEsproducto(Boolean.TRUE);
            } else {
                producto.setProdEsproducto(Boolean.FALSE);
            }
            if (accion.equals("create")) {
                if (servicioProducto.findByProdCodigo(producto.getProdCodigo()) != null) {
                    Clients.showNotification("El codigo del prodcuto ya se encuentra registrado",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 3000, true);
                    return;
                }

                servicioProducto.crear(producto);
                if (servicioKardex.FindALlKardexs(producto) == null && producto.getProdEsproducto()) {
                    kardex = new Kardex();
                    DetalleKardex detalleKardex = new DetalleKardex();
                    kardex.setIdProducto(producto);
                    kardex.setKarDetalle("Inicio de inventario desde la creacion del produto: " + producto.getProdNombre());
                    kardex.setKarFecha(new Date());
                    kardex.setKarFechaKardex(new Date());
                    kardex.setKarTotal(producto.getProdCantidadInicial());
                    servicioKardex.crear(kardex);
                    detalleKardex.setIdKardex(kardex);
                    detalleKardex.setDetkFechacreacion(new Date());
                    detalleKardex.setDetkFechakardex(new Date());
                    detalleKardex.setDetkCantidad(producto.getProdCantidadInicial());
                    detalleKardex.setDetkDetalles("Aumenta INICIO DE INVETARIO ");
                    detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                    detalleKardex.setIdTipokardex(servicioTipoKardex.findByTipkSigla("ING"));
                    servicioDetalleKardex.crear(detalleKardex);
                }

                windowCliente.detach();
            } else {
                servicioProducto.modificar(producto);
                if (servicioKardex.FindALlKardexs(producto) == null && producto.getProdEsproducto()) {
                    kardex = new Kardex();
                    DetalleKardex detalleKardex = new DetalleKardex();
                    kardex.setIdProducto(producto);
                    kardex.setKarDetalle("Inicio de inventario desde la creacion del produto: " + producto.getProdNombre());
                    kardex.setKarFecha(new Date());
                    kardex.setKarFechaKardex(new Date());
                    kardex.setKarTotal(producto.getProdCantidadInicial());
                    servicioKardex.crear(kardex);
                    detalleKardex.setIdKardex(kardex);
                    detalleKardex.setDetkFechacreacion(new Date());
                    detalleKardex.setDetkFechakardex(new Date());
                    detalleKardex.setDetkCantidad(producto.getProdCantidadInicial());
                    detalleKardex.setDetkDetalles("Aumenta INICIO DE INVETARIO ");
                    detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                    detalleKardex.setIdTipokardex(servicioTipoKardex.findByTipkSigla("ING"));
                    servicioDetalleKardex.crear(detalleKardex);
                }

                windowCliente.detach();
            }

        } else {
            Messagebox.show("Verifique la informacion requerida", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getConIva() {
        return conIva;
    }

    public void setConIva(String conIva) {
        this.conIva = conIva;
    }

    public String getEsProducto() {
        return esProducto;
    }

    public void setEsProducto(String esProducto) {
        this.esProducto = esProducto;
    }

}
