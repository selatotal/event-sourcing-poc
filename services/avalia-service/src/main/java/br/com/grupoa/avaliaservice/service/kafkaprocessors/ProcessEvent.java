package br.com.grupoa.avaliaservice.service.kafkaprocessors;

import br.com.grupoa.avaliaservice.contract.v1.event.Event;

public interface ProcessEvent<T> {

    String INVALID_EVENT_TYPE_MESSAGE = "Invalid Event Type: %s";

    T convertToEntity(Event event);

    void processEvent(Event event);
}
