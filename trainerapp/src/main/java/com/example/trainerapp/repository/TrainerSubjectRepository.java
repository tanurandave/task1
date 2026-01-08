package com.example.trainerapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.trainerapp.entity.TrainerSubject;

public interface TrainerSubjectRepository 
        extends JpaRepository<TrainerSubject, Long> {
}
