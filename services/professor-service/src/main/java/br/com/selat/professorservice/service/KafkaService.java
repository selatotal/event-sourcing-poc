package br.com.selat.professorservice.service;

import br.com.selat.professorservice.contract.v1.event.Event;
import br.com.selat.professorservice.contract.v1.event.EventEntity;
import br.com.selat.professorservice.contract.v1.event.EventType;
import br.com.selat.professorservice.contract.v1.exception.InternalErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Value("${kafka.eventTopicName}")
    private String kafkaEventTopicName;

    @Value("${kafka.publishTimeout}")
    private long kafkaPublishTimeout;

    private final KafkaTemplate<String, Event> kafkaTemplate;

    @Autowired
    public KafkaService(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public synchronized void publishEvent(EventType eventType, EventEntity eventEntity, String payload) {
        Event event = new Event(eventType, eventEntity, payload);

        ListenableFuture<SendResult<String, Event>> future = kafkaTemplate.send(kafkaEventTopicName, event);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Event>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error("Error sending message to kafka: ", ex);
            }

            @Override
            public void onSuccess(SendResult<String, Event> result) {
                logger.info("Message sent to kafka");
            }
        });

        try {
            future.get(kafkaPublishTimeout, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            throw new InternalErrorException("Error publishing Kafka", e);
        }
    }

}
