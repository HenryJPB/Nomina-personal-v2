/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.concepto;

import bean.modelo.entidad.NomFormulaDat;
import bean.utilitario.libreria.ClassArregloTokens;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

/**
 *
 * @author hpulgar
 */
public class ControladorDefFormulaEdit extends GenericForwardComposer {

    final String CONDICION = "SI";
    String ssnRifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");
    private AnnotateDataBinder binder;     // deprecated en ZK para las ver 6.i y superiores... 
    private Textbox txtCodFormulaPadre;    // Bindered
    private Textbox txtAccion;             // Bindered 
    private NomFormulaDat nomFormulaDat;   // Bindered
    //
    private Window winDefFormulaEdit;      // NOT bindered
    private Listbox lbxFormula;            // NOT bindered ** pasado por parametro!.  
    private Button btnEdit;                // NOT bindered
    private Menuitem mnnAccionOpcion1, mnnAccionOpcion2, mnnAccionOpcion3;  // NOT bindered
    private Boolean registroNuevo;         // Atributo de ambito Global.  
    final int PRECISION = 100;             // formula / observacion. 
    private Textbox txtFormula;
    private Label lblAcumulador;
    private Vbox vbxDefFormula;
    private Image imgRefresh;

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // << intrucciones del usuario >>
        //certificarUsuario(); 
        //checkEventQueves();  
        binder = new AnnotateDataBinder(comp);
        binder.bindBean("atributoControlador", this);
        //
        nomFormulaDat = (NomFormulaDat) arg.get("NOMFORMULA_DAT");
        registroNuevo = (Boolean) arg.get("REGISTRO_NUEVO");
        //vbxDefFormula = (Vbox) arg.get("VBOX");  
        lbxFormula = (Listbox) arg.get("LISTBOX");
        //System.out.println("CodFormula="+nomFormulaDat.getNomFormulaDatPK().getCodFormula()+"****");   // Correcto  ✔✔ 
        //System.err.println("Tamano de lbxFormula (items count)="+lbxFormula.getItemCount()+"***");
        //
        imgRefresh = (Image) arg.get("COMP_PADRE");
        iniciar();
        //pruebaBinder();  
    }

    //--------------------------------------------------------------------------
    // ** Importantisimo el siguiente metodo. De no implementarse no se rellenaran 
    //    los valores de los campos bindered !!!. 
    //--------------------------------------------------------------------------
    public void onCreate() {
        binder.loadAll();
    }

    //--------------------------------------------------------------------------
    public void onChange$txtFormula() {
        validarFormula();
    }  // onChange$txtFormula(). 

    //--------------------------------------------------------------------------
    private void iniciar() {
        if (nomFormulaDat == null) {
            System.err.println("(NEGATIVO) NO procede nomFormulaDat es NULO >>> Requiere Refrescamiento!");
        } else {
            Integer codNomina = nomFormulaDat.getNomFormulaDatPK().getCodNomina();
            Integer codConcepto = nomFormulaDat.getNomFormulaDatPK().getCodConcepto();
            String codFormula = nomFormulaDat.getNomFormulaDatPK().getCodFormula();
            //    
            Integer cuantos = new bean.controlador.concepto.NomFormulaDatJpaController().getCorrelativo(ssnRifEmpresa, codNomina, codConcepto, codFormula);
            //System.out.println("Cuantos="+cuantos+"****");
            if (registroNuevo) {
                nomFormulaDat.getNomFormulaDatPK().setCorrelativo(cuantos + 1);
            }
            if ((nomFormulaDat.getNomFormulaDatPK().getAccion() == null) || nomFormulaDat.getNomFormulaDatPK().getAccion().trim().isEmpty()) {
                lblAcumulador.setValue("");
            }
            if (nomFormulaDat.getNomFormulaDatPK().getAccion().equals("T")) {
                lblAcumulador.setValue("T");
            } else {
                lblAcumulador.setValue(nomFormulaDat.getNomFormulaDatPK().getAccion() + nomFormulaDat.getNomFormulaDatPK().getCorrelativo());
            }
        }
        //System.out.println("lblAcum="+nomFormulaDat.getNomFormulaDatPK().getAccion()+nomFormulaDat.getNomFormulaDatPK().getCorrelativo());
        //Messagebox.show("ELEMENTOS DEL listbox="+lbxFormula.getModel().getSize());  
        //System.out.println("Vbox, align?="+vbxDefFormula.getAlign());
        refrescar();   
    }  // iniciar(). 

    //--------------------------------------------------------------------------
    // *NOTA IMPORTANTE* (Bqto, 05  marzo 2021 )
    // Para refrescar el Listbox ( lbxFormula ) se implantó el siguiente metodo
    // que consiste en simular al usuario haciendo 'click' en el boton 'Refrescar'
    // del panel de la pagina. Cualquier otro metodo fue probado con Salidas Inde-
    // seables. 
    //--------------------------------------------------------------------------
    private void refrescar() {
        if (imgRefresh == null) {
            Messagebox.show("'ControladorDefFormulaEdit' dice: widget 'imgProbar' es *NULO* ????t>t>t>REVISAR!!");
        } else {
            //Messagebox.show("'ControladorDefFormulaEdit' dice: widget 'imgProbar' es *NO* *NULO* ????");
            Events.sendEvent("onClick", imgRefresh, null);
        }
    } // refrescar()

    //--------------------------------------------------------------------------
    public void onClick$mnnAccionOpcion1() {
        if (registroNuevo) {
            nomFormulaDat.getNomFormulaDatPK().setAccion("R");
            //binder.loadAll();                 // Refrescar valor de todos los componentes (conveniente pero NO para todos los casos) 
            lblAcumulador.setValue("R" + nomFormulaDat.getNomFormulaDatPK().getCorrelativo());
            binder.loadComponent(txtAccion);    // Correcto  
        }
    }

    //--------------------------------------------------------------------------
    public void onClick$mnnAccionOpcion2() {
        if (registroNuevo) {
            nomFormulaDat.getNomFormulaDatPK().setAccion("C");
            //binder.loadAll();
            lblAcumulador.setValue("C" + nomFormulaDat.getNomFormulaDatPK().getCorrelativo());
            binder.loadComponent(txtAccion);    // Correcto  
        }
    }

    //--------------------------------------------------------------------------
    public void onClick$mnnAccionOpcion3() {
        if (registroNuevo) {
            nomFormulaDat.getNomFormulaDatPK().setAccion("T");
            //binder.loadAll();
            lblAcumulador.setValue("T");
            binder.loadComponent(txtAccion);    // Correcto  
        }
    }

    //--------------------------------------------------------------------------
    public void onClick$btnEdit() {
        actualizar();
    }

    //--------------------------------------------------------------------------
    // prueba ejecutada correctamente, Bqto, 04/12/2020 09:43. Autor: HJPB.  
    // REMIND: para ejecutar esta prueba el Controlador que me invoca debe estar 
    // creada y implantada las siguientes instrucciones:
    //      binder = new AnnotateDataBinder(comp);
    //      binder.bindBean("atributoControlador", this);
    //--------------------------------------------------------------------------
    private void pruebaBinder() {
        txtCodFormulaPadre = (Textbox) arg.get("COD_FORMULA");
        System.err.println("Cod_Formula=" + txtCodFormulaPadre.getText() + "***");
        //
        txtCodFormulaPadre.setText("*HENRY*");   // (1er intento) NO funcionó ?????    ..  (2do intento) Prueba utilizando 'binder'  
        binder.saveAll();  // 2do intento (utilizando binder) funcionó correctisimo ✔✔✔✔
    }  // pruebaBinder(). 

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
    private void validarFormula() {
        bean.controlador.concepto.ControladorDefFormula ctldrFormula = new bean.controlador.concepto.ControladorDefFormula();
        String formula = txtFormula.getText().trim();
        if (formula != null && !formula.isEmpty()) {
            // if ( formula.substring(0, 2).toString().toUpperCase().equals(CONDICION) ) {
            if (condicion(formula)) {
                if (!ctldrFormula.validarCondicion(formula)) {    // validar INSTRUCCION Condicional 'SI'
                    Messagebox.show("CONDICIONA INVALIDA.", "ATENCION", Messagebox.OK, Messagebox.ERROR);
                }
            } else {
                formula = "[ " + formula + " ]";   // formatear ecuacion...! - Los blanks espaces son de caracter necesarios. 
                ClassArregloTokens tokens = ctldrFormula.dividirTokens(formula);
                if (!ctldrFormula.tokenValidos(tokens)) {
                    Messagebox.show("TERMINOS NO DEFINIDOS O ERROR DE SINTAXIS EN FORMULA/ECUACION.", "ATENCION", Messagebox.OK, Messagebox.ERROR);
                } else {
                    tokens = ctldrFormula.validarEcuacion(tokens);
                    if (tokens == null) {
                        //System.err.println("ATENCION: ERROR DE SINTAXIS EN LA DEFINCION DE LA FORMULA!!!!!!!");
                        Messagebox.show("ERROR DE SINTAXIS EN LA DEFINCION DE LA FORMULA/ECUACION.", "ATENCION", Messagebox.OK, Messagebox.ERROR);
                    }  // if ( tokens == null)
                }  // if (formula != null && !formula.isEmpty()
            }  // if-else if ( formula.subSequence(0,2).toString().toUpperCase().equals(CONDICION)  )
        } // if (formula != null && !formula.isEmpty())
    } // valudarFormula().  

    //--------------------------------------------------------------------------
    private String procesarTexto(String cadena, int precision) {
        String c = "";
        if (cadena != null) {
            c = (cadena.length() <= precision ? cadena : cadena.substring(0, precision));
            c = c.toUpperCase();
        }
        return (c);
    } // procesarTexto().

    //--------------------------------------------------------------------------
    private void ajustar() {
        //
        String cadena = nomFormulaDat.getFormula();
        cadena = procesarTexto(cadena, PRECISION);
        nomFormulaDat.setFormula(cadena.toUpperCase());
        //
        cadena = nomFormulaDat.getObservacion();
        cadena = procesarTexto(cadena, PRECISION);
        nomFormulaDat.setObservacion(cadena.toUpperCase());
    } // ajustarDatos().  

    //--------------------------------------------------------------------------
    private Boolean datosValidos() {
        //System.err.println("*Accion="+nomFormulaDat.getNomFormulaDatPK().getAccion().trim()+"***Correlativo="+nomFormulaDat.getNomFormulaDatPK().getCorrelativo()+"***Formula="+nomFormulaDat.getFormula()+"***");
        Boolean ok = Boolean.FALSE;
        String accion = nomFormulaDat.getNomFormulaDatPK().getAccion().trim();
        if (accion != null && !accion.isEmpty()) {
            ok = (nomFormulaDat.getNomFormulaDatPK().getCorrelativo() >= 0);
            ok = ok && (nomFormulaDat.getFormula() != null && !nomFormulaDat.getFormula().isEmpty());
        }  // if.   
        return (ok);
    }  // datosValidos()

    //--------------------------------------------------------------------------
    private void actualizar() {
        Messagebox.show("CONFORME ?", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event evento) throws Exception {
                //if (evento.getName().equals("onYes") && datosConforme()) {
                if (evento.getName().equals("onYes")) {
                    try {
                        if (!datosValidos()) {
                            Messagebox.show("ATENCION: CAMPOS CLAVES CON VALORES NULO(S) O VACIO(S). Chequear!.", "ATENCION", Messagebox.OK, Messagebox.ERROR);
                        } else {
                            ajustar();
                            //System.out.println("****Tipo Gestion="+winDefFormulaEdit.getTitle()+"*****");
                            /* --- */
                            if (registroNuevo) {
                                new bean.controlador.concepto.NomFormulaDatJpaController().create(nomFormulaDat);
                            } else {
                                new bean.controlador.concepto.NomFormulaDatJpaController().edit(nomFormulaDat);
                            }
                            //**refrescar Listbox*****
                            //refrescar();  // Metodo funciona con ERRORES, >>> SUJETO A REVISION  :?(  genera SALIDAS INDESEABLES ??? 
                            //new bean.modelo.vista.ModelViewFormula().btnRefresh();   // <<< Esto NO Funciona ???? 
                            /*
                             Component widget = hlyFormulacion.getFellow("btnRfr");
                             if ( widget == null ) {
                             Messagebox.show("'ControladorDefFormulaEdit' dice: widget 'btnRfr' es *NULO* ????");
                             } else {    
                             Events.sendEvent("onClick", widget, null);
                             }
                             */
                            refrescar();    // lbxFormula !!
                            /* ----  */
                            winDefFormulaEdit.detach();
                            // *Aqui deberia ejecutar un refrescamiento del Listbox.  
                            // 1er Intento:
                            // binder.loadComponent(lbxFormula);    // ?? NO funcionó
                            // binder.saveAll();                    // ?? NO funcionó
                            // Puede que no tenga efecto xq el Listbox 'lbxFormula' esta creado e implementado x un 'ModelView' : revisar ????  
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ControladorDefFormulaEdit.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  // if
            }  // onEvent()  
        }); // MessageBox()
    }  // agregar();      

    //--------------------------------------------------------------------------
    // Bqto: 18 diciembre 2020. 
    //  ** NOTA:  
    // (1). Este metodo esta funcionando con ERRORES!!!! -- Sujeto a REVISION!!
    //--------------------------------------------------------------------------
    private void refrescar0() {
        List<NomFormulaDat> lista = new bean.controlador.concepto.NomFormulaDatJpaController().getFormulas(ssnRifEmpresa, nomFormulaDat.getNomFormulaDatPK().getCodNomina(), nomFormulaDat.getNomFormulaDatPK().getCodConcepto(), nomFormulaDat.getNomFormulaDatPK().getCodFormula());
        lbxFormula.setModel(new ListModelList<NomFormulaDat>(lista));       // 
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
    } // refrescar0().

    //************************getters/setters***********************************
    public AnnotateDataBinder getBinder() {
        return binder;
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public Textbox getTxtCodFormulaPadre() {
        return txtCodFormulaPadre;
    }

    public void setTxtCodFormulaPadre(Textbox txtCodFormulaPadre) {
        this.txtCodFormulaPadre = txtCodFormulaPadre;
    }

    public Textbox getTxtAccion() {
        return txtAccion;
    }

    public void setTxtAccion(Textbox txtAccion) {
        this.txtAccion = txtAccion;
    }

    public NomFormulaDat getNomFormulaDat() {
        return nomFormulaDat;
    }

    public void setNomFormulaDat(NomFormulaDat nomFormulaDat) {
        this.nomFormulaDat = nomFormulaDat;
    }

}
