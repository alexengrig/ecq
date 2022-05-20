package dev.alexengrig.sample.ecq.crud.service;

import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.domain.User;

import java.util.Set;

public interface UserService {

    User create(String userId, String firstName, String lastName);

    User update(String userId, Set<Contact> contacts, Set<Address> addresses);

}
