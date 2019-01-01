package wat.semestr7.ai.services.dataservices;

import org.springframework.stereotype.Service;
import wat.semestr7.ai.dtos.TicketDto;
import wat.semestr7.ai.entities.Concert;
import wat.semestr7.ai.entities.Purchase;
import wat.semestr7.ai.entities.Seat;
import wat.semestr7.ai.entities.Ticket;
import wat.semestr7.ai.repositories.TicketRepository;

@Service
public class TicketService
{
    private TicketRepository ticketRepository;
    private SeatService seatService;
    private DiscountService discountService;

    public TicketService(TicketRepository ticketRepository, SeatService seatService, DiscountService discountService) {
        this.ticketRepository = ticketRepository;
        this.seatService = seatService;
        this.discountService = discountService;
    }

    public void buyTicket(TicketDto ticketDto, Concert concert, Purchase purchase)
    {
        Seat seat = seatService.getSeatByRowAndPosition(ticketDto.getSeatRow(),ticketDto.getSeatCol());
        Ticket ticket = new Ticket();
        ticket.setSeat(seat);
        ticket.setConcert(concert);
        ticket.setPurchase(purchase);
        ticket.setDiscount(discountService.getByName(ticketDto.getDiscountName()));
        ticketRepository.save(ticket);
    }
}
