/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.interfaz.IModelViewConceptoHist;
import bean.modelo.entidad.AsigConcepto;
import bean.servicio.ServicioAdmonConcepto;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author hpulgar
 */
public class ModelViewConceptoHist implements IModelViewConceptoHist {

    //==========================================================================
    // Definir atributos de ambito global para esta clase:  ////////////////////
    //==========================================================================
    private AsigConcepto selectedAsigConcepto;

    private AsigConceptoViewFilter nomConceptoDatViewFilter = new AsigConceptoViewFilter();

    String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    private List<AsigConcepto> listaAsigConceptos = new ArrayList<>();

    {
        if (rifEmpresa != null && !rifEmpresa.isEmpty()) {
            listaAsigConceptos = new bean.controlador.concepto.NomConceptoHistDatJpaController().listaAsigConceptos(rifEmpresa);
        }
    }

    //************************SUP05_VIEW ==> Sup05Vista ************************
    // NOTA: todos los atributos de esta clase son tratados tipo String para 
    //       gestionar el Listbox y saltarce el paso de atributos con Date y/o Decimal (box ).  
    //**************************************************************************

    @Override
    @Init
    public void init() {
        //
    }

    //--------------------------------------------------------------------------
    //                                   get_ViewModel()
    //--------------------------------------------------------------------------
    @Override
    public ListModel<AsigConcepto> getConceptoHistViewModel() {
        return (new ListModelList<AsigConcepto>(listaAsigConceptos));
    }

    //--------------------------------------------------------------------------
    //                              get_ViewFilter()
    //--------------------------------------------------------------------------
    @Override
    public AsigConceptoViewFilter getConceptoHistViewFilter() {
        return (nomConceptoDatViewFilter);  
    }

    @Command
    @NotifyChange({"conceptoHistViewModel", "footer"})  // *** Anotacion mandatoria ***
    @Override
    public void changeFilter() {
        //System.out.println("codNom="+nomConceptoDatViewFilter.getCodNominaS()+" nombre="+nomConceptoDatViewFilter.getNombre()+"**codConcepto="+nomConceptoDatViewFilter.getCodConceptoS()+"***"+"***FcehaNom="+nomConceptoDatViewFilter.getFechaNominaS()+"***");
        listaAsigConceptos = new ServicioAdmonConcepto().getFilterAsigConcepto(nomConceptoDatViewFilter);  
    }

    // =========================================================================
    // ..    ** IMPORTANTE **
    // generar los getter y los setter del atributo: selectedItem
    // =========================================================================
    public AsigConcepto getSelectedAsigConcepto() {
        return selectedAsigConcepto;
    }

    public void setSelectedAsigConcepto(AsigConcepto selectedAsigConcepto) {
        this.selectedAsigConcepto = selectedAsigConcepto;
    }
   
}
