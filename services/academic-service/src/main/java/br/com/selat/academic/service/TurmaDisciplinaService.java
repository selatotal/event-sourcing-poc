package br.com.selat.academic.service;

import br.com.selat.academic.contract.v1.event.EventEntity;
import br.com.selat.academic.contract.v1.event.EventType;
import br.com.selat.academic.contract.v1.exception.NotFoundException;
import br.com.selat.academic.model.TurmaDisciplina;
import br.com.selat.academic.repository.TurmaDisciplinaRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurmaDisciplinaService {

    private static final String NOT_FOUND_MESSAGE = "TurmaDisciplina not found";
    private final Gson gson = new Gson();
    private final KafkaService kafkaService;
    private final TurmaDisciplinaRepository turmaDisciplinaRepository;

    @Autowired
    public TurmaDisciplinaService(KafkaService kafkaService, TurmaDisciplinaRepository turmaDisciplinaRepository){
        this.kafkaService = kafkaService;
        this.turmaDisciplinaRepository = turmaDisciplinaRepository;
    }

    public TurmaDisciplina getById(String id) {
        return turmaDisciplinaRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    public TurmaDisciplina create(TurmaDisciplina input) {
        TurmaDisciplina entity = turmaDisciplinaRepository.save(input);
        kafkaService.publishEvent(EventType.CREATE, EventEntity.GRADE_DISCIPLINA, gson.toJson(entity));
        return entity;
    }

    @Transactional
    public TurmaDisciplina update(String id, TurmaDisciplina input) {
        TurmaDisciplina entity = turmaDisciplinaRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setCodigoTurma(input.getCodigoTurma());
        entity.setNomeTurma(input.getNomeTurma());
        entity.setCodigoGradeDisciplina(input.getCodigoGradeDisciplina());
        entity.setInicio(input.getInicio());
        entity.setFim(input.getFim());
        entity.setProfessores(input.getProfessores());
        entity = turmaDisciplinaRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.GRADE_DISCIPLINA, gson.toJson(entity));
        return entity;
    }

    @Transactional
    public void delete(String id) {
        TurmaDisciplina entity = turmaDisciplinaRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        turmaDisciplinaRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.GRADE_DISCIPLINA, gson.toJson(entity));
    }

}
