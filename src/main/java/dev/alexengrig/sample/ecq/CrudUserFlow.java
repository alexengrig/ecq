package dev.alexengrig.sample.ecq;

import dev.alexengrig.sample.ecq.crud.repository.HashMapUserRepository;
import dev.alexengrig.sample.ecq.crud.repository.UserRepository;
import dev.alexengrig.sample.ecq.crud.service.RepositoryAddressService;
import dev.alexengrig.sample.ecq.crud.service.RepositoryContactService;
import dev.alexengrig.sample.ecq.crud.service.RepositoryUserService;
import dev.alexengrig.sample.ecq.crud.service.UserService;
import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;
import dev.alexengrig.sample.ecq.domain.User;

import java.util.Set;

public class CrudUserFlow extends UserFlow {

    private final UserService userService;
    private final RepositoryContactService contactService;
    private final RepositoryAddressService addressService;

    public CrudUserFlow() {
        UserRepository userRepository = new HashMapUserRepository();
        userService = new RepositoryUserService(userRepository);
        contactService = new RepositoryContactService(userRepository);
        addressService = new RepositoryAddressService(userRepository);
    }

    @Override
    protected User createUser(String userId, String firstName, String lastName) {
        return userService.create(userId, firstName, lastName);
    }

    @Override
    protected User updateUser(String userId, Set<Contact> contacts, Set<Address> addresses) {
        return userService.update(userId, contacts, addresses);
    }

    @Override
    protected Set<Contact> getContacts(String userId, String type) {
        return contactService.getByType(userId, type);
    }

    @Override
    protected Set<Address> getAddresses(String userId, String state) {
        return addressService.getByState(userId, state);
    }

}
