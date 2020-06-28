package br.com.grupoa.academic.service;

import br.com.grupoa.academic.model.event.EventEntity;
import br.com.grupoa.academic.model.event.EventType;
import br.com.grupoa.academic.contract.v1.exception.NotFoundException;
import br.com.grupoa.academic.model.MatriculaAluno;
import br.com.grupoa.academic.repository.MatriculaAlunoRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatriculaAlunoService {

    private static final String NOT_FOUND_MESSAGE = "MatriculaAluno not found";
    private static final Gson gson = new Gson();
    private final MatriculaAlunoRepository matriculaAlunoRepository;
    private final KafkaService kafkaService;

    @Autowired
    public MatriculaAlunoService(MatriculaAlunoRepository matriculaAlunoRepository,
                                 KafkaService kafkaService){
        this.matriculaAlunoRepository = matriculaAlunoRepository;
        this.kafkaService = kafkaService;
    }

    public List<MatriculaAluno> getAll(){
        return matriculaAlunoRepository.findAll();
    }

    public MatriculaAluno getById(String id) {
        return matriculaAlunoRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    public MatriculaAluno create(MatriculaAluno input) {
        MatriculaAluno entity = matriculaAlunoRepository.save(input);
        kafkaService.publishEvent(EventType.CREATE, EventEntity.MATRICULA_ALUNO, entity.getCodigoAluno(), entity);
        return entity;
    }

    @Transactional
    public MatriculaAluno update(String id, MatriculaAluno input) {
        MatriculaAluno entity = matriculaAlunoRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setCodigoAluno(input.getCodigoAluno());
        entity.setPeriodoLetivo(input.getPeriodoLetivo());
        entity.setCurso(input.getCurso());
        entity.setGrade(input.getGrade());
        input.setPeriodo(input.getPeriodo());
        input.setTurmasDisciplina(input.getTurmasDisciplina());
        MatriculaAluno matriculaAluno = matriculaAlunoRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.MATRICULA_ALUNO, matriculaAluno.getCodigoAluno(), matriculaAluno);
        return matriculaAluno;
    }

    @Transactional
    public void delete(String id) {
        MatriculaAluno entity = matriculaAlunoRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        matriculaAlunoRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.MATRICULA_ALUNO, id, new MatriculaAluno(id));
    }

}
