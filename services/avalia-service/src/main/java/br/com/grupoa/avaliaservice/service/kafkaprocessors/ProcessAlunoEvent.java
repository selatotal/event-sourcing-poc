package br.com.grupoa.avaliaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.Aluno;
import br.com.grupoa.academic.model.event.Event;
import br.com.grupoa.academic.model.event.EventType;
import br.com.grupoa.avaliaservice.model.AlunoEntity;
import br.com.grupoa.avaliaservice.repository.AlunoRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class ProcessAlunoEvent implements ProcessEvent<AlunoEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ProcessAlunoEvent.class);
    private final Gson gson = new Gson();
    private final AlunoRepository repository;

    public ProcessAlunoEvent(AlunoRepository repository) {
        this.repository = repository;
    }

    @Override
    public AlunoEntity convertToEntity(Event event) {
        if (event.getPayload() == null){
            return null;
        }
        Aluno aluno = gson.fromJson(event.getPayload(), Aluno.class);
        return new AlunoEntity(
                aluno.getCodigo(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getRa());
    }

    @Override
    public void processEvent(Event event) {
        AlunoEntity entity = convertToEntity(event);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                repository.save(entity);
                logger.info("Aluno Saved: {}", gson.toJson(entity));
                break;
            case DELETE:
                repository.deleteById(entity.getCodigo());
                logger.info("Aluno Removed: {}", gson.toJson(entity));
                break;
            default:
                logger.error(INVALID_EVENT_TYPE_MESSAGE, event.getType());
        }

    }
}
