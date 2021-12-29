/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Pedidos;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioPedido;
import com.ec.untilitario.ConexionReportes;
import com.ec.untilitario.PedidoDAO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class AdministrarPedidos {

    ServicioPedido servicioPedido = new ServicioPedido();
    //variables
    private ListModelList<PedidoDAO> listaPedidoModel;
    private List<PedidoDAO> listaPedidoDatos = new ArrayList<PedidoDAO>();
    private Set<PedidoDAO> registrosSeleccionados = new HashSet<PedidoDAO>();

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") String valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        agregarRegistroVacio();

    }

    public AdministrarPedidos() {
        cargarDatos();
        getPedidoDAO();
    }

    private void cargarDatos() {
        listaPedidoDatos.clear();
        List<Pedidos> lista = servicioPedido.findAllPendientes();
        for (Pedidos pedidos : lista) {
            listaPedidoDatos.add(new PedidoDAO(pedidos.getPedCantidad(),
                    pedidos.getPedDescripcion(), pedidos.getPedFecha(),
                    pedidos.getPedEstado(), pedidos.getPedProveedor(), pedidos));
        }
    }

    private void getPedidoDAO() {

        setListaPedidoModel(new ListModelList<PedidoDAO>(getListaPedidoDatos()));
        ((ListModelList<PedidoDAO>) listaPedidoModel).setMultiple(true);
    }

    @Command
    public void seleccionarRegistros() {
        registrosSeleccionados = ((ListModelList<PedidoDAO>) getListaPedidoModel()).getSelection();
    }

    public void agregarRegistroVacio() {

        //ingresa un registro vacio
        boolean registroVacio = true;
        List<PedidoDAO> listaPedido = listaPedidoModel.getInnerList();

        for (PedidoDAO item : listaPedido) {
            if (item.getPedCantidad().doubleValue() <= 0) {
                registroVacio = false;
                break;
            }
        }

        System.out.println("existe un vacio " + registroVacio);
        if (registroVacio) {

            ((ListModelList<PedidoDAO>) listaPedidoModel).add(new PedidoDAO(BigDecimal.ZERO, "", new Date(), "SOLICITADO", "", null));
        }
    }

    public ListModelList<PedidoDAO> getListaPedidoModel() {
        return listaPedidoModel;
    }

    public void setListaPedidoModel(ListModelList<PedidoDAO> listaPedidoModel) {
        this.listaPedidoModel = listaPedidoModel;
    }

    public List<PedidoDAO> getListaPedidoDatos() {
        return listaPedidoDatos;
    }

    @Command
    @NotifyChange({"listaPedidoModel"})
    public void nuevoRegistro() {
        try {


            //ingresa un registro vacio
            boolean registroVacio = true;
            List<PedidoDAO> listaPedido = listaPedidoModel.getInnerList();

            for (PedidoDAO item : listaPedido) {
                if (item.getPedCantidad().doubleValue() <= 0) {
                    registroVacio = false;
                    break;
                }
            }

            System.out.println("existe un vacio " + registroVacio);
            if (registroVacio) {

                ((ListModelList<PedidoDAO>) listaPedidoModel).add(new PedidoDAO(BigDecimal.ZERO, "", new Date(), "SOLICITADO", "", null));
            }
        } catch (Exception e) {
            Messagebox.show("Ocurrio un error al calcular los valores", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public void setListaPedidoDatos(List<PedidoDAO> listaPedidoDatos) {
        this.listaPedidoDatos = listaPedidoDatos;
    }

    public Set<PedidoDAO> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<PedidoDAO> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    @Command
    @NotifyChange({"listaPedidoModel"})
    public void guardar() {

        List<PedidoDAO> listaPedido = listaPedidoModel.getInnerList();
        if (listaPedido.size() > 0) {
            for (PedidoDAO item : listaPedido) {
                if (item.getPedCantidad().doubleValue() > 0) {
                    if (item.getPedido() != null) {
                        Pedidos p = item.getPedido();
                        p.setPedCantidad(item.getPedCantidad());
                        p.setPedDescripcion(item.getPedDescripcion());
                        p.setPedProveedor(item.getPedProveedor());
                        servicioPedido.modificar(p);
                    } else {
                        servicioPedido.crear(new Pedidos(item.getPedCantidad(),
                                item.getPedDescripcion(), new Date(),
                                item.getPedEstado(), item.getPedProveedor()));
                    }
                }
            }

        }
        cargarDatos();
        getPedidoDAO();
        agregarRegistroVacio();
    }

    @Command
    @NotifyChange({"listaPedidoModel"})
    public void confirmar() {

        for (PedidoDAO item : registrosSeleccionados) {
            System.out.println("id " + item.getPedido().getCodPedido());
            System.out.println("descrip " + item.getPedido().getPedDescripcion());
            item.getPedido().setPedEstado("CONFIRMADO");
            servicioPedido.modificar(item.getPedido());
        }
        cargarDatos();
        getPedidoDAO();
        agregarRegistroVacio();

    }

    @Command
    @NotifyChange({"listaPedidoModel"})
    public void eliminarItem(@BindingParam("valor") PedidoDAO valor) {
        if (Messagebox.show("¿Seguro que desea eliminar el item?", "Atención", Messagebox.YES | Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
            servicioPedido.eliminar(valor.getPedido());
            cargarDatos();
            getPedidoDAO();
            agregarRegistroVacio();

        }
    }
    //reportes
    //reporte
    AMedia fileContent = null;
    Connection con = null;

    @Command
    public void reportePedido() throws JRException,
            IOException, ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();

        try {


            emf.getTransaction().begin();
            con = emf.unwrap(java.sql.Connection.class);
            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            //con = conexionReportes.conexion();


            reportPath = reportFile + File.separator + "pedido.jasper";





            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
//            parametros.put("numfactura", numeroFactura);


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
            con.close();
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
}