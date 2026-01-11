package com.example.trainerapp.repository;

import com.example.trainerapp.entity.SubjectTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectTopicRepository extends JpaRepository<SubjectTopic, Long> {
    List<SubjectTopic> findBySubjectId(Long subjectId);
    List<SubjectTopic> findBySubjectIdAndTopicId(Long subjectId, Long topicId);
}
