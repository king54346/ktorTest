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
-- Table structure for table `auth_role_resource_bind`
--

DROP TABLE IF EXISTS `auth_role_resource_bind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auth_role_resource_bind` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT 'role ID',
  `resource_id` bigint(20) NOT NULL COMMENT 'resource ID',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `gmt_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_bind` (`role_id`,`resource_id`),
  KEY `auth_role_resource_bind_auth_resource_id_fk` (`resource_id`),
  CONSTRAINT `auth_role_resource_bind_auth_resource_id_fk` FOREIGN KEY (`resource_id`) REFERENCES `auth_resource` (`id`),
  CONSTRAINT `auth_role_resource_bind_auth_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `auth_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_role_resource_bind`
--

LOCK TABLES `auth_role_resource_bind` WRITE;
/*!40000 ALTER TABLE `auth_role_resource_bind` DISABLE KEYS */;
INSERT INTO `auth_role_resource_bind` VALUES (1,100,101,'2021-03-26 04:07:37','2021-03-26 12:07:37'),(2,100,102,'2021-03-26 04:07:37','2021-03-26 12:07:37'),(3,100,103,'2021-03-26 04:07:37','2021-03-26 12:07:37'),(4,100,104,'2021-03-26 04:07:37','2021-03-26 12:07:37'),(5,100,105,'2021-03-26 04:07:37','2021-03-26 12:07:37'),(6,100,106,'2021-03-26 04:07:37','2021-03-26 12:07:37'),(7,100,107,'2021-03-26 04:07:37','2021-03-26 12:07:37'),(8,100,108,'2021-03-26 04:07:37','2021-03-26 12:07:37'),(9,100,109,'2021-03-26 04:07:38','2021-03-26 12:07:38'),(10,100,110,'2021-03-26 04:07:38','2021-03-26 12:07:38'),(11,102,103,'2021-03-26 04:07:38','2021-03-26 12:07:38'),(12,102,104,'2021-03-26 04:07:38','2021-03-26 12:07:38'),(13,102,106,'2021-03-26 04:07:38','2021-03-26 12:07:38'),(14,102,107,'2021-03-26 04:07:38','2021-03-26 12:07:38'),(15,102,108,'2021-03-26 04:07:38','2021-03-26 12:07:38'),(16,102,110,'2021-03-26 04:07:39','2021-03-26 12:07:39'),(17,103,106,'2021-03-26 04:07:39','2021-03-26 12:07:39'),(18,103,110,'2021-03-26 04:07:39','2021-03-26 12:07:39');
/*!40000 ALTER TABLE `auth_role_resource_bind` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-31 12:26:30
