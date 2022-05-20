package dev.alexengrig.sample.ecq;

import dev.alexengrig.sample.ecq.cqrs.aggregate.UserAggregate;
import dev.alexengrig.sample.ecq.cqrs.command.CreateUserCommand;
import dev.alexengrig.sample.ecq.cqrs.command.UpdateUserCommand;
import dev.alexengrig.sample.ecq.cqrs.projection.UserProjection;
import dev.alexengrig.sample.ecq.cqrs.projector.UserProjector;
import dev.alexengrig.sample.ecq.cqrs.query.AddressByStateQuery;
import dev.alexengrig.sample.ecq.cqrs.query.ContactByTypeQuery;
import dev.alexengrig.sample.ecq.cqrs.repository.UserReadRepository;
import dev.alexengrig.sample.ecq.cqrs.repository.UserWriteRepository;
import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.domain.User;

import java.util.Set;

public class CqrsUserFlow extends UserFlow {

    private final UserAggregate aggregate;
    private final UserProjector projector;
    private final UserProjection projection;

    public CqrsUserFlow() {
        UserWriteRepository writeRepository = new UserWriteRepository();
        UserReadRepository readRepository = new UserReadRepository();
        aggregate = new UserAggregate(writeRepository);
        projector = new UserProjector(readRepository);
        projection = new UserProjection(readRepository);
    }

    @Override
    protected User createUser(String userId, String firstName, String lastName) {
        CreateUserCommand command = new CreateUserCommand(userId, firstName, lastName);
        User result = aggregate.handleCreateUserCommand(command);
        sync(result);
        return result;
    }

    @Override
    protected User updateUser(String userId, Set<Contact> contacts, Set<Address> addresses) {
        UpdateUserCommand command = new UpdateUserCommand(userId, contacts, addresses);
        User result = aggregate.handleUpdateUserCommand(command);
        sync(result);
        return result;
    }

    @Override
    protected Set<Contact> getContacts(String userId, String type) {
        ContactByTypeQuery query = new ContactByTypeQuery(userId, "family");
        return projection.handleContactByTypeQuery(query);
    }

    @Override
    protected Set<Address> getAddresses(String userId, String state) {
        AddressByStateQuery query = new AddressByStateQuery(userId, "New York");
        return projection.handleAddressByStateQuery(query);
    }

    private void sync(User user) {
        projector.project(user);
    }

}
