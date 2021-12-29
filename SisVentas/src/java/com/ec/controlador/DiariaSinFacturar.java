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
import com.ec.entidad.Producto;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCliente;
import com.ec.servicio.ServicioDetalleFactura;
import com.ec.servicio.ServicioEstadoFactura;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioProducto;
import com.ec.untilitario.ConexionReportes;
import java.io.ByteArrayInputStream;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class DiariaSinFacturar {

    @Wire
    Window windowClienteBuscar;
    @Wire
    Window windowProductoBuscar;
    //buscar cliente
    ServicioCliente servicioCliente = new ServicioCliente();
    ServicioEstadoFactura servicioEstadoFactura = new ServicioEstadoFactura();
    ServicioDetalleFactura servicioDetalleFactura = new ServicioDetalleFactura();
    ServicioFactura servicioFactura = new ServicioFactura();
    public Cliente clienteBuscado = new Cliente();
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
    //Cabecera de la factura
    private String estdoFactura = "PA";
    private Integer numeroFactura = 0;
    private Date fechafacturacion = new Date();
    //usuario que factura
    UserCredential credential = new UserCredential();
    private Producto p1 = new Producto();
    private Producto p2 = new Producto();
    private Producto p3 = new Producto();
    private Producto p4 = new Producto();
    private Producto p5 = new Producto();
    private Producto p6 = new Producto();
    private Producto p7 = new Producto();
    private Producto p8 = new Producto();
    private Producto p9 = new Producto();
    private Producto p10 = new Producto();
//reporte
    AMedia fileContent = null;
    Connection con = null;
    //valor a recuperar en la factura
    private Integer idFactura = 0;
    private String action = "create";
    private String clienteFactura = "CLIENTE FINAL";
    Factura factRecuperada = new Factura();
    //cambio
    private BigDecimal cobro = BigDecimal.ZERO;
    private BigDecimal cambio = BigDecimal.ZERO;
    //suma los valores seleccionados
    private BigDecimal valorUltimaVenta = BigDecimal.ZERO;
    private String clietipo = "0";

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        iniciarVentaDiaria();
        cargarProductosPrincipales();

    }

    private void cargarProductosPrincipales() {
        List<Producto> lstPrincipal = servicioProducto.findProductoPrincipal();
        if (lstPrincipal.size() > 0) {
            p1 = lstPrincipal.get(0);
        }
        if (lstPrincipal.size() > 1) {
            p2 = lstPrincipal.get(1);
        }
        if (lstPrincipal.size() > 2) {
            p3 = lstPrincipal.get(2);
        }
        if (lstPrincipal.size() > 3) {
            p4 = lstPrincipal.get(3);
        }
        if (lstPrincipal.size() > 4) {
            p5 = lstPrincipal.get(4);
        }
        if (lstPrincipal.size() > 5) {
            p6 = lstPrincipal.get(5);
        }
        if (lstPrincipal.size() > 6) {
            p7 = lstPrincipal.get(6);
        }
        if (lstPrincipal.size() > 7) {
            p8 = lstPrincipal.get(7);
        }
        if (lstPrincipal.size() > 8) {
            p9 = lstPrincipal.get(8);
        }
        if (lstPrincipal.size() > 9) {
            p10 = lstPrincipal.get(9);
        }





    }

    private void iniciarVentaDiaria() {
        try {
            action = "update";
//        System.out.println("================ " + valor);
            Factura ultimaVenta = new Factura();
            Factura verificarFact = servicioFactura.ultimaVentaDiaria(fechafacturacion);
            System.out.println("valor de la factura recuperada " + verificarFact);
            if (verificarFact == null) {
                Factura facturaNueva = new Factura();
                facturaNueva.setFacTipo("SINF");
                facturaNueva.setFacFecha(fechafacturacion);
                facturaNueva.setFacEstado(estdoFactura);
                facturaNueva.setFacNumero(0);
                facturaNueva.setIdCliente(servicioCliente.FindClientFinal(clienteFactura));
                facturaNueva.setIdUsuario(credential.getUsuarioSistema());
                facturaNueva.setFacSubtotal(BigDecimal.ZERO);
                facturaNueva.setFacIva(BigDecimal.ZERO);
                facturaNueva.setFacTotal(BigDecimal.ZERO);
                facturaNueva.setIdEstado(servicioEstadoFactura.findByEstCodigo(estdoFactura));
                System.out.println("crea la factura");
                servicioFactura.crear(facturaNueva);
                ultimaVenta = servicioFactura.ultimaVentaDiaria(fechafacturacion);
                idFactura = ultimaVenta.getIdFactura();
                cargarRegistros();
            } else {
                ultimaVenta = verificarFact;
                idFactura = ultimaVenta.getIdFactura();
                cargarRegistros();
                //agrega un registro vacio
                DetalleFacturaDAO nuevoRegistro = new DetalleFacturaDAO();
                nuevoRegistro.setProducto(productoBuscado);
                nuevoRegistro.setCantidad(BigDecimal.ZERO);
                nuevoRegistro.setSubTotal(BigDecimal.ZERO);
                nuevoRegistro.setDescripcion("");
                if (clietipo.equals("0")) {
                    nuevoRegistro.setTipoVenta("NORMAL");
                } else if (clietipo.equals("1")) {
                    nuevoRegistro.setTipoVenta("PREFERENCIAL 1");
                } else if (clietipo.equals("2")) {
                    nuevoRegistro.setTipoVenta("PREFERENCIAL 2");
                } else if (clietipo.equals("3")) {
                    nuevoRegistro.setTipoVenta("PREFERENCIAL 3");
                }
                nuevoRegistro.setProducto(null);
                nuevoRegistro.setElementoNuevo("SI");
                ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);
            }


            System.out.println("id de factura para la venta diaria " + idFactura);

            //FindClienteLikeNombre();
            findProductoLikeNombre();

        } catch (Exception e) {
            System.out.println("errorr de inicio de venta diaria " + e);
        }
    }

    private void cargarRegistros() {
        listaDetalleFacturaDAOMOdel.clear();
        factRecuperada = servicioFactura.findFirIdFactDiaria(idFactura);
        System.out.println("factura recuperada " + factRecuperada);
        //numero de factura
//        numeroFactura = factRecuperada.getFacNumero();
        //recupera el cliente
        clienteBuscado = factRecuperada.getIdCliente();
        //valores totales
        subTotalCotizacion = factRecuperada.getFacSubtotal();
        ivaCotizacion = factRecuperada.getFacIva();
        valorTotalCotizacion = factRecuperada.getFacTotal();
        estdoFactura = factRecuperada.getFacEstado();
        if (factRecuperada.getFacEstado().equals("PE")) {
            cobro = factRecuperada.getFacAbono();
            cambio = factRecuperada.getFacSaldo();
        }
        List<DetalleFactura> detalle = servicioDetalleFactura.findDetalleForIdFactuta(factRecuperada);
//        if (detalle.isEmpty()) {
//            agregarRegistrovacio();
//        }
        for (DetalleFactura detalleFactura : detalle) {
            System.out.println("detalle " + detalleFactura.getDetDescripcion());
            DetalleFacturaDAO nuevoRegistro = new DetalleFacturaDAO();
            nuevoRegistro.setProducto(detalleFactura.getIdProducto());
            nuevoRegistro.setCantidad(detalleFactura.getDetCantidad());
            nuevoRegistro.setSubTotal(detalleFactura.getDetSubtotal());
            nuevoRegistro.setSubTotalPorCantidad(detalleFactura.getDetTotal());
            
//            nuevoRegistro.setTotal(detalleFactura.getDetTotal());
            nuevoRegistro.setDetIva(detalleFactura.getDetIva());
            nuevoRegistro.setDetTotalconiva(detalleFactura.getDetTotalconiva());
            nuevoRegistro.setCodigo(detalleFactura.getIdProducto().getProdCodigo());
            nuevoRegistro.setDescripcion(detalleFactura.getDetDescripcion());
            nuevoRegistro.setElementoNuevo("NO");
            nuevoRegistro.setTipoVenta(detalleFactura.getDetTipoVenta());
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);
        }


    }

    public DiariaSinFacturar() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        getDetallefactura();
        Factura recuperada = servicioFactura.FindUltimaFactura();
//        if (recuperada != null) {
//            numeroFactura = recuperada.getFacNumero() + 1;
//        } else {
//            numeroFactura = 1;
//        }


    }

    private void getDetallefactura() {
        setListaDetalleFacturaDAOMOdel(new ListModelList<DetalleFacturaDAO>(getListaDetalleFacturaDAODatos()));
        ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).setMultiple(true);
    }

    @Command
    @NotifyChange("valorUltimaVenta")
    public void seleccionarRegistros() {
        valorUltimaVenta = BigDecimal.ZERO;
        registrosSeleccionados = ((ListModelList<DetalleFacturaDAO>) getListaDetalleFacturaDAOMOdel()).getSelection();
        for (DetalleFacturaDAO item : registrosSeleccionados) {
            if (item.getProducto() != null) {
                valorUltimaVenta = valorUltimaVenta.add(item.getTotal());
            }

        }
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
        DiariaSinFacturar.buscarCliente = buscarCliente;
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
        DiariaSinFacturar.codigoBusqueda = codigoBusqueda;
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

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Factura getFactRecuperada() {
        return factRecuperada;
    }

    public void setFactRecuperada(Factura factRecuperada) {
        this.factRecuperada = factRecuperada;
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

    public String getClienteFactura() {
        return clienteFactura;
    }

    public void setClienteFactura(String clienteFactura) {
        this.clienteFactura = clienteFactura;
    }

    public BigDecimal getValorUltimaVenta() {
        return valorUltimaVenta;
    }

    public void setValorUltimaVenta(BigDecimal valorUltimaVenta) {
        this.valorUltimaVenta = valorUltimaVenta;
    }

    public String getClietipo() {
        return clietipo;
    }

    public void setClietipo(String clietipo) {
        this.clietipo = clietipo;
    }

    @Command
    @NotifyChange({"listaClientesAll", "clienteBuscado", "fechaEmision"})
    public void buscarClienteEnLista() {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("valor", "cliente");
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/venta/buscarclienteEdit.zul", null, map);
        window.doModal();
        clienteBuscado = servicioCliente.FindClienteForCedula(buscarCliente);
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarRazonSocial"})
    public void buscarClienteRazon(@BindingParam("valor") String valor) {
        buscarRazonSocial = valor;
        FindClienteLikeRazon();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarNombre"})
    public void buscarClienteNombre(@BindingParam("valor") String valor) {
        buscarNombre = valor;
        FindClienteLikeNombre();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarCedula"})
    public void buscarClienteCedula(@BindingParam("valor") String valor) {
        buscarCedula = valor;
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
        nuevoRegistro.setDescripcion("");
        if (clietipo.equals("0")) {
            nuevoRegistro.setTipoVenta("NORMAL");
        } else if (clietipo.equals("1")) {
            nuevoRegistro.setTipoVenta("PREFERENCIAL 1");
        } else if (clietipo.equals("2")) {
            nuevoRegistro.setTipoVenta("PREFERENCIAL 2");
        } else if (clietipo.equals("3")) {
            nuevoRegistro.setTipoVenta("PREFERENCIAL 3");
        }
        nuevoRegistro.setProducto(null);
        ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);

    }

    private void agregarRegistrovacio() {
        DetalleFacturaDAO nuevoRegistro = new DetalleFacturaDAO();
        nuevoRegistro.setProducto(productoBuscado);
        nuevoRegistro.setCantidad(BigDecimal.ZERO);
        nuevoRegistro.setSubTotal(BigDecimal.ZERO);
        nuevoRegistro.setDescripcion("");
        if (clietipo.equals("0")) {
            nuevoRegistro.setTipoVenta("NORMAL");
        } else if (clietipo.equals("1")) {
            nuevoRegistro.setTipoVenta("PREFERENCIAL 1");
        } else if (clietipo.equals("2")) {
            nuevoRegistro.setTipoVenta("PREFERENCIAL 2");
        } else if (clietipo.equals("3")) {
            nuevoRegistro.setTipoVenta("PREFERENCIAL 3");
        }
        nuevoRegistro.setProducto(null);
        ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel"})
    public void nuevaDescripcionGeneral() {

//        final HashMap<String, String> map = new HashMap<String, String>();
//        map.put("valor", "producto");
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/venta/buscarproductodiaria.zul", null, null);
        window.doModal();
        productoBuscado = servicioProducto.findByProdCodigo(codigoBusqueda);
        if (productoBuscado != null) {
            DetalleFacturaDAO nuevoRegistro = new DetalleFacturaDAO();
            nuevoRegistro.setProducto(productoBuscado);
            nuevoRegistro.setDescripcion(productoBuscado.getProdNombre());
            nuevoRegistro.setCantidad(BigDecimal.ZERO);
            nuevoRegistro.setCodigo(productoBuscado.getProdCodigo());
            if (clietipo.equals("0")) {
                nuevoRegistro.setSubTotal(productoBuscado.getPordCostoVentaFinal());
                nuevoRegistro.setTipoVenta("NORMAL");
            } else if (clietipo.equals("1")) {
                nuevoRegistro.setSubTotal(productoBuscado.getProdCostoPreferencial());
                nuevoRegistro.setTipoVenta("PREFERENCIAL 1");
            } else if (clietipo.equals("2")) {
                nuevoRegistro.setSubTotal(productoBuscado.getProdCostoPreferencialDos());
                nuevoRegistro.setTipoVenta("PREFERENCIAL 2");
            } else if (clietipo.equals("3")) {
                nuevoRegistro.setSubTotal(productoBuscado.getProdCostoPreferencialTres());
                nuevoRegistro.setTipoVenta("PREFERENCIAL 3");
            }
            //nuevoRegistro.setSubTotal(productoBuscado.getPordCostoVentaFinal());
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);
        }
        codigoBusqueda = "";
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel"})
    public void cambiarRegistro(@BindingParam("valor") DetalleFacturaDAO valor) {

        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("valor", "producto");
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/venta/buscarproductodiaria.zul", null, null);
        window.doModal();
        productoBuscado = servicioProducto.findByProdCodigo(codigoBusqueda);

        if (productoBuscado != null) {

            valor.setProducto(productoBuscado);
            valor.setCodigo(productoBuscado.getProdCodigo());
            //valor.setSubTotal(productoBuscado.getPordCostoVentaFinal());

            if (clietipo.equals("0")) {
                valor.setSubTotal(productoBuscado.getPordCostoVentaFinal());
                valor.setTipoVenta("NORMAL");
            } else if (clietipo.equals("1")) {
                valor.setSubTotal(productoBuscado.getProdCostoPreferencial());
                valor.setTipoVenta("PREFERENCIAL 1");
            } else if (clietipo.equals("2")) {
                valor.setSubTotal(productoBuscado.getProdCostoPreferencialDos());
                valor.setTipoVenta("PREFERENCIAL 2");
            } else if (clietipo.equals("3")) {
                valor.setSubTotal(productoBuscado.getProdCostoPreferencialTres());
                valor.setTipoVenta("PREFERENCIAL 3");
            }

        }
        codigoBusqueda = "";
    }

    private void calcularValoresTotales() {
        BigDecimal valorTotal = BigDecimal.ZERO;

        List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();
        if (listaPedido.size() > 0) {
            for (DetalleFacturaDAO item : listaPedido) {

                if (item.getProducto() != null) {
                    valorTotal = valorTotal.add(item.getTotal());
                }
            }
            subTotalCotizacion = valorTotal;
            subTotalCotizacion.setScale(4, RoundingMode.UP);
            BigDecimal valorIva = subTotalCotizacion.multiply(BigDecimal.valueOf(0.14));
            ivaCotizacion = valorIva;
            valorTotalCotizacion = subTotalCotizacion.add(ivaCotizacion);
            valorTotalCotizacion.setScale(4, RoundingMode.UP);
            ivaCotizacion.setScale(4, RoundingMode.UP);
//            System.out.println("**********************************************************");
//            System.out.println("valor total:::: " + valorTotal);
//            BigDecimal valorIva = valorTotalCotizacion.multiply(BigDecimal.valueOf(0.14));
//            subTotalCotizacion = valorTotal.add(valorIva.negate());
//            ivaCotizacion = valorIva;
//            valorTotalCotizacion = valorTotal;
//            subTotalCotizacion.setScale(3, RoundingMode.FLOOR);
//            ivaCotizacion.setScale(3, RoundingMode.FLOOR);
//            valorTotalCotizacion.setScale(3, RoundingMode.FLOOR);
        }
    }

    private void guardarFactura() {

        try {

            //armar la cabecera de la factura
            factura.setFacTipo("SINF");
            factura.setFacFecha(fechafacturacion);
            factura.setFacEstado(estdoFactura);
            factura.setFacNumero(0);
            factura.setIdCliente(clienteBuscado);
            factura.setIdUsuario(credential.getUsuarioSistema());
            factura.setFacSubtotal(subTotalCotizacion);
            factura.setFacIva(ivaCotizacion);
            factura.setFacTotal(valorTotalCotizacion);
            factura.setIdEstado(servicioEstadoFactura.findByEstCodigo(estdoFactura));
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

                servicioFactura.guardarFactura(detalleFactura, factura);


            }
            //ejecutamos el mensaje 
//            Clients.showNotification("Factura registrada con éxito", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 5000, true);
            // Aquí se pone en marcha el timer cada segundo. 


            //luego de los 5 segundos ejecutamos el redireccionamiento de la ventana
//            Messagebox.show("Factura registrada correctamente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
//            reporteGeneral();
            Executions.sendRedirect("/venta/ventadiaria.zul");
        } catch (Exception e) {
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion"})
    public void Guardar() {
        if (action.equals("create")) {
            guardarFactura();
        } else {
            servicioFactura.eliminar(factRecuperada);
            guardarFactura();
        }


    }

    @Command
    @NotifyChange({"listaProducto", "buscarNombreProd"})
    public void buscarLikeNombreProd(@BindingParam("valor") String valor) {
        buscarNombreProd = valor;
        findProductoLikeNombre();
    }

    @Command
    @NotifyChange({"listaProducto", "buscarCodigoProd"})
    public void buscarLikeCodigoProd(@BindingParam("valor") String valor) {
        buscarCodigoProd = valor;
        findProductoLikeCodigo();
    }

    private void findProductoLikeNombre() {
        listaProducto = servicioProducto.findLikeProdNombre(buscarNombreProd);
    }

    private void findProductoLikeCodigo() {
        listaProducto = servicioProducto.findLikeProdCodigo(buscarCodigoProd);
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

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion"})
    public void agregarItemPrincipal(@BindingParam("valor") String bandera) {
        List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();

        for (DetalleFacturaDAO item : listaPedido) {
            if (item.getProducto() == null) {
                ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).remove(item);
                break;
            }
        }



        codigoBusqueda = bandera;
        productoBuscado = servicioProducto.findByProdCodigo(codigoBusqueda);
        if (productoBuscado != null) {
            DetalleFacturaDAO nuevoRegistro = new DetalleFacturaDAO();
            nuevoRegistro.setProducto(productoBuscado);
            nuevoRegistro.setDescripcion(productoBuscado.getProdNombre());
            nuevoRegistro.setCantidad(BigDecimal.ZERO);
            nuevoRegistro.setCodigo(productoBuscado.getProdCodigo());
            if (clietipo.equals("0")) {
                nuevoRegistro.setSubTotal(productoBuscado.getPordCostoVentaFinal());
                nuevoRegistro.setTipoVenta("NORMAL");
            } else if (clietipo.equals("1")) {
                nuevoRegistro.setSubTotal(productoBuscado.getProdCostoPreferencial());
                nuevoRegistro.setTipoVenta("PREFERENCIAL 1");
            } else if (clietipo.equals("2")) {
                nuevoRegistro.setSubTotal(productoBuscado.getProdCostoPreferencialDos());
                nuevoRegistro.setTipoVenta("PREFERENCIAL 2");
            } else if (clietipo.equals("3")) {
                nuevoRegistro.setSubTotal(productoBuscado.getProdCostoPreferencialTres());
                nuevoRegistro.setTipoVenta("PREFERENCIAL 3");
            }
            //nuevoRegistro.setSubTotal(productoBuscado.getPordCostoVentaFinal());
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);
        }

        codigoBusqueda = "";
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion"})
    public void calcularValores(@BindingParam("valor") DetalleFacturaDAO valor) {
        try {
            // clietipo = valor.getTipoVenta();
            if (valor.getCantidad().intValue() > 0) {
                valor.setTotal(valor.getCantidad().multiply(valor.getSubTotal()));
                calcularValoresTotales();
                //clietipo = valor.getTipoVenta();
                //nuevo registro
                Producto buscadoPorCodigo = servicioProducto.findByProdCodigo(valor.getCodigo());
                if (buscadoPorCodigo != null) {
                    valor.setDescripcion(buscadoPorCodigo.getProdNombre());
                    //  valor.setSubTotal(buscadoPorCodigo.getPordCostoVentaFinal());

                    if (clietipo.equals("0")) {
                        valor.setSubTotal(buscadoPorCodigo.getPordCostoVentaFinal());
                        valor.setTipoVenta("NORMAL");
                    } else if (clietipo.equals("1")) {
                        valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencial());
                        valor.setTipoVenta("PREFERENCIAL 1");
                    } else if (clietipo.equals("2")) {
                        valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencialDos());
                        valor.setTipoVenta("PREFERENCIAL 2");
                    } else if (clietipo.equals("3")) {
                        valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencialTres());
                        valor.setTipoVenta("PREFERENCIAL 3");
                    }
                    valor.setProducto(buscadoPorCodigo);

                    //ingresa un registro vacio
                    boolean registroVacio = true;
                    List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();

                    for (DetalleFacturaDAO item : listaPedido) {
                        if (item.getProducto() == null) {
                            registroVacio = false;
                            break;
                        }
                    }

                    System.out.println("existe un vacio " + registroVacio);
                    if (registroVacio) {
                        DetalleFacturaDAO nuevoRegistro = new DetalleFacturaDAO();
                        nuevoRegistro.setProducto(productoBuscado);
                        nuevoRegistro.setCantidad(BigDecimal.ZERO);
                        nuevoRegistro.setSubTotal(BigDecimal.ZERO);
                        if (clietipo.equals("0")) {
                            nuevoRegistro.setTipoVenta("NORMAL");
                        } else if (clietipo.equals("1")) {
                            nuevoRegistro.setTipoVenta("PREFERENCIAL 1");
                        } else if (clietipo.equals("2")) {
                            nuevoRegistro.setTipoVenta("PREFERENCIAL 2");
                        } else if (clietipo.equals("3")) {
                            nuevoRegistro.setTipoVenta("PREFERENCIAL 3");
                        }
                        nuevoRegistro.setDescripcion("");
                        nuevoRegistro.setProducto(null);
                        ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);
                    }


                }
            }
            calcularValoresTotales();
        } catch (Exception e) {
            Messagebox.show("Ocurrio un error al calcular los valores", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange("listaDetalleFacturaDAOMOdel")
    public void buscarPorCodigo(@BindingParam("valor") DetalleFacturaDAO valor) {
//        valorSystem.out.println("producto seleccionado " + valor.getProdCodigo());
        Producto buscadoPorCodigo = servicioProducto.findByProdCodigo(valor.getCodigo());
        if (buscadoPorCodigo != null) {
            valor.setDescripcion(buscadoPorCodigo.getProdNombre());
            //valor.setSubTotal(buscadoPorCodigo.getPordCostoVentaFinal());
            if (clietipo.equals("0")) {
                valor.setSubTotal(buscadoPorCodigo.getPordCostoVentaFinal());
                valor.setTipoVenta("NORMAL");
            } else if (clietipo.equals("1")) {
                valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencial());
                valor.setTipoVenta("PREFERENCIAL 1");
            } else if (clietipo.equals("2")) {
                valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencialDos());
                valor.setTipoVenta("PREFERENCIAL 2");
            } else if (clietipo.equals("3")) {
                valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencialTres());
                valor.setTipoVenta("PREFERENCIAL 3");
            }
            valor.setProducto(buscadoPorCodigo);

            //ingresa un registro vacio
            boolean registroVacio = true;
            List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();

            for (DetalleFacturaDAO item : listaPedido) {
                if (item.getProducto() == null) {
                    registroVacio = false;
                    break;
                }
            }

            System.out.println("existe un vacio " + registroVacio);
            if (registroVacio) {
                DetalleFacturaDAO nuevoRegistro = new DetalleFacturaDAO();
                nuevoRegistro.setProducto(productoBuscado);
                nuevoRegistro.setCantidad(BigDecimal.ZERO);
                nuevoRegistro.setSubTotal(BigDecimal.ZERO);
                nuevoRegistro.setDescripcion("");
                if (clietipo.equals("0")) {
                    nuevoRegistro.setTipoVenta("NORMAL");
                } else if (clietipo.equals("1")) {
                    nuevoRegistro.setTipoVenta("PREFERENCIAL 1");
                } else if (clietipo.equals("2")) {
                    nuevoRegistro.setTipoVenta("PREFERENCIAL 2");
                } else if (clietipo.equals("3")) {
                    nuevoRegistro.setTipoVenta("PREFERENCIAL 3");
                }
                nuevoRegistro.setProducto(null);
                nuevoRegistro.setElementoNuevo("SI");
                ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);
            }


        }
    }

    public void reporteGeneral() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {


        con = ConexionReportes.Conexion.conexion();
        //con = conexionReportes.conexion();


        String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");
        String reportPath = "";
        reportPath = reportFile + "/notaventa.jasper";

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

    @Command
    @NotifyChange({"cambio"})
    public void calcularCambio() {
        cambio = cobro.add(valorUltimaVenta.negate());
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel",
        "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion"})
    public void buscarVentaFecha() {
        iniciarVentaDiaria();
    }

    //productos principales
    public Producto getP1() {
        return p1;
    }

    public void setP1(Producto p1) {
        this.p1 = p1;
    }

    public Producto getP2() {
        return p2;
    }

    public void setP2(Producto p2) {
        this.p2 = p2;
    }

    public Producto getP3() {
        return p3;
    }

    public void setP3(Producto p3) {
        this.p3 = p3;
    }

    public Producto getP4() {
        return p4;
    }

    public void setP4(Producto p4) {
        this.p4 = p4;
    }

    public Producto getP5() {
        return p5;
    }

    public void setP5(Producto p5) {
        this.p5 = p5;
    }

    public Producto getP6() {
        return p6;
    }

    public void setP6(Producto p6) {
        this.p6 = p6;
    }

    public Producto getP7() {
        return p7;
    }

    public void setP7(Producto p7) {
        this.p7 = p7;
    }

    public Producto getP8() {
        return p8;
    }

    public void setP8(Producto p8) {
        this.p8 = p8;
    }

    public Producto getP9() {
        return p9;
    }

    public void setP9(Producto p9) {
        this.p9 = p9;
    }

    public Producto getP10() {
        return p10;
    }

    public void setP10(Producto p10) {
        this.p10 = p10;
    }
}
