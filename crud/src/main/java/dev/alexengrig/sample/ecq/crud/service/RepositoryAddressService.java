package dev.alexengrig.sample.ecq.crud.service;

import dev.alexengrig.sample.ecq.crud.repository.UserRepository;
import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.User;

import java.util.Set;
import java.util.stream.Collectors;

public record RepositoryAddressService(
        UserRepository repository
) implements AddressService {

    @Override
    public Set<Address> getByState(String userId, String state) {
        User user = repository.findById(userId);
        return user.addresses()
                .stream()
                .filter(address -> state.equals(address.state()))
                .collect(Collectors.toSet());
    }

}
