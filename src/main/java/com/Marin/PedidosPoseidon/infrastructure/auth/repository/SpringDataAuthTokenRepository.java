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

    // Búsquedas básicas
    @Query("SELECT a FROM AuthTokenJpaEntity a WHERE a.accessToken = :accessToken")
    Optional<AuthTokenJpaEntity> findByAccessToken(@Param("accessToken") String accessToken);

    @Query("SELECT a FROM AuthTokenJpaEntity a WHERE a.refreshToken = :refreshToken")
    Optional<AuthTokenJpaEntity> findByRefreshToken(@Param("refreshToken") String refreshToken);

    @Query("SELECT a FROM AuthTokenJpaEntity a WHERE a.userId = :userId")
    List<AuthTokenJpaEntity> findByUserId(@Param("userId") UUID userId);

    // Operaciones de actualización
    @Modifying
    @Query("UPDATE AuthTokenJpaEntity a SET a.revoked = true WHERE a.userId = :userId AND a.revoked = false")
    void revokeAllByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query("DELETE FROM AuthTokenJpaEntity a WHERE a.accessTokenExpiresAt < :now AND a.refreshTokenExpiresAt < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);

    // Búsquedas adicionales útiles
    @Query("SELECT a FROM AuthTokenJpaEntity a WHERE a.userId = :userId AND a.revoked = false")
    List<AuthTokenJpaEntity> findActiveTokensByUserId(@Param("userId") UUID userId);

    @Query("SELECT COUNT(a) FROM AuthTokenJpaEntity a WHERE a.userId = :userId AND a.revoked = false")
    long countActiveTokensByUserId(@Param("userId") UUID userId);
}