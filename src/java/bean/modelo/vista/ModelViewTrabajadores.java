/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.modelo.entidad.NomTrabajador00Dat;
import bean.modelo.entidad.Trabajador;
import bean.controlador.personal.NomTrabajador00DatJpaController;
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
public class ModelViewTrabajadores {

    private Trabajador selected;

    private TrabajadorViewFilter trabajadorViewFilter = new TrabajadorViewFilter();

    List<NomTrabajador00Dat> listaTrabajadores = (new NomTrabajador00DatJpaController()).findNomTrabajador00DatEntities();
    /*
    REMENBER: para la tabla NomTRabajador00_Dat no hay un metodo especifico: 'findNomTrabajador00DatEntities(rifEmpresa)' 
              debido q esta tabla es creada considerando clave(s) primarias, y rifEmpresa en esta tabla no es unica. 
              Ver metodo selectivo 'ServicioAdmonTrabajador.getTrabajadores()'. 
    */
    
    List<Trabajador> listaResumenTrabajadores = ServicioAdmonTrabajador.getTrabajadores(); 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Init
    public void init() {    // Initialize
        //selected = currentTrabajadores.get(0); // Selected First One
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public TrabajadorViewFilter getTrabajadorFilter() {
        return trabajadorViewFilter;
    }  // getTrabajadorFilter()
     
    //--------------------------------------------------------------------------
    // check this out ( pagPersonal.zul ). 
    // <listbox id="lbxTrabajadores" model="@load(vm.nomlViewModel)" height="100%" width="100%" checkmark="true" multiple="false"  ...
    //--------------------------------------------------------------------------
    public ListModel<Trabajador> getTrabajadorViewModel() {
        return ( new ListModelList<Trabajador>(listaResumenTrabajadores) );   
    } // getTrabajadorViewModel()
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Command
    @NotifyChange({"trabajadorViewModel", "footer"})  // check: getTrabajadorViewModel(). 
    public void changeFilter() {
        listaResumenTrabajadores = ServicioAdmonTrabajador.getFilterTrabajadores(trabajadorViewFilter);  
    }  // changeFilter()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public Trabajador getSelectedTrabajador() {
        return selected;
    }  // getSelectedTrabajador()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void setSelectedTrabajador(Trabajador selected) {
        this.selected = selected;
    }
    
}
