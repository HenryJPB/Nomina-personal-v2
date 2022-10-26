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
@Table(name = "NomEmpresa_Dat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NomEmpresaDat.findAll", query = "SELECT n FROM NomEmpresaDat n"),
    @NamedQuery(name = "NomEmpresaDat.findByNombre", query = "SELECT n FROM NomEmpresaDat n WHERE n.nombre = :nombre"),
    @NamedQuery(name = "NomEmpresaDat.findByRazonSocial", query = "SELECT n FROM NomEmpresaDat n WHERE n.razonSocial = :razonSocial"),
    @NamedQuery(name = "NomEmpresaDat.findByRamo", query = "SELECT n FROM NomEmpresaDat n WHERE n.ramo = :ramo"),
    @NamedQuery(name = "NomEmpresaDat.findByRif", query = "SELECT n FROM NomEmpresaDat n WHERE n.rif = :rif"),
    @NamedQuery(name = "NomEmpresaDat.findByPais", query = "SELECT n FROM NomEmpresaDat n WHERE n.pais = :pais"),
    @NamedQuery(name = "NomEmpresaDat.findByEstado", query = "SELECT n FROM NomEmpresaDat n WHERE n.estado = :estado"),
    @NamedQuery(name = "NomEmpresaDat.findByCiudad", query = "SELECT n FROM NomEmpresaDat n WHERE n.ciudad = :ciudad"),
    @NamedQuery(name = "NomEmpresaDat.findByDireccion1", query = "SELECT n FROM NomEmpresaDat n WHERE n.direccion1 = :direccion1"),
    @NamedQuery(name = "NomEmpresaDat.findByDireccion2", query = "SELECT n FROM NomEmpresaDat n WHERE n.direccion2 = :direccion2"),
    @NamedQuery(name = "NomEmpresaDat.findByDireccion3", query = "SELECT n FROM NomEmpresaDat n WHERE n.direccion3 = :direccion3"),
    @NamedQuery(name = "NomEmpresaDat.findByEmail", query = "SELECT n FROM NomEmpresaDat n WHERE n.email = :email"),
    @NamedQuery(name = "NomEmpresaDat.findByMision", query = "SELECT n FROM NomEmpresaDat n WHERE n.mision = :mision"),
    @NamedQuery(name = "NomEmpresaDat.findByVision", query = "SELECT n FROM NomEmpresaDat n WHERE n.vision = :vision")})
public class NomEmpresaDat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "razonSocial")
    private String razonSocial;
    @Basic(optional = false)
    @Column(name = "ramo")
    private String ramo;
    @Id
    @Basic(optional = false)
    @Column(name = "rif")
    private String rif;
    @Basic(optional = false)
    @Column(name = "pais")
    private String pais;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = false)
    @Column(name = "direccion1")
    private String direccion1;
    @Basic(optional = false)
    @Column(name = "direccion2")
    private String direccion2;
    @Basic(optional = false)
    @Column(name = "direccion3")
    private String direccion3;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "mision")
    private String mision;
    @Basic(optional = false)
    @Column(name = "vision")
    private String vision;

    public NomEmpresaDat() {
    }

    public NomEmpresaDat(String rif) {
        this.rif = rif;
    }

    public NomEmpresaDat(String rif, String nombre, String razonSocial, String ramo, String pais, String estado, String ciudad, String direccion1, String direccion2, String direccion3, String email, String mision, String vision) {
        this.rif = rif;
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.ramo = ramo;
        this.pais = pais;
        this.estado = estado;
        this.ciudad = ciudad;
        this.direccion1 = direccion1;
        this.direccion2 = direccion2;
        this.direccion3 = direccion3;
        this.email = email;
        this.mision = mision;
        this.vision = vision;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRamo() {
        return ramo;
    }

    public void setRamo(String ramo) {
        this.ramo = ramo;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public void setDireccion2(String direccion2) {
        this.direccion2 = direccion2;
    }

    public String getDireccion3() {
        return direccion3;
    }

    public void setDireccion3(String direccion3) {
        this.direccion3 = direccion3;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rif != null ? rif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NomEmpresaDat)) {
            return false;
        }
        NomEmpresaDat other = (NomEmpresaDat) object;
        if ((this.rif == null && other.rif != null) || (this.rif != null && !this.rif.equals(other.rif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.modelo.entidad.NomEmpresaDat[ rif=" + rif + " ]";
    }
    
}
