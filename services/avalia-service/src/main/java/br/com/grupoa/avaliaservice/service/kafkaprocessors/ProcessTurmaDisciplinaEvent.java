package br.com.grupoa.avaliaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.TurmaDisciplina;
import br.com.grupoa.academic.model.event.Event;
import br.com.grupoa.avaliaservice.model.TurmaDisciplinaEntity;
import br.com.grupoa.avaliaservice.repository.TurmaDisciplinaRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessTurmaDisciplinaEvent implements ProcessEvent<TurmaDisciplinaEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ProcessTurmaDisciplinaEvent.class);
    private final Gson gson = new Gson();
    private final TurmaDisciplinaRepository repository;

    public ProcessTurmaDisciplinaEvent(TurmaDisciplinaRepository repository) {
        this.repository = repository;
    }

    @Override
    public TurmaDisciplinaEntity convertToEntity(Event event) {
        if (event.getPayload() == null){
            return null;
        }
        TurmaDisciplina turmaDisciplina = gson.fromJson(event.getPayload(), TurmaDisciplina.class);
        return new TurmaDisciplinaEntity(
                turmaDisciplina.getCodigo(),
                turmaDisciplina.getCodigoTurma(),
                turmaDisciplina.getNomeTurma(),
                turmaDisciplina.getCodigoGradeDisciplina(),
                turmaDisciplina.getInicio(),
                turmaDisciplina.getFim(),
                turmaDisciplina.getProfessores());
    }

    @Override
    public void processEvent(Event event) {
        TurmaDisciplinaEntity entity = convertToEntity(event);
        String payload = gson.toJson(entity);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                repository.save(entity);
                logger.info("TurmaDisciplina Saved: {}", payload);
                break;
            case DELETE:
                repository.deleteById(entity.getCodigo());
                logger.info("TurmaDisciplina Removed: {}", payload);
                break;
            default:
                logger.error(INVALID_EVENT_TYPE_MESSAGE, event.getType());
        }

    }
}
