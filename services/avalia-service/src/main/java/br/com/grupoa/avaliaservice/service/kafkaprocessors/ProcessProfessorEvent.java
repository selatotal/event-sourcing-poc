package br.com.grupoa.avaliaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.Professor;
import br.com.grupoa.academic.model.event.Event;
import br.com.grupoa.avaliaservice.model.ProfessorEntity;
import br.com.grupoa.avaliaservice.repository.ProfessorRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessProfessorEvent implements ProcessEvent<ProfessorEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ProcessProfessorEvent.class);
    private final Gson gson = new Gson();
    private final ProfessorRepository repository;

    public ProcessProfessorEvent(ProfessorRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProfessorEntity convertToEntity(Event event) {
        if (event.getPayload() == null){
            return null;
        }
        Professor professor = gson.fromJson(event.getPayload(), Professor.class);
        return new ProfessorEntity(
                professor.getCodigo(),
                professor.getNome(),
                professor.getEmail());
    }

    @Override
    public void processEvent(Event event) {
        ProfessorEntity entity = convertToEntity(event);
        String payload = gson.toJson(entity);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                repository.save(entity);
                logger.info("Professor Saved: {}", payload);
                break;
            case DELETE:
                repository.deleteById(entity.getCodigo());
                logger.info("Professor Removed: {}", payload);
                break;
            default:
                logger.error(INVALID_EVENT_TYPE_MESSAGE, event.getType());
        }

    }
}
