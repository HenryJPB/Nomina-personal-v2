/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henrypb
 */
@Entity
@Table(name = "NomPais_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomPaisDat.findAll", query = "SELECT n FROM NomPaisDat n"),
    @NamedQuery(name = "NomPaisDat.findByCodPais", query = "SELECT n FROM NomPaisDat n WHERE n.codPais = :codPais"),
    @NamedQuery(name = "NomPaisDat.findByNombre", query = "SELECT n FROM NomPaisDat n WHERE n.nombre = :nombre")})
public class NomPaisDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codPais")
    private Integer codPais;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public NomPaisDat() {
    }

    public NomPaisDat(Integer codPais) {
        this.codPais = codPais;
    }

    public NomPaisDat(Integer codPais, String nombre) {
        this.codPais = codPais;
        this.nombre = nombre;
    }

    public Integer getCodPais() {
        return codPais;
    }

    public void setCodPais(Integer codPais) {
        this.codPais = codPais;
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
        hash += (codPais != null ? codPais.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomPaisDat)) {
            return false;
        }
        NomPaisDat other = (NomPaisDat) object;
        if ((this.codPais == null && other.codPais != null) || (this.codPais != null && !this.codPais.equals(other.codPais))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.entidad.NomPaisDat[ codPais=" + codPais + " ]";
    }
    
}
