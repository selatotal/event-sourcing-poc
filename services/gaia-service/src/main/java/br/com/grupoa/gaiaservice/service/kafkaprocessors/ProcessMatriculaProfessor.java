package br.com.grupoa.gaiaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.MatriculaProfessor;
import br.com.grupoa.academic.model.event.Event;
import br.com.grupoa.academic.model.event.EventType;
import br.com.grupoa.gaiaservice.repository.MatriculaProfessorRepository;
import br.com.grupoa.gaiaservice.model.MatriculaProfessorEntity;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Component
public class ProcessMatriculaProfessor {

    private static final Logger logger = LoggerFactory.getLogger(ProcessMatriculaProfessor.class);
    private final Gson gson = new Gson();
    private final MatriculaProfessorRepository repository;

    public ProcessMatriculaProfessor(MatriculaProfessorRepository repository) {
        this.repository = repository;
    }

    private MatriculaProfessorEntity convertToEntity(MatriculaProfessor matriculaProfessor) {
        if (matriculaProfessor == null){
            return null;
        }
        return new MatriculaProfessorEntity(
                matriculaProfessor.getIdMatricula(),
                matriculaProfessor.getCodigoProfessor(),
                matriculaProfessor.getTurmasDisciplina());
    }

    @KafkaListener(topics = "TABLE_MATRICULA_PROFESSOR")
    @Transactional
    public void processEvent(MatriculaProfessor matriculaProfessor, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        if (matriculaProfessor == null){
            repository.deleteById(key);
            logger.info("MatriculaProfessor Removed: {}", key);
        } else {
            repository.save(convertToEntity(matriculaProfessor));
            logger.info("MatriculaProfessor Saved: {}", matriculaProfessor);
        }

    }
}
