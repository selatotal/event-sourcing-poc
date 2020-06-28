package br.com.grupoa.avaliaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.MatriculaProfessor;
import br.com.grupoa.avaliaservice.contract.v1.event.Event;
import br.com.grupoa.avaliaservice.contract.v1.event.EventType;
import br.com.grupoa.avaliaservice.model.MatriculaProfessorEntity;
import br.com.grupoa.avaliaservice.repository.MatriculaProfessorRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class ProcessMatriculaProfessorEvent implements ProcessEvent<MatriculaProfessorEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ProcessMatriculaProfessorEvent.class);
    private final Gson gson = new Gson();
    private final MatriculaProfessorRepository repository;

    public ProcessMatriculaProfessorEvent(MatriculaProfessorRepository repository) {
        this.repository = repository;
    }

    @Override
    public MatriculaProfessorEntity convertToEntity(Event event) {
        if (event.getPayload() == null){
            return null;
        }
        MatriculaProfessor matriculaProfessor = gson.fromJson(event.getPayload(), MatriculaProfessor.class);
        return new MatriculaProfessorEntity(
                matriculaProfessor.getIdMatricula(),
                matriculaProfessor.getCodigoProfessor(),
                matriculaProfessor.getTurmasDisciplina());
    }

    @Override
    public void processEvent(Event event) {
        MatriculaProfessorEntity entity = convertToEntity(event);
        if (entity == null && !EventType.DELETE.equals(event.getType())){
            logger.error(format("Empty MatriculaProfessor in event: %s", gson.toJson(event)));
            return;
        }
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                repository.save(entity);
                logger.info("MatriculaProfessor Saved: " + gson.toJson(entity));
                break;
            case DELETE:
                repository.delete(entity);
                logger.info("MatriculaProfessor Removed: " + gson.toJson(entity));
                break;
            default:
                logger.error(format(INVALID_EVENT_TYPE_MESSAGE, event.getType()));
        }

    }
}
