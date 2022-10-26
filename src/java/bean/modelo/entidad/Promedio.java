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
public class Promedio {
    
    private String codProm; 
    private String nombreProm; 
    private String ecuacion; 
    private String observacion;  
    private Boolean conforme;  

    public Promedio(String codProm, String nombreProm, String ecuacion, String observacion, Boolean conforme) {
        this.codProm = codProm;
        this.nombreProm = nombreProm;
        this.ecuacion = ecuacion;
        this.observacion = observacion;
        this.conforme = conforme;
    }
    
    public String getCodProm() {
        return codProm;
    }

    public void setCodProm(String codProm) {
        this.codProm = codProm;
    }

    public String getNombreProm() {
        return nombreProm;
    }

    public void setNombreProm(String nombreProm) {
        this.nombreProm = nombreProm;
    }

    public String getEcuacion() {
        return ecuacion;
    }

    public void setEcuacion(String ecuacion) {
        this.ecuacion = ecuacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    
    public Boolean getConforme() {
        return conforme;
    }

    public void setConforme(Boolean conforme) {
        this.conforme = conforme;
    }
 
}
