/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.personal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Listbox;

/**
 *
 * @author hpulgar
 */
public class ControladorContratos extends GenericForwardComposer {

    private Listbox lbxContratos;

    //--------------------------------------------------------------------------
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onSelect$lbxContratos() {
        System.out.println("*****onSelect$lbxContratos()*****seleccion="+lbxContratos.getSelectedItem().getValue()+"**index="+lbxContratos.getSelectedIndex()+"****");  
        /*
        Listcell listcell  = (Listcell) lbxContratos.getSelectedItem().getChildren().get(0); 
        String c  = listcell.getLabel();
        Integer contrato = Integer.parseInt( c.substring(0,c.indexOf("-")) );  
        */  
        /*
        bean.modelo.entidad.NomTrabajador02DatPK pk = new bean.modelo.entidad.NomTrabajador02DatPK("J",1003,1); 
        bean.modelo.entidad.NomTrabajador02Dat nomTrab02dat = new bean.modelo.entidad.NomTrabajador02Dat(pk,0,0); 
        try {
            new bean.controlador.personal.NomTrabajador02DatJpaController().create(nomTrab02dat);
        } catch (Exception ex) {
            Logger.getLogger(ControladorContratos.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        /*
        Boolean ok = new bean.controlador.personal.NomTrabajador02DatJpaController().existeContrato("J-41309179-8",1003,contrato); 
        if ( ok ) Messagebox.show("TRAB posee contrato!!");
        else Messagebox.show("TRAB NO posee contrato!!");
        */  
    }    
}
