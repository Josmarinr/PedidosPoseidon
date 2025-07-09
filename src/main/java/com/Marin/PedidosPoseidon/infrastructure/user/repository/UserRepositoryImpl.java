package com.Marin.PedidosPoseidon.infrastructure.user.repository;

import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.entity.UserRole;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import com.Marin.PedidosPoseidon.infrastructure.user.mapper.UserJpaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaRepository;
    private final UserJpaMapper mapper;

    // ========================================
    // MÉTODOS BÁSICOS DEL DOMINIO
    // ========================================

    @Override
    @Transactional
    public User save(User user) {
        log.debug("Guardando usuario: {}", user.getUsername());
        var jpaEntity = mapper.toJpaEntity(user);
        var savedEntity = jpaRepository.save(jpaEntity);
        return mapper.toDomainEntity(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username)
                .map(mapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(mapper::toDomainEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByRole(UserRole role) {
        return jpaRepository.findByRole(role)
                .stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(UUID id) {
        log.debug("Eliminando usuario con ID: {}", id);
        jpaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    // ========================================
    // MÉTODOS ADICIONALES CON HIBERNATE
    // ========================================

    @Transactional(readOnly = true)
    public List<User> findActiveUsers() {
        log.debug("Buscando usuarios activos");
        return jpaRepository.findByActiveTrue()
                .stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<User> findInactiveUsers() {
        log.debug("Buscando usuarios inactivos");
        return jpaRepository.findByActiveFalse()
                .stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<User> findActiveUserByUsername(String username) {
        return jpaRepository.findByUsernameAndActiveTrue(username)
                .map(mapper::toDomainEntity);
    }

    @Transactional(readOnly = true)
    public Optional<User> findActiveUserByEmail(String email) {
        return jpaRepository.findByEmailAndActiveTrue(email)
                .map(mapper::toDomainEntity);
    }

    @Transactional(readOnly = true)
    public List<User> findActiveUsersByRole(UserRole role) {
        return jpaRepository.findByRoleAndActiveTrue(role)
                .stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    // ========================================
    // MÉTODOS DE BÚSQUEDA AVANZADA
    // ========================================

    @Transactional(readOnly = true)
    public List<User> searchUsersByName(String searchTerm) {
        log.debug("Buscando usuarios por nombre: {}", searchTerm);

        List<User> results = jpaRepository.findByFullNameContainingIgnoreCase(searchTerm)
                .stream()
                .map(mapper::toDomainEntity)
                .collect(Collectors.toList());

        // También buscar por nombre individual si no se encontraron resultados
        if (results.isEmpty()) {
            results = jpaRepository.findByFirstNameContainingIgnoreCase(searchTerm)
                    .stream()
                    .map(mapper::toDomainEntity)
                    .collect(Collectors.toList());

            if (results.isEmpty()) {
                results = jpaRepository.findByLastNameContainingIgnoreCase(searchTerm)
                        .stream()
                        .map(mapper::toDomainEntity)
                        .toList();
            }
        }

        return results;
    }

    @Transactional(readOnly = true)
    public Page<User> searchUsersWithFilters(UserRole role, Boolean active, String searchTerm,
                                             int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jpaRepository.findUsersWithFilters(role, active, searchTerm, pageable)
                .map(mapper::toDomainEntity);
    }

    @Transactional(readOnly = true)
    public Page<User> findUsersWithPagination(int page, int size, String sortBy, String direction) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ?
                Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        return jpaRepository.findAll(pageable)
                .map(mapper::toDomainEntity);
    }

    @Transactional(readOnly = true)
    public Page<User> findActiveUsersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return jpaRepository.findByActiveTrue(pageable)
                .map(mapper::toDomainEntity);
    }

    // ========================================
    // MÉTODOS DE FECHA Y TIEMPO
    // ========================================

    @Transactional(readOnly = true)
    public List<User> findRecentUsers(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return jpaRepository.findRecentUsers(since)
                .stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<User> findMostActiveUsers(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return jpaRepository.findMostRecentlyActiveUsers(pageable)
                .stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<User> findUsersByDateRange(LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByCreatedAtBetween(start, end)
                .stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<User> findInactiveUsersByDays(int daysInactive) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysInactive);
        return jpaRepository.findByUpdatedAtBeforeAndActiveTrue(cutoffDate)
                .stream()
                .map(mapper::toDomainEntity)
                .toList();
    }

    // ========================================
    // MÉTODOS DE VALIDACIÓN
    // ========================================

    @Transactional(readOnly = true)
    public boolean isUsernameAvailableForUser(String username, UUID userId) {
        return !jpaRepository.existsByUsernameAndIdNot(username, userId);
    }

    @Transactional(readOnly = true)
    public boolean isEmailAvailableForUser(String email, UUID userId) {
        return !jpaRepository.existsByEmailAndIdNot(email, userId);
    }

    // ========================================
    // OPERACIONES DE ACTUALIZACIÓN
    // ========================================

    @Transactional
    public void deactivateUser(UUID userId) {
        log.debug("Desactivando usuario con ID: {}", userId);
        jpaRepository.deactivateUser(userId);
    }

    @Transactional
    public void activateUser(UUID userId) {
        log.debug("Activando usuario con ID: {}", userId);
        jpaRepository.activateUser(userId);
    }

    @Transactional
    public int bulkUpdateUserRoles(UserRole fromRole, UserRole toRole) {
        log.info("Actualizando roles de {} a {}", fromRole, toRole);
        return jpaRepository.updateRoleForActiveUsers(fromRole, toRole);
    }

    // ========================================
    // MÉTODOS DE ESTADÍSTICAS
    // ========================================

    @Transactional(readOnly = true)
    public long countActiveUsers() {
        return jpaRepository.countByActiveTrue();
    }

    @Transactional(readOnly = true)
    public long countInactiveUsers() {
        return jpaRepository.countByActiveFalse();
    }

    @Transactional(readOnly = true)
    public long countUsersByRole(UserRole role) {
        return jpaRepository.countByRole(role);
    }

    @Transactional(readOnly = true)
    public long countActiveUsersByRole(UserRole role) {
        return jpaRepository.countByRoleAndActiveTrue(role);
    }

    @Transactional(readOnly = true)
    public Map<UserRole, Long> getUserCountByRole() {
        return jpaRepository.getUserCountByRole()
                .stream()
                .collect(Collectors.toMap(
                        row -> (UserRole) row[0],
                        row -> (Long) row[1]
                ));
    }

    @Transactional(readOnly = true)
    public long countUsersCreatedInLastDays(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return jpaRepository.countUsersCreatedSince(since);
    }

    @Transactional(readOnly = true)
    public long countUsersCreatedAfter(LocalDateTime date) {
        return jpaRepository.countByCreatedAtAfter(date);
    }
}