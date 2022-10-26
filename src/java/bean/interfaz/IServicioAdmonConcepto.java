/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfaz;

import bean.modelo.entidad.AsigConcepto;
import bean.modelo.vista.AsigConceptoViewFilter;
import java.util.List;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonConcepto {
    
    public abstract Boolean conceptoRegistrado(Integer codNomina,Integer codConcepto); 
    
    public abstract Boolean calculoConceptoRegistrado(Integer codNomina,Integer codConcepto, Integer nroTrabajador); 
    
    //public abstract Double getValorConcepto(Integer codNomina, Integer codConcepto, Integer nroTrabajador, String tipoVar);   // tipoVar = C)antidad, M)onto, )Total, ...
    
    public abstract List<AsigConcepto> getFilterAsigConcepto (AsigConceptoViewFilter asigConceptoViewFilter);  
    
}
