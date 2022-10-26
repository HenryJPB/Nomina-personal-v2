/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.modelo.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author hpulgar
 */
@Embeddable
public class NomTiposNominaDatPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "rifEmpresa")
    private String rifEmpresa;
    @Basic(optional = false)
    @Column(name = "codNomina")
    private int codNomina;

    public NomTiposNominaDatPK() {
    }

    public NomTiposNominaDatPK(String rifEmpresa, int codNomina) {
        this.rifEmpresa = rifEmpresa;
        this.codNomina = codNomina;
    }

    public String getRifEmpresa() {
        return rifEmpresa;
    }

    public void setRifEmpresa(String rifEmpresa) {
        this.rifEmpresa = rifEmpresa;
    }

    public int getCodNomina() {
        return codNomina;
    }

    public void setCodNomina(int codNomina) {
        this.codNomina = codNomina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rifEmpresa != null ? rifEmpresa.hashCode() : 0);
        hash += (int) codNomina;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomTiposNominaDatPK)) {
            return false;
        }
        NomTiposNominaDatPK other = (NomTiposNominaDatPK) object;
        if ((this.rifEmpresa == null && other.rifEmpresa != null) || (this.rifEmpresa != null && !this.rifEmpresa.equals(other.rifEmpresa))) {
            return false;
        }
        if (this.codNomina != other.codNomina) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomTiposNominaDatPK[ rifEmpresa=" + rifEmpresa + ", codNomina=" + codNomina + " ]";
    }
    
}
