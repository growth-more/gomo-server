DROP TABLE IF EXISTS `streak_quest_completed_success_event`;
DROP TABLE IF EXISTS `point_quest_completed_success_event`;
DROP TABLE IF EXISTS `score_quest_completed_success_event`;

CREATE TABLE `processed_event_entry` (
    `event_entry_id` VARCHAR(255) NOT NULL,
    `consumer_name` VARCHAR(255) NOT NULL,
    `processed_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`event_entry_id`, `consumer_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `event_entry`
RENAME COLUMN `event_type` TO `event_name`;
