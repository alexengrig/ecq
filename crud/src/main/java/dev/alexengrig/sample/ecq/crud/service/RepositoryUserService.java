package dev.alexengrig.sample.ecq.crud.service;

import dev.alexengrig.sample.ecq.crud.repository.UserRepository;
import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.domain.User;

import java.util.HashSet;
import java.util.Set;

public record RepositoryUserService(
        UserRepository repository
) implements UserService {

    @Override
    public User create(String userId, String firstName, String lastName) {
        User newUser = new User(userId, firstName, lastName, new HashSet<>(), new HashSet<>());
        return repository.save(newUser);
    }

    @Override
    public User update(String userId, Set<Contact> contacts, Set<Address> addresses) {
        User user = repository.findById(userId);
        User updatedUser = new User(userId, user.firstName(), user.lastName(), contacts, addresses);
        return repository.save(updatedUser);
    }

}
