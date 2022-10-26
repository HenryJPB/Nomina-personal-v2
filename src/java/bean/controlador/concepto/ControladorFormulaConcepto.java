/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

//import org.zkoss.canvas.Canvas;
//import javafx.scene.canvas.Canvas;
import java.awt.Canvas;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

/**
 *
 * @author henrypb
 */
public class ControladorFormulaConcepto extends GenericForwardComposer {
    
    private Canvas mycanvas;  
    private Label lblFormulaBreadcrumb; 
    private Grid grdFormulacion;  
    private Button btnVariables;  
    private Include incPagFormulaConcepto;  
    
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //binder = new AnnotateDataBinder(comp);
        //binder.bindBean("controller", this);   // vincular (bind) un atributo de nombre 'controller' usado en la pag con este controlador.  
        //createModel();
        iniciar();
    } // doAfterCompose()
    
    //--------------------------------------------------------------------------
    private void iniciar() {
       //grdFormulacion.setVisible(false);  
    }
    //--------------------------------------------------------------------------
    public void onClick$btnVariables() {
        lblFormulaBreadcrumb.setValue("FORMULACION CONCEPTO / DEFINIR VARIABLE");
        grdFormulacion.setVisible(false); 
        incPagFormulaConcepto.setSrc("../VISTA_CONCEPTO/pagDefVariables.zul");
    }
    
    //--------------------------------------------------------------------------
    public void onClick$btnPromedios() {
        lblFormulaBreadcrumb.setValue("FORMULACION CONCEPTO / DEFINIR PROMEDIO");
        grdFormulacion.setVisible(false); 
        incPagFormulaConcepto.setSrc("../VISTA_CONCEPTO/pagDefPromedios.zul");
    }
    
    //--------------------------------------------------------------------------
    public void onClick$btnFormula() {
        lblFormulaBreadcrumb.setValue("FORMULACION CONCEPTO / DEFINIR FORMULA");
        grdFormulacion.setVisible(false); 
        incPagFormulaConcepto.setSrc("../VISTA_CONCEPTO/pagDefFormula.zul");
    }

}
