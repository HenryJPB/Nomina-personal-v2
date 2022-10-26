/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servicio;

import bean.controlador.concepto.NomPromedioDatJpaController;
import bean.controlador.concepto.NomVariableDatJpaController;
import bean.controlador.concepto.NomVariableHistDatJpaController;
import bean.interfaz.IServicioAdmonFormulaConcepto;
import bean.modelo.entidad.Formula;
import bean.modelo.entidad.NomFormulaDat;
import bean.modelo.entidad.NomPromedioDat;
import bean.modelo.entidad.NomVariableDat;
import bean.modelo.entidad.Promedio;
import bean.modelo.entidad.PromedioHist;
import bean.modelo.entidad.Variable;
import bean.modelo.entidad.VariableHist;
import bean.modelo.vista.PromedioViewFilter;
import bean.modelo.vista.VariableViewFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.zkoss.zk.ui.Sessions;

/**
 *
 * @author henrypb
 */
//public class ServicioAdmonFormulaConcepto implements IServicioAdmonFormulaConcepto {  // NO PROCEDE, ver nota en la interfaz.  
public class ServicioAdmonFormulaConcepto implements IServicioAdmonFormulaConcepto {

    //private static String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");

    private static List<Variable> listaVariables = new ArrayList<Variable>();  /* Condicion necesaria: tiene que se atributos/variables de ambito GLOBAL */

    private static List<VariableHist> listaVariableHist = new ArrayList<VariableHist>();

    private static List<Promedio> listaPromedios = new ArrayList<Promedio>();  /* Condicion necesaria: tiene que ser atributo/variable de ambito GLOBAL */

    private static List<PromedioHist> listaPromedioHist = new ArrayList<>();  
    
    private static List<NomFormulaDat> listaFormulas = new ArrayList<>();  

    //--------------------------------------------------------------------------
    // ERROR --> Remind, Necesario asignar la lista => listaVariables de
    //           ambito Global ...
    //--------------------------------------------------------------------------

     //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public List<Variable> getListaVariables(String rifEmpresa) {
        List<Variable> lista = new ArrayList<Variable>();  // * OJO: piquete 
        //String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
        List<NomVariableDat> listaNomVariableDat = new NomVariableDatJpaController().getListaVariables(rifEmpresa);
        Variable variable = null;
        for (NomVariableDat nomVariableDat : listaNomVariableDat) {
            variable = new Variable(nomVariableDat.getNomVariableDatPK().getCodVar(), nomVariableDat.getNombreVar(), nomVariableDat.getValor(), (nomVariableDat.getStatus().equals("C") ? Boolean.TRUE : Boolean.FALSE));
            lista.add(variable);
            //System.out.println("Cod Var="+variable.getCodVar()+"Nombre Variable="+variable.getNombreVar()+"****");
        }  // for.  
        //System.out.println("Longitud de la lista variable="+lista.size()+"****"); 
        listaVariables = lista;  // *** Aqui esta el piquete ****
        return (listaVariables);
    } // getListaVariables()
    
    //--------------------------------------------------------------------------
    @Override
    public Variable[] getAllListaVariablesArray() {
        return (listaVariables.toArray(new Variable[listaVariables.size()]));
    }

    //--------------------------------------------------------------------------
    @Override
    public List<Variable> getFilterVariables(VariableViewFilter variableViewFilter) {
        List<Variable> someVariable = new ArrayList<>();
        String keyCodVar = variableViewFilter.getCodVar().toLowerCase();
        String keyNombreVar = variableViewFilter.getNombreVar().toLowerCase();
        String keyValor = variableViewFilter.getValor().toLowerCase();
        Variable tmp = null;
        //System.out.println("****KeycoVar="+variableViewFilter.getCodVar().toLowerCase()+"****KeyNmbre="+variableViewFilter.getNombreVar().toLowerCase()+"***Keyvalor="+variableViewFilter.getValor().toLowerCase()+"***");  
        for (Iterator<Variable> item = listaVariables.iterator(); item.hasNext();) {
            tmp = item.next();
            if (tmp.getCodVar().toLowerCase().contains(keyCodVar)
                    && tmp.getNombreVar().toLowerCase().contains(keyNombreVar)
                    && tmp.getValor().toString().toLowerCase().contains(keyValor)) {
                someVariable.add(tmp);
            }
        }  // for. 
        return (someVariable);
    } // getFilterVariables().  

    //--------------------------------------------------------------------------
    @Override
    public List<VariableHist> getListaVariableHist(String rifEmpresa) {
        String codVar = (String) Sessions.getCurrent().getAttribute("codVar");
        List<VariableHist> lista = new NomVariableHistDatJpaController().getListaVariableHist(rifEmpresa, codVar);
        /*
         List<NomVariableHistDat> listaNomVarHistDat = new NomVariableHistDatJpaController().  .findNomVariableHistDatEntities();  
         VariableHist variableHist=null;  
         for (NomVariableHistDat nomVariableHistDat : listaNomVarHistDat ) {
         variableHist = new VariableHist(nomVariableHistDat.getNomVariableHistDatPK().getCodVar(),nomVariableHistDat.getNomVariableHistDatPK().getFechaVigencia()); 
         lista.add(variableHist);  
         }
         */
        if (lista != null) {
            for (VariableHist varHist : lista ) {
                System.out.println("****Lista de Var Hist posee DATOS****");
            }  // for
        } else {
            System.out.println("****Lista de Var Hist sin DATOS****");
        }
        listaVariableHist = lista; 
        return (listaVariableHist);
    }  // getListaVariableHist()

    //--------------------------------------------------------------------------
    @Override
    public VariableHist[] getAllListaVariableHistArray() {
        return (listaVariableHist.toArray(new VariableHist[listaVariableHist.size()]));
    }

    //**////////////////////////////////////////////////////////////////////**//
    //--------------------------------------------------------------------------
    @Override
    public List<Promedio> getListaPromedios(String rifEmpresa) {
        List<Promedio> lista = new ArrayList<Promedio>();
        //String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
        List<NomPromedioDat> listaNomPromedioDat = new NomPromedioDatJpaController().getListaPromedios(rifEmpresa);
        Promedio promedio = null;
        for (NomPromedioDat nomPromedioDat : listaNomPromedioDat) {
            promedio = new Promedio(nomPromedioDat.getNomPromedioDatPK().getCodProm(), nomPromedioDat.getNombreProm(), nomPromedioDat.getEcuacion(), nomPromedioDat.getObservacion(), (nomPromedioDat.getStatus().equals("S") ? Boolean.TRUE : Boolean.FALSE));
            lista.add(promedio);
        }  // for. 
        listaPromedios = lista;  
        return (listaPromedios);
    }  // getListaPromedios()

    //--------------------------------------------------------------------------
    @Override
    public Promedio[] getAllListaPromediosArray() {
        return (listaPromedios.toArray(new Promedio[listaPromedios.size()]));
    }

    //--------------------------------------------------------------------------
    @Override
    public List<Promedio> getFilterPromedios(PromedioViewFilter promedioViewFilter) {
        List<Promedio> somePromedio = new ArrayList<Promedio>();
        String keyCodProm = promedioViewFilter.getCodProm().toLowerCase();
        String keyNombreProm = promedioViewFilter.getNombreProm().toLowerCase();
        Promedio tmp = null;
        for (Iterator<Promedio> item = listaPromedios.iterator(); item.hasNext();) {
            tmp = item.next();
            if (tmp.getCodProm().toLowerCase().contains(keyCodProm)
                    && tmp.getNombreProm().toLowerCase().contains(keyNombreProm)) {
                somePromedio.add(tmp);
            }  // if. 
        }
        return (somePromedio);
    }  // getFilterPromedios()

    //--------------------------------------------------------------------------
    public List<NomFormulaDat> getListaFormulas(String rifEmpresa, Integer codNomina, Integer codConcepto, String codFormula ) {
        listaFormulas = new bean.controlador.concepto.NomFormulaDatJpaController().getFormulas(rifEmpresa,codNomina,codConcepto,codFormula);   
        return (listaFormulas);
    }  // getListaFormulas()

    //--------------------------------------------------------------------------
    @Override
    public NomFormulaDat[] getAlllistaFormulasArray() {
        return ( listaFormulas.toArray(new NomFormulaDat[listaFormulas.size()]) );
    }

}
