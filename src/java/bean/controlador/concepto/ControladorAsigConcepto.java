/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.modelo.entidad.NomAsigConceptoDat;
import bean.modelo.entidad.NomCalculo1Dat;
import bean.modelo.entidad.NomCalculo1DatPK;
import bean.modelo.entidad.NomCalculoDat;
import bean.modelo.entidad.NomCalculoDatPK;
import bean.modelo.entidad.NomFormulaDat;
import bean.modelo.entidad.NomTiposNominaDat;
import bean.modelo.entidad.NomTiposNominaDatPK;
import bean.modelo.entidad.NomVariableDat;
import bean.modelo.entidad.NomVariableDatPK;
import bean.utilitario.libreria.ClassArregloTokens;
import bean.utilitario.libreria.LibreriaHP;
import bean.utilitario.libreria.Pila;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author hpulgar
 */
public class ControladorAsigConcepto extends GenericForwardComposer {

    final int SI = 1;
    final int NO = 0;
    final String CONDICION = "SI";
    final static String CONDICIONALES[] = {"MAYOR", "MAYOR_IGUAL", "MENOR", "MENOR_IGUAL", "IGUAL", "DIF"};
    final int CONT_CONDICIONALES = 6;
    private Button btnAdd, btnEdit, btnLimpiarKeys;
    private Textbox txtKeyCodNomina, txtKeyNroTrab, txtKeyNombre, txtKeyCodConcepto, txtKeyDescrip;
    private Listbox lbxAsigConceptos;
    private Image imgNomOpcion1, imgNomOpcion2;
    private Datebox dtbFechaCalculo, dtbFechaCierre;
    private Combobox cbbTipoNomina, cbbTipoNominaCierre;
    String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    String baseCalculo;   //  Frecuencia de Calculo de nomina-dependiente de la seleccion del usuario. 
    Integer tipoNomina = 0;
    final int MAX_TOKENS = 80;
    //*//////////////////////////////////////////////////////////////////////////
    //(INI)----*Declarativas para gestionar arreglo Calculo --------------------
    final int FILAS_SIZE = 999;   // x Trabajador
    final int COLS_SIZE = 6;   // (1) codTrabajador, (2)codConcepto (3) cantidad (4) monto (5) porcentaje (6) total
    //
    Double[][] arrCalculo = new Double[FILAS_SIZE][COLS_SIZE];
    Integer contFila = 0;
    //---------*Declarativas para gestionar arreglo Calculo----------------(END)
    Integer cantLunes = 0;

    //
    enum Atributo {

        TRABAJADOR, COD_CONCEPTO, CANTIDAD, MONTO, PORCENTAJE, TOTAL
    }

    // **/////////////////////////////////////////////////////////////////////**
    //
    //-----------------*Gestion LOGICA DE CALCULO - RECETA*----------------(FIN)
    enum Indice {

        NOMINA, COD_CONCEPTO, CORRELATIVO, TOTAL    // ACCION = [R,C,T] .. R)esultado parcial, C)valor C)antidad y/o T)otal.  
    }
    final int COLS_FORMULA_SIZE = 5;
    //
    Integer[][] arrIndice = new Integer[FILAS_SIZE][COLS_FORMULA_SIZE];

    //
    enum IndiceF {

        ACCION, FORMULA
    };
    String[][] arrFormula = new String[FILAS_SIZE][2]; // Array de F)ormulas. col 1 = ACCION. col 2 = FORMULA itself.  
    //

    final int FILAS_RP_SIZE = 20;

    //Double[] arrResultadoParcial = new Double[FILAS_RP_SIZE];
    enum IndiceR {

        CORRELATIVO, CANTIDAD, VALOR
    };
    Double[][] R = new Double[FILAS_RP_SIZE][3];   // Array de R)esultados parciales. col 1 = indice del Resultado parcial. col2 = Valor accion CANTIDAD col 3 = valor itself (RESULTADO).  

    //-----------------*Gestion LOGICA DE CALCULO - RECETA*----------------(FIN)
    //--------------------------------------------------------------------------
    final String BLANK = " ";
    final String OPERADOR = "+-/*";
    final String SIMBOLO = "()[]";
    final String SIGNO = ",.";  // signo de puntuacion. 
    final String DIGITO = "0123456789";
    final String CONCEPTO = "RVPCMTF";  // |-> V)ariable, P)romedio/Porcentaje, C)antidad, M)onto, F)uncion, R)esultado parcial y/o T)otal.  
    // -----------------------------------*gestion CONDICION*------------------
    //

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // << intrucciones del usuario >>
        //certificarUsuario(); 
        //checkEventQueves();  
        //prueba();
        //String d = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()); 
        //dtbFechaCalculo.setValue( new java.util.Date());
        iniciar();
        //pruebaNOMINA();
    }

    //--------------------------------------------------------------------------
    private void iniciar() {
        /*   ** PRUEBA EN DESARROLLO **
         java.util.Date f1 = new LibreriaHP().formatoFecha.parse("29/02/2020");  
         System.out.print("****Un dia cualquiera es="+f1+"****");
         java.util.Date f2 = new LibreriaHP().getUltDiaMes( ( new LibreriaHP().formatoFecha.parse("15/02/2020") ) );  
         System.out.println("Ult dia del mes="+f2+"*****"); 
         if ( f1.equals(f2)  ) {
         System.out.println(f1+" Es el ultimo dia del mes!!!!!!!!!!!");
         } else {
         System.out.println(f1+" No es el ultimo dia del mes !!!!!!!");
         }
         } catch (ParseException ex) {
         Logger.getLogger(ControladorAsigConcepto.class.getName()).log(Level.SEVERE, null, ex);
         }
         */
        /*
         List<Integer> lista = new bean.controlador.concepto.NomAsigConceptoDatJpaController().getListaTrabajadoresAsig("J-41309179-8","Q",1);  
         System.out.println("----------------------------------------------------");
         System.out.println("            ** PROBAR INI**       ");
         System.out.println("----------------------------------------------------");
         for ( Integer i : lista ) {
         System.out.println("Trabajajdor: "+i);
         }
         System.out.println("----------------------------------------------------");
         System.out.println("            ** PROBAR FIN**       ")
         */
        //cargarLogicaCalculo("J-41309179-8", 1);
        //new bean.utilitario.libreria.LibreriaHP().messageBox("TEST ME!!!");
    }  // iniciar().   

    //--------------------------------------------------------------------------
    public void onClick$btnEdit() {
        if (lbxAsigConceptos.getSelectedCount() > 0) {
            setEditSsnCamposClaves();
            Include ssnIncPag = (Include) Sessions.getCurrent().getAttribute("ssnIncPag");
            ssnIncPag.setSrc("../VISTA_CONCEPTO/pagAjusteAsigConEditV2.zul");
        } else {
            Messagebox.show("DEBES SELECCIONAR UN REGISTRO.", "ATENCION", Messagebox.OK, Messagebox.INFORMATION);
        }
    }  // onClick$btnEdit()

    //--------------------------------------------------------------------------
    public void onClick$btnAdd() {
        setNewSsnCamposClaves();
        Include ssnIncPag = (Include) Sessions.getCurrent().getAttribute("ssnIncPag");
        ssnIncPag.setSrc("../VISTA_CONCEPTO/pagAjusteAsigConEditV2.zul");
    }  // onClick$btnAdd()

    //--------------------------------------------------------------------------
    // El metodo de limpieza se raliaza en ModelViewAsigConcepto. 
    //--------------------------------------------------------------------------
    public void onClick$btnLimpiarKeys() {
        txtKeyCodNomina.setText("");
        txtKeyNroTrab.setText("");
        txtKeyNombre.setText("");
        txtKeyCodConcepto.setText("");
        txtKeyDescrip.setText("");
    }  // onClick$btnLimpiarKeys()

    //--------------------------------------------------------------------------
    public void onChange$cbbTipoNomina() {
        ajustarParametrosCalculo();
    }  // onChange$cbbTipoNomina().  

    //--------------------------------------------------------------------------
    public void onChange$cbbTipoNominaCierre() {
        ajustarParametrosCierre();
    }  // onChange$cbbTipoNomina().  

    //--------------------------------------------------------------------------
    public void onSelect$lbxAsigConceptos() {
        //setSsnCamposClaves();
    } // onSelect$lbxAsigConceptos()

    //--------------------------------------------------------------------------
    // ////////////////////////****CALCULAR NOMINA***/////////////////////////// 
    //--------------------------------------------------------------------------
    public void onClick$imgNomOpcion1() {
        //System.out.println("PRECALCULAR");
        try {
            //java.util.Date fechaCalculo = dtbFechaCalculo.getValue();
            //System.err.println("*** dtbFechaCalculo.getValue()="+dtbFechaCalculo.getValue()+" basealculo="+baseCalculo+" ***tipoNomina="+tipoNomina+"****"); 
            //calcularNomina(dtbFechaCalculo.getValue(), baseCalculo, tipoNomina);   // *O:))*
            calcularNominaV2(dtbFechaCalculo.getValue(), baseCalculo, tipoNomina);   // *O:))*  v.FORMULADA. 
            //eliminame();  
            //new bean.controlador.concepto.NomCalculoDatJpaController().eliminarCalculo(rifEmpresa);
        } catch (Exception ex) {
            Logger.getLogger(ControladorAsigConcepto.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("NOMINA PRECALCULADA");
    }  // onClick$imgNomOpcion1(). 

    //--------------------------------------------------------------------------
    // //////////////////////////***CERRAR NOMINA***////////////////////////////
    //--------------------------------------------------------------------------
    public void onClick$imgNomOpcion2() {
        //System.out.println("CERRAR NOMINA");
        Messagebox.show("DESEAS LLEVAR A CABO ESTA OPERACION ?", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event evento) throws Exception {
                //if (evento.getName().equals("onYes") && datosConforme()) {
                if (evento.getName().equals("onYes")) {
                    try {
                        //java.util.Date fechaCalculo = dtbFechaCalculo.getValue();
                        //System.out.println("*fechaCierre="+dtbFechaCierre.getValue()+"****BaseCalculo="+baseCalculo+"***tipoNom="+tipoNomina+"***");
                        cerrarNomina(dtbFechaCierre.getValue(), baseCalculo, tipoNomina);
                        //new bean.controlador.concepto.NomCalculoDatJpaController().eliminarCalculo(rifEmpresa);
                    } catch (Exception ex) {
                        Logger.getLogger(ControladorAsigConcepto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }  // onEvent()  

        }); // MessageBox()
        //System.out.println("NOMINA CERRADA");
    }  // onClick$imgNomOpcion2(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void ajustarParametrosCalculo() {
        String auxTipoNomina;
        Integer i = cbbTipoNomina.getSelectedIndex();
        if (i >= 0) {
            auxTipoNomina = (String) cbbTipoNomina.getModel().getElementAt(cbbTipoNomina.getSelectedIndex());
        } else {
            auxTipoNomina = (String) cbbTipoNomina.getModel().getElementAt(0);
        }
        if (auxTipoNomina != null && !auxTipoNomina.isEmpty()) {
            NomTiposNominaDat n = new bean.controlador.nomina.NomTiposNominaDatJpaController().getNomina(rifEmpresa, Integer.parseInt(auxTipoNomina.substring(0, auxTipoNomina.indexOf("-"))));
            dtbFechaCalculo.setValue(n.getProxRotacion());
            baseCalculo = n.getBaseCalculo();  // Atributo indole global. 
            tipoNomina = n.getNomTiposNominaDatPK().getCodNomina();   // Atributo de ambito global. 
            //
            Calendar c = Calendar.getInstance();
            c.setTime(n.getUltimaRotacion());
            c.add(Calendar.DATE, 1);
            //cantLunes = getLunes(n.getUltimaRotacion(),n.getProxRotacion()); 
            cantLunes = getLunes(c.getTime(), n.getProxRotacion());
            //System.out.println("********************Lunes="+cantLunes+"********************");
        }
    } //  ajustarParametrosCalculo().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void ajustarParametrosCierre() {
        String auxTipoNomina;
        Integer i = cbbTipoNominaCierre.getSelectedIndex();
        if (i >= 0) {
            auxTipoNomina = (String) cbbTipoNominaCierre.getModel().getElementAt(cbbTipoNominaCierre.getSelectedIndex());
        } else {
            auxTipoNomina = (String) cbbTipoNominaCierre.getModel().getElementAt(0);
        }
        if (auxTipoNomina != null && !auxTipoNomina.isEmpty()) {
            NomTiposNominaDat n = new bean.controlador.nomina.NomTiposNominaDatJpaController().getNomina(rifEmpresa, Integer.parseInt(auxTipoNomina.substring(0, auxTipoNomina.indexOf("-"))));
            dtbFechaCierre.setValue(n.getProxRotacion());
            baseCalculo = n.getBaseCalculo();  // Atributo indole global. 
            tipoNomina = n.getNomTiposNominaDatPK().getCodNomina();  // Atributo de ambito Global. 
        }
    } //  ajustarParametrosCierre().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Double getValorVar(String codVar) {
        Double valor = 0.00;
        NomVariableDatPK pk = new NomVariableDatPK(rifEmpresa, codVar);
        NomVariableDat reg = new bean.controlador.concepto.NomVariableDatJpaController().findNomVariableDat(pk);
        if (reg != null) {
            valor = reg.getValor();
        }
        return (valor);
    } // getValorVar() 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Double getCantidad(Integer nroTrabajador, Integer codConcepto) {
        Double nroTrabajadorD = nroTrabajador.doubleValue();
        Double codConceptoD = codConcepto.doubleValue();
        Double valor = 0.00;
        Boolean encontrado = Boolean.FALSE;
        for (int i = 0; i <= contFila && !encontrado; i++) {
            if ((arrCalculo[i][Atributo.TRABAJADOR.ordinal()] != null && arrCalculo[i][Atributo.TRABAJADOR.ordinal()].equals(nroTrabajadorD)) && (arrCalculo[i][Atributo.COD_CONCEPTO.ordinal()] != null && arrCalculo[i][Atributo.COD_CONCEPTO.ordinal()].equals(codConceptoD))) {
                valor = arrCalculo[i][Atributo.CANTIDAD.ordinal()];
                encontrado = Boolean.TRUE;
            }
        }  // for 
        return (valor);
    } // getCantidad.

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Double getMonto(Integer nroTrabajador, Integer codConcepto) {
        Double nroTrabajadorD = nroTrabajador.doubleValue();
        Double codConceptoD = codConcepto.doubleValue();
        Double valor = 0.00;
        //System.out.println("Trab="+nroTrabajador+"*Concepto="+codConcepto+"***valor="+valor+"***contFila="+contFila+"***");
        Boolean encontrado = Boolean.FALSE;
        for (int i = 0; i <= contFila && !encontrado; i++) {
            if ((arrCalculo[i][Atributo.TRABAJADOR.ordinal()] != null && arrCalculo[i][Atributo.TRABAJADOR.ordinal()].equals(nroTrabajadorD)) && (arrCalculo[i][Atributo.COD_CONCEPTO.ordinal()] != null && arrCalculo[i][Atributo.COD_CONCEPTO.ordinal()].equals(codConceptoD))) {
                valor = arrCalculo[i][Atributo.MONTO.ordinal()];
                //System.out.println("Trab="+nroTrabajador+"*Concepto="+codConcepto+"***Monto Encontrado="+valor+"***contFila="+contFila+"***");
                encontrado = Boolean.TRUE;
            }
        }  // for 
        return (valor);
    } // getMonto.

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Double getPorcentaje(Integer nroTrabajador, Integer codConcepto) {
        Double nroTrabajadorD = nroTrabajador.doubleValue();
        Double codConceptoD = codConcepto.doubleValue();
        Double valor = 0.00;
        //System.out.println("Trab="+nroTrabajador+"*Concepto="+codConcepto+"***valor="+valor+"***contFila="+contFila+"***");
        Boolean encontrado = Boolean.FALSE;
        for (int i = 0; i <= contFila && !encontrado; i++) {
            if ((arrCalculo[i][Atributo.TRABAJADOR.ordinal()] != null && arrCalculo[i][Atributo.TRABAJADOR.ordinal()].equals(nroTrabajadorD)) && (arrCalculo[i][Atributo.COD_CONCEPTO.ordinal()] != null && arrCalculo[i][Atributo.COD_CONCEPTO.ordinal()].equals(codConceptoD))) {
                valor = arrCalculo[i][Atributo.PORCENTAJE.ordinal()];
                //System.out.println("Trab="+nroTrabajador+"*Concepto="+codConcepto+"***Monto Encontrado="+valor+"***contFila="+contFila+"***");
                encontrado = Boolean.TRUE;
            }
        }  // for 
        return (valor);
    } // getPorcentaje().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Double getTotal(Integer nroTrabajador, Integer codConcepto) {
        Double nroTrabajadorD = nroTrabajador.doubleValue();
        Double codConceptoD = codConcepto.doubleValue();
        Double valor = 0.00;
        Boolean encontrado = Boolean.FALSE;
        for (int i = 0; i <= contFila && !encontrado; i++) {
            if ((arrCalculo[i][Atributo.TRABAJADOR.ordinal()] != null && arrCalculo[i][Atributo.TRABAJADOR.ordinal()].equals(nroTrabajadorD)) && (arrCalculo[i][Atributo.COD_CONCEPTO.ordinal()] != null && arrCalculo[i][Atributo.COD_CONCEPTO.ordinal()].equals(codConceptoD))) {
                valor = arrCalculo[i][Atributo.TOTAL.ordinal()];
                encontrado = Boolean.TRUE;
            }
        }  // for 
        return (valor);
    } // getTotal.

    //--------------------------------------------------------------------------
    // get C)antidad parcial del metodo ProcesarConcepto ( v.FORMULADO ).  
    // EJEMPLO :   C3 --> C=arrR  y 3 correlativo ubicado en arrR[ i ][ IndiceR.CORRELATVO.ordinal() ]; 
    //--------------------------------------------------------------------------
    private Double getC(Integer correlativo) {
        Double valor = 0.00;
        Boolean encontrado = Boolean.FALSE;
        //System.out.println("-----ini get C----------");   
        for (int i = 0; i < FILAS_RP_SIZE && !encontrado; i++) {
            //System.out.println("::: get C. ACCION="+arrFormula[i][IndiceF.ACCION.ordinal()]+ ". Correlativo="+R[i][IndiceR.CORRELATIVO.ordinal()]+". Valor="+R[i][IndiceR.VALOR.ordinal()]+". Cantidad="+R[i][IndiceR.CANTIDAD.ordinal()]+" ****" );
            if ((R[i][IndiceR.CORRELATIVO.ordinal()] == null)) {  // arr nulo xq fila esta vacia ? --> termina.  
                encontrado = Boolean.TRUE;
            } else if ((R[i][IndiceR.CORRELATIVO.ordinal()] != null && R[i][IndiceR.CORRELATIVO.ordinal()].equals(correlativo.doubleValue()))) {
                valor = R[i][IndiceR.CANTIDAD.ordinal()];
                encontrado = Boolean.TRUE;
                //System.out.println("::: get C (ENCONTRADO)"+R[i][IndiceR.CORRELATIVO.ordinal()]+". Valor="+R[i][IndiceR.CANTIDAD.ordinal()] );
            }
        }  // for 
        //System.out.println("----------fin GetR-------");  
        return (valor);
    } // getC().

    //--------------------------------------------------------------------------
    // get R)esultado parcial del metodo ProcesarConcepto v.FORMULADO.  
    // EJEMPLO :   R3 --> R=arrR  y 3 correlativo ubicado en arrR[ i ][ IndiceR.CORRELATVO.ordinal() ]; 
    //--------------------------------------------------------------------------
    private Double getR(Integer correlativo) {
        Double valor = 0.00;
        Boolean encontrado = Boolean.FALSE;
        //System.out.println("-----ini get R----------");   
        for (int i = 0; i < FILAS_RP_SIZE && !encontrado; i++) {
            //System.out.println("::: get R"+R[i][IndiceR.CORRELATIVO.ordinal()]+". Valor="+R[i][IndiceR.VALOR.ordinal()] );
            if ((R[i][IndiceR.CORRELATIVO.ordinal()] == null)) {  // arr nulo xq fila esta vacia ? --> termina.  
                encontrado = Boolean.TRUE;
            } else if ((R[i][IndiceR.CORRELATIVO.ordinal()] != null && R[i][IndiceR.CORRELATIVO.ordinal()].equals(correlativo.doubleValue()))) {
                valor = R[i][IndiceR.VALOR.ordinal()];
                encontrado = Boolean.TRUE;
                //System.out.println("::: get R (ENCONTRADO)"+R[i][IndiceR.CORRELATIVO.ordinal()]+". Valor="+R[i][IndiceR.VALOR.ordinal()] );
            }
        }  // for 
        //System.out.println("----------fin GetR-------");  
        return (valor);
    } // getR().

    //--------------------------------------------------------------------------
    // https://es.stackoverflow.com/questions/84159/obtener-todos-los-lunes-en-un-rango-de-fechas
    //--------------------------------------------------------------------------
    private Integer getLunes(java.util.Date fecha1, java.util.Date fecha2) {
        Calendar cFecha1 = Calendar.getInstance();
        cFecha1.setTime(fecha1);
        Calendar cFecha2 = Calendar.getInstance();
        cFecha2.setTime(fecha2);
        Integer contLunes = 0;
        while (!cFecha2.before(cFecha1)) {
            if (cFecha1.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                contLunes = contLunes + 1;
            }  // if 
            cFecha1.add(Calendar.DATE, 1);
        }  // while. 
        return (contLunes);
    } // getLunes(). 

    //--------------------------------------------------------------------------
    // **** CALCULO DE CONCEPTO *****
    //-------------------------------------------------------------------------
    private Double calcularTotalConcepto(Integer codNomina, Integer nroTrab, Integer codConcepto, Double cantidad, Double monto, Double porcentaje, String frecCalculo, Integer fila) {
        // Auxiliares Asignaciones:
        final int AUX_SUELDO = 10;
        final int AUX_OTRAS_ASIG_AFECTAN = 11;
        final int AUX_OTRAS_ASIG_NO_AFECTAN = 12;
        // final Integer AUX_ = 15;   // Asi intencionalmente 
        final int AUX_BONO = 16;
        final int AUX_REPOSO = 17;
        // Auxiliares Deducciones:
        final Integer AUX_FALTA = 50;
        final Integer AUX_PNO_REMUNERADO = 51;
        final Integer AUX_OTRAS_DEDUCC = 55;
        // ====================
        // ** ASIGNACIONES **  
        // ====================
        final int CONCEPTO_SUELDO = 100;
        final int CONCEPTO_OTRAS_ASIG_AFECTAN = 101;
        final int CONCEPTO_OTRAS_ASIG_NO_AFECTAN = 102;
        final int CONCEPTO_DIA_DESCANSO = 103;
        final int CONCEPTO_DIA_FERIADO = 104;
        final int CONCEPTO_TIEMPO_VIAJE = 105;
        final int CONCEPTO_BONO_PROD = 106;
        final int CONCEPTO_REPOSO = 107;
        // ===================
        // *** DEDUCCIONES **
        // ===================
        final int CONCEPTO_SSO = 500;
        final int CONCEPTO_LPH = 501;
        final int CONCEPTO_RPE = 502;
        final int CONCEPTO_FALTA = 503;
        final int CONCEPTO_PNO_REMUNERADO = 504;
        final int CONCEPTO_OTRAS_DEDUCC = 505;
        //
        final Double factorDiaFeriado = 1.50;
        Double total = 0.00;
        Double promMonto = 0.00;
        Double dias = 0.00;
        //
        switch (codConcepto) {
            // ==================*ASIGNACIONES*=================================
            case CONCEPTO_SUELDO: {   // SUELDO ********************************************
                //total = monto * (cantidad <= 0 ? 1 : cantidad) * (porcentaje <= 0 ? 1 : porcentaje / 100.00);
                total = getMonto(nroTrab, AUX_SUELDO);
                //System.out.println("NroTrab="+nroTrab+"CocConcepto="+codConcepto+"**total="+total+"**VDHS="+getValorVar("VDHS")+"***frecCalculo="+frecCalculo+"****");
                promMonto = total / 30.00;
                if (cantidad.equals(0.00)) {
                    switch (frecCalculo) {
                        case "S": {
                            dias = getValorVar("VDHS");
                            break;
                        } // case "S". 
                        case "Q": {
                            dias = getValorVar("VDHQ");
                            break;
                        }  // case "Q"
                    }  // switch (frecCalculo)
                } else {
                    dias = cantidad;
                } // if-else ( !cantidad.equals(0.00) ) 
                dias = dias - getCantidad(nroTrab, AUX_REPOSO);
                total = promMonto * dias;
                arrCalculo[fila][Atributo.CANTIDAD.ordinal()] = dias;
                //total = total - (promMonto * getCantidad(nroTrab, AUX_FALTA));
                //Messagebox.show("**trab="+nroTrab+"**concept="+codConcepto+"**cantidad="+cantidad+"***monto="+monto+"**porcentaje="+porcentaje+"***TOTAL="+total+"***");
                break;
            }  // case 100.
            case CONCEPTO_OTRAS_ASIG_AFECTAN: {   //OTRAS ASIGNACIONES AFECTAN *************************
                //total = monto * (cantidad <= 0 ? 1 : cantidad) * (porcentaje <= 0 ? 1 : porcentaje / 100.00);
                total = getMonto(nroTrab, CONCEPTO_OTRAS_ASIG_AFECTAN);
                break;
            }  // case 101.
            case CONCEPTO_OTRAS_ASIG_NO_AFECTAN: {   //OTRAS ASIGNACIONES *NO* AFECTAN *************************
                //total = monto * (cantidad <= 0 ? 1 : cantidad) * (porcentaje <= 0 ? 1 : porcentaje / 100.00);
                total = getMonto(nroTrab, CONCEPTO_OTRAS_ASIG_NO_AFECTAN);
                //System.out.println("NroTrab="+nroTrab+"CocConcepto="+codConcepto+"**total="+total+"**VDHS="+getValorVar("VDHS")+"***frecCalculo="+frecCalculo+"****");
                break;
            }  // case 102.
            case CONCEPTO_DIA_DESCANSO: {  // DIA DESCANSO ***************************************
                //total = monto * (cantidad <= 0 ? 1 : cantidad) * (porcentaje <= 0 ? 1 : porcentaje / 100.00);
                total = getMonto(nroTrab, AUX_SUELDO);
                //System.out.println("NroTrab="+nroTrab+"CocConcepto="+codConcepto+"**total="+total+"**VDHS="+getValorVar("VDHS")+"***frecCalculo="+frecCalculo+"****");
                promMonto = total / 30.00;
                if (cantidad.equals(0.00)) {
                    switch (frecCalculo) {
                        case "S": {
                            dias = getValorVar("VDDS");
                            break;
                        } // case "S". 
                        case "Q": {
                            //promMonto = total / 30.00;
                            dias = getValorVar("VDDQ");
                            break;
                        }  // case "Q"
                    }  // switch (frecCalculo)
                } else {
                    dias = cantidad;
                }
                total = promMonto * dias;
                arrCalculo[fila][Atributo.CANTIDAD.ordinal()] = dias;
                break;
            }  // case 103:
            case CONCEPTO_DIA_FERIADO: {  // DIA FERIADO ****************************************
                //total = monto * (cantidad <= 0 ? 1 : cantidad) * (porcentaje <= 0 ? 1 : porcentaje / 100.00);
                total = getMonto(nroTrab, AUX_SUELDO);
                //System.out.println("NroTrab="+nroTrab+"CocConcepto="+codConcepto+"**total="+total+"**VDHS="+getValorVar("VDHS")+"***frecCalculo="+frecCalculo+"****");
                promMonto = (total / 30.00) * factorDiaFeriado;
                if (cantidad.equals(0.00)) {
                    switch (frecCalculo) {
                        case "S": {
                            dias = getValorVar("VDFS");
                            break;
                        } // case "S". 
                        case "Q": {
                            //promMonto = (total / 30.00) * factorDiaFeriado;
                            dias = getValorVar("VDFQ");
                            break;
                        }  // case "Q"
                    }  // switch (frecCalculo)
                } else {
                    dias = cantidad;
                }
                total = promMonto * dias;
                arrCalculo[fila][Atributo.CANTIDAD.ordinal()] = dias;
                break;
            }  // case 104
            case CONCEPTO_TIEMPO_VIAJE: {  // TIEMPO DE VIAJE ************************************
                //total = monto * (cantidad <= 0 ? 1 : cantidad) * (porcentaje <= 0 ? 1 : porcentaje / 100.00);
                total = getMonto(nroTrab, AUX_SUELDO);
                //System.out.println("NroTrab="+nroTrab+"CocConcepto="+codConcepto+"**total="+total+"**VDHS="+getValorVar("VDHS")+"***frecCalculo="+frecCalculo+"****");
                promMonto = (total / 30.00) / 8.00;
                if (cantidad.equals(0.00)) {
                    switch (frecCalculo) {
                        case "S": {
                            dias = getValorVar("VDHS") + getValorVar("VDFS");
                            break;
                        } // case "S". 
                        case "Q": {
                            dias = getValorVar("VDHQ") + getValorVar("VDFQ");
                            break;
                        }  // case "Q"
                    }  // switch (frecCalculo)
                } else {
                    dias = cantidad;
                }
                // eliminame
                //if (nroTrab.equals(1003)) {
                //    System.out.println("Trab=" + nroTrab + " ***Dias=" + dias + " ***promMonto=" + promMonto + " ***");
                //}
                // eliminame
                dias = dias - getCantidad(nroTrab, AUX_FALTA) - getCantidad(nroTrab, AUX_PNO_REMUNERADO) - getCantidad(nroTrab, AUX_REPOSO);
                total = promMonto * dias;
                arrCalculo[fila][Atributo.CANTIDAD.ordinal()] = dias;
                break;
            }  // case 105:
            case CONCEPTO_BONO_PROD: {  // BONO DE PRODUCCION *********************************
                //total = monto * (cantidad <= 0 ? 1 : cantidad) * (porcentaje <= 0 ? 1 : porcentaje / 100.00);
                total = getMonto(nroTrab, AUX_BONO);
                //System.out.println("NroTrab="+nroTrab+"CocConcepto="+codConcepto+"**total="+total+"**VDHS="+getValorVar("VDHS")+"***frecCalculo="+frecCalculo+"****");
                switch (frecCalculo) {
                    case "S": {
                        promMonto = total / 5.00;   // 5 dias habiles de la semana 
                        break;
                    } // case "S". 
                    case "Q": {
                        promMonto = total / 21.00;  // 21 dias habiles del mes 
                        break;
                    }  // case "Q"
                }  // switch (frecCalculo)
                if (cantidad.equals(0.00)) {
                    switch (frecCalculo) {
                        case "S": {
                            dias = getValorVar("VDHS") + getValorVar("VDFS");
                            break;
                        } // case "S". 
                        case "Q": {
                            dias = getValorVar("VDHQ") + getValorVar("VDFQ");
                            break;
                        }  // case "Q"
                    }  // switch (frecCalculo)
                } else {
                    dias = cantidad;
                }
                dias = dias - getCantidad(nroTrab, AUX_FALTA) - getCantidad(nroTrab, AUX_PNO_REMUNERADO) - getCantidad(nroTrab, AUX_REPOSO);
                total = promMonto * dias;
                arrCalculo[fila][Atributo.CANTIDAD.ordinal()] = dias;
                break;
            }  // case 106:
            case CONCEPTO_REPOSO: {  // REPOSO ***Check it!! - Excepcion, debido a que NO estamos pagando SSO ******
                total = getMonto(nroTrab, AUX_SUELDO);     // Monto concepto 100 expresado mensual. 
                promMonto = total / 30.00;
                dias = getCantidad(nroTrab, AUX_REPOSO);
                total = promMonto * dias;
                arrCalculo[fila][Atributo.CANTIDAD.ordinal()] = dias;
                break;
            }  // case 107:
            // ==================*DEDUCCIONES*==================================
            case CONCEPTO_SSO: {   // CONTRIBUCION S.S.O. *******************************
                total = getMonto(nroTrab, AUX_SUELDO);     // Monto concepto 100 expresado mensual. 
                total = total * 12.00;
                total = total / 52.00;               // 52 semanas
                total = total * 0.04;                // 4%
                total = total * cantLunes;           // cantLunes de ambito Global (ver metodo ajustarParametrosCalculo).  
                break;
            }  // case 500
            case CONCEPTO_LPH: {  // CONTRIBUCION L.P.H. ********************************
                total = getTotal(nroTrab, CONCEPTO_SUELDO) + getTotal(nroTrab, CONCEPTO_OTRAS_ASIG_AFECTAN) + getTotal(nroTrab, CONCEPTO_DIA_DESCANSO) + getTotal(nroTrab, CONCEPTO_DIA_FERIADO) + getTotal(nroTrab, CONCEPTO_TIEMPO_VIAJE) + getTotal(nroTrab, CONCEPTO_REPOSO);  // + getTotal(nroTrab, CONCEPTO_BONO_PROD);
                total = total * (1 / 100.00);  // cantLunes de ambito Global
                break;
            }
            case CONCEPTO_RPE: {  // CONTRIBUCION R.P.E. ********************************
                total = getMonto(nroTrab, AUX_SUELDO);     // Monto concepto 100 expresado mensual. 
                total = total * 12.00;
                total = total / 52.00;              // 52 semanas
                total = total * 0.005;              // 0.5%
                total = total * cantLunes;          // cantLunes de ambito Global (ver metodo ajustarParametrosCalculo).  
                break;
            }  // case 502
            case CONCEPTO_FALTA: {  // FALTA INJUSTIFICADA. *******************************
                total = getMonto(nroTrab, AUX_SUELDO);     // Monto concepto 100 expresado mensual. 
                promMonto = total / 30.00;
                dias = getCantidad(nroTrab, AUX_FALTA);
                total = promMonto * dias;
                arrCalculo[fila][Atributo.CANTIDAD.ordinal()] = dias;
                break;
            }  // case 503
            case CONCEPTO_PNO_REMUNERADO: {  // PERMISO NO REMUNERADO. ***************************** 
                total = getMonto(nroTrab, AUX_SUELDO);     // Monto concepto 100 expresado mensual. 
                promMonto = total / 30.00;
                dias = getCantidad(nroTrab, AUX_PNO_REMUNERADO);
                total = promMonto * dias;
                arrCalculo[fila][Atributo.CANTIDAD.ordinal()] = dias;
                break;
            }  // case 504
            case CONCEPTO_OTRAS_DEDUCC: {  // OTRAS DEDUCCIONES  *********************************
                total = monto * (cantidad <= 0 ? 1 : cantidad) * (porcentaje <= 0 ? 1 : porcentaje / 100.00);
                break;
            }  // case 505
            default: {  // Check asignaciones .. 
                total = monto * (cantidad <= 0 ? 1 : cantidad) * (porcentaje <= 0 ? 1 : porcentaje / 100.00);
                //Messagebox.show("**trab="+nroTrab+"**concept="+codConcepto+"**cantidad="+cantidad+"***monto="+monto+"**porcentaje="+porcentaje+"***TOTAL="+total+"***");
            }  // defualt
        } // switch
        return (total);
    } // calcularTotalConcepto().  

    //--------------------------------------------------------------------------
    // TOTAL CONCEPTO v.FORMULADA **********************************************
    // Autor: H.J.P.B.
    // Creada: 15 diciembre 2020 08_13                           
    //--------------------------------------------------------------------------
    private Double totalConcepto(Integer codNomina, Integer nroTrabajador, Integer codConcepto, java.util.Date fechaProceso) {
        Double total = 0.00;
        if (existeFormula(codNomina, codConcepto)) {      // 
            //System.out.println("El concepto '"+codConcepto+"' de la nomina '"+codNomina+"' posee FORMULARIO");
            Double cantidad = 0.00;
            String formula = "";
            ClassArregloTokens tokens;
            Boolean termine = Boolean.FALSE;
            Integer contR = 0;
            for (Integer i = 0; (i < arrIndice.length) && !termine; i++) {
                if (arrIndice[i][Indice.NOMINA.ordinal()] == null) {
                    termine = true;
                } else {
                    if ((arrIndice[i][Indice.NOMINA.ordinal()].intValue() == codNomina.intValue()) && (arrIndice[i][Indice.COD_CONCEPTO.ordinal()].intValue() == codConcepto.intValue())) {
                        formula = arrFormula[i][IndiceF.FORMULA.ordinal()];
                        Boolean instruccionCondicional = condicion(formula);
                        //System.out.println("formula to process= " + formula + " *****");
                        if (!instruccionCondicional) {
                            formula = formatearEcuacion(formula);   // formatearEcuacion.  
                            tokens = new bean.controlador.concepto.ControladorDefFormula().dividirTokens(formula);
                            tokens = new bean.controlador.concepto.ControladorDefFormula().validarEcuacion(tokens);
                        } else {
                            tokens = new bean.controlador.concepto.ControladorDefFormula().dividirTokenizer(formula);
                        }
                        if (tokens != null) {
                            // *Formula / ecuacion VALIDA
                            //System.err.println("***ECUACION/FORMULA VALIDA***:" + formula);
                            //System.out.println(">>>>>> Formula="+arrFormula[i][IndiceF.FORMULA.ordinal()]+" ACCION="+arrFormula[i][IndiceF.ACCION.ordinal()] );  
                            if ((arrIndice[i][Indice.TOTAL.ordinal()].intValue() == SI)) {
                                //         **TOTALIZAR:
                                //   Check this out: >>>>>>-------------------------
                                /*
                                 String[] t = tokens.getArregloTokens();         // array token
                                 ClassArregloTokens.TIPO_TOKEN[] l;
                                 t = tokens.getArregloTokens();
                                 l = tokens.getArregloTipoToken();
                                 for (int j = 0; j < tokens.getContadorTokens(); j++) {
                                 System.out.print(t[j] + " ");
                                 }
                                 System.out.println();
                                 */
                                //----------------------------------------------------
                                if (instruccionCondicional) {
                                    total = procesarCondicion(codNomina, nroTrabajador, fechaProceso, tokens);
                                } else {
                                    total = procesarEcuacionNomina(codNomina, nroTrabajador, fechaProceso, tokens);
                                }
                                //System.out.println(">>>>>>Trabajador=" + nroTrabajador + ". Concepto=" + codConcepto + ". TOTAL=" + total);
                            } else if (arrFormula[i][IndiceF.ACCION.ordinal()].equals("C")) {   // AJUSTAR cantidad en el Array. 
                                //
                                if (instruccionCondicional) {
                                    cantidad = procesarCondicion(codNomina, nroTrabajador, fechaProceso, tokens);
                                } else {
                                    cantidad = procesarEcuacionNomina(codNomina, nroTrabajador, fechaProceso, tokens);
                                }
                                //
                                //System.out.println(">>>>>>Trabajador=" + nroTrabajador + ". Concepto=" + codConcepto + ". CANTIDAD=" + cantidad );
                                //
                                arrCalculo[contFila][Atributo.CANTIDAD.ordinal()] = cantidad;
                                R[contR][IndiceR.CORRELATIVO.ordinal()] = arrIndice[i][Indice.CORRELATIVO.ordinal()].doubleValue();
                                R[contR][IndiceR.CANTIDAD.ordinal()] = cantidad;
                                //System.out.println(">>>>>>Trabajador=" + nroTrabajador + ". Concepto=" + codConcepto + ". CANTIDAD=" + cantidad);
                                contR++;
                            } else if (arrFormula[i][IndiceF.ACCION.ordinal()].equals("R")) {   // **ES UN RESULTADO PARCIAL:       
                                R[contR][IndiceR.CORRELATIVO.ordinal()] = arrIndice[i][Indice.CORRELATIVO.ordinal()].doubleValue();
                                //
                                if (instruccionCondicional) {
                                    R[contR][IndiceR.VALOR.ordinal()] = procesarCondicion(codNomina, nroTrabajador, fechaProceso, tokens);
                                } else {
                                    R[contR][IndiceR.VALOR.ordinal()] = procesarEcuacionNomina(codNomina, nroTrabajador, fechaProceso, tokens);
                                }
                                //System.out.println(">>>>>>Trabajador=" + nroTrabajador + ". Concepto=" + codConcepto + ". R[" + R[contR][IndiceR.CORRELATIVO.ordinal()] + "]=" + R[contR][IndiceR.VALOR.ordinal()] + "");
                                contR++;
                            } // if-else. 
                        } else {
                            System.err.println("***ECUACION/FORMULA INVALIDA***:" + formula);
                            total = 0.00;
                        }// if-else ( tokens!=null )
                    }  // if ((arrIndice[i][Indice.NOMINA.ordinal() ...
                }  // if (arrIndice[i][Indice.NOMINA.ordinal()] == null)
            } // for
            //System.out.println("**TOTAL = " + total + " ????????????????????????????");
        } else {
            System.err.println("El concepto '" + codConcepto + "' de la nomina '" + codNomina + "' NO posee FORMULARIO");
        }
        return (total);
    }  // totalConcepto().   // v.Formulada().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void calcularNominaOLD(java.util.Date fechaCalculo, String baseCalculo, Integer codNomina) {  // baseCalculo <==> frecCalculo de ambito global. 
        List<NomAsigConceptoDat> lista = new bean.controlador.concepto.NomAsigConceptoDatJpaController().getListaAsigConceptos(rifEmpresa, baseCalculo, codNomina);
        if (lista != null && !lista.isEmpty()) {
            //System.out.println("INICIAR PRE CALCULO FECHA CALCULO NOMINA=" + fechaCalculo + "***");
            NomTiposNominaDat n1 = new bean.controlador.nomina.NomTiposNominaDatJpaController().findNomTiposNominaDat(new NomTiposNominaDatPK(rifEmpresa, codNomina));
            if (n1 == null) {
                System.err.println("ATENCION ( ControladorAsigConcepto.calcularNomina()): Error en el metodo 'calcularNomina' al tratar de 'get' tipo de Nomina ( vacia???? ) ");
                return;
            }
            java.util.Date fechaEnFondo = n1.getUltimaRotacion1();
            java.util.Date fechaPago = n1.getProxRotacion1();
            //new bean.controlador.concepto.NomConceptoHistDatJpaController().eliminarHistConceptos(rifEmpresa, fechaCalculo);
            // Limpiar tabla : NomCalculo_Dat 
            new bean.controlador.concepto.NomCalculoDatJpaController().eliminarCalculo(rifEmpresa, codNomina);
            // Limpiar tabla Aux: NomCalculo1_Dat 
            new bean.controlador.concepto.NomCalculo1DatJpaController().eliminarCalculo(rifEmpresa, codNomina, fechaCalculo);  //
            //Messagebox.show("REGISTROS ELIMINADOS..............");
            //Integer codNomina;
            Integer nroTrab;
            Integer codConcepto;
            Double cantidad;
            Double monto;
            Double porcentaje;
            Double acumulado, total = 0.00;
            String frecuenciaCalculo, frecuenciaPago, formaPago;
            String codFormula = null;
            String observacion1, observacion2;
            String calcularConcepto;
            contFila = 0;  // Atributo de ambito Global. 
            for (NomAsigConceptoDat n : lista) {
                try {
                    //  ***** Ejecutar CALCULO DE CONCEPTOS fullFill el ARRAY de Calculo ******
                    //codNomina = n.getNomAsigConceptoDatPK().getCodNomina();
                    nroTrab = n.getNomAsigConceptoDatPK().getNroTrabajador();
                    codConcepto = n.getNomAsigConceptoDatPK().getCodConcepto();
                    //
                    cantidad = (n.getCantidad() == null ? 0 : n.getCantidad());
                    monto = (n.getMonto() == null ? 0 : n.getMonto());
                    porcentaje = (n.getPorcentaje() == null ? 0 : n.getPorcentaje());
                    frecuenciaCalculo = (n.getFrecuenciaCalculo() == null ? "" : n.getFrecuenciaCalculo());
                    frecuenciaPago = (n.getFrecuenciaPago() == null ? "" : n.getFrecuenciaPago());
                    formaPago = (n.getFormaPago() == null ? "" : n.getFormaPago());
                    observacion1 = (n.getObservacion1() == null ? "" : n.getObservacion1());
                    observacion2 = (n.getObservacion2() == null ? "" : n.getObservacion2());
                    //
                    arrCalculo[contFila][Atributo.TRABAJADOR.ordinal()] = nroTrab.doubleValue();
                    arrCalculo[contFila][Atributo.COD_CONCEPTO.ordinal()] = codConcepto.doubleValue();
                    arrCalculo[contFila][Atributo.CANTIDAD.ordinal()] = cantidad;
                    arrCalculo[contFila][Atributo.MONTO.ordinal()] = monto;
                    arrCalculo[contFila][Atributo.PORCENTAJE.ordinal()] = porcentaje;
                    calcularConcepto = n.getCalcular().trim();
                    if (calcularConcepto != null && calcularConcepto.equals("S")) {
                        // ** Aqui el Piquete: **
                        total = calcularTotalConcepto(codNomina, nroTrab, codConcepto, cantidad, monto, porcentaje, frecuenciaCalculo, contFila);
                        // **********************
                    } else {
                        total = monto;
                    }
                    arrCalculo[contFila][Atributo.TOTAL.ordinal()] = total;
                    // * Gestionar NomCalculo_Dat *
                    NomCalculoDatPK nomCalculoDatPK = new NomCalculoDatPK(rifEmpresa, codNomina, nroTrab, codConcepto, fechaCalculo);
                    NomCalculoDat nomCalculoDat;
                    // * Gestionar NomCalculo1_Dat *  
                    NomCalculo1DatPK nomCalculo1DatPK;
                    NomCalculo1Dat nomCalculo1Dat;
                    // * Gestionar turno de Labor *  
                    Integer turno = new bean.controlador.personal.NomTrabajador01DatJpaController().getTurno(rifEmpresa, codNomina, nroTrab);
                    //
                    if (frecuenciaPago.equals(baseCalculo)) {
                        //System.out.println("****FECHA DE PAGO SEMANAL****"); 
                        //System.out.println("******PROCESANDO: (TipoNom=)"+n.getNomAsigConceptoDatPK().getCodNomina()+"*Trabajador="+n.getNomAsigConceptoDatPK().getNroTrabajador()+"*Concepto="+codConcepto+"**contFila="+contFila+"***");
                        cantidad = arrCalculo[contFila][Atributo.CANTIDAD.ordinal()];
                        nomCalculoDat = new NomCalculoDat(nomCalculoDatPK, turno, cantidad, monto, total, porcentaje, frecuenciaCalculo, codFormula, frecuenciaPago, formaPago, observacion1, observacion2);
                        new bean.controlador.concepto.NomCalculoDatJpaController().create(nomCalculoDat);
                        //System.out.println("******arrCalculo.Trab=" + arrCalculo[contFila][Atributo.TRABAJADOR.ordinal()] + "**arrCalculo.Total=" + arrCalculo[contFila][Atributo.TOTAL.ordinal()] + "****");
                    } else if (frecuenciaCalculo.equals("S") && frecuenciaPago.equals("Q")) {
                        if (fechaCalculo.equals(fechaPago)) {
                            //System.out.println("*Trab: "+nroTrab+"  ***FECHA DE PAGO QUINCENAL****"); 
                            // Acumular concepto 
                            //acumulado = new bean.controlador.concepto.NomCalculo1DatJpaController().getTotal(rifEmpresa,codNomina,nroTrab,codConcepto,fechaEnFondo);
                            nomCalculo1DatPK = new NomCalculo1DatPK(rifEmpresa, codNomina, nroTrab, codConcepto, fechaEnFondo);
                            nomCalculo1Dat = new bean.controlador.concepto.NomCalculo1DatJpaController().findNomCalculo1Dat(nomCalculo1DatPK);
                            if (nomCalculo1Dat != null) {
                                cantidad = arrCalculo[contFila][Atributo.CANTIDAD.ordinal()];
                                cantidad = cantidad + nomCalculo1Dat.getCantidad();
                                total = total + nomCalculo1Dat.getTotal();
                            }
                            // Actualizar tabla NomCalculo_Dat *  
                            nomCalculoDat = new NomCalculoDat(nomCalculoDatPK, turno, cantidad, monto, total, porcentaje, frecuenciaCalculo, codFormula, frecuenciaPago, formaPago, observacion1, observacion2);
                            new bean.controlador.concepto.NomCalculoDatJpaController().create(nomCalculoDat);
                            // REMIND: al ejecutar el Cierre -> DELETE FROM NomCalculo1_Dat WHERE codNomina = nomina && fechaNomina = fechaPago;  .... 
                        } else if (fechaCalculo.equals(fechaEnFondo)) {
                            // Actualizar tabla NomCalculo1_Dat 
                            nomCalculo1DatPK = new NomCalculo1DatPK(rifEmpresa, codNomina, nroTrab, codConcepto, fechaCalculo);
                            nomCalculo1Dat = new NomCalculo1Dat(nomCalculo1DatPK, turno, arrCalculo[contFila][Atributo.CANTIDAD.ordinal()], monto, total, porcentaje, frecuenciaCalculo, codFormula, frecuenciaPago, formaPago, observacion1, observacion2);
                            new bean.controlador.concepto.NomCalculo1DatJpaController().create(nomCalculo1Dat);
                        } else {
                            System.err.println("ATENCION (ControladorAsigConcepto.calcularNomina() ): revisar definicion de fechas S->Q (definido ✔ ) Q->S (NO definido ? ) !.");
                        }
                    } else {
                        System.err.println("ATENCION (ControladorAsigConcepto.calcularNomina() ): rutina en Desarrollo!! ");
                    }
                    contFila++;
                } catch (Exception ex) {
                    Logger.getLogger(ControladorAsigConcepto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   // for. 
            Messagebox.show("NOMINA CALCULADA EXITOSAMENTE !.", "ATENCION", Messagebox.OK, Messagebox.INFORMATION);
        } // if-else
        /**
         * *
         * for ( Integer i=0; i < contFila; i++) {
         * System.out.print("i="+i+"-Trab="+arrCalculo[i][Atributo.TRABAJADOR.ordinal()]);
         * System.out.print("-Con="+arrCalculo[i][Atributo.COD_CONCEPTO.ordinal()]);
         * System.out.print("-Cant="+arrCalculo[i][Atributo.CANTIDAD.ordinal()]);
         * System.out.print("-Monto="+arrCalculo[i][Atributo.MONTO.ordinal()]);
         * System.out.println("-Total="+arrCalculo[i][Atributo.TOTAL.ordinal()]);
         * }
         */
    } // calcularNominaOLD(). 

    //--------------------------------------------------------------------------
    //                     ** Calcular Nomina **
    // Autor: HJPB. 
    // Creado: 07/10/2020
    // Actualizado: 10/12/2020.   
    //--------------------------------------------------------------------------
    private void calcularNomina(java.util.Date fechaCalculo, String baseCalculo, Integer codNomina) {  // baseCalculo <==> frecCalculo de ambito global. 
        List<Integer> listaTrab = new bean.controlador.concepto.NomAsigConceptoDatJpaController().getListaTrabajadoresAsig(rifEmpresa, baseCalculo, codNomina);
        if (listaTrab == null || listaTrab.isEmpty()) {
            return;
        }
        // Limpiar tabla : NomCalculo_Dat 
        new bean.controlador.concepto.NomCalculoDatJpaController().eliminarCalculo(rifEmpresa, codNomina);
        // Limpiar tabla Aux: NomCalculo1_Dat 
        new bean.controlador.concepto.NomCalculo1DatJpaController().eliminarCalculo(rifEmpresa, codNomina, fechaCalculo);  //
        //Messagebox.show("REGISTROS ELIMINADOS..............");
        //System.out.println("INICIAR PRE CALCULO FECHA CALCULO NOMINA=" + fechaCalculo + "***");
        NomTiposNominaDat n1 = new bean.controlador.nomina.NomTiposNominaDatJpaController().findNomTiposNominaDat(new NomTiposNominaDatPK(rifEmpresa, codNomina));
        if (n1 == null) {
            System.err.println("ATENCION ( ControladorAsigConcepto.calcularNomina()): Error en el metodo 'calcularNomina' al tratar de 'get' tipo de Nomina ( vacia???? ) ");
            return;
        }
        java.util.Date fechaEnFondo = n1.getUltimaRotacion1();
        java.util.Date fechaPago = n1.getProxRotacion1();
        // 
        for (Integer ficha : listaTrab) {
            /*
             List<NomAsigConceptoDat> lista = new bean.controlador.concepto.NomAsigConceptoDatJpaController().getListaAsigConceptos(rifEmpresa, baseCalculo, codNomina);
             */
            List<NomAsigConceptoDat> lista = new bean.controlador.concepto.NomAsigConceptoDatJpaController().getListaAsigConceptos(rifEmpresa, baseCalculo, codNomina, ficha);
            if (lista != null && !lista.isEmpty()) {
                //Integer codNomina;
                Integer nroTrab;
                Integer codConcepto;
                Double cantidad;
                Double monto;
                Double porcentaje;
                Double acumulado, total = 0.00;
                String frecuenciaCalculo, frecuenciaPago, formaPago;
                String codFormula = null;
                String observacion1, observacion2;
                String calcularConcepto;
                contFila = 0;  // Atributo de ambito Global. 
                for (NomAsigConceptoDat n : lista) {
                    try {
                        //  ***** Ejecutar CALCULO DE CONCEPTOS fullFill el ARRAY de Calculo ******
                        //codNomina = n.getNomAsigConceptoDatPK().getCodNomina();
                        nroTrab = n.getNomAsigConceptoDatPK().getNroTrabajador();
                        codConcepto = n.getNomAsigConceptoDatPK().getCodConcepto();
                        //
                        cantidad = (n.getCantidad() == null ? 0 : n.getCantidad());
                        monto = (n.getMonto() == null ? 0 : n.getMonto());
                        porcentaje = (n.getPorcentaje() == null ? 0 : n.getPorcentaje());
                        frecuenciaCalculo = (n.getFrecuenciaCalculo() == null ? "" : n.getFrecuenciaCalculo());
                        frecuenciaPago = (n.getFrecuenciaPago() == null ? "" : n.getFrecuenciaPago());
                        formaPago = (n.getFormaPago() == null ? "" : n.getFormaPago());
                        observacion1 = (n.getObservacion1() == null ? "" : n.getObservacion1());
                        observacion2 = (n.getObservacion2() == null ? "" : n.getObservacion2());
                        //
                        arrCalculo[contFila][Atributo.TRABAJADOR.ordinal()] = nroTrab.doubleValue();
                        arrCalculo[contFila][Atributo.COD_CONCEPTO.ordinal()] = codConcepto.doubleValue();
                        arrCalculo[contFila][Atributo.CANTIDAD.ordinal()] = cantidad;
                        arrCalculo[contFila][Atributo.MONTO.ordinal()] = monto;
                        arrCalculo[contFila][Atributo.PORCENTAJE.ordinal()] = porcentaje;
                        calcularConcepto = n.getCalcular().trim();
                        if (calcularConcepto != null && calcularConcepto.equals("S")) {
                            // ** Aqui el Piquete: **
                            total = calcularTotalConcepto(codNomina, nroTrab, codConcepto, cantidad, monto, porcentaje, frecuenciaCalculo, contFila);
                            // **********************
                        } else {
                            total = monto;
                        }
                        arrCalculo[contFila][Atributo.TOTAL.ordinal()] = total;
                        // * Gestionar NomCalculo_Dat *
                        NomCalculoDatPK nomCalculoDatPK = new NomCalculoDatPK(rifEmpresa, codNomina, nroTrab, codConcepto, fechaCalculo);
                        NomCalculoDat nomCalculoDat;
                        // * Gestionar NomCalculo1_Dat *  
                        NomCalculo1DatPK nomCalculo1DatPK;
                        NomCalculo1Dat nomCalculo1Dat;
                        // * Gestionar turno de Labor *  
                        Integer turno = new bean.controlador.personal.NomTrabajador01DatJpaController().getTurno(rifEmpresa, codNomina, nroTrab);
                        //
                        if (frecuenciaPago.equals(baseCalculo)) {
                            //System.out.println("****FECHA DE PAGO SEMANAL****"); 
                            //System.out.println("******PROCESANDO: (TipoNom=)"+n.getNomAsigConceptoDatPK().getCodNomina()+"*Trabajador="+n.getNomAsigConceptoDatPK().getNroTrabajador()+"*Concepto="+codConcepto+"**contFila="+contFila+"***");
                            cantidad = arrCalculo[contFila][Atributo.CANTIDAD.ordinal()];
                            nomCalculoDat = new NomCalculoDat(nomCalculoDatPK, turno, cantidad, monto, total, porcentaje, frecuenciaCalculo, codFormula, frecuenciaPago, formaPago, observacion1, observacion2);
                            new bean.controlador.concepto.NomCalculoDatJpaController().create(nomCalculoDat);
                            //System.out.println("******arrCalculo.Trab=" + arrCalculo[contFila][Atributo.TRABAJADOR.ordinal()] + "**arrCalculo.Total=" + arrCalculo[contFila][Atributo.TOTAL.ordinal()] + "****");
                        } else if (frecuenciaCalculo.equals("S") && frecuenciaPago.equals("Q")) {
                            if (fechaCalculo.equals(fechaPago)) {
                                //System.out.println("*Trab: "+nroTrab+"  ***FECHA DE PAGO QUINCENAL****"); 
                                // Acumular concepto 
                                //acumulado = new bean.controlador.concepto.NomCalculo1DatJpaController().getTotal(rifEmpresa,codNomina,nroTrab,codConcepto,fechaEnFondo);
                                nomCalculo1DatPK = new NomCalculo1DatPK(rifEmpresa, codNomina, nroTrab, codConcepto, fechaEnFondo);
                                nomCalculo1Dat = new bean.controlador.concepto.NomCalculo1DatJpaController().findNomCalculo1Dat(nomCalculo1DatPK);
                                if (nomCalculo1Dat != null) {
                                    cantidad = arrCalculo[contFila][Atributo.CANTIDAD.ordinal()];
                                    cantidad = cantidad + nomCalculo1Dat.getCantidad();
                                    total = total + nomCalculo1Dat.getTotal();
                                }
                                // Actualizar tabla NomCalculo_Dat *  
                                nomCalculoDat = new NomCalculoDat(nomCalculoDatPK, turno, cantidad, monto, total, porcentaje, frecuenciaCalculo, codFormula, frecuenciaPago, formaPago, observacion1, observacion2);
                                new bean.controlador.concepto.NomCalculoDatJpaController().create(nomCalculoDat);
                                // REMIND: al ejecutar el Cierre -> DELETE FROM NomCalculo1_Dat WHERE codNomina = nomina && fechaNomina = fechaPago;  .... 
                            } else if (fechaCalculo.equals(fechaEnFondo)) {
                                // Actualizar tabla NomCalculo1_Dat 
                                nomCalculo1DatPK = new NomCalculo1DatPK(rifEmpresa, codNomina, nroTrab, codConcepto, fechaCalculo);
                                nomCalculo1Dat = new NomCalculo1Dat(nomCalculo1DatPK, turno, arrCalculo[contFila][Atributo.CANTIDAD.ordinal()], monto, total, porcentaje, frecuenciaCalculo, codFormula, frecuenciaPago, formaPago, observacion1, observacion2);
                                new bean.controlador.concepto.NomCalculo1DatJpaController().create(nomCalculo1Dat);
                            } else {
                                System.err.println("ATENCION (ControladorAsigConcepto.calcularNomina() ): revisar definicion de fechas S->Q (definido ✔ ) Q->S (NO definido ? ) !.");
                            }
                        } else {
                            System.err.println("ATENCION (ControladorAsigConcepto.calcularNomina() ): rutina en Desarrollo!! ");
                        }
                        contFila++;
                    } catch (Exception ex) {
                        Logger.getLogger(ControladorAsigConcepto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   // for interno ( Conceptos asignados ) 
            }  //   if (lista != null && !lista.isEmpty())  ( Conceptos Asignados al trabajador  )
        }  // for ( Integer ficha :  listaTrab )
        Messagebox.show("NOMINA CALCULADA EXITOSAMENTE !.", "ATENCION", Messagebox.OK, Messagebox.INFORMATION);
    } // calcularNomina(). 

    //--------------------------------------------------------------------------
    //                     ** Calcular Nomina (V2) **
    // Version:      FORMULADA.   
    // Autor: HJPB. 
    // Creado: 07/10/2020
    // Actualizado: 10/12/2020.   
    //--------------------------------------------------------------------------
    private void calcularNominaV2(java.util.Date fechaCalculo, String baseCalculo, Integer codNomina) {  // baseCalculo <==> frecCalculo de ambito global. 
        List<Integer> listaTrab = new bean.controlador.concepto.NomAsigConceptoDatJpaController().getListaTrabajadoresAsig(rifEmpresa, baseCalculo, codNomina);
        if (listaTrab == null || listaTrab.isEmpty()) {
            return;
        }
        //
        cargarFormulario(rifEmpresa, codNomina);   // Recetario para la Nomina = ? 
        //
        // Limpiar tabla : NomCalculo_Dat 
        new bean.controlador.concepto.NomCalculoDatJpaController().eliminarCalculo(rifEmpresa, codNomina);
        // Limpiar tabla Aux: NomCalculo1_Dat 
        new bean.controlador.concepto.NomCalculo1DatJpaController().eliminarCalculo(rifEmpresa, codNomina, fechaCalculo);  //
        //Messagebox.show("REGISTROS ELIMINADOS..............");
        //System.out.println("INICIAR PRE CALCULO FECHA CALCULO NOMINA=" + fechaCalculo + "***");
        NomTiposNominaDat n1 = new bean.controlador.nomina.NomTiposNominaDatJpaController().findNomTiposNominaDat(new NomTiposNominaDatPK(rifEmpresa, codNomina));
        if (n1 == null) {
            System.err.println("ATENCION ( ControladorAsigConcepto.calcularNomina()): Error en el metodo 'calcularNomina' al tratar de 'get' tipo de Nomina ( vacia???? ) ");
            return;
        }
        java.util.Date fechaEnFondo = n1.getUltimaRotacion1();
        java.util.Date fechaPago = n1.getProxRotacion1();
        // *** get LISTA DE TRABAJADORES ACTIVOS ***
        for (Integer ficha : listaTrab) {
            /*
             List<NomAsigConceptoDat> lista = new bean.controlador.concepto.NomAsigConceptoDatJpaController().getListaAsigConceptos(rifEmpresa, baseCalculo, codNomina);
             */
            List<NomAsigConceptoDat> lista = new bean.controlador.concepto.NomAsigConceptoDatJpaController().getListaAsigConceptos(rifEmpresa, baseCalculo, codNomina, ficha);
            if (lista != null && !lista.isEmpty()) {
                //Integer codNomina;
                Integer nroTrab;
                Integer codConcepto;
                Double cantidad;
                Double monto;
                Double porcentaje;
                Double acumulado, total = 0.00;
                String frecuenciaCalculo, frecuenciaPago, formaPago;
                String codFormula = null;
                String observacion1, observacion2;
                String calcularConcepto;
                contFila = 0;  // Atributo de ambito Global. 
                for (NomAsigConceptoDat n : lista) {   // get LISTA DE CONCEPTOS ASIGNADOS x TRABAJADOR.  
                    try {
                        //  ***** Ejecutar CALCULO DE CONCEPTOS fullFill el ARRAY de Calculo ******
                        //codNomina = n.getNomAsigConceptoDatPK().getCodNomina();
                        nroTrab = n.getNomAsigConceptoDatPK().getNroTrabajador();
                        codConcepto = n.getNomAsigConceptoDatPK().getCodConcepto();
                        //
                        cantidad = (n.getCantidad() == null ? 0 : n.getCantidad());
                        monto = (n.getMonto() == null ? 0 : n.getMonto());
                        porcentaje = (n.getPorcentaje() == null ? 0 : n.getPorcentaje());
                        frecuenciaCalculo = (n.getFrecuenciaCalculo() == null ? "" : n.getFrecuenciaCalculo());
                        frecuenciaPago = (n.getFrecuenciaPago() == null ? "" : n.getFrecuenciaPago());
                        formaPago = (n.getFormaPago() == null ? "" : n.getFormaPago());
                        observacion1 = (n.getObservacion1() == null ? "" : n.getObservacion1());
                        observacion2 = (n.getObservacion2() == null ? "" : n.getObservacion2());
                        //
                        arrCalculo[contFila][Atributo.TRABAJADOR.ordinal()] = nroTrab.doubleValue();
                        arrCalculo[contFila][Atributo.COD_CONCEPTO.ordinal()] = codConcepto.doubleValue();
                        arrCalculo[contFila][Atributo.CANTIDAD.ordinal()] = cantidad;
                        arrCalculo[contFila][Atributo.MONTO.ordinal()] = monto;
                        arrCalculo[contFila][Atributo.PORCENTAJE.ordinal()] = porcentaje;
                        calcularConcepto = n.getCalcular().trim();
                        if (calcularConcepto != null && calcularConcepto.equals("S")) {  // 
                            // ** AQUI ESTA EL PIQUETE: **
                            /*
                             if (codConcepto <= 501) {   // Instruccion CENTINELA - TEMPORAL solo para efectos de implantacion!.  
                             total = totalConcepto(codNomina, nroTrab, codConcepto, fechaCalculo);   // v.FORMULADA.  
                             } else {
                             total = calcularTotalConcepto(codNomina, nroTrab, codConcepto, cantidad, monto, porcentaje, frecuenciaCalculo, contFila);
                             }
                             */
                            total = totalConcepto(codNomina, nroTrab, codConcepto, fechaCalculo);   // v.FORMULADA.  
                            // **********************
                        } else {
                            //total = monto;
                            total = monto * (cantidad <= 0 ? 1 : cantidad) * (porcentaje <= 0 ? 1 : porcentaje / 100.00);
                        }  // if/else : if (calcularConcepto != null && calcularConcepto.equals("S"))
                        arrCalculo[contFila][Atributo.TOTAL.ordinal()] = total;
                        // * Gestionar NomCalculo_Dat *
                        NomCalculoDatPK nomCalculoDatPK = new NomCalculoDatPK(rifEmpresa, codNomina, nroTrab, codConcepto, fechaCalculo);
                        NomCalculoDat nomCalculoDat;
                        // * Gestionar NomCalculo1_Dat *  
                        NomCalculo1DatPK nomCalculo1DatPK;
                        NomCalculo1Dat nomCalculo1Dat;
                        // * Gestionar turno de Labor *  
                        Integer turno = new bean.controlador.personal.NomTrabajador01DatJpaController().getTurno(rifEmpresa, codNomina, nroTrab);
                        //
                        if (frecuenciaPago.equals(baseCalculo)) {
                            //System.out.println("****FECHA DE PAGO SEMANAL****"); 
                            //System.out.println("******PROCESANDO: (TipoNom=)"+n.getNomAsigConceptoDatPK().getCodNomina()+"*Trabajador="+n.getNomAsigConceptoDatPK().getNroTrabajador()+"*Concepto="+codConcepto+"**contFila="+contFila+"***");
                            cantidad = arrCalculo[contFila][Atributo.CANTIDAD.ordinal()];
                            nomCalculoDat = new NomCalculoDat(nomCalculoDatPK, turno, cantidad, monto, total, porcentaje, frecuenciaCalculo, codFormula, frecuenciaPago, formaPago, observacion1, observacion2);
                            new bean.controlador.concepto.NomCalculoDatJpaController().create(nomCalculoDat);
                            //System.out.println("******arrCalculo.Trab=" + arrCalculo[contFila][Atributo.TRABAJADOR.ordinal()] + "**arrCalculo.Total=" + arrCalculo[contFila][Atributo.TOTAL.ordinal()] + "****");
                        } else if (frecuenciaCalculo.equals("S") && frecuenciaPago.equals("Q")) {
                            if (fechaCalculo.equals(fechaPago)) {
                                //System.out.println("*Trab: "+nroTrab+"  ***FECHA DE PAGO QUINCENAL****"); 
                                // Acumular concepto 
                                //acumulado = new bean.controlador.concepto.NomCalculo1DatJpaController().getTotal(rifEmpresa,codNomina,nroTrab,codConcepto,fechaEnFondo);
                                nomCalculo1DatPK = new NomCalculo1DatPK(rifEmpresa, codNomina, nroTrab, codConcepto, fechaEnFondo);
                                nomCalculo1Dat = new bean.controlador.concepto.NomCalculo1DatJpaController().findNomCalculo1Dat(nomCalculo1DatPK);
                                if (nomCalculo1Dat != null) {
                                    cantidad = arrCalculo[contFila][Atributo.CANTIDAD.ordinal()];
                                    cantidad = cantidad + nomCalculo1Dat.getCantidad();
                                    total = total + nomCalculo1Dat.getTotal();
                                }
                                // Actualizar tabla NomCalculo_Dat *  
                                nomCalculoDat = new NomCalculoDat(nomCalculoDatPK, turno, cantidad, monto, total, porcentaje, frecuenciaCalculo, codFormula, frecuenciaPago, formaPago, observacion1, observacion2);
                                new bean.controlador.concepto.NomCalculoDatJpaController().create(nomCalculoDat);
                                // REMIND: al ejecutar el Cierre -> DELETE FROM NomCalculo1_Dat WHERE codNomina = nomina && fechaNomina = fechaPago;  .... 
                            } else if (fechaCalculo.equals(fechaEnFondo)) {
                                // Actualizar tabla NomCalculo1_Dat 
                                nomCalculo1DatPK = new NomCalculo1DatPK(rifEmpresa, codNomina, nroTrab, codConcepto, fechaCalculo);
                                nomCalculo1Dat = new NomCalculo1Dat(nomCalculo1DatPK, turno, arrCalculo[contFila][Atributo.CANTIDAD.ordinal()], monto, total, porcentaje, frecuenciaCalculo, codFormula, frecuenciaPago, formaPago, observacion1, observacion2);
                                new bean.controlador.concepto.NomCalculo1DatJpaController().create(nomCalculo1Dat);
                            } else {
                                System.err.println("ATENCION (ControladorAsigConcepto.calcularNomina() ): revisar definicion de fechas S->Q (definido ✔ ) Q->S (NO definido ? ) !.");
                            }
                        } else {
                            System.err.println("ATENCION (ControladorAsigConcepto.calcularNomina() ): rutina en Desarrollo!! ");
                        }
                        contFila++;
                    } catch (Exception ex) {
                        Logger.getLogger(ControladorAsigConcepto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   // for interno ( Conceptos asignados ) 
            }  //   if (lista != null && !lista.isEmpty())  ( Conceptos Asignados al trabajador  )
        }  // for ( Integer ficha :  listaTrab )
        //Messagebox.show("NOMINA CALCULADA EXITOSAMENTE !.", "ATENCION", Messagebox.OK, Messagebox.INFORMATION);
        new bean.utilitario.libreria.LibreriaHP().messageBox("NOMINA CALCULADA CORRECTAMENTE !.");
    } // calcularNominaV2().   -version FORMULADA- 

    //--------------------------------------------------------------------------
    //  Autor:  HJPB. 
    //  ** formatear FORMULA/ECUACION**  
    //--------------------------------------------------------------------------
    private String formatearEcuacion(String ecuacion) {
        return ("[ " + ecuacion + " + 0.00 ]");
    } // formatearEcuacion(); 

    //--------------------------------------------------------------------------
    //  Autor:  HJPB. 
    //  ** Receta para el calculo de conceptos **  
    //--------------------------------------------------------------------------
    private void cargarFormulario(String rifEmpresa, Integer codNomina) {
        final String TOTAL = "T";
        List<NomFormulaDat> listaFormulas = new bean.controlador.concepto.NomFormulaDatJpaController().getListaFormula(rifEmpresa, codNomina);
        Integer contFilas = 0;
        for (NomFormulaDat f : listaFormulas) {
            //System.out.println(" codNom, codFormula, accion, correlativo, formula+  ");
            //System.out.println(" " + f.getNomFormulaDatPK().getCodNomina() + "," + f.getNomFormulaDatPK().getCodFormula() + "," + f.getNomFormulaDatPK().getAccion() + "," + f.getNomFormulaDatPK().getCorrelativo() + "," + f.getFormula() + " ");
            arrIndice[contFilas][Indice.NOMINA.ordinal()] = f.getNomFormulaDatPK().getCodNomina();
            arrFormula[contFilas][IndiceF.ACCION.ordinal()] = f.getNomFormulaDatPK().getAccion();
            arrIndice[contFilas][Indice.COD_CONCEPTO.ordinal()] = f.getNomFormulaDatPK().getCodConcepto();
            arrIndice[contFilas][Indice.CORRELATIVO.ordinal()] = f.getNomFormulaDatPK().getCorrelativo();
            if (f.getNomFormulaDatPK().getAccion().equals(TOTAL)) {
                arrIndice[contFilas][Indice.TOTAL.ordinal()] = SI;
            } else {
                arrIndice[contFilas][Indice.TOTAL.ordinal()] = NO;
            } // if-else.   
            arrFormula[contFilas][IndiceF.FORMULA.ordinal()] = f.getFormula();
            contFilas++;
        } // for    
        // --------------eliminame------------------------
        /*
         Integer i = 0;
         do {
         System.out.println("()NOMINA=" + arrIndice[i][Indice.NOMINA.ordinal()] + "***CONCEPTO=" + arrIndice[i][Indice.COD_CONCEPTO.ordinal()] + "INDICE TOTAL=" + arrIndice[i][Indice.TOTAL.ordinal()] + "*****");
         i++;
         } while (i <= arrFormula.length && arrIndice[i][Indice.NOMINA.ordinal()]!=null );
         */
    } // cargarLogicaCalculo()

//--------------------------------------------------------------------------
//  Autor:  HJPB.
//  Creado: 11 diciembre 2020.
//  revisar la exitencia / o NO del formulario para el concepto indicado en 
//  el 'arrFormula'.  
//  NOTA:
//   (1). Expresion como if ( a==b ) ... funcionará regularmente en java, pero 
//        no es garantia de que siempre funcione. 
//        Should be better : if ( a.intValue() == b.intValue() ) .... 
//        ( htpps://www.dorky.com/1225 ). 
//--------------------------------------------------------------------------
    private Boolean existeFormula(Integer codNomina, Integer codConcepto) {
        Integer i = 0;
        Boolean termine = Boolean.FALSE;
        do {
            //System.out.println("(existe Formula)NOMINA=" + arrIndice[i][Indice.NOMINA.ordinal()] + "***CONCEPTO=" + arrIndice[i][Indice.COD_CONCEPTO.ordinal()] + "INDICE TOTAL=" + arrIndice[i][Indice.TOTAL.ordinal()] + "*****");
            if ((arrIndice[i][Indice.NOMINA.ordinal()].intValue() == codNomina.intValue()) && (arrIndice[i][Indice.COD_CONCEPTO.ordinal()].intValue() == codConcepto.intValue()) && (arrIndice[i][Indice.TOTAL.ordinal()].intValue() == SI)) {  // Posee resultado TOTAL >> break the LOOP  
                //if ( ( arrIndice[i][Indice.NOMINA.ordinal()] == 1 ) && ( arrIndice[i][Indice.COD_CONCEPTO.ordinal()] == 500 ) && ( arrIndice[i][Indice.TOTAL.ordinal()] == 1 ) ) {  // Posee resultado TOTAL >> break the LOOP  
                termine = Boolean.TRUE;
                //System.out.println("(existe Formula-ENCONTRADO-)NOMINA=" + arrIndice[i][Indice.NOMINA.ordinal()] + "***CONCEPTO=" + arrIndice[i][Indice.COD_CONCEPTO.ordinal()] + "INDICE TOTAL=" + arrIndice[i][Indice.TOTAL.ordinal()] + "*****");
            }
            i++;
        } while (i <= arrFormula.length && !termine && arrIndice[i][Indice.NOMINA.ordinal()] != null);
        return (termine);
    } // existeFormula()

    //--------------------------------------------------------------------------
    private Boolean condicion(String cadena) {
        Boolean ok;
        if (cadena.substring(0, 2).toUpperCase().equals(CONDICION)) {
            ok = Boolean.TRUE;
        } else {
            ok = Boolean.FALSE;
        }
        return (ok);
    } // condicion().  Instruccion 'SI' ( IF,... =.  

    //--------------------------------------------------------------------------  
    //--------------------------------------------------------------------------
    private void setEditSsnCamposClaves() {
        // =============================
        // *Listitem de campos claves: *
        // =============================
        org.zkoss.zul.Listitem listItem = lbxAsigConceptos.getSelectedItem();
        java.util.List celdas = listItem.getChildren();
        org.zkoss.zul.Listcell listCell = (org.zkoss.zul.Listcell) celdas.get(0);
        // *codNomina*
        Integer ssnCodNomina = -1;
        String codNominaS = listCell.getLabel();
        if (codNominaS != null && !codNominaS.isEmpty()) {
            ssnCodNomina = Integer.parseInt(codNominaS);
        }
        // *nroTrabajador*
        Integer ssnNroTrabajador = -1;
        listCell = (org.zkoss.zul.Listcell) celdas.get(1);
        String nroTrabajadorS = listCell.getLabel();
        if (nroTrabajadorS != null && !nroTrabajadorS.isEmpty()) {
            ssnNroTrabajador = Integer.parseInt(nroTrabajadorS);
        }
        // *codConcepto*
        Integer ssnCodConcepto = -1;
        listCell = (org.zkoss.zul.Listcell) celdas.get(3);
        String codConceptoS = listCell.getLabel();
        if (codConceptoS != null && !codConceptoS.isEmpty()) {
            ssnCodConcepto = Integer.parseInt(codConceptoS);
        }
        //System.out.println("**codNom=" + ssnCodNomina + "**nroTrab=" + ssnNroTrabajador + "**codConcepto=" + ssnCodConcepto + "**fechaNomina=" + ssnFechaNomina + "**");
        //System.out.println("**codNom=" + ssnCodNomina + "**nroTrab=" + ssnNroTrabajador + "**codConcepto=" + ssnCodConcepto + "**");  
        Sessions.getCurrent().setAttribute("ssnCodNomina", ssnCodNomina);
        Sessions.getCurrent().setAttribute("ssnNroTrabajador", ssnNroTrabajador);
        Sessions.getCurrent().setAttribute("ssnCodConcepto", ssnCodConcepto);
    }  // setEditSSnCamposClaves(). 

    //--------------------------------------------------------------------------
    private void setNewSsnCamposClaves() {
        // nomConceptoHistDatPK(ssnRifEmpresa,-1,-1,-1,new java.util.Date()), 0.00, 0.00, 0.00, 0, null);
        Sessions.getCurrent().setAttribute("ssnCodNomina", -1);
        Sessions.getCurrent().setAttribute("ssnNroTrabajador", -1);
        Sessions.getCurrent().setAttribute("ssnCodConcepto", -1);
    }  // setSSnCamposClaves(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void cerrarNomina(java.util.Date fechaCierre, String baseCalculo, Integer codNomina) {
        //System.out.println("Fecha cierre="+fechaCierre+"***BC="+baseCalculo+"*** ");
        NomTiposNominaDat n1 = new bean.controlador.nomina.NomTiposNominaDatJpaController().findNomTiposNominaDat(new NomTiposNominaDatPK(rifEmpresa, codNomina));
        if (n1 == null) {
            System.err.println("ATENCION ( ControladorAsigConcepto.cerrarNomina()): Error en el metodo 'calcularNomina' al tratar de 'get' tipo de Nomina ( vacia???? ) ");
            return;
        }
        java.util.Date fechaEnFondo = n1.getUltimaRotacion1();
        java.util.Date fechaPago = n1.getProxRotacion1();
        // RPP:
        //  i.   Insertar reg en la tabla Hist ==> NomConceptoHistDatJpaController
        new bean.controlador.concepto.NomConceptoHistDatJpaController().procesarRegsHist(rifEmpresa, fechaCierre, codNomina);
        //  ii. Gestionar Tabla de * Calculo_Dat   *  
        new bean.controlador.concepto.NomCalculoDatJpaController().eliminarCalculo(rifEmpresa, codNomina);
        //  ii.1. gestionar Tabla de * Calculo1_Dat *  Si fechaCalculo = fecha de Pago exe la siguiente instruccion:  ....
        if (fechaCierre.equals(fechaPago)) {
            new bean.controlador.concepto.NomCalculo1DatJpaController().eliminarCalculo(rifEmpresa, codNomina, fechaEnFondo);  //
        }
        //  iii.  Inicializar atributos tabla AsigConcepto. ==> NomAsigConceptoDatJpaController
        new bean.controlador.concepto.NomAsigConceptoDatJpaController().inicializarAtributos(rifEmpresa, codNomina);
        //  iv. Actualizar fecha de rotacion de la tabla TiposNomina. ==> NomTiposNominaDatJpaController
        new bean.controlador.nomina.NomTiposNominaDatJpaController().actualizarRotacionNomina(rifEmpresa, codNomina, baseCalculo, fechaCierre, fechaPago);
        // fin 
        Messagebox.show("NOMINA CERRADA EXITOSAMENTE !.", "ATENCION", Messagebox.OK, Messagebox.INFORMATION);
    }  // cerrarNomina().  

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
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
    //  Autor:  HJPB. 
    //  REMIND: el tratamiento de la variables 'concepto' es y debe ser solo en 
    //          mayuscula.  ****************************
    //--------------------------------------------------------------------------
    private Double procesarConcepto(Integer codNomina, Integer nroTrabajador, String concepto) {
        Double valor = 0.00;
        Integer codConcepto, correlativo;
        if (!concepto.isEmpty() && concepto.length() > 1) {
            String indicativo = concepto.substring(0, 1);
            switch (indicativo) {
                case "C": {
                    //System.out.println("DENTRO DE getC");
                    if (concepto.length() <= 2) {  // Restringido a la Var del arrIndice--> C[1..9]
                        correlativo = Integer.parseInt(concepto.substring(1, concepto.length()));
                        valor = getC(correlativo); // pick this up del array 'R'  ( Resultado ) 
                        //System.out.println("correlativo="+correlativo+"..valor="+valor+".....");
                    } else {
                        codConcepto = Integer.parseInt(concepto.substring(1, concepto.length()));
                        valor = getCantidad(nroTrabajador, codConcepto);
                    }
                    //System.out.println("procesar opcion CANTIDAD=" + concepto + " valor=" + valor);
                    break;
                }  // case "C"
                case "M": {
                    codConcepto = Integer.parseInt(concepto.substring(1, concepto.length()));
                    valor = getMonto(nroTrabajador, codConcepto);
                    //System.out.println("procesar opcion MONTO=" + concepto + ". nroTrabajador=" + nroTrabajador + ". Cod concepto=" + codConcepto + ". Valor=" + valor);
                    break;
                }  // case "M"
                case "P": {
                    codConcepto = Integer.parseInt(concepto.substring(1, concepto.length()));
                    valor = getPorcentaje(nroTrabajador, codConcepto);
                    //System.out.println("procesar opcion PORCENTAJE=" + concepto + "");
                    break;
                }  // case "P"
                case "F": {
                    //String funcion = concepto.substring(1,concepto.length());  
                    //System.out.println("procesar opcion FUNCION=" + concepto + "*****");
                    valor = 0.00;
                    if (concepto != null) {
                        switch (concepto) {
                            case "FLUNES": {
                                valor = cantLunes * 1.00;  // previamente calculada en el metodo: ajustarParametrosCalculo()
                                /*
                                 try {
                                 java.util.Date f1,f2;
                                 f1=new LibreriaHP().formatoFecha.parse("01/11/2020");
                                 f2=new LibreriaHP().formatoFecha.parse("15/11/2020");
                                 valor = getLunes(f1,f2).doubleValue();
                                 break;  
                                 } catch (ParseException ex) {
                                 Logger.getLogger(ControladorAsigConcepto.class.getName()).log(Level.SEVERE, null, ex);
                                 }
                                 */
                                break;
                            }
                            default:
                                valor = 0.00;
                        } // switch case F)uncion. 
                        //System.out.println("procesar opcion FUNCION=" + concepto + "");
                    }  // if concepto!=null,....
                    break;
                }  // case "V"

                case "V": {
                    valor = getValorVar(concepto);
                    //System.out.println("procesar opcion VARIABLE=" + concepto + " Valor=" + valor);
                    break;
                }  // case "V"
                case "R": {
                    correlativo = Integer.parseInt(concepto.substring(1, concepto.length()));
                    valor = getR(correlativo);    // get Resultado parcial !!!.  
                    //System.out.println("procesar opcion RESULTADO PARCIAL=" + concepto + ". nroTrabajador=" + nroTrabajador + ". R=" + valor);
                    break;
                }  // case "R"
                case "T": {
                    codConcepto = Integer.parseInt(concepto.substring(1, concepto.length()));
                    valor = getTotal(nroTrabajador, codConcepto);
                    //System.out.println("procesar opcion MONTO=" + concepto + ". nroTrabajador=" + nroTrabajador + ". Cod concepto=" + codConcepto + ". Valor=" + valor);
                    break;
                }  // case "T"
                default: {
                    return 0.00;
                }
            }  // switch
        }
        return (valor);
    } // procesarConcepto()

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Double getValorToken(String token, Integer codNomina, Integer nroTrabajador, java.util.Date fechaNomina) {
        String tipoVar = null;
        Double valor = 0.00;
        Integer codConcepto = 0;
        if (tipoToken(token) == ClassArregloTokens.TIPO_TOKEN.NUMERO) {
            valor = Double.parseDouble(token);
            //System.out.println(" token.NUMERO="+token+" ");  
        } else if (tipoToken(token) == ClassArregloTokens.TIPO_TOKEN.CONCEPTO) {  // procesarConcepto().   <<< **AQUI ESTA EL PIQUETE**    
            //System.out.println(" token.CONCEPTO=" + token + " ");
            valor = procesarConcepto(codNomina, nroTrabajador, token);
        } else {
            System.err.println("****Que token es esto?='" + token + "', tipo token=" + tipoToken(token) + "****");
        }
        return (valor);
    } // getValorToken().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public Double procesarEcuacionNomina(Integer codNomina, Integer nroTrabajador, java.util.Date fecha, ClassArregloTokens classArregloTokens) {
        //Object token = null;
        String tipoVar = null;
        Integer codConcepto;
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
        ClassArregloTokens.TIPO_TOKEN[] l = classArregloTokens.getArregloTipoToken();
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
            if (l[i] == ClassArregloTokens.TIPO_TOKEN.SIMBOLO) {  // "[","]","(","(" 
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
                        ClassArregloTokens.TIPO_TOKEN[] l2 = new ClassArregloTokens.TIPO_TOKEN[MAX_TOKENS];
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
                            // ********************AQUI EL PIQUETE********************************************
                            r = procesarEcuacionNomina(codNomina, nroTrabajador, fecha, classArrTokens2); // ** Aplicar RECURSIVIDAD **
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
                            //--------------------------------------------------
                            // <<<<<<<< Procesar CALCULOS DE CONCEPTOS>>>>>>>>>>
                            //--------------------------------------------------
                            while (!pila1.vacia()) {
                                //System.out.println("Tope pila1 ="+pila1.tope()+"     ");  
                                //token = pila1.pop().toString();
                                //System.out.println("******token="+token+"     ");  
                                v2 = getValorToken(pila1.pop().toString(), codNomina, nroTrabajador, fecha);
                                //v2 = Double.parseDouble(pila1.pop().toString());
                                //System.out.println("******v2="+v2+"     ");
                                if (!pila1.vacia()) {
                                    operador = (String) pila1.pop();
                                } else {
                                    pila2.push(v2);
                                    operador = null;
                                }
                                if (operador != null && (operador.equals("*") || operador.equals("/"))) {
                                    ////////////////////////////////////////////
                                    //*****AQUI ESTA EL PIQUETE*1***************
                                    ////////////////////////////////////////////
                                    v1 = getValorToken(pila1.pop().toString(), codNomina, nroTrabajador, fecha);
                                    //v1 = Double.parseDouble(pila1.pop().toString());
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
                                //token = (String) pila2.pop();
                                v2 = getValorToken(pila2.pop().toString(), codNomina, nroTrabajador, fecha);
                                //v2 = Double.parseDouble(pila2.pop().toString());
                                if (!pila2.vacia()) {
                                    operador = (String) pila2.pop();
                                } else {
                                    operador = null;
                                }
                                if (operador != null && (operador.equals("+") || operador.equals("-"))) {
                                    //token = pila2.pop();  
                                    ////////////////////////////////////////////
                                    //*****AQUI ESTA EL PIQUETE*2***************
                                    ////////////////////////////////////////////
                                    v1 = getValorToken(pila2.pop().toString(), codNomina, nroTrabajador, fecha);
                                    //v1 = Double.parseDouble(pila2.pop().toString());
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
    } // procesarEcuacionNomina(Integer codNomina, Integer nroTrabajador, ClassArregloTokens classArregloTokens); 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean operadorValido(String operador, int contOperadores) {
        Boolean ok = Boolean.FALSE;
        for (int i = 0; (i < contOperadores) && !ok; i++) {
            if (CONDICIONALES[i].equals(operador)) {
                ok = Boolean.TRUE;
            }
        } // for
        return (ok);
    } // operadorValido().

    //--------------------------------------------------------------------------
    // *IMPORTANTE: 
    //  (*.1.)metodo de argumentos discretos, un valor, un concepto ..  <--> un solo termino. 
    //  (*.2.)condicion necesaria es que la ecuacion-condicional este previamente validada.  
    //  SI  <arg1> CONDICIONAL  <arg2> ; <arg3> : <arg4> OPCIONAL 
    //  p0         p1                 p2        p3       p4   --> p)ALABRAS RESERVADAS.  
    //--------------------------------------------------------------------------
    public Double procesarCondicion(Integer codNomina, Integer nroTrabajador, java.util.Date fecha, ClassArregloTokens token) {
        Double r = 0.00;
        //ClassArregloTokens token = dividirTokenizer(condicion);
        Integer contToken = 0;
        Integer cantToken = token.getContadorTokens() + 1;           // enumerados desde 0 -> Ajustar + 1.  
        String[] t = token.getArregloTokens();
        //---------------------
        // get los argumentos:
        //---------------------
        String arg1S = t[1];
        String arg2S = t[3];
        String arg3S = t[5];
        String arg4S = t[7];
        //System.out.println("Aquica ProcesarCondicion,.. argumentos: " + arg1S + ", " + arg2S + ", " + arg3S + ", " + arg4S);
        //---------------------
        // get operador relacional/logico: MAYOR, MENOR, IGUAL, DIF
        //---------------------
        String operadorRelacional = t[2].toUpperCase();
        //System.out.println("Operador RELACIONAL="+operadorRelacional+" ");           
        if (operadorValido(operadorRelacional, CONT_CONDICIONALES)) {
            // gestion arg1:  --------------------------------------------------
            arg1S = formatearEcuacion(arg1S);
            ClassArregloTokens tArg1 = new bean.controlador.concepto.ControladorDefFormula().dividirTokens(arg1S);
            tArg1 = new bean.controlador.concepto.ControladorDefFormula().validarEcuacion(tArg1);
            Double arg1 = 0.00;
            if (tArg1 != null) {
                arg1 = procesarEcuacionNomina(codNomina, nroTrabajador, fecha, tArg1);
                //System.out.println("arg1S=" + arg1S + "  arg1=" + arg1);
            } else {
                return (0.00);
            }
            // gestion arg2: --------------------------------------------------- 
            arg2S = formatearEcuacion(arg2S);
            ClassArregloTokens tArg2 = new bean.controlador.concepto.ControladorDefFormula().dividirTokens(arg2S);
            tArg2 = new bean.controlador.concepto.ControladorDefFormula().validarEcuacion(tArg2);
            Double arg2 = 0.00;
            if (tArg2 != null) {
                arg2 = procesarEcuacionNomina(codNomina, nroTrabajador, fecha, tArg2);
            } else {
                return (0.00);
            }
            // gestion arg3:---------------------------------------------------- 
            arg3S = formatearEcuacion(arg3S);
            ClassArregloTokens tArg3 = new bean.controlador.concepto.ControladorDefFormula().dividirTokens(arg3S);
            tArg3 = new bean.controlador.concepto.ControladorDefFormula().validarEcuacion(tArg3);
            Double arg3 = 0.00;
            if (tArg3 != null) {
                arg3 = procesarEcuacionNomina(codNomina, nroTrabajador, fecha, tArg3);
            } else {
                return (0.00);
            }
            // gestion arg4: ---------------------------------------------------
            arg4S = formatearEcuacion(arg4S);
            ClassArregloTokens tArg4 = new bean.controlador.concepto.ControladorDefFormula().dividirTokens(arg4S);
            tArg4 = new bean.controlador.concepto.ControladorDefFormula().validarEcuacion(tArg4);
            Double arg4 = 0.00;
            if (tArg4 != null) {
                arg4 = procesarEcuacionNomina(codNomina, nroTrabajador, fecha, tArg4);
            } else {
                return (0.00);
            }
            //
            switch (operadorRelacional) {
                case "MAYOR": {
                    if (arg1 > arg2) {
                        r = arg3;
                    } else {
                        r = arg4;
                    }
                    //System.out.println("MAYOR r="+arg3+"  ???????'");
                    break;
                }  // case "MAYOR" : {
                case "MAYOR_IGUAL": {
                    if (arg1 >= arg2) {
                        r = arg3;
                    } else {
                        r = arg4;
                    }
                    break;
                }  // case "MAYOR_IGUAL" : {
                case "MENOR": {
                    if (arg1 < arg2) {
                        r = arg3;
                    } else {
                        r = arg4;
                    }
                    //System.out.println("MENOR  r=" + arg3 + " ? else r='" + arg4);
                    break;
                }  // case "MAYOR" : {
                case "MENOR_IGUAL": {
                    if (arg1 <= arg2) {
                        r = arg3;
                    } else {
                        r = arg4;
                    }
                    break;
                }  // case "MENOR_IGUAL" : {
                case "IGUAL": {
                    if (arg1 == arg2) {
                        r = arg3;
                    } else {
                        r = arg4;
                    }
                    break;
                }  // case "IGUAL" : {
                case "DIF": {
                    if (arg1 != arg2) {
                        r = arg3;
                    } else {
                        r = arg4;
                    }
                    break;
                }  // case "DIF" : {
                default: {
                    r = 0.00;
                }
            } // swicth()
        }
        return (r);
    } // procesarCondicion(Integer codNomina, Integer nroTrabajador, ClassArregloTokens classArregloTokens); 

    //**************************************************************************
    //   ** NO ELIMINAR ** 
    //**************************************************************************
    private void pruebaNOMINA() {
        // *PARAMETROS*---------------------------------------------------------
        Integer codNomina = 1;
        Integer codConcepto = 500;
        Integer nroTrabajador = 1003;
        java.util.Date fechaProceso = null;
        try {
            fechaProceso = new LibreriaHP().formatoFecha.parse("22/11/2020");

        } catch (ParseException ex) {
            Logger.getLogger(ControladorDefFormula.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        // *FIN PARAMETROS*-----------------------------------------------------
        cargarFormulario("J-41309179-8", codNomina);   // Recetario para la Nomina = ? 
        if (existeFormula(codNomina, codConcepto)) {      // 
            System.out.println("----------------------------------------------------");
            System.out.println("El concepto '" + codConcepto + "' de la nomina '" + codNomina + "' posee FORMULARIO");
            System.out.println("----------------------------------------------------");
            System.out.println("            ** PRUEBA EN DESARROLLO (INI)**       ");
            System.out.println(" CATALOGO REGISTROS: ");
            Boolean termine = false;
            for (Integer i = 0; (i < arrIndice.length) && (!termine); i++) {
                if (arrIndice[i][Indice.NOMINA.ordinal()] == null) {
                    termine = true;
                } else {
                    System.out.print("NOMINA=" + arrIndice[i][Indice.NOMINA.ordinal()]);
                    System.out.print("-COD Concepto=" + arrIndice[i][Indice.COD_CONCEPTO.ordinal()]);
                    System.out.print("-CORRELATIVO=" + arrIndice[i][Indice.CORRELATIVO.ordinal()]);
                    System.out.print("-TOTAL?=" + arrIndice[i][Indice.TOTAL.ordinal()]);
                    System.out.println("-FORMULA=" + arrFormula[i][IndiceF.FORMULA.ordinal()]);
                }
            }  // for   
            System.out.println("RESULTADO:---------------------------------------------");
            for (Integer i = 0; (i < arrIndice.length && arrIndice[i][Indice.NOMINA.ordinal()] != null); i++) {
                if ((arrIndice[i][Indice.NOMINA.ordinal()].intValue() == codNomina.intValue()) && (arrIndice[i][Indice.COD_CONCEPTO.ordinal()].intValue() == codConcepto.intValue())) {
                    //System.out.println("Array=" + arrIndice[i][Indice.NOMINA.ordinal()] + " codNomina=" + codNomina);
                    System.out.print("NOMINA=" + arrIndice[i][Indice.NOMINA.ordinal()]);
                    System.out.print("-COD Concepto=" + arrIndice[i][Indice.COD_CONCEPTO.ordinal()]);
                    System.out.print("-CORRELATIVO=" + arrIndice[i][Indice.CORRELATIVO.ordinal()]);
                    System.out.print("-ULTIMO?=" + arrIndice[i][Indice.TOTAL.ordinal()]);
                    System.out.println("-FORMULA=" + arrFormula[i][IndiceF.FORMULA.ordinal()]);
                }
            }
            System.out.println("----------------------------------------------------");
            System.out.println("            ** PRUEBA EN DESARROLLO (FIN)**       ");
            System.out.println("----------------------------------------------------");
            // CONTINUAR; ////////*procesarEcuacion de *NOMINA*/////////////////
            String formula = "";
            Double total = 0.00;
            ClassArregloTokens tokens;
            termine = Boolean.FALSE;
            for (Integer i = 0; (i <= arrIndice.length) && !termine; i++) {
                if (arrIndice[i][Indice.NOMINA.ordinal()] == null) {
                    termine = true;
                } else {
                    if ((arrIndice[i][Indice.NOMINA.ordinal()].intValue() == codNomina.intValue()) && (arrIndice[i][Indice.COD_CONCEPTO.ordinal()].intValue() == codConcepto.intValue())) {
                        formula = arrFormula[i][IndiceF.FORMULA.ordinal()];
                        formula = "[" + formula + "+ 0.00 ]";        // formatear la ecuacion para procesarla. 
                        System.out.println("formula to process= " + formula + " *****");
                        tokens = new bean.controlador.concepto.ControladorDefFormula().dividirTokens(formula);
                        //--------------eliminame:
                        String[] t = tokens.getArregloTokens();         // array token
                        ClassArregloTokens.TIPO_TOKEN[] l = tokens.getArregloTipoToken();  // logica token
                        t = tokens.getArregloTokens();
                        l = tokens.getArregloTipoToken();
                        for (int j = 0; j < tokens.getContadorTokens(); j++) {
                            System.out.print(t[j] + " ");
                        }
                        System.out.println();
                        for (int j = 0; j < tokens.getContadorTokens(); j++) {
                            System.out.print(l[j] + " ");
                        }
                        System.out.println();
                        //---------------------
                        System.err.println("***VALIDAR ECUACION!!!***:");
                        tokens = new bean.controlador.concepto.ControladorDefFormula().validarEcuacion(tokens);
                        if (tokens != null) {
                            // *Formula / ecuacion VALIDA
                            System.err.println("***ECUACION/FORMULA VALIDA***:" + formula);
                            //--------------------------------------------------
                            //   CALCULAR TOTAL
                            //--------------------------------------------------
                            total = total + procesarEcuacionNomina(codNomina, nroTrabajador, fechaProceso, tokens);
                            // Actualizar registro y enviarlo a la tabla NomCalculo_Dat
                        } else {
                            System.err.println("***ECUACION/FORMULA INVALIDA***:" + formula);
                            total = 0.00;
                        }// if-else ( tokens!=null )
                    }  // if ((arrIndice[i][Indice.NOMINA.ordinal() ...
                }  // if (arrIndice[i][Indice.NOMINA.ordinal()] == null)
            } // for
            System.out.println("**TOTAL = " + total + " ????????????????????????????");
        } else {
            System.out.println("El concepto '" + codConcepto + "' de la nomina '" + codNomina + "' NO posee FORMULARIO");
        }
    }  // pruebaNOMINA().  

    //--------------------------------------------------------------------------
    private void eliminame() {
        cargarFormulario(rifEmpresa, 1);
        if (existeFormula(1, 100)) {
            Messagebox.show("EXISTE FORMULARIO PARA CONCEPTO 100 NOMINA 1 ");
        } else {
            Messagebox.show("NO EXISTE FORMULARIO PARA CONCEPTO 100 NOMINA 1 ");
        }
        if (existeFormula(1, 500)) {
            Messagebox.show("CORRECTO: EXISTE FORMULARIO PARA CONCEPTO 500 NOMINA 1 ");
        } else {
            Messagebox.show("INCORRECTO: NO EXISTE FORMULARIO PARA CONCEPTO 500 NOMINA 1 ");
        }
    }

}
