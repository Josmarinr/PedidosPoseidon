package com.Marin.PedidosPoseidon.application.user.service;

import com.Marin.PedidosPoseidon.application.user.dto.UpdateUserRequest;
import com.Marin.PedidosPoseidon.application.user.dto.UserResponse;
import com.Marin.PedidosPoseidon.application.user.mapper.UserMapper;
import com.Marin.PedidosPoseidon.application.user.usecase.UpdateUserUseCase;
import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.exception.UserException;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class UpdateUserService implements UpdateUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UpdateUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse execute(UUID userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuario no encontrado"));

        // Actualizar campos si están presentes
        if (request.getUsername() != null) {
            if (userRepository.existsByUsername(request.getUsername()) &&
                    !user.getUsername().equals(request.getUsername())) {
                throw new UserException("El nombre de usuario ya existe");
            }
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null) {
            if (userRepository.existsByEmail(request.getEmail()) &&
                    !user.getEmail().equals(request.getEmail())) {
                throw new UserException("El email ya está registrado");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }

        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }
}
