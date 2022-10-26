/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.modelo.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hpulgar
 */
@Entity
@Table(name = "NomTrabajador01_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomTrabajador01Dat.findAll", query = "SELECT n FROM NomTrabajador01Dat n"),
    @NamedQuery(name = "NomTrabajador01Dat.findByRifEmpresa", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.nomTrabajador01DatPK.rifEmpresa = :rifEmpresa AND n.nomTrabajador01DatPK.nroTrabajador = :nroTrabajador"),  // Adaptado x HJPB. Bqto; 20/10/2020 13:51.
    @NamedQuery(name = "NomTrabajador01Dat.findByNroTrabajador", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.nomTrabajador01DatPK.nroTrabajador = :nroTrabajador"),
    @NamedQuery(name = "NomTrabajador01Dat.findByFechaIngreso", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "NomTrabajador01Dat.findByTurno", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.turno = :turno"),
    @NamedQuery(name = "NomTrabajador01Dat.findByRotacion", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.rotacion = :rotacion"),
    @NamedQuery(name = "NomTrabajador01Dat.findByRif", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.rif = :rif"),
    @NamedQuery(name = "NomTrabajador01Dat.findByNroSSO", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.nroSSO = :nroSSO"),
    @NamedQuery(name = "NomTrabajador01Dat.findByTipoNomina", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.tipoNomina = :tipoNomina"),
    @NamedQuery(name = "NomTrabajador01Dat.findByCodBanco", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.codBanco = :codBanco"),
    @NamedQuery(name = "NomTrabajador01Dat.findByCuentaBanco", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.cuentaBanco = :cuentaBanco"),
    @NamedQuery(name = "NomTrabajador01Dat.findByFechaRetiro", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.fechaRetiro = :fechaRetiro"),
    @NamedQuery(name = "NomTrabajador01Dat.findByMarcaTarjeta", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.marcaTarjeta = :marcaTarjeta"),
    @NamedQuery(name = "NomTrabajador01Dat.findByStatus", query = "SELECT n FROM NomTrabajador01Dat n WHERE n.status = :status")})
public class NomTrabajador01Dat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomTrabajador01DatPK nomTrabajador01DatPK;
    @Column(name = "fechaIngreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Column(name = "turno")
    private Integer turno;
    @Column(name = "rotacion")
    private Integer rotacion;
    @Column(name = "rif")
    private String rif;
    @Column(name = "nroSSO")
    private String nroSSO;
    @Column(name = "tipoNomina")
    private Integer tipoNomina;
    @Column(name = "codBanco")
    private String codBanco;
    @Column(name = "cuentaBanco")
    private String cuentaBanco;
    @Column(name = "fechaRetiro")
    @Temporal(TemporalType.DATE)
    private Date fechaRetiro;
    @Column(name = "marcaTarjeta")
    private String marcaTarjeta;
    @Column(name = "status")
    private String status;

    public NomTrabajador01Dat() {
    }

    public NomTrabajador01Dat(NomTrabajador01DatPK nomTrabajador01DatPK) {
        this.nomTrabajador01DatPK = nomTrabajador01DatPK;
    }

    public NomTrabajador01Dat(String rifEmpresa, int nroTrabajador) {
        this.nomTrabajador01DatPK = new NomTrabajador01DatPK(rifEmpresa, nroTrabajador);
    }

    //---------------------HJPB---Bqto: 16 octubre 2020-------------------------
    public NomTrabajador01Dat(NomTrabajador01DatPK nomTrabajador01DatPK, Date fechaIngreso, Integer turno, Integer rotacion, String rif, String nroSSO, Integer tipoNomina, String codBanco, String cuentaBanco, Date fechaRetiro, String marcaTarjeta, String status) {
        this.nomTrabajador01DatPK = nomTrabajador01DatPK;
        this.fechaIngreso = fechaIngreso;
        this.turno = turno;
        this.rotacion = rotacion;
        this.rif = rif;
        this.nroSSO = nroSSO;
        this.tipoNomina = tipoNomina;
        this.codBanco = codBanco;
        this.cuentaBanco = cuentaBanco;
        this.fechaRetiro = fechaRetiro;
        this.marcaTarjeta = marcaTarjeta;
        this.status = status;
    }
    //------------------------------------o-------------------------------------
    
    public NomTrabajador01DatPK getNomTrabajador01DatPK() {
        return nomTrabajador01DatPK;
    }

    public void setNomTrabajador01DatPK(NomTrabajador01DatPK nomTrabajador01DatPK) {
        this.nomTrabajador01DatPK = nomTrabajador01DatPK;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getNroSSO() {
        return nroSSO;
    }

    public void setNroSSO(String nroSSO) {
        this.nroSSO = nroSSO;
    }

    public Integer getTipoNomina() {
        return tipoNomina;
    }

    public void setTipoNomina(Integer tipoNomina) {
        this.tipoNomina = tipoNomina;
    }

    public String getCodBanco() {
        return codBanco;
    }

    public void setCodBanco(String codBanco) {
        this.codBanco = codBanco;
    }

    public String getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(String cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getMarcaTarjeta() {
        return marcaTarjeta;
    }

    public void setMarcaTarjeta(String marcaTarjeta) {
        this.marcaTarjeta = marcaTarjeta;
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
        hash += (nomTrabajador01DatPK != null ? nomTrabajador01DatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomTrabajador01Dat)) {
            return false;
        }
        NomTrabajador01Dat other = (NomTrabajador01Dat) object;
        if ((this.nomTrabajador01DatPK == null && other.nomTrabajador01DatPK != null) || (this.nomTrabajador01DatPK != null && !this.nomTrabajador01DatPK.equals(other.nomTrabajador01DatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomTrabajador01Dat[ nomTrabajador01DatPK=" + nomTrabajador01DatPK + " ]";
    }
    
}
