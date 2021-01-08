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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-08 15:07:14
