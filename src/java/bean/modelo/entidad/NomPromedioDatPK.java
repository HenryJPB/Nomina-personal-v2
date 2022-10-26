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
 * @author henrypb
 */
@Embeddable
public class NomPromedioDatPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "rifEmpresa")
    private String rifEmpresa;
    @Basic(optional = false)
    @Column(name = "codProm")
    private String codProm;

    public NomPromedioDatPK() {
    }

    public NomPromedioDatPK(String rifEmpresa, String codProm) {
        this.rifEmpresa = rifEmpresa;
        this.codProm = codProm;
    }

    public String getRifEmpresa() {
        return rifEmpresa;
    }

    public void setRifEmpresa(String rifEmpresa) {
        this.rifEmpresa = rifEmpresa;
    }

    public String getCodProm() {
        return codProm;
    }

    public void setCodProm(String codProm) {
        this.codProm = codProm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rifEmpresa != null ? rifEmpresa.hashCode() : 0);
        hash += (codProm != null ? codProm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomPromedioDatPK)) {
            return false;
        }
        NomPromedioDatPK other = (NomPromedioDatPK) object;
        if ((this.rifEmpresa == null && other.rifEmpresa != null) || (this.rifEmpresa != null && !this.rifEmpresa.equals(other.rifEmpresa))) {
            return false;
        }
        if ((this.codProm == null && other.codProm != null) || (this.codProm != null && !this.codProm.equals(other.codProm))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomPromedioDatPK[ rifEmpresa=" + rifEmpresa + ", codProm=" + codProm + " ]";
    }
    
}
