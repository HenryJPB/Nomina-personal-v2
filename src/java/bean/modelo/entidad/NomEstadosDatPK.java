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
public class NomEstadosDatPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "codPais")
    private int codPais;
    @Basic(optional = false)
    @Column(name = "codEstado")
    private int codEstado;

    public NomEstadosDatPK() {
    }

    public NomEstadosDatPK(int codPais, int codEstado) {
        this.codPais = codPais;
        this.codEstado = codEstado;
    }

    public int getCodPais() {
        return codPais;
    }

    public void setCodPais(int codPais) {
        this.codPais = codPais;
    }

    public int getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(int codEstado) {
        this.codEstado = codEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codPais;
        hash += (int) codEstado;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomEstadosDatPK)) {
            return false;
        }
        NomEstadosDatPK other = (NomEstadosDatPK) object;
        if (this.codPais != other.codPais) {
            return false;
        }
        if (this.codEstado != other.codEstado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.entidad.NomEstadosDatPK[ codPais=" + codPais + ", codEstado=" + codEstado + " ]";
    }
    
}
