/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfaz;

import bean.modelo.entidad.VariableHist;
import org.zkoss.zul.ListModel;

/**
 *
 * @author henrypb
 */
public interface IModelViewVariableHist {
 
    public abstract ListModel<VariableHist> getVariableHistViewModel();  
    
}
