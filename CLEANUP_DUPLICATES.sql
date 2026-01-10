-- SQL Script to Cleanup Duplicate Trainer-Subject Assignments
-- This script removes duplicate records, keeping only the first one

-- Check for duplicates
SELECT emp_id, subject_id, COUNT(*) as count
FROM trainer_subject
GROUP BY emp_id, subject_id
HAVING COUNT(*) > 1;

-- Delete duplicates, keep the one with the smallest ID
DELETE FROM trainer_subject
WHERE id NOT IN (
  SELECT MIN(id)
  FROM (
    SELECT MIN(id) as id
    FROM trainer_subject
    GROUP BY emp_id, subject_id
  ) as keep_rows
);

-- Verify cleanup
SELECT emp_id, subject_id, COUNT(*) as count
FROM trainer_subject
GROUP BY emp_id, subject_id
HAVING COUNT(*) > 1;

-- Add unique constraint if not exists
ALTER TABLE trainer_subject 
ADD CONSTRAINT uk_trainer_subject_emp_subject 
UNIQUE (emp_id, subject_id);
