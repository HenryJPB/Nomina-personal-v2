<%-- 
    Document   : jspReporteHTML
    Created on : 12/02/2020, 11:59:19 AM
    Author     : hpulgar
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.zkoss.zk.ui.Sessions"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%-- Importamos las Libreria --%>   
<%@page import="net.sf.jasperreports.engine.*" %>
<%@page import="java.util.*" %>
<%@page import="java.sql.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NOMINA</title>
    </head>
    <body>
        <h1>NOMINA</h1>
    </body>

    <%
        /* Parametros para realizar la conexion */
        Connection conexion;
        /*  Error -» no se corresponde con este driver '...mariadb..'  
         Class.forName("com.mariadb.jdbc.Driver").newInstance();
         conexion = DriverManager.getConnection("jdbc:mariadb://localhost/DESICA", "root", "root");
         */
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        /* Class.forName("com.mariadb.jdbc.Driver").newInstance();  */  
        /* conexion = DriverManager.getConnection("jdbc:mysql://localhost/DESICA", "root", "root"); */
        conexion = DriverManager.getConnection("jdbc:mysql://193.168.0.59:3306/DESICA", "root", "root");
        /* conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3306/DESICA", "root", "root"); */  

        /* Establecemos la ruta del reporte */
        /* Los siguientes ejemplos de reportes funcionaron correctamente en Bqto al 13 Abril 2018 */
        /* File reportFile = new File(application.getRealPath("/REPORTES//report1.jasper")); */
        /* File reportFile = new File(application.getRealPath("/REPORTES//MySqlReportJSP.jasper")); */
        /* File reportFile = new File(application.getRealPath("/REPORTES//reportMariaDB1.jasper")); */
        //File reportFile = new File(application.getRealPath("/REPORTES//reportMariaDB2.jasper"));
        File reportFile = new File(application.getRealPath("/REPORTES//Nomina.jasper"));

        /* No enviamos parametros porque nuetro reporte NO lo requiere */
        Map parametros = new HashMap();
        //parametros.put("nombreParametro", "valorParametro");
        parametros.put("p_empresa", "J-41309179-8");
        //System.out.println("EMPRESA(rif)="+paramEmpresa); 
        //System.out.println("Frec Nomina="+request.getParameter("frec_nomina")); 
        parametros.put("p_frec_calculo", "Q" );
        //System.out.println("fecha2="+request.getParameter("fecha2")); 
        //parametros.put("tipoMov",request.getParameter("tipo_mov") );
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");   
        parametros.put("p_fecha_nomina", dFormat.parse( "2020-09-18" ) ); 

        /* Enviamos el reporte ( ruta ), los parametrios y la conexion */
        byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros, conexion);

        /* Indicamos que la respuesta va a ser en PDF/HTML/EXCEL ??????? (SUJETO A REVISION  */
        /* response.setContentType("text/html"); Revisar ??? */
        //response.setContentType("application/pdf");
        response.setContentType("application/html");
        response.setContentLength(bytes.length);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes, 0, bytes.length);

        /* Limpiamos y cerramos los flujos de salida */
        outputStream.flush();
        outputStream.close();
    %>
</html>