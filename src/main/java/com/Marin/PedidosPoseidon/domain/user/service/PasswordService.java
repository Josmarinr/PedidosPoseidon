package com.Marin.PedidosPoseidon.domain.user.service;

public interface PasswordService {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
    void validatePasswordStrength(String password);
}
