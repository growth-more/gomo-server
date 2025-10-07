ALTER TABLE `score_threshold_policy`
RENAME TO `level_threshold_policy`;

ALTER TABLE `level_threshold_policy`
CHANGE COLUMN `threshold` `threshold_score` INT NOT NULL;
