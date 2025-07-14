package com.Josmarinr.PedidosPoseidon.domain.user.entity;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class User {

    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

}
