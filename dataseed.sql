-- MySQL dump 10.13  Distrib 8.4.5, for Linux (x86_64)
--
-- Host: localhost    Database: booking-service
-- ------------------------------------------------------
-- Server version	8.4.5

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `booking-service`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `booking-service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `booking-service`;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `booking_id` bigint NOT NULL AUTO_INCREMENT,
  `booking_status` tinyint DEFAULT NULL,
  `final_price` double DEFAULT NULL,
  `show_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `booking_archive`
--

DROP TABLE IF EXISTS `booking_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_archive` (
  `booking_id` bigint NOT NULL,
  `archived_at` datetime(6) DEFAULT NULL,
  `booking_status` tinyint DEFAULT NULL,
  `final_price` double DEFAULT NULL,
  `show_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_archive`
--

LOCK TABLES `booking_archive` WRITE;
/*!40000 ALTER TABLE `booking_archive` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking_archive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking_status_response`
--

DROP TABLE IF EXISTS `booking_status_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking_status_response` (
  `bookingid` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`bookingid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking_status_response`
--

LOCK TABLES `booking_status_response` WRITE;
/*!40000 ALTER TABLE `booking_status_response` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking_status_response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat`
--

DROP TABLE IF EXISTS `seat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat` (
  `seat_id` varchar(255) NOT NULL,
  `seat_price` double DEFAULT NULL,
  `seat_status` tinyint DEFAULT NULL,
  `booking_booking_id` bigint DEFAULT NULL,
  PRIMARY KEY (`seat_id`),
  KEY `FK4lm55i0fe5ih6wxhgbppd9jy9` (`booking_booking_id`),
  CONSTRAINT `FK4lm55i0fe5ih6wxhgbppd9jy9` FOREIGN KEY (`booking_booking_id`) REFERENCES `booking` (`booking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat`
--

LOCK TABLES `seat` WRITE;
/*!40000 ALTER TABLE `seat` DISABLE KEYS */;
/*!40000 ALTER TABLE `seat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seat_archive`
--

DROP TABLE IF EXISTS `seat_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seat_archive` (
  `seat_id` varchar(255) NOT NULL,
  `seat_price` double DEFAULT NULL,
  `seat_status` tinyint DEFAULT NULL,
  `booking_archive_booking_id` bigint DEFAULT NULL,
  PRIMARY KEY (`seat_id`),
  KEY `FKp9cljt1tgpmp6r33y5kksy7n5` (`booking_archive_booking_id`),
  CONSTRAINT `FKp9cljt1tgpmp6r33y5kksy7n5` FOREIGN KEY (`booking_archive_booking_id`) REFERENCES `booking_archive` (`booking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seat_archive`
--

LOCK TABLES `seat_archive` WRITE;
/*!40000 ALTER TABLE `seat_archive` DISABLE KEYS */;
/*!40000 ALTER TABLE `seat_archive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `events-service`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `events-service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `events-service`;

--
-- Table structure for table `auditorium`
--

DROP TABLE IF EXISTS `auditorium`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditorium` (
  `auditorium_id` bigint NOT NULL AUTO_INCREMENT,
  `auditorium` tinyint DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `numberofblocks` int DEFAULT NULL,
  `seats_arrangement` json DEFAULT NULL,
  PRIMARY KEY (`auditorium_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditorium`
--

LOCK TABLES `auditorium` WRITE;
/*!40000 ALTER TABLE `auditorium` DISABLE KEYS */;
INSERT INTO `auditorium` VALUES (1,1,'Mumbai','Infinity Mall','Cinepolis Infinity',2,'[[8, 8, 8], [6, 6]]'),(2,1,'Mumbai','Phoenix Marketcity','PVR Phoenix',2,'[[9, 9], [8, 8]]'),(3,1,'Bangalore','Forum Mall','INOX Forum',2,'[[7, 7, 7], [7, 7]]'),(4,1,'Thane','Viviana Mall','Carnival Cinemas',2,'[[8, 8], [8, 8]]'),(5,1,'Pune','City Centre','Miraj Cinemas',2,'[[6, 6, 6], [8, 8]]'),(6,1,'Noida','Wave Mall','Wave Cinemas',2,'[[9, 9, 9], [6, 6]]'),(7,1,'Chennai','Forum Vijaya','SPI Palazzo',2,'[[8, 8, 8], [7, 7]]'),(8,1,'Ahmedabad','Iscon Mall','Rajhans Cinemas',2,'[[7, 7], [7, 7]]'),(9,2,'Mumbai','Prabhadevi','National Drama Hall',2,'[[7, 7, 7], [6, 6]]'),(10,2,'Goa','Campal','Kala Academy',2,'[[8, 8], [7, 7]]'),(11,2,'Mumbai','Prabhadevi','Ravindra Natya Mandir',2,'[[6, 6, 6], [7, 7]]'),(12,0,'Mumbai','Dadar','Shivaji Open Ground',3,'[[12, 12], [15, 15], [12, 12]]'),(13,0,'Kolkata','Salt Lake','City Sports Arena',3,'[[10, 10], [14, 14], [10, 10]]'),(14,0,'Delhi','Lodhi Road','Jawaharlal Nehru Stadium',3,'[[15, 15], [18, 18], [15, 15]]');
/*!40000 ALTER TABLE `auditorium` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `event_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `duration` time(6) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `meta_data` bigint DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `UK_j4t6wq3q8vekson37xb7fa2ga` (`meta_data`),
  CONSTRAINT `FKgp9bku1jto281deg06dri5rqe` FOREIGN KEY (`meta_data`) REFERENCES `event_metadata` (`movies_metadata_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'A skilled thief enters dreams to steal secrets.','02:28:00.000000','Sci-Fi','2010-07-16','Movies','Inception',1),(2,'A team travels through a wormhole to save humanity.','02:49:00.000000','Sci-Fi','2014-11-07','Movies','Interstellar',2),(3,'Batman faces the Joker in Gotham City.','02:32:00.000000','Action','2008-07-18','Movies','The Dark Knight',3),(4,'The Avengers assemble for a final battle.','03:01:00.000000','Superhero','2019-04-26','Movies','Avengers: Endgame',4),(5,'A poor family schemes to infiltrate a wealthy household.','02:12:00.000000','Thriller','2019-05-30','Movies','Parasite',5),(6,'A noble family becomes embroiled in a galactic war.','02:35:00.000000','Sci-Fi','2021-10-22','Movies','Dune: Part One',6),(7,'A failed comedian descends into madness.','02:02:00.000000','Drama','2019-10-04','Movies','Joker',7),(8,'Two revolutionaries fight against colonial rule.','03:07:00.000000','Action','2022-03-25','Movies','RRR',8),(9,'Three friends navigate college and life.','02:51:00.000000','Comedy','2009-12-25','Movies','3 Idiots',9),(10,'A love story aboard the ill-fated Titanic.','03:14:00.000000','Romance','1997-12-19','Movies','Titanic',10),(11,'The grand finale of the Indian Premier League.','04:00:00.000000','Cricket','2025-05-29','Sports','IPL Final 2025',11),(12,'India\'s biggest football league final.','02:30:00.000000','Football','2025-03-18','Sports','ISL Championship Match',12),(13,'The ultimate kabaddi showdown.','02:00:00.000000','Kabaddi','2025-02-10','Sports','Pro Kabaddi League Finals',13),(14,'Relatable comedy by Zakir Khan.','01:45:00.000000','Stand-up Comedy','2025-08-12','Comic Shows','Zakir Khan Live',14),(15,'Clean and musical stand-up comedy.','01:40:00.000000','Stand-up Comedy','2025-09-20','Comic Shows','Kenny Sebastian Live',15),(16,'Sharp observational comedy.','01:30:00.000000','Stand-up Comedy','2025-07-05','Comic Shows','Biswa Kalyan Rath Live',16),(17,'A powerful tale of love and revenge.','02:10:00.000000','Greek Tragedy','2025-07-10','Drama','Medea',17),(18,'A modern Indian theatre classic.','01:55:00.000000','Modern Drama','2025-08-01','Drama','Evam Indrajit',18),(19,'Two men wait endlessly for someone who never arrives.','02:00:00.000000','Absurdist Drama','2025-09-05','Drama','Waiting for Godot',19);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_language`
--

DROP TABLE IF EXISTS `event_language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_language` (
  `event_event_id` bigint NOT NULL,
  `language` varchar(255) DEFAULT NULL,
  KEY `FK1ymo8n8sj8p4ruqletfeughgf` (`event_event_id`),
  CONSTRAINT `FK1ymo8n8sj8p4ruqletfeughgf` FOREIGN KEY (`event_event_id`) REFERENCES `event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_language`
--

LOCK TABLES `event_language` WRITE;
/*!40000 ALTER TABLE `event_language` DISABLE KEYS */;
INSERT INTO `event_language` VALUES (1,'English'),(2,'English'),(3,'English'),(4,'English'),(5,'Korean'),(6,'English'),(7,'English'),(8,'Telugu'),(8,'Hindi'),(9,'Hindi'),(10,'English'),(11,'English'),(11,'Hindi'),(12,'English'),(13,'Hindi'),(14,'Hindi'),(15,'English'),(16,'English'),(16,'Hindi'),(17,'English'),(18,'Hindi'),(19,'English');
/*!40000 ALTER TABLE `event_language` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_metadata`
--

DROP TABLE IF EXISTS `event_metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_metadata` (
  `movies_metadata_id` bigint NOT NULL AUTO_INCREMENT,
  `posterurl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`movies_metadata_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_metadata`
--

LOCK TABLES `event_metadata` WRITE;
/*!40000 ALTER TABLE `event_metadata` DISABLE KEYS */;
INSERT INTO `event_metadata` VALUES (1,'https://images4.alphacoders.com/112/1122038.jpg'),(2,'https://i.pinimg.com/originals/11/1c/5c/111c5c9ad99661af2d80e38948cf29d8.jpg'),(3,'https://wallpapers.com/images/hd/dark-knight-hd-kyhwt7kiwonapdy2.jpg'),(4,'https://m.media-amazon.com/images/S/pv-target-images/ae8093e4439b66320d3cc1f1fb96fa414530202ee4b21cc00a08d6b66de541d6.jpg'),(5,'https://tse3.mm.bing.net/th/id/OIP.oYFRL1BoTowDboEyCRyJ1wHaIo?pid=Api&P=0&h=180'),(6,'https://tse3.mm.bing.net/th/id/OIP.HlXXravGwMPkdrASEz7dewHaEK?pid=Api&P=0&h=180'),(7,'https://tse2.mm.bing.net/th/id/OIP.0-BWHq2BSxYHEPsJGfl3vAHaEK?pid=Api&P=0&h=180'),(8,'https://assets-prd.ignimgs.com/2022/07/11/rrr-button-ver-1657577923010.jpg'),(9,'https://tse1.mm.bing.net/th/id/OIP.PkV-nNKsQeI2Mxj0dGgnlAHaJf?pid=Api&P=0&h=180'),(10,'https://wallpapers.com/images/hd/titanic-movie-poster-03rhvsddapntyky8.jpg'),(11,'https://www.livemint.com/lm-img/img/2024/04/15/600x338/PTI04-14-2024-000468A-0_1713158084056_1713158156529.jpg'),(12,'https://technosports.co.in/wp-content/uploads/2024/09/Sunil-Chhetri-1.jpg'),(13,'https://tse2.mm.bing.net/th/id/OIP.xcVOl0a9407bVKzeI85P0AHaEe?pid=Api&P=0&h=180'),(14,'https://tse3.mm.bing.net/th/id/OIP.UlupASOVA26cPzznm96cFwHaEK?pid=Api&P=0&h=180'),(15,'https://wallpapers.com/images/featured/kenny-chesney-99xyowhoeg3fiqc2.jpg'),(16,'https://tse3.mm.bing.net/th/id/OIP.X3j619jKNfg_NcMlKBkHgwHaE8?pid=Api&P=0&h=180'),(17,'https://tse2.mm.bing.net/th/id/OIP.LD50uwPLneAZjTVRpix3swHaFq?pid=Api&P=0&h=180'),(18,'https://i.ytimg.com/vi/6vLWhSKn1yA/maxresdefault.jpg'),(19,'https://tse4.mm.bing.net/th/id/OIP.zNZ7BZ7FIH2MFEZVyZBJ4AHaEn?pid=Api&P=0&h=180');
/*!40000 ALTER TABLE `event_metadata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_metadata_cast`
--

DROP TABLE IF EXISTS `event_metadata_cast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_metadata_cast` (
  `event_metadata_movies_metadata_id` bigint NOT NULL,
  `cast` varchar(255) DEFAULT NULL,
  KEY `FKrky1s4idc5po5rt193jbupm6` (`event_metadata_movies_metadata_id`),
  CONSTRAINT `FKrky1s4idc5po5rt193jbupm6` FOREIGN KEY (`event_metadata_movies_metadata_id`) REFERENCES `event_metadata` (`movies_metadata_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_metadata_cast`
--

LOCK TABLES `event_metadata_cast` WRITE;
/*!40000 ALTER TABLE `event_metadata_cast` DISABLE KEYS */;
INSERT INTO `event_metadata_cast` VALUES (1,'Leonardo DiCaprio'),(2,'Matthew McConaughey'),(3,'Christian Bale'),(3,'Heath Ledger'),(4,'Robert Downey Jr.'),(4,'Chris Evans'),(5,'Song Kang-ho'),(6,'Timothée Chalamet'),(7,'Joaquin Phoenix'),(8,'Ram Charan'),(8,'NTR Jr.'),(9,'Aamir Khan'),(10,'Leonardo DiCaprio'),(10,'Kate Winslet'),(11,'Top IPL Teams'),(12,'ISL Finalists'),(13,'PKL Teams'),(14,'Zakir Khan'),(15,'Kenny Sebastian'),(16,'Biswa Kalyan Rath'),(17,'Theatre Artists'),(18,'Indian Theatre Group'),(19,'Stage Actors');
/*!40000 ALTER TABLE `event_metadata_cast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `shows-service`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `shows-service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `shows-service`;

--
-- Table structure for table `shows`
--

DROP TABLE IF EXISTS `shows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shows` (
  `show_id` bigint NOT NULL AUTO_INCREMENT,
  `auditorium_id` bigint NOT NULL,
  `end_time` datetime(6) NOT NULL,
  `event_id` bigint NOT NULL,
  `start_time` datetime(6) NOT NULL,
  PRIMARY KEY (`show_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shows`
--

LOCK TABLES `shows` WRITE;
/*!40000 ALTER TABLE `shows` DISABLE KEYS */;
INSERT INTO `shows` VALUES (1,1,'2025-06-20 20:30:00.000000',1,'2025-06-20 18:00:00.000000'),(2,2,'2025-06-21 21:45:00.000000',2,'2025-06-21 19:00:00.000000'),(3,3,'2025-06-22 20:00:00.000000',3,'2025-06-22 17:30:00.000000'),(4,4,'2025-06-23 23:00:00.000000',4,'2025-06-23 20:00:00.000000'),(5,5,'2025-06-24 20:45:00.000000',5,'2025-06-24 18:30:00.000000'),(6,6,'2025-06-25 21:50:00.000000',6,'2025-06-25 19:15:00.000000'),(7,7,'2025-06-26 20:15:00.000000',7,'2025-06-26 18:00:00.000000'),(8,8,'2025-06-27 23:30:00.000000',8,'2025-06-27 21:00:00.000000'),(9,1,'2025-06-28 19:50:00.000000',9,'2025-06-28 17:00:00.000000'),(10,2,'2025-06-29 23:15:00.000000',10,'2025-06-29 20:00:00.000000'),(11,12,'2025-05-29 20:00:00.000000',11,'2025-05-29 16:00:00.000000'),(12,13,'2025-03-18 20:30:00.000000',12,'2025-03-18 18:00:00.000000'),(13,12,'2025-02-10 19:00:00.000000',13,'2025-02-10 17:00:00.000000'),(14,9,'2025-08-12 20:45:00.000000',14,'2025-08-12 19:00:00.000000'),(15,10,'2025-07-05 20:00:00.000000',15,'2025-07-05 18:30:00.000000'),(16,11,'2025-09-20 21:10:00.000000',16,'2025-09-20 19:30:00.000000'),(17,9,'2025-06-15 20:20:00.000000',17,'2025-06-15 18:00:00.000000'),(18,10,'2025-07-10 20:40:00.000000',18,'2025-07-10 18:30:00.000000'),(19,11,'2025-08-01 20:55:00.000000',19,'2025-08-01 19:00:00.000000'),(20,9,'2025-09-05 21:30:00.000000',20,'2025-09-05 19:30:00.000000');
/*!40000 ALTER TABLE `shows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shows_blockprices`
--

DROP TABLE IF EXISTS `shows_blockprices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shows_blockprices` (
  `shows_show_id` bigint NOT NULL,
  `blockprices` int NOT NULL,
  KEY `FKbrk48xrpt4bar2567emkirfee` (`shows_show_id`),
  CONSTRAINT `FKbrk48xrpt4bar2567emkirfee` FOREIGN KEY (`shows_show_id`) REFERENCES `shows` (`show_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shows_blockprices`
--

LOCK TABLES `shows_blockprices` WRITE;
/*!40000 ALTER TABLE `shows_blockprices` DISABLE KEYS */;
INSERT INTO `shows_blockprices` VALUES (1,150),(1,250),(2,180),(2,280),(3,160),(3,260),(4,200),(4,320),(5,170),(5,270),(6,190),(6,300),(7,160),(7,260),(8,210),(8,340),(9,150),(9,240),(10,220),(10,360),(11,300),(11,500),(11,800),(12,250),(12,450),(12,700),(13,200),(13,400),(13,650),(14,300),(14,500),(15,280),(15,480),(16,320),(16,520),(17,250),(17,400),(18,260),(18,420),(19,240),(19,380),(20,270),(20,430);
/*!40000 ALTER TABLE `shows_blockprices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `users-service`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `users-service` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `users-service`;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `expires_at` datetime(6) NOT NULL,
  `is_revoked` bit(1) NOT NULL,
  `jti` varchar(36) NOT NULL,
  `last_used_at` datetime(6) DEFAULT NULL,
  `token_hash` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tohckreilkhueuxpf5nci51o0` (`jti`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (1,NULL,'2026-02-03 11:45:08.773244',_binary '\0','4e39f656-1a22-4ef2-8b0b-e2b269973fa8',NULL,'y0bDkLxbv2POX/TX7rq40A1N3TNxLUygWE35sRxhhyY=','rohankhedekar2803@gmail.com');
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'$2a$10$9pGBt0JJa1DHmwMKVBW/suV3B2.s.I5WgXXeYRW6.pXWQq4QxKgyG','rohankhedekar2803@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-27 12:36:36
