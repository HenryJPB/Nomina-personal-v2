/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henrypb
 */
@Entity
@Table(name = "NomEstados_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomEstadosDat.findAll", query = "SELECT n FROM NomEstadosDat n"),
    @NamedQuery(name = "NomEstadosDat.findByCodPais", query = "SELECT n FROM NomEstadosDat n WHERE n.nomEstadosDatPK.codPais = :codPais"),
    @NamedQuery(name = "NomEstadosDat.findByCodEstado", query = "SELECT n FROM NomEstadosDat n WHERE n.nomEstadosDatPK.codEstado = :codEstado"),
    @NamedQuery(name = "NomEstadosDat.findByNombre", query = "SELECT n FROM NomEstadosDat n WHERE n.nombre = :nombre")})
public class NomEstadosDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomEstadosDatPK nomEstadosDatPK;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public NomEstadosDat() {
    }

    public NomEstadosDat(NomEstadosDatPK nomEstadosDatPK) {
        this.nomEstadosDatPK = nomEstadosDatPK;
    }

    public NomEstadosDat(NomEstadosDatPK nomEstadosDatPK, String nombre) {
        this.nomEstadosDatPK = nomEstadosDatPK;
        this.nombre = nombre;
    }

    public NomEstadosDat(int codPais, int codEstado) {
        this.nomEstadosDatPK = new NomEstadosDatPK(codPais, codEstado);
    }

    public NomEstadosDatPK getNomEstadosDatPK() {
        return nomEstadosDatPK;
    }

    public void setNomEstadosDatPK(NomEstadosDatPK nomEstadosDatPK) {
        this.nomEstadosDatPK = nomEstadosDatPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomEstadosDatPK != null ? nomEstadosDatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomEstadosDat)) {
            return false;
        }
        NomEstadosDat other = (NomEstadosDat) object;
        if ((this.nomEstadosDatPK == null && other.nomEstadosDatPK != null) || (this.nomEstadosDatPK != null && !this.nomEstadosDatPK.equals(other.nomEstadosDatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.entidad.NomEstadosDat[ nomEstadosDatPK=" + nomEstadosDatPK + " ]";
    }
    
}
