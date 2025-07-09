package com.Marin.PedidosPoseidon.application.auth.usecase;

import com.Marin.PedidosPoseidon.domain.user.entity.User;

import java.util.Optional;

public interface ValidateTokenUseCase {
    Optional<User> execute(String token);
}
