package br.com.grupoa.academic.service;

import br.com.grupoa.academic.contract.v1.event.EventEntity;
import br.com.grupoa.academic.contract.v1.event.EventType;
import br.com.grupoa.academic.model.Professor;
import br.com.grupoa.academic.contract.v1.exception.NotFoundException;
import br.com.grupoa.academic.repository.ProfessorRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorService {

    private static final String NOT_FOUND_MESSAGE = "Professor not found";
    private static final Gson gson = new Gson();
    private final ProfessorRepository professorRepository;
    private final KafkaService kafkaService;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository,
                            KafkaService kafkaService){
        this.professorRepository = professorRepository;
        this.kafkaService = kafkaService;
    }

    public List<Professor> getAll(){
        return professorRepository.findAll();
    }

    public Professor getById(String id) {
        return professorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    public Professor create(Professor input) {
        Professor professor = professorRepository.save(input);
        kafkaService.publishEvent(EventType.CREATE, EventEntity.PROFESSOR, professor.getCodigo(), professor);
        return professor;
    }

    @Transactional
    public Professor update(String id, Professor input) {
        Professor entity = professorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setEmail(input.getEmail());
        entity.setNome(input.getNome());
        Professor professor = professorRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.PROFESSOR, professor.getCodigo(), professor);
        return professor;
    }

    @Transactional
    public void delete(String id) {
        Professor entity = professorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        professorRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.PROFESSOR, id, null);
    }

}
