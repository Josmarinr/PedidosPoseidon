package com.Marin.PedidosPoseidon.application.user.usecase;

import com.Marin.PedidosPoseidon.application.user.dto.UserResponse;
import com.Marin.PedidosPoseidon.domain.user.entity.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FindUserUseCase {
    Optional<UserResponse> findById(UUID id);
    Optional<UserResponse> findByUsername(String username);
    Optional<UserResponse> findByEmail(String email);
    List<UserResponse> findByRole(UserRole role);
    List<UserResponse> findAll();
}
