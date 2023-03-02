/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

/**
 *
 * @author Darwin
 */
public class InfoPersona {
    private String nombre;
    private String direccion;

    public InfoPersona() {
    }

    
    public InfoPersona(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
    
}
