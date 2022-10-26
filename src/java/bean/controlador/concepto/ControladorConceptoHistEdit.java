/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Selectbox;

/**
 *
 * @author hpulgar
 */
public class ControladorConceptoHistEdit extends GenericForwardComposer {

    private Button btnAdd, btnEdit;
    private Selectbox slbTipoNomina;
    private Datebox dtbFecha;  
    private String ssnRifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    private Integer ssnCodNomina = (Integer) Sessions.getCurrent().getAttribute("ssnCodNomina");
    private Integer ssnNroTrabajador = (Integer) Sessions.getCurrent().getAttribute("ssnNroTrabajador");
    private Integer ssnCodConcepto = (Integer) Sessions.getCurrent().getAttribute("ssnCodConcepto");
    private java.util.Date ssnFechaNomina = (java.util.Date) Sessions.getCurrent().getAttribute("ssnFechaNomina");

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // << intrucciones del usuario >>
        //certificarUsuario(); 
        //checkEventQueves();  
        //prueba();
        iniciar();
    }

    //--------------------------------------------------------------------------
    private void iniciar() {
        if ( registroNuevo() ) {
            dtbFecha.setReadonly(Boolean.FALSE);
        } else {
            dtbFecha.setReadonly(Boolean.TRUE);
        } 
    } // iniciar().  

    //--------------------------------------------------------------------------
    private Boolean registroNuevo() {
        if ((ssnRifEmpresa == null || ssnRifEmpresa.isEmpty())
                || (ssnCodNomina == null || ssnCodNomina < 0)
                || (ssnNroTrabajador == null || ssnNroTrabajador < 0)
                || (ssnCodConcepto == null || ssnCodConcepto < 0)
                || (ssnFechaNomina == null)) {
            return Boolean.TRUE;
        } else {
            return (Boolean.FALSE);
        }
    } // registroNuevo(); 

    //--------------------------------------------------------------------------
    public void onClick$btnEdit() {
    }  // onClick$btnEdit()

    //--------------------------------------------------------------------------
    public void onClick$btnAdd() {
    }  // onClick$btnAdd()

    //--------------------------------------------------------------------------
    public void onCreate$slbTipoNomina() {
        /*
         // setSelectCodNomina();   // finooooooooo !!!!!!!! 
         System.out.println("*********onCREATE codNomina*******");
         String ssnRifEmpresa = (String) org.zkoss.zk.ui.Sessions.getCurrent().getAttribute("rifEmpresa");
         java.util.List<String> lista = new bean.controlador.nomina.NomTiposNominaDatJpaController().getTiposNomina(ssnRifEmpresa);
         for ( String s : lista ) {
         System.out.println(s);
         }
         */
        //System.out.println("========================AQUICA(onCreate)==============================");
        //System.out.println("selected Item="+slbTipoNomina.getModel().getElementAt(0)+"*****");   // fiiiinooooo 
    } // onCreate$sbxCodNomina().

    //--------------------------------------------------------------------------
    private void setSelectTipoNomina() {
        /*
         String ssnRifEmpresa = (String) org.zkoss.zk.ui.Sessions.getCurrent().getAttribute("rifEmpresa");
         java.util.List<String> listaTipoNominas = new bean.controlador.nomina.NomTiposNominaDatJpaController().getTiposNomina(ssnRifEmpresa);
         //java.util.List listaModelo = new java.util.ArrayList<String>();
         java.util.List listaModelo;
         bean.modelo.entidad.NomTiposNominaDat nomTiposNominaDat;
         for (java.util.Iterator<bean.modelo.entidad.NomTiposNominaDat> item = listaTiposNomina.iterator(); item.hasNext();) {
         nomTiposNominaDat = item.next();
         listaModelo.add(nomTiposNominaDat.getNomTiposNominaDatPK().getCodNomina() + " " + nomTiposNominaDat.getNombreNomina());
         alert(nomTiposNominaDat.getNomTiposNominaDatPK().getCodNomina() + " " + nomTiposNominaDat.getNombreNomina());
         }
         */
    }  // setSelectTipoNomina()

    //--------------------------------------------------------------------------
    public void onSelect$slbTipoNomina() {
        //System.out.println("========================AQUICA(onSelect)==============================");
        //System.out.println("selected Item="+slbTipoNomina.getModel().getElementAt( slbTipoNomina.getSelectedIndex() )+"*****");  // fiiiiinooooo 
    }  // onSelect$slbTipoNomina(). 

}
