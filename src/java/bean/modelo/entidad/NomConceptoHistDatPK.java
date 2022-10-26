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
 * @author hpulgar
 */
@Embeddable
public class NomConceptoHistDatPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "rifEmpresa")
    private String rifEmpresa;
    @Basic(optional = false)
    @Column(name = "codNomina")
    private int codNomina;
    @Basic(optional = false)
    @Column(name = "nroTrabajador")
    private int nroTrabajador;
    @Basic(optional = false)
    @Column(name = "codConcepto")
    private int codConcepto;
    @Basic(optional = false)
    @Column(name = "fechaNomina")
    @Temporal(TemporalType.DATE)
    private Date fechaNomina;

    public NomConceptoHistDatPK() {
    }

    public NomConceptoHistDatPK(String rifEmpresa, int codNomina, int nroTrabajador, int codConcepto, Date fechaNomina) {
        this.rifEmpresa = rifEmpresa;
        this.codNomina = codNomina;
        this.nroTrabajador = nroTrabajador;
        this.codConcepto = codConcepto;
        this.fechaNomina = fechaNomina;
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

    public int getNroTrabajador() {
        return nroTrabajador;
    }

    public void setNroTrabajador(int nroTrabajador) {
        this.nroTrabajador = nroTrabajador;
    }

    public int getCodConcepto() {
        return codConcepto;
    }

    public void setCodConcepto(int codConcepto) {
        this.codConcepto = codConcepto;
    }

    public Date getFechaNomina() {
        return fechaNomina;
    }

    public void setFechaNomina(Date fechaNomina) {
        this.fechaNomina = fechaNomina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rifEmpresa != null ? rifEmpresa.hashCode() : 0);
        hash += (int) codNomina;
        hash += (int) nroTrabajador;
        hash += (int) codConcepto;
        hash += (fechaNomina != null ? fechaNomina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomConceptoHistDatPK)) {
            return false;
        }
        NomConceptoHistDatPK other = (NomConceptoHistDatPK) object;
        if ((this.rifEmpresa == null && other.rifEmpresa != null) || (this.rifEmpresa != null && !this.rifEmpresa.equals(other.rifEmpresa))) {
            return false;
        }
        if (this.codNomina != other.codNomina) {
            return false;
        }
        if (this.nroTrabajador != other.nroTrabajador) {
            return false;
        }
        if (this.codConcepto != other.codConcepto) {
            return false;
        }
        if ((this.fechaNomina == null && other.fechaNomina != null) || (this.fechaNomina != null && !this.fechaNomina.equals(other.fechaNomina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomConceptoHistDatPK[ rifEmpresa=" + rifEmpresa + ", codNomina=" + codNomina + ", nroTrabajador=" + nroTrabajador + ", codConcepto=" + codConcepto + ", fechaNomina=" + fechaNomina + " ]";
    }
    
}
