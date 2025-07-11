package com.Marin.PedidosPoseidon.domain.user.valueobject;

import java.util.Objects;
import java.util.UUID;

public class UserId {
    private final UUID value;

    public UserId(UUID value) {
        Objects.requireNonNull(value, "UserId cannot be null");
        this.value = value;
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    public static UserId of(String value) {
        return new UserId(UUID.fromString(value));
    }

    public static UserId of(UUID value) {
        return new UserId(value);
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;
        UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
