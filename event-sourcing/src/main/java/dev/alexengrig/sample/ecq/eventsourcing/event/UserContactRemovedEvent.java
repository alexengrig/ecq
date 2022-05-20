package dev.alexengrig.sample.ecq.eventsourcing.event;

public final class UserContactRemovedEvent extends Event {

    private final String type;
    private final String details;

    public UserContactRemovedEvent(String type, String details) {
        this.type = type;
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

}
