package com.Marin.PedidosPoseidon.infrastructure.security;

import com.Marin.PedidosPoseidon.domain.user.exception.UserException;
import com.Marin.PedidosPoseidon.domain.user.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public void validatePasswordStrength(String password) {
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
