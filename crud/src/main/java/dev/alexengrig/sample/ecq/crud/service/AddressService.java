package dev.alexengrig.sample.ecq.crud.service;

import dev.alexengrig.sample.ecq.domain.Address;

import java.util.Set;

public interface AddressService {

    Set<Address> getByState(String userId, String state);

}
