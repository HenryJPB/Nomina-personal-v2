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
public class PromedioViewFilter {
 
    private String codProm=""; 
    private String nombreProm=""; 

    public String getCodProm() {
        return codProm;
    }

    public void setCodProm(String codProm) {
        this.codProm = codProm==null?"":codProm.trim();
    }

    public String getNombreProm() {
        return nombreProm;
    }

    public void setNombreProm(String nombreProm) {
        this.nombreProm = nombreProm==null?"":nombreProm.trim();
    }

}
