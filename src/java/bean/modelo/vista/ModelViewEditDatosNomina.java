/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import org.zkoss.zk.ui.Sessions;
import bean.modelo.entidad.NomTrabajador01Dat;
import bean.modelo.entidad.NomTrabajador01DatPK;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.Messagebox;
import bean.controlador.personal.NomTrabajador01DatJpaController;
import bean.servicio.ServicioAdmonTrabajador;
import org.zkoss.zk.ui.event.Event;

/**
 *
 * @author henrypb
 */
public class ModelViewEditDatosNomina {

    String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    Integer nroTrabajador = (Integer) Sessions.getCurrent().getAttribute("nroTrabSeleccionado");
    NomTrabajador01Dat nomTrabajador01Dat = new NomTrabajador01Dat();

    {
        
        if (nroTrabajador >= 0) {  // edit record. 
            NomTrabajador01DatPK clave = new NomTrabajador01DatPK(rifEmpresa, nroTrabajador);
            nomTrabajador01Dat = new bean.controlador.personal.NomTrabajador01DatJpaController().findNomTrabajador01Dat(clave);
            if (nomTrabajador01Dat == null) { // Add record
                nomTrabajador01Dat = new NomTrabajador01Dat(clave, null, 0, 0, "", "", 0, "","", null, "N", "A");
            }
        }
    }

    @Init
    public void init() {    // Initialize
        //selected = currentTrabajadores.get(0); // Selected First One
    }  // init(). 

    public NomTrabajador01Dat getNomTrabajador01Dat() {
        return nomTrabajador01Dat;
    }

    public void setNomTrabajador01Dat(NomTrabajador01Dat nomTrabajador01Dat) {
        this.nomTrabajador01Dat = nomTrabajador01Dat;
    }

    //--------------------------------------------------------------------------
    private void ajustarDatos() {

    }  // prepararDatos(). 

    //--------------------------------------------------------------------------
    @Command("btnEditClicked")
    public void btnEditClicked(@BindingParam("nomTrabajador00Dat") NomTrabajador01Dat fx) {
        Messagebox.show("CONFORME ?", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event evento) throws Exception {
                if (evento.getName().endsWith("onYes")) {
                    if (nroTrabajador >= 0 && rifEmpresa != null && !rifEmpresa.isEmpty()) {
                        // prepararDatos();   ¿¿¿ El trabajador tiene un registro en la tabla DATOS de NOMINA ???
                        if (new ServicioAdmonTrabajador().existeTrabNomina(rifEmpresa, nroTrabajador)) {
                            (new NomTrabajador01DatJpaController()).edit(nomTrabajador01Dat);
                        } else {
                            (new NomTrabajador01DatJpaController()).create(nomTrabajador01Dat);
                        }
                    }  // nroTrabajador >= 0 ,.. 
                }  // if ( evento.getName()
            }  //  onEvent(Event evento, ...
        });  // Messagebox().  
    }  // btEditClicked().  

    //--------------------------------------------------------------------------
    @Command("setMarcaTarjeta")
    public void setMarcaTarjeta(@BindingParam("valorParametro") Boolean target) {
        //System.out.println("Esta funcionando?????---->Yesssss");
        //System.out.println("**** S.Target (valorParametro)=" + target + "****");
        nomTrabajador01Dat.setMarcaTarjeta((target ? "S" : "N"));
    }  // setMarcaTarjeta()

}
