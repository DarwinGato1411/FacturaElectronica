/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.DetalleKardex;
import com.ec.entidad.FacturasActorizadaSri;
import com.ec.entidad.Kardex;
import com.ec.entidad.Producto;
import com.ec.entidad.Tipoambiente;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioDetalleKardex;
import com.ec.servicio.ServicioKardex;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.servicio.ServicioTipoKardex;
import com.ec.untilitario.CodigoQR;
import com.ec.untilitario.ConexionReportes;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.internet.ParseException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
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
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class AdmProducto {

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    ServicioProducto servicioProducto = new ServicioProducto();
    ServicioKardex servicioKardex = new ServicioKardex();
    ServicioDetalleKardex servicioDetalleKardex = new ServicioDetalleKardex();
    ServicioTipoKardex servicioTipoKardex = new ServicioTipoKardex();
    private List<Producto> listaProducto = new ArrayList<Producto>();

    private ListModelList<Producto> listaProductosModel;
    private Set<Producto> registrosSeleccionados = new HashSet<Producto>();

    private String buscarNombre = "";
    private String buscarCodigo = "";
    //reporte
    AMedia fileContent = null;
    Connection con = null;

    private static String PATH_BASE = "";
    private static String FOLDER_CODIGO_BARRAS = "";
    private static String PATH_CODIGO_BARRAS = "";

    private Integer cantidadCodBar = 1;

    public AdmProducto() {

        Tipoambiente amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();
        FOLDER_CODIGO_BARRAS = PATH_BASE + File.separator + "CODIGOBARRAS";

        File folderGen = new File(FOLDER_CODIGO_BARRAS);
        if (!folderGen.exists()) {
            folderGen.mkdirs();
        }
        findLikeNombre();
        getProductosModel();
    }

    private void getProductosModel() {
        setListaProductosModel(new ListModelList<Producto>(getListaProducto()));
        ((ListModelList<Producto>) listaProductosModel).setMultiple(true);
    }

    @Command
    public void seleccionarRegistros() {
        registrosSeleccionados = ((ListModelList<Producto>) getListaProductosModel()).getSelection();
    }

    private void findLikeNombre() {
        listaProducto = servicioProducto.findLikeProdNombre(buscarNombre);
    }

    private void findLikeProdCodigo() {
        listaProducto = servicioProducto.findLikeProdCodigo(buscarCodigo);
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void crearCodigoBarras() {

        List<String> listaImprime = new ArrayList<String>();
        for (Producto producto : registrosSeleccionados) {

            // CodigoQR.generarCodigoBarras(producto.getProdCodigo(), FOLDER_CODIGO_BARRAS + File.separator + producto.getProdCodigo() + ".png");
            // producto.setProdPathCodbar(FOLDER_CODIGO_BARRAS + File.separator + producto.getProdCodigo() + ".png");
            //servicioProducto.modificar(producto);
//            if (producto.getProdCodigo().matches("[0-9]*")) {
            listaImprime.add(producto.getProdCodigo());
//            }

        }
        servicioProducto.actulizarImpresion(listaImprime);
        try {
            reporteCodigosBarras();
        } catch (JRException ex) {
            Logger.getLogger(AdmProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdmProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdmProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AdmProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AdmProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdmProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(AdmProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void generarCodigosQR() {
        String pathQR = "";
        for (Producto producto : listaProducto) {

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/codigoqr");
            String reportPath = "";
            System.out.println("PATh codigos " + reportFile);
            pathQR = reportFile + reportPath + File.separator + producto.getProdNombre() + ".JPEG";
            producto.setProdQr(CodigoQR.generarCodigoQR(producto.getProdNombre() + " " + producto.getPordCostoVentaFinal(), pathQR));
            servicioProducto.modificar(producto);
//            File fichero = new File(pathQR);
//            if (fichero.delete()) {
//                System.out.println("El fichero ha sido borrado satisfactoriamente");
//            } else {
//                System.out.println("El fichero no puede ser borrado");
//            }
        }
    }

    @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void buscarLikeNombre() {

        findLikeNombre();
        getProductosModel();
    }

    @Command
    @NotifyChange({"listaProductosModel", "buscarCodigo"})
    public void buscarLikeCodigo() {

        findLikeProdCodigo();
        getProductosModel();
    }

    @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void productoPrincipal(@BindingParam("valor") Producto valor) {
        int nelementos = servicioProducto.findCountPrincipal();

        System.out.println("numero de elementos " + nelementos);
        if ((nelementos < 24 && valor.getProdPrincipal() == 0)) {
            System.out.println("ingresa a marcar");
            valor.setProdPrincipal(1);
            valor.setProdIsPrincipal(Boolean.TRUE);
            servicioProducto.modificar(valor);
            findLikeNombre();
        } else if (nelementos <= 24 && valor.getProdPrincipal() == 1) {
            System.out.println("INgresa a desmarcar");
            valor.setProdIsPrincipal(Boolean.FALSE);
            valor.setProdPrincipal(0);
            servicioProducto.modificar(valor);
            findLikeNombre();
            getProductosModel();
        } else if (nelementos >= 24) {
            Messagebox.show("El numero maximo de selecciones es: 24 productos", "Atención", Messagebox.OK, Messagebox.ERROR);
        }

    }

    @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void nuevoCliente() {
        buscarNombre = "";
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/producto.zul", null, null);
        window.doModal();
        findLikeNombre();
        getProductosModel();
    }

    @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void actualizarCliente(@BindingParam("valor") Producto valor) {
        buscarNombre = "";
        final HashMap<String, Producto> map = new HashMap<String, Producto>();
        map.put("valor", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/producto.zul", null, map);
        window.doModal();
       // findLikeNombre();
       // getProductosModel();
    }

    @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void eliminarCliente(@BindingParam("valor") Producto valor) {
        if (Messagebox.show("¿Seguro que desea eliminar el registro?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            servicioProducto.eliminar(valor);
            findLikeNombre();
            getProductosModel();
            Clients.showNotification("Eliminado correctamente", "Info", null, "end_center", 3000, true);

        } else {
        }

    }

    @Command
    @NotifyChange({"listaProductosModel", "buscarNombre"})
    public void inicializarKardex() {
        if (Messagebox.show("¿Seguro que desea inicializar el Kardex?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            Kardex kardex;
            List<Producto> listaKardex=servicioProducto.FindALlProducto();
            for (Producto producto : listaKardex) {
                if (servicioKardex.FindALlKardexs(producto) == null) {
                    kardex = new Kardex();
                    DetalleKardex detalleKardex = new DetalleKardex();
                    kardex.setIdProducto(producto);
                    kardex.setKarDetalle("INICIO DE INVENTARIO: " + producto.getProdNombre());
                    kardex.setKarFecha(new Date());
                    kardex.setKarFechaKardex(new Date());
                    kardex.setKarTotal(producto.getProdCantidadInicial());
                    servicioKardex.crear(kardex);
                    detalleKardex.setIdKardex(kardex);
                    detalleKardex.setDetkFechacreacion(new Date());
                    detalleKardex.setDetkFechakardex(new Date());
                    detalleKardex.setDetkCantidad(producto.getProdCantidadInicial());
                    detalleKardex.setDetkDetalles("Aumenta INICIO DE INVETARIO ");
                    detalleKardex.setDetkKardexmanual(Boolean.FALSE);
                    detalleKardex.setIdTipokardex(servicioTipoKardex.findByTipkSigla("ING"));
                    servicioDetalleKardex.crear(detalleKardex);
                }
            }
          //  findLikeNombre();
          //  getProductosModel();
        } else {
        }

    }

    @Command
    public void reporteCodigosQR() throws JRException, IOException,
            ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();

        try {

            emf.getTransaction().begin();
            con = emf.unwrap(java.sql.Connection.class);

            con = ConexionReportes.Conexion.conexion();
            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            //con = conexionReportes.conexion();

            reportPath = reportFile + "/codigosqr.jasper";

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
//        parametros.put("numfactura", numeroFactura);
            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);

            byte[] buf = JasperRunManager.runReportToPdf(is, null, con);
            InputStream mediais = new ByteArrayInputStream(buf);
            AMedia amedia = new AMedia("Reporte", "pdf", "application/pdf", mediais);
            fileContent = amedia;
            final HashMap<String, AMedia> map = new HashMap<String, AMedia>();
//para pasar al visor
            map.put("pdf", fileContent);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/venta/contenedorReporte.zul", null, map);
            window.doModal();
//        con.close();
            emf.getTransaction().commit();
        } catch (Exception e) {
            if (emf != null) {
                emf.getTransaction().rollback();
            }
        } finally {
            if (emf != null) {
                emf.close();
            }
        }

    }

    public String getBuscarCodigo() {
        return buscarCodigo;
    }

    public void setBuscarCodigo(String buscarCodigo) {
        this.buscarCodigo = buscarCodigo;
    }

    /*PRODUCTOS EN MODEL LIST*/
    public ListModelList<Producto> getListaProductosModel() {
        return listaProductosModel;
    }

    public void setListaProductosModel(ListModelList<Producto> listaProductosModel) {
        this.listaProductosModel = listaProductosModel;
    }

    public Set<Producto> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<Producto> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    public void reporteCodigosBarras() throws JRException, IOException,
            ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();

        try {

            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);

            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            //con = conexionReportes.conexion();

            reportPath = reportFile + "/codigobar.jasper";
            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);
            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("cantidad", cantidadCodBar);
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
//        con.close();
            emf.getTransaction().commit();
        } catch (Exception e) {
            if (emf != null) {
                emf.getTransaction().rollback();
            }
        } finally {
            if (emf != null) {
                emf.close();
            }
        }

    }

    public Integer getCantidadCodBar() {
        return cantidadCodBar;
    }

    public void setCantidadCodBar(Integer cantidadCodBar) {
        this.cantidadCodBar = cantidadCodBar;
    }
    
    /*EXPORTAR EXCEL*/
    
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

        String pathSalida = directorioReportes + File.separator + "productos.xls";
        System.out.println("Direccion del reporte  " + pathSalida);
        try {
            int j = 1;
            File archivoXLS = new File(pathSalida);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet("Autorizadas");

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

            HSSFCell chfe = r.createCell(0);
            chfe.setCellValue(new HSSFRichTextString("Codigo"));
            chfe.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("Descripcion"));
            ch1.setCellStyle(estiloCelda);

            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("P Compra"));
            ch2.setCellStyle(estiloCelda);
            
            HSSFCell ch21 = r.createCell(j++);
            ch21.setCellValue(new HSSFRichTextString("% Util"));
            ch21.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(j++);
            ch3.setCellValue(new HSSFRichTextString("P Venta"));
            ch3.setCellStyle(estiloCelda);
            
            HSSFCell ch33 = r.createCell(j++);
            ch33.setCellValue(new HSSFRichTextString("P Venta 2"));
            ch33.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(j++);
            ch4.setCellValue(new HSSFRichTextString("Grava Iva"));
            ch4.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (Producto item : listaProductosModel) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getProdCodigo().toString()));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getProdNombre()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getPordCostoCompra().toString()));

                 HSSFCell c11 = r.createCell(i++);
                c11.setCellValue(new HSSFRichTextString(item.getProdUtilidadNormal().toString()));
                
                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getPordCostoVentaFinal().toString()));
                
                HSSFCell c22 = r.createCell(i++);
                c22.setCellValue(new HSSFRichTextString(item.getProdCostoPreferencial().toString()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(item.getProdGrabaIva().toString()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaProductosModel.size(); k++) {
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
    public void exportListboxToExcelTodo() throws Exception {
        try {
            List<Producto> listarTodo = servicioProducto.FindALlProducto();
            File dosfile = new File(exportarExcelTodo(listarTodo));
            if (dosfile.exists()) {
                FileInputStream inputStream = new FileInputStream(dosfile);
                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR AL DESCARGAR EL ARCHIVO" + e.getMessage());
        }
    }
     
      private String exportarExcelTodo(List<Producto> listarTodo) throws FileNotFoundException, IOException, ParseException {
        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/reportes");

        Date date = new Date();
        SimpleDateFormat fhora = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sm = new SimpleDateFormat("yyy-MM-dd");
        String strDate = sm.format(date);

        String pathSalida = directorioReportes + File.separator + "productos.xls";
        System.out.println("Direccion del reporte  " + pathSalida);
        try {
            int j = 1;
            File archivoXLS = new File(pathSalida);
            if (archivoXLS.exists()) {
                archivoXLS.delete();
            }
            archivoXLS.createNewFile();
            FileOutputStream archivo = new FileOutputStream(archivoXLS);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet s = wb.createSheet("Autorizadas");

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

            HSSFCell chfe = r.createCell(0);
            chfe.setCellValue(new HSSFRichTextString("Codigo"));
            chfe.setCellStyle(estiloCelda);

            HSSFCell ch1 = r.createCell(j++);
            ch1.setCellValue(new HSSFRichTextString("Descripcion"));
            ch1.setCellStyle(estiloCelda);

            HSSFCell ch2 = r.createCell(j++);
            ch2.setCellValue(new HSSFRichTextString("P Compra"));
            ch2.setCellStyle(estiloCelda);
            
            HSSFCell ch21 = r.createCell(j++);
            ch21.setCellValue(new HSSFRichTextString("% Util"));
            ch21.setCellStyle(estiloCelda);

            HSSFCell ch3 = r.createCell(j++);
            ch3.setCellValue(new HSSFRichTextString("P Venta"));
            ch3.setCellStyle(estiloCelda);

            HSSFCell ch4 = r.createCell(j++);
            ch4.setCellValue(new HSSFRichTextString("Grava Iva"));
            ch4.setCellStyle(estiloCelda);

            int rownum = 1;
            int i = 0;

            for (Producto item : listarTodo) {
                i = 0;

                r = s.createRow(rownum);

                HSSFCell cf = r.createCell(i++);
                cf.setCellValue(new HSSFRichTextString(item.getProdCodigo().toString()));

                HSSFCell c0 = r.createCell(i++);
                c0.setCellValue(new HSSFRichTextString(item.getProdNombre()));

                HSSFCell c1 = r.createCell(i++);
                c1.setCellValue(new HSSFRichTextString(item.getPordCostoCompra().toString()));

                 HSSFCell c11 = r.createCell(i++);
                c11.setCellValue(new HSSFRichTextString(item.getProdUtilidadNormal().toString()));
                
                HSSFCell c2 = r.createCell(i++);
                c2.setCellValue(new HSSFRichTextString(item.getPordCostoVentaFinal().toString()));

                HSSFCell c3 = r.createCell(i++);
                c3.setCellValue(new HSSFRichTextString(item.getProdGrabaIva().toString()));
                /*autemta la siguiente fila*/
                rownum += 1;

            }
            for (int k = 0; k <= listaProductosModel.size(); k++) {
                s.autoSizeColumn(k);
            }
            wb.write(archivo);
            archivo.close();

        } catch (IOException e) {
            System.out.println("error " + e.getMessage());
        }
        return pathSalida;

    }



}
