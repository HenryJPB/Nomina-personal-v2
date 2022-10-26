/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.entidad;

import java.awt.image.BufferedImage;

/**
 *
 * @author henrypb
 * Entidad usada como catalogo de seleccion para una lista de trabajadores.
 * 
 */
public class TrabajadorView {
    
    private BufferedImage fotoImage; 
    private Integer tipoNomina;  
    private Integer nroTrabajador; 
    private String cedulaID; 
    private String nombre;   
    private String apellido;  

    public TrabajadorView(BufferedImage fotoImage, Integer tipoNomina, Integer nroTrabajador, String cedulaID, String nombre, String apellido) {
        this.fotoImage = fotoImage;
        this.tipoNomina = tipoNomina;
        this.nroTrabajador = nroTrabajador;
        this.cedulaID = cedulaID;
        this.nombre = nombre;
        this.apellido = apellido;
    } // *poliformismo* 

    public BufferedImage getFotoImage() {
        return fotoImage;
    }

    public void setFotoImage(BufferedImage fotoImage) {
        this.fotoImage = fotoImage;
    }

    public Integer getTipoNomina() {
        return tipoNomina;
    }

    public void setTipoNomina(Integer tipoNomina) {
        this.tipoNomina = tipoNomina;
    }
    
    public Integer getNroTrabajador() {
        return nroTrabajador;
    }

    public void setNroTrabajador(Integer nroTrabajador) {
        this.nroTrabajador = nroTrabajador;
    }

    public String getCedulaID() {
        return cedulaID;
    }

    public void setCedulaID(String cedulaID) {
        this.cedulaID = cedulaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

}
