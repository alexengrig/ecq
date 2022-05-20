package dev.alexengrig.sample.ecq.crud.service;

import dev.alexengrig.sample.ecq.crud.repository.UserRepository;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.domain.User;

import java.util.Set;
import java.util.stream.Collectors;

public record RepositoryContactService(
        UserRepository repository
) implements ContactService {

    @Override
    public Set<Contact> getByType(String userId, String type) {
        User user = repository.findById(userId);
        return user.contacts()
                .stream()
                .filter(contact -> type.equals(contact.type()))
                .collect(Collectors.toSet());
    }

}
