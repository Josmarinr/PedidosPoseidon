package com.Marin.PedidosPoseidon.domain.auth.valueobject;

import java.util.Objects;

public class JwtToken {
    private final String value;

    public JwtToken(String value) {
        Objects.requireNonNull(value, "JWT token cannot be null");
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT token cannot be empty");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JwtToken)) return false;
        JwtToken jwtToken = (JwtToken) o;
        return Objects.equals(value, jwtToken.value);
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
