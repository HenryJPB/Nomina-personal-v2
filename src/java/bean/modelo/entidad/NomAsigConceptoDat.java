/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.modelo.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hpulgar
 */
@Entity
@Table(name = "NomAsigConcepto_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomAsigConceptoDat.findAll", query = "SELECT n FROM NomAsigConceptoDat n"),
    @NamedQuery(name = "NomAsigConceptoDat.findByRifEmpresa", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.nomAsigConceptoDatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomAsigConceptoDat.findByCodNomina", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.nomAsigConceptoDatPK.codNomina = :codNomina"),
    @NamedQuery(name = "NomAsigConceptoDat.findByNroTrabajador", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.nomAsigConceptoDatPK.nroTrabajador = :nroTrabajador"),
    @NamedQuery(name = "NomAsigConceptoDat.findByCodConcepto", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.nomAsigConceptoDatPK.codConcepto = :codConcepto"),
    @NamedQuery(name = "NomAsigConceptoDat.findByFechaInicial", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.fechaInicial = :fechaInicial"),
    @NamedQuery(name = "NomAsigConceptoDat.findByFechaFinal", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.fechaFinal = :fechaFinal"),
    @NamedQuery(name = "NomAsigConceptoDat.findByCantidad", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.cantidad = :cantidad"),
    @NamedQuery(name = "NomAsigConceptoDat.findByMonto", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.monto = :monto"),
    @NamedQuery(name = "NomAsigConceptoDat.findByPorcentaje", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.porcentaje = :porcentaje"),
    @NamedQuery(name = "NomAsigConceptoDat.findByMontoMin", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.montoMin = :montoMin"),
    @NamedQuery(name = "NomAsigConceptoDat.findByMontoMax", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.montoMax = :montoMax"),
    @NamedQuery(name = "NomAsigConceptoDat.findByFrecuenciaCalculo", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.frecuenciaCalculo = :frecuenciaCalculo"),
    @NamedQuery(name = "NomAsigConceptoDat.findByCalcular", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.calcular = :calcular"),
    @NamedQuery(name = "NomAsigConceptoDat.findByCodFormula", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.codFormula = :codFormula"),
    @NamedQuery(name = "NomAsigConceptoDat.findByFrecuenciaPago", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.frecuenciaPago = :frecuenciaPago"),
    @NamedQuery(name = "NomAsigConceptoDat.findByFormaPago", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.formaPago = :formaPago"),
    @NamedQuery(name = "NomAsigConceptoDat.findByInicializable", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.inicializable = :inicializable"),
    @NamedQuery(name = "NomAsigConceptoDat.findByActivo", query = "SELECT n FROM NomAsigConceptoDat n WHERE n.activo = :activo")})
public class NomAsigConceptoDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomAsigConceptoDatPK nomAsigConceptoDatPK;
    @Column(name = "fechaInicial")
    @Temporal(TemporalType.DATE)
    private Date fechaInicial;
    @Column(name = "fechaFinal")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private Double cantidad;
    @Column(name = "monto")
    private Double monto;
    @Column(name = "porcentaje")
    private Double porcentaje;
    @Column(name = "montoMin")
    private Double montoMin;
    @Column(name = "montoMax")
    private Double montoMax;
    @Column(name = "frecuenciaCalculo")
    private String frecuenciaCalculo;
    @Column(name = "calcular")
    private String calcular;
    @Column(name = "codFormula")
    private String codFormula;
    @Column(name = "frecuenciaPago")
    private String frecuenciaPago;
    @Column(name = "formaPago")
    private String formaPago;
    @Column(name = "inicializable")
    private String inicializable;
    @Column(name = "activo")
    private String activo;
    @Lob
    @Column(name = "observacion1")
    private String observacion1;
    @Lob
    @Column(name = "observacion2")
    private String observacion2;

    public NomAsigConceptoDat() {
    }

    public NomAsigConceptoDat(NomAsigConceptoDatPK nomAsigConceptoDatPK) {
        this.nomAsigConceptoDatPK = nomAsigConceptoDatPK;
    }

    public NomAsigConceptoDat(String rifEmpresa, int codNomina, int nroTrabajador, int codConcepto) {
        this.nomAsigConceptoDatPK = new NomAsigConceptoDatPK(rifEmpresa, codNomina, nroTrabajador, codConcepto);
    }
    
    //--------------------------------------------------------------------------
    // HJPB, Bqto: 01-12-2020 10:05 
    //--------------------------------------------------------------------------
    //  Cant  Monto Porc  FC    FP     Cal  CF  Ini   Acti  Obs1  Obs2
    public NomAsigConceptoDat(NomAsigConceptoDatPK nomAsigConceptoDatPK, Double cantidad, Double monto, Double porcentaje, String frecuenciaCalculo, String calcular, String codFormula, String frecuenciaPago, String inicializable, String activo, String observacion1, String observacion2) {
        this.nomAsigConceptoDatPK = nomAsigConceptoDatPK;
        this.cantidad = cantidad;
        this.monto = monto;
        this.porcentaje = porcentaje;
        this.frecuenciaCalculo = frecuenciaCalculo;
        this.calcular = calcular;
        this.codFormula = codFormula;
        this.frecuenciaPago = frecuenciaPago;
        this.inicializable = inicializable;
        this.activo = activo;
        this.observacion1 = observacion1;
        this.observacion2 = observacion2;
    }

    public NomAsigConceptoDat(NomAsigConceptoDatPK nomAsigConceptoDatPK, Date fechaInicial, Date fechaFinal, Double cantidad, Double monto, Double porcentaje, Double montoMin, Double montoMax, String frecuenciaCalculo, String calcular, String codFormula, String frecuenciaPago, String formaPago, String inicializable, String activo, String observacion1, String observacion2) {
        this.nomAsigConceptoDatPK = nomAsigConceptoDatPK;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.cantidad = cantidad;
        this.monto = monto;
        this.porcentaje = porcentaje;
        this.montoMin = montoMin;
        this.montoMax = montoMax;
        this.frecuenciaCalculo = frecuenciaCalculo;
        this.calcular = calcular;
        this.codFormula = codFormula;
        this.frecuenciaPago = frecuenciaPago;
        this.formaPago = formaPago;
        this.inicializable = inicializable;
        this.activo = activo;
        this.observacion1 = observacion1;
        this.observacion2 = observacion2;
    }
    
    //--------------------------------------------------------------------------

    public NomAsigConceptoDatPK getNomAsigConceptoDatPK() {
        return nomAsigConceptoDatPK;
    }

    public void setNomAsigConceptoDatPK(NomAsigConceptoDatPK nomAsigConceptoDatPK) {
        this.nomAsigConceptoDatPK = nomAsigConceptoDatPK;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
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

    public String getFrecuenciaCalculo() {
        return frecuenciaCalculo;
    }

    public void setFrecuenciaCalculo(String frecuenciaCalculo) {
        this.frecuenciaCalculo = frecuenciaCalculo;
    }

    public String getCalcular() {
        return calcular;
    }

    public void setCalcular(String calcular) {
        this.calcular = calcular;
    }

    public String getCodFormula() {
        return codFormula;
    }

    public void setCodFormula(String codFormula) {
        this.codFormula = codFormula;
    }

    public String getFrecuenciaPago() {
        return frecuenciaPago;
    }

    public void setFrecuenciaPago(String frecuenciaPago) {
        this.frecuenciaPago = frecuenciaPago;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
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

    public String getObservacion1() {
        return observacion1;
    }

    public void setObservacion1(String observacion1) {
        this.observacion1 = observacion1;
    }

    public String getObservacion2() {
        return observacion2;
    }

    public void setObservacion2(String observacion2) {
        this.observacion2 = observacion2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomAsigConceptoDatPK != null ? nomAsigConceptoDatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomAsigConceptoDat)) {
            return false;
        }
        NomAsigConceptoDat other = (NomAsigConceptoDat) object;
        if ((this.nomAsigConceptoDatPK == null && other.nomAsigConceptoDatPK != null) || (this.nomAsigConceptoDatPK != null && !this.nomAsigConceptoDatPK.equals(other.nomAsigConceptoDatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomAsigConceptoDat[ nomAsigConceptoDatPK=" + nomAsigConceptoDatPK + " ]";
    }
    
}
