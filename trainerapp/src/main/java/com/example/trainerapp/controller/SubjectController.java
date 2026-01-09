package com.example.trainerapp.controller;

import com.example.trainerapp.entity.Subject;
import com.example.trainerapp.entity.SubjectWithTrainers;
import com.example.trainerapp.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@CrossOrigin("*")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public Subject addSubject(@RequestBody Subject subject) {
        return subjectService.addSubject(subject);
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public SubjectWithTrainers getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectWithTrainers(id);
    }
}
