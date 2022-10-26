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
public class NomTrabajador02DatPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "rifEmpresa")
    private String rifEmpresa;
    @Basic(optional = false)
    @Column(name = "nroTrabajador")
    private int nroTrabajador;
    @Basic(optional = false)
    @Column(name = "codNomina")
    private int codNomina;

    public NomTrabajador02DatPK() {
    }

    public NomTrabajador02DatPK(String rifEmpresa, int nroTrabajador, int codNomina) {
        this.rifEmpresa = rifEmpresa;
        this.nroTrabajador = nroTrabajador;
        this.codNomina = codNomina;
    }

    public String getRifEmpresa() {
        return rifEmpresa;
    }

    public void setRifEmpresa(String rifEmpresa) {
        this.rifEmpresa = rifEmpresa;
    }

    public int getNroTrabajador() {
        return nroTrabajador;
    }

    public void setNroTrabajador(int nroTrabajador) {
        this.nroTrabajador = nroTrabajador;
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
        hash += (int) nroTrabajador;
        hash += (int) codNomina;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomTrabajador02DatPK)) {
            return false;
        }
        NomTrabajador02DatPK other = (NomTrabajador02DatPK) object;
        if ((this.rifEmpresa == null && other.rifEmpresa != null) || (this.rifEmpresa != null && !this.rifEmpresa.equals(other.rifEmpresa))) {
            return false;
        }
        if (this.nroTrabajador != other.nroTrabajador) {
            return false;
        }
        if (this.codNomina != other.codNomina) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomTrabajador02DatPK[ rifEmpresa=" + rifEmpresa + ", nroTrabajador=" + nroTrabajador + ", codNomina=" + codNomina + " ]";
    }
    
}
