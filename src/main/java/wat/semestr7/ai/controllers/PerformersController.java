package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.services.dataservices.PerformersService;

@RestController
public class PerformersController
{
    private PerformersService performersService;

    public PerformersController(PerformersService performersService) {
        this.performersService = performersService;
    }
}
