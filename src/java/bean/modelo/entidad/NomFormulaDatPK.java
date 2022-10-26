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
public class NomFormulaDatPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "rifEmpresa")
    private String rifEmpresa;
    @Basic(optional = false)
    @Column(name = "codNomina")
    private int codNomina;
    @Basic(optional = false)
    @Column(name = "codFormula")
    private String codFormula;
    @Basic(optional = false)
    @Column(name = "codConcepto")
    private int codConcepto;
    @Basic(optional = false)
    @Column(name = "accion")
    private String accion;
    @Basic(optional = false)
    @Column(name = "correlativo")
    private int correlativo;

    public NomFormulaDatPK() {
    }

    public NomFormulaDatPK(String rifEmpresa, int codNomina, String codFormula, int codConcepto, String accion, int correlativo) {
        this.rifEmpresa = rifEmpresa;
        this.codNomina = codNomina;
        this.codFormula = codFormula;
        this.codConcepto = codConcepto;
        this.accion = accion;
        this.correlativo = correlativo;
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

    public String getCodFormula() {
        return codFormula;
    }

    public void setCodFormula(String codFormula) {
        this.codFormula = codFormula;
    }

    public int getCodConcepto() {
        return codConcepto;
    }

    public void setCodConcepto(int codConcepto) {
        this.codConcepto = codConcepto;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public int getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(int correlativo) {
        this.correlativo = correlativo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rifEmpresa != null ? rifEmpresa.hashCode() : 0);
        hash += (int) codNomina;
        hash += (codFormula != null ? codFormula.hashCode() : 0);
        hash += (int) codConcepto;
        hash += (accion != null ? accion.hashCode() : 0);
        hash += (int) correlativo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomFormulaDatPK)) {
            return false;
        }
        NomFormulaDatPK other = (NomFormulaDatPK) object;
        if ((this.rifEmpresa == null && other.rifEmpresa != null) || (this.rifEmpresa != null && !this.rifEmpresa.equals(other.rifEmpresa))) {
            return false;
        }
        if (this.codNomina != other.codNomina) {
            return false;
        }
        if ((this.codFormula == null && other.codFormula != null) || (this.codFormula != null && !this.codFormula.equals(other.codFormula))) {
            return false;
        }
        if (this.codConcepto != other.codConcepto) {
            return false;
        }
        if ((this.accion == null && other.accion != null) || (this.accion != null && !this.accion.equals(other.accion))) {
            return false;
        }
        if (this.correlativo != other.correlativo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomFormulaDatPK[ rifEmpresa=" + rifEmpresa + ", codNomina=" + codNomina + ", codFormula=" + codFormula + ", codConcepto=" + codConcepto + ", accion=" + accion + ", correlativo=" + correlativo + " ]";
    }
    
}
