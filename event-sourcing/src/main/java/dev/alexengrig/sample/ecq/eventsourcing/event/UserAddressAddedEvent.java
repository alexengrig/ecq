package dev.alexengrig.sample.ecq.eventsourcing.event;

public final class UserAddressAddedEvent extends Event {

    private final String city;
    private final String state;
    private final String postCode;

    public UserAddressAddedEvent(String city, String state, String postCode) {
        this.city = city;
        this.state = state;
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostCode() {
        return postCode;
    }

}
