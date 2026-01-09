package com.example.trainerapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trainerapp.entity.TrainerSubject;
import java.util.List;

public interface TrainerSubjectRepository 
        extends JpaRepository<TrainerSubject, Long> {
    List<TrainerSubject> findBySubjectId(Long subjectId);
}
