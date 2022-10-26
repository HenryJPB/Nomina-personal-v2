/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.entidad;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author henrypb
 */
@Entity
@Table(name = "NomVariable_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomVariableDat.findAll", query = "SELECT n FROM NomVariableDat n"),
    @NamedQuery(name = "NomVariableDat.findByRifEmpresa", query = "SELECT n FROM NomVariableDat n WHERE n.nomVariableDatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomVariableDat.findByCodVar", query = "SELECT n FROM NomVariableDat n WHERE n.nomVariableDatPK.codVar = :codVar"),
    @NamedQuery(name = "NomVariableDat.findByNombreVar", query = "SELECT n FROM NomVariableDat n WHERE n.nombreVar = :nombreVar"),
    @NamedQuery(name = "NomVariableDat.findByValor", query = "SELECT n FROM NomVariableDat n WHERE n.valor = :valor"),
    @NamedQuery(name = "NomVariableDat.findByObservacion", query = "SELECT n FROM NomVariableDat n WHERE n.observacion = :observacion"),
    @NamedQuery(name = "NomVariableDat.findByStatus", query = "SELECT n FROM NomVariableDat n WHERE n.status = :status")})
public class NomVariableDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomVariableDatPK nomVariableDatPK;
    @Column(name = "nombreVar")
    private String nombreVar;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "rifEmpresa", referencedColumnName = "rif", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NomEmpresaDat nomEmpresaDat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nomVariableDat")
    private Collection<NomVariableHistDat> nomVariableHistDatCollection;

    public NomVariableDat() {
    }

    public NomVariableDat(NomVariableDatPK nomVariableDatPK) {
        this.nomVariableDatPK = nomVariableDatPK;
    }

    public NomVariableDat(String rifEmpresa, String codVar) {
        this.nomVariableDatPK = new NomVariableDatPK(rifEmpresa, codVar);
    }

    public NomVariableDatPK getNomVariableDatPK() {
        return nomVariableDatPK;
    }

    public void setNomVariableDatPK(NomVariableDatPK nomVariableDatPK) {
        this.nomVariableDatPK = nomVariableDatPK;
    }

    public String getNombreVar() {
        return nombreVar;
    }

    public void setNombreVar(String nombreVar) {
        this.nombreVar = nombreVar;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NomEmpresaDat getNomEmpresaDat() {
        return nomEmpresaDat;
    }

    public void setNomEmpresaDat(NomEmpresaDat nomEmpresaDat) {
        this.nomEmpresaDat = nomEmpresaDat;
    }

    @XmlTransient
    public Collection<NomVariableHistDat> getNomVariableHistDatCollection() {
        return nomVariableHistDatCollection;
    }

    public void setNomVariableHistDatCollection(Collection<NomVariableHistDat> nomVariableHistDatCollection) {
        this.nomVariableHistDatCollection = nomVariableHistDatCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomVariableDatPK != null ? nomVariableDatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomVariableDat)) {
            return false;
        }
        NomVariableDat other = (NomVariableDat) object;
        if ((this.nomVariableDatPK == null && other.nomVariableDatPK != null) || (this.nomVariableDatPK != null && !this.nomVariableDatPK.equals(other.nomVariableDatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomVariableDat[ nomVariableDatPK=" + nomVariableDatPK + " ]";
    }
    
}
