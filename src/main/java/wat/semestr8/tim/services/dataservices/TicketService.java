package wat.semestr8.tim.services.dataservices;

import org.springframework.stereotype.Service;
import wat.semestr8.tim.dtos.TicketDto;
import wat.semestr8.tim.entities.Concert;
import wat.semestr8.tim.entities.Purchase;
import wat.semestr8.tim.entities.Seat;
import wat.semestr8.tim.entities.Ticket;
import wat.semestr8.tim.exceptions.customexceptions.EntityNotFoundException;
import wat.semestr8.tim.repositories.TicketRepository;

import java.util.List;

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

    public void buyTicket(TicketDto ticketDto, Concert concert, Purchase purchase) throws EntityNotFoundException {
        Seat seat = seatService.getSeatByRowAndPosition(ticketDto.getSeatRow(),ticketDto.getSeatCol());
        Ticket ticket = new Ticket();
        ticket.setSeat(seat);
        ticket.setConcert(concert);
        ticket.setPurchase(purchase);
        ticket.setDiscount(discountService.getByName(ticketDto.getDiscountName()));
        ticketRepository.save(ticket);
    }

    public Ticket getFirstTicketByPurchase(Purchase purchase)
    {
        return ticketRepository.findFirstByPurchase(purchase);
    }

    public List<Ticket> getAllTicketsByPurchase(Purchase purchase)
    {
        return ticketRepository.findAllByPurchase(purchase);
    }

    public List<Ticket> getAllTicketsByConcert(Concert concert)
    {
        return ticketRepository.findAllPaidByConcert(concert.getIdConcert());
    }
}
