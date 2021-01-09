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
INSERT INTO `orders` VALUES ('6','kim@gmail.com','0543456543','member',600,419.9,'jurasic','2020-12-10 08:00:00','1113','111222333','0',1210),('3','john@gmail.com','0565434543','regular',300,242.25,'jurasic','2020-12-10 12:00:00','null','120120120','0',1211),('12','Maluma@yahoo.com','0503456545','group',1200,855,'jurasic','2020-12-10 16:00:00','1137','324321235','0',1212),('1','johny@gmail.com','0534323454','regular',100,80.75,'jurasic','2020-12-11 08:00:00','null','121121121','0',1213),('14','Jason@gmail.com','0503456546','group',1400,997.5,'jurasic','2020-12-11 12:00:00','1138','324321236','0',1214),('2','Kourtney@gmail.com','0543456541','member',200,129.2,'jurasic','2020-12-11 16:00:00','1114','111222334','0',1215),('3','jack@gmail.com','0546765432','regular',300,242.25,'jurasic','2020-12-12 08:00:00','null','897787765','0',1216),('3','Khloe@gmail.com','0543456542','member',300,209.95,'jurasic','2020-12-12 12:00:00','1115','111222335','0',1217),('12','Liam@gmail.com','0503456547','group',1200,855,'jurasic','2020-12-12 16:00:00','1139','324321237','0',1218),('4','nur@gmail.com','0565432345','regular',400,323,'jurasic','2020-12-13 08:00:00','null','777666554','0',1219),('2','Robert@gmail.com','0543456544','member',200,129.2,'jurasic','2020-12-13 12:00:00','1116','111222336','0',1220),('15','Cameron@yahoo.com','0503456541','group',1500,1068.75,'jurasic','2020-12-13 16:00:00','1141','324321239','0',1221),('7','Kylie@gmail.com','0543456545','member',700,532.95,'jurasic','2020-12-14 08:00:00','1117','111222337','0',1222),('5','micha@walla.com','0564345432','regular',500,403.75,'jurasic','2020-12-14 12:00:00','null','345434565','0',1223),('10','LiamN@gmail.com','0503456548','group',1000,712.5,'jurasic','2020-12-14 16:00:00','1140','324321238','0',1224),('1','shir@gmail.com','0564321231','regular',100,80.75,'jurasic','2020-12-15 08:00:00','null','776655664','0',1225),('6','Kendel@gmail.com','0543456546','member',600,468.35,'jurasic','2020-12-15 12:00:00','1118','111222338','0',1226),('9','Maluma@yahoo.com','0503456545','group',900,641.25,'jurasic','2020-12-15 16:00:00','1137','324321235','0',1227),('2','mil@gmaill.com','0532343212','regular',200,161.5,'jurasic','2020-12-16 08:00:00','null','987876765','0',1228),('1','Kris@gmail.com','0543456547','member',100,64.6,'jurasic','2020-12-16 12:00:00','1119','111222339','0',1229),('5','Liam@gmail.com','0503456547','group',500,356.25,'jurasic','2020-12-16 16:00:00','1139','324321237','0',1230),('1','loi@gmail.com','0543456787','regular',100,80.75,'jurasic','2020-12-17 08:00:00','null','123432124','0',1231),('9','Jason@gmail.com','0503456546','group',900,641.25,'jurasic','2020-12-17 12:00:00','1138','324321236','0',1232),('8','Scott@gmail.com','0543456548','member',800,613.7,'jurasic','2020-12-17 16:00:00','1120','111221333','0',1233),('4','gal@walla.com','0567654567','regular',400,323,'jurasic','2020-12-18 08:00:00','null','454323432','0',1234),('10','LiamN@gmail.com','0503456548','group',1000,712.5,'jurasic','2020-12-18 12:00:00','1140','324321238','0',1235),('3','Kourtney@gmail.com','0543456541','member',300,193.79999999999998,'jurasic','2020-12-18 16:00:00','1114','111222334','0',1236),('4','Kourtney@gmail.com','0543456541','member',400,274.54999999999995,'jurasic','2020-12-19 08:00:00','1114','111222334','0',1237),('1','lori@gmail.com','0598964324','regular',100,80.75,'jurasic','2020-12-19 12:00:00','null','233322212','0',1238),('6','Jason@gmail.com','0503456546','group',600,427.5,'jurasic','2020-12-19 16:00:00','1138','324321236','0',1239),('8','rona@walla.com','0598767876','regular',800,646,'jurasic','2020-12-20 08:00:00','null','333223221','0',1240),('2','Matt@yahoo.com','0533456547','member',200,129.2,'jurasic','2020-12-20 12:00:00','1134','234543348','0',1241),('14','Cameron@yahoo.com','0503456541','group',1400,997.5,'jurasic','2020-12-20 16:00:00','1141','324321239','0',1242),('5','hello@gmail.com','0584678765','regular',500,403.75,'jurasic','2020-12-21 08:00:00','null','243423423','0',1243),('15','Maluma@yahoo.com','0503456545','group',1500,1068.75,'jurasic','2020-12-21 12:00:00','1137','324321235','0',1244),('5','kim@gmail.com','0543456543','member',500,339.15,'jurasic','2020-12-21 16:00:00','1113','111222333','0',1245),('9','milan@walla.com','0564345676','regular',900,726.75,'jurasic','2020-12-22 08:00:00','null','345432344','0',1246),('7','Matt@yahoo.com','0533456547','member',700,500.65,'jurasic','2020-12-22 12:00:00','1134','234543348','0',1247),('15','Jason@gmail.com','0503456546','group',1500,1068.75,'jurasic','2020-12-22 16:00:00','1138','324321236','0',1248),('4','Oprah@yahoo.com','0523456543','member',400,290.7,'jurasic','2020-12-23 08:00:00','1121','222333444','0',1249),('12','Cameron@yahoo.com','0503456541','group',1200,855,'jurasic','2020-12-23 12:00:00','1141','324321239','0',1250),('2','Bradley@yahoo.com','0523456542','member',200,145.35,'jurasic','2020-12-23 16:00:00','1123','222333442','0',1251),('1','rona@walla.com','0598767876','regular',100,80.75,'jurasic','2020-12-24 08:00:00','null','333223221','0',1252),('15','LiamN@gmail.com','0503456548','group',1500,1068.75,'jurasic','2020-12-24 12:00:00','1140','324321238','0',1253),('2','Khloe@gmail.com','0543456542','member',200,129.2,'jurasic','2020-12-24 16:00:00','1115','111222335','0',1254),('6','eyal@gmail.com','0589876787','regular',600,484.5,'jurasic','2020-12-25 08:00:00','null','034321234','0',1255),('5','Rihanna@gmail.com','0533456544','member',500,339.15,'jurasic','2020-12-25 12:00:00','1131','234543341','0',1256),('14','Jason@gmail.com','0503456546','group',1400,997.5,'jurasic','2020-12-25 16:00:00','1138','324321236','0',1257),('6','Angelina@yahoo.com','0503457441','member',600,387.59999999999997,'jurasic','2020-12-26 08:00:00','1142','325321239','0',1258),('8','lori@gmail.com','0598964324','regular',800,646,'jurasic','2020-12-26 12:00:00','null','233322212','0',1259),('11','Cameron@yahoo.com','0503456541','group',1100,783.75,'jurasic','2020-12-26 16:00:00','1141','324321239','0',1260),('9','Hay@yahoo.com','0523459945','regular',900,726.75,'jurasic','2020-12-27 08:00:00','null','123432123','0',1261),('14','Maluma@yahoo.com','0503456545','group',1400,997.5,'jurasic','2020-12-27 12:00:00','1137','324321235','0',1262),('3','Selena@yahoo.com','0523456547','member',300,226.1,'jurasic','2020-12-27 16:00:00','1127','234543345','0',1263),('2','Robert@gmail.com','0543456544','member',200,129.2,'jurasic','2020-12-28 08:00:00','1116','111222336','0',1264),('12','Maluma@yahoo.com','0503456545','group',1200,855,'jurasic','2020-12-28 12:00:00','1137','324321235','0',1265),('4','shir@gmail.com','0564321231','regular',400,323,'jurasic','2020-12-28 16:00:00','null','776655664','0',1266),('1','jack@gmail.com','0546765432','regular',100,80.75,'jurasic','2020-12-29 08:00:00','null','897787765','0',1267),('13','LiamN@gmail.com','0503456548','group',1300,926.25,'jurasic','2020-12-29 12:00:00','1140','324321238','0',1268),('5','Robert@gmail.com','0543456544','member',500,371.45,'jurasic','2020-12-29 16:00:00','1116','111222336','0',1269),('7','gal@walla.com','0567654567','regular',700,565.25,'jurasic','2020-12-30 08:00:00','null','454323432','0',1270),('4','Justin@yahoo.com','0523456544','member',400,290.7,'jurasic','2020-12-30 12:00:00','1124','222333443','0',1271),('11','Cameron@yahoo.com','0503456541','group',1100,783.75,'jurasic','2020-12-30 16:00:00','1141','324321239','0',1272),('6','micha@walla.com','0564345432','regular',600,484.5,'jurasic','2020-12-31 08:00:00','null','345434565','0',1273),('7','Kendel@gmail.com','0543456546','member',700,549.1,'jurasic','2020-12-31 12:00:00','1118','111222338','0',1274),('14','LiamN@gmail.com','0503456548','group',1400,997.5,'jurasic','2020-12-31 16:00:00','1140','324321238','0',1275);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
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

-- Dump completed on 2021-01-09 12:15:57
