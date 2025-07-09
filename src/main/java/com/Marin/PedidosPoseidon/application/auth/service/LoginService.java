package com.Marin.PedidosPoseidon.application.auth.service;

import com.Marin.PedidosPoseidon.application.auth.dto.LoginRequest;
import com.Marin.PedidosPoseidon.application.auth.dto.LoginResponse;
import com.Marin.PedidosPoseidon.application.auth.mapper.AuthMapper;
import com.Marin.PedidosPoseidon.application.auth.usecase.LoginUseCase;
import com.Marin.PedidosPoseidon.domain.auth.entity.AuthToken;
import com.Marin.PedidosPoseidon.domain.auth.exception.AuthException;
import com.Marin.PedidosPoseidon.domain.auth.repository.AuthTokenRepository;
import com.Marin.PedidosPoseidon.domain.auth.service.TokenService;
import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import com.Marin.PedidosPoseidon.domain.user.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final AuthTokenRepository authTokenRepository;
    private final PasswordService passwordService;
    private final TokenService tokenService;
    private final AuthMapper authMapper;

    @Override
    public LoginResponse execute(LoginRequest request) {
        // Buscar usuario
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthException("Credenciales inválidas"));

        // Validar usuario activo
        if (!user.isActive()) {
            throw new AuthException("Usuario inactivo");
        }

        // Validar contraseña
        if (!passwordService.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("Credenciales inválidas");
        }

        // Revocar tokens existentes (opcional - para single session)
        authTokenRepository.revokeAllByUserId(user.getId());

        // Generar nuevos tokens
        AuthToken authToken = tokenService.generateTokens(user);

        // Guardar tokens
        authTokenRepository.save(authToken);

        // Mapear respuesta
        return authMapper.toLoginResponse(user, authToken);
    }
}
