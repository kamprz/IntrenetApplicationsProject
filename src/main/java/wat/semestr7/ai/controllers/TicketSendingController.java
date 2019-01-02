package wat.semestr7.ai.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr7.ai.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr7.ai.services.ticketsending.TicketSendingService;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class TicketSendingController
{
    public TicketSendingController(TicketSendingService ticketSendingService) {
        this.ticketSendingService = ticketSendingService;
    }

    private TicketSendingService ticketSendingService;
    @PostMapping("/admin/generate-ticket")
    public boolean sendTicketAgain(int purchaseId) throws EntityNotFoundException, MessagingException, IOException
    {
        return ticketSendingService.sendTickets(purchaseId);
    }
}
