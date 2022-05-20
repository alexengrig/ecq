package dev.alexengrig.sample.ecq;

import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.domain.User;
import dev.alexengrig.sample.ecq.eventsourcing.repository.UserEventRepository;
import dev.alexengrig.sample.ecq.eventsourcing.service.UserEventService;

import java.util.Set;

public class EventSourcingUserFlow extends UserFlow {

    private final UserEventService service;

    public EventSourcingUserFlow() {
        UserEventRepository repository = new UserEventRepository();
        service = new UserEventService(repository);
    }

    @Override
    protected User createUser(String userId, String firstName, String lastName) {
        service.create(userId, firstName, lastName);
        return service.get(userId);
    }

    @Override
    protected User updateUser(String userId, Set<Contact> contacts, Set<Address> addresses) {
        service.update(userId, contacts, addresses);
        return service.get(userId);
    }

    @Override
    protected Set<Contact> getContacts(String userId, String type) {
        return service.getContactByType(userId, type);
    }

    @Override
    protected Set<Address> getAddresses(String userId, String state) {
        return service.getAddressByState(userId, state);
    }

}
