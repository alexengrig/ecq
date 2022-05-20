package dev.alexengrig.sample.ecq;

import dev.alexengrig.sample.ecq.cqrs.command.CreateUserCommand;
import dev.alexengrig.sample.ecq.cqrs.command.UpdateUserCommand;
import dev.alexengrig.sample.ecq.cqrs.projection.UserProjection;
import dev.alexengrig.sample.ecq.cqrs.query.AddressByStateQuery;
import dev.alexengrig.sample.ecq.cqrs.query.ContactByTypeQuery;
import dev.alexengrig.sample.ecq.cqrs.repository.UserReadRepository;
import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.domain.User;
import dev.alexengrig.sample.ecq.escqrs.aggregate.UserEventAggregate;
import dev.alexengrig.sample.ecq.escqrs.projector.UserEventProjector;
import dev.alexengrig.sample.ecq.eventsourcing.event.Event;
import dev.alexengrig.sample.ecq.eventsourcing.repository.UserEventRepository;

import java.util.List;
import java.util.Set;

public class EsCqrsUserFlow extends UserFlow {

    private final UserEventAggregate aggregate;
    private final UserEventProjector projector;
    private final UserProjection projection;

    public EsCqrsUserFlow() {
        UserEventRepository userEventRepository = new UserEventRepository();
        aggregate = new UserEventAggregate(userEventRepository);
        UserReadRepository readRepository = new UserReadRepository();
        projector = new UserEventProjector(readRepository);
        projection = new UserProjection(readRepository);
    }

    @Override
    protected User createUser(String userId, String firstName, String lastName) {
        List<Event> events = aggregate.handleCreateUserCommand(new CreateUserCommand(userId, firstName, lastName));
        projector.project(userId, events);
        return aggregate.get(userId);
    }

    @Override
    protected User updateUser(String userId, Set<Contact> contacts, Set<Address> addresses) {
        List<Event> events = aggregate.handleUpdateUserCommand(new UpdateUserCommand(userId, contacts, addresses));
        projector.project(userId, events);
        return aggregate.get(userId);
    }

    @Override
    protected Set<Contact> getContacts(String userId, String type) {
        ContactByTypeQuery query = new ContactByTypeQuery(userId, type);
        return projection.handleContactByTypeQuery(query);
    }

    @Override
    protected Set<Address> getAddresses(String userId, String state) {
        AddressByStateQuery query = new AddressByStateQuery(userId, state);
        return projection.handleAddressByStateQuery(query);
    }

}
