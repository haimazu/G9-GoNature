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
INSERT INTO `orders` VALUES ('8','kim@gmail.com','0543456543','member',800,581.4,'jurasic','2021-01-09 08:00:00','1113','111222333','0',1112),('4','john@gmail.com','0565434543','regular',400,323,'jurasic','2021-01-09 12:00:00','null','444555666','0',1113),('10','Maluma@yahoo.com','0503456545','group',1000,712.5,'jurasic','2021-01-09 16:00:00','1137','324321235','0',1114),('2','Kourtney@gmail.com','0543456541','member',200,129.2,'jurasic','2021-01-10 08:00:00','1114','111222334','0',1115),('15','Jason@gmail.com','0503456546','group',1500,1068.75,'jurasic','2021-01-10 12:00:00','1138','324321236','0',1116),('3','johny@gmail.com','0534323454','regular',300,242.25,'jurasic','2021-01-10 16:00:00','null','234123234','0',1117),('10','Liam@gmail.com','0503456547','group',1000,712.5,'jurasic','2021-01-11 08:00:00','1139','324321237','0',1118),('5','Khloe@gmail.com','0543456542','member',500,371.45,'jurasic','2021-01-11 12:00:00','1115','111222335','0',1119),('1','jack@gmail.com','0546765432','regular',100,80.75,'jurasic','2021-01-11 16:00:00','null','897787765','0',1120),('5','nur@gmail.com','0565432345','regular',500,403.75,'jurasic','2021-01-12 08:00:00','null','777666554','0',1122),('3','Robert@gmail.com','0543456544','member',300,209.95,'jurasic','2021-01-12 12:00:00','1116','111222336','0',1123),('12','Cameron@yahoo.com','0503456541','group',1200,855,'jurasic','2021-01-12 16:00:00','1141','324321239','0',1124),('7','micha@walla.com','0564345432','regular',700,565.25,'jurasic','2021-01-13 08:00:00','null','345434565','0',1125),('15','LiamN@gmail.com','0503456548','group',1500,1068.75,'jurasic','2021-01-14 12:00:00','1140','324321238','0',1126),('3','Kylie@gmail.com','0543456545','member',300,209.95,'jurasic','2021-01-13 12:00:00','1117','111222337','0',1127),('8','Maluma@yahoo.com','0503456545','group',800,570,'jurasic','2021-01-13 16:00:00','1137','324321235','0',1128),('2','Kendel@gmail.com','0543456546','member',200,145.35,'jurasic','2021-01-14 08:00:00','1118','111222338','0',1129),('2','shir@gmail.com','0564321231','regular',200,161.5,'jurasic','2021-01-14 16:00:00','null','776655664','0',1130),('1','Kris@gmail.com','0543456547','member',100,64.6,'jurasic','2021-01-15 08:00:00','1119','111222339','0',1131),('7','Liam@gmail.com','0503456547','group',700,498.75,'jurasic','2021-01-15 12:00:00','1139','324321237','0',1132),('2','mil@gmaill.com','0532343212','regular',200,161.5,'jurasic','2021-01-15 16:00:00','null','987876765','0',1133),('14','LiamN@gmail.com','0503456548','group',1400,997.5,'jurasic','2021-01-16 08:00:00','1140','324321238','0',1135),('3','loi@gmail.com','0543456787','regular',300,242.25,'jurasic','2021-01-16 12:00:00','null','123432124','0',1137),('4','Scott@gmail.com','0543456548','member',400,290.7,'jurasic','2021-01-16 16:00:00','1120','111221333','0',1139),('8','gal@walla.com','0567654567','regular',800,646,'jurasic','2021-01-17 08:00:00','null','454323432','0',1140),('15','Jason@gmail.com','0503456546','group',1500,1068.75,'jurasic','2021-01-17 12:00:00','1138','324321236','0',1141),('5','gili@walla.com','0576789876','regular',500,403.75,'jurasic','2021-01-09 12:00:00','null','564675786','0',1142),('4','Oprah@yahoo.com','0523456543','member',400,290.7,'jurasic','2021-01-17 16:00:00','1121','222333444','0',1143),('8','Brad@yahoo.com','0523456541','member',800,532.95,'jurasic','2021-01-17 12:00:00','1122','222333441','0',1145),('7','gilli@wall.com','0576789876','regular',700,565.25,'jurasic','2021-01-17 12:00:00','null','564675786','0',1146),('15','Cameron@yahoo.com','0503456541','group',1500,1068.75,'jurasic','2021-01-17 08:00:00','1141','324321239','0',1147),('15','LiamN@gmail.com','0503456548','group',1500,1068.75,'jurasic','2021-01-17 08:00:00','1140','324321238','0',1148),('10','Bradley@yahoo.com','0523456542','member',1000,791.35,'jurasic','2021-01-18 08:00:00','1123','222333442','0',1149),('15','Jason@gmail.com','0503456546','group',1500,1068.75,'jurasic','2021-01-18 08:00:00','1138','324321236','0',1150),('5','kim@gmail.com','0543456543','member',500,339.15,'jurasic','2021-01-18 08:00:00','1113','111222333','0',1151),('7','Justin@yahoo.com','0523456544','member',700,532.95,'jurasic','2021-01-18 08:00:00','1124','222333443','0',1152),('4','Hay@yahoo.com','0523459945','regular',400,323,'jurasic','2021-01-18 12:00:00','null','123432123','0',1154),('3','hila@gmail.com','0564345676','regular',300,242.25,'jurasic','2021-01-18 16:00:00','null','545434543','0',1155),('15','Maluma@yahoo.com','0503456545','group',1500,1068.75,'jurasic','2021-01-19 12:00:00','1137','324321235','0',1156),('15','Jason@gmail.com','0503456546','group',1500,1068.75,'jurasic','2021-01-19 12:00:00','1138','324321236','0',1157),('10','Kris@gmail.com','0543456547','member',1000,791.35,'jurasic','2021-01-19 12:00:00','1119','111222339','0',1159),('3','roni@yahoo.com','0528888544','regular',300,242.25,'jurasic','2021-01-19 16:00:00','null','456354635','0',1160),('4','milan@walla.com','0564345676','regular',400,323,'jurasic','2021-01-19 08:00:00','null','345432344','0',1161),('10','Selena@yahoo.com','0523456547','member',1000,791.35,'jurasic','2021-01-20 08:00:00','1127','234543345','0',1162),('8','limi@gmail.com','0576543234','regular',800,646,'jurasic','2021-01-20 08:00:00','null','012343212','0',1163),('12','Cameron@yahoo.com','0503456541','group',1200,855,'jurasic','2021-01-20 12:00:00','1141','324321239','0',1164),('4','gilat@gmail.com','0587656789','regular',400,323,'jurasic','2021-01-20 16:00:00','null','023454327','0',1165),('10','Shawn@yahoo.com','0523456548','member',1000,742.9,'jurasic','2021-01-20 08:00:00','1128','234543344','0',1167),('15','Liam@gmail.com','0503456547','group',1500,1068.75,'jurasic','2021-01-21 08:00:00','1139','324321237','0',1168),('3','Denzel@yahoo.com','0533456542','member',300,193.79999999999998,'jurasic','2021-01-21 12:00:00','1130','234543342','0',1169),('1','hola@walla.com','0586543456','regular',100,80.75,'jurasic','2021-01-21 16:00:00','null','098787654','0',1170),('3','eyal@gmail.com','0589876787','regular',300,242.25,'jurasic','2021-01-22 08:00:00','null','034321234','0',1172),('5','Rihanna@gmail.com','0533456544','member',500,339.15,'jurasic','2021-01-22 12:00:00','1131','234543341','0',1173),('8','Cameron@yahoo.com','0503456541','group',800,570,'jurasic','2021-01-22 16:00:00','1141','324321239','0',1174),('15','Jason@gmail.com','0503456546','group',1500,1068.75,'jurasic','2021-01-23 08:00:00','1138','324321236','0',1175),('3','Ciara@gmail.com','0533456545','member',300,193.79999999999998,'jurasic','2021-01-23 12:00:00','1132','234543346','0',1176),('2','rona@walla.com','0598767876','regular',200,161.5,'jurasic','2021-01-23 16:00:00','null','333223221','0',1177),('1','George@gmail.com','0533456546','member',100,64.6,'jurasic','2021-01-24 08:00:00','1133','234543347','0',1178),('8','Angelina@yahoo.com','0503457441','member',800,516.8,'jurasic','2021-01-24 12:00:00','1142','325321239','0',1179),('4','lior@gmail.com','0587678987','regular',400,323,'jurasic','2021-01-24 16:00:00','null','987876678','0',1181),('4','Matt@yahoo.com','0533456547','member',400,258.4,'jurasic','2021-01-25 08:00:00','1134','234543348','0',1182),('9','Angelina@yahoo.com','0503457441','member',900,597.55,'jurasic','2021-01-25 12:00:00','1142','325321239','0',1183),('1','lidan@walla.com','0535645345','regular',100,80.75,'jurasic','2021-01-25 16:00:00','null','098876565','0',1184),('6','lori@gmail.com','0598964324','regular',600,484.5,'jurasic','2021-01-26 08:00:00','null','233322212','0',1185),('3','Nicole@yahoo.com','0533456548','member',300,193.79999999999998,'jurasic','2021-01-26 12:00:00','1135','234543349','0',1186),('12','Liam@gmail.com','0503456547','group',1200,855,'jurasic','2021-01-26 16:00:00','1139','324321237','0',1188),('4','flori@walla.com','0588765456','regular',400,323,'jurasic','2021-01-27 08:00:00','null','012367654','0',1189),('1','Bruno@yahoo.com','0503456544','member',100,64.6,'jurasic','2021-01-27 12:00:00','1136','324321234','0',1190),('13','Maluma@yahoo.com','0503456545','group',1300,926.25,'jurasic','2021-01-27 16:00:00','1137','324321235','0',1192),('1','liroi@gmail.com','0564345432','regular',100,80.75,'jurasic','2021-01-28 16:00:00','null','335665447','0',1194),('8','LiamN@gmail.com','0503456548','group',800,570,'jurasic','2021-01-28 08:00:00','1140','324321238','0',1195),('4','kim@gmail.com','0543456543','member',400,258.4,'jurasic','2021-01-28 12:00:00','1113','111222333','0',1196),('2','Khloe@gmail.com','0543456542','member',200,129.2,'jurasic','2021-01-29 08:00:00','1115','111222335','0',1197),('5','aliyah@gmail.com','0534323432','regular',500,403.75,'jurasic','2021-01-29 12:00:00','null','454433323','0',1198),('12','Maluma@yahoo.com','0503456545','group',1200,855,'jurasic','2021-01-29 16:00:00','1137','324321235','0',1199),('7','hello@gmail.com','0584678765','regular',700,565.25,'jurasic','2021-01-30 08:00:00','null','243423423','0',1201),('3','Oprah@yahoo.com','0523456543','member',300,209.95,'jurasic','2021-01-30 12:00:00','1121','222333444','0',1202),('14','Cameron@yahoo.com','0503456541','group',1400,997.5,'jurasic','2021-01-30 16:00:00','1141','324321239','0',1203),('2','miran@walla.com','0565434534','regular',200,161.5,'jurasic','2021-01-31 08:00:00','null','220330440','0',1204),('6','Bradley@yahoo.com','0523456542','member',600,468.35,'jurasic','2021-01-31 12:00:00','1123','222333442','0',1205),('15','LiamN@gmail.com','0503456548','group',1500,1068.75,'jurasic','2021-01-31 16:00:00','1140','324321238','0',1206);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-09  1:31:51
