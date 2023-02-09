/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Tipoambiente;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioTipoAmbiente;
import com.ec.servicio.ServicioUsuario;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author gato
 */
public class NuevoTipoambiente {
    
    @Wire
    Window windowIdUsuario;
    ServicioUsuario servicioUsuario = new ServicioUsuario();
    private Tipoambiente tipoambiente = new Tipoambiente();
    private String accion = "create";
    UserCredential credential = new UserCredential();
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
//    UserCredential credential = new UserCredential();
//    private String amRuc = "";
//    private Tipoambiente amb = new Tipoambiente();
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
//    private Boolean readOnly = true;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Tipoambiente valor, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        if (valor != null) {
            this.tipoambiente = valor;
            
            accion = "update";
            
        } else {
            this.tipoambiente = new Tipoambiente();
            accion = "create";
            
        }
    }
    
    public NuevoTipoambiente() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());
    }
    
    @Command
    @NotifyChange("usuarioSistema")
    public void guardar() {
        
       
        if (tipoambiente != null && !tipoambiente.getAmRuc().equals("")
                    && !tipoambiente.getAmNombreComercial().equals("")
                    && !tipoambiente.getAmDireccionMatriz().equals("")) {
            
            if (tipoambiente.getAmRuc().length() != 13) {
                Clients.showNotification("Ingrese un RUC valido..!!",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 2000, true);
                return;
            }

            // verifica si existe sino lo crea
            // PRUEBAS
            if (accion.equals("create")) {
              
                tipoambiente.setAmDirBaseArchivos("//DOCUMENTOSRI");
                tipoambiente.setAmCodigo("1");
                tipoambiente.setAmDescripcion("PRODUCCION");
                tipoambiente.setAmEstado(Boolean.TRUE);
                tipoambiente.setAmIdEmpresa(1);
                tipoambiente.setAmUsuariosri("PRODUCCION");
                tipoambiente.setAmUrlsri("cel.sri.gob.ec");
                
                tipoambiente.setAmDirReportes("REPORTES");
                tipoambiente.setAmGenerados("GENERADOS");
                tipoambiente.setAmDirXml("XML");
                tipoambiente.setAmFirmados("FIRMADOS");
                tipoambiente.setAmTrasmitidos("TRASMITIDOS");
                tipoambiente.setAmDevueltos("DEVUELTOS");
                tipoambiente.setAmFolderFirma("FIRMA");
                tipoambiente.setAmAutorizados("AUTORIZADOS");
                tipoambiente.setAmNoAutorizados("NOAUTORIZADOS");
                tipoambiente.setAmTipoEmision("1");
                tipoambiente.setAmEnviocliente("ENVIARCLIENTE");
                tipoambiente.setAmEstab("001");
                tipoambiente.setAmPtoemi("001");
                
                tipoambiente.setAmPort("587");
                tipoambiente.setAmProtocol("smtp");
                tipoambiente.setAmUsuarioSmpt("no-reply@defactec.com");
                tipoambiente.setAmPassword("1h@t3Pap3r");
                tipoambiente.setAmHost("smtp.office365.com");
                tipoambiente.setLlevarContabilidad("NO");
                tipoambiente.setAmMicroEmp(Boolean.FALSE);
                tipoambiente.setAmAgeRet(Boolean.FALSE);
                tipoambiente.setAmContrEsp(Boolean.FALSE);
                tipoambiente.setAmExp(Boolean.FALSE);
                tipoambiente.setIdUsuario(credential.getUsuarioSistema());
                tipoambiente.setAmUnidadDisco("/");
                
                servicioTipoAmbiente.crear(tipoambiente);
            } else {
                servicioTipoAmbiente.modificar(tipoambiente);
            }
            
            windowIdUsuario.detach();
            
        } else {
            Messagebox.show("Verifique la informacion ingresada", "Atención", Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Tipoambiente getTipoambiente() {
        return tipoambiente;
    }

    public void setTipoambiente(Tipoambiente tipoambiente) {
        this.tipoambiente = tipoambiente;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public UserCredential getCredential() {
        return credential;
    }

    public void setCredential(UserCredential credential) {
        this.credential = credential;
    }
    
}
