-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dbcds
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.10-MariaDB

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
-- Table structure for table `tblcost`
--

DROP TABLE IF EXISTS `tblcost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcost` (
  `idCost` int(11) NOT NULL,
  `idInDoor` int(11) DEFAULT NULL,
  `idOutDoor` int(11) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  PRIMARY KEY (`idCost`),
  KEY `FK_tblCost_tblInDoor` (`idInDoor`),
  KEY `FK_tblCost_tblOutDoor` (`idOutDoor`),
  CONSTRAINT `FK_tblCost_tblInDoor` FOREIGN KEY (`idInDoor`) REFERENCES `tblindoor` (`idInDoor`),
  CONSTRAINT `FK_tblCost_tblOutDoor` FOREIGN KEY (`idOutDoor`) REFERENCES `tbloutdoor` (`idOutDoor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcost`
--

LOCK TABLES `tblcost` WRITE;
/*!40000 ALTER TABLE `tblcost` DISABLE KEYS */;
INSERT INTO `tblcost` VALUES (1,1,1,5),(2,1,2,5),(3,1,3,6),(4,1,4,8),(5,1,5,8),(6,2,1,5),(7,2,2,4),(8,2,3,5),(9,2,4,6),(10,2,5,7),(11,3,1,6),(12,3,2,5),(13,3,3,4),(14,3,4,5),(15,3,5,6),(16,4,1,7),(17,4,2,6),(18,4,3,5),(19,4,4,3),(20,4,5,5),(21,5,1,9),(22,5,2,7),(23,5,3,6),(24,5,4,5),(25,5,5,4),(26,6,1,5),(27,6,2,4),(28,6,3,6),(29,6,4,5),(30,6,5,5),(31,1,6,5),(32,2,6,3),(33,3,6,4),(34,4,6,5),(35,5,6,5),(36,6,6,6),(37,7,1,0),(38,7,2,0),(39,7,3,0),(40,7,4,0),(41,7,5,0),(42,7,6,0);
/*!40000 ALTER TABLE `tblcost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblcrossdockingsystem`
--

DROP TABLE IF EXISTS `tblcrossdockingsystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblcrossdockingsystem` (
  `idCrossDockingSystem` int(11) NOT NULL,
  `nameCrossDockingSystem` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCrossDockingSystem`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblcrossdockingsystem`
--

LOCK TABLES `tblcrossdockingsystem` WRITE;
/*!40000 ALTER TABLE `tblcrossdockingsystem` DISABLE KEYS */;
INSERT INTO `tblcrossdockingsystem` VALUES (1,'CDS','ADR',22000);
/*!40000 ALTER TABLE `tblcrossdockingsystem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblemployee`
--

DROP TABLE IF EXISTS `tblemployee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblemployee` (
  `idEmployee` int(11) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `active` int(11) DEFAULT NULL,
  PRIMARY KEY (`idEmployee`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblemployee`
--

LOCK TABLES `tblemployee` WRITE;
/*!40000 ALTER TABLE `tblemployee` DISABLE KEYS */;
INSERT INTO `tblemployee` VALUES (1,NULL,NULL,NULL,NULL,NULL,'admin','admin',1),(2,'Tran Anh Phuong',1,'taphuong@gmail.com','0962524284','Bac Ninh','taphuong','taphuong',1);
/*!40000 ALTER TABLE `tblemployee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblindoor`
--

DROP TABLE IF EXISTS `tblindoor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblindoor` (
  `idInDoor` int(11) NOT NULL,
  `nameInDoor` varchar(255) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `idCrossDockingSystem` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`idInDoor`),
  KEY `FK_tblInDoor_tblCrossDockingSystem` (`idCrossDockingSystem`),
  CONSTRAINT `FK_tblInDoor_tblCrossDockingSystem` FOREIGN KEY (`idCrossDockingSystem`) REFERENCES `tblcrossdockingsystem` (`idCrossDockingSystem`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblindoor`
--

LOCK TABLES `tblindoor` WRITE;
/*!40000 ALTER TABLE `tblindoor` DISABLE KEYS */;
INSERT INTO `tblindoor` VALUES (1,'InDoor 1',1000,1,1),(2,'InDoor 2',2000,1,1),(3,'InDoor 3',3000,1,1),(4,'InDoor 4',4000,1,1),(5,'InDoor 5',5000,1,1),(6,'InDoor 6',6000,1,0),(7,'InDoor 7',1000,1,0);
/*!40000 ALTER TABLE `tblindoor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblinvehicle`
--

DROP TABLE IF EXISTS `tblinvehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblinvehicle` (
  `idInVehicle` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `arrivalTime` time DEFAULT NULL,
  `startUnloadTime` time DEFAULT NULL,
  `finishUnloadTime` time DEFAULT NULL,
  `volumn` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `idInDoor` int(11) DEFAULT NULL,
  `idVehicle` int(11) DEFAULT NULL,
  `idCrossDockingSystem` int(11) DEFAULT NULL,
  PRIMARY KEY (`idInVehicle`),
  KEY `FK_tblInVehicle_tblInDoor` (`idInDoor`),
  KEY `FK_tblInVehicle_tblCrossDockingSystem` (`idCrossDockingSystem`),
  KEY `FK_tblInVehicle_tblVehicle` (`idVehicle`),
  CONSTRAINT `FK_tblInVehicle_tblCrossDockingSystem` FOREIGN KEY (`idCrossDockingSystem`) REFERENCES `tblcrossdockingsystem` (`idCrossDockingSystem`),
  CONSTRAINT `FK_tblInVehicle_tblInDoor` FOREIGN KEY (`idInDoor`) REFERENCES `tblindoor` (`idInDoor`),
  CONSTRAINT `FK_tblInVehicle_tblVehicle` FOREIGN KEY (`idVehicle`) REFERENCES `tblvehicle` (`idVehicle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblinvehicle`
--

LOCK TABLES `tblinvehicle` WRITE;
/*!40000 ALTER TABLE `tblinvehicle` DISABLE KEYS */;
INSERT INTO `tblinvehicle` VALUES (1,'2016-08-28','16:14:00',NULL,NULL,40,1,1,1,1),(2,'2016-08-28','16:14:00',NULL,NULL,30,1,1,2,1),(3,'2016-08-28','16:14:00',NULL,NULL,60,1,1,3,1),(4,'2016-08-28','16:14:00',NULL,NULL,70,0,NULL,4,1),(5,'2016-08-29','16:14:00',NULL,NULL,80,0,NULL,5,1),(6,'2016-08-29','15:30:00',NULL,NULL,90,0,NULL,6,1),(7,'2016-08-29','15:45:00','22:33:48','22:33:50',100,3,1,7,1),(8,'2016-09-15','22:10:15',NULL,NULL,45,0,NULL,1,1),(9,'2016-09-15','22:10:15',NULL,NULL,40,0,NULL,2,1),(10,'2016-09-15','22:10:15',NULL,NULL,55,0,NULL,3,1),(11,'2016-09-16','23:16:15',NULL,NULL,40,0,NULL,1,1),(12,'2016-09-17','00:21:30',NULL,NULL,40,0,NULL,1,1),(13,'2016-09-17','00:22:00',NULL,NULL,100,0,NULL,2,1),(14,'2016-09-18','20:10:00',NULL,NULL,40,0,NULL,1,1);
/*!40000 ALTER TABLE `tblinvehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbloutdoor`
--

DROP TABLE IF EXISTS `tbloutdoor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbloutdoor` (
  `idOutDoor` int(11) NOT NULL,
  `nameOutDoor` varchar(255) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `idCrossDockingSystem` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`idOutDoor`),
  KEY `FK_tblOutDoor_tblCrossDockingSystem` (`idCrossDockingSystem`),
  CONSTRAINT `FK_tblOutDoor_tblCrossDockingSystem` FOREIGN KEY (`idCrossDockingSystem`) REFERENCES `tblcrossdockingsystem` (`idCrossDockingSystem`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbloutdoor`
--

LOCK TABLES `tbloutdoor` WRITE;
/*!40000 ALTER TABLE `tbloutdoor` DISABLE KEYS */;
INSERT INTO `tbloutdoor` VALUES (1,'OutDoor 1',1000,1,1),(2,'OutDoor 2',2000,1,1),(3,'OutDoor 3',3000,1,1),(4,'OutDoor 4',4000,1,1),(5,'OutDoor 5',5000,1,1),(6,'OutDoor 6',6000,1,0);
/*!40000 ALTER TABLE `tbloutdoor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbloutvehicle`
--

DROP TABLE IF EXISTS `tbloutvehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbloutvehicle` (
  `idOutVehicle` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `arrivalTime` time DEFAULT NULL,
  `startLoadTime` time DEFAULT NULL,
  `finishLoadTime` time DEFAULT NULL,
  `demand` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `idOutDoor` int(11) DEFAULT NULL,
  `idVehicle` int(11) DEFAULT NULL,
  `idCrossDockingSystem` int(11) DEFAULT NULL,
  PRIMARY KEY (`idOutVehicle`),
  KEY `FK_tblOutVehicle_tblInDoor` (`idOutDoor`),
  KEY `FK_tblOutVehicle_tblCrossDockingSystem` (`idCrossDockingSystem`),
  CONSTRAINT `FK_tblOutVehicle_tblCrossDockingSystem` FOREIGN KEY (`idCrossDockingSystem`) REFERENCES `tblcrossdockingsystem` (`idCrossDockingSystem`),
  CONSTRAINT `FK_tblOutVehicle_tblInDoor` FOREIGN KEY (`idOutDoor`) REFERENCES `tbloutdoor` (`idOutDoor`),
  CONSTRAINT `FK_tblOutVehicle_tblVehicle` FOREIGN KEY (`idOutVehicle`) REFERENCES `tblvehicle` (`idVehicle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbloutvehicle`
--

LOCK TABLES `tbloutvehicle` WRITE;
/*!40000 ALTER TABLE `tbloutvehicle` DISABLE KEYS */;
INSERT INTO `tbloutvehicle` VALUES (1,'2016-08-29','06:30:00',NULL,NULL,20,1,2,8,1),(2,'2016-08-29','04:30:00',NULL,NULL,30,0,NULL,9,1),(3,'2016-08-29','05:30:00',NULL,NULL,40,0,NULL,10,1),(4,'2016-08-29','04:30:00',NULL,NULL,50,0,NULL,11,1),(5,'2016-08-29','04:30:00',NULL,NULL,60,0,NULL,12,1),(6,'2016-08-29','04:30:00','22:18:01',NULL,70,2,2,13,1),(7,'2016-08-29','04:30:00','22:37:15','22:38:04',80,3,1,14,1),(8,'2016-09-16','23:16:30',NULL,NULL,50,0,NULL,8,1),(9,'2016-09-17','00:22:30',NULL,NULL,50,0,NULL,8,1),(10,'2016-09-17','00:22:15',NULL,NULL,30,0,NULL,9,1),(11,'2016-09-18','20:10:15',NULL,NULL,30,0,NULL,8,1);
/*!40000 ALTER TABLE `tbloutvehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblproduct`
--

DROP TABLE IF EXISTS `tblproduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblproduct` (
  `idProduct` int(11) NOT NULL,
  `nameProduct` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idProduct`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblproduct`
--

LOCK TABLES `tblproduct` WRITE;
/*!40000 ALTER TABLE `tblproduct` DISABLE KEYS */;
INSERT INTO `tblproduct` VALUES (1,'Product 1','Des 1'),(2,'Product 2','Des 2'),(3,'Product 3','Des 3'),(4,'Product 4','Des 4'),(5,'Product 5','Des 5'),(6,'Product 6','Des 6');
/*!40000 ALTER TABLE `tblproduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblproductinvehicle`
--

DROP TABLE IF EXISTS `tblproductinvehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblproductinvehicle` (
  `idProductInVehicle` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `idProduct` int(11) DEFAULT NULL,
  `idInVehicle` int(11) DEFAULT NULL,
  PRIMARY KEY (`idProductInVehicle`),
  KEY `FK_tblProductInVehicle_tblProduct` (`idProduct`),
  KEY `FK_tblProductInVehicle_tblInVehicle` (`idInVehicle`),
  CONSTRAINT `FK_tblProductInVehicle_tblInVehicle` FOREIGN KEY (`idInVehicle`) REFERENCES `tblinvehicle` (`idInVehicle`),
  CONSTRAINT `FK_tblProductInVehicle_tblProduct` FOREIGN KEY (`idProduct`) REFERENCES `tblproduct` (`idProduct`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblproductinvehicle`
--

LOCK TABLES `tblproductinvehicle` WRITE;
/*!40000 ALTER TABLE `tblproductinvehicle` DISABLE KEYS */;
INSERT INTO `tblproductinvehicle` VALUES (1,20,'unit',1,1),(2,30,'unit',1,1),(3,40,'unit',1,2),(4,30,'unit',1,3),(5,30,'unit',1,4),(6,30,'unit',1,5),(7,40,'unit',1,6),(8,50,'unit',1,7),(9,30,'unit',1,7);
/*!40000 ALTER TABLE `tblproductinvehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblproductoutvehicle`
--

DROP TABLE IF EXISTS `tblproductoutvehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblproductoutvehicle` (
  `idProductOutVehicle` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `idProduct` int(11) DEFAULT NULL,
  `idOutVehicle` int(11) DEFAULT NULL,
  PRIMARY KEY (`idProductOutVehicle`),
  KEY `FK_tblProductOutVehicle_tblProduct` (`idProduct`),
  KEY `FK_tblProductOutVehicle_tblOutVehicle` (`idOutVehicle`),
  CONSTRAINT `FK_tblProductOutVehicle_tblOutVehicle` FOREIGN KEY (`idOutVehicle`) REFERENCES `tbloutvehicle` (`idOutVehicle`),
  CONSTRAINT `FK_tblProductOutVehicle_tblProduct` FOREIGN KEY (`idProduct`) REFERENCES `tblproduct` (`idProduct`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblproductoutvehicle`
--

LOCK TABLES `tblproductoutvehicle` WRITE;
/*!40000 ALTER TABLE `tblproductoutvehicle` DISABLE KEYS */;
INSERT INTO `tblproductoutvehicle` VALUES (1,30,'unit',1,1),(2,20,'unit',1,1),(3,30,'unit',2,2),(4,40,'unit',3,2),(5,50,'unit',4,3),(6,20,'unit',1,3),(7,30,'unit',2,4),(8,40,'unit',3,5),(9,60,'unit',3,6),(10,50,'unit',3,7);
/*!40000 ALTER TABLE `tblproductoutvehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblproducttransfer`
--

DROP TABLE IF EXISTS `tblproducttransfer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblproducttransfer` (
  `idProductTransfer` int(11) NOT NULL,
  `idInVehicle` int(11) DEFAULT NULL,
  `idOutVehicle` int(11) DEFAULT NULL,
  `transfer` int(11) DEFAULT NULL,
  PRIMARY KEY (`idProductTransfer`),
  KEY `PK_tblProductTrasferTime_tblInVehicle` (`idInVehicle`),
  KEY `PK_tblProductTrasferTime_tblOutVehicle` (`idOutVehicle`),
  CONSTRAINT `PK_tblProductTrasferTime_tblInVehicle` FOREIGN KEY (`idInVehicle`) REFERENCES `tblinvehicle` (`idInVehicle`),
  CONSTRAINT `PK_tblProductTrasferTime_tblOutVehicle` FOREIGN KEY (`idOutVehicle`) REFERENCES `tbloutvehicle` (`idOutVehicle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblproducttransfer`
--

LOCK TABLES `tblproducttransfer` WRITE;
/*!40000 ALTER TABLE `tblproducttransfer` DISABLE KEYS */;
INSERT INTO `tblproducttransfer` VALUES (1,1,1,2),(2,1,1,3),(3,1,2,2),(4,1,3,3),(5,1,4,4),(6,1,5,1),(7,1,6,5),(8,1,7,4),(9,2,1,2),(10,2,2,3),(11,2,3,4),(12,2,4,5),(13,2,5,3),(14,2,6,4),(15,2,7,5),(16,3,1,1),(17,3,2,2),(18,3,3,3),(19,3,4,4),(20,3,5,5),(21,3,6,1),(22,3,7,2),(23,4,7,2),(24,4,1,3),(25,4,2,4),(26,4,3,5),(27,4,4,2),(28,4,5,3),(29,4,6,4),(30,5,1,2),(31,5,2,3),(32,5,3,4),(33,5,4,5),(34,5,5,6),(35,5,6,2),(36,6,1,22),(37,6,2,3),(38,6,3,4),(39,6,4,5),(40,6,5,1),(41,6,6,3),(42,7,1,2),(43,7,2,3),(44,7,3,4),(45,7,4,5),(46,7,5,2),(47,7,6,3),(48,7,7,4),(49,5,2,4),(50,11,8,4),(51,12,9,4),(52,13,9,5),(53,12,10,6),(54,13,10,8);
/*!40000 ALTER TABLE `tblproducttransfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblproducttransform`
--

DROP TABLE IF EXISTS `tblproducttransform`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblproducttransform` (
  `idProductTransform` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `idProductInVehicle` int(11) DEFAULT NULL,
  `idProductOutVehicle` int(11) DEFAULT NULL,
  `idProduct` int(11) DEFAULT NULL,
  PRIMARY KEY (`idProductTransform`),
  KEY `FK_tblProductTransform_tblProductInVehicle` (`idProductInVehicle`),
  KEY `FK_tblProductTransform_tblProductOutVehicle` (`idProductOutVehicle`),
  CONSTRAINT `FK_tblProductTransform_tblProductInVehicle` FOREIGN KEY (`idProductInVehicle`) REFERENCES `tblproductinvehicle` (`idProductInVehicle`),
  CONSTRAINT `FK_tblProductTransform_tblProductOutVehicle` FOREIGN KEY (`idProductOutVehicle`) REFERENCES `tblproductoutvehicle` (`idProductOutVehicle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblproducttransform`
--

LOCK TABLES `tblproducttransform` WRITE;
/*!40000 ALTER TABLE `tblproducttransform` DISABLE KEYS */;
/*!40000 ALTER TABLE `tblproducttransform` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tblvehicle`
--

DROP TABLE IF EXISTS `tblvehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tblvehicle` (
  `idVehicle` int(11) NOT NULL,
  `vehicleCode` varchar(50) DEFAULT NULL,
  `vehicleType` varchar(50) DEFAULT NULL,
  `vehicleYear` int(11) DEFAULT NULL,
  `vehicleMake` varchar(255) DEFAULT NULL,
  `vehicleWeight` int(11) DEFAULT NULL,
  `vehicleTrailerNum` int(11) DEFAULT NULL,
  `vehicleDes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idVehicle`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tblvehicle`
--

LOCK TABLES `tblvehicle` WRITE;
/*!40000 ALTER TABLE `tblvehicle` DISABLE KEYS */;
INSERT INTO `tblvehicle` VALUES (1,'29A-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(2,'29B-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(3,'29C-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(4,'29D-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(5,'29E-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(6,'29F-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(7,'29G-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(8,'30A-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(9,'30B-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(10,'30C-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(11,'30D-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(12,'30E-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(13,'30F-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(14,'30G-12345','Type 1',1994,'TOYOTA',100,0,'Des 1'),(15,'31A-12345','Type 1',2000,'TOYOTA',200,0,'Des 1');
/*!40000 ALTER TABLE `tblvehicle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-18 21:33:20
