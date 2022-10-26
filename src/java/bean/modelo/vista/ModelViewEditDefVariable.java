/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.controlador.concepto.NomVariableDatJpaController;
import bean.controlador.concepto.exceptions.NonexistentEntityException;
import bean.modelo.entidad.NomVariableDat;
import bean.modelo.entidad.NomVariableDatPK;
import bean.utilitario.libreria.LibreriaHP;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author henrypb
 */
public class ModelViewEditDefVariable {

    String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    String codVar = (String) Sessions.getCurrent().getAttribute("codVar");
    NomVariableDat nomVariableDat = new NomVariableDat();

    {
        //System.out.println("*CodVar="+codVar);
        if (codVar != null && !codVar.isEmpty()) {
            NomVariableDatPK nomVariableDatPK = new NomVariableDatPK(rifEmpresa, codVar);
            nomVariableDat = new NomVariableDatJpaController().findNomVariableDat(nomVariableDatPK);
            //System.out.println("Variable="+nomVariableDat.getNomVariableDatPK().getCodVar());
        } else {
            //System.out.println("*CodVar es NULA =((*");
        }

    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Init
    public void init() {    // Initialize
    }  // init().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean datosValido() {
        Boolean ok = Boolean.FALSE;
        String valorStr = nomVariableDat.getValor().toString();
        LibreriaHP libHP = new LibreriaHP();
        if (libHP.esNumerico(valorStr) && libHP.numeroValido(valorStr)) {
            ok = Boolean.TRUE;
        } else {
            Messagebox.show("Valor numerico invalido", "ATENCION", Messagebox.OK, Messagebox.ERROR);
        }
        return ok;
    } // datosValido()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void ajustarDatos() {
        nomVariableDat.setStatus("C");  // C)onforme / N)o conforme.  
    } // ajustarDatos()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Command("btnEditClicked")
    public void btnEditClicked() {
        //System.out.println("***************[btnEditClicked] presionado*********************");
        Messagebox.show("CONFORME ?", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {

            @Override
            public void onEvent(Event evento) throws Exception {
                //if (evento.equals("onYes") && datosValido()) {
                if (evento.getName().equals("onYes")) {
                    ajustarDatos();
                    //System.out.println("Save? .. CodVar=" + nomVariableDat.getNomVariableDatPK().getCodVar() + "Nombre=" + nomVariableDat.getNombreVar() + "valor=" + nomVariableDat.getValor() + "conforme=" + nomVariableDat.getStatus() + "****");
                    new NomVariableDatJpaController().edit(nomVariableDat);
                    //System.out.println("Save listo!!!!!");
                } else {
                    System.out.println("ERROR ;-(");
                }
            } // onEvent()  
        });
    }  // btnEditClicked_OLD()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //@Command("btnEditClicked")
    public void btnEditClicked_OLD() {
        //System.out.println("***************[btnEditClicked] presionado*********************");
        ajustarDatos();
        try {
            //System.out.println("Save? .. CodVar=" + nomVariableDat.getNomVariableDatPK().getCodVar() + "Nombre=" + nomVariableDat.getNombreVar() + "valor=" + nomVariableDat.getValor() + "conforme=" + nomVariableDat.getStatus() + "****");
            new NomVariableDatJpaController().edit(nomVariableDat);
            //System.out.println("Save listo!!!!!");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ModelViewEditDefVariable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ModelViewEditDefVariable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  // btnEditClicked_OLD()

    public NomVariableDat getNomVariableDat() {
        return nomVariableDat;
    }

    public void setNomVariableDat(NomVariableDat nomVariableDat) {
        this.nomVariableDat = nomVariableDat;
    }

}
