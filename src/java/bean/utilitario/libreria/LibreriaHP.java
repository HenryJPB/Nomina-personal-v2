/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.utilitario.libreria;

import bean.interfaz.ILibreriaHP;
import bean.servicio.ServicioAdmonConcepto;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.commons.io.IOUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author henrypb Documentacion sobre arrays:
 * https://darkbyteblog.wordpress.com/2011/03/28/java-estructuras-de-datos-arreglos/
 *
 */
public class LibreriaHP implements ILibreriaHP {

    public SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

    public DecimalFormat formatoDecimal = new DecimalFormat("###,###,###,##0.00");

    public DecimalFormat formatoFactura = new DecimalFormat("000000");

    public DecimalFormat formatoNcf = new DecimalFormat("0000000000");

    final String OPERADOR = "+-/*";
    final String SIMBOLO = "()[]";
    final String SIGNO = ",.";  // signo de puntuacion. 
    final String DIGITO = "0123456789";
    final String CONCEPTO = "VPCMT";  // |-> V)ariable,P)romedio,C)antidad,M)onto y/o T)otal.  
    // 
    final int MAX_ELEMENTOS = 100;

    //
    private enum TIPO_TOKEN {
        BLANK, CONCEPTO, DIGITO, FUNCION, NUMERO, PROMEDIO, SIMBOLO, SIGNO, OPERADOR
    }
    
    //--------------------------------------------------------------------------    
    // Original:  
    //--------------------------------------------------------------------------
    @Override
    public void divideTokenizer(String cadena) {
        /*
         String[] arrTokens = new String[MAX_ELEMENTOS];
         char caracter;  
         for (int i=0; ( i<cadena.length() );i++) {
         caracter = cadena[i]; 
         System.out.println(cadena[i]);  
         }
         */
        //String string = cadena; 
        String string = cadena;
        //StringTokenizer token = new StringTokenizer(string,"=");  
        StringTokenizer token = new StringTokenizer(string, " ");
        String st = null;
        while (token.hasMoreTokens()) {
            st = token.nextToken();
            System.out.println("Token=" + st);
        }  // while. 
    }  // divideTokenizer();

    //--------------------------------------------------------------------------    
    // Modificado:  
    //--------------------------------------------------------------------------
    @Override
    public ClassArregloTokens dividirTokenizer(String cadena) {
        String[] arrTokens = new String[MAX_ELEMENTOS];
        String string = cadena;
        //StringTokenizer token = new StringTokenizer(string,"=");  
        StringTokenizer token = new StringTokenizer(string, " ");
        String st = null;
        int contador = -1;
        while (token.hasMoreTokens()) {
            contador++;
            st = token.nextToken();
            //System.out.println("Token="+st); 
            arrTokens[contador] = st;
        }  // while. 
        ClassArregloTokens classArrTokens = new ClassArregloTokens(contador, arrTokens, null);
        return (classArrTokens);
    }  // dividirTokenizer();

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public ClassArregloTokens dividirTokens(String cadena) {
        /*
         final String OPERADOR = "+-/*";
         final String SIMBOLO = "()[]";
         final String SIGNO = ",.";
         final String DIGITO = "0123456789";
         */
        ClassArregloTokens.TIPO_TOKEN tipoToken = ClassArregloTokens.TIPO_TOKEN.BLANK;
        ClassArregloTokens.TIPO_TOKEN[] arrTipoToken = new ClassArregloTokens.TIPO_TOKEN[MAX_ELEMENTOS];
        String[] arrTokens = new String[MAX_ELEMENTOS];
        String token = "";
        String tokenAux = "";
        int contador = 0;
        int i = 0;
        Boolean ok, termine;
        while (i < cadena.length()) {
            ok = Boolean.FALSE;
            //System.out.println("cadena[" + i + "]=" + cadena.charAt(i));
            token = String.valueOf(cadena.charAt(i));
            if (OPERADOR.contains(token)) {
                tipoToken = ClassArregloTokens.TIPO_TOKEN.OPERADOR;
                ok = Boolean.TRUE;
            } else if (SIMBOLO.contains(token)) {
                tipoToken = ClassArregloTokens.TIPO_TOKEN.SIMBOLO;
                ok = Boolean.TRUE;
            } else if (DIGITO.contains(token) || SIGNO.contains(token)) {
                while (i < cadena.length() && (DIGITO.contains(token) || SIGNO.contains(token))) {
                    tokenAux = tokenAux + token;
                    ok = Boolean.TRUE;
                    i++;
                    if (i < cadena.length()) {
                        token = String.valueOf(cadena.charAt(i));
                    }
                } // while ( i < cadena.length() && (DIGITO.contains(token) ...
                if (ok) {
                    token = tokenAux;
                    tipoToken = ClassArregloTokens.TIPO_TOKEN.NUMERO;
                    tokenAux = "";
                    i--;
                }  // if (ok) ...
            } else if (CONCEPTO.contains(token)) {
                while (i < cadena.length() && (CONCEPTO.contains(token) || DIGITO.contains(token)) && (!token.equals(" "))) {
                    tokenAux = tokenAux + token;
                    ok = Boolean.TRUE;
                    i++;
                    if (i < cadena.length()) {
                        token = String.valueOf(cadena.charAt(i));
                    }
                } // while ( i < cadena.length() && (CONCEPTO.contains(token) ...
                if (ok) {
                    token = tokenAux;
                    tipoToken = ClassArregloTokens.TIPO_TOKEN.CONCEPTO;
                    tokenAux = "";
                    i--;
                }  // if (ok) ...
            }  // 
            if (ok) {  // <<< Aqui cargar arreglo de token y Arreglo logica de tokens,.....>>>>
                arrTokens[contador] = token;
                arrTipoToken[contador] = tipoToken;
                //System.out.println("contador=" + contador + " token=" + token + " tipoToken=" + tipoToken + "");
                contador++;
            }
            i++;
        }
        ClassArregloTokens classArrTokens = new ClassArregloTokens(contador, arrTokens, arrTipoToken);
        return (classArrTokens);
    } // dividirTokens(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public boolean esNumerico(String valorStr) {
        try {
            double d = Double.parseDouble(valorStr);
        } // try.
        catch (NumberFormatException nfe) {
            return false;
        }  // catch.  
        return true;
    }  // esNumerico()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public boolean numeroValido(String valorStr) {
        try {
            double d = Double.parseDouble(valorStr);
        } // try.
        catch (NumberFormatException nfe) {
            return false;
        }  // catch.  
        return true;
    }  // numeroValido(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void error(JPanel panel, JTextField campo, String mensaje) {
        campo.setBackground(Color.YELLOW);
        JOptionPane.showMessageDialog(panel, mensaje, "ATENCION", JOptionPane.ERROR_MESSAGE);
        campo.setBackground(Color.WHITE);
        campo.requestFocus();
    }  // errror(). 

    //--------------------------------------------------------------------------
    // convertir un String a un String en formato numerico convencional. 
    // Ejm. 10.213,45 -> 10213.45 para convertir en <>.Parse... / new BigDecimal().  
    //--------------------------------------------------------------------------
    @Override
    public String convFormatoNumerico(String valorS1) {
        String valorS2 = null;
        valorS2 = valorS1.replace(".", "");
        valorS2 = valorS2.replace(",", ".");
        valorS2 = valorS2.replace(" ", "");
        return (valorS2);
    }  //  convFormatoNumerico(). 

    //--------------------------------------------------------------------------
    // convertir un String a un String en formato numerico convencional. 
    // Ejm. 10,213.45 -> 10213.45 para convertir en <>.Parse... / new BigDecimal(). 
    // para operar en Base de Datos. 
    //--------------------------------------------------------------------------
    @Override
    public String convFormatoNumericoBD(String valorS1) {
        String valorS2 = null;
        valorS2 = valorS1.replace(",", "");
        valorS2 = valorS2.replace(" ", "");
        return (valorS2);
    }  //  convFormatoNumerico(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void notificarFocus(JTextField campo) {
        campo.setBackground(Color.YELLOW);
        campo.requestFocus();
    }  // notificarFocus().  

    //--------------------------------------------------------------------------
    // Leer y ordenar ficheros del sistema de archivos (carpeta) 
    // aplicando un filtro:
    // Documentacion sobre arrays: https://darkbyteblog.wordpress.com/2011/03/28/java-estructuras-de-datos-arreglos/
    //--------------------------------------------------------------------------
    @Override
    public String[] leerFicheros(String ruta) {
        final String filtro = ".jpg";  // 
        final int MAX = 999;
        File carpeta = new File(ruta);
        String[] arreglo = new String[MAX];
        String[] arregloAux = null;
        int i = 0;
        Boolean capacidad = Boolean.TRUE;
        if (carpeta.isDirectory()) {
            for (File ficheroEntrada : carpeta.listFiles()) {
                if (ficheroEntrada.isFile()) {
                    String nombreArchivo = ficheroEntrada.getName().toLowerCase();
                    if (nombreArchivo.contains(filtro) && capacidad) {
                        //System.out.println("******Nombre de la imagen="+nombreArchivo+"*****");
                        arreglo[i] = ficheroEntrada.getName();
                        i++;
                        if (i > MAX) {
                            capacidad = Boolean.FALSE;
                        }
                    }
                }
            }  // for.  
        } else { // verificar q existe el directorio 
            //alert("La ruta: "+ruta+" NO es un directorio de este sistema"); 
        }
        if (i > 0) {
            // Procesar valores NO nulos.  ***************
            int contador = 0;
            for (String s : arreglo) {
                if (s != null && !s.isEmpty()) {
                    contador++;
                }
            }  // for
            arregloAux = new String[contador];
            System.arraycopy(arreglo, 0, arregloAux, 0, contador);
            Arrays.sort(arregloAux);
        }  // if. 
        return (arregloAux);
    }  // leerFicheros().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public BufferedImage getImagen(byte[] bytes) {
        //leerFoto.setTooltiptext(String);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException ex) {
            Logger.getLogger(LibreriaHP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (img);
    }  // getImagen().

    //--------------------------------------------------------------------------
    // Haciendo uso de Poliformismo: 
    //--------------------------------------------------------------------------
    @Override
    public BufferedImage getImagen(String carpeta) throws IOException {
        byte[] bytes = convertirImagen(carpeta);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException ex) {
            Logger.getLogger(LibreriaHP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (img);
    }  // getImagen().

    //--------------------------------------------------------------------------
    // Archivo -> byte[]
    //--------------------------------------------------------------------------
    @Override
    public byte[] convertirImagen(String carpeta) throws FileNotFoundException, IOException {
        File file = new File(carpeta);
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = IOUtils.toByteArray(fis);
        return (bytes);
    }  // convertirImagen()

    //--------------------------------------------------------------------------
    // Haciendo uso de Poliformismo. 
    // https://www.tutorialspoint.com/How-to-convert-Image-to-Byte-Array-in-java
    // Buffered img -> byte[]
    //--------------------------------------------------------------------------
    @Override
    public byte[] convertirImagen(BufferedImage bImage) throws IOException {
        //BufferedImage bImage = ImageIO.read(new File("sample.jpg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        byte[] bytes = bos.toByteArray();
        return (bytes);
    }  // convertirImagen()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public ClassArregloTokens.TIPO_TOKEN tipoToken(String token) {
        ClassArregloTokens.TIPO_TOKEN tipoToken = ClassArregloTokens.TIPO_TOKEN.NO_APLICA;
        if (SIMBOLO.contains(token)) {
            tipoToken = ClassArregloTokens.TIPO_TOKEN.SIMBOLO;
        } else if (SIGNO.contains(token) || DIGITO.contains(token.substring(0, 1)) || (OPERADOR.contains(token.substring(0, 1)) && token.substring(0, 1).equals("-"))) {
            tipoToken = ClassArregloTokens.TIPO_TOKEN.NUMERO;
        } else if (OPERADOR.contains(token)) {
            tipoToken = ClassArregloTokens.TIPO_TOKEN.OPERADOR;
        } else if (CONCEPTO.contains(token.substring(0, 1))) {
            tipoToken = ClassArregloTokens.TIPO_TOKEN.CONCEPTO;
        }
        return (tipoToken);
    }  // tipoToken(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public ClassArregloTokens validarEcuacionNomina(Integer codNominaProceso, Integer nroTrabajador, ClassArregloTokens classArrTokens) {
        LibreriaHP libHP = new bean.utilitario.libreria.LibreriaHP();
        ClassArregloTokens newClassArrTokens = null;
        String[] t = classArrTokens.getArregloTokens();
        String dumy, tokenAnterior, token = null;
        int MAX_ELEMENTOS = 0;
        String[] t2 = new String[MAX_ELEMENTOS];
        ClassArregloTokens.TIPO_TOKEN[] l = classArrTokens.getArregloTipoToken();
        ClassArregloTokens.TIPO_TOKEN[] l2 = new ClassArregloTokens.TIPO_TOKEN[MAX_ELEMENTOS];
        ClassArregloTokens.TIPO_TOKEN tipoToken, tipoTokenAnterior;
        Boolean termina = Boolean.FALSE;
        Pila pilaSimbolo = new Pila();
        int j = 0;
        int i = 0;
        while (i < classArrTokens.getContadorTokens() && l[i] != ClassArregloTokens.TIPO_TOKEN.NO_APLICA && !termina) {
            token = t[i];
            System.out.println("token[i]=" + t[i]);
            tipoToken = l[i];
            if (i > 0) {
                tokenAnterior = t[i - 1];
                tipoTokenAnterior = l[i - 1];
                switch (tipoTokenAnterior) {
                    case OPERADOR: { // "+-/*"
                        switch (tipoToken) {
                            case OPERADOR: {
                                Messagebox.show("Omision de termino en la formula/ecuacion. Check: '" + tokenAnterior + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                                termina = Boolean.TRUE;
                                break;
                            }
                            case SIMBOLO: {
                                if (token.equals("]") || token.equals(")")) {
                                    Messagebox.show("Omision de termino en la formula/ecuacion. Check: '" + tokenAnterior + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                                    termina = Boolean.TRUE;
                                }
                                break;
                            }
                            case NUMERO: {
                                if (tokenAnterior.equals("/")) {
                                    token = libHP.convFormatoNumericoBD(token);
                                    if (!libHP.numeroValido(token) || Double.parseDouble(token) == 0.00) {
                                        Messagebox.show("Valor numerico invalido o division entre zero no definida. Check: '" + tokenAnterior + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                                        termina = Boolean.TRUE;
                                    }
                                }
                            }
                            break;
                        }  // switch (tipoToken)
                    }  // case OPERADOR: { // "+-/*"
                    case SIMBOLO: {   // "()[]";   
                        if ((tokenAnterior.equals("(") || tokenAnterior.equals("[")) && (tipoToken == ClassArregloTokens.TIPO_TOKEN.OPERADOR)) {
                            //token = t[i];
                            switch (token) {
                                case "+":
                                case "-": {
                                    t2[j] = "0.00";
                                    l2[j] = ClassArregloTokens.TIPO_TOKEN.NUMERO;
                                    j++;
                                    break;
                                }
                                case "*": {
                                    t2[j] = "1.00";
                                    l2[j] = ClassArregloTokens.TIPO_TOKEN.NUMERO;
                                    j++;
                                    break;
                                }
                                case "/": {
                                    Messagebox.show("Omision de Numerador en formula/ecuacion. Check: '" + tokenAnterior + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                                    termina = Boolean.TRUE;
                                    break;
                                }
                            }  // switch
                        } else if ((tokenAnterior.equals("]") || tokenAnterior.equals(")")) && (tipoToken == ClassArregloTokens.TIPO_TOKEN.NUMERO || tipoToken == ClassArregloTokens.TIPO_TOKEN.CONCEPTO)) {
                            Messagebox.show("Omision de Operador en formula/ecuacion. Check: '" + tokenAnterior + "' y '" + token + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                            termina = Boolean.TRUE;
                        }
                    } // case OPERADOR, SIMBOLO
                    break;
                    case CONCEPTO:
                    case NUMERO: {
                        //System.out.println("*********Aquica*****NUMERO***tokenAnterior="+tipoTokenAnterior+"***tipoTokenActual"+tipoToken);
                        switch (tipoToken) {
                            case CONCEPTO:
                            case NUMERO: {
                                Messagebox.show("Omision de Operador en formula/ecuacion. Check: '" + tokenAnterior + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                                termina = Boolean.TRUE;
                                break;
                            }
                            case SIMBOLO: {
                                if (token.equals("[") || token.equals("(")) {
                                    Messagebox.show("Omision de Operador en formula/ecuacion. Check: '" + tokenAnterior + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                                    termina = Boolean.TRUE;
                                    break;
                                }
                            }
                        }
                        break;
                    }  // case NUMERO : 
                    }
            }  // if (i > 0)
            if (tipoToken == ClassArregloTokens.TIPO_TOKEN.SIMBOLO) {
                switch (token) {
                    case "[":
                    case "(": {
                        pilaSimbolo.push(token);
                        break;
                    }
                    case "]":
                    case ")":
                        dumy = (String) pilaSimbolo.pop();
                        if (dumy == null) {
                            Messagebox.show("Parentesis/corchetes en desbalance en la formula/ecuacion ", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                            termina = Boolean.TRUE;
                        }
                        break;
                }  // switch (token).  
            } else if (tipoToken == ClassArregloTokens.TIPO_TOKEN.NUMERO) {
                token = libHP.convFormatoNumericoBD(token);
                if (!libHP.numeroValido(token)) {
                    Messagebox.show("Numero incorrecto en la formula/ecuacion. Check: '" + token + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                    termina = Boolean.TRUE;
                } else {
                    t[i] = token;
                }
            } else if (tipoToken == ClassArregloTokens.TIPO_TOKEN.CONCEPTO) {
                Integer codConcepto = Integer.parseInt(token.substring(1, token.length()));
                //System.out.println("token=" + token + "  subStr.Cod concepto=" + codConcepto + "*****");
                ServicioAdmonConcepto servAdmConcepto = new bean.servicio.ServicioAdmonConcepto();
                if (!servAdmConcepto.conceptoRegistrado(codNominaProceso, codConcepto) || !servAdmConcepto.calculoConceptoRegistrado(codNominaProceso, codConcepto, nroTrabajador)) {
                    Messagebox.show("El Concepto " + token.substring(1) + " para el trabajador (ficha) : " + nroTrabajador + " no esta Registrado o Calculado.", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                    termina = Boolean.TRUE;
                }
            }
            t2[j] = t[i];
            l2[j] = l[i];
            j++;
            i++;
        } // while 
        if (!termina) {
            if (!pilaSimbolo.vacia()) {
                Messagebox.show("Formula/ecuacion incompleta->Parentesis/corchetes en desbalance.", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
            } else {
                newClassArrTokens = new ClassArregloTokens(j, t2, l2);
            }
        }
        return (newClassArrTokens);
    }  // validarEcuacionNomina(). 

    
    //--------------------------------------------------------------------------
    @Override
    public Date getUltDiaMes(Date fecha) {
        java.util.Calendar c = java.util.Calendar.getInstance();  
        c.setTime(fecha);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return (c.getTime());
    }  // getUltDiaMes().  
    
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void messageBox( String mensaje ) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("MENSAJE", mensaje); 
        Window editDialog = (Window) Executions.createComponents("/VISTA_ACCESORIOS/pagMessagebox.zul", null, parameters);
        editDialog.setTitle("ATENCION");
        editDialog.doModal();
    } // messageBox().  
    
}  // MyLibreryHP.  
