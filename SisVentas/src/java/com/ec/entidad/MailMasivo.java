/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gato
 */
@Entity
@Table(name = "mail_masivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MailMasivo.findAll", query = "SELECT m FROM MailMasivo m"),
    @NamedQuery(name = "MailMasivo.findByIdMail", query = "SELECT m FROM MailMasivo m WHERE m.idMail = :idMail"),
    @NamedQuery(name = "MailMasivo.findAllLike", query = "SELECT m FROM MailMasivo m WHERE m.email LIKE  :email"),
    @NamedQuery(name = "MailMasivo.findByEmail", query = "SELECT m FROM MailMasivo m WHERE m.email = :email"),
    @NamedQuery(name = "MailMasivo.findByEmailCategoria", query = "SELECT m FROM MailMasivo m WHERE m.categoria = :categoria"),
    @NamedQuery(name = "MailMasivo.findByClienet", query = "SELECT m FROM MailMasivo m WHERE m.clienet LIKE :clienet")})
public class MailMasivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mail")
    private Integer idMail;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 150)
    @Column(name = "email")
    private String email;
    @Size(max = 200)
    @Column(name = "clienet")
    private String clienet;
    @Size(max = 200)
    @Column(name = "categoria")
    private String categoria;

    public MailMasivo() {
    }

    public MailMasivo(String email, String clienet) {
        this.email = email;
        this.clienet = clienet;
    }
    public MailMasivo(String email, String clienet,String categoria) {
        this.email = email;
        this.clienet = clienet;
        this.categoria=categoria;
    }
    

    public MailMasivo(Integer idMail) {
        this.idMail = idMail;
    }

    public Integer getIdMail() {
        return idMail;
    }

    public void setIdMail(Integer idMail) {
        this.idMail = idMail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClienet() {
        return clienet;
    }

    public void setClienet(String clienet) {
        this.clienet = clienet;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMail != null ? idMail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MailMasivo)) {
            return false;
        }
        MailMasivo other = (MailMasivo) object;
        if ((this.idMail == null && other.idMail != null) || (this.idMail != null && !this.idMail.equals(other.idMail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "imp.entidades.MailMasivo[ idMail=" + idMail + " ]";
    }
    
}
