/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Cliente;
import com.ec.entidad.Factura;
import com.ec.entidad.OrdenTrabajo;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioCliente;
import com.ec.servicio.ServicioEstadoFactura;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioFormaPago;
import com.ec.servicio.ServicioOrdenTrabajo;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.servicio.ServicioTransportista;
import com.ec.untilitario.ParamFactura;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class OrdenTrabajoVm extends SelectorComposer<Component> {

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

    //buscar cliente
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    ServicioFormaPago servicioFormaPago = new ServicioFormaPago();
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    ServicioCliente servicioCliente = new ServicioCliente();
    ServicioEstadoFactura servicioEstadoFactura = new ServicioEstadoFactura();
    ServicioFactura servicioFactura = new ServicioFactura();
    ServicioTransportista servicioTransportista = new ServicioTransportista();

    public Cliente clienteBuscado = new Cliente("");
    private List<Cliente> listaClientesAll = new ArrayList<Cliente>();
    private String buscarNombre = "";
    private String buscarRazonSocial = "";
    private String buscarCedula = "";
    public static String buscarCliente = "";

    //Cabecera de la factura
    private String estdoFactura = "PE";
    private String tipoVentaAnterior = "ORD";
    private String tipoVenta = "ORD";
    private String facturaDescripcion = "";
    private Integer numeroFactura = 0;
    private String numeroOrdenText = "";

    private Date fechaOrden = new Date();

    //usuario que factura
    UserCredential credential = new UserCredential();

//reporte
    AMedia fileContent = null;
    Connection con = null;

    /*RECUPERA EL ID DE LA FACTURA*/
    private Integer idFactuta = 0;
    private String accion = "create";
    private String tipoDoc = "";
    private String clietipo = "0";

    //orden de trabajo
    private OrdenTrabajo ordenTrabajo;
    ServicioOrdenTrabajo servicioOrdenTrabajo = new ServicioOrdenTrabajo();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") ParamFactura valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor == null) {
            accion = "create";
            ordenTrabajo = new OrdenTrabajo();
            clienteBuscado = servicioCliente.findClienteLikeCedula("9999999999999");
        } else if (valor.getBusqueda().equals("producto") || valor.getBusqueda().equals("cliente")) {

        } else {
            accion = "update";

            ordenTrabajo = servicioOrdenTrabajo.findByIdOrden(Integer.valueOf(valor.getIdFactura()));
            clienteBuscado = ordenTrabajo.getIdCliente();
            numeroOrdenText = ordenTrabajo.getOrdNumText();
            numeroFactura = ordenTrabajo.getOrdNumero();

        }

        FindClienteLikeNombre();
    }

    public OrdenTrabajoVm() {
        Session sess = Sessions.getCurrent();
        //sess.setMaxInactiveInterval(10000);
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
    }

    private void numeroFacturaTexto() {
        numeroOrdenText = "";
        for (int i = numeroFactura.toString().length(); i < 9; i++) {
            numeroOrdenText = numeroOrdenText + "0";
        }
        numeroOrdenText = numeroOrdenText + numeroFactura;
        System.out.println("nuemro texto " + numeroOrdenText);
    }

    private void numeroFactura() {
        OrdenTrabajo recuperada = servicioOrdenTrabajo.findUltimaOrden();
        if (recuperada != null) {
            // System.out.println("numero de factura " + recuperada);
            numeroFactura = recuperada.getOrdNumero() + 1;
            numeroFacturaTexto();
        } else {
            numeroFactura = 1;
            numeroOrdenText = "000000001";
        }
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
        OrdenTrabajoVm.buscarCliente = buscarCliente;
    }

    public List<Cliente> getListaClientesAll() {
        return listaClientesAll;
    }

    public void setListaClientesAll(List<Cliente> listaClientesAll) {
        this.listaClientesAll = listaClientesAll;
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
                "/venta/buscarclienteorden.zul", null, map);
        window.doModal();
        System.out.println("clinete de la lsitas buscarCliente " + buscarCliente);
        clienteBuscado = servicioCliente.FindClienteForCedula(buscarCliente);
        if (clienteBuscado == null) {
            clienteBuscado = servicioCliente.findClienteLikeCedula("999999999");
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

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion"})
    public void Guardar() {

        guardarOrden();

    }

    private void guardarOrden() {

        try {
            if (accion.equals("create")) {
                numeroFactura();
            }
            ordenTrabajo.setOrdNumero(numeroFactura);
            ordenTrabajo.setOrdNumText(numeroOrdenText);
            ordenTrabajo.setIdEstado(servicioEstadoFactura.findByEstCodigo(estdoFactura));
            ordenTrabajo.setIdUsuario(credential.getUsuarioSistema());
            ordenTrabajo.setIdCliente(clienteBuscado);
            ordenTrabajo.setOrdFecha(fechaOrden);
            if (accion.equals("create")) {
                servicioOrdenTrabajo.crear(ordenTrabajo);
            } else {
                servicioOrdenTrabajo.modificar(ordenTrabajo);
            }
            reporteGeneral();
            if (accion.equals("create")) {
                Executions.sendRedirect("/venta/ordentrabajo.zul");
            } else {
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

    public void reporteGeneral() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();

        try {
            //String reporte = parametrizar.getParImprimeFactura().trim();
            emf.getTransaction().begin();
            /*CONEXION A LA BASE DE DATOS*/
            con = emf.unwrap(Connection.class);

            //  con = emf.unwrap(Connection.class);
            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
//                con = ConexionReportes.Conexion.conexion();

            /*ES EL PATH DONDE SE ENCUENTRA EL REPORTE EN MI CASO*/
            reportPath = reportFile + File.separator + "ordentrabajo.jasper";

            /*PARAMETROS DEL REPORTE*/
            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numfactura", numeroFactura);

            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }

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

    public String getClietipo() {
        return clietipo;
    }

    public void setClietipo(String clietipo) {
        this.clietipo = clietipo;
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

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getNumeroOrdenText() {
        return numeroOrdenText;
    }

    public void setNumeroOrdenText(String numeroOrdenText) {
        this.numeroOrdenText = numeroOrdenText;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

}
