/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.modelo.vista;

import bean.interfaz.IModelViewConceptos;
import bean.modelo.entidad.NomConceptoDat;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author hpulgar
 */
public class ModelViewConceptos implements IModelViewConceptos {
    
    // Definir atributos de ambito global para esta clase: ///////////////////// 
    // =========================================================================
    
    private NomConceptoDat selectedConcepto;  
    
    // private VistaItemViewFilter vistaItemViewFilter = new VistaItemViewFilter();  *(2)*  
    
    public List<NomConceptoDat> listaConceptos = new bean.controlador.concepto.NomConceptoDatJpaController().findNomConceptoDatEntities(); 

    //==========================================================================
    @Override
    public void init() {
        //
    }

    //==========================================================================
    @Override
    public ListModel<NomConceptoDat> getNomConceptoDatViewModel() {
        return( new ListModelList<NomConceptoDat>(listaConceptos) );
    }

    //==========================================================================
    @Override
    public AsigConceptoViewFilter getNomConceptoDatViewFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //==========================================================================
    @Override
    @Command
    @NotifyChange({"nomConceptoDatViewModel","footer"})
    public void changeFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //--------------------getter's  / setter's----------------------------------
    public NomConceptoDat getSelectedConcepto() {
        return selectedConcepto;
    }

    public void setSelectedConcepto(NomConceptoDat selectedConcepto) {
        this.selectedConcepto = selectedConcepto;
    }
    
    
}
