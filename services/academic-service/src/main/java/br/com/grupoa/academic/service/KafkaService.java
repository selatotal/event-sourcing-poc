package br.com.grupoa.academic.service;

import br.com.grupoa.academic.contract.v1.event.Event;
import br.com.grupoa.academic.contract.v1.event.EventEntity;
import br.com.grupoa.academic.contract.v1.event.EventType;
import br.com.grupoa.academic.contract.v1.exception.InternalErrorException;
import br.com.grupoa.academic.contract.v1.exception.ServiceValidationException;
import br.com.grupoa.academic.model.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Value("${kafka.eventTopicName}")
    private String kafkaEventTopicName;

    @Value("${kafka.tableRootName}")
    private String kafkaTableRootName;

    @Value("${kafka.publishTimeout}")
    private long kafkaPublishTimeout;

    private final Map<EventEntity, PublishKafka<?>> sendMap;
    private final PublishKafka<Event> publishKafkaEvent;
    private final Gson gson = new Gson();

    @Autowired
    public KafkaService(KafkaTemplate<String, Event> kafkaTemplateEvent, KafkaTemplate<String, Aluno> kafkaTemplateAluno, KafkaTemplate<String, GradeDisciplina> kafkaTemplateGradeDisciplina, KafkaTemplate<String, MatriculaAluno> kafkaTemplateGradeMatriculaAluno, KafkaTemplate<String, MatriculaProfessor> kafkaTemplateGradeMatriculaProfessor, KafkaTemplate<String, Professor> kafkaTemplateProfessor, KafkaTemplate<String, TurmaDisciplina> kafkaTemplateTurmaDisciplina) {
        this.publishKafkaEvent = new PublishKafka<>(kafkaTemplateEvent);
        this.sendMap = new EnumMap<>(EventEntity.class);
        sendMap.put(EventEntity.ALUNO, new PublishKafka<>(kafkaTemplateAluno));
        sendMap.put(EventEntity.GRADE_DISCIPLINA, new PublishKafka<>(kafkaTemplateGradeDisciplina));
        sendMap.put(EventEntity.MATRICULA_ALUNO, new PublishKafka<>(kafkaTemplateGradeMatriculaAluno));
        sendMap.put(EventEntity.MATRICULA_PROFESSOR, new PublishKafka<>(kafkaTemplateGradeMatriculaProfessor));
        sendMap.put(EventEntity.PROFESSOR, new PublishKafka<>(kafkaTemplateProfessor));
        sendMap.put(EventEntity.TURMA_DISCIPLINA, new PublishKafka<>(kafkaTemplateTurmaDisciplina));
    }

    public synchronized void publishEvent(EventType eventType, EventEntity eventEntity, String entityKey, AcademicEntity payload) {
        Event event = new Event(eventType, eventEntity, gson.toJson(payload));
        publishKafkaEvent.publishEvent(kafkaEventTopicName, entityKey, event);

        PublishKafka publishKafka = Optional.ofNullable(sendMap.get(eventEntity)).orElseThrow(() -> new ServiceValidationException("Invalid entity"));
        publishKafka.publishEvent(kafkaTableRootName + "_" + eventEntity.name(), entityKey, payload);
    }

    class PublishKafka <T> {
        private final KafkaTemplate<String, T> kafkaTemplate;

        PublishKafka(KafkaTemplate<String, T> kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
        }

        public synchronized void publishEvent(String topicName, String key, T payload) {
            ListenableFuture<SendResult<String, T>> future = kafkaTemplate.send(topicName, key, payload);
            future.addCallback(new ListenableFutureCallback<>() {
                @Override
                public void onSuccess(SendResult<String, T> result) {
                    logger.info("Message sent to kafka");
                }

                @Override
                public void onFailure(Throwable ex) {
                    logger.error("Error sending message to kafka: ", ex);
                }
            });

            try {
                future.get(kafkaPublishTimeout, TimeUnit.MILLISECONDS);
            } catch (ExecutionException | TimeoutException | InterruptedException e) {
                throw new InternalErrorException("Error publishing Kafka", e);
            }
        }
    }

}
