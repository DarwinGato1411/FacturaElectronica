/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Cliente;
import com.ec.entidad.ComprasSri;
import com.ec.entidad.DetalleFactura;
import com.ec.entidad.Factura;
import com.ec.entidad.FormaPago;
import com.ec.entidad.Producto;
import com.ec.entidad.Tipoadentificacion;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.VentaRuta;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCliente;
import com.ec.servicio.ServicioDetalleFactura;
import com.ec.servicio.ServicioEstadoFactura;
import com.ec.servicio.ServicioFactura;
import com.ec.servicio.ServicioFormaPago;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.servicio.ServicioTipoIdentificacion;
import com.ec.servicio.ServicioVentaRuta;
import com.ec.untilitario.ArchivoUtils;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class ListaVentaRuta {

    private static String PATH_BASE = "";
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    ServicioCliente servicioCliente = new ServicioCliente();
    ServicioTipoIdentificacion servicioTipoIdentificacion = new ServicioTipoIdentificacion();
    ServicioFactura servicioFactura = new ServicioFactura();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioFormaPago servicioFormaPago = new ServicioFormaPago();
    ServicioEstadoFactura servicioEstadoFactura = new ServicioEstadoFactura();
    ServicioDetalleFactura servicioDetalleFactura = new ServicioDetalleFactura();
    private Tipoambiente amb = new Tipoambiente();
    ServicioVentaRuta servicioVentaRuta = new ServicioVentaRuta();
    private String buscarNombre = "";
    private String buscarRuc = "";
    private String buscarEstado = "N";
    private Date inicio = new Date();
    private Date fin = new Date();
    private List<VentaRuta> listaVentaRuta = new ArrayList<VentaRuta>();

    private String numeroFacturaText = "";
    private Integer numeroFactura = 0;

    UserCredential credential = new UserCredential();

    public ListaVentaRuta() {

        //muestra 7 dias atras
        Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy 
        //  calendar.add(Calendar.DATE, -7); //el -3 indica que se le restaran 3 dias 
        inicio = calendar.getTime();

        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;

        findBuscarDocumentos();
        amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();

    }

    private void findBuscarDocumentos() {
        listaVentaRuta = servicioVentaRuta.findFacFecha(inicio, fin, buscarEstado, buscarNombre, buscarRuc);
    }

    @Command
    @NotifyChange({"listaVentaRuta", "inicio", "fin", "buscarEstado", "buscarNombre", "buscarRuc"})
    public void buscarForFechas() {
        findBuscarDocumentos();
    }

    @Command
    @NotifyChange({"listaVentaRuta", "inicio", "fin", "buscarEstado", "buscarNombre", "buscarRuc"})
    public void actualizar(@BindingParam("valor") VentaRuta valor) {
        servicioVentaRuta.modificar(valor);
        Clients.showNotification("Modificado correctamente", Clients.NOTIFICATION_TYPE_INFO, null, "end_before", 1000, true);
    }

    @Command
    @NotifyChange({"listaVentaRuta", "inicio", "fin", "buscarEstado", "buscarNombre", "buscarRuc"})
    public void eliminar(@BindingParam("valor") VentaRuta valor) {
        if (Messagebox.show("¿Desea elimiar el registro?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            servicioVentaRuta.eliminar(valor);
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

    @Command
    @NotifyChange({"listaVentaRuta", "inicio", "fin", "buscarEstado", "buscarNombre", "buscarRuc"})
    public void crearFacturas() {
        if (Messagebox.show("¿Desea crear las facturas de los registros listados?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            Factura factura;
            Producto prodGLP = servicioProducto.findLikeProdGlp("CARGA GLP").get(0);
            Producto prodTransporte = servicioProducto.findLikeProdGlp("TRANSPORTE").get(0);
            FormaPago formaPagoSelected = servicioFormaPago.finPrincipal();
            for (VentaRuta ventaRuta : listaVentaRuta) {
                numeroFactura();
                numeroFacturaTexto();
                factura = new Factura();
                factura.setFacTipo("FACT");
                factura.setFacDescripcion("");
                factura.setFacFecha(ventaRuta.getFecha());
                factura.setFacEstado("PA");
                factura.setFacNumeroText(numeroFacturaText);
                factura.setPuntoemision(amb.getAmPtoemi());
                factura.setEstadosri("PENDIENTE");
                factura.setCodestablecimiento(amb.getAmEstab());
                factura.setCod_tipoambiente(amb);
                factura.setFaConSinGuia("SG");
                factura.setFacSubsidio(prodGLP.getProdSubsidio().multiply(BigDecimal.valueOf(Double.valueOf(ventaRuta.getCantidad()))));
                factura.setFacValorSinSubsidio(prodGLP.getProdPrecioSinSubsidio().multiply(BigDecimal.valueOf(Double.valueOf(ventaRuta.getCantidad()))));

                factura.setFacNumero(numeroFactura);
                factura.setFacNumProforma(0);
                factura.setFacNumNotaEntrega(0);
                /*PARA EL SRI*/
                factura.setTipodocumento("01");
                BigDecimal subTotalUnidad = BigDecimal.ZERO;
//                BigDecimal subTrans = BigDecimal.ZERO;
//                BigDecimal subIVATrans = BigDecimal.ZERO;
                BigDecimal ivaUnidad = BigDecimal.ZERO;
                BigDecimal totalUnidad = BigDecimal.ZERO;
                BigDecimal totalBaseCero = BigDecimal.ZERO;
                BigDecimal precioGLP = BigDecimal.valueOf(1.4294);
                if (ventaRuta.getTransporte().equals("S")) {

                    subTotalUnidad = BigDecimal.valueOf(2.68);
                    ivaUnidad = BigDecimal.valueOf(0.32);
                    totalUnidad = BigDecimal.valueOf(3);
                    totalBaseCero = BigDecimal.ZERO;

                } else {
                    subTotalUnidad = BigDecimal.valueOf(1.4294);
                    ivaUnidad = BigDecimal.valueOf(0.17);
                    totalUnidad = BigDecimal.valueOf(1.6);
                    totalBaseCero = BigDecimal.ZERO;
                }
                factura.setIdCliente(servicioCliente.FindClienteForCedula(ventaRuta.getCedula()));
                factura.setIdUsuario(credential.getUsuarioSistema());
                factura.setFacSubtotal(subTotalUnidad.multiply(BigDecimal.valueOf(Double.valueOf(ventaRuta.getCantidad()))));

                factura.setFacSaldoAmortizado(BigDecimal.ZERO);
                factura.setFacDescuento(BigDecimal.ZERO);
                factura.setFacCodIce("3");
                factura.setFacCodIva("2");
//                1.4010
                factura.setFacTotalBaseCero(totalBaseCero.multiply(BigDecimal.valueOf(Double.valueOf(ventaRuta.getCantidad()))));
                /*0 SI NO LLEVA IVA Y 2 SI LLEVA IVA*/
                factura.setCodigoPorcentaje("2");
                factura.setFacPorcentajeIva("12");
                factura.setFacMoneda("DOLAR");
                factura.setIdFormaPago(formaPagoSelected);
                factura.setFacPlazo(BigDecimal.valueOf(Double.valueOf(formaPagoSelected.getPlazo())));
                factura.setFacUnidadTiempo(formaPagoSelected.getUnidadTiempo());
                factura.setIdEstado(servicioEstadoFactura.findByEstCodigo("PA"));
                factura.setFacTotalBaseGravaba(subTotalUnidad.multiply(BigDecimal.valueOf(Double.valueOf(ventaRuta.getCantidad()))));
//                factura.setFacTotalBaseGravaba(precioGLP.multiply(BigDecimal.valueOf(Double.valueOf(ventaRuta.getCantidad()))));
                factura.setFacAbono(BigDecimal.ZERO);
                factura.setFacSaldo(BigDecimal.ZERO);
                factura.setFacIva(ivaUnidad.multiply(BigDecimal.valueOf(Double.valueOf(ventaRuta.getCantidad()))));
                factura.setFacTotal(factura.getFacTotalBaseCero().add(factura.getFacTotalBaseGravaba()).add(factura.getFacIva()));

                String claveAcceso = ArchivoUtils.generaClave(factura.getFacFecha(), "01", amb.getAmRuc(), amb.getAmCodigo(), "001001", factura.getFacNumeroText(), "12345678", "1");
                factura.setFacClaveAcceso(claveAcceso);
                factura.setFacClaveAutorizacion(claveAcceso);
                servicioFactura.crear(factura);

                /*Detalle de factura GLP*/
                DetalleFactura detalleFactura = new DetalleFactura(BigDecimal.valueOf(Double.valueOf(ventaRuta.getCantidad())),
                        prodGLP.getProdNombre(),
                        BigDecimal.valueOf(1.4285),
                        BigDecimal.valueOf(1.60),
                        prodGLP,
                        factura,
                        "NORMAL");
                detalleFactura.setDetIva(BigDecimal.valueOf(0.1714));
                detalleFactura.setDetTotalconiva(BigDecimal.valueOf(1.6));

                detalleFactura.setDetSubtotaldescuento(BigDecimal.valueOf(1.4285));
                detalleFactura.setDetTotaldescuento(BigDecimal.valueOf(1.6));
                detalleFactura.setDetPordescuento(BigDecimal.ZERO);
                detalleFactura.setDetValdescuento(BigDecimal.ZERO);
                detalleFactura.setDetTotaldescuentoiva(BigDecimal.valueOf(1.6));
                detalleFactura.setDetCantpordescuento(BigDecimal.ZERO);
                detalleFactura.setDetSubtotaldescuentoporcantidad(BigDecimal.valueOf(1.4285));
                detalleFactura.setDetCodTipoVenta("0");
                detalleFactura.setDetCodIva("2");
                detalleFactura.setDetCodPorcentaje("2");
                detalleFactura.setDetTarifa(prodGLP.getProdIva());
                servicioDetalleFactura.crear(detalleFactura);

                if (ventaRuta.getTransporte().equals("S")) {
                    /*Detalle de factura TRANSPORTE*/
                    DetalleFactura detalleFacturaTr = new DetalleFactura(BigDecimal.valueOf(Double.valueOf(ventaRuta.getCantidad())),
                            prodTransporte.getProdNombre(),
                            BigDecimal.valueOf(1.40),
                            BigDecimal.valueOf(1.40),
                            prodTransporte,
                            factura,
                            "NORMAL");
                    detalleFacturaTr.setDetIva(BigDecimal.ZERO);
                    detalleFacturaTr.setDetTotalconiva(BigDecimal.valueOf(1.4));

                    detalleFacturaTr.setDetSubtotaldescuento(BigDecimal.valueOf(1.25));
                    detalleFacturaTr.setDetTotaldescuento(BigDecimal.valueOf(1.4));
                    detalleFacturaTr.setDetPordescuento(BigDecimal.ZERO);
                    detalleFacturaTr.setDetValdescuento(BigDecimal.ZERO);
                    detalleFacturaTr.setDetTotaldescuentoiva(BigDecimal.valueOf(1.4));
                    detalleFacturaTr.setDetCantpordescuento(BigDecimal.ZERO);
                    detalleFacturaTr.setDetSubtotaldescuentoporcantidad(BigDecimal.valueOf(1.25));
                    detalleFacturaTr.setDetCodTipoVenta("0");
                    detalleFacturaTr.setDetCodIva("2");
                    detalleFacturaTr.setDetCodPorcentaje("2");
                    detalleFacturaTr.setDetTarifa(prodTransporte.getProdIva());
                    servicioDetalleFactura.crear(detalleFacturaTr);
                }
                ventaRuta.setFacturado("S");
                servicioVentaRuta.modificar(ventaRuta);

            }
            findBuscarDocumentos();
            Clients.showNotification("Sus facturas fueron generadas correctamente..",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 2000, true);
        }
    }

    @Command
    @NotifyChange({"listaVentaRuta"})
    public void cargarVentas()
            throws JRException, IOException, NamingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

    }
    /*subir rchivo*/
    //subir pdfArchivo
    private String filePath;
    byte[] buffer = new byte[1024 * 1024];
    private AImage fotoGeneral = null;

    @Command
    @NotifyChange({"listaVentaRuta", "inicio", "fin", "buscarEstado", "buscarNombre", "buscarRuc"})
    public void subirArchivo() {

        try {
            String folderDescargados = PATH_BASE + File.separator + "VENTARUTA"
                    + File.separator + new Date().getYear()
                    + File.separator + new Date().getMonth();

            /*EN EL CASO DE NO EXISTIR LOS DIRECTORIOS LOS CREA*/
            File folderGen = new File(folderDescargados);
            if (!folderGen.exists()) {
                folderGen.mkdirs();
            }
            org.zkoss.util.media.Media media = Fileupload.get("Cargar su archivo que obtuvo en el SRI", "Subir Archivo SRI");

            if (media.getName().contains(".txt")) {
                String builder = media.getStringData();

                String[] campos = builder.split("\t");
                String[] campos1 = builder.split("\t|\n");

                ComprasSri comprasSri;
                SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
                // myDate is the java.util.Date in yyyy-mm-dd format
                // Converting it into String using formatter
                String strDate = sm.format(inicio);
                //Converting the String back to java.util.Date
                Date dt = sm.parse(strDate);
                String existentes = "";
                VentaRuta nuevaVenta = null;
                Boolean existenRepetido = Boolean.FALSE;
                for (int i = 0; i < campos1.length; i++) {

                    String[] camposInd = campos1[i].split(";");
                    String fecha[] = camposInd[7].split("/");
//                    System.out.println("DATOS " + camposInd[0] + "  "
//                            + camposInd[1] + "  "
//                            + camposInd[2] + "  "
//                            + camposInd[3] + "  "
//                            + camposInd[4] + "  "
//                            + camposInd[5] + "  "
//                            + camposInd[6] + "  "
//                            + sm.format(new Date(new Date().getYear(), Integer.valueOf(fecha[1]), Integer.valueOf(fecha[0]))) + "  "
//                            + camposInd[8]);

                    /*ARMA EL OBJETO PRA SUBIR*/
                    nuevaVenta = new VentaRuta(camposInd[0],//cedula
                            camposInd[1].toUpperCase(),//nombre
                            camposInd[8],//cantidad
                            camposInd[2].toUpperCase(),//direccion
                            camposInd[4],//correo
                            camposInd[3],//celular
                            camposInd[5],//semana
                            new Date(new Date().getYear(), Integer.valueOf(fecha[1]), Integer.valueOf(fecha[0])),//fecha
                            camposInd[6],//codigo venta
                            "N");
                    nuevaVenta.setTransporte(camposInd[9]);
                    /*CREAR EL CLIENTE SI NO EXISTE*/
                    Cliente buscado = servicioCliente.FindClienteForCedula(camposInd[0]);
                    Cliente cliNuevo = null;
                    if (buscado == null) {
                        cliNuevo = new Cliente();
                        cliNuevo.setCliCedula(camposInd[0]);
                        cliNuevo.setCiudad("OTAVALO");
                        cliNuevo.setCliNombre(camposInd[1].toUpperCase());
                        cliNuevo.setCliRazonSocial(camposInd[1].toUpperCase());
                        cliNuevo.setCliDireccion(camposInd[2].toUpperCase());
                        cliNuevo.setCliCorreo(camposInd[4]);
                        cliNuevo.setCliMontoAsignado(BigDecimal.valueOf(100000.0));
                        cliNuevo.setCliMovil(camposInd[3]);
                        String nombreApellido[] = camposInd[1].split(" ");
                        String nombrePersona = "";
                        String apellidoPersona = "";
                        switch (nombreApellido.length) {
                            case 1:
                                apellidoPersona = nombreApellido[0];
                                nombrePersona = "S/N";
                                break;
                            case 2:
                                apellidoPersona = nombreApellido[0];
                                nombrePersona = nombreApellido[1];

                                break;
                            case 3:
                                apellidoPersona = nombreApellido[0] + " " + nombreApellido[1];
                                nombrePersona = nombreApellido[2];
                                break;
                            case 4:
                                apellidoPersona = nombreApellido[0] + " " + nombreApellido[1];
                                nombrePersona = nombreApellido[2] + " " + nombreApellido[3];
                                break;
                            default:
                                break;
                        }
                        cliNuevo.setCliNombres(nombrePersona);
                        cliNuevo.setCliApellidos(apellidoPersona);
                        cliNuevo.setCliTelefono(camposInd[3]);
                        cliNuevo.setClieFechaRegistro(new Date());
                        cliNuevo.setClietipo(0);
                        Tipoadentificacion tipIdent = null;
                        if (camposInd[0].length() == 10) {
                            tipIdent = servicioTipoIdentificacion.findByTipoIdentificacion("CEDULA");
                        }
                        if (camposInd[0].length() == 13) {
                            tipIdent = servicioTipoIdentificacion.findByTipoIdentificacion("RUC");
                        }
                        cliNuevo.setIdTipoIdentificacion(tipIdent);

                        servicioCliente.crear(cliNuevo);
                    }

                    /*RIGISTRA LA VENTA SI NO ESXISTE*/
                    VentaRuta valid = servicioVentaRuta.findByCodigo(camposInd[6]);
                    if (valid == null) {
                        servicioVentaRuta.crear(nuevaVenta);
                    } else {
                        System.out.println("EXISTE " + valid);
                    }

                }
                findBuscarDocumentos();
                Clients.showNotification("Informacion cargada", "info", null, "end_before", 1000, true);
            }

        } catch (NumberFormatException e) {
            System.out.println("ERROR al subir la imagen IOException " + e.getMessage());
            e.getStackTrace();
        } catch (ParseException e) {
            System.out.println("ERROR al subir la imagen IOException " + e.getMessage());
            e.getStackTrace();
        }
    }

    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    public String getBuscarRuc() {
        return buscarRuc;
    }

    public void setBuscarRuc(String buscarRuc) {
        this.buscarRuc = buscarRuc;
    }

    public String getBuscarEstado() {
        return buscarEstado;
    }

    public void setBuscarEstado(String buscarEstado) {
        this.buscarEstado = buscarEstado;
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

    public List<VentaRuta> getListaVentaRuta() {
        return listaVentaRuta;
    }

    public void setListaVentaRuta(List<VentaRuta> listaVentaRuta) {
        this.listaVentaRuta = listaVentaRuta;
    }

}
