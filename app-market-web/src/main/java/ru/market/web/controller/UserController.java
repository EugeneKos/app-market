package ru.market.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ru.market.domain.data.dto.UserDTO;
import ru.market.domain.data.dto.UserWithPasswordDTO;
import ru.market.domain.service.IUserService;

import java.util.Map;

@Controller
@RequestMapping(path = "/users")
public class UserController {
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ModelAndView createUser(@RequestParam Map<String, String> requestParams, ModelMap modelMap){
        UserWithPasswordDTO userWithPasswordDTO = createUserDTO(requestParams);

        UserDTO userDTO = userService.create(userWithPasswordDTO);
        modelMap.addAttribute("user", userDTO);

        return new ModelAndView("user");
    }

    private UserWithPasswordDTO createUserDTO(Map<String, String> requestParams){
        UserWithPasswordDTO userWithPasswordDTO = new UserWithPasswordDTO();
        userWithPasswordDTO.setFirstName(requestParams.get("firstName"));
        userWithPasswordDTO.setLastName(requestParams.get("lastName"));
        userWithPasswordDTO.setMiddleName(requestParams.get("middleName"));
        userWithPasswordDTO.setLogin(requestParams.get("login"));
        userWithPasswordDTO.setPassword(requestParams.get("password"));
        return userWithPasswordDTO;
    }
}
