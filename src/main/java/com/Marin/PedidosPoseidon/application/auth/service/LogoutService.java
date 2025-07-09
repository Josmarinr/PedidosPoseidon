package com.Marin.PedidosPoseidon.application.auth.service;

import com.Marin.PedidosPoseidon.application.auth.usecase.LogoutUseCase;
import com.Marin.PedidosPoseidon.domain.auth.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LogoutService implements LogoutUseCase {

    private final AuthTokenRepository authTokenRepository;

    @Override
    public void execute(String accessToken) {
        authTokenRepository.findByAccessToken(accessToken)
                .ifPresent(authToken -> {
                    authToken.revoke();
                    authTokenRepository.save(authToken);
                });
    }

}
