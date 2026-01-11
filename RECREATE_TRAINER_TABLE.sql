-- RECREATE_TRAINER_TABLE.sql
-- 1) Renames current trainer table to a backup (keeps data safe)
-- 2) Creates new `trainer` table matching entity fields
-- 3) Copies data from backup to new table (maps known columns)
-- 4) Do NOT drop the backup table until you verify data

SET @ts = DATE_FORMAT(NOW(), '%Y%m%d%H%i%s');

-- 1) Backup existing table if it exists
DROP PROCEDURE IF EXISTS backup_trainer_table;
DELIMITER $$
CREATE PROCEDURE backup_trainer_table()
BEGIN
  IF (SELECT COUNT(*) FROM information_schema.tables
      WHERE table_schema = DATABASE() AND table_name = 'trainer') > 0 THEN
    SET @bkname = CONCAT('trainer_backup_', @ts);
    SET @sql = CONCAT('RENAME TABLE `trainer` TO `', @bkname, '`');
    PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
  END IF;
END$$
DELIMITER ;
CALL backup_trainer_table();
DROP PROCEDURE IF EXISTS backup_trainer_table;

-- 2) Create new trainer table with all fields
CREATE TABLE IF NOT EXISTS `trainer` (
  `emp_id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `experience` INT NOT NULL DEFAULT 0,
  `address` VARCHAR(255) DEFAULT NULL,
  `format` VARCHAR(50) DEFAULT NULL,
  `mobile_number` VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `uk_trainer_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3) Migrate data from backup table (if present)
-- This will insert existing rows into the new table. New columns will be NULL if not available.
SET @bkname = NULL;
SELECT table_name INTO @bkname
FROM information_schema.tables
WHERE table_schema = DATABASE() AND table_name LIKE 'trainer_backup_%'
ORDER BY table_name DESC
LIMIT 1;

IF (@bkname IS NOT NULL) THEN
  SET @sql = CONCAT(
    'INSERT INTO `trainer` (emp_id, name, email, experience, address, format, mobile_number) ',
    'SELECT emp_id, name, email, IFNULL(experience,0) AS experience, NULL AS address, NULL AS format, NULL AS mobile_number ',
    'FROM `', @bkname, '`'
  );
  PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
END IF;

-- NOTE: Do not DROP the backup table here. Verify data first:
--   SELECT * FROM trainer LIMIT 50;
--   SELECT COUNT(*) FROM trainer;
-- After verification, you can DROP the backup table with:
--   DROP TABLE `trainer_backup_<timestamp>`;

SELECT 'Migration script completed. Verify data in `trainer` table before dropping backups.' AS info;
