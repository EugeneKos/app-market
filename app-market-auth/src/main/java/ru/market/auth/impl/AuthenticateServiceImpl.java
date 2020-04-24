package ru.market.auth.impl;

import org.springframework.security.crypto.password.PasswordEncoder;

import ru.market.auth.api.AuthenticateService;

import ru.market.data.session.api.PersonDataManagement;
import ru.market.data.session.api.SessionManagement;

import ru.market.domain.service.IPersonService;

import ru.market.dto.auth.AuthAnswerDTO;
import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonWithPasswordDTO;

public class AuthenticateServiceImpl implements AuthenticateService {
    private static final int INACTIVE_INTERVAL = 1200; // 20 min

    private IPersonService personService;

    private PasswordEncoder passwordEncoder;

    private SessionManagement sessionManagement;
    private PersonDataManagement personDataManagement;

    public AuthenticateServiceImpl(IPersonService personService,
                                   PasswordEncoder passwordEncoder,
                                   SessionManagement sessionManagement,
                                   PersonDataManagement personDataManagement) {

        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
        this.sessionManagement = sessionManagement;
        this.personDataManagement = personDataManagement;
    }

    @Override
    public AuthAnswerDTO authenticate(UsernamePasswordDTO usernamePasswordDTO) {
        PersonWithPasswordDTO person = personService.getByUserNameWithPassword(usernamePasswordDTO.getUsername());
        if(person == null){
            return new AuthAnswerDTO("failed", "username not found");
        }

        return passwordEncoder.matches(usernamePasswordDTO.getPassword(), person.getPassword())
                ? authenticateSuccess(person)
                : new AuthAnswerDTO("failed", "password doesn't match");
    }

    private AuthAnswerDTO authenticateSuccess(PersonDTO person){
        personDataManagement.setPerson(person);
        sessionManagement.setMaxInactiveInterval(INACTIVE_INTERVAL);
        return new AuthAnswerDTO("success", "authenticate success");
    }

    @Override
    public boolean isAuthenticate() {
        return personDataManagement.getPerson() != null;
    }

    @Override
    public void invalidate() {
        personDataManagement.removePerson();
        sessionManagement.invalidateSession();
    }
}
