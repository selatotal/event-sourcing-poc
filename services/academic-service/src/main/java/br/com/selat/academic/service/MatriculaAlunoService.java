package br.com.selat.academic.service;

import br.com.selat.academic.contract.v1.event.EventEntity;
import br.com.selat.academic.contract.v1.event.EventType;
import br.com.selat.academic.contract.v1.exception.NotFoundException;
import br.com.selat.academic.model.MatriculaAluno;
import br.com.selat.academic.repository.MatriculaAlunoRepository;
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
        MatriculaAluno aluno = matriculaAlunoRepository.save(input);
        kafkaService.publishEvent(EventType.CREATE, EventEntity.MATRICULA_ALUNO, gson.toJson(aluno));
        return aluno;
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
        MatriculaAluno aluno = matriculaAlunoRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.MATRICULA_ALUNO, gson.toJson(aluno));
        return aluno;
    }

    @Transactional
    public void delete(String id) {
        MatriculaAluno entity = matriculaAlunoRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        matriculaAlunoRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.MATRICULA_ALUNO, gson.toJson(entity));
    }

}
