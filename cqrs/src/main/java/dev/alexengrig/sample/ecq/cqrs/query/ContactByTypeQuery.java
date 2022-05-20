package dev.alexengrig.sample.ecq.cqrs.query;

public record ContactByTypeQuery(
        String userId,
        String type
) {
}
