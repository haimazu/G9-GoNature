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
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('bark','12345678','bar','katz',1,'bar@Nature.com','ParkManager','jurasic',0),('rinat','12345678','rinat','st',12,'r@r.com','ParkManager','disney',0),('hodaya','12345678','Hodaya','Mekonen',123,'hey@hey.com','DepartmentManager','disney',0),('shawn','12345678','Shawn','Mendes',1234,'hey@hey.com','ParkEmployee','disney',0),('harry','12345678','Harry','Styles',12345,'hey@hey.com','ServiceRepresentative','universal',0),('jennifer','12345678','Jennifer','Lopez',12346,'hey@hey.com','ServiceRepresentative','disney',0),('haim','12345678','Haim','Azulay',12347,'hey@hey.com','ParkManager','universal',0),('nastya','12345678','Nastya','Kokin',12348,'hey@hey.com','ParkEmployee','universal',0),('beyonce','12345678','Beyonce','Knowles',12349,'hey@hey.com','ParkEmployee','jurasic',0),('roi','12345678','Roi','Amar',12350,'hey@hey.com','ServiceRepresentative','jurasic',0);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-06 20:15:08
