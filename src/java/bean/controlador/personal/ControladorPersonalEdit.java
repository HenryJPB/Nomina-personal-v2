/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.personal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;

/**
 *
 * @author henrypb
 */
public class ControladorPersonalEdit  extends GenericForwardComposer {

    private Label lblMsg;  
    private Button btnReg; 
    private Button btnIni;  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // Instrucciones del programador: 
        /*
         if (validarUsuario()) {
         iniciar();
         }
         */
        iniciar(); 
    }  // doAfterCompose().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
       Integer nroTrabSeleccionado = (Integer) Sessions.getCurrent().getAttribute("nroTrabSeleccionado"); 
       //String msg = "**EDITAR TRABAJADOR FICHA="+nroTrabSeleccionado+"**";  
       //lblMsg.setValue(msg);
       //System.out.println(msg);
    } // iniciar().
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnReg() {
        String pagActiva = "../VISTA_PERSONAL/pagPersonal.zul";  
        Sessions.getCurrent().setAttribute("pagInclude",pagActiva); 
        Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnIni() {
        Sessions.getCurrent().setAttribute("pagInclude","inicio"); 
        Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");
    }
}
