package com.Marin.PedidosPoseidon.infrastructure.auth.repository;

import com.Marin.PedidosPoseidon.infrastructure.auth.entity.AuthTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface SpringDataAuthTokenRepository extends JpaRepository<AuthTokenJpaEntity, UUID> {

    Optional<AuthTokenJpaEntity> findByAccessToken(String accessToken);
    Optional<AuthTokenJpaEntity> findByRefreshToken(String refreshToken);
    List<AuthTokenJpaEntity> findByUserId(UUID userId);

    @Modifying
    @Query("UPDATE AuthTokenJpaEntity a SET a.revoked = true WHERE a.userId = :userId AND a.revoked = false")
    void revokeAllByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query("DELETE FROM AuthTokenJpaEntity a WHERE a.accessTokenExpiresAt < :now AND a.refreshTokenExpiresAt < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);

}
