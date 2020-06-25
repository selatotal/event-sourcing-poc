package br.com.selat.academic.service;

import br.com.selat.academic.contract.v1.event.EventEntity;
import br.com.selat.academic.contract.v1.event.EventType;
import br.com.selat.academic.contract.v1.exception.NotFoundException;
import br.com.selat.academic.model.Aluno;
import br.com.selat.academic.repository.AlunoRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlunoService {

    private static final String NOT_FOUND_MESSAGE = "Aluno not found";
    private static final Gson gson = new Gson();
    private final AlunoRepository alunoRepository;
    private final KafkaService kafkaService;

    @Autowired
    public AlunoService(AlunoRepository alunoRepository,
                        KafkaService kafkaService){
        this.alunoRepository = alunoRepository;
        this.kafkaService = kafkaService;
    }

    public List<Aluno> getAll(){
        return alunoRepository.findAll();
    }

    public Aluno getById(String id) {
        return alunoRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
    }

    @Transactional
    public Aluno create(Aluno input) {
        Aluno aluno = alunoRepository.save(input);
        kafkaService.publishEvent(EventType.CREATE, EventEntity.ALUNO, gson.toJson(aluno));
        return aluno;
    }

    @Transactional
    public Aluno update(String id, Aluno input) {
        Aluno entity = alunoRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        entity.setEmail(input.getEmail());
        entity.setNome(input.getNome());
        entity.setRa(input.getRa());
        Aluno aluno = alunoRepository.save(entity);
        kafkaService.publishEvent(EventType.UPDATE, EventEntity.ALUNO, gson.toJson(aluno));
        return aluno;
    }

    @Transactional
    public void delete(String id) {
        Aluno entity = alunoRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
        alunoRepository.delete(entity);
        kafkaService.publishEvent(EventType.DELETE, EventEntity.ALUNO, gson.toJson(entity));
    }

}
