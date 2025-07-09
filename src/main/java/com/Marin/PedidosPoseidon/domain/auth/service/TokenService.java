package com.Marin.PedidosPoseidon.domain.auth.service;

import com.Marin.PedidosPoseidon.domain.auth.entity.AuthToken;
import com.Marin.PedidosPoseidon.domain.auth.valueobject.JwtToken;
import com.Marin.PedidosPoseidon.domain.user.entity.User;

import java.util.Optional;

public interface TokenService {
    AuthToken generateTokens(User user);
    Optional<String> extractUsername(JwtToken token);
    Optional<String> extractRole(JwtToken token);
    boolean validateToken(JwtToken token, String username);
    AuthToken refreshTokens(String refreshToken, User user);
}
