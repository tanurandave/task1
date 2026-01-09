package com.example.trainerapp.service;

import com.example.trainerapp.entity.Subject;
import com.example.trainerapp.entity.SubjectWithTrainers;
import com.example.trainerapp.entity.Trainer;
import com.example.trainerapp.repository.SubjectRepository;
import com.example.trainerapp.repository.TrainerSubjectRepository;
import com.example.trainerapp.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TrainerSubjectRepository trainerSubjectRepository;
    private final TrainerRepository trainerRepository;

    public SubjectService(SubjectRepository subjectRepository, TrainerSubjectRepository trainerSubjectRepository, TrainerRepository trainerRepository) {
        this.subjectRepository = subjectRepository;
        this.trainerSubjectRepository = trainerSubjectRepository;
        this.trainerRepository = trainerRepository;
    }

    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    public SubjectWithTrainers getSubjectWithTrainers(Long id) {
        Subject subject = subjectRepository.findById(id).orElse(null);
        if (subject == null) {
            return null;
        }
        List<Trainer> trainers = trainerSubjectRepository.findBySubjectId(id).stream()
                .map(ts -> trainerRepository.findById(ts.getEmpId()).orElse(null))
                .filter(trainer -> trainer != null)
                .collect(Collectors.toList());
        return new SubjectWithTrainers(subject, trainers);
    }
}
