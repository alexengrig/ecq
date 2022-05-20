package dev.alexengrig.sample.ecq.eventsourcing.repository;

import dev.alexengrig.sample.ecq.eventsourcing.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserEventRepository {

    private final Map<String, List<Event>> events = new HashMap<>();

    public void push(String userId, Event event) {
        events.computeIfAbsent(userId, k -> new ArrayList<>()).add(event);
    }

    public List<Event> events(String userId) {
        return events.get(userId);
    }

}
