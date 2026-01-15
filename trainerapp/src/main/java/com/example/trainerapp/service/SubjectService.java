package com.example.trainerapp.service;

import com.example.trainerapp.entity.Subject;
import com.example.trainerapp.entity.SubjectRequest;
import com.example.trainerapp.entity.SubjectTopic;
import com.example.trainerapp.entity.SubjectWithTrainers;
import com.example.trainerapp.entity.Topic;
import com.example.trainerapp.entity.Trainer;
import com.example.trainerapp.repository.SubjectRepository;
import com.example.trainerapp.repository.SubjectTopicRepository;
import com.example.trainerapp.repository.TopicRepository;
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
    private final TopicRepository topicRepository;
    private final SubjectTopicRepository subjectTopicRepository;

    public SubjectService(SubjectRepository subjectRepository, TrainerSubjectRepository trainerSubjectRepository, TrainerRepository trainerRepository, TopicRepository topicRepository, SubjectTopicRepository subjectTopicRepository) {
        this.subjectRepository = subjectRepository;
        this.trainerSubjectRepository = trainerSubjectRepository;
        this.trainerRepository = trainerRepository;
        this.topicRepository = topicRepository;
        this.subjectTopicRepository = subjectTopicRepository;
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

    public List<Topic> getTopicsForSubject(Long subjectId) {
        return subjectTopicRepository.findBySubjectId(subjectId).stream()
                .map(st -> topicRepository.findById(st.getTopicId()).orElse(null))
                .filter(topic -> topic != null)
                .collect(Collectors.toList());
    }

    public void assignTopicToSubject(Long subjectId, Long topicId) {
        if (subjectTopicRepository.findBySubjectIdAndTopicId(subjectId, topicId).isEmpty()) {
            SubjectTopic subjectTopic = new SubjectTopic();
            subjectTopic.setSubjectId(subjectId);
            subjectTopic.setTopicId(topicId);
            subjectTopicRepository.save(subjectTopic);
        }
    }

    public void removeTopicFromSubject(Long subjectId, Long topicId) {
        List<SubjectTopic> subjectTopics = subjectTopicRepository.findBySubjectIdAndTopicId(subjectId, topicId);
        subjectTopicRepository.deleteAll(subjectTopics);
    }

    public Subject addSubjectWithTopics(SubjectRequest subjectRequest) {
        // Create and save the subject
        Subject subject = new Subject();
        subject.setSubjectName(subjectRequest.getSubjectName());
        subject.setDescription(subjectRequest.getDescription());
        Subject savedSubject = subjectRepository.save(subject);

        // Save topics and associate with subject
        if (subjectRequest.getTopics() != null) {
            for (Topic topic : subjectRequest.getTopics()) {
                Topic savedTopic;
                if (topic.getTopicId() == null) {
                    // Save new topic
                    savedTopic = topicRepository.save(topic);
                } else {
                    // Use existing topic
                    savedTopic = topic;
                }
                // Associate topic with subject
                assignTopicToSubject(savedSubject.getSubjectId(), savedTopic.getTopicId());
            }
        }

        return savedSubject;
    }

    public void deleteSubject(Long id) {
        // Delete related TrainerSubject records
        trainerSubjectRepository.deleteBySubjectId(id);
        // Delete related SubjectTopic records
        subjectTopicRepository.findBySubjectId(id);
        // Delete the subject
        subjectRepository.deleteById(id);
    }
}
