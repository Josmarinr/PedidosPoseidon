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
public class JpaAuthTokenRepository implements AuthTokenRepository {
    private final JpaAuthTokenRepository jpaAuthTokenRepository;
    private final AuthTokenJpaMapper authTokenJpaMapper;

    @Override
    public AuthToken save(AuthToken authToken) {
        var jpaEntity = authTokenJpaMapper.toJpaEntity(authToken);
        var savedEntity = jpaAuthTokenRepository.save(jpaEntity);
        return authTokenJpaMapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<AuthToken> findById(UUID id) {
        return jpaAuthTokenRepository.findById(id)
                .map(authTokenJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<AuthToken> findByAccessToken(String accessToken) {
        return jpaAuthTokenRepository.findByAccessToken(accessToken)
                .map(authTokenJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<AuthToken> findByRefreshToken(String refreshToken) {
        return jpaAuthTokenRepository.findByRefreshToken(refreshToken)
                .map(authTokenJpaMapper::toDomainEntity);
    }

    @Override
    public List<AuthToken> findByUserId(UUID userId) {
        return jpaAuthTokenRepository.findByUserId(userId)
                .stream()
                .map(authTokenJpaMapper::toDomainEntity)
                .toList();
    }

    @Override
    public void revokeAllByUserId(UUID userId) {
        jpaAuthTokenRepository.revokeAllByUserId(userId);
    }

    @Override
    public void deleteExpiredTokens() {
        jpaAuthTokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}
