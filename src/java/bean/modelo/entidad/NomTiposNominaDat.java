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
@Table(name = "NomTiposNomina_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomTiposNominaDat.findAll", query = "SELECT n FROM NomTiposNominaDat n"),
    @NamedQuery(name = "NomTiposNominaDat.findByRifEmpresa", query = "SELECT n FROM NomTiposNominaDat n WHERE n.nomTiposNominaDatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomTiposNominaDat.findByCodNomina", query = "SELECT n FROM NomTiposNominaDat n WHERE n.nomTiposNominaDatPK.codNomina = :codNomina"),
    @NamedQuery(name = "NomTiposNominaDat.findByNombreNomina", query = "SELECT n FROM NomTiposNominaDat n WHERE n.nombreNomina = :nombreNomina"),
    @NamedQuery(name = "NomTiposNominaDat.findByUltimaRotacion", query = "SELECT n FROM NomTiposNominaDat n WHERE n.ultimaRotacion = :ultimaRotacion"),
    @NamedQuery(name = "NomTiposNominaDat.findByProxRotacion", query = "SELECT n FROM NomTiposNominaDat n WHERE n.proxRotacion = :proxRotacion"),
    @NamedQuery(name = "NomTiposNominaDat.findByUltimaRotacion1", query = "SELECT n FROM NomTiposNominaDat n WHERE n.ultimaRotacion1 = :ultimaRotacion1"),
    @NamedQuery(name = "NomTiposNominaDat.findByProxRotacion1", query = "SELECT n FROM NomTiposNominaDat n WHERE n.proxRotacion1 = :proxRotacion1"),
    @NamedQuery(name = "NomTiposNominaDat.findByUltimaRotacion2", query = "SELECT n FROM NomTiposNominaDat n WHERE n.ultimaRotacion2 = :ultimaRotacion2"),
    @NamedQuery(name = "NomTiposNominaDat.findByProxRotacion2", query = "SELECT n FROM NomTiposNominaDat n WHERE n.proxRotacion2 = :proxRotacion2"),
    @NamedQuery(name = "NomTiposNominaDat.findByBaseCalculo", query = "SELECT n FROM NomTiposNominaDat n WHERE n.baseCalculo = :baseCalculo"),
    @NamedQuery(name = "NomTiposNominaDat.findByPagaSemanaFondo", query = "SELECT n FROM NomTiposNominaDat n WHERE n.pagaSemanaFondo = :pagaSemanaFondo"),
    @NamedQuery(name = "NomTiposNominaDat.findByHabilLun", query = "SELECT n FROM NomTiposNominaDat n WHERE n.habilLun = :habilLun"),
    @NamedQuery(name = "NomTiposNominaDat.findByHabilMar", query = "SELECT n FROM NomTiposNominaDat n WHERE n.habilMar = :habilMar"),
    @NamedQuery(name = "NomTiposNominaDat.findByHabilMier", query = "SELECT n FROM NomTiposNominaDat n WHERE n.habilMier = :habilMier"),
    @NamedQuery(name = "NomTiposNominaDat.findByHabilJue", query = "SELECT n FROM NomTiposNominaDat n WHERE n.habilJue = :habilJue"),
    @NamedQuery(name = "NomTiposNominaDat.findByHabilVier", query = "SELECT n FROM NomTiposNominaDat n WHERE n.habilVier = :habilVier"),
    @NamedQuery(name = "NomTiposNominaDat.findByHabilSab", query = "SELECT n FROM NomTiposNominaDat n WHERE n.habilSab = :habilSab"),
    @NamedQuery(name = "NomTiposNominaDat.findByHabilDom", query = "SELECT n FROM NomTiposNominaDat n WHERE n.habilDom = :habilDom")})
public class NomTiposNominaDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomTiposNominaDatPK nomTiposNominaDatPK;
    @Column(name = "nombreNomina")
    private String nombreNomina;
    @Column(name = "ultimaRotacion")
    @Temporal(TemporalType.DATE)
    private Date ultimaRotacion;
    @Column(name = "proxRotacion")
    @Temporal(TemporalType.DATE)
    private Date proxRotacion;
    @Column(name = "ultimaRotacion1")
    @Temporal(TemporalType.DATE)
    private Date ultimaRotacion1;
    @Column(name = "proxRotacion1")
    @Temporal(TemporalType.DATE)
    private Date proxRotacion1;
    @Column(name = "ultimaRotacion2")
    @Temporal(TemporalType.DATE)
    private Date ultimaRotacion2;
    @Column(name = "proxRotacion2")
    @Temporal(TemporalType.DATE)
    private Date proxRotacion2;
    @Column(name = "baseCalculo")
    private String baseCalculo;
    @Column(name = "pagaSemanaFondo")
    private String pagaSemanaFondo;
    @Column(name = "habilLun")
    private Integer habilLun;
    @Column(name = "habilMar")
    private Integer habilMar;
    @Column(name = "habilMier")
    private Integer habilMier;
    @Column(name = "habilJue")
    private Integer habilJue;
    @Column(name = "habilVier")
    private Integer habilVier;
    @Column(name = "habilSab")
    private Integer habilSab;
    @Column(name = "habilDom")
    private Integer habilDom;

    public NomTiposNominaDat() {
    }

    public NomTiposNominaDat(NomTiposNominaDatPK nomTiposNominaDatPK) {
        this.nomTiposNominaDatPK = nomTiposNominaDatPK;
    }

    public NomTiposNominaDat(String rifEmpresa, int codNomina) {
        this.nomTiposNominaDatPK = new NomTiposNominaDatPK(rifEmpresa, codNomina);
    }

    public NomTiposNominaDat(NomTiposNominaDatPK nomTiposNominaDatPK, String nombreNomina, Date ultimaRotacion, Date proxRotacion, Date ultimaRotacion1, Date proxRotacion1, Date ultimaRotacion2, Date proxRotacion2, String baseCalculo) {
        this.nomTiposNominaDatPK = nomTiposNominaDatPK;
        this.nombreNomina = nombreNomina;
        this.ultimaRotacion = ultimaRotacion;
        this.proxRotacion = proxRotacion;
        this.ultimaRotacion1 = ultimaRotacion1;
        this.proxRotacion1 = proxRotacion1;
        this.ultimaRotacion2 = ultimaRotacion2;
        this.proxRotacion2 = proxRotacion2;
        this.baseCalculo = baseCalculo;
    }

    public NomTiposNominaDatPK getNomTiposNominaDatPK() {
        return nomTiposNominaDatPK;
    }

    public void setNomTiposNominaDatPK(NomTiposNominaDatPK nomTiposNominaDatPK) {
        this.nomTiposNominaDatPK = nomTiposNominaDatPK;
    }

    public String getNombreNomina() {
        return nombreNomina;
    }

    public void setNombreNomina(String nombreNomina) {
        this.nombreNomina = nombreNomina;
    }

    public Date getUltimaRotacion() {
        return ultimaRotacion;
    }

    public void setUltimaRotacion(Date ultimaRotacion) {
        this.ultimaRotacion = ultimaRotacion;
    }

    public Date getProxRotacion() {
        return proxRotacion;
    }

    public void setProxRotacion(Date proxRotacion) {
        this.proxRotacion = proxRotacion;
    }

    public Date getUltimaRotacion1() {
        return ultimaRotacion1;
    }

    public void setUltimaRotacion1(Date ultimaRotacion1) {
        this.ultimaRotacion1 = ultimaRotacion1;
    }

    public Date getProxRotacion1() {
        return proxRotacion1;
    }

    public void setProxRotacion1(Date proxRotacion1) {
        this.proxRotacion1 = proxRotacion1;
    }

    public Date getUltimaRotacion2() {
        return ultimaRotacion2;
    }

    public void setUltimaRotacion2(Date ultimaRotacion2) {
        this.ultimaRotacion2 = ultimaRotacion2;
    }

    public Date getProxRotacion2() {
        return proxRotacion2;
    }

    public void setProxRotacion2(Date proxRotacion2) {
        this.proxRotacion2 = proxRotacion2;
    }

    public String getBaseCalculo() {
        return baseCalculo;
    }

    public void setBaseCalculo(String baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    public String getPagaSemanaFondo() {
        return pagaSemanaFondo;
    }

    public void setPagaSemanaFondo(String pagaSemanaFondo) {
        this.pagaSemanaFondo = pagaSemanaFondo;
    }

    public Integer getHabilLun() {
        return habilLun;
    }

    public void setHabilLun(Integer habilLun) {
        this.habilLun = habilLun;
    }

    public Integer getHabilMar() {
        return habilMar;
    }

    public void setHabilMar(Integer habilMar) {
        this.habilMar = habilMar;
    }

    public Integer getHabilMier() {
        return habilMier;
    }

    public void setHabilMier(Integer habilMier) {
        this.habilMier = habilMier;
    }

    public Integer getHabilJue() {
        return habilJue;
    }

    public void setHabilJue(Integer habilJue) {
        this.habilJue = habilJue;
    }

    public Integer getHabilVier() {
        return habilVier;
    }

    public void setHabilVier(Integer habilVier) {
        this.habilVier = habilVier;
    }

    public Integer getHabilSab() {
        return habilSab;
    }

    public void setHabilSab(Integer habilSab) {
        this.habilSab = habilSab;
    }

    public Integer getHabilDom() {
        return habilDom;
    }

    public void setHabilDom(Integer habilDom) {
        this.habilDom = habilDom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomTiposNominaDatPK != null ? nomTiposNominaDatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomTiposNominaDat)) {
            return false;
        }
        NomTiposNominaDat other = (NomTiposNominaDat) object;
        if ((this.nomTiposNominaDatPK == null && other.nomTiposNominaDatPK != null) || (this.nomTiposNominaDatPK != null && !this.nomTiposNominaDatPK.equals(other.nomTiposNominaDatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomTiposNominaDat[ nomTiposNominaDatPK=" + nomTiposNominaDatPK + " ]";
    }
    
}
