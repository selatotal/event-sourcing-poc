package br.com.selat.studentservice.service;

import br.com.selat.studentservice.contract.v1.StudentService;
import br.com.selat.studentservice.contract.v1.event.EventEntity;
import br.com.selat.studentservice.contract.v1.event.EventType;
import br.com.selat.studentservice.contract.v1.exception.NotFoundException;
import br.com.selat.studentservice.contract.v1.exception.ServiceValidationException;
import br.com.selat.studentservice.contract.v1.input.StudentInput;
import br.com.selat.studentservice.contract.v1.output.StudentOutput;
import br.com.selat.studentservice.model.Student;
import br.com.selat.studentservice.repository.StudentRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class StudentServiceImpl implements StudentService {

    private static final String NOT_FOUND_MESSAGE = "Student not found";
    private static final int NAME_MAX_LENGTH = 255;
    private final Gson gson = new Gson();
    private final StudentRepository studentRepository;
    private final KafkaService kafkaService;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              KafkaService kafkaService){
        this.studentRepository = studentRepository;
        this.kafkaService = kafkaService;
    }

    @Override
    public StudentOutput getById(String id) {
        Student entity = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        return toStudentOutput(entity);
    }

    @Override
    @Transactional
    public StudentOutput create(StudentInput input) {
        validateInput(input);
        Student student = studentRepository.save(new Student(UUID.randomUUID().toString(), input.getName(), input.getEmail(), input.getRa()));
        kafkaService.publishEvent(EventType.CREATE, EventEntity.Student, gson.toJson(student));
        return toStudentOutput(student);
    }

    @Override
    @Transactional
    public StudentOutput update(String id, StudentInput input) {
        validateInput(input);
        Student entity = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setName(input.getName());
        Student student = studentRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.Student, gson.toJson(student));
        return toStudentOutput(student);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Student entity = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        kafkaService.publishEvent(EventType.DELETE, EventEntity.Student, gson.toJson(entity));
        studentRepository.delete(entity);
    }

    private void validateInput(StudentInput input){
        if (input == null){
            throw new ServiceValidationException("Invalid contrct");
        }
        if (isBlank(input.getName())){
            throw new ServiceValidationException("Name should be informed");
        }
        if (input.getName().length() > NAME_MAX_LENGTH){
            throw new ServiceValidationException("Name too long");
        }
        if (isBlank(input.getEmail())){
            throw new ServiceValidationException("Invalid email");
        }
        if (isBlank(input.getRa())){
            throw new ServiceValidationException("Invalid Ra");
        }
    }

    private StudentOutput toStudentOutput(Student student){
        return new StudentOutput(student.getId(), student.getName(), student.getEmail(), student.getRa());
    }
}
