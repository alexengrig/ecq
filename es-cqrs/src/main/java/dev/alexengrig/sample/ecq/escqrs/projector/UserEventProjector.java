package dev.alexengrig.sample.ecq.escqrs.projector;

import dev.alexengrig.sample.ecq.cqrs.repository.UserReadRepository;
import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.eventsourcing.event.Event;
import dev.alexengrig.sample.ecq.eventsourcing.event.UserAddressAddedEvent;
import dev.alexengrig.sample.ecq.eventsourcing.event.UserAddressRemovedEvent;
import dev.alexengrig.sample.ecq.eventsourcing.event.UserContactAddedEvent;
import dev.alexengrig.sample.ecq.eventsourcing.event.UserContactRemovedEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public record UserEventProjector(
        UserReadRepository repository
) {

    public void project(String userId, List<Event> events) {
        for (Event event : events) {
            if (event instanceof UserAddressAddedEvent)
                apply(userId, (UserAddressAddedEvent) event);
            if (event instanceof UserAddressRemovedEvent)
                apply(userId, (UserAddressRemovedEvent) event);
            if (event instanceof UserContactAddedEvent)
                apply(userId, (UserContactAddedEvent) event);
            if (event instanceof UserContactRemovedEvent)
                apply(userId, (UserContactRemovedEvent) event);
        }

    }

    public void apply(String userId, UserAddressAddedEvent event) {
        Address address = new Address(event.getCity(), event.getState(), event.getPostCode());
        Map<String, Set<Address>> userAddress = Optional.ofNullable(repository.getAddresses(userId))
                .orElse(new HashMap<>());
        Set<Address> addresses = Optional.ofNullable(userAddress.get(address.state()))
                .orElse(new HashSet<>());
        addresses.add(address);
        userAddress.put(address.state(), addresses);
        repository.putAddresses(userId, userAddress);
    }

    public void apply(String userId, UserAddressRemovedEvent event) {
        Address address = new Address(event.getCity(), event.getState(), event.getPostCode());
        Map<String, Set<Address>> userAddress = repository.getAddresses(userId);
        if (userAddress != null) {
            Set<Address> addresses = userAddress.get(address.state());
            if (addresses != null)
                addresses.remove(address);
            repository.putAddresses(userId, userAddress);
        }
    }

    public void apply(String userId, UserContactAddedEvent event) {
        Contact contact = new Contact(event.getType(), event.getDetails());
        Map<String, Set<Contact>> userContact = Optional.ofNullable(repository.getContacts(userId))
                .orElse(new HashMap<>());
        Set<Contact> contacts = Optional.ofNullable(userContact.get(contact.type()))
                .orElse(new HashSet<>());
        contacts.add(contact);
        userContact.put(contact.type(), contacts);
        repository.putContacts(userId, userContact);
    }

    public void apply(String userId, UserContactRemovedEvent event) {
        Contact contact = new Contact(event.getType(), event.getDetails());
        Map<String, Set<Contact>> userContact = repository.getContacts(userId);
        if (userContact != null) {
            Set<Contact> contacts = userContact.get(contact.type());
            if (contacts != null)
                contacts.remove(contact);
            repository.putContacts(userId, userContact);
        }
    }

}
