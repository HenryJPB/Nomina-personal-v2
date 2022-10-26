/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author hpulgar
 */
public class ControladorConceptoHist extends GenericForwardComposer {

    private Button btnAdd, btnEdit;
    private Listbox lbxAsigConceptos;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // << intrucciones del usuario >>
        //certificarUsuario(); 
        //checkEventQueves();  
        //prueba();
    }

    //--------------------------------------------------------------------------
    public void onClick$btnEdit() {
        if (lbxAsigConceptos.getSelectedCount() > 0) {
            setEditSsnCamposClaves();
            Include ssnIncPag = (Include) Sessions.getCurrent().getAttribute("ssnIncPag");
            ssnIncPag.setSrc("../VISTA_CONCEPTO/pagAjusteConHistEdit.zul");
        } else {
            Messagebox.show("DEBES SELECCIONAR UN REGISTRO.", "ATENCION", Messagebox.OK, Messagebox.INFORMATION);
        }
    }  // onClick$btnEdit()

    //--------------------------------------------------------------------------
    public void onClick$btnAdd() {
        setNewSsnCamposClaves();
        Include ssnIncPag = (Include) Sessions.getCurrent().getAttribute("ssnIncPag");
        ssnIncPag.setSrc("../VISTA_CONCEPTO/pagAjusteConHistEdit.zul");
    }  // onClick$btnAdd()

    //--------------------------------------------------------------------------
    public void onSelect$lbxAsigConceptos() {
        //setSsnCamposClaves();
    } // onSelect$lbxAsigConceptos()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void setEditSsnCamposClaves() {
        // =============================
        // *Listitem de campos claves: *
        // =============================
        org.zkoss.zul.Listitem listItem = lbxAsigConceptos.getSelectedItem();
        java.util.List celdas = listItem.getChildren();
        org.zkoss.zul.Listcell listCell = (org.zkoss.zul.Listcell) celdas.get(0);
        // *codNomina*
        Integer ssnCodNomina = -1;
        String codNominaS = listCell.getLabel();
        if (codNominaS != null && !codNominaS.isEmpty()) {
            ssnCodNomina = Integer.parseInt(codNominaS);
        }
        // *nroTrabajador*
        Integer ssnNroTrabajador = -1;
        listCell = (org.zkoss.zul.Listcell) celdas.get(1);
        String nroTrabajadorS = listCell.getLabel();
        if (nroTrabajadorS != null && !nroTrabajadorS.isEmpty()) {
            ssnNroTrabajador = Integer.parseInt(nroTrabajadorS);
        }
        // *codConcepto*
        Integer ssnCodConcepto = -1;
        listCell = (org.zkoss.zul.Listcell) celdas.get(3);
        String codConceptoS = listCell.getLabel();
        if (codConceptoS != null && !codConceptoS.isEmpty()) {
            ssnCodConcepto = Integer.parseInt(codConceptoS);
        }
        // *fechaNomina*
        java.util.Date ssnFechaNomina = new java.util.Date();
        listCell = (org.zkoss.zul.Listcell) celdas.get(5);
        String fechaNominaS = listCell.getLabel();
        //
        if (fechaNominaS != null && !fechaNominaS.isEmpty()) {
            try {
                ssnFechaNomina = new SimpleDateFormat("dd/MM/yyyy").parse(fechaNominaS);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorConceptoHist.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //System.out.println("**codNom=" + ssnCodNomina + "**nroTrab=" + ssnNroTrabajador + "**codConcepto=" + ssnCodConcepto + "**fechaNomina=" + ssnFechaNomina + "**");
        Sessions.getCurrent().setAttribute("ssnCodNomina", ssnCodNomina);
        Sessions.getCurrent().setAttribute("ssnNroTrabajador", ssnNroTrabajador);
        Sessions.getCurrent().setAttribute("ssnCodConcepto", ssnCodConcepto);
        Sessions.getCurrent().setAttribute("ssnFechaNomina", ssnFechaNomina);
    }  // setEditSSnCamposClaves(). 

    //--------------------------------------------------------------------------
    private void setNewSsnCamposClaves() {
        // nomConceptoHistDatPK(ssnRifEmpresa,-1,-1,-1,new java.util.Date()), 0.00, 0.00, 0.00, 0, null);
        Sessions.getCurrent().setAttribute("ssnCodNomina", -1);
        Sessions.getCurrent().setAttribute("ssnNroTrabajador", -1);
        Sessions.getCurrent().setAttribute("ssnCodConcepto", -1 );
        Sessions.getCurrent().setAttribute("ssnFechaNomina", new java.util.Date());
    }  // setSSnCamposClaves(). 

}
