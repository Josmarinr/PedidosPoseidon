package com.Marin.PedidosPoseidon.application.user.service;

import com.Marin.PedidosPoseidon.application.user.usecase.DeleteUserUseCase;
import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.domain.user.exception.UserException;
import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DeleteUserService implements DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuario no encontrado"));

        user.deactivate();
        userRepository.save(user);

    }
}
