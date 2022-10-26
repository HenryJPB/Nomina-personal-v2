/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfaz;

import bean.utilitario.libreria.ClassArregloTokens;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author henrypb
 */
public interface ILibreriaHP {
    
    //--------------------------------------------------------------------------
    public abstract boolean esNumerico(String valorStr); 

    //--------------------------------------------------------------------------
    public abstract boolean numeroValido(String valorStr);  

    //--------------------------------------------------------------------------
    // convertir un String a un String en formato numerico convencional. 
    // Ejm. 10.213,45 -> 10213.45 para convertir en <>.Parse... / new BigDecimal().  
    //--------------------------------------------------------------------------
    public abstract String convFormatoNumerico(String valorS1); 
    
    //--------------------------------------------------------------------------
    // convertir un String a un String en formato numerico convencional. 
    // Ejm. 10,213.45 -> 10213.45 para convertir en <>.Parse... / new BigDecimal(). 
    // para operar en Base de Datos. 
    //--------------------------------------------------------------------------
    public abstract String convFormatoNumericoBD(String valorS1); 

    //--------------------------------------------------------------------------
    // Leer y ordenar ficheros del sistema de archivos (carpeta) 
    // aplicando un filtro:
    // Documentacion sobre arrays: https://darkbyteblog.wordpress.com/2011/03/28/java-estructuras-de-datos-arreglos/
    //--------------------------------------------------------------------------
    public abstract String[] leerFicheros(String ruta); 
    
    //--------------------------------------------------------------------------
    public abstract BufferedImage getImagen(byte[] bytes);  

    //--------------------------------------------------------------------------
    // Haciendo uso de Poliformismo: 
    //--------------------------------------------------------------------------
    public abstract BufferedImage getImagen(String carpeta) throws IOException; 

    //--------------------------------------------------------------------------
    // Haciendo uso de Poliformismo: 
    // Archivo -> byte[]
    //--------------------------------------------------------------------------
    public abstract byte[] convertirImagen(String carpeta) throws FileNotFoundException, IOException;  

    //--------------------------------------------------------------------------
    // Haciendo uso de Poliformismo. 
    // https://www.tutorialspoint.com/How-to-convert-Image-to-Byte-Array-in-java
    // Buffered img -> byte[]
    //--------------------------------------------------------------------------
    public abstract byte[] convertirImagen(BufferedImage bImage) throws IOException ; 
    
    //--------------------------------------------------------------------------    
    public abstract ClassArregloTokens.TIPO_TOKEN tipoToken(String token);  
    
    //--------------------------------------------------------------------------    
    // Original:  
    //--------------------------------------------------------------------------
    public abstract void divideTokenizer(String cadena);  
    
    //--------------------------------------------------------------------------
    public abstract ClassArregloTokens dividirTokenizer(String cadena);  

    //--------------------------------------------------------------------------
    public abstract ClassArregloTokens dividirTokens(String cadena); 

    //--------------------------------------------------------------------------
    public abstract ClassArregloTokens validarEcuacionNomina(Integer codNominaProceso, Integer nroTrabajador, ClassArregloTokens classArrTokens);  
    
    //--------------------------------------------------------------------------
    public abstract java.util.Date getUltDiaMes( java.util.Date fecha );   
    
} // ILibreriaHP().  
