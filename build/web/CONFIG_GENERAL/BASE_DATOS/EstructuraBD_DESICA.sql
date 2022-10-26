-- MySQL dump 10.18  Distrib 10.3.27-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 193.168.0.59    Database: DESICA
-- ------------------------------------------------------
-- Server version	5.1.73

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `KK_Dat`
--

DROP TABLE IF EXISTS `KK_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `KK_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codVar` varchar(5) NOT NULL,
  `nombreVar` varchar(80) DEFAULT NULL,
  `valor` double(10,0) DEFAULT NULL,
  `observacion` varchar(100) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomAsigConcepto_Dat`
--

DROP TABLE IF EXISTS `NomAsigConcepto_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomAsigConcepto_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codNomina` int(2) NOT NULL,
  `nroTrabajador` int(5) NOT NULL,
  `codConcepto` int(5) NOT NULL,
  `fechaInicial` date DEFAULT NULL,
  `fechaFinal` date DEFAULT NULL,
  `cantidad` double DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `porcentaje` double DEFAULT NULL,
  `montoMin` double DEFAULT NULL,
  `montoMax` double DEFAULT NULL,
  `frecuenciaCalculo` varchar(1) DEFAULT NULL,
  `calcular` varchar(1) DEFAULT NULL,
  `codFormula` varchar(10) DEFAULT NULL,
  `frecuenciaPago` varchar(1) DEFAULT NULL,
  `formaPago` varchar(1) DEFAULT NULL,
  `inicializable` varchar(1) DEFAULT NULL,
  `activo` varchar(1) DEFAULT 'S',
  `observacion1` text,
  `observacion2` text,
  PRIMARY KEY (`rifEmpresa`,`codNomina`,`nroTrabajador`,`codConcepto`),
  KEY `NomAsigConcepto_FK02` (`codNomina`),
  KEY `NomAsigConcepto_FK03` (`nroTrabajador`),
  CONSTRAINT `NomAsigConcepto_FK02` FOREIGN KEY (`codNomina`) REFERENCES `NomTiposNomina_Dat` (`codNomina`),
  CONSTRAINT `NomAsigConcepto_FK03` FOREIGN KEY (`nroTrabajador`) REFERENCES `NomTrabajador00_Dat` (`nroTrabajador`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomBanco_Dat`
--

DROP TABLE IF EXISTS `NomBanco_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomBanco_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codBanco` varchar(5) NOT NULL,
  `nombreBanco` varchar(50) NOT NULL,
  `tipocuenta1` varchar(1) DEFAULT NULL,
  `ctaNro1` varchar(30) DEFAULT NULL,
  `tipoCuenta2` varchar(2) DEFAULT NULL,
  `ctaNro2` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`codBanco`),
  UNIQUE KEY `NomBanco_Ind` (`rifEmpresa`,`codBanco`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomCalculo1_Dat`
--

DROP TABLE IF EXISTS `NomCalculo1_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomCalculo1_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codNomina` int(2) NOT NULL,
  `nroTrabajador` int(5) NOT NULL,
  `codConcepto` int(5) NOT NULL,
  `fechaNomina` date NOT NULL DEFAULT '0000-00-00',
  `turno` int(2) DEFAULT NULL,
  `cantidad` double DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `porcentaje` double DEFAULT NULL,
  `montoMin` double DEFAULT NULL,
  `montoMax` double DEFAULT NULL,
  `frecuenciaCalculo` varchar(1) DEFAULT NULL,
  `codFormula` varchar(10) DEFAULT NULL,
  `frecuenciaPago` varchar(1) DEFAULT NULL,
  `formaPago` varchar(1) DEFAULT NULL,
  `inicializable` varchar(1) DEFAULT NULL,
  `observacion1` text,
  `observacion2` text,
  PRIMARY KEY (`rifEmpresa`,`codNomina`,`nroTrabajador`,`codConcepto`,`fechaNomina`),
  KEY `NomCalculo1_Ind` (`rifEmpresa`,`codNomina`,`nroTrabajador`,`codConcepto`,`fechaNomina`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomCalculo_Dat`
--

DROP TABLE IF EXISTS `NomCalculo_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomCalculo_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codNomina` int(2) NOT NULL,
  `nroTrabajador` int(5) NOT NULL,
  `codConcepto` int(5) NOT NULL,
  `fechaNomina` date NOT NULL DEFAULT '0000-00-00',
  `turno` int(2) DEFAULT NULL,
  `cantidad` double DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `porcentaje` double DEFAULT NULL,
  `montoMin` double DEFAULT NULL,
  `montoMax` double DEFAULT NULL,
  `frecuenciaCalculo` varchar(1) DEFAULT NULL,
  `codFormula` varchar(10) DEFAULT NULL,
  `frecuenciaPago` varchar(1) DEFAULT NULL,
  `formaPago` varchar(1) DEFAULT NULL,
  `inicializable` varchar(1) DEFAULT NULL,
  `observacion1` text,
  `observacion2` text,
  PRIMARY KEY (`rifEmpresa`,`codNomina`,`nroTrabajador`,`codConcepto`,`fechaNomina`),
  KEY `NomCalculo_Ind` (`rifEmpresa`,`codNomina`,`codConcepto`,`fechaNomina`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomConceptoHist_Dat`
--

DROP TABLE IF EXISTS `NomConceptoHist_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomConceptoHist_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codNomina` int(2) NOT NULL,
  `nroTrabajador` int(5) NOT NULL,
  `codConcepto` int(5) NOT NULL,
  `fechaNomina` date NOT NULL DEFAULT '0000-00-00',
  `turno` int(2) DEFAULT NULL,
  `cantidad` double DEFAULT NULL,
  `monto` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `porcentaje` double DEFAULT NULL,
  `montoMin` double DEFAULT NULL,
  `montoMax` double DEFAULT NULL,
  `frecuenciaCalculo` varchar(1) DEFAULT NULL,
  `codFormula` int(5) DEFAULT NULL,
  `frecuenciaPago` varchar(1) DEFAULT NULL,
  `formaPago` varchar(1) DEFAULT NULL,
  `inicializable` varchar(1) DEFAULT NULL,
  `observacion1` text,
  `observacion2` text,
  PRIMARY KEY (`rifEmpresa`,`codNomina`,`nroTrabajador`,`codConcepto`,`fechaNomina`),
  KEY `NomAsigConcepto_FK02` (`codNomina`),
  KEY `NomAsigConcepto_FK03` (`nroTrabajador`),
  CONSTRAINT `NomConceptoHist_FK01` FOREIGN KEY (`rifEmpresa`) REFERENCES `NomEmpresa_Dat` (`rif`),
  CONSTRAINT `NomConceptoHist_FK02` FOREIGN KEY (`codNomina`) REFERENCES `NomTiposNomina_Dat` (`codNomina`),
  CONSTRAINT `NomConceptoHist_FK03` FOREIGN KEY (`nroTrabajador`) REFERENCES `NomTrabajador00_Dat` (`nroTrabajador`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomConcepto_Dat`
--

DROP TABLE IF EXISTS `NomConcepto_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomConcepto_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codNomina` int(2) NOT NULL,
  `codConcepto` int(5) NOT NULL,
  `descripcion` varchar(60) DEFAULT NULL,
  `codFormula` int(5) DEFAULT NULL,
  `formaCalculo` varchar(1) DEFAULT NULL,
  `asignacion` varchar(1) DEFAULT NULL,
  `ctaDebito` varchar(10) DEFAULT NULL,
  `ctaCredito` varchar(10) DEFAULT NULL,
  `montoMin` double DEFAULT NULL,
  `montoMax` double DEFAULT NULL,
  `inicializable` varchar(1) DEFAULT NULL,
  `activo` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`codNomina`,`codConcepto`),
  CONSTRAINT `NomConcepto_FK` FOREIGN KEY (`rifEmpresa`) REFERENCES `NomEmpresa_Dat` (`rif`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomConfig_Dat`
--

DROP TABLE IF EXISTS `NomConfig_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomConfig_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `ruta1` varchar(60) NOT NULL,
  `ruta2` varchar(60) DEFAULT NULL,
  `correo1` varchar(50) DEFAULT NULL,
  `correo2` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`),
  CONSTRAINT `NomConfig_FK` FOREIGN KEY (`rifEmpresa`) REFERENCES `NomEmpresa_Dat` (`rif`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomEmpresa_Dat`
--

DROP TABLE IF EXISTS `NomEmpresa_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomEmpresa_Dat` (
  `nombre` varchar(150) NOT NULL,
  `razonSocial` varchar(200) NOT NULL,
  `ramo` varchar(50) NOT NULL,
  `rif` varchar(25) NOT NULL DEFAULT 'J-08503850-7',
  `pais` varchar(30) NOT NULL,
  `estado` varchar(30) NOT NULL,
  `ciudad` varchar(40) NOT NULL,
  `direccion1` varchar(100) NOT NULL,
  `direccion2` varchar(100) NOT NULL,
  `direccion3` varchar(100) NOT NULL,
  `email` varchar(200) NOT NULL,
  `mision` varchar(200) NOT NULL,
  `vision` varchar(200) NOT NULL,
  PRIMARY KEY (`rif`),
  UNIQUE KEY `razonSocial` (`razonSocial`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomEstados_Dat`
--

DROP TABLE IF EXISTS `NomEstados_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomEstados_Dat` (
  `codPais` int(8) NOT NULL,
  `codEstado` int(8) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`codPais`,`codEstado`),
  UNIQUE KEY `codEstado` (`codEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomFormula_Dat`
--

DROP TABLE IF EXISTS `NomFormula_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomFormula_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codNomina` int(2) NOT NULL,
  `codFormula` varchar(10) NOT NULL DEFAULT '',
  `codConcepto` int(5) NOT NULL,
  `accion` varchar(1) NOT NULL,
  `correlativo` int(3) NOT NULL,
  `formula` varchar(100) DEFAULT NULL,
  `observacion` varchar(100) DEFAULT NULL,
  `siguiente` int(3) DEFAULT NULL,
  `anterior` int(3) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`codNomina`,`codFormula`,`codConcepto`,`accion`,`correlativo`),
  CONSTRAINT `NomFormula01_FK` FOREIGN KEY (`rifEmpresa`) REFERENCES `NomEmpresa_Dat` (`rif`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomPais_Dat`
--

DROP TABLE IF EXISTS `NomPais_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomPais_Dat` (
  `codPais` int(8) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`codPais`),
  UNIQUE KEY `codPais` (`codPais`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomPromedioHist_Dat`
--

DROP TABLE IF EXISTS `NomPromedioHist_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomPromedioHist_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codProm` varchar(5) NOT NULL,
  `fechaVigencia` date NOT NULL,
  `valor` double DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`codProm`,`fechaVigencia`),
  CONSTRAINT `NomPromedioHist00_FK` FOREIGN KEY (`rifEmpresa`) REFERENCES `NomEmpresa_Dat` (`rif`),
  CONSTRAINT `NomPromedioHist01_FK` FOREIGN KEY (`rifEmpresa`, `codProm`) REFERENCES `NomPromedio_Dat` (`rifEmpresa`, `codProm`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomPromedio_Dat`
--

DROP TABLE IF EXISTS `NomPromedio_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomPromedio_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codProm` varchar(5) NOT NULL,
  `nombreProm` varchar(80) DEFAULT NULL,
  `ecuacion` varchar(60) DEFAULT NULL,
  `observacion` varchar(100) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`codProm`),
  UNIQUE KEY `nombreProm` (`codProm`),
  CONSTRAINT `NomPromedio_FK` FOREIGN KEY (`rifEmpresa`) REFERENCES `NomEmpresa_Dat` (`rif`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomTiposNomina_Dat`
--

DROP TABLE IF EXISTS `NomTiposNomina_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomTiposNomina_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codNomina` int(2) NOT NULL,
  `nombreNomina` varchar(60) DEFAULT NULL,
  `ultimaRotacion` date DEFAULT NULL,
  `proxRotacion` date DEFAULT NULL,
  `ultimaRotacion1` date DEFAULT NULL,
  `proxRotacion1` date DEFAULT NULL,
  `ultimaRotacion2` date DEFAULT NULL,
  `proxRotacion2` date DEFAULT NULL,
  `baseCalculo` varchar(1) DEFAULT NULL,
  `pagaSemanaFondo` varchar(1) DEFAULT NULL,
  `habilLun` int(1) DEFAULT NULL,
  `habilMar` int(1) DEFAULT NULL,
  `habilMier` int(1) DEFAULT NULL,
  `habilJue` int(1) DEFAULT NULL,
  `habilVier` int(1) DEFAULT NULL,
  `habilSab` int(1) DEFAULT NULL,
  `habilDom` int(1) DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`codNomina`),
  KEY `codNomina` (`codNomina`),
  CONSTRAINT `NomTiposNom_FK` FOREIGN KEY (`rifEmpresa`) REFERENCES `NomEmpresa_Dat` (`rif`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomTrabajador00_Dat`
--

DROP TABLE IF EXISTS `NomTrabajador00_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomTrabajador00_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `nroTrabajador` int(5) NOT NULL,
  `foto` blob,
  `nacionalidad` varchar(1) DEFAULT NULL,
  `cedulaID` varchar(20) DEFAULT NULL,
  `nombre1` varchar(25) DEFAULT NULL,
  `nombre2` varchar(25) DEFAULT NULL,
  `apellido1` varchar(25) DEFAULT NULL,
  `apellido2` varchar(25) DEFAULT NULL,
  `alias` varchar(60) DEFAULT NULL,
  `sexo` varchar(1) DEFAULT NULL,
  `edoCivil` varchar(1) DEFAULT NULL,
  `fechaNac` date DEFAULT NULL,
  `telefonoHab1` varchar(14) DEFAULT NULL,
  `telefonoHab2` varchar(14) DEFAULT NULL,
  `telefonoMovil1` varchar(14) DEFAULT NULL,
  `telefonoMovil2` varchar(14) DEFAULT NULL,
  `email1` varchar(30) DEFAULT NULL,
  `email2` varchar(30) DEFAULT NULL,
  `direccionHab1` varchar(50) DEFAULT NULL,
  `direccionHab2` varchar(50) DEFAULT NULL,
  `direccionHab3` varchar(50) DEFAULT NULL,
  `codigoPostalHab` varchar(10) DEFAULT NULL,
  `parroquiaHab` varchar(30) DEFAULT NULL,
  `municipioHab` varchar(30) DEFAULT NULL,
  `ciudadHab` varchar(30) DEFAULT NULL,
  `estadoHab` varchar(30) DEFAULT NULL,
  `paisHab` varchar(30) DEFAULT NULL,
  `codigoPostalNac` varchar(10) DEFAULT NULL,
  `parroquiaNac` varchar(30) DEFAULT NULL,
  `municipioNac` varchar(30) DEFAULT NULL,
  `ciudadNac` varchar(30) DEFAULT NULL,
  `estadoNac` varchar(30) DEFAULT NULL,
  `paisNac` varchar(30) DEFAULT NULL,
  `nombreVarAux1` varchar(30) DEFAULT NULL,
  `varAux1` varchar(30) DEFAULT NULL,
  `nombreVarAux2` varchar(30) DEFAULT NULL,
  `varAux2` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`nroTrabajador`),
  KEY `nroTrabajador` (`nroTrabajador`),
  CONSTRAINT `NomTrabajador00_FK` FOREIGN KEY (`rifEmpresa`) REFERENCES `NomEmpresa_Dat` (`rif`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomTrabajador01_Dat`
--

DROP TABLE IF EXISTS `NomTrabajador01_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomTrabajador01_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `nroTrabajador` int(5) NOT NULL,
  `fechaIngreso` date DEFAULT NULL,
  `turno` int(2) DEFAULT NULL,
  `rotacion` int(6) DEFAULT NULL,
  `rif` varchar(25) DEFAULT NULL,
  `nroSSO` varchar(25) DEFAULT NULL,
  `tipoNomina` int(2) DEFAULT NULL,
  `codBanco` varchar(5) DEFAULT NULL,
  `cuentaBanco` varchar(30) DEFAULT NULL,
  `fechaRetiro` date DEFAULT NULL,
  `marcaTarjeta` varchar(1) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`nroTrabajador`),
  KEY `rifEmpresa` (`rifEmpresa`,`nroTrabajador`),
  KEY `nroTrabajador` (`nroTrabajador`),
  CONSTRAINT `NomTrabajador01_FK1` FOREIGN KEY (`rifEmpresa`, `nroTrabajador`) REFERENCES `NomTrabajador00_Dat` (`rifEmpresa`, `nroTrabajador`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomTrabajador02_Dat`
--

DROP TABLE IF EXISTS `NomTrabajador02_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomTrabajador02_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `nroTrabajador` int(5) NOT NULL,
  `codNomina` int(2) NOT NULL DEFAULT '0',
  `turno` int(2) DEFAULT NULL,
  `rotacion` int(6) DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`nroTrabajador`,`codNomina`),
  KEY `rifEmpresa` (`rifEmpresa`,`nroTrabajador`),
  KEY `nroTrabajador` (`nroTrabajador`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `NomTrabajador_View`
--

DROP TABLE IF EXISTS `NomTrabajador_View`;
/*!50001 DROP VIEW IF EXISTS `NomTrabajador_View`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `NomTrabajador_View` (
  `rifEmpresa` tinyint NOT NULL,
  `tipoNomina` tinyint NOT NULL,
  `nroTrabajador` tinyint NOT NULL,
  `cedulaID` tinyint NOT NULL,
  `nombre1` tinyint NOT NULL,
  `apellido1` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `NomVariableHist_Dat`
--

DROP TABLE IF EXISTS `NomVariableHist_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomVariableHist_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codVar` varchar(5) NOT NULL,
  `fechaVigencia` date NOT NULL,
  `valor` double DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`codVar`,`fechaVigencia`),
  CONSTRAINT `NomVariableHist00_FK` FOREIGN KEY (`rifEmpresa`) REFERENCES `NomEmpresa_Dat` (`rif`),
  CONSTRAINT `NomVariableHist01_FK` FOREIGN KEY (`rifEmpresa`, `codVar`) REFERENCES `NomVariable_Dat` (`rifEmpresa`, `codVar`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `NomVariable_Dat`
--

DROP TABLE IF EXISTS `NomVariable_Dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `NomVariable_Dat` (
  `rifEmpresa` varchar(25) NOT NULL,
  `codVar` varchar(5) NOT NULL,
  `nombreVar` varchar(80) DEFAULT NULL,
  `valor` double(10,0) DEFAULT NULL,
  `observacion` varchar(100) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`rifEmpresa`,`codVar`),
  CONSTRAINT `NomVariable_FK` FOREIGN KEY (`rifEmpresa`) REFERENCES `NomEmpresa_Dat` (`rif`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Nomina_dat`
--

DROP TABLE IF EXISTS `Nomina_dat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Nomina_dat` (
  `rif` varchar(20) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `apellido` varchar(20) NOT NULL,
  `alias` varchar(40) DEFAULT NULL,
  `fecha_nac` date DEFAULT NULL,
  `edad` smallint(1) DEFAULT NULL,
  PRIMARY KEY (`rif`),
  UNIQUE KEY `Nomina_ind` (`rif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `NomTrabajador_View`
--

/*!50001 DROP TABLE IF EXISTS `NomTrabajador_View`*/;
/*!50001 DROP VIEW IF EXISTS `NomTrabajador_View`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = latin1 */;
/*!50001 SET character_set_results     = latin1 */;
/*!50001 SET collation_connection      = latin1_swedish_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `NomTrabajador_View` AS select `t1`.`rifEmpresa` AS `rifEmpresa`,`t2`.`tipoNomina` AS `tipoNomina`,`t1`.`nroTrabajador` AS `nroTrabajador`,`t1`.`cedulaID` AS `cedulaID`,`t1`.`nombre1` AS `nombre1`,`t1`.`apellido1` AS `apellido1` from (`NomTrabajador00_Dat` `t1` left join `NomTrabajador01_Dat` `t2` on(((`t1`.`rifEmpresa` = `t2`.`rifEmpresa`) and (`t1`.`nroTrabajador` = `t2`.`nroTrabajador`)))) order by `t1`.`rifEmpresa`,`t2`.`tipoNomina`,`t1`.`nroTrabajador` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-17 16:34:41
