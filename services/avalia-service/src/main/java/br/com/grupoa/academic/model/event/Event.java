package br.com.grupoa.academic.model.event;

public class Event {
    private EventType type;
    private EventEntity entity;
    private String payload;

    public Event() {
    }

    public Event(EventType type, EventEntity entity, String payload) {
        this.type = type;
        this.entity = entity;
        this.payload = payload;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventEntity getEntity() {
        return entity;
    }

    public void setEntity(EventEntity entity) {
        this.entity = entity;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
