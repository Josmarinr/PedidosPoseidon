package com.Marin.PedidosPoseidon.domain.auth.valueobject;

import java.time.LocalDateTime;
import java.util.Objects;

public class TokenExpiration {
    private final LocalDateTime expiresAt;

    public TokenExpiration(LocalDateTime expiresAt) {
        Objects.requireNonNull(expiresAt, "Expiration time cannot be null");
        this.expiresAt = expiresAt;
    }

    public static TokenExpiration fromMinutes(int minutes) {
        return new TokenExpiration(LocalDateTime.now().plusMinutes(minutes));
    }

    public static TokenExpiration fromDays(int days) {
        return new TokenExpiration(LocalDateTime.now().plusDays(days));
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenExpiration)) return false;
        TokenExpiration that = (TokenExpiration) o;
        return Objects.equals(expiresAt, that.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expiresAt);
    }
}
