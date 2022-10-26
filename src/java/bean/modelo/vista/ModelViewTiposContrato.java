/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.controlador.configGeneral.NomConfigDatJpaController;
import bean.interfaz.IModelViewTiposContrato;
import bean.modelo.entidad.NomTrabajador02DatPK;
import bean.modelo.entidad.TipoContrato;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author hpulgar
 */
public class ModelViewTiposContrato implements IModelViewTiposContrato {

    //==========================================================================
    // Definir atributos de ambito global para esta clase:  ////////////////////
    //==========================================================================
    
    private TipoContrato selectedContrato;                                   
    
    private List<TipoContrato> listaContratos = new ArrayList<>();
    
    String ssnRifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    Integer ssnNroTrabajador = (Integer) Sessions.getCurrent().getAttribute("nroTrabSeleccionado");
    
    //--------------------------------------------------------------------------
    @Override
    @Init
    public void init() {
       if (ssnRifEmpresa != null && !ssnRifEmpresa.isEmpty() && ssnNroTrabajador >= 0 ) {
            //listaAsigConceptos = new bean.controlador.concepto.NomAsigConceptoDatJpaController().listaAsigConceptos(rifEmpresa);
            listaContratos  = new bean.controlador.personal.NomTrabajador02DatJpaController().getTiposContrato(ssnRifEmpresa, ssnNroTrabajador);
        }
    }

    //--------------------------------------------------------------------------
    @Override                 //   get_?_ViewModel()
    public ListModel<TipoContrato> getTiposContratoViewModel() {
        return( new ListModelList<TipoContrato>(listaContratos) ); 
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Command("btnRfr")
    @NotifyChange({"tiposContratoViewModel", "footer"})  // check: getTrabajadorViewModel(). 
    public void btnRfr() {
        //System.out.println("RFERESH tipoContrato Model View-----------------------------------");
        listaContratos  = new bean.controlador.personal.NomTrabajador02DatJpaController().getTiposContrato(ssnRifEmpresa, ssnNroTrabajador);
    }  // btnRfr()
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Command("btnDel")
    @NotifyChange({"tiposContratoViewModel", "footer"})
    public void btnDel() {
    Messagebox.show("ELIMINAR CONTRATO ?", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event evento) throws Exception {
                if ( evento.getName().equals("onYes") ) {
                    try {
                        //System.out.println("***Eliminar contrato de rif="+ssnRifEmpresa+"*Trabajador="+ssnNroTrabajador+"*CodNomina="+selectedContrato.getCodNomina());
                        NomTrabajador02DatPK pk = new NomTrabajador02DatPK( ssnRifEmpresa,ssnNroTrabajador,selectedContrato.getCodNomina() );                          
                        new bean.controlador.personal.NomTrabajador02DatJpaController().destroy(pk);
                        //
                    } catch (Exception ex) {
                        Logger.getLogger(ModelViewTiposContrato.class.getName()).log(Level.SEVERE, null, ex);
                    }  // try
                }  // if
            }  // onEvent()  
        }); // MessageBox()
       listaContratos  = new bean.controlador.personal.NomTrabajador02DatJpaController().getTiposContrato(ssnRifEmpresa, ssnNroTrabajador);
    } // btnDel()
    
    public TipoContrato getSelectedContrato() {
        return selectedContrato;
    }

    public void setSelectedContrato(TipoContrato selectedContrato) {
        this.selectedContrato = selectedContrato;
    }
    
    
}
