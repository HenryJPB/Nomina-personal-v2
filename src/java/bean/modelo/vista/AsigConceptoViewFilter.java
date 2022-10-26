/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.modelo.vista;

/**
 *
 * @author hpulgar
 */
public class AsigConceptoViewFilter {
    
    private String codNominaS="";
    private String nroTrabajadorS=""; 
    private String nombre="";
    private String codConceptoS="";
    private String descripcion="";
    private String fechaNominaS="";

    public String getCodNominaS() {
        return codNominaS;
    }

    public void setCodNominaS(String codNominaS) {
        this.codNominaS = ( codNominaS==null?"":codNominaS.trim() );  
    }

    public String getNroTrabajadorS() {
        return nroTrabajadorS;
    }

    public void setNroTrabajadorS(String nroTrabajadorS) {
        this.nroTrabajadorS = (nroTrabajadorS==null?"":nroTrabajadorS.trim()); 
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = (nombre==null?"":nombre.trim());
    }

    public String getCodConceptoS() {
        return codConceptoS;
    }

    public void setCodConceptoS(String codConceptoS) {
        this.codConceptoS = (codConceptoS==null?"":codConceptoS.trim());
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = (descripcion==null?"":descripcion.trim());
    }

    public String getFechaNominaS() {
        return fechaNominaS;
    }

    public void setFechaNominaS(String fechaNominaS) {
        this.fechaNominaS = (fechaNominaS==null?"":fechaNominaS.trim());
    }
    
}
