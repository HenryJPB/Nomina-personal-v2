/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.interfaz.IControladorDefFormula;
import bean.modelo.entidad.NomCalculoDat;
import bean.modelo.entidad.NomFormulaDat;
import bean.modelo.entidad.NomFormulaDatPK;
import bean.servicio.ServicioAdmonConcepto;
import bean.utilitario.libreria.ClassArregloTokens;
import bean.utilitario.libreria.ClassArregloTokens.TIPO_TOKEN;
import bean.utilitario.libreria.LibreriaHP;
import bean.utilitario.libreria.Pila;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Selectbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

/**
 *
 * @author henrypb
 */
public class ControladorDefFormula extends GenericForwardComposer implements IControladorDefFormula {

    final int SI = 1;
    final int NO = 0;
    final int MAX_TOKENS = 80;
    final int FILAS_SIZE = 999;
    //
    String ssnRifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    // -------------------------------------------------------------------------
    // Semantica para operar FORMULACION CALCULO DE CONCEPTOS DE NOMINA ***
    // ------------------------------*gestion CONDICION*------------------------
    final String CONDICION = "SI";
    //-------------------------palabras reservadas------------------------------
    final static String PALABRAS_RESERVADAS[] = {"SI", "MAYOR", "MAYOR_IGUAL", "MENOR", "MENOR_IGUAL", "IGUAL", "DIF", ";", ":", "CONTINUAR", "TERMINAR"};
    final static List<String> LISTA_PALABRAS_RESERVADAS = Arrays.asList(PALABRAS_RESERVADAS);
    final static Set<String> CONJUNTO_PALABRAS_RESERVADAS = new HashSet<String>(LISTA_PALABRAS_RESERVADAS);
    //-------------------------condicionales------------------------------
    final static String CONDICIONALES[] = {"MAYOR", "MAYOR_IGUAL", "MENOR", "MENOR_IGUAL", "IGUAL", "DIF"};
    final static List<String> LISTA_CONDICIONALES = Arrays.asList(CONDICIONALES);
    final static Set<String> CONJUNTO_CONDICIONALES = new HashSet<String>(LISTA_CONDICIONALES);
    //
    //                                     a[0]a[1]a[2]
    final static String ESTRUCTURA_SI[] = {"SI", ";", ":"};
    //-------------------------opcopnal-----------------------------------------
    //                                         a[3]
    final static String OPCIONAL[] = {"CONTINUAR", "TERMINAR"};
    final static List<String> LISTA_OPCIONAL = Arrays.asList(OPCIONAL);
    final static Set<String> CONJUNTO_OPCIONAL = new HashSet<String>(LISTA_OPCIONAL);
    //--------------------------------------------------------------------------
    final String BLANK = " ";
    final String OPERADOR = "+-/*";
    final String SIMBOLO = "()[]";
    final String SIGNO = ",.";  // signo de puntuacion. 
    final String DIGITO = "0123456789";
    final String CONCEPTO = "RVPCMTF";  // |-> V)ariable, P)romedio/Porcentaje, C)antidad, M)onto, F)uncion, R)esultado parcial y/o T)otal.  

    // -----------------------------------*gestion CONDICION*------------------
    /*
     private enum TIPO_TOKEN {
     BLANK, CONCEPTO, DIGITO, FUNCION, NUMERO, PROMEDIO, SIMBOLO, SIGNO, OPERADOR
     }
     */
    //(INI)------------*Gestion LOGICA DE CALCULO - RECETA*---------------------
    /*
     final int FILAS_SIZE = 999; 
     final int COLS_SIZE = 2;   
     //
     enum Indice { CORRELATIVO, ULTIMO }
     //
     Integer[][] arrIndice = new Integer[FILAS_SIZE][2];  
     String[]    arrFormula = new String[FILAS_SIZE];   
     */
    private Selectbox slbTipoNomina, slbCodConcepto;
    private Vbox vbxDefFormula;
    private Listbox lbxFormula;
    private Textbox txtCodFormula;             // pagDefFormula
    //private Menuitem mnnAddOpcion1, mnnAddOpcion2, mnnAddOpcion3; 
    private Button btnAdd, btnPrueba, btnEdit, btnRefresh;
    //private NomFormulaDat selectedFormula;     // NOT bindered. Reesuelto x el Constructor de esta clase.    
    private Image  imgRefresh;   
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // << intrucciones del usuario >>
        //certificarUsuario(); 
        //checkEventQueves(); 
        //binder = new AnnotateDataBinder(comp);
        //binder.bindBean("atributoControlador", this);
        iniciar();
        //prueba();                    // VALIDAR Y PROCESAR ECUACION / FORMULA *****
        //pruebaCondicionTokenizer();  // VALIDAR CONDICON SI ( if-else ).   
        //Events.sendEvent("onClick",imgProbar,null);
    }

    /*
     public void onSaved() {
     binder.loadComponent(lbxFormula);
     }
     */
    //---------Eliminame--------------------------------------------------------
    public void checkit() {
        // System.out.println("****Selected For, accion="+selectedFormula.getNomFormulaDatPK().getAccion()+"**correlativo="+selectedFormula.getNomFormulaDatPK().getCorrelativo()+"**codFormula="+selectedFormula.getNomFormulaDatPK().getCodFormula()+"****codConcepto="+selectedFormula.getNomFormulaDatPK().getCodConcepto()+"***codNomina="+selectedFormula.getNomFormulaDatPK().getCodConcepto()+"***");
    }

    //--------------------------------------------------------------------------
    private void iniciar() {
        //private String ssnRifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
        //Integer ssnCodNomina = (Integer) Sessions.getCurrent().getAttribute("ssnCodNomina");
        //Integer ssnCodConcepto = (Integer) Sessions.getCurrent().getAttribute("ssnCodConcepto");
        //String ssnCodFormula = (String) Sessions.getCurrent().getAttribute("ssnCodFormula");
        //System.err.println("INICIAR....***rif="+rifEmpresa+" codNomina="+ssnCodNomina+" **codConcepto="+ssnCodConcepto+" ***cFormula="+ssnCodFormula+" ***");
    }

    //--------------------------------------------------------------------------
    // Bqto: 18 diciembre 2020. 
    //  ** PRUEBA ejecutada exitosamente
    //  ** NO BORRAR !!!!!!!!!!!!
    //--------------------------------------------------------------------------
    public void onClick$btnPrueba() {
        // List l =  lbxFormula.getModel();    ??????
        // List<NomFormulaDat> talla = lModel.getSize();     ???????
        ListModel lm = lbxFormula.getModel();
        Integer talla = lm.getSize();
        System.out.println("========================================");
        System.out.println("| COD FOR ACCION CORRELATIVO FORMULA   |");
        System.out.println("========================================");
        for (int i = 0; i < talla; i++) {
            NomFormulaDat l = (NomFormulaDat) lm.getElementAt(i);
            System.out.print(l.getNomFormulaDatPK().getCodFormula() + "     ");
            System.out.print(l.getNomFormulaDatPK().getAccion() + "        ");
            System.out.print(l.getNomFormulaDatPK().getCorrelativo() + "        ");
            System.out.print(l.getFormula() + " ");
            System.out.println();
        }
        //Messagebox.show("Aquica onClick - btnPueba. TAMAÑO del lbxFormula=" + talla);             
        //lbxFormula.
        //lbxFormula.setModel(new ListModelList<NomFormulaDat>());  // Blanquear el Listbox. ( CORRECTO )
        List<NomFormulaDat> lista = new bean.controlador.concepto.NomFormulaDatJpaController().getFormulas(ssnRifEmpresa, 1, 100, "FM1100");
        //Messagebox.show("TAMAÑO de la lista=" + lista.size());
        lbxFormula.setModel(new ListModelList<NomFormulaDat>(lista));   // REVISAR ?????????? 
        lbxFormula.setItemRenderer(new ListitemRenderer<NomFormulaDat>() {  // CORRECTO.  

            @Override
            public void render(Listitem lstm, NomFormulaDat data, int i) throws Exception {
                NomFormulaDat f = (NomFormulaDat) data;
                lstm.setValue(data);
                //lstm.setStyle("color: black; font-weight: bold;");
                //
                addListcell(lstm, f.getNomFormulaDatPK().getAccion());
                //
                Integer correlativo = f.getNomFormulaDatPK().getCorrelativo();
                String c = correlativo.toString();
                addListcell(lstm, c);
                //
                addListcell(lstm, f.getFormula());
                addListcell(lstm, f.getObservacion());
                //
            }

            private void addListcell(Listitem li, String s) {
                Listcell lc = new Listcell();
                Label lb = new Label(s);
                lb.setStyle("color: black; font-weight: bold; font-size: 14px;");
                lb.setParent(lc);
                lc.setParent(li);
            }

        });  // lbxFormula.setItemRenderer(new ListitemRenderer<NomFormulaDat>().  
    } // onClick$btnPrueba().

    //--------------------------------------------------------------------------
    public void onCreate$selTipoNomina() {
        //loadCbbTiposNomina();
    }

    //--------------------------------------------------------------------------
    public void onSelect$selTipoNomina() {
        // *** Correcto ✔✔✔ 
        //int i = selTipoNomina.getSelectedIndex();
        // System.out.println("******on Change selTipoNomina*****=" + selTipoNomina.getModel().getElementAt(i) + "*****");
    }

    //--------------------------------------------------------------------------
    public void onCreate$selCodConcepto() {
        //loadCbbCodConceptos();
    }

    //--------------------------------------------------------------------------
    public void onSelect$selCodConcepto() {
        //loadCbbCodConceptos();
    }

    //--------------------------------------------------------------------------
    public void onCreate$selCodFormula() {
        loadCbbCodFormulas();
    }

    //--------------------------------------------------------------------------
    public void onClick$btnAdd() {
        //System.err.println("****EJECUTADO Button ADD*****");  
        agregar();
    }

    //--------------------------------------------------------------------------
    public void onClick$btnEdit() {
        //System.err.println("****click on Button EDIT*****");
        // editar(selectedItem);
        if (lbxFormula.getSelectedCount() > 0) {
            int i = lbxFormula.getSelectedIndex();
            //System.err.println("**REGISTRO SELECCIONADO y listo to EDIT*****Selected Item (Nro)=" + i + "***");
            //NomFormulaDat f = null, selectedItem = getSeleccion( lbxFormula.getSelectedItem() );
            NomFormulaDat f = null;
            Listitem li = lbxFormula.getSelectedItem();
            //System.err.println("Listitem li=" + li + "***Children vacios=" + li.getChildren().isEmpty() + "****");
            if (li == null || li.getChildren().isEmpty()) {
                Messagebox.show("ATENCION: REGISTRO SELECCIONADO VACIO / NULO");
            } else {
                f = getSeleccion(lbxFormula.getSelectedItem()); 
                editar(f);
            }
            //System.err.println("*f.Nomina=" + f.getNomFormulaDatPK().getCodNomina() + "**f.CodFormula=*" + f.getNomFormulaDatPK().getCodFormula() + "***accion=" + f.getNomFormulaDatPK().getAccion() + "**corelativo=*" + f.getNomFormulaDatPK().getCorrelativo() + "****");
        } else {
            System.err.println("**NO SE HA SELECCIONADO ningun REGISTRO****");
        }
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private NomFormulaDat getSeleccion(Listitem itemSelected) {
        NomFormulaDat f = null;
        List celdas = itemSelected.getChildren();
        //
        Listcell listcell = (Listcell) celdas.get(0);
        String accion = listcell.getLabel();
        //System.err.println("getSelected.accion=" + accion + "****");
        if (accion != null && !accion.isEmpty()) {
            listcell = (Listcell) celdas.get(1);
            String correlativoS = listcell.getLabel();
            //System.err.println("getSelected.correlativo=" + correlativoS + "****");
            Integer correlativo = Integer.parseInt(correlativoS);
            //
            Integer i = slbTipoNomina.getSelectedIndex();
            String cadena = (String) slbTipoNomina.getModel().getElementAt(i);
            cadena = cadena.substring(0, cadena.indexOf("-"));
            Integer codNomina = Integer.parseInt(cadena);
            //
            i = slbCodConcepto.getSelectedIndex();
            cadena = (String) slbCodConcepto.getModel().getElementAt(i);
            cadena = cadena.substring(0, cadena.indexOf("-"));
            Integer codConcepto = Integer.parseInt(cadena);
            //
            String codFormula = txtCodFormula.getText();
            //
            NomFormulaDatPK pk = new NomFormulaDatPK(ssnRifEmpresa, codNomina, codFormula, codConcepto, accion, correlativo);
            f = new bean.controlador.concepto.NomFormulaDatJpaController().findNomFormulaDat(pk);
        } // if ( accion != null  )
        return (f);
    }  // getSeleccion()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void agregar() {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("COD_FORMULA", txtCodFormula);    // Probando pase de Parametros vs Binder.  
        Integer codNomina = valorSelectbox(slbTipoNomina);
        Integer codConcepto = valorSelectbox(slbCodConcepto);
        //
        NomFormulaDatPK nomFormulaDatPK = new NomFormulaDatPK(ssnRifEmpresa, codNomina, txtCodFormula.getText(), codConcepto, "", -1);
        NomFormulaDat nomFormulaDat = new NomFormulaDat(nomFormulaDatPK, "", "");
        //
        parametros.put("LISTBOX", lbxFormula);
        parametros.put("NOMFORMULA_DAT", nomFormulaDat);
        parametros.put("REGISTRO_NUEVO", Boolean.TRUE);
        parametros.put("COMP_PADRE",imgRefresh);  
        //parametros.put("LISTBOX",lbxFormula);  
        //
        Window winEditDialog = (Window) Executions.createComponents("../VISTA_CONCEPTO/pagDefFormulaEdit.zul", null, parametros);
        winEditDialog.doModal();
        winEditDialog.setTitle("AGREGAR REGISTRO");
    }  // agregar().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void editar(NomFormulaDat selectedFormula) {
        Map<String, Object> parametros = new HashMap<String, Object>();
        //NomFormulaDatPK nomFormulaDatPK = new NomFormulaDatPK(ssnRifEmpresa,codNomina,txtCodFormula.getText(),codConcepto,"",-1);  
        //NomFormulaDat nomFormulaDat = new NomFormulaDat(nomFormulaDatPK,"","");  
        //
        parametros.put("LISTBOX", lbxFormula);
        //parametros.put("VBOX",vbxDefFormula);  
        parametros.put("NOMFORMULA_DAT", selectedFormula);
        parametros.put("REGISTRO_NUEVO", Boolean.FALSE);
        parametros.put("WIDGET_PADRE",btnRefresh);
        //parametros.put("COMP_PAFRE",hlyFormulacion);
        parametros.put("COMP_PADRE",imgRefresh);
        //parametros.put("LISTBOX",lbxFormula);  
        //
        Window winEditDialog = (Window) Executions.createComponents("../VISTA_CONCEPTO/pagDefFormulaEdit.zul", null, parametros);
        winEditDialog.doModal();
        winEditDialog.setTitle("EDITAR REGISTRO");
    }  // editar(). 

    //--------------------------------------------------------------------------
    private Integer valorSelectbox(Selectbox slb) {
        int i = slb.getSelectedIndex();
        String cadena = (String) slb.getModel().getElementAt(i);
        cadena = cadena.substring(0, cadena.indexOf("-"));
        return (Integer.parseInt(cadena));
    } // valorSeleccionado(). 

    //--------------------------------------------------------------------------
    private void loadCbbTiposNomina() {
        List<String> lista = new bean.controlador.nomina.NomTiposNominaDatJpaController().getTiposNomina(ssnRifEmpresa);
        ListModelList modelo = new ListModelList(lista);
        slbTipoNomina.setModel(modelo);
        //selModel.addToSelection("0 CONFINDENCIAL");        // iniciar widget. => Correcto!
        modelo.addToSelection(modelo.getElementAt(0));   // Iniciar widget. => Correcto!
        //selTipoNomina.setItemRenderer();
    }

    //--------------------------------------------------------------------------
    private void loadCbbCodConceptos() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //--------------------------------------------------------------------------
    private void loadCbbCodFormulas() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //--------------------------------------------------------------------------    
    // Original:  
    //--------------------------------------------------------------------------
    private void divideTokenizer(String cadena) {
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
        String[] arrTokens = new String[MAX_TOKENS];
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

    //
    //
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public ClassArregloTokens dividirTokens(String cadena) {
        TIPO_TOKEN tipoToken = TIPO_TOKEN.BLANK;
        TIPO_TOKEN[] arrTipoToken = new TIPO_TOKEN[MAX_TOKENS];
        String[] arrTokens = new String[MAX_TOKENS];
        String token = "";
        String tokenAux = "";
        int contador = 0;
        int i = 0;
        Boolean ok, termine;
        while (i < cadena.length()) {
            ok = Boolean.FALSE;
            //System.out.println("cadena[" + i + "]=" + cadena.charAt(i));
            token = String.valueOf(cadena.charAt(i));
            //System.out.println("token="+token);   
            if (OPERADOR.contains(token)) {
                tipoToken = TIPO_TOKEN.OPERADOR;
                ok = Boolean.TRUE;
            } else if (SIMBOLO.contains(token)) {
                tipoToken = TIPO_TOKEN.SIMBOLO;
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
                    tipoToken = TIPO_TOKEN.NUMERO;
                    tokenAux = "";
                    i--;
                }  // if (ok) ...
            } else if (CONCEPTO.contains(token)) {
                /*
                 while (i < cadena.length() && (CONCEPTO.contains(token) || DIGITO.contains(token)) && (!token.equals(" "))) {
                 */
                while (i < cadena.length() && (!token.equals(" ")) && (!OPERADOR.contains(token)) && (!SIMBOLO.contains(token)) && (!SIGNO.contains(token))) {
                    tokenAux = tokenAux + token;
                    ok = Boolean.TRUE;
                    i++;
                    if (i < cadena.length()) {
                        token = String.valueOf(cadena.charAt(i));
                    }
                } // while ( i < cadena.length() && (CONCEPTO.contains(token) ...
                if (ok) {
                    token = tokenAux;
                    tipoToken = TIPO_TOKEN.CONCEPTO;
                    tokenAux = "";
                    i--;
                }  // if (ok) ...
            } else if (!BLANK.contains(token)) {   // By default.  ??? elemento desconocido ???  
                //System.out.println("Token NO Aplica  ====???????????????????????????????????'");  
                tipoToken = TIPO_TOKEN.NO_APLICA;
                ok = Boolean.TRUE;
            } // 
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
    public TIPO_TOKEN tipoToken(String token) {
        TIPO_TOKEN tipoToken = TIPO_TOKEN.NO_APLICA;
        if (SIMBOLO.contains(token)) {
            tipoToken = TIPO_TOKEN.SIMBOLO;
        } else if (SIGNO.contains(token) || DIGITO.contains(token.substring(0, 1)) || (OPERADOR.contains(token.substring(0, 1)) && token.substring(0, 1).equals("-"))) {
            tipoToken = TIPO_TOKEN.NUMERO;
        } else if (OPERADOR.contains(token)) {
            tipoToken = TIPO_TOKEN.OPERADOR;
        } else if (CONCEPTO.contains(token.substring(0, 1))) {
            tipoToken = TIPO_TOKEN.CONCEPTO;
        }
        return (tipoToken);
    }  // tipoToken().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public ClassArregloTokens validarEcuacion(ClassArregloTokens classArrTokens) {
        LibreriaHP libHP = new bean.utilitario.libreria.LibreriaHP();
        ClassArregloTokens newClassArrTokens = null;
        String[] t = classArrTokens.getArregloTokens();
        String dumy, tokenAnterior, token = null;
        String[] t2 = new String[MAX_TOKENS];
        TIPO_TOKEN[] l = classArrTokens.getArregloTipoToken();
        TIPO_TOKEN[] l2 = new TIPO_TOKEN[MAX_TOKENS];
        TIPO_TOKEN tipoToken, tipoTokenAnterior;
        Boolean termina = Boolean.FALSE;
        Pila pilaSimbolo = new Pila();
        int j = 0;
        int i = 0;
        while (i < classArrTokens.getContadorTokens() && l[i] != TIPO_TOKEN.NO_APLICA && !termina) {
            token = t[i];
            //System.out.println("token[i]=" + t[i]);
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
                        if ((tokenAnterior.equals("(") || tokenAnterior.equals("[")) && (tipoToken == TIPO_TOKEN.OPERADOR)) {
                            //token = t[i];
                            switch (token) {
                                case "+":
                                case "-": {
                                    t2[j] = "0.00";
                                    l2[j] = TIPO_TOKEN.NUMERO;
                                    j++;
                                    break;
                                }
                                case "*": {
                                    t2[j] = "1.00";
                                    l2[j] = TIPO_TOKEN.NUMERO;
                                    j++;
                                    break;
                                }
                                case "/": {
                                    Messagebox.show("Omision de Numerador en formula/ecuacion. Check: '" + tokenAnterior + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                                    termina = Boolean.TRUE;
                                    break;
                                }
                            }  // switch
                        } else if ((tokenAnterior.equals("]") || tokenAnterior.equals(")")) && (tipoToken == TIPO_TOKEN.NUMERO || tipoToken == TIPO_TOKEN.CONCEPTO)) {
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
                }  // switch (tipoTokenAnterior)
            }  // if (i > 0)
            if (tipoToken == TIPO_TOKEN.SIMBOLO) {
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
            } else if (tipoToken == TIPO_TOKEN.NUMERO) {
                token = libHP.convFormatoNumericoBD(token);
                if (!libHP.numeroValido(token)) {
                    Messagebox.show("Numero incorrecto en la formula/ecuacion. Check: '" + token + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                    termina = Boolean.TRUE;
                } else {
                    t[i] = token;
                }
            } else if (tipoToken == TIPO_TOKEN.CONCEPTO) {
                //Integer codConcepto = Integer.parseInt(token.substring(1, token.length()));
                //System.out.println("token=" + token + "  subStr.Cod concepto=" + codConcepto + "*****");
                //ServicioAdmonConcepto servAdmConcepto = new bean.servicio.ServicioAdmonConcepto();
                //if (!servAdmConcepto.conceptoRegistrado(codNominaProceso, codConcepto) || !servAdmConcepto.calculoConceptoRegistrado(codNominaProceso, codConcepto, nroTrabajador)) {
                //if (!servAdmConcepto.conceptoRegistrado(codNominaProceso, codConcepto)) {
                //    Messagebox.show("El Concepto " + token.substring(1) + " para el trabajador (ficha) : " + nroTrabajador + " no esta Registrado o Calculado.", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                //    termina = Boolean.TRUE;
                //}
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
    }  // validarEcuacion(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public ClassArregloTokens validarEcuacionNomina(Integer codNominaProceso, Integer nroTrabajador, ClassArregloTokens classArrTokens) {
        LibreriaHP libHP = new bean.utilitario.libreria.LibreriaHP();
        ClassArregloTokens newClassArrTokens = null;
        String[] t = classArrTokens.getArregloTokens();
        String dumy, tokenAnterior, token = null;
        String[] t2 = new String[MAX_TOKENS];
        TIPO_TOKEN[] l = classArrTokens.getArregloTipoToken();
        TIPO_TOKEN[] l2 = new TIPO_TOKEN[MAX_TOKENS];
        TIPO_TOKEN tipoToken, tipoTokenAnterior;
        Boolean termina = Boolean.FALSE;
        Pila pilaSimbolo = new Pila();
        int j = 0;
        int i = 0;
        while (i < classArrTokens.getContadorTokens() && l[i] != TIPO_TOKEN.NO_APLICA && !termina) {
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
                        if ((tokenAnterior.equals("(") || tokenAnterior.equals("[")) && (tipoToken == TIPO_TOKEN.OPERADOR)) {
                            //token = t[i];
                            switch (token) {
                                case "+":
                                case "-": {
                                    t2[j] = "0.00";
                                    l2[j] = TIPO_TOKEN.NUMERO;
                                    j++;
                                    break;
                                }
                                case "*": {
                                    t2[j] = "1.00";
                                    l2[j] = TIPO_TOKEN.NUMERO;
                                    j++;
                                    break;
                                }
                                case "/": {
                                    Messagebox.show("Omision de Numerador en formula/ecuacion. Check: '" + tokenAnterior + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                                    termina = Boolean.TRUE;
                                    break;
                                }
                            }  // switch
                        } else if ((tokenAnterior.equals("]") || tokenAnterior.equals(")")) && (tipoToken == TIPO_TOKEN.NUMERO || tipoToken == TIPO_TOKEN.CONCEPTO)) {
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
            if (tipoToken == TIPO_TOKEN.SIMBOLO) {
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
            } else if (tipoToken == TIPO_TOKEN.NUMERO) {
                token = libHP.convFormatoNumericoBD(token);
                if (!libHP.numeroValido(token)) {
                    Messagebox.show("Numero incorrecto en la formula/ecuacion. Check: '" + token + "'", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                    termina = Boolean.TRUE;
                } else {
                    t[i] = token;
                }
            } else if (tipoToken == TIPO_TOKEN.CONCEPTO) {
                //
                /*
                 Integer codConcepto = Integer.parseInt(token.substring(1, token.length()));
                 //System.out.println("token=" + token + "  subStr.Cod concepto=" + codConcepto + "*****");
                 ServicioAdmonConcepto servAdmConcepto = new bean.servicio.ServicioAdmonConcepto();
                 //if (!servAdmConcepto.conceptoRegistrado(codNominaProceso, codConcepto) || !servAdmConcepto.calculoConceptoRegistrado(codNominaProceso, codConcepto, nroTrabajador)) {
                 if (!servAdmConcepto.conceptoRegistrado(codNominaProceso, codConcepto)) {
                 Messagebox.show("El Concepto " + token.substring(1) + " para el trabajador (ficha) : " + nroTrabajador + " no esta Registrado o Calculado.", "ATENCION", Messagebox.OK, Messagebox.EXCLAMATION);
                 termina = Boolean.TRUE;
                 }
                 */
                //
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
    //--------------------------------------------------------------------------
    @Override
    public Double procesarEcuacion(ClassArregloTokens classArregloTokens) {
        Double r = 0.00;
        Double v1 = 0.00;
        Double v2 = 0.00;
        Pila pilaSimbolo = new Pila();
        Pila pilaSimbolo2 = new Pila();
        Pila pila1 = new Pila();
        Pila pila2 = new Pila();
        String[] t = classArregloTokens.getArregloTokens();
        String simbolo = null;
        String operador = null;
        String dumy = null;
        TIPO_TOKEN[] l = classArregloTokens.getArregloTipoToken();
        Integer contTokens = classArregloTokens.getContadorTokens();
        Integer i = 0;
        //----------------------------------------------------------------------
        //System.out.println("-----------------------------------------");
        //System.out.print("ARR TOKENS=");
        //for (int k = 0; k < classArregloTokens.getContadorTokens(); k++) {
        //    System.out.print(t[k] + " ");
        //}
        //System.out.println();
        //for (int k = 0; k < classArregloTokens.getContadorTokens(); k++) {
        //    System.out.print(l[k] + " ");
        //}
        //System.out.println();
        //System.out.println("-----------------------------------------");
        //----------------------------------------------------------------------
        while (i <= contTokens) {
            if (l[i] == TIPO_TOKEN.SIMBOLO) {  // "[","]","(","(" 
                if (pilaSimbolo.vacia()) {
                    if (t[i].equals("]") || t[i].equals(")")) {
                        //alert("ATENCION ;=( desbalance en la construccion de(l/los) parentesis/corchetes.");
                        System.out.println("ATENCION (E1): desbalance en la construccion del(los) parentesis/corchetes.");
                        return (-1.00);  // ERROR =(
                    }
                    if (t[i].equals("[") || t[i].equals("(")) {
                        pilaSimbolo.push(t[i]);
                    }
                } else if (!pilaSimbolo.vacia()) {
                    if (t[i].equals("(") || t[i].equals("[")) {
                        String[] t2 = new String[MAX_TOKENS];
                        TIPO_TOKEN[] l2 = new TIPO_TOKEN[MAX_TOKENS];
                        int j = -1;
                        t2[++j] = t[i];
                        l2[j] = l[i];
                        pilaSimbolo2.push(t[i]);
                        while (!pilaSimbolo2.vacia() && i <= contTokens) {
                            i++;
                            if (t[i].equals("(") || t[i].equals("[")) {
                                pilaSimbolo2.push(t[i]);
                            } else if ((pilaSimbolo2.tope().equals("[") && t[i].equals("]")) || (pilaSimbolo2.tope().equals("(") && t[i].equals(")"))) {  //--> t[i] = "]",")" 
                                dumy = (String) pilaSimbolo2.pop();
                            }
                            t2[++j] = t[i];
                            l2[j] = l[i];
                            //alert("Continuar");
                        } // while
                        if (!pilaSimbolo2.vacia()) {
                            System.out.println("ATENCION (E2) ;=( desbalance en la construccion del(los) parentesis/corchetes.");
                            return (-1.00);  // ERROR =(
                        } else {
                            ClassArregloTokens classArrTokens2 = new ClassArregloTokens(j + 1, t2, l2);
                            r = procesarEcuacion(classArrTokens2); // ** Aplicar recursividad **
                            System.out.println("Resultado parcial subEcuacion=" + r + "----");
                            pila1.push(r);
                        }
                    } else if (t[i].equals("]") || t[i].equals(")")) {
                        simbolo = (String) pilaSimbolo.pop();
                        if (simbolo == null) {
                            System.out.println("ATENCION (E3) ;=( desbalance en la construccion del(los) parentesis/corchetes.");
                            return (-1.00);  // ERROR =(
                        } else if ((simbolo.equals("[") && t[i].equals("]") || (simbolo.equals("(") && t[i].equals(")")))) {
                            //System.out.println("***** PROCESAR pila: ******");
                            //------------------------------------------------
                            while (!pila1.vacia()) {  // <<<< Procesar "*" "/" >>>>>
                                v2 = Double.parseDouble(pila1.pop().toString());
                                if (!pila1.vacia()) {
                                    operador = (String) pila1.pop();
                                } else {
                                    pila2.push(v2);
                                    operador = null;
                                }
                                if (operador != null && (operador.equals("*") || operador.equals("/"))) {
                                    v1 = Double.parseDouble(pila1.pop().toString());
                                    switch (operador) {
                                        case "/": {
                                            if (v2 == Double.valueOf(0)) {
                                                return (-1.00);  // Operacion NO DEFINIDA. 
                                            } else {
                                                r = v1 / v2;
                                                break;
                                            }
                                        }  // case "/":
                                        case "*":
                                            r = v1 * v2;
                                            break;
                                    }  // switch().
                                    pila1.push(r);
                                } else if (operador != null && (operador.equals("+") || operador.equals("-"))) {
                                    pila2.push(v2);
                                    pila2.push(operador);
                                }
                            } // while (!pila1.vacia()),..
                            //
                            //************************OJO***************************
                            //System.err.println("***ini.CHEQUEAR Stack pila2: ");
                            //while (!pila2.vacia()) {
                            //    System.out.println("pila2=" + pila2.pop());
                            //}
                            //System.err.println("***fin.CHEQUEAR Stack pila2: ");
                            //*****************************************************
                            //
                            while (!pila2.vacia()) {
                                v2 = Double.parseDouble(pila2.pop().toString());
                                if (!pila2.vacia()) {
                                    operador = (String) pila2.pop();
                                } else {
                                    operador = null;
                                }
                                if (operador != null && (operador.equals("+") || operador.equals("-"))) {
                                    v1 = Double.parseDouble(pila2.pop().toString());
                                    r = 0.00;
                                    switch (operador) {
                                        case "+":
                                            r = v1 + v2;
                                            break;
                                        case "-":
                                            r = v2 - v1;
                                            break;
                                    }  // switch
                                    pila2.push(r);
                                }
                            }  // while (!pila2.vacia()),..
                            pila1.push(r);
                        } else {
                            //alert("ATENCION ;=( desbalance en la construccion de(l/los) parentesis/corchetes.");
                            System.out.println("ATENCION (E4): desbalance en la construccion del(los) parentesis/corchetes.");
                            return (-1.00);
                        }
                    }
                }
            } else { // 
                pila1.push(t[i]);
            }
            i++;
        }  // while
        if (!pilaSimbolo.vacia()) {
            //alert("ATENCION ;=( desbalance en la construccion de(l/los) parentesis/corchetes.");
            System.out.println("ATENCION (E5): desbalance en la construccion del(los) parentesis/corchetes.");
            r = -1.00;
        }
        return (r);
    } // procesarEcuacion()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public Double procesarEcuacionNomina(ClassArregloTokens classArregloTokens) {
        Double r = 0.00;
        Double v1 = 0.00;
        Double v2 = 0.00;
        Pila pilaSimbolo = new Pila();
        Pila pilaSimbolo2 = new Pila();
        Pila pila1 = new Pila();
        Pila pila2 = new Pila();
        String[] t = classArregloTokens.getArregloTokens();
        String simbolo = null;
        String operador = null;
        String dumy = null;
        TIPO_TOKEN[] l = classArregloTokens.getArregloTipoToken();
        Integer contTokens = classArregloTokens.getContadorTokens();
        Integer i = 0;
        //----------------------------------------------------------------------
        //System.out.println("-----------------------------------------");
        //System.out.print("ARR TOKENS=");
        //for (int k = 0; k < classArregloTokens.getContadorTokens(); k++) {
        //    System.out.print(t[k] + " ");
        //}
        //System.out.println();
        //for (int k = 0; k < classArregloTokens.getContadorTokens(); k++) {
        //    System.out.print(l[k] + " ");
        //}
        //System.out.println();
        //System.out.println("-----------------------------------------");
        //----------------------------------------------------------------------
        while (i <= contTokens) {
            if (l[i] == TIPO_TOKEN.SIMBOLO) {  // "[","]","(","(" 
                if (pilaSimbolo.vacia()) {
                    if (t[i].equals("]") || t[i].equals(")")) {
                        //alert("ATENCION ;=( desbalance en la construccion de(l/los) parentesis/corchetes.");
                        System.out.println("ATENCION (E1): desbalance en la construccion del(los) parentesis/corchetes.");
                        return (-1.00);  // ERROR =(
                    }
                    if (t[i].equals("[") || t[i].equals("(")) {
                        pilaSimbolo.push(t[i]);
                    }
                } else if (!pilaSimbolo.vacia()) {
                    if (t[i].equals("(") || t[i].equals("[")) {
                        String[] t2 = new String[MAX_TOKENS];
                        TIPO_TOKEN[] l2 = new TIPO_TOKEN[MAX_TOKENS];
                        int j = -1;
                        t2[++j] = t[i];
                        l2[j] = l[i];
                        pilaSimbolo2.push(t[i]);
                        while (!pilaSimbolo2.vacia() && i <= contTokens) {
                            i++;
                            if (t[i].equals("(") || t[i].equals("[")) {
                                pilaSimbolo2.push(t[i]);
                            } else if ((pilaSimbolo2.tope().equals("[") && t[i].equals("]")) || (pilaSimbolo2.tope().equals("(") && t[i].equals(")"))) {  //--> t[i] = "]",")" 
                                dumy = (String) pilaSimbolo2.pop();
                            }
                            t2[++j] = t[i];
                            l2[j] = l[i];
                            //alert("Continuar");
                        } // while
                        if (!pilaSimbolo2.vacia()) {
                            System.out.println("ATENCION (E2) ;=( desbalance en la construccion del(los) parentesis/corchetes.");
                            return (-1.00);  // ERROR =(
                        } else {
                            ClassArregloTokens classArrTokens2 = new ClassArregloTokens(j + 1, t2, l2);
                            r = procesarEcuacionNomina(classArrTokens2); // ** Aplicar RECURSIVIDAD **
                            //System.out.println("Resultado parcial subEcuacion=" + r + "----");
                            pila1.push(r);
                        }
                    } else if (t[i].equals("]") || t[i].equals(")")) {
                        simbolo = (String) pilaSimbolo.pop();
                        if (simbolo == null) {
                            System.out.println("ATENCION (E3) ;=( desbalance en la construccion del(los) parentesis/corchetes.");
                            return (-1.00);  // ERROR =(
                        } else if ((simbolo.equals("[") && t[i].equals("]") || (simbolo.equals("(") && t[i].equals(")")))) {
                            //System.out.println("***** PROCESAR pila: ******");
                            //------------------------------------------------
                            while (!pila1.vacia()) {  // <<<< Procesar "*" "/" >>>>>
                                v2 = Double.parseDouble(pila1.pop().toString());
                                if (!pila1.vacia()) {
                                    operador = (String) pila1.pop();
                                } else {
                                    pila2.push(v2);
                                    operador = null;
                                }
                                if (operador != null && (operador.equals("*") || operador.equals("/"))) {
                                    v1 = Double.parseDouble(pila1.pop().toString());
                                    switch (operador) {
                                        case "/": {
                                            if (v2 == Double.valueOf(0)) {
                                                return (-1.00);  // Operacion NO DEFINIDA. 
                                            } else {
                                                r = v1 / v2;
                                                break;
                                            }
                                        }  // case "/":
                                        case "*":
                                            r = v1 * v2;
                                            break;
                                    }  // switch().
                                    pila1.push(r);
                                } else if (operador != null && (operador.equals("+") || operador.equals("-"))) {
                                    pila2.push(v2);
                                    pila2.push(operador);
                                }
                            } // while (!pila1.vacia()),..
                            //
                            //************************OJO***************************
                            //System.err.println("***ini.CHEQUEAR Stack pila2: ");
                            //while (!pila2.vacia()) {
                            //    System.out.println("pila2=" + pila2.pop());
                            //}
                            //System.err.println("***fin.CHEQUEAR Stack pila2: ");
                            //*****************************************************
                            //
                            while (!pila2.vacia()) {
                                v2 = Double.parseDouble(pila2.pop().toString());
                                if (!pila2.vacia()) {
                                    operador = (String) pila2.pop();
                                } else {
                                    operador = null;
                                }
                                if (operador != null && (operador.equals("+") || operador.equals("-"))) {
                                    v1 = Double.parseDouble(pila2.pop().toString());
                                    r = 0.00;
                                    switch (operador) {
                                        case "+":
                                            r = v1 + v2;
                                            break;
                                        case "-":
                                            r = v2 - v1;
                                            break;
                                    }  // switch
                                    pila2.push(r);
                                }
                            }  // while (!pila2.vacia()),..
                            pila1.push(r);
                        } else {
                            //alert("ATENCION ;=( desbalance en la construccion de(l/los) parentesis/corchetes.");
                            System.out.println("ATENCION (E4): desbalance en la construccion del(los) parentesis/corchetes.");
                            return (-1.00);
                        }
                    }
                }
            } else { // 
                pila1.push(t[i]);
            }
            i++;
        }  // while
        if (!pilaSimbolo.vacia()) {
            //alert("ATENCION ;=( desbalance en la construccion de(l/los) parentesis/corchetes.");
            System.out.println("ATENCION (E5): desbalance en la construccion del(los) parentesis/corchetes.");
            r = -1.00;
        }
        return (r);
    } // procesarEcuacionNomina()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Double getValorTokenOLD(String token, Integer codNomina, Integer nroTrabajador, java.util.Date fechaNomina) {
        String tipoVar = null;
        Double valor = 0.00;
        Integer codConcepto = 0;
        if (tipoToken(token) == TIPO_TOKEN.NUMERO) {
            valor = Double.parseDouble(token);
            //System.out.println(" Double.parseDouble(token)="+token+" ");  
        } else if (tipoToken(token) == TIPO_TOKEN.CONCEPTO) {
            tipoVar = token.substring(0, 1);
            //System.out.println("getValorToken tipoVar="+tipoVar+" codConcepto="+token.substring(1, token.length()));  
            codConcepto = Integer.parseInt(token.substring(1, token.length()));
            NomCalculoDat n = new ServicioAdmonConcepto().getRegistroCalculado(ssnRifEmpresa, codNomina, codConcepto, nroTrabajador, fechaNomina);
            //System.out.println("getValorToken tipoVar="+tipoVar+" codConcepto="+codConcepto+" ***Nomina="+codNomina+" ***Trab="+nroTrabajador+" ***fechaNom="+fechaNomina+" ****");  
            if (n == null) {
                return 0.00;
            } else {
                switch (tipoVar) {
                    case "C": {
                        valor = n.getCantidad();
                        break;
                    }
                    case "M": {
                        valor = n.getMonto();
                        break;
                    }
                    case "P": {
                        valor = n.getPorcentaje();
                        break;
                    }
                    case "T": {
                        valor = n.getTotal();
                        break;
                    }
                    default: {
                        valor = 0.00;
                    }
                }  // switch 
            }
        } else {
            System.out.println("****Que token es esto?='" + token + "', tipo token=" + tipoToken(token) + "****");
        }
        //System.out.println(" (getValorToken) Ido,............................."); 
        //return (Double.parseDouble(token));
        return (valor);
    } // getValorTokenOLD().

    //--------------------------------------------------------------------------
    // Verificar si la cadena o formula o ecuacion posee Terminos NO definidos 
    // o NO APLICABLES al desarrollo. 
    //--------------------------------------------------------------------------
    public Boolean tokenValidos(ClassArregloTokens classArrTokens) {
        Boolean ok = Boolean.TRUE;
        Integer i = 0;
        TIPO_TOKEN[] l = classArrTokens.getArregloTipoToken();  // logica token
        while (i < classArrTokens.getContadorTokens() && ok) {
            if (l[i].equals(TIPO_TOKEN.NO_APLICA)) {
                ok = Boolean.FALSE;
            }
            i++;
        }  // while 
        return (ok);
    }  // tokenValidos(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean palabrasReservadas(String[] palabras, Integer max) {
        Boolean ok = Boolean.TRUE;
        for (Integer i = 0; i <= max && ok; i++) {
            if (!CONJUNTO_PALABRAS_RESERVADAS.contains(palabras[i])) {
                ok = Boolean.FALSE;
            }
        } // for
        return (ok);
    } // palabraReservada(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean argumentosValidos(String[] argumento, Integer max) {
        Boolean ok = Boolean.TRUE;
        LibreriaHP libHP = new LibreriaHP();
        String token = null;
        for (Integer i = 0; i <= max && ok; i++) {
            token = argumento[i];                            // Num valido = #999.99  // sin separador decimal !!!
            if (DIGITO.contains(token.substring(0, 1))) {     // Es un numero.
                if (!libHP.numeroValido(token)) {
                    //System.err.println(argumento[i] + "NUMERO con Problema.................????");
                    ok = Boolean.FALSE;
                }
            } else {   // Es un concepto.   
                // System.err.println("Token=" + token + " subStr()=" + token.substring(0, 1).toUpperCase() + " ************???");
                if (!CONCEPTO.contains(token.substring(0, 1).toUpperCase())) {
                    //System.err.println("*****PROBLEMA CON EL Token " + token + "?????********");
                    ok = Boolean.FALSE;
                }
            }
        } // for
        return (ok);
    } // argumentosValidos(). 

    //--------------------------------------------------------------------------
    //  Autor:  HJPB.
    //  Creado: 11 diciembre 2020.
    //  * calcularTotal  *Formulado()* 
    //--------------------------------------------------------------------------
    private void calcularTotal(String rifEmpresa, Integer codNomina, Integer nroTrabjador, Integer codConcepto) {

    } //  * calcularTotal  *Formulado()*

    //--------------------------------------------------------------------------
    // CONDICION NECESARIA: 
    // (1) - var 'condicion' debe estar en mayuscula ; y 
    // (2) - var 'condicion' debe estar validada : NOT NULL / NOT EMPTY.  
    //  ---------*mapa estructural*---------------------------------------------
    //  t0  t1     t2           t3    t4   t5   t6  t7     t8    
    //  SI  <arg1> CONDICIONAL  <arg2> ; <arg3> : <arg4> OPCIONAL 
    //  a0         a1                 a2        a3       a4
    //--------------------------------------------------------------------------
    public Boolean validarCondicion(String condicion) {   // de argumentos discretos, un valor, un concepto ..  <--> un solo termino. 
        final int MAX_TOKENS = 9;
        final int MIN_TOKENS = 8;
        Boolean ok = Boolean.TRUE;
        ClassArregloTokens token = dividirTokenizer(condicion);
        Integer contToken = 0;
        Integer cantToken = token.getContadorTokens() + 1;           // enumerados desde 0 -> Ajustar + 1.  
        if (cantToken < MIN_TOKENS || cantToken > MAX_TOKENS) {
            //System.err.println("*****PROBLEMA cantToken=" + cantToken + "********");
            ok = Boolean.FALSE;
        } else {  // aqui aseguro que vengan 8 o 9 tokens. (i) ** Validar palabras resrvadas
            String[] t = token.getArregloTokens();
            String[] tokenAux = new String[MAX_TOKENS];
            tokenAux[0] = t[0];
            tokenAux[1] = t[2];
            tokenAux[2] = t[4];
            tokenAux[3] = t[6];
            contToken = 3;         // enumerado desde 0
            if (cantToken == MAX_TOKENS) {
                tokenAux[4] = t[8];
                contToken = 4;     // enumerado desde 0
            }
            if (!palabrasReservadas(tokenAux, contToken)) {
                Messagebox.show("REVISAR PALABRA(S) RESERVADA(S)", "ATENCION", Messagebox.OK, Messagebox.ERROR);
                ok = Boolean.FALSE;
            }
            if (ok) {  // (ii). Validar argumentos
                tokenAux[0] = t[1];
                tokenAux[1] = t[3];
                tokenAux[2] = t[5];
                tokenAux[3] = t[7];
                contToken = 3;  // enumerado desde 0
                if (!argumentosValidos(tokenAux, contToken)) {
                    Messagebox.show("PROBLEMA CON LOS ARGUMENTOS DEL CONDICIONAL", "ATENCION", Messagebox.OK, Messagebox.ERROR);
                    ok = Boolean.FALSE;
                }
            }  //  if ( ok ) {  // (ii). Validar argumentos
        }  //  
        return (ok);
    } // validarCondicion()

    // *************************************************************************
    //                   ****** NO ELIMINAR **********
    // *************************************************************************
    private void prueba() {
        System.out.println("**************************************************");
        System.out.println("*PROBAR: validarEcuacionNomina: ******************");
        System.out.println("**************************************************");
        //--------------------------------------
        // **** INI.DATOS PARA LA PRUEBA: *****
        //--------------------------------------
        java.util.Date fechaNomProceso = null;
        try {
            fechaNomProceso = new LibreriaHP().formatoFecha.parse("15/11/2020");

        } catch (ParseException ex) {
            Logger.getLogger(ControladorDefFormula.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        Integer nominaEnProceso = 1;    // OJO. 
        Integer nroTrabajador = 1003;
        // **** FIN.DATOS PARA LA PRUEBA: *****      
        //                                                     // *** IMPORTANTISIMO ****
        String ecuacion = "100-M10+ C106 -( - 1,29.04djf )";   // **** OK positivo Correcto ✔✔ Eeeeeeeexiiiiiitoooooooooo!!!!!!
        //                                                     // Usando AI, el metodo infiere que '1,29.04djf' es = 129.04 ✔✔✔✔                                                     
        //
        //ecuacion = "100-200*C200+[ 500-(67.89+(10+20))*333.33333-(100*M200-150*2-50)/2 ] + "; 
        //ecuacion = "100-200*T200+[ 500-(67.89+(10+20))*333.33333-(100*M200-150*2-50)/2 ] + 10.98";  // CORRECTO (*RESULTADO PRUEBA I=-118844.01967370001**********) para (Nom=2, Trab=58) ( Bqto: 17 dic 18  )
        //ecuacion = "(*100)/jdjfhjfff10992+C200*(1/2)";   // ( **** Correcto: (**RESULTADO PRUEBA I=3.5090975254730714******). Bqto: 17/12/2018 14:47. 
        //ecuacion = "(*100)/jdjfhjfff10992+C200*(0/2)";   // ( **** Correcto: (**RESULTADO PRUEBA I=3.5090975254730714******). Bqto: 17/12/2018 14:47. 
        //ecuacion = "(*100)/jdjfhjfff10992+C200*(2/0)";   // ( **** Correcto: (**RESULTADO PRUEBA I=3.5090975254730714******). Bqto: 17/12/2018 14:47. 
        //cuacion = "O890 + R100";
        ecuacion = "R1 * VDHQ";
        ecuacion = "VDHQ";
        ecuacion = "FLUNES";  
        System.out.println(" Ecuacion=" + ecuacion + " ");
        ecuacion = "[" + ecuacion + "+ 0.00 ]"; // formatear ecuacion...!
        ClassArregloTokens classArrTokens = dividirTokens(ecuacion);  //
        String[] t = classArrTokens.getArregloTokens();         // array token
        TIPO_TOKEN[] l = classArrTokens.getArregloTipoToken();  // logica token
        t = classArrTokens.getArregloTokens();
        l = classArrTokens.getArregloTipoToken();
        for (int i = 0; i < classArrTokens.getContadorTokens(); i++) {
            System.out.print(t[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < classArrTokens.getContadorTokens(); i++) {
            System.out.print(l[i] + " ");
        }
        //******************************************************
        // * validar Ecuacion de Nomina * 
        //*******************************************************
        //classArrTokens = validarEcuacionNomina(nominaEnProceso, nroTrabajador, classArrTokens);   // OJO 
        classArrTokens = validarEcuacion(classArrTokens);
        if (classArrTokens != null) {
            System.out.println(" Ecuacion VALIDADA=");
            //******************************************************
            // * procesar ( get el resultado ) Ecuacion de Nomina * 
            //*******************************************************
            System.out.println();
            System.out.println(" -> es CORRECTA  ;=)");
            Double total = new bean.controlador.concepto.ControladorAsigConcepto().procesarEcuacionNomina(nominaEnProceso, nroTrabajador, fechaNomProceso, classArrTokens);  // procesarEcuacionNomina(nominaEnProceso, nroTrabajador, fechaNomProceso, classArrTokens);  
            System.out.println("**RESULTADO PRUEBA ( procesarEcuacionNomina )=" + total + "**********");
            System.out.println("(( FIN PRUEBA I))");
        } else {
            System.out.println(" ******* ECUACION INCORRECTA ;=(  ************");
        }
    }

    //--------------------------------------------------------------------------
    // Autor:  HJPB _ NO ELMINAR **
    //--------------------------------------------------------------------------
    private void pruebaCondicionTokenizer() {
        String cadena = "SI R100 MENOR R200 ; 1.00 : 2.00 TERMINAR";   // Verdadero y Correcto  
        cadena = "R100 MENOR R200 ; 1.00 : 2.00 TERMINAR";             // Falso positivo
        cadena = "SIOOP R100 MENOR R200 ; 1.00 : 2.00 TERMINAR";       // Falso positivo
        cadena = "SI t100 DIF R200 ; 1.0088olld : 2.00";               // Falso positivo
        cadena = "SI t100 DIF m2 ; 1.0088 : 2.00 continuar";           // Verdadero positivo
        cadena = "SI t100 DIFerente m2 ; 1.0088 : 2.00 continuar";     // Falso positivo.    
        cadena = "SI t100 DIFerente m2 ; 1.0088 : Tontinuar";     // Falso positivo.
        cadena = "SI t100 ; 1.0088 : 2.00 Continuar";     // Falso positivo.
        cadena = cadena.toUpperCase();
        System.out.println("------------Prueba en DESARROLLO--------------");
        System.out.println("FORMULA/ECUACION=" + cadena);
        System.out.println("cadena divida en tokens: ");
        ClassArregloTokens tokens = dividirTokenizer(cadena);
        if (tokens != null) {
            String[] t = tokens.getArregloTokens();
            for (int i = 0; i <= tokens.getContadorTokens(); i++) {
                System.out.print(t[i] + " ");
            }  // for
            System.out.println();
            System.out.println("---------------Validar------------------------");
            if (validarCondicion(cadena)) {
                System.out.println(cadena + " ===/> condicion VALIDA!!!");
            } else {
                System.out.println(cadena + " ===/> condicion NO VALIDA!!!");
            }
            System.out.println("----------------------------------------------");
        }
    } // pruebaTokenizer().  

}
