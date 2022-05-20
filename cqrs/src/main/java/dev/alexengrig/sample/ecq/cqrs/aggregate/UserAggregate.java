package dev.alexengrig.sample.ecq.cqrs.aggregate;

import dev.alexengrig.sample.ecq.cqrs.command.CreateUserCommand;
import dev.alexengrig.sample.ecq.cqrs.command.UpdateUserCommand;
import dev.alexengrig.sample.ecq.cqrs.repository.UserWriteRepository;
import dev.alexengrig.sample.ecq.domain.User;

import java.util.HashSet;

public record UserAggregate(
        UserWriteRepository repository
) {

    public User handleCreateUserCommand(CreateUserCommand command) {
        User user = new User(command.userId(), command.firstName(), command.lastName(), new HashSet<>(), new HashSet<>());
        return repository.save(user);
    }

    public User handleUpdateUserCommand(UpdateUserCommand command) {
        User user = repository.findById(command.userId());
        User updatedUser = new User(user.id(), user.firstName(), user.lastName(),
                command.contacts(), command.addresses());
        return repository.save(updatedUser);
    }

}
