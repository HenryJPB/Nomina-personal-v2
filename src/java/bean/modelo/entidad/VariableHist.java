/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.entidad;

import java.util.Date;

/**
 *
 * @author henrypb
 */
public class VariableHist {
    
    private java.util.Date fechaVigencia; 
    private Double valor;

    public VariableHist(Date fechaVigencia, Double valor) {
        this.fechaVigencia = fechaVigencia;
        this.valor = valor;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
