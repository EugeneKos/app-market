package ru.market.web.auth;

import ru.market.domain.service.IPersonService;
import ru.market.dto.auth.AuthAnswerDTO;
import ru.market.dto.auth.UsernamePasswordDTO;
import ru.market.dto.person.PersonDTO;
import ru.market.dto.person.PersonWithPasswordDTO;
import ru.market.web.session.SessionContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {
    private IPersonService personService;

    @Autowired
    public AuthenticateServiceImpl(IPersonService personService) {
        this.personService = personService;
    }

    @Override
    public AuthAnswerDTO authenticate(UsernamePasswordDTO usernamePasswordDTO) {
        PersonWithPasswordDTO person = personService.getByUserNameWithPassword(usernamePasswordDTO.getUsername());
        if(person == null){
            return new AuthAnswerDTO("failed", "username not found");
        }

        return person.getPassword().equals(usernamePasswordDTO.getPassword())
                ? authenticateSuccess(person)
                : new AuthAnswerDTO("failed", "password doesn't match");
    }

    private AuthAnswerDTO authenticateSuccess(PersonDTO person){
        SessionContext.setPerson(person);
        return new AuthAnswerDTO("success", "authenticate success");
    }

    @Override
    public boolean isAuthenticate() {
        return SessionContext.getPerson() != null;
    }

    @Override
    public void invalidate() {
        SessionContext.removePerson();
        SessionContext.invalidateSession();
    }
}
