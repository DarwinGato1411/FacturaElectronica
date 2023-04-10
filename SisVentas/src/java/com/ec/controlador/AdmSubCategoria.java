/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Subcategoria;
import com.ec.entidad.Tipoambiente;
import com.ec.servicio.ServicioSubCategoria;
import com.ec.servicio.ServicioTipoAmbiente;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author gato
 */
public class AdmSubCategoria {

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();



    //reporte
    AMedia fileContent = null;
    Connection con = null;

    private static String PATH_BASE = "";
    private static String FOLDER_CODIGO_BARRAS = "";




    /*subcategorias*/
    private List<Subcategoria> listaSubcategoria = new ArrayList<Subcategoria>();
    ServicioSubCategoria servicioSubCategoria = new ServicioSubCategoria();
    /*subcategorias*/

    public AdmSubCategoria() {

        Tipoambiente amb = servicioTipoAmbiente.FindALlTipoambiente();
        //OBTIENE LAS RUTAS DE ACCESO A LOS DIRECTORIOS DE LA TABLA TIPOAMBIENTE
        PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                + amb.getAmDirXml();
        FOLDER_CODIGO_BARRAS = PATH_BASE + File.separator + "CODIGOBARRAS";

        File folderGen = new File(FOLDER_CODIGO_BARRAS);
        if (!folderGen.exists()) {
            folderGen.mkdirs();
        }
 
        consultarSubCategorias();
    
    }

    private void consultarSubCategorias() {

        listaSubcategoria = servicioSubCategoria.findLikeDescipcion("");
    }

 

    /*SUBCATEGORIA*/
    @Command
    @NotifyChange({"listaSubcategoria", "buscarNombre"})
    public void nuevoSubCategoria() {
        
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/subcategoria.zul", null, null);
        window.doModal();
    
        consultarSubCategorias();
    }

    @Command
    @NotifyChange({"listaSubcategoria", "buscarNombre"})
    public void actualizarSubCategoria(@BindingParam("valor") Subcategoria valor) {
       
        final HashMap<String, Subcategoria> map = new HashMap<String, Subcategoria>();
        map.put("valor", valor);
        org.zkoss.zul.Window window = (org.zkoss.zul.Window) Executions.createComponents(
                "/nuevo/subcategoria.zul", null, map);
        window.doModal();
    
        consultarSubCategorias();
    }

    public List<Subcategoria> getListaSubcategoria() {
        return listaSubcategoria;
    }

    public void setListaSubcategoria(List<Subcategoria> listaSubcategoria) {
        this.listaSubcategoria = listaSubcategoria;
    }

    
}
