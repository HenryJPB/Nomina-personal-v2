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
public class NomPromedioHistDatPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "rifEmpresa")
    private String rifEmpresa;
    @Basic(optional = false)
    @Column(name = "codProm")
    private String codProm;
    @Basic(optional = false)
    @Column(name = "fechaVigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaVigencia;

    public NomPromedioHistDatPK() {
    }

    public NomPromedioHistDatPK(String rifEmpresa, String codProm, Date fechaVigencia) {
        this.rifEmpresa = rifEmpresa;
        this.codProm = codProm;
        this.fechaVigencia = fechaVigencia;
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
        hash += (codProm != null ? codProm.hashCode() : 0);
        hash += (fechaVigencia != null ? fechaVigencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomPromedioHistDatPK)) {
            return false;
        }
        NomPromedioHistDatPK other = (NomPromedioHistDatPK) object;
        if ((this.rifEmpresa == null && other.rifEmpresa != null) || (this.rifEmpresa != null && !this.rifEmpresa.equals(other.rifEmpresa))) {
            return false;
        }
        if ((this.codProm == null && other.codProm != null) || (this.codProm != null && !this.codProm.equals(other.codProm))) {
            return false;
        }
        if ((this.fechaVigencia == null && other.fechaVigencia != null) || (this.fechaVigencia != null && !this.fechaVigencia.equals(other.fechaVigencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomPromedioHistDatPK[ rifEmpresa=" + rifEmpresa + ", codProm=" + codProm + ", fechaVigencia=" + fechaVigencia + " ]";
    }
    
}
