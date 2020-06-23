package br.com.selat.classroomservice.service;

import br.com.selat.classroomservice.contract.v1.ClassroomService;
import br.com.selat.classroomservice.contract.v1.event.EventEntity;
import br.com.selat.classroomservice.contract.v1.event.EventType;
import br.com.selat.classroomservice.contract.v1.exception.NotFoundException;
import br.com.selat.classroomservice.contract.v1.exception.ServiceValidationException;
import br.com.selat.classroomservice.contract.v1.input.ClassroomInput;
import br.com.selat.classroomservice.contract.v1.output.ClassroomOutput;
import br.com.selat.classroomservice.model.Classroom;
import br.com.selat.classroomservice.repository.ClassroomRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    private static final String NOT_FOUND_MESSAGE = "Student not found";
    private static final int NAME_MAX_LENGTH = 255;
    private final Gson gson = new Gson();
    private final ClassroomRepository studentRepository;
    private final KafkaService kafkaService;

    @Autowired
    public ClassroomServiceImpl(ClassroomRepository studentRepository, KafkaService kafkaService){
        this.studentRepository = studentRepository;
        this.kafkaService = kafkaService;
    }

    @Override
    public ClassroomOutput getById(String id) {
        Classroom entity = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        return toClassroomOutput(entity);
    }

    @Override
    @Transactional
    public ClassroomOutput create(ClassroomInput input) {
        validateInput(input);
        Classroom entity = studentRepository.save(new Classroom(UUID.randomUUID().toString(), input.getName()));
        kafkaService.publishEvent(EventType.CREATE, EventEntity.Classroom, gson.toJson(entity));
        return toClassroomOutput(entity);
    }

    @Override
    @Transactional
    public ClassroomOutput update(String id, ClassroomInput input) {
        validateInput(input);
        Classroom entity = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setName(input.getName());
        Classroom classroom = studentRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.Classroom, gson.toJson(classroom));
        return toClassroomOutput(classroom);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Classroom entity = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        studentRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.Classroom, gson.toJson(entity));
    }

    private void validateInput(ClassroomInput input){
        if (input == null){
            throw new ServiceValidationException("Invalid contrct");
        }
        if (isBlank(input.getName())){
            throw new ServiceValidationException("Name should be informed");
        }
        if (input.getName().length() > NAME_MAX_LENGTH){
            throw new ServiceValidationException("Name too long");
        }
    }

    private ClassroomOutput toClassroomOutput(Classroom student){
        return new ClassroomOutput(student.getId(), student.getName());
    }
}
