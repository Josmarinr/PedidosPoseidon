package com.Marin.PedidosPoseidon.application.user.usecase;

import com.Marin.PedidosPoseidon.application.user.dto.UpdateUserRequest;
import com.Marin.PedidosPoseidon.application.user.dto.UserResponse;

import java.util.UUID;

public interface UpdateUserUseCase {
    UserResponse execute(UUID userId, UpdateUserRequest request);
}
