/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.VistaVentaDiaria;
import com.ec.servicio.ServicioVistaVentaDiaria;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;

public class ListaVentaDiaria {

    ServicioVistaVentaDiaria servicioVistaVentaDiaria = new ServicioVistaVentaDiaria();
    private List<VistaVentaDiaria> listaVentaDiaria = new ArrayList<VistaVentaDiaria>();
    private Date fechaBusqueda = new Date();
    private Date fechaInicio = new Date();
    private Date fechaFin = new Date();
    private Date fechaFinBus = new Date();

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
    //rango de fechas

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ListaVentaDiaria() {
        findVentasDiaria();
    }

    private void findVentasDiaria() {
        listaVentaDiaria = servicioVistaVentaDiaria.FindALlVistaVentaDiaria();
    }

    private void findVentasDiariaForFecha() {
        listaVentaDiaria = servicioVistaVentaDiaria.findBetweenFecha(fechaBusqueda, fechaFinBus);
    }

    @Command
    @NotifyChange({"listaVentaDiaria", "fechaBusqueda"})
    public void buscarForFecha() {
        findVentasDiariaForFecha();

    }

    public List<VistaVentaDiaria> getListaVentaDiaria() {
        return listaVentaDiaria;
    }

    public void setListaVentaDiaria(List<VistaVentaDiaria> listaVentaDiaria) {
        this.listaVentaDiaria = listaVentaDiaria;
    }

    public Date getFechaBusqueda() {
        return fechaBusqueda;
    }

    public void setFechaBusqueda(Date fechaBusqueda) {
        this.fechaBusqueda = fechaBusqueda;
    }

    @Command
    @NotifyChange({"reporteMes"})
    public void graficarForMes() throws IOException {
        List<VistaVentaDiaria> lstVenta = servicioVistaVentaDiaria.findBetweenFecha(fechaInicio, fechaFin);

        //freechart
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();

//        SimpleDateFormat formato =
//                new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formato
                = new SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        for (VistaVentaDiaria item : lstVenta) {
            defaultcategorydataset.addValue(item.getTotal(), formato.format(item.getFecha()), "VENTAS");
//            defaultcategorydataset.addValue(item.getTotal(), "ventas", item.getFecha());

        }

        jfreechartMes = ChartFactory.createBarChart(
                "ESTADÍSTICA POR VENTA DIARIA", // título del
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
        CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
//                "{2}", new DecimalFormat("####,###"));
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

        BufferedImage image = jfreechartMes.createBufferedImage(900, 480);
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

    public Date getFechaFinBus() {
        return fechaFinBus;
    }

    public void setFechaFinBus(Date fechaFinBus) {
        this.fechaFinBus = fechaFinBus;
    }

}
