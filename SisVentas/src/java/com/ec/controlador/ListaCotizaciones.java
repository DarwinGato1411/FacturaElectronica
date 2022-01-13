/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Factura;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioFactura;
import com.ec.untilitario.ParamFactura;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class ListaCotizaciones {

    ServicioFactura servicioFactura = new ServicioFactura();
    private List<Factura> lstFacturas = new ArrayList<Factura>();
    //reporte
    AMedia fileContent = null;
    Connection con = null;
    private String buscarCliente = "";
    private String estadoBusqueda = "";
    private BigDecimal porCobrar = BigDecimal.ZERO;

    //GRAFICA POR UBICACION
    JFreeChart jfreechartMes;
    private byte[] graficoBarrasMes;
    String pathSalidaMes = "";
    private AImage reporteMes;

    public ListaCotizaciones() {
        consultarFactura();
    }

    private void consultarFactura() {
        lstFacturas = servicioFactura.findAllProformas();
    }

    public List<Factura> getLstFacturas() {
        return lstFacturas;
    }

    public void setLstFacturas(List<Factura> lstFacturas) {
        this.lstFacturas = lstFacturas;
    }

    public String getBuscarCliente() {
        return buscarCliente;
    }

    public void setBuscarCliente(String buscarCliente) {
        this.buscarCliente = buscarCliente;
    }

    public String getEstadoBusqueda() {
        return estadoBusqueda;
    }

    public void setEstadoBusqueda(String estadoBusqueda) {
        this.estadoBusqueda = estadoBusqueda;
    }

    public BigDecimal getPorCobrar() {
        return porCobrar;
    }

    public void setPorCobrar(BigDecimal porCobrar) {
        this.porCobrar = porCobrar;
    }

    @Command
    public void reporteCotizacion(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getFacNumProforma(), "DET");
    }

    @Command

    public void reporteCotizacionPuntoVent(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getFacNumProforma(), "PROFPV");
    }

    @Command
    public void reporteCotizacionSinDet(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getFacNumProforma(), "SINDET");
    }

    @Command
    @NotifyChange({"lstFacturas", "buscarCliente"})
    public void modificarCotizacion(@BindingParam("valor") Factura valor) throws JRException, IOException, NamingException, SQLException {
        try {
            final HashMap<String, ParamFactura> map = new HashMap<String, ParamFactura>();
            ParamFactura param = new ParamFactura();
            param.setIdFactura(String.valueOf(valor.getIdFactura()));
            param.setTipoDoc(valor.getFacTipo());
            param.setBusqueda("ninguna");
            map.put("valor", param);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/modificar/factura.zul", null, map);
            window.doModal();
//            window.detach();
            consultarFacturas();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public void reporteGeneral(Integer numeroFactura, String consindet) throws JRException, IOException, NamingException, SQLException {
        EntityManager emf = HelperPersistencia.getEMF();
        try {

            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);
            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            if (consindet.equals("DET")) {
                reportPath = reportFile + File.separator + "proforma.jasper";
            } else if (consindet.equals("SINDET")) {
                reportPath = reportFile + File.separator + "proformasindet.jasper";

            } else {
                reportPath = reportFile + File.separator + "proformapuntoventa.jasper";
            }

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
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException " + e.getMessage());
        } catch (JRException e) {
            System.out.println("JRException " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }

        }

    }

    //buscart notas de venta
    @Command
    @NotifyChange({"lstFacturas", "buscarCliente"})
    public void buscarLikeCliente() {

        consultarFacturas();

    }

    private void consultarFacturas() {
        lstFacturas = servicioFactura.findLikeProformaCliente(buscarCliente);
    }

    @Command
    @NotifyChange({"lstFacturas", "buscarCliente", "porCobrar"})
    public void buscarEstado() {
        consultarFacturasEstado();

    }

    private void consultarFacturasEstado() {
        lstFacturas = servicioFactura.FindEstado(estadoBusqueda);
        porCobrar = BigDecimal.ZERO;
        for (Factura factura : lstFacturas) {
            porCobrar = porCobrar.add(factura.getFacSaldo());
        }
    }

    //estadistica de ventas por mes
    @Command
    @NotifyChange({"reporteUbicacion"})
    public void graficarForMes() throws IOException {
        List<Factura> lstModel = servicioFactura.FindALlFacturaMaxVeinte();

        //freechart
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

        for (Factura item : lstModel) {

            defaultcategorydataset.addValue(item.getFacTotal(), item.getFacFecha(), item.getFacTipo());

        }

        jfreechartMes = ChartFactory.createBarChart(
                "ESTADÍSTICA POR VENTA MENSUAL", // título del
                // grafico
                "", // título de las categorias(eje x)
                "", // titulo de las series(eje y)
                defaultcategorydataset, // conjunto de datos
                PlotOrientation.VERTICAL, // orientación del gráfico
                true, // incluye o no las series
                false, // tooltips?
                false // URLs?
        );
        jfreechartMes.setBackgroundPaint(Color.decode("#ffffff"));
        // plot maneja el dataset, axes(categories and series) y el rendered
        CategoryPlot plot = (CategoryPlot) jfreechartMes.getPlot();

        // renderer se uitiliza para getionar las barras
        CategoryItemRenderer renderer = plot.getRenderer();
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator(
                "{2}", new DecimalFormat("0"));
        renderer.setBaseItemLabelGenerator(generator);

        BarRenderer rerender1 = (BarRenderer) plot.getRenderer();
        rerender1.setBaseItemLabelsVisible(true);
        rerender1.setItemMargin(0.0);
        rerender1.setShadowVisible(false);

        renderer.setSeriesPaint(0, Color.decode("#4198af"));
        renderer.setSeriesPaint(1, Color.decode("#91c3d5"));
        renderer.setBaseItemLabelPaint(Color.black);

//        plot.setBackgroundPaint(Color.WHITE);
//        plot.setDomainGridlinePaint(Color.white);
//        plot.setRangeGridlinePaint(Color.white);
//        plot.setDomainGridlinesVisible(true);
//        plot.setRangeGridlinesVisible(true);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        // legendSeries para ubicar las series
        LegendTitle legendSeries = jfreechartMes.getLegend();
        RectangleEdge posicion = null;
        legendSeries.setPosition(posicion.RIGHT);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        BufferedImage image = jfreechartMes.createBufferedImage(650, 480);
        graficoBarrasMes = ChartUtilities.encodeAsPNG(image);
        reporteMes = new AImage("foto", graficoBarrasMes);

        String directorioReportes = Executions.getCurrent().getDesktop().getWebApp()
                .getRealPath("/reportes");

        //crea la carpeta en el caso que no exista
        File baseDir = new File(directorioReportes);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        pathSalidaMes = directorioReportes + File.separator + "reportGenero.jpg";
        System.out.println("RUTA " + pathSalidaMes);
        ChartUtilities.saveChartAsJPEG(new File(pathSalidaMes), jfreechartMes, 500,
                300);

    }

    public AMedia getFileContent() {
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }

    public JFreeChart getJfreechartMes() {
        return jfreechartMes;
    }

    public void setJfreechartMes(JFreeChart jfreechartMes) {
        this.jfreechartMes = jfreechartMes;
    }

    public byte[] getGraficoBarrasMes() {
        return graficoBarrasMes;
    }

    public void setGraficoBarrasMes(byte[] graficoBarrasMes) {
        this.graficoBarrasMes = graficoBarrasMes;
    }

    public String getPathSalidaMes() {
        return pathSalidaMes;
    }

    public void setPathSalidaMes(String pathSalidaMes) {
        this.pathSalidaMes = pathSalidaMes;
    }

    public AImage getReporteMes() {
        return reporteMes;
    }

    public void setReporteMes(AImage reporteMes) {
        this.reporteMes = reporteMes;
    }

}
