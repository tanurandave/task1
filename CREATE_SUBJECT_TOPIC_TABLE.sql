CREATE TABLE subject_topic (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_id BIGINT NOT NULL,
    topic_id BIGINT NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
    FOREIGN KEY (topic_id) REFERENCES topic(topic_id),
    UNIQUE KEY unique_subject_topic (subject_id, topic_id)
);
