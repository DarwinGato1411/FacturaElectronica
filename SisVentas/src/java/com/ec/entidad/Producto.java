/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import com.ec.entidad.sri.DetalleCompraSri;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p ORDER BY p.prodNombre ASC")
    ,
    @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto ORDER BY p.prodNombre ASC")
    ,
    @NamedQuery(name = "Producto.findCountPrincipal", query = "SELECT p FROM Producto p WHERE p.prodPrincipal=1 ORDER BY p.prodNombre ASC")
    ,
    @NamedQuery(name = "Producto.findByProdCodigo", query = "SELECT p FROM Producto p WHERE p.prodCodigo = :prodCodigo ORDER BY p.prodNombre ASC")
    ,
    @NamedQuery(name = "Producto.findLikeProdCodigo", query = "SELECT p FROM Producto p WHERE p.prodCodigo like :prodCodigo ORDER BY p.prodNombre ASC")
    ,
    @NamedQuery(name = "Producto.findByProdNombre", query = "SELECT p FROM Producto p WHERE p.prodNombre = :prodNombre ORDER BY p.prodNombre ASC")
    ,
    @NamedQuery(name = "Producto.findLikeProdNombre", query = "SELECT p FROM Producto p WHERE p.prodNombre like :prodNombre ORDER BY p.prodNombre ASC")
    ,
    @NamedQuery(name = "Producto.findByPordCostoVentaRef", query = "SELECT p FROM Producto p WHERE p.pordCostoVentaRef = :pordCostoVentaRef ORDER BY p.prodNombre ASC")
    ,
    @NamedQuery(name = "Producto.findByPordCostoVentaFinal", query = "SELECT p FROM Producto p WHERE p.pordCostoVentaFinal = :pordCostoVentaFinal ORDER BY p.prodNombre ASC")
    ,
    @NamedQuery(name = "Producto.findByProdEstado", query = "SELECT p FROM Producto p WHERE p.prodEstado = :prodEstado ORDER BY p.prodNombre ASC")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "prod_codigo")
    private String prodCodigo;

    @Column(name = "prod_nombre")
    private String prodNombre;
    @Column(name = "prod_abreviado")
    private String prodAbreviado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pord_costo_venta_ref")
    private BigDecimal pordCostoVentaRef;
    @Column(name = "pord_costo_venta_final")
    private BigDecimal pordCostoVentaFinal;
    @Column(name = "prod_estado")
    private Integer prodEstado;
    @Column(name = "prod_trasnporte")
    private BigDecimal prodTrasnporte;
    @Column(name = "prod_iva")
    private BigDecimal prodIva;
    @Column(name = "prod_utilidad_normal")
    private BigDecimal prodUtilidadNormal;
    @Column(name = "prod_mano_obra")
    private BigDecimal prodManoObra;
    @Column(name = "prod_utilidad_preferencial")
    private BigDecimal prodUtilidadPreferencial;
    @Column(name = "prod_utilidad_dos")
    private BigDecimal prodUtilidadDos;

    @Column(name = "prod_costo_preferencial")
    private BigDecimal prodCostoPreferencial;
    @Column(name = "prod_costo_preferencial_dos")
    private BigDecimal prodCostoPreferencialDos;
    @Column(name = "prod_costo_preferencial_tres")
    private BigDecimal prodCostoPreferencialTres;
    @Column(name = "prod_principal")
    private Integer prodPrincipal;
    @Column(name = "prod_isPrincipal")
    private Boolean prodIsPrincipal;
    @Column(name = "pord_costo_compra")
    private BigDecimal pordCostoCompra;
    @Column(name = "prod_cant_minima")
    private BigDecimal prodCantMinima;
    @Column(name = "prod_subsidio")
    private BigDecimal prodSubsidio;
    @Column(name = "prod_precio_sin_subsidio")
    private BigDecimal prodPrecioSinSubsidio;

    @Column(name = "prod_cantidad_inicial")
    private BigDecimal prodCantidadInicial;
    @Column(name = "prod_graba_iva")
    private Boolean prodGrabaIva;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<ProductoProveedor> productoProveedorCollection;
    @JoinColumn(name = "id_sub_categoria", referencedColumnName = "id_sub_categoria")
    @ManyToOne
    private Subcategoria idSubCategoria;
    @OneToMany(mappedBy = "idProducto")
    private Collection<DetalleFactura> detalleFacturaCollection;
    @OneToMany(mappedBy = "idProducto")
    private Collection<Kardex> kardexCollection;

    @Lob
    @Column(name = "prod_qr")
    private byte[] prodQr;
    @Column(name = "prod_path_codbar")
    private String prodPathCodbar;
    @Column(name = "prod_tiene_subsidio")
    private String prodTieneSubsidio;
    @Column(name = "prod_glp")
    private String proGlp;
    @Column(name = "prod_imprime_codbar")
    private Boolean prodImprimeCodbar;
    @Column(name = "prod_esproducto")
    private Boolean prodEsproducto;
    @Column(name = "prod_unidad_medida")
    private String prodUnidadMedida;
    @Column(name = "prod_unidad_conversion")
    private String prodUnidadConversion;
    @Column(name = "prod_factor_conversion")
    private BigDecimal prodFactorConversion;
    

    @OneToMany(mappedBy = "idProducto")
    private Collection<DetalleCompraSri> detalleCompraSriCollection;
    @Column(name = "pord_costo_promedio_compra")
    private BigDecimal pordCostoPromedioCompra;
    @Column(name = "prod_fecha_registro")
    @Temporal(TemporalType.DATE)
    private Date prodFechaRegistro;

    public Producto() {
    }

    public Producto(Integer prodPrincipal, Boolean prodIsPrincipal) {
        this.prodPrincipal = prodPrincipal;
        this.prodIsPrincipal = prodIsPrincipal;
    }

    public Producto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getProdCodigo() {
        return prodCodigo;
    }

    public void setProdCodigo(String prodCodigo) {
        this.prodCodigo = prodCodigo;
    }

    public String getProdNombre() {
        return prodNombre;
    }

    public void setProdNombre(String prodNombre) {
        this.prodNombre = prodNombre;
    }

    public BigDecimal getPordCostoVentaRef() {
        return pordCostoVentaRef;
    }

    public void setPordCostoVentaRef(BigDecimal pordCostoVentaRef) {
        this.pordCostoVentaRef = pordCostoVentaRef;
    }

    public BigDecimal getPordCostoVentaFinal() {
        pordCostoVentaFinal.setScale(2, RoundingMode.FLOOR);
        return pordCostoVentaFinal;
    }

    public void setPordCostoVentaFinal(BigDecimal pordCostoVentaFinal) {
        this.pordCostoVentaFinal = pordCostoVentaFinal;
    }

    public Integer getProdEstado() {
        return prodEstado;
    }

    public void setProdEstado(Integer prodEstado) {
        this.prodEstado = prodEstado;
    }

    public BigDecimal getProdTrasnporte() {
        if (prodTrasnporte == null) {
            prodTrasnporte = BigDecimal.ZERO;
        }
        return prodTrasnporte;
    }

    public void setProdTrasnporte(BigDecimal prodTrasnporte) {
        this.prodTrasnporte = prodTrasnporte;
    }

    public BigDecimal getProdIva() {
        return prodIva;
    }

    public void setProdIva(BigDecimal prodIva) {
        this.prodIva = prodIva;
    }

    public BigDecimal getProdUtilidadNormal() {
        return prodUtilidadNormal;
    }

    public void setProdUtilidadNormal(BigDecimal prodUtilidadNormal) {
        this.prodUtilidadNormal = prodUtilidadNormal;
    }

    public BigDecimal getProdManoObra() {
        if (prodManoObra == null) {
            prodManoObra = BigDecimal.ZERO;
        }
        return prodManoObra;
    }

    public void setProdManoObra(BigDecimal prodManoObra) {
        this.prodManoObra = prodManoObra;
    }

    public BigDecimal getProdUtilidadPreferencial() {
        if (prodUtilidadPreferencial == null) {
            prodUtilidadPreferencial = BigDecimal.ZERO;
        }
        return prodUtilidadPreferencial;
    }

    public void setProdUtilidadPreferencial(BigDecimal prodUtilidadPreferencial) {
        this.prodUtilidadPreferencial = prodUtilidadPreferencial;
    }

    public BigDecimal getProdCostoPreferencial() {
        if (prodCostoPreferencial == null) {
            prodCostoPreferencial = BigDecimal.ZERO;
        }
        return prodCostoPreferencial;
    }

    public void setProdCostoPreferencial(BigDecimal prodCostoPreferencial) {
        this.prodCostoPreferencial = prodCostoPreferencial;
    }

    public BigDecimal getProdCostoPreferencialDos() {
        if (prodCostoPreferencialDos == null) {
            prodCostoPreferencialDos = BigDecimal.ZERO;
        }
        return prodCostoPreferencialDos;
    }

    public void setProdCostoPreferencialDos(BigDecimal prodCostoPreferencialDos) {
        this.prodCostoPreferencialDos = prodCostoPreferencialDos;
    }

    public BigDecimal getProdCostoPreferencialTres() {
        return prodCostoPreferencialTres;
    }

    public void setProdCostoPreferencialTres(BigDecimal prodCostoPreferencialTres) {
        this.prodCostoPreferencialTres = prodCostoPreferencialTres;
    }

    @XmlTransient
    public Collection<ProductoProveedor> getProductoProveedorCollection() {
        return productoProveedorCollection;
    }

    public void setProductoProveedorCollection(Collection<ProductoProveedor> productoProveedorCollection) {
        this.productoProveedorCollection = productoProveedorCollection;
    }

    public Subcategoria getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(Subcategoria idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public Integer getProdPrincipal() {
        return prodPrincipal;
    }

    public void setProdPrincipal(Integer prodPrincipal) {
        this.prodPrincipal = prodPrincipal;
    }

    public String getProdAbreviado() {
        return prodAbreviado;
    }

    public void setProdAbreviado(String prodAbreviado) {
        this.prodAbreviado = prodAbreviado;
    }

    public Boolean getProdIsPrincipal() {
        return prodIsPrincipal;
    }

    public void setProdIsPrincipal(Boolean prodIsPrincipal) {
        this.prodIsPrincipal = prodIsPrincipal;
    }

    public BigDecimal getProdCantidadInicial() {
        return prodCantidadInicial;
    }

    public void setProdCantidadInicial(BigDecimal prodCantidadInicial) {
        this.prodCantidadInicial = prodCantidadInicial;
    }

    @XmlTransient
    public Collection<DetalleFactura> getDetalleFacturaCollection() {
        return detalleFacturaCollection;
    }

    public void setDetalleFacturaCollection(Collection<DetalleFactura> detalleFacturaCollection) {
        this.detalleFacturaCollection = detalleFacturaCollection;
    }

    public byte[] getProdQr() {
        return prodQr;
    }

    public void setProdQr(byte[] prodQr) {
        this.prodQr = prodQr;
    }

    public BigDecimal getPordCostoCompra() {
        return pordCostoCompra;
    }

    public void setPordCostoCompra(BigDecimal pordCostoCompra) {
        this.pordCostoCompra = pordCostoCompra;
    }

    public BigDecimal getProdCantMinima() {
        return prodCantMinima;
    }

    public void setProdCantMinima(BigDecimal prodCantMinima) {
        this.prodCantMinima = prodCantMinima;
    }

    public Collection<Kardex> getKardexCollection() {
        return kardexCollection;
    }

    public String getProdPathCodbar() {
        return prodPathCodbar;
    }

    public void setProdPathCodbar(String prodPathCodbar) {
        this.prodPathCodbar = prodPathCodbar;
    }

    public void setKardexCollection(Collection<Kardex> kardexCollection) {
        this.kardexCollection = kardexCollection;
    }

    public Boolean getProdImprimeCodbar() {
        return prodImprimeCodbar;
    }

    public void setProdImprimeCodbar(Boolean prodImprimeCodbar) {
        this.prodImprimeCodbar = prodImprimeCodbar;
    }

    public BigDecimal getProdUtilidadDos() {
        if (prodUtilidadDos == null) {
            prodUtilidadDos = BigDecimal.ZERO;
        }
        return prodUtilidadDos;
    }

    public void setProdUtilidadDos(BigDecimal prodUtilidadDos) {
        this.prodUtilidadDos = prodUtilidadDos;
    }

    public Boolean getProdEsproducto() {
        return prodEsproducto;
    }

    public void setProdEsproducto(Boolean prodEsproducto) {
        this.prodEsproducto = prodEsproducto;
    }

    public String getProdUnidadConversion() {
        return prodUnidadConversion;
    }

    public void setProdUnidadConversion(String prodUnidadConversion) {
        this.prodUnidadConversion = prodUnidadConversion;
    }

    public BigDecimal getProdFactorConversion() {
        return prodFactorConversion;
    }

    public void setProdFactorConversion(BigDecimal prodFactorConversion) {
        this.prodFactorConversion = prodFactorConversion;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    public Boolean getProdGrabaIva() {
        return prodGrabaIva;
    }

    public void setProdGrabaIva(Boolean prodGrabaIva) {
        this.prodGrabaIva = prodGrabaIva;
    }

    public BigDecimal getProdSubsidio() {
        return prodSubsidio;
    }

    public void setProdSubsidio(BigDecimal prodSubsidio) {
        this.prodSubsidio = prodSubsidio;
    }

    public String getProdTieneSubsidio() {
        return prodTieneSubsidio;
    }

    public void setProdTieneSubsidio(String prodTieneSubsidio) {
        this.prodTieneSubsidio = prodTieneSubsidio;
    }

    public BigDecimal getProdPrecioSinSubsidio() {
        return prodPrecioSinSubsidio;
    }

    public void setProdPrecioSinSubsidio(BigDecimal prodPrecioSinSubsidio) {
        this.prodPrecioSinSubsidio = prodPrecioSinSubsidio;
    }

    public String getProGlp() {
        return proGlp;
    }

    public void setProGlp(String proGlp) {
        this.proGlp = proGlp;
    }

    public Collection<DetalleCompraSri> getDetalleCompraSriCollection() {
        return detalleCompraSriCollection;
    }

    public void setDetalleCompraSriCollection(Collection<DetalleCompraSri> detalleCompraSriCollection) {
        this.detalleCompraSriCollection = detalleCompraSriCollection;
    }

    public BigDecimal getPordCostoPromedioCompra() {
        return pordCostoPromedioCompra;
    }

    public void setPordCostoPromedioCompra(BigDecimal pordCostoPromedioCompra) {
        this.pordCostoPromedioCompra = pordCostoPromedioCompra;
    }

    public Date getProdFechaRegistro() {
        return prodFechaRegistro;
    }

    public void setProdFechaRegistro(Date prodFechaRegistro) {
        this.prodFechaRegistro = prodFechaRegistro;
    }

    public String getProdUnidadMedida() {
        return prodUnidadMedida;
    }

    public void setProdUnidadMedida(String prodUnidadMedida) {
        this.prodUnidadMedida = prodUnidadMedida;
    }
    
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Producto[ idProducto=" + idProducto + " ]";
    }
}
