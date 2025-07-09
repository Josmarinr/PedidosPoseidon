package com.Marin.PedidosPoseidon.application.user.service;

import com.Marin.PedidosPoseidon.application.user.dto.UserResponse;
import com.Marin.PedidosPoseidon.application.user.mapper.UserMapper;
import com.Marin.PedidosPoseidon.application.user.usecase.FindUserUseCase;
import com.Marin.PedidosPoseidon.domain.user.entity.UserRole;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FindUserService implements FindUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public FindUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserResponse> findById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse);
    }

    @Override
    public Optional<UserResponse> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toResponse);
    }

    @Override
    public Optional<UserResponse> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toResponse);
    }

    @Override
    public List<UserResponse> findByRole(UserRole role) {
        return userRepository.findByRole(role)
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }
}
