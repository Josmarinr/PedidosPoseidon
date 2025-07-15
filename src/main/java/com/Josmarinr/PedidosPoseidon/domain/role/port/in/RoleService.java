package com.Josmarinr.PedidosPoseidon.domain.role.port.in;

import com.Josmarinr.PedidosPoseidon.domain.permission.model.Permission;
import com.Josmarinr.PedidosPoseidon.domain.role.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role createRole(String name, String description);
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    List<Role> findAllActive();
    Role updateRole(Long id, String name, String description);
    void assignPermission(Long roleId, Long permissionId);
    void removePermission(Long roleId, Long permissionId);
    void deactivateRole(Long roleId);
    void activateRole(Long roleId);
    List<Permission> getRolePermissions(Long roleId);

}
