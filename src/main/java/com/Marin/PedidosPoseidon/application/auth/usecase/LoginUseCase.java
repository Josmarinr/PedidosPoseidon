package com.Marin.PedidosPoseidon.application.auth.usecase;

import com.Marin.PedidosPoseidon.application.auth.dto.LoginRequest;
import com.Marin.PedidosPoseidon.application.auth.dto.LoginResponse;

public interface LoginUseCase {
    LoginResponse execute(LoginRequest request);
}
