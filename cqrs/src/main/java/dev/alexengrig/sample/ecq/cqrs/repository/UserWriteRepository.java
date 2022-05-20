package dev.alexengrig.sample.ecq.cqrs.repository;

import dev.alexengrig.sample.ecq.domain.User;

import java.util.HashMap;
import java.util.Map;

public class UserWriteRepository {

    private final Map<String, User> userById = new HashMap<>();

    public User save(User user) {
        userById.put(user.id(), user);
        return user;
    }

    public User findById(String userId) {
        return userById.get(userId);
    }

}
