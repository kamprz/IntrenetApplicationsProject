package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.services.UserService;

@RestController
public class UserController
{
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
