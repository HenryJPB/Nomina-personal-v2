/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.modelo.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hpulgar
 */
@Entity
@Table(name = "NomConcepto_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomConceptoDat.findAll", query = "SELECT n FROM NomConceptoDat n"),
    @NamedQuery(name = "NomConceptoDat.findByRifEmpresa", query = "SELECT n FROM NomConceptoDat n WHERE n.nomConceptoDatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomConceptoDat.findByCodNomina", query = "SELECT n FROM NomConceptoDat n WHERE n.nomConceptoDatPK.codNomina = :codNomina"),
    @NamedQuery(name = "NomConceptoDat.findByCodConcepto", query = "SELECT n FROM NomConceptoDat n WHERE n.nomConceptoDatPK.codConcepto = :codConcepto"),
    @NamedQuery(name = "NomConceptoDat.findByDescripcion", query = "SELECT n FROM NomConceptoDat n WHERE n.descripcion = :descripcion"),
    @NamedQuery(name = "NomConceptoDat.findByCodFormula", query = "SELECT n FROM NomConceptoDat n WHERE n.codFormula = :codFormula"),
    @NamedQuery(name = "NomConceptoDat.findByFormaCalculo", query = "SELECT n FROM NomConceptoDat n WHERE n.formaCalculo = :formaCalculo"),
    @NamedQuery(name = "NomConceptoDat.findByAsignacion", query = "SELECT n FROM NomConceptoDat n WHERE n.asignacion = :asignacion"),
    @NamedQuery(name = "NomConceptoDat.findByCtaDebito", query = "SELECT n FROM NomConceptoDat n WHERE n.ctaDebito = :ctaDebito"),
    @NamedQuery(name = "NomConceptoDat.findByCtaCredito", query = "SELECT n FROM NomConceptoDat n WHERE n.ctaCredito = :ctaCredito"),
    @NamedQuery(name = "NomConceptoDat.findByMontoMin", query = "SELECT n FROM NomConceptoDat n WHERE n.montoMin = :montoMin"),
    @NamedQuery(name = "NomConceptoDat.findByMontoMax", query = "SELECT n FROM NomConceptoDat n WHERE n.montoMax = :montoMax"),
    @NamedQuery(name = "NomConceptoDat.findByInicializable", query = "SELECT n FROM NomConceptoDat n WHERE n.inicializable = :inicializable"),
    @NamedQuery(name = "NomConceptoDat.findByActivo", query = "SELECT n FROM NomConceptoDat n WHERE n.activo = :activo")})
public class NomConceptoDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomConceptoDatPK nomConceptoDatPK;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "codFormula")
    private Integer codFormula;
    @Column(name = "formaCalculo")
    private String formaCalculo;
    @Column(name = "asignacion")
    private String asignacion;
    @Column(name = "ctaDebito")
    private String ctaDebito;
    @Column(name = "ctaCredito")
    private String ctaCredito;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montoMin")
    private Double montoMin;
    @Column(name = "montoMax")
    private Double montoMax;
    @Column(name = "inicializable")
    private String inicializable;
    @Column(name = "activo")
    private String activo;

    public NomConceptoDat() {
    }

    public NomConceptoDat(NomConceptoDatPK nomConceptoDatPK) {
        this.nomConceptoDatPK = nomConceptoDatPK;
    }

    public NomConceptoDat(String rifEmpresa, int codNomina, int codConcepto) {
        this.nomConceptoDatPK = new NomConceptoDatPK(rifEmpresa, codNomina, codConcepto);
    }

    public NomConceptoDatPK getNomConceptoDatPK() {
        return nomConceptoDatPK;
    }

    public void setNomConceptoDatPK(NomConceptoDatPK nomConceptoDatPK) {
        this.nomConceptoDatPK = nomConceptoDatPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodFormula() {
        return codFormula;
    }

    public void setCodFormula(Integer codFormula) {
        this.codFormula = codFormula;
    }

    public String getFormaCalculo() {
        return formaCalculo;
    }

    public void setFormaCalculo(String formaCalculo) {
        this.formaCalculo = formaCalculo;
    }

    public String getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

    public String getCtaDebito() {
        return ctaDebito;
    }

    public void setCtaDebito(String ctaDebito) {
        this.ctaDebito = ctaDebito;
    }

    public String getCtaCredito() {
        return ctaCredito;
    }

    public void setCtaCredito(String ctaCredito) {
        this.ctaCredito = ctaCredito;
    }

    public Double getMontoMin() {
        return montoMin;
    }

    public void setMontoMin(Double montoMin) {
        this.montoMin = montoMin;
    }

    public Double getMontoMax() {
        return montoMax;
    }

    public void setMontoMax(Double montoMax) {
        this.montoMax = montoMax;
    }

    public String getInicializable() {
        return inicializable;
    }

    public void setInicializable(String inicializable) {
        this.inicializable = inicializable;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomConceptoDatPK != null ? nomConceptoDatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomConceptoDat)) {
            return false;
        }
        NomConceptoDat other = (NomConceptoDat) object;
        if ((this.nomConceptoDatPK == null && other.nomConceptoDatPK != null) || (this.nomConceptoDatPK != null && !this.nomConceptoDatPK.equals(other.nomConceptoDatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomConceptoDat[ nomConceptoDatPK=" + nomConceptoDatPK + " ]";
    }
    
}
