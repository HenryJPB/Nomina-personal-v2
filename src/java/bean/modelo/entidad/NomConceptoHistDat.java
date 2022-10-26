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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hpulgar
 */
@Entity
@Table(name = "NomConceptoHist_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomConceptoHistDat.findAll", query = "SELECT n FROM NomConceptoHistDat n"),
    @NamedQuery(name = "NomConceptoHistDat.findByRifEmpresa", query = "SELECT n FROM NomConceptoHistDat n WHERE n.nomConceptoHistDatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomConceptoHistDat.findByCodNomina", query = "SELECT n FROM NomConceptoHistDat n WHERE n.nomConceptoHistDatPK.codNomina = :codNomina"),
    @NamedQuery(name = "NomConceptoHistDat.findByNroTrabajador", query = "SELECT n FROM NomConceptoHistDat n WHERE n.nomConceptoHistDatPK.nroTrabajador = :nroTrabajador"),
    @NamedQuery(name = "NomConceptoHistDat.findByCodConcepto", query = "SELECT n FROM NomConceptoHistDat n WHERE n.nomConceptoHistDatPK.codConcepto = :codConcepto"),
    @NamedQuery(name = "NomConceptoHistDat.findByFechaNomina", query = "SELECT n FROM NomConceptoHistDat n WHERE n.nomConceptoHistDatPK.fechaNomina = :fechaNomina"),
    @NamedQuery(name = "NomConceptoHistDat.findByTurno", query = "SELECT n FROM NomConceptoHistDat n WHERE n.turno = :turno"),
    @NamedQuery(name = "NomConceptoHistDat.findByCantidad", query = "SELECT n FROM NomConceptoHistDat n WHERE n.cantidad = :cantidad"),
    @NamedQuery(name = "NomConceptoHistDat.findByMonto", query = "SELECT n FROM NomConceptoHistDat n WHERE n.monto = :monto"),
    @NamedQuery(name = "NomConceptoHistDat.findByTotal", query = "SELECT n FROM NomConceptoHistDat n WHERE n.total = :total"),
    @NamedQuery(name = "NomConceptoHistDat.findByPorcentaje", query = "SELECT n FROM NomConceptoHistDat n WHERE n.porcentaje = :porcentaje"),
    @NamedQuery(name = "NomConceptoHistDat.findByMontoMin", query = "SELECT n FROM NomConceptoHistDat n WHERE n.montoMin = :montoMin"),
    @NamedQuery(name = "NomConceptoHistDat.findByMontoMax", query = "SELECT n FROM NomConceptoHistDat n WHERE n.montoMax = :montoMax"),
    @NamedQuery(name = "NomConceptoHistDat.findByFrecuenciaCalculo", query = "SELECT n FROM NomConceptoHistDat n WHERE n.frecuenciaCalculo = :frecuenciaCalculo"),
    @NamedQuery(name = "NomConceptoHistDat.findByCodFormula", query = "SELECT n FROM NomConceptoHistDat n WHERE n.codFormula = :codFormula"),
    @NamedQuery(name = "NomConceptoHistDat.findByFrecuenciaPago", query = "SELECT n FROM NomConceptoHistDat n WHERE n.frecuenciaPago = :frecuenciaPago"),
    @NamedQuery(name = "NomConceptoHistDat.findByFormaPago", query = "SELECT n FROM NomConceptoHistDat n WHERE n.formaPago = :formaPago"),
    @NamedQuery(name = "NomConceptoHistDat.findByInicializable", query = "SELECT n FROM NomConceptoHistDat n WHERE n.inicializable = :inicializable")})
public class NomConceptoHistDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomConceptoHistDatPK nomConceptoHistDatPK;
    @Column(name = "turno")
    private Integer turno;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private Double cantidad;
    @Column(name = "monto")
    private Double monto;
    @Column(name = "total")
    private Double total;
    @Column(name = "porcentaje")
    private Double porcentaje;
    @Column(name = "montoMin")
    private Double montoMin;
    @Column(name = "montoMax")
    private Double montoMax;
    @Column(name = "frecuenciaCalculo")
    private String frecuenciaCalculo;
    @Column(name = "codFormula")
    private Integer codFormula;
    @Column(name = "frecuenciaPago")
    private String frecuenciaPago;
    @Column(name = "formaPago")
    private String formaPago;
    @Column(name = "inicializable")
    private String inicializable;
    @Lob
    @Column(name = "observacion1")
    private String observacion1;
    @Lob
    @Column(name = "observacion2")
    private String observacion2;

    public NomConceptoHistDat() {
    }

    public NomConceptoHistDat(NomConceptoHistDatPK nomConceptoHistDatPK) {
        this.nomConceptoHistDatPK = nomConceptoHistDatPK;
    }

    public NomConceptoHistDat(String rifEmpresa, int codNomina, int nroTrabajador, int codConcepto, Date fechaNomina) {
        this.nomConceptoHistDatPK = new NomConceptoHistDatPK(rifEmpresa, codNomina, nroTrabajador, codConcepto, fechaNomina);
    }

    public NomConceptoHistDatPK getNomConceptoHistDatPK() {
        return nomConceptoHistDatPK;
    }

    public void setNomConceptoHistDatPK(NomConceptoHistDatPK nomConceptoHistDatPK) {
        this.nomConceptoHistDatPK = nomConceptoHistDatPK;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Integer getCodFormula() {
        return codFormula;
    }

    public void setCodFormula(Integer codFormula) {
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
        hash += (nomConceptoHistDatPK != null ? nomConceptoHistDatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomConceptoHistDat)) {
            return false;
        }
        NomConceptoHistDat other = (NomConceptoHistDat) object;
        if ((this.nomConceptoHistDatPK == null && other.nomConceptoHistDatPK != null) || (this.nomConceptoHistDatPK != null && !this.nomConceptoHistDatPK.equals(other.nomConceptoHistDatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomConceptoHistDat[ nomConceptoHistDatPK=" + nomConceptoHistDatPK + " ]";
    }
    
}
