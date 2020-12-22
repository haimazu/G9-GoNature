-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: g9_gonature
-- ------------------------------------------------------
-- Server version	8.0.22

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
INSERT INTO `creditcard` VALUES ('2222333344445555','bd gh','1/2021',123,'5573');
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
  `loggedin` int DEFAULT '0',
  PRIMARY KEY (`employeeNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('bark','12345678','bar','katz',1,'bar@Nature.com','ParkManager','jurasic',0),('hodaya','12345678','Hodaya','Mekonen',123,'hey@hey.com','DepartmentManager','disney',0),('shawn','12345678','Shawn','Mendes',1234,'hey@hey.com','ParkEmployee','disney',0),('harry','12345678','Harry','Styles',12345,'hey@hey.com','ServiceRepresentative','universal',0);
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
  `familtAmount` int DEFAULT NULL,
  PRIMARY KEY (`memberNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (111111111,'rinat','students','1','972867648374','rinatbeeretz@haenglish.com','paypal','family',3),(222222222,'rinat','gabai','2','999777888','rinatGab@test.com','cash','single',1);
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
INSERT INTO `orders` VALUES ('10','a@b.c','123123123','group',400,100,'jurasic','2021-01-02 12:00:00','1','123456','0',1111),('5','d@e.f','12312313','group',100,80,'disney','2020-12-19 12:00:00','2','987656432','0',2222),('2','bar@bark.ci','0549991234','member',200,170,'disney','2020-12-20 08:00:00','null','315818987','0',5465),('2','bar@bar.ci','0549991234','member',200,170,'disney','2020-12-22 08:00:00','5','315818567','0',5555),('3','bar@bark.ci','0549991234','member',300,255,'jurasic','2020-12-21 08:00:00','null','222111333','0',5556),('5','bar@bark.ci','0549991234','member',500,425,'disney','2020-12-24 08:00:00','null','111222333','0',5557),('50','bar@bark.ci','0549991234','member',5000,4250,'universal','2020-12-28 12:00:00','null','333222111','0',5558),('50','a@v.com','0549991234','member',5000,4250,'disney','2020-12-20 08:00:00','null','111111111','0',5559),('5','bar@bark.ci','0549991234','member',500,425,'disney','2020-12-20 08:00:00','null','315818987','0',5560),('2','bar@bark.ci','0549991234','group',200,170,'jurasic','2020-12-20 16:00:00','null','888888888','0',5561),('2','bar@bark.ci','0549991234','group',400,200,'jurasic','2020-12-29 08:00:00','null','444444444','0',5562),('20','bar@bark.ci','0549991234','group',1700,2000,'jurasic','2020-12-29 12:00:00','null','555555555','0',5563),('2','bar@bark.ci','0549991234','group',300,200,'jurasic','2021-01-02 12:00:00','null','111111111','0',5564),('2','bar@bark.ci','0549991234','group',350,200,'disney','2020-12-28 08:00:00','null','315818987','0',5565),('2','bar@bark.ci','0549991234','group',250,200,'disney','2021-03-17 16:00:00','null','315818987','0',5566),('50','bar@kaz.com','0541234567','regular',4250,300,'disney','2020-12-23 08:00:00','null','null','0',5567),('1','bar@kaz.com','0541234567','regular',190,100,'disney','2020-12-23 08:00:00','null','null','0',5568),('40','bar@kaz.com','0541234567','regular',4000,3400,'disney','2020-12-23 08:00:00','null','null','0',5569),('2','bar@kaz.com','0541234567','regular',265,200,'disney','2020-12-24 12:00:00','null','315818987','0',5570),('2','bar@kaz.com','0541234567','regular',170,200,'disney','2020-12-28 08:00:00','null','315818987','0',5571),('2','bar@kaz.com','0541234567','regular',170,200,'disney','2020-12-28 08:00:00','null','315818987','0',5572),('2','bar@kaz.com','0541234567','regular',170,200,'disney','2020-12-23 08:00:00','null','315818987','0',5573);
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
INSERT INTO `park` VALUES ('disney','0','150','100','100','1'),('jurasic','0','100','90','100','1'),('universal','0','250','150','100','1');
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
INSERT INTO `waitinglist` VALUES ('5','t@t.t','0546290290','group',500,400,'jurasic','2020-12-17 10:00:00','1','333555444','0',1101);
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

-- Dump completed on 2020-12-22 21:03:09
