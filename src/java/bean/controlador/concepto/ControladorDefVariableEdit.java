/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.modelo.entidad.NomVariableDat;
import bean.modelo.entidad.Variable;
import bean.utilitario.libreria.LibreriaHP;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;

/**
 *
 * @author henrypb
 */
public class ControladorDefVariableEdit extends GenericForwardComposer {

    private static final long serialVersionUID = 1L;
    private Window winDefVarEdit;
    private Button btnRecuerdame, btnValidar;
    private Image imgValidado;
    private Decimalbox dcbValor;   
    Variable variableSelected = null;
    NomVariableDat nomVariableDat = new NomVariableDat();
    enum enumAtributosVariable {
        COD_VAR, NOMBRE_VAR, VALOR, CONFORME
    }

    //--------------------------------------------------------------------------
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        variableSelected = (Variable) arg.get("VARIABLE_SELECTED");
        inciar();
        /*
         System.out.println("***ESTOy EN CONTROLADOR: 'ControladorDefVAriableEDIT'");
         Variable variableSelected = (Variable) arg.get("VARIABLE_SELECTED");
         //System.out.println("***listItemSelected="+listItemSelected+"*******");
         if (variableSelected == null) {
         System.out.println("***Variable Select ES NULA =( ");
         } else {
         //Variable variable = getSeleccion(listItemSelected);  
         System.out.println("***Cod Var=" + variableSelected.getCodVar() + "***Nombre Var=" + variableSelected.getNombreVar() + "**Valida?=*"+variableSelected.getConforme()+"***");
         //Sessions.getCurrent().setAttribute("codVar", variableSelected.getCodVar());
         }
         */
    }  // doAfterCompose().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void inciar() {
        if (variableSelected != null) {
            if (variableSelected.getConforme()) {
                //imgValidado.setSrc("../IMAGENES/checkButton.ico");
                imgValidado.setSrc("../IMAGENES/check.ico");
            } else {
                imgValidado.setSrc("");
            }
        }
    }  //  inciar(). 
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onChange$dcbValor() {
        variableSelected.setConforme(Boolean.FALSE);
        imgValidado.setSrc("");
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnValidar() {
        validar();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnRecuerdame() {
        System.out.println("****Recuerdame*****");
    }  // onClick$btnRecuerdame(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void validar() {
        if (variableSelected != null) {
            if (!variableSelected.getConforme()) {
                String valorStr = variableSelected.getValor().toString();
                LibreriaHP libHP = new LibreriaHP();
                if (libHP.esNumerico(valorStr) && libHP.numeroValido(valorStr)) {
                    imgValidado.setSrc("../IMAGENES/check.ico");
                }
                imgValidado.setSrc("../IMAGENES/check.ico");
            } else {
                imgValidado.setSrc("");
            }
        }
    }  // validar()

}
