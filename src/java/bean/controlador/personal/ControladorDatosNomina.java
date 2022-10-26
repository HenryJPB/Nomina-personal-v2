/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.personal;

import bean.modelo.entidad.NomTrabajador00Dat;
import bean.modelo.entidad.NomTrabajador00DatPK;
import bean.modelo.entidad.NomTrabajador01Dat;
import bean.modelo.entidad.NomTrabajador01DatPK;
import bean.servicio.ServicioAdmonTrabajador;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

/**
 *
 * @author henrypb
 */
public class ControladorDatosNomina extends GenericForwardComposer {

    private Label lblCI, lblNroTrab, lblNombre;
    private Button btnIni, btnReg, btnAdd, btnEdit;
    private Checkbox chbMarcaTarjeta;
    private Window winSelectContrato;   
  

    //--------------------------------------------------------------------------
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // Instrucciones del programador: 
        /*
         LocalDate hoy = LocalDate.now();  
         java.util.Date hoy = new java.util.Date(); 
         LocalDate lHoy = hoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();  
         //long edad = ChronoUnit.YEARS.between(hoy,hoy);
         System.out.println("****LocalDate hoy="+lHoy+"****"); 
         int edad = (int) ChronoUnit.YEARS.between(lHoy, lHoy); 
         lblEdad.setValue(Long.toString(edad));
         */
        /*
         if (validarUsuario()) {
         iniciar();
         }
         */
        //chbMarcaTarjeta.isChecked()
        iniciar();
    }  // doAfterCompose()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
        Integer ssnNroTrabSeleccionado = (Integer) Sessions.getCurrent().getAttribute("nroTrabSeleccionado");
        String ssnRifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
        //
        //List<String> l = new bean.controlador.nomina.NomTiposNominaDatJpaController().getTiposNomina(ssnRifEmpresa);  
        //System.err.println("*TIPOS NOMINA(lista)*");
        //for ( String s : l ) {
        //    System.err.println(s);
        //}
        //
        //String[] aContratos = l.toArray(new String[l.size()]);  
        //
        if (ssnNroTrabSeleccionado >= 0 && (ssnRifEmpresa != null && !ssnRifEmpresa.isEmpty())) {
            NomTrabajador00DatPK clave1 = new NomTrabajador00DatPK(ssnRifEmpresa, ssnNroTrabSeleccionado);
            NomTrabajador00Dat trabajador = new NomTrabajador00DatJpaController().findNomTrabajador00Dat(clave1);
            if (trabajador != null) {
                String cedula = trabajador.getNacionalidad() + "-" + trabajador.getCedulaID();
                lblCI.setValue(cedula);
                Integer nroTrab = trabajador.getNomTrabajador00DatPK().getNroTrabajador();
                lblNroTrab.setValue(nroTrab.toString());
                String nombre = trabajador.getNombre1() + " " + trabajador.getApellido1();
                lblNombre.setValue(nombre);
                if (new ServicioAdmonTrabajador().existeTrabNomina(ssnRifEmpresa, ssnNroTrabSeleccionado)) {
                    // btnEdit.setDisabled(Boolean.FALSE);
                    NomTrabajador01DatPK clave2 = new NomTrabajador01DatPK(ssnRifEmpresa, ssnNroTrabSeleccionado);
                    NomTrabajador01Dat nomTrabajador01Dat = new NomTrabajador01DatJpaController().findNomTrabajador01Dat(clave2);
                    //System.out.println("***Ficha en nomina="+nomTrabajador01Dat.getNomTrabajador01DatPK().getNroTrabajador()+"**Marca Tarjeta="+nomTrabajador01Dat.getMarcaTarjeta()+"**"); 
                    if (nomTrabajador01Dat.getMarcaTarjeta() != null && nomTrabajador01Dat.getMarcaTarjeta().equals("S")) {
                        chbMarcaTarjeta.setChecked(Boolean.TRUE);
                    } else {
                        chbMarcaTarjeta.setChecked(Boolean.FALSE);
                    }
                }
            }  // trabajador != null
        } // nroTrabSeleccionado >= 0 && (rifEmpresa  ...
    }  //  iniciar().          

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnReg() {
        String pagActiva = "../VISTA_PERSONAL/pagPersonal.zul";
        Sessions.getCurrent().setAttribute("pagInclude", pagActiva);
        Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnIni() {
        Sessions.getCurrent().setAttribute("pagInclude", "inicio");
        Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnAdd() {
        winSelectContrato.doPopup();
    } // btnAdd()

}
