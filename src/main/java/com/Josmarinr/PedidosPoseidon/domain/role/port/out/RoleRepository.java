package com.Josmarinr.PedidosPoseidon.domain.role.port.out;

import com.Josmarinr.PedidosPoseidon.domain.role.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    Role save(Role role);
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    List<Role> findAllActive();
    void deleteById(Long id);
    boolean existsByName(String name);

}
