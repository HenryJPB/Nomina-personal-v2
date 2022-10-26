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
@Table(name = "NomPromedio_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomPromedioDat.findAll", query = "SELECT n FROM NomPromedioDat n"),
    @NamedQuery(name = "NomPromedioDat.findByRifEmpresa", query = "SELECT n FROM NomPromedioDat n WHERE n.nomPromedioDatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomPromedioDat.findByCodProm", query = "SELECT n FROM NomPromedioDat n WHERE n.nomPromedioDatPK.codProm = :codProm"),
    @NamedQuery(name = "NomPromedioDat.findByNombreProm", query = "SELECT n FROM NomPromedioDat n WHERE n.nombreProm = :nombreProm"),
    @NamedQuery(name = "NomPromedioDat.findByEcuacion", query = "SELECT n FROM NomPromedioDat n WHERE n.ecuacion = :ecuacion"),
    @NamedQuery(name = "NomPromedioDat.findByObservacion", query = "SELECT n FROM NomPromedioDat n WHERE n.observacion = :observacion"),
    @NamedQuery(name = "NomPromedioDat.findByStatus", query = "SELECT n FROM NomPromedioDat n WHERE n.status = :status")})
public class NomPromedioDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomPromedioDatPK nomPromedioDatPK;
    @Column(name = "nombreProm")
    private String nombreProm;
    @Column(name = "ecuacion")
    private String ecuacion;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "status")
    private String status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nomPromedioDat")
    private Collection<NomPromedioHistDat> nomPromedioHistDatCollection;
    @JoinColumn(name = "rifEmpresa", referencedColumnName = "rif", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NomEmpresaDat nomEmpresaDat;

    public NomPromedioDat() {
    }

    public NomPromedioDat(NomPromedioDatPK nomPromedioDatPK) {
        this.nomPromedioDatPK = nomPromedioDatPK;
    }

    public NomPromedioDat(String rifEmpresa, String codProm) {
        this.nomPromedioDatPK = new NomPromedioDatPK(rifEmpresa, codProm);
    }

    public NomPromedioDatPK getNomPromedioDatPK() {
        return nomPromedioDatPK;
    }

    public void setNomPromedioDatPK(NomPromedioDatPK nomPromedioDatPK) {
        this.nomPromedioDatPK = nomPromedioDatPK;
    }

    public String getNombreProm() {
        return nombreProm;
    }

    public void setNombreProm(String nombreProm) {
        this.nombreProm = nombreProm;
    }

    public String getEcuacion() {
        return ecuacion;
    }

    public void setEcuacion(String ecuacion) {
        this.ecuacion = ecuacion;
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

    @XmlTransient
    public Collection<NomPromedioHistDat> getNomPromedioHistDatCollection() {
        return nomPromedioHistDatCollection;
    }

    public void setNomPromedioHistDatCollection(Collection<NomPromedioHistDat> nomPromedioHistDatCollection) {
        this.nomPromedioHistDatCollection = nomPromedioHistDatCollection;
    }

    public NomEmpresaDat getNomEmpresaDat() {
        return nomEmpresaDat;
    }

    public void setNomEmpresaDat(NomEmpresaDat nomEmpresaDat) {
        this.nomEmpresaDat = nomEmpresaDat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomPromedioDatPK != null ? nomPromedioDatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomPromedioDat)) {
            return false;
        }
        NomPromedioDat other = (NomPromedioDat) object;
        if ((this.nomPromedioDatPK == null && other.nomPromedioDatPK != null) || (this.nomPromedioDatPK != null && !this.nomPromedioDatPK.equals(other.nomPromedioDatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomPromedioDat[ nomPromedioDatPK=" + nomPromedioDatPK + " ]";
    }
    
}
