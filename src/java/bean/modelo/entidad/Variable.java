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
public class Variable {
   
    private String codVar;
    private String nombreVar; 
    private Double valor; 
    private Boolean conforme;  

    public Variable(String codVar, String nombreVar, Double valor, Boolean conforme) {
        this.codVar = codVar;
        this.nombreVar = nombreVar;
        this.valor = valor;
        this.conforme = conforme;
    }

    public String getCodVar() {
        return codVar;
    }

    public void setCodVar(String codVar) {
        this.codVar = codVar;
    }

    public String getNombreVar() {
        return nombreVar;
    }

    public void setNombreVar(String nombreVar) {
        this.nombreVar = nombreVar;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getConforme() {
        return conforme;
    }

    public void setConforme(Boolean conforme) {
        this.conforme = conforme;
    }

}
