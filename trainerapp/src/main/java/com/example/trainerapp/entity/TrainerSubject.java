package com.example.trainerapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainer_subject")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long empId;
    private Long subjectId;
}
