package wat.semestr8.tim.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.services.ticketsending.TicketSendingService;

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
