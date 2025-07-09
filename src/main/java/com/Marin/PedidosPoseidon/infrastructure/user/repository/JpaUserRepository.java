package com.Marin.PedidosPoseidon.infrastructure.user.repository;

import com.Marin.PedidosPoseidon.domain.user.entity.UserRole;
import com.Marin.PedidosPoseidon.infrastructure.user.entity.UserJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserJpaEntity, UUID> {

    // ========================================
    // MÉTODOS BÁSICOS CON NAMING CONVENTIONS
    // ========================================

    Optional<UserJpaEntity> findByUsername(String username);

    Optional<UserJpaEntity> findByEmail(String email);

    List<UserJpaEntity> findByRole(UserRole role);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // ========================================
    // MÉTODOS PARA USUARIOS ACTIVOS/INACTIVOS
    // ========================================

    List<UserJpaEntity> findByActiveTrue();

    List<UserJpaEntity> findByActiveFalse();

    List<UserJpaEntity> findByRoleAndActiveTrue(UserRole role);

    Optional<UserJpaEntity> findByUsernameAndActiveTrue(String username);

    Optional<UserJpaEntity> findByEmailAndActiveTrue(String email);

    // ========================================
    // PAGINACIÓN
    // ========================================

    Page<UserJpaEntity> findByActiveTrue(Pageable pageable);

    Page<UserJpaEntity> findByRole(UserRole role, Pageable pageable);

    Page<UserJpaEntity> findByRoleAndActiveTrue(UserRole role, Pageable pageable);

    // ========================================
    // BÚSQUEDAS POR NOMBRE
    // ========================================

    List<UserJpaEntity> findByFirstNameContainingIgnoreCase(String firstName);

    List<UserJpaEntity> findByLastNameContainingIgnoreCase(String lastName);

    // Búsqueda por nombre completo usando JPQL
    @Query("SELECT u FROM UserJpaEntity u WHERE " +
            "LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :fullName, '%'))")
    List<UserJpaEntity> findByFullNameContainingIgnoreCase(@Param("fullName") String fullName);

    // ========================================
    // BÚSQUEDAS POR FECHAS
    // ========================================

    List<UserJpaEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<UserJpaEntity> findByCreatedAtAfter(LocalDateTime date);

    List<UserJpaEntity> findByUpdatedAtAfter(LocalDateTime date);

    List<UserJpaEntity> findByUpdatedAtBeforeAndActiveTrue(LocalDateTime cutoffDate);

    // ========================================
    // CONTADORES
    // ========================================

    long countByRole(UserRole role);

    long countByActiveTrue();

    long countByActiveFalse();

    long countByRoleAndActiveTrue(UserRole role);

    long countByCreatedAtAfter(LocalDateTime date);

    // ========================================
    // OPERACIONES DE ACTUALIZACIÓN CON JPQL
    // ========================================

    @Modifying
    @Query("UPDATE UserJpaEntity u SET u.active = false WHERE u.id = :userId")
    void deactivateUser(@Param("userId") UUID userId);

    @Modifying
    @Query("UPDATE UserJpaEntity u SET u.active = true WHERE u.id = :userId")
    void activateUser(@Param("userId") UUID userId);

    @Modifying
    @Query("UPDATE UserJpaEntity u SET u.role = :newRole WHERE u.role = :oldRole AND u.active = true")
    int updateRoleForActiveUsers(@Param("oldRole") UserRole oldRole, @Param("newRole") UserRole newRole);

    // ========================================
    // VERIFICACIONES DE UNICIDAD
    // ========================================

    boolean existsByUsernameAndIdNot(String username, UUID id);

    boolean existsByEmailAndIdNot(String email, UUID id);

    // ========================================
    // BÚSQUEDAS COMPLEJAS CON JPQL
    // ========================================

    @Query("SELECT u FROM UserJpaEntity u WHERE " +
            "(:role IS NULL OR u.role = :role) AND " +
            "(:active IS NULL OR u.active = :active) AND " +
            "(:searchTerm IS NULL OR " +
            " LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            " LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            " LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "ORDER BY u.createdAt DESC")
    Page<UserJpaEntity> findUsersWithFilters(
            @Param("role") UserRole role,
            @Param("active") Boolean active,
            @Param("searchTerm") String searchTerm,
            Pageable pageable);

    // ========================================
    // ESTADÍSTICAS CON JPQL
    // ========================================

    @Query("SELECT u.role, COUNT(u) FROM UserJpaEntity u WHERE u.active = true GROUP BY u.role")
    List<Object[]> getUserCountByRole();

    @Query("SELECT COUNT(u) FROM UserJpaEntity u WHERE u.createdAt >= :since")
    long countUsersCreatedSince(@Param("since") LocalDateTime since);

    // Usuarios más activos (por última actualización)
    @Query("SELECT u FROM UserJpaEntity u WHERE u.active = true ORDER BY u.updatedAt DESC")
    List<UserJpaEntity> findMostRecentlyActiveUsers(Pageable pageable);

    // Usuarios recientes
    @Query("SELECT u FROM UserJpaEntity u WHERE u.createdAt >= :since ORDER BY u.createdAt DESC")
    List<UserJpaEntity> findRecentUsers(@Param("since") LocalDateTime since);
}