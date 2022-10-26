/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.interfaz;

import bean.modelo.entidad.AsigConcepto;
import bean.modelo.entidad.TipoContrato;
import org.zkoss.zul.ListModel;

/**
 *
 * @author hpulgar
 */
public interface IModelViewTiposContrato {

    //==========================================================================
    // Definir atributos de ambito global para esta clase:  ////////////////////
    //==========================================================================
    
    // private Nomina selectedContrato;                                   
    
    // private List<TipoContrato> listaContratos = new ArrayList<>();
    
    //**************************************************************************
    // NOTA: todos los atributos de esta clase son tratados tipo String para 
    //       gestionar el Listbox y saltarce el paso de atributos con Date y/o Decimal (box ).  
    //**************************************************************************
    
    
    //Initialize
    //@Init
    public abstract void init();  
    
    //                                        get_?_ViewModel()
    //--------------------------------------------------------------------------
    public abstract ListModel<TipoContrato> getTiposContratoViewModel();  

    // =========================================================================
    // ..    ** IMPORTANTE **
    // generar los getter y los setter del atributo: selectedItem
    // =========================================================================
    
}
