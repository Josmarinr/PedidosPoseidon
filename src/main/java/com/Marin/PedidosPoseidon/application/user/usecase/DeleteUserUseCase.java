package com.Marin.PedidosPoseidon.application.user.usecase;

import java.util.UUID;

public interface DeleteUserUseCase {
    void execute(UUID userId);
}
