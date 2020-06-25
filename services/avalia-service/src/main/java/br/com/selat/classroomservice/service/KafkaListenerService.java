package br.com.selat.classroomservice.service;

import br.com.selat.classroomservice.contract.v1.event.Event;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Component
public class KafkaListenerService {


    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);

    private final Gson gson = new Gson();

    @KafkaListener(topics = "#{'${kafka.listenEventTopics}'.split(',')}")
    @Transactional
    public void listenEventTopic(Event event){
        logger.info(format("Message received: %s", new Gson().toJson(event)));
        switch (event.getEntity()){
            case PROFESSOR:
                processProcessorEvent(event);
                break;
            case STUDENT:
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

