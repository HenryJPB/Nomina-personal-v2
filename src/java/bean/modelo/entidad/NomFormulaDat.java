/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.modelo.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hpulgar
 */
@Entity
@Table(name = "NomFormula_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomFormulaDat.findAll", query = "SELECT n FROM NomFormulaDat n"),
    @NamedQuery(name = "NomFormulaDat.findByRifEmpresa", query = "SELECT n FROM NomFormulaDat n WHERE n.nomFormulaDatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomFormulaDat.findByCodNomina", query = "SELECT n FROM NomFormulaDat n WHERE n.nomFormulaDatPK.codNomina = :codNomina"),
    @NamedQuery(name = "NomFormulaDat.findByCodFormula", query = "SELECT n FROM NomFormulaDat n WHERE n.nomFormulaDatPK.codFormula = :codFormula"),
    @NamedQuery(name = "NomFormulaDat.findByCodConcepto", query = "SELECT n FROM NomFormulaDat n WHERE n.nomFormulaDatPK.codConcepto = :codConcepto"),
    @NamedQuery(name = "NomFormulaDat.findByAccion", query = "SELECT n FROM NomFormulaDat n WHERE n.nomFormulaDatPK.accion = :accion"),
    @NamedQuery(name = "NomFormulaDat.findByCorrelativo", query = "SELECT n FROM NomFormulaDat n WHERE n.nomFormulaDatPK.correlativo = :correlativo"),
    @NamedQuery(name = "NomFormulaDat.findByFormula", query = "SELECT n FROM NomFormulaDat n WHERE n.formula = :formula"),
    @NamedQuery(name = "NomFormulaDat.findByObservacion", query = "SELECT n FROM NomFormulaDat n WHERE n.observacion = :observacion"),
    @NamedQuery(name = "NomFormulaDat.findBySiguiente", query = "SELECT n FROM NomFormulaDat n WHERE n.siguiente = :siguiente"),
    @NamedQuery(name = "NomFormulaDat.findByAnterior", query = "SELECT n FROM NomFormulaDat n WHERE n.anterior = :anterior"),
    @NamedQuery(name = "NomFormulaDat.findByStatus", query = "SELECT n FROM NomFormulaDat n WHERE n.status = :status")})
public class NomFormulaDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomFormulaDatPK nomFormulaDatPK;
    @Column(name = "formula")
    private String formula;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "siguiente")
    private Integer siguiente;
    @Column(name = "anterior")
    private Integer anterior;
    @Column(name = "status")
    private String status;

    public NomFormulaDat() {
    }

    public NomFormulaDat(NomFormulaDatPK nomFormulaDatPK) {
        this.nomFormulaDatPK = nomFormulaDatPK;
    }

    public NomFormulaDat(String rifEmpresa, int codNomina, String codFormula, int codConcepto, String accion, int correlativo) {
        this.nomFormulaDatPK = new NomFormulaDatPK(rifEmpresa, codNomina, codFormula, codConcepto, accion, correlativo);
    }
    
    //-------------Modificado x Henry J. Pulgar B. -----------------------------

    public NomFormulaDat(NomFormulaDatPK nomFormulaDatPK, String formula, String observacion) {
        this.nomFormulaDatPK = nomFormulaDatPK;
        this.formula = formula;
        this.observacion = observacion;
    }
    
    //--------------------------------------------------------------------------
    
    public NomFormulaDatPK getNomFormulaDatPK() {
        return nomFormulaDatPK;
    }

    public void setNomFormulaDatPK(NomFormulaDatPK nomFormulaDatPK) {
        this.nomFormulaDatPK = nomFormulaDatPK;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Integer siguiente) {
        this.siguiente = siguiente;
    }

    public Integer getAnterior() {
        return anterior;
    }

    public void setAnterior(Integer anterior) {
        this.anterior = anterior;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomFormulaDatPK != null ? nomFormulaDatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomFormulaDat)) {
            return false;
        }
        NomFormulaDat other = (NomFormulaDat) object;
        if ((this.nomFormulaDatPK == null && other.nomFormulaDatPK != null) || (this.nomFormulaDatPK != null && !this.nomFormulaDatPK.equals(other.nomFormulaDatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomFormulaDat[ nomFormulaDatPK=" + nomFormulaDatPK + " ]";
    }
    
}
