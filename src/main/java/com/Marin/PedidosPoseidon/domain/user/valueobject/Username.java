package com.Marin.PedidosPoseidon.domain.user.valueobject;

import java.util.Objects;

public class Username {
    private final String value;

    public Username(String value) {
        Objects.requireNonNull(value, "Username cannot be null");
        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (trimmedValue.length() < 3) {
            throw new IllegalArgumentException("Username must have at least 3 characters");
        }
        if (trimmedValue.length() > 50) {
            throw new IllegalArgumentException("Username cannot exceed 50 characters");
        }
        if (!trimmedValue.matches("^[a-zA-Z0-9._-]+$")) {
            throw new IllegalArgumentException("Username can only contain letters, numbers, dots, underscores and hyphens");
        }
        this.value = trimmedValue;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Username)) return false;
        Username username = (Username) o;
        return Objects.equals(value, username.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
