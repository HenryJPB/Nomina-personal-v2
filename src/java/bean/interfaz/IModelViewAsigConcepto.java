/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.interfaz;

import bean.modelo.entidad.AsigConcepto;
import bean.modelo.vista.AsigConceptoViewFilter;
import org.zkoss.zul.ListModel;

/**
 *
 * @author hpulgar
 */
public interface IModelViewAsigConcepto {

    //==========================================================================
    // Definir atributos de ambito global para esta clase:  ////////////////////
    //==========================================================================
    
    // private AsigConcepto selectedConcepto;                                   
    
    // private AsigConceptoViewFilter asigConceptoViewFilter = new AsigConceptoViewFilter();
    
    // private List<NomConceptoDat> listaConceptos = new ArrayList<>();
    
    //************************SUP05_VIEW ==> Sup05Vista ************************
    // NOTA: todos los atributos de esta clase son tratados tipo String para 
    //       gestionar el Listbox y saltarce el paso de atributos con Date y/o Decimal (box ).  
    //**************************************************************************
    
    
    //@Init Initialize
    //@Init
    public abstract void init();  
    
    //                                        get_ViewModel()
    //--------------------------------------------------------------------------
    public abstract ListModel<AsigConcepto> getAsigConceptoViewModel();  

    //--------------------------------------------------------------------------
    // NOTA: es indistinto manejar Sup05ViewFilter o un supuesto Sup05VistaFilter
    //       proque ambos tratan los atributos como valores tipo String. 
    //--------------------------------------------------------------------------
    //                                       get_ViewFilter()
    public abstract AsigConceptoViewFilter getAsigConceptoViewFilter();
    
    //@Command
    //@NotifyChange({"nomAsigConceptoDatViewModel", "footer"})  // Anotacion mandatoria <-> get'ViewSup05VistaModel
    public abstract void changeFilter();  
    
    // =========================================================================
    // ..    ** IMPORTANTE **
    // generar los getter y los setter del atributo: selectedItem
    // =========================================================================
    
}
