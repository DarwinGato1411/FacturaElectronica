/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.Cliente;
import com.ec.entidad.DetalleFactura;
import com.ec.entidad.Factura;
import com.ec.entidad.FormaPago;
import com.ec.entidad.Kardex;
import com.ec.entidad.NotaCreditoDebito;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Producto;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.Tipocomprobante;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioCliente;
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
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
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
    private Integer numeroProforma = 0;
    private Date fechafacturacion = new Date();
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
    private Tipoambiente amb = new Tipoambiente();

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
        amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();

        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
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
            clietipo = det.getDetCodTipoVenta();
//            calcularValores(nuevoRegistro);
            listaDetalleFacturaDAODatos.add(nuevoRegistro);
        }

        getDetallefactura();
        calcularValoresTotales();
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento"})
    public void calcularValores(@BindingParam("valor") DetalleFacturaDAO valor) {
        try {
            BigDecimal factorIva = (parametrizar.getParIva().divide(BigDecimal.valueOf(100.0)));
            BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));
//            Kardex kardex = servicioKardex.FindALlKardexs(valor.getProducto());
//            if (kardex.getKarTotal().intValue() < valor.getCantidad().intValue()) {
//               // valor.setCantidad(kardex.getKarTotal());
//                Clients.showNotification("Verifique el stock del producto cuenta con " + kardex.getKarTotal().intValue() + " en estock",
//                        Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
//
//            }

            if (valor.getCantidad().intValue() > 0) {
                BigDecimal porcentajeDesc = valor.getDetPordescuento().divide(BigDecimal.valueOf(100.0), 4, RoundingMode.FLOOR);
                BigDecimal valorDescuentoIva = valor.getTotal().multiply(porcentajeDesc);

                BigDecimal valorIva = valor.getSubTotalDescuento().multiply(factorIva).multiply(valor.getCantidad());
//                valor.setDetIva(valorIva);
                //valor unitario con descuento ioncluido iva
                BigDecimal valorTotalIvaDesc = valor.getTotal().subtract(valorDescuentoIva);

                //valor unitario sin iva con descuento
                BigDecimal subTotalDescuento = valorTotalIvaDesc.divide(factorSacarSubtotal, 4, RoundingMode.FLOOR);
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

    private void getDetallefactura() {
        setListaDetalleFacturaDAOMOdel(new ListModelList<DetalleFacturaDAO>(getListaDetalleFacturaDAODatos()));
        ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).setMultiple(true);
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel","subTotalBaseCero", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion","totalDescuento"})
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
        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal valorTotalConIva = BigDecimal.ZERO;
        BigDecimal valorIva = BigDecimal.ZERO;
        BigDecimal valorDescuento = BigDecimal.ZERO;
        BigDecimal Subtotal = BigDecimal.ZERO;
        BigDecimal basecero = BigDecimal.ZERO;
        BigDecimal basedoce = BigDecimal.ZERO;
        
           BigDecimal factorIva = (parametrizar.getParIva().divide(BigDecimal.valueOf(100.0)));
        BigDecimal facturIvaMasBase = (factorIva.add(BigDecimal.ONE));
//        BigDecimal descuentoValorFinal = BigDecimal.ZERO;
        BigDecimal valorTotalInicial = BigDecimal.ZERO;
//        BigDecimal valorTotal = BigDecimal.ZERO;
//        BigDecimal valorTotalConIva = BigDecimal.ZERO;
//        BigDecimal valorIva = BigDecimal.ZERO;
//        BigDecimal valorDescuento = BigDecimal.ZERO;
        BigDecimal baseCero = BigDecimal.ZERO;
        BigDecimal sumaSubsidio = BigDecimal.ZERO;
        BigDecimal sumaDeItems = BigDecimal.ZERO;

        List<DetalleFacturaDAO> listaPedido = new ArrayList<DetalleFacturaDAO>();
        if (registrosSeleccionados.size() > 0) {
            for (DetalleFacturaDAO registrosSeleccionado : registrosSeleccionados) {
                listaPedido.add(registrosSeleccionado);
            }
        } else {
            listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();
        }


            for (DetalleFacturaDAO item : listaPedido) {
                sumaDeItems = sumaDeItems.add(BigDecimal.ONE);
                if (item.getProducto() != null) {
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

//            totalItems = "ITEMS: " + (sumaDeItems.intValue() - 1);
            System.out.println("**********************************************************");
            System.out.println("valor total:::: subTotalCotizacion " + valorTotal);
            //valorTotal.setScale(5, RoundingMode.UP);
            try {
              subsidioTotal = sumaSubsidio;
                subTotalCotizacion = valorTotal;
                // subTotalCotizacion.setScale(5, RoundingMode.UP);
                subTotalBaseCero = baseCero;
                /*Obtiene el porcentaje del IVA*/
//                BigDecimal valorIva = subTotalCotizacion.multiply(parametrizar.getParIva());

                ivaCotizacion = valorIva;
                // ivaCotizacion.setScale(5, RoundingMode.UP);

                valorTotalCotizacion = valorTotal.add(baseCero.add(valorIva));
                // valorTotalCotizacion.setScale(5, RoundingMode.UP);

                valorTotalInicialVent = valorTotalInicial;
                //  valorTotalInicialVent.setScale(5, RoundingMode.UP);

                
                //  descuentoValorFinal.setScale(5, RoundingMode.UP);
                totalDescuento = valorDescuento;
                //descuentoValorFinal.setScale(5, RoundingMode.UP);

                subTotalCotizacion = ArchivoUtils.redondearDecimales(subTotalCotizacion, 2);
                subTotalBaseCero = ArchivoUtils.redondearDecimales(subTotalBaseCero, 2);
                valorTotalCotizacion = ArchivoUtils.redondearDecimales(valorTotalCotizacion, 2);
                valorTotalInicialVent = ArchivoUtils.redondearDecimales(valorTotalInicialVent, 2);
                ivaCotizacion = ArchivoUtils.redondearDecimales(ivaCotizacion, 2);
                

            } catch (Exception e) {
                System.out.println("error de calculo de valores " + e);
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
            creditoDebito.setFacSubtotal(subTotalCotizacion.add(subTotalBaseCero));
            creditoDebito.setFacTipo("NCRE");
            creditoDebito.setEstadosri("PENDIENTE");
            creditoDebito.setFacTotal(valorTotalCotizacion);
            creditoDebito.setFacTotalBaseCero(subTotalBaseCero);
            creditoDebito.setFacTotalBaseGravaba(subTotalCotizacion);
            creditoDebito.setFacUnidadTiempo(formaPagoSelected.getUnidadTiempo());
            creditoDebito.setIdFactura(factura);
            creditoDebito.setIdUsuario(credential.getUsuarioSistema());
            creditoDebito.setPuntoemision(amb.getAmPtoemi());
            creditoDebito.setTipodocumento("04");
            creditoDebito.setTipodocumentomod("01");

            servicioNotaCredito.guardarNotaCreditoDebito(detalleFactura, creditoDebito);

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

}
