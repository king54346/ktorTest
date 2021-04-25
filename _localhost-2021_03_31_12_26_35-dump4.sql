-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: kotlin_user
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auth_user`
--

DROP TABLE IF EXISTS `auth_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auth_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) NOT NULL COMMENT 'username(nick_name)',
  `password` varchar(50) NOT NULL COMMENT 'password=MD5(passwd+salt)',
  `salt` varchar(20) DEFAULT NULL COMMENT 'salt',
  `avatar` varchar(100) DEFAULT NULL COMMENT 'avatar',
  `phone` varchar(20) DEFAULT NULL COMMENT 'phone number(unique)',
  `email` varchar(50) DEFAULT NULL COMMENT 'email(unique)',
  `sex` tinyint(4) DEFAULT NULL COMMENT 'sex(1.man 2.woman)',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT 'account status(1.normal 2.locked 3.deleted 4.illegal)',
  `create_where` tinyint(4) DEFAULT NULL COMMENT 'create where(1.web 2.android 3.ios 4.win 5.mac 6.linux)',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `gmt_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_user_username_uindex` (`username`),
  UNIQUE KEY `username` (`username`,`phone`,`email`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user`
--

LOCK TABLES `auth_user` WRITE;
/*!40000 ALTER TABLE `auth_user` DISABLE KEYS */;
INSERT INTO `auth_user` VALUES (111,'admin','admin',NULL,NULL,NULL,NULL,NULL,1,1,'2021-03-26 04:04:43','2021-03-26 12:04:43'),(112,'user','user',NULL,NULL,NULL,NULL,NULL,1,1,'2021-03-26 04:04:43','2021-03-26 12:04:43'),(113,'guest','guest',NULL,NULL,NULL,NULL,NULL,1,1,'2021-03-26 04:04:43','2021-03-26 12:04:43'),(114,'noRole','noRole',NULL,NULL,NULL,NULL,NULL,1,1,'2021-03-26 04:04:43','2021-03-26 12:04:43'),(124,'admin2','D2F7D5C4BD974C5A2BD98FC11E56081E','ixrsj2doh6dblrx5',NULL,NULL,NULL,NULL,1,1,'2021-03-27 08:13:50','2021-03-27 16:13:50'),(125,'jinyf','haha',NULL,NULL,NULL,NULL,NULL,1,NULL,'2021-03-27 08:56:13','2021-03-27 16:56:13');
/*!40000 ALTER TABLE `auth_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-31 12:26:35
