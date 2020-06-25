package br.com.grupoa.academic.service;

import br.com.grupoa.academic.contract.v1.event.EventEntity;
import br.com.grupoa.academic.contract.v1.event.EventType;
import br.com.grupoa.academic.contract.v1.exception.NotFoundException;
import br.com.grupoa.academic.model.MatriculaProfessor;
import br.com.grupoa.academic.repository.MatriculaProfessorRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatriculaProfessorService {

    private static final String NOT_FOUND_MESSAGE = "MatriculaProfessor not found";
    private static final Gson gson = new Gson();
    private final MatriculaProfessorRepository matriculaProfessorRepository;
    private final KafkaService kafkaService;

    @Autowired
    public MatriculaProfessorService(MatriculaProfessorRepository matriculaProfessorRepository,
                                     KafkaService kafkaService){
        this.matriculaProfessorRepository = matriculaProfessorRepository;
        this.kafkaService = kafkaService;
    }

    public List<MatriculaProfessor> getAll(){
        return matriculaProfessorRepository.findAll();
    }

    public MatriculaProfessor getById(String id) {
        return matriculaProfessorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    public MatriculaProfessor create(MatriculaProfessor input) {
        MatriculaProfessor entity = matriculaProfessorRepository.save(input);
        kafkaService.publishEvent(EventType.CREATE, EventEntity.MATRICULA_PROFESSOR, entity.getIdMatricula(), entity);
        return entity;
    }

    @Transactional
    public MatriculaProfessor update(String id, MatriculaProfessor input) {
        MatriculaProfessor entity = matriculaProfessorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setCodigoProfessor(input.getCodigoProfessor());
        entity.setTurmasDisciplina(input.getTurmasDisciplina());
        MatriculaProfessor matriculaProfessor = matriculaProfessorRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.MATRICULA_PROFESSOR, entity.getIdMatricula(), matriculaProfessor);
        return matriculaProfessor;
    }

    @Transactional
    public void delete(String id) {
        MatriculaProfessor entity = matriculaProfessorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        matriculaProfessorRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.MATRICULA_PROFESSOR, id, null);
    }

}
