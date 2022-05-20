package dev.alexengrig.sample.ecq.eventsourcing.event;

public final class UserCreatedEvent extends Event {

    private final String userId;
    private final String firstName;
    private final String lastName;

    public UserCreatedEvent(String userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
