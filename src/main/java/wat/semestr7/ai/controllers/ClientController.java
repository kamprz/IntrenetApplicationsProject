package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.services.ClientService;

@RestController
public class ClientController
{
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
}
