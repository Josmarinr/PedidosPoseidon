package com.Marin.PedidosPoseidon.application.auth.service;

import com.Marin.PedidosPoseidon.application.auth.usecase.ValidateTokenUseCase;
import com.Marin.PedidosPoseidon.domain.auth.entity.AuthToken;
import com.Marin.PedidosPoseidon.domain.auth.repository.AuthTokenRepository;
import com.Marin.PedidosPoseidon.domain.auth.service.TokenService;
import com.Marin.PedidosPoseidon.domain.auth.valueobject.JwtToken;
import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ValidateTokenService implements ValidateTokenUseCase {

    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public Optional<User> execute(String token) {
        try {
            JwtToken jwtToken = new JwtToken(token);

            // Buscar token en la base de datos
            Optional<AuthToken> authTokenOpt = authTokenRepository.findByAccessToken(token);
            if (authTokenOpt.isEmpty()) {
                return Optional.empty();
            }

            AuthToken authToken = authTokenOpt.get();

            // Verificar que el token es válido
            if (!authToken.isValid()) {
                return Optional.empty();
            }

            // Extraer username del JWT
            Optional<String> usernameOpt = tokenService.extractUsername(jwtToken);
            if (usernameOpt.isEmpty()) {
                return Optional.empty();
            }

            String username = usernameOpt.get();

            // Validar JWT
            if (!tokenService.validateToken(jwtToken, username)) {
                return Optional.empty();
            }

            // Buscar usuario
            return userRepository.findByUsername(username)
                    .filter(User::isActive);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
