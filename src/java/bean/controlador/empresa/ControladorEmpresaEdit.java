/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.empresa;

import bean.interfaz.IControladorEmpresa;
import bean.modelo.entidad.NomEmpresaDat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author henrypb
 */
public class ControladorEmpresaEdit extends GenericForwardComposer implements IControladorEmpresa {

    //private static final String RIF = "J-1234567-7";
    private AnnotateDataBinder binder;   // binder: vinculador
    private NomEmpresaDat entidad;
    private Button btnAdd;
    private Button btnEdit;
    private Button btnDel;
    private Button btnIni;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        binder = new AnnotateDataBinder(comp);
        binder.bindBean("controller", this);   // vincular (bind) un atributo de nombre 'controller' usado en la pag con este controlador.  
        iniciar();
        createModel();
    } // doAfterCompose(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
   
    } // iniciar()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onCreate(Event event) {
        binder.loadAll();
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void createModel() {
        NomEmpresaDatJpaController nomEmpJpaCtlr = new NomEmpresaDatJpaController();
        String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa"); 
        if (rifEmpresa == null) {
            rifEmpresa = "";
        }
        entidad = nomEmpJpaCtlr.findNomEmpresaDat(rifEmpresa);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void onChange() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void onNew() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void onSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnEdit() {
        Messagebox.show("CONFORME  ? ;=)", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event evento) throws Exception {
                if (evento.getName().equals("onYes")) {
                    binder.saveAll();
                    try {
                            (new NomEmpresaDatJpaController()).edit(entidad);   // Actualizar.
                    } catch (Exception ex) {
                        Logger.getLogger(ControladorEmpresaEdit.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    binder.loadAll();
                }
            }  // onEvent()  
        }); // MessageBox()
    } // onClick$btnEdit()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnIni() {
        Sessions.getCurrent().setAttribute("pagInclude", "inicio");
        Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");
    }

    public AnnotateDataBinder getBinder() {
        return binder;
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public NomEmpresaDat getEntidad() {
        return entidad;
    }

    public void setEntidad(NomEmpresaDat entidad) {
        this.entidad = entidad;
    }

}
