package com.Marin.PedidosPoseidon.web.auth.controller;

import com.Marin.PedidosPoseidon.application.auth.dto.LoginRequest;
import com.Marin.PedidosPoseidon.application.auth.dto.LoginResponse;
import com.Marin.PedidosPoseidon.application.auth.dto.RefreshTokenRequest;
import com.Marin.PedidosPoseidon.application.auth.dto.TokenResponse;
import com.Marin.PedidosPoseidon.application.auth.usecase.LoginUseCase;
import com.Marin.PedidosPoseidon.application.auth.usecase.LogoutUseCase;
import com.Marin.PedidosPoseidon.application.auth.usecase.RefreshTokenUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = loginUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        // Extraer token del header "Bearer token"
        String token = authHeader.replace("Bearer ", "");
        logoutUseCase.execute(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        TokenResponse response = refreshTokenUseCase.execute(request);
        return ResponseEntity.ok(response);
    }
}