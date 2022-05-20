package dev.alexengrig.sample.ecq.escqrs.aggregate;

import dev.alexengrig.sample.ecq.cqrs.command.CreateUserCommand;
import dev.alexengrig.sample.ecq.cqrs.command.UpdateUserCommand;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public record UserEventAggregate(
        UserEventRepository repository
) {

    public List<Event> handleCreateUserCommand(CreateUserCommand command) {
        UserCreatedEvent event = new UserCreatedEvent(command.userId(), command.firstName(), command.lastName());
        repository.push(command.userId(), event);
        return List.of(event);
    }

    public List<Event> handleUpdateUserCommand(UpdateUserCommand command) {
        User user = get(command.userId());
        List<Event> events = new ArrayList<>();

        List<Contact> contactsToRemove = user.contacts()
                .stream()
                .filter(c -> !command.contacts().contains(c))
                .toList();
        for (Contact contact : contactsToRemove) {
            UserContactRemovedEvent contactRemovedEvent = new UserContactRemovedEvent(contact.type(), contact.detail());
            events.add(contactRemovedEvent);
            repository.push(command.userId(), contactRemovedEvent);
        }

        List<Contact> contactsToAdd = command.contacts()
                .stream()
                .filter(c -> !user.contacts().contains(c))
                .toList();
        for (Contact contact : contactsToAdd) {
            UserContactAddedEvent contactAddedEvent = new UserContactAddedEvent(contact.type(), contact.detail());
            events.add(contactAddedEvent);
            repository.push(command.userId(), contactAddedEvent);
        }

        List<Address> addressesToRemove = user.addresses()
                .stream()
                .filter(a -> !command.addresses().contains(a))
                .toList();
        for (Address address : addressesToRemove) {
            UserAddressRemovedEvent addressRemovedEvent = new UserAddressRemovedEvent(address.city(), address.state(), address.postcode());
            events.add(addressRemovedEvent);
            repository.push(command.userId(), addressRemovedEvent);
        }

        List<Address> addressesToAdd = command.addresses()
                .stream()
                .filter(a -> !user.addresses().contains(a))
                .toList();
        for (Address address : addressesToAdd) {
            UserAddressAddedEvent addressAddedEvent = new UserAddressAddedEvent(address.city(), address.state(), address.postcode());
            events.add(addressAddedEvent);
            repository.push(command.userId(), addressAddedEvent);
        }

        return events;
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

}
