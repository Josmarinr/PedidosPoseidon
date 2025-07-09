package com.Marin.PedidosPoseidon.application.auth.service;

import com.Marin.PedidosPoseidon.application.auth.dto.RefreshTokenRequest;
import com.Marin.PedidosPoseidon.application.auth.dto.TokenResponse;
import com.Marin.PedidosPoseidon.application.auth.mapper.AuthMapper;
import com.Marin.PedidosPoseidon.application.auth.usecase.RefreshTokenUseCase;
import com.Marin.PedidosPoseidon.domain.auth.entity.AuthToken;
import com.Marin.PedidosPoseidon.domain.auth.exception.AuthException;
import com.Marin.PedidosPoseidon.domain.auth.repository.AuthTokenRepository;
import com.Marin.PedidosPoseidon.domain.auth.service.TokenService;
import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshTokenUseCase {

    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthMapper authMapper;

    @Override
    public TokenResponse execute(RefreshTokenRequest request) {
        // Buscar token de refresh
        AuthToken existingToken = authTokenRepository.findByRefreshToken(request.getRefreshToken())
                .orElseThrow(() -> new AuthException("Refresh token inválido"));

        // Validar que puede ser refrescado
        if (!existingToken.canRefresh()) {
            throw new AuthException("Refresh token expirado o revocado");
        }

        // Buscar usuario
        User user = userRepository.findById(existingToken.getUserId())
                .orElseThrow(() -> new AuthException("Usuario no encontrado"));

        // Verificar que el usuario sigue activo
        if (!user.isActive()) {
            throw new AuthException("Usuario inactivo");
        }

        // Revocar token actual
        existingToken.revoke();
        authTokenRepository.save(existingToken);

        // Generar nuevos tokens
        AuthToken newAuthToken = tokenService.refreshTokens(request.getRefreshToken(), user);

        // Guardar nuevos tokens
        authTokenRepository.save(newAuthToken);

        return authMapper.toTokenResponse(newAuthToken);
    }

}
