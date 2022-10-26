/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.entidad;

/**
 *
 * @author henrypb
 */
public class Empresa {
    
    private String rif; 
    private String nombre; 
    private String razonSocial;  

    public Empresa(String rif, String nombre) {
        this.rif = rif;
        this.nombre = nombre;
    }

    public Empresa(String rif, String nombre, String razonSocial) {
        this.rif = rif;
        this.nombre = nombre;
        this.razonSocial = razonSocial;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
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

}
