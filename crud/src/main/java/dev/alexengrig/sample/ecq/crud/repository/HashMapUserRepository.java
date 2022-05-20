package dev.alexengrig.sample.ecq.crud.repository;

import dev.alexengrig.sample.ecq.domain.User;

import java.util.HashMap;
import java.util.Map;

public class HashMapUserRepository implements UserRepository {

    private final Map<String, User> userById = new HashMap<>();

    @Override
    public User save(User user) {
        userById.put(user.id(), user);
        return user;
    }

    @Override
    public User findById(String userId) {
        return userById.get(userId);
    }

    @Override
    public User deleteById(String userId) {
        return userById.remove(userId);
    }

}
