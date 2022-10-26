/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.entidad;

/**
 *
 * @author hpulgar
 */
public class TipoContrato {
 
    private Integer codTrabajador; 
    private Integer codNomina;
    private String  nombreNomina; 
    private Integer turno;
    private Integer rotacion;  

    public TipoContrato(Integer codNomina, String nombreNomina, Integer turno, Integer rotacion) {
        this.codNomina = codNomina;
        this.nombreNomina = nombreNomina;
        this.turno = turno;
        this.rotacion = rotacion;
    }

    public TipoContrato(Integer codTrabajador, Integer codNomina, String nombreNomina, Integer turno, Integer rotacion) {
        this.codTrabajador = codTrabajador;
        this.codNomina = codNomina;
        this.nombreNomina = nombreNomina;
        this.turno = turno;
        this.rotacion = rotacion;
    }

    public Integer getCodTrabajador() {
        return codTrabajador;
    }

    public void setCodTrabajador(Integer codTrabajador) {
        this.codTrabajador = codTrabajador;
    }

    public Integer getCodNomina() {
        return codNomina;
    }

    public void setCodNomina(Integer codNomina) {
        this.codNomina = codNomina;
    }

    public String getNombreNomina() {
        return nombreNomina;
    }

    public void setNombreNomina(String nombreNomina) {
        this.nombreNomina = nombreNomina;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public Integer getRotacion() {
        return rotacion;
    }

    public void setRotacion(Integer rotacion) {
        this.rotacion = rotacion;
    }

    
    
}
