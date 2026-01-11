-- DROP_AND_RECREATE_SUBJECT_TOPIC.sql
-- This script safely removes old tables and creates new ones with Topic support

-- Step 1: Drop trainer_subject table (has foreign key to subject)
DROP TABLE IF EXISTS `trainer_subject`;

-- Step 2: Drop old tables
DROP TABLE IF EXISTS `topic`;
DROP TABLE IF EXISTS `subject`;

-- Step 3: Create new subject table
CREATE TABLE `subject` (
  `subject_id` BIGINT NOT NULL AUTO_INCREMENT,
  `subject_name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Step 4: Create new topic table with foreign key to subject
CREATE TABLE `topic` (
  `topic_id` BIGINT NOT NULL AUTO_INCREMENT,
  `topic_name` VARCHAR(255) NOT NULL,
  `subject_id` BIGINT NOT NULL,
  PRIMARY KEY (`topic_id`),
  KEY `fk_topic_subject` (`subject_id`),
  CONSTRAINT `fk_topic_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Step 5: Recreate trainer_subject table
CREATE TABLE `trainer_subject` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `emp_id` BIGINT NOT NULL,
  `subject_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk4ss3re5g7efw0tbq0f30xpqs9` (`emp_id`, `subject_id`),
  KEY `fk_trainer_subject_subject` (`subject_id`),
  CONSTRAINT `fk_trainer_subject_subject` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SELECT 'Tables recreated successfully!' AS status;
