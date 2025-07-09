package com.Marin.PedidosPoseidon.infrastructure.auth.repository;

import com.Marin.PedidosPoseidon.domain.auth.entity.AuthToken;
import com.Marin.PedidosPoseidon.domain.auth.repository.AuthTokenRepository;
import com.Marin.PedidosPoseidon.infrastructure.auth.mapper.AuthTokenJpaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthTokenRepositoryImpl implements AuthTokenRepository {

    private final SpringDataAuthTokenRepository springDataRepository;
    private final AuthTokenJpaMapper mapper;

    @Override
    @Transactional
    public AuthToken save(AuthToken authToken) {
        log.debug("Guardando token para usuario: {}", authToken.getUserId());
        var jpaEntity = mapper.toJpaEntity(authToken);
        var savedEntity = springDataRepository.save(jpaEntity);
        return mapper.toDomainEntity(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthToken> findById(UUID id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthToken> findByAccessToken(String accessToken) {
        return springDataRepository.findByAccessToken(accessToken)
                .map(mapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthToken> findByRefreshToken(String refreshToken) {
        return springDataRepository.findByRefreshToken(refreshToken)
                .map(mapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthToken> findByUserId(UUID userId) {
        return springDataRepository.findByUserId(userId)
                .stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Override
    @Transactional
    public void revokeAllByUserId(UUID userId) {
        log.debug("Revocando todos los tokens para usuario: {}", userId);
        springDataRepository.revokeAllByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteExpiredTokens() {
        log.debug("Eliminando tokens expirados");
        springDataRepository.deleteExpiredTokens(LocalDateTime.now());
    }
}