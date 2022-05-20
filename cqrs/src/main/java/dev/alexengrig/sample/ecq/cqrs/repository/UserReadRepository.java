package dev.alexengrig.sample.ecq.cqrs.repository;

import dev.alexengrig.sample.ecq.domain.Address;
import dev.alexengrig.sample.ecq.domain.Contact;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserReadRepository {

    private final Map<String, Map<String, Set<Contact>>> contactsByUserId = new HashMap<>();
    private final Map<String, Map<String, Set<Address>>> addressByUserId = new HashMap<>();

    public void putContacts(String userId, Map<String, Set<Contact>> contacts) {
        contactsByUserId.put(userId, contacts);
    }

    public Map<String, Set<Contact>> getContacts(String userId) {
        return contactsByUserId.get(userId);
    }

    public Map<String, Set<Address>> getAddresses(String userId) {
        return addressByUserId.get(userId);
    }

    public void putAddresses(String userId, Map<String, Set<Address>> addresses) {
        addressByUserId.put(userId, addresses);
    }

}
