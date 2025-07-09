package com.Marin.PedidosPoseidon.infrastructure.user.repository;

import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.entity.UserRole;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import com.Marin.PedidosPoseidon.infrastructure.user.mapper.UserJpaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserJpaMapper userJpaMapper;

    @Override
    public User save(User user) {
        var jpaEntity = userJpaMapper.toJpaEntity(user);
        var savedEntity = jpaUserRepository.save(jpaEntity);
        return userJpaMapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserRepository.findById(id)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userJpaMapper::toDomainEntity);
    }

    @Override
    public List<User> findByRole(UserRole role) {
        return jpaUserRepository.findByRole(role)
                .stream()
                .map(userJpaMapper::toDomainEntity)
                .toList();
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll()
                .stream()
                .map(userJpaMapper::toDomainEntity)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

}
