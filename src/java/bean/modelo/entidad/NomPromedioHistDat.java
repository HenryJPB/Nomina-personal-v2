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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henrypb
 */
@Entity
@Table(name = "NomPromedioHist_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomPromedioHistDat.findAll", query = "SELECT n FROM NomPromedioHistDat n"),
    @NamedQuery(name = "NomPromedioHistDat.findByRifEmpresa", query = "SELECT n FROM NomPromedioHistDat n WHERE n.nomPromedioHistDatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomPromedioHistDat.findByCodProm", query = "SELECT n FROM NomPromedioHistDat n WHERE n.nomPromedioHistDatPK.codProm = :codProm"),
    @NamedQuery(name = "NomPromedioHistDat.findByFechaVigencia", query = "SELECT n FROM NomPromedioHistDat n WHERE n.nomPromedioHistDatPK.fechaVigencia = :fechaVigencia"),
    @NamedQuery(name = "NomPromedioHistDat.findByValor", query = "SELECT n FROM NomPromedioHistDat n WHERE n.valor = :valor")})
public class NomPromedioHistDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomPromedioHistDatPK nomPromedioHistDatPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @JoinColumn(name = "rifEmpresa", referencedColumnName = "rif", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NomEmpresaDat nomEmpresaDat;
    @JoinColumns({
        @JoinColumn(name = "rifEmpresa", referencedColumnName = "rifEmpresa", insertable = false, updatable = false),
        @JoinColumn(name = "codProm", referencedColumnName = "codProm", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private NomPromedioDat nomPromedioDat;

    public NomPromedioHistDat() {
    }

    public NomPromedioHistDat(NomPromedioHistDatPK nomPromedioHistDatPK) {
        this.nomPromedioHistDatPK = nomPromedioHistDatPK;
    }

    public NomPromedioHistDat(String rifEmpresa, String codProm, Date fechaVigencia) {
        this.nomPromedioHistDatPK = new NomPromedioHistDatPK(rifEmpresa, codProm, fechaVigencia);
    }

    public NomPromedioHistDatPK getNomPromedioHistDatPK() {
        return nomPromedioHistDatPK;
    }

    public void setNomPromedioHistDatPK(NomPromedioHistDatPK nomPromedioHistDatPK) {
        this.nomPromedioHistDatPK = nomPromedioHistDatPK;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public NomEmpresaDat getNomEmpresaDat() {
        return nomEmpresaDat;
    }

    public void setNomEmpresaDat(NomEmpresaDat nomEmpresaDat) {
        this.nomEmpresaDat = nomEmpresaDat;
    }

    public NomPromedioDat getNomPromedioDat() {
        return nomPromedioDat;
    }

    public void setNomPromedioDat(NomPromedioDat nomPromedioDat) {
        this.nomPromedioDat = nomPromedioDat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomPromedioHistDatPK != null ? nomPromedioHistDatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomPromedioHistDat)) {
            return false;
        }
        NomPromedioHistDat other = (NomPromedioHistDat) object;
        if ((this.nomPromedioHistDatPK == null && other.nomPromedioHistDatPK != null) || (this.nomPromedioHistDatPK != null && !this.nomPromedioHistDatPK.equals(other.nomPromedioHistDatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomPromedioHistDat[ nomPromedioHistDatPK=" + nomPromedioHistDatPK + " ]";
    }
    
}
