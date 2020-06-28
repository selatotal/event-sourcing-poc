package br.com.grupoa.avaliaservice.service;

import br.com.grupoa.academic.model.event.Event;
import br.com.grupoa.academic.model.event.EventEntity;
import br.com.grupoa.avaliaservice.contract.v1.exception.ServiceValidationException;
import br.com.grupoa.avaliaservice.repository.*;
import br.com.grupoa.avaliaservice.service.kafkaprocessors.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@Component
public class KafkaListenerService {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);
    private final Gson gson = new Gson();
    private final Map<EventEntity, ProcessEvent> processEventMap;

    @Autowired
    public KafkaListenerService(AlunoRepository alunoRepository,
                                GradeDisciplinaRepository gradeDisciplinaRepository,
                                MatriculaAlunoRepository matriculaAlunoRepository,
                                MatriculaProfessorRepository matriculaProfessorRepository,
                                ProfessorRepository professorRepository,
                                TurmaDisciplinaRepository turmaDisciplinaRepository) {
        this.processEventMap = new EnumMap(EventEntity.class);
        this.processEventMap.put(EventEntity.ALUNO, new ProcessAlunoEvent(alunoRepository));
        this.processEventMap.put(EventEntity.GRADE_DISCIPLINA, new ProcessGradeDisciplinaEvent(gradeDisciplinaRepository));
        this.processEventMap.put(EventEntity.MATRICULA_ALUNO, new ProcessMatriculaAlunoEvent(matriculaAlunoRepository));
        this.processEventMap.put(EventEntity.MATRICULA_PROFESSOR, new ProcessMatriculaProfessorEvent(matriculaProfessorRepository));
        this.processEventMap.put(EventEntity.PROFESSOR, new ProcessProfessorEvent(professorRepository));
        this.processEventMap.put(EventEntity.TURMA_DISCIPLINA, new ProcessTurmaDisciplinaEvent(turmaDisciplinaRepository));
    }

    @KafkaListener(topics = "#{'${kafka.listenEventTopics}'.split(',')}")
    @Transactional
    public void listenEventTopic(Event event, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key){
        String payload = gson.toJson(event);
        logger.info("Message received: [{}] {}", key, payload);
        ProcessEvent processEvent = Optional.ofNullable(processEventMap.get(event.getEntity())).orElseThrow(() -> new ServiceValidationException(format("Invalid Event Entity: %s", event.getEntity())));
        processEvent.processEvent(event);
    }

}

