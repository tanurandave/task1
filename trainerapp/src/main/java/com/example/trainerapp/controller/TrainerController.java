package com.example.trainerapp.controller;

import com.example.trainerapp.entity.Trainer;
import com.example.trainerapp.service.TrainerService;
import com.example.trainerapp.entity.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trainer")
@CrossOrigin("*")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public Trainer addTrainer(@RequestBody Trainer trainer) {
        return trainerService.addTrainer(trainer);
    }

    @GetMapping
    public List<Trainer> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable Long id) {
        Optional<Trainer> trainer = trainerService.getTrainerById(id);
        return trainer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public String deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return "Trainer deleted successfully";
    }

    @GetMapping("/{subject}/topic")
    public List<Trainer> getTrainersBySubject(@PathVariable String subject) {
        return trainerService.getTrainersBySubject(subject);
    }

    @GetMapping("/{id}/subjects")
    public List<Subject> getSubjectsForTrainer(@PathVariable Long id) {
        return trainerService.getSubjectsByTrainer(id);
    }
}
