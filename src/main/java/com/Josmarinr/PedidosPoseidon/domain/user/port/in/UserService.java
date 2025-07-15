package com.Josmarinr.PedidosPoseidon.domain.user.port.in;

import com.Josmarinr.PedidosPoseidon.domain.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(String username, String email, String password, String firstName, String lastName);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllActive();
    User updateUser(Long id, String firstName, String lastName, String email);
    void assignRole(Long userId, Long roleId);
    void removeRole(Long userId, Long roleId);
    void deactivateUser(Long userId);
    void activateUser(Long userId);
    boolean validateUserPermission(Long userId, String permission);
    List<User> findUsersByRole(String roleName);

}
