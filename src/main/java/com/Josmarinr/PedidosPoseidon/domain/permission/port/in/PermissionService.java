package com.Josmarinr.PedidosPoseidon.domain.permission.port.in;

import com.Josmarinr.PedidosPoseidon.domain.permission.model.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    Permission createPermission(String name, String description, String resource, String action);
    Optional<Permission> findById(Long id);
    Optional<Permission> findByName(String name);
    List<Permission> findAllActive();
    List<Permission> findByResource(String resource);
    Permission updatePermission(Long id, String name, String description, String resource, String action);
    void deactivatePermission(Long permissionId);
    void activatePermission(Long permissionId);

}
