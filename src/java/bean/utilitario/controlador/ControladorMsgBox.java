/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.controlador;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;

/**
 *
 * @author hpulgar
 */
public class ControladorMsgBox extends GenericForwardComposer {

    //private Window winMsgBox;  
    private Label lblMsgBox;  
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // << intrucciones del usuario >>
        //certificarUsuario(); 
        //checkEventQueves();  
        iniciar();
    }
    
    //--------------------------------------------------------------------------
    private void iniciar() {
       String mensaje = (String) arg.get("MENSAJE");  
       //System.out.println("mensaje="+mensaje+"");
       lblMsgBox.setValue(mensaje);
    } // iniciar(). 

}
