/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.interfaz.IModelViewAsigConcepto;
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
public class ModelViewAsigConcepto implements IModelViewAsigConcepto {

    //==========================================================================
    // Definir atributos de ambito global para esta clase:  ////////////////////
    //==========================================================================
    private AsigConcepto selectedAsigConcepto;

    private AsigConceptoViewFilter nomConceptoDatViewFilter = new AsigConceptoViewFilter();

    String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    Integer ssnNroTrabajadorAC = (Integer) Sessions.getCurrent().getAttribute("ssnNroTrabajadorAC");
    Integer ssnCodNominaAC = (Integer) Sessions.getCurrent().getAttribute("ssnCodNominaAC");
    Integer ssnCodConceptoAC = (Integer) Sessions.getCurrent().getAttribute("ssnCodConceptoAC");
    private List<AsigConcepto> listaAsigConceptos = new ArrayList<>();

    {
        if (rifEmpresa != null && !rifEmpresa.isEmpty()) {
            //listaAsigConceptos = new bean.controlador.concepto.NomAsigConceptoDatJpaController().listaAsigConceptos(rifEmpresa);
            listaAsigConceptos = new bean.servicio.ServicioAdmonConcepto().getListaAsigConcepto(rifEmpresa);
        }
    }

    //************************SUP05_VIEW ==> Sup05Vista ************************
    // NOTA: todos los atributos de esta clase son tratados tipo String para 
    //       gestionar el Listbox y saltarce el paso de atributos con Date y/o Decimal (box ).  
    //**************************************************************************
    @Override
    @Init
    public void init() {
        if ( ( ssnNroTrabajadorAC!=null && ssnNroTrabajadorAC > 0 ) || ( ssnCodNominaAC!=null && ssnCodNominaAC > 0 ) || ( ssnCodConceptoAC!=null && ssnCodConceptoAC > 0 ) ) {
            if ( ssnNroTrabajadorAC!=null && ssnNroTrabajadorAC > 0 ) {
                nomConceptoDatViewFilter.setNroTrabajadorS(ssnNroTrabajadorAC.toString());   // Correcto ✔- Bqto, 26/10/2020 08:45
            } // 
            if ( ssnCodNominaAC!=null &&  ssnCodNominaAC > 0 ) {
                nomConceptoDatViewFilter.setCodNominaS(ssnCodNominaAC.toString());
            }
            if ( ssnCodConceptoAC != null && ssnCodConceptoAC > 0 ) {
                nomConceptoDatViewFilter.setCodConceptoS(ssnCodConceptoAC.toString());
            }
            changeFilter();
        }
    } // init().  

    //--------------------------------------------------------------------------
    //                                   get_ViewModel()
    //--------------------------------------------------------------------------
    @Override
    public ListModel<AsigConcepto> getAsigConceptoViewModel() {
        return (new ListModelList<AsigConcepto>(listaAsigConceptos));
    }

    //--------------------------------------------------------------------------
    //                              get_ViewFilter()
    //--------------------------------------------------------------------------
    @Override
    public AsigConceptoViewFilter getAsigConceptoViewFilter() {
        return (nomConceptoDatViewFilter);
    }

    //--------------------------------------------------------------------------
    //           (get) asigConceptoViewModel())
    //--------------------------------------------------------------------------
    @Command("changeFilter")
    @NotifyChange({"asigConceptoViewModel", "footer"})  // *** Anotacion mandatoria ***
    @Override
    public void changeFilter() {
        //System.out.println("codNom="+nomConceptoDatViewFilter.getCodNominaS()+" nombre="+nomConceptoDatViewFilter.getNombre()+"**codConcepto="+nomConceptoDatViewFilter.getCodConceptoS()+"***"+"***FcehaNom="+nomConceptoDatViewFilter.getFechaNominaS()+"***");
        listaAsigConceptos = new ServicioAdmonConcepto().getFilterAsigConcepto(nomConceptoDatViewFilter);
    }

    //--------------------------------------------------------------------------
    // Nota: los atributos txtKey..?.. del lbxAsigConcepto se blankean en el 
    //       ControladorAsigConcepto.  
    //--------------------------------------------------------------------------
    @Command("btnLimpiarKeys")
    @NotifyChange({"asigConceptoViewModel", "footer"})  // *** Anotacion mandatoria ***
    public void btnLimpiarKeys() {
        nomConceptoDatViewFilter.setCodNominaS("");
        nomConceptoDatViewFilter.setNroTrabajadorS("");
        nomConceptoDatViewFilter.setNombre("");  
        nomConceptoDatViewFilter.setCodConceptoS("");
        nomConceptoDatViewFilter.setDescripcion("");
        changeFilter();  
    } // btnLimpiarKeys()
    
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
