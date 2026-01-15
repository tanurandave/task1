package com.example.trainerapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    private String address;

    private String format;

    private String mobileNumber;

    @Column(columnDefinition = "JSON")
    private String subjects; // Store subjects as JSON string
}
