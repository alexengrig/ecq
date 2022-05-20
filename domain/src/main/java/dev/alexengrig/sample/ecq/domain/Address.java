package dev.alexengrig.sample.ecq.domain;

public record Address(
        String city,
        String state,
        String postcode
) {
}
