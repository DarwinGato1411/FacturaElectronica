/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.CierreCaja;
import com.ec.entidad.Cliente;
import com.ec.entidad.DetalleFactura;
import com.ec.entidad.DetalleGuiaremision;
import com.ec.entidad.DetalleKardex;
import com.ec.entidad.DetallePago;
import com.ec.entidad.Factura;
import com.ec.entidad.FormaPago;
import com.ec.entidad.Guiaremision;
import com.ec.entidad.Kardex;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Producto;
import com.ec.entidad.Referencia;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.Tipocomprobante;
import com.ec.entidad.Tipokardex;
import com.ec.entidad.Transportista;
import com.ec.entidad.Usuario;
import com.ec.seguridad.AutentificadorLogeo;
import com.ec.seguridad.AutentificadorService;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioCierreCaja;
import com.ec.servicio.ServicioCliente;
import com.ec.servicio.ServicioDetalleFactura;
import com.ec.servicio.ServicioDetalleGuia;
import com.ec.servicio.ServicioDetalleKardex;
import com.ec.servicio.ServicioDetallePago;
import com.ec.servicio.ServicioEstadoFactura;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioFormaPago;
import com.ec.servicio.ServicioGuia;
import com.ec.servicio.ServicioKardex;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioReferencia;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.servicio.ServicioTipoKardex;
import com.ec.servicio.ServicioTransportista;
import com.ec.servicio.ServicioUsuario;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.ParamFactura;
import com.ec.untilitario.TotalKardex;
import com.ec.untilitario.UtilitarioAutorizarSRI;
import com.ec.untilitario.Verificaciones;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
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
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class Facturar extends SelectorComposer<Component> {

//    @Wire
//    Window windowNotaEntrega;
    @Wire
    Window windowClienteBuscar;
    @Wire
    Textbox txtBuscarNombre;
    @Wire
    Window windowProductoBuscar;
    @Wire
    Textbox idBusquedaProd;
    @Wire
    Window windowModCotizacionFact;
    @Wire
    Window windowCambioPrecio;

    @Wire
    Window windowValidaBorra;
    @Wire
    Listbox lstFacturar;

    /*DETALLE DEL KARDEX Y DETALLE KARDEX*/
    ServicioKardex servicioKardex = new ServicioKardex();
    ServicioDetalleKardex servicioDetalleKardex = new ServicioDetalleKardex();
    ServicioTipoKardex servicioTipoKardex = new ServicioTipoKardex();
    ServicioDetalleFactura servicioDetalleFactura = new ServicioDetalleFactura();
    //buscar cliente
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    ServicioFormaPago servicioFormaPago = new ServicioFormaPago();
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    ServicioCliente servicioCliente = new ServicioCliente();
    ServicioEstadoFactura servicioEstadoFactura = new ServicioEstadoFactura();
    ServicioFactura servicioFactura = new ServicioFactura();
    ServicioTransportista servicioTransportista = new ServicioTransportista();

    /*SERVICIOS DE LA GUIA*/
    ServicioGuia servicioGuia = new ServicioGuia();
    ServicioDetalleGuia servicioDetalleGuia = new ServicioDetalleGuia();
    List<Transportista> listaTransportistas = new ArrayList<Transportista>();
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
    private BigDecimal valorTotalInicialVent = BigDecimal.ZERO;
    private BigDecimal descuentoValorFinal = BigDecimal.ZERO;
    private BigDecimal subTotalCotizacion = BigDecimal.ZERO;
    private BigDecimal subTotalBaseCero = BigDecimal.ZERO;
    private BigDecimal ivaCotizacion = BigDecimal.ZERO;
    private BigDecimal totalDescuento = BigDecimal.ZERO;
    //Cabecera de la factura
    private String estdoFactura = "PA";
    private String tipoVentaAnterior = "FACT";
    private String tipoVenta = "FACT";
    private String facturaDescripcion = "";
    private Integer numeroFactura = 0;
    private String numeroFacturaText = "";

    /*GUIA DE REMISION*/
    private Integer numeroGuia = 0;
    private String numeroGuiaText = "";
    private Transportista transportista = null;
    private String numeroPlaca = "";
    private Date incioTraslado = new Date();
    private Date finTraslado = new Date();
    private String motivoGuia = "";
    private String partida = "";
    private String llegada = "";

    private Integer numeroProforma = 0;
    private Date fechafacturacion = new Date();
    private Date facFechaCobro = new Date();
    private static BigDecimal DESCUENTOGENERAL = BigDecimal.valueOf(5.0);
    //usuario que factura
    UserCredential credential = new UserCredential();
    private Parametrizar parametrizar = null;
//reporte
    AMedia fileContent = null;
    Connection con = null;
    //cambio
    private BigDecimal cobro = BigDecimal.ZERO;
    private BigDecimal cambio = BigDecimal.ZERO;
    private BigDecimal saldoFacturas = BigDecimal.ZERO;
    private BigDecimal subsidioTotal = BigDecimal.ZERO;


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

    private List<Producto> listaProductoCmb = new ArrayList<Producto>();
    private String codigo = "";

    private Boolean descargarKardex = Boolean.TRUE;
    /*GESTIONA NOTAS DE ENTREGA*/
    //crear un factura nueva        
    private ListModelList<Factura> listaNotaEntregaModel;
    private List<Factura> listalistaNotaEntregaDatos = new ArrayList<Factura>();
    public static Set<Factura> seleccionNotaEntrega = new HashSet<Factura>();

    /*RUTAS PARA LOS ARCHIVPOS XML SRI*/
    private static String PATH_BASE = "";
    private Tipoambiente amb = new Tipoambiente();

    /*si es con o sin guia*/
    private String facConSinGuia = "";
    private String facplazo = "30";

    /*CUENTA LOS ITEMS DE LA FACTURA*/
    private String totalItems = "";
    private List<Kardex> listaKardexProducto = new ArrayList<Kardex>();
    /*para apertura de caja*/
    ServicioCierreCaja servicioCierreCaja = new ServicioCierreCaja();
    AutentificadorService authService = new AutentificadorLogeo();

    /*servicio para eliminar producto */
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    public static Boolean validaBorrado = Boolean.FALSE;
    private String usuLoginVal = "";
    private String usuPasswordVal = "";

    /*cambio de precio*/
    public static String TIPOPRECIO = "NORMAL";
    public Producto PRODUCTOCAMBIO = null;

    private String facPlaca;

    private String facMarca;

    private Integer facAnio;

    private String facCilindraje;

    private String facKilometraje;

    private String facChasis;
    /*Floricola*/
    private String facMadre;
    private String facHija;
    private String facDestino;

    ServicioReferencia servicioReferencia = new ServicioReferencia();
    private List<Referencia> listaReferencia = new ArrayList();
    private Referencia referenciaSelected;

    ServicioDetallePago servicioDetallePago = new ServicioDetallePago();
    Verificaciones verificaciones = new Verificaciones();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") ParamFactura valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        if (valor == null) {

            accion = "create";
            fechafacturacion = new Date();
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).clear();
            verificarSecNumeracion();
            clienteBuscado = servicioCliente.findClienteLikeCedula("9999999999999");

            List<Factura> listaFacturasPendientes = servicioFactura.findEstadoCliente("PE", clienteBuscado);
            saldoFacturas = BigDecimal.ZERO;
        } else if (valor.getBusqueda().equals("producto") || valor.getBusqueda().equals("cliente")) {

        } else if (valor.getBusqueda().equals("cambio")) {
            PRODUCTOCAMBIO = servicioProducto.findByProdCodigo(valor.getCodigo());
        } else if (valor.getBusqueda().equals("nte")) {
            cargaNotaEntrega();
        } else {

            accion = "update";
            idFactuta = Integer.valueOf(valor.getIdFactura());
            tipoVentaAnterior = valor.getTipoDoc();
            tipoVenta = valor.getTipoDoc();
            System.out.println("tipoVenta " + tipoVenta);
            System.out.println("idFactuta " + idFactuta);
            recuperFactura();

        }
        parametrizar = servicioParametrizar.FindALlParametrizar();
        DESCUENTOGENERAL = parametrizar.getParDescuentoGeneral();
        validaBorrado = parametrizar.getParBorraItemsFac();

        FindClienteLikeNombre();
        findKardexProductoLikeNombre();
        //para establecer el cliente final

        agregarRegistroVacio();
        buscarCliente = clienteBuscado.getCliCedula();
        llegada = clienteBuscado.getCliDireccion();
        listaTransportistas = servicioTransportista.findTransportista("");
        listaReferencia = servicioReferencia.findAll();
    }
//<editor-fold defaultstate="collapsed" desc="Facturar">

    @Command
    public void aperturaCaja() {

//        if (!verificaciones.verificarNumeroDocumentos()) {
//
//            Messagebox.show("Usted cuenta con un plan basico y sobre paso el limite de  documentos ¡Contactese con el administrador!", "Atención", Messagebox.OK, Messagebox.EXCLAMATION);
//            authService.logout();
//            Executions.sendRedirect("/");
//
//        }
//        if (servicioCierreCaja.findALlCierreCajaForFechaIdUsuario(new Date(), credential.getUsuarioSistema()).isEmpty()
//                && credential.getUsuarioSistema().getUsuNivel() != 1) {
        if (servicioCierreCaja.findALlCierreCajaForFechaIdUsuario(new Date(), credential.getUsuarioSistema()).isEmpty()) {
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/nuevo/aperturacaja.zul", null, null);
            window.doModal();
            if (servicioCierreCaja.findALlCierreCajaForFechaIdUsuario(new Date(), credential.getUsuarioSistema()).isEmpty()) {
                authService.logout();
                Executions.sendRedirect("/");
            }
        } else {
            if (servicioCierreCaja.findALlCierreCajaForFechaIdUsuario(new Date(), credential.getUsuarioSistema()).isEmpty()) {
                CierreCaja cierreCaja = new CierreCaja();
                cierreCaja.setCieValorInicio(BigDecimal.ZERO);
                cierreCaja.setCieCuadre(BigDecimal.ZERO);
                cierreCaja.setCieDiferencia(BigDecimal.ZERO);
                cierreCaja.setCieValor(BigDecimal.ZERO);
                cierreCaja.setCieFecha(new Date());
                cierreCaja.setIdUsuario(credential.getUsuarioSistema());
                servicioCierreCaja.crear(cierreCaja);
            }

        }

    }

    public Facturar() {

        Session sess = Sessions.getCurrent();
        //sess.setMaxInactiveInterval(10000);
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;

        getDetallefactura();
        parametrizar = servicioParametrizar.FindALlParametrizar();
        listaFormaPago = servicioFormaPago.FindALlFormaPago();
        formaPagoSelected = servicioFormaPago.finPrincipal();
        if (accion.equals("create")) {
            numeroFactura();

        } else {

        }
        amb = servicioTipoAmbiente.FindALlTipoambiente();
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                    + amb.getAmDirXml();
        partida = amb.getAmDireccionMatriz();

    }
// </editor-fold>

    private void recuperFactura() {

        if (tipoVenta.equals("FACT")) {
            factura = servicioFactura.findFirIdFact(idFactuta);
            facConSinGuia = factura.getFaConSinGuia();
            facplazo = factura.getFacPlazo() == null ? "30" : factura.getFacPlazo().setScale(0).toString();
        } else {
            factura = servicioFactura.findByIdCotizacion(idFactuta);
            facConSinGuia = "SG";
        }

        if (tipoVenta.equals("NTV")) {
            factura = servicioFactura.findFirIdFactNTV(idFactuta);
        }
        clienteBuscado = factura.getIdCliente();
        /*RECUPERA EL SALDO DE CREDITO*/
        List<Factura> listaFacturasPendientes = servicioFactura.findEstadoCliente("PE", clienteBuscado);
        saldoFacturas = BigDecimal.ZERO;
//        BigDecimal sumaPendientes = BigDecimal.ZERO;
//        for (Factura listaFacturasPendiente : listaFacturasPendientes) {
//
//            sumaPendientes = sumaPendientes.add(listaFacturasPendiente.getFacSaldoAmortizado());
//        }
//        saldoFacturas = clienteBuscado.getCliMontoAsignado().subtract(sumaPendientes);
//        saldoFacturas.setScale(2, RoundingMode.FLOOR);
        /*FIN DEL SALDO DE FACTURAS CON ELLO VEMOS CUANTO CREDITO TIENE EL CLIENTE*/
        if (tipoVenta.equals("FACT") && accion.equals("update")) {
            numeroFactura = factura.getFacNumero();
        } else if (tipoVenta.equals("PROF") && accion.equals("update")) {
            numeroFactura = factura.getFacNumProforma();
        } else if (tipoVenta.equals("NTE") && accion.equals("update")) {
            numeroFactura = factura.getFacNumNotaEntrega();
        } else if (tipoVenta.equals("NTV") && accion.equals("update")) {
            numeroFactura = factura.getFacNumNotaVenta();
        }
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
            nuevoRegistro.setDetIva(det.getDetIva());
            nuevoRegistro.setDetTotalconiva(det.getDetTotalconiva());
            nuevoRegistro.setTipoVenta(det.getDetTipoVenta());
            //valores con descuentos
            nuevoRegistro.setSubTotalDescuento(det.getDetSubtotaldescuento());
            nuevoRegistro.setDetTotaldescuento(det.getDetTotaldescuento());
            nuevoRegistro.setDetPordescuento(det.getDetPordescuento());
            nuevoRegistro.setDetValdescuento(det.getDetValdescuento());
            nuevoRegistro.setDetTotalconivadescuento(det.getDetTotaldescuentoiva());
            nuevoRegistro.setDetCantpordescuento(det.getDetCantpordescuento());
            nuevoRegistro.setDetIvaDesc(det.getDetIva());
            nuevoRegistro.setCodTipoVenta(det.getDetCodTipoVenta());
            nuevoRegistro.setDetSubtotaldescuentoporcantidad(det.getDetSubtotaldescuentoporcantidad());
            nuevoRegistro.setTotalInicial(det.getDetTotal());
            nuevoRegistro.setEsProducto(det.getIdProducto().getProdEsproducto());
            clietipo = det.getDetCodTipoVenta();
//            calcularValores(nuevoRegistro);
            listaDetalleFacturaDAODatos.add(nuevoRegistro);
        }

        getDetallefactura();
        calcularValoresTotales();
    }

    public void ultimaPagina() {
        Integer numPage = lstFacturar.getPageCount();
        System.out.println("numeroPagina " + numPage);

        lstFacturar.setActivePage(numPage - 1);
    }

    @Command
    @NotifyChange({"numeroFactura"})
    public void calcularNumeroFactTexto() {
        numeroFacturaTexto();
    }

    private void numeroFacturaTexto() {
        numeroFacturaText = "";
        for (int i = numeroFactura.toString().length(); i < 9; i++) {
            numeroFacturaText = numeroFacturaText + "0";
        }
        numeroFacturaText = numeroFacturaText + numeroFactura;
        System.out.println("nuemro texto " + numeroFacturaText);
    }

    private void numeroFactura() {
        Factura recuperada = servicioFactura.FindUltimaFactura();
        if (recuperada != null) {
            // System.out.println("numero de factura " + recuperada);
            numeroFactura = recuperada.getFacNumero() + 1;
            numeroFacturaTexto();
        } else {
            numeroFactura = 1;
            numeroFacturaText = "000000001";
        }
    }
//numero de guia

    private void numeroGuia() {
        Guiaremision recuperada = servicioGuia.findUltimaGuiaremision();
        if (recuperada != null) {
            // System.out.println("numero de factura " + recuperada);
            numeroGuia = recuperada.getFacNumero() + 1;
            numeroTexto(numeroGuia);
        } else {
            numeroGuia = 1;
            numeroGuiaText = "000000001";
        }
    }

    private void numeroTexto(Integer valor) {
        numeroGuiaText = "";
        for (int i = valor.toString().length(); i < 9; i++) {
            numeroGuiaText = numeroGuiaText + "0";
        }
        numeroGuiaText = numeroGuiaText + valor;
        System.out.println("numero texto guia  " + numeroGuiaText);
        //  return numeroGuiaText;
    }

    private void numeroProforma() {
        Factura recuperada = servicioFactura.FindUltimaProforma();
        if (recuperada != null) {
            System.out.println("numero de factura PROOOO " + recuperada);
            numeroFactura = recuperada.getFacNumProforma() + 1;
            System.out.println("numeroFactura PROF " + numeroFactura);
        } else {
            numeroFactura = 1;
        }
    }

    private void numeroNotaEntrega() {
        Factura recuperada = servicioFactura.findUltimaNotaEnt();
        if (recuperada != null) {
            // System.out.println("numero de factura " + recuperada);
            numeroFactura = recuperada.getFacNumNotaEntrega() + 1;
        } else {
            numeroFactura = 1;
        }
    }

    private void numeroNotaVenta() {
        Factura recuperada = servicioFactura.findUltimaNotaVent();
        if (recuperada != null) {
            // System.out.println("numero de factura " + recuperada);
            numeroFactura = recuperada.getFacNumNotaVenta() + 1;
        } else {
            numeroFactura = 1;
        }
    }

    private void verificarSecNumeracion() {

        if (tipoVenta.equals("FACT")) {
            numeroFactura();
        } else if (tipoVenta.equals("PROF")) {
            numeroProforma();
        } else if (tipoVenta.equals("NTE")) {
            numeroNotaEntrega();
        } else if (tipoVenta.equals("NTV")) {
            numeroNotaVenta();
        } else {

            System.out.println("cliente  " + clienteBuscado);
            numeroFactura = 0;

        }

    }

    /*AGREGAMOS DESDE LA LSITA */
    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "buscarNombreProd", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero", "listaProducto", "totalItems"})
    public void agregarItemLista(@BindingParam("valor") Producto producto) {

        if (parametrizar.getParNumRegistrosFactura().intValue() <= listaDetalleFacturaDAOMOdel.size()) {
            Clients.showNotification("Numero de registros permitidos imprima y genere otra factura",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 5000, true);
            return;
        }
        /*calcula con el iva para todo el 12%*/
//        BigDecimal factorIva = (parametrizar.getParIva().divide(BigDecimal.valueOf(100.0)));
/*calcula con el iva para todo el 12%*/
        BigDecimal factorIva = (producto.getProdIva().divide(BigDecimal.valueOf(100.0)));
        BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));

        List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();

        for (DetalleFacturaDAO item : listaPedido) {
            if (item.getProducto() == null) {
                ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).remove(item);
                break;
            }
        }
        productoBuscado = producto;
        if (parametrizar.getParActivaKardex() && producto.getProdEsproducto()) {
            Kardex kardex = servicioKardex.FindALlKardexs(productoBuscado);
            if (kardex.getKarTotal().intValue() < 1) {
                Clients.showNotification("Verifique el stock del producto cuenta con " + kardex.getKarTotal().intValue() + " en estock",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
                agregarRegistroVacio();
                return;
            }
        }

        System.out.println("cliente panel  " + clienteBuscado);
        if (productoBuscado != null) {
            DetalleFacturaDAO valor = new DetalleFacturaDAO();
            valor.setCantidad(BigDecimal.ONE);
            valor.setProducto(productoBuscado);
            valor.setDescripcion(productoBuscado.getProdNombre());
            valor.setDetPordescuento(DESCUENTOGENERAL);
            valor.setCodigo(productoBuscado.getProdCodigo());
            valor.setEsProducto(producto.getProdEsproducto());

            BigDecimal costVentaTipoCliente = BigDecimal.ZERO;
            BigDecimal costVentaTipoClienteInicial = BigDecimal.ZERO;
            String tipoVenta = "NORMAL";
            if (clienteBuscado.getClietipo() == 0) {
                tipoVenta = "NORMAL";
                if (clietipo.equals("0")) {
                    costVentaTipoClienteInicial = productoBuscado.getPordCostoVentaFinal();
                    costVentaTipoCliente = productoBuscado.getPordCostoVentaFinal();
                } else if (clietipo.equals("1")) {
                    tipoVenta = "PREFERENCIAL 1";
                    costVentaTipoClienteInicial = productoBuscado.getProdCostoPreferencial();
                    costVentaTipoCliente = productoBuscado.getProdCostoPreferencial();
                } else if (clietipo.equals("2")) {
                    tipoVenta = "PREFERENCIAL 2";
                    costVentaTipoClienteInicial = productoBuscado.getProdCostoPreferencialDos();
                    costVentaTipoCliente = productoBuscado.getProdCostoPreferencialDos();
                }

                valor.setTotalInicial(ArchivoUtils.redondearDecimales(costVentaTipoClienteInicial, 6));
                BigDecimal porcentajeDesc = valor.getDetPordescuento().divide(BigDecimal.valueOf(100.0), 6, RoundingMode.FLOOR);
                BigDecimal valorDescuentoIva = costVentaTipoCliente.multiply(porcentajeDesc).setScale(6, RoundingMode.FLOOR);;
                //valor unitario con descuento ioncluido iva
                BigDecimal valorTotalIvaDesc = costVentaTipoCliente.subtract(valorDescuentoIva).setScale(6, RoundingMode.FLOOR);
                //valor unit sin iva sin descuento
                BigDecimal subTotal = costVentaTipoCliente.divide(factorSacarSubtotal, 6, RoundingMode.FLOOR);
                valor.setSubTotal(subTotal);
                //valor unitario sin iva con descuento
                BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 6, RoundingMode.FLOOR);
                valor.setSubTotalDescuento(subTotalDescuento);
                //valor del descuento
                BigDecimal valorDescuento = valor.getSubTotal().subtract(valor.getSubTotalDescuento()).setScale(6, RoundingMode.FLOOR);
                valor.setDetValdescuento(valorDescuento);
                BigDecimal valorIva = subTotal.multiply(factorIva).multiply(valor.getCantidad());
//                valor.setDetIva(valorIva);
                //valor del iva con descuento
                BigDecimal valorIvaDesc = subTotalDescuento.multiply(factorIva).multiply(valor.getCantidad());
                valor.setDetIva(valorIvaDesc);
                //valor total sin decuento y con iva
                valor.setTotal(valorTotalIvaDesc.setScale(6, RoundingMode.FLOOR));
                //valor total con decuento y con iva
                valor.setDetTotaldescuento(valorTotalIvaDesc);
                valor.setDetTotalconiva(valor.getCantidad().multiply(costVentaTipoCliente));
                valor.setDetTotalconivadescuento(valor.getCantidad().multiply(valorTotalIvaDesc));
                valor.setDetCantpordescuento(valorDescuento.multiply(valor.getCantidad()));
                //cantidad por subtotal con descuento
                valor.setDetSubtotaldescuentoporcantidad(subTotalDescuento.multiply(valor.getCantidad()));
                valor.setTipoVenta("NORMAL");
                valor.setCodTipoVenta(clietipo);
            }
            //nuevoRegistro.setSubTotal(productoBuscado.getPordCostoVentaFinal());
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(valor);

            //ingresa un registro vacio
            boolean registroVacio = true;
            List<DetalleFacturaDAO> listaPedidoPost = listaDetalleFacturaDAOMOdel.getInnerList();

            for (DetalleFacturaDAO item : listaPedidoPost) {
                if (item.getProducto() == null) {
                    registroVacio = false;
                    break;
                }
            }

            System.out.println("existe un vacio " + registroVacio);
            if (registroVacio) {
                DetalleFacturaDAO nuevoRegistroPost = new DetalleFacturaDAO();
//                nuevoRegistroPost.setProducto(productoBuscado);
                nuevoRegistroPost.setCantidad(BigDecimal.ZERO);
                nuevoRegistroPost.setSubTotal(BigDecimal.ZERO);
                nuevoRegistroPost.setDetIva(BigDecimal.ZERO);
                nuevoRegistroPost.setDetTotalconiva(BigDecimal.ZERO);
                nuevoRegistroPost.setDescripcion("");
                nuevoRegistroPost.setProducto(null);
                ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistroPost);
            }
        }
        calcularValoresTotales();
        codigoBusqueda = "";

        buscarNombreProd = "";
        idBusquedaProd.setFocus(Boolean.TRUE);
        /*COLOCA EL FOCO EN EL BUSCADOR*/
//        idBusquedaProd.setFocus(Boolean.TRUE);
        ultimaPagina();
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero"})
    public void cambiarRegistro(@BindingParam("valor") DetalleFacturaDAO valor) {
        if (parametrizar.getParNumRegistrosFactura().intValue() <= listaDetalleFacturaDAOMOdel.size()) {
            Clients.showNotification("Numero de registros permitidos, imprima y genere otra factura", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000, true);
            return;
        }

        if (!clienteBuscado.getCliCedula().equals("")) {
            ParamFactura paramFactura = new ParamFactura();
            paramFactura.setBusqueda("producto");
            final HashMap<String, ParamFactura> map = new HashMap<String, ParamFactura>();
            map.put("valor", paramFactura);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/venta/buscarproducto.zul", null, map);
            window.doModal();
            productoBuscado = servicioProducto.findByProdCodigo(codigoBusqueda);
            if (productoBuscado == null) {
                return;
            }
//verifica el kardex
            if (parametrizar.getParActivaKardex() && productoBuscado.getProdEsproducto()) {
                Kardex kardex = servicioKardex.FindALlKardexs(productoBuscado);
                if (kardex.getKarTotal().intValue() < 1) {
                    Clients.showNotification("Verifique el stock del producto cuenta con " + kardex.getKarTotal().intValue() + " en estock",
                                Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
                    agregarRegistroVacio();
                    return;
                }
            }

            if (productoBuscado != null) {
                valor.setCantidad(BigDecimal.ONE);
                valor.setProducto(productoBuscado);
                valor.setDescripcion(productoBuscado.getProdNombre());
                valor.setDetPordescuento(DESCUENTOGENERAL);
                valor.setCodigo(productoBuscado.getProdCodigo());
                valor.setEsProducto(productoBuscado.getProdEsproducto());

                BigDecimal costVentaTipoCliente = BigDecimal.ZERO;
                BigDecimal costVentaTipoClienteInicial = BigDecimal.ZERO;
                String tipoVenta = "NORMAL";
                if (clienteBuscado.getClietipo() == 0) {
                    tipoVenta = "NORMAL";
                    if (clietipo.equals("0")) {
                        costVentaTipoClienteInicial = productoBuscado.getPordCostoVentaFinal();
                        costVentaTipoCliente = productoBuscado.getPordCostoVentaFinal();
                    } else if (clietipo.equals("1")) {
                        tipoVenta = "PREFERENCIAL 1";
                        costVentaTipoClienteInicial = productoBuscado.getProdCostoPreferencial();
                        costVentaTipoCliente = productoBuscado.getProdCostoPreferencial();
                    } else if (clietipo.equals("2")) {
                        tipoVenta = "PREFERENCIAL 2";
                        costVentaTipoClienteInicial = productoBuscado.getProdCostoPreferencialDos();
                        costVentaTipoCliente = productoBuscado.getProdCostoPreferencialDos();
                    }
                    /*OBTIENE LOS VALORES LUEGO DE LA BUSQUEDA*/
                    //        BigDecimal factorIva = (parametrizar.getParIva().divide(BigDecimal.valueOf(100.0)));
                    BigDecimal factorIva = (valor.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
                    BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));

                    valor.setTotalInicial(costVentaTipoClienteInicial);
                    BigDecimal porcentajeDesc = valor.getDetPordescuento().divide(BigDecimal.valueOf(100.0), 4, RoundingMode.FLOOR);
                    BigDecimal valorDescuentoIva = costVentaTipoCliente.multiply(porcentajeDesc);
                    //valor unitario con descuento ioncluido iva
                    BigDecimal valorTotalIvaDesc = costVentaTipoCliente.subtract(valorDescuentoIva);
                    //valor unit sin iva sin descuento
                    BigDecimal subTotal = costVentaTipoCliente.divide(factorSacarSubtotal, 4, RoundingMode.FLOOR);
                    valor.setSubTotal(subTotal);
                    //valor unitario sin iva con descuento
                    BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 4, RoundingMode.FLOOR);
                    valor.setSubTotalDescuento(subTotalDescuento);
                    //valor del descuento
                    BigDecimal valorDescuento = valor.getSubTotal().subtract(valor.getSubTotalDescuento());
                    valor.setDetValdescuento(valorDescuento);
                    BigDecimal valorIva = subTotal.multiply(factorIva).multiply(valor.getCantidad());
//                valor.setDetIva(valorIva);
                    //valor del iva con descuento
                    BigDecimal valorIvaDesc = subTotalDescuento.multiply(factorIva).multiply(valor.getCantidad());
                    valor.setDetIva(valorIvaDesc);
                    //valor total sin decuento y con iva
                    valor.setTotal(costVentaTipoCliente);
                    //valor total con decuento y con iva
                    valor.setDetTotaldescuento(valorTotalIvaDesc);
                    valor.setDetTotalconiva(valor.getCantidad().multiply(costVentaTipoCliente));
                    valor.setDetTotalconivadescuento(valor.getCantidad().multiply(valorTotalIvaDesc));
                    valor.setDetCantpordescuento(valorDescuento.multiply(valor.getCantidad()));
                    //cantidad por subtotal con descuento
                    valor.setDetSubtotaldescuentoporcantidad(subTotalDescuento.multiply(valor.getCantidad()));
                    valor.setTipoVenta("NORMAL");
                    valor.setCodTipoVenta(clietipo);
                }

                //ingresa un registro vacio
                boolean registroVacio = true;
                List<DetalleFacturaDAO> listaPedidoPost = listaDetalleFacturaDAOMOdel.getInnerList();

                for (DetalleFacturaDAO item : listaPedidoPost) {
                    if (item.getProducto() == null) {
                        registroVacio = false;
                        break;
                    }
                }

                System.out.println("existe un vacio " + registroVacio);
                if (registroVacio) {
                    DetalleFacturaDAO nuevoRegistroPost = new DetalleFacturaDAO();
                    nuevoRegistroPost.setProducto(productoBuscado);
                    nuevoRegistroPost.setCantidad(BigDecimal.ZERO);
                    nuevoRegistroPost.setSubTotal(BigDecimal.ZERO);
                    nuevoRegistroPost.setDetIva(BigDecimal.ZERO);
                    nuevoRegistroPost.setDetTotalconiva(BigDecimal.ZERO);
                    nuevoRegistroPost.setDescripcion("");
                    nuevoRegistroPost.setProducto(null);
                    ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistroPost);
                }
            }

            calcularValoresTotales();
            codigoBusqueda = "";
        } else {
            Messagebox.show("Verifique el cliente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero"})
    public void actualizarCostoVenta() {

        BigDecimal factorIva = (parametrizar.getParIva().divide(BigDecimal.valueOf(100.0)));
        BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));
        List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();
        for (DetalleFacturaDAO valor : listaPedido) {

            Producto buscadoPorCodigo = valor.getProducto();

            if (buscadoPorCodigo != null) {
//                valor.setCantidad(BigDecimal.ONE);
                valor.setProducto(buscadoPorCodigo);
                valor.setDescripcion(buscadoPorCodigo.getProdNombre());
                valor.setDetPordescuento(DESCUENTOGENERAL);
                valor.setCodigo(buscadoPorCodigo.getProdCodigo());
                // valor.setCantidad(valor.getCantidad());
                BigDecimal costVentaTipoCliente = BigDecimal.ZERO;
                BigDecimal costVentaTipoClienteInicial = BigDecimal.ZERO;
                String tipoVenta = "NORMAL";
                if (clienteBuscado.getClietipo() == 0) {
                    tipoVenta = "NORMAL";
                    if (clietipo.equals("0")) {
                        costVentaTipoClienteInicial = buscadoPorCodigo.getPordCostoVentaFinal();
                        costVentaTipoCliente = buscadoPorCodigo.getPordCostoVentaFinal();
                    } else if (clietipo.equals("1")) {
                        tipoVenta = "PREFERENCIAL 1";
                        costVentaTipoClienteInicial = buscadoPorCodigo.getProdCostoPreferencial();
                        costVentaTipoCliente = buscadoPorCodigo.getProdCostoPreferencial();
                    } else if (clietipo.equals("2")) {
                        tipoVenta = "PREFERENCIAL 2";
                        costVentaTipoClienteInicial = buscadoPorCodigo.getProdCostoPreferencialDos();
                        costVentaTipoCliente = buscadoPorCodigo.getProdCostoPreferencialDos();
                    }

                    valor.setTotalInicial(ArchivoUtils.redondearDecimales(costVentaTipoClienteInicial, 6));
                    BigDecimal porcentajeDesc = valor.getDetPordescuento().divide(BigDecimal.valueOf(100.0), 6, RoundingMode.FLOOR);
                    BigDecimal valorDescuentoIva = costVentaTipoCliente.multiply(porcentajeDesc).setScale(6, RoundingMode.FLOOR);;
                    //valor unitario con descuento ioncluido iva
                    BigDecimal valorTotalIvaDesc = costVentaTipoCliente.subtract(valorDescuentoIva).setScale(6, RoundingMode.FLOOR);
                    //valor unit sin iva sin descuento
                    BigDecimal subTotal = costVentaTipoCliente.divide(factorSacarSubtotal, 6, RoundingMode.FLOOR);
                    valor.setSubTotal(subTotal);
                    //valor unitario sin iva con descuento
                    BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 6, RoundingMode.FLOOR);
                    valor.setSubTotalDescuento(subTotalDescuento);
                    //valor del descuento
                    BigDecimal valorDescuento = valor.getSubTotal().subtract(valor.getSubTotalDescuento()).setScale(6, RoundingMode.FLOOR);
                    valor.setDetValdescuento(valorDescuento);
                    BigDecimal valorIva = subTotal.multiply(factorIva).multiply(valor.getCantidad());
//                valor.setDetIva(valorIva);
                    //valor del iva con descuento
                    BigDecimal valorIvaDesc = subTotalDescuento.multiply(factorIva).multiply(valor.getCantidad());
                    valor.setDetIva(valorIvaDesc);
                    //valor total sin decuento y con iva
                    valor.setTotal(valorTotalIvaDesc.setScale(6, RoundingMode.FLOOR));
                    //valor total con decuento y con iva
                    valor.setDetTotaldescuento(valorTotalIvaDesc);
                    valor.setDetTotalconiva(valor.getCantidad().multiply(costVentaTipoCliente));
                    valor.setDetTotalconivadescuento(valor.getCantidad().multiply(valorTotalIvaDesc));
                    valor.setDetCantpordescuento(valorDescuento.multiply(valor.getCantidad()));
                    //cantidad por subtotal con descuento
                    valor.setDetSubtotaldescuentoporcantidad(subTotalDescuento.multiply(valor.getCantidad()));
                    valor.setTipoVenta("NORMAL");
                    valor.setCodTipoVenta(clietipo);
                }

            }
        }
        //ingresa un registro vacio
        boolean registroVacio = true;
        List<DetalleFacturaDAO> listaPedidoPost = listaDetalleFacturaDAOMOdel.getInnerList();

        for (DetalleFacturaDAO item : listaPedidoPost) {
            if (item.getProducto() == null) {
                registroVacio = false;
                break;
            }
        }

        System.out.println("existe un vacio " + registroVacio);
        if (registroVacio) {
            DetalleFacturaDAO nuevoRegistroPost = new DetalleFacturaDAO();
            nuevoRegistroPost.setProducto(productoBuscado);
            nuevoRegistroPost.setCantidad(BigDecimal.ZERO);
            nuevoRegistroPost.setSubTotal(BigDecimal.ZERO);
            nuevoRegistroPost.setDetIva(BigDecimal.ZERO);
            nuevoRegistroPost.setDetTotalconiva(BigDecimal.ZERO);
            nuevoRegistroPost.setDescripcion("");
            nuevoRegistroPost.setProducto(null);
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistroPost);
        }
        calcularValoresTotales();
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero"})
    public void buscarPorCodigo(@BindingParam("valor") DetalleFacturaDAO valor) {
        if (parametrizar.getParNumRegistrosFactura().intValue() <= listaDetalleFacturaDAOMOdel.size()) {
            Clients.showNotification("Numero de registros permitidos, imprima y genere otra factura", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000, true);
            return;
        }

//        BigDecimal factorIva = (parametrizar.getParIva().divide(BigDecimal.valueOf(100.0)));
        Producto buscadoPorCodigo = servicioProducto.findByProdCodigo(valor.getCodigo());

        if (buscadoPorCodigo == null) {
            valor.setCodigo("");
            Clients.showNotification("No existe el producto",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 2000, true);
            return;
        }

        BigDecimal factorIva = (buscadoPorCodigo.getProdIva().divide(BigDecimal.valueOf(100.0)));

        BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));

//verifica el kardex
        if (parametrizar.getParActivaKardex()) {
            Kardex kardex = servicioKardex.FindALlKardexs(productoBuscado);
            if (kardex.getKarTotal().intValue() < 1) {
                Clients.showNotification("Verifique el stock del producto cuenta con " + kardex.getKarTotal().intValue() + " en estock",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
                agregarRegistroVacio();
                return;
            }
        }

        if (buscadoPorCodigo != null) {
            valor.setCantidad(BigDecimal.ONE);
            valor.setProducto(buscadoPorCodigo);
            valor.setDescripcion(buscadoPorCodigo.getProdNombre());
            valor.setDetPordescuento(DESCUENTOGENERAL);
            valor.setCodigo(buscadoPorCodigo.getProdCodigo());

            BigDecimal costVentaTipoCliente = BigDecimal.ZERO;
            BigDecimal costVentaTipoClienteInicial = BigDecimal.ZERO;
            String tipoVenta = "NORMAL";

            if (clienteBuscado.getClietipo() == 0) {
                tipoVenta = "NORMAL";
                if (clietipo.equals("0")) {
                    costVentaTipoClienteInicial = buscadoPorCodigo.getPordCostoVentaFinal();
                    costVentaTipoCliente = buscadoPorCodigo.getPordCostoVentaFinal();
                } else if (clietipo.equals("1")) {
                    tipoVenta = "PREFERENCIAL 1";
                    costVentaTipoClienteInicial = buscadoPorCodigo.getProdCostoPreferencial();
                    costVentaTipoCliente = buscadoPorCodigo.getProdCostoPreferencial();
                } else if (clietipo.equals("2")) {
                    tipoVenta = "PREFERENCIAL 2";
                    costVentaTipoClienteInicial = buscadoPorCodigo.getProdCostoPreferencialDos();
                    costVentaTipoCliente = buscadoPorCodigo.getProdCostoPreferencialDos();
                }

                valor.setTotalInicial(costVentaTipoClienteInicial);
                BigDecimal porcentajeDesc = valor.getDetPordescuento().divide(BigDecimal.valueOf(100.0), 4, RoundingMode.FLOOR);
                BigDecimal valorDescuentoIva = costVentaTipoCliente.multiply(porcentajeDesc);
                //valor unitario con descuento ioncluido iva
                BigDecimal valorTotalIvaDesc = costVentaTipoCliente.subtract(valorDescuentoIva);
                //valor unit sin iva sin descuento
                BigDecimal subTotal = costVentaTipoCliente.divide(factorSacarSubtotal, 4, RoundingMode.FLOOR);
                valor.setSubTotal(subTotal);
                //valor unitario sin iva con descuento
                BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 4, RoundingMode.FLOOR);
                valor.setSubTotalDescuento(subTotalDescuento);
                //valor del descuento
                BigDecimal valorDescuento = valor.getSubTotal().subtract(valor.getSubTotalDescuento());
                valor.setDetValdescuento(valorDescuento);
                BigDecimal valorIva = subTotal.multiply(factorIva).multiply(valor.getCantidad());
//                valor.setDetIva(valorIva);
                //valor del iva con descuento
                BigDecimal valorIvaDesc = subTotalDescuento.multiply(factorIva).multiply(valor.getCantidad());
                valor.setDetIva(valorIvaDesc);
                //valor total sin decuento y con iva
                valor.setTotal(costVentaTipoCliente);
                //valor total con decuento y con iva
                valor.setDetTotaldescuento(valorTotalIvaDesc);
                valor.setDetTotalconiva(valor.getCantidad().multiply(costVentaTipoCliente));
                valor.setDetTotalconivadescuento(valor.getCantidad().multiply(valorTotalIvaDesc));
                valor.setDetCantpordescuento(valorDescuento.multiply(valor.getCantidad()));
                //cantidad por subtotal con descuento
                valor.setDetSubtotaldescuentoporcantidad(subTotalDescuento.multiply(valor.getCantidad()));
                valor.setTipoVenta("NORMAL");
                valor.setCodTipoVenta(clietipo);
            }
            //nuevoRegistro.setSubTotal(productoBuscado.getPordCostoVentaFinal());
//            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(valor);

            //ingresa un registro vacio
            boolean registroVacio = true;
            List<DetalleFacturaDAO> listaPedidoPost = listaDetalleFacturaDAOMOdel.getInnerList();

            for (DetalleFacturaDAO item : listaPedidoPost) {
                if (item.getProducto() == null) {
                    registroVacio = false;
                    break;
                }
            }

            if (parametrizar.getParPistolaNuevo()) {
                System.out.println("existe un vacio " + registroVacio);
                if (registroVacio) {
                    DetalleFacturaDAO nuevoRegistroPost = new DetalleFacturaDAO();
                    nuevoRegistroPost.setProducto(null);
                    nuevoRegistroPost.setCantidad(BigDecimal.ZERO);
                    nuevoRegistroPost.setSubTotal(BigDecimal.ZERO);
                    nuevoRegistroPost.setDetIva(BigDecimal.ZERO);
                    nuevoRegistroPost.setDetTotalconiva(BigDecimal.ZERO);
                    nuevoRegistroPost.setDescripcion("");
                    ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistroPost);
                }
            }

        }
        calcularValoresTotales();
        ultimaPagina();
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero"})
    public void calcularValores(@BindingParam("valor") DetalleFacturaDAO valor) {
        try {
            BigDecimal factorIva = (parametrizar.getParIva().divide(BigDecimal.valueOf(100.0)));
            BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));
//            Kardex kardex = servicioKardex.FindALlKardexs(valor.getProducto());
//            if (kardex.getKarTotal().intValue() < valor.getCantidad().intValue()) {
//                valor.setCantidad(kardex.getKarTotal());
//                Clients.showNotification("Verifique el stock del producto cuenta con " + kardex.getKarTotal().intValue() + " en estock",
//                        Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
//
//            }

            if (valor.getCantidad().intValue() > 0) {
                BigDecimal porcentajeDesc = valor.getDetPordescuento().divide(BigDecimal.valueOf(100.0), 7, RoundingMode.FLOOR);
                BigDecimal valorDescuentoIva = valor.getTotal().multiply(porcentajeDesc);

                BigDecimal valorIva = valor.getSubTotalDescuento().multiply(factorIva).multiply(valor.getCantidad());
//                valor.setDetIva(valorIva);
                //valor unitario con descuento ioncluido iva
                BigDecimal valorTotalIvaDesc = valor.getTotal().subtract(valorDescuentoIva);

                //valor unitario sin iva con descuento
                BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 7, RoundingMode.FLOOR);
                valor.setSubTotalDescuento(subTotalDescuento);
                //valor del descuento
                BigDecimal valorDescuento = valor.getSubTotal().subtract(valor.getSubTotalDescuento());
                valor.setDetValdescuento(valorDescuento);
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

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero"})
    public void calcularValoresDesCantidad(@BindingParam("valor") DetalleFacturaDAO valor) {
        try {

            if (valor.getEsProducto() && valor.getTotalInicial().doubleValue() < valor.getTotal().doubleValue()) {
                Clients.showNotification("El precio ingresado no puede superar al precio inicial, si desea colocar un precio superior cree un servicio.",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 4000, true);
                return;
            }

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

                valor.setTotalInicial(valor.getTotal());
            }
            BigDecimal factorIva = (valor.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
            BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));

            if (valor.getCantidad().doubleValue() > 0) {
                /*CALCULO DEL PORCENTAJE DE DESCUENTO*/
                BigDecimal porcentajeDesc = BigDecimal.ZERO;
                BigDecimal valorPorcentaje = BigDecimal.ZERO;
                BigDecimal valorDescuentoIva = BigDecimal.ZERO;
                if (valor.getEsProducto()) {
                    porcentajeDesc = valor.getTotal().multiply(BigDecimal.valueOf(100.0));
                    valorPorcentaje = porcentajeDesc.divide(valor.getTotalInicial(), 6, RoundingMode.FLOOR);
                    valorDescuentoIva = valor.getTotalInicial().subtract(valor.getTotal());
                }

                /*COLOCAMOS EN EL CAMPO DE DESCUENTO*/
                BigDecimal porcentajeDiferencia = BigDecimal.valueOf(100.0).subtract(valorPorcentaje).setScale(6, RoundingMode.FLOOR);
                valor.setDetPordescuento(porcentajeDiferencia);
                //valor unitario con descuento ioncluido iva
                BigDecimal valorTotalIvaDesc = valor.getTotalInicial().subtract(valorDescuentoIva);

                //valor unitario sin iva con descuento
                BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 6, RoundingMode.FLOOR);

                valor.setSubTotalDescuento(subTotalDescuento);
                //valor del descuento
                BigDecimal valorDescuento = BigDecimal.ZERO;
                if (!valor.getEsProducto()) {
                    valor.setSubTotal(valor.getSubTotalDescuento());
                }
                if (valor.getEsProducto()) {
                    valorDescuento = ArchivoUtils.redondearDecimales(valor.getSubTotal(), 6).subtract(ArchivoUtils.redondearDecimales(valor.getSubTotalDescuento(), 6));
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
            //ingresa un registro vacio
            boolean registroVacio = true;
            List<DetalleFacturaDAO> listaPedidoPost = listaDetalleFacturaDAOMOdel.getInnerList();

            for (DetalleFacturaDAO item : listaPedidoPost) {
                if (item.getProducto() == null) {
                    registroVacio = false;
                    break;
                }
            }

            System.out.println("existe un vacio " + registroVacio);
            if (registroVacio) {
                DetalleFacturaDAO nuevoRegistroPost = new DetalleFacturaDAO();
                nuevoRegistroPost.setProducto(null);
                nuevoRegistroPost.setCantidad(BigDecimal.ZERO);
                nuevoRegistroPost.setSubTotal(BigDecimal.ZERO);
                nuevoRegistroPost.setDetIva(BigDecimal.ZERO);
                nuevoRegistroPost.setDetTotalconiva(BigDecimal.ZERO);
                nuevoRegistroPost.setDescripcion("");
                ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistroPost);
            }

        } catch (Exception e) {
            Messagebox.show("Ocurrio un error al calcular los valores" + e, "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    /*CALCULAR EL DESCUENTO EN FUNCION DEL PORCENTAJE*/
    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero"})
    public void calcularValoresDesCantidadPorPorcentaje(@BindingParam("valor") DetalleFacturaDAO valor) {
        try {
            if (valor.getProducto() == null) {
                return;
            }

            BigDecimal factorIva = (valor.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
            BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));

//            Kardex kardex = servicioKardex.FindALlKardexs(valor.getProducto());
//            if (kardex.getKarTotal().intValue() < valor.getCantidad().intValue()) {
//                valor.setCantidad(kardex.getKarTotal());
//                Clients.showNotification("Verifique el stock del producto cuenta con " + kardex.getKarTotal().intValue() + " en estock",
//                        Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
//
//            }
            if (valor.getCantidad().intValue() > 0) {
                BigDecimal porcentajeDesc = valor.getDetPordescuento().divide(BigDecimal.valueOf(100.0), 5, RoundingMode.FLOOR);
//                BigDecimal valorDescuentoIva = valor.getTotalInicial().subtract(valor.getTotal());
                BigDecimal valorDescuentoIva = valor.getTotalInicial().multiply(porcentajeDesc);
                //valor unitario con descuento ioncluido iva
                BigDecimal valorTotalIvaDesc = valor.getTotalInicial().subtract(valorDescuentoIva);
                /*VALOR DEL DETALLE MENOS EL DESCUENTO*/
                valor.setTotal((valor.getTotalInicial().subtract(valorDescuentoIva)).setScale(5, RoundingMode.FLOOR));
                //valor unitario sin iva con descuento
                BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 5, RoundingMode.FLOOR);
                valor.setSubTotalDescuento(subTotalDescuento);
                //valor del descuento
                BigDecimal valorDescuento = valor.getSubTotal().subtract(valor.getSubTotalDescuento());
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
        } catch (Exception e) {
            Messagebox.show("Ocurrio un error al calcular los valores" + e, "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange({"numeroFactura", "numeroProforma", "clienteBuscado"})
    public void verificarNumeracion() {
        verificarSecNumeracion();
    }

    private void getDetallefactura() {
        setListaDetalleFacturaDAOMOdel(new ListModelList<DetalleFacturaDAO>(getListaDetalleFacturaDAODatos()));
        ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).setMultiple(true);
    }

    @Command
    public void seleccionarRegistros() {
        registrosSeleccionados = ((ListModelList<DetalleFacturaDAO>) getListaDetalleFacturaDAOMOdel()).getSelection();
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
        Facturar.buscarCliente = buscarCliente;
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
        Facturar.codigoBusqueda = codigoBusqueda;
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
    @NotifyChange({"listaClientesAll", "clienteBuscado", "fechaEmision", "saldoFacturas", "llegada"})
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
        if (clienteBuscado == null) {
            clienteBuscado = servicioCliente.findClienteLikeCedula("999999999");
        }
        List<Factura> listaFacturasPendientes = servicioFactura.findEstadoCliente("PE", clienteBuscado);
//        saldoFacturas = BigDecimal.ZERO;
//        BigDecimal sumaPendientes = BigDecimal.ZERO;
//        for (Factura listaFacturasPendiente : listaFacturasPendientes) {
//
//            sumaPendientes = sumaPendientes.add(listaFacturasPendiente.getFacSaldoAmortizado());
//        }
//        if (clienteBuscado != null) {
//            saldoFacturas = clienteBuscado.getCliMontoAsignado().subtract(sumaPendientes);
//            saldoFacturas.setScale(2, RoundingMode.FLOOR);
//        }
        if (clienteBuscado != null) {
            llegada = clienteBuscado.getCliDireccion();
        }

    }

    @Command
    @NotifyChange({"clienteBuscado", "fechaEmision", "saldoFacturas", "llegada"})
    public void buscarClienteDni(@BindingParam("valor") Cliente valor) {
        if (valor.getCliCedula() == null) {
            //  Clients.showNotification("Ingrese un valor ", "error", null, "end_before", 3000, true);
            clienteBuscado = servicioCliente.findClienteLikeCedula("999999999");
            return;
        }
        if (valor.getCliCedula().equals("")) {
            //  Clients.showNotification("Ingrese un valor ", "error", null, "end_before", 3000, true);
            clienteBuscado = servicioCliente.findClienteLikeCedula("999999999");
            return;
        }

        clienteBuscado = servicioCliente.FindClienteForCedula(valor.getCliCedula());
        if (clienteBuscado == null) {
            clienteBuscado = servicioCliente.findClienteLikeCedula("999999999");
        }

    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "buscarNombreProd", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero"})
    public void verNotasEntrega() {
        ParamFactura paramFactura = new ParamFactura();
        paramFactura.setBusqueda("nte");
        paramFactura.setCedula(buscarCliente);
        final HashMap<String, ParamFactura> map = new HashMap<String, ParamFactura>();
        map.put("valor", paramFactura);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/venta/listanotaentrega.zul", null, map);
        window.doModal();
        buscarCliente = paramFactura.getCedula();
        listaDetalleFacturaDAODatos.clear();
        if (seleccionNotaEntrega != null) {
            for (Factura fac : seleccionNotaEntrega) {
                System.out.println("FAct " + fac.getIdFactura());

                List<DetalleFactura> detalleFac = servicioDetalleFactura.findDetalleForIdFac(fac.getIdFactura());
                DetalleFacturaDAO nuevoRegistro;

                for (DetalleFactura det : detalleFac) {
                    nuevoRegistro = new DetalleFacturaDAO();
                    nuevoRegistro.setCodigo(det.getIdProducto().getProdCodigo());
                    nuevoRegistro.setCantidad(det.getDetCantidad());
                    nuevoRegistro.setProducto(det.getIdProducto());
                    nuevoRegistro.setDescripcion(det.getDetDescripcion());
                    nuevoRegistro.setSubTotal(det.getDetSubtotal());
                    nuevoRegistro.setTotal(det.getDetTotal());
                    nuevoRegistro.setDetIva(det.getDetIva());
                    nuevoRegistro.setDetTotalconiva(det.getDetTotalconiva());
                    nuevoRegistro.setTipoVenta(det.getDetTipoVenta());
                    //valores con descuentos
                    nuevoRegistro.setSubTotalDescuento(det.getDetSubtotaldescuento());
                    nuevoRegistro.setDetTotaldescuento(det.getDetTotaldescuento());
                    nuevoRegistro.setDetPordescuento(det.getDetPordescuento());
                    nuevoRegistro.setDetValdescuento(det.getDetValdescuento());
                    nuevoRegistro.setDetTotalconivadescuento(det.getDetTotaldescuentoiva());
                    nuevoRegistro.setDetCantpordescuento(det.getDetCantpordescuento());
                    nuevoRegistro.setDetIvaDesc(det.getDetIva());
                    nuevoRegistro.setCodTipoVenta(det.getDetCodTipoVenta());
                    nuevoRegistro.setDetSubtotaldescuentoporcantidad(det.getDetSubtotaldescuentoporcantidad());
                    nuevoRegistro.setTotalInicial(det.getDetTotal());
                    clietipo = det.getDetCodTipoVenta();
//            calcularValores(nuevoRegistro);
                    listaDetalleFacturaDAODatos.add(nuevoRegistro);
                }

            }
            getDetallefactura();
            calcularValoresTotales();
            tipoVentaAnterior = "NTE";
        } else {
            listaDetalleFacturaDAODatos.clear();
            getDetallefactura();
            calcularValoresTotales();
        }

    }

    @Command
    public void nuevoCliente() {

        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/nuevo/cliente.zul", null, null);
        window.doModal();
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
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "subTotalBaseCero", "totalItems", "valorTotalInicialVent"})
    public void eliminarRegistros() {
        if (registrosSeleccionados.size() > 0) {
//
//            ParamFactura paramFactura = new ParamFactura();
//            paramFactura.setBusqueda("cliente");
//            final HashMap<String, ParamFactura> map = new HashMap<String, ParamFactura>();
//            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
//                    "/venta/confirmaborrado.zul", null, map);
//            window.doModal();

            if (true) {
                ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).removeAll(registrosSeleccionados);
                calcularValoresTotales();
            } else {
                Clients.showNotification("No tiene permisos para eliminar, verifique el usuario y contraseña",
                            Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 5000, true);
            }

        } else {
            Messagebox.show("Seleccione al menos un registro para eliminar", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    @Command
    public void validarBorrado() {
        Usuario usuRec = servicioUsuario.FindUsuarioPorNombre(usuLoginVal);
        if (usuRec != null) {
            if (usuRec.getUsuNivel() == 1) {
                if (usuRec.getUsuLogin().equals(usuLoginVal) && usuRec.getUsuPassword().equals(usuPasswordVal)) {
                    validaBorrado = Boolean.TRUE;

                } else {
                    validaBorrado = Boolean.FALSE;
                }
            } else {
                validaBorrado = Boolean.FALSE;
            }

        } else {
            validaBorrado = Boolean.FALSE;
        }

        windowValidaBorra.detach();
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
        BigDecimal factorIva = (parametrizar.getParIva().divide(BigDecimal.valueOf(100.0)));
        BigDecimal facturIvaMasBase = (factorIva.add(BigDecimal.ONE));
//        BigDecimal descuentoValorFinal = BigDecimal.ZERO;
        BigDecimal valorTotalInicial = BigDecimal.ZERO;
        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal valorTotalConIva = BigDecimal.ZERO;
        BigDecimal valorIva = BigDecimal.ZERO;
        BigDecimal valorDescuento = BigDecimal.ZERO;
        BigDecimal baseCero = BigDecimal.ZERO;
        BigDecimal sumaSubsidio = BigDecimal.ZERO;
        BigDecimal sumaDeItems = BigDecimal.ZERO;
        BigDecimal totalizado = BigDecimal.ZERO;

        List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();
        if (listaPedido.size() > 0) {
            for (DetalleFacturaDAO item : listaPedido) {
                sumaDeItems = sumaDeItems.add(BigDecimal.ONE);
                if (item.getProducto() != null) {

                    totalizado = totalizado.add(item.getDetTotalconivadescuento());
                    valorTotal = valorTotal.add(item.getProducto().getProdGrabaIva() ? item.getSubTotalDescuento().multiply(item.getCantidad()) : BigDecimal.ZERO);
                    valorIva = valorIva.add(item.getDetIva());
//                    valorTotalConIva = valorTotalConIva.add(item.getDetTotalconivadescuento());
                    valorDescuento = valorDescuento.add(item.getDetCantpordescuento());
                    valorTotalInicial = valorTotalInicial.add(item.getTotalInicial().multiply(item.getCantidad()));
                    baseCero = baseCero.add(!item.getProducto().getProdGrabaIva() ? item.getSubTotalDescuento().multiply(item.getCantidad()) : BigDecimal.ZERO);
                    /*COSTO SIN SUBSIDIO*/

                    if (item.getProducto().getProdTieneSubsidio().equals("S")) {
                        BigDecimal precioSinSubporcantidad = item.getProducto().getProdSubsidio().multiply(item.getCantidad());
                        sumaSubsidio = sumaSubsidio.add(precioSinSubporcantidad.setScale(5, RoundingMode.FLOOR));
                    }

                }
            }

            totalItems = "ITEMS: " + (sumaDeItems.intValue() - 1);
            System.out.println("**********************************************************");
            System.out.println("valor total:::: subTotalCotizacion " + valorTotal);
            //valorTotal.setScale(5, RoundingMode.UP);
            try {
                subsidioTotal = sumaSubsidio;
                subTotalCotizacion = ArchivoUtils.redondearDecimales(valorTotal, 2);
                // subTotalCotizacion.setScale(5, RoundingMode.UP);
                subTotalBaseCero = ArchivoUtils.redondearDecimales(baseCero, 2);
                /*Obtiene el porcentaje del IVA*/
//                BigDecimal valorIva = subTotalCotizacion.multiply(parametrizar.getParIva());

                ivaCotizacion = ArchivoUtils.redondearDecimales(valorIva, 2);

                // ivaCotizacion.setScale(5, RoundingMode.UP);
                valorTotalCotizacion = ArchivoUtils.redondearDecimales(totalizado, 2);
                // valorTotalCotizacion.setScale(5, RoundingMode.UP);

                valorTotalInicialVent = valorTotalInicial;
                //  valorTotalInicialVent.setScale(5, RoundingMode.UP);

                descuentoValorFinal = valorDescuento.multiply(facturIvaMasBase);
                //  descuentoValorFinal.setScale(5, RoundingMode.UP);
                totalDescuento = valorDescuento;
                //descuentoValorFinal.setScale(5, RoundingMode.UP);

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

    private void guardarFactura(String valor) {

        try {

            String folderGenerados = PATH_BASE + File.separator + amb.getAmGenerados()
                        + File.separator + new Date().getYear()
                        + File.separator + new Date().getMonth();
            String folderEnviarCliente = PATH_BASE + File.separator + amb.getAmEnviocliente()
                        + File.separator + new Date().getYear()
                        + File.separator + new Date().getMonth();
            String folderFirmado = PATH_BASE + File.separator + amb.getAmFirmados()
                        + File.separator + new Date().getYear()
                        + File.separator + new Date().getMonth();

            String foldervoAutorizado = PATH_BASE + File.separator + amb.getAmAutorizados()
                        + File.separator + new Date().getYear()
                        + File.separator + new Date().getMonth();

            String folderNoAutorizados = PATH_BASE + File.separator + amb.getAmNoAutorizados()
                        + File.separator + new Date().getYear()
                        + File.separator + new Date().getMonth();

            /*EN EL CASO DE NO EXISTIR LOS DIRECTORIOS LOS CREA*/
            File folderGen = new File(folderGenerados);
            if (!folderGen.exists()) {
                folderGen.mkdirs();
            }
            File folderFirm = new File(folderFirmado);
            if (!folderFirm.exists()) {
                folderFirm.mkdirs();
            }

            File folderAu = new File(foldervoAutorizado);
            if (!folderAu.exists()) {
                folderAu.mkdirs();
            }

            File folderCliente = new File(folderEnviarCliente);
            if (!folderCliente.exists()) {
                folderCliente.mkdirs();
            }
            File folderNoAut = new File(folderNoAutorizados);
            if (!folderNoAut.exists()) {
                folderNoAut.mkdirs();
            }
            /*Ubicacion del archivo firmado para obtener la informacion*/

//            if (!parametrizar.getParCreditoClientes()) {
//                if (saldoFacturas.doubleValue() < valorTotalCotizacion.doubleValue()) {
//                    Clients.showNotification("Excedio el monto asignado al cliente",
//                            Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000, true);
//                    return;
//                }
//            }
            if (valor.equals("CG")) {
                if (transportista == null || numeroPlaca.equals("")) {

                    Clients.showNotification("Para generar una guia debe seleccionar un conductor e ingresar la placa",
                                Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000, true);
                    return;

                }
            }
            /*VERIFICA SI ES FACTURA O PROFORMA Y COLOCAL EL NUMERO*/
            if ((accion.equals("create")) || (tipoVentaAnterior.equals("PROF") && (tipoVenta.equals("FACT")))) {
                verificarSecNumeracion();
                descargarKardex = Boolean.TRUE;

            } else {

                Boolean verificaEntraSecuen = Boolean.FALSE;

                if (tipoVenta.equals("FACT") && (tipoVentaAnterior.equals("NTV"))) {
                    verificarSecNumeracion();
                    // numeroFactura = factura.getFacNumProforma();
                } else if (tipoVenta.equals("NTV") && (tipoVentaAnterior.equals("NTV"))) {
                    numeroFactura = factura.getFacNumNotaVenta();
                    // numeroFactura = factura.getFacNumProforma();
                } else if (tipoVenta.equals("PROF") && (tipoVentaAnterior.equals("FACT"))) {
                    verificarSecNumeracion();
                    // numeroFactura = factura.getFacNumProforma();
                } else if (tipoVenta.equals("NTE") && (!tipoVentaAnterior.equals("FACT"))) {
                    //
                    verificarSecNumeracion();
                } else if (tipoVenta.equals("NTV") && (!tipoVentaAnterior.equals("FACT"))) {
                    verificarSecNumeracion();
                } else if (tipoVenta.equals("PROF") && (!tipoVentaAnterior.equals("PROF"))) {
                    numeroFactura = factura.getFacNumProforma();
                } else {

                    if (tipoVenta.equals("NTV")) {
                        numeroFactura = factura.getFacNumNotaVenta();
                    }
                    if (tipoVenta.equals("PROF")) {
                        numeroFactura = factura.getFacNumProforma();
                    }
                    if (tipoVenta.equals("NTE")) {
                        numeroFactura = factura.getFacNumNotaEntrega();
                    }

//                    if (tipoVenta.equals("FACT")) {
//                    descargarKardex = Boolean.FALSE;
//                    numeroFactura = factura.getFacNumero();
//                } else
                    if (tipoVenta.equals("FACT")) {
                        descargarKardex = Boolean.TRUE;
                        numeroFactura = factura.getFacNumero();
                    }
                }

            }

            if (tipoVentaAnterior.equals("NTE") && (tipoVenta.equals("FACT"))) {
                descargarKardex = Boolean.FALSE;
                for (Factura factura1 : seleccionNotaEntrega) {
                    factura1.setFacNotaEntregaProcess("S");
                    servicioFactura.modificar(factura1);
                }
            }
            if (tipoVentaAnterior.equals("NTV") && (tipoVenta.equals("FACT"))) {
                descargarKardex = Boolean.FALSE;
            }
            numeroFacturaTexto();

            //guarda con o sin guia de remision 
            facConSinGuia = valor;

            Tipoambiente amb = servicioTipoAmbiente.FindALlTipoambiente();
            //armar la cabecera de la factura
//Coloca la fecha para el cobro de la totalidad de la factura
            Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy 
            calendar.add(Calendar.DATE, Integer.valueOf(facplazo)); //el -3 indica que se le restaran 3 dias 
            Date fechaPagoPlazo = calendar.getTime();

            factura.setFacTipo(tipoVenta);
            factura.setFacDescripcion(facturaDescripcion);
            factura.setFacFecha(fechafacturacion);
            factura.setFacFechaCobro(facFechaCobro);
            factura.setFacEstado(estdoFactura);
            factura.setFacNumeroText(numeroFacturaText);
            factura.setPuntoemision(amb.getAmPtoemi());
            factura.setEstadosri("PENDIENTE");
            factura.setCodestablecimiento(amb.getAmEstab());
            factura.setCod_tipoambiente(amb);
            factura.setFaConSinGuia(facConSinGuia);
            factura.setFacSubsidio(subsidioTotal);
            factura.setFacFechaCobroPlazo(fechaPagoPlazo);
            /*PARA LAS FLORICOLAS*/
            factura.setFacMadre(facMadre);
            factura.setFacHija(facHija);
            factura.setFacDestino(facDestino);
            factura.setIdReferencia(referenciaSelected);
            /*PARA MECANICAS*/
//            factura.setFacPlaca(facPlaca);
//            factura.setFacMarca(facMarca);
//            factura.setFacCilindraje(facCilindraje);
//            factura.setFacChasis(facChasis);
//            factura.setFacAnio(facAnio);
//            factura.setFacKilometraje(facKilometraje);

            if (tipoVenta.equals("SINF")) {
                factura.setFacNumero(0);
                factura.setFacNumProforma(0);
            } else if (tipoVenta.equals("FACT")) {
                factura.setFacNumero(numeroFactura);
                factura.setFacNumProforma(0);
                factura.setFacNumNotaEntrega(0);
                /*PARA EL SRI*/
                factura.setTipodocumento("01");
            } else if (tipoVenta.equals("PROF")) {
                descargarKardex = Boolean.FALSE;
                factura.setFacNumero(0);
                factura.setFacNumNotaEntrega(0);
                factura.setFacNumProforma(numeroFactura);
            } else if (tipoVenta.equals("NTE")) {
                factura.setFacNotaEntregaProcess("N");
                factura.setFacNumero(0);
                factura.setFacNumProforma(0);
                factura.setFacNumNotaEntrega(numeroFactura);
            } else if (tipoVenta.equals("NTV")) {

                factura.setFacNumero(0);
                factura.setFacNumProforma(0);
                factura.setFacNumNotaEntrega(0);
                factura.setFacNumNotaVenta(numeroFactura);
            }

            factura.setIdCliente(clienteBuscado);
            if (accion.equals("create")) {
                factura.setIdUsuario(credential.getUsuarioSistema());
            }
            factura.setFacSubtotal(subTotalCotizacion.add(subTotalBaseCero));
            factura.setFacIva(ivaCotizacion);
            factura.setFacTotal(valorTotalCotizacion);
            // si esta pagada coloca el saldo en cero
            factura.setFacSaldoAmortizado(estdoFactura.equals("PA") ? BigDecimal.ZERO : valorTotalCotizacion);
            factura.setFacDescuento(totalDescuento);
            factura.setFacCodIce("3");
            factura.setFacCodIva("2");
            factura.setFacTotalBaseCero(subTotalBaseCero);
            /*0 SI NO LLEVA IVA Y 2 SI LLEVA IVA*/
            factura.setCodigoPorcentaje(parametrizar.getParCodigoIva());
            factura.setFacPorcentajeIva(parametrizar.getParIva().toString());
            factura.setFacMoneda("DOLAR");
            factura.setIdFormaPago(formaPagoSelected);
            factura.setFacPlazo(BigDecimal.valueOf(Double.valueOf(facplazo)));
            factura.setFacUnidadTiempo(formaPagoSelected.getUnidadTiempo());
            factura.setIdEstado(servicioEstadoFactura.findByEstCodigo(estdoFactura));

            factura.setFacTotalBaseGravaba(subTotalCotizacion);
//            factura.setFacTotalBaseGravaba(subTotalBaseCero);

            if (factura.getFacEstado().equals("PE")) {
                factura.setFacAbono(cobro);
                factura.setFacSaldo(cambio.negate());
            } else {
                factura.setFacAbono(BigDecimal.ZERO);
                factura.setFacSaldo(BigDecimal.ZERO);
            }
            //armar el detalle de la factura
            List<DetalleFacturaDAO> detalleFactura = new ArrayList<DetalleFacturaDAO>();
            List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();
            if (listaPedido.size() > 0) {
                for (DetalleFacturaDAO item : listaPedido) {
                    if (item.getProducto() != null) {
                        detalleFactura.add(item);
                    }

                }

                if (tipoVenta.equals("SINF")) {

                    Factura ultimaVenta = new Factura();
                    Factura verificarFact = servicioFactura.ultimaVentaDiaria(fechafacturacion);

                    if (verificarFact == null) {
                        Factura facturaNueva = new Factura();
                        facturaNueva.setFacTipo("SINF");
                        facturaNueva.setFacFecha(fechafacturacion);
                        facturaNueva.setFacEstado(estdoFactura);
                        facturaNueva.setFacNumero(0);
                        facturaNueva.setIdCliente(clienteBuscado);
                        if (accion.equals("create")) {
                            facturaNueva.setIdUsuario(credential.getUsuarioSistema());
                        }
                        facturaNueva.setFacSubtotal(subTotalCotizacion);
                        // facturaNueva.setFacSubtotal(valorTotalCotizacion);
                        facturaNueva.setFacIva(ivaCotizacion);

                        facturaNueva.setFacTotal(valorTotalCotizacion);
                        facturaNueva.setFacDescuento(totalDescuento);
                        facturaNueva.setFacCodIce("3");
                        facturaNueva.setFacCodIva("2");
                        facturaNueva.setFacTotalBaseCero(BigDecimal.ZERO);
                        facturaNueva.setCodigoPorcentaje("2");
                        facturaNueva.setFacPorcentajeIva(parametrizar.getParIva().toString());
                        facturaNueva.setFacMoneda("DOLAR");
                        facturaNueva.setIdFormaPago(formaPagoSelected);
                        facturaNueva.setFacPlazo(BigDecimal.valueOf(Double.valueOf(formaPagoSelected.getPlazo())));
                        facturaNueva.setFacUnidadTiempo(formaPagoSelected.getUnidadTiempo());
                        facturaNueva.setIdEstado(servicioEstadoFactura.findByEstCodigo(estdoFactura));
                        facturaNueva.setFacTotalBaseGravaba(facturaNueva.getFacSubtotal());
                        //servicioFactura.crear(facturaNueva);

                        servicioFactura.guardarFactura(detalleFactura, facturaNueva);

                    } else {

                        BigDecimal total = verificarFact.getFacTotal().add(valorTotalCotizacion);
                        BigDecimal subTotal = total.divide(BigDecimal.valueOf(1.14), 4, RoundingMode.UP);
                        BigDecimal iva = subTotal.multiply(BigDecimal.valueOf(0.14));
                        iva.setScale(4, RoundingMode.UP);
                        subTotal.setScale(4, RoundingMode.UP);
                        total.setScale(4, RoundingMode.UP);
                        verificarFact.setFacTipo("SINF");
                        verificarFact.setFacFecha(fechafacturacion);
                        verificarFact.setFacEstado(factura.getFacEstado());
                        verificarFact.setFacNumero(0);
                        verificarFact.setIdCliente(factura.getIdCliente());
                        if (accion.equals("create")) {
                            verificarFact.setIdUsuario(credential.getUsuarioSistema());
                        }

                        verificarFact.setFacSubtotal(subTotal);
                        verificarFact.setFacIva(iva);
                        verificarFact.setFacTotal(total);
                        verificarFact.setFacAbono(BigDecimal.ZERO);
                        verificarFact.setFacSaldo(BigDecimal.ZERO);

                        verificarFact.setFacDescuento(BigDecimal.ZERO);
                        verificarFact.setFacCodIce("3");
                        verificarFact.setFacCodIva("2");
                        verificarFact.setFacTotalBaseCero(BigDecimal.ZERO);
                        verificarFact.setCodigoPorcentaje("2");
                        verificarFact.setFacPorcentajeIva(parametrizar.getParIva().toString());
                        verificarFact.setFacMoneda("DOLAR");
                        verificarFact.setIdFormaPago(formaPagoSelected);
                        verificarFact.setFacPlazo(BigDecimal.valueOf(Double.valueOf(formaPagoSelected.getPlazo())));
                        verificarFact.setFacUnidadTiempo(formaPagoSelected.getUnidadTiempo());
                        verificarFact.setIdEstado(servicioEstadoFactura.findByEstCodigo(estdoFactura));
                        verificarFact.setFacTotalBaseGravaba(verificarFact.getFacSubtotal());

                        servicioFactura.guardarFacturaVentaDiaria(detalleFactura, verificarFact);
                    }

                } else {
                    System.out.println("  factura.setIdCliente(clienteBuscado); " + clienteBuscado.getCliCedula() + " " + clienteBuscado.getCliApellidos());
                    factura.setIdCliente(clienteBuscado);
                    /*GENERAMOS LA CLAVE DE ACCESO PARA ENVIAR LA FACTURA DIRECTAMENTE ASI NO ESTE 
                    AUTORIZADA*/
                    String claveAcceso = ArchivoUtils.generaClave(factura.getFacFecha(), "01", amb.getAmRuc(), amb.getAmCodigo(), amb.getAmEstab() + amb.getAmPtoemi(), factura.getFacNumeroText(), "12345678", "1");
                    factura.setFacClaveAcceso(claveAcceso);
                    factura.setFacClaveAutorizacion(claveAcceso);

                    if (accion.equals("create")) {

                        servicioFactura.guardarFactura(detalleFactura, factura);
                        if (estdoFactura.equals("PA")) {
                            DetallePago detallePago = new DetallePago();
                            detallePago.setDetpNumPago(1);
                            detallePago.setDetpFechaCobro(new Date());
                            detallePago.setDetpTotal(factura.getFacTotal());
                            detallePago.setDetpAbono(factura.getFacTotal());
                            detallePago.setDetpSaldo(BigDecimal.ZERO);
                            detallePago.setIdFactura(factura);
                            servicioDetallePago.crear(detallePago);
                        }
                    } else {

                        servicioFactura.eliminar(factura);
                        servicioDetalleKardex.eliminarKardexVenta(factura.getIdFactura());
                        servicioFactura.guardarFactura(detalleFactura, factura);
                    }
                    if (valor.equalsIgnoreCase("CG")) {

                        numeroGuia();
                        Guiaremision guiaremision = new Guiaremision();
                        guiaremision.setFacNumero(numeroGuia);
                        guiaremision.setFacNumeroText(numeroGuiaText);
                        guiaremision.setIdFactura(factura);
                        guiaremision.setIdUsuario(credential.getUsuarioSistema());
                        guiaremision.setFacFecha(new Date());
                        guiaremision.setFacEstado("PENDIENTE");
                        guiaremision.setTipodocumento("06");
                        guiaremision.setPuntoemision(factura.getPuntoemision());
                        guiaremision.setCodestablecimiento(factura.getCodestablecimiento());
                        guiaremision.setEstadosri("PENDIENTE");
                        String claveAccesoGuia = ArchivoUtils.generaClave(guiaremision.getFacFecha(), "06", amb.getAmRuc(), amb.getAmCodigo(), amb.getAmEstab() + amb.getAmPtoemi(), guiaremision.getFacNumeroText(), "12345678", "1");
                        guiaremision.setFacClaveAcceso(claveAccesoGuia);
                        guiaremision.setFacClaveAutorizacion(claveAccesoGuia);
                        guiaremision.setCodTipoambiente(factura.getCod_tipoambiente().getCodTipoambiente());
                        guiaremision.setFacFechaSustento(factura.getFacFecha());
                        guiaremision.setIdTransportista(transportista);
                        guiaremision.setNumplacaguia(numeroPlaca);
                        guiaremision.setIdCliente(factura.getIdCliente());
                        guiaremision.setFechainitranspguia(incioTraslado);
                        guiaremision.setFechafintranspguia(finTraslado);
                        guiaremision.setMotivoGuia(motivoGuia);
                        guiaremision.setPartida(partida);
                        guiaremision.setLlegada(llegada);
                        List<DetalleGuiaremision> detalleGuia = new ArrayList<DetalleGuiaremision>();
                        for (DetalleFacturaDAO itemDet : detalleFactura) {
                            detalleGuia.add(new DetalleGuiaremision(itemDet.getCantidad(), itemDet.getDescripcion(), itemDet.getProducto(), guiaremision));
                        }
                        servicioGuia.guardarGuiaremision(detalleGuia, guiaremision);

                        /*PARA CREAR EL ARCHIVO XML FIRMADO*/
//                        String nombreArchivoXML = File.separator + "GUIA-"
//                                + guiaremision.getCodestablecimiento()
//                                + guiaremision.getPuntoemision()
//                                + guiaremision.getFacNumeroText() + ".xml";
//
//
//                        /*RUTAS FINALES DE,LOS ARCHIVOS XML FIRMADOS Y AUTORIZADOS*/
//                        String pathArchivoFirmado = folderFirmado + nombreArchivoXML;
//                        String pathArchivoAutorizado = foldervoAutorizado + nombreArchivoXML;
//                        String pathArchivoNoAutorizado = folderNoAutorizados + nombreArchivoXML;
//                        String archivoEnvioCliente = "";
//
//                        //tipoambiente tiene los parameteos para los directorios y la firma digital
//                        AutorizarDocumentos aut = new AutorizarDocumentos();
//                        /*Generamos el archivo XML de la factura*/
//                        String archivo = aut.generaXMLGuiaRemision(guiaremision, amb, folderGenerados, nombreArchivoXML);
//
//                        byte[] datos = null;
//                        File f = null;
//                        File fEnvio = null;
//                        /*amb.getAmClaveAccesoSri() es el la clave proporcionada por el SRI
//                        archivo es la ruta del archivo xml generado
//                        nomre del archivo a firmar*/
//                        XAdESBESSignature.firmar(archivo, nombreArchivoXML,
//                                amb.getAmClaveAccesoSri(), amb, folderFirmado);
//
//                        f = new File(pathArchivoFirmado);
//
//                        datos = ArchivoUtils.ConvertirBytes(pathArchivoFirmado);
//                        //obtener la clave de acceso desde el archivo xml
//                        String claveAccesoComprobante = ArchivoUtils.obtenerValorXML(f, "/*/infoTributaria/claveAcceso");
//                        /*GUARDAMOS LA CLAVE DE ACCESO ANTES DE ENVIAR A AUTORIZAR*/
//                        guiaremision.setFacClaveAcceso(claveAccesoComprobante);
//                        AutorizarDocumentos autorizarDocumentos = new AutorizarDocumentos();
//                        RespuestaSolicitud resSolicitud = autorizarDocumentos.validar(datos);
//
//                        if (resSolicitud != null && resSolicitud.getComprobantes() != null) {
//                            if (resSolicitud.getEstado().equals("RECIBIDA")) {
//                                try {
//                                    RespuestaComprobante resComprobante = autorizarDocumentos.autorizarComprobante(claveAccesoComprobante);
//                                    for (Autorizacion autorizacion : resComprobante.getAutorizaciones().getAutorizacion()) {
//                                        FileOutputStream nuevo = null;
//
//                                        /*CREA EL ARCHIVO XML AUTORIZADO*/
//                                        System.out.println("pathArchivoNoAutorizado " + pathArchivoNoAutorizado);
//                                        nuevo = new FileOutputStream(pathArchivoNoAutorizado);
//                                        nuevo.write(autorizacion.getComprobante().getBytes());
//                                        if (!autorizacion.getEstado().equals("AUTORIZADO")) {
//
//                                            String texto = autorizacion.getMensajes().getMensaje().get(0).getMensaje();
//                                            String smsInfo = autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional();
//                                            nuevo.write(autorizacion.getMensajes().getMensaje().get(0).getMensaje().getBytes());
//                                            if (autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional() != null) {
//                                                nuevo.write(autorizacion.getMensajes().getMensaje().get(0).getInformacionAdicional().getBytes());
//                                            }
//
//                                            guiaremision.setMensajesri(texto);
//                                            guiaremision.setEstadosri(autorizacion.getEstado());
//
//                                            nuevo.flush();
//                                            servicioGuia.modificar(guiaremision);
//                                        } else {
//
//                                            guiaremision.setFacClaveAutorizacion(claveAccesoComprobante);
//                                            guiaremision.setEstadosri(autorizacion.getEstado());
//                                            guiaremision.setFacFechaAutorizacion(autorizacion.getFechaAutorizacion().toGregorianCalendar().getTime());
//
//                                            /*se agrega la la autorizacion, fecha de autorizacion y se firma nuevamente*/
//                                            archivoEnvioCliente = aut.generaXMLGuiaRemision(guiaremision, amb, foldervoAutorizado, nombreArchivoXML);
////                            XAdESBESSignature.firmar(archivoEnvioCliente,
////                                    nombreArchivoXML,
////                                    amb.getAmClaveAccesoSri(),
////                                    amb, foldervoAutorizado);
//
//                                            fEnvio = new File(archivoEnvioCliente);
//
//                                            System.out.println("PATH DEL ARCHIVO PARA ENVIAR AL CLIENTE " + archivoEnvioCliente);
//                                            ArchivoUtils.reporteGeneralPdfMail(archivoEnvioCliente.replace(".xml", ".pdf"), guiaremision.getFacNumero(), "GUIA");
//                                            ArchivoUtils.zipFile(fEnvio, archivoEnvioCliente);
//                                            /*GUARDA EL PATH PDF CREADO*/
////                                            guiaremision.setFacpath(archivoEnvioCliente.replace(".xml", ".pdf"));
//                                            servicioGuia.modificar(guiaremision);
//                                            /*envia el mail*/
//
//                                            String[] attachFiles = new String[2];
//                                            attachFiles[0] = archivoEnvioCliente.replace(".xml", ".pdf");
//                                            attachFiles[1] = archivoEnvioCliente.replace(".xml", ".zip");
//                                            MailerClass mail = new MailerClass();
//
//                                            if (guiaremision.getIdCliente().getCliCorreo() != null) {
//                                                mail.sendMailSimple(guiaremision.getIdCliente().getCliCorreo(),
//                                                        "Gracias por preferirnos se ha emitido nuestra guia de remision electrónica",
//                                                        attachFiles,
//                                                        "GUIA DE REMISION ELECTRONICA", guiaremision.getFacClaveAcceso());
//                                            }
//                                        }
//
//                                    }
//                                } catch (Exception e) {
//                                }
//                            }
//                        }
                    }
                    /*VERIFICA SI EL CLINETE QUIERE AUTORIZAR LA FACTURA*/
                    if (!parametrizar.getParEstado() || tipoVenta.equals("PROF")) {
                        /*en el caso que no se desee autorizar la factura*/
                    } else {
                        UtilitarioAutorizarSRI autorizarSRI = new UtilitarioAutorizarSRI();
                        autorizarSRI.autorizarSRI(factura);
                    }

                }

            }
            //ejecutamos el mensaje 
//            Clients.showNotification("Factura registrada con éxito", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 5000, true);
            /*VERIFICA QUE NO SEA UNA PROFORMA QUE SE CONVERTIRA EN FACTURA, VERIFICA SI ES NOT DE ENTREGA 
            NINGUNA PROFORMA DESCARGA*/

 /*Registrar detalle de pago*/
            if (descargarKardex) {
                /*INGRESAMOS LO MOVIMIENTOS AL KARDEX*/
                Kardex kardex = null;
                DetalleKardex detalleKardex = null;

                for (DetalleFacturaDAO item : listaPedido) {
                    if (item.getProducto() != null) {

                        Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla("SAL");
                        if (servicioKardex.FindALlKardexs(item.getProducto()) == null) {
                            kardex = new Kardex();
                            kardex.setIdProducto(item.getProducto());
                            kardex.setKarDetalle("Inicio de inventario desde la facturacion para el producto: " + item.getProducto().getProdNombre());
                            kardex.setKarFecha(new Date());
                            kardex.setKarFechaKardex(new Date());
                            kardex.setKarTotal(BigDecimal.ZERO);
                            servicioKardex.crear(kardex);
                        }
                        detalleKardex = new DetalleKardex();
                        kardex = servicioKardex.FindALlKardexs(item.getProducto());
                        detalleKardex.setIdKardex(kardex);
                        detalleKardex.setDetkFechakardex(fechafacturacion);
                        detalleKardex.setDetkFechacreacion(new Date());
                        detalleKardex.setIdTipokardex(tipokardex);
                        detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                        detalleKardex.setDetkDetalles("Disminuye al kardex desde facturacion con: " + tipoVenta + "-" + factura.getFacNumeroText());
                        detalleKardex.setIdFactura(factura);
                        detalleKardex.setDetkCantidad(item.getCantidad());
                        servicioDetalleKardex.crear(detalleKardex);
                        /*ACTUALIZA EL TOTAL DEL KARDEX*/
                        TotalKardex totales = servicioKardex.totalesForKardex(kardex);
                        BigDecimal total = totales.getTotalKardex();
                        kardex.setKarTotal(total);
                        servicioKardex.modificar(kardex);

                    }
                }

            }

            /*Verificar numero de proforma */
            reporteGeneral();
            if (accion.equals("create")) {
                Executions.sendRedirect("/venta/facturar.zul");
            } else {
//                Executions.sendRedirect("/venta/listafacturas.zul");
                windowModCotizacionFact.detach();
            }

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
    public void Guardar(@BindingParam("valor") String valor) {
        System.out.println("formaPagoSelected " + formaPagoSelected);
        facConSinGuia = valor;
        if (!clienteBuscado.getCliCedula().equals("") && formaPagoSelected != null) {
            if (valorTotalCotizacion.intValue() >= 50 && clienteBuscado.getCliCedula().contains("999999999")) {
                Clients.showNotification("El valor de la factura no puede pasar de $50 para enviarla como Consumidor Final ", "error", null, "end_before", 3000, true);
                return;
            }
            if (listaDetalleFacturaDAOMOdel.size() > 0) {
                if (!listaDetalleFacturaDAOMOdel.get(0).getDescripcion().equals("")) {
                    guardarFactura(valor);

                } else {
                    Messagebox.show("Registre un producto para proceder a la facturación", "Atención", Messagebox.OK, Messagebox.ERROR);
                }

            } else {
                Messagebox.show("Registre un producto para proceder a la facturación", "Atención", Messagebox.OK, Messagebox.ERROR);
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

    @Command
    @NotifyChange({"listaProducto", "buscarCodigoProd"})
    public void buscarLikeCodigoProd() {

        findProductoLikeCodigo();
    }

    @Command
    @NotifyChange({"listaKardexProducto", "buscarNombreProd"})
    public void buscarLikeKardexNombreProd() {

        findKardexProductoLikeNombre();
    }

    @Command
    @NotifyChange({"listaKardexProducto", "buscarCodigoProd"})
    public void buscarLikeKardexCodigoProd() {

        findKardexProductoLikeCodigo();
    }

    private void findKardexProductoLikeNombre() {
        listaKardexProducto = servicioKardex.findByCodOrName(buscarCodigoProd, buscarNombreProd);
    }

    private void findKardexProductoLikeCodigo() {
        listaKardexProducto = servicioKardex.findByCodOrName(buscarCodigoProd, buscarNombreProd);
    }

    @Command
    @NotifyChange({"listaProductoCmb", "codigo"})
    public void buscarInternoCodigo(@BindingParam("valor") DetalleFacturaDAO valor) {
        System.out.println("valor codigo " + valor.getCodigo());
//        findProductoLikeCodigo();
        if (codigo.length() >= 3) {
            listaProductoCmb = servicioProducto.findLikeProdNombre(codigo.toUpperCase());
        }

//        valor.setListaProductoCmb(listaProductoCmb);
    }

    private void findProductoLikeNombre() {
        listaProducto = servicioProducto.findLikeProdNombre(buscarNombreProd);
    }

    private void findProductoLikeCodigo() {
        listaProducto = servicioProducto.findLikeProdCodigo(buscarCodigoProd);
    }

    @Command
    @NotifyChange({"subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion"})
    public void refrescarPagina() {
        calcularValoresTotales();
//        Clients.showNotification("Actaliza", Clients.NOTIFICATION_TYPE_INFO, null, "end_before", 100, true);
    }

    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionarProductoLista(@BindingParam("valor") Producto valor) {
        System.out.println("producto seleccionado " + valor.getProdCodigo());
        codigoBusqueda = valor.getProdCodigo();
        windowProductoBuscar.detach();

    }

    @Command
    @NotifyChange("clienteBuscado")
    public void mensaje(@BindingParam("valor") DetalleFacturaDAO valor) {
        Messagebox.show("Fucniona " + valor.getDescripcion(), "Atención", Messagebox.OK, Messagebox.INFORMATION);

    }

    public void reporteGeneral() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();

        try {
            String reporte = parametrizar.getParImprimeFactura().trim();
            emf.getTransaction().begin();
            /*CONEXION A LA BASE DE DATOS*/
            con = emf.unwrap(Connection.class);
            if (!tipoVenta.equals("SINF")) {

                //  con = emf.unwrap(Connection.class);
                String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                            .getRealPath("/reportes");
                String reportPath = "";
//                con = ConexionReportes.Conexion.conexion();

                if (tipoVenta.equals("FACT")) {
//                    reportPath = reportFile + File.separator + "puntoventa.jasper";
//                    reportPath = reportFile + File.separator + "factura.jasper";
                    reportPath = reportFile + File.separator + reporte;

                } else if (tipoVenta.equals("PROF")) {
                    /*ES EL PATH DONDE SE ENCUENTRA EL REPORTE EN MI CASO*/
                    reportPath = reportFile + File.separator + "proforma.jasper";
                } else if (tipoVenta.equals("NTE")) {
                    /*ES EL PATH DONDE SE ENCUENTRA EL REPORTE EN MI CASO*/
                    reportPath = reportFile + File.separator + "notaentrega.jasper";
                } else if (tipoVenta.equals("NTV")) {
                    /*ES EL PATH DONDE SE ENCUENTRA EL REPORTE EN MI CASO*/
                    reportPath = reportFile + File.separator + "notaventaticket.jasper";
                }
                /*PARAMETROS DEL REPORTE*/
                Map<String, Object> parametros = new HashMap<String, Object>();

                //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
                parametros.put("numfactura", numeroFactura);

                if (con != null) {
                    System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                }

                if (parametrizar.getParImpFactura()) {
                    FileInputStream is = null;
                    is = new FileInputStream(reportPath);
                    /*COMPILAS EL ARCHIVO.JASPER*/
                    byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
                    /*EN MI CASO LO PRESENTO EN UNA VENTANA EMERGENTE  PERO LO ANTERIOR SERIA TODO*/
                    InputStream mediais = new ByteArrayInputStream(buf);

                    AMedia amedia = new AMedia("Reporte", "pdf", "application/pdf", mediais);
                    fileContent = amedia;
                    final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
                    //para pasar al visor
                    map.put("pdf", fileContent);

                    org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                                "/venta/contenedorReporte.zul", null, map);
                    window.doModal();

                }

                if (parametrizar.getParImpAutomatico() && (tipoDoc.equals("FACT") || (tipoDoc.equals("NTV")))) {
                    /*imprime la factura */
 /*para la factura*/
                    FileInputStream is1 = null;
                    is1 = new FileInputStream(reportFile + File.separator + "puntoventa.jasper");
                    JasperPrint jasperprint = JasperFillManager.fillReport(is1, parametros, con);
                    PrinterJob pj = PrinterJob.getPrinterJob();
                    // 
                    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
                    /*ESCOGE LA IMPRESORA */
                    for (PrintService printService : services) {
                        System.out.println("printService.getName() " + printService.getName());
                        if (printService.getName().equals(parametrizar.getParNombreImpresora())) {

                            System.out.println("printService.getName() " + printService.getName());
//                    if (printService.getName().equals("Microsoft Print to PDF")) {
                            pj.setPrintService(printService);
                            //JasperPrintManager.printReport(print, false);
                        }
                    }

                    imprimirTecket(pj, jasperprint);
                }
                /*ESCOGE LA IMPRESORA */
//                for (PrintService printService : services) {
//                    if (printService.getName().equals("LR2000")) {
////                    if (printService.getName().equals("Microsoft Print to PDF")) {
//                        pj.setPrintService(printService);
//                        //JasperPrintManager.printReport(print, false);
//                    }
//                }
//                if (parametrizar.getParImpAutomatico()) {
//                     
//                }
            }
        } catch (PrinterException e) {
            System.out.println("Error PrinterException en generar el reporte " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Error FileNotFoundException en generar el reporte " + e.getMessage());
        } catch (JRException e) {
            System.out.println("Error JRException en generar el reporte " + e.getMessage());
        } finally {
            if (con != null) {
                con.close();
            }
            if (emf != null) {
                emf.close();
                System.out.println("cerro entity");
            }
        }

    }

    private void imprimirTecket(PrinterJob pj, JasperPrint jasperprint) {
        try {
            /*REALIZA LA IMPRESION DE LA FACTURA*/
            JRPrintServiceExporter exporter;
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
            printRequestAttributeSet.add(new Copies(1));

            // these are deprecated
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperprint);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, pj.getPrintService());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, pj.getPrintService().getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);

            exporter.exportReport();

            /*REALIZAE EL CORTE DE PAPEL*/
 /*
            PrintService printService = PrinterOutputStream.getPrintServiceByName("LR2000");
            PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService);
            EscPos escpos = new EscPos(printerOutputStream);
            escpos.writeLF("");
            escpos.writeLF("");
            escpos.feed(5);
            escpos.cut(EscPos.CutMode.PART);
            escpos.close();
             */
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO AL IMPRIMIR LA FACTURA " + e.getMessage());
        } catch (JRException e) {
            System.out.println("ERRO AL IMPRIMIR LA FACTURA " + e.getMessage());
        }

    }

    public void reporteGeneralPdfMail(String pathPDF) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
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

                if (tipoVenta.equals("FACT")) {
                    reportPath = reportFile + File.separator + "puntoventa.jasper";
                } else if (tipoVenta.equals("PROF")) {
                    reportPath = reportFile + File.separator + "proforma.jasper";
                }

                Map<String, Object> parametros = new HashMap<String, Object>();

                //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
                parametros.put("numfactura", numeroFactura);

                if (con != null) {
                    System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                }
                FileInputStream is = null;
                is = new FileInputStream(reportPath);

//                byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
                JasperPrint print = JasperFillManager.fillReport(reportPath, parametros, con);
                JasperExportManager.exportReportToPdfFile(print, pathPDF);
            }
        } catch (Exception e) {
            System.out.println("Error en generar el reporte " + e.getMessage());
        } finally {
            if (con != null) {
                con.close();
            }
            if (emf != null) {
                emf.close();
                System.out.println("cerro entity");
            }
        }

    }

    @Command
    @NotifyChange({"cambio"})
    public void calcularCambio() {
        cambio = cobro.add(valorTotalCotizacion.negate());
        cambio.setScale(2, RoundingMode.FLOOR);
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

    public List<Producto> getListaProductoCmb() {
        return listaProductoCmb;
    }

    public void setListaProductoCmb(List<Producto> listaProductoCmb) {
        this.listaProductoCmb = listaProductoCmb;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getValorTotalInicialVent() {
        return valorTotalInicialVent;
    }

    public void setValorTotalInicialVent(BigDecimal valorTotalInicialVent) {
        this.valorTotalInicialVent = valorTotalInicialVent;
    }

    public BigDecimal getDescuentoValorFinal() {
        return descuentoValorFinal;
    }

    public void setDescuentoValorFinal(BigDecimal descuentoValorFinal) {
        this.descuentoValorFinal = descuentoValorFinal;
    }

    /*crea la tabla de amortizacion*/
    @Command
    public void verDetallePago() throws JRException, IOException, NamingException, SQLException {
        try {
            final HashMap<String, Factura> map = new HashMap<String, Factura>();

            map.put("valor", factura);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/venta/detallepago.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public BigDecimal getSaldoFacturas() {
        return saldoFacturas;
    }

    public void setSaldoFacturas(BigDecimal saldoFacturas) {
        this.saldoFacturas = saldoFacturas;
    }

    /*carga LAS NOTAS DE ENTREGA*/
    private void cargaNotaEntrega() {
        String clienteCedula = buscarCliente;
        listalistaNotaEntregaDatos = servicioFactura.findAllNotaEnt();
//        listalistaNotaEntregaDatos = servicioFactura.findNotaEntPorCliente(buscarCliente);
        setListaNotaEntregaModel(new ListModelList<Factura>(getListalistaNotaEntregaDatos()));
        ((ListModelList<Factura>) listaNotaEntregaModel).setMultiple(true);
        buscarCliente = clienteCedula;
    }

    @Command
    public void seleccionarRegistrosNotaEntrega() {
        seleccionNotaEntrega = ((ListModelList<Factura>) getListaNotaEntregaModel()).getSelection();
    }

    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionarNotaEntrega() {
//        windowNotaEntrega.detach();
        Window windows = (Window) Path.getComponent("/windowNotaEntrega");
        windows.detach();
    }

    public ListModelList<Factura> getListaNotaEntregaModel() {
        return listaNotaEntregaModel;
    }

    public void setListaNotaEntregaModel(ListModelList<Factura> listaNotaEntregaModel) {
        this.listaNotaEntregaModel = listaNotaEntregaModel;
    }

    public List<Factura> getListalistaNotaEntregaDatos() {
        return listalistaNotaEntregaDatos;
    }

    public void setListalistaNotaEntregaDatos(List<Factura> listalistaNotaEntregaDatos) {
        this.listalistaNotaEntregaDatos = listalistaNotaEntregaDatos;
    }

    public static Set<Factura> getSeleccionNotaEntrega() {
        return seleccionNotaEntrega;
    }

    public static void setSeleccionNotaEntrega(Set<Factura> seleccionNotaEntrega) {
        Facturar.seleccionNotaEntrega = seleccionNotaEntrega;
    }

    public BigDecimal getSubTotalBaseCero() {
        return subTotalBaseCero;
    }

    public void setSubTotalBaseCero(BigDecimal subTotalBaseCero) {
        this.subTotalBaseCero = subTotalBaseCero;
    }

    public Textbox getTxtBuscarNombre() {
        return txtBuscarNombre;
    }

    public void setTxtBuscarNombre(Textbox txtBuscarNombre) {
        this.txtBuscarNombre = txtBuscarNombre;
    }

    public String getTipoVentaAnterior() {
        return tipoVentaAnterior;
    }

    public void setTipoVentaAnterior(String tipoVentaAnterior) {
        this.tipoVentaAnterior = tipoVentaAnterior;
    }

    public String getNumeroFacturaText() {
        return numeroFacturaText;
    }

    public void setNumeroFacturaText(String numeroFacturaText) {
        this.numeroFacturaText = numeroFacturaText;
    }

    public Integer getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(Integer numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public String getNumeroGuiaText() {
        return numeroGuiaText;
    }

    public void setNumeroGuiaText(String numeroGuiaText) {
        this.numeroGuiaText = numeroGuiaText;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public void setTransportista(Transportista transportista) {
        this.transportista = transportista;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public List<Transportista> getListaTransportistas() {
        return listaTransportistas;
    }

    public void setListaTransportistas(List<Transportista> listaTransportistas) {
        this.listaTransportistas = listaTransportistas;
    }

    public Date getIncioTraslado() {
        return incioTraslado;
    }

    public void setIncioTraslado(Date incioTraslado) {
        this.incioTraslado = incioTraslado;
    }

    public Date getFinTraslado() {
        return finTraslado;
    }

    public void setFinTraslado(Date finTraslado) {
        this.finTraslado = finTraslado;
    }

    public String getMotivoGuia() {
        return motivoGuia;
    }

    public void setMotivoGuia(String motivoGuia) {
        this.motivoGuia = motivoGuia;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public String getLlegada() {
        return llegada;
    }

    public void setLlegada(String llegada) {
        this.llegada = llegada;
    }

    public BigDecimal getSubsidioTotal() {
        return subsidioTotal;
    }

    public void setSubsidioTotal(BigDecimal subsidioTotal) {
        this.subsidioTotal = subsidioTotal;
    }

    public String getFacConSinGuia() {
        return facConSinGuia;
    }

    public void setFacConSinGuia(String facConSinGuia) {
        this.facConSinGuia = facConSinGuia;
    }

    public String getFacplazo() {
        return facplazo;
    }

    public void setFacplazo(String facplazo) {
        this.facplazo = facplazo;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    @Command
    public void datosMoto(@BindingParam("valor") DetalleFacturaDAO valor) throws JRException, IOException, NamingException, SQLException {
        try {

            final HashMap<String, DetalleFacturaDAO> map = new HashMap<String, DetalleFacturaDAO>();

            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/modificar/motocicleta.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public List<Kardex> getListaKardexProducto() {
        return listaKardexProducto;
    }

    public void setListaKardexProducto(List<Kardex> listaKardexProducto) {
        this.listaKardexProducto = listaKardexProducto;
    }

    public static Boolean getValidaBorrado() {
        return validaBorrado;
    }

    public static void setValidaBorrado(Boolean validaBorrado) {
        Facturar.validaBorrado = validaBorrado;
    }

    public String getUsuLoginVal() {
        return usuLoginVal;
    }

    public void setUsuLoginVal(String usuLoginVal) {
        this.usuLoginVal = usuLoginVal;
    }

    public String getUsuPasswordVal() {
        return usuPasswordVal;
    }

    public void setUsuPasswordVal(String usuPasswordVal) {
        this.usuPasswordVal = usuPasswordVal;
    }

    /*CAMBIAR DE PRECIO */
    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero"})
    public void cambioPrecio(@BindingParam("valor") DetalleFacturaDAO valor) {
        if (parametrizar.getParNumRegistrosFactura().intValue() <= listaDetalleFacturaDAOMOdel.size()) {
            Clients.showNotification("Numero de registros permitidos, imprima y genere otra factura", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000, true);
            return;
        }

        if (!clienteBuscado.getCliCedula().equals("")) {
            ParamFactura paramFactura = new ParamFactura();
            paramFactura.setCodigo(valor.getCodigo());
            paramFactura.setBusqueda("cambio");
            final HashMap<String, ParamFactura> map = new HashMap<String, ParamFactura>();
            map.put("valor", paramFactura);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                        "/venta/cambioprecio.zul", null, map);
            window.doModal();
            productoBuscado = valor.getProducto();
            if (productoBuscado == null) {
                return;
            }
            //verifica el kardex
            if (parametrizar.getParActivaKardex() && productoBuscado.getProdEsproducto()) {
                Kardex kardex = servicioKardex.FindALlKardexs(productoBuscado);
                if (kardex.getKarTotal().intValue() < 1) {
                    Clients.showNotification("Verifique el stock del producto cuenta con " + kardex.getKarTotal().intValue() + " en estock",
                                Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
                    agregarRegistroVacio();
                    return;
                }
            }

            if (productoBuscado != null) {
                valor.setCantidad(valor.getCantidad());
                valor.setProducto(productoBuscado);
                valor.setDescripcion(productoBuscado.getProdNombre());
                valor.setDetPordescuento(DESCUENTOGENERAL);
                valor.setCodigo(productoBuscado.getProdCodigo());
                valor.setEsProducto(productoBuscado.getProdEsproducto());

                BigDecimal costVentaTipoCliente = BigDecimal.ZERO;
                BigDecimal costVentaTipoClienteInicial = BigDecimal.ZERO;
                String tipoVenta = TIPOPRECIO;
                if (clienteBuscado.getClietipo() == 0) {
                    tipoVenta = "NORMAL";
                    if (TIPOPRECIO.equals("NORMAL")) {
                        costVentaTipoClienteInicial = productoBuscado.getPordCostoVentaFinal();
                        costVentaTipoCliente = productoBuscado.getPordCostoVentaFinal();
                    } else if (TIPOPRECIO.equals("PREFERENCIAL 1")) {
                        tipoVenta = "PREFERENCIAL 1";
                        costVentaTipoClienteInicial = productoBuscado.getProdCostoPreferencial();
                        costVentaTipoCliente = productoBuscado.getProdCostoPreferencial();
                    } else if (TIPOPRECIO.equals("PREFERENCIAL 2")) {
                        tipoVenta = "PREFERENCIAL 2";
                        costVentaTipoClienteInicial = productoBuscado.getProdCostoPreferencialDos();
                        costVentaTipoCliente = productoBuscado.getProdCostoPreferencialDos();
                    } else if (TIPOPRECIO.equals("PREFERENCIAL 3")) {
                        tipoVenta = "PREFERENCIAL 3";
                        costVentaTipoClienteInicial = productoBuscado.getProdCostoPreferencialTres();
                        costVentaTipoCliente = productoBuscado.getProdCostoPreferencialTres();
                    }
                    /*OBTIENE LOS VALORES LUEGO DE LA BUSQUEDA*/
                    //        BigDecimal factorIva = (parametrizar.getParIva().divide(BigDecimal.valueOf(100.0)));
                    BigDecimal factorIva = (valor.getProducto().getProdIva().divide(BigDecimal.valueOf(100.0)));
                    BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));

                    valor.setTotalInicial(costVentaTipoClienteInicial);
                    BigDecimal porcentajeDesc = valor.getDetPordescuento().divide(BigDecimal.valueOf(100.0), 5, RoundingMode.FLOOR);
                    BigDecimal valorDescuentoIva = costVentaTipoCliente.multiply(porcentajeDesc);
                    //valor unitario con descuento ioncluido iva
                    BigDecimal valorTotalIvaDesc = costVentaTipoCliente.subtract(valorDescuentoIva);
                    //valor unit sin iva sin descuento
                    BigDecimal subTotal
                                = costVentaTipoCliente.divide(factorSacarSubtotal, 5, RoundingMode.FLOOR);
                    valor.setSubTotal(subTotal);
                    //valor unitario sin iva con descuento
                    BigDecimal subTotalDescuento
                                = valorTotalIvaDesc.divide(factorSacarSubtotal, 5, RoundingMode.FLOOR);
                    valor.setSubTotalDescuento(subTotalDescuento);
                    //valor del descuento
                    BigDecimal valorDescuento = valor.getSubTotal().subtract(valor.getSubTotalDescuento());
                    valor.setDetValdescuento(valorDescuento);
                    BigDecimal valorIva = subTotal.multiply(factorIva).multiply(valor.getCantidad());
//                valor.setDetIva(valorIva);
                    //valor del iva con descuento
                    BigDecimal valorIvaDesc = subTotalDescuento.multiply(factorIva).multiply(valor.getCantidad());
                    valor.setDetIva(valorIvaDesc);
                    //valor total sin decuento y con iva
                    valor.setTotal(costVentaTipoCliente);
                    //valor total con decuento y con iva
                    valor.setDetTotaldescuento(valorTotalIvaDesc);
                    valor.setDetTotalconiva(valor.getCantidad().multiply(costVentaTipoCliente));
                    valor.setDetTotalconivadescuento(valor.getCantidad().multiply(valorTotalIvaDesc));
                    valor.setDetCantpordescuento(valorDescuento.multiply(valor.getCantidad()));
                    //cantidad por subtotal con descuento
                    valor.setDetSubtotaldescuentoporcantidad(subTotalDescuento.multiply(valor.getCantidad()));
                    valor.setTipoVenta("NORMAL");
                    valor.setCodTipoVenta(clietipo);
                }

                //ingresa un registro vacio
                boolean registroVacio = true;
                List<DetalleFacturaDAO> listaPedidoPost = listaDetalleFacturaDAOMOdel.getInnerList();

                for (DetalleFacturaDAO item : listaPedidoPost) {
                    if (item.getProducto() == null) {
                        registroVacio = false;
                        break;
                    }
                }

                System.out.println("existe un vacio " + registroVacio);
                if (registroVacio) {
                    DetalleFacturaDAO nuevoRegistroPost = new DetalleFacturaDAO();
                    nuevoRegistroPost.setProducto(productoBuscado);
                    nuevoRegistroPost.setCantidad(BigDecimal.ZERO);
                    nuevoRegistroPost.setSubTotal(BigDecimal.ZERO);
                    nuevoRegistroPost.setDetIva(BigDecimal.ZERO);
                    nuevoRegistroPost.setDetTotalconiva(BigDecimal.ZERO);
                    nuevoRegistroPost.setDescripcion("");
                    nuevoRegistroPost.setProducto(null);
                    ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistroPost);
                }
            }

            calcularValoresTotales();
            codigoBusqueda = "";
        } else {
            Messagebox.show("Verifique el cliente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public static String getTIPOPRECIO() {
        return TIPOPRECIO;
    }

    public static void setTIPOPRECIO(String TIPOPRECIO) {
        Facturar.TIPOPRECIO = TIPOPRECIO;
    }

    public Producto getPRODUCTOCAMBIO() {
        return PRODUCTOCAMBIO;
    }

    public void setPRODUCTOCAMBIO(Producto PRODUCTOCAMBIO) {
        this.PRODUCTOCAMBIO = PRODUCTOCAMBIO;
    }

    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionPrecioNorm() {
        System.out.println("TIPOPRECIO NORMAL");
        TIPOPRECIO = "NORMAL";
        windowCambioPrecio.detach();

    }

    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionPrecioPref1() {
        System.out.println("TIPOPRECIO PREFERENCIAL 1");
        TIPOPRECIO = "PREFERENCIAL 1";
        windowCambioPrecio.detach();

    }

    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionPrecioPref2() {
        System.out.println("TIPOPRECIO PREFERENCIAL 2");
        TIPOPRECIO = "PREFERENCIAL 2";
        windowCambioPrecio.detach();

    }

    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionPrecioPref3() {
        System.out.println("TIPOPRECIO PREFERENCIAL 3");
        TIPOPRECIO = "PREFERENCIAL 3";
        windowCambioPrecio.detach();

    }

    public Date getFacFechaCobro() {
        return facFechaCobro;
    }

    public void setFacFechaCobro(Date facFechaCobro) {
        this.facFechaCobro = facFechaCobro;
    }

    public String getFacPlaca() {
        return facPlaca;
    }

    public void setFacPlaca(String facPlaca) {
        this.facPlaca = facPlaca;
    }

    public String getFacMarca() {
        return facMarca;
    }

    public void setFacMarca(String facMarca) {
        this.facMarca = facMarca;
    }

    public Integer getFacAnio() {
        return facAnio;
    }

    public void setFacAnio(Integer facAnio) {
        this.facAnio = facAnio;
    }

    public String getFacCilindraje() {
        return facCilindraje;
    }

    public void setFacCilindraje(String facCilindraje) {
        this.facCilindraje = facCilindraje;
    }

    public String getFacKilometraje() {
        return facKilometraje;
    }

    public void setFacKilometraje(String facKilometraje) {
        this.facKilometraje = facKilometraje;
    }

    public String getFacChasis() {
        return facChasis;
    }

    public void setFacChasis(String facChasis) {
        this.facChasis = facChasis;
    }

    public String getFacMadre() {
        return facMadre;
    }

    public void setFacMadre(String facMadre) {
        this.facMadre = facMadre;
    }

    public String getFacHija() {
        return facHija;
    }

    public void setFacHija(String facHija) {
        this.facHija = facHija;
    }

    public String getFacDestino() {
        return facDestino;
    }

    public void setFacDestino(String facDestino) {
        this.facDestino = facDestino;
    }

    public List<Referencia> getListaReferencia() {
        return listaReferencia;
    }

    public void setListaReferencia(List<Referencia> listaReferencia) {
        this.listaReferencia = listaReferencia;
    }

    public Referencia getReferenciaSelected() {
        return referenciaSelected;
    }

    public void setReferenciaSelected(Referencia referenciaSelected) {
        this.referenciaSelected = referenciaSelected;
    }

}
