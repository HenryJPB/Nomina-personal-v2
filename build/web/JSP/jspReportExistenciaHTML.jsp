<%-- 
    Document   : jspReportExistenciaPDF
    Created on : 11-nov-2019, 8:07:43
    Author     : henrypb
    ** SUJETO A REVSION ** 
--%>

<%@page import="java.io.FileInputStream"%>
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
        <title>Reporte Existencia</title>
    </head>
    <body>
    <center> <h3>EXISTENCIA DE ALMACEN DE SUPLIDOS Y REPUESTOS</h3> </center>
</body>
<%
    /* Parametros para realizar la conexion */
    Connection conexion;
    Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
    conexion = DriverManager.getConnection("jdbc:oracle:thin:@193.168.0.59:1521:DES112", "OPS$DESSUP03", "OPS$DESSUP03");

    /* Establecemos la ruta del reporte */
    /* Los siguientes ejemplos de reportes funcionaron correctamente en Bqto, 11/11/2019 */
    File reportFile = new File(application.getRealPath("/REPORTES//Existencia.jasper"));

    /* No enviamos parametros porque nuetro reporte NO lo requiere */
    Map parametros = new HashMap();
    parametros.put("nombreParametro", "valorParametro");

    /* Enviamos el reporte ( ruta ), los parametrios y la conexion */
    byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parametros, conexion);
    /*
    String reportPath =JasperRunManager.runReportToHtmlFile(reportFile.getPath(), parametros,conexion);
    File reportHtmlFile = new File(reportPath);
    FileInputStream fis = new FileInputStream(reportHtmlFile);
    byte[] bytes =  new byte[(int)reportHtmlFile.length()];
    fis.read(bytes);
    response.setHeader("Content-Disposition","inline; filename=Existencia.html");  
    */

    /* Indicamos que la respuesta va a ser en PDF */
     response.setContentType("application/html");
     //response.setContentType("text/html");     // Una loquera. 
     response.setContentLength(bytes.length);
     ServletOutputStream outputStream = response.getOutputStream();
     outputStream.write(bytes, 0, bytes.length);
    
    // SUJETO A REVISION:  
    /* Indicamos que la respuesta va a ser en HTML */
    /*
    response.setContentType("text/html");
    response.setContentLength(bytes.length);
    ServletOutputStream outputStream = response.getOutputStream();
    outputStream.write(bytes, 0, bytes.length);
    */
     
    /* Limpiamos y cerramos los flujos de salida */
    outputStream.flush();
    outputStream.close();
%>  
</html>
