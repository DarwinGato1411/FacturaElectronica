/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.DetalleKardex;
import com.ec.entidad.Factura;
import com.ec.entidad.Kardex;
import com.ec.entidad.Producto;
import com.ec.entidad.Tipocomprobante;
import com.ec.entidad.Tipokardex;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioDetalleFactura;
import com.ec.servicio.ServicioDetalleKardex;
import com.ec.servicio.ServicioKardex;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioTipoKardex;
import com.ec.untilitario.ParamFactura;
import com.ec.untilitario.TotalKardex;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
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
public class AjusteEntradaSalida {

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
    /*DETALLE DEL KARDEX Y DETALLE KARDEX*/
    ServicioKardex servicioKardex = new ServicioKardex();
    ServicioDetalleKardex servicioDetalleKardex = new ServicioDetalleKardex();
    ServicioTipoKardex servicioTipoKardex = new ServicioTipoKardex();
    ServicioDetalleFactura servicioDetalleFactura = new ServicioDetalleFactura();
    //buscar cliente

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

    //usuario que factura
    UserCredential credential = new UserCredential();

//reporte
    AMedia fileContent = null;
    Connection con = null;
    //cambio

    /*KARDEX*/
    private List<Producto> listaProductoCmb = new ArrayList<Producto>();
    private String codigo = "";


    /*GESTIONA NOTAS DE ENTREGA*/
    //crear un factura nueva        

    /*AJUSTE DE ENTRADA O SALIDA*/
    private String tipoAjuste = "ING";
    private String motivoAjuste = "";
    private List<Kardex> listaKardexProducto = new ArrayList<Kardex>();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") ParamFactura valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);

        findKardexProductoLikeNombre();
        //para establecer el cliente final

        agregarRegistroVacio();

    }
//<editor-fold defaultstate="collapsed" desc="Facturar">

    public AjusteEntradaSalida() {
        Session sess = Sessions.getCurrent();
        sess.setMaxInactiveInterval(10000);
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        getDetallefactura();

    }
// </editor-fold>

    /*AGREGAMOS DESDE LA LSITA */
    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "totalDescuento", "buscarNombreProd", "valorTotalInicialVent", "descuentoValorFinal", "subTotalBaseCero", "listaProducto", "totalItems"})
    public void agregarItemLista(@BindingParam("valor") Producto producto) {

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

        if (productoBuscado != null) {
            DetalleFacturaDAO valor = new DetalleFacturaDAO();
            valor.setCantidad(BigDecimal.ONE);
            valor.setProducto(productoBuscado);
            valor.setDescripcion(productoBuscado.getProdNombre());
            valor.setDetPordescuento(BigDecimal.ZERO);
            valor.setCodigo(productoBuscado.getProdCodigo());
            valor.setEsProducto(producto.getProdEsproducto());

            BigDecimal costVentaTipoCliente = BigDecimal.ZERO;
            BigDecimal costVentaTipoClienteInicial = BigDecimal.ZERO;
            String tipoVenta = "NORMAL";
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
        //calcularValoresTotales();
        codigoBusqueda = "";

        buscarNombreProd = "";
        idBusquedaProd.setFocus(Boolean.TRUE);
        /*COLOCA EL FOCO EN EL BUSCADOR*/
//        idBusquedaProd.setFocus(Boolean.TRUE);
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
        AjusteEntradaSalida.codigoBusqueda = codigoBusqueda;
    }

    public String getBuscarCodigoProd() {
        return buscarCodigoProd;
    }

    public void setBuscarCodigoProd(String buscarCodigoProd) {
        this.buscarCodigoProd = buscarCodigoProd;
    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
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

    //busqueda del producto
    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion", "subTotalBaseCero", "totalItems", "valorTotalInicialVent"})
    public void eliminarRegistros() {
        if (registrosSeleccionados.size() > 0) {
            ((ListModelList<DetalleFacturaDAO>) listaDetalleFacturaDAOMOdel).removeAll(registrosSeleccionados);
            //calcularValoresTotales();

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

    private void guardarFactura() {

        try {
            if (motivoAjuste == null) {
                Clients.showNotification("Verifique el motivo del ajuste", Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
               return;
            }
            
             if (motivoAjuste.equals("")) {
                Clients.showNotification("Verifique el motivo del ajuste", Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 2000, true);
                return;
            }
            //armar el detalle de la factura
            List<DetalleFacturaDAO> detalleFactura = new ArrayList<DetalleFacturaDAO>();
            List<DetalleFacturaDAO> listaPedido = listaDetalleFacturaDAOMOdel.getInnerList();

//            Clients.showNotification("Factura registrada con éxito", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 5000, true);
            /*VERIFICA QUE NO SEA UNA PROFORMA QUE SE CONVERTIRA EN FACTURA, VERIFICA SI ES NOT DE ENTREGA 
            NINGUNA PROFORMA DESCARGA*/
 /*INGRESAMOS LO MOVIMIENTOS AL KARDEX*/
            Kardex kardex = null;
            DetalleKardex detalleKardex = null;

            for (DetalleFacturaDAO item : listaPedido) {
                if (item.getProducto() != null) {

                    Tipokardex tipokardex = servicioTipoKardex.findByTipkSigla(tipoAjuste);
                    if (servicioKardex.FindALlKardexs(item.getProducto()) == null) {
                        kardex = new Kardex();
                        kardex.setIdProducto(item.getProducto());
                        kardex.setKarDetalle("Inicio de inventario desde la AJUSTE para el producto: " + item.getProducto().getProdNombre());
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
                    detalleKardex.setDetkDetalles("AJUSTE: " + motivoAjuste.toUpperCase());
//                    detalleKardex.setIdFactura(factura);
                    detalleKardex.setDetkCantidad(item.getCantidad());
                    servicioDetalleKardex.crear(detalleKardex);
                    /*ACTUALIZA EL TOTAL DEL KARDEX*/
                    TotalKardex totales = servicioKardex.totalesForKardex(kardex);
                    BigDecimal total = totales.getTotalKardex();
                    kardex.setKarTotal(total);
                    servicioKardex.modificar(kardex);

                }

            }
            Clients.showNotification("Guardado correctamente", Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 2000, true);
            Executions.sendRedirect("/administrar/ajuste.zul");

        } catch (Exception e) {
            System.out.println("ERROR EN EL AJUSTE  " + e.getMessage());
            Messagebox.show("Ocurrio un error guardar la factura ", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    @Command
    @NotifyChange({"listaDetalleFacturaDAOMOdel", "subTotalCotizacion", "ivaCotizacion", "valorTotalCotizacion"})
    public void Guardar() {

        guardarFactura();

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

//    public void reporteGeneral() throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
//        EntityManager emf = HelperPersistencia.getEMF();
//
//        try {
//            String reporte = parametrizar.getParImprimeFactura().trim();
//            emf.getTransaction().begin();
//            /*CONEXION A LA BASE DE DATOS*/
//            con = emf.unwrap(Connection.class);
//            if (!tipoVenta.equals("SINF")) {
//
//                //  con = emf.unwrap(Connection.class);
//                String reportFile = Executions.getCurrent().getDesktop().getWebApp()
//                        .getRealPath("/reportes");
//                String reportPath = "";
////                con = ConexionReportes.Conexion.conexion();
//
//                if (tipoVenta.equals("FACT")) {
////                    reportPath = reportFile + File.separator + "puntoventa.jasper";
////                    reportPath = reportFile + File.separator + "factura.jasper";
//                    reportPath = reportFile + File.separator + reporte;
//
//                } else if (tipoVenta.equals("PROF")) {
//                    /*ES EL PATH DONDE SE ENCUENTRA EL REPORTE EN MI CASO*/
//                    reportPath = reportFile + File.separator + "proforma.jasper";
//                } else if (tipoVenta.equals("NTE")) {
//                    /*ES EL PATH DONDE SE ENCUENTRA EL REPORTE EN MI CASO*/
//                    reportPath = reportFile + File.separator + "notaentrega.jasper";
//                } else if (tipoVenta.equals("NTV")) {
//                    /*ES EL PATH DONDE SE ENCUENTRA EL REPORTE EN MI CASO*/
//                    reportPath = reportFile + File.separator + "notaventaticket.jasper";
//                }
//                /*PARAMETROS DEL REPORTE*/
//                Map<String, Object> parametros = new HashMap<String, Object>();
//
//                //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
//                parametros.put("numfactura", numeroFactura);
//
//                if (con != null) {
//                    System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//                }
//                FileInputStream is = null;
//                is = new FileInputStream(reportPath);
//                /*COMPILAS EL ARCHIVO.JASPER*/
//                byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
//                /*EN MI CASO LO PRESENTO EN UNA VENTANA EMERGENTE  PERO LO ANTERIOR SERIA TODO*/
//                InputStream mediais = new ByteArrayInputStream(buf);
//
//                AMedia amedia = new AMedia("Reporte", "pdf", "application/pdf", mediais);
//                fileContent = amedia;
//                final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//                //para pasar al visor
//                map.put("pdf", fileContent);
//
//                if (parametrizar.getParImpFactura()) {
//                    org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
//                            "/venta/contenedorReporte.zul", null, map);
//                    window.doModal();
//
//                }
//
//                /*para la factura*/
//                FileInputStream is1 = null;
//                is1 = new FileInputStream(reportFile + File.separator + "puntoventa.jasper");
//                JasperPrint jasperprint = JasperFillManager.fillReport(is1, parametros, con);
//                PrinterJob pj = PrinterJob.getPrinterJob();
//                if (parametrizar.getParImpAutomatico()) {
//                    /*imprime la factura */
//
//                    // 
//                    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//                    /*ESCOGE LA IMPRESORA */
//                    for (PrintService printService : services) {
//                        System.out.println("printService.getName() " + printService.getName());
//                        if (printService.getName().equals(parametrizar.getParNombreImpresora())) {
//
//                            System.out.println("printService.getName() " + printService.getName());
////                    if (printService.getName().equals("Microsoft Print to PDF")) {
//                            pj.setPrintService(printService);
//                            //JasperPrintManager.printReport(print, false);
//                        }
//                    }
//                }
//                /*ESCOGE LA IMPRESORA */
////                for (PrintService printService : services) {
////                    if (printService.getName().equals("LR2000")) {
//////                    if (printService.getName().equals("Microsoft Print to PDF")) {
////                        pj.setPrintService(printService);
////                        //JasperPrintManager.printReport(print, false);
////                    }
////                }
//                imprimirTecket(pj, jasperprint);
//            }
//        } catch (PrinterException e) {
//            System.out.println("Error PrinterException en generar el reporte " + e.getMessage());
//        } catch (FileNotFoundException e) {
//            System.out.println("Error FileNotFoundException en generar el reporte " + e.getMessage());
//        } catch (JRException e) {
//            System.out.println("Error JRException en generar el reporte " + e.getMessage());
//        } finally {
//            if (con != null) {
//                con.close();
//            }
//            if (emf != null) {
//                emf.close();
//                System.out.println("cerro entity");
//            }
//        }
//
//    }
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

    public List<Kardex> getListaKardexProducto() {
        return listaKardexProducto;
    }

    public void setListaKardexProducto(List<Kardex> listaKardexProducto) {
        this.listaKardexProducto = listaKardexProducto;
    }

    //ajuste de entrada
    public String getTipoAjuste() {
        return tipoAjuste;
    }

    public void setTipoAjuste(String tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

    public String getMotivoAjuste() {
        return motivoAjuste;
    }

    public void setMotivoAjuste(String motivoAjuste) {
        this.motivoAjuste = motivoAjuste;
    }

}
