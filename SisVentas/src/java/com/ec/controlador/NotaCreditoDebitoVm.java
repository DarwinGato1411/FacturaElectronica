/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.Cliente;
import com.ec.entidad.ComboProducto;
import com.ec.entidad.DetalleFactura;
import com.ec.entidad.DetalleKardex;
import com.ec.entidad.Factura;
import com.ec.entidad.FormaPago;
import com.ec.entidad.Kardex;
import com.ec.entidad.NotaCreditoDebito;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Producto;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.Tipocomprobante;
import com.ec.entidad.Tipokardex;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioCliente;
import com.ec.servicio.ServicioComboProducto;
import com.ec.servicio.ServicioDetalleFactura;
import com.ec.servicio.ServicioDetalleKardex;
import com.ec.servicio.ServicioDetalleNotaCredito;
import com.ec.servicio.ServicioEstadoFactura;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioFormaPago;
import com.ec.servicio.ServicioKardex;
import com.ec.servicio.ServicioNotaCredito;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.servicio.ServicioTipoKardex;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.ParamFactura;
import com.ec.untilitario.TotalKardex;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NotaCreditoDebitoVm {

    @Wire
    Window windowClienteBuscar;
    @Wire
    Window windowProductoBuscar;
    /*DETALLE DEL KARDEX Y DETALLE KARDEX*/
    ServicioKardex servicioKardex = new ServicioKardex();
    ServicioDetalleKardex servicioDetalleKardex = new ServicioDetalleKardex();
    ServicioTipoKardex servicioTipoKardex = new ServicioTipoKardex();
    ServicioDetalleFactura servicioDetalleFactura = new ServicioDetalleFactura();
    //buscar cliente
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    ServicioFormaPago servicioFormaPago = new ServicioFormaPago();

    ServicioCliente servicioCliente = new ServicioCliente();
    ServicioEstadoFactura servicioEstadoFactura = new ServicioEstadoFactura();
    ServicioFactura servicioFactura = new ServicioFactura();
    //NOTA DE CREDITO
    ServicioNotaCredito servicioNotaCredito = new ServicioNotaCredito();
    ServicioDetalleNotaCredito servicioDetalleNotaCredito = new ServicioDetalleNotaCredito();
    public Cliente clienteBuscado = new Cliente("");
    private List<Cliente> listaClientesAll = new ArrayList<Cliente>();
    private String buscarNombre = "";
    private String buscarRazonSocial = "";
    private String buscarCedula = "";
    public static String buscarCliente = "";
    //busacar producto
    ServicioProducto servicioProducto = new ServicioProducto();
    private List<Producto> listaProducto = new ArrayList<Producto>();
    private String buscarNombreProd = "";
    private String buscarCodigoProd = "";
    private Producto productoBuscado = new Producto();
    public static String codigoBusqueda = "";
    //crear un factura nueva
    private Factura factura = new Factura();
    private DetalleFacturaDAO detalleFacturaDAO = new DetalleFacturaDAO();
    private ListModelList<DetalleFacturaDAO> listaDetalleFacturaDAOMOdel;
    private List<DetalleFacturaDAO> listaDetalleFacturaDAODatos = new ArrayList<DetalleFacturaDAO>();
    private Set<DetalleFacturaDAO> registrosSeleccionados = new HashSet<DetalleFacturaDAO>();
    //valorTotalCotizacion
    private BigDecimal valorTotalCotizacion = BigDecimal.ZERO;
    private BigDecimal subTotalCotizacion = BigDecimal.ZERO;
    private BigDecimal ivaCotizacion = BigDecimal.ZERO;
    private BigDecimal totalDescuento = BigDecimal.ZERO;
    private BigDecimal subsidioTotal = BigDecimal.ZERO;
    private BigDecimal subTotalBaseCero = BigDecimal.ZERO;
    private BigDecimal valorTotalInicialVent = BigDecimal.ZERO;
    //Cabecera de la factura
    private String estdoFactura = "PA";
    private String tipoVenta = "FACT";
    private String facturaDescripcion = "";
    private Integer numeroFactura = 0;
    private String numeroFacturaText = "";
    private String motivo = "";
    private Integer numeroProforma = 0;
    private Date fechafacturacion = new Date();
    private static BigDecimal DESCUENTOGENERAL = BigDecimal.valueOf(5.0);
    //usuario que factura

    private Parametrizar parametrizar = null;
//reporte
    AMedia fileContent = null;
    Connection con = null;
    //cambio
    private BigDecimal cobro = BigDecimal.ZERO;
    private BigDecimal cambio = BigDecimal.ZERO;


    /*forma de pago*/
    private List<FormaPago> listaFormaPago = new ArrayList<FormaPago>();
    private FormaPago formaPagoSelected = null;

    /*KARDEX*/
    private static Tipocomprobante tipocomprobante = null;
    /*RECUPERA EL ID DE LA FACTURA*/
    private Integer idFactuta = 0;
    private String accion = "create";
    private String tipoDoc = "";
    private String clietipo = "0";

    /*RUTAS PARA LOS ARCHIVPOS XML SRI*/
    private static String PATH_BASE = "";
    //tabla para los parametros del SRI
    UserCredential credential = new UserCredential();
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private Tipoambiente amb = new Tipoambiente();
    private String amRuc = "";

    private BigDecimal descuentoValorFinal = BigDecimal.ZERO;
    private BigDecimal subTotalCotizacion5 = BigDecimal.ZERO;
    private BigDecimal subTotalCotizacion12 = BigDecimal.ZERO;
    private BigDecimal subTotalCotizacion13 = BigDecimal.ZERO;
    private BigDecimal subTotalCotizacion14 = BigDecimal.ZERO;
    private BigDecimal subTotalCotizacion15 = BigDecimal.ZERO;
//    private BigDecimal subTotalBaseCero = BigDecimal.ZERO;
//    private BigDecimal ivaCotizacion = BigDecimal.ZERO;
    private BigDecimal ivaCotizacion5 = BigDecimal.ZERO;
    private BigDecimal ivaCotizacion13 = BigDecimal.ZERO;
    private BigDecimal ivaCotizacion14 = BigDecimal.ZERO;
    private BigDecimal ivaCotizacion15 = BigDecimal.ZERO;
//    private BigDecimal totalDescuento = BigDecimal.ZERO;
    
        /* PARA GESTION DE COMBO DE PRODCUTO */
    ServicioComboProducto servicioComboProducto = new ServicioComboProducto();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") ParamFactura valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        accion = "update";
        idFactuta = Integer.valueOf(valor.getIdFactura());
        tipoVenta = valor.getTipoDoc();
        System.out.println("idFactuta " + idFactuta);
        recuperFactura();

    }
//<editor-fold defaultstate="collapsed" desc="NOTA DE CREDITO">

    public NotaCreditoDebitoVm() {

        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
//        amRuc = credential.getUsuarioSistema().getUsuRuc();
//        amb = servicioTipoAmbiente.findALlTipoambientePorUsuario(credential.getUsuarioSistema());

        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();

//        Session sess = Sessions.getCurrent();
//        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
//        credential = cre;
        getDetallefactura();
        parametrizar = servicioParametrizar.FindALlParametrizar();
        listaFormaPago = servicioFormaPago.FindALlFormaPago();
        formaPagoSelected = servicioFormaPago.finPrincipal();

    }
// </editor-fold>

    private void recuperFactura() {

        if (tipoVenta.equals("FACT")) {
            factura = servicioFactura.findFirIdFact(idFactuta);
        } else {
            factura = servicioFactura.findByIdCotizacion(idFactuta);
        }
        clienteBuscado = factura.getIdCliente();
        numeroFactura = factura.getFacNumero();
        fechafacturacion = factura.getFacFecha();
        /*RECUPERA LOS VALORES TOTALES DE LA FACTURA*/
        subTotalCotizacion = factura.getFacSubtotal();
        ivaCotizacion = factura.getFacIva();
        valorTotalCotizacion = factura.getFacTotal();
        totalDescuento = factura.getFacDescuento();
        List<DetalleFactura> detalleFac = servicioDetalleFactura.findDetalleForIdFac(idFactuta);
        DetalleFacturaDAO nuevoRegistro;
        listaDetalleFacturaDAODatos.clear();
        for (DetalleFactura det : detalleFac) {
            nuevoRegistro = new DetalleFacturaDAO();
            nuevoRegistro.setCodigo(det.getIdProducto().getProdCodigo());
            nuevoRegistro.setCantidad(det.getDetCantidad());
            nuevoRegistro.setProducto(det.getIdProducto());
            nuevoRegistro.setDescripcion(det.getDetDescripcion());
            nuevoRegistro.setSubTotal(det.getDetSubtotal());
            nuevoRegistro.setTotal(det.getDetTotal());
            nuevoRegistro.setTotalInicial(det.getDetTotal());
            nuevoRegistro.setDetIva(det.getDetIva());
            nuevoRegistro.setDetTotalconiva(det.getDetTotalconiva());
            nuevoRegistro.setTipoVenta(det.getDetTipoVenta());
            nuevoRegistro.setEsProducto(det.getIdProducto().getProdEsproducto());
            //valores con descuentos
            nuevoRegistro.setSubTotalDescuento(det.getDetSubtotaldescuento());
            nuevoRegistro.setDetTotaldescuento(det.getDetTotaldescuento());
            nuevoRegistro.setDetPordescuento(det.getDetPordescuento());
            nuevoRegistro.setDetValdescuento(det.getDetValdescuento());
            nuevoRegistro.setDetTotalconivadescuento(det.getDetTotaldescuentoiva());
            nuevoRegistro.setDetCantpordescuento(det.getDetCantpordescuento().doubleValue() < 0 ? BigDecimal.ZERO : det.getDetCantpordescuento());
            nuevoRegistro.setDetIvaDesc(det.getDetIva());
            nuevoRegistro.setCodTipoVenta(det.getDetCodTipoVenta());
            nuevoRegistro.setDetSubtotaldescuentoporcantidad(det.getDetSubtotaldescuentoporcantidad());
            clietipo = det.getDetCodTipoVenta();
//            calcularValores(nuevoRegistro);
            listaDetalleFacturaDAODatos.add(nuevoRegistro);
        }

        getDetallefactura();
        calcularValoresTotales();
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero", "valorIce"})
    public void calcularValoresDesCantidad(@BindingParam("valor") DetalleFacturaDAO valor) {
        try {

            if (valor.getCantidad() == null) {
                return;
            }

            if (valor.getCantidad().doubleValue() <= 0) {
                return;
            }

            if (valor.getProducto() == null) {
                return;
            }
            /*SERVICOS */
            if (!valor.getEsProducto()) {

                valor.setTotal(valor.getDetTotaldescuento());
            }
            if (valor.getTotalInicial().doubleValue() == 0) {
                valor.setTotal(valor.getDetTotaldescuento());
            }
            BigDecimal factorIva = (valor.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
            BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));

//            BigDecimal factorice = valor.getProducto().getProdGrabaIce() ? (valor.getProducto().getProdPorcentajeIce().divide(BigDecimal.valueOf(100.0))) : BigDecimal.ZERO;
            BigDecimal factorSacarSubtotalIce = BigDecimal.ZERO;

            if (valor.getCantidad().doubleValue() > 0) {
                /*CALCULO DEL PORCENTAJE DE DESCUENTO*/
                BigDecimal porcentajeDesc = BigDecimal.ZERO;
                BigDecimal valorPorcentaje = BigDecimal.ZERO;
                BigDecimal valorDescuentoIva = BigDecimal.ZERO;
//                if (valor.getEsProducto()) {
//                    porcentajeDesc = valor.getTotal().multiply(BigDecimal.valueOf(100.0));
//                    valorPorcentaje = porcentajeDesc.divide(valor.getTotalInicial(), 5, RoundingMode.FLOOR);
//                    valorDescuentoIva = valor.getTotalInicial().subtract(valor.getTotal());
//                }else{
//                  valorPorcentaje = BigDecimal.ZERO;
//                    valorDescuentoIva = BigDecimal.ZERO;
//                }

                /*COLOCAMOS EN EL CAMPO DE DESCUENTO*/
//                BigDecimal porcentajeDiferencia = BigDecimal.valueOf(100.0).subtract(valorPorcentaje).setScale(5, RoundingMode.FLOOR);
                BigDecimal porcentajeDiferencia = BigDecimal.ZERO;
                valor.setDetPordescuento(porcentajeDiferencia);
                //valor unitario con descuento ioncluido iva
                BigDecimal valorTotalIvaDesc = valor.getTotalInicial().subtract(valorDescuentoIva);

                //valor unitario sin iva con descuento
                BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 5, RoundingMode.FLOOR);

//                valor.setSubTotalDescuento(subTotalDescuento);
                /*Calculo del ICE*/
//                BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 5, RoundingMode.FLOOR);
                /*Calculamos el Subtotal ICE*/
                BigDecimal valorICE = subTotalDescuento.divide(factorSacarSubtotalIce, 5, RoundingMode.FLOOR);
                BigDecimal IcePorProducto = subTotalDescuento.subtract(valorICE);
                IcePorProducto = ArchivoUtils.redondearDecimales(IcePorProducto, 3);

//                valor.setValorIce(IcePorProducto);
                valorICE = ArchivoUtils.redondearDecimales(valorICE, 3);
//                valor.setSubTotalDescuento(subTotalDescuento);
                valor.setSubTotalDescuento(valorICE);

                //valor del descuento
                BigDecimal valorDescuento = BigDecimal.ZERO;
                if (!valor.getEsProducto()) {
                    valor.setSubTotal(valor.getSubTotalDescuento());
                }
                if (valor.getEsProducto()) {
                    valorDescuento = ArchivoUtils.redondearDecimales(valor.getSubTotal(), 5).subtract(ArchivoUtils.redondearDecimales(valor.getSubTotalDescuento(), 5));
                }
                valor.setDetValdescuento(valorDescuento);
                //valor del iva con descuento
                BigDecimal valorIvaDesc = subTotalDescuento.multiply(factorIva).multiply(valor.getCantidad());

                valor.setDetIva(valorIvaDesc);

                //valor total con decuento y con iva
                valor.setDetTotaldescuento(valorDescuento.multiply(valor.getCantidad()));
                //cantidad por subtotal con descuento
                valor.setDetSubtotaldescuentoporcantidad(subTotalDescuento.multiply(valor.getCantidad()));
                valor.setDetTotalconivadescuento(valor.getCantidad().multiply(valorTotalIvaDesc));
                valor.setDetTotalconiva(valor.getCantidad().multiply(valor.getTotal()));

                valor.setDetCantpordescuento(valorDescuento.multiply(valor.getCantidad()));

            }
            calcularValoresTotales();
//            //ingresa un registro vacio
//            boolean registroVacio = true;
//            List<DetalleFacturaDAO> listaPedidoPost = listaDetalleFacturaDAOMOdel.getInnerList();
//
//            for (DetalleFacturaDAO item : listaPedidoPost) {
//                if (item.getProducto() == null) {
//                    registroVacio = false;
//                    break;
//                }
//            }
//
//            System.out.println("existe un vacio " + registroVacio);
//            if (registroVacio) {
//                DetalleFacturaDAO nuevoRegistroPost = new DetalleFacturaDAO();
//                nuevoRegistroPost.setProducto(null);
//                nuevoRegistroPost.setCantidad(BigDecimal.ZERO);
//                nuevoRegistroPost.setSubTotal(BigDecimal.ZERO);
//                nuevoRegistroPost.setDetIva(BigDecimal.ZERO);
//                nuevoRegistroPost.setDetTotalconiva(BigDecimal.ZERO);
//                nuevoRegistroPost.setDescripcion("");
//                ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistroPost);
//            }

        } catch (Exception e) {
            Messagebox.show("Ocurrio un error al calcular los valores" + e, "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "subTotalCotizacion13", "subTotalCotizacion14", "subTotalCotizacion15", "subTotalCotizacion5", "ivaCotizacion5", "ivaCotizacion13", "ivaCotizacion14", "ivaCotizacion15", "totalDescuento"})
    public void calcularValores(@BindingParam("valor") DetalleFacturaDAO valor) {
        try {
            BigDecimal factorIva = new BigDecimal(0);
            BigDecimal factorSacarSubtotal = new BigDecimal(1);
            if (valor.getProducto().getProdGrabaIva()) {
                factorIva = (valor.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
                factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));
            }

//         
            if (valor.getCantidad().intValue() > 0) {
//                BigDecimal porcentajeDesc = valor.getDetPordescuento().divide(BigDecimal.valueOf(100.0), 4, RoundingMode.FLOOR);
                BigDecimal porcentajeDesc = BigDecimal.ZERO;
                BigDecimal valorDescuentoIva = valor.getTotal().multiply(porcentajeDesc);

                BigDecimal valorIva = valor.getSubTotalDescuento().multiply(factorIva).multiply(valor.getCantidad());
//                valor.setDetIva(valorIva);
                //valor unitario con descuento ioncluido iva
                BigDecimal valorTotalIvaDesc = valor.getTotal().subtract(valorDescuentoIva);

                //valor unitario sin iva con descuento
                BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 4, RoundingMode.FLOOR);
                valor.setSubTotalDescuento(subTotalDescuento);
                //valor del descuento

                if (!valor.getEsProducto()) {
                    valor.setSubTotal(subTotalDescuento);
                }

                BigDecimal valorDescuento = valor.getSubTotal().subtract(valor.getSubTotalDescuento());
                if (valorDescuento.doubleValue() < 0) {
                    valorDescuento = BigDecimal.ZERO;
                }
                valor.setDetValdescuento(valorDescuento.doubleValue() < 0 ? BigDecimal.ZERO : valorDescuento);
                //valor del iva con descuento
                BigDecimal valorIvaDesc = subTotalDescuento.multiply(factorIva).multiply(valor.getCantidad());

                valor.setDetIva(valorIvaDesc);

                //valor total con decuento y con iva
                valor.setDetTotaldescuento(valorTotalIvaDesc);
                //cantidad por subtotal con descuento
                valor.setDetSubtotaldescuentoporcantidad(subTotalDescuento.multiply(valor.getCantidad()));
                valor.setDetTotalconivadescuento(valor.getCantidad().multiply(valorTotalIvaDesc));
                valor.setDetTotalconiva(valor.getCantidad().multiply(valor.getTotal()));
                valor.setDetCantpordescuento(valorDescuento.multiply(valor.getCantidad()));

            }
            calcularValoresTotales();
        } catch (Exception e) {
            Messagebox.show("Ocurrio un error al calcular los valores" + e, "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    private void getDetallefactura() {
        setListaDetalleFacturaDAOMOdel(new ListModelList<DetalleFacturaDAO>(getListaDetalleFacturaDAODatos()));
        ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).setMultiple(true);
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalBaseCero", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento"})
    public void seleccionarRegistros() {
        registrosSeleccionados = ((ListModelList<DetalleFacturaDAO>) getListaDetalleFacturaDAOMOdel()).getSelection();
        calcularValoresTotales();
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public String getBuscarNombreProd() {
        return buscarNombreProd;
    }

    public void setBuscarNombreProd(String buscarNombreProd) {
        this.buscarNombreProd = buscarNombreProd;
    }

    private void FindClienteLikeNombre() {
        listaClientesAll = servicioCliente.FindClienteLikeNombre(buscarNombre);
    }

    private void FindClienteLikeRazon() {
        listaClientesAll = servicioCliente.FindClienteLikeRazonSocial(buscarRazonSocial);
    }

    private void FindClienteLikeCedula() {
        listaClientesAll = servicioCliente.FindClienteLikeCedula(buscarCedula);
    }

    public Cliente getClienteBuscado() {
        return clienteBuscado;
    }

    public void setClienteBuscado(Cliente clienteBuscado) {
        this.clienteBuscado = clienteBuscado;
    }

    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    public String getBuscarRazonSocial() {
        return buscarRazonSocial;
    }

    public void setBuscarRazonSocial(String buscarRazonSocial) {
        this.buscarRazonSocial = buscarRazonSocial;
    }

    public String getBuscarCedula() {
        return buscarCedula;
    }

    public void setBuscarCedula(String buscarCedula) {
        this.buscarCedula = buscarCedula;
    }

    public static String getBuscarCliente() {
        return buscarCliente;
    }

    public static void setBuscarCliente(String buscarCliente) {
        NotaCreditoDebitoVm.buscarCliente = buscarCliente;
    }

    public List<Cliente> getListaClientesAll() {
        return listaClientesAll;
    }

    public void setListaClientesAll(List<Cliente> listaClientesAll) {
        this.listaClientesAll = listaClientesAll;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public DetalleFacturaDAO getDetalleFacturaDAO() {
        return detalleFacturaDAO;
    }

    public void setDetalleFacturaDAO(DetalleFacturaDAO detalleFacturaDAO) {
        this.detalleFacturaDAO = detalleFacturaDAO;
    }

    public ListModelList<DetalleFacturaDAO> getListaDetalleFacturaDAOMOdel() {
        return listaDetalleFacturaDAOMOdel;
    }

    public void setListaDetalleFacturaDAOMOdel(ListModelList<DetalleFacturaDAO> listaDetalleFacturaDAOMOdel) {
        this.listaDetalleFacturaDAOMOdel = listaDetalleFacturaDAOMOdel;
    }

    public List<DetalleFacturaDAO> getListaDetalleFacturaDAODatos() {
        return listaDetalleFacturaDAODatos;
    }

    public void setListaDetalleFacturaDAODatos(List<DetalleFacturaDAO> listaDetalleFacturaDAODatos) {
        this.listaDetalleFacturaDAODatos = listaDetalleFacturaDAODatos;
    }

    public Set<DetalleFacturaDAO> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<DetalleFacturaDAO> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    public Producto getProductoBuscado() {
        return productoBuscado;
    }

    public void setProductoBuscado(Producto productoBuscado) {
        this.productoBuscado = productoBuscado;
    }

    public static String getCodigoBusqueda() {
        return codigoBusqueda;
    }

    public static void setCodigoBusqueda(String codigoBusqueda) {
        NotaCreditoDebitoVm.codigoBusqueda = codigoBusqueda;
    }

    public String getBuscarCodigoProd() {
        return buscarCodigoProd;
    }

    public void setBuscarCodigoProd(String buscarCodigoProd) {
        this.buscarCodigoProd = buscarCodigoProd;
    }

    public BigDecimal getValorTotalCotizacion() {
        return valorTotalCotizacion;
    }

    public void setValorTotalCotizacion(BigDecimal valorTotalCotizacion) {
        this.valorTotalCotizacion = valorTotalCotizacion;
    }

    public BigDecimal getSubTotalCotizacion() {
        return subTotalCotizacion;
    }

    public void setSubTotalCotizacion(BigDecimal subTotalCotizacion) {
        this.subTotalCotizacion = subTotalCotizacion;
    }

    public BigDecimal getIvaCotizacion() {
        return ivaCotizacion;
    }

    public void setIvaCotizacion(BigDecimal ivaCotizacion) {
        this.ivaCotizacion = ivaCotizacion;
    }

    public String getEstdoFactura() {
        return estdoFactura;
    }

    public void setEstdoFactura(String estdoFactura) {
        this.estdoFactura = estdoFactura;
    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechafacturacion() {
        return fechafacturacion;
    }

    public void setFechafacturacion(Date fechafacturacion) {
        this.fechafacturacion = fechafacturacion;
    }

    public AMedia getFileContent() {
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public BigDecimal getCobro() {
        return cobro;
    }

    public void setCobro(BigDecimal cobro) {
        this.cobro = cobro;
    }

    public BigDecimal getCambio() {
        return cambio;
    }

    public void setCambio(BigDecimal cambio) {
        this.cambio = cambio;
    }

    public String getFacturaDescripcion() {
        return facturaDescripcion;
    }

    public void setFacturaDescripcion(String facturaDescripcion) {
        this.facturaDescripcion = facturaDescripcion;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    @Command
    @NotifyChange({"listaClientesAll", "clienteBuscado", "fechaEmision"})
    public void buscarClienteEnLista() {
        ParamFactura paramFactura = new ParamFactura();
        paramFactura.setBusqueda("cliente");
        final HashMap<String, ParamFactura> map = new HashMap<String, ParamFactura>();
        map.put("valor", paramFactura);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/venta/buscarcliente.zul", null, map);
        window.doModal();
        System.out.println("clinete de la lsitas buscarCliente " + buscarCliente);
        clienteBuscado = servicioCliente.FindClienteForCedula(buscarCliente);
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarRazonSocial"})
    public void buscarClienteRazon() {

        FindClienteLikeRazon();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarNombre"})
    public void buscarClienteNombre() {

        FindClienteLikeNombre();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarCedula"})
    public void buscarClienteCedula() {

        FindClienteLikeCedula();
    }

    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionarClienteLista(@BindingParam("cliente") Cliente valor) {
        System.out.println("cliente seleccionado " + valor.getCliCedula());
        buscarCliente = valor.getCliCedula();
        windowClienteBuscar.detach();

    }

    //busqueda del producto
    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion"})
    public void eliminarRegistros() {
        if (registrosSeleccionados.size() > 0) {
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).removeAll(registrosSeleccionados);
            calcularValoresTotales();

        } else {
            Messagebox.show("Seleccione al menos un registro para eliminar", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel"})
    public void agregarRegistroVacio() {

        DetalleFacturaDAO nuevoRegistro = new DetalleFacturaDAO();
        nuevoRegistro.setProducto(productoBuscado);
        nuevoRegistro.setCantidad(BigDecimal.ZERO);
        nuevoRegistro.setSubTotal(BigDecimal.ZERO);
        nuevoRegistro.setDetIva(BigDecimal.ZERO);
        nuevoRegistro.setDetTotalconiva(BigDecimal.ZERO);
        nuevoRegistro.setDescripcion("");
        nuevoRegistro.setProducto(null);
        ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);

    }

    private void calcularValoresTotales() {
//        BigDecimal factorIva = (valor.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
//        BigDecimal facturIvaMasBase = (factorIva.add(BigDecimal.ONE));
        // BigDecimal descuentoValorFinal = BigDecimal.ZERO;
        BigDecimal valorTotalInicial = BigDecimal.ZERO;
        BigDecimal valorTotal = BigDecimal.ZERO;
//        BigDecimal valorTotal0 = BigDecimal.ZERO;
        BigDecimal valorTotal5 = BigDecimal.ZERO;
        BigDecimal valorTotal13 = BigDecimal.ZERO;
        BigDecimal valorTotal14 = BigDecimal.ZERO;
        BigDecimal valorTotal15 = BigDecimal.ZERO;
//        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal valorTotalConIva = BigDecimal.ZERO;
        BigDecimal valorIva = BigDecimal.ZERO;
        BigDecimal valorIva5 = BigDecimal.ZERO;
        BigDecimal valorIva13 = BigDecimal.ZERO;
        BigDecimal valorIva14 = BigDecimal.ZERO;
        BigDecimal valorIva15 = BigDecimal.ZERO;
        BigDecimal valorDescuento = BigDecimal.ZERO;
        BigDecimal valorDescuentoIvaTotal = BigDecimal.ZERO;
        BigDecimal baseCero = BigDecimal.ZERO;
        BigDecimal sumaSubsidio = BigDecimal.ZERO;
        BigDecimal sumaDeItems = BigDecimal.ZERO;
        BigDecimal totalizado = BigDecimal.ZERO;

        BigDecimal descuentoMasIva = BigDecimal.ZERO;

        List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();
        if (listaPedido.size() > 0) {
            for (DetalleFacturaDAO item : listaPedido) {
                sumaDeItems = sumaDeItems.add(BigDecimal.ONE);
                if (item.getProducto() != null) {
                    totalizado = totalizado.add(item.getDetTotalconivadescuento());
                    System.out.println("totalizado" + totalizado);

                    BigDecimal factIVA = BigDecimal.ZERO;
                    BigDecimal factMASIVA = BigDecimal.ZERO;
                    /*SUMAR TOTALES POR IVA*/
                    Integer comparaIva = item.getProducto().getProdIva().intValue();
                    switch (comparaIva) {
                        case 0:
                            // secuencia de sentencias.
                            baseCero = baseCero.add(!item.getProducto().getProdGrabaIva()
                                    ? item.getSubTotalDescuento().multiply(item.getCantidad())
                                    : BigDecimal.ZERO);
                            descuentoMasIva = item.getDetTotaldescuento();
                            valorDescuentoIvaTotal = valorDescuentoIvaTotal.add(descuentoMasIva);
                            break;
                        case 5:

                            // secuencia de sentencias.
                            valorTotal5 = valorTotal5.add(item.getProducto().getProdGrabaIva()
                                    ? item.getSubTotalDescuento().multiply(item.getCantidad())
                                    : BigDecimal.ZERO);
                            valorIva5 = valorIva5.add(item.getDetIva());
                            System.out.println("valorIva" + valorIva);
                            /*CALCULA EL DECUENTO ICLUIDO IVA*/
                            factIVA = (item.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
                            factMASIVA = (factIVA.add(BigDecimal.ONE));
                            descuentoMasIva = item.getDetTotaldescuento();
//                            descuentoMasIva = item.getDetCantpordescuento().multiply(factMASIVA);
                            valorDescuentoIvaTotal = valorDescuentoIvaTotal.add(descuentoMasIva);
                            break;

                        case 12:
                            // secuencia de sentencias.
                            valorTotal = valorTotal.add(item.getProducto().getProdGrabaIva()
                                    ? item.getSubTotalDescuento().multiply(item.getCantidad())
                                    : BigDecimal.ZERO);

                            valorIva = valorIva.add(item.getDetIva());
                            System.out.println("valorIva" + valorIva);
                            /*CALCULA EL DECUENTO ICLUIDO IVA*/
 /*CALCULA EL DECUENTO ICLUIDO IVA*/
                            factIVA = (item.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
                            factMASIVA = (factIVA.add(BigDecimal.ONE));
                            descuentoMasIva = item.getDetTotaldescuento();
//                            descuentoMasIva = item.getDetCantpordescuento().multiply(factMASIVA);
                            valorDescuentoIvaTotal = valorDescuentoIvaTotal.add(descuentoMasIva);
                            break;
                        case 13:
                            // secuencia de sentencias.
                            valorTotal13 = valorTotal13.add(item.getProducto().getProdGrabaIva()
                                    ? item.getSubTotalDescuento().multiply(item.getCantidad())
                                    : BigDecimal.ZERO);

                            valorIva13 = valorIva13.add(item.getDetIva());
                            System.out.println("valorIva" + valorIva);

                            /*CALCULA EL DECUENTO ICLUIDO IVA*/
                            factIVA = (item.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
                            factMASIVA = (factIVA.add(BigDecimal.ONE));
                            descuentoMasIva = item.getDetTotaldescuento();
//                            descuentoMasIva = item.getDetCantpordescuento().multiply(factMASIVA);
                            valorDescuentoIvaTotal = valorDescuentoIvaTotal.add(descuentoMasIva);
                            break;
                        case 14:
                            // secuencia de sentencias.
                            valorTotal14 = valorTotal14.add(item.getProducto().getProdGrabaIva()
                                    ? item.getSubTotalDescuento().multiply(item.getCantidad())
                                    : BigDecimal.ZERO);

                            valorIva14 = valorIva14.add(item.getDetIva());
                            System.out.println("valorIva" + valorIva);

                            /*CALCULA EL DECUENTO ICLUIDO IVA*/
                            factIVA = (item.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
                            factMASIVA = (factIVA.add(BigDecimal.ONE));
//                            descuentoMasIva = item.getDetCantpordescuento().multiply(factMASIVA);
                            descuentoMasIva = item.getDetTotaldescuento();
                            valorDescuentoIvaTotal = valorDescuentoIvaTotal.add(descuentoMasIva);
                            break;
                        case 15:
                            // secuencia de sentencias.
                            valorTotal15 = valorTotal15.add(item.getProducto().getProdGrabaIva()
                                    ? item.getSubTotalDescuento().multiply(item.getCantidad())
                                    : BigDecimal.ZERO);

                            valorIva15 = valorIva15.add(item.getDetIva());
                            System.out.println("valorIva" + valorIva);

                            /*CALCULA EL DECUENTO ICLUIDO IVA*/
                            factIVA = (item.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
                            factMASIVA = (factIVA.add(BigDecimal.ONE));
                            descuentoMasIva = item.getDetTotaldescuento();
                            valorDescuentoIvaTotal = valorDescuentoIvaTotal.add(descuentoMasIva);
                            break;
                        default:
                        // Default secuencia de sentencias.
                    }

                    System.out.println("valor total" + valorTotal);

                    // valorTotalConIva = valorTotalConIva.add(item.getDetTotalconivadescuento());
                    valorDescuento = valorDescuento.add(item.getDetCantpordescuento());
                    System.out.println("valorDescuento" + valorDescuento);
                    valorTotalInicial = valorTotalInicial.add(item.getTotalInicial().multiply(item.getCantidad()));
                    System.out.println("valorTotalInicial" + valorTotalInicial);

                    /* COSTO SIN SUBSIDIO */
                    System.out.println("baseCero" + baseCero);
                    if (item.getProducto().getProdTieneSubsidio().equals("S")) {
                        BigDecimal precioSinSubporcantidad = item.getProducto().getProdSubsidio()
                                .multiply(item.getCantidad());
                        sumaSubsidio = sumaSubsidio.add(precioSinSubporcantidad.setScale(5, RoundingMode.FLOOR));
                    }

                }
            }

//            totalItems = "ITEMS: " + (sumaDeItems.intValue() - 1);
            System.out.println("**********************************************************");
            System.out.println("valor total:::: subTotalCotizacion " + valorTotal);
            // valorTotal.setScale(5, RoundingMode.UP);
            try {
                subsidioTotal = sumaSubsidio;
                subTotalCotizacion = ArchivoUtils.redondearDecimales(valorTotal, 2);
                subTotalCotizacion5 = ArchivoUtils.redondearDecimales(valorTotal5, 2);
                subTotalCotizacion13 = ArchivoUtils.redondearDecimales(valorTotal13, 2);
                subTotalCotizacion14 = ArchivoUtils.redondearDecimales(valorTotal14, 2);
                subTotalCotizacion15 = ArchivoUtils.redondearDecimales(valorTotal15, 2);
                // subTotalCotizacion.setScale(5, RoundingMode.UP);
                subTotalBaseCero = ArchivoUtils.redondearDecimales(baseCero, 2);
                /* Obtiene el porcentaje del IVA */
                // BigDecimal valorIva = subTotalCotizacion.multiply(valor.getProducto().getProdIva());

                ivaCotizacion = ArchivoUtils.redondearDecimales(valorIva, 2);

                ivaCotizacion5 = ArchivoUtils.redondearDecimales(valorIva5, 2);
                ivaCotizacion13 = ArchivoUtils.redondearDecimales(valorIva13, 2);
                ivaCotizacion14 = ArchivoUtils.redondearDecimales(valorIva14, 2);
                ivaCotizacion15 = ArchivoUtils.redondearDecimales(valorIva15, 2);

                // ivaCotizacion.setScale(5, RoundingMode.UP);
                valorTotalCotizacion = totalizado;
                // valorTotalCotizacion =
                // subTotalCotizacion.add(subTotalBaseCero.add(ivaCotizacion));
                // valorTotalCotizacion.setScale(5, RoundingMode.UP);

                valorTotalInicialVent = valorTotalInicial;
                // valorTotalInicialVent.setScale(5, RoundingMode.UP);

                descuentoValorFinal = valorDescuentoIvaTotal;
                // descuentoValorFinal.setScale(5, RoundingMode.UP);
                totalDescuento = valorDescuento;
                // descuentoValorFinal.setScale(5, RoundingMode.UP);

                subTotalCotizacion = ArchivoUtils.redondearDecimales(subTotalCotizacion, 2);
                subTotalBaseCero = ArchivoUtils.redondearDecimales(subTotalBaseCero, 2);
                valorTotalCotizacion = ArchivoUtils.redondearDecimales(valorTotalCotizacion, 2);
                valorTotalInicialVent = ArchivoUtils.redondearDecimales(valorTotalInicialVent, 2);
                ivaCotizacion = ArchivoUtils.redondearDecimales(ivaCotizacion, 2);
                descuentoValorFinal = ArchivoUtils.redondearDecimales(descuentoValorFinal, 2);

            } catch (Exception e) {
                System.out.println("error de calculo de valores " + e);
            }

        }
    }
//    }

    private void numeroFactura() {
        NotaCreditoDebito recuperada = servicioNotaCredito.FindUltimaNotaCreditoDebito();
        if (recuperada != null) {
            // System.out.println("numero de factura " + recuperada);
            numeroFactura = recuperada.getFacNumero() + 1;
            numeroFacturaTexto();
        } else {
            numeroFactura = 1;
            numeroFacturaText = "000000001";
        }
    }

    private void numeroFacturaTexto() {
        numeroFacturaText = "";
        for (int i = numeroFactura.toString().length(); i < 9; i++) {
            numeroFacturaText = numeroFacturaText + "0";
        }
        numeroFacturaText = numeroFacturaText + numeroFactura;
        System.out.println("nuemro texto " + numeroFacturaText);
    }

    private void guardarNotaCredito() {

        try {

            if (motivo.equals("")) {
                Clients.showNotification("Ingrese un motivo de la nota de credito",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000, true);
                return;
            }
            //armar el detalle de la factura
            List<DetalleFacturaDAO> detalleFactura = new ArrayList<DetalleFacturaDAO>();
            List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();
//            if (listaPedido.size() > 0) {
            if (registrosSeleccionados.size() > 0) {
                for (DetalleFacturaDAO registrosSeleccionado : registrosSeleccionados) {
                    if (registrosSeleccionado.getProducto() != null) {
                        detalleFactura.add(registrosSeleccionado);
                    }
                }
            } else {
                for (DetalleFacturaDAO item : listaPedido) {
                    if (item.getProducto() != null) {
                        detalleFactura.add(item);
                    }

                }
            }
            //genera el numero de retencion al momento de guardar
            numeroFactura();
            NotaCreditoDebito creditoDebito = new NotaCreditoDebito();
            creditoDebito.setCodTipoambiente(factura.getCod_tipoambiente().getCodTipoambiente());
            creditoDebito.setCodestablecimiento(factura.getCodestablecimiento());
//            creditoDebito.setPuntoemision(motivo);
            creditoDebito.setCodigoPorcentaje(factura.getCodigoPorcentaje());
            creditoDebito.setFacAbono(BigDecimal.ZERO);
            creditoDebito.setFacCodIce(factura.getFacCodIce());
            creditoDebito.setFacCodIva(factura.getFacCodIva());
            creditoDebito.setFacDescripcion(factura.getFacDescripcion());
            creditoDebito.setFacDescuento(totalDescuento);
            //cuando solo se emite pero no se procesa para pagos de alguna factura
            creditoDebito.setFacEstado("EM");
            //fecha de e mision de la nota de credito
            creditoDebito.setFacFecha(new Date());
            creditoDebito.setFacFechaSustento(factura.getFacFecha());
            creditoDebito.setFacIva(ivaCotizacion);
            creditoDebito.setFacMoneda("DOLAR");
            creditoDebito.setFacNumero(numeroFactura);
            creditoDebito.setFacNumeroText(numeroFacturaText);
            creditoDebito.setFacPlazo(BigDecimal.ZERO);
            creditoDebito.setFacPorcentajeIva(factura.getFacPorcentajeIva());
            creditoDebito.setFacSaldo(BigDecimal.ZERO);
            creditoDebito.setFacSubtotal(subTotalCotizacion.add(subTotalBaseCero).add(subTotalCotizacion15).add(subTotalCotizacion5));
            creditoDebito.setFacTipo("NCRE");
            creditoDebito.setEstadosri("PENDIENTE");
            creditoDebito.setFacTotal(valorTotalCotizacion);
            creditoDebito.setFacTotalBaseCero(subTotalBaseCero);
            creditoDebito.setFacTotalBaseGravaba(subTotalCotizacion);
            creditoDebito.setFacSubt15(subTotalCotizacion15);
            creditoDebito.setFacSubt5(subTotalCotizacion5);
            creditoDebito.setFacIva15(ivaCotizacion15);
            creditoDebito.setFacIva5(ivaCotizacion5);
            creditoDebito.setFacUnidadTiempo(formaPagoSelected.getUnidadTiempo());
            creditoDebito.setIdFactura(factura);
            creditoDebito.setIdUsuario(credential.getUsuarioSistema());
            creditoDebito.setPuntoemision(amb.getAmPtoemi());
            creditoDebito.setTipodocumento("04");
            creditoDebito.setTipodocumentomod("01");
            creditoDebito.setMotivo(motivo);

            servicioNotaCredito.guardarNotaCreditoDebito(detalleFactura, creditoDebito);
            
            if (true) {
                /* INGRESAMOS LO MOVIMIENTOS AL KARDEX */
                Kardex kardex = null;
                DetalleKardex detalleKardex = null;
                Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla("ING");
                for (DetalleFacturaDAO item : listaPedido) {
                    if (item.getProducto() != null) {
                        if (!item.getProducto().getProdEsreceta()) {

                            if (servicioKardex.FindALlKardexs(item.getProducto()) == null) {
                                kardex = new Kardex();
                                kardex.setIdProducto(item.getProducto());
                                kardex.setKarDetalle("Inicio de inventario desde la facturacion para el producto: "
                                        + item.getProducto().getProdNombre());
                                kardex.setKarFecha(new Date());
                                kardex.setKarFechaKardex(new Date());
                                kardex.setKarTotal(BigDecimal.ZERO);
                                servicioKardex.crear(kardex);
                            }
                            detalleKardex = new DetalleKardex();
                            kardex = servicioKardex.FindALlKardexs(item.getProducto());
                            detalleKardex.setIdKardex(kardex);
                            detalleKardex.setDetkFechakardex(new Date());
                            detalleKardex.setDetkFechacreacion(new Date());
                            detalleKardex.setIdTipokardex(tipokardex);
                            detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                            detalleKardex.setDetkDetalles("Aumenta al kardex con NC"
                                    + "-" + numeroFactura);
                            detalleKardex.setIdFactura(factura);
                            detalleKardex.setDetkCantidad(item.getCantidad());
                            servicioDetalleKardex.crear(detalleKardex);
//                            BigDecimal total = kardex.getKarTotal();
//                            total = total.subtract(item.getCantidad());
//                            kardex.setKarTotal(total);
//                            servicioKardex.modificar(kardex);

                            TotalKardex totales = servicioKardex.totalesForKardex(kardex);
                            BigDecimal total = totales.getTotalKardex();
                            kardex.setKarTotal(total);
                            servicioKardex.modificar(kardex);

                        } else {
                            List<ComboProducto> lislaRecup = servicioComboProducto.findForProducto(item.getProducto());
                            for (ComboProducto comboProducto : lislaRecup) {
                                if (servicioKardex.FindALlKardexs(comboProducto.getIdProducto()) == null) {
                                    kardex = new Kardex();
                                    kardex.setIdProducto(comboProducto.getIdProducto());
                                    kardex.setKarDetalle("Inicio de inventario desde la facturacion para el producto: "
                                            + item.getProducto().getProdNombre());
                                    kardex.setKarFecha(new Date());
                                    kardex.setKarFechaKardex(new Date());
                                    kardex.setKarTotal(BigDecimal.ZERO);
                                    servicioKardex.crear(kardex);
                                }
                                detalleKardex = new DetalleKardex();
                                kardex = servicioKardex.FindALlKardexs(comboProducto.getIdProducto());
                                detalleKardex.setIdKardex(kardex);
                                detalleKardex.setDetkFechakardex(new Date());
                                detalleKardex.setDetkFechacreacion(new Date());
                                detalleKardex.setIdTipokardex(tipokardex);
                                detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                                detalleKardex.setDetkDetalles("Aumenta kardex con NC-"
                                        + numeroFactura);
                                detalleKardex.setIdFactura(factura);

                                /* calcular la cantidad a descontar del Kardex */
                                BigDecimal cantidadDescuento = comboProducto.getComCantidad()
                                        .multiply(item.getCantidad());
                                detalleKardex.setDetkCantidad(cantidadDescuento);
                                servicioDetalleKardex.crear(detalleKardex);
                                /* ACTUALIZA EL TOTAL DEL KARDEX */
                                TotalKardex totales = servicioKardex.totalesForKardex(kardex);
                                BigDecimal total = totales.getTotalKardex();
                                kardex.setKarTotal(total);
                                servicioKardex.modificar(kardex);
                            }
                        }
                    }
                }

            }

            reporteGeneral();
            if (accion.equals("create")) {
                Executions.sendRedirect("/venta/facturar.zul");
            } else {
                Executions.sendRedirect("/venta/listafacturas.zul");
            }

//            }
//            Messagebox.show("Factura registrada correctamente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
        } catch (IOException e) {
            System.out.println("ERROR FACTURA " + e.getMessage());
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR FACTURA " + e.getMessage());
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        } catch (IllegalAccessException e) {
            System.out.println("ERROR FACTURA " + e.getMessage());
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        } catch (InstantiationException e) {
            System.out.println("ERROR FACTURA " + e.getMessage());
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        } catch (NumberFormatException e) {
            System.out.println("ERROR FACTURA " + e.getMessage());
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        } catch (SQLException e) {
            System.out.println("ERROR FACTURA " + e.getMessage());
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        } catch (NamingException e) {
            System.out.println("ERROR FACTURA " + e.getMessage());
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        } catch (JRException e) {
            System.out.println("ERROR FACTURA " + e.getMessage());
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion"})
    public void Guardar() {
        System.out.println("formaPagoSelected " + formaPagoSelected);

        if (!clienteBuscado.getCliCedula().equals("") && formaPagoSelected != null) {
            if (listaDetalleFacturaDAOMOdel.size() > 0) {
                if (!listaDetalleFacturaDAOMOdel.get(0).getDescripcion().equals("")) {
                    guardarNotaCredito();
                } else {
                    Messagebox.show("Registre un producto para proceder con la nota de credito", "Atención", Messagebox.OK, Messagebox.ERROR);
                }

            } else {
                Messagebox.show("Registre un producto para proceder con la nota de credito", "Atención", Messagebox.OK, Messagebox.ERROR);
            }

        } else {
            Messagebox.show("Verifique el cliente y la forma de pago", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    @Command
    @NotifyChange({"listaProducto", "buscarNombreProd"})
    public void buscarLikeNombreProd() {

        findProductoLikeNombre();
    }

    private void findProductoLikeNombre() {
        listaProducto = servicioProducto.findLikeProdNombre(buscarNombreProd);
    }

    public void reporteGeneral() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();

        try {
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);
            if (!tipoVenta.equals("SINF")) {

                //  con = emf.unwrap(Connection.class);
                String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                        .getRealPath("/reportes");
                String reportPath = "";
//                con = ConexionReportes.Conexion.conexion();

//                    reportPath = reportFile + File.separator + "puntoventa.jasper";
                reportPath = reportFile + File.separator + "notacr.jasper";

                Map<String, Object> parametros = new HashMap<String, Object>();

                //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
                parametros.put("numfactura", numeroFactura);
                parametros.put("tipoambiente", amb.getCodTipoambiente());

                if (con != null) {
                    System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                }
                FileInputStream is = null;
                is = new FileInputStream(reportPath);

                byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
                InputStream mediais = new ByteArrayInputStream(buf);
                AMedia amedia = new AMedia("Reporte", "pdf", "application/pdf", mediais);
                fileContent = amedia;
                final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
                map.put("pdf", fileContent);
                org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/venta/contenedorReporte.zul", null, map);
                window.doModal();
                con.close();
            }
        } catch (Exception e) {
            System.out.println("Error en generar el reporte " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.close();
                System.out.println("cerro entity");
            }
        }

    }

    public Integer getNumeroProforma() {
        return numeroProforma;
    }

    public void setNumeroProforma(Integer numeroProforma) {
        this.numeroProforma = numeroProforma;
    }

    public List<FormaPago> getListaFormaPago() {
        return listaFormaPago;
    }

    public void setListaFormaPago(List<FormaPago> listaFormaPago) {
        this.listaFormaPago = listaFormaPago;
    }

    public FormaPago getFormaPagoSelected() {
        return formaPagoSelected;
    }

    public void setFormaPagoSelected(FormaPago formaPagoSelected) {
        this.formaPagoSelected = formaPagoSelected;
    }

    public Integer getIdFactuta() {
        return idFactuta;
    }

    public void setIdFactuta(Integer idFactuta) {
        this.idFactuta = idFactuta;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public String getClietipo() {
        return clietipo;
    }

    public void setClietipo(String clietipo) {
        this.clietipo = clietipo;
    }

    public static String getPATH_BASE() {
        return PATH_BASE;
    }

    public static void setPATH_BASE(String PATH_BASE) {
        NotaCreditoDebitoVm.PATH_BASE = PATH_BASE;
    }

    public Tipoambiente getAmb() {
        return amb;
    }

    public void setAmb(Tipoambiente amb) {
        this.amb = amb;
    }

    public BigDecimal getSubsidioTotal() {
        return subsidioTotal;
    }

    public void setSubsidioTotal(BigDecimal subsidioTotal) {
        this.subsidioTotal = subsidioTotal;
    }

    public BigDecimal getSubTotalBaseCero() {
        return subTotalBaseCero;
    }

    public void setSubTotalBaseCero(BigDecimal subTotalBaseCero) {
        this.subTotalBaseCero = subTotalBaseCero;
    }

    public String getNumeroFacturaText() {
        return numeroFacturaText;
    }

    public void setNumeroFacturaText(String numeroFacturaText) {
        this.numeroFacturaText = numeroFacturaText;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public BigDecimal getSubTotalCotizacion5() {
        return subTotalCotizacion5;
    }

    public void setSubTotalCotizacion5(BigDecimal subTotalCotizacion5) {
        this.subTotalCotizacion5 = subTotalCotizacion5;
    }

    public BigDecimal getSubTotalCotizacion12() {
        return subTotalCotizacion12;
    }

    public void setSubTotalCotizacion12(BigDecimal subTotalCotizacion12) {
        this.subTotalCotizacion12 = subTotalCotizacion12;
    }

    public BigDecimal getSubTotalCotizacion13() {
        return subTotalCotizacion13;
    }

    public void setSubTotalCotizacion13(BigDecimal subTotalCotizacion13) {
        this.subTotalCotizacion13 = subTotalCotizacion13;
    }

    public BigDecimal getSubTotalCotizacion14() {
        return subTotalCotizacion14;
    }

    public void setSubTotalCotizacion14(BigDecimal subTotalCotizacion14) {
        this.subTotalCotizacion14 = subTotalCotizacion14;
    }

    public BigDecimal getSubTotalCotizacion15() {
        return subTotalCotizacion15;
    }

    public void setSubTotalCotizacion15(BigDecimal subTotalCotizacion15) {
        this.subTotalCotizacion15 = subTotalCotizacion15;
    }

    public BigDecimal getIvaCotizacion5() {
        return ivaCotizacion5;
    }

    public void setIvaCotizacion5(BigDecimal ivaCotizacion5) {
        this.ivaCotizacion5 = ivaCotizacion5;
    }

    public BigDecimal getIvaCotizacion13() {
        return ivaCotizacion13;
    }

    public void setIvaCotizacion13(BigDecimal ivaCotizacion13) {
        this.ivaCotizacion13 = ivaCotizacion13;
    }

    public BigDecimal getIvaCotizacion14() {
        return ivaCotizacion14;
    }

    public void setIvaCotizacion14(BigDecimal ivaCotizacion14) {
        this.ivaCotizacion14 = ivaCotizacion14;
    }

    public BigDecimal getIvaCotizacion15() {
        return ivaCotizacion15;
    }

    public void setIvaCotizacion15(BigDecimal ivaCotizacion15) {
        this.ivaCotizacion15 = ivaCotizacion15;
    }

}
