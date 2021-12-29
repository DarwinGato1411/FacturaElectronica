/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Cliente;
import com.ec.entidad.DetalleGuiaremision;
import com.ec.entidad.Guiaremision;
import com.ec.entidad.Producto;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.Transportista;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCliente;
import com.ec.servicio.ServicioDetalleGuia;
import com.ec.servicio.ServicioGuia;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.servicio.ServicioTransportista;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.DetalleGuiaDao;
import com.ec.untilitario.ParamFactura;
import java.math.BigDecimal;
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
public class GuiaRemision {

    @Wire
    Window windowGuiaRemision;
    @Wire
    Window wCliente;
    @Wire
    Window windowClienteBuscarGuia;

    @Wire("#txtNumeroGuiaRecibida")
    Textbox txtNumeroGuiaRecibida;

    ServicioCliente servicioCliente = new ServicioCliente();
    ServicioTransportista servicioTransportista = new ServicioTransportista();
    ServicioGuia servicioGuia = new ServicioGuia();

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();

    ServicioDetalleGuia servicioDetalleGuia = new ServicioDetalleGuia();
    private List<Producto> listaProductoCmb = new ArrayList<Producto>();

    //crear un factura nueva        
    private ListModelList<DetalleGuiaDao> listaGuiaModel;
    private List<DetalleGuiaDao> listaGuiaRemisionDatos = new ArrayList<DetalleGuiaDao>();
    public static Set<DetalleGuiaDao> seleccionGuiaRemision = new HashSet<DetalleGuiaDao>();

    private List<Transportista> listaTransportistas = new ArrayList<Transportista>();

    private Date fechaGuia = new Date();
    UserCredential credential = new UserCredential();

    public Cliente clienteBuscado = new Cliente("");
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
    private String tipoGuiaRemision = "EMITIDA";

    /*GUIA DE REMISION*/
    public static String buscarCliente = "";
    private String buscarNombre = "";
    private String buscarRazonSocial = "";
    private String buscarCedula = "";
    private List<Cliente> listaClientesAll = new ArrayList<Cliente>();

    //busacar producto
    ServicioProducto servicioProducto = new ServicioProducto();
    private List<Producto> listaProducto = new ArrayList<Producto>();
    private String buscarNombreProd = "";

    private static Tipoambiente tipoambiente = null;
    private String numeroGuiaRecibida = "";

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") String valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        findClienteLikeNombre();
        findProductoLikeNombre();
        listaTransportistas = servicioTransportista.findTransportista("");
        getDetalle();
        
    }

    public GuiaRemision() {
        Session sess = Sessions.getCurrent();
        sess.setMaxInactiveInterval(10000);
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        tipoambiente = servicioTipoAmbiente.FindALlTipoambiente();
    }

    @Command
    @NotifyChange({"listaClientesAll", "clienteBuscado", "llegada"})
    public void buscarClienteEnLista() {
        ParamFactura paramFactura = new ParamFactura();
        paramFactura.setBusqueda("cliente");
        final HashMap<String, ParamFactura> map = new HashMap<String, ParamFactura>();
        map.put("valor", paramFactura);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/venta/buscarclienteguia.zul", null, map);
        window.doModal();
        System.out.println("clinete de la lsitas buscarCliente " + buscarCliente);
        clienteBuscado = servicioCliente.FindClienteForCedula(buscarCliente);
        if (clienteBuscado != null) {
            llegada = clienteBuscado.getCliDireccion();
        }

    }

    @Command
    @NotifyChange("clienteBuscado")
    public void seleccionarClienteLista(@BindingParam("cliente") Cliente valor) {
        System.out.println("cliente seleccionado " + valor.getCliCedula());
        buscarCliente = valor.getCliCedula();
        windowClienteBuscarGuia.detach();

    }

    @Command
    @NotifyChange({"listaProducto", "buscarNombreProd"})
    public void buscarLikeNombreProd() {

        findProductoLikeNombre();
    }

    //busqueda del producto
    @Command
    @NotifyChange({"listaGuiaModel"})
    public void eliminarRegistros() {
        if (seleccionGuiaRemision.size() > 0) {
            ((ListModelList<DetalleGuiaDao>) listaGuiaModel).removeAll(seleccionGuiaRemision);

        } else {
            Messagebox.show("Seleccione al menos un registro para eliminar", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

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

    @Command
    @NotifyChange({"lstGuiaRemision", "fechafin", "fechainicio"})
    public void verificaNumeracion() {
        if (tipoGuiaRemision.equals("RECIBIDA")) {
            txtNumeroGuiaRecibida.setVisible(Boolean.TRUE);
        } else {
            txtNumeroGuiaRecibida.setVisible(Boolean.FALSE);
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

    @Command
    @NotifyChange({"listaGuiaModel"})
    public void guardar() {

        try {

            if (listaGuiaModel.size() > 0) {

            } else {
                Clients.showNotification("Verifique el detalle de la guia",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
                return;
            }
            numeroGuia();
            if (!partida.equals("")                    
                    && !numeroPlaca.equals("")
                    && clienteBuscado != null
                    && transportista != null) {

                Guiaremision guiaremision = new Guiaremision();
               

                if (tipoGuiaRemision.equals("EMITIDA")) {
                     guiaremision.setFacNumero(numeroGuia);
//                    numeroGuia();
                    guiaremision.setFacNumeroText(numeroGuiaText);
                } else {
                    guiaremision.setFacNumero(0);
                    guiaremision.setFacNumeroTextRecibida(numeroGuiaRecibida);
                }
                guiaremision.setIdUsuario(credential.getUsuarioSistema());
                guiaremision.setFacFecha(new Date());
                guiaremision.setFacEstado("PENDIENTE");
                guiaremision.setTipodocumento("06");
                guiaremision.setPuntoemision("001");
                guiaremision.setCodestablecimiento("001");
                guiaremision.setEstadosri("PENDIENTE");
                String claveAccesoGuia = ArchivoUtils.generaClave(guiaremision.getFacFecha(), "06", tipoambiente.getAmRuc(), tipoambiente.getAmCodigo(), "001001", numeroGuiaText, "12345678", "1");
                guiaremision.setFacClaveAcceso(claveAccesoGuia);
                guiaremision.setFacClaveAutorizacion(claveAccesoGuia);
                guiaremision.setCodTipoambiente(tipoambiente.getCodTipoambiente());
                guiaremision.setFacFechaSustento(new Date());
                guiaremision.setIdTransportista(transportista);
                guiaremision.setNumplacaguia(numeroPlaca);
                guiaremision.setIdCliente(clienteBuscado);
                guiaremision.setFechainitranspguia(incioTraslado);
                guiaremision.setFechafintranspguia(finTraslado);
                guiaremision.setMotivoGuia(motivoGuia);
                guiaremision.setPartida(partida);
                guiaremision.setLlegada(llegada);
                guiaremision.setTipoGuia(tipoGuiaRemision);
                List<DetalleGuiaremision> detalleGuia = new ArrayList<DetalleGuiaremision>();
                DetalleGuiaremision nuevo = null;
                for (DetalleGuiaDao itemDet : listaGuiaModel) {
                    nuevo = new DetalleGuiaremision(itemDet.getDetCantidad(), itemDet.getDetDescripcion(), itemDet.getIdProducto(), guiaremision);
                    detalleGuia.add(nuevo);
                }
                servicioGuia.guardarGuiaremision(detalleGuia, guiaremision);
                Clients.showNotification("Guardado con exito",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 3000, true);

                Executions.sendRedirect("/venta/guia.zul");
            } else {
                Clients.showNotification("Verifique la informacion",
                        Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 3000, true);
            }
        } catch (Exception e) {
            Clients.showNotification("Error al registrar " + e.getMessage(),
                    Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 3000, true);
        }
    }

    @Command
    @NotifyChange({"listaGuiaModel"})
    public void agregarItemLista(@BindingParam("valor") Producto producto) {

        if (producto != null) {
            DetalleGuiaDao valor = new DetalleGuiaDao();
            valor.setDetCantidad(BigDecimal.ONE);
            valor.setDetDescripcion(producto.getProdNombre());
            valor.setIdProducto(producto);
            ((ListModelList<DetalleGuiaDao>) listaGuiaModel).add(valor);
        }
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarRazonSocial"})
    public void buscarClienteRazon() {

        findClienteLikeRazon();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarNombre"})
    public void buscarClienteNombre() {

        findClienteLikeNombre();
    }

    @Command
    @NotifyChange({"listaClientesAll", "buscarCedula"})
    public void buscarClienteCedula() {

        findClienteLikeCedula();
    }

    private void findClienteLikeNombre() {
        listaClientesAll = servicioCliente.FindClienteLikeNombre(buscarNombre);
    }

    private void findClienteLikeRazon() {
        listaClientesAll = servicioCliente.FindClienteLikeRazonSocial(buscarRazonSocial);
    }

    private void findClienteLikeCedula() {
        listaClientesAll = servicioCliente.FindClienteLikeCedula(buscarCedula);
    }

    private void getDetalle() {
        setListaGuiaModel(new ListModelList<DetalleGuiaDao>(getListaGuiaRemisionDatos()));
        ((ListModelList<DetalleGuiaDao>) listaGuiaModel).setMultiple(true);
    }

    @Command
    public void seleccionarRegistros() {
        seleccionGuiaRemision = ((ListModelList<DetalleGuiaDao>) getListaGuiaModel()).getSelection();
    }

    private void findProductoLikeNombre() {
        listaProducto = servicioProducto.findLikeProdNombre(buscarNombreProd);
    }

    public ListModelList<DetalleGuiaDao> getListaGuiaModel() {
        return listaGuiaModel;
    }

    public void setListaGuiaModel(ListModelList<DetalleGuiaDao> listaGuiaModel) {
        this.listaGuiaModel = listaGuiaModel;
    }

    public List<DetalleGuiaDao> getListaGuiaRemisionDatos() {
        return listaGuiaRemisionDatos;
    }

    public void setListaGuiaRemisionDatos(List<DetalleGuiaDao> listaGuiaRemisionDatos) {
        this.listaGuiaRemisionDatos = listaGuiaRemisionDatos;
    }

    public List<Producto> getListaProductoCmb() {
        return listaProductoCmb;
    }

    public void setListaProductoCmb(List<Producto> listaProductoCmb) {
        this.listaProductoCmb = listaProductoCmb;
    }

    public Date getFechaGuia() {
        return fechaGuia;
    }

    public void setFechaGuia(Date fechaGuia) {
        this.fechaGuia = fechaGuia;
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

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public void setNumeroPlaca(String numeroPlaca) {
        this.numeroPlaca = numeroPlaca;
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

    public Cliente getClienteBuscado() {
        return clienteBuscado;
    }

    public void setClienteBuscado(Cliente clienteBuscado) {
        this.clienteBuscado = clienteBuscado;
    }

    public List<Transportista> getListaTransportistas() {
        return listaTransportistas;
    }

    public void setListaTransportistas(List<Transportista> listaTransportistas) {
        this.listaTransportistas = listaTransportistas;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public void setTransportista(Transportista transportista) {
        this.transportista = transportista;
    }

    public List<Cliente> getListaClientesAll() {
        return listaClientesAll;
    }

    public void setListaClientesAll(List<Cliente> listaClientesAll) {
        this.listaClientesAll = listaClientesAll;
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

    public String getTipoGuiaRemision() {
        return tipoGuiaRemision;
    }

    public void setTipoGuiaRemision(String tipoGuiaRemision) {
        this.tipoGuiaRemision = tipoGuiaRemision;
    }

    public String getNumeroGuiaRecibida() {
        return numeroGuiaRecibida;
    }

    public void setNumeroGuiaRecibida(String numeroGuiaRecibida) {
        this.numeroGuiaRecibida = numeroGuiaRecibida;
    }

}
