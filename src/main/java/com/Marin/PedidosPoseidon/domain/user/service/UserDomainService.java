package com.Marin.PedidosPoseidon.domain.user.service;

import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.exception.UserException;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDomainService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public UserDomainService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public void validateUserCreation(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserException("El nombre de usuario ya existe");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("El email ya está registrado");
        }
    }

    public void validateUserUpdate(User existingUser, User updatedUser) {
        // Validar username único si cambió
        if (!existingUser.getUsername().equals(updatedUser.getUsername())) {
            if (userRepository.existsByUsername(updatedUser.getUsername())) {
                throw new UserException("El nombre de usuario ya existe");
            }
        }

        // Validar email único si cambió
        if (!existingUser.getEmail().equals(updatedUser.getEmail())) {
            if (userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new UserException("El email ya está registrado");
            }
        }
    }

    public String encodePassword(String rawPassword) {
        passwordService.validatePasswordStrength(rawPassword);
        return passwordService.encode(rawPassword);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordService.matches(rawPassword, encodedPassword);
    }
}
