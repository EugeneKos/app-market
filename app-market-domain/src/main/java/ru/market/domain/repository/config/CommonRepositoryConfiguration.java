package ru.market.domain.repository.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"ru.market.domain.repository.common"})
public class CommonRepositoryConfiguration {
}
