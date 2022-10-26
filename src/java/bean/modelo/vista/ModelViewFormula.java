/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.modelo.entidad.NomFormulaDat;
import bean.modelo.entidad.NomFormulaDatPK;
import bean.servicio.ServicioAdmonFormulaConcepto;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author henrypb
 */
public class ModelViewFormula {

    private String ssnRifEmpresa;
    private Integer ssnCodNomina;
    private Integer ssnCodConcepto;
    private String ssnCodFormula;
    // 
    private NomFormulaDat selectedFormula;

    // Esta clase opera sin filtros. 
    // private VariableViewFilter variableViewFilter = new VariableViewFilter();  
    private List<NomFormulaDat> listaFormulas;

    //--------------------------------------------------------------------------
    @Init
    public void init() {    // Initialize
        ssnRifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
        ssnCodNomina = (Integer) Sessions.getCurrent().getAttribute("ssnCodNomina");
        ssnCodConcepto = (Integer) Sessions.getCurrent().getAttribute("ssnCodConcepto");
        ssnCodFormula = (String) Sessions.getCurrent().getAttribute("ssnCodFormula");
        //System.err.println("***rif="+ssnRifEmpresa+" codNomina="+ssnCodNomina+" **codConcepto="+ssnCodConcepto+" ***cFormula="+ssnCodFormula+" ***");
        listaFormulas = new ServicioAdmonFormulaConcepto().getListaFormulas(ssnRifEmpresa, ssnCodNomina, ssnCodConcepto, ssnCodFormula);
    }

    //--------------------------------------------------------------------------
    //  <listbox id="lbxFormula" model="@load(vm.formulaViewModel)" ... >
    //--------------------------------------------------------------------------
    public List<NomFormulaDat> getFormulaViewModel() {
        return (new ListModelList<NomFormulaDat>(listaFormulas));
    }

    //--------------------------------------------------------------------------
    //                 (get)_formulaViewModel())
    //                 Correctisimo ✔✔ 
    //--------------------------------------------------------------------------
    @Command("btnRefresh")
    @NotifyChange({"formulaViewModel", "footer"})  // *** Anotacion mandatoria ***
    public void btnRefresh() {
        ssnCodNomina = (Integer) Sessions.getCurrent().getAttribute("ssnCodNomina");
        ssnCodConcepto = (Integer) Sessions.getCurrent().getAttribute("ssnCodConcepto");
        ssnCodFormula = (String) Sessions.getCurrent().getAttribute("ssnCodFormula");
        //System.err.println("Dentro del metodo Refresh***rif="+ssnRifEmpresa+" codNomina="+ssnCodNomina+" **codConcepto="+ssnCodConcepto+" ***cFormula="+ssnCodFormula+" ***");
        listaFormulas = new ServicioAdmonFormulaConcepto().getListaFormulas(ssnRifEmpresa, ssnCodNomina, ssnCodConcepto, ssnCodFormula);
    }  // btnRefresh().   

    //--------------------------------------------------------------------------
    //                 (get)_formulaViewModel())
    //                 Correctisimo ✔✔ 
    //--------------------------------------------------------------------------
    @Command("btnRefreshNEW")
    @NotifyChange({"formulaViewModel", "footer"})  // *** Anotacion mandatoria ***
    public void btnRefreshNEW() {
        if (selectedFormula != null) {
            Integer codNomina = selectedFormula.getNomFormulaDatPK().getCodNomina();
            String codFormula = selectedFormula.getNomFormulaDatPK().getCodFormula();
            Integer codConcepto = selectedFormula.getNomFormulaDatPK().getCodConcepto();
             //ssnCodNomina = (Integer) Sessions.getCurrent().getAttribute("ssnCodNomina");
            //ssnCodConcepto = (Integer) Sessions.getCurrent().getAttribute("ssnCodConcepto");
            //ssnCodFormula = (String) Sessions.getCurrent().getAttribute("ssnCodFormula");
            //System.err.println("Dentro del metodo Refresh***rif="+ssnRifEmpresa+" codNomina="+ssnCodNomina+" **codConcepto="+ssnCodConcepto+" ***cFormula="+ssnCodFormula+" ***");
            listaFormulas = new ServicioAdmonFormulaConcepto().getListaFormulas(ssnRifEmpresa, codNomina, codConcepto, codFormula);
        }  // if (selectedFormula != null)
    }  // btnRefreshNEW().   

    //--------------------------------------------------------------------------
    // NOTA: se tuvo que resolver directamente via el controlador
    //       para poder pasar el 'Listbox' como parametro y hacer un 
    //       refrescamiento directo desde el Window popUp modal utilizando 
    //       la tecnica 'ListItemrenderer'.     
    //--------------------------------------------------------------------------
    @Command("btnEdit")
    public void btnEdit() {
        if (selectedFormula != null) {
            //System.out.println("ATENCION, rec seleccionado = " + selectedFormula.getFormula() + "****");
            //new ControladorDefFormula().editar(selectedFormula);
            //System.out.println("metodo new ControladorDefFormula().editar(selectedFormula) EN PROGRESO");  
        }
    }  // btnEdit().  

    //--------------------------------------------------------------------------
    @Command("btnDel")
    @NotifyChange({"formulaViewModel", "footer"})  // *** Anotacion mandatoria para afectar el componente ***
    public void btnDel() {
        if (selectedFormula != null) {
            Messagebox.show("ELIMINAR REGISTRO SELECCIONADO ?", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {

                @Override
                public void onEvent(Event e) throws Exception {
                    if (e.getName().equals("onYes")) {
                        Integer codNomina = selectedFormula.getNomFormulaDatPK().getCodNomina();
                        String codFormula = selectedFormula.getNomFormulaDatPK().getCodFormula();
                        Integer codConcepto = selectedFormula.getNomFormulaDatPK().getCodConcepto();
                        String accion = selectedFormula.getNomFormulaDatPK().getAccion();
                        Integer correlativo = selectedFormula.getNomFormulaDatPK().getCorrelativo();
                        //System.out.println("***codNom="+codNomina+"***codConcepto="+codConcepto+"****correlativo="+correlativo+"*****");
                        NomFormulaDatPK pk = new NomFormulaDatPK(ssnRifEmpresa, codNomina, codFormula, codConcepto, accion, correlativo);
                        new bean.controlador.concepto.NomFormulaDatJpaController().destroy(pk);
                        // * Refrescar * ????  Revisar ????
                        listaFormulas = new ServicioAdmonFormulaConcepto().getListaFormulas(ssnRifEmpresa, codNomina, codConcepto, codFormula);
                        Messagebox.show("REGISTRO ELIMINADO");
                        //
                        //Sessions.getCurrent().setAttribute("ssnCodNomina",selectedFormula.getNomFormulaDatPK().getCodNomina());
                        //Sessions.getCurrent().setAttribute("ssnCodFormula",selectedFormula.getNomFormulaDatPK().getCodFormula());
                        //Sessions.getCurrent().setAttribute("ssnCodConcepto",selectedFormula.getNomFormulaDatPK().getCodConcepto());
                        //
                        btnRefresh();   // NO ESTA FUNCIONANDO ????? Revisar ????
                    }

                }
            });  // Messagebox().  
        }  //  if ( selectedFormula!=null )
    }  // btnDel(). 

    //--------------------------------------------------------------------------
    //  <listbox id="lbxFormula" ... selectedItem="@bind(selectedFormula)" >
    //-------------------------------------------------------------------------
    public NomFormulaDat getSelectedFormula() {
        return selectedFormula;
    }

    //--------------------------------------------------------------------------
    public void setSelectedFormula(NomFormulaDat selectedFormula) {
        this.selectedFormula = selectedFormula;
    }

}
