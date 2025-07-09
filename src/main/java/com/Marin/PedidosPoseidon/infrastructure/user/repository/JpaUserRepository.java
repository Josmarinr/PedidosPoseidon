package com.Marin.PedidosPoseidon.infrastructure.user.repository;

import com.Marin.PedidosPoseidon.domain.user.entity.UserRole;
import com.Marin.PedidosPoseidon.infrastructure.user.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserJpaEntity, UUID> {

    // Métodos básicos que ya tienes
    Optional<UserJpaEntity> findByUsername(String username);
    Optional<UserJpaEntity> findByEmail(String email);
    List<UserJpaEntity> findByRole(UserRole role);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Métodos que necesitas arreglar con @Query
    @Query("SELECT u FROM UserJpaEntity u WHERE u.active = true")
    List<UserJpaEntity> findByActiveTrue();

    @Query("SELECT u FROM UserJpaEntity u WHERE u.active = false")
    List<UserJpaEntity> findByActiveFalse();

    @Query("SELECT u FROM UserJpaEntity u WHERE u.role = :role AND u.active = true")
    List<UserJpaEntity> findByRoleAndActiveTrue(@Param("role") UserRole role);

    @Query("SELECT u FROM UserJpaEntity u WHERE u.username = :username AND u.active = true")
    Optional<UserJpaEntity> findByUsernameAndActiveTrue(@Param("username") String username);

    @Query("SELECT u FROM UserJpaEntity u WHERE u.email = :email AND u.active = true")
    Optional<UserJpaEntity> findByEmailAndActiveTrue(@Param("email") String email);

    // Operaciones de actualización
    @Modifying
    @Query("UPDATE UserJpaEntity u SET u.active = false WHERE u.id = :userId")
    void deactivateUser(@Param("userId") UUID userId);

    @Modifying
    @Query("UPDATE UserJpaEntity u SET u.active = true WHERE u.id = :userId")
    void activateUser(@Param("userId") UUID userId);

    // Verificaciones de unicidad
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserJpaEntity u WHERE u.username = :username AND u.id != :id")
    boolean existsByUsernameAndIdNot(@Param("username") String username, @Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserJpaEntity u WHERE u.email = :email AND u.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") UUID id);
}