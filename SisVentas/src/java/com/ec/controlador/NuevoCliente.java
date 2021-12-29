/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.Cliente;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Tipoadentificacion;
import com.ec.servicio.ServicioCliente;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioTipoIdentificacion;
import com.ec.untilitario.AduanaJson;
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
public class NuevoCliente {

    ServicioTipoIdentificacion servicioTipoIdentificacion = new ServicioTipoIdentificacion();
    private Cliente cliente = new Cliente();
    ServicioCliente servicioCliente = new ServicioCliente();
    private String accion = "create";
    private String clietipo = "0"; //0 normal 1 preferencial 1 -> 2 preferencial 2 -> 3 - preferencial 3
    private Date fechaReg = new Date();
    private List<Tipoadentificacion> listaIdentificaciones = new ArrayList<Tipoadentificacion>();
    private Tipoadentificacion tipoadentificacion = null;
    //datos por defecto
    ServicioParametrizar servicioParametrizar = new ServicioParametrizar();
    private Parametrizar parametrizar = null;
    @Wire
    Window windowCliente;

    @AfterCompose
    public void afterCompose(@ExecutionArgParam("valor") Cliente cliente, @ContextParam(ContextType.VIEW) Component view) {
        Selectors.wireComponents(view, this, false);
        listaIdentificaciones = servicioTipoIdentificacion.FindALlTipoadentificacion();
        parametrizar = servicioParametrizar.FindALlParametrizar();
        if (cliente != null) {
            this.cliente = cliente;
            tipoadentificacion = cliente.getIdTipoIdentificacion();
            clietipo = "0";
            accion = "update";
        } else {
            this.cliente = new Cliente();
            this.cliente.setCiudad(parametrizar.getParCiudad() != null ? parametrizar.getParCiudad() : "");
            this.cliente.setCliDireccion(parametrizar.getParCiudad() != null ? parametrizar.getParCiudad() : "");
            this.cliente.setCliMontoAsignado(BigDecimal.valueOf(999999));
            this.cliente.setCliMovil("0999999999");
            this.cliente.setCliTelefono("099999999");
            this.cliente.setCliCorreo(parametrizar.getParCorreoDefecto() != null ? parametrizar.getParCorreoDefecto() : "");
            accion = "create";
        }

    }
@Command
    @NotifyChange({"cliente"})
    public void buscarAduana() {
        if (cliente.getCliCedula() != null) {
            if (!cliente.getCliCedula().equals("")) {
                String cedulaBuscar = "";
                if (cliente.getCliCedula().length() > 10) {
                    cedulaBuscar = cliente.getCliCedula().substring(0, 10);
                } else {
                    cedulaBuscar = cliente.getCliCedula();
                }
                AduanaJson aduana = ArchivoUtils.obtenerdatoAduana(cedulaBuscar);
                if (aduana != null) {

                    String nombreApellido[] = aduana.getNombre().split(" ");
                    String nombrePersona = "";
                    String apellidoPersona = "";
                    switch (nombreApellido.length) {
                        case 1:
                            apellidoPersona = nombreApellido[0];
                            nombrePersona = "A";
                            break;
                        case 2:
                            apellidoPersona = nombreApellido[0];
                            nombrePersona = nombreApellido[1];
                            break;
                        case 3:
                            apellidoPersona = nombreApellido[0] + " " + nombreApellido[1];
                            nombrePersona = nombreApellido[2];
                            break;
                        case 4:
                            apellidoPersona = nombreApellido[0] + " " + nombreApellido[1];
                            nombrePersona = nombreApellido[2] + " " + nombreApellido[3];
                            break;
                        default:
                            break;
                    }
                    cliente.setCliApellidos(apellidoPersona);
                    cliente.setCliNombres(nombrePersona);
                    cliente.setCliNombre(nombrePersona + " " + apellidoPersona);
                    cliente.setCliRazonSocial(nombrePersona + " " + apellidoPersona);
                }
            }
        }

    }

    @Command
    @NotifyChange({"cliente"})
    public void actualizarTipo() {
    }

    @Command
    public void guardar() {
        /*getCliNombre es el nombre comercial*/
        if (cliente.getCliCedula() != null
                && cliente.getCliNombres() != null
                && cliente.getCliApellidos() != null
                && cliente.getCliNombre() != null
                && cliente.getCliDireccion() != null
                && cliente.getCliTelefono() != null
                && cliente.getCliMovil() != null
                && cliente.getCiudad() != null
                && cliente.getCliCorreo() != null
                && tipoadentificacion != null) {

            if (tipoadentificacion.getTidCodigo().equals("04")) {
                if (cliente.getCliCedula().length() != 13) {
                    Clients.showNotification("Verifique el RUC ingresada debe tener 13 caracteres ",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
                    return;
                }
            } else if (tipoadentificacion.getTidCodigo().equals("05")) {
                if (cliente.getCliCedula().length() != 10) {
                    Clients.showNotification("Verifique la CEDULA ingresada debe tener 10 caracteres ",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
                    return;
                }
            }
            cliente.setCliRazonSocial(cliente.getCliNombre());
            if (accion.equals("create")) {

                if (servicioCliente.FindClienteForCedula(cliente.getCliCedula()) == null) {
                    cliente.setClietipo(Integer.valueOf(clietipo));
                    cliente.setClieFechaRegistro(fechaReg);
                    cliente.setIdTipoIdentificacion(tipoadentificacion);
                    cliente.setCliClave(ArchivoUtils.generaraClaveTemporal());
                    servicioCliente.crear(cliente);

                    windowCliente.detach();
                } else {

                    Clients.showNotification("El número de documento (CI / RUC) ya se encuentra registrado ",
                            Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
                }

            } else {
                if (cliente.getCliClave() == null) {
                    cliente.setCliClave(ArchivoUtils.generaraClaveTemporal());
                }
                cliente.setIdTipoIdentificacion(tipoadentificacion);
                cliente.setClietipo(Integer.valueOf(clietipo));
                servicioCliente.modificar(cliente);
//                Messagebox.show("Guardado con exito");

                windowCliente.detach();
            }

        } else {

            Clients.showNotification("Verifique la informacion requerida",
                    Clients.NOTIFICATION_TYPE_ERROR, null, "end_center", 3000, true);
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getClietipo() {
        return clietipo;
    }

    public void setClietipo(String clietipo) {
        this.clietipo = clietipo;
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setFechaReg(Date fechaReg) {
        this.fechaReg = fechaReg;
    }

    public List<Tipoadentificacion> getListaIdentificaciones() {
        return listaIdentificaciones;
    }

    public void setListaIdentificaciones(List<Tipoadentificacion> listaIdentificaciones) {
        this.listaIdentificaciones = listaIdentificaciones;
    }

    public Tipoadentificacion getTipoadentificacion() {
        return tipoadentificacion;
    }

    public void setTipoadentificacion(Tipoadentificacion tipoadentificacion) {
        this.tipoadentificacion = tipoadentificacion;
    }

}
