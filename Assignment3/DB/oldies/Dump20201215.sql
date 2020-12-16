-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: g9_gonature
-- ------------------------------------------------------
-- Server version	8.0.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creditcard` (
  `cardNumber` int NOT NULL,
  `cardHolderName` varchar(45) DEFAULT NULL,
  `expiredDate` date DEFAULT NULL,
  `cvc` int DEFAULT NULL,
  PRIMARY KEY (`cardNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard`
--

LOCK TABLES `creditcard` WRITE;
/*!40000 ALTER TABLE `creditcard` DISABLE KEYS */;
/*!40000 ALTER TABLE `creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dateandtime`
--

DROP TABLE IF EXISTS `dateandtime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dateandtime` (
  `day` date DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `orderNumber` varchar(45) NOT NULL,
  PRIMARY KEY (`orderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dateandtime`
--

LOCK TABLES `dateandtime` WRITE;
/*!40000 ALTER TABLE `dateandtime` DISABLE KEYS */;
/*!40000 ALTER TABLE `dateandtime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departmentmanager`
--

DROP TABLE IF EXISTS `departmentmanager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departmentmanager` (
  `departmentName` varchar(45) DEFAULT NULL,
  `employeeNumber` varchar(45) NOT NULL,
  PRIMARY KEY (`employeeNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departmentmanager`
--

LOCK TABLES `departmentmanager` WRITE;
/*!40000 ALTER TABLE `departmentmanager` DISABLE KEYS */;
/*!40000 ALTER TABLE `departmentmanager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `employeeNumber` int NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`employeeNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('bark','12345678','bar','katz',1,'bar@Nature.com','ParkManager','jurasic'),('Haim','12121212','Haim','Azulay',12,'exmaple@website.com','ParkManager','idk');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `ID` int DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `memberNumber` varchar(45) NOT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `payment` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `amount` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`memberNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (123456,'rinat','students','1','972867648374','rinatbeeretz@haenglish.com','paypal','family','3'),(1234567,'rinat','gabai','2','999777888','rinatGab@test.com','cash','single','1');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `orderNumber` int NOT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  `arrivedTime` datetime DEFAULT NULL,
  `visitorsNumber` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  `discount` varchar(45) DEFAULT NULL,
  `payment` varchar(45) DEFAULT NULL,
  `orderType` varchar(45) DEFAULT NULL,
  `memberID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`orderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1111,'jurasic','2021-01-02 08:00:00','10','a@b.c','400','10','CASH','guide','1'),(2222,'shoko','2020-12-15 12:00:00','12','exmaple@website.com','100','1.5','CASH','guide','2');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `park`
--

DROP TABLE IF EXISTS `park`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `park` (
  `parkName` varchar(45) NOT NULL,
  `currentVisitoreAmount` varchar(45) DEFAULT NULL,
  `maxVisitorAmount` varchar(45) NOT NULL,
  `maxAmountOrders` varchar(45) NOT NULL,
  PRIMARY KEY (`parkName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park`
--

LOCK TABLES `park` WRITE;
/*!40000 ALTER TABLE `park` DISABLE KEYS */;
INSERT INTO `park` VALUES ('jura','10','100','70');
/*!40000 ALTER TABLE `park` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `startDate` date NOT NULL,
  `end date` date DEFAULT NULL,
  `totalVisitorCapacity` int DEFAULT NULL,
  `notFullBooked` date DEFAULT NULL,
  `calcRevenueForPark` varchar(45) DEFAULT NULL,
  `reportType` varchar(45) DEFAULT NULL,
  `cancelOrder` varchar(45) DEFAULT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`startDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typetable`
--

DROP TABLE IF EXISTS `typetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `typetable` (
  `memberNumber` int NOT NULL,
  `amount` int DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `typeTablecol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`memberNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typetable`
--

LOCK TABLES `typetable` WRITE;
/*!40000 ALTER TABLE `typetable` DISABLE KEYS */;
/*!40000 ALTER TABLE `typetable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `waitinglist`
--

DROP TABLE IF EXISTS `waitinglist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `waitinglist` (
  `parkname` varchar(45) NOT NULL,
  `orderNumber` varchar(45) DEFAULT NULL,
  `enteredtime` datetime DEFAULT NULL,
  PRIMARY KEY (`parkname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waitinglist`
--

LOCK TABLES `waitinglist` WRITE;
/*!40000 ALTER TABLE `waitinglist` DISABLE KEYS */;
/*!40000 ALTER TABLE `waitinglist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'g9_gonature'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-15 21:46:36
