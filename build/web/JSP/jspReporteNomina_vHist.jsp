<%-- 
    Document   : jspReportNomina
    Created on : 20 ago. 2020, 1:33:52 p. m.
    Author     : hpulgar
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.FileInputStream"%>
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
        <title>NOMINA</title>
    </head>
    <body>
    <center> <h3>CALCULO DE NOMINA</h3> </center>
    </body>
    <%
        /* Parametros para realizar la conexion */
        Connection conexion;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conexion = DriverManager.getConnection("jdbc:mysql://193.168.0.59:3306/DESICA","root","root");
        
        /* Establecemos la ruta del reporte */
        /* Los siguientes ejemplos de reportes funcionaron correctamente en Bqto al 13 Abril 2018 */
        /* File reportFile = new File(application.getRealPath("/REPORTES//report1.jasper")); */
        //
        File reportFile = new File(application.getRealPath("/REPORTES//Nomina.jasper"));
        //JasperReport jasperReport = JasperCompileManager.compileReport(request.getSession().getServletContext().getRealPath("/REPORTES//Nomina.jrxml"));
        // 
        /* No enviamos parametros porque nuetro reporte NO lo requiere */
        Map parametros = new HashMap();
        //parametros.put("nombreParametro", "valorParametro");
        String paramEmpresa = request.getParameter("empresa");  
        paramEmpresa = paramEmpresa.substring(0, paramEmpresa.indexOf(" ")); 
        //parametros.put("p_empresa", "J-41309179-8");
        parametros.put("p_empresa", paramEmpresa );
        //System.out.println("EMPRESA(rif)="+paramEmpresa+"*****"); 
        //System.out.println("Frec calculo="+request.getParameter("frec_calculo")+"*****"); 
        //parametros.put("p_frec_calculo", request.getParameter("frec_calculo") );
        parametros.put("p_frec_calculo", request.getParameter("frec_calculo") );
        //System.out.println("fecha2="+request.getParameter("fecha2")); 
        //parametros.put("tipoMov",request.getParameter("tipo_mov") );
        // 
        String fechaNominaS = request.getParameter("fecha_nomina");  
        //System.out.println("fechaNomina="+fechaNominaS); 
        if ( fechaNominaS!=null && !fechaNominaS.isEmpty() ) {
            SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
            parametros.put("p_fecha_nomina", dFormat.parse( fechaNominaS ) ); 
        } else {
            parametros.put("p_fecha_nomina", ( new java.util.Date() ) ); 
        }  // if-else 
        //
        //parametros.put("p_fecha_nomina", dFormat.parse( request.getParameter("fecha_nomina") )); 
        //parametros.put("fecha2", request.getParameter("fecha2"));   // Error!!!!!!!
        //parametros.put("p_fecha_nomina", dFormat.parse("2020-09-16"));
        //parametros.put("fecha2", dFormat.parse(request.getParameter("fecha2") ));
        //System.out.println("Fecha Nomina="+dFormat.parse( request.getParameter("fecha_nomina") )+"****" );
        //
        //SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");   
        //parametros.put("p_fecha_nomina", dFormat.parse( "2020-09-18" ) ); 
        //
        /* Enviamos el reporte ( ruta ), los parametrios y la conexion */
        byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros, conexion);
        //byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, parametros, conexion);
        //
        /* Indicamos que la respuesta va a ser en PDF */
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes, 0, bytes.length);

        /* Limpiamos y cerramos los flujos de salida */
        outputStream.flush();
        outputStream.close();
    %>  
</html>