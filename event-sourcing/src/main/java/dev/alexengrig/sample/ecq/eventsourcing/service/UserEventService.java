package dev.alexengrig.sample.ecq.eventsourcing.service;

import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.domain.User;
import dev.alexengrig.sample.ecq.eventsourcing.event.Event;
import dev.alexengrig.sample.ecq.eventsourcing.event.UserAddressAddedEvent;
import dev.alexengrig.sample.ecq.eventsourcing.event.UserAddressRemovedEvent;
import dev.alexengrig.sample.ecq.eventsourcing.event.UserContactAddedEvent;
import dev.alexengrig.sample.ecq.eventsourcing.event.UserContactRemovedEvent;
import dev.alexengrig.sample.ecq.eventsourcing.event.UserCreatedEvent;
import dev.alexengrig.sample.ecq.eventsourcing.repository.UserEventRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record UserEventService(
        UserEventRepository repository
) {

    public void create(String userId, String firstName, String lastName) {
        UserCreatedEvent event = new UserCreatedEvent(userId, firstName, lastName);
        repository.push(userId, event);
    }

    public void update(String userId, Set<Contact> contacts, Set<Address> addresses) {
        User user = get(userId);
        user.contacts().stream()
                .filter(c -> !contacts.contains(c))
                .forEach(c -> repository.push(userId, new UserContactRemovedEvent(c.type(), c.detail())));
        contacts.stream()
                .filter(c -> !user.contacts().contains(c))
                .forEach(c -> repository.push(userId, new UserContactAddedEvent(c.type(), c.detail())));
        user.addresses().stream()
                .filter(a -> !addresses.contains(a))
                .forEach(a -> repository.push(userId, new UserAddressRemovedEvent(a.city(), a.state(), a.postcode())));
        addresses.stream()
                .filter(a -> !user.addresses().contains(a))
                .forEach(a -> repository.push(userId, new UserAddressAddedEvent(a.city(), a.state(), a.postcode())));
    }

    public User get(String userId) {
        User user = null;
        List<Event> events = repository.events(userId);
        for (Event event : events) {
            if (event instanceof UserCreatedEvent e) {
                user = new User(e.getUserId(), e.getFirstName(), e.getLastName(),
                        new HashSet<>(), new HashSet<>());
            }
            if (event instanceof UserAddressAddedEvent e) {
                Address address = new Address(e.getCity(), e.getState(), e.getPostCode());
                if (user != null)
                    user.addresses().add(address);
            }
            if (event instanceof UserAddressRemovedEvent e) {
                Address address = new Address(e.getCity(), e.getState(), e.getPostCode());
                if (user != null)
                    user.addresses().remove(address);
            }
            if (event instanceof UserContactAddedEvent e) {
                Contact contact = new Contact(e.getType(), e.getDetails());
                if (user != null)
                    user.contacts().add(contact);
            }
            if (event instanceof UserContactRemovedEvent e) {
                Contact contact = new Contact(e.getType(), e.getDetails());
                if (user != null)
                    user.contacts().remove(contact);
            }
        }
        return user;
    }

    public Set<Contact> getContactByType(String userId, String contactType) {
        User user = get(userId);
        return user.contacts().stream()
                .filter(c -> c.type().equals(contactType))
                .collect(Collectors.toSet());
    }

    public Set<Address> getAddressByState(String userId, String state) {
        User user = get(userId);
        return user.addresses().stream()
                .filter(a -> a.state().equals(state))
                .collect(Collectors.toSet());
    }

}
