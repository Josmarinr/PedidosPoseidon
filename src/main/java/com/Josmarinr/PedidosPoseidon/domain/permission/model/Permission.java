package com.Josmarinr.PedidosPoseidon.domain.permission.model;

import java.time.LocalDateTime;

public class Permission {

    private Long id;
    private String name;
    private String description;
    private String resource;
    private String action;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
