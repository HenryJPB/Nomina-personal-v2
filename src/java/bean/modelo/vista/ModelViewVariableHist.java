/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.interfaz.IModelViewVariableHist;
import bean.modelo.entidad.VariableHist;
import bean.servicio.ServicioAdmonFormulaConcepto;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author henrypb
 */
public class ModelViewVariableHist implements IModelViewVariableHist {
    
    private static String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");

    private VariableHist selectedVariableHist;  

    private List<VariableHist> listaVariableHist = new ServicioAdmonFormulaConcepto().getListaVariableHist(rifEmpresa);
    
    //--------------------------------------------------------------------------
    @Init
    public void init() {    // Initialize
        //listaVariables.clear();
        //selected = currentTrabajadores.get(0); // Selected First One
    }

    //--------------------------------------------------------------------------
    @Override
    public ListModel<VariableHist> getVariableHistViewModel() {
        return (new ListModelList<VariableHist>(listaVariableHist));
    }
    
    //--------------------------------------------------------------------------
    // Bqto: 29 enero 2019 
    // NOTA *importante*
    // El Item del Listbox debe ser (mandatorio) seleccionado previamente
    //--------------------------------------------------------------------------
    @Command("btnEliminar")
    public void btnEliminar(@BindingParam("selectedVariableHist") VariableHist selectedVariableHist) {
        if (selectedVariableHist != null) {
            System.out.println("****selectVar Hist.Codigo=AQUICAAAAAAAAAAAAAAAAAAAAAAAAA***CodVar=" + selectedVariableHist.getFechaVigencia()+"********");
        } else {
           System.out.println("****selectVar Hist .Codigo=AQUICAAAAAAAAAAAAAAAAAAAAAAAAA***SelectedVariabler=es NULA??????");  // Chequear que el item haya sido seleccionado previamente 
        }
    } // btnEliminar
    
    public VariableHist getSelectedVariableHist() {
        return selectedVariableHist;
    }

    public void setSelectedVariableHist(VariableHist selectedVariableHist) {
        this.selectedVariableHist = selectedVariableHist;
    }
    
}
