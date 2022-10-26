/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servicio;

import bean.controlador.personal.NomTrabajador00DatJpaController;
import bean.controlador.personal.NomTrabajador01DatJpaController;
import bean.interfaz.IServicioAdmonTrabajador;
import bean.modelo.entidad.NomTrabajador00Dat;
import bean.modelo.entidad.NomTrabajador01Dat;
import bean.modelo.entidad.NomTrabajador01DatPK;
import bean.modelo.entidad.Trabajador;
import bean.modelo.entidad.TrabajadorView;
import bean.modelo.vista.TrabajadorViewFilter;
import bean.modelo.vista.ViewTrabajadorViewFilter;
import bean.utilitario.libreria.LibreriaHP;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.zkoss.zk.ui.Sessions;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonTrabajador implements IServicioAdmonTrabajador {

    //private static final String RIF = "J-1234567-7";
    //private static String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    //private static List<NomTrabajador00Dat> listaTrabajadores = (new NomTrabajador00DatJpaController()).findNomTrabajador00DatEntities();
    //private static List<NomTrabajador00Dat> listaTrabajadores = new ArrayList<NomTrabajador00Dat>();  
    //private static NomConfigDat nomConfigDat = ( new NomConfigDatJpaController() ).findNomConfigDat(RIF); 
    //private static String ruta = nomConfigDat.getRutaAbsFoto();     // Esta instruccion aqui genera errores en el modelViewTrabajadores
    private static List<Trabajador> listaResumenTrabajadores = new ArrayList<Trabajador>();   /* Condicion necesaria: tiene que se atributos/variables de ambito GLOBAL */

    private static List<TrabajadorView> listaTrabView = new ArrayList<TrabajadorView>();    /* Condicion necesaria: tiene que se atributos/variables de ambito GLOBAL */

    //--------------------------------------------------------------------------
    // static
    //--------------------------------------------------------------------------
    private static List<Trabajador> getListaResumidaOLD(List<NomTrabajador00Dat> lista) {
        final String RUTA = "../IMAGENES/PERSONAL/";
        //NomConfigDat config = ( new NomConfigDatJpaController() ).findNomConfigDat(RIF); 
        //String ruta = config.getRutaAbsFoto();     // Esta instruccion aqui genera errores en el modelView del Listbox ????
        //String ruta = RUTA;  
        List<Trabajador> listaResumen = new ArrayList<Trabajador>();
        String foto = null;
        String cedula = null;
        String nombre = null;
        String apellido = null;
        Trabajador trabajador = null;
        //String rutaAux = getRutaFoto();    // NO FUNCIONA -> genera error en el ModelView init del Listbox ???????  
        for (NomTrabajador00Dat nomTrabajador : lista) {
            foto = RUTA + nomTrabajador.getNomTrabajador00DatPK().getNroTrabajador() + ".jpg";
            //System.out.println("**** ruta="+ruta+"****rutaAbs registrada="+nomConfigDat.getRutaAbsFoto()+"***");
            cedula = nomTrabajador.getNacionalidad() + nomTrabajador.getCedulaID();
            nombre = nomTrabajador.getNombre1() + " " + nomTrabajador.getNombre2();
            apellido = nomTrabajador.getApellido1() + " " + nomTrabajador.getApellido2();
            trabajador = new Trabajador(foto, nomTrabajador.getNomTrabajador00DatPK().getNroTrabajador(), cedula, nombre, apellido);
            listaResumen.add(trabajador);
        } // for
        return (listaResumen);
    } // getListaResumidaOLD().

    //--------------------------------------------------------------------------
    // static
    //--------------------------------------------------------------------------
    private static List<Trabajador> getListaResumidaOLD2(List<NomTrabajador00Dat> lista) {
        List<Trabajador> listaResumen = new ArrayList<Trabajador>();
        BufferedImage foto = null;
        String cedula = null;
        String nombre = null;
        String apellido = null;
        Trabajador trabajador = null;
        String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
        //String rutaAux = getRutaFoto();    // NO FUNCIONA -> genera error en el ModelView init del Listbox ???????  
        for (NomTrabajador00Dat nomTrabajador00Dat : lista) {
            //System.out.println("rif Empresa="+rifEmpresa+"****rifEmpresa Trab="+nomTrabajador00Dat.getNomTrabajador00DatPK().getRifEmpresa()+"*****"); 
            if (nomTrabajador00Dat.getNomTrabajador00DatPK().getRifEmpresa().contains(rifEmpresa)) {
                //foto = RUTA+nomTrabajador.getNroTrabajador()+".jpg"; 
                if (nomTrabajador00Dat.getFoto() != null) {
                    foto = new LibreriaHP().getImagen(nomTrabajador00Dat.getFoto());
                }
                //System.out.println("**** ruta="+ruta+"****rutaAbs registrada="+nomConfigDat.getRutaAbsFoto()+"***");
                cedula = nomTrabajador00Dat.getNacionalidad() + nomTrabajador00Dat.getCedulaID();
                nombre = nomTrabajador00Dat.getNombre1() + " " + nomTrabajador00Dat.getNombre2();
                apellido = nomTrabajador00Dat.getApellido1() + " " + nomTrabajador00Dat.getApellido2();
                trabajador = new Trabajador(foto, nomTrabajador00Dat.getNomTrabajador00DatPK().getNroTrabajador(), cedula, nombre, apellido);
                listaResumen.add(trabajador);
            }
        } // for
        return (listaResumen);
    } // getListaResumidaOLD2().   

    //--------------------------------------------------------------------------
    // static
    //--------------------------------------------------------------------------
    private static List<Trabajador> getListaResumida(List<NomTrabajador00Dat> lista) {
        List<Trabajador> listaTrabResumen = new ArrayList<Trabajador>();
        BufferedImage foto = null;
        String cedula = null;
        String nombre = null;
        String apellido = null;
        Trabajador trabajador = null;
        for ( NomTrabajador00Dat nomTrabajador00Dat : lista ) {
            //foto = RUTA+nomTrabajador.getNroTrabajador()+".jpg"; 
            //System.out.println("****Trab="+nomTrabajador00Dat.getNomTrabajador00DatPK().getNroTrabajador()+"*****");
            if (nomTrabajador00Dat.getFoto() != null) {
                foto = new LibreriaHP().getImagen(nomTrabajador00Dat.getFoto());
                //System.out.println("****Trab="+nomTrabajador00Dat.getNomTrabajador00DatPK().getNroTrabajador()+"****FOTO NO NULA***");
            } else { 
                foto = null;  
                //System.out.println("****Trab="+nomTrabajador00Dat.getNomTrabajador00DatPK().getNroTrabajador()+"****FOTO NULA***");
            }
            //System.out.println("**** ruta="+ruta+"****rutaAbs registrada="+nomConfigDat.getRutaAbsFoto()+"***");
            cedula = nomTrabajador00Dat.getNacionalidad() + nomTrabajador00Dat.getCedulaID();
            nombre = nomTrabajador00Dat.getNombre1() + " " + nomTrabajador00Dat.getNombre2();
            apellido = nomTrabajador00Dat.getApellido1() + " " + nomTrabajador00Dat.getApellido2();
            trabajador = new Trabajador(foto, nomTrabajador00Dat.getNomTrabajador00DatPK().getNroTrabajador(), cedula, nombre, apellido);
            listaTrabResumen.add(trabajador);
        } // for
        return (listaTrabResumen);
    } // getListaResumida().   

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //private static String getRutaFoto() {
    //    return ((new NomConfigDatJpaController()).findNomConfigDat(rifEmpresa)).getRutaAbsFoto();
    //}
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public static List<Trabajador> getTrabajadores() {
        String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
        List<NomTrabajador00Dat> listaTrab = new NomTrabajador00DatJpaController().getlistaTrab(rifEmpresa);
        //List<NomTrabajador00Dat> listaTrabajadores = (new NomTrabajador00DatJpaController()).findNomTrabajador00DatEntities(); 
        //
        listaResumenTrabajadores = getListaResumida(listaTrab);
        // Probando ? :=>  Correcto :)   .!!!
        /*
         for (int i=0;i<listaResumenTrabajadores.size();i++) {
         Trabajador trab = listaResumenTrabajadores.get(i);  
         System.out.println("listaResTrab(i)=> foto="+trab.getFoto()+"**nombre="+trab.getNombre()+"**apellido="+trab.getApellido()+"***");  
         }
         */
        return (listaResumenTrabajadores);
    }  // getTrabajadores().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public static Trabajador[] getAllTrabajadoresArray() {
        return listaResumenTrabajadores.toArray(new Trabajador[listaResumenTrabajadores.size()]);
    } // getAllTrabajadoresArray().

    //--------------------------------------------------------------------------
    /*
     List<NomlView> someTrabajadores = new ArrayList<NomlView>();
     String dep = nomlViewFilter.getDepartamento().toLowerCase();
     String nro = nomlViewFilter.getNroEmpleado().toLowerCase();
     String nom = nomlViewFilter.getNombre().toLowerCase();
     for (Iterator<NomlView> i = listaTrabajadores.iterator(); i.hasNext();) {
     NomlView tmp = i.next();
     if (tmp.getDepartamento().toLowerCase().contains(dep)
     && tmp.getNroEmpleado().toLowerCase().contains(nro)
     && tmp.getNombre().toLowerCase().contains(nom)) {
     someTrabajadores.add(tmp);
     }
     }
     return (someTrabajadores);
     */
    //--------------------------------------------------------------------------
    public static List<Trabajador> getFilterTrabajadores(TrabajadorViewFilter trabajadorFilter) {
        List<Trabajador> someTrabajador = new ArrayList<Trabajador>();
        String nroTrabS = trabajadorFilter.getNroTrabajador().toLowerCase();
        String cedula = trabajadorFilter.getCedulaID().toLowerCase();
        String nombre = trabajadorFilter.getNombre().toLowerCase();
        String apellido = trabajadorFilter.getApellido().toLowerCase();
        //System.out.println("Antes de iterar..., listaResTrab.size="+listaResumenTrabajadores.size()+"*"); 
        for (Iterator<Trabajador> i = listaResumenTrabajadores.iterator(); i.hasNext();) {
            //for (Iterator<Trabajador> i = (getTrabajadores()).iterator(); i.hasNext();) {
            Trabajador tmp = i.next();
            //if (tmp.getNroTrabajador().compareTo(nroTrab) == 0
            if (tmp.getNroTrabajador().toString().toLowerCase().contains(nroTrabS)
                    && tmp.getCedulaID().toLowerCase().contains(cedula)
                    && tmp.getNombre().toLowerCase().contains(nombre)
                    && tmp.getApellido().toLowerCase().contains(apellido)) {
                someTrabajador.add(tmp);
            } // if
        }  // for.  
        return (someTrabajador);
    }  // getFilterTrabajadores().  

    //--------------------------------------------------------------------------
    public static List<TrabajadorView> getTrabajadoresView() {
        String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
        listaTrabView = new NomTrabajador01DatJpaController().getListaTrab(rifEmpresa);
        return (listaTrabView);
    }  // getTrabajadoresView().  

    //--------------------------------------------------------------------------
    public static TrabajadorView[] getAllTrabajadoresViewArray() {
        return listaTrabView.toArray(new TrabajadorView[listaTrabView.size()]);
    } // getAllTrabajadoresArray().

    //--------------------------------------------------------------------------
    public static List<TrabajadorView> getFilterTrabajadoresView(ViewTrabajadorViewFilter viewTrabajadorViewFilter) {
        List<TrabajadorView> someTrabajador = new ArrayList<TrabajadorView>();
        String tipoNominaS = viewTrabajadorViewFilter.getTipoNomina();
        String nroTrabS = viewTrabajadorViewFilter.getNroTrabajador();
        String cedula = viewTrabajadorViewFilter.getCedulaID().toLowerCase();
        String nombre = viewTrabajadorViewFilter.getNombre().toLowerCase();
        String apellido = viewTrabajadorViewFilter.getApellido().toLowerCase();
        //System.out.println("Antes de iterar..., listatrabView.size="+listaTrabView.size()+"*"); 
        for (Iterator<TrabajadorView> i = listaTrabView.iterator(); i.hasNext();) {
            //for (Iterator<Trabajador> i = (getTrabajadores()).iterator(); i.hasNext();) {
            TrabajadorView tmp = i.next();
            //if (tmp.getNroTrabajador().compareTo(nroTrab) == 0
            if ((tmp.getTipoNomina() == null ? "" : tmp.getTipoNomina()).toString().contains(tipoNominaS)
                    && tmp.getNroTrabajador().toString().contains(nroTrabS) // Operarador ternario no requerido xq es un campo clave -> no puede ser registrado nulo. 
                    && (tmp.getCedulaID() == null ? "" : tmp.getCedulaID()).toLowerCase().contains(cedula)
                    && (tmp.getNombre() == null ? "" : tmp.getNombre()).toLowerCase().contains(nombre)
                    && (tmp.getApellido() == null ? "" : tmp.getApellido()).toLowerCase().contains(apellido)) {
                someTrabajador.add(tmp);
            } // if
        }  // for.  
        return (someTrabajador);
    }  // getFilterTrabajadoresView(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public List<Trabajador> getListaTrab(String rifEmpresa) {
        List<NomTrabajador00Dat> listaTrabajadores = (new NomTrabajador00DatJpaController()).getlistaTrab(rifEmpresa);
        List<Trabajador> lista = new ArrayList<Trabajador>();
        for (NomTrabajador00Dat trabajador : listaTrabajadores) {
            lista.add(new Trabajador(trabajador.getNomTrabajador00DatPK().getNroTrabajador(), trabajador.getNombre1(), trabajador.getApellido1()));
        }
        return (lista);
    }  // getListaTrab().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public List<String> getListaTrabS(String rifEmpresa) {
        List<NomTrabajador00Dat> listaTrabajadores = (new NomTrabajador00DatJpaController()).getlistaTrab(rifEmpresa);
        List<String> lista = new ArrayList<String>();    
        for (NomTrabajador00Dat trabajador : listaTrabajadores) {
            lista.add( trabajador.getNomTrabajador00DatPK().getNroTrabajador()+" "+trabajador.getNombre1()+" "+trabajador.getApellido1() );
        }
        return (lista);
    }  // getListaTrab().  
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean existeTrabNomina(String rifEmpresa, Integer nroTrab) {
        Boolean ok = Boolean.FALSE;  
        if (rifEmpresa != null && !rifEmpresa.isEmpty() && nroTrab >= 0) {
            NomTrabajador01DatPK clave = new NomTrabajador01DatPK(rifEmpresa,nroTrab);  
            NomTrabajador01Dat trabajador = new NomTrabajador01DatJpaController().findNomTrabajador01Dat(clave);  
            if ( trabajador != null ) ok = Boolean.TRUE;  
        }
        return (ok);
    }  // existeTrabNomina()

}
