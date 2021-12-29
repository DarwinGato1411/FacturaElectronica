/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.ComprasSri;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Producto;
import com.ec.entidad.Proveedores;
import com.ec.entidad.TipoIdentificacionCompra;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.sri.CabeceraCompraSri;
import com.ec.entidad.sri.DetalleCompraSri;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioCabeceraComprasSri;
import com.ec.servicio.ServicioCompra;
import com.ec.servicio.ServicioComprasSri;
import com.ec.servicio.ServicioDetalleCompra;
import com.ec.servicio.ServicioDetalleComprasSri;
import com.ec.servicio.ServicioEstadoFactura;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioProveedor;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.servicio.ServicioTipoIdentificacionCompra;
import com.ec.untilitario.ArchivoUtils;
import com.ec.untilitario.AutorizarDocumentos;
import ec.gob.sri.comprobantes.modelo.factura.Factura;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.internet.ParseException;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class ListaComprasSri {

    private static String PATH_BASE = "";
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    private Tipoambiente amb = new Tipoambiente();
    ServicioComprasSri servicioComprasSri = new ServicioComprasSri();
    ServicioDetalleCompra Servici = new ServicioDetalleCompra();
    ServicioCompra servicioCompra = new ServicioCompra();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    ServicioDetalleCompra servicioDetalleCompra = new ServicioDetalleCompra();

    ServicioCabeceraComprasSri servicioCabeceraComprasri = new ServicioCabeceraComprasSri();
    ServicioDetalleComprasSri servicioDetalleComprasSri = new ServicioDetalleComprasSri();

    private List<CabeceraCompra> listaCabeceraCompras = new ArrayList<CabeceraCompra>();
    private String buscar = "";
    private String buscarNumFac = "";
    private Date inicio = new Date();
    private Date fin = new Date();
    ServicioProveedor servicioProveedor = new ServicioProveedor();
    ServicioEstadoFactura servicioEstadoFactura = new ServicioEstadoFactura();
    UserCredential credential = new UserCredential();
    Parametrizar parametrizar = new Parametrizar();

    ServicioTipoIdentificacionCompra servicioTipoIdentificacionCompra = new ServicioTipoIdentificacionCompra();

    private List<ComprasSri> listaComprasSris = new ArrayList<ComprasSri>();

    /*subir rchivo*/
    //subir pdfArchivo
    private String filePath;
    byte[] buffer = new byte[1024 * 1024];
    private AImage fotoGeneral = null;

    private List<CabeceraCompraSri> listaCabeceraCompraSris = new ArrayList<CabeceraCompraSri>();

    public ListaComprasSri() {

        //muestra 7 dias atras
        Calendar calendar = Calendar.getInstance(); //obtiene la fecha de hoy 
        calendar.add(Calendar.DATE, -7); //el -3 indica que se le restaran 3 dias 
        inicio = calendar.getTime();

        Session sess = Sessions.getCurrent();
        UserCredential cre = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
        credential = cre;
        parametrizar = servicioParametrizar.FindALlParametrizar();
        findByBetweenFecha();
        amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();

    }

    private void buscarLikeNombre() {
        listaCabeceraCompras = servicioCompra.findCabProveedorSRI(buscar);
    }

    private void findByBetweenFecha() {
        listaCabeceraCompras = servicioCompra.findByBetweenFechaSRI(inicio, fin);
    }

    private void findComprasSriByBetweenFecha() {
        listaComprasSris = servicioComprasSri.findNoVerificadosBetweenFecha(inicio, fin);

    }

    private void findCabeceraComprasSriByBetweenFecha() {
        listaCabeceraCompraSris = servicioCabeceraComprasri.findByBetweenFechaSRI(inicio, fin);

    }

    private void findByNumFac() {
        listaCabeceraCompras = servicioCompra.findByNumeroFacturaSRI(buscarNumFac);
    }

    @Command
    @NotifyChange({"listaComprasSris", "inicio", "fin", "listaCabeceraCompraSris"})
    public void buscarComprasSri() {
        findComprasSriByBetweenFecha();
        findCabeceraComprasSriByBetweenFecha();
    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "buscar"})
    public void buscarForProveedor() {
        buscarLikeNombre();
    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "buscarNumFac"})
    public void buscarForNumFactura() {
        findByNumFac();
    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "inicio", "fin"})
    public void buscarForFechas() {
        findByBetweenFecha();
    }

    @Command
    public void modificarFactura(@BindingParam("valor") CabeceraCompra valor) {
        try {
//            if (Messagebox.show("¿Desea modificar el registro, recuerde que debe crear las reteniones nuevamente?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            final HashMap<String, CabeceraCompra> map = new HashMap<String, CabeceraCompra>();

            map.put("valor", valor);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/compra/modificarcompra.zul", null, map);
            window.doModal();
//            }
//            window.detach();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public List<CabeceraCompra> getListaCabeceraCompras() {
        return listaCabeceraCompras;
    }

    public void setListaCabeceraCompras(List<CabeceraCompra> listaCabeceraCompras) {
        this.listaCabeceraCompras = listaCabeceraCompras;
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

    public String getBuscarNumFac() {
        return buscarNumFac;
    }

    public void setBuscarNumFac(String buscarNumFac) {
        this.buscarNumFac = buscarNumFac;
    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }

    @Command
    public void exportListboxToExcel() throws Exception {
        try {
            File dosfile = new File(exportarExcel());
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO" + e.getMessage());
        }
    }

    private String exportarExcel() throws FileNotFoundException, IOException, ParseException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "compras.xls";
        System.out.println("Direccion del reporte  " + pathSalida);
        try {
            int j = 0;
            File archivoXLS = new File(pathSalida);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet("Compras");

            HSSFFont fuente = wb.createFont();
            fuente.setBoldweight((short) 700);
            HSSFCellStyle estiloCelda = wb.createCellStyle();
            estiloCelda.setWrapText(true);
            estiloCelda.setAlignment((short) 2);
            estiloCelda.setFont(fuente);

            HSSFCellStyle estiloCeldaInterna = wb.createCellStyle();
            estiloCeldaInterna.setWrapText(true);
            estiloCeldaInterna.setAlignment((short) 5);
            estiloCeldaInterna.setFont(fuente);

            HSSFCellStyle estiloCelda1 = wb.createCellStyle();
            estiloCelda1.setWrapText(true);
            estiloCelda1.setFont(fuente);

            HSSFRow r = null;

            HSSFCell c = null;
            r = s.createRow(0);

            HSSFCell ch0 = r.createCell(j++);
            ch0.setCellValue(new HSSFRichTextString("RUC"));
            ch0.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("Proveedor"));
            ch1.setCellStyle(estiloCelda);

            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("Factura"));
            ch2.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(j++);
            ch3.setCellValue(new HSSFRichTextString("Fecha emision"));
            ch3.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(j++);
            ch4.setCellValue(new HSSFRichTextString("Subtotal"));
            ch4.setCellStyle(estiloCelda);

            HSSFCell ch5 = r.createCell(j++);
            ch5.setCellValue(new HSSFRichTextString("Iva"));
            ch5.setCellStyle(estiloCelda);

            HSSFCell ch6 = r.createCell(j++);
            ch6.setCellValue(new HSSFRichTextString("Total"));
            ch6.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (CabeceraCompra item : listaCabeceraCompras) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getIdProveedor().getProvCedula()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getIdProveedor().getProvNombre()));

                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getCabNumFactura()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(sm.format(item.getCabFechaEmision())));

                HSSFCell c4 = r.createCell(i++);
                c4.setCellValue(new HSSFRichTextString(item.getCabSubTotal().toString()));

                HSSFCell c5 = r.createCell(i++);
                c5.setCellValue(new HSSFRichTextString(item.getCabIva().toString()));

                HSSFCell c6 = r.createCell(i++);
                c6.setCellValue(new HSSFRichTextString(item.getCabTotal().toString()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaCabeceraCompras.size(); k++) {
                s.autoSizeColumn(k);
            }
            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }

    @Command
    @NotifyChange({"listaCabeceraCompras", "listaCabeceraCompraSris", "inicio", "fin"})
    public void cargarComprasSRI()
            throws JRException, IOException, NamingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        AutorizarDocumentos autorizarDocumentos = new AutorizarDocumentos();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Tipoambiente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String folderDescargados = PATH_BASE + File.separator + "COMPRASDESCARGADAS"
                    + File.separator + new Date().getYear()
                    + File.separator + new Date().getMonth();
            String pathArchivoXML = "";

            File folderGen = new File(folderDescargados);
            if (!folderGen.exists()) {
                folderGen.mkdirs();
            }
            SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyy");

            File f = null;
            /*TRAEMOS EL DOCUMENTO DEL SRI*/
            for (ComprasSri noVerific : listaComprasSris) {
               // if (servicioCompra.findByAutorizacion(noVerific.getCsriAutorizacion()) == null) {

                    RespuestaComprobante resComprobante = autorizarDocumentos.autorizarComprobante(noVerific.getCsriAutorizacion());
                    for (Autorizacion autorizacion : resComprobante.getAutorizaciones().getAutorizacion()) {
                        FileOutputStream nuevo = null;
                        pathArchivoXML = folderDescargados + File.separator + autorizacion.getNumeroAutorizacion() + ".xml";
//                CREA EL ARCHIVO XML AUTORIZADO

                        nuevo = new FileOutputStream(pathArchivoXML);
                        nuevo.write(autorizacion.getComprobante().getBytes());

                        /*obtenemos el tipo de documento*/
//                          f = new File(pathArchivoXML);
//                         String tipoDoc = ArchivoUtils.obtenerValorXML(f, "/*/infoTributaria/codDoc");
                        if (!autorizacion.getEstado().equals("AUTORIZADO")) {
                            System.out.println("COMPROBANTE NO AUTORIZADO");
                        }
                        //para el caso que sea factura
                        if (noVerific.getCsriComprobante().trim().contains("Factura")) {
                            ec.gob.sri.comprobantes.modelo.factura.Factura adto
                                    = ec.gob.sri.comprobantes.util.xml.XML2Java.unmarshalFactura(pathArchivoXML);
                            procesaFactura(adto, autorizacion.getComprobante());
                        } else if (noVerific.getCsriComprobante().contains("Comprobante de Retenci")) {
                            //para el caso que sea retencion
                            ec.gob.sri.comprobantes.modelo.rentencion.ComprobanteRetencion comRetencion
                                    = ec.gob.sri.comprobantes.util.xml.XML2Java.unmarshalComprobanteRetencion(pathArchivoXML);

                        }

                    }
                //}
            }

        } catch (Exception ex) {
            Logger.getLogger(ListaFacturas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void procesaFactura(ec.gob.sri.comprobantes.modelo.factura.Factura adto, String xml) {
        SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyy");
        /*PARA UN PROVEEDOR EN ESPECIFICO*/
//                        if (adto.getInfoTributaria().getRuc().equals("1719631549001")) {
        servicioCabeceraComprasri.eliminarbyClaveAcceso(adto.getInfoTributaria().getClaveAcceso());

        CabeceraCompraSri cabeceraCompra;

        cabeceraCompra = new CabeceraCompraSri();
        cabeceraCompra.setCabAutorizacion(adto.getInfoTributaria().getClaveAcceso());
        cabeceraCompra.setCabClaveAcceso(adto.getInfoTributaria().getClaveAcceso());
        cabeceraCompra.setCabDescripcion("SRI");
        cabeceraCompra.setCabEstado("PA");
        cabeceraCompra.setCabXmlSri(xml);
        cabeceraCompra.setCabCasillero("500");
        /*formato de la fecha*/
        try {
            Date dt = sm.parse(adto.getInfoFactura().getFechaEmision());
            cabeceraCompra.setCabFecha(new Date());
            cabeceraCompra.setCabFechaEmision(dt);
        } catch (java.text.ParseException e) {
            System.out.println("ERROR FECHA " + e.getMessage());
        }

        cabeceraCompra.setCabHomologado("N");
        BigDecimal ice = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;
        for (Factura.InfoFactura.TotalConImpuestos.TotalImpuesto totalImpuesto : adto.getInfoFactura().getTotalConImpuestos().getTotalImpuesto()) {
            if (totalImpuesto.getCodigoPorcentaje().equals("0")) {
                ice = ice.add(totalImpuesto.getValor());
            } else if (totalImpuesto.getCodigoPorcentaje().equals("2")) {
                iva = iva.add(totalImpuesto.getValor());
            }
        }
        cabeceraCompra.setCabIva(iva);
        cabeceraCompra.setCabNumFactura(adto.getInfoTributaria().getSecuencial());
        cabeceraCompra.setCabProveedor(adto.getInfoTributaria().getRazonSocial());
        cabeceraCompra.setCabRetencionAutori("N");
        cabeceraCompra.setCabSubTotal(adto.getInfoFactura().getTotalSinImpuestos());
        cabeceraCompra.setCabTotal(adto.getInfoFactura().getTotalSinImpuestos().add(ice).add(iva));
        cabeceraCompra.setCabTraeSri(Boolean.TRUE);
        cabeceraCompra.setCabRucProveedor(adto.getInfoTributaria().getRuc());
        cabeceraCompra.setDrcCodigoSustento(adto.getInfoTributaria().getCodDoc());
        cabeceraCompra.setIdEstado(servicioEstadoFactura.findByEstCodigo("PA"));
        cabeceraCompra.setIdUsuario(credential.getUsuarioSistema());
        cabeceraCompra.setCategoriaFactura("Inventario");
        cabeceraCompra.setCabSubTotalCero(ice);
        /*VERIFICAMOS EL PROVEEDOR Y SI NO LO CREAMOS*/
        Proveedores prov = servicioProveedor.findProvCedula(adto.getInfoTributaria().getRuc());
        if (prov != null) {
            cabeceraCompra.setIdProveedor(prov);
        } else {
            Proveedores provNuevo = new Proveedores();
            //DEPENDE DEL PROVEEDOR SI ES CEDULA O RUC
            TipoIdentificacionCompra identificacionCompra = null;
            if (adto.getInfoTributaria().getRuc().length() == 13) {
                identificacionCompra = servicioTipoIdentificacionCompra.findByCedulaRuc("04");
            } else if (adto.getInfoTributaria().getRuc().length() == 10) {
                identificacionCompra = servicioTipoIdentificacionCompra.findByCedulaRuc("05");
            }
            provNuevo.setIdTipoIdentificacionCompra(identificacionCompra);
            provNuevo.setProvBanco("S/N");
            provNuevo.setProvNombre(adto.getInfoTributaria().getRazonSocial());
            provNuevo.setProvNomComercial(adto.getInfoTributaria().getNombreComercial());
            provNuevo.setProvCedula(adto.getInfoTributaria().getRuc());
            provNuevo.setProvDireccion(adto.getInfoTributaria().getDirMatriz());
            servicioProveedor.crear(provNuevo);
            cabeceraCompra.setIdProveedor(provNuevo);
            prov = provNuevo;
        }

        servicioCabeceraComprasri.crear(cabeceraCompra);

        DetalleCompraSri detalleCom = null;
        BigDecimal factorIva = BigDecimal.ONE.add(parametrizar.getParIvaActual().divide(BigDecimal.valueOf(100)));
        BigDecimal factorUtilidad = BigDecimal.ONE.add(BigDecimal.valueOf(0.47));
//                                CabeceraCompra_.cabEstado
        for (ec.gob.sri.comprobantes.modelo.factura.Factura.Detalles.Detalle detalle : adto.getDetalles().getDetalle()) {
            // detalleCom = new DetalleCompra();
            Producto buscado = servicioProducto.findByProdCodigo(detalle.getCodigoPrincipal());
            Producto nuevoProd = new Producto();
            detalleCom = new DetalleCompraSri();
            if (buscado == null && prov != null) {
                BigDecimal costoInicial = detalle.getPrecioUnitario().setScale(2, RoundingMode.FLOOR);
                BigDecimal calCostoCompr = (costoInicial.divide(factorIva, 3, RoundingMode.FLOOR));

                nuevoProd = new Producto();
                nuevoProd.setPordCostoCompra(calCostoCompr);
                nuevoProd.setPordCostoVentaFinal(detalle.getPrecioUnitario().multiply(factorUtilidad));
                nuevoProd.setPordCostoVentaRef(detalle.getPrecioUnitario());
                nuevoProd.setProdAbreviado("");
                nuevoProd.setProdCantMinima(BigDecimal.TEN);
                nuevoProd.setProdCantidadInicial(detalle.getCantidad());
                nuevoProd.setProdCodigo(detalle.getCodigoPrincipal());
                nuevoProd.setProdCostoPreferencial(BigDecimal.ZERO);
                nuevoProd.setProdCostoPreferencialDos(BigDecimal.ZERO);
                nuevoProd.setProdCostoPreferencialTres(BigDecimal.ZERO);
                nuevoProd.setProdIsPrincipal(Boolean.FALSE);
                nuevoProd.setProdIva(parametrizar.getParIvaActual());
                nuevoProd.setProdManoObra(BigDecimal.ZERO);
                nuevoProd.setProdNombre(detalle.getDescripcion());
                nuevoProd.setProdTrasnporte(BigDecimal.ZERO);
                nuevoProd.setProdUtilidadNormal(BigDecimal.ZERO);
                nuevoProd.setProdUtilidadPreferencial(BigDecimal.ZERO);
                servicioProducto.crear(nuevoProd);
                detalleCom.setIdProducto(nuevoProd);
            }

            if (buscado != null) {
                detalleCom.setIdProducto(buscado);
            }
            // detalleCom.setIdProducto(buscado);
            detalleCom.setIdCabeceraSri(cabeceraCompra);
            detalleCom.setIprodCantidad(detalle.getCantidad());
            detalleCom.setIprodDescripcion(detalle.getDescripcion());
            detalleCom.setIprodSubtotal(detalle.getPrecioUnitario());
            detalleCom.setIprodTotal(detalle.getCantidad().multiply(detalle.getPrecioUnitario()));

            servicioDetalleComprasSri.crear(detalleCom);
            System.out.println("DETALLE " + detalle.getCantidad()
                    + " " + detalle.getCodigoPrincipal()
                    + " " + detalle.getDescripcion()
                    + " " + detalle.getPrecioUnitario()
                    + " " + detalle.getPrecioTotalSinImpuesto());
        }

        findCabeceraComprasSriByBetweenFecha();
    }

    @Command
    @NotifyChange({"listaComprasSris", "inicio", "fin"})
    public void actualizar(@BindingParam("valor") CabeceraCompraSri valor) {
        servicioCabeceraComprasri.modificar(valor);
        Clients.showNotification("Registro actualizado",
                Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 1000, true);
    }

    @Command
    @NotifyChange({"listaComprasSris", "inicio", "fin"})
    public void subirArchivo() {

        try {
            String folderDescargados = PATH_BASE + File.separator + "COMPRASDESCARGADASTXT"
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
                String datosFormateados = "";
                for (int i = 0; i < campos1.length; i++) {
                    if (!campos1.equals("")) {
                        datosFormateados = datosFormateados + campos1[i] + "\t";
                    }

                }
                String[] campos2 = datosFormateados.split("\t");
                String[] campos3 = new String[campos2.length];
                int cantidadBlancos = 0;
                int contador = 0;
                for (int i = 0; i < campos2.length; i++) {
                    if (!campos2[i].equals("")) {
                        campos3[contador] = campos2[i];
                        contador++;
                    } else {
                        cantidadBlancos++;
                    }
                }
                ComprasSri comprasSri;
                SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyy");
                // myDate is the java.util.Date in yyyy-mm-dd format
                // Converting it into String using formatter
                //String strDate = sm.format(inicio);
                //Converting the String back to java.util.Date
                //    Date dt = sm.parse(strDate);
                String existentes = "";
                FileWriter flwriter = null;
                String pathTXT = folderDescargados + File.separator + "repetidos.txt";
                File descargar = new File(pathTXT);
                if (!descargar.exists()) {
                    descargar.createNewFile();
                }
                flwriter = new FileWriter(pathTXT);
                BufferedWriter bfwriter = new BufferedWriter(flwriter);
                Boolean existenRepetido = Boolean.FALSE;
                for (int i = 11; i <= campos3.length - (cantidadBlancos + 11); i++) {

                    comprasSri = new ComprasSri(
                            campos3[i],
                            campos3[++i],
                            campos3[++i],
                            AutorizarDocumentos.removeCaracteres(campos3[++i]),
                            sm.parse(campos3[++i].trim().replace("/", "-")),
                            sm.parse(campos3[++i].trim().replace("/", "-")),
                            campos3[++i],
                            campos3[++i],
                            campos3[++i],
                            campos3[++i],
                            campos3[++i],
                            "N");
                    System.out.println("iiiii " + i);
                    System.out.println("ComprasSri " + comprasSri.toString());
                    if (servicioComprasSri.findByAutorizacion(comprasSri.getCsriAutorizacion()) == null) {

                        servicioComprasSri.crear(comprasSri);
                    } else {
                        existenRepetido = Boolean.TRUE;
                        existentes = existentes + ";" + comprasSri.getCsriAutorizacion();

                    }

                }
                if (existenRepetido) {
                    bfwriter.write(existentes);
                    FileInputStream inputStream = new FileInputStream(descargar);
                    Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(descargar), descargar.getName());
                }
                bfwriter.close();
                Clients.showNotification("Informacion cargada", "info", null, "end_before", 1000, true);
                findComprasSriByBetweenFecha();
            }

        } catch (IOException e) {
            System.out.println("ERROR al subir la imagen IOException " + e.getMessage());
        } catch (java.text.ParseException e) {
            System.out.println("ERROR al subir la imagen ParseException " + e.getMessage());
        }
    }

    public List<ComprasSri> getListaComprasSris() {
        return listaComprasSris;
    }

    public void setListaComprasSris(List<ComprasSri> listaComprasSris) {
        this.listaComprasSris = listaComprasSris;
    }

    public List<CabeceraCompraSri> getListaCabeceraCompraSris() {
        return listaCabeceraCompraSris;
    }

    public void setListaCabeceraCompraSris(List<CabeceraCompraSri> listaCabeceraCompraSris) {
        this.listaCabeceraCompraSris = listaCabeceraCompraSris;
    }

}
