package com.Marin.PedidosPoseidon.domain.user.entity;

public enum UserRole {
    ADMIN("ADMIN"),
    SELLER("SELLER"),
    DISPATCHER("DISPATCHER");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
