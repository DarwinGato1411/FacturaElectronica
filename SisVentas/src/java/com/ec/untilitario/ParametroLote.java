/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import java.util.Date;

/**
 *
 * @author Darwin
 */
public class ParametroLote {
    private Date inicio;
    private Date fin;

    public ParametroLote() {
    }

    public ParametroLote(Date inicio, Date fin) {
        this.inicio = inicio;
        this.fin = fin;
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
    
}
