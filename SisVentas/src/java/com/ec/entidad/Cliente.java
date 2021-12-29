/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    ,
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente")
    ,
    @NamedQuery(name = "Cliente.findByCliCedula", query = "SELECT c FROM Cliente c WHERE c.cliCedula = :cliCedula")
    ,
    @NamedQuery(name = "Cliente.findByCliCedulaPassword", query = "SELECT c FROM Cliente c WHERE c.cliCedula = :cliCedula AND c.cliClave=:clave")
    ,
    @NamedQuery(name = "Cliente.findLikeCliCedula", query = "SELECT c FROM Cliente c WHERE c.cliCedula like :cliCedula")
    ,
    @NamedQuery(name = "Cliente.findByCliNombre", query = "SELECT c FROM Cliente c WHERE c.cliNombre like :cliNombre")
    ,
    @NamedQuery(name = "Cliente.findByCliRazonSocial", query = "SELECT c FROM Cliente c WHERE c.cliRazonSocial like :cliRazonSocial")
    ,
    @NamedQuery(name = "Cliente.findByCliDireccion", query = "SELECT c FROM Cliente c WHERE c.cliDireccion = :cliDireccion")
    ,
    @NamedQuery(name = "Cliente.findByCliTelefono", query = "SELECT c FROM Cliente c WHERE c.cliTelefono = :cliTelefono")
    ,
    @NamedQuery(name = "Cliente.findByCliMovil", query = "SELECT c FROM Cliente c WHERE c.cliMovil = :cliMovil")
    ,
    @NamedQuery(name = "Cliente.findByCliCorreo", query = "SELECT c FROM Cliente c WHERE c.cliCorreo = :cliCorreo")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Size(max = 100)
    @Column(name = "cli_cedula")
    private String cliCedula;
    @Size(max = 100)
    @Column(name = "cli_nombre")
    private String cliNombre;
    @Size(max = 150)
    @Column(name = "cli_razon_social")
    private String cliRazonSocial;
    @Size(max = 150)
    @Column(name = "cli_direccion")
    private String cliDireccion;
    @Size(max = 20)
    @Column(name = "cli_telefono")
    private String cliTelefono;
    @Size(max = 20)
    @Column(name = "cli_movil")
    private String cliMovil;
    @Size(max = 100)
    @Column(name = "cli_correo")
    private String cliCorreo;
    @Size(max = 100)
    @Column(name = "cli_nombres")
    private String cliNombres;
    @Size(max = 100)
    @Column(name = "cli_apellidos")
    private String cliApellidos;
    @Size(max = 100)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 10)
    @Column(name = "cli_clave")
    private String cliClave;
    @Column(name = "clie_fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date clieFechaRegistro;
    @Column(name = "clie_tipo")
    private Integer clietipo;
    @Column(name = "cli_monto_asignado")
    private BigDecimal cliMontoAsignado;
    @OneToMany(mappedBy = "idCliente")
    private Collection<Factura> facturaCollection;
    @JoinColumn(name = "id_tipo_identificacion", referencedColumnName = "id_tipo_identificacion")
    @ManyToOne
    private Tipoadentificacion idTipoIdentificacion;

    @OneToMany(mappedBy = "idCliente")
    private Collection<Guiaremision> guiaremisionCollection;
    @OneToMany(mappedBy = "idCliente")
    private Collection<OrdenTrabajo> ordenTrabajoCollection;    

    @Transient
    private String nombresCompletso;

    public Cliente() {
    }

    public Cliente(String cliCedula) {
        this.cliCedula = cliCedula;
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getCliCedula() {
        return cliCedula;
    }

    public void setCliCedula(String cliCedula) {
        this.cliCedula = cliCedula;
    }

    public String getCliNombre() {
        return cliNombre;
    }

    public void setCliNombre(String cliNombre) {
        this.cliNombre = cliNombre;
    }

    public String getCliRazonSocial() {
        return cliRazonSocial;
    }

    public void setCliRazonSocial(String cliRazonSocial) {
        this.cliRazonSocial = cliRazonSocial;
    }

    public String getCliDireccion() {
        return cliDireccion;
    }

    public void setCliDireccion(String cliDireccion) {
        this.cliDireccion = cliDireccion;
    }

    public String getCliTelefono() {
        return cliTelefono;
    }

    public void setCliTelefono(String cliTelefono) {
        this.cliTelefono = cliTelefono;
    }

    public String getCliMovil() {
        return cliMovil;
    }

    public void setCliMovil(String cliMovil) {
        this.cliMovil = cliMovil;
    }

    public String getCliCorreo() {
        return cliCorreo;
    }

    public void setCliCorreo(String cliCorreo) {
        this.cliCorreo = cliCorreo;
    }

    public Date getClieFechaRegistro() {
        return clieFechaRegistro;
    }

    public void setClieFechaRegistro(Date clieFechaRegistro) {
        this.clieFechaRegistro = clieFechaRegistro;
    }

    public Integer getClietipo() {
        return clietipo;
    }

    public String getCliNombres() {
        return cliNombres;
    }

    public void setCliNombres(String cliNombres) {
        this.cliNombres = cliNombres;
    }

    public String getCliApellidos() {
        return cliApellidos;
    }

    public void setCliApellidos(String cliApellidos) {
        this.cliApellidos = cliApellidos;
    }

    public void setClietipo(Integer clietipo) {
        this.clietipo = clietipo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Tipoadentificacion getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setIdTipoIdentificacion(Tipoadentificacion idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public String getCliClave() {
        return cliClave;
    }

    public void setCliClave(String cliClave) {
        this.cliClave = cliClave;
    }

    public BigDecimal getCliMontoAsignado() {
        return cliMontoAsignado;
    }

    public void setCliMontoAsignado(BigDecimal cliMontoAsignado) {
        this.cliMontoAsignado = cliMontoAsignado;
    }

    public String getNombresCompletso() {
        nombresCompletso = cliNombres + " " + cliApellidos;
        return nombresCompletso;
    }

    public void setNombresCompletso(String nombresCompletso) {
        this.nombresCompletso = nombresCompletso;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @XmlTransient
    public Collection<Guiaremision> getGuiaremisionCollection() {
        return guiaremisionCollection;
    }

    public void setGuiaremisionCollection(Collection<Guiaremision> guiaremisionCollection) {
        this.guiaremisionCollection = guiaremisionCollection;
    }

    @XmlTransient
    public Collection<OrdenTrabajo> getOrdenTrabajoCollection() {
        return ordenTrabajoCollection;
    }

    public void setOrdenTrabajoCollection(Collection<OrdenTrabajo> ordenTrabajoCollection) {
        this.ordenTrabajoCollection = ordenTrabajoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCliente != null ? idCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Cliente[ idCliente=" + idCliente + " ]";
    }
}
