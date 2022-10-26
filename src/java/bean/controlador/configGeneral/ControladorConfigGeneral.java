/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.configGeneral;

import bean.controlador.personal.NomTrabajador01DatJpaController;
import bean.modelo.entidad.TrabajadorView;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;

/**
 *
 * @author henrypb
 */
public class ControladorConfigGeneral extends GenericForwardComposer {

    private Button btnRfr;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //binder = new AnnotateDataBinder(comp);
        //binder.bindBean("controller", this);   // vincular (bind) un atributo de nombre 'controller' usado en la pag con este controlador.  
        //createModel();
    } // doAfterCompose(). 

    //--------------------------------------------------------------------------
    // despreciar el ultimo char y aplicar recursividad.  
    //--------------------------------------------------------------------------
    public String formatearCarpeta(String carpeta) {
        // leer el uttimo caracter
        String ultimoChar = carpeta.substring(carpeta.length() - 1, carpeta.length());
        if (ultimoChar != null && (ultimoChar.equals("/") || ultimoChar.equals("\\"))) {   // "\\" -> caracter escapeado para Wimdows System file   
            carpeta = formatearCarpeta(carpeta.substring(0, carpeta.length() - 1));   // despreciar el ultimo char y aplicar recursividad.  
        }
        return (carpeta);
    }  // formatearCarpeta(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnRfr() {
        // *********************Eliminar:******************************
        prueba(); 
        // ******************eof(Eliminar)*****************************
        String pagActiva = "../VISTA_CONFIG_GEN/pagConfigGeneral.zul";
        Sessions.getCurrent().setAttribute("pagInclude", pagActiva);
        Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");
    }
    
    // ****************PRUEBA******************
    private void prueba() {
        System.out.println("INI.Prueba");  
        String rif = "J-08503850-7";
        //List<Object[]> lista = new NomTrabajador01DatJpaController().getListaTrab(rif);  
        List<TrabajadorView> lista = new NomTrabajador01DatJpaController().getListaTrab(rif);  
        for ( TrabajadorView tv : lista ) {
            System.out.println("TipoNomina="+tv.getTipoNomina()+"***ficha="+tv.getNroTrabajador()+"***Nombre="+tv.getNombre()+"****Apellido="+tv.getApellido()+"****");  
            //System.out.println("TipoNomina="+tv[1]+"***ficha="+tv[2]+"***Nombre="+tv[4]+"****Apellido="+tv[5]+"****"); 
        }
        System.out.println("FIN.Prueba");  
    }  // prueba().  

}
