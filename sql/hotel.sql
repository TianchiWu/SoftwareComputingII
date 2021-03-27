

-- MySQL dump 10.13  Distrib 5.7.19, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: Hotel
-- ------------------------------------------------------

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
-- Table structure for table `Coupon`
--

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT '-1',
  `coupon_type` int(11) NOT NULL,
  `coupon_name` varchar(255) NOT NULL,
  `target_money` int(11) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `start_time` varchar(255) DEFAULT NULL,
  `end_time` varchar(255) DEFAULT NULL,
  `discount_money` int(11) DEFAULT NULL,
  `property` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Coupon`
--

--
-- Table structure for table `Hotel`
--

DROP TABLE IF EXISTS `Hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_name` varchar(255) NOT NULL,
  `hotel_description` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `hotel_star` varchar(255) DEFAULT NULL,
  `phone_num` varchar(255) DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `information` varchar(10000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel`
--

--
-- Table structure for table `OrderList`
--

DROP TABLE IF EXISTS `OrderList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OrderList` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `hotel_name` varchar(255) DEFAULT NULL,
  `checkin_date` varchar(255) DEFAULT NULL,
  `checkout_date` varchar(255) DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `room_num` int(255) DEFAULT NULL,
  `people_num` int(255) DEFAULT NULL,
  `have_child` tinyint,
  `create_date` varchar(255) DEFAULT NULL,
  `annul_date` varchar(255) DEFAULT NULL,
  `price` decimal(65,1) DEFAULT NULL,
  `client_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `order_state` varchar(255) DEFAULT NULL,
  `birthday` varchar(10) DEFAULT NULL,
  `room_id` int(255) DEFAULT NULL,
  `last` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderList`
--

/*!40000 ALTER TABLE `OrderList` DISABLE KEYS */;
/*!40000 ALTER TABLE `OrderList` ENABLE KEYS */;


--
-- Table structure for table `CreditList`
--
DROP TABLE IF EXISTS `CreditList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CreditList`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) DEFAULT NULL,
    `cur_date` varchar(255) DEFAULT NULL,
    `order_id` int(11) DEFAULT NULL,
    `Action` varchar(255) DEFAULT NULL,
    `credit_before` double(255,2) DEFAULT NULL,
    `credit_after` double(255,2) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CreditList`
--

/*!40000 ALTER TABLE `CreditList` DISABLE KEYS */;
/*!40000 ALTER TABLE `CreditList` ENABLE KEYS */;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `cur_num` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `room_type` varchar(50) DEFAULT NULL,
  `breakfast` char(255) DEFAULT NULL,
  `facility` char(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

--
-- Table structure for table `Enterprise`
--

DROP TABLE IF EXISTS `Enterprise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Enterprise` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
BEGIN;
/*!40000 ALTER TABLE `Enterprise` DISABLE KEYS */;
INSERT INTO `Enterprise` VALUES (1,'慕测有限公司'),(2,'华为有限公司');
/*!40000 ALTER TABLE `Enterprise` ENABLE KEYS */;
COMMIT;

--
-- Dumping data for table `Enterprise`
--

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `credit` double(255,2) DEFAULT NULL,
  `usertype` varchar(255) DEFAULT NULL,
  `birthday` varchar(10) DEFAULT NULL,
  `membertype` varchar(255) DEFAULT NULL,
  `memberlevel` varchar(255) DEFAULT NULL,
  `enterprisename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--
DROP TABLE  IF EXISTS `RoomList`;
CREATE TABLE `RoomList`(
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `hotel_id` int(11) DEFAULT NULL,
 `room_type` char(255) DEFAULT NULL,
 `room_state` char(255) DEFAULT NULL,
 `breakfast` char(255) DEFAULT NULL,
 `facility` char(255) DEFAULT NULL,
 `price` double DEFAULT NULL,
 `room_id` int(11) DEFAULT NULL,
 `order_id` int(11) DEFAULT NULL,
 PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Comment`;
CREATE TABLE `Comment`(
`id` int(11) NOT NULL AUTO_INCREMENT,
`user_email` char(255) DEFAULT NULL,
`hotel_id` int(11) DEFAULT NULL,
`rate` double DEFAULT NULL,
`comment_time` char(255) DEFAULT NULL,
`text` varchar(10000) DEFAULT NULL,
PRIMARY KEY(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

BEGIN;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (4,'111@qq.com','123456','测试一号','12345678919',100,'Client','1999-02-21','普通会员','VIP1',NULL),(5,'123@qq.com','123456','测试二号','12345678911',100,'Client','1999-06-07','企业会员',NULL,'华为有限公司'),(6,'333@qq.com','123456',NULL,NULL,NULL,'HotelManager',NULL,NULL,NULL,NULL),(7,'444@qq.com',123456,NULL,NULL,NULL,'Manager',NULL,NULL,NULL,NULL),(8,'345@qq.com',123456,NULL,NULL,NULL,'Sale',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
COMMIT;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-12 10:08:42
SET FOREIGN_KEY_CHECKS = 1;