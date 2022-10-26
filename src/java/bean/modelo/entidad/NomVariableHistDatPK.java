/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author henrypb
 */
@Embeddable
public class NomVariableHistDatPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "rifEmpresa")
    private String rifEmpresa;
    @Basic(optional = false)
    @Column(name = "codVar")
    private String codVar;
    @Basic(optional = false)
    @Column(name = "fechaVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaVigencia;

    public NomVariableHistDatPK() {
    }

    public NomVariableHistDatPK(String rifEmpresa, String codVar, Date fechaVigencia) {
        this.rifEmpresa = rifEmpresa;
        this.codVar = codVar;
        this.fechaVigencia = fechaVigencia;
    }

    public String getRifEmpresa() {
        return rifEmpresa;
    }

    public void setRifEmpresa(String rifEmpresa) {
        this.rifEmpresa = rifEmpresa;
    }

    public String getCodVar() {
        return codVar;
    }

    public void setCodVar(String codVar) {
        this.codVar = codVar;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rifEmpresa != null ? rifEmpresa.hashCode() : 0);
        hash += (codVar != null ? codVar.hashCode() : 0);
        hash += (fechaVigencia != null ? fechaVigencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomVariableHistDatPK)) {
            return false;
        }
        NomVariableHistDatPK other = (NomVariableHistDatPK) object;
        if ((this.rifEmpresa == null && other.rifEmpresa != null) || (this.rifEmpresa != null && !this.rifEmpresa.equals(other.rifEmpresa))) {
            return false;
        }
        if ((this.codVar == null && other.codVar != null) || (this.codVar != null && !this.codVar.equals(other.codVar))) {
            return false;
        }
        if ((this.fechaVigencia == null && other.fechaVigencia != null) || (this.fechaVigencia != null && !this.fechaVigencia.equals(other.fechaVigencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomVariableHistDatPK[ rifEmpresa=" + rifEmpresa + ", codVar=" + codVar + ", fechaVigencia=" + fechaVigencia + " ]";
    }
    
}
