package dev.alexengrig.sample.ecq;

import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.domain.User;

import java.util.Collections;
import java.util.Set;

public abstract class UserFlow {

    public void run() {
        User user = createUser("foo-bar", "Foo", "Bar");
        System.out.println("Created user: " + user);
        User updatedUser = updateUser(user.id(),
                Collections.singleton(new Contact("family", "mother")),
                Collections.singleton(new Address("New York", "New York", "abc"))
        );
        System.out.println("Updated user: " + updatedUser);
        Set<Contact> contacts = getContacts(user.id(), "family");
        System.out.println("Contacts: " + contacts);
        Set<Address> addresses = getAddresses(user.id(), "New York");
        System.out.println("Addresses: " + addresses);
    }

    protected abstract User createUser(String userId, String firstName, String lastName);

    protected abstract User updateUser(String userId, Set<Contact> contacts, Set<Address> addresses);

    protected abstract Set<Contact> getContacts(String userId, String type);

    protected abstract Set<Address> getAddresses(String userId, String state);

}
