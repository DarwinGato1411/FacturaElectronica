/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Parametrizar;
import com.ec.servicio.ServicioParametrizar;
import java.util.Date;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author Darwin
 */
public class VerificaCaduca {
    
    private Boolean caducaAct=Boolean.FALSE;
     Date actual = new Date();
    Date caduca = new Date();

    private int dias = 0;

    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
    
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    
    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") String valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        Parametrizar param = servicioParametrizar.FindALlParametrizar();
        caduca = param.getParCaduca();

//        System.out.println("vigente  " + actual + " vegente hasta " + caduca);
//        if (caduca.after(actual)) {
            dias = (int) ((caduca.getTime() - actual.getTime()) / MILLSECS_PER_DAY);
        if (dias>0) {
            caducaAct=Boolean.TRUE;
        }
//            Clients.showNotification("Estimado distribuidor su plataforma debe ser renovada en  " + dias + " dias, caso contrario se encontrara fuera de linea contatctese con el proveedor",
//                    Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 5000, true);
//            System.out.println("DIAS   " + dias);
//        }
//
//        if (caduca.after(actual)) {
//
//        } else {
//            Clients.showNotification("Su plataforma debe ser renovada, contactese con su distribuidor",
//                    Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 5000, true);
//            return;
//        }
        
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public Boolean getCaducaAct() {
        return caducaAct;
    }

    public void setCaducaAct(Boolean caducaAct) {
        this.caducaAct = caducaAct;
    }
    
    
}
