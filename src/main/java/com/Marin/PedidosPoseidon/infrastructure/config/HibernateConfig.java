package com.Marin.PedidosPoseidon.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.Marin.PedidosPoseidon.infrastructure")
@EnableTransactionManagement
@Slf4j
public class HibernateConfig {
    // Configuración mínima - Spring Boot se encarga del resto
}