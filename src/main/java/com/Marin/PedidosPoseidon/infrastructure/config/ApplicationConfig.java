package com.Marin.PedidosPoseidon.infrastructure.config;

import com.Marin.PedidosPoseidon.domain.user.repository.UserRepository;
import com.Marin.PedidosPoseidon.domain.user.service.PasswordService;
import com.Marin.PedidosPoseidon.domain.user.service.UserDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDomainService userDomainService(
            UserRepository userRepository,
            PasswordService passwordService) {
        return new UserDomainService(userRepository, passwordService);
    }

}
