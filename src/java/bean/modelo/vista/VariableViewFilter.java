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
public class VariableViewFilter {
        
    private String codVar="";
    private String nombreVar=""; 
    private String valor="";

    public String getCodVar() {
        return codVar;
    }

    public void setCodVar(String codVar) {
        this.codVar = codVar==null?"":codVar.trim();
    }

    public String getNombreVar() {
        return nombreVar;
    }

    public void setNombreVar(String nombreVar) {
        this.nombreVar = nombreVar==null?"":nombreVar.trim();
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor==null?"":valor.trim();
    }
    
}
