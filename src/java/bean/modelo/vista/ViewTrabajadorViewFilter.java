/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

/**
 *
 * @author henrypb
 */
public class ViewTrabajadorViewFilter {
    
    private String tipoNomina=""; 
    private String nroTrabajador=""; 
    private String cedulaID=""; 
    private String nombre="";   
    private String apellido="";  

    public String getTipoNomina() {
        return tipoNomina;
    }

    public void setTipoNomina(String tipoNomina) {
        this.tipoNomina = tipoNomina==null?"":tipoNomina;
    }

    public String getNroTrabajador() {
        return nroTrabajador;
    }

    public void setNroTrabajador(String nroTrabajador) {
        this.nroTrabajador = nroTrabajador==null?"":nroTrabajador;
    }

    public String getCedulaID() {
        return cedulaID;
    }

    public void setCedulaID(String cedulaID) {
        this.cedulaID = cedulaID==null?"":cedulaID.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre==null?"":nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido==null?"":apellido.trim();
    }
    
}
