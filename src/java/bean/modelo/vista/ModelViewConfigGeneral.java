/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.modelo.vista;

import bean.modelo.entidad.NomConfigDat;
import bean.controlador.configGeneral.ControladorConfigGeneral;
import bean.controlador.configGeneral.NomConfigDatJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author henrypb
 */
public class ModelViewConfigGeneral {

    //private final String RIF = "J-08503850-7";
    String rifEmpresa = (String) Sessions.getCurrent().getAttribute("rifEmpresa");  

    NomConfigDat nomConfigDat = new NomConfigDatJpaController().findNomConfigDat(rifEmpresa);

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Init
    public void init() {    // Initialize
        //selected = currentTrabajadores.get(0); // Selected First One
    }

    //--------------------------------------------------------------------------
    // check this out ( pagConfigGeneral.zul ). 
    // <.. model="@load(vm.nomConfigViewModel)" ...>
    //--------------------------------------------------------------------------
    public NomConfigDat getNomConfigViewModel() {
        return (nomConfigDat);
    } // getNomConfigViewModel()

    //--------------------------------------------------------------------------------
    // https://www.programcreek.com/java-api-examples/?api=org.zkoss.util.media.Media
    // http://zkfiddle.org/sample/2padq0u/1-Binding-notifyChange-behaviour-changed#source-2  (*) 
    // NO funciono....???????? ( Bqto: 09 agosto 2018 09:34 ????????????????????
    //--------------------------------------------------------------------------------
    @Listen("onClick = #btnEdit")
    //public void btnEdit_onClick(Event event) {
    public void onEditClicked(Event event) {
        System.out.println("*****Aquica: Exitoo111111!!!!!************");
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private Boolean datosConforme() {
        Boolean ok = Boolean.TRUE;
        // ..
        // << validar datos aqui. Si es ok preparar datos para la actualizacion >> 
        // ...
        if (ok) {
            String carpeta = new ControladorConfigGeneral().formatearCarpeta(nomConfigDat.getRuta1());
            //nomConfigDat = new NomConfigDat(rifEmpresa, carpeta);   // nomConfigDat -> ambito global 
            nomConfigDat.setRuta1(carpeta);
            carpeta = new ControladorConfigGeneral().formatearCarpeta(nomConfigDat.getRuta2());
            nomConfigDat.setRuta2(carpeta);
        }
        return (ok);
    }  // datosConforme()

    //--------------------------------------------------------------------------
    // http://forum.zkoss.org/question/91835/notify-form-of-property-change/
    // Condicion NO necesaria: Los nombres pueden/o no coincidir 
    // Se ejecuto correctamente ( Bqto: 09/08/2018 09:36.  
    //--------------------------------------------------------------------------
    //@NotifyChange({"forma"})
    @Command("btnEditClicked")
    //public void btnEditClicked(@BindingParam("config") SimpleForm fx) {
    public void btnEditClicked() {
        Messagebox.show("CONFORME ?", "CONFIRMACION", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            @Override
            public void onEvent(Event evento) throws Exception {
                if (evento.getName().equals("onYes") && datosConforme()) {
                    try {
                        //System.out.println("***Antes de actualizar="+nomConfigDat.getRutaAbsFoto()+"*******");
                        new NomConfigDatJpaController().edit(nomConfigDat); // Actualizar reg BD.
                        //
                    } catch (Exception ex) {
                        Logger.getLogger(ModelViewConfigGeneral.class.getName()).log(Level.SEVERE, null, ex);
                    }  // try
                }  // if
            }  // onEvent()  
        }); // MessageBox()
    }  // btnEditClicked().  

    //--------------------------------------------------------------------------
    // **************************PRUEBA************************    
    //                 ????? ==Sujeto a revision==  ??????
    // Check out below link:  
    // http://forum.zkoss.org/question/91835/notify-form-of-property-change/
    //--------------------------------------------------------------------------
    @Command("btnEditClickedPRUEBA")
    //public void btnEditClicked(@BindingParam("config") SimpleForm fx) {
    //public void btnEditClickedPRUEBA(@BindingParam("nomConfigDat") SimpleForm fx) {
    public void btnEditClickedPRUEBA(@BindingParam("nomConfigDat") NomConfigDat fx) {
    //public void btnEditClicked(@BindingParam("fx") NomConfigDat fx) {
        //System.out.println("1.btnEditClicked, fx.rutaAbsFoto="+fx .getField("rutaAbsFoto")+"****");
        System.out.println("1.btnEditClicked, fx.rutaAbsFoto="+fx.getRuta1()+"****");
        //fx.setField("rutaAbsFoto","mi ruta");   // No altere la variable en la vista .zul ????????????
        //nomConfigDat.setRutaAbsFoto("mi ruta"); // either ??????
        fx.setCorreo1("mi ruta");    // Error .............
        //BindUtils.postNotifyChange(null, null,this,"forma");// here notify 
        BindUtils.postNotifyChange(null, null, fx, "*");
        //System.out.println("fx.rutaAbsFoto="+fx.getField("rutaAbsFoto")+"****");
        System.out.println("2.btnEditClicked,fx.rutaAbsFoto="+fx.getRuta1()+"***");
    }  // btnEditClicked().  // ???????????????????????????????????????????????

    //--------------------------------------------------------------------------
    // Bqto: 13 agosto 2018 12:03. Este ejemplo fue utilizado para desarrollar el 
    // uso de "Command binding that pass parametro." ==> funcionó correctamente. 
    // Doc: http://books.zkoss.org/zk-mvvm-book/8.0/syntax/bindingparam.html
    //--------------------------------------------------------------------------
    @Command 
    public void setSelSexo(@BindingParam("valorParametro") String target, @BindingParam("content") String content) {
        System.out.println("Esta funcionando?????---->Yesssss");     
        System.out.println("**** S.Target (valorParametro)="+target+"****");  
        System.out.println("**** S.Target (content)????="+content+"****");
    }  // setSelSexo()
    
    public NomConfigDat getNomConfigDat() {
        return nomConfigDat;
    }

    public void setNomConfigDat(NomConfigDat nomConfigDat) {
        this.nomConfigDat = nomConfigDat;
    }

}
