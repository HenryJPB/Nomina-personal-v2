/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.modelo.entidad.Variable;
import bean.servicio.ServicioAdmonFormulaConcepto;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author henrypb
 */
public class ModelViewDefVariables {
    
    private static String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");

    private Variable selectedVariable;

    private VariableViewFilter variableViewFilter = new VariableViewFilter();

    private List<Variable> listaVariables = new ServicioAdmonFormulaConcepto().getListaVariables(rifEmpresa);

    //--------------------------------------------------------------------------
    @Init
    public void init() {    // Initialize
        //listaVariables.clear();
        //selected = currentTrabajadores.get(0); // Selected First One
    }

    //--------------------------------------------------------------------------
    public ListModel<Variable> getVariablesViewModel() {
        return (new ListModelList<Variable>(listaVariables));
    }

    //--------------------------------------------------------------------------
    public VariableViewFilter getVariableViewFilter() {
        return (variableViewFilter);
    }

    //--------------------------------------------------------------------------
    @Command
    @NotifyChange({"variablesViewModel", "footer"})
    public void changeFilter() {
        listaVariables = new ServicioAdmonFormulaConcepto().getFilterVariables(variableViewFilter);
    }

    //--------------------------------------------------------------------------
    public Variable getSelectedVariable() {
        return selectedVariable;
    }

    //--------------------------------------------------------------------------
    public void setSelectedVariable(Variable selectedVariable) {
        this.selectedVariable = selectedVariable;
    }

    // *************************( Valor agregado )******************************
    
    //--------------------------------------------------------------------------
    // Bqto: 24 enero 2019 
    // (( Correcto )) 
    // NOTA *importante*  
    // El Item del Listbox debe ser (mandatorio) seleccionado previamente
    //--------------------------------------------------------------------------
    @Command("btnEliminar")
    public void btnEliminar(@BindingParam("selectedVariable") Variable selectedVariable) {
        if (selectedVariable != null) {
            System.out.println("****selectVar.Codigo=AQUICAAAAAAAAAAAAAAAAAAAAAAAAA***CodVar=" + selectedVariable.getCodVar() + "********");
        } else {
            System.out.println("****selectVar.Codigo=AQUICAAAAAAAAAAAAAAAAAAAAAAAAA***SelectedVariabler=es NULA??????");  // Chequear que el item haya sido seleccionado previamente 
        }
    } // btnEliminar

    //--------------------------------------------------------------------------
    // Bqto: 14 febrero 2019   (( Correcto )) 
    //--------------------------------------------------------------------------
    @Command("refrescarListbox")
    @NotifyChange({"variablesViewModel", "footer"})
    //public void btnRfr() {
    public void refrescarListbox() {
        System.out.println("....Inside refrescarListbox..???????????????*****************");  // Correctisimo 
        listaVariables = new ServicioAdmonFormulaConcepto().getListaVariables(rifEmpresa);  // ((( Variable de ambito global )))
    } // btnRfr()


}
