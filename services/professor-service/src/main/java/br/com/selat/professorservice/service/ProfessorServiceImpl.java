package br.com.selat.professorservice.service;

import br.com.selat.professorservice.contract.v1.ProfessorService;
import br.com.selat.professorservice.contract.v1.event.EventEntity;
import br.com.selat.professorservice.contract.v1.event.EventType;
import br.com.selat.professorservice.contract.v1.exception.NotFoundException;
import br.com.selat.professorservice.contract.v1.exception.ServiceValidationException;
import br.com.selat.professorservice.contract.v1.input.ProfessorInput;
import br.com.selat.professorservice.contract.v1.output.ProfessorOutput;
import br.com.selat.professorservice.model.Professor;
import br.com.selat.professorservice.repository.ProfessorRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private static final String NOT_FOUND_MESSAGE = "Professor not found";
    private static final int NAME_MAX_LENGTH = 255;
    private static final Gson gson = new Gson();
    private final ProfessorRepository professorRepository;
    private final KafkaService kafkaService;

    @Autowired
    public ProfessorServiceImpl(ProfessorRepository professorRepository,
                                KafkaService kafkaService){
        this.professorRepository = professorRepository;
        this.kafkaService = kafkaService;
    }

    @Override
    public ProfessorOutput getById(String id) {
        Professor entity = professorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        return toProfessorOutput(entity);
    }

    @Override
    @Transactional
    public ProfessorOutput create(ProfessorInput input) {
        validateInput(input);
        Professor professor = professorRepository.save(new Professor(UUID.randomUUID().toString(), input.getName()));
        kafkaService.publishEvent(EventType.CREATE, EventEntity.Professor, gson.toJson(professor));
        return toProfessorOutput(professor);
    }

    @Override
    @Transactional
    public ProfessorOutput update(String id, ProfessorInput input) {
        validateInput(input);
        Professor entity = professorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setName(input.getName());
        Professor professor = professorRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.Professor, gson.toJson(professor));
        return toProfessorOutput(professor);
    }

    @Override
    @Transactional
    public void delete(String id) {
        Professor entity = professorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        professorRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.Professor, gson.toJson(entity));
    }

    private void validateInput(ProfessorInput input){
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

    private ProfessorOutput toProfessorOutput(Professor professor){
        return new ProfessorOutput(professor.getId(), professor.getName());
    }
}
