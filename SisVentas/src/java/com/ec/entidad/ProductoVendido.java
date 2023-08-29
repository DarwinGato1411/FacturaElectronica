package com.ec.entidad;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.NamedStoredProcedureQuery;

@Entity
@NamedStoredProcedureQuery(
        name = "obtener_productos_vendidos",
        procedureName = "obtener_productos_vendidos",
        resultClasses = ProductoVendido.class,
        parameters = {
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_inicio", type = Date.class)
            ,
            @StoredProcedureParameter(mode = ParameterMode.IN, name = "fecha_fin", type = Date.class)
        }
)
public class ProductoVendido implements Serializable {

    @Id
    @Column(name = "prod_codigo")
    private String prodCodigo;

    @Column(name = "prod_nombre")
    private String prodNombre;

    @Column(name = "cantidad_vendido")
    private double cantidadVendido;

    @Column(name = "total_venta")
    private double totalVenta;

    public ProductoVendido() {
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

    public double getCantidadVendido() {
        return cantidadVendido;
    }

    public void setCantidadVendido(double cantidadVendido) {
        this.cantidadVendido = cantidadVendido;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    private static final long serialVersionUID = 1L;

    // Otras anotaciones y métodos de la entidad
}
