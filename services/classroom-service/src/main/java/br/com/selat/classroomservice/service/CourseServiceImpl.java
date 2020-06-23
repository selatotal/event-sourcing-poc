package br.com.selat.classroomservice.service;

import br.com.selat.classroomservice.contract.v1.CourseService;
import br.com.selat.classroomservice.contract.v1.event.EventEntity;
import br.com.selat.classroomservice.contract.v1.event.EventType;
import br.com.selat.classroomservice.contract.v1.exception.NotFoundException;
import br.com.selat.classroomservice.contract.v1.exception.ServiceValidationException;
import br.com.selat.classroomservice.contract.v1.input.CourseInput;
import br.com.selat.classroomservice.contract.v1.output.CourseOutput;
import br.com.selat.classroomservice.model.Course;
import br.com.selat.classroomservice.repository.CourseRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class CourseServiceImpl implements CourseService {

    private static final String NOT_FOUND_MESSAGE = "Student not found";
    private static final int NAME_MAX_LENGTH = 255;
    private final Gson gson = new Gson();
    private final CourseRepository courseRepository;
    private final KafkaService kafkaService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, KafkaService kafkaService){
        this.courseRepository = courseRepository;
        this.kafkaService = kafkaService;
    }

    @Override
    public CourseOutput getById(String id) {
        Course entity = courseRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        return toCourseOutput(entity);
    }

    @Override
    @Transactional
    public CourseOutput create(CourseInput input) {
        validateInput(input);
        Course entity = courseRepository.save(new Course(UUID.randomUUID().toString(), input.getCourseId(), input.getPeriod(), input.getName(), input.getHours(), input.getProgram()));
        kafkaService.publishEvent(EventType.CREATE, EventEntity.Course, gson.toJson(entity));
        return toCourseOutput(entity);
    }

    @Override
    @Transactional
    public CourseOutput update(String id, CourseInput input) {
        validateInput(input);
        Course entity = courseRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setName(input.getName());
        entity.setCourseId(input.getCourseId());
        entity.setPeriod(input.getPeriod());
        entity.setName(input.getName());
        entity.setHours(input.getHours());
        entity.setProgram(input.getProgram());
        Course course = courseRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.Course, gson.toJson(entity));
        return toCourseOutput(course);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Course entity = courseRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        courseRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.Course, gson.toJson(entity));
    }

    private void validateInput(CourseInput input){
        if (input == null){
            throw new ServiceValidationException("Invalid contrct");
        }
        if (isBlank(input.getName())){
            throw new ServiceValidationException("Name should be informed");
        }
        if (input.getName().length() > NAME_MAX_LENGTH){
            throw new ServiceValidationException("Name too long");
        }
        if (isBlank(input.getPeriod())){
            throw new ServiceValidationException("Period should be informed");
        }
        if (isBlank(input.getCourseId())){
            throw new ServiceValidationException("CourseId should be informed");
        }
        if (input.getHours() == null || input.getHours() <= 0){
            throw new ServiceValidationException("Hours should be a positive number");
        }
        if (isBlank(input.getProgram())){
            throw new ServiceValidationException("Program should be informed");
        }
    }

    private CourseOutput toCourseOutput(Course entity){
        return new CourseOutput(entity.getId(), entity.getCourseId(), entity.getPeriod(), entity.getName(), entity.getHours(), entity.getProgram());
    }
}
