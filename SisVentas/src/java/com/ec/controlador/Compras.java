/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.Cliente;
import com.ec.entidad.DetalleKardex;
import com.ec.entidad.Kardex;
import com.ec.entidad.Producto;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Proveedores;
import com.ec.entidad.Tipokardex;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCompra;
import com.ec.servicio.ServicioDetalleKardex;
import com.ec.servicio.ServicioEstadoFactura;
import com.ec.servicio.ServicioKardex;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioProveedor;
import com.ec.servicio.ServicioTipoKardex;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.DetalleCompraUtil;
import com.ec.untilitario.TotalKardex;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class Compras {

    @Wire
    Window windowProveedorBuscar;
    @Wire
    Window wProductoBuscar;
    ServicioCompra servicioCompra = new ServicioCompra();

    ServicioProveedor servicioProveedor = new ServicioProveedor();
    ServicioEstadoFactura servicioEstadoFactura = new ServicioEstadoFactura();
    //variables
    private CabeceraCompra cabeceraCompra = new CabeceraCompra();
    private DetalleCompraUtil compraProductos = new DetalleCompraUtil();
    private ListModelList<DetalleCompraUtil> listaCompraProductosMOdel;
    private List<DetalleCompraUtil> listaCompraProductosDatos = new ArrayList<DetalleCompraUtil>();
    private Set<DetalleCompraUtil> registrosSeleccionados = new HashSet<DetalleCompraUtil>();
//credenc9ial
    UserCredential credential = new UserCredential();
    private Date fechafacturacion = new Date();
//valores
    //valorTotalCotizacion
    private BigDecimal valorTotalFactura = BigDecimal.ZERO;
    private BigDecimal subTotalFactura = BigDecimal.ZERO;
    private BigDecimal ivaFactura = BigDecimal.ZERO;
    private BigDecimal subTotalFacturaCero = BigDecimal.ZERO;
    //buscar proveedor
    public Proveedores proveedorSeleccionado = new Proveedores("");
    private List<Proveedores> listaProveedoresAll = new ArrayList<Proveedores>();
    public static String buscarCedulaProveedor = "";
    public String buscarProvCedula = "";
    public String buscarProvNombre = "";
    //busacar producto
    ServicioProducto servicioProducto = new ServicioProducto();
    private List<Producto> listaProducto = new ArrayList<Producto>();
    private String buscarNombreProd = "";
    private String buscarCodigoProd = "";
    private Producto productoBuscado = new Producto();
    public static String codigoBusqueda = "";
    private String numeroFactura = "";
    private String estdoFactura = "PA";
    //Lista para recuperar productos
    private List<Kardex> listaKardexProducto = new ArrayList<Kardex>();
    private Parametrizar parametrizar = null;
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    private ListModelList<DetalleFacturaDAO> listaDetalleFacturaDAOMOdel;
    public Cliente clienteBuscado = new Cliente("");
    private static BigDecimal DESCUENTOGENERAL = BigDecimal.valueOf(5.0);
    private String clietipo = "0";
    @Wire
    Textbox idBusquedaProd;
    /*DETALLE DEL KARDEX Y DETALLE KARDEX*/
    ServicioKardex servicioKardex = new ServicioKardex();
    ServicioDetalleKardex servicioDetalleKardex = new ServicioDetalleKardex();
    ServicioTipoKardex servicioTipoKardex = new ServicioTipoKardex();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") String valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor == null) {
        } else {
        }
        agregarRegistroVacio();
        buscarProveedoresLikeNombre();
        findKardexProductoLikeNombre();
        fechafacturacion = new Date();
        parametrizar = servicioParametrizar.FindALlParametrizar();

    }

    private void buscarProveedoresLikeNombre() {
        listaProveedoresAll = servicioProveedor.findLikeProvNombre("");
    }

    public Compras() {

        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        getDetalleCompra();
    }

    private void getDetalleCompra() {
        setListaCompraProductosMOdel(new ListModelList<DetalleCompraUtil>(getListaCompraProductosDatos()));
        ((ListModelList<DetalleCompraUtil>) listaCompraProductosMOdel).setMultiple(true);
    }

    @Command
    public void seleccionarRegistros() {
        registrosSeleccionados = ((ListModelList<DetalleCompraUtil>) getListaCompraProductosMOdel()).getSelection();
    }

    public void agregarRegistroVacio() {

        DetalleCompraUtil nuevoRegistro = new DetalleCompraUtil(BigDecimal.ZERO, "", BigDecimal.ZERO, BigDecimal.ZERO);
        nuevoRegistro.setProducto(null);
        ((ListModelList<DetalleCompraUtil>) listaCompraProductosMOdel).add(nuevoRegistro);

    }

    public CabeceraCompra getCabeceraCompra() {
        return cabeceraCompra;
    }

    public void setCabeceraCompra(CabeceraCompra cabeceraCompra) {
        this.cabeceraCompra = cabeceraCompra;
    }

    public DetalleCompraUtil getCompraProductos() {
        return compraProductos;
    }

    public void setCompraProductos(DetalleCompraUtil compraProductos) {
        this.compraProductos = compraProductos;
    }

    public ListModelList<DetalleCompraUtil> getListaCompraProductosMOdel() {
        return listaCompraProductosMOdel;
    }

    public void setListaCompraProductosMOdel(ListModelList<DetalleCompraUtil> listaCompraProductosMOdel) {
        this.listaCompraProductosMOdel = listaCompraProductosMOdel;
    }

    public List<DetalleCompraUtil> getListaCompraProductosDatos() {
        return listaCompraProductosDatos;
    }

    public void setListaCompraProductosDatos(List<DetalleCompraUtil> listaCompraProductosDatos) {
        this.listaCompraProductosDatos = listaCompraProductosDatos;
    }

    public Set<DetalleCompraUtil> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<DetalleCompraUtil> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }

    public Date getFechafacturacion() {
        return fechafacturacion;
    }

    public void setFechafacturacion(Date fechafacturacion) {
        this.fechafacturacion = fechafacturacion;
    }

    public BigDecimal getValorTotalFactura() {
        return valorTotalFactura;
    }

    public void setValorTotalFactura(BigDecimal valorTotalFactura) {
        this.valorTotalFactura = valorTotalFactura;
    }

    public BigDecimal getSubTotalFactura() {
        return subTotalFactura;
    }

    public void setSubTotalFactura(BigDecimal subTotalFactura) {
        this.subTotalFactura = subTotalFactura;
    }

    public BigDecimal getIvaFactura() {
        return ivaFactura;
    }

    public void setIvaFactura(BigDecimal ivaFactura) {
        this.ivaFactura = ivaFactura;
    }

    public Proveedores getProveedorSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(Proveedores proveedorSeleccionado) {
        this.proveedorSeleccionado = proveedorSeleccionado;
    }

    public List<Proveedores> getListaProveedoresAll() {
        return listaProveedoresAll;
    }

    public void setListaProveedoresAll(List<Proveedores> listaProveedoresAll) {
        this.listaProveedoresAll = listaProveedoresAll;
    }

    public static String getBuscarCedulaProveedor() {
        return buscarCedulaProveedor;
    }

    public static void setBuscarCedulaProveedor(String buscarCedulaProveedor) {
        Compras.buscarCedulaProveedor = buscarCedulaProveedor;
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

    public String getBuscarCodigoProd() {
        return buscarCodigoProd;
    }

    public void setBuscarCodigoProd(String buscarCodigoProd) {
        this.buscarCodigoProd = buscarCodigoProd;
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
        Compras.codigoBusqueda = codigoBusqueda;
    }

    public String getBuscarProvCedula() {
        return buscarProvCedula;
    }

    public void setBuscarProvCedula(String buscarProvCedula) {
        this.buscarProvCedula = buscarProvCedula;
    }

    public String getBuscarProvNombre() {
        return buscarProvNombre;
    }

    public void setBuscarProvNombre(String buscarProvNombre) {
        this.buscarProvNombre = buscarProvNombre;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getEstdoFactura() {
        return estdoFactura;
    }

    public void setEstdoFactura(String estdoFactura) {
        this.estdoFactura = estdoFactura;
    }

    @Command
    @NotifyChange({"listaProveedortesAll", "proveedorSeleccionado", "fechaEmision"})
    public void buscarProveedorEnLista() {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("valor", "proveedor");
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/compra/buscarproveedor.zul", null, map);
        window.doModal();
        proveedorSeleccionado = servicioProveedor.findProvCedula(buscarCedulaProveedor);
    }

    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionarProveedorLista(@BindingParam("valor") Proveedores valor) {
//        System.out.println("cliente seleccionado " + valor.getProvCedula());
        buscarCedulaProveedor = valor.getProvCedula();
        windowProveedorBuscar.detach();

    }

    //buscar el producto y ponerlo en la lista
    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionarProductoLista(@BindingParam("valor") Producto valor) {
//        System.out.println("producto seleccionado " + valor.getProdCodigo());
        codigoBusqueda = valor.getProdCodigo();
        wProductoBuscar.detach();

    }

    @Command
    @NotifyChange({"listaCompraProductosMOdel"})
    public void cambiarRegistro(@BindingParam("valor") DetalleCompraUtil valor) {

        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("valor", "producto");
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/compra/buscarproducto.zul", null, map);
        window.doModal();
        productoBuscado = servicioProducto.findByProdCodigo(codigoBusqueda);
        if (productoBuscado != null) {
            valor.setProducto(productoBuscado);
            valor.setCodigo(productoBuscado.getProdCodigo());
            valor.setSubtotal(productoBuscado.getPordCostoVentaRef());
        }
        codigoBusqueda = "";
    }
    //calcular los valores de la lista

    @Command
    @NotifyChange({"listaCompraProductosMOdel", "subTotalFactura", "ivaFactura", "valorTotalFactura", "subTotalFacturaCero"})
    public void calcularValores(@BindingParam("valor") DetalleCompraUtil valor) {
        try {

            if (valor.getCantidad().intValue() > 0) {

                //calcularValoresTotales();
                //nuevo registro
                Producto buscadoPorCodigo = servicioProducto.findByProdCodigo(valor.getCodigo());
                if (buscadoPorCodigo != null) {
                    valor.setDescripcion(buscadoPorCodigo.getProdNombre());
//                    valor.setSubtotal(buscadoPorCodigo.getPordCostoVentaRef());
                    valor.setTotalTRanformado(valor.getFactor() != null ? (valor.getFactor().multiply(valor.getCantidad())) : BigDecimal.ZERO);
                    valor.setProducto(buscadoPorCodigo);
                    valor.setTotal(valor.getTotalTRanformado().multiply(valor.getSubtotal()));
                    //ingresa un registro vacio
                    boolean registroVacio = true;
                    List<DetalleCompraUtil> listaPedido = listaCompraProductosMOdel.getInnerList();

                    for (DetalleCompraUtil item : listaPedido) {
                        if (item.getProducto() == null) {
                            registroVacio = false;
                            break;
                        }
                    }

                    System.out.println("existe un vacio " + registroVacio);
                    if (registroVacio) {
                        DetalleCompraUtil nuevoRegistro = new DetalleCompraUtil();
                        nuevoRegistro.setProducto(productoBuscado);
                        nuevoRegistro.setCantidad(BigDecimal.ZERO);
                        nuevoRegistro.setSubtotal(BigDecimal.ZERO);
                        nuevoRegistro.setDescripcion("");
                        nuevoRegistro.setProducto(null);
                        ((ListModelList<DetalleCompraUtil>) listaCompraProductosMOdel).add(nuevoRegistro);
                    }

                }
            }
            calcularValoresTotales();
        } catch (Exception e) {
            Messagebox.show("Ocurrio un error al calcular los valores", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    private void calcularValoresTotales() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        BigDecimal valorTotalCero = BigDecimal.ZERO;

        List<DetalleCompraUtil> listaPedido = listaCompraProductosMOdel.getInnerList();
        if (listaPedido.size() > 0) {
            for (DetalleCompraUtil item : listaPedido) {

                if (item.getProducto() != null) {
                    valorTotal = valorTotal.add(item.getProducto().getProdGrabaIva() ? item.getTotal() : BigDecimal.ZERO);
                    valorTotalCero = valorTotalCero.add(!item.getProducto().getProdGrabaIva() ? item.getTotal() : BigDecimal.ZERO);
                }
            }
            System.out.println("**********************************************************");
            System.out.println("valor total:::: " + valorTotal);
            subTotalFacturaCero = valorTotalCero;
            subTotalFactura = valorTotal;
            BigDecimal valorIva = subTotalFactura.multiply(BigDecimal.valueOf(0.12));
            ivaFactura = valorIva;
            valorTotalFactura = valorTotal.add(valorIva).add(subTotalFacturaCero);
            subTotalFactura.setScale(4, RoundingMode.FLOOR);
            ivaFactura.setScale(4, RoundingMode.FLOOR);
            valorTotalFactura.setScale(4, RoundingMode.FLOOR);
        }
    }

//producto
    @Command
    @NotifyChange({"listaKardexProducto", "buscarCodigoProd"})
    public void buscarLikeCodigoProd(@BindingParam("valor") String valor) {
        buscarCodigoProd = valor;
        findProductoLikeCodigo();
    }

    @Command
    @NotifyChange({"listaKardexProducto", "buscarNombreProd"})
    public void buscarLikeNombreProd() {

        findProductoLikeNombre();
    }

    private void findProductoLikeCodigo() {
        listaProducto = servicioProducto.findLikeProdCodigo(buscarCodigoProd);
    }

    private void findProductoLikeNombre() {
        listaProducto = servicioProducto.findLikeProdNombre(buscarNombreProd);
    }
//proveedor

    @Command
    @NotifyChange({"listaProveedoresAll", "buscarProvCedula"})
    public void buscarProveedorCedula() {

        findProveedorCedula();
    }

    @Command
    @NotifyChange({"listaProveedoresAll", "buscarProvNombre"})
    public void buscarProveedorNombre() {

        findProveedorLikeNombre();
    }
//    @Command
//    @NotifyChange({"listaProducto", "buscarCodigoProd"})
//    public void buscarLikeCodigoProd() {
//
//        findProductoLikeCodigo();
//    }

    private void findProveedorLikeNombre() {
        listaProveedoresAll = servicioProveedor.findLikeProvNombre(buscarProvNombre);
    }

    private void findProveedorCedula() {
        listaProveedoresAll = servicioProveedor.findProveedorCedula(buscarProvCedula);
    }

    //para buscar karder y mostrar en productos
    @Command
    @NotifyChange({"listaKardexProducto", "buscarNombreProd"})
    public void buscarLikeKardexNombreProdComp() {

        findKardexProductoLikeNombre();
    }

    @Command
    @NotifyChange({"listaKardexProducto", "buscarCodigoProd"})
    public void buscarLikeKardexCodigoProdComp() {

        findKardexProductoLikeCodigo();
    }

    private void findKardexProductoLikeNombre() {
        listaKardexProducto = servicioKardex.findByCodOrName(buscarCodigoProd, buscarNombreProd);
    }

    private void findKardexProductoLikeCodigo() {
        listaKardexProducto = servicioKardex.findByCodOrName(buscarCodigoProd, buscarNombreProd);
    }

    //guardar la factura
    @Command
    @NotifyChange({"listaCompraProductosMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion"})
    public void Guardar() {
        if (!proveedorSeleccionado.getProvCedula().equals("")
                && !numeroFactura.equals("")) {
            guardarCompra();

        } else {
            Messagebox.show("Verifique el proveedor, numero de factura, numero de autorizacion", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    private void guardarCompra() {

        try {
            BigDecimal factorIva = (parametrizar.getParIva().divide(BigDecimal.valueOf(100.0)));
            BigDecimal facturIvaMasBase = (factorIva.add(BigDecimal.ONE));
            if (true) {
                //armar la cabecera de la factura

                cabeceraCompra.setCabRetencionAutori("N");
                cabeceraCompra.setCabFechaEmision(fechafacturacion);
                cabeceraCompra.setCabFecha(new Date());
                cabeceraCompra.setCabEstado(estdoFactura);
                cabeceraCompra.setCabNumFactura(numeroFactura);
                cabeceraCompra.setIdProveedor(proveedorSeleccionado);
                cabeceraCompra.setCabProveedor(proveedorSeleccionado.getProvNombre());
                cabeceraCompra.setIdUsuario(credential.getUsuarioSistema());
                cabeceraCompra.setCabSubTotal(subTotalFactura);
                cabeceraCompra.setCabIva(ivaFactura);
                cabeceraCompra.setCabTotal(valorTotalFactura);
                cabeceraCompra.setDrcCodigoSustento("01");
                cabeceraCompra.setCabSubTotalCero(subTotalFacturaCero);
                cabeceraCompra.setIdEstado(servicioEstadoFactura.findByEstCodigo(estdoFactura));
                if (cabeceraCompra.getCabEstado().equals("PE")) {
                    //para realizar un abono
                } else {
                }
                //armar el detalle de la factura
                List<DetalleCompraUtil> detalleCompra = new ArrayList<DetalleCompraUtil>();
                List<DetalleCompraUtil> listaPedido = listaCompraProductosMOdel.getInnerList();
                if (listaPedido.size() > 0) {
                    for (DetalleCompraUtil item : listaPedido) {
                        if (item.getProducto() != null) {
                            Producto actualizaPrecio = item.getProducto();
                            if (actualizaPrecio.getProdGrabaIva()) {
                                actualizaPrecio.setPordCostoCompra(item.getSubtotal());
                                actualizaPrecio.setPordCostoVentaRef(ArchivoUtils.redondearDecimales(item.getSubtotal().multiply(facturIvaMasBase), 4));
                            } else {
                                actualizaPrecio.setPordCostoCompra(item.getSubtotal());
                                actualizaPrecio.setPordCostoVentaRef(item.getSubtotal());
                            }
                            servicioProducto.modificar(actualizaPrecio);
                            detalleCompra.add(item);
                        }

                    }

                    //implementar el guaradado en cascada para las compras
                    servicioCompra.guardarCompra(detalleCompra, cabeceraCompra);

                    /*INGRESAMOS LO MOVIMIENTOS AL KARDEX*/
                    Kardex kardex = null;
                    DetalleKardex detalleKardex = null;

                    for (DetalleCompraUtil item : listaPedido) {
                        if (item.getProducto() != null) {

                            Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla("ING");
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
                            detalleKardex.setDetkDetalles("Aumenta al kardex facturacion con: FACTC-" + cabeceraCompra.getCabNumFactura());
                            detalleKardex.setIdCompra(cabeceraCompra);
                            detalleKardex.setDetkIngresoCantidadSinTransformar(item.getCantidad());
                            detalleKardex.setDetkUnidadOrigen(item.getProducto().getProdUnidadMedida() != null ? item.getProducto().getProdUnidadMedida() : "S/U");
                            detalleKardex.setDetkUnidadFin(item.getProducto().getProdUnidadConversion() != null ? item.getProducto().getProdUnidadConversion() : "S/U");
                            //se cambia a la conversion 
                            //detalleKardex.setDetkCantidad(item.getCantidad());
                            detalleKardex.setDetkCantidad(item.getTotalTRanformado());
                            servicioDetalleKardex.crear(detalleKardex);
                            TotalKardex totales = servicioKardex.totalesForKardex(kardex);
                            BigDecimal total = totales.getTotalKardex();
                            kardex.setKarTotal(total);
                            servicioKardex.modificar(kardex);
                        }

                    }
                }

                Executions.sendRedirect("/compra/compras.zul");
            } else {
                Clients.showNotification("Verifique el numero de factura", "error", null, "start_before", 2000, true);
            }
        } catch (Exception e) {
            System.out.println("error guardar compra " + e.getMessage());
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    //busqueda del producto
    @Command
    @NotifyChange({"listaCompraProductosMOdel", "subTotalFactura", "ivaFactura", "valorTotalFactura", "subTotalFacturaCero"})
    public void eliminarRegistros() {
        if (registrosSeleccionados.size() > 0) {
            ((ListModelList<DetalleCompraUtil>) listaCompraProductosMOdel).removeAll(registrosSeleccionados);
            calcularValoresTotales();

        } else {
            Messagebox.show("Seleccione al menos un registro para eliminar", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    public List<Kardex> getListaKardexProducto() {
        return listaKardexProducto;
    }

    public void setListaKardexProducto(List<Kardex> listaKardexProducto) {
        this.listaKardexProducto = listaKardexProducto;
    }


    /*AGREGAMOS DESDE LA LSITA */
    @Command
    @NotifyChange({"listaCompraProductosMOdel", "subTotalFactura", "ivaFactura", "valorTotalFactura", "subTotalFacturaCero"})
    public void agregarItemLista(@BindingParam("valor") Producto producto) {


        /*calcula con el iva para todo el 12%*/
        BigDecimal factorIva = (producto.getProdIva().divide(BigDecimal.valueOf(100.0)));
        BigDecimal factorSacarSubtotal = (factorIva.add(BigDecimal.ONE));

        List<DetalleCompraUtil> listaPedido = listaCompraProductosMOdel.getInnerList();

        for (DetalleCompraUtil item : listaPedido) {
            if (item.getProducto() == null) {
                ((ListModelList<DetalleCompraUtil>) listaCompraProductosMOdel).remove(item);
                break;
            }
        }
        productoBuscado = producto;
        DetalleCompraUtil valor = new DetalleCompraUtil();
        if (productoBuscado != null) {
            valor.setCantidad(BigDecimal.ONE);
            valor.setFactor(productoBuscado.getProdFactorConversion() != null ? productoBuscado.getProdFactorConversion() : BigDecimal.ONE);
            valor.setTotalTRanformado(valor.getCantidad().multiply(valor.getFactor()).setScale(2));
            valor.setProducto(productoBuscado);
            valor.setCodigo(productoBuscado.getProdCodigo());
            valor.setSubtotal(productoBuscado.getPordCostoCompra());
            valor.setTotal(valor.getSubtotal().multiply(valor.getCantidad()));
        }

        ((ListModelList<DetalleCompraUtil>) listaCompraProductosMOdel).add(valor);

        //ingresa un registro vacio
        boolean registroVacio = true;
        List<DetalleCompraUtil> listaPedidoPost = listaCompraProductosMOdel.getInnerList();

        for (DetalleCompraUtil item : listaPedidoPost) {
            if (item.getProducto() == null) {
                registroVacio = false;
                break;
            }
        }

        System.out.println("existe un vacio " + registroVacio);
        if (registroVacio) {
            DetalleCompraUtil nuevoRegistroPost = new DetalleCompraUtil();
//                nuevoRegistroPost.setProducto(productoBuscado);
            nuevoRegistroPost.setProducto(null);
            nuevoRegistroPost.setCodigo("");
            nuevoRegistroPost.setSubtotal(BigDecimal.ZERO);
            ((ListModelList<DetalleCompraUtil>) listaCompraProductosMOdel).add(nuevoRegistroPost);
        }

        calcularValoresTotales();
    }

    public BigDecimal getSubTotalFacturaCero() {
        return subTotalFacturaCero;
    }

    public void setSubTotalFacturaCero(BigDecimal subTotalFacturaCero) {
        this.subTotalFacturaCero = subTotalFacturaCero;
    }

}
