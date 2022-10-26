/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.controlador.concepto.NomAsigConceptoDatJpaController;
import bean.interfaz.IModelViewConceptoHistEdit;
import bean.modelo.entidad.NomAsigConceptoDat;
import bean.modelo.entidad.NomAsigConceptoDatPK;
import bean.modelo.entidad.NomConceptoHistDat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Selectbox;

/**
 *
 * @author hpulgar
 */
public class ModelViewAsigConceptoEdit implements IModelViewConceptoHistEdit {

    //==========================================================================
    // Definir atributos de ambito global para esta clase:  ////////////////////
    //==========================================================================
    //private NomConceptoHistDat nomConceptoHistDat = new NomConceptoHistDat(); 
    private NomAsigConceptoDat nomAsigConceptoDat = new NomAsigConceptoDat();
    //
    private String ssnRifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    private Integer ssnCodNomina = (Integer) Sessions.getCurrent().getAttribute("ssnCodNomina");
    private Integer ssnNroTrabajador = (Integer) Sessions.getCurrent().getAttribute("ssnNroTrabajador");
    private Integer ssnCodConcepto = (Integer) Sessions.getCurrent().getAttribute("ssnCodConcepto");

    /*
     {
     if ((ssnRifEmpresa == null || ssnRifEmpresa.isEmpty())
     || (ssnCodNomina == null || ssnCodNomina < 0)
     || (ssnNroTrabajador == null || ssnNroTrabajador < 0)
     || (ssnCodConcepto == null || ssnCodConcepto < 0)) {
     // *Registro nuevo*                                                     Cant  Monto Porc  FC   FP   CF Ini  Acti Obs1 Obs2
     nomAsigConceptoDat = new NomAsigConceptoDat(new NomAsigConceptoDatPK(), 0.00, 0.00, 0.00, null, null, 0, null, null, null, null);
     } else {  // *editar registr*  
     NomAsigConceptoDatPK nomAsigConceptoDatPK = new NomAsigConceptoDatPK(ssnRifEmpresa, ssnCodNomina, ssnNroTrabajador, ssnCodConcepto);
     nomAsigConceptoDat = new bean.controlador.concepto.NomAsigConceptoDatJpaController().findNomAsigConceptoDat(nomAsigConceptoDatPK);
     System.out.println("****DENTRO DE ModelViewAsigConceptoDat.....****"); 
     //
     }  // if-else
     //System.out.println("****COD_NOM="+nomConceptoHistDat.getNomConceptoHistDatPK().getCodNomina()+"***FECHA_NOMINA="+nomConceptoHistDat.getNomConceptoHistDatPK().getFechaNomina()+"***");
     } 
     */
    //--------------------------------------------------------------------------
    // Sujeto a Revision ???
    //-------------------------------------------------------------------------- 
    @Override
    @Init
    public void init() {
        if ((ssnRifEmpresa == null || ssnRifEmpresa.isEmpty())
                || (ssnCodNomina == null || ssnCodNomina < 0)
                || (ssnNroTrabajador == null || ssnNroTrabajador < 0)
                || (ssnCodConcepto == null || ssnCodConcepto < 0)) {
            // *Registro nuevo*       Double cantidad,                              Cant  Mont  Porc  FC    Cal, CF, FP,   Ini,  Act   Obs1  Obs2 
            nomAsigConceptoDat = new NomAsigConceptoDat(new NomAsigConceptoDatPK(), 0.00, 0.00, 0.00, null, null, null, null, null, null, null, null);
        } else {  // *editar registro*  
            NomAsigConceptoDatPK nomAsigConceptoDatPK = new NomAsigConceptoDatPK(ssnRifEmpresa, ssnCodNomina, ssnNroTrabajador, ssnCodConcepto);
            nomAsigConceptoDat = new bean.controlador.concepto.NomAsigConceptoDatJpaController().findNomAsigConceptoDat(nomAsigConceptoDatPK);
        }  // if-else
        //System.out.println("****DENTRO DE init****Porc="+nomAsigConceptoDat.getPorcentaje()+"****frecCalc="+nomAsigConceptoDat.getFrecuenciaCalculo()+"***frecPago="+nomAsigConceptoDat.getFrecuenciaPago()+"*****CodFormula="+nomAsigConceptoDat.getCodFormula()+"**observa2="+nomAsigConceptoDat.getObservacion2()+"***"); 
    }  // init 

    //--------------------------------------------------------------------------
    private Boolean registroNuevo() {
        Boolean ok = Boolean.FALSE;
        ok = (nomAsigConceptoDat.getNomAsigConceptoDatPK().getCodNomina() < 0)
                && (nomAsigConceptoDat.getNomAsigConceptoDatPK().getNroTrabajador() < 0)
                && (nomAsigConceptoDat.getNomAsigConceptoDatPK().getCodConcepto() < 0);
        return ok;
    } // registroNuevo(). 

    //--------------------------------------------------------------------------
    @Command("btnEditClicked")
    //public void btnEditCliked(@BindingParam("nomConceptoHistDat") NomConceptoHistDat fx) {
    //public void btnEditCliked(@BindingParam("slbNomina") Selectbox slbNomina, @BindingParam("slbTrabajador") Selectbox slbTrabajador, @BindingParam("slbConcepto") Selectbox slbConcepto, @BindingParam("dtbFecha") java.util.Date fechaNomina ) {
    //public void btnEditCliked(@BindingParam("slbNomina") Selectbox slbNomina, @BindingParam("slbTrabajador") Selectbox slbTrabajador, @BindingParam("slbConcepto") Selectbox slbConcepto) {
    public void btnEditCliked(@BindingParam("codNomina") final Integer codNomina, @BindingParam("nroTrabajador") final Integer nroTrabajador, @BindingParam("codConcepto") final Integer codConcepto) {
        Messagebox.show("CONFORME ?", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event evento) throws Exception {
                //if (evento.getName().equals("onYes") && datosConforme()) {
                if (evento.getName().equals("onYes")) {
                    try {
                        // ajustarDatosOLD(String rifEmpresa, Selectbox slbNomina, Selectbox slbTrabajador, Selectbox slbConcepto ); 
                        ajustarDatos(ssnRifEmpresa, codNomina, nroTrabajador, codConcepto);
                        //System.out.println("Nomina =" + slbNomina.getModel().getElementAt(slbNomina.getSelectedIndex()) + "****");
                        //System.out.println("Trabajador =" + slbTrabajador.getModel().getElementAt(slbTrabajador.getSelectedIndex()) + "****");
                        //System.out.println("*** Guardar datos ===>");
                        if (registroNuevo()) {   // **Nuevo registro **
                            new NomAsigConceptoDatJpaController().create(nomAsigConceptoDat); // Agregar reg BD.
                            System.out.println("Registron editado y agregado ✔✔*****");
                        } else {                   // *Actualizar registro*
                            new NomAsigConceptoDatJpaController().edit(nomAsigConceptoDat); // Actualizar reg BD. 
                            //new NomAsigConceptoDatJpaController().actualizar(nomAsigConceptoDat);  // Actualizar reg BD. ???? REVISAR ESTE METODO ... 
                            System.out.println("****Registro editado y guardado ✔✔*****");
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ModelViewAsigConceptoEdit.class.getName()).log(Level.SEVERE, null, ex);
                    }  // try  // try
                }  // if
            }  // onEvent()  

        }); // MessageBox()
    } // btnEditCliked()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Command("btnAddClicked")
    @NotifyChange({"nomAsigConceptoDat", "footer"})   // Funcionó correctamente en Bqto: 04/09/2020 14:45
    //public void btnAddClicked(@BindingParam("nomConceptoHistDat") NomConceptoHistDat fx, @BindingParam("slbNomina") Selectbox slbNomina, @BindingParam("slbTrabajador") Selectbox slbTrabajador, @BindingParam("slbConcepto") Selectbox slbConcepto, @BindingParam("dtbFecha") java.util.Date fechaNomina) {
    public void btnAddClicked(@BindingParam("nomConceptoHistDat") NomConceptoHistDat fx, @BindingParam("slbNomina") Selectbox slbNomina, @BindingParam("slbTrabajador") Selectbox slbTrabajador, @BindingParam("slbConcepto") Selectbox slbConcepto) {
        //System.out.println("************btnAddCliked-Begin***************************");
        nomAsigConceptoDat = new NomAsigConceptoDat(new NomAsigConceptoDatPK(ssnRifEmpresa, -1, -1, -1), 0.00, 0.00, 0.00, null, null, null, null, null, null, null, null);
        // *Registro nuevo*                                                                              Cant  Mont  Porc  FC    Cal, CF, FP,   Ini,  Act   Obs1  Obs2 
    }  // btnAddClicked()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Command("btnRefrescar")
    @NotifyChange({"nomAsigConceptoDat", "footer"})   //  Good !!! 
    public void btnRefrescar(@BindingParam("codNomina") final Integer codNomina, @BindingParam("nroTrabajador") final Integer nroTrabajador, @BindingParam("codConcepto") final Integer codConcepto, @BindingParam("chbCalcular") Checkbox chbCalcular, @BindingParam("chbInicializable") Checkbox chbInicializable, @BindingParam("chbActivo") Checkbox chbActivo ) {
        //NomAsigConceptoDatPK nomAsigConceptoDatPK = new NomAsigConceptoDatPK(ssnRifEmpresa, ssnCodNomina, ssnNroTrabajador, ssnCodConcepto);
        NomAsigConceptoDatPK nomAsigConceptoDatPK = new NomAsigConceptoDatPK(ssnRifEmpresa, codNomina, nroTrabajador, codConcepto);
        nomAsigConceptoDat = new bean.controlador.concepto.NomAsigConceptoDatJpaController().findNomAsigConceptoDat(nomAsigConceptoDatPK);
        //NomAsigConceptoDat n = new bean.controlador.concepto.NomAsigConceptoDatJpaController().findNomAsigConceptoDat(nomAsigConceptoDatPK);
        //System.out.println("****DENTRO DE Refresh****Porc="+n.getPorcentaje()+"****frecCalc="+n.getFrecuenciaCalculo()+"***frecPago="+n.getFrecuenciaPago()+"*****CodFormula="+nomAsigConceptoDat.getCodFormula()+"**observa2="+n.getObservacion2()+" ***inicializable="+n.+" **"); 
        //  * Calcular concepto *
        String cadena = nomAsigConceptoDat.getCalcular().trim();  
        if ( cadena != null && cadena.equals("S") ) {
            chbCalcular.setChecked(Boolean.TRUE);
        } else {
            chbCalcular.setChecked(Boolean.FALSE);
        }
        // * Inicializable *
        cadena = nomAsigConceptoDat.getInicializable().trim();  
        if ( cadena != null && cadena.equals("S") ) {
            chbInicializable.setChecked(Boolean.TRUE);
        } else {
            chbInicializable.setChecked(Boolean.FALSE);
        }
        // * Activo * 
        cadena = nomAsigConceptoDat.getActivo().trim(); 
        if ( cadena != null && cadena.equals("S") ) {
            chbActivo.setChecked(Boolean.TRUE);
        } else {
            chbActivo.setChecked(Boolean.FALSE);
        }
    }  // btnRefrescar().  
    
    //--------------------------------------------------------------------------
    @Command("refreshChbCalcular")
    @NotifyChange({"nomAsigConceptoDat", "footer"})   //  Fine !!! 
    public void refreshChbCalcular(@BindingParam("chbCalcular") Checkbox chb ) {
        nomAsigConceptoDat.setCalcular( chb.isChecked() ? "S" : "N" );
    } // refreshChbCalcular()
    
    //--------------------------------------------------------------------------
    @Command("refreshChbInicializable")
    @NotifyChange({"nomAsigConceptoDat", "footer"})   //  Fine !!! 
    public void refreshChbInicializable(@BindingParam("chbInicializable") Checkbox chb ) {
        nomAsigConceptoDat.setInicializable( chb.isChecked() ? "S" : "N" );
    } // refreshChbInicializable()
    
    //--------------------------------------------------------------------------
    @Command("refreshChbActivo")
    @NotifyChange({"nomAsigConceptoDat", "footer"})   //  Fine !!! 
    public void refreshChbActivo(@BindingParam("chbActivo") Checkbox chb ) {
        nomAsigConceptoDat.setActivo( chb.isChecked() ? "S" : "N" );
    } // refreshChbCalcular()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //private void ajustarDatos(Selectbox slbNomina, Selectbox slbTrabajador, Selectbox slbConcepto, java.util.Date fechaNomina) {
    private void ajustarDatosOLD(String rifEmpresa, Selectbox slbNomina, Selectbox slbTrabajador, Selectbox slbConcepto) {
        // rifEmpresa: 
        nomAsigConceptoDat.getNomAsigConceptoDatPK().setRifEmpresa(rifEmpresa);
        // Nomina*
        String cadena = (String) slbNomina.getModel().getElementAt(slbNomina.getSelectedIndex());
        Integer codNomina = Integer.parseInt(cadena.substring(0, cadena.indexOf("-")));
        //System.out.println("codNomina=" + codNomina + "****");
        nomAsigConceptoDat.getNomAsigConceptoDatPK().setCodNomina(codNomina);
        // Trabajador*
        cadena = (String) slbTrabajador.getModel().getElementAt(slbTrabajador.getSelectedIndex());
        Integer trabajador = Integer.parseInt(cadena.substring(0, cadena.indexOf("-")));
        //System.out.println("trabajador=" + trabajador + "****");
        nomAsigConceptoDat.getNomAsigConceptoDatPK().setNroTrabajador(trabajador);
        // Concepto * 
        cadena = (String) slbConcepto.getModel().getElementAt(slbConcepto.getSelectedIndex());
        Integer concepto = Integer.parseInt(cadena.substring(0, cadena.indexOf("-")));
        //System.out.println("concepto=" + concepto + "****");
        nomAsigConceptoDat.getNomAsigConceptoDatPK().setCodConcepto(concepto);
        // ** Fecha **
        //System.out.println("fechaNomina=" + nomConceptoHistDat.getNomConceptoHistDatPK().getFechaNomina() + "****");
        //nomConceptoHistDat.getNomConceptoHistDatPK().setFechaNomina(fechaNomina);
        //System.out.println("************btnAddCliked-End***************************");
    }  // ajustarDatosOLD(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void ajustarDatos(String rifEmpresa, Integer codNomina, Integer nroTrabajador, Integer codConcepto) {
        // rifEmpresa: 
        nomAsigConceptoDat.getNomAsigConceptoDatPK().setRifEmpresa(rifEmpresa);
        // Nomina*
        //System.out.println("codNomina=" + codNomina + "****");
        nomAsigConceptoDat.getNomAsigConceptoDatPK().setCodNomina(codNomina);
        // Trabajador*
        nomAsigConceptoDat.getNomAsigConceptoDatPK().setNroTrabajador(nroTrabajador);
        // Concepto * 
        nomAsigConceptoDat.getNomAsigConceptoDatPK().setCodConcepto(codConcepto);
        // ** Fecha **
        //System.out.println("fechaNomina=" + nomConceptoHistDat.getNomConceptoHistDatPK().getFechaNomina() + "****");
        //nomConceptoHistDat.getNomConceptoHistDatPK().setFechaNomina(fechaNomina);
        //System.out.println("************btnAddCliked-End***************************");
        //    
        // * Frec. Calculo * 
        // Caused by: java.sql.SQLDataException: (conn=58) Data too long for column 'frecuenciaPago' at row 1 ?????
        String c = nomAsigConceptoDat.getFrecuenciaCalculo();
        /*
         if (c==null || c.isEmpty()) {
         System.out.println("Frecuencia de calculo es NULO????????????????");
         c="";  
         } else {
         System.out.println("*NO NULO* c="+c+"***c.substring(0,0)="+c.substring(0,0)+"***c.substring(0,1)="+c.substring(0,1)+"***");
         c=c.substring(0,0); 
         //System.out.println("*NO NULO*Frecuencia de calculo=*"+nomAsigConceptoDat.getFrecuenciaCalculo()+"*");
         //System.out.println("*NO NULO* c="+c+"*");
         }  
         nomAsigConceptoDat.setFrecuenciaCalculo(c);
         */
        //
        nomAsigConceptoDat.setFrecuenciaCalculo((c != null && !c.isEmpty() ? c.substring(0, 1) : ""));
        // * Frec. Pago * 
        c = nomAsigConceptoDat.getFrecuenciaPago();
        nomAsigConceptoDat.setFrecuenciaPago((c != null && !c.isEmpty() ? c.substring(0, 1) : ""));
        //
    }  // ajustarDatos(). 

    // =========================================================================
    // generar los getter y los setter
    // =========================================================================
    public NomAsigConceptoDat getNomAsigConceptoDat() {
        return nomAsigConceptoDat;
    }

    public void setNomAsigConceptoDat(NomAsigConceptoDat nomAsigConceptoDat) {
        this.nomAsigConceptoDat = nomAsigConceptoDat;
    }

}
