package com.Marin.PedidosPoseidon.domain.user.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class User {

    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole role;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Métodos de negocio
    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePassword(String newPassword) {
        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }
        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean hasRole(UserRole role) {
        return this.role.equals(role);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
