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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author henrypb
 */
public class ControladorEmpresaNew extends GenericForwardComposer implements IControladorEmpresa {

    private Textbox txtRif;
    private Textbox txtNombreEmpresa;
    private Textbox txtRazonSocial;
    private Textbox txtRamo;
    private Bandbox bbxPais;
    private Bandbox bbxEstado;
    private Textbox txtCiudad;
    private Textbox txtDireccion1;
    private Textbox txtDireccion2;
    private Textbox txtDireccion3;
    private Textbox txtEmail;
    private Textbox txtMision;
    private Textbox txtVision;
    private NomEmpresaDat entidad;
    private Button btnAdd;
    private Button btnEdit;
    private Button btnDel;
    private Button btnIni;
    private String rifEmpresa;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //binder = new AnnotateDataBinder(comp);
        //binder.bindBean("controller", this);   // vincular (bind) un atributo de nombre 'controller' usado en la pag con este controlador.  
        iniciar();
        //createModel();
    } // doAfterCompose(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
        rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
        if (rifEmpresa == null) {
            btnAdd.setDisabled(true);
            btnDel.setDisabled(true);
            btnIni.setDisabled(true);
        }
    } // iniciar()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void createModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    private Boolean datosValidos() {
        Boolean ok = Boolean.TRUE;
        String rif = txtRif.getText();
        String nombre = txtNombreEmpresa.getText();
        String razonSocial = txtRazonSocial.getText();
        if ((rif == null || rif.isEmpty()) || (nombre == null || nombre.isEmpty()) || (razonSocial == null || razonSocial.isEmpty())) {
            Messagebox.show("CAMPOS CLAVES CON VALORES nulos/vacios. ;=(", "ATENCION", Messagebox.OK, Messagebox.ERROR);
            ok = Boolean.FALSE;
        } else {
            NomEmpresaDat empresa= ( new NomEmpresaDatJpaController() ).findNomEmpresaDat(rif);  
            if ( empresa!=null ) {
                 Messagebox.show("REGISTRO DE CLAVE DUPLICADA ;=(","ATENCION",Messagebox.OK,Messagebox.ERROR); 
                 ok = Boolean.FALSE;                   
            }
        }
        return (ok);
    }  // datosValidos()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private NomEmpresaDat prepararDatos() {
        String rif = txtRif.getText();
        String nombre = txtNombreEmpresa.getText();
        String razonSocial = txtRazonSocial.getText();
        String ramo = txtRamo.getText();
        String pais = bbxPais.getText();
        String estado = bbxEstado.getText();
        String ciudad = txtCiudad.getText();
        String direccion1 = txtDireccion1.getText();
        String direccion2 = txtDireccion2.getText();
        String direccion3 = txtDireccion3.getText();
        String email = txtEmail.getText();
        String mision = txtMision.getText();
        String vision = txtVision.getText();
        NomEmpresaDat empresa = new NomEmpresaDat(rif, nombre, razonSocial, ramo, pais, estado, ciudad, direccion1, direccion2, direccion3, email, mision, vision);
        return (empresa);
    }  // prepararDatos()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnEdit() {
        if ( datosValidos() ) {
            Messagebox.show("CONFORME  ? ;=)", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                @Override
                public void onEvent(Event evento) throws Exception {
                    if (evento.getName().equals("onYes")) {
                        try {
                            entidad = prepararDatos();
                            (new NomEmpresaDatJpaController()).create(entidad);
                        } catch (Exception ex) {
                            Logger.getLogger(ControladorEmpresaNew.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }  // onEvent()  
            }); // MessageBox()
        }  // if datosValidos...
    } // onClick$btnEdit()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnIni() {
        Sessions.getCurrent().setAttribute("pagInclude", "inicio");
        Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");
    }

    public NomEmpresaDat getEntidad() {
        return entidad;
    }

    public void setEntidad(NomEmpresaDat entidad) {
        this.entidad = entidad;
    }


}
