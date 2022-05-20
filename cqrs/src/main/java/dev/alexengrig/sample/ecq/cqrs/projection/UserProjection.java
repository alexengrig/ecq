package dev.alexengrig.sample.ecq.cqrs.projection;

import dev.alexengrig.sample.ecq.cqrs.query.AddressByStateQuery;
import dev.alexengrig.sample.ecq.cqrs.query.ContactByTypeQuery;
import dev.alexengrig.sample.ecq.cqrs.repository.UserReadRepository;
import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;

import java.util.Map;
import java.util.Set;

public record UserProjection(
        UserReadRepository repository
) {

    public Set<Contact> handleContactByTypeQuery(ContactByTypeQuery query) {
        Map<String, Set<Contact>> contacts = repository.getContacts(query.userId());
        return contacts.get(query.type());
    }

    public Set<Address> handleAddressByStateQuery(AddressByStateQuery query) {
        Map<String, Set<Address>> addresses = repository.getAddresses(query.userId());
        return addresses.get(query.state());
    }

}
