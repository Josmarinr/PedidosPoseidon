package com.Marin.PedidosPoseidon.application.auth.mapper;

import com.Marin.PedidosPoseidon.application.auth.dto.LoginResponse;
import com.Marin.PedidosPoseidon.application.auth.dto.TokenResponse;
import com.Marin.PedidosPoseidon.domain.auth.entity.AuthToken;
import com.Marin.PedidosPoseidon.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public LoginResponse toLoginResponse(User user, AuthToken authToken) {
        return LoginResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole())
                .accessToken(authToken.getAccessToken().getValue())
                .refreshToken(authToken.getRefreshToken().getValue())
                .accessTokenExpiresAt(authToken.getAccessTokenExpiration().getExpiresAt())
                .refreshTokenExpiresAt(authToken.getRefreshTokenExpiration().getExpiresAt())
                .build();
    }

    public TokenResponse toTokenResponse(AuthToken authToken) {
        return TokenResponse.builder()
                .accessToken(authToken.getAccessToken().getValue())
                .refreshToken(authToken.getRefreshToken().getValue())
                .accessTokenExpiresAt(authToken.getAccessTokenExpiration().getExpiresAt())
                .refreshTokenExpiresAt(authToken.getRefreshTokenExpiration().getExpiresAt())
                .build();
    }

}
