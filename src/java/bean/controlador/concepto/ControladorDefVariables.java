/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.modelo.entidad.Variable;
import bean.servicio.ServicioAdmonFormulaConcepto;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

/**
 *
 * @author henrypb
 */
public class ControladorDefVariables extends GenericForwardComposer {
    
    private Listbox lbxDefVariables;
    private Button  btnAdd;  
    private Button  btnEdit;  
    private Button  btnDel;
    private Button  btnRegresar; 
    private Button  btnRfr;  
    //
    private Button  btnLimpiarKeys; 
    String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // << intrucciones del usuario >>
        //certificarUsuario(); 
        //checkEventQueves();  
        iniciar();  
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onSelect$lbxDefVariables(Event event) throws IOException {
        // System.out.println("*****************onSelect activado***************");    // Fino !!
    }  // onSelect$lbxDefVariables().  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnEdit() {
        editar();  
    } // onClick$btnEdit()
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnRfr() {
        System.out.println("**************Refrescar***************");   
        //lbxDefVariables.renderAll();   //   ?????No funcionó????????
    } // onClick$btnRfr()
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnLimpiarKeys() {
        refrescarListbox();
    } // onClick$btnRfr()
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
        //lbxDefVariables.getItems().clear();
        //lbxDefVariables.renderAll();   // ???? 
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void editar() {
        //
        // System.out.println("***Index item selected="+lbxDefVariables.getIndexOfItem(lbxDefVariables.getSelectedItem())+"****");   // positivo enumerados desde cero. 
        //
        Listitem listitem = lbxDefVariables.getSelectedItem(); 
        if ( listitem!=null ) {
            Variable variable = getSeleccion(listitem);  
            Sessions.getCurrent().setAttribute("codVar",variable.getCodVar());  
            //
            Map<String,Object> parametros = new HashMap<String,Object>(); 
            parametros.put("VARIABLE_SELECTED",variable);  
            Window editDialog = (Window) Executions.createComponents("../VISTA_CONCEPTO/pagDefVariableEdit.zul", null,parametros);  
            //editDialog.setTitle("EDITAR VARIABLE");
            editDialog.doModal();
        } else {
            System.out.println("NO HAY SELECCION para -> Editar Variable");   
        }
    }  // editar().  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Variable getSeleccion(Listitem itemSelected) {
        List celdas = itemSelected.getChildren();
        //
        Listcell listcell = (Listcell) celdas.get(ControladorDefVariableEdit.enumAtributosVariable.COD_VAR.ordinal());
        String codVar = listcell.getLabel();  
        //
        listcell = (Listcell) celdas.get(ControladorDefVariableEdit.enumAtributosVariable.NOMBRE_VAR.ordinal());
        String nombreVar = listcell.getLabel();  
        //
        //listcell = (Listcell) celdas.get(ControladorDefVariableEdit.enumAtributosVariable.VALOR.ordinal());
        //Double valor = Double.parseDouble(listcell.getLabel());  
        //
        listcell = (Listcell) celdas.get(ControladorDefVariableEdit.enumAtributosVariable.VALOR.ordinal());
        Decimalbox dcbValor = (Decimalbox) listcell.getFirstChild();     // OJO: <= get a BigDecimal value
        //System.err.println("*******"+dcbValor.getValue()+"**************");
        //Double valor = Double.parseDouble(listcell.getLabel());  
        Double valor = dcbValor.doubleValue();  
        //
        listcell = (Listcell) celdas.get(ControladorDefVariableEdit.enumAtributosVariable.CONFORME.ordinal());
        Checkbox chqBoxConforme = (Checkbox) listcell.getFirstChild(); 
        Boolean conforme = chqBoxConforme.isChecked();  
        //Boolean conforme = ( listcell.getLabel()==null || listcell.getLabel().equals("N") ) ? Boolean.FALSE : Boolean.TRUE;
        
        Variable variable = new Variable(codVar,nombreVar,valor,conforme); 
        return (variable);  
    }  // getSeleccion()
    
    //--------------------------------------------------------------------------
    // ???? => Sujeto a revision ...but, Remenber that: Este listbox es del tipo 
    // template=model ...  ((( CHECK THIS OUT )))
    //--------------------------------- -----------------------------------------
    private void refrescarListbox() {
         List<Variable> lista = new ServicioAdmonFormulaConcepto().getListaVariables(rifEmpresa);
         System.out.println("Tamaño de la lista (refreshListbos)="+lista.size()+"****"); 
         ListModelList lml = new ListModelList(lista);
         lbxDefVariables.setModel(lml);
    } // refrescarListbox()
    
}
