package br.com.selat.classroomservice.service;

import br.com.selat.classroomservice.contract.v1.event.Event;
import br.com.selat.classroomservice.contract.v1.event.EventEntity;
import br.com.selat.classroomservice.contract.v1.event.EventType;
import br.com.selat.classroomservice.model.Professor;
import br.com.selat.classroomservice.model.Student;
import br.com.selat.classroomservice.repository.ProfessorRepository;
import br.com.selat.classroomservice.repository.StudentRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Value("${kafka.eventTopicName}")
    private String kafkaEventTopicName;

    private final Gson gson = new Gson();
    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;
    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Autowired
    public KafkaService(ProfessorRepository professorRepository,
                        StudentRepository studentRepository, KafkaTemplate<String, Event> kafkaTemplate) {
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(EventType eventType, EventEntity eventEntity, String payload){
        Event event = new Event(eventType, eventEntity, payload);
        ListenableFuture<SendResult<String, Event>> future = kafkaTemplate.send(kafkaEventTopicName, event);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Event>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("Error sending message to kafka: ", ex);
            }

            @Override
            public void onSuccess(SendResult<String, Event> result) {
                logger.info("Message sent to kafka");
            }
        });
    }

    @KafkaListener(topics = "#{'${kafka.listenEventTopics}'.split(',')}")
    @Transactional
    public void listenEventTopic(Event event){
        logger.info("Message received: " + new Gson().toJson(event));
        switch (event.getEntity()){
            case Professor:
                processProcessorEvent(event);
                break;
            case Student:
                processStudentEvent(event);
                break;
        }
    }

    private void processProcessorEvent(Event event) {
        Professor professor = gson.fromJson(event.getPayload(), Professor.class);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                professorRepository.save(professor);
                logger.info("Professor Saved: " + new Gson().toJson(professor));
                break;
            case DELETE:
                professorRepository.delete(professor);
                logger.info("Professor Removed: " + new Gson().toJson(professor));
                break;
        }
    }

    private void processStudentEvent(Event event) {
        Student student = gson.fromJson(event.getPayload(), Student.class);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                studentRepository.save(student);
                logger.info("Student Saved: " + new Gson().toJson(student));
                break;
            case DELETE:
                studentRepository.delete(student);
                logger.info("Student Removed: " + new Gson().toJson(student));
                break;
        }
    }
}
