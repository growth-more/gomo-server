-- MySQL dump 10.13  Distrib 8.0.41, for Linux (aarch64)
--
-- Host: localhost    Database: gomo
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `achiever`
--

DROP TABLE IF EXISTS `achiever`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achiever` (
  `id` binary(16) NOT NULL,
  `longest_streak_days` int DEFAULT NULL,
  `current_streak_days` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `assign_quest`
--

DROP TABLE IF EXISTS `assign_quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assign_quest` (
  `id` binary(16) NOT NULL,
  `participant_id` binary(16) NOT NULL,
  `subject_id` binary(16) NOT NULL,
  `subject_name` varchar(30) NOT NULL,
  `quest_type` enum('DAILY','WEEKLY','MONTHLY') NOT NULL,
  `content` varchar(255) NOT NULL,
  `proof` varchar(512) DEFAULT NULL,
  `is_confirmed` tinyint(1) DEFAULT NULL,
  `is_completed` tinyint(1) DEFAULT NULL,
  `display_order` int NOT NULL,
  `start_date_time` datetime(6) NOT NULL,
  `completed_date_time` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_entry`
--

DROP TABLE IF EXISTS `event_entry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_entry` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_type` varchar(255) NOT NULL,
  `event_status` enum('PENDING','COMPLETED','FAILED') NOT NULL,
  `payload` text NOT NULL,
  `timestamp` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interest` (
  `id` binary(16) NOT NULL,
  `registrant_id` binary(16) NOT NULL,
  `level` int DEFAULT '0',
  `score` int DEFAULT '0',
  `score_threshold` int DEFAULT '40',
  `total_score` int DEFAULT '0',
  `name` varchar(30) NOT NULL,
  `logo_url` varchar(512) NOT NULL,
  `color_code` varchar(10) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interest_relation`
--

DROP TABLE IF EXISTS `interest_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interest_relation` (
  `id` binary(16) NOT NULL,
  `registrant_id` binary(16) NOT NULL,
  `parent_interest_id` binary(16) NOT NULL,
  `child_interest_id` binary(16) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `major_interest`
--

DROP TABLE IF EXISTS `major_interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `major_interest` (
  `id` binary(16) NOT NULL,
  `registrant_id` binary(16) NOT NULL,
  `interest_id` binary(16) NOT NULL,
  `display_order` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` binary(16) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(128) NOT NULL,
  `profile_banner_url` varchar(512) DEFAULT NULL,
  `profile_image_url` varchar(512) DEFAULT NULL,
  `handle` varchar(50) NOT NULL,
  `name` varchar(30) NOT NULL,
  `motto` varchar(255) DEFAULT NULL,
  `daily_quest_threshold` tinyint DEFAULT '1',
  `weekly_quest_threshold` tinyint DEFAULT '1',
  `monthly_quest_threshold` tinyint DEFAULT '1',
  `login_provider` enum('EMAIL','GOOGLE','KAKAO','NAVER') NOT NULL,
  `role_type` enum('ROLE_MEMBER','ROLE_ADMIN') NOT NULL,
  `subscription_plan` enum('FREE','BASIC','PREMIUM') NOT NULL,
  `activate_status` enum('ACTIVE','INACTIVE','BLOCKED','DELETED') NOT NULL,
  `sign_up_date_time` datetime(6) DEFAULT NULL,
  `last_login_date_time` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `handle` (`handle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point`
--

DROP TABLE IF EXISTS `point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `point` (
  `id` binary(16) NOT NULL,
  `transactor_id` binary(16) NOT NULL,
  `source_type` enum('QUEST','ATTENDANCE','STORE','EVENT') NOT NULL,
  `transaction_type` enum('GAIN','SPEND') NOT NULL,
  `amount` int NOT NULL,
  `description` varchar(255) NOT NULL,
  `transaction_date_time` datetime(6) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_quest_completed_success_event`
--

DROP TABLE IF EXISTS `point_quest_completed_success_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `point_quest_completed_success_event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_entry_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_wallet`
--

DROP TABLE IF EXISTS `point_wallet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `point_wallet` (
  `id` binary(16) NOT NULL,
  `transactor_id` binary(16) NOT NULL,
  `balance` int NOT NULL DEFAULT '0',
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quest_point_policy`
--

DROP TABLE IF EXISTS `quest_point_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quest_point_policy` (
  `quest_type` enum('DAILY','WEEKLY','MONTHLY') NOT NULL,
  `points` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quest_score_policy`
--

DROP TABLE IF EXISTS `quest_score_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quest_score_policy` (
  `quest_type` enum('DAILY','WEEKLY','MONTHLY') NOT NULL,
  `score` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `repeat_quest`
--

DROP TABLE IF EXISTS `repeat_quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repeat_quest` (
  `id` binary(16) NOT NULL,
  `participant_id` binary(16) NOT NULL,
  `subject_id` binary(16) NOT NULL,
  `subject_name` varchar(30) NOT NULL,
  `quest_type` enum('DAILY','WEEKLY','MONTHLY') NOT NULL,
  `content` varchar(255) NOT NULL,
  `display_order` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `score_quest_completed_success_event`
--

DROP TABLE IF EXISTS `score_quest_completed_success_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score_quest_completed_success_event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_entry_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `score_threshold_policy`
--

DROP TABLE IF EXISTS `score_threshold_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `score_threshold_policy` (
  `level` smallint NOT NULL,
  `threshold` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `streak`
--

DROP TABLE IF EXISTS `streak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `streak` (
  `id` binary(16) NOT NULL,
  `achiever_id` binary(16) NOT NULL,
  `streak_type` enum('DAILY','WEEKLY','MONTHLY') NOT NULL,
  `filled_date` date NOT NULL,
  `completed_quest_count` tinyint NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `streak_quest_completed_success_event`
--

DROP TABLE IF EXISTS `streak_quest_completed_success_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `streak_quest_completed_success_event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_entry_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `survey_item`
--

DROP TABLE IF EXISTS `survey_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `survey_item` (
  `id` binary(16) NOT NULL,
  `survey_question_id` binary(16) NOT NULL,
  `content` varchar(255) NOT NULL,
  `display_order` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `survey_question`
--

DROP TABLE IF EXISTS `survey_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `survey_question` (
  `id` binary(16) NOT NULL,
  `question_select_type` enum('SINGLE','MULTIPLE') DEFAULT NULL,
  `is_required` tinyint(1) NOT NULL,
  `content` varchar(255) NOT NULL,
  `display_order` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `survey_result`
--

DROP TABLE IF EXISTS `survey_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `survey_result` (
  `respondent_id` binary(16) NOT NULL,
  `survey_question_id` binary(16) NOT NULL,
  `survey_item_id` binary(16) NOT NULL,
  `survey_item_content` varchar(255) NOT NULL,
  `custom_answer` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-18  4:18:57
