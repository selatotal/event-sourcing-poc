package br.com.grupoa.avaliaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.MatriculaAluno;
import br.com.grupoa.academic.model.event.Event;
import br.com.grupoa.academic.model.event.EventType;
import br.com.grupoa.avaliaservice.model.MatriculaAlunoEntity;
import br.com.grupoa.avaliaservice.repository.MatriculaAlunoRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;

public class ProcessMatriculaAlunoEvent implements ProcessEvent<MatriculaAlunoEntity> {

    private static final Logger logger = LoggerFactory.getLogger(ProcessMatriculaAlunoEvent.class);
    private final Gson gson = new Gson();
    private final MatriculaAlunoRepository repository;

    public ProcessMatriculaAlunoEvent(MatriculaAlunoRepository repository) {
        this.repository = repository;
    }

    @Override
    public MatriculaAlunoEntity convertToEntity(Event event) {
        if (event.getPayload() == null){
            return null;
        }
        MatriculaAluno matriculaAluno = gson.fromJson(event.getPayload(), MatriculaAluno.class);
        return new MatriculaAlunoEntity(
                matriculaAluno.getIdMatricula(),
                matriculaAluno.getCodigoAluno(),
                matriculaAluno.getPeriodoLetivo(),
                matriculaAluno.getCurso(),
                matriculaAluno.getGrade(),
                matriculaAluno.getPeriodo(),
                matriculaAluno.getTurmasDisciplina());
    }

    @Override
    public void processEvent(Event event) {
        MatriculaAlunoEntity entity = convertToEntity(event);
        switch (event.getType()){
            case CREATE:
            case UPDATE:
                repository.save(entity);
                logger.info("MatriculaAluno Saved: " + gson.toJson(entity));
                break;
            case DELETE:
                repository.deleteById(entity.getIdMatricula());
                logger.info("MatriculaAluno Removed: " + gson.toJson(entity));
                break;
            default:
                logger.error(INVALID_EVENT_TYPE_MESSAGE, event.getType());
        }

    }
}
