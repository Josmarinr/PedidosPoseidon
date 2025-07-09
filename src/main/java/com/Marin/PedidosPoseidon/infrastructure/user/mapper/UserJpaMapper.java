package com.Marin.PedidosPoseidon.infrastructure.user.mapper;

import com.Marin.PedidosPoseidon.domain.user.entity.User;
import com.Marin.PedidosPoseidon.infrastructure.user.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserJpaMapper {

    public UserJpaEntity toJpaEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User toDomainEntity(UserJpaEntity jpaEntity) {
        return User.builder()
                .id(jpaEntity.getId())
                .username(jpaEntity.getUsername())
                .email(jpaEntity.getEmail())
                .password(jpaEntity.getPassword())
                .firstName(jpaEntity.getFirstName())
                .lastName(jpaEntity.getLastName())
                .role(jpaEntity.getRole())
                .active(jpaEntity.isActive())
                .createdAt(jpaEntity.getCreatedAt())
                .updatedAt(jpaEntity.getUpdatedAt())
                .build();
    }

}
