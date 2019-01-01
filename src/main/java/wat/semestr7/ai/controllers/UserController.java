package wat.semestr7.ai.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
    private UserDetailsService userService;

    @Autowired
    public UserController(UserDetailsService userService) {
        this.userService = userService;
    }



}
