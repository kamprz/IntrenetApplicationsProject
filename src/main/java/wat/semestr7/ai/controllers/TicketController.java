package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.dtos.TicketDto;
import wat.semestr7.ai.services.dataservices.TicketService;

@RestController
public class TicketController
{
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


}
