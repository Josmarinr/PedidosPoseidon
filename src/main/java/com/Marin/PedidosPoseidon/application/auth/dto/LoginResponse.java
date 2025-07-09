package com.Marin.PedidosPoseidon.application.auth.dto;

import com.Marin.PedidosPoseidon.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class LoginResponse {
    private UUID userId;
    private String username;
    private String email;
    private String fullName;
    private UserRole role;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenExpiresAt;
    private LocalDateTime refreshTokenExpiresAt;
}
