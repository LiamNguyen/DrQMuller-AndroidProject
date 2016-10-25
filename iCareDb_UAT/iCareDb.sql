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
-- Table structure for table `tbl_appointments`
--

DROP TABLE IF EXISTS `tbl_appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_appointments` (
  `APPOINTMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DAY_ID` int(11) DEFAULT NULL,
  `TIME_ID` int(11) DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  `EXPIRED_DATE` date DEFAULT NULL,
  `CUSTOMER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`APPOINTMENT_ID`),
  KEY `DAY_ID` (`DAY_ID`),
  KEY `TIME_ID` (`TIME_ID`),
  KEY `CUSTOMER_ID` (`CUSTOMER_ID`),
  CONSTRAINT `tbl_appointments_ibfk_1` FOREIGN KEY (`DAY_ID`) REFERENCES `tbl_weekdays` (`DAY_ID`),
  CONSTRAINT `tbl_appointments_ibfk_2` FOREIGN KEY (`TIME_ID`) REFERENCES `tbl_time` (`TIME_ID`),
  CONSTRAINT `tbl_appointments_ibfk_3` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `tbl_customers` (`CUSTOMER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_appointments`
--

LOCK TABLES `tbl_appointments` WRITE;
/*!40000 ALTER TABLE `tbl_appointments` DISABLE KEYS */;
INSERT INTO `tbl_appointments` VALUES (1,1,1,'2016-10-13','2016-11-13',1),(2,2,6,'2016-12-10','2017-01-10',1);
/*!40000 ALTER TABLE `tbl_appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_customers`
--

DROP TABLE IF EXISTS `tbl_customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_customers` (
  `CUSTOMER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CUSTOMER_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `GENDER` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  `PHONE` varchar(45) CHARACTER SET latin1 DEFAULT NULL,
  `ADDRESS` varchar(150) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `EMAIL` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `ISFILLEDTOCONFIRM` int(11) DEFAULT '0',
  `LOGIN_ID` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PASSWORD` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATEDAT` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATEDAT` datetime NOT NULL,
  `STATUS` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`CUSTOMER_ID`),
  UNIQUE KEY `LOGIN_ID` (`LOGIN_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_customers`
--

LOCK TABLES `tbl_customers` WRITE;
/*!40000 ALTER TABLE `tbl_customers` DISABLE KEYS */;
INSERT INTO `tbl_customers` VALUES (1,'Nguyen Thien Phuc','1996-10-06','Male','0468452599','Espoo',NULL,0,NULL,NULL,'2016-10-18 10:26:15','2016-10-18 10:27:09',1),(2,'Vu Hoang Long','1996-08-24','Male','0468338859','Espoo',NULL,0,NULL,NULL,'2016-10-18 10:26:15','2016-10-18 10:27:09',1),(3,'Mai Thanh Truc','1994-10-12','Female','093845732743','Ho Chi Minh',NULL,0,NULL,NULL,'2016-10-18 10:26:15','2016-10-18 10:27:09',1),(4,'Nguyen Quang Minh','1999-11-18','Male','01234233955','Ho Chi Minh',NULL,0,NULL,NULL,'2016-10-18 10:26:15','2016-10-18 10:27:09',1),(5,'Nguyen Quang Minh','1890-04-11','Male','01234555847','Ho Chi Minh',NULL,0,NULL,NULL,'2016-10-18 10:26:15','2016-10-18 10:27:09',1),(6,'Nguyen Thien Phuc','2016-10-06','Male','0468452599','HO CHI MINH','',0,NULL,NULL,'2016-10-18 10:26:15','2016-10-18 10:27:09',1),(7,'Nguyen Quang Minh','1999-11-18','Male','0468452599','HO CHI MINH','',0,NULL,NULL,'2016-10-18 10:32:22','2016-10-18 10:32:22',1),(9,'Nguyễn Quang Minh','1999-11-18','Male','0468452599','HO CHI MINH','',0,NULL,NULL,'2016-10-18 10:49:57','0000-00-00 00:00:00',1),(10,'Nguyen Quang Minh','1999-11-18','Male','0468452599','HO CHI MINH','',0,'TEST_USERNAME','TEST_PASSWORD','2016-10-18 12:44:28','2016-10-18 12:45:00',1);
/*!40000 ALTER TABLE `tbl_customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_locations`
--

DROP TABLE IF EXISTS `tbl_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_locations` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(150) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `ADDRESS` varchar(150) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci DEFAULT NULL,
  `LATITUDE` varchar(10) DEFAULT NULL,
  `LONGITUDE` varchar(10) DEFAULT NULL,
  `DATE_CREATED` datetime DEFAULT NULL,
  `STATUS` int(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_locations`
--

LOCK TABLES `tbl_locations` WRITE;
/*!40000 ALTER TABLE `tbl_locations` DISABLE KEYS */;
INSERT INTO `tbl_locations` VALUES (1,'Apple Centre','1 Infinite Loop Cupertino, CA','37.331741','-122.03033','0000-00-00 00:00:00',1),(2,'Googleplex','1600 Amphitheatre Pkwy, Mountain View','37.421999','-122.08395','0000-00-00 00:00:00',1);
/*!40000 ALTER TABLE `tbl_locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_time`
--

DROP TABLE IF EXISTS `tbl_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_time` (
  `TIME_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TIME` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`TIME_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_time`
--

LOCK TABLES `tbl_time` WRITE;
/*!40000 ALTER TABLE `tbl_time` DISABLE KEYS */;
INSERT INTO `tbl_time` VALUES (1,'07:00'),(2,'07:25'),(3,'07:50'),(6,'08:15'),(7,'08:40'),(8,'09:05'),(9,'09:30'),(10,'09:55'),(11,'10:20'),(12,'10:45'),(13,'10:10'),(14,'10:35'),(15,'11:00'),(16,'11:25'),(17,'11:50'),(18,'12:15'),(19,'12:40'),(20,'13:05'),(21,'13:30'),(22,'13:55'),(23,'14:20'),(24,'14:45'),(25,'15:10'),(26,'15:35'),(27,'15:00'),(28,'16:25'),(29,'16:50'),(30,'17:15'),(31,'17:40'),(32,'18:05'),(33,'18:30'),(34,'18:55'),(35,'19:20'),(36,'19:45'),(37,'20:10'),(38,'20:35'),(39,'20:00'),(40,'20:25'),(41,'20:50'),(42,'21:15'),(43,'21:40'),(44,'22:05'),(45,'22:30'),(46,'06:35');
/*!40000 ALTER TABLE `tbl_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_weekdays`
--

DROP TABLE IF EXISTS `tbl_weekdays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_weekdays` (
  `DAY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DAY` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`DAY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_weekdays`
--

LOCK TABLES `tbl_weekdays` WRITE;
/*!40000 ALTER TABLE `tbl_weekdays` DISABLE KEYS */;
INSERT INTO `tbl_weekdays` VALUES (1,'Monday'),(2,'Tuesday'),(3,'Wednesday'),(4,'Thursday'),(5,'Friday'),(6,'Saturday'),(7,'Sunday');
/*!40000 ALTER TABLE `tbl_weekdays` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-23 16:37:02
