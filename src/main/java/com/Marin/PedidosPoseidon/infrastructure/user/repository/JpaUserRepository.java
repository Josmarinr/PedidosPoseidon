package com.Marin.PedidosPoseidon.infrastructure.user.repository;

import com.Marin.PedidosPoseidon.domain.user.entity.UserRole;
import com.Marin.PedidosPoseidon.infrastructure.user.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserJpaEntity, UUID> {

    Optional<UserJpaEntity> findByUsername(String username);
    Optional<UserJpaEntity> findByEmail(String email);
    List<UserJpaEntity> findByRole(UserRole role);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserJpaEntity u WHERE u.active = true")
    List<UserJpaEntity> findAllActive();

    @Query("SELECT u FROM UserJpaEntity u WHERE u.role = :role AND u.active = true")
    List<UserJpaEntity> findActiveByRole(@Param("role") UserRole role);

}
