<%-- 
    Document   : jspReportNomina
    Created on : 20 ago. 2020, 1:33:52 p. m.
    Author     : hpulgar
--%>
<%@page import="net.sf.jasperreports.engine.export.JRPdfExporter"%>
<%@page import="net.sf.jasperreports.engine.util.JRLoader"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.FileInputStream"%>
<%--
<%@page import="bean.modelo.entidad.NomConfigDat.java"%>
--%>
<%-- Importamos las Libreria --%>  
<%@page import="java.io.File"%>
<%@page import="net.sf.jasperreports.engine.*" %>
<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RECIBOS</title>
    </head>
    <%
        /* Parametros para realizar la conexion */
        Connection conexion;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conexion = DriverManager.getConnection("jdbc:mysql://193.168.0.59:3306/DESICA", "root", "root");
        /* Establecemos la ruta del reporte */
        /* Los siguientes ejemplos de reportes funcionaron correctamente en Bqto al 13 Abril 2018 */
        /* File reportFile = new File(application.getRealPath("/REPORTES/report1.jasper")); */
        //
        //JasperReport reporte = (JasperReport) JRLoader.loadObject("/home/hpulgar/NetBeansProjects80/ZK/InformesNomina_y_Personal/web/REPORTES/RecibosNominaHist.jasper");   
        File reportFile = new File(application.getRealPath("/REPORTES/RecibosNominaHistV2.jasper"));
        //JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/REPORTES//Nomina.jrxml"));
        // 
        /* No enviamos parametros porque nuetro reporte NO lo requiere */
        //......................................................................
        Map parametros = new HashMap();
        //parametros.put("nombreParametro", "valorParametro");
        String paramRifEmpresa = request.getParameter("empresa");
        //parametros.put("p_empresa", "J-41309179-8");
        paramRifEmpresa = paramRifEmpresa.substring(0, paramRifEmpresa.indexOf(" "));
        parametros.put("p_empresa", paramRifEmpresa);
        //......................................................................
        String codNomina = request.getParameter("n_tipo_nomina");
        if (codNomina == null || codNomina.isEmpty()) {
            codNomina = "0";
        }
        parametros.put("p_tipo_nomina", Integer.parseInt(codNomina));
        //......................................................................
        String fechaNominaS = request.getParameter("fecha");
        SimpleDateFormat dFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        //String fechaNominaS = "2020-10-11";
        //System.out.println("fechaNomina="+fechaNominaS); 
        if (fechaNominaS != null && !fechaNominaS.isEmpty()) {
            //SimpleDateFormat dFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            parametros.put("p_fecha_nomina", dFormat2.parse(fechaNominaS));
        } else {
            parametros.put("p_fecha_nomina", (new java.util.Date()));
        }  // if-else 
        //......................................................................
        String trabajadorS1 = request.getParameter("n_trabajador1");
        if (trabajadorS1 == null || trabajadorS1.isEmpty()) {
            trabajadorS1 = "0";
        }
        //
        String trabajadorS2 = request.getParameter("n_trabajador2");
        if (trabajadorS2 == null || trabajadorS2.isEmpty()) {
            trabajadorS2 = "0";
        }
        final char EOL = '\n';
        bean.modelo.entidad.NomConfigDat n = new bean.controlador.configGeneral.NomConfigDatJpaController().findNomConfigDat(paramRifEmpresa);
        //System.out.println("*******Carpeta descarga="+n.getRuta2()+"********Correos destino="+n.getCorreo2()+"***");
        String rutaCarpeta = n.getRuta2(); 
        String remitente = ""; 
        String destinatario = n.getCorreo2();   // OJO :-) Solo para efectos de Prueba en las 1ras de Cambio ......
        for (Integer ficha = Integer.parseInt(trabajadorS1); ficha <= Integer.parseInt(trabajadorS2); ficha++) {
            bean.modelo.entidad.NomTrabajador00Dat r = new bean.controlador.personal.NomTrabajador00DatJpaController().getTrabajador(paramRifEmpresa, Integer.parseInt(codNomina), ficha);
            //
            //System.out.println("*****Correo del Trab="+r.getEmail1()+"****");  
            // **
            if (r != null) {  //  System.out.println("Ttrabajador Existe y es ACTIVO******");
                // *Generar informe a Disco:  
                parametros.put("p_trabajador1", ficha);
                parametros.put("p_trabajador2", ficha);
                JasperRunManager.runReportToPdfFile(reportFile.getPath(), rutaCarpeta+ "/" + ficha + ".pdf", parametros, conexion);
                // * Enviar correo:
                java.util.Date hoy = new java.util.Date();
                String asunto = "RECIBO DE NOMINA. Barquisimeto," + (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(hoy.getTime()));
                String textoCuerpo = "Estimado Usuario, reciba un cordial saludo." + EOL;
                textoCuerpo = textoCuerpo + EOL;
                textoCuerpo = textoCuerpo + "En documento adjunto le envío recibo de nomina correspondiente al " + dFormat1.format( dFormat2.parse(fechaNominaS) ) + "."+ EOL;
                textoCuerpo = textoCuerpo + EOL;
                textoCuerpo = textoCuerpo + "Atentamente," + EOL;
                textoCuerpo = textoCuerpo + EOL;
                textoCuerpo = textoCuerpo + "RECURSOS HUMANOS ARCELOR MITTAL" + EOL;
                textoCuerpo = textoCuerpo + EOL;
                textoCuerpo = textoCuerpo + EOL;
                //textoCuerpo = textoCuerpo + "gestion de envio de recibos de nomina automatizado x informática DESICA" + EOL;
                String adjunto = rutaCarpeta + "/" + ficha + ".pdf";
                String nombreAdjunto = ficha + ".pdf";
                // remitente = "desica.informatica@gmail.com"; 
                // destinatario = "hpulgar.desica@gmail.com";
                /*
                 System.out.print("*****Trab: "+ficha+"****"); 
                 if ( r!=null ) System.out.println("Existe y es ACTIVO******");
                 else System.out.print("No existe o NO ACTIVO"); 
                 */
                //destinatario = r.getEmail1();  
                new bean.servicio.EmailSenderService().sendMail(asunto, textoCuerpo, adjunto, nombreAdjunto, destinatario);
            }  // if ( r!=null ) System.out.println("Existe y es ACTIVO******");
        } // for
        //
        //hhbyte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros, conexion);
        //JasperRunManager.runReportToPdfFile("./Temp/archivo.pdf", parametros, conexion); 
        //byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, parametros, conexion);
        //
        /* Indicamos que la respuesta va a ser en HTML */
        //response.setContentType("application/html");
        /* Indicamos que la respuesta va a ser en PDF */
    %> 
    <body>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
    <center>
        <br/>
        <hr style="border: 2px solid green; border-radius: 5px;" width="65%"/>
        <h3 style="color: darkslategrey; font-weight: bold;"> RECIBOS DE NOMINA GENERADOS Y ENVIADOS EXITOSAMENTE</h3> 
        <hr style="border: 2px solid green; border-radius: 5px;" width="65%"/>
        <br/>
        <%-- 
        <a href="jspParamRecibosNomFiletoEmail.jsp"/> <input type="submit" value="r e t o r n a r"> </a>   
        --%>
        <br/>
    </center>
    <br/>
</body>
</html>