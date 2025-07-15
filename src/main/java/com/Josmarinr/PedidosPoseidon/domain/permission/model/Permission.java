package com.Josmarinr.PedidosPoseidon.domain.permission.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    private Long id;
    private String name;
    private String description;
    private String resource;
    private String action;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Métodos de negocio
    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }

    public String getFullPermission() {
        return resource + ":" + action;
    }

}
