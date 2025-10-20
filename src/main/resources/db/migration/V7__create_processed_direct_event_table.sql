CREATE TABLE `processed_direct_event` (
    `direct_event_id` VARCHAR(255) NOT NULL,
    `consumer_name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`direct_event_id`, `consumer_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
