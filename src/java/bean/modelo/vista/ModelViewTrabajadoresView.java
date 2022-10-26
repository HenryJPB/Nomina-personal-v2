/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.modelo.entidad.TrabajadorView;
import bean.servicio.ServicioAdmonTrabajador;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author henrypb
 */
public class ModelViewTrabajadoresView {

    private TrabajadorView selected;

    private ViewTrabajadorViewFilter viewTrabajadorViewFilter = new ViewTrabajadorViewFilter();

    //List<NomTrabajadorView> listaTrabajadores = (new NomTrabajadorViewJpaController()).findNomTrabajadorView(null);  
    /*
    REMENBER: para la tabla NomTRabajador00_Dat no hay un metodo especifico: 'findNomTrabajador00DatEntities(rifEmpresa)' 
              debido q esta tabla es creada considerando clave(s) primarias, y rifEmpresa en esta tabla no es unica. 
              Ver metodo selectivo 'ServicioAdmonTrabajador.getTrabajadores()'. 
    */
    
    List<TrabajadorView> listaTrabajadores = ServicioAdmonTrabajador.getTrabajadoresView(); 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Init
    public void init() {    // Initialize
        //selected = currentTrabajadores.get(0); // Selected First One
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public ViewTrabajadorViewFilter getViewTrabajadorViewFilter() {
        return viewTrabajadorViewFilter;
    }  // getTrabajadorFilter()
     
    //--------------------------------------------------------------------------
    // check this out ( pagPersonal.zul ). 
    // <listbox id="lbxTrabajadores" model="@load(vm.nomlViewModel)" height="100%" width="100%" checkmark="true" multiple="false" rows="n" ...
    //--------------------------------------------------------------------------
    public ListModel<TrabajadorView> getTrabajadorViewModel() {
        return ( new ListModelList<TrabajadorView>(listaTrabajadores) );   
    } // getTrabajadorViewModel()
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Command
    @NotifyChange({"trabajadorViewModel", "footer"})  // check: getTrabajadorViewModel(). // Anotacion Mandatoria sino-> Salida Indeseable. 
    public void changeFilter() {
        listaTrabajadores = ServicioAdmonTrabajador.getFilterTrabajadoresView(viewTrabajadorViewFilter);  
    }  // changeFilter()

    //--------------------------------------------------------------------------
    // [Alt]-[Insert] Getter-Setter ( selected ) : getSelected, setSelected.
    //--------------------------------------------------------------------------
    // *Modificado* getSelected => getSelectedTrabajadorView()
    //--------------------------------------------------------------------------
    public TrabajadorView getSelectedTrabajadorView() {
        return selected;
    }
    
    //--------------------------------------------------------------------------
    // *Modificado* setSelected => setSelectedTrabajadorView()
    //--------------------------------------------------------------------------
    public void setSelectedTrabajadorView(TrabajadorView selected) {
        this.selected = selected;
    }
    
}
