package com.Josmarinr.PedidosPoseidon.domain.role.model;

import com.Josmarinr.PedidosPoseidon.domain.permission.model.Permission;

import java.time.LocalDateTime;
import java.util.Set;

public class Role {

    private Long id;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Permission> permissions;

}
