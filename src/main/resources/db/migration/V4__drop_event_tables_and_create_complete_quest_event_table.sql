-- streak_quest_completed_success_event, point_quest_completed_success_event, score_quest_completed_success_event 테이블 삭제
DROP TABLE IF EXISTS `streak_quest_completed_success_event`;
DROP TABLE IF EXISTS `point_quest_completed_success_event`;
DROP TABLE IF EXISTS `score_quest_completed_success_event`;

-- complete_quest_event 테이블 생성
CREATE TABLE `complete_quest_event` (
  `id` binary(16) NOT NULL,
  `participant_id` binary(16) NOT NULL,
  `subject_id` binary(16) NOT NULL,
  `quest_type` varchar(30) NOT NULL,
  `score_reward` int NOT NULL,
  `point_reward` int NOT NULL,
  `quest_completion_time` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- event_entry 테이블의 event_type 필드명을 event_name으로 수정
ALTER TABLE `event_entry`
    RENAME COLUMN `event_type` TO `event_name`;
