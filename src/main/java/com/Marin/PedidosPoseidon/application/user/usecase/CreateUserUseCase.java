package com.Marin.PedidosPoseidon.application.user.usecase;

import com.Marin.PedidosPoseidon.application.user.dto.CreateUserRequest;
import com.Marin.PedidosPoseidon.application.user.dto.UserResponse;

public interface CreateUserUseCase {
    UserResponse execute(CreateUserRequest request);
}
