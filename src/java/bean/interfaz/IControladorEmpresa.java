/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfaz;

/**
 *
 * @author henrypb
 */
public interface IControladorEmpresa {
    
    public abstract void createModel();  
    
    public abstract void onChange(); 
    
    public abstract void onNew(); 
    
    public abstract void onSave();  
    
}
