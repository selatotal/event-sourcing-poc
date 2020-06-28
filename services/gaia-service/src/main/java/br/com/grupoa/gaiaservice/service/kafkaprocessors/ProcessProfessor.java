package br.com.grupoa.gaiaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.Professor;
import br.com.grupoa.gaiaservice.repository.ProfessorRepository;
import br.com.grupoa.gaiaservice.model.ProfessorEntity;
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
public class ProcessProfessor {

    private static final Logger logger = LoggerFactory.getLogger(ProcessProfessor.class);
    private final Gson gson = new Gson();
    private final ProfessorRepository repository;

    public ProcessProfessor(ProfessorRepository repository) {
        this.repository = repository;
    }

    private ProfessorEntity convertToEntity(Professor professor) {
        if (professor == null){
            return null;
        }
        return new ProfessorEntity(
                professor.getCodigo(),
                professor.getNome(),
                professor.getEmail());
    }

    @KafkaListener(topics = "TABLE_PROFESSOR")
    @Transactional
    public void processEvent(Professor professor, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        if (isEmpty(professor.getCodigo())){
            repository.deleteById(key);
            logger.info("Professor Removed: {}", key);
        } else {
            repository.save(convertToEntity(professor));
            String strProfessor = gson.toJson(professor);
            logger.info("Professor Saved: {}", strProfessor);
        }
    }
}
