/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.OrdenTrabajo;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioOrdenTrabajo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import org.jfree.chart.JFreeChart;
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
public class ListaOrden {

    ServicioOrdenTrabajo servicioOrdenTrabajo = new ServicioOrdenTrabajo();
    private List<OrdenTrabajo> listaDatos = new ArrayList<OrdenTrabajo>();
    //reporte
    AMedia fileContent = null;
    Connection con = null;
    private String buscar = "";

    //GRAFICA POR UBICACION
    JFreeChart jfreechartMes;
    private byte[] graficoBarrasMes;
    String pathSalidaMes = "";
    private AImage reporteMes;

    public ListaOrden() {
        consultarFactura();
    }

    private void consultarFactura() {
        listaDatos = servicioOrdenTrabajo.findOrdenTrabajoNumNombreApellido(buscar);
    }

    public List<OrdenTrabajo> getListaDatos() {
        return listaDatos;
    }

    public void setListaDatos(List<OrdenTrabajo> listaDatos) {
        this.listaDatos = listaDatos;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    @Command
    public void reporteCotizacion(@BindingParam("valor") OrdenTrabajo valor) throws JRException, IOException, NamingException, SQLException {
        reporteGeneral(valor.getOrdNumero());
    }

    @Command
    public void modificarOrden(@BindingParam("valor") OrdenTrabajo valor) throws JRException, IOException, NamingException, SQLException {
        try {
            final HashMap<String, ParamFactura> map = new HashMap<String, ParamFactura>();
            ParamFactura param = new ParamFactura();
            param.setIdFactura(String.valueOf(valor.getIdOrdenTrabajo()));
            param.setTipoDoc("ORD");
            param.setBusqueda("ninguna");
            map.put("valor", param);
            org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                    "/modificar/ordentrabajo.zul", null, map);
            window.doModal();
        } catch (Exception e) {
            Messagebox.show("Error " + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    public void reporteGeneral(Integer numeroFactura) throws JRException, IOException, NamingException, SQLException {
        EntityManager emf = HelperPersistencia.getEMF();
        try {

            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);
            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";

            reportPath = reportFile + File.separator + "ordentrabajo.jasper";

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
            System.out.println("ERROR EL PRESENTAR EL REPORTE " + e.getMessage());
        } catch (JRException e) {
            System.out.println("ERROR EL PRESENTAR EL REPORTE " + e.getMessage());
        } finally {
            if (emf != null) {
                emf.getTransaction().commit();
            }

        }

    }

    //buscart notas de venta
    @Command
    @NotifyChange({"listaDatos", "buscar"})
    public void buscarOrdenes() {

        consultarFactura();

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
