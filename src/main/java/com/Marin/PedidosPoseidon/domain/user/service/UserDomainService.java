package com.Marin.PedidosPoseidon.domain.user.service;

import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.exception.UserException;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDomainService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDomainService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void validateUserCreation(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserException("El nombre de usuario ya existe");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("El email ya está registrado");
        }

        validatePassword(user.getPassword());
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new UserException("La contraseña debe tener al menos 8 caracteres");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new UserException("La contraseña debe tener al menos una mayúscula");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new UserException("La contraseña debe tener al menos una minúscula");
        }

        if (!password.matches(".*[0-9].*")) {
            throw new UserException("La contraseña debe tener al menos un número");
        }
    }
}
