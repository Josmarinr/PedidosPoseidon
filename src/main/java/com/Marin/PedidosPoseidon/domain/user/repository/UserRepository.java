package com.Marin.PedidosPoseidon.domain.user.repository;


import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.entity.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
    List<User> findAll();
    void deleteById(UUID id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
