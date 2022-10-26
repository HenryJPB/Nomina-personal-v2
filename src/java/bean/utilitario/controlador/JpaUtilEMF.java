/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.controlador;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author henrypb
 * 
  // Adaptado y corregido segun doc: https://es.slideshare.net/maxmouse/jpa-en-netbeans 
  // ARTICULO ADAPTANDO EL CONTROLADOR: "cuando netbeans genera un controlador, este
  // este controlador en su constructor inicializa el motor de persisntencia, es decir
  // si creamos varias isntancias de este controlador se inicializa varias veces el motor
  // de persistencia haciendo nuestra aplicacion Pesada y Lenta.." :: ver solucion
  // centralizando EntityManagerFatory en una clase de utileria: (( Utilitario JpAutil )).  
  * 
 */
public class JpaUtilEMF {

    public static String EMF = "NominaPersonalPU";
    public static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(EMF);
        } catch (Throwable t) {
            System.err.printf("ATENCION: Error al iniciar Entity Manager Factory." + t);
            t.printStackTrace();
            throw new ExceptionInInitializerError();
        }  // try-catch.          
    }  // static

    ;
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
