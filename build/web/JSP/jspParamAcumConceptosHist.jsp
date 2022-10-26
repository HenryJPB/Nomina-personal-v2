<%-- 
    Document   : jspParamNomina
    Created on : 17 septiembre 2020, 13:36:00  
    Author     : hpulgar
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%-- --%>
<!DOCTYPE html>
<%-- --%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="hojaEstilo.css">
        <title>PARAMETROS ACUM CONCEPTOS</title>
    </head>
    <%--
    <form action="proceso.jsp" method="post">
    --%>
    <body>
        <sql:setDataSource var = "mySQLCon" driver = "com.mysql.jdbc.Driver"
                           url = "jdbc:mysql://193.168.0.59/DESICA"
                           user = "root"  password = "root"/>

        <sql:query dataSource = "${mySQLCon}" var = "empresa">
            SELECT concat_ws(" ",rif,nombre) as rifEmpresa   
            FROM   NomEmpresa_Dat 
            ORDER  BY rif, nombre;
        </sql:query>  
        <sql:query dataSource = "${mySQLCon}" var = "tipo_nomina">
            SELECT concat_ws(" ",codNomina,nombreNomina) as tipoNomina   
            FROM   NomTiposNomina_Dat 
            ORDER  BY codNomina;
        </sql:query>  
        <table border="1" class="miTablaModelo1" >
            <thead>
                <tr>
            <hr style="color: #0056b2;" />
            <h1> PARAMETROS ACUM CONCEPTOS (HIST)</h1>  
            <hr style="color: #0056b2;" />
            <br/>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td> 
                <%--
                <form action="./jspReporteNomina.jsp" method="post">
                <form action="./jspReporteHTML.jsp" method="post">
                --%>
                <form action="./jspReporteAcumConceptosHist.jsp" method="post">
                    <%--
                    Nombre:
                    <input type="text" name="nombre" id="nombre" value="Eulalio Prueba"  >
                    <br/>
                    Apellido:
                    <input type="text" name="apellido" id="apellido" value="" >
                    <br/>
                    Lenguaje de mi Preferencia:  
                    <select name="lenguaje">
                        <option value="java">java
                        <option value="jsp" selected>jsp
                        <option value="php">php
                        <option value="C/C++">C/C++
                        <option value="C#">C#
                        <option value="Asp">Asp
                        <option value="AS3">AS3
                    </select>
                    --%>
                    <br/>
                    <br/>
                    <strong>
                        Empresa / Sucursal :
                    </strong>  
                    <select name="empresa" >
                        <c:forEach var="row" items="${empresa.rowsByIndex}">
                            <c:forEach var="column" items="${row}">
                                <option value="<c:out value="${column}" />"> <c:out value="${column}"/> </option>
                            </c:forEach>
                        </c:forEach>        
                    </select> 
                    <br/>
                    <br/>
                    <%--
                    <strong>
                        <label for="frec_calculo">Tipo de Nomina ( S)emanal / Q)uincenal</label>:
                    </strong> 
                    <select name="frec_calculo">
                        <option value="S">Semanal
                        <option value="Q" selected>Quincenal
                    </select>
                    
                    Me gusta el:
                    <br/>
                    <input type="Radio" name="preferencia" value= "Diseño"checked>Diseño
                    <br/>
                    <input type="Radio" name= "preferencia"value="Programacion">Programacion
                    <br/>
                    <input type="Radio" name= "preferencia"value="Modelado">Modelado
                    <br/>
                    <input type="Radio" name= "preferencia"value="Gerencia">Gerencia de proyectos
                    <br/>
                    --%>
                    <%--       
                    Ejemplo Fecha ...-> https://developer.mozilla.org/es/docs/Web/HTML/Elemento/input/date
                    <label for="start">Fecha:</label>
                    <input type="date" id="start" name="trip-start"
                       value="2018-07-22"
                       min="2018-01-01" max="2018-12-31">
                    //*
                    <label for="i_fecha1">Fecha inicial:</label>
                    <input type="date" id="i_fecha1" name="fecha1">
                    <br/>
                    <br/>
                    <label for="i_fecha2">Fecha final:</label>
                    <input type="date" id="i_fecha1" name="fecha2">
                    <br/>
                    <br/>
                    <%-- 
                    <p><input type="submit" value="Enviar" width="100" ></p> 
                    --%>
                    <%-- 
                         HJPB: didn't work :-(
                    <p><button type="submit" value="Enviar" width="600" button/></p> 
                    --%>
                    <strong>
                        Tipo de nomina (codigo) :
                    </strong>  
                    <%--  SUJETO A REVISION ... Salida Indeseable ???? 
                    <select name="tipo_nomina" >
                        <c:forEach var="row2" items="${tipo_nomina.rowsByIndex}">
                            <c:forEach var="column2" items="${row2}">
                                <option value="<c:out value="${column2}" />"> <c:out value="${column2}"/> </option>
                            </c:forEach>
                        </c:forEach>        
                    </select> 
                    --%>
                    <input type="number" name="n_tipo_nomina" placeholder="multiple of 1" min="0" max="9"  >
                    <%-- 
                    <strong>
                        <label for="i_fecha">Fecha nomina (dd/MM/aaaa ): </label>
                    </strong>
                    <input type="date" id="i_fecha" name="fecha_nomina">
                    <br/>
                    <br/>
                    --%>
                    <br/>
                    <br/>
                    <strong>
                        <label for="i_fecha">Fecha: </label>
                    </strong>
                    <input type="date" id="i_fecha" name="fecha">
                    <br/>
                    <br/>
                    <p><input type="submit" value="ENVIAR" class="miBoton" ></p> 
                </form>  
            </td>
        </tr>
    </tbody>
</table> 
</body>
</html>
