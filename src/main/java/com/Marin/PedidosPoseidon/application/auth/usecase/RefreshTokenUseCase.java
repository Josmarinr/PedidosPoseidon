package com.Marin.PedidosPoseidon.application.auth.usecase;

import com.Marin.PedidosPoseidon.application.auth.dto.RefreshTokenRequest;
import com.Marin.PedidosPoseidon.application.auth.dto.TokenResponse;

public interface RefreshTokenUseCase {
    TokenResponse execute(RefreshTokenRequest request);
}
