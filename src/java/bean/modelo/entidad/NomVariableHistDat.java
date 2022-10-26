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
@Table(name = "NomVariableHist_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomVariableHistDat.findAll", query = "SELECT n FROM NomVariableHistDat n"),
    @NamedQuery(name = "NomVariableHistDat.findByRifEmpresa", query = "SELECT n FROM NomVariableHistDat n WHERE n.nomVariableHistDatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomVariableHistDat.findByCodVar", query = "SELECT n FROM NomVariableHistDat n WHERE n.nomVariableHistDatPK.codVar = :codVar"),
    @NamedQuery(name = "NomVariableHistDat.findByFechaVigencia", query = "SELECT n FROM NomVariableHistDat n WHERE n.nomVariableHistDatPK.fechaVigencia = :fechaVigencia"),
    @NamedQuery(name = "NomVariableHistDat.findByValor", query = "SELECT n FROM NomVariableHistDat n WHERE n.valor = :valor")})
public class NomVariableHistDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomVariableHistDatPK nomVariableHistDatPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @JoinColumn(name = "rifEmpresa", referencedColumnName = "rif", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NomEmpresaDat nomEmpresaDat;
    @JoinColumns({
        @JoinColumn(name = "rifEmpresa", referencedColumnName = "rifEmpresa", insertable = false, updatable = false),
        @JoinColumn(name = "codVar", referencedColumnName = "codVar", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private NomVariableDat nomVariableDat;

    public NomVariableHistDat() {
    }

    public NomVariableHistDat(NomVariableHistDatPK nomVariableHistDatPK) {
        this.nomVariableHistDatPK = nomVariableHistDatPK;
    }

    public NomVariableHistDat(String rifEmpresa, String codVar, Date fechaVigencia) {
        this.nomVariableHistDatPK = new NomVariableHistDatPK(rifEmpresa, codVar, fechaVigencia);
    }

    public NomVariableHistDatPK getNomVariableHistDatPK() {
        return nomVariableHistDatPK;
    }

    public void setNomVariableHistDatPK(NomVariableHistDatPK nomVariableHistDatPK) {
        this.nomVariableHistDatPK = nomVariableHistDatPK;
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

    public NomVariableDat getNomVariableDat() {
        return nomVariableDat;
    }

    public void setNomVariableDat(NomVariableDat nomVariableDat) {
        this.nomVariableDat = nomVariableDat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomVariableHistDatPK != null ? nomVariableHistDatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomVariableHistDat)) {
            return false;
        }
        NomVariableHistDat other = (NomVariableHistDat) object;
        if ((this.nomVariableHistDatPK == null && other.nomVariableHistDatPK != null) || (this.nomVariableHistDatPK != null && !this.nomVariableHistDatPK.equals(other.nomVariableHistDatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomVariableHistDat[ nomVariableHistDatPK=" + nomVariableHistDatPK + " ]";
    }
    
}
