/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador.personal;

import bean.controlador.configGeneral.NomConfigDatJpaController;
import bean.modelo.entidad.NomConfigDat;
import bean.utilitario.libreria.LibreriaHP;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author henrypb
 */
public class ControladorDatosPersonales extends GenericForwardComposer {

    private String ruta = null;
    private Image imgFoto;
    private Button btnIni;
    private Button btnReg;
    private Button btnPreViewFoto;
    private Bandbox bbxFicheros;
    private Listbox lbxFicheros;
    Integer nroTrabSeleccionado = 0;
    //
    String ssnRifEmpresa = (String) org.zkoss.zk.ui.Sessions.getCurrent().getAttribute("rifEmpresa");

    //--------------------------------------------------------------------------
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        // Instrucciones del programador: 
        /*
         if (validarUsuario()) {
         iniciar();
         }
         */
        iniciar();
        /* -----
         LocalDate hoy = LocalDate.now();  
         java.util.Date hoy = new java.util.Date(); 
         LocalDate lHoy = hoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();  
         //long edad = ChronoUnit.YEARS.between(hoy,hoy);
         System.out.println("****LocalDate hoy="+lHoy+"****"); 
         int edad = (int) ChronoUnit.YEARS.between(lHoy, lHoy); 
         lblEdad.setValue(Long.toString(edad));
         */
        /* --------
        List<bean.modelo.entidad.Trabajador> trabajadores = new bean.servicio.ServicioAdmonTrabajador().getListaTrab(ssnRifEmpresa); 
        List<String> ls =  new bean.servicio.ServicioAdmonTrabajador().getListaTrabS(ssnRifEmpresa); 
        // String[] a = trabajadores.toArray(new String[trabajadores.size()]);   // ERRORRO!!!!!!
        //String[] a = new String[200]; 
        System.out.println("==============="); 
        System.out.println("IMPRIMIR LISTA:");     
        for (bean.modelo.entidad.Trabajador trab : trabajadores) {
            System.out.println( trab.getNroTrabajador()+" "+trab.getNombre()+" "+trab.getApellido() );
        }
        System.out.println("==============="); 
        System.out.println("IMPRIMIR LISTA-ARRAY:");     
        for (String trabS : ls ) {
            System.out.println( trabS );
        }
        // doc: https://www.techiedelight.com/convert-list-to-array-java/
        String[] a = ls.toArray(new String[ls.size()]);   // 
        System.out.println("==============="); 
        System.out.println("IMPRIMIR ARRAY:");     
        // System.out.println(a);   // did NOT work. 
        for (int i=0; i<20; i++ ) {
            System.err.println("a[i]="+a[i]+"ficha="+a[i].substring(0,a[i].indexOf(" ")));
        }
        ---- */
    }  // doAfterCompose().

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    private void iniciar() {
        nroTrabSeleccionado = (Integer) Sessions.getCurrent().getAttribute("nroTrabSeleccionado");
        //--------------------------------------------------------------------------
        // Interactive between multiple ZUL pages ( Bqto, 21/08/2018 ) no reconoce 
        // el evento. Mi ejemplo "onEventGetNroTrab" ????? --> Trabajaremos con variables
        // de session. ( Sujeto a Revision ). 
        // REMENBER: en teoria EventQueues.lookup funciona entre controladores ????
        // *Error: NO se activa la escucha del Evento....??????.. puede q sea Java Version?? EE 6 Web..? o 1.4..? => Sujeto a revision..?? 
        //--------------------------------------------------------------------------
        //    System.out.println("Chequear EventQueue**");
        //    EventQueues.lookup("onEventGetNroTrab", EventQueues.DESKTOP, true).subscribe(new EventListener() {
        //        @Override
        //        public void onEvent(Event evento) throws Exception {
        //            System.out.println("DENTRO*****de Chequear EventQueue**");
        //            if (evento.getData() != null) {
        //                nroTrabSeleccionado = (Integer) evento.getData();
        //                System.out.println("Ficha=" + nroTrabSeleccionado + "**");
        //            } else {
        //                System.out.println("EROR en EventQueue nroTrabSeleccionado");  // Revisar
        //            }
        //        }
        //    });  // EventQueue(). 
        //    System.out.println("Event Queue chequeado??????????????.Ficha="+nroTrabSeleccionado+"**");
        if (nroTrabSeleccionado != null && nroTrabSeleccionado > 0) {
            // imgFoto.setSrc(RUTA + nroTrabSeleccionado + ".jpg");   // old diseño 
            //NomTrabajador00Dat nomTrabajador00Dat = new bean.controlador.personal.NomTrabajador00DatJpaController().findNomTrabajador00Dat(nroTrabSeleccionado);
            //BufferedImage bImagen = new LibreriaHP().getImagen(nomTrabajador00Dat.getFoto());
            BufferedImage bImagen = (BufferedImage) Sessions.getCurrent().getAttribute("imagenFoto");  // atributo set en el controlador "ControladorPersonal"
            if (bImagen != null) {
                imgFoto.setContent(bImagen);
            }
            /*                                                                  //****************************************************************************************
             java.util.Date hoy = new java.util.Date();                          // **NOTA IMPORTANTE** (Bqto: 31/08/2018 13:07 ) 
             java.util.Date utilFecha = dbxFechaNac.getValue();                  // A este punto y en este controlador 
             if (utilFecha!=null) {                                              // El metodo desconoce el valor del widget Datebox, que tal????????????
             java.sql.Date fecha = new java.sql.Date(utilFecha.getTime());   // CON: aquellos atributos cuyo value son 'bind(vm)' seran nulos para este controlador.  
             } else {                                                            //****************************************************************************************
             System.out.println("***utilFecha es NULO??????????????"); 
             }
             System.out.println("***+Hoy es="+hoy+"**fecha Nac=aNac.getText()"); 
             System.out.println("***Nombre del worker="+txtNombre1.getText()+"***"); // Prueba: observacion corroborada ;(
             */
        }
        NomConfigDat nomConfigDat = (new NomConfigDatJpaController()).findNomConfigDat(ssnRifEmpresa);
        ruta = nomConfigDat.getRuta1();
        btnPreViewFoto.setTooltiptext("Actualizar foto de tu Sistema de archivos: " + ruta + "");
        //String[] ficheros=( new bean.utilitario.libreria.LibreriaHP().leerFicheros(ruta));   // Correcto. 
    }  // iniciar(). 

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnReg() {
        String pagActiva = "../VISTA_PERSONAL/pagPersonal.zul";
        Sessions.getCurrent().setAttribute("pagInclude", pagActiva);
        Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnIni() {
        Sessions.getCurrent().setAttribute("pagInclude", "inicio");
        Executions.sendRedirect("../VISTA_PRINCIPAL/pagPrincipal.zul");
    }

    //--------------------------------------------------------------------------------
    // https://www.programcreek.com/java-api-examples/?api=org.zkoss.util.media.Media
    //--------------------------------------------------------------------------------
    @Listen("onUpload = #btnUpload")
    public void btnUpload_onUpload(UploadEvent evnt) throws IOException {
        Media media = evnt.getMedia();
        if (media != null) {
            System.out.println("En btnUpload_onUpload media NOT NULL (correcto)");
            System.out.println("btnUpload_onUpload. tipo file" + media.getContentType());
            System.out.println("btnUpload_onUpload. nombre" + media.getName());
            java.io.File foto = new java.io.File(media.getName());
            System.out.println("btnUpload_onUpload. get path=" + foto.getPath());
            System.out.println("------------------------------------------------");
            /*
             if (media.isBinary()) {
             data = IOUtils.toByteArray(media.getStreamData());
             } else {
             data = media.getStringData().getBytes(Charset.forName("UTF-8"));
             }
             */
        } else { // if (media!=null) 
            System.out.println("En btnUpload_onUpload media NULL:");
        }
    }  // btnUpload_onUpload(UploadEvent evnt) 

    //------------------------------------------------------------------------------------
    // Correcto: METODO II. (Correcto)
    // https://es.stackoverflow.com/questions/52012/copiar-archivos-de-una-carpeta-a-otra
    // ATENCION: Este metodo utiliza la funcion <archivo>.getFullPath de las librerias
    //           java.io.File, pero no ejecuta correctamente y solo desplega la ruta 
    //           de ejecucion del web server. En este caso, utilizando un web server apache:
    //           /usr/local/apache-tomcat-8.0.15/bin/..................??? ( sujeto a revision )
    // SOLUCION: se van a colocar las rutas fijas en valores constantes dentro del metodo. 
    //------------------------------------------------------------------------------------
    public void onUpload$btnActFoto(UploadEvent event) throws IOException {
        final String RUTA_ORIGEN = "/home/henrypb/Imagenes/";
        final String RUTA_DESTINO = "/home/henrypb/Imagenes/Personal/";  // En tiempo de ejecucion ..! (?)
        Media media = event.getMedia();
        System.out.println("IN onUpload$btnActFoto*********************************");
        if (media != null) {
            //System.out.println("En btnUpload_onUpload media NOT NULL (correcto)");
            //System.out.println(media.getContentType());  // tipo archivo.  
            //System.out.println("Nombre del archivo: " + media.getName());
            java.io.File foto = new java.io.File(media.getName());
            Messagebox.show("full path?:" + foto.getAbsolutePath());
            //System.out.println("Full path (getAbsolutePath())? :" + foto.getAbsolutePath());
            //System.out.println("path (getCanonicalPath())?:" + foto.getCanonicalPath());
            //System.out.println("path (getPath())?:" + foto.getPath());
            // if (media.isBinary()) {
            // data = IOUtils.toByteArray(media.getStreamData());
            // } else {
            // data = media.getStringData().getBytes(Charset.forName("UTF-8"));
            // }
            //java.io.File origen = new java.io.File(foto.getName());  // ???
            java.io.File origen = new java.io.File(RUTA_ORIGEN + foto.getName());
            String nombreFotoDestino = nroTrabSeleccionado + ".jpg";
            java.io.File destino = new java.io.File(RUTA_DESTINO + nombreFotoDestino);
            if (origen.exists()) {
                System.out.println("** En proceso copia foto a la carpeta destino,.........");
                Files.copy(Paths.get(origen.getAbsolutePath()), Paths.get(destino.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
                imgFoto.setSrc(RUTA_DESTINO + nombreFotoDestino);
                System.out.println("RUTA:" + RUTA_DESTINO + nombreFotoDestino + "*******");
            } else {
                System.err.println("*****El fichero " + origen.getName() + " no existe en el directorio " + foto.getAbsolutePath() + "****");
                Messagebox.show("ATENCION: El fichero " + origen.getName() + " no existe en el directorio :-( ", "ERROR", Messagebox.OK, Messagebox.ERROR);
            }
        }
        System.out.println("OUT onUpload$btnActFoto*********************************");
    }  //onUpload$btnActFoto(UploadEvent event) 

    //--------------------------------------------------------------------------
    // ??????????????????????
    //--------------------------------------------------------------------------
    public void onChange$bbxFicheros() {
        System.out.println("**bbxFicheros****Paso algo****Valor=" + bbxFicheros.getText());  // Nothig happen....????
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onSelect$lbxFicheros() {
        //System.out.println("**lbxFicheros****Paso algo***"+ruta+"/"+bbxFicheros.getText()+"**bbxText="+bbxFicheros.getText());  // Yes. 
        //imgFoto.setSrc(ruta+"/"+bbxFicheros.getText());
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    public void onClick$btnPreViewFoto() throws IOException {
        String carpeta = ruta + "/" + bbxFicheros.getText();
        if (bbxFicheros.getText() != null && !bbxFicheros.getText().isEmpty()) {
            BufferedImage bImagen = new LibreriaHP().getImagen(carpeta);
            if (bImagen != null) {
                imgFoto.setContent(bImagen);
                Sessions.getCurrent().setAttribute("imagenFoto", bImagen);
            }
        }  // if externo. 
    }  // onClick$btnActFoto().  

    //--------------------------------------------------------------------------
    // Este metodo esta publicado en la Libreria: LibreriaHP. 
    //--------------------------------------------------------------------------
    public byte[] getBytesFoto() throws IOException {
        byte[] bytes = null;
        BufferedImage bImagen = (BufferedImage) imgFoto.getContent();
        if (bImagen != null) {
            bytes = new LibreriaHP().convertirImagen(bImagen);
        }
        return (bytes);
    }  // getBytesFoto()

}
