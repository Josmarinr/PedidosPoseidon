package com.Marin.PedidosPoseidon.infrastructure.auth.repository;

import com.Marin.PedidosPoseidon.domain.auth.entity.AuthToken;
import com.Marin.PedidosPoseidon.domain.auth.repository.AuthTokenRepository;
import com.Marin.PedidosPoseidon.infrastructure.auth.mapper.AuthTokenJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AuthTokenRepositoryImpl implements AuthTokenRepository {

    private final SpringDataAuthTokenRepository springDataAuthTokenRepository;
    private final AuthTokenJpaMapper authTokenJpaMapper;

    @Override
    public AuthToken save(AuthToken authToken) {
        var jpaEntity = authTokenJpaMapper.toJpaEntity(authToken);
        var savedEntity = springDataAuthTokenRepository.save(jpaEntity);  // ← CAMBIO AQUÍ
        return authTokenJpaMapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<AuthToken> findById(UUID id) {
        return springDataAuthTokenRepository.findById(id)  // ← CAMBIO AQUÍ
                .map(authTokenJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<AuthToken> findByAccessToken(String accessToken) {
        return springDataAuthTokenRepository.findByAccessToken(accessToken)  // ← CAMBIO AQUÍ
                .map(authTokenJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<AuthToken> findByRefreshToken(String refreshToken) {
        return springDataAuthTokenRepository.findByRefreshToken(refreshToken)  // ← CAMBIO AQUÍ
                .map(authTokenJpaMapper::toDomainEntity);
    }

    @Override
    public List<AuthToken> findByUserId(UUID userId) {
        return springDataAuthTokenRepository.findByUserId(userId)  // ← CAMBIO AQUÍ
                .stream()
                .map(authTokenJpaMapper::toDomainEntity)
                .toList();
    }

    @Override
    public void revokeAllByUserId(UUID userId) {
        springDataAuthTokenRepository.revokeAllByUserId(userId);  // ← CAMBIO AQUÍ
    }

    @Override
    public void deleteExpiredTokens() {
        springDataAuthTokenRepository.deleteExpiredTokens(LocalDateTime.now());  // ← CAMBIO AQUÍ
    }

}
