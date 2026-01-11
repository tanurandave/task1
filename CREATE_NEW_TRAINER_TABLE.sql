-- SQL Script to Delete Existing Tables and Create New Trainer Table with All Fields from Add Trainer Form

-- Drop existing tables if they exist
DROP TABLE IF EXISTS trainer_subject;
DROP TABLE IF EXISTS trainer;

-- Create new trainer table with all fields from the add trainer form
CREATE TABLE trainer (
    empId BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    experience INT NOT NULL,
    address VARCHAR(255),
    format VARCHAR(50),
    mobileNumber VARCHAR(20) NOT NULL,
    subjects JSON -- Store selected subjects as JSON array
);

-- Insert sample data (optional, for testing)
INSERT INTO trainer (name, email, experience, address, format, mobileNumber, subjects) VALUES
('John Doe', 'john@example.com', 5, '123 Main St', 'Online', '1234567890', '["Java", "Python"]'),
('Jane Smith', 'jane@example.com', 3, '456 Elm St', 'Offline', '0987654321', '["JavaScript", "React"]');
