/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.controlador.empresa;

import bean.modelo.entidad.Empresa;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 *
 * @author hpulgar
 */
public class TestMe extends GenericForwardComposer {
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        System.out.println(" MODEULO DE PRUEBA: TextMe!!!!!!!!!!!!!!!");
        List<Empresa> lista = new bean.servicio.ServicioAdmonEmpresa().getListaEmpresa(); //
        for ( Empresa e : lista ) {
            System.out.println("Nombre: " + e.getNombre() );
            System.out.println("Razon Social: " + e.getRazonSocial() );
            System.out.println("Rif: " + e.getRif() );
            
        }
    }
    
}
