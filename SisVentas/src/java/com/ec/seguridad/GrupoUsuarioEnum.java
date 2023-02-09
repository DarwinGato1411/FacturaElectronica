/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.seguridad;

/**
 *
 * @author Personal
 */
public enum GrupoUsuarioEnum {
    USUARIO ("usuario",Integer.valueOf("2")),
   ADMINISTRADOR("administrador",Integer.valueOf("1"));
    
    private String descripcion;
    private Integer codigo;
    
    GrupoUsuarioEnum(String descripcion, Integer codigo)
    {
        this.setDescripcion(descripcion);
        this.setCodigo(codigo);
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }


}
