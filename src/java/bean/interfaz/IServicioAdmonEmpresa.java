/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfaz;

import bean.modelo.entidad.Empresa;
import java.util.List;

/**
 *
 * @author henrypb
 */
public interface IServicioAdmonEmpresa {
    
    public abstract List<Empresa> getListaEmpresa();  
    
}
