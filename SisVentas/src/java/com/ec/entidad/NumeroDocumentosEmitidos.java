/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.entidad;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Darwin Morocho
 */
@Entity
@Table(name = "numero_documentos_emitidos")
public class NumeroDocumentosEmitidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 12)
    @Column(name = "mes")
    private Integer mes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "numero")
    private Long numero;

    public NumeroDocumentosEmitidos() {
    }

    public NumeroDocumentosEmitidos(Long id, Integer mes, Long numero) {
        this.id = id;
        this.mes = mes;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

}
