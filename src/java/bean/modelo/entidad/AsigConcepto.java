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
public class AsigConcepto {
    
    private Integer codNomina;
    private Integer nroTrabajador;
    private String nombre;
    private Integer codConcepto; 
    private String descripcion; 
    private String fechaNominaS;  
    private Double cantidad;
    private Double monto; 
    private Double porcentaje;  
    private String cantidadS; 
    private String montoS;
    private String porcentajeS;  

    public AsigConcepto(Integer codNomina, Integer nroTrabajador, String nombre, Integer codConcepto, String descripcion) {
        this.codNomina = codNomina;
        this.nroTrabajador = nroTrabajador;
        this.nombre = nombre;
        this.codConcepto = codConcepto;
        this.descripcion = descripcion;
    }

    public AsigConcepto(Integer codNomina, Integer nroTrabajador, String nombre, Integer codConcepto, String descripcion, String fechaNominaS) {
        this.codNomina = codNomina;
        this.nroTrabajador = nroTrabajador;
        this.nombre = nombre;
        this.codConcepto = codConcepto;
        this.descripcion = descripcion;
        this.fechaNominaS = fechaNominaS;
    }

    public AsigConcepto(Integer codNomina, Integer nroTrabajador, String nombre, Integer codConcepto, String descripcion, Double cantidad, Double monto, Double porcentaje) {
        this.codNomina = codNomina;
        this.nroTrabajador = nroTrabajador;
        this.nombre = nombre;
        this.codConcepto = codConcepto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.monto = monto;
        this.porcentaje = porcentaje;
    }

    public AsigConcepto(Integer codNomina, Integer nroTrabajador, String nombre, Integer codConcepto, String descripcion, String cantidadS, String montoS, String porcentajeS) {
        this.codNomina = codNomina;
        this.nroTrabajador = nroTrabajador;
        this.nombre = nombre;
        this.codConcepto = codConcepto;
        this.descripcion = descripcion;
        this.cantidadS = cantidadS;
        this.montoS = montoS;
        this.porcentajeS = porcentajeS;
    }
    
    public Integer getCodNomina() {
        return codNomina;
    }

    public void setCodNomina(Integer codNomina) {
        this.codNomina = codNomina;
    }

    public Integer getNroTrabajador() {
        return nroTrabajador;
    }

    public void setNroTrabajador(Integer nroTrabajador) {
        this.nroTrabajador = nroTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCodConcepto() {
        return codConcepto;
    }

    public void setCodConcepto(Integer codConcepto) {
        this.codConcepto = codConcepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaNominaS() {
        return fechaNominaS;
    }

    public void setFechaNominaS(String fechaNominaS) {
        this.fechaNominaS = fechaNominaS;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getCantidadS() {
        return cantidadS;
    }

    public void setCantidadS(String cantidadS) {
        this.cantidadS = cantidadS;
    }

    public String getMontoS() {
        return montoS;
    }

    public void setMontoS(String montoS) {
        this.montoS = montoS;
    }

    public String getPorcentajeS() {
        return porcentajeS;
    }

    public void setPorcentajeS(String porcentajeS) {
        this.porcentajeS = porcentajeS;
    }
    
}
