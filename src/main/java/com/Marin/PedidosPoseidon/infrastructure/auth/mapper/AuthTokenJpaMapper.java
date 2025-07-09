package com.Marin.PedidosPoseidon.infrastructure.auth.mapper;

import com.Marin.PedidosPoseidon.domain.auth.entity.AuthToken;
import com.Marin.PedidosPoseidon.domain.auth.valueobject.JwtToken;
import com.Marin.PedidosPoseidon.domain.auth.valueobject.TokenExpiration;
import com.Marin.PedidosPoseidon.infrastructure.auth.entity.AuthTokenJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenJpaMapper {
    public AuthTokenJpaEntity toJpaEntity(AuthToken authToken) {
        return AuthTokenJpaEntity.builder()
                .id(authToken.getId())
                .userId(authToken.getUserId())
                .accessToken(authToken.getAccessToken().getValue())
                .refreshToken(authToken.getRefreshToken().getValue())
                .accessTokenExpiresAt(authToken.getAccessTokenExpiration().getExpiresAt())
                .refreshTokenExpiresAt(authToken.getRefreshTokenExpiration().getExpiresAt())
                .revoked(authToken.isRevoked())
                .createdAt(authToken.getCreatedAt())
                .build();
    }

    public AuthToken toDomainEntity(AuthTokenJpaEntity jpaEntity) {
        return AuthToken.builder()
                .id(jpaEntity.getId())
                .userId(jpaEntity.getUserId())
                .accessToken(new JwtToken(jpaEntity.getAccessToken()))
                .refreshToken(new JwtToken(jpaEntity.getRefreshToken()))
                .accessTokenExpiration(new TokenExpiration(jpaEntity.getAccessTokenExpiresAt()))
                .refreshTokenExpiration(new TokenExpiration(jpaEntity.getRefreshTokenExpiresAt()))
                .revoked(jpaEntity.isRevoked())
                .createdAt(jpaEntity.getCreatedAt())
                .build();
    }
}
