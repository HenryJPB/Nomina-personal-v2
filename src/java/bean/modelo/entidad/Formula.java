/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.entidad;

/**
 *
 * @author henrypb
 */
public class Formula {
    
    private Integer correlativo; 
    private String formula; 
    private Integer siguiente; 
    private Integer anterior; 
    private String status;
    private String observacion;  

    public Formula(Integer correlativo, String formula, Integer siguiente, Integer anterior, String status, String observacion) {
        this.correlativo = correlativo;
        this.formula = formula;
        this.siguiente = siguiente;
        this.anterior = anterior;
        this.status = status;
        this.observacion = observacion;
    }

    public Integer getCorrelativo() {
        return correlativo;
    }

    public void setCorrelativo(Integer correlativo) {
        this.correlativo = correlativo;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Integer siguiente) {
        this.siguiente = siguiente;
    }

    public Integer getAnterior() {
        return anterior;
    }

    public void setAnterior(Integer anterior) {
        this.anterior = anterior;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
