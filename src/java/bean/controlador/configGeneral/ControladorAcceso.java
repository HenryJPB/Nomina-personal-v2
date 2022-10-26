/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.configGeneral;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

/**
 *
 * @author henrypb
 */
public class ControladorAcceso extends GenericForwardComposer {

    private Button btnEntrar;
    private Window winSelecEmpresa;
    private Window winEmpresaNew;  
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //binder = new AnnotateDataBinder(comp);
        //binder.bindBean("controller", this);   // vincular (bind) un atributo de nombre 'controller' usado en la pag con este controlador.  
        //createModel();
        iniciar();
    } // doAfterCompose()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnEntrar() {
        // 1) Validar Usuario
        validarUsuario();  
        //    
        // 2) if BD Empresa esta vacia redirigir pag a pagEmpresa -> accionar metodo include. 
        //    , else popUp pagSelecEmpresa despues de seleccionada una empresa redirigir el Thread a
        //    VISTA_PRINCIPAL/Menu principal .... 
        checkEmpresa();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
        //Messagebox.show("Switch include"); 
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void validarUsuario() {
        Sessions.getCurrent().setAttribute("usuarioAutentificado", "usuario");   // cualquier nombre; Recuerda validar el nombre del usuario: henrypb, 08 Julio 2012. 
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void checkEmpresa() {
        if ((new NomConfigDatJpaController()).findNomConfigDatEntities() == null) {  // 1ra ves?? No hay empresas registradas 
            //Executions.sendRedirect("VISTA_PRINCIPAL/pagPrincipal.zul");
            winEmpresaNew.doPopup();  // 
            //Executions.sendRedirect("VISTA_EMPRESA/pagEmpresa.zul");  // incluir>>obligatorio
        } else {
            winSelecEmpresa.doPopup();
            //winSelecEmpresa.setTitle("SELECCIONAR EMPRESA / SUCURSAL");
        }
    }  // checkEmpresa()

}
