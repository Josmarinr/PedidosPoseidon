package com.Marin.PedidosPoseidon.application.user.service;

import com.Marin.PedidosPoseidon.application.user.dto.CreateUserRequest;
import com.Marin.PedidosPoseidon.application.user.dto.UserResponse;
import com.Marin.PedidosPoseidon.application.user.mapper.UserMapper;
import com.Marin.PedidosPoseidon.application.user.usecase.CreateUserUseCase;
import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import com.Marin.PedidosPoseidon.domain.user.service.PasswordService;
import com.Marin.PedidosPoseidon.domain.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final PasswordService passwordService;
    private final UserMapper userMapper;

    @Override
    public UserResponse execute(CreateUserRequest request) {
        // Validar fortaleza de contraseña
        passwordService.validatePasswordStrength(request.getPassword());

        // Crear entidad de dominio
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordService.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(request.getRole())
                .build();

        // Validar reglas de negocio
        userDomainService.validateUserCreation(user);

        // Guardar usuario
        User savedUser = userRepository.save(user);

        // Mapear a DTO de respuesta
        return userMapper.toResponse(savedUser);
    }
}
