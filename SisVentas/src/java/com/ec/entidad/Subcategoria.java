/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "subcategoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subcategoria.findAll", query = "SELECT s FROM Subcategoria s"),
    @NamedQuery(name = "Subcategoria.findByIdSubCategoria", query = "SELECT s FROM Subcategoria s WHERE s.idSubCategoria = :idSubCategoria"),
    @NamedQuery(name = "Subcategoria.findBySubCatDescripcion", query = "SELECT s FROM Subcategoria s WHERE s.subCatDescripcion = :subCatDescripcion")})
public class Subcategoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sub_categoria")
    private Integer idSubCategoria;
    @Size(max = 50)
    @Column(name = "sub_cat_descripcion")
    private String subCatDescripcion;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne
    private Categoria idCategoria;
    @OneToMany(mappedBy = "idSubCategoria")
    private Collection<Producto> productoCollection;

    public Subcategoria() {
    }

    public Subcategoria(Integer idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public Integer getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(Integer idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public String getSubCatDescripcion() {
        return subCatDescripcion;
    }

    public void setSubCatDescripcion(String subCatDescripcion) {
        this.subCatDescripcion = subCatDescripcion;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubCategoria != null ? idSubCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subcategoria)) {
            return false;
        }
        Subcategoria other = (Subcategoria) object;
        if ((this.idSubCategoria == null && other.idSubCategoria != null) || (this.idSubCategoria != null && !this.idSubCategoria.equals(other.idSubCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ec.entidad.Subcategoria[ idSubCategoria=" + idSubCategoria + " ]";
    }
    
}
