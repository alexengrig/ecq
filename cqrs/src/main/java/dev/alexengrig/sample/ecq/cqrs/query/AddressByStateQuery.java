package dev.alexengrig.sample.ecq.cqrs.query;

public record AddressByStateQuery(
        String userId,
        String state
) {
}
