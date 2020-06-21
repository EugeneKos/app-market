package ru.market.cli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.ed.microlib.command.Command;

import ru.market.cli.command.auth.AuthenticateCommand;
import ru.market.cli.command.auth.LogoutCommand;
import ru.market.cli.command.money.CreateMoneyAccountCommand;
import ru.market.cli.command.money.DeleteMoneyAccountCommand;
import ru.market.cli.command.money.GetAllMoneyAccountCommand;
import ru.market.cli.command.money.GetMoneyAccountCommand;
import ru.market.cli.command.person.CurrentPersonCommand;
import ru.market.cli.command.person.UpdatePersonCommand;
import ru.market.cli.command.user.RegistrationUserCommand;
import ru.market.client.config.ClientConfiguration;
import ru.market.client.rest.AuthenticateRestClient;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.client.rest.PersonRestClient;
import ru.market.client.rest.UserRestClient;

@Configuration
@Import(ClientConfiguration.class)
public class CommandConfiguration {
    @Bean
    public Command authenticateCommand(AuthenticateRestClient authenticateRestClient){
        return new AuthenticateCommand(authenticateRestClient);
    }

    @Bean
    public Command logoutCommand(AuthenticateRestClient authenticateRestClient){
        return new LogoutCommand(authenticateRestClient);
    }

    @Bean
    public Command registrationUserCommand(UserRestClient userRestClient){
        return new RegistrationUserCommand(userRestClient);
    }

    @Bean
    public Command currentPersonCommand(PersonRestClient personRestClient){
        return new CurrentPersonCommand(personRestClient);
    }

    @Bean
    public Command updatePersonCommand(PersonRestClient personRestClient){
        return new UpdatePersonCommand(personRestClient);
    }

    @Bean
    public Command createMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient){
        return new CreateMoneyAccountCommand(moneyAccountRestClient);
    }

    @Bean
    public Command getMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient){
        return new GetMoneyAccountCommand(moneyAccountRestClient);
    }

    @Bean
    public Command getAllMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient){
        return new GetAllMoneyAccountCommand(moneyAccountRestClient);
    }

    @Bean
    public Command deleteMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient){
        return new DeleteMoneyAccountCommand(moneyAccountRestClient);
    }
}
