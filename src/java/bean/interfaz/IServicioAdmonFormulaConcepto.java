/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfaz;

import bean.modelo.entidad.NomFormulaDat;
import bean.modelo.entidad.Promedio;
import bean.modelo.entidad.Variable;
import bean.modelo.entidad.VariableHist;
import bean.modelo.vista.PromedioViewFilter;
import bean.modelo.vista.VariableViewFilter;
import java.util.List;

/**
 *
 * @author henrypb
 *  NOTA:           ** IMPORTANTE ** 
 *  "implements IServicioAdmonFormulaConcepto" --> no procede en la Clase: 
 *  ServicioAdmonFormulaConcepto, debido a que ña Condicion necesaria 
 *  es que estos metodos sean "static", NO abstract.. 
 * 
 */
public interface IServicioAdmonFormulaConcepto {
    
    public abstract List<Variable> getListaVariables(String rifEmpresa); 
    
    public abstract List<Variable> getFilterVariables(VariableViewFilter variableViewFilter);  
 
    public abstract Variable[] getAllListaVariablesArray();  // getAll<..>Array() 
    
    public abstract List<VariableHist> getListaVariableHist(String rifEmpresa);  

    public VariableHist[] getAllListaVariableHistArray();  
    
    //--------------------------------------------------------------------------
    
    public abstract List<Promedio> getListaPromedios(String rifEmpresa);  
    
    public abstract List<Promedio> getFilterPromedios(PromedioViewFilter promedioViewFilter);  
    
    public abstract Promedio[] getAllListaPromediosArray();   // getAll<..>Array()
    
    //--------------------------------------------------------------------------
    
    public abstract List<NomFormulaDat> getListaFormulas(String rifEmpresa, Integer codNomina, Integer codConcepto, String codFormula);  
    
    public abstract NomFormulaDat[] getAlllistaFormulasArray();   // getAll<..>Array()
    
}
