package br.com.grupoa.gaiaservice.service.kafkaprocessors;

import br.com.grupoa.academic.model.GradeDisciplina;
import br.com.grupoa.gaiaservice.repository.GradeDisciplinaRepository;
import br.com.grupoa.gaiaservice.model.GradeDisciplinaEntity;
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
public class ProcessGradeDisciplina {

    private static final Logger logger = LoggerFactory.getLogger(ProcessGradeDisciplina.class);
    private final Gson gson = new Gson();
    private final GradeDisciplinaRepository repository;

    public ProcessGradeDisciplina(GradeDisciplinaRepository repository) {
        this.repository = repository;
    }

    private GradeDisciplinaEntity convertToEntity(GradeDisciplina gradeDisciplina) {
        if (gradeDisciplina == null){
            return null;
        }
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

    @KafkaListener(topics = "TABLE_GRADE_DISCIPLINA")
    @Transactional
    public void processEvent(GradeDisciplina gradeDisciplina, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        if (isEmpty(gradeDisciplina.getCodigoGradeDisciplina())) {
            repository.deleteById(key);
            logger.info("GradeDisciplina Removed: {}", key);
        } else {
            repository.save(convertToEntity(gradeDisciplina));
            String payload = gson.toJson(gradeDisciplina);
            logger.info("GradeDisciplina Saved: {}", payload);
        }
    }
}
