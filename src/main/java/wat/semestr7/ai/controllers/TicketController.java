package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.services.TicketService;

@RestController
public class TicketController
{
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
}
