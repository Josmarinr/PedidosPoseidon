package com.Marin.PedidosPoseidon.domain.auth.repository;

import com.Marin.PedidosPoseidon.domain.auth.entity.AuthToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthTokenRepository {
    AuthToken save(AuthToken authToken);
    Optional<AuthToken> findById(UUID id);
    Optional<AuthToken> findByAccessToken(String accessToken);
    Optional<AuthToken> findByRefreshToken(String refreshToken);
    List<AuthToken> findByUserId(UUID userId);
    void revokeAllByUserId(UUID userId);
    void deleteExpiredTokens();
}
