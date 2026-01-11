package com.example.trainerapp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "topic")
@Data
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    private String topicName;
    private String description;
}
