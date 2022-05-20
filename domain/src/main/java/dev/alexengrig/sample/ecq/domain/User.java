package dev.alexengrig.sample.ecq.domain;

import java.util.Set;

public record User(
        String id,
        String firstName,
        String lastName,
        Set<Contact> contacts,
        Set<Address> addresses
) {
}
