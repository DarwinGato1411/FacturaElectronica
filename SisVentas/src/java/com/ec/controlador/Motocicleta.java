/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.dao.DetalleFacturaDAO;
import com.ec.entidad.Cliente;
import com.ec.entidad.Tipoadentificacion;
import com.ec.servicio.ServicioCliente;
import com.ec.servicio.ServicioTipoIdentificacion;
import com.ec.untilitario.ArchivoUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class Motocicleta {

    private DetalleFacturaDAO detalledao;
    @Wire
    Window windowMoto;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") DetalleFacturaDAO detalleFacturaDAO, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        detalledao = detalleFacturaDAO;
    }

    @Command
    public void guardar() {

        windowMoto.detach();

    }

    public DetalleFacturaDAO getDetalledao() {
        return detalledao;
    }

    public void setDetalledao(DetalleFacturaDAO detalledao) {
        this.detalledao = detalledao;
    }

}
