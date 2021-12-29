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
import org.zkoss.bind.annotation.ExecutionArgParam;
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
public class FacturarEditar {

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
//reporte
    AMedia fileContent = null;
    Connection con = null;
    //valor a recuperar en la factura
    private Integer idFactura = 0;
    private String action = "create";
    Factura factRecuperada = new Factura();
    //cambio
    private BigDecimal cobro = BigDecimal.ZERO;
    private BigDecimal cambio = BigDecimal.ZERO;
    private String facturaDescripcion = "";

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") String valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor == null) {
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).clear();
            agregarRegistroVacio();
        } else {
            action = "update";
            System.out.println("================ " + valor);
            if (valor.trim().equals("cliente") || valor.trim().equals("producto")) {
            } else {
                idFactura = Integer.valueOf(valor);
                cargarRegistros();
            }


        }

        FindClienteLikeNombre();
        findProductoLikeNombre();

        fechafacturacion = new Date();

    }

    private void cargarRegistros() {

        factRecuperada = servicioFactura.findFirIdFact(idFactura);
        //numero de factura
        numeroFactura = factRecuperada.getFacNumero();

        //recuperar detalle
        facturaDescripcion = factRecuperada.getFacDescripcion();
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
        for (DetalleFactura detalleFactura : detalle) {
            System.out.println("detalle " + detalleFactura.getDetDescripcion());
            DetalleFacturaDAO nuevoRegistro = new DetalleFacturaDAO();
            nuevoRegistro.setProducto(detalleFactura.getIdProducto());
            nuevoRegistro.setCantidad(detalleFactura.getDetCantidad());
            nuevoRegistro.setSubTotal(detalleFactura.getDetSubtotal());
            nuevoRegistro.setTotal(detalleFactura.getDetTotal());
            nuevoRegistro.setCodigo(detalleFactura.getIdProducto().getProdCodigo());
            nuevoRegistro.setDescripcion(detalleFactura.getDetDescripcion());
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);
        }

    }

    public FacturarEditar() {
        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        getDetallefactura();
        Factura recuperada = servicioFactura.FindUltimaFactura();
        if (recuperada != null) {
            numeroFactura = recuperada.getFacNumero() + 1;
        } else {
            numeroFactura = 1;
        }


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
        FacturarEditar.buscarCliente = buscarCliente;
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
        FacturarEditar.codigoBusqueda = codigoBusqueda;
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

    public String getFacturaDescripcion() {
        return facturaDescripcion;
    }

    public void setFacturaDescripcion(String facturaDescripcion) {
        this.facturaDescripcion = facturaDescripcion;
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
        nuevoRegistro.setDescripcion("");
        nuevoRegistro.setProducto(null);
        ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);

    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel"})
    public void nuevaDescripcionGeneral() {

        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("valor", "producto");
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/venta/buscarproductoEdit.zul", null, map);
        window.doModal();
        productoBuscado = servicioProducto.findByProdCodigo(codigoBusqueda);
        if (productoBuscado != null) {
            DetalleFacturaDAO nuevoRegistro = new DetalleFacturaDAO();
            nuevoRegistro.setProducto(productoBuscado);
            nuevoRegistro.setDescripcion(productoBuscado.getProdNombre());
            nuevoRegistro.setCantidad(BigDecimal.ZERO);
            nuevoRegistro.setCodigo(productoBuscado.getProdCodigo());
            // nuevoRegistro.setSubTotal(productoBuscado.getPordCostoVentaFinal());
            if (clienteBuscado.getClietipo() == 0) {
                nuevoRegistro.setSubTotal(productoBuscado.getPordCostoVentaFinal());
            } else if (clienteBuscado.getClietipo() == 1) {
                nuevoRegistro.setSubTotal(productoBuscado.getProdCostoPreferencial());
            } else if (clienteBuscado.getClietipo() == 2) {
                nuevoRegistro.setSubTotal(productoBuscado.getProdCostoPreferencialDos());
            } else if (clienteBuscado.getClietipo() == 3) {
                nuevoRegistro.setSubTotal(productoBuscado.getProdCostoPreferencialTres());
            }
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).add(nuevoRegistro);
        }
        codigoBusqueda = "";
    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel"})
    public void cambiarRegistro(@BindingParam("valor") DetalleFacturaDAO valor) {
        if (!clienteBuscado.getCliCedula().equals("")) {
            final HashMap<String, String> map = new HashMap<String, String>();
            map.put("valor", "producto");
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/venta/buscarproductoEdit.zul", null, map);
            window.doModal();
            productoBuscado = servicioProducto.findByProdCodigo(codigoBusqueda);
            if (productoBuscado != null) {
                valor.setProducto(productoBuscado);
                valor.setCodigo(productoBuscado.getProdCodigo());
                //  valor.setSubTotal(productoBuscado.getPordCostoVentaFinal());
                if (clienteBuscado.getClietipo() == 0) {
                    valor.setSubTotal(productoBuscado.getPordCostoVentaFinal());
                } else if (clienteBuscado.getClietipo() == 1) {
                    valor.setSubTotal(productoBuscado.getProdCostoPreferencial());
                } else if (clienteBuscado.getClietipo() == 2) {
                    valor.setSubTotal(productoBuscado.getProdCostoPreferencialDos());
                } else if (clienteBuscado.getClietipo() == 3) {
                    valor.setSubTotal(productoBuscado.getProdCostoPreferencialTres());
                }
            }
            codigoBusqueda = "";
        } else {
            Messagebox.show("Verifique el cliente", "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
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
            factura.setFacTipo("FACT");
            factura.setFacFecha(fechafacturacion);
            factura.setFacEstado(estdoFactura);
            factura.setFacNumero(numeroFactura);
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
            reporteGeneral();
            Executions.sendRedirect("/venta/listanotaventa.zul");
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
    public void calcularValores(@BindingParam("valor") DetalleFacturaDAO valor) {
        try {
            if (valor.getCantidad().intValue() > 0) {
                valor.setTotal(valor.getCantidad().multiply(valor.getSubTotal()));
                calcularValoresTotales();
                //nuevo registro
                Producto buscadoPorCodigo = servicioProducto.findByProdCodigo(valor.getCodigo());
                if (buscadoPorCodigo != null) {
                    valor.setDescripcion(buscadoPorCodigo.getProdNombre());
                   // valor.setSubTotal(buscadoPorCodigo.getPordCostoVentaFinal());
                     if (clienteBuscado.getClietipo() == 0) {
                        valor.setSubTotal(buscadoPorCodigo.getPordCostoVentaFinal());
                    } else if (clienteBuscado.getClietipo() == 1) {
                        valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencial());
                    } else if (clienteBuscado.getClietipo() == 2) {
                        valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencialDos());
                    } else if (clienteBuscado.getClietipo() == 3) {
                        valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencialTres());
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
            if (clienteBuscado.getClietipo() == 0) {
                valor.setSubTotal(buscadoPorCodigo.getPordCostoVentaFinal());
            } else if (clienteBuscado.getClietipo() == 1) {
                valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencial());
            } else if (clienteBuscado.getClietipo() == 2) {
                valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencialDos());
            } else if (clienteBuscado.getClietipo() == 3) {
                valor.setSubTotal(buscadoPorCodigo.getProdCostoPreferencialTres());
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
                nuevoRegistro.setProducto(null);
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
        cambio = cobro.add(valorTotalCotizacion.negate());
    }
}
