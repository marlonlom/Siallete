package com.s2c.demos.conceptos.data;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity class for <b>conceptos_precio</b> database table
 * @author demo
 */
@Entity
@Table(name = "conceptos_precio")
@NamedQueries({
    @NamedQuery(name = "ConceptoPrecio.findAll", query = "SELECT c FROM ConceptoPrecio c"),
    @NamedQuery(name = "ConceptoPrecio.findByCodigo", query = "SELECT c FROM ConceptoPrecio c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "ConceptoPrecio.findByConcepto", query = "SELECT c FROM ConceptoPrecio c WHERE c.concepto = :concepto"),
    @NamedQuery(name = "ConceptoPrecio.findByClasificacion", query = "SELECT c FROM ConceptoPrecio c WHERE c.clasificacion = :clasificacion"),
    @NamedQuery(name = "ConceptoPrecio.findByDescripcion", query = "SELECT c FROM ConceptoPrecio c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "ConceptoPrecio.findByCreado", query = "SELECT c FROM ConceptoPrecio c WHERE c.creado = :creado")})
public class ConceptoPrecio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull(message = "El concepto es requerido")
    @Size(min = 1, max = 15)
    @Column(name = "concepto")
    private String concepto;
    @Basic(optional = false)
    @NotNull(message = "la clasificacion es requerida")
    @Column(name = "clasificacion")
    private int clasificacion;
    @Basic(optional = true)
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = true)
    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;

    public ConceptoPrecio() {
    }

    public ConceptoPrecio(Integer codigo) {
        this.codigo = codigo;
    }

    public ConceptoPrecio(Integer codigo, String concepto, int clasificacion, Date creado) {
        this.codigo = codigo;
        this.concepto = concepto;
        this.clasificacion = clasificacion;
        this.creado = creado;
    }
    
    public ConceptoPrecio(Integer codigo, String concepto, int clasificacion) {
        this.codigo = codigo;
        this.concepto = concepto;
        this.clasificacion = clasificacion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConceptoPrecio)) {
            return false;
        }
        ConceptoPrecio other = (ConceptoPrecio) object;
        return (this.codigo != null || other.codigo == null) && (this.codigo == null || this.codigo.equals(other.codigo));
    }

    @Override
    public String toString() {
        return "com.s2c.demos.conceptos.data.ConceptoPrecio[ codigo=" + codigo + " ]";
    }
    
}
