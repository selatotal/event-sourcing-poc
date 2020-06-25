package br.com.selat.academic.service;

import br.com.selat.academic.contract.v1.event.EventEntity;
import br.com.selat.academic.contract.v1.event.EventType;
import br.com.selat.academic.contract.v1.exception.NotFoundException;
import br.com.selat.academic.model.MatriculaProfessor;
import br.com.selat.academic.repository.MatriculaProfessorRepository;
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
        MatriculaProfessor aluno = matriculaProfessorRepository.save(input);
        kafkaService.publishEvent(EventType.CREATE, EventEntity.MATRICULA_PROFESSOR, gson.toJson(aluno));
        return aluno;
    }

    @Transactional
    public MatriculaProfessor update(String id, MatriculaProfessor input) {
        MatriculaProfessor entity = matriculaProfessorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setCodigoProfessor(input.getCodigoProfessor());
        entity.setTurmasDisciplina(input.getTurmasDisciplina());
        MatriculaProfessor aluno = matriculaProfessorRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.MATRICULA_PROFESSOR, gson.toJson(aluno));
        return aluno;
    }

    @Transactional
    public void delete(String id) {
        MatriculaProfessor entity = matriculaProfessorRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        matriculaProfessorRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.MATRICULA_PROFESSOR, gson.toJson(entity));
    }

}
