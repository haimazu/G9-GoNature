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
INSERT INTO `creditcard` VALUES ('1234234534564560','Kim Kardashian','4/2021',234,'111222333'),('1234234534564561','Kourtney Kardashian','3/2021',235,'111222334'),('1234234534564562','Khloe Kardashian','1/2021',236,'111222335'),('1234234534564563','Robert Kardashian','6/2021',345,'111222336'),('1234234534564564','Kylie Jenner','3/2021',346,'111222337'),('1234234534564565','Kendel Jenner','8/2021',678,'111222338');
/*!40000 ALTER TABLE `creditcard` ENABLE KEYS */;
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
  `requestID` varchar(45) NOT NULL,
  `discountscol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`requestID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discounts`
--

LOCK TABLES `discounts` WRITE;
/*!40000 ALTER TABLE `discounts` DISABLE KEYS */;
INSERT INTO `discounts` VALUES ('jurasic','2021-01-08 00:00:00','2021-01-16 00:00:00','1112','0.95'),('disney','2021-01-08 00:00:00','2021-01-16 00:00:00','1113','0.98'),('universal','2021-01-08 00:00:00','2021-01-16 00:00:00','1114','0.99');
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
INSERT INTO `employee` VALUES ('shawn','12345678','Shawn','Mendes',12341,'hey@hey.com','ParkEmployee','disney',0),('rinat','12345678','rinat','st',12343,'r@r.com','ParkManager','disney',0),('bark','12345678','bar','katz',12344,'bar@Nature.com','ParkManager','jurasic',0),('hodaya','12345678','Hodaya','Mekonen',12344,'hey@hey.com','DepartmentManager','disney',0),('harry','12345678','Harry','Styles',12345,'hey@hey.com','ServiceRepresentative','universal',0),('jennifer','12345678','Jennifer','Lopez',12346,'hey@hey.com','ServiceRepresentative','disney',0),('haim','12345678','Haim','Azulay',12347,'hey@hey.com','ParkManager','universal',0),('nastya','12345678','Nastya','Kokin',12348,'hey@hey.com','ParkEmployee','universal',0),('beyonce','12345678','Beyonce','Knowles',12349,'hey@hey.com','ParkEmployee','jurasic',0),('roia','12345678','Roi','Amar',12350,'hey@hey.com','ServiceRepresentative','jurasic',0);
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
INSERT INTO `member` VALUES (111222333,'Kim','Kardashian','1113','0543456543','kim@gmail.com','yes','member',4),(111222334,'Kourtney','Kardashian','1114','0543456541','Kourtney@gmail.com','yes','member',3),(111222335,'Khloe ','Kardashian','1115','0543456542','Khloe@gmail.com','yes','member',2),(111222336,'Robert ','Kardashian','1116','0543456544','Robert@gmail.com','yes','member',2),(111222337,'Kylie ','Jenner','1117','0543456545','Kylie@gmail.com','yes','member',2),(111222338,'Kendel ','Jenner','1118','0543456546','Kendel@gmail.com','yes','member',1),(111222339,'Kris','Jenner','1119','0543456547','Kris@gmail.com','yes','member',1),(111221333,'Scott','Disick','1120','0543456548','Scott@gmail.com','yes','member',2),(222333444,'Oprah','Winfrey','1121','0523456543','Oprah@yahoo.com','yes','member',2),(222333441,'Brad','Pitt','1122','0523456541','Brad@yahoo.com','yes','member',7),(222333442,'Bradley','Cooper','1123','0523456542','Bradley@yahoo.com','yes','member',1),(222333443,'Justin','Bieber','1124','0523456544','Justin@yahoo.com','yes','member',2),(222333445,'Hailey','Baldwin','1125','0523456545','Hailey@yahoo.com','yes','member',1),(222333446,'Halle','Berry','1126','0523456546','Halle@yahoo.com','yes','member',5),(234543345,'Selena','Gomez','1127','0523456547','Selena@yahoo.com','yes','member',1),(234543344,'Shawn','Carter','1128','0523456548','Shawn@yahoo.com','yes','member',4),(234543343,'Kanye','West','1129','0533456541','Kanye@yahoo.com','yes','member',2),(234543342,'Denzel','Washington','1130','0533456542','Denzel@yahoo.com','yes','member',3),(234543341,'Rihanna','Fenty','1131','0533456544','Rihanna@gmail.com','yes','member',4),(234543346,'Ciara','win','1132','0533456545','Ciara@gmail.com','yes','member',3),(234543347,'George','Clooney','1133','0533456546','George@gmail.com','yes','member',1),(234543348,'Matt','Damon','1134','0533456547','Matt@yahoo.com','yes','member',4),(234543349,'Nicole','Kidman','1135','0533456548','Nicole@yahoo.com','yes','member',3),(324321234,'Bruno','Mars','1136','0503456544','Bruno@yahoo.com','yes','member',1),(324321235,'Maluma','Arias','1137','0503456545','Maluma@yahoo.com','yes','group',0),(324321236,'Jason','Statham','1138','0503456546','Jason@gmail.com','yes','group',0),(324321237,'Liam','Payne','1139','0503456547','Liam@gmail.com','yes','group',0),(324321238,'Liam','Neeson','1140','0503456548','LiamN@gmail.com','yes','group',0),(324321239,'Cameron','Diaz','1141','0503456541','Cameron@yahoo.com','yes','group',0),(325321239,'Angelina','Jolie','1142','0503457441','Angelina@yahoo.com','no','member',8);
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
INSERT INTO `park` VALUES ('disney','0','50','20','100','1.0'),('jurasic','0','50','40','100','0.95'),('universal','0','50','30','100','1.0');
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
INSERT INTO `pendingmanagerrequests` VALUES (12344,'discount',60,20,'0.94','2021-01-17 00:00:00','2021-01-23 00:00:00','jurasic'),(12344,'max_c',60,30,'','2001-01-00 00:00:00','2001-01-00 00:00:00','jurasic'),(12344,'max_o',60,20,'','2001-01-00 00:00:00','2001-01-00 00:00:00','jurasic');
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

-- Dump completed on 2021-01-08 18:16:51
