/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfaz;

import bean.modelo.entidad.Trabajador;
import java.util.List;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonTrabajador {
    
    //  Ilegal combination of static && abstract  ...???
    // public static abstract List<Trabajador> getFilterTrabajadores(TrabajadorViewFilter trabajadorFilter);  

    public abstract List<Trabajador> getListaTrab(String rifEmpresa); 
    
    // public abstract static List<Trabajador> getFilterTrab();  // This method cannot be implemented overriding method is static  
    
    // public abstract static Trabajador[] getAllTrabajadoresArray();  // This method cannot be implemented overriding method is static  
    
    public abstract Boolean existeTrabNomina(String rifEmpresa, Integer nroTrab);  
    
}
