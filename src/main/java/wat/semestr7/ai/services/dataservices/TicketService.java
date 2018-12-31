package wat.semestr7.ai.services;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.TicketDto;
import wat.semestr7.ai.repositories.TicketRepository;

@Service
public class TicketService
{
    private TicketRepository ticketRepository;


    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public boolean buyTicket(TicketDto dto, int concertId) {

    }
}
