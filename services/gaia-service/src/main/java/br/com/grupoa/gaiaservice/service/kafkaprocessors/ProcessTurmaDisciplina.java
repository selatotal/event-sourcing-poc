package br.com.grupoa.gaiaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.TurmaDisciplina;
import br.com.grupoa.gaiaservice.repository.TurmaDisciplinaRepository;
import br.com.grupoa.gaiaservice.model.TurmaDisciplinaEntity;
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
public class ProcessTurmaDisciplina {

    private static final Logger logger = LoggerFactory.getLogger(ProcessTurmaDisciplina.class);
    private final Gson gson = new Gson();
    private final TurmaDisciplinaRepository repository;

    public ProcessTurmaDisciplina(TurmaDisciplinaRepository repository) {
        this.repository = repository;
    }

    private TurmaDisciplinaEntity convertToEntity(TurmaDisciplina turmaDisciplina) {
        if (turmaDisciplina == null){
            return null;
        }
        return new TurmaDisciplinaEntity(
                turmaDisciplina.getCodigo(),
                turmaDisciplina.getCodigoTurma(),
                turmaDisciplina.getNomeTurma(),
                turmaDisciplina.getCodigoGradeDisciplina(),
                turmaDisciplina.getInicio(),
                turmaDisciplina.getFim(),
                turmaDisciplina.getProfessores());
    }

    @KafkaListener(topics = "TABLE_TURMA_DISCIPLINA")
    @Transactional
    public void processEvent(TurmaDisciplina turmaDisciplina, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        if (isEmpty(turmaDisciplina.getCodigo())){
            repository.deleteById(key);
            logger.info("TurmaDisciplina Removed: {}", key);
        } else {
            repository.save(convertToEntity(turmaDisciplina));
            String payload = gson.toJson(turmaDisciplina);
            logger.info("TurmaDisciplina Saved: {}", payload);
        }
    }
}
