/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.modelo.entidad.Promedio;
import bean.servicio.ServicioAdmonFormulaConcepto;
import java.util.List;
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
public class ModelViewDefPromedios {
    
    private static String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    
    private Promedio selectedPromedio;  
    
    private PromedioViewFilter promedioViewFilter = new PromedioViewFilter(); 
    
    private List<Promedio> listaPromedios = new ServicioAdmonFormulaConcepto().getListaPromedios(rifEmpresa);  
 
    //--------------------------------------------------------------------------
    @Init
    public void init() {    // Initialize
        //selected = currentTrabajadores.get(0); // Selected First One
    }
    
    //--------------------------------------------------------------------------
    public ListModel<Promedio> getPromediosViewModel() {
        return( new ListModelList<Promedio>(listaPromedios) ); 
    }
    
    //--------------------------------------------------------------------------
    public PromedioViewFilter getPromedioViewFilter() {
        return (promedioViewFilter); 
    }
    
    
    @Command
    @NotifyChange({"promediosViewModel","footer"})
    public void changeFilter() {
        listaPromedios = new ServicioAdmonFormulaConcepto().getFilterPromedios(promedioViewFilter);  
    }
    
    //--------------------------------------------------------------------------
    public Promedio getSelectedPromedio() {
        return selectedPromedio;
    }

    //--------------------------------------------------------------------------
    public void setSelectedPromedio(Promedio selectedPromedio) {
        this.selectedPromedio = selectedPromedio;
    }
    
    
}
