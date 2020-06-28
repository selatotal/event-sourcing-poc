package br.com.grupoa.avaliaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.GradeDisciplina;
import br.com.grupoa.avaliaservice.contract.v1.event.Event;
import br.com.grupoa.avaliaservice.contract.v1.event.EventType;
import br.com.grupoa.avaliaservice.model.GradeDisciplinaEntity;
import br.com.grupoa.avaliaservice.repository.GradeDisciplinaRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class ProcessGradeDisciplinaEvent implements ProcessEvent<GradeDisciplinaEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ProcessGradeDisciplinaEvent.class);
    private final Gson gson = new Gson();
    private final GradeDisciplinaRepository repository;

    public ProcessGradeDisciplinaEvent(GradeDisciplinaRepository repository) {
        this.repository = repository;
    }

    @Override
    public GradeDisciplinaEntity convertToEntity(Event event) {
        if (event.getPayload() == null){
            return null;
        }
        GradeDisciplina gradeDisciplina = gson.fromJson(event.getPayload(), GradeDisciplina.class);
        return new GradeDisciplinaEntity(
                gradeDisciplina.getCodigoGradeDisciplina(),
                gradeDisciplina.getCodigoGrade(),
                gradeDisciplina.getCodigoDisciplina(),
                gradeDisciplina.getPeriodo(),
                gradeDisciplina.getNomeGrade(),
                gradeDisciplina.getNomeDisciplina(),
                gradeDisciplina.getCargaHoraria(),
                gradeDisciplina.getCodigoCurso(),
                gradeDisciplina.getNomeCurso());
    }

    @Override
    public void processEvent(Event event) {
        GradeDisciplinaEntity entity = convertToEntity(event);
        if (entity == null && !EventType.DELETE.equals(event.getType())){
            logger.error(format("Empty GradeDisciplina in event: %s", gson.toJson(event)));
            return;
        }
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                repository.save(entity);
                logger.info("GradeDisciplina Saved: " + gson.toJson(entity));
                break;
            case DELETE:
                repository.delete(entity);
                logger.info("GradeDisciplina Removed: " + gson.toJson(entity));
                break;
            default:
                logger.error(format(INVALID_EVENT_TYPE_MESSAGE, event.getType()));
        }

    }
}
