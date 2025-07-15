package com.Josmarinr.PedidosPoseidon.domain.user.port.out;

import com.Josmarinr.PedidosPoseidon.domain.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllActive();
    List<User> findByRole(String roleName);
    void deleteById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
