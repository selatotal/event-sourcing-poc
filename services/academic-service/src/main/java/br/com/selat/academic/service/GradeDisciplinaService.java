package br.com.selat.academic.service;

import br.com.selat.academic.contract.v1.event.EventEntity;
import br.com.selat.academic.contract.v1.event.EventType;
import br.com.selat.academic.contract.v1.exception.NotFoundException;
import br.com.selat.academic.model.GradeDisciplina;
import br.com.selat.academic.repository.GradeDisciplinaRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GradeDisciplinaService {

    private static final String NOT_FOUND_MESSAGE = "GradeDisciplina not found";
    private final Gson gson = new Gson();
    private final KafkaService kafkaService;
    private final GradeDisciplinaRepository gradeDisciplinaRepository;

    @Autowired
    public GradeDisciplinaService(KafkaService kafkaService, GradeDisciplinaRepository gradeDisciplinaRepository){
        this.kafkaService = kafkaService;
        this.gradeDisciplinaRepository = gradeDisciplinaRepository;
    }

    public GradeDisciplina getById(String id) {
        return gradeDisciplinaRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    public GradeDisciplina create(GradeDisciplina input) {
        GradeDisciplina entity = gradeDisciplinaRepository.save(input);
        kafkaService.publishEvent(EventType.CREATE, EventEntity.GRADE_DISCIPLINA, gson.toJson(entity));
        return entity;
    }

    @Transactional
    public GradeDisciplina update(String id, GradeDisciplina input) {
        GradeDisciplina entity = gradeDisciplinaRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setCodigoGrade(input.getCodigoGrade());
        entity.setCodigoDisciplina(input.getCodigoDisciplina());
        entity.setPeriodo(input.getPeriodo());
        entity.setNomeGrade(input.getNomeGrade());
        entity.setNomeDisciplina(input.getNomeDisciplina());
        entity.setCargaHoraria(input.getCargaHoraria());
        entity.setCodigoCurso(input.getCodigoCurso());
        entity.setNomeCurso(input.getNomeCurso());
        entity = gradeDisciplinaRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.GRADE_DISCIPLINA, gson.toJson(entity));
        return entity;
    }

    @Transactional
    public void delete(String id) {
        GradeDisciplina entity = gradeDisciplinaRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        gradeDisciplinaRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.GRADE_DISCIPLINA, gson.toJson(entity));
    }

}
