/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servicio;

import bean.modelo.entidad.NomEstadosDat;
import bean.modelo.entidad.NomPaisDat;
import bean.interfaz.IServicioAdmonGeopolitica;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonGeopolitica implements IServicioAdmonGeopolitica {
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public List<String> getEstados() {
       List<String> estados = new ArrayList<>();
       List<NomEstadosDat> lista = new bean.controlador.geopolitica.NomEstadosDatJpaController().findNomEstadosDatEntities();  
       for ( NomEstadosDat nomEstadosDat : lista ) {
           estados.add(nomEstadosDat.getNombre()); 
       } // for
       return (estados); 
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public List<String> getPaises() {
       List<String> paises = new ArrayList<>(); 
       List<NomPaisDat> lista = new bean.controlador.geopolitica.NomPaisDatJpaController().findNomPaisDatEntities();  
       for (NomPaisDat nomPaisDat : lista ) {
           paises.add(nomPaisDat.getNombre()); 
       }
       return (paises); 
    }
    
}
