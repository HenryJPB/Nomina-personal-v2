/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfaz;

import bean.utilitario.libreria.ClassArregloTokens;
import bean.utilitario.libreria.ClassArregloTokens.TIPO_TOKEN;

/**
 *
 * @author henrypb
 */
public interface IControladorDefFormula {
    
    public abstract ClassArregloTokens dividirTokenizer(String cadena); 
    
    public abstract ClassArregloTokens dividirTokens(String cadena);  
    
    public abstract TIPO_TOKEN tipoToken(String token);  
    
    public abstract ClassArregloTokens validarEcuacionNomina(Integer codNomProceso,Integer nroTrabajador, ClassArregloTokens classArrTokens);  
    
    public abstract Double procesarEcuacion(ClassArregloTokens classArregloTokens);  
    
    public abstract Double procesarEcuacionNomina(ClassArregloTokens classArregloTokens);  
    /*
    public abstract Double procesarEcuacionNomina(Integer codNomina, Integer nroTrabajador, java.util.Date fecha, ClassArregloTokens classArregloTokens); 
    */  
    
}
