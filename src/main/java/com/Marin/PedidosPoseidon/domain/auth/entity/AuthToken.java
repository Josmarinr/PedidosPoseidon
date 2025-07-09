package com.Marin.PedidosPoseidon.domain.auth.entity;

import com.Marin.PedidosPoseidon.domain.auth.valueobject.JwtToken;
import com.Marin.PedidosPoseidon.domain.auth.valueobject.TokenExpiration;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AuthToken {
    @Builder.Default
    private UUID id = UUID.randomUUID();

    private UUID userId;
    private JwtToken accessToken;
    private JwtToken refreshToken;
    private TokenExpiration accessTokenExpiration;
    private TokenExpiration refreshTokenExpiration;

    @Builder.Default
    private boolean revoked = false;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // Métodos de negocio
    public boolean isAccessTokenExpired() {
        return accessTokenExpiration.isExpired();
    }

    public boolean isRefreshTokenExpired() {
        return refreshTokenExpiration.isExpired();
    }

    public void revoke() {
        this.revoked = true;
    }

    public boolean isValid() {
        return !revoked && !isAccessTokenExpired();
    }

    public boolean canRefresh() {
        return !revoked && !isRefreshTokenExpired();
    }
}
