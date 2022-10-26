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
@Table(name = "NomTrabajador02_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomTrabajador02Dat.findAll", query = "SELECT n FROM NomTrabajador02Dat n"),
    @NamedQuery(name = "NomTrabajador02Dat.findByRifEmpresa", query = "SELECT n FROM NomTrabajador02Dat n WHERE n.nomTrabajador02DatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomTrabajador02Dat.findByNroTrabajador", query = "SELECT n FROM NomTrabajador02Dat n WHERE n.nomTrabajador02DatPK.nroTrabajador = :nroTrabajador"),
    @NamedQuery(name = "NomTrabajador02Dat.findByCodNomina", query = "SELECT n FROM NomTrabajador02Dat n WHERE n.nomTrabajador02DatPK.codNomina = :codNomina"),
    @NamedQuery(name = "NomTrabajador02Dat.findByTurno", query = "SELECT n FROM NomTrabajador02Dat n WHERE n.turno = :turno"),
    @NamedQuery(name = "NomTrabajador02Dat.findByRotacion", query = "SELECT n FROM NomTrabajador02Dat n WHERE n.rotacion = :rotacion")})
public class NomTrabajador02Dat implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomTrabajador02DatPK nomTrabajador02DatPK;
    @Column(name = "turno")
    private Integer turno;
    @Column(name = "rotacion")
    private Integer rotacion;

    public NomTrabajador02Dat() {
    }

    public NomTrabajador02Dat(NomTrabajador02DatPK nomTrabajador02DatPK) {
        this.nomTrabajador02DatPK = nomTrabajador02DatPK;
    }

    public NomTrabajador02Dat(String rifEmpresa, int nroTrabajador, int codNomina) {
        this.nomTrabajador02DatPK = new NomTrabajador02DatPK(rifEmpresa, nroTrabajador, codNomina);
    }

    //----------------------creada x HJPB-----------------------------------------------
    public NomTrabajador02Dat(NomTrabajador02DatPK nomTrabajador02DatPK, Integer turno, Integer rotacion) {
        this.nomTrabajador02DatPK = nomTrabajador02DatPK;
        this.turno = turno;
        this.rotacion = rotacion;
    }

    public NomTrabajador02DatPK getNomTrabajador02DatPK() {
        return nomTrabajador02DatPK;
    }

    public void setNomTrabajador02DatPK(NomTrabajador02DatPK nomTrabajador02DatPK) {
        this.nomTrabajador02DatPK = nomTrabajador02DatPK;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public Integer getRotacion() {
        return rotacion;
    }

    public void setRotacion(Integer rotacion) {
        this.rotacion = rotacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomTrabajador02DatPK != null ? nomTrabajador02DatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomTrabajador02Dat)) {
            return false;
        }
        NomTrabajador02Dat other = (NomTrabajador02Dat) object;
        if ((this.nomTrabajador02DatPK == null && other.nomTrabajador02DatPK != null) || (this.nomTrabajador02DatPK != null && !this.nomTrabajador02DatPK.equals(other.nomTrabajador02DatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomTrabajador02Dat[ nomTrabajador02DatPK=" + nomTrabajador02DatPK + " ]";
    }
    
}
