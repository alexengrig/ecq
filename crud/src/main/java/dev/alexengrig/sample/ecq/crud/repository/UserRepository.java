package dev.alexengrig.sample.ecq.crud.repository;

import dev.alexengrig.sample.ecq.domain.User;

public interface UserRepository {

    User save(User user);

    User findById(String userId);

    User deleteById(String userId);

}
