package com.Marin.PedidosPoseidon.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "com.Marin.PedidosPoseidon.infrastructure")
@EnableJpaAuditing
@EnableTransactionManagement
@Slf4j
public class HibernateConfig {

    /**
     * Configuraciones adicionales de Hibernate
     */
    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return new HibernatePropertiesCustomizer() {
            @Override
            public void customize(Map<String, Object> hibernateProperties) {
                log.info("Configurando propiedades avanzadas de Hibernate");

                // Configuraciones de rendimiento
                hibernateProperties.put("hibernate.jdbc.fetch_size", 50);
                hibernateProperties.put("hibernate.default_batch_fetch_size", 16);

                // Configuraciones de logging SQL (solo en desarrollo)
                hibernateProperties.put("hibernate.show_sql", false);
                hibernateProperties.put("hibernate.format_sql", true);
                hibernateProperties.put("hibernate.use_sql_comments", true);

                // Configuraciones de optimización
                hibernateProperties.put("hibernate.jdbc.time_zone", "America/Bogota");
                hibernateProperties.put("hibernate.connection.provider_disables_autocommit", true);

                // Configuraciones de conexión
                hibernateProperties.put("hibernate.connection.autocommit", false);
                hibernateProperties.put("hibernate.connection.release_mode", "after_transaction");

                log.info("Propiedades de Hibernate configuradas exitosamente");
            }
        };
    }
}