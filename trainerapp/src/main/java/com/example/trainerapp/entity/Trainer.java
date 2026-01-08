package com.example.trainerapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Table(name = "trainer")
@Data
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;
    @NotBlank
    private String name;

    
        @NotBlank
    @Email(message = "Invalid email format")
    private String email;
    private int experience;
}
