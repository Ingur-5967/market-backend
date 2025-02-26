package ru.solomka.identity.common;

import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

public interface Entity {

    @NonNull UUID getId();
    void setId(UUID uuid);

    @NonNull Instant getCreatedAt();
    void setCreatedAt(Instant createdAt);
}