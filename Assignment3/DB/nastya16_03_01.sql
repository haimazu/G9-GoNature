-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: g9_gonature
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
-- Table structure for table `canceledorders`
--

DROP TABLE IF EXISTS `canceledorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `canceledorders` (
  `visitorsNumber` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `orderType` varchar(45) DEFAULT NULL,
  `beforeDiscountPrice` double DEFAULT NULL,
  `afterDiscountPrice` double DEFAULT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  `arrivedTime` datetime DEFAULT NULL,
  `memberID` varchar(45) DEFAULT NULL,
  `ID` varchar(45) DEFAULT NULL,
  `amountArrived` varchar(45) DEFAULT NULL,
  `orderNumber` int NOT NULL,
  PRIMARY KEY (`orderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `canceledorders`
--

LOCK TABLES `canceledorders` WRITE;
/*!40000 ALTER TABLE `canceledorders` DISABLE KEYS */;
INSERT INTO `canceledorders` VALUES ('5','bdika','lo','guide',0,0,'limhok',NULL,'af','paam',NULL,1111),('5','a@b.c','123123123','group',100,400,'jurasic','2021-01-02 12:00:00','1','123456','0',1112),('5','a@b.c','123123123','group',100,400,'jurasic','2021-01-02 12:00:00','1','123456','0',1113),('4','bar@kaz.com','0541234567','regular',9000,7650,'jurasic','2020-12-27 08:00:00','null','315818987','0',1114),('2','bar@kaz.com','0541234567','regular',200,170,'disney','2020-12-29 08:00:00','null','315818987','0',1115);
/*!40000 ALTER TABLE `canceledorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creditcard` (
  `cardNumber` varchar(45) NOT NULL,
  `cardHolderName` varchar(45) NOT NULL,
  `expiredDate` varchar(45) NOT NULL,
  `cvc` int NOT NULL,
  `orderID` varchar(45) NOT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard`
--

LOCK TABLES `creditcard` WRITE;
/*!40000 ALTER TABLE `creditcard` DISABLE KEYS */;
INSERT INTO `creditcard` VALUES ('7777444499993334','bar katz','6/2021',123,'1112'),('2222333344445555','bd gh','1/2021',123,'5573');
/*!40000 ALTER TABLE `creditcard` ENABLE KEYS */;
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
-- Table structure for table `discounts`
--

DROP TABLE IF EXISTS `discounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discounts` (
  `parkName` varchar(45) DEFAULT NULL,
  `from` datetime DEFAULT NULL,
  `to` datetime DEFAULT NULL,
  `requestID` varchar(45) DEFAULT NULL,
  `discountscol` varchar(45) NOT NULL,
  PRIMARY KEY (`discountscol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discounts`
--

LOCK TABLES `discounts` WRITE;
/*!40000 ALTER TABLE `discounts` DISABLE KEYS */;
INSERT INTO `discounts` VALUES ('disney','2020-12-30 00:00:00','2020-12-31 00:00:00','1','disney');
/*!40000 ALTER TABLE `discounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `employeeNumber` int NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  `loggedin` int DEFAULT '0',
  PRIMARY KEY (`employeeNumber`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('bark','12345678','bar','katz',1,'bar@Nature.com','ParkManager','jurasic',0),('rinat','12345678','rinat','st',12,'r@r.com','ParkManager','disney',0),('hodaya','12345678','Hodaya','Mekonen',123,'hey@hey.com','DepartmentManager','disney',0),('shawn','12345678','Shawn','Mendes',1234,'hey@hey.com','ParkEmployee','disney',0),('harry','12345678','Harry','Styles',12345,'hey@hey.com','ServiceRepresentative','universal',0);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enteryandexit`
--

DROP TABLE IF EXISTS `enteryandexit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enteryandexit` (
  `orderNumber` int NOT NULL,
  `timeEnter` datetime DEFAULT NULL,
  `timeExit` datetime DEFAULT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  `orderType` varchar(45) DEFAULT NULL,
  `amountArrived` int DEFAULT NULL,
  PRIMARY KEY (`orderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enteryandexit`
--

LOCK TABLES `enteryandexit` WRITE;
/*!40000 ALTER TABLE `enteryandexit` DISABLE KEYS */;
INSERT INTO `enteryandexit` VALUES (12345,'2021-01-02 12:00:00','2021-01-02 13:00:00','disney','member',7),(12346,'2021-01-02 12:00:00','2021-01-02 14:00:00','disney','member',5),(12359,'2021-01-02 12:00:00','2021-01-02 15:00:00','disney','member',8),(12387,'2021-01-02 12:00:00','2021-01-02 16:00:00','disney','member',10);
/*!40000 ALTER TABLE `enteryandexit` ENABLE KEYS */;
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
  `familtAmount` int DEFAULT NULL,
  PRIMARY KEY (`memberNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (111111111,'rinat','students','1','972867648374','rinatbeeretz@haenglish.com','paypal','group',3),(222222222,'rinat','gabai','2','999777888','rinatGab@test.com','cash','member',1);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `visitorsNumber` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `orderType` varchar(45) DEFAULT NULL,
  `beforeDiscountPrice` double DEFAULT NULL,
  `afterDiscountPrice` double DEFAULT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  `arrivedTime` datetime DEFAULT NULL,
  `memberID` varchar(45) DEFAULT NULL,
  `ID` varchar(45) DEFAULT NULL,
  `amountArrived` varchar(45) DEFAULT '0',
  `orderNumber` int NOT NULL,
  PRIMARY KEY (`orderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('5','a@b.c','123123123','group',400,100,'jurasic','2021-01-02 12:00:00','1','123456','0',1111),('4','bar@kaz.com','0541234567','regular',7650,9000,'jurasic','2020-12-27 08:00:00','null','315818987','0',1119),('2','bar@kaz.com','0541234567','regular',7650,9000,'jurasic','2020-12-28 08:00:00','null','315818987','0',1120),('4','bar@kaz.com','0541234567','regular',7650,9000,'jurasic','2020-12-30 08:00:00','null','315818987','1',1124),('2','bar@kaz.com','0541234567','regular',170,200,'disney','2020-12-29 08:00:00','null','315818987','1',1125),('2','bar@kaz.com','0541234567','regular',170,200,'disney','2020-12-28 08:00:00','null','315818987','1',1126),('2','bar@kaz.com','0541234567','regular',170,200,'universal','2020-12-28 08:00:00','null','315818987','2',1127),('2','bar@kaz.com','0541234567','regular',170,200,'universal','2020-12-28 16:00:00','null','315818987','1',1128),('10','ksdk@asdfk.com','5555555555','regular',200,100,'disney','2020-01-02 08:00:00','null','123456789','0',1129),('55','ino@df.com','1234567890','regular',900,800,'disney','2021-01-05 08:00:00','null','787878787','0',1130),('5','sss@ss.dd','0546666666','group',500,375,'universal','2021-01-02 16:00:00','1','111111111','0',1132),('55','slslsl@dldl.dd','0555555556','regular',5500,4675,'jurasic','2021-01-15 16:00:00','null','123123663','0',1133);
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
  `maxVisitorAmount` varchar(45) DEFAULT NULL,
  `maxOrderVisitorsAmount` varchar(45) DEFAULT NULL,
  `entryPrice` varchar(45) DEFAULT '100',
  `mangerDiscount` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`parkName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park`
--

LOCK TABLES `park` WRITE;
/*!40000 ALTER TABLE `park` DISABLE KEYS */;
INSERT INTO `park` VALUES ('disney','0','400','10','100','1'),('jurasic','0','100','90','100','1'),('universal','0','250','150','100','1');
/*!40000 ALTER TABLE `park` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pendingmanagerrequests`
--

DROP TABLE IF EXISTS `pendingmanagerrequests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pendingmanagerrequests` (
  `employeeID` int NOT NULL,
  `requesttype` varchar(45) NOT NULL,
  `maxCapacity` int DEFAULT NULL,
  `ordersCapacity` int DEFAULT NULL,
  `discount` varchar(45) DEFAULT NULL,
  `fromdate` datetime DEFAULT NULL,
  `todate` datetime DEFAULT NULL,
  `parkname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`employeeID`,`requesttype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pendingmanagerrequests`
--

LOCK TABLES `pendingmanagerrequests` WRITE;
/*!40000 ALTER TABLE `pendingmanagerrequests` DISABLE KEYS */;
INSERT INTO `pendingmanagerrequests` VALUES (12,'discount',500,10,'0.5','2021-02-02 00:00:00','2021-02-06 00:00:00','disney');
/*!40000 ALTER TABLE `pendingmanagerrequests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pendingwaitlist`
--

DROP TABLE IF EXISTS `pendingwaitlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pendingwaitlist` (
  `orderNumber` varchar(45) NOT NULL,
  `timeLimit` datetime DEFAULT NULL,
  PRIMARY KEY (`orderNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pendingwaitlist`
--

LOCK TABLES `pendingwaitlist` WRITE;
/*!40000 ALTER TABLE `pendingwaitlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `pendingwaitlist` ENABLE KEYS */;
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
-- Table structure for table `waitinglist`
--

DROP TABLE IF EXISTS `waitinglist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `waitinglist` (
  `visitorsNumber` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `orderType` varchar(45) DEFAULT NULL,
  `beforeDiscountPrice` double DEFAULT NULL,
  `afterDiscountPrice` double DEFAULT NULL,
  `parkName` varchar(45) DEFAULT NULL,
  `arrivedTime` datetime DEFAULT NULL,
  `memberID` varchar(45) DEFAULT NULL,
  `ID` varchar(45) DEFAULT NULL,
  `amountArrived` varchar(45) DEFAULT '0',
  `waitlistID` int NOT NULL,
  PRIMARY KEY (`waitlistID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `waitinglist`
--

LOCK TABLES `waitinglist` WRITE;
/*!40000 ALTER TABLE `waitinglist` DISABLE KEYS */;
/*!40000 ALTER TABLE `waitinglist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-02 16:52:25
