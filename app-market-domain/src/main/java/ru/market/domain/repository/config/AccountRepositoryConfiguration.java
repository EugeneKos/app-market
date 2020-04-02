package ru.market.domain.repository.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ru.market.domain.repository.account.AccountRepositoryImpl;

@Configuration
@EnableJpaRepositories(basePackages = {"ru.market.domain.repository.account"},
        repositoryBaseClass = AccountRepositoryImpl.class)
public class AccountRepositoryConfiguration {
}
