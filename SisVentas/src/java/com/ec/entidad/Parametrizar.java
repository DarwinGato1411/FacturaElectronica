/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Darwin Morocho
 */
@Entity
@Table(name = "parametrizar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametrizar.findAll", query = "SELECT p FROM Parametrizar p WHERE p.isprincipal=TRUE")
    ,
    @NamedQuery(name = "Parametrizar.findByCodParametrizar", query = "SELECT p FROM Parametrizar p WHERE p.codParametrizar = :codParametrizar")
    ,
    @NamedQuery(name = "Parametrizar.findByParIva", query = "SELECT p FROM Parametrizar p WHERE p.parIva = :parIva")
    ,
    @NamedQuery(name = "Parametrizar.findByParUtilidad", query = "SELECT p FROM Parametrizar p WHERE p.parUtilidad = :parUtilidad")
    ,
    @NamedQuery(name = "Parametrizar.findByParUtilidadPreferencial", query = "SELECT p FROM Parametrizar p WHERE p.parUtilidadPreferencial = :parUtilidadPreferencial")
    ,
    @NamedQuery(name = "Parametrizar.findByParEmpresa", query = "SELECT p FROM Parametrizar p WHERE p.parEmpresa = :parEmpresa")
    ,
    @NamedQuery(name = "Parametrizar.findByParRucEmpresa", query = "SELECT p FROM Parametrizar p WHERE p.parRucEmpresa = :parRucEmpresa")
    ,
    @NamedQuery(name = "Parametrizar.findByParContactoEmpresa", query = "SELECT p FROM Parametrizar p WHERE p.parContactoEmpresa = :parContactoEmpresa")})
public class Parametrizar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_parametrizar")
    private Integer codParametrizar;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "par_iva")
    private BigDecimal parIva;
    @Column(name = "par_utilidad")
    private BigDecimal parUtilidad;
    @Column(name = "par_utilidad_preferencial")
    private BigDecimal parUtilidadPreferencial;
    @Column(name = "par_utilidad_preferencial_dos")
    private BigDecimal parUtilidadPreferencialDos;
    @Column(name = "par_descuento_general")
    private BigDecimal parDescuentoGeneral;
    @Column(name = "par_num_registros_factura")
    private BigDecimal parNumRegistrosFactura;
    @Size(max = 100)
    @Column(name = "par_empresa")
    private String parEmpresa;
    @Size(max = 45)
    @Column(name = "par_ruc_empresa")
    private String parRucEmpresa;
    @Size(max = 45)
    @Column(name = "par_contacto_empresa")
    private String parContactoEmpresa;
    @Size(max = 30)
    @Column(name = "par_imprime_factura")
    private String parImprimeFactura;
    @Size(max = 4)
    @Column(name = "par_codigo_iva")
    private String parCodigoIva;
    @Column(name = "isprincipal")
    private Boolean isprincipal;
    @Column(name = "par_estado")
    private Boolean parEstado;
    @Column(name = "par_activa_kardex")
    private Boolean parActivaKardex;

    @Column(name = "par_iva_actual")
    private BigDecimal parIvaActual;

    @Column(name = "par_credito_clientes")
    private Boolean parCreditoClientes;

    @Column(name = "par_imp_automatico")
    private Boolean parImpAutomatico;

    @Column(name = "par_imp_factura")
    private Boolean parImpFactura;

    @Column(name = "par_nombre_impresora")
    private String parNombreImpresora;

    @Column(name = "par_ciudad")
    private String parCiudad;

    @Column(name = "par_correo_defecto")
    private String parCorreoDefecto;

    @Column(name = "par_caduca")
    @Temporal(TemporalType.DATE)
    private Date parCaduca;

    @Column(name = "par_borra_items_fac")
    private Boolean parBorraItemsFac;

    @Column(name = "par_pistola_nuevo")
    private Boolean parPistolaNuevo;

    @Column(name = "par_numero_factura")
    private Integer parNumeroFactura;

    @Column(name = "par_producto_factura")
    private Integer parProductoFactura;

    @Column(name = "par_plan_basico")
    private Boolean parPlanBasico;

    @Column(name = "par_ilimitado_arriendo")
    private Boolean parIlimitadoArriendo;

    @Column(name = "par_ilimitado_permanente")
    private Boolean parIlimitadoPermanente;

    public Parametrizar() {
    }

    public Parametrizar(Integer codParametrizar) {
        this.codParametrizar = codParametrizar;
    }

    public Integer getCodParametrizar() {
        return codParametrizar;
    }

    public void setCodParametrizar(Integer codParametrizar) {
        this.codParametrizar = codParametrizar;
    }

    public BigDecimal getParIva() {
        return parIva;
    }

    public void setParIva(BigDecimal parIva) {
        this.parIva = parIva;
    }

    public BigDecimal getParUtilidad() {
        return parUtilidad;
    }

    public void setParUtilidad(BigDecimal parUtilidad) {
        this.parUtilidad = parUtilidad;
    }

    public BigDecimal getParUtilidadPreferencial() {
        return parUtilidadPreferencial;
    }

    public void setParUtilidadPreferencial(BigDecimal parUtilidadPreferencial) {
        this.parUtilidadPreferencial = parUtilidadPreferencial;
    }

    public String getParEmpresa() {
        return parEmpresa;
    }

    public void setParEmpresa(String parEmpresa) {
        this.parEmpresa = parEmpresa;
    }

    public String getParRucEmpresa() {
        return parRucEmpresa;
    }

    public void setParRucEmpresa(String parRucEmpresa) {
        this.parRucEmpresa = parRucEmpresa;
    }

    public String getParContactoEmpresa() {
        return parContactoEmpresa;
    }

    public void setParContactoEmpresa(String parContactoEmpresa) {
        this.parContactoEmpresa = parContactoEmpresa;
    }

    public Boolean getIsprincipal() {
        if (isprincipal == null) {
            isprincipal = Boolean.FALSE;
        }
        return isprincipal;
    }

    public void setIsprincipal(Boolean isprincipal) {
        this.isprincipal = isprincipal;
    }

    public Boolean getParEstado() {
        if (parEstado == null) {
            parEstado = Boolean.FALSE;
        }
        return parEstado;
    }

    public void setParEstado(Boolean parEstado) {
        this.parEstado = parEstado;
    }

    public BigDecimal getParNumRegistrosFactura() {
        return parNumRegistrosFactura;
    }

    public void setParNumRegistrosFactura(BigDecimal parNumRegistrosFactura) {
        this.parNumRegistrosFactura = parNumRegistrosFactura;
    }

    public Boolean getParActivaKardex() {
        if (parActivaKardex != null) {
            parActivaKardex = Boolean.FALSE;
        }
        return parActivaKardex;
    }

    public void setParActivaKardex(Boolean parActivaKardex) {
        this.parActivaKardex = parActivaKardex;
    }

    public Boolean getParImpAutomatico() {
        if (parImpAutomatico == null) {
            parImpAutomatico = Boolean.FALSE;
        }
        return parImpAutomatico;
    }

    public void setParImpAutomatico(Boolean parImpAutomatico) {
        this.parImpAutomatico = parImpAutomatico;
    }

    public Boolean getParImpFactura() {
        if (parImpFactura == null) {
            parImpFactura = Boolean.FALSE;
        }
        return parImpFactura;
    }

    public void setParImpFactura(Boolean parImpFactura) {
        this.parImpFactura = parImpFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codParametrizar != null ? codParametrizar.hashCode() : 0);
        return hash;
    }

    public BigDecimal getParDescuentoGeneral() {
        return parDescuentoGeneral;
    }

    public void setParDescuentoGeneral(BigDecimal parDescuentoGeneral) {
        this.parDescuentoGeneral = parDescuentoGeneral;
    }

    public String getParImprimeFactura() {
        return parImprimeFactura;
    }

    public void setParImprimeFactura(String parImprimeFactura) {
        this.parImprimeFactura = parImprimeFactura;
    }

    public String getParCodigoIva() {
        return parCodigoIva;
    }

    public void setParCodigoIva(String parCodigoIva) {
        this.parCodigoIva = parCodigoIva;
    }

    public BigDecimal getParIvaActual() {
        return parIvaActual;
    }

    public void setParIvaActual(BigDecimal parIvaActual) {
        this.parIvaActual = parIvaActual;
    }

    public Boolean getParCreditoClientes() {
        if (parCreditoClientes == null) {
            parCreditoClientes = Boolean.FALSE;
        }
        return parCreditoClientes;
    }

    public void setParCreditoClientes(Boolean parCreditoClientes) {
        this.parCreditoClientes = parCreditoClientes;
    }

    public BigDecimal getParUtilidadPreferencialDos() {
        return parUtilidadPreferencialDos;
    }

    public void setParUtilidadPreferencialDos(BigDecimal parUtilidadPreferencialDos) {
        this.parUtilidadPreferencialDos = parUtilidadPreferencialDos;
    }

    public Date getParCaduca() {
        return parCaduca;
    }

    public void setParCaduca(Date parCaduca) {
        this.parCaduca = parCaduca;
    }

    public String getParNombreImpresora() {
        return parNombreImpresora;
    }

    public void setParNombreImpresora(String parNombreImpresora) {
        this.parNombreImpresora = parNombreImpresora;
    }

    public String getParCiudad() {
        return parCiudad;
    }

    public void setParCiudad(String parCiudad) {
        this.parCiudad = parCiudad;
    }

    public String getParCorreoDefecto() {
        return parCorreoDefecto;
    }

    public void setParCorreoDefecto(String parCorreoDefecto) {
        this.parCorreoDefecto = parCorreoDefecto;
    }

    public Boolean getParBorraItemsFac() {
        if (parBorraItemsFac == null) {
            parBorraItemsFac = Boolean.FALSE;
        }
        return parBorraItemsFac;
    }

    public void setParBorraItemsFac(Boolean parBorraItemsFac) {
        this.parBorraItemsFac = parBorraItemsFac;
    }

    public Boolean getParPistolaNuevo() {
        if (parPistolaNuevo == null) {
            parPistolaNuevo = Boolean.FALSE;
        }
        return parPistolaNuevo;
    }

    public void setParPistolaNuevo(Boolean parPistolaNuevo) {
        this.parPistolaNuevo = parPistolaNuevo;
    }

    public Integer getParNumeroFactura() {
        return parNumeroFactura==null?0:parNumeroFactura;
    }

    public void setParNumeroFactura(Integer parNumeroFactura) {
        this.parNumeroFactura = parNumeroFactura;
    }

    public Integer getParProductoFactura() {
        return parProductoFactura;
    }

    public void setParProductoFactura(Integer parProductoFactura) {
        this.parProductoFactura = parProductoFactura;
    }

    public Boolean getParPlanBasico() {
        return parPlanBasico == null ? Boolean.FALSE : parPlanBasico;
    }

    public void setParPlanBasico(Boolean parPlanBasico) {
        this.parPlanBasico = parPlanBasico;
    }

    public Boolean getParIlimitadoArriendo() {

        return parIlimitadoArriendo == null ? Boolean.FALSE : parIlimitadoArriendo;
    }

    public void setParIlimitadoArriendo(Boolean parIlimitadoArriendo) {
        this.parIlimitadoArriendo = parIlimitadoArriendo;
    }

    public Boolean getParIlimitadoPermanente() {
        return parIlimitadoPermanente == null ? Boolean.FALSE : parIlimitadoPermanente;
    }

    public void setParIlimitadoPermanente(Boolean parIlimitadoPermanente) {
        this.parIlimitadoPermanente = parIlimitadoPermanente;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametrizar)) {
            return false;
        }
        Parametrizar other = (Parametrizar) object;
        if ((this.codParametrizar == null && other.codParametrizar != null) || (this.codParametrizar != null && !this.codParametrizar.equals(other.codParametrizar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Parametrizar[ codParametrizar=" + codParametrizar + " ]";
    }

}
