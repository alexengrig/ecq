package dev.alexengrig.sample.ecq.crud.service;

import dev.alexengrig.sample.ecq.domain.Contact;

import java.util.Set;

public interface ContactService {

    Set<Contact> getByType(String userId, String type);

}
