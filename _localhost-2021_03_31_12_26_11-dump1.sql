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
-- Table structure for table `auth_resource`
--

DROP TABLE IF EXISTS `auth_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auth_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'resource ID',
  `name` varchar(50) NOT NULL COMMENT 'resource name',
  `code` varchar(50) NOT NULL COMMENT 'resource code',
  `uri` varchar(255) NOT NULL COMMENT 'access URL',
  `type` varchar(20) DEFAULT NULL COMMENT 'resource type',
  `method` varchar(10) NOT NULL COMMENT 'access method: GET POST PUT DELETE PATCH',
  `status` smallint(4) NOT NULL DEFAULT '1' COMMENT 'status   1:normal、9：filtering(Exclusion protection-all can access this api)',
  `description` varchar(255) DEFAULT NULL COMMENT 'resource description',
  `gmt_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `gmt_update` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_resource`
--

LOCK TABLES `auth_resource` WRITE;
/*!40000 ALTER TABLE `auth_resource` DISABLE KEYS */;
INSERT INTO `auth_resource` VALUES (101,'User get token','ACCOUNT_TOKEN','/auth/token','account','POST',9,NULL,'2021-03-26 04:04:17','2021-03-26 12:04:17'),(102,'User register','ACCOUNT_REGISTER','/auth/register','account','POST',9,NULL,'2021-03-26 04:04:17','2021-03-26 12:04:17'),(103,'Add resource','ADD_RESOURCE','/resource','resource','POST',1,NULL,'2021-03-26 04:04:17','2021-03-26 12:04:17'),(104,'Update resource','UPDATE_RESOURCE','/resource','resource','PUT',1,NULL,'2021-03-26 04:04:17','2021-03-26 12:04:17'),(105,'Delete resource','DELETE_RESOURCE','/resource/*','resource','DELETE',1,NULL,'2021-03-26 04:04:17','2021-03-26 12:04:17'),(106,'Get resource','GET_RESOURCES','/resource/*/*','resource','GET',1,NULL,'2021-03-26 04:04:18','2021-03-26 12:04:18'),(107,'Add role','ADD_ROLE','/role','role','POST',1,NULL,'2021-03-26 04:04:18','2021-03-26 12:04:18'),(108,'Update role','UPDATE_ROLE','/role','role','PUT',1,NULL,'2021-03-26 04:04:18','2021-03-26 12:04:18'),(109,'Delete role','DELETE_ROLE','/role/*','role','DELETE',1,NULL,'2021-03-26 04:04:18','2021-03-26 12:04:18'),(110,'Get role','GET_ROLES','/role/*/*','role','GET',1,NULL,'2021-03-26 04:04:18','2021-03-26 12:04:18'),(111,'User get custom token','ACCOUNT_CUSTOM_TOKEN','/auth/custom/token','account','POST',9,NULL,'2021-03-26 04:04:18','2021-03-26 12:04:18'),(112,'Static Resource','Static Resource','/**/*.html','static','GET',9,NULL,'2021-03-26 04:04:18','2021-03-26 12:04:18'),(113,'Static Resource','Static Resource','/**/*.js','static','GET',9,NULL,'2021-03-26 04:04:19','2021-03-26 12:04:19'),(114,'Static Resource','Static Resource','/**/*.css','static','GET',9,NULL,'2021-03-26 04:04:19','2021-03-26 12:04:19'),(120,'Any role','test','/*','test','post',9,NULL,'2021-03-31 04:14:07','2021-03-31 12:14:07');
/*!40000 ALTER TABLE `auth_resource` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-31 12:26:12
