package br.com.grupoa.gaiaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.Aluno;
import br.com.grupoa.gaiaservice.repository.AlunoRepository;
import br.com.grupoa.gaiaservice.model.AlunoEntity;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class ProcessAluno {

    private static final Logger logger = LoggerFactory.getLogger(ProcessAluno.class);
    private final Gson gson = new Gson();
    private final AlunoRepository repository;

    public ProcessAluno(AlunoRepository repository) {
        this.repository = repository;
    }

    private AlunoEntity convertToEntity(Aluno aluno) {
        if (aluno == null){
            return null;
        }
        return new AlunoEntity(
                aluno.getCodigo(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getRa());
    }

    @KafkaListener(topics = "TABLE_ALUNO")
    @Transactional
    public void processEvent(Aluno aluno, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        if (isEmpty(aluno.getCodigo())) {
            repository.deleteById(key);
            logger.info("Aluno Removed: {}", key);
        } else {
            repository.save(convertToEntity(aluno));
            String payload = gson.toJson(aluno);
            logger.info("Aluno Saved: {}", payload);
        }
    }
}
