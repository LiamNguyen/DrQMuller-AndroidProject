-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: iCareDb
-- ------------------------------------------------------
-- Server version	5.6.27

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
-- Table structure for table `tbl_Appointments`
--

DROP TABLE IF EXISTS `tbl_Appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_Appointments` (
  `APPOINTMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DAY_ID` int(11) DEFAULT NULL,
  `TIME_ID` int(11) DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  `EXPIRED_DATE` date DEFAULT NULL,
  `CUSTOMER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`APPOINTMENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_Appointments`
--

LOCK TABLES `tbl_Appointments` WRITE;
/*!40000 ALTER TABLE `tbl_Appointments` DISABLE KEYS */;
INSERT INTO `tbl_Appointments` VALUES (1,1,1,'2016-10-13','2016-11-13',1),(2,2,6,'2016-12-10','2017-01-10',1);
/*!40000 ALTER TABLE `tbl_Appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_Customers`
--

DROP TABLE IF EXISTS `tbl_Customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_Customers` (
  `CUSTOMER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_NAME` varchar(200) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `GENDER` varchar(45) DEFAULT NULL,
  `PHONE` varchar(45) DEFAULT NULL,
  `ADDRESS` varchar(150) DEFAULT NULL,
  `EMAIL` varchar(200) DEFAULT NULL,
  `LOGIN_ID` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CUSTOMER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_Customers`
--

LOCK TABLES `tbl_Customers` WRITE;
/*!40000 ALTER TABLE `tbl_Customers` DISABLE KEYS */;
INSERT INTO `tbl_Customers` VALUES (1,'Nguyen Thien Phuc','1996-10-06','Male','0468452599','Espoo',NULL,NULL,NULL),(2,'Vu Hoang Long','1996-08-24','Male','0468338859','Espoo',NULL,NULL,NULL),(3,'Mai Thanh Truc','1994-10-12','Female','093845732743','Ho Chi Minh',NULL,NULL,NULL),(4,'Nguyen Quang Minh','1999-11-18','Male','01234233955','Ho Chi Minh',NULL,NULL,NULL),(5,'Nhan Phuc Vinh','1890-04-11','Male','01234555847','Ho Chi Minh',NULL,NULL,NULL);
/*!40000 ALTER TABLE `tbl_Customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_Locations`
--

DROP TABLE IF EXISTS `tbl_Locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_Locations` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(150) DEFAULT NULL,
  `ADDRESS` varchar(150) DEFAULT NULL,
  `LATITUDE` varchar(10) DEFAULT NULL,
  `LONGITUDE` varchar(10) DEFAULT NULL,
  `DATE_CREATED` datetime DEFAULT NULL,
  `STATUS` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_Locations`
--

LOCK TABLES `tbl_Locations` WRITE;
/*!40000 ALTER TABLE `tbl_Locations` DISABLE KEYS */;
INSERT INTO `tbl_Locations` VALUES (1,'Apple Centre','1 Infinite Loop Cupertino, CA','37.331741','-122.03033','0000-00-00 00:00:00',1),(2,'Googleplex','1600 Amphitheatre Pkwy, Mountain View','37.421999','-122.08395','0000-00-00 00:00:00',1);
/*!40000 ALTER TABLE `tbl_Locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_Time`
--

DROP TABLE IF EXISTS `tbl_Time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_Time` (
  `TIME_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TIME` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`TIME_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_Time`
--

LOCK TABLES `tbl_Time` WRITE;
/*!40000 ALTER TABLE `tbl_Time` DISABLE KEYS */;
INSERT INTO `tbl_Time` VALUES (1,'07:00'),(2,'07:25'),(3,'07:50'),(6,'08:15'),(7,'08:40'),(8,'09:05'),(9,'09:30'),(10,'09:55'),(11,'10:20'),(12,'10:45'),(13,'10:10'),(14,'10:35'),(15,'11:00'),(16,'11:25'),(17,'11:50'),(18,'12:15'),(19,'12:40'),(20,'13:05'),(21,'13:30'),(22,'13:55'),(23,'14:20'),(24,'14:45'),(25,'15:10'),(26,'15:35'),(27,'15:00'),(28,'16:25'),(29,'16:50'),(30,'17:15'),(31,'17:40'),(32,'18:05'),(33,'18:30'),(34,'18:55'),(35,'19:20'),(36,'19:45'),(37,'20:10'),(38,'20:35'),(39,'20:00'),(40,'20:25'),(41,'20:50'),(42,'21:15'),(43,'21:40'),(44,'22:05'),(45,'22:30'),(46,'06:35');
/*!40000 ALTER TABLE `tbl_Time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_WeekDays`
--

DROP TABLE IF EXISTS `tbl_WeekDays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_WeekDays` (
  `DAY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DAY` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`DAY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_WeekDays`
--

LOCK TABLES `tbl_WeekDays` WRITE;
/*!40000 ALTER TABLE `tbl_WeekDays` DISABLE KEYS */;
INSERT INTO `tbl_WeekDays` VALUES (1,'Monday'),(2,'Tuesday'),(3,'Wednesday'),(4,'Thursday'),(5,'Friday'),(6,'Saturday'),(7,'Sunday');
/*!40000 ALTER TABLE `tbl_WeekDays` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-14 19:16:29
