/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.servicio;

import bean.modelo.entidad.Empresa;
import bean.modelo.entidad.NomEmpresaDat;
import bean.interfaz.IServicioAdmonEmpresa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author henrypb
 */
public class ServicioAdmonEmpresa implements IServicioAdmonEmpresa {

    @Override
    public List<Empresa> getListaEmpresa() {
        List<Empresa> lista = new ArrayList<Empresa>();  
        List<NomEmpresaDat> listaNomEmpresaDat = new bean.controlador.empresa.NomEmpresaDatJpaController().findNomEmpresaDatEntities();  
        Empresa empresa = null;  
        for ( NomEmpresaDat nomEmpresaDat : listaNomEmpresaDat ) {
            empresa = new Empresa(nomEmpresaDat.getRif(),nomEmpresaDat.getNombre(),nomEmpresaDat.getRazonSocial());  
            lista.add(empresa);  
        } 
        return (lista);
    }  // getListaEmpresa()
    
}
