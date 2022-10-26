/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servicio;

import bean.controlador.concepto.NomConceptoDatJpaController;
import bean.interfaz.IServicioAdmonConcepto;
import bean.modelo.entidad.AsigConcepto;
import bean.modelo.entidad.NomCalculoDat;
import bean.modelo.entidad.NomCalculoDatPK;
import bean.modelo.entidad.NomConceptoDat;
import bean.modelo.entidad.NomConceptoDatPK;
import bean.modelo.vista.AsigConceptoViewFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.zkoss.zk.ui.Sessions;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonConcepto implements IServicioAdmonConcepto {

    public static String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");

    public static List<AsigConcepto> listaAsigConceptos = new ArrayList<AsigConcepto>();

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Boolean conceptoRegistrado(Integer codNomina, Integer codConcepto) {
        NomConceptoDatPK nomConceptoDatPK = new NomConceptoDatPK(rifEmpresa, codNomina, codConcepto);
        NomConceptoDat nomConceptoDat = new NomConceptoDatJpaController().findNomConceptoDat(nomConceptoDatPK);
        if (nomConceptoDat == null) {
            return (Boolean.FALSE);
        } else {
            return (Boolean.TRUE);
        }
    } // conceptoRegistrado().  


    //**************************************************************************
    ///////// *OJO:: metodos obligados para activar getFilterAsigConcepto() */// 
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    // *** NOTA *** La regla aplica que debe :
    //  (1). Los atributos deben ser Publicos ****
    //  (2). Este metodo debe ser "OBLIGATORIO". El mismo metodo utilizado en el 
    //       ModelView para asignar la variable listaAsigConcepto. 
    //--------------------------------------------------------------------------
    public List<AsigConcepto> getListaAsigConcepto(String rifEmpresa) {
        listaAsigConceptos = new bean.controlador.concepto.NomAsigConceptoDatJpaController().listaAsigConceptos(rifEmpresa); 
        return (listaAsigConceptos); 
    }   // getlistaAsigConceptos(). 


    public AsigConcepto[] getAllAsigConceptoArray() { 
       return( listaAsigConceptos.toArray(new AsigConcepto[listaAsigConceptos.size()]) );          
    }  // getAllListaAsigConceptosArray()
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public List<AsigConcepto> getFilterAsigConcepto(AsigConceptoViewFilter asigConceptoViewFilter) {
        List<AsigConcepto> someItem = new ArrayList<AsigConcepto>();
        String keyCodNomina = asigConceptoViewFilter.getCodNominaS().toLowerCase();
        String keyNroTrab = asigConceptoViewFilter.getNroTrabajadorS().toLowerCase();
        String keyNombre = asigConceptoViewFilter.getNombre().toLowerCase();
        String keyCodConcepto = asigConceptoViewFilter.getCodConceptoS().toLowerCase();
        String keyDescripcion = asigConceptoViewFilter.getDescripcion().toLowerCase();
        String keyFechaNomina = asigConceptoViewFilter.getFechaNominaS();
        AsigConcepto tmp;
        //System.out.println("Voy a for. keyCodNom=" + keyCodNomina + " keyNombre=" + keyNombre + "**");
        //System.out.println("listaAsigConcepto.size ="+listaAsigConceptos.size() +"**");
        for (Iterator<AsigConcepto> i = listaAsigConceptos.iterator(); i.hasNext();) {
            tmp = i.next();
            //System.out.println("Iterenado dentro del for");
            if (tmp.getCodNomina().toString().toLowerCase().contains(keyCodNomina)
                    && tmp.getNroTrabajador().toString().toLowerCase().contains(keyNroTrab)
                    && tmp.getNombre().toLowerCase().contains(keyNombre)
                    && tmp.getCodConcepto().toString().toLowerCase().contains(keyCodConcepto)
                    && tmp.getDescripcion().toLowerCase().contains(keyDescripcion) ) {
                //System.out.println("** Item added **");
                someItem.add(tmp);
            } // if
        } // for
        return (someItem);
    }  // getFilterAsigConcepto().  

    //--------------------------------------------------------------------------
    @Override
    public Boolean calculoConceptoRegistrado(Integer codNomina, Integer codConcepto, Integer nroTrabajador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //--------------------------------------------------------------------------
    // (*) El siguiente metodo fue creado para efectos Ilustrativos!!!!!!!!!
    // (*) Puede que que no sea implantado en las rutinas de calculo de concepto. ????????????????
    //--------------------------------------------------------------------------
    public NomCalculoDat getRegistroCalculado(String rifEmpresa, Integer codNomina, Integer codConcepto, Integer nroTrabajador, java.util.Date fechaNomina ) {
        NomCalculoDat c;  
        NomCalculoDatPK pk = new NomCalculoDatPK(rifEmpresa,codNomina,nroTrabajador,codConcepto,fechaNomina); 
        c = new bean.controlador.concepto.NomCalculoDatJpaController().findNomCalculoDat(pk);   
        return (c); 
    }  // getRegistroCalculado(). 

}
