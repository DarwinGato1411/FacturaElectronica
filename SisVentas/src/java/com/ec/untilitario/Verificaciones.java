/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import com.ec.entidad.NumeroDocumentosEmitidos;
import com.ec.entidad.Parametrizar;
import com.ec.servicio.ServicioParametrizar;
import com.ec.vista.servicios.ServicioNumeroDocumentosEmitidos;
import java.util.Date;

/**
 *
 * @author Darwin
 */
public class Verificaciones {

    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    ServicioNumeroDocumentosEmitidos servicioNumeroDocumentosEmitidos = new ServicioNumeroDocumentosEmitidos();

    public Boolean verificarNumeroDocumentos() {
        Date caduca = new Date();
        Parametrizar param = servicioParametrizar.FindALlParametrizar();
        NumeroDocumentosEmitidos emitidos = servicioNumeroDocumentosEmitidos.findByMes(caduca.getMonth() + 1);

        if (param.getParPlanBasico()) {
            Integer numeroDocumentos = emitidos.getNumero() != null ? emitidos.getNumero().intValue() : 0;
            System.out.println("numeroDocumentos " + numeroDocumentos);
            if (numeroDocumentos > param.getParNumeroFactura()) {

                return false;
            }
        }
        return true;
    }
}
