package dev.alexengrig.sample.ecq.eventsourcing.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public sealed abstract class Event permits
        UserAddressAddedEvent,
        UserAddressRemovedEvent,
        UserContactAddedEvent,
        UserContactRemovedEvent,
        UserCreatedEvent {

    private final UUID id = UUID.randomUUID();
    private final OffsetDateTime createdAt = OffsetDateTime.now();

    public UUID getId() {
        return id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

}
