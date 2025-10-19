--
-- Table structure for table `quest_pool`
--

DROP TABLE IF EXISTS `quest_pool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quest_pool` (
  `id` binary(16) NOT NULL,
  `participant_id` binary(16) NOT NULL,
  `subject_id` binary(16) NOT NULL,
  `subject_name` varchar(30) NOT NULL,
  `quest_type` enum('DAILY','WEEKLY','MONTHLY') NOT NULL,
  `content` varchar(255) NOT NULL,
  `processing_status` enum('UNUSED','ASSIGNED') NOT NULL,
  `source_type` enum('AI') NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `last_modified_at` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
