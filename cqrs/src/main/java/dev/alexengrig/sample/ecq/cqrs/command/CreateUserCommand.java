package dev.alexengrig.sample.ecq.cqrs.command;

public record CreateUserCommand(
        String userId,
        String firstName,
        String lastName
) {
}
