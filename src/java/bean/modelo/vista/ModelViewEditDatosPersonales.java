/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.controlador.personal.NomTrabajador00DatJpaController;
import bean.modelo.entidad.NomTrabajador00Dat;
import bean.modelo.entidad.NomTrabajador00DatPK;
import bean.utilitario.libreria.LibreriaHP;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author henrypb
 */
public class ModelViewEditDatosPersonales {

    Integer nroTrabajador = (Integer) Sessions.getCurrent().getAttribute("nroTrabSeleccionado");
    String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    NomTrabajador00Dat nomTrabajador00Dat = new NomTrabajador00Dat();
    //Integer nroTrabajador = 0;

    //--------------------------------------------------------------------------
    // Interactive between multiple ZUL pages ( Bqto, 21/08/2018 ) no reconoce 
    // el evento. Mi ejemplo "onEventGetNroTrab" ????? --> Trabajaremos con variables
    // de session. ( Sujeto a Revision ). 
    // REMENBER: en teoria EventQueues.lookup funciona entre controladores ????
    //--------------------------------------------------------------------------
    //{
    //    System.out.println("DENTRO de ModelViewDatPerso*****de Chequear EventQueue**");
    //    EventQueues.lookup("onEventGetNroTrab", EventQueues.DESKTOP, true).subscribe((Event evento) -> {
    //        nroTrabajador = (Integer) evento.getData();
    //    });  // EventQueue()
    //}
    // 
    //{
    //    NomTrabajador00DatPK clave = new NomTrabajador00DatPK(rifEmpresa, nroTrabajador);
    //    if (nroTrabajador >= 0) {
    //        nomTrabajador00Dat = new bean.controlador.personal.NomTrabajador00DatJpaController().findNomTrabajador00Dat(clave);
    //    } else {
    //        nomTrabajador00Dat = new NomTrabajador00Dat(clave, null, "", "", "", "", "", "", "", "", "", null, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
    //    }
    //}
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Init
    public void init() {
        if (nroTrabajador >= 0) {
            NomTrabajador00DatPK clave = new NomTrabajador00DatPK(rifEmpresa, nroTrabajador);
            nomTrabajador00Dat = new bean.controlador.personal.NomTrabajador00DatJpaController().findNomTrabajador00Dat(clave);
        } else {
            //nomTrabajador00Dat = new NomTrabajador00Dat( new NomTrabajador00DatPK(rifEmpresa,-1), null, "", "", "", "", "", "", "", "", "", null, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
            nuevoRegistro();  
        }
    } // init. 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void nuevoRegistro() {
        nomTrabajador00Dat = new NomTrabajador00Dat( new NomTrabajador00DatPK(rifEmpresa,-1), null, "", "", "", "", "", "", "", "", "", null, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
    } // nuevoRegistro().
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void ajustarDatos() throws IOException {
        // nomTrabajador00Dat.setFoto(new ControladorDatosPersonales().getBytesFoto());  // Eroro JavaNullPoint.  
        BufferedImage imagenFoto = (BufferedImage) Sessions.getCurrent().getAttribute("imagenFoto");  // Insumo del ControladorDatosPersonales.  
        byte[] bytes = null;
        if (imagenFoto != null) {
            bytes = new LibreriaHP().convertirImagen(imagenFoto);
        }
        nomTrabajador00Dat.setFoto(bytes);
    }  // prepararDatos(). 
    
    
    //-----------------------------------------------------------------------------------
    // @NotifyChange({"(i)", "footer)    // para (i) = [get]nomTrabajador00Dat() // check getter/setter
    //-----------------------------------------------------------------------------------
    @Command("btnAddClicked")
    @NotifyChange({"nomTrabajador00Dat", "footer"})  // *** Anotacion mandatoria para display valores de los atributos***
    public void btnAddClicked() {
        nuevoRegistro();   
    } // btnAddClicked()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Command("btnEditClicked")
    public void btnEditCliked() {
        Messagebox.show("CONFORME ?", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event evento) throws Exception {
                //if (evento.getName().equals("onYes") && datosConforme()) {
                if (evento.getName().equals("onYes")) {
                    try {
                        ajustarDatos();
                        //System.out.println("NOMBRE:" + nomTrabajador00Dat.getNombre1() + "****");
                        (new NomTrabajador00DatJpaController()).edit(nomTrabajador00Dat); // Actualizar reg BD.
                    } catch (Exception ex) {
                        Logger.getLogger(ModelViewEditDatosPersonales.class.getName()).log(Level.SEVERE, null, ex);
                    }  // try  // try
                }  // if
            }  // onEvent()  
        }); // MessageBox()
    } // btnEditCliked()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void switchInformacion(NomTrabajador00Dat fx, NomTrabajador00Dat provDatos) {
        fx.setNacionalidad(provDatos.getNacionalidad());
        fx.setSexo(provDatos.getSexo());
        fx.setEdoCivil(provDatos.getEdoCivil());
        fx.setFechaNac(provDatos.getFechaNac());
        fx.setTelefonoHab1(provDatos.getTelefonoHab1());
        fx.setTelefonoHab2(provDatos.getTelefonoHab2());
        fx.setTelefonoMovil1(provDatos.getTelefonoMovil1());
        fx.setTelefonoMovil2(provDatos.getTelefonoMovil2());
        fx.setEmail1(provDatos.getEmail1());
        fx.setEmail2(provDatos.getEmail2());
        fx.setDireccionHab1(provDatos.getDireccionHab1());
        fx.setDireccionHab2(provDatos.getDireccionHab2());
        fx.setDireccionHab3(provDatos.getDireccionHab3());
        fx.setParroquiaHab(provDatos.getParroquiaHab());
        fx.setMunicipioHab(provDatos.getMunicipioHab());
        fx.setCodigoPostalHab(provDatos.getCodigoPostalHab());
        fx.setCiudadHab(provDatos.getCiudadHab());
        fx.setEstadoHab(provDatos.getEstadoHab());
        fx.setPaisHab(provDatos.getPaisHab());
        fx.setParroquiaNac(provDatos.getParroquiaNac());
        fx.setMunicipioNac(provDatos.getMunicipioNac());
        fx.setCodigoPostalNac(provDatos.getCodigoPostalNac());
        fx.setCiudadNac(provDatos.getCiudadNac());
        fx.setEstadoNac(provDatos.getEstadoNac());
        fx.setPaisNac(provDatos.getPaisNac());
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Command("heredaClicked")
    public void heredaClicked(@BindingParam("nomTrabajador00Dat") NomTrabajador00Dat fx, @BindingParam("codTrab") Integer codTrab) {
        // i.) if codTrab != null entonces:  
        //  ..i.1.) Get de la BD datos de la ficha del trab = findEntity(codTrab); y 
        //  ..i.2.) Rellenar los campos fx.SetNombre1=trab.getNombre1(), fx.apellido1=trab.getApellido1,..fx.paisHab=trab.getpaisHab() para el nuevo record
        if (codTrab != null && codTrab > 0) {
            NomTrabajador00Dat provDatos = (new NomTrabajador00DatJpaController()).findNomTrabajador00Dat(new NomTrabajador00DatPK(rifEmpresa, codTrab));
            if (provDatos != null) {
                //fx.setNombre1("*PROBANDO*");
                switchInformacion(fx, provDatos);
                BindUtils.postNotifyChange(null, null, fx, "*");
                //System.out.println("fx.Nombre1=" + fx.getNombre1() + "***Herada del trabajador=" + codTrab + "**Ejemplo. ESTADO NAC=*" + fx.getEstadoNac() + "***");
            } // if interno 
        }  // if externo 
    }  // tbbHeredaClicked(). 

    //--------------------------------------------------------------------------
    // ?? Revisar esto ????
    //--------------------------------------------------------------------------
    /*
    public NomTrabajador00Dat getNomTrabajador00DatViewModel() {
        return (nomTrabajador00Dat);
    }
    */   

    public NomTrabajador00Dat getNomTrabajador00Dat() {
        return nomTrabajador00Dat;
    }

    public void setNomTrabajador00Dat(NomTrabajador00Dat nomTrabajador00Dat) {
        this.nomTrabajador00Dat = nomTrabajador00Dat;
    }

}
