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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author henrypb
 */
@Entity
@Table(name = "NomTrabajador00_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomTrabajador00Dat.findAll", query = "SELECT n FROM NomTrabajador00Dat n"),
    @NamedQuery(name = "NomTrabajador00Dat.findByRifEmpresa", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.nomTrabajador00DatPK.rifEmpresa = :rifEmpresa"),
    @NamedQuery(name = "NomTrabajador00Dat.findByNroTrabajador", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.nomTrabajador00DatPK.nroTrabajador = :nroTrabajador"),
    @NamedQuery(name = "NomTrabajador00Dat.findByNacionalidad", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.nacionalidad = :nacionalidad"),
    @NamedQuery(name = "NomTrabajador00Dat.findByCedulaID", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.cedulaID = :cedulaID"),
    @NamedQuery(name = "NomTrabajador00Dat.findByNombre1", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.nombre1 = :nombre1"),
    @NamedQuery(name = "NomTrabajador00Dat.findByNombre2", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.nombre2 = :nombre2"),
    @NamedQuery(name = "NomTrabajador00Dat.findByApellido1", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.apellido1 = :apellido1"),
    @NamedQuery(name = "NomTrabajador00Dat.findByApellido2", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.apellido2 = :apellido2"),
    @NamedQuery(name = "NomTrabajador00Dat.findByAlias", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.alias = :alias"),
    @NamedQuery(name = "NomTrabajador00Dat.findBySexo", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.sexo = :sexo"),
    @NamedQuery(name = "NomTrabajador00Dat.findByEdoCivil", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.edoCivil = :edoCivil"),
    @NamedQuery(name = "NomTrabajador00Dat.findByFechaNac", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.fechaNac = :fechaNac"),
    @NamedQuery(name = "NomTrabajador00Dat.findByTelefonoHab1", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.telefonoHab1 = :telefonoHab1"),
    @NamedQuery(name = "NomTrabajador00Dat.findByTelefonoHab2", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.telefonoHab2 = :telefonoHab2"),
    @NamedQuery(name = "NomTrabajador00Dat.findByTelefonoMovil1", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.telefonoMovil1 = :telefonoMovil1"),
    @NamedQuery(name = "NomTrabajador00Dat.findByTelefonoMovil2", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.telefonoMovil2 = :telefonoMovil2"),
    @NamedQuery(name = "NomTrabajador00Dat.findByEmail1", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.email1 = :email1"),
    @NamedQuery(name = "NomTrabajador00Dat.findByEmail2", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.email2 = :email2"),
    @NamedQuery(name = "NomTrabajador00Dat.findByDireccionHab1", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.direccionHab1 = :direccionHab1"),
    @NamedQuery(name = "NomTrabajador00Dat.findByDireccionHab2", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.direccionHab2 = :direccionHab2"),
    @NamedQuery(name = "NomTrabajador00Dat.findByDireccionHab3", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.direccionHab3 = :direccionHab3"),
    @NamedQuery(name = "NomTrabajador00Dat.findByCodigoPostalHab", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.codigoPostalHab = :codigoPostalHab"),
    @NamedQuery(name = "NomTrabajador00Dat.findByParroquiaHab", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.parroquiaHab = :parroquiaHab"),
    @NamedQuery(name = "NomTrabajador00Dat.findByMunicipioHab", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.municipioHab = :municipioHab"),
    @NamedQuery(name = "NomTrabajador00Dat.findByCiudadHab", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.ciudadHab = :ciudadHab"),
    @NamedQuery(name = "NomTrabajador00Dat.findByEstadoHab", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.estadoHab = :estadoHab"),
    @NamedQuery(name = "NomTrabajador00Dat.findByPaisHab", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.paisHab = :paisHab"),
    @NamedQuery(name = "NomTrabajador00Dat.findByCodigoPostalNac", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.codigoPostalNac = :codigoPostalNac"),
    @NamedQuery(name = "NomTrabajador00Dat.findByParroquiaNac", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.parroquiaNac = :parroquiaNac"),
    @NamedQuery(name = "NomTrabajador00Dat.findByMunicipioNac", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.municipioNac = :municipioNac"),
    @NamedQuery(name = "NomTrabajador00Dat.findByCiudadNac", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.ciudadNac = :ciudadNac"),
    @NamedQuery(name = "NomTrabajador00Dat.findByEstadoNac", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.estadoNac = :estadoNac"),
    @NamedQuery(name = "NomTrabajador00Dat.findByPaisNac", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.paisNac = :paisNac"),
    @NamedQuery(name = "NomTrabajador00Dat.findByNombreVarAux1", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.nombreVarAux1 = :nombreVarAux1"),
    @NamedQuery(name = "NomTrabajador00Dat.findByVarAux1", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.varAux1 = :varAux1"),
    @NamedQuery(name = "NomTrabajador00Dat.findByNombreVarAux2", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.nombreVarAux2 = :nombreVarAux2"),
    @NamedQuery(name = "NomTrabajador00Dat.findByVarAux2", query = "SELECT n FROM NomTrabajador00Dat n WHERE n.varAux2 = :varAux2")})
public class NomTrabajador00Dat implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NomTrabajador00DatPK nomTrabajador00DatPK;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Column(name = "nacionalidad")
    private String nacionalidad;
    @Column(name = "cedulaID")
    private String cedulaID;
    @Column(name = "nombre1")
    private String nombre1;
    @Column(name = "nombre2")
    private String nombre2;
    @Column(name = "apellido1")
    private String apellido1;
    @Column(name = "apellido2")
    private String apellido2;
    @Column(name = "alias")
    private String alias;
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "edoCivil")
    private String edoCivil;
    @Column(name = "fechaNac")
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Column(name = "telefonoHab1")
    private String telefonoHab1;
    @Column(name = "telefonoHab2")
    private String telefonoHab2;
    @Column(name = "telefonoMovil1")
    private String telefonoMovil1;
    @Column(name = "telefonoMovil2")
    private String telefonoMovil2;
    @Column(name = "email1")
    private String email1;
    @Column(name = "email2")
    private String email2;
    @Column(name = "direccionHab1")
    private String direccionHab1;
    @Column(name = "direccionHab2")
    private String direccionHab2;
    @Column(name = "direccionHab3")
    private String direccionHab3;
    @Column(name = "codigoPostalHab")
    private String codigoPostalHab;
    @Column(name = "parroquiaHab")
    private String parroquiaHab;
    @Column(name = "municipioHab")
    private String municipioHab;
    @Column(name = "ciudadHab")
    private String ciudadHab;
    @Column(name = "estadoHab")
    private String estadoHab;
    @Column(name = "paisHab")
    private String paisHab;
    @Column(name = "codigoPostalNac")
    private String codigoPostalNac;
    @Column(name = "parroquiaNac")
    private String parroquiaNac;
    @Column(name = "municipioNac")
    private String municipioNac;
    @Column(name = "ciudadNac")
    private String ciudadNac;
    @Column(name = "estadoNac")
    private String estadoNac;
    @Column(name = "paisNac")
    private String paisNac;
    @Column(name = "nombreVarAux1")
    private String nombreVarAux1;
    @Column(name = "varAux1")
    private String varAux1;
    @Column(name = "nombreVarAux2")
    private String nombreVarAux2;
    @Column(name = "varAux2")
    private String varAux2;
    @JoinColumn(name = "rifEmpresa", referencedColumnName = "rif", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private NomEmpresaDat nomEmpresaDat;

    public NomTrabajador00Dat() {
    }

    public NomTrabajador00Dat(NomTrabajador00DatPK nomTrabajador00DatPK) {
        this.nomTrabajador00DatPK = nomTrabajador00DatPK;
    }

    public NomTrabajador00Dat(String rifEmpresa, int nroTrabajador) {
        this.nomTrabajador00DatPK = new NomTrabajador00DatPK(rifEmpresa, nroTrabajador);
    }

    public NomTrabajador00Dat(NomTrabajador00DatPK nomTrabajador00DatPK, byte[] foto, String nacionalidad, String cedulaID, String nombre1, String nombre2, String apellido1, String apellido2, String alias, String sexo, String edoCivil, Date fechaNac, String telefonoHab1, String telefonoHab2, String telefonoMovil1, String telefonoMovil2, String email1, String email2, String direccionHab1, String direccionHab2, String direccionHab3, String codigoPostalHab, String parroquiaHab, String municipioHab, String ciudadHab, String estadoHab, String paisHab, String codigoPostalNac, String parroquiaNac, String municipioNac, String ciudadNac, String estadoNac, String paisNac, String nombreVarAux1, String varAux1, String nombreVarAux2, String varAux2) {
        this.nomTrabajador00DatPK = nomTrabajador00DatPK;
        this.foto = foto;                       // 2
        this.nacionalidad = nacionalidad;       // 3
        this.cedulaID = cedulaID;               // 4
        this.nombre1 = nombre1;                 // 5
        this.nombre2 = nombre2;                 // 6
        this.apellido1 = apellido1;             // 7
        this.apellido2 = apellido2;             // 8
        this.alias = alias;                     // 9
        this.sexo = sexo;                       // 10
        this.edoCivil = edoCivil;               // 11
        this.fechaNac = fechaNac;               // 12
        this.telefonoHab1 = telefonoHab1;       // 13
        this.telefonoHab2 = telefonoHab2;       // 14
        this.telefonoMovil1 = telefonoMovil1;   // 15
        this.telefonoMovil2 = telefonoMovil2;   // 16
        this.email1 = email1;                   // 17
        this.email2 = email2;                   // 18
        this.direccionHab1 = direccionHab1;     // 19
        this.direccionHab2 = direccionHab2;     // 20
        this.direccionHab3 = direccionHab3;     // 21
        this.codigoPostalHab = codigoPostalHab; // 22
        this.parroquiaHab = parroquiaHab;       // 23
        this.municipioHab = municipioHab;       // 24
        this.ciudadHab = ciudadHab;             // 25
        this.estadoHab = estadoHab;             // 26
        this.paisHab = paisHab;                 // 27
        this.codigoPostalNac = codigoPostalNac; // 28
        this.parroquiaNac = parroquiaNac;       // 29
        this.municipioNac = municipioNac;       // 30
        this.ciudadNac = ciudadNac;             // 31
        this.estadoNac = estadoNac;             // 32
        this.paisNac = paisNac;                 // 33
        this.nombreVarAux1 = nombreVarAux1;     // 34
        this.varAux1 = varAux1;                 // 35
        this.nombreVarAux2 = nombreVarAux2;     // 36
        this.varAux2 = varAux2;                 // 37
    }

    public NomTrabajador00DatPK getNomTrabajador00DatPK() {
        return nomTrabajador00DatPK;
    }

    public void setNomTrabajador00DatPK(NomTrabajador00DatPK nomTrabajador00DatPK) {
        this.nomTrabajador00DatPK = nomTrabajador00DatPK;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCedulaID() {
        return cedulaID;
    }

    public void setCedulaID(String cedulaID) {
        this.cedulaID = cedulaID;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEdoCivil() {
        return edoCivil;
    }

    public void setEdoCivil(String edoCivil) {
        this.edoCivil = edoCivil;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getTelefonoHab1() {
        return telefonoHab1;
    }

    public void setTelefonoHab1(String telefonoHab1) {
        this.telefonoHab1 = telefonoHab1;
    }

    public String getTelefonoHab2() {
        return telefonoHab2;
    }

    public void setTelefonoHab2(String telefonoHab2) {
        this.telefonoHab2 = telefonoHab2;
    }

    public String getTelefonoMovil1() {
        return telefonoMovil1;
    }

    public void setTelefonoMovil1(String telefonoMovil1) {
        this.telefonoMovil1 = telefonoMovil1;
    }

    public String getTelefonoMovil2() {
        return telefonoMovil2;
    }

    public void setTelefonoMovil2(String telefonoMovil2) {
        this.telefonoMovil2 = telefonoMovil2;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getDireccionHab1() {
        return direccionHab1;
    }

    public void setDireccionHab1(String direccionHab1) {
        this.direccionHab1 = direccionHab1;
    }

    public String getDireccionHab2() {
        return direccionHab2;
    }

    public void setDireccionHab2(String direccionHab2) {
        this.direccionHab2 = direccionHab2;
    }

    public String getDireccionHab3() {
        return direccionHab3;
    }

    public void setDireccionHab3(String direccionHab3) {
        this.direccionHab3 = direccionHab3;
    }

    public String getCodigoPostalHab() {
        return codigoPostalHab;
    }

    public void setCodigoPostalHab(String codigoPostalHab) {
        this.codigoPostalHab = codigoPostalHab;
    }

    public String getParroquiaHab() {
        return parroquiaHab;
    }

    public void setParroquiaHab(String parroquiaHab) {
        this.parroquiaHab = parroquiaHab;
    }

    public String getMunicipioHab() {
        return municipioHab;
    }

    public void setMunicipioHab(String municipioHab) {
        this.municipioHab = municipioHab;
    }

    public String getCiudadHab() {
        return ciudadHab;
    }

    public void setCiudadHab(String ciudadHab) {
        this.ciudadHab = ciudadHab;
    }

    public String getEstadoHab() {
        return estadoHab;
    }

    public void setEstadoHab(String estadoHab) {
        this.estadoHab = estadoHab;
    }

    public String getPaisHab() {
        return paisHab;
    }

    public void setPaisHab(String paisHab) {
        this.paisHab = paisHab;
    }

    public String getCodigoPostalNac() {
        return codigoPostalNac;
    }

    public void setCodigoPostalNac(String codigoPostalNac) {
        this.codigoPostalNac = codigoPostalNac;
    }

    public String getParroquiaNac() {
        return parroquiaNac;
    }

    public void setParroquiaNac(String parroquiaNac) {
        this.parroquiaNac = parroquiaNac;
    }

    public String getMunicipioNac() {
        return municipioNac;
    }

    public void setMunicipioNac(String municipioNac) {
        this.municipioNac = municipioNac;
    }

    public String getCiudadNac() {
        return ciudadNac;
    }

    public void setCiudadNac(String ciudadNac) {
        this.ciudadNac = ciudadNac;
    }

    public String getEstadoNac() {
        return estadoNac;
    }

    public void setEstadoNac(String estadoNac) {
        this.estadoNac = estadoNac;
    }

    public String getPaisNac() {
        return paisNac;
    }

    public void setPaisNac(String paisNac) {
        this.paisNac = paisNac;
    }

    public String getNombreVarAux1() {
        return nombreVarAux1;
    }

    public void setNombreVarAux1(String nombreVarAux1) {
        this.nombreVarAux1 = nombreVarAux1;
    }

    public String getVarAux1() {
        return varAux1;
    }

    public void setVarAux1(String varAux1) {
        this.varAux1 = varAux1;
    }

    public String getNombreVarAux2() {
        return nombreVarAux2;
    }

    public void setNombreVarAux2(String nombreVarAux2) {
        this.nombreVarAux2 = nombreVarAux2;
    }

    public String getVarAux2() {
        return varAux2;
    }

    public void setVarAux2(String varAux2) {
        this.varAux2 = varAux2;
    }

    public NomEmpresaDat getNomEmpresaDat() {
        return nomEmpresaDat;
    }

    public void setNomEmpresaDat(NomEmpresaDat nomEmpresaDat) {
        this.nomEmpresaDat = nomEmpresaDat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nomTrabajador00DatPK != null ? nomTrabajador00DatPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomTrabajador00Dat)) {
            return false;
        }
        NomTrabajador00Dat other = (NomTrabajador00Dat) object;
        if ((this.nomTrabajador00DatPK == null && other.nomTrabajador00DatPK != null) || (this.nomTrabajador00DatPK != null && !this.nomTrabajador00DatPK.equals(other.nomTrabajador00DatPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.entidad.NomTrabajador00Dat[ nomTrabajador00DatPK=" + nomTrabajador00DatPK + " ]";
    }
    
}
