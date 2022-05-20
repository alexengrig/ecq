package dev.alexengrig.sample.ecq.cqrs.command;

import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;

import java.util.Set;

public record UpdateUserCommand(
        String userId,
        Set<Contact> contacts,
        Set<Address> addresses
) {
}
