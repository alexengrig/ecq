package dev.alexengrig.sample.ecq.cqrs.projector;

import dev.alexengrig.sample.ecq.cqrs.repository.UserReadRepository;
import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.domain.User;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record UserProjector(
        UserReadRepository repository
) {

    public void project(User user) {
        Map<String, Set<Contact>> contacts = user.contacts()
                .stream()
                .collect(Collectors.groupingBy(Contact::type, Collectors.toSet()));
        repository.putContacts(user.id(), contacts);
        Map<String, Set<Address>> addresses = user.addresses()
                .stream()
                .collect(Collectors.groupingBy(Address::state, Collectors.toSet()));
        repository.putAddresses(user.id(), addresses);
    }

}
