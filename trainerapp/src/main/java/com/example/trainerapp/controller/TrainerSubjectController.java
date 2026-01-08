package com.example.trainerapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.trainerapp.entity.TrainerSubject;
import com.example.trainerapp.repository.TrainerSubjectRepository;

@RestController
@RequestMapping("/trainer-subject")
@CrossOrigin(origins = "*")
public class TrainerSubjectController {

    @Autowired
    private TrainerSubjectRepository trainerSubjectRepository;

    // ASSIGN trainer to subject
    @PostMapping("/assign")
    public TrainerSubject assignTrainerToSubject(
            @RequestBody TrainerSubject trainerSubject) {
        return trainerSubjectRepository.save(trainerSubject);
    }
}
