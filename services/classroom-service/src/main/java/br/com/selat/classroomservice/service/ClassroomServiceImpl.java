package br.com.selat.classroomservice.service;

import br.com.selat.classroomservice.contract.v1.ClassroomService;
import br.com.selat.classroomservice.contract.v1.event.EventEntity;
import br.com.selat.classroomservice.contract.v1.event.EventType;
import br.com.selat.classroomservice.contract.v1.exception.NotFoundException;
import br.com.selat.classroomservice.contract.v1.exception.ServiceValidationException;
import br.com.selat.classroomservice.contract.v1.input.ClassroomInput;
import br.com.selat.classroomservice.contract.v1.output.ClassroomOutput;
import br.com.selat.classroomservice.model.Classroom;
import br.com.selat.classroomservice.model.Professor;
import br.com.selat.classroomservice.repository.ClassroomRepository;
import br.com.selat.classroomservice.repository.ProfessorRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    private static final String NOT_FOUND_MESSAGE = "Student not found";
    private static final int NAME_MAX_LENGTH = 255;
    private final Gson gson = new Gson();
    private final ClassroomRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final KafkaService kafkaService;

    @Autowired
    public ClassroomServiceImpl(ClassroomRepository studentRepository, ProfessorRepository professorRepository, KafkaService kafkaService){
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.kafkaService = kafkaService;
    }

    @Override
    public ClassroomOutput getById(String id) {
        Classroom entity = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setProfessorList(entity.getProfessors().stream().map(Professor::getId).collect(Collectors.toSet()));
        return toClassroomOutput(entity);
    }

    @Override
    @Transactional
    public ClassroomOutput create(ClassroomInput input) {
        validateInput(input);
        Set<Professor> professors = input.getProfessors().stream().map(professorId -> professorRepository.findById(professorId).orElseThrow(NotFoundException::new)).collect(Collectors.toSet());
        Classroom entity = studentRepository.save(new Classroom(UUID.randomUUID().toString(), input.getName(), input.getDateStart(), input.getDateEnd(), professors));
        entity.setProfessorList(professors.stream().map(Professor::getId).collect(Collectors.toSet()));
        kafkaService.publishEvent(EventType.CREATE, EventEntity.Classroom, gson.toJson(entity));
        return toClassroomOutput(entity);
    }

    @Override
    @Transactional
    public ClassroomOutput update(String id, ClassroomInput input) {
        validateInput(input);
        Set<Professor> professors = input.getProfessors().stream().map(professorId -> professorRepository.findById(professorId).orElseThrow(NotFoundException::new)).collect(Collectors.toSet());
        Classroom entity = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setName(input.getName());
        entity.setDateStart(input.getDateStart());
        entity.setDateEnd(input.getDateEnd());
        entity.setProfessors(professors);
        entity.setProfessorList(professors.stream().map(Professor::getId).collect(Collectors.toSet()));
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

    private ClassroomOutput toClassroomOutput(Classroom entity){
        return new ClassroomOutput(entity.getId(), entity.getName(), entity.getDateStart(), entity.getDateEnd(), entity.getProfessorList());
    }
}
