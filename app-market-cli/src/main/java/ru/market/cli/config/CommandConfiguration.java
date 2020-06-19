package ru.market.cli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ru.ed.microlib.command.Command;

import ru.market.cli.command.auth.AuthenticateCommand;
import ru.market.cli.command.auth.LogoutCommand;
import ru.market.cli.command.money.CreateMoneyAccountCommand;
import ru.market.cli.command.money.GetAllMoneyAccountCommand;
import ru.market.cli.command.person.CurrentPersonCommand;
import ru.market.cli.command.person.UpdatePersonCommand;
import ru.market.cli.command.user.RegistrationUserCommand;
import ru.market.cli.printer.Printer;
import ru.market.client.config.ClientConfiguration;
import ru.market.client.rest.AuthenticateRestClient;
import ru.market.client.rest.MoneyAccountRestClient;
import ru.market.client.rest.PersonRestClient;
import ru.market.client.rest.UserRestClient;

@Configuration
@Import(ClientConfiguration.class)
public class CommandConfiguration {
    @Bean
    public Command authenticateCommand(AuthenticateRestClient authenticateRestClient, Printer printer){
        return new AuthenticateCommand(authenticateRestClient, printer);
    }

    @Bean
    public Command logoutCommand(AuthenticateRestClient authenticateRestClient){
        return new LogoutCommand(authenticateRestClient);
    }

    @Bean
    public Command registrationUserCommand(UserRestClient userRestClient, Printer printer){
        return new RegistrationUserCommand(userRestClient, printer);
    }

    @Bean
    public Command currentPersonCommand(PersonRestClient personRestClient, Printer printer){
        return new CurrentPersonCommand(personRestClient, printer);
    }

    @Bean
    public Command updatePersonCommand(PersonRestClient personRestClient, Printer printer){
        return new UpdatePersonCommand(personRestClient, printer);
    }

    @Bean
    public Command createMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, Printer printer){
        return new CreateMoneyAccountCommand(moneyAccountRestClient, printer);
    }

    @Bean
    public Command getAllMoneyAccountCommand(MoneyAccountRestClient moneyAccountRestClient, Printer printer){
        return new GetAllMoneyAccountCommand(moneyAccountRestClient, printer);
    }
}
