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
 * @author hpulgar
 */
@Entity
@Table(name = "NomConfig_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomConfigDat.findAll", query = "SELECT n FROM NomConfigDat n"),
    @NamedQuery(name = "NomConfigDat.findByRifEmpresa", query = "SELECT n FROM NomConfigDat n WHERE n.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomConfigDat.findByRuta1", query = "SELECT n FROM NomConfigDat n WHERE n.ruta1 = :ruta1"),
    @NamedQuery(name = "NomConfigDat.findByRuta2", query = "SELECT n FROM NomConfigDat n WHERE n.ruta2 = :ruta2"),
    @NamedQuery(name = "NomConfigDat.findByCorreo1", query = "SELECT n FROM NomConfigDat n WHERE n.correo1 = :correo1"),
    @NamedQuery(name = "NomConfigDat.findByCorreo2", query = "SELECT n FROM NomConfigDat n WHERE n.correo2 = :correo2")})
public class NomConfigDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "rifEmpresa")
    private String rifEmpresa;
    @Basic(optional = false)
    @Column(name = "ruta1")
    private String ruta1;
    @Column(name = "ruta2")
    private String ruta2;
    @Column(name = "correo1")
    private String correo1;
    @Column(name = "correo2")
    private String correo2;

    public NomConfigDat() {
    }

    public NomConfigDat(String rifEmpresa) {
        this.rifEmpresa = rifEmpresa;
    }

    /*
    public NomConfigDat(String rifEmpresa, String ruta1) {
    this.rifEmpresa = rifEmpresa;
    this.ruta1 = ruta1;
    }
     */
    public NomConfigDat(String rifEmpresa, String ruta1, String ruta2, String correo1, String correo2) {
        this.rifEmpresa = rifEmpresa;
        this.ruta1 = ruta1;
        this.ruta2 = ruta2;
        this.correo1 = correo1;
        this.correo2 = correo2;
    }
    
    public String getRifEmpresa() {
        return rifEmpresa;
    }

    public void setRifEmpresa(String rifEmpresa) {
        this.rifEmpresa = rifEmpresa;
    }

    public String getRuta1() {
        return ruta1;
    }

    public void setRuta1(String ruta1) {
        this.ruta1 = ruta1;
    }

    public String getRuta2() {
        return ruta2;
    }

    public void setRuta2(String ruta2) {
        this.ruta2 = ruta2;
    }

    public String getCorreo1() {
        return correo1;
    }

    public void setCorreo1(String correo1) {
        this.correo1 = correo1;
    }

    public String getCorreo2() {
        return correo2;
    }

    public void setCorreo2(String correo2) {
        this.correo2 = correo2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rifEmpresa != null ? rifEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomConfigDat)) {
            return false;
        }
        NomConfigDat other = (NomConfigDat) object;
        if ((this.rifEmpresa == null && other.rifEmpresa != null) || (this.rifEmpresa != null && !this.rifEmpresa.equals(other.rifEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomConfigDat[ rifEmpresa=" + rifEmpresa + " ]";
    }
    
}
