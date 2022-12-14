/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.personal;

import bean.modelo.entidad.NomTrabajador00Dat;
import bean.modelo.entidad.NomTrabajador00DatPK;
import bean.modelo.entidad.Trabajador;
import bean.utilitario.libreria.LibreriaHP;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

/**
 *
 * @author henrypb
 */
public class ControladorPersonal extends GenericForwardComposer {

    private Button btnAdd;
    private Button btnEdit;
    private Button btnDel;
    private Button btnRfr;
    private Button btnIni;  
    private Listbox lbxTrabajadores;
    private final String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");  

    enum enumAtributosTrabajador {
        FOTO, NRO_TRAB, CEDULA, NOMBRE, APELLIDO
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // Instrucciones del programador: 
        /*
         if (validarUsuario()) {
         iniciar();
         }
         */
    }  // doAfterCompose().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnEdit() {
        if ( lbxTrabajadores.getSelectedItem()!=null ) {
            String pagActiva = "../VISTA_PERSONAL/pagPersonalEdit.zul";  
            Sessions.getCurrent().setAttribute("pagInclude",pagActiva); 
            Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");  // redirigir hacia la pag q gestiona el menu de la aplicacion
        }
    }  // onClick$btnEdit(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnIni() {
        Sessions.getCurrent().setAttribute("pagInclude","inicio"); 
        Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onSelect$lbxTrabajadores(Event evento) throws IOException {
        Trabajador trabajador = getSeleccion();
        //* Validar datos trabajadorSeleccionado **
        //System.out.println("***Antes.NroTrab="+trabajador.getNroTrabajador()+"****Nombre y apellido="+trabajador.getNombre()+" "+trabajador.getApellido()+"***");
        //
        //String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");  
        Sessions.getCurrent().setAttribute("nroTrabSeleccionado", trabajador.getNroTrabajador());
        Integer nroTrabSeleccionado = trabajador.getNroTrabajador();  
        if (nroTrabSeleccionado != null && nroTrabSeleccionado>0) {
            // imgFoto.setSrc(RUTA + nroTrabSeleccionado + ".jpg");   // old dise??o 
            NomTrabajador00Dat nomTrabajador00Dat = new bean.controlador.personal.NomTrabajador00DatJpaController().findNomTrabajador00Dat( new NomTrabajador00DatPK(rifEmpresa,nroTrabSeleccionado) );
            BufferedImage bImagen = null;  
            if (nomTrabajador00Dat.getFoto()!=null) {
                bImagen = new LibreriaHP().getImagen(nomTrabajador00Dat.getFoto());
            }
            Sessions.getCurrent().setAttribute("imagenFoto", bImagen);
        }
        //
        //EventQueues.lookup("onEventGetNroTrab", EventQueues.DESKTOP,true).publish(new Event("eventGetNroTrab",null,nroTrab) );  // ??? -> sujeto a revision.
        //
        // ************************Probando***********************
        //List<NomTrabajador00Dat> lista = new NomTrabajador00DatJpaController().getlistaTrabxEmpresa(rifEmpresa);  
        //for ( NomTrabajador00Dat n : lista ) {
        //    System.out.println("Ficha="+n.getNomTrabajador00DatPK().getNroTrabajador()+"Nombre="+n.getNombre1()+" "+n.getApellido1()+"****");  
        //}   // Positiiiiiiiiiiiiiiiiiiivooooooooooooooooooo..!!! ( Bqto, 13 septiembre 2018 ).
    }  // onSelect$lbxTrabajadores().

    //--------------------------------------------------------------------------
    // Doc convertir una ZK Image content to BufferedImage:
    // http://forum.zkoss.org/question/31369/process-a-image/
    //--------------------------------------------------------------------------
    private Trabajador getSeleccion() throws IOException {
        Listitem listitem = lbxTrabajadores.getSelectedItem();
        List celdas = listitem.getChildren();
        // 
        // Foto
        Listcell listcell= (Listcell) celdas.get(enumAtributosTrabajador.FOTO.ordinal());  
        Image imagenWidget = (Image) listcell.getFirstChild();  
        //BufferedImage imagenFoto = (BufferedImage) imageWidGet.getContent();  // Error ..??   
        //BufferedImage imagenFoto = ImageIO.read(new File(imagenWidGet.getContent())); 
        Media media  = imagenWidget.getContent();  
        BufferedImage imagenFoto = null;  
        if (media!=null) {
             imagenFoto = ImageIO.read(media.getStreamData());
        }     
        //byte[] bFoto = new LibreriaHP().convertirImagen(imagenFoto);  
        //BufferedImage imagenFoto = ImageIO.read(imageWidGet.getContent());  
        //
        // nroTrab: 
        listcell = (Listcell) celdas.get(enumAtributosTrabajador.NRO_TRAB.ordinal());
        Integer nroTrab = Integer.parseInt(listcell.getLabel());
        // cedula:  
        listcell = (Listcell) celdas.get(enumAtributosTrabajador.CEDULA.ordinal());
        String cedula = listcell.getLabel();
        // nombre: 
        listcell = (Listcell) celdas.get(enumAtributosTrabajador.NOMBRE.ordinal());
        String nombre = listcell.getLabel();
        // apellido:
        listcell = (Listcell) celdas.get(enumAtributosTrabajador.APELLIDO.ordinal());
        String apellido = listcell.getLabel();
        Trabajador trabajador = new Trabajador(imagenFoto, nroTrab, cedula, nombre, apellido);
        return (trabajador);
    }  // getSeleccion(). 

}
