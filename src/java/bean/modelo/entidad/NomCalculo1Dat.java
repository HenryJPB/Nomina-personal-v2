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
@Table(name = "NomCalculo1_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomCalculo1Dat.findAll", query = "SELECT n FROM NomCalculo1Dat n"),
    @NamedQuery(name = "NomCalculo1Dat.findByRifEmpresa", query = "SELECT n FROM NomCalculo1Dat n WHERE n.nomCalculo1DatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomCalculo1Dat.findByCodNomina", query = "SELECT n FROM NomCalculo1Dat n WHERE n.nomCalculo1DatPK.codNomina = :codNomina"),
    @NamedQuery(name = "NomCalculo1Dat.findByNroTrabajador", query = "SELECT n FROM NomCalculo1Dat n WHERE n.nomCalculo1DatPK.nroTrabajador = :nroTrabajador"),
    @NamedQuery(name = "NomCalculo1Dat.findByCodConcepto", query = "SELECT n FROM NomCalculo1Dat n WHERE n.nomCalculo1DatPK.codConcepto = :codConcepto"),
    @NamedQuery(name = "NomCalculo1Dat.findByFechaNomina", query = "SELECT n FROM NomCalculo1Dat n WHERE n.nomCalculo1DatPK.fechaNomina = :fechaNomina"),
    @NamedQuery(name = "NomCalculo1Dat.findByTurno", query = "SELECT n FROM NomCalculo1Dat n WHERE n.turno = :turno"),
    @NamedQuery(name = "NomCalculo1Dat.findByCantidad", query = "SELECT n FROM NomCalculo1Dat n WHERE n.cantidad = :cantidad"),
    @NamedQuery(name = "NomCalculo1Dat.findByMonto", query = "SELECT n FROM NomCalculo1Dat n WHERE n.monto = :monto"),
    @NamedQuery(name = "NomCalculo1Dat.findByTotal", query = "SELECT n FROM NomCalculo1Dat n WHERE n.total = :total"),
    @NamedQuery(name = "NomCalculo1Dat.findByPorcentaje", query = "SELECT n FROM NomCalculo1Dat n WHERE n.porcentaje = :porcentaje"),
    @NamedQuery(name = "NomCalculo1Dat.findByMontoMin", query = "SELECT n FROM NomCalculo1Dat n WHERE n.montoMin = :montoMin"),
    @NamedQuery(name = "NomCalculo1Dat.findByMontoMax", query = "SELECT n FROM NomCalculo1Dat n WHERE n.montoMax = :montoMax"),
    @NamedQuery(name = "NomCalculo1Dat.findByFrecuenciaCalculo", query = "SELECT n FROM NomCalculo1Dat n WHERE n.frecuenciaCalculo = :frecuenciaCalculo"),
    @NamedQuery(name = "NomCalculo1Dat.findByCodFormula", query = "SELECT n FROM NomCalculo1Dat n WHERE n.codFormula = :codFormula"),
    @NamedQuery(name = "NomCalculo1Dat.findByFrecuenciaPago", query = "SELECT n FROM NomCalculo1Dat n WHERE n.frecuenciaPago = :frecuenciaPago"),
    @NamedQuery(name = "NomCalculo1Dat.findByFormaPago", query = "SELECT n FROM NomCalculo1Dat n WHERE n.formaPago = :formaPago"),
    @NamedQuery(name = "NomCalculo1Dat.findByInicializable", query = "SELECT n FROM NomCalculo1Dat n WHERE n.inicializable = :inicializable")})
public class NomCalculo1Dat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomCalculo1DatPK nomCalculo1DatPK;
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
    private String codFormula;
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

    public NomCalculo1Dat() {
    }

    public NomCalculo1Dat(NomCalculo1DatPK nomCalculo1DatPK) {
        this.nomCalculo1DatPK = nomCalculo1DatPK;
    }

    public NomCalculo1Dat(String rifEmpresa, int codNomina, int nroTrabajador, int codConcepto, Date fechaNomina) {
        this.nomCalculo1DatPK = new NomCalculo1DatPK(rifEmpresa, codNomina, nroTrabajador, codConcepto, fechaNomina);
    }

    //--------------------------------------------------------------------------
    public NomCalculo1Dat(NomCalculo1DatPK nomCalculo1DatPK, Integer turno, Double cantidad, Double monto, Double total, Double porcentaje, String frecuenciaCalculo, String codFormula, String frecuenciaPago, String formaPago, String observacion1, String observacion2) {
        this.nomCalculo1DatPK = nomCalculo1DatPK;
        this.turno = turno;
        this.cantidad = cantidad;
        this.monto = monto;
        this.total = total;
        this.porcentaje = porcentaje;
        this.frecuenciaCalculo = frecuenciaCalculo;
        this.codFormula = codFormula;
        this.frecuenciaPago = frecuenciaPago;
        this.formaPago = formaPago;
        this.observacion1 = observacion1;
        this.observacion2 = observacion2;
    }

    public NomCalculo1DatPK getNomCalculo1DatPK() {
        return nomCalculo1DatPK;
    }

    public void setNomCalculo1DatPK(NomCalculo1DatPK nomCalculo1DatPK) {
        this.nomCalculo1DatPK = nomCalculo1DatPK;
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
        hash += (nomCalculo1DatPK != null ? nomCalculo1DatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomCalculo1Dat)) {
            return false;
        }
        NomCalculo1Dat other = (NomCalculo1Dat) object;
        if ((this.nomCalculo1DatPK == null && other.nomCalculo1DatPK != null) || (this.nomCalculo1DatPK != null && !this.nomCalculo1DatPK.equals(other.nomCalculo1DatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomCalculo1Dat[ nomCalculo1DatPK=" + nomCalculo1DatPK + " ]";
    }
    
}
