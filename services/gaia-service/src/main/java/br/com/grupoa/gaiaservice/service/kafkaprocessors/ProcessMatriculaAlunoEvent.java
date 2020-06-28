package br.com.grupoa.gaiaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.MatriculaAluno;
import br.com.grupoa.academic.model.event.Event;
import br.com.grupoa.academic.model.event.EventType;
import br.com.grupoa.gaiaservice.repository.MatriculaAlunoRepository;
import br.com.grupoa.gaiaservice.model.MatriculaAlunoEntity;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ProcessMatriculaAlunoEvent {

    private static final Logger logger = LoggerFactory.getLogger(ProcessMatriculaAlunoEvent.class);
    private final Gson gson = new Gson();
    private final MatriculaAlunoRepository repository;

    public ProcessMatriculaAlunoEvent(MatriculaAlunoRepository repository) {
        this.repository = repository;
    }

    private MatriculaAlunoEntity convertToEntity(MatriculaAluno matriculaAluno) {
        if (matriculaAluno == null){
            return null;
        }
        return new MatriculaAlunoEntity(
                matriculaAluno.getIdMatricula(),
                matriculaAluno.getCodigoAluno(),
                matriculaAluno.getPeriodoLetivo(),
                matriculaAluno.getCurso(),
                matriculaAluno.getGrade(),
                matriculaAluno.getPeriodo(),
                matriculaAluno.getTurmasDisciplina());
    }

    @KafkaListener(topics = "TABLE_MATRICULA_ALUNO")
    @Transactional
    public void processEvent(MatriculaAluno matriculaAluno, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        if (isEmpty(matriculaAluno.getIdMatricula())){
            repository.deleteById(key);
            logger.info("MatriculaAluno Removed {}", key);
        } else {
            repository.save(convertToEntity(matriculaAluno));
            logger.info("MatriculaAluno Saved: {}", gson.toJson(matriculaAluno));
        }
    }
}
